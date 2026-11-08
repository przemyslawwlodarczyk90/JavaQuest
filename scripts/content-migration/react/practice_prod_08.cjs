const {lesson}=require('./practice_kit.cjs');
const query=String.raw`export const STATUSES=["OPEN","IN_PROGRESS","CLOSED"] as const;
export const PRIORITIES=["LOW","MEDIUM","HIGH","CRITICAL"] as const;
export const SORTABLE=["id","title","status","priority","createdAt"] as const;

export type Status=typeof STATUSES[number];
export type Priority=typeof PRIORITIES[number];
export type SortField=typeof SORTABLE[number];
export type IssueQuery={
  status:Status|null;priority:Priority|null;assignee:string|null;q:string;
  sort:SortField;dir:"asc"|"desc";page:number;
};

export const DEFAULT_QUERY:IssueQuery={status:null,priority:null,assignee:null,q:"",sort:"createdAt",dir:"desc",page:1};

function oneOf<T extends string>(allowed:readonly T[],value:string|null):T|null{
  return value!==null&&(allowed as readonly string[]).includes(value)?value as T:null;
}

// Adres pochodzi od użytkownika: każdą wartość sprawdzamy, a nieznane wracają do domyślnych.
export function parseIssueQuery(params:URLSearchParams):IssueQuery{
  const page=Number(params.get("page"));
  return {
    status:oneOf(STATUSES,params.get("status")),
    priority:oneOf(PRIORITIES,params.get("priority")),
    assignee:params.get("assignee")||null,
    q:params.get("q")??"",
    sort:oneOf(SORTABLE,params.get("sort"))??DEFAULT_QUERY.sort,
    dir:params.get("dir")==="asc"?"asc":"desc",
    page:Number.isInteger(page)&&page>=1?page:1,
  };
}

export function toSearchParams(query:IssueQuery){
  const params=new URLSearchParams();
  if(query.status)params.set("status",query.status);
  if(query.priority)params.set("priority",query.priority);
  if(query.assignee)params.set("assignee",query.assignee);
  if(query.q)params.set("q",query.q);
  if(query.sort!==DEFAULT_QUERY.sort)params.set("sort",query.sort);
  if(query.dir!==DEFAULT_QUERY.dir)params.set("dir",query.dir);
  if(query.page!==1)params.set("page",String(query.page));
  return params;
}

// Zmiana filtra wraca na pierwszą stronę: inaczej łatwo trafić na pustą stronę wyników.
export function applyFilter(query:IssueQuery,change:Partial<Omit<IssueQuery,"page">>):IssueQuery{
  return {...query,...change,page:1};
}

export function toggleSort(query:IssueQuery,field:SortField):IssueQuery{
  const dir=query.sort===field&&query.dir==="asc"?"desc":"asc";
  return {...query,sort:field,dir,page:1};
}

export function pageCount(total:number,size:number){
  return Math.max(1,Math.ceil(total/size));
}`;
const issuesPage=String.raw`import {useSearchParams} from "react-router";
import {applyFilter,parseIssueQuery,pageCount,STATUSES,toggleSort,toSearchParams,type IssueQuery,type Status} from "./issueQuery";

export type Issue={id:number;title:string;status:Status};
export type PageResult={items:readonly Issue[];total:number};

export function IssuesList({result,error}:{result:PageResult|null;error?:string}){
  const [params,setParams]=useSearchParams();
  const query=parseIssueQuery(params);
  const update=(next:IssueQuery)=>setParams(toSearchParams(next));
  if(error)return <p role="alert">{error}</p>;
  if(!result)return <p role="status">Ładowanie zgłoszeń…</p>;
  const pages=pageCount(result.total,10);
  return (
    <section aria-label="Zgłoszenia">
      <label>Status
        <select value={query.status??""} onChange={event=>update(applyFilter(query,{status:(event.currentTarget.value||null) as Status|null}))}>
          <option value="">Wszystkie</option>
          {STATUSES.map(status=><option key={status}>{status}</option>)}
        </select>
      </label>
      <button type="button" onClick={()=>update(toggleSort(query,"title"))}>Sortuj po tytule</button>
      {result.items.length===0?(
        <p>Brak zgłoszeń dla tych filtrów.</p>
      ):(
        <ul>{result.items.map(issue=><li key={issue.id}>{issue.title}</li>)}</ul>
      )}
      <nav aria-label="Strony">
        <button type="button" disabled={query.page<=1} onClick={()=>update({...query,page:query.page-1})}>Poprzednia</button>
        <span>Strona {query.page} z {pages}</span>
        <button type="button" disabled={query.page>=pages} onClick={()=>update({...query,page:query.page+1})}>Następna</button>
      </nav>
    </section>
  );
}`;
const auth=String.raw`import {createContext,useContext,type ReactNode} from "react";
import {Navigate,useLocation} from "react-router";

export type Role="AGENT"|"ADMIN";
export type User={id:number;name:string;role:Role};

export const AuthContext=createContext<User|null>(null);

export function canAccess(user:User|null,role?:Role){
  if(!user)return "login" as const;
  if(role&&user.role!==role)return "forbidden" as const;
  return "ok" as const;
}

// To wygoda interfejsu. Prawdziwe odrzucenie żądania należy do backendu.
export function RequireAuth({role,children}:{role?:Role;children:ReactNode}){
  const user=useContext(AuthContext);
  const location=useLocation();
  const access=canAccess(user,role);
  if(access==="login")return <Navigate to="/login" replace state={{from:location.pathname}}/>;
  if(access==="forbidden")return <p role="alert">Brak uprawnień do tego ekranu.</p>;
  return <>{children}</>;
}

export function menuFor(user:User){
  return [
    {to:"/issues",label:"Zgłoszenia"},
    {to:"/dashboard",label:"Dashboard"},
    ...(user.role==="ADMIN"?[{to:"/admin/users",label:"Użytkownicy"}]:[]),
  ];
}`;
const form=String.raw`import {z} from "zod";

export const issueSchema=z.object({
  title:z.string().trim().min(3,"Tytuł ma mieć co najmniej 3 znaki"),
  status:z.enum(["OPEN","IN_PROGRESS","CLOSED"]),
  assignee:z.string().nullable(),
});

export type IssueInput=z.infer<typeof issueSchema>;

export type FieldErrors=Partial<Record<keyof IssueInput,string>>;

export function validateIssue(value:unknown):{ok:true;data:IssueInput}|{ok:false;errors:FieldErrors}{
  const parsed=issueSchema.safeParse(value);
  if(parsed.success)return {ok:true,data:parsed.data};
  const errors:FieldErrors={};
  for(const issue of parsed.error.issues){
    const key=issue.path[0] as keyof IssueInput;
    errors[key]??=issue.message;
  }
  return {ok:false,errors};
}

// Serwer odpowiada listą pól. Nieznane pole nie może zniknąć, więc trafia do błędu ogólnego.
export function mapServerErrors(response:{fieldErrors?:Record<string,string>;message?:string}){
  const fields:FieldErrors={};
  const general:string[]=response.message?[response.message]:[];
  for(const [field,message] of Object.entries(response.fieldErrors??{})){
    if(field in issueSchema.shape)fields[field as keyof IssueInput]=message;
    else general.push(field+": "+message);
  }
  return {fields,general};
}

export function isDirty(initial:IssueInput,current:IssueInput){
  return (Object.keys(initial) as (keyof IssueInput)[]).some(key=>initial[key]!==current[key]);
}`;
const bulk=String.raw`export type Selection=ReadonlySet<number>;

export function toggleOne(selection:Selection,id:number):Selection{
  const next=new Set(selection);
  if(!next.delete(id))next.add(id);
  return next;
}

export function togglePage(selection:Selection,pageIds:readonly number[]):Selection{
  const allSelected=pageIds.length>0&&pageIds.every(id=>selection.has(id));
  const next=new Set(selection);
  for(const id of pageIds){
    if(allSelected)next.delete(id);
    else next.add(id);
  }
  return next;
}

export function headerState(selection:Selection,pageIds:readonly number[]){
  const count=pageIds.filter(id=>selection.has(id)).length;
  if(count===0)return "none";
  return count===pageIds.length?"all":"some";
}

// Akcja zbiorcza może udać się częściowo: użytkownik musi wiedzieć, które zgłoszenia nie przeszły.
export function summarizeBulk(results:readonly {id:number;ok:boolean}[]){
  const failed=results.filter(result=>!result.ok).map(result=>result.id);
  return {done:results.length-failed.length,failed};
}`;
const adr=String.raw`const SECTIONS=["## Kontekst","## Rozważane opcje","## Decyzja","## Konsekwencje"];
const REQUIRED_TOPICS=["JSX/TSX","stan","uwierzytelnianie","struktura","testy"];

export function auditAdr(text:string){
  const problems=SECTIONS.filter(section=>!text.includes(section)).map(section=>"brak sekcji "+section);
  if(!/^Status:\s*\S+/m.test(text))problems.push("brak statusu");
  const options=text.split("## Rozważane opcje")[1]?.split("##")[0]??"";
  if((options.match(/^\s*\d+\./gm)?.length??0)<2)problems.push("mniej niż dwie opcje");
  return problems;
}

export function missingTopics(titles:readonly string[]){
  const lower=titles.map(title=>title.toLowerCase());
  return REQUIRED_TOPICS.filter(topic=>!lower.some(title=>title.includes(topic.toLowerCase())));
}`;
const router=String.raw`// app/router.tsx
import {lazy} from "react";
import {createBrowserRouter,Navigate,type RouteObject} from "react-router";
import {AppLayout} from "./AppLayout";
import {RequireAuth} from "@/features/auth";
import {RouteError} from "./RouteError";
import {NotFoundPage} from "./NotFoundPage";

const IssuesPage=lazy(()=>import("@/features/issues/pages/IssuesPage"));
const IssueDetailsPage=lazy(()=>import("@/features/issues/pages/IssueDetailsPage"));
const DashboardPage=lazy(()=>import("@/features/dashboard/pages/DashboardPage"));
const UsersPage=lazy(()=>import("@/features/users/pages/UsersPage"));
const LoginPage=lazy(()=>import("@/features/auth/pages/LoginPage"));

const routes:RouteObject[]=[
  {path:"/login",element:<LoginPage/>},
  {
    element:<RequireAuth><AppLayout/></RequireAuth>,
    errorElement:<RouteError/>,
    children:[
      {index:true,element:<Navigate to="/issues" replace/>},
      {path:"issues",element:<IssuesPage/>},
      {path:"issues/:issueId",element:<IssueDetailsPage/>,errorElement:<RouteError/>},
      {path:"dashboard",element:<DashboardPage/>},
      {path:"admin/users",element:<RequireAuth role="ADMIN"><UsersPage/></RequireAuth>},
    ],
  },
  {path:"*",element:<NotFoundPage/>},
];

export const router=createBrowserRouter(routes);`;
const sampleAdr=`# 0003. Stan serwera w TanStack Query, stan UI lokalnie i w URL

Data: 2026-11-02
Status: przyjęta

## Kontekst
Lista zgłoszeń i komentarze pochodzą z API, a filtry mają dać się udostępnić linkiem.

## Rozważane opcje
1. Redux Toolkit i RTK Query.
2. TanStack Query oraz parametry URL.
3. Ręczny fetch w useEffect i Context.

## Decyzja
Opcja 2: stan serwera to cache, a filtry są w URL.

## Konsekwencje
+ mniej kodu i działające linki do widoków
- dwa mechanizmy do nauczenia dla nowych osób
`;
const tests=String.raw`import {expect,test} from "vitest";
import {fireEvent,render,screen} from "@testing-library/react";
import {MemoryRouter,Route,Routes,useLocation} from "react-router";
import {applyFilter,DEFAULT_QUERY,pageCount,parseIssueQuery,toggleSort,toSearchParams} from "./issueQuery";
import {IssuesList} from "./IssuesList";
import {AuthContext,canAccess,menuFor,RequireAuth,type User} from "./auth";
import {isDirty,mapServerErrors,validateIssue} from "./issueForm";
import {headerState,summarizeBulk,toggleOne,togglePage} from "./bulk";
import {auditAdr,missingTopics} from "./adrAudit";
import {readFileSync} from "node:fs";
import {resolve} from "node:path";

const params=(text:string)=>new URLSearchParams(text);
const agent:User={id:1,name:"Ala",role:"AGENT"},admin:User={id:2,name:"Ola",role:"ADMIN"};

function Where(){const location=useLocation();return <output>{location.pathname+location.search}</output>;}

test("adres listy odtwarza ten sam widok",()=>{
  const query={...DEFAULT_QUERY,status:"OPEN" as const,q:"awaria",sort:"title" as const,dir:"asc" as const,page:3};
  expect(parseIssueQuery(toSearchParams(query))).toEqual(query);
  expect(toSearchParams(DEFAULT_QUERY).toString()).toBe("");
});

test("nieprawidłowe wartości w adresie wracają do domyślnych",()=>{
  expect(parseIssueQuery(params("status=XXX&page=-2&sort=hack&dir=boczny"))).toEqual(DEFAULT_QUERY);
  expect(parseIssueQuery(params("page=1.5")).page).toBe(1);
  expect(parseIssueQuery(params("page=abc")).page).toBe(1);
});

test("zmiana filtra i sortowania wraca na pierwszą stronę",()=>{
  const start={...DEFAULT_QUERY,page:5};
  expect(applyFilter(start,{q:"x"}).page).toBe(1);
  expect(toggleSort(start,"title")).toMatchObject({sort:"title",dir:"asc",page:1});
  expect(toggleSort({...start,sort:"title",dir:"asc"},"title").dir).toBe("desc");
  expect(pageCount(0,10)).toBe(1);
  expect(pageCount(21,10)).toBe(3);
});

test("filtr zapisuje się w adresie, a strona wraca do pierwszej",()=>{
  render(<MemoryRouter initialEntries={["/issues?page=3"]}><IssuesList result={{items:[{id:1,title:"A",status:"OPEN"}],total:50}}/><Where/></MemoryRouter>);
  expect(screen.getByText("Strona 3 z 5")).toBeVisible();
  fireEvent.change(screen.getByRole("combobox",{name:"Status"}),{target:{value:"CLOSED"}});
  expect(screen.getByRole("status")).toHaveTextContent("/issues?status=CLOSED");
});

test("paginacja blokuje krańce i zmienia adres",()=>{
  render(<MemoryRouter initialEntries={["/issues"]}><IssuesList result={{items:[{id:1,title:"A",status:"OPEN"}],total:15}}/><Where/></MemoryRouter>);
  expect(screen.getByRole("button",{name:"Poprzednia"})).toBeDisabled();
  fireEvent.click(screen.getByRole("button",{name:"Następna"}));
  expect(screen.getByRole("status")).toHaveTextContent("page=2");
  expect(screen.getByRole("button",{name:"Następna"})).toBeDisabled();
});

test("stany listy: ładowanie, błąd i pusto",()=>{
  const view=render(<MemoryRouter><IssuesList result={null}/></MemoryRouter>);
  expect(screen.getByRole("status")).toHaveTextContent("Ładowanie");
  view.rerender(<MemoryRouter><IssuesList result={null} error="Błąd serwera"/></MemoryRouter>);
  expect(screen.getByRole("alert")).toHaveTextContent("Błąd serwera");
  view.rerender(<MemoryRouter><IssuesList result={{items:[],total:0}}/></MemoryRouter>);
  expect(screen.getByText("Brak zgłoszeń dla tych filtrów.")).toBeVisible();
});

test("trasa chroniona i role",()=>{
  const app=(user:User|null)=>(
    <AuthContext.Provider value={user}>
      <MemoryRouter initialEntries={["/admin/users"]}>
        <Routes>
          <Route path="/login" element={<p>Logowanie</p>}/>
          <Route path="/admin/users" element={<RequireAuth role="ADMIN"><p>Użytkownicy</p></RequireAuth>}/>
        </Routes>
      </MemoryRouter>
    </AuthContext.Provider>
  );
  const view=render(app(null));
  expect(screen.getByText("Logowanie")).toBeVisible();
  view.unmount();
  const second=render(app(agent));
  expect(screen.getByRole("alert")).toHaveTextContent("Brak uprawnień");
  second.unmount();
  render(app(admin));
  expect(screen.getByText("Użytkownicy")).toBeVisible();
});

test("menu zależy od roli",()=>{
  expect(menuFor(agent).map(item=>item.to)).toEqual(["/issues","/dashboard"]);
  expect(menuFor(admin).map(item=>item.to)).toContain("/admin/users");
  expect(canAccess(null)).toBe("login");
  expect(canAccess(agent,"ADMIN")).toBe("forbidden");
  expect(canAccess(agent)).toBe("ok");
});

test("formularz: walidacja i błędy serwera",()=>{
  expect(validateIssue({title:"  Awaria  ",status:"OPEN",assignee:null})).toEqual({ok:true,data:{title:"Awaria",status:"OPEN",assignee:null}});
  const invalid=validateIssue({title:"a",status:"X",assignee:null});
  expect(invalid.ok).toBe(false);
  if(!invalid.ok){expect(invalid.errors.title).toContain("co najmniej 3");expect(invalid.errors.status).toBeDefined();}
  expect(mapServerErrors({fieldErrors:{title:"Zajęty",inne:"Zły"},message:"Błąd"})).toEqual({fields:{title:"Zajęty"},general:["Błąd","inne: Zły"]});
  const base={title:"A",status:"OPEN" as const,assignee:null};
  expect(isDirty(base,{...base})).toBe(false);
  expect(isDirty(base,{...base,title:"B"})).toBe(true);
});

test("zaznaczanie i wynik akcji zbiorczej",()=>{
  const none=new Set<number>();
  expect([...toggleOne(none,1)]).toEqual([1]);
  expect([...toggleOne(new Set([1]),1)]).toEqual([]);
  expect(none.size).toBe(0);
  expect([...togglePage(new Set([1]),[1,2])].sort()).toEqual([1,2]);
  expect([...togglePage(new Set([1,2,9]),[1,2])]).toEqual([9]);
  expect(headerState(new Set([1]),[1,2])).toBe("some");
  expect(headerState(new Set([1,2]),[1,2])).toBe("all");
  expect(headerState(new Set(),[1,2])).toBe("none");
  expect(summarizeBulk([{id:1,ok:true},{id:2,ok:false}])).toEqual({done:1,failed:[2]});
});

test("rekord decyzji ma wymagane sekcje i alternatywy",()=>{
  const sample=readFileSync(resolve(process.cwd(),"docs/adr/0003-stan-serwera.md"),"utf8");
  expect(auditAdr(sample)).toEqual([]);
  expect(auditAdr("# Tytuł\nOpis kodu")).toContain("brak statusu");
  expect(auditAdr(sample.replace("2. TanStack Query oraz parametry URL.\n3. Ręczny fetch w useEffect i Context.\n",""))).toEqual(["mniej niż dwie opcje"]);
  expect(missingTopics(["Wybór JSX/TSX","Stan serwera","Uwierzytelnianie","Struktura projektu","Testy"])).toEqual([]);
  expect(missingTopics(["Wybór JSX/TSX"])).toHaveLength(4);
});`;
const exercises=[
['Przygotuj szkielet projektu i podgląd pull requesta.','Pusta aplikacja już przechodzi przez CI i wdrożenie.','Projekt Vite, app/features/shared, aliasy, ESLint z regułami granic, Prettier, CI z lintem, typami i buildem. Wdróż pustą aplikację z podglądem. Problemy konfiguracyjne wyjdą pierwszego dnia, a nie w ostatnim tygodniu.'],
['Napisz pierwszy ADR: JSX czy TSX.','Kontekst, opcje, decyzja, konsekwencje.',sampleAdr],
['Zapisz filtry, sortowanie i stronę w adresie.','Link odtwarza widok.',query],
['Sprawdź wartości z adresu.','Adres pochodzi od użytkownika.',query],
['Wracaj na pierwszą stronę po zmianie filtra.','Inaczej łatwo trafić na pustą stronę.',query],
['Napisz przełączanie sortowania.','Druga zmiana odwraca kierunek.',query],
['Zbuduj listę z useSearchParams.','Komponent nie trzyma drugiej kopii filtrów.',issuesPage],
['Obsłuż ładowanie, błąd i pusty wynik.','Najczęstsza uwaga przy przeglądzie.',issuesPage],
['Zablokuj przyciski na krańcach paginacji.','Stan wynika z numeru strony.',issuesPage],
['Napisz szkielet tras.','Lazy i błąd na poziomie trasy.',router],
['Chroń trasy i role.','RequireAuth przekierowuje lub pokazuje brak uprawnień.',auth],
['Dopasuj menu do roli.','Agent nie widzi zarządzania użytkownikami.',auth],
['Sprawdź role także na backendzie.','Interfejs to wygoda.','Agent może wysłać żądanie ręcznie, więc Spring Boot odrzuca je przez @PreAuthorize("hasRole(\'ADMIN\')") lub konfigurację. Test: żądanie jako agent do /api/admin/users zwraca 403. Ukrycie menu nie jest kontrolą dostępu.'],
['Napisz schemat formularza wspólny z API.','Komunikaty błędów po polsku.',form],
['Pokaż błędy z serwera przy polach.','Nieznane pole trafia do błędu ogólnego.',form],
['Ostrzegaj przed utratą zmian.','isDirty porównuje wartości.',form],
['Zarządzaj fokusem w modalu.','Pułapka i powrót do przycisku.','Po otwarciu fokus na pierwszym polu; Tab zostaje w modalu; Escape prosi o potwierdzenie, jeśli są niezapisane zmiany; po zamknięciu fokus wraca do przycisku. Natywny dialog z showModal rozwiązuje część problemów. Dodaj test E2E.'],
['Dodaj komentarze z aktualizacją optymistyczną.','Wycofanie przy błędzie.','useMutation: w onMutate zapamiętaj listę i dopisz komentarz z oznaczeniem, w onError przywróć, a w onSettled unieważnij zapytanie. Komentarz ma własny identyfikator tymczasowy zastępowany identyfikatorem z serwera.'],
['Napisz zaznaczanie i akcję zbiorczą.','Bez mutacji zbioru.',bulk],
['Pokaż stan nagłówka tabeli.','Stan mieszany: indeterminate.',bulk],
['Obsłuż częściowe powodzenie akcji.','Użytkownik musi wiedzieć, co nie przeszło.',bulk],
['Dodaj dashboard ładowany leniwie.','Biblioteka wykresów nie spowalnia logowania.','Zapisz w routerze: const DashboardPage=lazy(()=>import("@/features/dashboard/pages/DashboardPage")); W analizie paczki sprawdź, że recharts jest w osobnym pliku. Dane z osobnego zapytania, liczby kluczowe i tabela pod wykresem.'],
['Napisz trzy scenariusze E2E.','Logowanie, filtrowanie, tworzenie.','1. Logowanie i przejście do listy. 2. Filtr w adresie, odświeżenie, przejście do szczegółów. 3. Utworzenie zgłoszenia w modalu i widoczność na liście. Asercje czekają na stan, a nie na stałe opóźnienia.'],
['Przeprowadź audyt dostępności wszystkich ekranów.','Axe, klawiatura, czytnik.','axe bez naruszeń na każdym ekranie i w otwartym modalu, przejście klawiaturą, ogłoszenia i zapis wyników w raporcie. Lighthouse dostępność co najmniej 90 to dodatkowy wskaźnik, a nie dowód.'],
['Podłącz monitoring z mapami źródeł.','Błąd z produkcji musi wskazać plik.','Sentry z reactErrorHandler w createRoot, release równy SHA commita, mapy wysłane w CI i usunięte z dist, beforeSend usuwa dane osobowe. Wywołaj błąd testowy na produkcji i sprawdź stos.'],
['Sprawdź rekord decyzji automatycznie.','Struktura nie dowodzi sensu.',adr],
['Wskaż brakujące rekordy decyzji.','Pięć wymaganych tematów.',adr],
['Zacznij od pionowego przekroju jednej funkcji.','Wszystkie warstwy naraz, zanim zaczniesz kolejne.','MSW, API, Query, tabela, test i wdrożenie listy zgłoszeń. Problemy z aliasami, MSW w testach i zmiennymi w CI wyjdą na jednej funkcji. Testy pisz razem z funkcją, wtedy każdy kamień milowy jest naprawdę skończony.'],
['Znajdź błędy w tej architekturze.','Porównaj z listą pułapek.','Filtry w useState gubią link. Dane z API skopiowane do stanu dają dwa źródła prawdy. Ochrona tylko w interfejsie nie chroni danych. Wszystko w jednej paczce spowalnia logowanie. Brak stanów pustych i błędów. ADR napisany po fakcie bez alternatyw.'],
['Oddaj projekt.','README, ADR, linki i testy.','README z uruchomieniem, testami, .env.example i wdrożeniem. Co najmniej pięć ADR. Link do repozytorium, produkcji i raportu CI. Sprawdź wszystkie kryteria na wdrożonej wersji, a nie lokalnie.'],
];
const concepts=[
['Co brief opisuje?','Co ma działać, a nie jak','Dokładną strukturę plików','Nazwy zmiennych','Kolory przycisków','Decyzje należą do Ciebie.'],
['Co oddajesz oprócz kodu?','Rekordy decyzji','Tylko zrzuty ekranu','Plik CSS','Listę commitów','ADR uzasadnia wybory.'],
['Co opisuje ADR?','Decyzję, kontekst, opcje i konsekwencje','Kod linia po linii','Harmonogram','Listę testów','Ma pokazać, dlaczego tak, a nie inaczej.'],
['Kiedy pisać ADR?','Gdy podejmujesz decyzję','Na końcu projektu','Nigdy','Po wdrożeniu','Inaczej opisuje kod, a nie wybór.'],
['Ile ADR wymaga projekt?','Co najmniej pięć','Jeden','Dwadzieścia','Żadnego','Tematy są wskazane w kryteriach.'],
['Czemu ADR wymaga odrzuconych opcji?','Pokazują, że wybór był świadomy','Dla objętości','Dla CSS','Bo tak wygląda JSON','Przyszłe osoby zrozumieją kompromis.'],
['Gdzie przechowywać ADR?','W repozytorium, np. docs/adr','W e-mailu','W głowie','W CSS','Zmieniają się razem z kodem.'],
['Gdzie trzymać filtry listy?','W adresie URL','W useState','W localStorage','W stanie modułu','Link i odświeżenie działają.'],
['Co testuje skopiowanie adresu?','Odtworzenie tego samego widoku','Tylko CSS','Tylko sesję','Wydajność','To kryterium akceptacji.'],
['Czemu walidować parametry adresu?','Pochodzą od użytkownika','Dla SEO','Dla CSS','Bez powodu','Nieznana wartość nie może wysypać ekranu.'],
['Co zrobić ze stroną po zmianie filtra?','Wrócić na pierwszą','Zostawić','Dodać jeden','Losować','Inaczej możesz trafić na pustą stronę.'],
['Czemu paginacja serwerowa?','Tysiące zgłoszeń nie trafiają do klienta','Dla CSS','Bez powodu','Bo tak łatwiej','Klient dostaje jedną stronę.'],
['Co musi zawierać każdy ekran danych?','Ładowanie, błąd i pusty wynik','Tylko dane','Tylko spinner','Animację','Najczęstsza uwaga z przeglądu.'],
['Czemu nie kopiować danych z API do stanu?','Powstają dwa źródła prawdy','Bo stan jest wolny','Bo TS nie pozwala','Bez powodu','Formularz może mieć własną wersję roboczą.'],
['Kiedy kopia jest dopuszczalna?','Wersja robocza formularza do czasu zapisu','Zawsze','Nigdy','Dla każdej listy','Warto ją nazwać jako draft.'],
['Kto ostatecznie odrzuca żądanie?','Backend','Komponent menu','CSS','Router klienta','Interfejs można obejść.'],
['Co pokazać agentowi pod adresem admina?','Brak uprawnień lub 404','Pusty ekran','Dane admina','Błąd składni','Ani w menu, ani pod adresem.'],
['Jak przekierować niezalogowanego?','Navigate do /login z replace','window.close','Reload','Alert','replace nie zaśmieca historii.'],
['Po co state.from przy przekierowaniu?','Powrót po zalogowaniu','Dla CSS','Dla CORS','Bez powodu','Użytkownik wraca tam, gdzie chciał.'],
['Jaki typ sesji rozważyć najpierw?','Ciasteczko HttpOnly','Token w localStorage','Hasło w stanie','Token w URL','Wybór uzasadnij w ADR.'],
['Co wchodzi w szkielet z dnia pierwszego?','Struktura, lint, CI i wdrożenie pustej aplikacji','Wszystkie funkcje','Tylko CSS','Tylko README','Konfiguracja wychodzi wcześnie.'],
['Czym jest pionowy przekrój?','Jedna funkcja przez wszystkie warstwy','Wycięcie CSS','Podział ekranu','Rodzaj testu','Odsłania problemy konfiguracji.'],
['Kiedy pisać testy?','Razem z funkcją','Na końcu','Nigdy','Tylko E2E','Kamień milowy jest wtedy skończony.'],
['Ile scenariuszy E2E minimum?','Trzy','Jeden','Trzydzieści','Żaden','Logowanie, filtrowanie, tworzenie.'],
['Czemu E2E niestabilne na końcu są problemem?','Zespół przestaje ufać CI','Są zbyt szybkie','Psują CSS','Nie ma problemu','Dodawaj je stopniowo.'],
['Jakie wyniki Lighthouse?','Wydajność i dostępność co najmniej 90','100 zawsze','Dowolne','Tylko SEO','Wynik mierz na produkcji.'],
['Na czym mierzyć Lighthouse?','Na wersji produkcyjnej','Na dev','Na testach','Na Storybook','Tryb dev jest wolniejszy.'],
['Co oznacza „axe bez naruszeń na wszystkich ekranach”?','Także w otwartych warstwach i stanach','Tylko strona główna','Tylko logowanie','Tylko CSS','Stany ujawniają nowe problemy.'],
['Co musi zapewnić modal?','Pułapkę fokusu i powrót','Tylko tło','Animację','Brak Escape','Kryterium dostępności.'],
['Co z zamknięciem modala z niezapisanymi zmianami?','Ostrzeżenie','Ciche zamknięcie','Zapis bez pytania','Reload','Wymaganie z briefu.'],
['Gdzie trzymać schemat formularza?','Wspólnie z warstwą API','W komponencie osobno','W CSS','W README','Jedno źródło reguł.'],
['Jak pokazać błąd z serwera?','Przy polu lub jako błąd ogólny','Tylko w konsoli','Zignorować','Odświeżyć stronę','Błąd nie może zniknąć.'],
['Co zrobić z błędem nieznanego pola?','Pokazać jako błąd ogólny','Pominąć','Rzucić wyjątek w render','Zmienić status','Użytkownik musi go zobaczyć.'],
['Czym jest aktualizacja optymistyczna komentarza?','Pokazanie od razu z wycofaniem przy błędzie','Zapis bez serwera','Ukrycie błędu','Tylko spinner','Wymaganie z briefu.'],
['Jakie stany ma nagłówek zaznaczania?','Żaden, część i wszystkie','Tylko dwa','Tylko jeden','Cztery','Stan częściowy to indeterminate.'],
['Czym jest indeterminate?','Stan mieszany pola wyboru','Błąd','Tryb ładowania','Atrybut HTML w JSX','Ustawiany przez ref.'],
['Co pokazać po akcji zbiorczej z częściowym błędem?','Liczbę udanych i listę nieudanych','Tylko sukces','Tylko błąd ogólny','Nic','Inaczej dane rozjadą się z oczekiwaniem.'],
['Jak zaznaczenie zachowuje się przy zmianie strony?','Trzeba to świadomie zdecydować i pokazać','Zawsze znika po cichu','Zawsze obejmuje wszystko','Nie ma znaczenia','Użytkownik musi wiedzieć, co zaznaczył.'],
['Co ładować leniwie?','Dashboard z biblioteką wykresów','Formularz logowania jako pierwszy ekran zawsze','Tylko CSS','React','Logowanie nie płaci za wykresy.'],
['Co pokazuje odświeżenie dowolnej trasy?','Działający fallback SPA','Cache CSS','Sentry','TS','Kryterium funkcjonalne.'],
['Co pokazać dla nieistniejącego zgłoszenia?','404 w aplikacji','Pusty ekran','Biały ekran','Dane innego','Normalny stan.'],
['Co pokazać przy błędzie serwera?','Ekran z ponowieniem','Pusty ekran','Alert','Nic','Wymaganie z briefu.'],
['Skąd konfiguracja?','Zmienne środowiskowe ze schematem','Stałe w komponentach','localStorage','Komentarze','Lekcja 02 tego rozdziału.'],
['Co nie może znaleźć się w repozytorium?','Sekrety','README','Testy','ADR','Sprawdź też historię.'],
['Co pomaga w wyborze JSX lub TSX?','Model danych, mutacje, zespół','Moda','Długość nazwy','Kolor edytora','Pierwszy ADR.'],
['Co jest obowiązkowe niezależnie od JSX lub TSX?','Walidacja danych z API','as Ticket','any','Redux','Typy nie sprawdzają działania.'],
['Kiedy dodać Zustand?','Gdy pojawi się złożony stan UI między ekranami','Zawsze','Nigdy','Przed pierwszą funkcją','Nowa decyzja, nowy ADR.'],
['Co zawiera README projektu?','Uruchomienie, testy, zmienne, wdrożenie','Tylko logo','Historię firmy','Klucze','Kryterium dokumentacji.'],
['Czym jest .env.example?','Listą zmiennych bez wartości','Plikiem z sekretami','Konfiguracją Nginx','Testem','Dla nowych osób.'],
['Co sprawdzić przed oddaniem?','Wszystkie kryteria na wdrożonej wersji','Tylko lokalnie','Tylko testy','Tylko Lighthouse','Produkcja różni się od dev.'],
['Czy można pominąć MSW, mając backend?','Tak, ale testy i tak na nim korzystają','Nie','Zawsze trzeba oba','Tylko w JSX','Brief dopuszcza MSW zamiast backendu.'],
['Jak traktować Storybook w projekcie?','Opcjonalnie dla shared/ui','Obowiązkowo dla stron','Jako produkcję','Zamiast testów','Koszt dobierz do potrzeb.'],
['Co oznacza „zespół wsparcia” dla projektu?','Dużo pracy klawiaturą i filtrami','Tylko telefony','Brak ról','Brak tabel','Użytkownik wpływa na priorytety.'],
['Co zrobić z historią zmian w zgłoszeniu?','Pokazać z backendu jako dane','Liczyć w CSS','Trzymać w localStorage','Pominąć','Źródłem jest serwer.'],
['Jaki element dla tabeli zgłoszeń?','table z nagłówkami i sortowaniem przez aria-sort','Zagnieżdżone div','Canvas','Lista obrazków','Semantyka pomaga czytnikom.'],
['Jak ogłosić wynik filtrowania?','Region status z liczbą wyników','alert przy każdej literze','Przeniesienie fokusu','Nic','Spokojne ogłoszenie.'],
['Jakie zapytanie ma klucz listy?','Zawiera parametry filtra','Stały tekst bez parametrów','Losowy','Nazwa komponentu','Inaczej cache pomiesza wyniki.'],
['Jak zachować płynność przy zmianie strony?','placeholderData z poprzednią stroną','Pusty ekran','Reload','Blokada całej aplikacji','Tabela nie znika przy każdym kliknięciu.'],
['Co oznacza „oddanie z raportem CI”?','Link do przebiegu z wynikami','Plik PDF z życzeniami','Sam zrzut ekranu','Wiadomość tekstowa','Wynik można sprawdzić.'],
['Jaki jest główny cel projektu końcowego?','Samodzielne decyzje i ich uzasadnienie','Największa liczba bibliotek','Najkrótszy kod','Brak testów','To nowa umiejętność.'],
];
const header=String.raw`import {applyFilter,DEFAULT_QUERY,pageCount,parseIssueQuery,toggleSort,toSearchParams} from "./issueQuery";
import {canAccess,menuFor,type User} from "./auth";
import {isDirty,mapServerErrors,validateIssue} from "./issueForm";
import {headerState,summarizeBulk,toggleOne,togglePage} from "./bulk";
import {auditAdr,missingTopics} from "./adrAudit";
const p=(text:string)=>parseIssueQuery(new URLSearchParams(text));
const s=(over:object)=>toSearchParams({...DEFAULT_QUERY,...over}).toString()||"pusto";
const agent:User={id:1,name:"A",role:"AGENT"},admin:User={id:2,name:"B",role:"ADMIN"};
const base={title:"Abc",status:"OPEN" as const,assignee:null};
const list=(set:ReadonlySet<number>)=>[...set].join()||"pusto";`;
const probeRows=[
['Pusty adres: strona','p("").page',1,'Wartość domyślna.'],
['Pusty adres: sortowanie','p("").sort','createdAt','Wartość domyślna.',['id','title','status']],
['Pusty adres: kierunek','p("").dir','desc','Najnowsze najpierw.',['asc','brak','title']],
['Pusty adres: status','String(p("").status)','null','Bez filtra.',['OPEN','pusty tekst','undefined']],
['Status poprawny','p("status=CLOSED").status','CLOSED','Na liście.',['OPEN','null','IN_PROGRESS']],
['Status nieznany','String(p("status=X").status)','null','Odrzucony.',['X','OPEN','undefined']],
['Status małymi literami','String(p("status=open").status)','null','Wielkość liter ma znaczenie.',['open','OPEN','undefined']],
['Strona 3','p("page=3").page',3,'Poprawna liczba.'],
['Strona 0','p("page=0").page',1,'Poniżej zakresu.'],
['Strona ujemna','p("page=-5").page',1,'Poniżej zakresu.'],
['Strona ułamkowa','p("page=2.5").page',1,'Tylko całkowite.'],
['Strona tekstowa','p("page=abc").page',1,'NaN odrzucone.'],
['Sortowanie poprawne','p("sort=title").sort','title','Dozwolone pole.',['createdAt','id','status']],
['Sortowanie nieznane','p("sort=hack").sort','createdAt','Nie przekazujemy dowolnego pola do API.',['hack','id','title']],
['Kierunek asc','p("dir=asc").dir','asc','Dozwolony.',['desc','brak','title']],
['Kierunek nieznany','p("dir=x").dir','desc','Domyślny.',['asc','x','brak']],
['Tekst wyszukiwania','p("q=awaria").q','awaria','Zachowany.',['pusty tekst','q','brak']],
['Tekst z kodowaniem','p("q=a%20b").q','a b','URLSearchParams dekoduje.',['a%20b','a+b','ab']],
['Adres dla domyślnych','s({})','pusto','Krótki link bez wartości domyślnych.',['page=1','sort=createdAt','status=null']],
['Adres dla strony 2','s({page:2})','page=2','Tylko to, co inne.',['pusto','page=1','2']],
['Adres dla statusu','s({status:"OPEN"})','status=OPEN','Filtr w linku.',['pusto','OPEN','status=']],
['Strona po filtrze','applyFilter({...DEFAULT_QUERY,page:9},{q:"x"}).page',1,'Powrót na początek.'],
['Filtr zachowuje inne','applyFilter({...DEFAULT_QUERY,sort:"title"},{q:"x"}).sort','title','Sortowanie zostaje.',['createdAt','id','x']],
['Sortowanie nowe pole','toggleSort(DEFAULT_QUERY,"title").dir','asc','Nowe pole rosnąco.',['desc','brak','title']],
['Sortowanie drugi raz','toggleSort({...DEFAULT_QUERY,sort:"title",dir:"asc"},"title").dir','desc','Odwrócenie.',['asc','brak','title']],
['Strony dla 0','pageCount(0,10)',1,'Pusta lista też ma stronę.'],
['Strony dla 20','pageCount(20,10)',2,'Pełne strony.'],
['Strony dla 21','pageCount(21,10)',3,'Zaokrąglenie w górę.'],
['Dostęp bez użytkownika','canAccess(null)','login','Przekierowanie.',['ok','forbidden','brak']],
['Dostęp agenta do admina','canAccess(agent,"ADMIN")','forbidden','Rola nie pasuje.',['ok','login','brak']],
['Dostęp admina','canAccess(admin,"ADMIN")','ok','Rola pasuje.',['forbidden','login','brak']],
['Pozycje menu agenta','menuFor(agent).length',2,'Bez użytkowników.'],
['Pozycje menu admina','menuFor(admin).length',3,'Z użytkownikami.'],
['Walidacja tytułu ze spacjami','validateIssue({...base,title:"  a  "}).ok',false,'Po trim są mniej niż 3 znaki.'],
['Walidacja poprawna','validateIssue(base).ok',true,'Spełnia schemat.'],
['Błąd nieznanego pola serwera','mapServerErrors({fieldErrors:{x:"Zły"}}).general[0]','x: Zły','Nie może zniknąć.',['Zły','x','pusty tekst']],
['Czy formularz zmieniony?','isDirty(base,{...base,assignee:"Ala"})',true,'Inna wartość.'],
['Zaznacz pozycję','list(toggleOne(new Set(),1))','1','Dodanie.',['pusto','0','1,1']],
['Zaznacz całą stronę przy części','list(togglePage(new Set([1]),[1,2]))','1,2','Dopełnienie.',['pusto','1','2']],
['Stan nagłówka przy części','headerState(new Set([1]),[1,2])','some','Stan mieszany.',['all','none','brak']],
];
const project={
'src/issueQuery.ts':query,'src/IssuesList.tsx':issuesPage,'src/auth.tsx':auth,'src/issueForm.ts':form,'src/bulk.ts':bulk,'src/adrAudit.ts':adr,'src/capstone.test.tsx':tests,
'docs/adr/0003-stan-serwera.md':sampleAdr,'docs/router.example.txt':router,
'README.md':'Issue Admin: szkielet projektu końcowego. npm install, npm test. Tu są sprawdzone fragmenty najtrudniejszych reguł: filtry w adresie, role, schemat formularza, zaznaczanie i przegląd ADR. Całą aplikację z backendem, E2E, monitoringiem i wdrożeniem budujesz samodzielnie według briefu.\n',
};
const mutants=[
{file:'src/issueQuery.ts',before:'return {...query,...change,page:1};',after:'return {...query,...change};'},
{file:'src/issueQuery.ts',before:'Number.isInteger(page)&&page>=1?page:1',after:'page||1'},
{file:'src/issueQuery.ts',before:'oneOf(SORTABLE,params.get("sort"))??DEFAULT_QUERY.sort',after:'(params.get("sort") as SortField)??DEFAULT_QUERY.sort'},
{file:'src/issueQuery.ts',before:'Math.max(1,Math.ceil(total/size))',after:'Math.ceil(total/size)'},
{file:'src/IssuesList.tsx',before:'disabled={query.page<=1} ',after:''},
{file:'src/IssuesList.tsx',before:'{result.items.length===0?(\n        <p>Brak zgłoszeń dla tych filtrów.</p>\n      ):(',after:'{false?(\n        <p>Brak zgłoszeń dla tych filtrów.</p>\n      ):('},
{file:'src/auth.tsx',before:'if(role&&user.role!==role)return "forbidden" as const;',after:''},
{file:'src/auth.tsx',before:'...(user.role==="ADMIN"?[{to:"/admin/users",label:"Użytkownicy"}]:[]),',after:'{to:"/admin/users",label:"Użytkownicy"},'},
{file:'src/issueForm.ts',before:'.trim()',after:''},
{file:'src/issueForm.ts',before:'else general.push(field+": "+message);',after:''},
{file:'src/bulk.ts',before:'if(allSelected)next.delete(id);\n    else next.add(id);',after:'next.add(id);'},
{file:'src/adrAudit.ts',before:'<2)problems.push("mniej niż dwie opcje");',after:'<0)problems.push("mniej niż dwie opcje");'},
];
module.exports=lesson({exercises,concepts,probeRows,project,mutants,header});
