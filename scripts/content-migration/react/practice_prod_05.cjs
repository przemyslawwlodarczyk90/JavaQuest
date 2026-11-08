const {lesson,nodeSafeSetup}=require('./practice_kit.cjs');
const ci=`# .github/workflows/ci.yml
name: CI

on:
  pull_request:
  push:
    branches: [main]

permissions:
  contents: read

concurrency:
  group: ci-\${{ github.ref }}
  cancel-in-progress: true

jobs:
  check:
    runs-on: ubuntu-latest
    timeout-minutes: 15
    steps:
      - uses: actions/checkout@v4
      - uses: actions/setup-node@v4
        with:
          node-version-file: .nvmrc
          cache: npm
      - run: npm ci
      - run: npm run lint
      - run: npx tsc --noEmit -p tsconfig.app.json
      - run: npx vitest run --coverage
      - run: npm run build
        env:
          VITE_API_URL: /api
      - uses: actions/upload-artifact@v4
        with:
          name: dist
          path: dist
          retention-days: 7

  e2e:
    needs: check
    runs-on: ubuntu-latest
    timeout-minutes: 20
    steps:
      - uses: actions/checkout@v4
      - uses: actions/setup-node@v4
        with:
          node-version-file: .nvmrc
          cache: npm
      - run: npm ci
      - run: npx playwright install --with-deps chromium
      - uses: actions/download-artifact@v4
        with:
          name: dist
          path: dist
      - run: npx playwright test
      - uses: actions/upload-artifact@v4
        if: failure()
        with:
          name: playwright-report
          path: playwright-report
`;
const deploy=`# .github/workflows/deploy.yml
name: Deploy

on:
  workflow_run:
    workflows: [CI]
    types: [completed]
    branches: [main]

permissions:
  contents: read

jobs:
  deploy:
    if: \${{ github.event.workflow_run.conclusion == 'success' }}
    runs-on: ubuntu-latest
    environment:
      name: production
      url: https://devdesk.example
    permissions:
      contents: read
      deployments: write
    steps:
      - uses: actions/checkout@v4
        with:
          ref: \${{ github.event.workflow_run.head_sha }}
      - uses: actions/setup-node@v4
        with:
          node-version-file: .nvmrc
          cache: npm
      - run: npm ci
      - run: npm run build
        env:
          VITE_API_URL: \${{ vars.API_URL }}
          VITE_SENTRY_DSN: \${{ vars.SENTRY_DSN }}
          VITE_APP_VERSION: \${{ github.event.workflow_run.head_sha }}
      - name: Wdrożenie na Cloudflare Pages
        run: npx wrangler pages deploy dist --project-name=devdesk --branch=main
        env:
          CLOUDFLARE_API_TOKEN: \${{ secrets.CLOUDFLARE_API_TOKEN }}
          CLOUDFLARE_ACCOUNT_ID: \${{ vars.CLOUDFLARE_ACCOUNT_ID }}
      - name: Wysłanie map źródeł do Sentry
        run: npx @sentry/cli sourcemaps upload --release=\${{ github.event.workflow_run.head_sha }} dist
        env:
          SENTRY_AUTH_TOKEN: \${{ secrets.SENTRY_AUTH_TOKEN }}
`;
const imageJob=`# fragment .github/workflows/deploy.yml
  image:
    runs-on: ubuntu-latest
    permissions:
      contents: read
      packages: write
    steps:
      - uses: actions/checkout@v4
      - uses: docker/setup-buildx-action@v3
      - uses: docker/login-action@v3
        with:
          registry: ghcr.io
          username: \${{ github.actor }}
          password: \${{ secrets.GITHUB_TOKEN }}
      - uses: docker/build-push-action@v6
        with:
          context: ./frontend
          push: true
          tags: ghcr.io/\${{ github.repository }}/frontend:\${{ github.sha }}
          build-args: VITE_APP_VERSION=\${{ github.sha }}
          cache-from: type=gha
          cache-to: type=gha,mode=max
`;
const dependabot=`# .github/dependabot.yml
version: 2
updates:
  - package-ecosystem: npm
    directory: /
    schedule:
      interval: weekly
  - package-ecosystem: github-actions
    directory: /
    schedule:
      interval: weekly
`;
const pkg=JSON.stringify({private:true,type:'module',scripts:{lint:'eslint .',typecheck:'tsc --noEmit -p tsconfig.app.json',test:'vitest run',build:'vite build',verify:'npm run lint && npm run typecheck && npm test && npm run build'}},null,2);
const audit=String.raw`export type Step={run?:string;uses?:string;if?:string;env?:Record<string,string>;with?:Record<string,unknown>;name?:string};
export type Job={steps?:Step[];needs?:string|string[];permissions?:unknown;environment?:unknown;"timeout-minutes"?:number;if?:string};
export type Workflow={name?:string;on?:unknown;permissions?:unknown;concurrency?:unknown;jobs?:Record<string,Job>};

const COST:readonly [RegExp,number][]=[
  [/npm ci/,0],[/lint/,1],[/tsc/,2],[/vitest|npm (run )?test/,3],[/(vite|npm run) build/,4],[/playwright test/,5],
];

export function stepCost(run:string){
  return COST.find(([pattern])=>pattern.test(run))?.[1]??null;
}

export function triggers(workflow:Workflow){
  const on=workflow.on;
  if(typeof on==="string")return [on];
  if(Array.isArray(on))return on.map(String);
  return on&&typeof on==="object"?Object.keys(on):[];
}

export function auditWorkflow(workflow:Workflow,{typescript=true}:{typescript?:boolean}={}){
  const problems:string[]=[];
  const events=triggers(workflow);
  const jobs=Object.entries(workflow.jobs??{});
  const allSteps=jobs.flatMap(([,job])=>job.steps??[]);
  const commands=allSteps.flatMap(step=>step.run?[step.run]:[]);
  if(events.includes("pull_request_target")&&allSteps.some(step=>JSON.stringify(step.with??{}).includes("pull_request.head"))){
    problems.push("pull_request_target z kodem z PR daje dostęp do sekretów");
  }
  if(!workflow.permissions&&jobs.some(([,job])=>!job.permissions))problems.push("brak minimalnych uprawnień tokenu");
  if(commands.some(command=>/npm install\b/.test(command)))problems.push("npm install zamiast npm ci");
  if(typescript&&commands.some(command=>/(vite|npm run) build/.test(command))&&!commands.some(command=>/\btsc\b.*--noEmit|npm run typecheck/.test(command))){
    problems.push("build bez sprawdzania typów");
  }
  for(const [name,job] of jobs){
    if(job["timeout-minutes"]===undefined&&name!=="deploy"&&name!=="image")problems.push(name+": brak limitu czasu");
    const costs=(job.steps??[]).flatMap(step=>{
      const cost=step.run?stepCost(step.run):null;
      return cost===null?[]:[cost];
    });
    if(costs.some((cost,index)=>index>0&&cost<costs[index-1]))problems.push(name+": droższy krok przed tańszym");
    for(const step of job.steps??[]){
      if(step.run&&/\$\{\{\s*secrets\./.test(step.run))problems.push(name+": sekret wstawiony do polecenia");
      if(step.run&&/^\s*(echo\b.*\$[A-Z_]*(TOKEN|SECRET)|env\s*$)/.test(step.run))problems.push(name+": sekret lub środowisko w logach");
      if(step.uses&&!/@/.test(step.uses))problems.push(name+": akcja bez wersji");
    }
  }
  return problems;
}

export type Protection={requiredChecks:readonly string[];requireReview:boolean;allowForcePush:boolean;allowDirectPush:boolean};

export function auditProtection(protection:Protection,jobs:readonly string[]){
  const problems:string[]=[];
  for(const job of jobs)if(!protection.requiredChecks.includes("CI / "+job))problems.push("brak wymaganego statusu CI / "+job);
  if(!protection.requireReview)problems.push("brak wymaganego przeglądu");
  if(protection.allowForcePush)problems.push("dozwolony force push");
  if(protection.allowDirectPush)problems.push("dozwolony bezpośredni push");
  return problems;
}

export function canMerge(statuses:Record<string,"success"|"failure"|"pending">,protection:Pick<Protection,"requiredChecks">){
  return protection.requiredChecks.every(check=>statuses[check]==="success");
}

export function imageTag(repository:string,sha:string){
  if(!/^[0-9a-f]{7,40}$/.test(sha))throw new Error("Tag obrazu wymaga SHA commita");
  return "ghcr.io/"+repository.toLowerCase()+"/frontend:"+sha;
}`;
const tests=String.raw`import {readFileSync} from "node:fs";
import {resolve} from "node:path";
import {parse} from "yaml";
import {expect,test} from "vitest";
import {auditProtection,auditWorkflow,canMerge,imageTag,stepCost,triggers,type Workflow} from "./ciAudit";

const load=(file:string)=>parse(readFileSync(resolve(process.cwd(),file),"utf8")) as Workflow;
const ci=load(".github/workflows/ci.yml"),deploy=load(".github/workflows/deploy.yml");
const secret=(name:string)=>"$"+"{{ secrets."+name+" }}";

test("workflow lekcji przechodzi przegląd",()=>{
  expect(auditWorkflow(ci)).toEqual([]);
  expect(auditWorkflow(deploy,{typescript:false})).toEqual([]);
});

test("CI ma właściwe zdarzenia i kolejność jobów",()=>{
  expect(triggers(ci)).toEqual(["pull_request","push"]);
  expect(ci.jobs?.e2e.needs).toBe("check");
  const commands=(ci.jobs?.check.steps??[]).flatMap(step=>step.run?[step.run]:[]);
  expect(commands).toEqual(["npm ci","npm run lint","npx tsc --noEmit -p tsconfig.app.json","npx vitest run --coverage","npm run build"]);
});

test("wdrożenie startuje po CI z głównej gałęzi i ma środowisko produkcyjne",()=>{
  expect(triggers(deploy)).toEqual(["workflow_run"]);
  const job=deploy.jobs!.deploy;
  expect(job.if).toContain("conclusion == 'success'");
  expect(job.environment).toMatchObject({name:"production"});
  expect(job.permissions).toEqual({contents:"read",deployments:"write"});
  const upload=job.steps!.find(step=>step.name==="Wysłanie map źródeł do Sentry")!;
  expect(upload.run).toContain("head_sha");
  expect(upload.env?.SENTRY_AUTH_TOKEN).toBe(secret("SENTRY_AUTH_TOKEN"));
});

test("Dependabot obejmuje npm i akcje",()=>{
  const config=parse(readFileSync(resolve(process.cwd(),".github/dependabot.yml"),"utf8")) as {updates:{"package-ecosystem":string}[]};
  expect(config.updates.map(item=>item["package-ecosystem"])).toEqual(["npm","github-actions"]);
});

test("przegląd wykrywa niebezpieczne ustawienia",()=>{
  const base:Workflow={on:"pull_request",permissions:{contents:"read"},jobs:{a:{"timeout-minutes":5,steps:[]}}};
  expect(auditWorkflow({...base,on:{pull_request_target:{}},jobs:{a:{"timeout-minutes":5,steps:[{uses:"actions/checkout@v4",with:{ref:"$"+"{{ github.event.pull_request.head.sha }}"}}]}}})).toContain("pull_request_target z kodem z PR daje dostęp do sekretów");
  expect(auditWorkflow({on:"push",jobs:{a:{"timeout-minutes":5,steps:[]}}})).toContain("brak minimalnych uprawnień tokenu");
  expect(auditWorkflow({...base,jobs:{a:{"timeout-minutes":5,steps:[{run:"npm install"}]}}})).toContain("npm install zamiast npm ci");
  expect(auditWorkflow({...base,jobs:{a:{"timeout-minutes":5,steps:[{run:"npm run build"}]}}})).toContain("build bez sprawdzania typów");
  expect(auditWorkflow({...base,jobs:{a:{"timeout-minutes":5,steps:[{run:"npm run build"}]}}},{typescript:false})).toEqual([]);
  expect(auditWorkflow({...base,jobs:{a:{steps:[]}}})).toContain("a: brak limitu czasu");
  expect(auditWorkflow({...base,jobs:{a:{"timeout-minutes":5,steps:[{run:"npx vitest run"},{run:"npm run lint"}]}}})).toContain("a: droższy krok przed tańszym");
  expect(auditWorkflow({...base,jobs:{a:{"timeout-minutes":5,steps:[{run:"curl -H "+secret("TOKEN")}]}}})).toContain("a: sekret wstawiony do polecenia");
  expect(auditWorkflow({...base,jobs:{a:{"timeout-minutes":5,steps:[{run:"echo $CF_TOKEN"}]}}})).toContain("a: sekret lub środowisko w logach");
  expect(auditWorkflow({...base,jobs:{a:{"timeout-minutes":5,steps:[{uses:"actions/checkout"}]}}})).toContain("a: akcja bez wersji");
});

test("ochrona gałęzi i możliwość scalenia",()=>{
  const safe={requiredChecks:["CI / check","CI / e2e"],requireReview:true,allowForcePush:false,allowDirectPush:false};
  expect(auditProtection(safe,["check","e2e"])).toEqual([]);
  expect(auditProtection({...safe,requiredChecks:["CI / check"]},["check","e2e"])).toEqual(["brak wymaganego statusu CI / e2e"]);
  expect(auditProtection({...safe,allowForcePush:true,allowDirectPush:true,requireReview:false},[])).toHaveLength(3);
  expect(canMerge({"CI / check":"success","CI / e2e":"success"},safe)).toBe(true);
  expect(canMerge({"CI / check":"success","CI / e2e":"failure"},safe)).toBe(false);
  expect(canMerge({"CI / check":"success"},safe)).toBe(false);
  expect(canMerge({"CI / check":"success","CI / e2e":"pending"},safe)).toBe(false);
});

test("tag obrazu pochodzi z SHA commita",()=>{
  expect(imageTag("Firma/DevDesk","abc1234")).toBe("ghcr.io/firma/devdesk/frontend:abc1234");
  expect(()=>imageTag("a/b","latest")).toThrow("SHA");
});

test("koszt kroków rośnie z ich czasem",()=>{
  expect(stepCost("npm run lint")).toBeLessThan(stepCost("npx tsc --noEmit")!);
  expect(stepCost("npx playwright test")).toBeGreaterThan(stepCost("npm run build")!);
  expect(stepCost("echo x")).toBeNull();
});`;
const exercises=[
['Dodaj plik .nvmrc i skrypty w package.json.','Ta sama wersja Node lokalnie i w CI, a verify łączy kroki.',pkg],
['Napisz workflow CI dla pull requestów.','pull_request i push do main.',ci],
['Ułóż kroki od najtańszych.','Lint i typy przed testami i buildem.',ci],
['Przerywaj stary przebieg po nowym commicie.','concurrency z cancel-in-progress.',ci],
['Dodaj osobny krok sprawdzania typów.','Vite usuwa typy i ich nie sprawdza.',ci],
['Zachowaj dist jako artefakt.','Drugi job korzysta z tego samego wyniku.',ci],
['Uruchom E2E po job check.','needs tworzy zależność.',ci],
['Prześlij raport Playwright tylko po błędzie.','if: failure().',ci],
['Ustaw limit czasu jobów.','Zawieszony przebieg nie powinien trwać godzin.',ci],
['Ustaw minimalne uprawnienia tokenu.','contents: read na poziomie workflow.',ci],
['Napisz workflow wdrożenia po zielonym CI.','workflow_run z warunkiem conclusion.',deploy],
['Wskaż commit, który przeszedł CI.','checkout z head_sha.',deploy],
['Użyj chronionego środowiska production.','Zatwierdzenie i własne sekrety.',deploy],
['Przekaż wersję do builda i Sentry.','Ta sama wartość to SHA commita.',deploy],
['Rozróżnij vars i secrets.','Co może pojawić się w logach?','vars to wartości jawne, np. publiczny adres API i identyfikator konta. secrets to tokeny maskowane w logach. VITE_* i tak trafi do paczki, więc umieszczanie go w secrets niczego nie ukrywa.'],
['Zbuduj obraz i wyślij do rejestru.','Tag równy SHA, a nie latest.',imageJob],
['Włącz Dependabot.','npm i github-actions tygodniowo.',dependabot],
['Skonfiguruj ochronę gałęzi main.','Workflow sam niczego nie blokuje.','Reguły w Settings: wymagane statusy CI / check i CI / e2e, co najmniej jedna akceptacja, gałąź aktualna względem main lub merge queue, zakaz bezpośredniego push i force push. Dopiero to blokuje scalenie czerwonego pull requesta.'],
['Sprawdź działanie przez celowy błąd.','Czerwony przebieg to wynik pożądany.','W gałęzi zepsuj typ, a potem test. Otwórz pull request i sprawdź: status czerwony, wskazany krok, przycisk scalenia zablokowany. Cofnij zmianę i sprawdź zieleń. Tego testu nie zastąpi samo przeczytanie YAML.'],
['Napisz przegląd workflow w kodzie.','Dane z YAML i proste reguły.',audit],
['Wykryj build bez sprawdzania typów.','W projekcie JS ta reguła nie obowiązuje.',audit],
['Wykryj sekret wstawiony bezpośrednio do polecenia.','Lepsze jest env kroku.',audit],
['Sprawdź workflow przez test z parserem YAML.','Czytamy prawdziwy plik z repozytorium.',tests],
['Napisz model możliwości scalenia.','Pending to nie zgoda.',audit],
['Wyjaśnij zagrożenie pull_request_target.','Czyj kod działa i z jakimi sekretami?','Zdarzenie działa w kontekście repozytorium bazowego i ma dostęp do sekretów. Checkout kodu z pull requesta oznacza, że cudzy kod działa z tym dostępem. Do testów forka używaj pull_request, które sekretów nie dostaje.'],
['Znajdź problem z maskowaniem sekretów.','Base64 nie jest maskowane.','GitHub maskuje znane wartości, ale nie pochodne, np. zakodowane w base64 albo pocięte. Nie wypisuj env do logów. Nie dodawaj kroków debugowania z sekretami, a po wycieku unieważnij token.'],
['Skróć czas CI.','Zmierz, zanim zmienisz.','Sprawdź czas kroków w przebiegu. Włącz cache npm, instaluj tylko chromium, podziel E2E przez --shard w macierzy, a lint, typy i testy uruchom równolegle. Nie usuwaj kroków ani nie dodawaj ślepych ponowień.'],
['Zareaguj na niestabilny test.','Ślepe ponawianie to dług.','Znajdź przyczynę: brak await, zależność od czasu, kolejność testów, stałe opóźnienia. Popraw lub tymczasowo wyłącz z zadaniem i właścicielem. Inaczej zespół przestanie ufać czerwonemu CI, a prawdziwe błędy przejdą.'],
['Dodaj hook lokalny.','Szybka część verify przed push.','lefthook.yml: pre-push uruchamia lint i typecheck. Hook jest wygodą, a nie zabezpieczeniem, bo można go pominąć przez --no-verify. Właściwą bramką pozostaje CI z ochroną gałęzi.'],
['Sprawdź kryterium akceptacji lekcji.','PR, main, Sentry, Dependabot.','Pull request z błędem typów, lintera lub testu jest czerwony i nie da się go scalić. Zielony main wdraża się sam. Wdrożona wersja ma w Sentry mapy źródeł z wersją równą SHA. Dependabot otwiera pull requesty przechodzące przez to samo CI.'],
];
const concepts=[
['Co oznacza CI?','Automatyczne sprawdzanie każdej zmiany','Ręczne wdrożenie','Pisanie dokumentacji','Podział CSS','Ciągła integracja wykrywa problem przed scaleniem.'],
['Co oznacza CD?','Automatyczne wdrażanie zielonej gałęzi','Sprawdzanie typów','Kompresję','Cache','Ciągłe wdrażanie.'],
['Gdzie leży workflow?','.github/workflows/','src/ci','public/','.env','Plik YAML w repozytorium.'],
['Co uruchamia workflow?','Zdarzenie, np. pull_request','Sam commit bez konfiguracji','npm test','Sentry','Sekcja on określa zdarzenia.'],
['Czym jest job?','Zestawem kroków na jednej maszynie','Pojedynczym poleceniem','Plikiem YAML','Commitem','Joby działają równolegle.'],
['Jak uzależnić job od innego?','needs','after','depends','wait','Bez tego działają równolegle.'],
['Czym jest step?','Poleceniem lub akcją','Maszyną','Gałęzią','Tagiem','run albo uses.'],
['Czym jest artefakt?','Plik przekazany między jobami lub zachowany','Sekret','Commit','Runner','np. dist lub raport.'],
['Czy dane z job check są widoczne w e2e bez artefaktu?','Nie, to osobne maszyny','Tak','Tak, po needs','Tylko dist','needs nie kopiuje plików.'],
['Jak sprawdzić typy w CI?','tsc --noEmit','vite build','npm ci','eslint samo','Vite usuwa typy bez sprawdzania.'],
['Czemu sam build nie wykryje błędu typów?','Vite ich nie sprawdza','Bo CSS','Bo Node','Bo cache','Dlatego osobny krok.'],
['Co można uruchomić w JS z JSDoc?','tsc --noEmit z checkJs','Nic','Tylko CSS','Tylko Vite','Część korzyści także bez migracji.'],
['Która kolejność jest najlepsza?','Lint, typy, testy, build, E2E','E2E, build, testy','Build, lint, E2E','Dowolna','Szybki błąd oszczędza czas.'],
['Co robi cache: npm w setup-node?','Przyspiesza npm ci','Zapisuje node_modules do repozytorium','Pomija lockfile','Wyłącza audyt','Korzysta z cache pobranych pakietów.'],
['Czemu npm ci w CI?','Instaluje dokładnie wersje z lockfile','Bo szybsze od wszystkiego','Bo tak działa Vite','Bo pomija testy','Powtarzalność.'],
['Co zawiera .nvmrc?','Wersję Node','Sekrety','Listę testów','Port','Wspólna wersja lokalnie i w CI.'],
['Co robi concurrency z cancel-in-progress?','Przerywa poprzedni przebieg tej gałęzi','Równoległe joby','Limit runnerów','Cache','Nowy commit unieważnia stary przebieg.'],
['Po co timeout-minutes?','Zawieszony job nie trwa godzin','Dla SEO','Dla cache','Dla typów','Domyślny limit jest długi.'],
['Co oznacza if: failure()?','Krok działa, gdy poprzednie zawiodły','Krok zawsze zawodzi','Blokuje job','Ponawia','Dobre dla raportów.'],
['Co oznacza if: always()?','Krok działa niezależnie od wyniku','Tylko po sukcesie','Nigdy','Tylko w main','Np. sprzątanie.'],
['Jak uruchomić wdrożenie po CI?','workflow_run z types: completed','push bez warunku','schedule','Ręcznie zawsze','Sprawdź też conclusion.'],
['Po co warunek conclusion == success?','workflow_run startuje także po porażce','Dla składni','Bez znaczenia','Dla cache','Bez warunku wdrożysz czerwony kod.'],
['Czemu checkout z head_sha?','Wdrażasz commit, który przeszedł CI','Dla szybkości','Dla CSS','Bez powodu','Domyślny ref mógłby być inny.'],
['Co daje environment production?','Zatwierdzenie, reguły i własne sekrety','Szybszy runner','Nową domenę','Pełny dostęp','Sekrety są dostępne tylko temu jobowi.'],
['Skąd sekrety?','Ustawień repozytorium lub środowiska','Z pliku .env w repo','Z kodu','Z logów','Dostępne jako secrets.NAZWA.'],
['Czy sekrety pojawiają się w logach?','Znane wartości są maskowane, pochodne nie','Nigdy, w żadnej formie','Zawsze','Tylko w main','Base64 omija maskowanie.'],
['Czym vars różni się od secrets?','vars są jawne','Niczym','vars są szyfrowane mocniej','secrets są publiczne','Publiczne adresy mogą być w vars.'],
['Czy VITE_API_URL musi być sekretem?','Nie, trafia do publicznej paczki','Tak','Tak, w produkcji','Tak, w forkach','Traktowanie jako sekret myli.'],
['Czemu tag obrazu to SHA?','Jednoznacznie wskazuje kod','Dla krótszej nazwy','Bo latest nie działa','Dla CSS','latest nie mówi, co działa.'],
['Co robi cache-from: type=gha?','Używa cache warstw GitHub Actions','Pobiera z Docker Hub','Wyłącza warstwy','Czyści rejestr','Przyspiesza budowanie obrazu.'],
['Czym jest GITHUB_TOKEN?','Tymczasowym tokenem przebiegu','Hasłem użytkownika','Kluczem SSH','Plikiem','Uprawnienia ustawiasz przez permissions.'],
['Jakie uprawnienia nadać domyślnie?','contents: read','write-all','admin','Brak ustawienia','Zasada najmniejszych uprawnień.'],
['Kiedy packages: write?','Gdy job wysyła obraz','Zawsze','Przy lint','Przy testach','Tylko temu jobowi.'],
['Czy workflow sam blokuje scalenie?','Nie, robią to reguły ochrony gałęzi','Tak','Tak, gdy jest czerwony','Tak, w main','Wymagane statusy trzeba ustawić.'],
['Co oznacza wymagany status?','Musi być zielony przed scaleniem','Jest opcjonalny','Działa po scaleniu','Dotyczy tylko forków','Status pending też blokuje.'],
['Co robi merge queue?','Testuje zmiany w kolejce względem najnowszego main','Scala bez testów','Usuwa gałęzie','Tworzy tagi','Rozwiązuje wyścig zielonych PR.'],
['Po co zakaz force push na main?','Historia nie może zniknąć','Dla szybkości','Dla CSS','Dla cache','Chroni przed utratą commitów.'],
['Co robi Dependabot?','Otwiera PR z aktualizacjami','Scala bez testów','Skanuje CSS','Wdraża','Przechodzą przez to samo CI.'],
['Jakie ekosystemy włączyć?','npm i github-actions','Tylko npm zawsze','Tylko Docker','Żadnych','Akcje też mają aktualizacje.'],
['Czemu forki nie dostają sekretów?','Obcy kod mógłby je wykraść','Bo brak runnera','Bo YAML','Dla szybkości','To właściwe zachowanie.'],
['Co jest groźne w pull_request_target?','Cudzy kod z dostępem do sekretów','Wolniejszy przebieg','Brak logów','Brak cache','Szczególnie z checkoutem kodu z PR.'],
['Jak wstawiać sekret do polecenia?','Przez env kroku, nie w tekście run','W run bezpośrednio','Przez echo','Przez plik w repo','Zmniejsza ryzyko wstrzyknięcia.'],
['Skąd ryzyko wstrzyknięcia w run?','Wyrażenia są podstawiane w tekście skryptu','Z YAML','Z Node','Z cache','Np. tytuł PR nie może trafić tam bez env.'],
['Czym grozi env w logach?','Ujawnieniem wartości pochodnych','Niczym','Wolniejszym buildem','Błędem YAML','Nie używaj do debugowania.'],
['Jak traktować niestabilny test?','Znaleźć przyczynę','Dodać ponowienia bez końca','Zignorować','Usunąć CI','Inaczej czerwony status traci znaczenie.'],
['Jak przyspieszyć E2E?','Shardy i tylko chromium','Usunąć asercje','Dodać sleep','Wyłączyć build','Równoległość przez macierz.'],
['Co robi strategy.matrix?','Uruchamia job dla wielu wartości','Tworzy tabelę','Scala gałęzie','Blokuje joby','np. wiele wersji Node lub shardy.'],
['Po co skrypt verify?','Ten sam zestaw lokalnie i w CI','Dla Sentry','Dla SEO','Tylko dla Dockera','Zmniejsza „u mnie działa”.'],
['Czy hook pre-push zastępuje CI?','Nie, można go pominąć','Tak','Tak, z husky','Tak, w zespole','Prawdziwą bramką jest CI.'],
['Co oznacza coverage w testach?','Które linie kod wykonały','Jakość testów wprost','Liczbę błędów','Rozmiar paczki','Wysokie pokrycie nie dowodzi poprawności.'],
['Jaką wartość ma artefakt z raportem?','Można obejrzeć błąd E2E po przebiegu','Zwiększa paczkę','Zastępuje log','Wdraża aplikację','Zrzuty i ślady przyspieszają diagnozę.'],
['Na jak długo trzymać artefakt?','Krótko, np. 7 dni','Na zawsze','1 sekundę','Bez limitu zawsze','Pamięć ma koszt.'],
['Co testuje podgląd PR?','Zmianę w działaniu bez uruchamiania lokalnie','Tylko typy','Tylko CSS','Bazę produkcyjną','Pomaga w przeglądzie.'],
['Czemu sprawdzać E2E po buildzie?','Testujesz to, co trafi na produkcję','Bo dev nie działa','Dla SEO','Bez powodu','Tryb dev zachowuje się inaczej.'],
['Co oznacza rollback w CD?','Wdrożenie poprzedniej dobrej wersji','Usunięcie repozytorium','Reset bazy','Force push','Warto mieć procedurę.'],
['Kiedy wymagać ręcznego zatwierdzenia?','Przy ryzykownych wdrożeniach lub regułach firmy','Zawsze i wszędzie','Nigdy','Tylko w forkach','Środowisko pozwala to ustawić.'],
['Jak nazwać wymagany status?','Według workflow i joba, np. CI / check','Dowolnie w README','Nazwą pliku CSS','Numerem PR','Zmiana nazwy joba wymaga zmiany reguły.'],
['Co się stanie po zmianie nazwy joba wymaganego?','PR może utknąć w oczekiwaniu na status','Nic','Scali się samo','Usunie regułę','Trzeba zaktualizować ochronę.'],
['Jak sprawdzić czas kroków?','W widoku przebiegu','npm audit','tsc','Lighthouse','Mierz przed optymalizacją.'],
['Czym jest fail-fast w macierzy?','Przerwanie pozostałych po pierwszej porażce','Szybszy Node','Brak testów','Pomijanie lint','Domyślnie włączone w macierzy.'],
];
const header=String.raw`import {auditProtection,auditWorkflow,canMerge,imageTag,stepCost,triggers,type Workflow} from "./ciAudit";
const wf=(steps:object[],over:object={}):Workflow=>({on:"pull_request",permissions:{contents:"read"},jobs:{a:{"timeout-minutes":5,steps}},...over});
const safe={requiredChecks:["CI / check","CI / e2e"],requireReview:true,allowForcePush:false,allowDirectPush:false};`;
const probeRows=[
['Zdarzenia z tekstu','triggers({on:"push"}).join()','push','Zapis skrócony.',['pull_request','pusto','on']],
['Zdarzenia z listy','triggers({on:["push","pull_request"]}).length',2,'Zapis listowy.'],
['Zdarzenia z obiektu','triggers({on:{push:{},schedule:[]}}).join()','push,schedule','Klucze obiektu.',['push','schedule','pusto']],
['Zdarzenia bez sekcji on','triggers({}).length',0,'Brak zdarzeń.'],
['Koszt npm ci','stepCost("npm ci")',0,'Przygotowanie.'],
['Koszt lint','stepCost("npm run lint")',1,'Najtańsze sprawdzenie.'],
['Koszt tsc','stepCost("npx tsc --noEmit")',2,'Sekundy.'],
['Koszt vitest','stepCost("npx vitest run")',3,'Droższy.'],
['Koszt build','stepCost("npm run build")',4,'Jeszcze droższy.'],
['Koszt playwright','stepCost("npx playwright test")',5,'Najdroższy.'],
['Koszt nieznanego','String(stepCost("echo x"))','null','Reguła nie ocenia innych poleceń.',['0','1','undefined']],
['Czysty workflow','auditWorkflow(wf([{run:"npm ci"},{run:"npm run lint"}])).length',0,'Brak problemów.'],
['npm install','auditWorkflow(wf([{run:"npm install"}]))[0]','npm install zamiast npm ci','Powtarzalność.',['build bez sprawdzania typów','a: akcja bez wersji','brak']],
['Build bez tsc','auditWorkflow(wf([{run:"npm run build"}]))[0]','build bez sprawdzania typów','Vite ich nie sprawdza.',['npm install zamiast npm ci','a: akcja bez wersji','brak']],
['Build z tsc','auditWorkflow(wf([{run:"npx tsc --noEmit"},{run:"npm run build"}])).length',0,'Krok istnieje.'],
['Build z typecheck','auditWorkflow(wf([{run:"npm run typecheck"},{run:"npm run build"}])).filter(p=>p.includes("typów")).length',0,'Skrypt też się liczy.'],
['Build bez typów w JS','auditWorkflow(wf([{run:"npm run build"}]),{typescript:false}).length',0,'Reguła dotyczy TS.'],
['Złe uprawnienia','auditWorkflow({on:"push",jobs:{a:{"timeout-minutes":1,steps:[]}}})[0]','brak minimalnych uprawnień tokenu','Domyślne bywają szerokie.',['a: brak limitu czasu','brak','npm install zamiast npm ci']],
['Uprawnienia na jobie','auditWorkflow({on:"push",jobs:{a:{"timeout-minutes":1,permissions:{contents:"read"},steps:[]}}}).length',0,'Ustawione na poziomie joba.'],
['Brak limitu','auditWorkflow(wf([],{jobs:{a:{steps:[]}}}))[0]','a: brak limitu czasu','Zawieszony przebieg.',['brak','a: akcja bez wersji','brak minimalnych uprawnień tokenu']],
['Droższy przed tańszym','auditWorkflow(wf([{run:"npx vitest run"},{run:"npm run lint"}]))[0]','a: droższy krok przed tańszym','Wolniejsza informacja zwrotna.',['brak','build bez sprawdzania typów','a: brak limitu czasu']],
['Sekret w poleceniu','auditWorkflow(wf([{run:"curl "+"$"+"{{ secrets.T }}"}]))[0]','a: sekret wstawiony do polecenia','Użyj env.',['brak','a: akcja bez wersji','a: sekret lub środowisko w logach']],
['Sekret w env','auditWorkflow(wf([{run:"curl x",env:{T:"$"+"{{ secrets.T }}"}}])).length',0,'Tak jest bezpieczniej.'],
['Polecenie env','auditWorkflow(wf([{run:"env"}]))[0]','a: sekret lub środowisko w logach','Wypisuje wszystko.',['brak','a: brak limitu czasu','npm install zamiast npm ci']],
['Echo tokenu','auditWorkflow(wf([{run:"echo $MY_TOKEN"}])).length',1,'Wartość mogłaby wyjść.'],
['Akcja bez wersji','auditWorkflow(wf([{uses:"actions/checkout"}]))[0]','a: akcja bez wersji','Zmiany przyjdą bez kontroli.',['brak','a: brak limitu czasu','npm install zamiast npm ci']],
['Akcja z wersją','auditWorkflow(wf([{uses:"actions/checkout@v4"}])).length',0,'Wersja przypięta.'],
['pull_request_target bez PR','auditWorkflow(wf([{uses:"actions/checkout@v4"}],{on:{pull_request_target:{}}})).length',0,'Kod z bazy nie jest cudzy.'],
['Ochrona pełna','auditProtection(safe,["check","e2e"]).length',0,'Wszystko ustawione.'],
['Ochrona bez e2e','auditProtection({...safe,requiredChecks:["CI / check"]},["check","e2e"])[0]','brak wymaganego statusu CI / e2e','E2E mogłoby być czerwone.',['brak wymaganego przeglądu','dozwolony force push','brak']],
['Ochrona bez przeglądu','auditProtection({...safe,requireReview:false},[])[0]','brak wymaganego przeglądu','Reguła zespołu.',['dozwolony force push','brak','dozwolony bezpośredni push']],
['Ochrona z force push','auditProtection({...safe,allowForcePush:true},[])[0]','dozwolony force push','Historia mogłaby zniknąć.',['brak wymaganego przeglądu','brak','dozwolony bezpośredni push']],
['Scalenie: wszystko zielone','canMerge({"CI / check":"success","CI / e2e":"success"},safe)',true,'Komplet.'],
['Scalenie: pending','canMerge({"CI / check":"success","CI / e2e":"pending"},safe)',false,'Oczekiwanie to nie zgoda.'],
['Scalenie: failure','canMerge({"CI / check":"failure","CI / e2e":"success"},safe)',false,'Jedna porażka blokuje.'],
['Scalenie: brak statusu','canMerge({"CI / check":"success"},safe)',false,'Brak to nie sukces.'],
['Scalenie bez wymagań','canMerge({},{requiredChecks:[]})',true,'Bez reguł nic nie blokuje: stąd ochrona gałęzi.'],
['Tag obrazu','imageTag("a/b","abc1234")','ghcr.io/a/b/frontend:abc1234','Jednoznaczny commit.',['ghcr.io/a/b/frontend:latest','a/b:abc1234','abc1234']],
['Wielkość liter w tagu','imageTag("Firma/App","abc1234")','ghcr.io/firma/app/frontend:abc1234','Rejestry wymagają małych liter.',['ghcr.io/Firma/App/frontend:abc1234','Firma/App','abc1234']],
['Tag latest','(()=>{try{imageTag("a/b","latest");return "przyjęty";}catch{return "odrzucony";}})()','odrzucony','latest nie wskazuje commita.',['przyjęty','latest','pusto']],
];
const project={
'src/setup.ts':nodeSafeSetup,'src/ciAudit.ts':audit,'src/ci.test.ts':tests,
'.github/workflows/ci.yml':ci,'.github/workflows/deploy.yml':deploy,'.github/workflows/image.fragment.txt':imageJob,'.github/dependabot.yml':dependabot,'.nvmrc':'22\n',
'README.md':'DevDesk: CI i CD. npm install, npm test. Test parsuje prawdziwe pliki YAML i sprawdza reguły przeglądu. Workflow nie były uruchamiane na GitHub Actions w tej lekcji, a ochrona gałęzi to ustawienie repozytorium: po wypchnięciu sprawdź je celowym błędem.\n',
};
const mutants=[
{file:'.github/workflows/ci.yml',before:'      - run: npx tsc --noEmit -p tsconfig.app.json\n',after:''},
{file:'.github/workflows/ci.yml',before:'    needs: check\n',after:''},
{file:'.github/workflows/ci.yml',before:'permissions:\n  contents: read\n\n',after:''},
{file:'.github/workflows/deploy.yml',before:"    if: ${{ github.event.workflow_run.conclusion == 'success' }}\n",after:''},
{file:'.github/dependabot.yml',before:'  - package-ecosystem: github-actions\n    directory: /\n    schedule:\n      interval: weekly\n',after:''},
{file:'src/ciAudit.ts',before:'if(step.run&&/\\$\\{\\{\\s*secrets\\./.test(step.run))problems.push(name+": sekret wstawiony do polecenia");',after:''},
{file:'src/ciAudit.ts',before:'statuses[check]==="success"',after:'statuses[check]!=="failure"'},
{file:'src/ciAudit.ts',before:'if(!/^[0-9a-f]{7,40}$/.test(sha))throw new Error("Tag obrazu wymaga SHA commita");',after:''},
{file:'src/ciAudit.ts',before:'if(costs.some((cost,index)=>index>0&&cost<costs[index-1]))',after:'if(false)'},
];
module.exports=lesson({exercises,concepts,probeRows,project,mutants,header});
