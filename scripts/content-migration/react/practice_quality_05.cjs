const {lesson}=require('./practice_kit.cjs');
const safeHtml=String.raw`import DOMPurify from "dompurify";

export type SanitizedHtml=string&{readonly __brand:"SanitizedHtml"};

const POLICY={
  ALLOWED_TAGS:["b","strong","i","em","a","p","ul","ol","li","code","pre","br"],
  ALLOWED_ATTR:["href"],
};

export function sanitize(html:string):SanitizedHtml{
  return DOMPurify.sanitize(html,POLICY) as SanitizedHtml;
}

export function SafeHtml({html}:{html:SanitizedHtml}){
  return <div className="rich-text" dangerouslySetInnerHTML={{__html:html}}/>;
}`;
const safeLink=String.raw`import type {ReactNode} from "react";

const ALLOWED=["http:","https:","mailto:"];

export function safeUrl(value:string):string|null{
  try{
    const url=new URL(value,window.location.origin);
    return ALLOWED.includes(url.protocol)?url.href:null;
  }catch{
    return null;
  }
}

export function SafeLink({href,children}:{href:string;children:ReactNode}){
  const safe=safeUrl(href);
  if(!safe)return <span>{children}</span>;
  return <a href={safe} target="_blank" rel="noopener noreferrer">{children}</a>;
}`;
const props=String.raw`const ALLOWED=["title","className","lang"] as const;
type Allowed=typeof ALLOWED[number];

// Lista dozwolonych, a nie zakazanych: nowy niebezpieczny prop nie przejdzie sam.
export function pickSafeProps(untrusted:Record<string,unknown>){
  const safe:Partial<Record<Allowed,string>>={};
  for(const key of ALLOWED){
    const value=untrusted[key];
    if(typeof value==="string")safe[key]=value;
  }
  return safe;
}`;
const naive=String.raw`// Celowo zły przykład: tak NIE sanityzuje się HTML.
export function naiveSanitize(html:string){
  return html.replace(/<script.*?>.*?<\/script>/g,"");
}`;
const csp=String.raw`export type Directives=Record<string,readonly string[]>;

export const devDeskPolicy:Directives={
  "default-src":["'self'"],
  "script-src":["'self'"],
  "style-src":["'self'","'unsafe-inline'"],
  "img-src":["'self'","data:","https://files.devdesk.example"],
  "connect-src":["'self'","https://api.devdesk.example"],
  "frame-ancestors":["'none'"],
  "base-uri":["'self'"],
  "form-action":["'self'"],
};

export function buildCsp(directives:Directives){
  return Object.entries(directives).map(([name,values])=>name+" "+values.join(" ")).join("; ");
}

export function parseCsp(header:string):Directives{
  return Object.fromEntries(header.split(";").map(part=>part.trim()).filter(Boolean).map(part=>{
    const [name,...values]=part.split(/\s+/);
    return [name.toLowerCase(),values];
  }));
}

export function auditCsp(directives:Directives){
  const warnings:string[]=[];
  const scripts=directives["script-src"]??directives["default-src"];
  if(!scripts)warnings.push("brak script-src i default-src");
  if(scripts?.includes("'unsafe-inline'"))warnings.push("script-src dopuszcza skrypty inline");
  if(scripts?.includes("'unsafe-eval'"))warnings.push("script-src dopuszcza eval");
  if(scripts?.some(value=>value==="*"||value==="https:"))warnings.push("script-src dopuszcza zbyt szerokie źródła");
  if(!directives["frame-ancestors"])warnings.push("brak frame-ancestors");
  if(!directives["base-uri"])warnings.push("brak base-uri");
  return warnings;
}`;
const secrets=String.raw`const RULES:readonly {name:string;pattern:RegExp}[]=[
  {name:"Klucz AWS",pattern:/AKIA[0-9A-Z]{16}/},
  {name:"Klucz Stripe live",pattern:/sk_live_[0-9a-zA-Z]{10,}/},
  {name:"Klucz prywatny",pattern:/-----BEGIN [A-Z ]*PRIVATE KEY-----/},
  {name:"Adres wewnętrzny",pattern:/https?:\/\/[a-z0-9.-]+\.internal\b/i},
];

export function scanBundle(files:Record<string,string>){
  return Object.entries(files).flatMap(([file,text])=>
    RULES.filter(rule=>rule.pattern.test(text)).map(rule=>file+": "+rule.name));
}`;
const lab=String.raw`import {render} from "@testing-library/react";
import type {ReactElement} from "react";

export function html(element:ReactElement){
  const view=render(element);
  const out=view.container.innerHTML;
  view.unmount();
  return out;
}`;
const tests=String.raw`import {expect,test} from "vitest";
import {render,screen} from "@testing-library/react";
import {SafeHtml,sanitize} from "./SafeHtml";
import {SafeLink,safeUrl} from "./SafeLink";
import {pickSafeProps} from "./safeProps";
import {naiveSanitize} from "./naive";
import {auditCsp,buildCsp,devDeskPolicy,parseCsp} from "./csp";
import {scanBundle} from "./secrets";

test("React escapuje tekst wstawiony w JSX",()=>{
  const view=render(<p>{"<img src=x onerror=alert(1)>"}</p>);
  expect(view.container.querySelector("img")).toBeNull();
  expect(screen.getByText("<img src=x onerror=alert(1)>")).toBeVisible();
});

test("sanityzacja usuwa kod, a zostawia dozwolone znaczniki",()=>{
  const clean=sanitize('<b>Ważne</b><img src=x onerror="alert(1)"><script>alert(1)</script><a href="https://a.example" onclick="x()">link</a>');
  const view=render(<SafeHtml html={clean}/>);
  expect(view.container.querySelector("b")).toHaveTextContent("Ważne");
  expect(view.container.querySelector("img")).toBeNull();
  expect(view.container.querySelector("script")).toBeNull();
  const link=view.container.querySelector("a")!;
  expect(link).toHaveAttribute("href","https://a.example");
  expect(link).not.toHaveAttribute("onclick");
});

test("sanityzacja usuwa adres javascript z linku i style",()=>{
  const clean=sanitize('<a href="javascript:alert(1)">x</a><p style="position:fixed">y</p>');
  const view=render(<SafeHtml html={clean}/>);
  expect(view.container.querySelector("a")).not.toHaveAttribute("href");
  expect(view.container.querySelector("p")).not.toHaveAttribute("style");
});

test("safeUrl dopuszcza tylko protokoły z listy",()=>{
  expect(safeUrl("https://a.example/x")).toBe("https://a.example/x");
  expect(safeUrl("mailto:a@b.example")).toBe("mailto:a@b.example");
  expect(safeUrl("/tickets/1")).toBe(window.location.origin+"/tickets/1");
  for(const bad of ["javascript:alert(1)","JaVaScRiPt:alert(1)"," javascript:alert(1)","java\nscript:alert(1)","data:text/html,<script>1</script>","vbscript:x","http://"]){
    expect(safeUrl(bad)).toBeNull();
  }
});

test("SafeLink renderuje tekst zamiast złego linku i chroni okno",()=>{
  const view=render(<><SafeLink href="javascript:alert(1)">Zły</SafeLink><SafeLink href="https://a.example">Dobry</SafeLink></>);
  expect(screen.queryByRole("link",{name:"Zły"})).toBeNull();
  expect(screen.getByText("Zły").tagName).toBe("SPAN");
  const good=screen.getByRole("link",{name:"Dobry"});
  expect(good).toHaveAttribute("rel","noopener noreferrer");
  expect(good).toHaveAttribute("target","_blank");
  view.unmount();
});

test("lista dozwolonych props odcina niebezpieczne wartości",()=>{
  const untrusted={title:"Tytuł",dangerouslySetInnerHTML:{__html:"<img onerror=x>"},onClick:"x",className:5,style:{position:"fixed"}};
  expect(pickSafeProps(untrusted)).toEqual({title:"Tytuł"});
  const view=render(<div {...pickSafeProps(untrusted)}/>);
  expect(view.container.querySelector("img")).toBeNull();
});

test("wyrażenie regularne nie jest sanityzacją",()=>{
  expect(naiveSanitize("<script>x</script>")).toBe("");
  expect(naiveSanitize("<img onerror=alert(1)>")).toContain("onerror");
  expect(naiveSanitize('<svg onload="x()">')).toContain("onload");
});

test("CSP: budowa, odczyt i audyt",()=>{
  const header=buildCsp(devDeskPolicy);
  expect(header).toContain("script-src 'self'");
  expect(parseCsp(header)["img-src"]).toContain("data:");
  expect(parseCsp("Script-Src 'self' ; ")).toEqual({"script-src":["'self'"]});
  expect(auditCsp(devDeskPolicy)).toEqual([]);
  expect(auditCsp({"default-src":["'self'"],"frame-ancestors":["'none'"],"base-uri":["'self'"]})).toEqual([]);
  expect(auditCsp({})).toEqual(["brak script-src i default-src","brak frame-ancestors","brak base-uri"]);
  expect(auditCsp({...devDeskPolicy,"script-src":["'self'","'unsafe-inline'"]})).toEqual(["script-src dopuszcza skrypty inline"]);
  expect(auditCsp({...devDeskPolicy,"script-src":["'self'","'unsafe-eval'"]})).toEqual(["script-src dopuszcza eval"]);
  expect(auditCsp({...devDeskPolicy,"script-src":["https:"]})).toEqual(["script-src dopuszcza zbyt szerokie źródła"]);
});

test("skaner wykrywa sekrety w paczce",()=>{
  const key="sk_live_"+"abcdefghij12";
  expect(scanBundle({"index.js":"const dsn='https://public@sentry.example/1'"})).toEqual([]);
  expect(scanBundle({"a.js":"k='"+key+"'"})).toEqual(["a.js: Klucz Stripe live"]);
  expect(scanBundle({"b.js":"fetch('http://billing.internal/x')"})).toEqual(["b.js: Adres wewnętrzny"]);
});`;
const headersJava=String.raw`// SecurityConfig.java (fragment)
http.headers(headers -> headers
    .contentSecurityPolicy(csp -> csp.policyDirectives(
        "default-src 'self'; script-src 'self'; style-src 'self' 'unsafe-inline'; " +
        "img-src 'self' data: https://files.devdesk.example; connect-src 'self' https://api.devdesk.example; " +
        "frame-ancestors 'none'; base-uri 'self'; form-action 'self'"))
    .referrerPolicy(r -> r.policy(ReferrerPolicyHeaderWriter.ReferrerPolicy.STRICT_ORIGIN_WHEN_CROSS_ORIGIN))
    .frameOptions(f -> f.deny()));
// server.servlet.session.cookie.http-only=true, secure=true, same-site=lax`;
const exercises=[
['Pokaż, że React sam escapuje komentarz wstawiony jako tekst.','Tekst z tagami ma być widoczny dosłownie.',tests],
['Wskaż miejsca, gdzie ochrona Reacta przestaje działać.','Szukaj HTML, adresów, DOM i spreadu.','dangerouslySetInnerHTML; href lub src z adresem javascript: od użytkownika; ref.current.innerHTML i biblioteki spoza Reacta; eval, new Function, setTimeout z tekstem; spread niezaufanego obiektu na element.'],
['Zabezpiecz HTML komentarza przez DOMPurify.','Ustaw wąską listę znaczników i atrybutów.',safeHtml],
['Wprowadź typ markowany SanitizedHtml.','Jedyną drogą do typu ma być funkcja sanitize.',safeHtml],
['Sprawdź, że SafeHtml nie przyjmie surowego tekstu.','To jest błąd kompilacji, nie test czasu wykonania.','// @ts-expect-error tekst nie jest SanitizedHtml\n<SafeHtml html={comment.html}/>;\n<SafeHtml html={sanitize(comment.html)}/>;\n// Przegląd kodu szuka już tylko rzutowań as SanitizedHtml.'],
['Przetestuj usunięcie onerror i zachowanie pogrubienia.','Sprawdź DOM, a nie fragment tekstu.',tests],
['Przetestuj usunięcie adresu javascript z linku w HTML.','Atrybut href ma zniknąć.',tests],
['Napisz safeUrl oparty na URL i liście protokołów.','Parser radzi sobie ze spacjami, wielkością liter i znakami nowej linii.',safeLink],
['Dodaj SafeLink z bezpiecznym zachowaniem nowej karty.','Zły adres renderuj jako tekst.',safeLink],
['Przetestuj odmiany zapisu javascript.','Obejmij wielkie litery, spację, nową linię i data.',tests],
['Odetnij niezaufane props listą dozwolonych.','Lista zakazanych nie obroni przed nowym atrybutem.',props],
['Wykaż, że wyrażenie regularne nie sanityzuje HTML.','Sprawdź img z onerror i svg z onload.',naive],
['Wyjaśnij, dlaczego wyrażenie regularne przegrywa z parserem HTML.','Przeglądarka czyta HTML inaczej niż wzorzec.','HTML ma tolerancyjny parser: zagnieżdżone i niedomknięte znaczniki, kodowanie znaków, wiele atrybutów zdarzeń oraz adresy javascript:. Usunięcie ciągu tekstu może nawet złożyć nowy znacznik, np. <scr<script></script>ipt>. Dlatego używaj aktualizowanej biblioteki albo formatu bez HTML.'],
['Zaproponuj format komentarzy bez HTML.','Które komponenty renderują treść?','Przechowuj Markdown lub strukturę JSON, a renderuj ją do komponentów React. Tekst i adresy przechodzą wtedy przez zwykłe escapowanie i safeUrl. Nie ma potrzeby dangerouslySetInnerHTML, o ile parser Markdown nie przepuszcza surowego HTML.'],
['Wypisz politykę CSP DevDesk jako dane.','Dyrektywy jako obiekt da się przetestować.',csp],
['Zbuduj nagłówek CSP z obiektu.','Dyrektywy rozdziela średnik.',csp],
['Odczytaj nagłówek CSP do obiektu.','Nazwy dyrektyw nie rozróżniają wielkości liter.',csp],
['Napisz audyt typowych słabości CSP.','unsafe-inline, unsafe-eval i zbyt szerokie źródła.',csp],
['Wyjaśnij zapasowe użycie default-src.','Dotyczy dyrektyw bez własnej wartości.','default-src jest wartością zapasową m.in. dla script-src, style-src i img-src. Nie dotyczy jednak frame-ancestors, base-uri ani form-action, dlatego te dyrektywy trzeba podać osobno.'],
['Wdróż CSP bez psucia produkcji.','Najpierw obserwuj naruszenia.','Ustaw Content-Security-Policy-Report-Only z adresem raportowania i przejdź główne ścieżki. Dopasuj politykę do rzeczywistych źródeł, usuń zbędne skrypty inline i dopiero wtedy przełącz na Content-Security-Policy. Monitoruj zgłoszenia po wdrożeniu.'],
['Skonfiguruj nagłówki w Spring Security.','Ta sama polityka w konfiguracji serwera.',headersJava],
['Napisz skaner paczki pod kątem sekretów.','Sprawdzaj zbudowane pliki, nie tylko kod źródłowy.',secrets],
['Uzasadnij, czego skaner nie gwarantuje.','Reguły znajdują znane wzorce.','Skaner znajdzie typowe wzorce, ale nie sekret o nietypowym kształcie ani wartość złożoną w czasie wykonania. Jest siatką bezpieczeństwa. Właściwa zasada to brak sekretów w kliencie, a klucze z uprawnieniami trzyma backend.'],
['Rozstrzygnij, gdzie trzymać token dostępu.','Porównaj localStorage, pamięć i ciasteczko.','localStorage odczyta każdy wstrzyknięty skrypt. Ciasteczko HttpOnly; Secure; SameSite=Lax jest niedostępne dla skryptu, ale wymaga ochrony przed CSRF. Token w pamięci z odświeżaniem przez ciasteczko to kompromis dla nagłówka Authorization, bo znika po odświeżeniu.'],
['Przenieś DevDesk na ciasteczko HttpOnly.','Kto ustawia ciasteczko i co usuwasz z klienta?','Backend ustawia ciasteczko sesji z flagami HttpOnly, Secure i SameSite. Klient usuwa odczyt tokenu z localStorage i wysyła żądania z credentials:"include". Spring Security pilnuje CSRF. Stan zalogowania klient pobiera np. z /api/me.'],
['Wyjaśnij, dlaczego zmienna VITE_* nie jest sekretem.','Jak trafia do paczki?','Vite wstawia zmienne z prefiksem VITE_ do zbudowanego JS. Każdy użytkownik może odczytać je w plikach strony. Klucze z uprawnieniami muszą zostać na backendzie, a klient wywołuje backend. Publiczny DSN Sentry jest identyfikatorem, a nie sekretem.'],
['Dodaj regułę lintera react/no-danger.','Wyjątek tylko dla zatwierdzonego komponentu.','// eslint.config.js\n{rules:{"react/no-danger":"error"}},\n{files:["src/SafeHtml.tsx"],rules:{"react/no-danger":"off"}},\n// Każde nowe użycie poza SafeHtml zostanie odrzucone w CI.'],
['Zaplanuj kontrolę zależności.','Każda biblioteka ma dostęp do całej strony.','Uruchamiaj npm audit w CI, włącz Dependabot lub Renovate, przeglądaj nowe pakiety pod kątem utrzymania i uprawnień, trzymaj package-lock w repozytorium i korzystaj z npm ci. Aktualizuj szczególnie DOMPurify, bo sanityzator musi nadążać za nowymi obejściami.'],
['Odtwórz atak lokalnie i sprawdź naprawę.','Tylko na środowisku deweloperskim.','Dodaj w MSW komentarz z img i onerror oraz wyświetl go przez dangerouslySetInnerHTML: alert potwierdza lukę. Podmień na SafeHtml: pogrubienie zostaje, a skrypt się nie wykona. Dodaj link javascript: i sprawdź, że renderuje się jako tekst. Zapisz oba przypadki jako testy.'],
['Sprawdź kryterium akceptacji lekcji.','Zachowania, które da się zademonstrować.','Komentarz z onerror i link javascript: nie wykonują kodu. Token nie jest dostępny dla skryptów. Nagłówek CSP działa bez naruszeń w normalnym użyciu. Skan paczki nie znajduje sekretów. Testy sanityzacji i safeUrl przechodzą w CI.'],
];
const concepts=[
['Co oznacza XSS?','Wstrzyknięcie obcego kodu wykonywanego na stronie','Błąd stylów','Atak na bazę SQL','Przeciążenie serwera','Cross-Site Scripting wykonuje skrypt w kontekście ofiary.'],
['Przed czym React chroni domyślnie?','Przed wykonaniem tekstu wstawionego w JSX jako kod','Przed CSRF','Przed wszystkimi rodzajami XSS','Przed złym API','Tekst w JSX jest escapowany.'],
['Co robi dangerouslySetInnerHTML?','Wstawia HTML bez escapowania','Sanityzuje HTML','Blokuje skrypty','Zamienia HTML na tekst','Nazwa ostrzega, że odpowiedzialność jest po Twojej stronie.'],
['Jaki adres w href jest niebezpieczny?','javascript:alert(1)','https://a.example','/tickets/1','mailto:a@b.example','Kliknięcie wykonałoby kod.'],
['Czym jest DOMPurify?','Biblioteką usuwającą niebezpieczne elementy z HTML','Linterem CSS','Pakietem do CSP','Parserem JSON','Działa na prawdziwym parserze HTML.'],
['Po co ALLOWED_TAGS w DOMPurify?','Zostawia tylko potrzebne znaczniki','Dodaje brakujące znaczniki','Wyłącza sanityzację','Wymusza Markdown','Wąska lista zmniejsza powierzchnię ataku.'],
['Czy sanityzacja na serwerze zwalnia z ostrożności w kliencie?','Nie, warto traktować to jako wiele warstw','Tak, zawsze','Tak, gdy jest Spring','Tak, gdy jest CSP','Kontekst użycia i nowe obejścia uzasadniają obronę w głąb.'],
['Czym jest typ markowany?','Typem tekstu, który powstaje tylko przez konkretną funkcję','Typem dla Markdown','Typem z JSX','Nazwą klasy CSS','Kompilator blokuje surowy tekst.'],
['Czy typ markowany działa w czasie wykonania?','Nie, to kontrakt kompilacji','Tak, sprawdza HTML','Tak, blokuje skrypty','Tak, tworzy obiekt','Sama sanityzacja jest w funkcji.'],
['Co oznacza rzutowanie as SanitizedHtml w przeglądzie kodu?','Miejsce wymagające szczególnej uwagi','Poprawną sanityzację','Brak problemu z definicji','Błąd składni','Rzutowanie omija barierę typu.'],
['Dlaczego parser URL jest lepszy od startsWith("javascript:")?','Obsługuje wielkie litery, spacje i znaki sterujące','Jest krótszy','Działa bez przeglądarki zawsze','Nie wymaga try/catch','Sprawdzasz protokół po normalizacji.'],
['Lista dozwolonych czy zakazanych protokołów?','Dozwolonych','Zakazanych','Obie są równoważne','Żadnych','Nowy protokół nie przejdzie sam.'],
['Po co rel="noopener" przy target="_blank"?','Nowa strona nie dostaje dostępu do window.opener','Link działa szybciej','Otwiera link w tej samej karcie','Blokuje JavaScript na stronie docelowej','Współczesne przeglądarki robią to domyślnie, ale jawny zapis nie szkodzi.'],
['Po co rel="noreferrer"?','Nie wysyła adresu strony źródłowej','Blokuje skrypty','Wymusza HTTPS','Dodaje token','Adres może zawierać dane wewnętrzne.'],
['Czym grozi <div {...userProps}>?','Niezaufany obiekt może przekazać dangerouslySetInnerHTML lub zdarzenia','Niczym','Tylko złym CSS','Błędem kompilacji zawsze','Props z danych nie mogą być spreadowane bez filtru.'],
['Czy ref.current.innerHTML = x jest bezpieczne?','Nie, omija escapowanie Reacta','Tak, ref jest bezpieczny','Tak, poza trybem deweloperskim','Tak, z TSX','To ten sam problem co dangerouslySetInnerHTML.'],
['Który kod jest ryzykowny niezależnie od Reacta?','eval(userInput)','useState(userInput)','<p>{userInput}</p>','encodeURIComponent(userInput)','eval wykonuje tekst jako kod.'],
['Dlaczego sekret w paczce JS to nie sekret?','Każdy użytkownik może pobrać i odczytać pliki','Minifikacja go szyfruje','Przeglądarka go ukrywa','React go usuwa','Minifikacja nie jest szyfrowaniem.'],
['Czy prefiks VITE_ ukrywa wartość?','Nie, wręcz wystawia ją do klienta','Tak','Tak, w produkcji','Tak, w TS','Prefiks oznacza zgodę na publikację.'],
['Czy DSN Sentry jest sekretem?','Nie, identyfikuje projekt i nie daje dostępu do danych','Tak, musi być ukryty','Tak, tak jak hasło','Nie wolno go używać w kliencie','Dokumentacja traktuje go jako publiczny identyfikator.'],
['Co oznacza HttpOnly w ciasteczku?','JavaScript nie może go odczytać','Działa tylko przez HTTP','Jest usuwane po zamknięciu','Jest szyfrowane przez przeglądarkę','Wstrzyknięty skrypt nie wykradnie tej wartości.'],
['Co oznacza Secure w ciasteczku?','Wysyłane tylko przez HTTPS','Niedostępne dla JS','Szyfrowane w pamięci','Wymaga 2FA','Chroni przed podsłuchem na niezabezpieczonym połączeniu.'],
['Co daje SameSite=Lax?','Nie wysyła ciasteczka z większości żądań z innych stron','Blokuje XSS','Szyfruje treść','Zmienia domenę','To ważna warstwa przeciw CSRF.'],
['Czym jest CSRF?','Wykonanie żądania z sesją ofiary z obcej strony','Wstrzyknięcie skryptu','Podmiana DNS','Przepełnienie bufora','Dlatego przydają się SameSite i tokeny CSRF.'],
['Dlaczego ciasteczko HttpOnly nie rozwiązuje wszystkiego?','XSS nadal może wykonywać żądania jako użytkownik','Bo można je odczytać','Bo nie działa w Chrome','Bo Spring go nie obsługuje','Atakujący nie wykradnie tokenu, ale nadal działa w sesji.'],
['Dlaczego localStorage jest złym miejscem na token?','Każdy skrypt na stronie może go odczytać','Jest zbyt mały','Działa tylko w HTTP','Jest czyszczony co minutę','Po XSS token łatwo wyciec.'],
['Co robi CSP?','Mówi przeglądarce, skąd wolno ładować zasoby','Sanityzuje HTML','Szyfruje ruch','Ustawia ciasteczka','Ogranicza skutki XSS.'],
['Co oznacza script-src \'self\'?','Skrypty tylko z tego samego źródła','Wszystkie skrypty','Skrypty inline','Brak skryptów','Skrypty inline wymagają osobnego dopuszczenia.'],
['Dlaczego \'unsafe-inline\' w script-src jest złe?','Dopuszcza wstrzyknięty skrypt inline','Spowalnia stronę','Blokuje pliki','Jest błędem składni','Znacząco osłabia obronę przed XSS.'],
['Dlaczego \'unsafe-inline\' w style-src jest mniej groźne?','Style są mniej ryzykowne niż skrypty, choć nie są bez ryzyka','Bo jest ignorowane','Bo CSS nie może niczego zrobić','Bo przeglądarka je blokuje','Często to świadomy kompromis przy stylach inline Reacta.'],
['Co robi frame-ancestors \'none\'?','Zabrania osadzania strony w ramce','Blokuje iframe w treści','Wyłącza wszystkie obrazy','Blokuje fetch','Chroni przed clickjackingiem.'],
['Co robi base-uri \'self\'?','Ogranicza element base','Zmienia adres API','Wymusza HTTPS','Blokuje skrypty','Wstrzyknięty base mógłby zmienić adresy względne.'],
['Co robi form-action \'self\'?','Ogranicza dokąd formularze wysyłają dane','Blokuje walidację','Zmienia metodę na POST','Wyłącza CSRF','Utrudnia wyprowadzanie danych formularzem.'],
['Co robi connect-src?','Ogranicza fetch, XHR i WebSocket','Ogranicza obrazy','Ogranicza style','Ogranicza routing','Utrudnia wysyłkę danych na obce serwery.'],
['Która dyrektywa jest wartością zapasową?','default-src','frame-ancestors','base-uri','report-uri','Pozostałe wymagają własnego wpisu.'],
['Po co nagłówek Report-Only?','Pozwala obserwować naruszenia bez blokowania','Wyłącza CSP na stałe','Działa tylko w dev','Wysyła raport do Google','Wdrożenie staje się bezpieczniejsze.'],
['Czy CSP zastępuje sanityzację?','Nie, to dodatkowa warstwa','Tak','Tak, jeśli jest ścisła','Tak, dla HTML','Warstwy łapią różne błędy.'],
['Co jest problemem nonce w CSP przy statycznym hostingu?','Nonce musi być generowany dla każdej odpowiedzi','Nie działa w Chrome','Blokuje CSS','Wymaga PostgreSQL','Plik statyczny ma stałą treść, więc potrzebny jest serwer lub hash.'],
['Po co hash w CSP?','Dopuszcza konkretny skrypt inline o znanej zawartości','Szyfruje skrypt','Blokuje skrypty zewnętrzne','Podpisuje tokeny','Zmiana treści unieważnia wpis.'],
['Co dzieje się z script-src bez default-src i odwrotnie?','Brak obu oznacza brak ograniczeń skryptów','Skrypty są zablokowane','Strona się nie ładuje','Skrypty wymagają hash','Polityka bez tych dyrektyw niewiele chroni.'],
['Co jest ryzykiem biblioteki npm?','Działa w tym samym kontekście co aplikacja','Nie da się jej zaktualizować','Blokuje CSP','Zawsze jest wirusem','Stąd audyt i ostrożne wybieranie zależności.'],
['Co sprawdza npm audit?','Znane podatności w zależnościach','Jakość kodu','Sekrety w repozytorium','Poprawność JSX','To lista znanych problemów, a nie gwarancja bezpieczeństwa.'],
['Co daje package-lock i npm ci?','Powtarzalne wersje zależności','Szyfrowanie pakietów','Automatyczne aktualizacje','Brak podatności','Wiesz, co dokładnie zostało zbudowane.'],
['Co oznacza Dependabot lub Renovate?','Automatyczne propozycje aktualizacji','Skaner XSS','Kompilator','Serwer CI','Dzięki temu aktualizacje są regularne.'],
['Gdzie należy uwierzytelniać uprawnienia?','Na backendzie','Tylko w komponencie','Tylko w routerze klienta','W localStorage','Ukrycie przycisku nie chroni endpointu.'],
['Co pokazują uprawnienia w kliencie?','Decydują o UX, ale nie o bezpieczeństwie','Pełną ochronę danych','Nowy token','Nic','Żądanie można wysłać bez interfejsu.'],
['Które dane nie powinny trafiać do logów klienta?','Tokeny i dane osobowe','Numer wersji aplikacji','Identyfikator trasy','Kod błędu HTTP','Logi trafiają też do narzędzi zewnętrznych.'],
['Co zrobić z mapami źródeł na produkcji?','Wysłać do monitoringu, a nie publikować na serwerze','Publikować wszystkie','Trzymać w localStorage','Wstawić do HTML','Ułatwiają też analizę atakującym, choć nie są ochroną samą w sobie.'],
['Który komentarz jest błędny?','„Mamy minifikację, więc sekret jest bezpieczny”','„DOMPurify trzeba aktualizować”','„CSP ogranicza skutki XSS”','„React escapuje tekst”','Minifikacja nie ukrywa wartości.'],
['Jak traktować dane od backendu?','Też jako niezaufane w kontekście HTML i adresów','Zawsze jako bezpieczne','Jak kod aplikacji','Jak dane z typów','Backend często przekazuje treść od użytkowników.'],
['Czym różni się walidacja od sanityzacji?','Walidacja odrzuca, a sanityzacja usuwa niebezpieczne elementy','Niczym','Walidacja działa tylko w TS','Sanityzacja działa tylko w CSS','To uzupełniające się działania.'],
['Czym jest stored XSS?','Złośliwa treść zapisana na serwerze i podawana innym','Atak działający tylko raz w jednej karcie','Atak na pamięć podręczną CSS','Podmiana certyfikatu','Komentarz w tickecie to klasyczny przykład.'],
['Czym jest DOM XSS?','Kod klienta sam wstawia dane do niebezpiecznego miejsca DOM','Atak na serwer','Błąd SQL','Błąd CSS','Np. location.hash trafiające do innerHTML.'],
['Czy TSX blokuje adres javascript: w href?','Nie, href to zwykły string','Tak','Tak, w trybie strict','Tak, dzięki React','Dlatego potrzebna jest walidacja w czasie wykonania.'],
['Jakie jest zachowanie React 19 przy href javascript:?','Wymaga własnej ochrony: nie zakładaj, że ostrzeżenie wystarczy','React zawsze sanityzuje cały HTML','React zamienia adres na https','React wyłącza link i loguje na serwerze','Odpowiedzialność za dane nadal jest po stronie aplikacji.'],
['Które podejście do linków z ticketu jest najlepsze?','safeUrl przy renderze i walidacja na backendzie','Tylko CSS','Tylko typ string','Tylko ukrycie linku','Warstwy chronią nawet po błędzie jednej z nich.'],
['Co przetestować po podbiciu DOMPurify?','Testy zachowania, że dozwolona treść nadal się wyświetla','Tylko rozmiar paczki','Tylko typy','Nic','Aktualizacja może zmienić wynik sanityzacji.'],
['Jak zgłaszać incydent wycieku klucza?','Unieważnić klucz i ustalić zakres, nie tylko usunąć z kodu','Tylko usunąć z repozytorium','Zmienić nazwę zmiennej','Zminifikować plik','Klucz z historii lub paczki trzeba uznać za ujawniony.'],
['Który atrybut iframe ogranicza osadzaną treść?','sandbox','async','defer','lazy','Bez dopuszczenia skrypty w sandboxie nie działają.'],
['Który test najlepiej chroni przed powrotem luki z komentarzem?','Test z konkretnym ładunkiem onerror na komponencie','Test migawki przycisku','Sprawdzenie wersji Node','Test kolorów','Każda luka powinna zostawić test regresji.'],
];
const header=String.raw`import {sanitize} from "./SafeHtml";
import {safeUrl} from "./SafeLink";
import {pickSafeProps} from "./safeProps";
import {naiveSanitize} from "./naive";
import {auditCsp,buildCsp,devDeskPolicy,parseCsp} from "./csp";
import {scanBundle} from "./secrets";
import {html} from "./securityLab";`;
const probeRows=[
['Co zawiera wynik sanityzacji pogrubienia?','sanitize("<b>A</b>")','<b>A</b>','Dozwolony znacznik zostaje.',['A','&lt;b&gt;A&lt;/b&gt;','pusty tekst']],
['Co zostaje z obrazu z onerror?','sanitize("<img src=x onerror=alert(1)>")||"pusty tekst"','pusty tekst','Znacznik img nie jest na liście dozwolonych.',['<img src="x">','<img>','onerror']],
['Co zostaje ze skryptu?','sanitize("A<script>alert(1)</script>")','A','Skrypt znika razem z treścią.',['Aalert(1)','A<script>','alert(1)']],
['Co zostaje z linku z onclick?','sanitize(\'<a href="https://a.example" onclick="x()">L</a>\')','<a href="https://a.example">L</a>','Zdarzenie jest usuwane, a adres zostaje.',['L','<a onclick="x()">L</a>','pusty tekst']],
['Co zostaje z linku javascript?','sanitize(\'<a href="javascript:alert(1)">L</a>\')','<a>L</a>','Niebezpieczny adres zostaje usunięty.',['L','<a href="javascript:alert(1)">L</a>','pusty tekst']],
['Co zostaje z akapitu ze stylem?','sanitize(\'<p style="color:red">A</p>\')','<p>A</p>','style nie ma na liście atrybutów.',['A','<p style="color:red">A</p>','pusty tekst']],
['Co zostaje z nagłówka h1 poza listą?','sanitize("<h1>T</h1>")','T','Znacznik znika, a treść zostaje.',['<h1>T</h1>','pusty tekst','&lt;h1&gt;']],
['Jaki protokół przejdzie: https?','safeUrl("https://a.example/x")','https://a.example/x','Jest na liście.',['null','https://a.example','pusty tekst']],
['Jaki wynik dla mailto?','safeUrl("mailto:a@b.example")','mailto:a@b.example','Jest na liście.',['null','a@b.example','pusty tekst']],
['Jaki wynik dla javascript?','safeUrl("javascript:alert(1)")','null','Protokół jest poza listą.',['javascript:alert(1)','alert(1)','pusty tekst']],
['Jaki wynik dla JaVaScRiPt?','safeUrl("JaVaScRiPt:alert(1)")','null','Parser normalizuje protokół.',['JaVaScRiPt:alert(1)','javascript:alert(1)','pusty tekst']],
['Jaki wynik dla spacji przed javascript?','safeUrl(" javascript:alert(1)")','null','Parser usuwa spację z początku.',['javascript:alert(1)',' javascript:alert(1)','pusty tekst']],
['Jaki wynik dla nowej linii w javascript?','safeUrl("java\\nscript:alert(1)")','null','Parser usuwa znak nowej linii.',['java','http://localhost:3000/java','pusty tekst']],
['Jaki wynik dla data?','safeUrl("data:text/html,x")','null','data: mogłoby wykonać kod.',['data:text/html,x','x','pusty tekst']],
['Jaki protokół dostanie adres względny?','new URL(safeUrl("/x")!).pathname','/x','Bazą jest bieżące źródło.',['null','x','/']],
['Czy niepoprawny adres http:// zostanie odrzucony?','safeUrl("http://")','null','Konstruktor URL rzuca wyjątek.',['http://','http://localhost/','pusty tekst']],
['Co zwróci naiwna funkcja dla skryptu?','naiveSanitize("<script>x</script>")||"pusty tekst"','pusty tekst','Ten przypadek działa i daje złudne poczucie bezpieczeństwa.',['x','<script>','null']],
['Czy naiwna funkcja zostawia onerror?','naiveSanitize("<img onerror=x>").includes("onerror")',true,'Wyrażenie patrzy tylko na znacznik script.'],
['Czy naiwna funkcja usunie skrypt z nowymi liniami?','naiveSanitize("<script>\\nx\\n</script>").includes("<script>")',true,'Kropka nie obejmuje nowych linii.'],
['Co powstaje ze sklejonego skryptu?','naiveSanitize("<scr<script></script>ipt>")','<script>','Usunięcie fragmentu złożyło nowy znacznik.',['pusty tekst','<scr','ipt>']],
['Ile pól przepuści filtr props?','Object.keys(pickSafeProps({title:"A",onClick:"x",style:"y"})).length',1,'Tylko title jest na liście.'],
['Czy dangerouslySetInnerHTML przejdzie przez filtr?','"dangerouslySetInnerHTML" in pickSafeProps({dangerouslySetInnerHTML:{__html:"x"}})',false,'Klucza nie ma na liście dozwolonych.'],
['Czy className o złym typie przejdzie?','"className" in pickSafeProps({className:5})',false,'Sprawdzamy też typ wartości.'],
['Jak React zapisze tekst z tagiem?','html(<p>{"<b>x</b>"}</p>)','<p>&lt;b&gt;x&lt;/b&gt;</p>','To jest domyślne escapowanie.',['<p><b>x</b></p>','<p>x</p>','<b>x</b>']],
['Jak React zapisze HTML przez dangerouslySetInnerHTML?','html(<p dangerouslySetInnerHTML={{__html:"<b>x</b>"}}/>)','<p><b>x</b></p>','Escapowanie jest wyłączone.',['<p>&lt;b&gt;x&lt;/b&gt;</p>','<p>x</p>','<p></p>']],
['Jak wygląda jedna dyrektywa po zbudowaniu?','buildCsp({"script-src":["\'self\'"]})',"script-src 'self'",'Nazwa i wartości oddziela spacja.',["script-src='self'","script-src: 'self'","'self'"]],
['Ile dyrektyw ma polityka DevDesk?','Object.keys(parseCsp(buildCsp(devDeskPolicy))).length',8,'Odczyt odtwarza wszystkie dyrektywy.'],
['Ile wartości ma img-src?','parseCsp(buildCsp(devDeskPolicy))["img-src"].length',3,'self, data i domena plików.'],
['Jak odczytana zostanie nazwa z wielkimi literami?','Object.keys(parseCsp("Script-Src \'self\'"))[0]','script-src','Nazwy dyrektyw normalizujemy.',['Script-Src','SCRIPT-SRC','brak']],
['Ile ostrzeżeń ma polityka DevDesk?','auditCsp(devDeskPolicy).length',0,'unsafe-inline jest tylko w style-src.'],
['Ile ostrzeżeń ma pusta polityka?','auditCsp({}).length',3,'Brak skryptów, ramek i base-uri.'],
['Jakie ostrzeżenie daje unsafe-inline w script-src?','auditCsp({...devDeskPolicy,"script-src":["\'unsafe-inline\'"]})[0]','script-src dopuszcza skrypty inline','To główna słabość polityki.',['brak base-uri','script-src dopuszcza eval','brak ostrzeżeń']],
['Czy default-src zastąpi script-src w audycie?','auditCsp({"default-src":["\'self\'"],"frame-ancestors":["\'none\'"],"base-uri":["\'self\'"]}).length',0,'default-src jest wartością zapasową skryptów.'],
['Czy default-src zastąpi frame-ancestors?','auditCsp({"default-src":["\'self\'"],"base-uri":["\'self\'"]})[0]','brak frame-ancestors','Ta dyrektywa nie dziedziczy z default-src.',['brak script-src i default-src','brak base-uri','brak ostrzeżeń']],
['Ile znalezisk ma czysty plik?','scanBundle({"a.js":"const a=1"}).length',0,'Brak znanych wzorców.'],
['Co znajdzie skaner w kluczu AWS?','scanBundle({"a.js":"AKIA"+"ABCDEFGHIJKLMNOP"})[0]','a.js: Klucz AWS','Wzorzec ma prefiks i 16 znaków.',['a.js: Klucz Stripe live','brak','a.js']],
['Co znajdzie skaner w kluczu prywatnym?','scanBundle({"a.js":"-----BEGIN RSA PRIVATE KEY-----"})[0]','a.js: Klucz prywatny','Klucz prywatny w kliencie to krytyczny błąd.',['a.js: Klucz AWS','brak','a.js']],
['Czy skaner oznaczy publiczny adres?','scanBundle({"a.js":"https://api.devdesk.example"}).length',0,'Publiczne API nie jest sekretem.'],
['Czy skaner oznaczy adres wewnętrzny?','scanBundle({"a.js":"http://billing.internal/x"}).length',1,'Adres wewnętrzny nie powinien trafiać do paczki.'],
['Ile plików z sekretem wskaże skaner?','scanBundle({"a.js":"sk_live_"+"abcdefghij","b.js":"x"}).length',1,'Wynik wskazuje tylko plik ze znaleziskiem.'],
];
const project={
'src/SafeHtml.tsx':safeHtml,'src/SafeLink.tsx':safeLink,'src/safeProps.ts':props,'src/naive.ts':naive,'src/csp.ts':csp,'src/secrets.ts':secrets,'src/securityLab.tsx':lab,'src/security.test.tsx':tests,
'README.md':'DevDesk: bezpieczeństwo w kliencie. npm install, npm test. Testy działają w jsdom, więc pokazują wynik sanityzacji i filtrów, a nie egzekwowanie CSP przez przeglądarkę. Nagłówki sprawdź w DevTools po wdrożeniu Spring Boot. Ładunki testowe uruchamiaj tylko lokalnie.\n',
};
const mutants=[
{file:'src/SafeHtml.tsx',before:'DOMPurify.sanitize(html,POLICY)',after:'html'},
{file:'src/SafeHtml.tsx',before:'ALLOWED_ATTR:["href"]',after:'ALLOWED_ATTR:["href","style"]'},
{file:'src/SafeLink.tsx',before:'ALLOWED.includes(url.protocol)?url.href:null',after:'url.href'},
{file:'src/SafeLink.tsx',before:' rel="noopener noreferrer"',after:''},
{file:'src/SafeLink.tsx',before:'if(!safe)return <span>{children}</span>;',after:'if(!safe)return <a href={href}>{children}</a>;'},
{file:'src/safeProps.ts',before:'typeof value==="string"',after:'value!==undefined'},
{file:'src/csp.ts',before:'.toLowerCase()',after:''},
{file:'src/csp.ts',before:'??directives["default-src"]',after:''},
{file:'src/csp.ts',before:'value==="*"||value==="https:"',after:'value==="*"'},
{file:'src/secrets.ts',before:'sk_live_',after:'sk_test_'},
];
module.exports=lesson({exercises,concepts,probeRows,project,mutants,header});
