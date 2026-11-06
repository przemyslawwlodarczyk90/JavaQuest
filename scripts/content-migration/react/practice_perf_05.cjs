// Lekcja 16/05: lazy i Suspense. Ładowanie kodu na żądanie sprawdza React 19 i React Router 7 w Vitest (jsdom)
// — z kontrolowanymi obietnicami zamiast sieci oraz z prawdziwym dynamicznym import().
const reports=String.raw`// Ekran ładowany leniwie: eksport domyślny dla lazy(), loader dla route.lazy.
export default function ReportsPage({title="Raporty"}:{title?:string}){
  return <section><h1>{title}</h1><p>Wykres zgłoszeń</p></section>;
}
export async function reportsLoader(){return {total:42};}`;
const editor=String.raw`// Ciężki komponent z eksportem nazwanym — do lazy trzeba go zmapować na default.
export function RichEditor({value,onChange}:{value:string;onChange:(v:string)=>void}){
  return <textarea aria-label="Edytor opisu" value={value} onChange={e=>onChange(e.target.value)}/>;
}`;
const preload=String.raw`import {lazy,type ComponentType} from "react";
// Jedno pobranie na moduł: preload przy najechaniu i lazy przy renderze używają tej samej obietnicy.
export function preloadable<T extends ComponentType<any>>(load:()=>Promise<{default:T}>){
  let promise:Promise<{default:T}>|null=null;
  const preload=()=>{promise??=load();return promise;};
  return {preload,Component:lazy(preload)};
}
// Ponawianie importu, gdy pobranie chunka się nie udało (np. chwilowy brak sieci).
export function withRetry<T>(load:()=>Promise<T>,retries=2):()=>Promise<T>{
  return async()=>{
    let lastError:unknown;
    for(let attempt=0;attempt<=retries;attempt++){
      try{return await load();}catch(error){lastError=error;}
    }
    throw lastError;
  };
}`;
const section=String.raw`import {Suspense,useState} from "react";
import {preloadable} from "./preload";
type EditorModule={default:(props:{value:string;onChange:(v:string)=>void})=>React.JSX.Element};
// Fabryka, żeby test mógł wstrzyknąć kontrolowane ładowanie; lazy powstaje raz, poza renderem.
export function createDescriptionSection(load:()=>Promise<EditorModule>){
  const editor=preloadable(load);
  return function DescriptionSection({text}:{text:string}){
    const [editing,setEditing]=useState(false);
    const [value,setValue]=useState(text);
    return <section>
      {editing?(
        <Suspense fallback={<p aria-busy="true">Wczytywanie edytora…</p>}>
          <editor.Component value={value} onChange={setValue}/>
        </Suspense>
      ):(
        <>
          <p>{value}</p>
          <button type="button" onMouseEnter={()=>void editor.preload()} onFocus={()=>void editor.preload()} onClick={()=>setEditing(true)}>Edytuj</button>
        </>
      )}
    </section>;
  };
}
// Eksport nazwany zmapowany na default.
export const DescriptionSection=createDescriptionSection(()=>import("./RichEditor").then(m=>({default:m.RichEditor})));`;
const layout=String.raw`import {Component,Suspense,type ReactNode} from "react";
// Granica Suspense blisko leniwej części: nagłówek zostaje, placeholder tylko w miejscu treści.
export function Layout({children}:{children:ReactNode}){
  return <div>
    <header><strong>DevDesk</strong></header>
    <main>
      <ChunkErrorBoundary>
        <Suspense fallback={<p aria-busy="true">Ładowanie ekranu…</p>}>{children}</Suspense>
      </ChunkErrorBoundary>
    </main>
  </div>;
}
// Błąd pobrania chunka (np. po wdrożeniu nowej wersji) łapie granica błędu.
export class ChunkErrorBoundary extends Component<{children:ReactNode},{failed:boolean}>{
  state={failed:false};
  static getDerivedStateFromError(){return {failed:true};}
  render(){
    if(this.state.failed)return <p role="alert">Odśwież stronę — dostępna jest nowa wersja.</p>;
    return this.props.children;
  }
}`;
const router=String.raw`import {createMemoryRouter,Link,Outlet,useLoaderData} from "react-router";
// route.lazy: router pobiera kod trasy równolegle z danymi i nie potrzebuje osobnego Suspense.
export function Shell(){return <div><nav><Link to="/reports">Raporty</Link></nav><Outlet/></div>;}
export function ReportsRoute(){const data=useLoaderData() as {total:number};return <p>Zgłoszeń: {data.total}</p>;}
export function createAppRouter(loadReports:()=>Promise<{default:typeof ReportsRoute;reportsLoader:()=>Promise<{total:number}>}>){
  return createMemoryRouter([{
    path:"/",
    Component:Shell,
    children:[
      {index:true,element:<p>Pulpit</p>},
      {path:"reports",lazy:async()=>{const mod=await loadReports();return {Component:mod.default,loader:mod.reportsLoader};}},
    ],
  }],{initialEntries:["/"]});
}`;
const tests=String.raw`import {lazy,startTransition,Suspense,useState,type ComponentType} from "react";
import {act,fireEvent,render,screen} from "@testing-library/react";
import userEvent from "@testing-library/user-event";
import {RouterProvider} from "react-router";
import {expect,test,vi} from "vitest";
import {createDescriptionSection,DescriptionSection} from "./DescriptionSection";
import {Layout} from "./Layout";
import {preloadable,withRetry} from "./preload";
import {createAppRouter,ReportsRoute} from "./router";
function deferred<T>(){let resolve!:(value:T)=>void,reject!:(error:unknown)=>void;const promise=new Promise<T>((res,rej)=>{resolve=res;reject=rej;});return {promise,resolve,reject};}
test("prawdziwy dynamiczny import: lazy pokazuje fallback, potem ekran",async()=>{
  const Reports=lazy(()=>import("./pages/ReportsPage"));
  render(<Layout><Reports/></Layout>);
  expect(screen.getByText("Ładowanie ekranu…")).toBeInTheDocument();
  expect(await screen.findByRole("heading",{name:"Raporty"})).toBeInTheDocument();
  expect(screen.getByText("DevDesk")).toBeInTheDocument();
});
test("granica blisko treści: nagłówek zostaje podczas ładowania",async()=>{
  const chunk=deferred<{default:ComponentType}>();
  const Page=lazy(()=>chunk.promise);
  render(<Layout><Page/></Layout>);
  expect(screen.getByText("DevDesk")).toBeInTheDocument();
  expect(screen.getByText("Ładowanie ekranu…")).toBeInTheDocument();
  await act(async()=>{chunk.resolve({default:()=><p>Ustawienia</p>});await chunk.promise;});
  expect(screen.getByText("Ustawienia")).toBeInTheDocument();
  expect(screen.queryByText("Ładowanie ekranu…")).not.toBeInTheDocument();
});
test("błąd pobrania chunka pokazuje komunikat granicy błędu",async()=>{
  vi.spyOn(console,"error").mockImplementation(()=>{});
  const chunk=deferred<{default:ComponentType}>();
  const Page=lazy(()=>chunk.promise);
  render(<Layout><Page/></Layout>);
  await act(async()=>{chunk.reject(new Error("Failed to fetch dynamically imported module"));await chunk.promise.catch(()=>{});});
  expect(screen.getByRole("alert")).toHaveTextContent("Odśwież stronę");
  expect(screen.getByText("DevDesk")).toBeInTheDocument();
});
test("edytor: preload przy najechaniu, jedno pobranie, fallback po kliknięciu",async()=>{
  const chunk=deferred<{default:(p:{value:string;onChange:(v:string)=>void})=>React.JSX.Element}>();
  const load=vi.fn(()=>chunk.promise);
  const Section=createDescriptionSection(load);
  const user=userEvent.setup();
  render(<Section text="Błąd eksportu"/>);
  expect(load).not.toHaveBeenCalled();
  await user.hover(screen.getByRole("button",{name:"Edytuj"}));
  expect(load).toHaveBeenCalledTimes(1);
  fireEvent.focus(screen.getByRole("button",{name:"Edytuj"}));
  expect(load).toHaveBeenCalledTimes(1);
  await user.click(screen.getByRole("button",{name:"Edytuj"}));
  expect(screen.getByText("Wczytywanie edytora…")).toBeInTheDocument();
  await act(async()=>{chunk.resolve({default:({value})=><output>{value}</output>});await chunk.promise;});
  expect(screen.getByText("Błąd eksportu")).toBeInTheDocument();
  expect(load).toHaveBeenCalledTimes(1);
});
test("edytor z prawdziwym importem i eksportem nazwanym",async()=>{
  const user=userEvent.setup();
  render(<DescriptionSection text="Opis"/>);
  await user.click(screen.getByRole("button",{name:"Edytuj"}));
  const area=await screen.findByLabelText("Edytor opisu");
  await user.type(area,"!");
  expect(area).toHaveValue("Opis!");
});
test("preloadable: wielokrotny preload to jedno pobranie",async()=>{
  const load=vi.fn(async()=>({default:()=>null}));
  const {preload}=preloadable(load);
  await Promise.all([preload(),preload(),preload()]);
  expect(load).toHaveBeenCalledTimes(1);
});
test("withRetry ponawia nieudane pobranie i w końcu zgłasza błąd",async()=>{
  let attempts=0;
  const flaky=withRetry(async()=>{attempts+=1;if(attempts<3)throw new Error("sieć");return "moduł";},2);
  await expect(flaky()).resolves.toBe("moduł");
  const broken=withRetry(async()=>{throw new Error("404");},1);
  await expect(broken()).rejects.toThrow("404");
});
test("przejście zostawia stary ekran zamiast fallbacku",async()=>{
  const chunk=deferred<{default:ComponentType}>();
  const Admin=lazy(()=>chunk.promise);
  let go!:()=>void;
  function App(){const [screenName,setScreen]=useState("lista");go=()=>startTransition(()=>setScreen("admin"));
    return <Suspense fallback={<p>Ładowanie…</p>}>{screenName==="lista"?<p>Lista ticketów</p>:<Admin/>}</Suspense>;}
  render(<App/>);
  await act(async()=>{go();});
  expect(screen.getByText("Lista ticketów")).toBeInTheDocument();
  expect(screen.queryByText("Ładowanie…")).not.toBeInTheDocument();
  await act(async()=>{chunk.resolve({default:()=><p>Panel administracji</p>});await chunk.promise;});
  expect(screen.getByText("Panel administracji")).toBeInTheDocument();
});
test("route.lazy: kod trasy pobierany dopiero przy nawigacji, razem z loaderem",async()=>{
  const loadReports=vi.fn(async()=>({default:ReportsRoute,reportsLoader:async()=>({total:42})}));
  const router=createAppRouter(loadReports);
  const user=userEvent.setup();
  render(<RouterProvider router={router}/>);
  expect(screen.getByText("Pulpit")).toBeInTheDocument();
  expect(loadReports).not.toHaveBeenCalled();
  await user.click(screen.getByRole("link",{name:"Raporty"}));
  expect(await screen.findByText("Zgłoszeń: 42")).toBeInTheDocument();
  expect(loadReports).toHaveBeenCalledTimes(1);
});`;
const header=String.raw`import {act,lazy,startTransition,Suspense,useEffect,useState,type ComponentType,type ReactNode} from "react";
import {createRoot} from "react-dom/client";
const log:string[]=[];
async function mount(ui:ReactNode){const host=document.createElement("div");document.body.append(host);const root=createRoot(host);await act(async()=>root.render(ui));return {host,root,update:async(next:ReactNode)=>{await act(async()=>root.render(next));}};}
function deferred<T>(){let resolve!:(value:T)=>void,reject!:(error:unknown)=>void;const promise=new Promise<T>((res,rej)=>{resolve=res;reject=rej;});return {promise,resolve,reject};}
type Mod={default:ComponentType};
void lazy;void startTransition;void Suspense;void useEffect;void useState;void log;void deferred;
`;
const exercises=[
  ['Utwórz leniwy ekran raportów przez lazy i dynamiczny import.','Moduł z eksportem domyślnym.',String.raw`import {lazy,Suspense} from "react";
const ReportsPage=lazy(()=>import("./pages/ReportsPage")); // na poziomie modułu, nie w komponencie
export function ReportsScreen(){
  return <Suspense fallback={<p aria-busy="true">Ładowanie raportów…</p>}><ReportsPage/></Suspense>;
}`],
  ['Przygotuj moduł ekranu z eksportem domyślnym i loaderem.','default dla lazy, reportsLoader dla routera.',reports],
  ['Zmapuj eksport nazwany na default dla lazy.','.then(m => ({ default: m.RichEditor })).',String.raw`import {lazy} from "react";
export const RichEditor=lazy(()=>import("./RichEditor").then(m=>({default:m.RichEditor})));`],
  ['Umieść granicę Suspense blisko treści, żeby nagłówek został.','Layout z Suspense w main.',layout],
  ['Dodaj granicę błędu dla nieudanego pobrania chunka.','getDerivedStateFromError i komunikat o nowej wersji.',layout],
  ['Sprawdź testem prawdziwy dynamiczny import ekranu.','findByRole czeka na załadowanie.',tests],
  ['Sprawdź testem kontrolowaną obietnicą, że nagłówek zostaje podczas ładowania.','deferred() zamiast sieci.',tests],
  ['Sprawdź testem komunikat po odrzuceniu importu.','Wycisz console.error.',tests],
  ['Napisz preloadable: jedna obietnica dla preload i lazy.','promise ??= load().',preload],
  ['Załaduj edytor po kliknięciu i pobierz go wstępnie przy najechaniu lub fokusie.','onMouseEnter i onFocus wywołują preload.',section],
  ['Sprawdź testem, że najechanie i fokus pobierają edytor raz.','vi.fn liczy wywołania load.',tests],
  ['Sprawdź testem edytor z prawdziwym importem i wpisywaniem.','findByLabelText.',tests],
  ['Napisz withRetry ponawiający nieudany import.','Pętla prób, na końcu rzuć ostatni błąd.',preload],
  ['Przetestuj withRetry dla sukcesu po dwóch błędach i dla trwałego błędu.','resolves / rejects.',tests],
  ['Pokaż, że przejście zostawia stary ekran zamiast fallbacku.','startTransition przy zmianie ekranu.',tests],
  ['Skonfiguruj trasę z route.lazy zwracającą Component i loader.','Router pobiera kod równolegle z danymi.',router],
  ['Sprawdź testem, że kod trasy pobiera się dopiero przy nawigacji.','createMemoryRouter i RouterProvider.',tests],
  ['Wskaż błąd: lazy wywołane wewnątrz komponentu.','Nowy komponent przy każdym renderze resetuje stan.',String.raw`import {lazy,Suspense} from "react";
// ŹLE: const Chart = lazy(() => import("./Chart")) wewnątrz komponentu — przy każdym renderze nowy typ,
// więc React odmontowuje i montuje wykres od nowa (stan znika, import wywoływany ponownie).
const Chart=lazy(()=>import("./pages/ReportsPage")); // DOBRZE: raz, na poziomie modułu
export function Dashboard(){return <Suspense fallback={<p>…</p>}><Chart/></Suspense>;}`],
  ['Rozdziel ekrany DevDesk: raporty, administracja i ustawienia jako leniwe trasy.','Główna ścieżka zostaje w pakiecie startowym.',String.raw`import {createBrowserRouter} from "react-router";
export const router=createBrowserRouter([
  {path:"/",lazy:async()=>({Component:(await import("./pages/ReportsPage")).default})},
  // {path:"/admin",lazy:()=>import("./pages/AdminPage").then(m=>({Component:m.default}))},
  // {path:"/settings",lazy:()=>import("./pages/SettingsPage").then(m=>({Component:m.default}))},
]);`],
  ['Zanotuj rozmiar pliku startowego przed i po podziale.','npm run build pokazuje rozmiary chunków.',String.raw`// Przed: dist/assets/index-*.js  612 kB (gzip 190 kB)
// Po:    index-*.js 340 kB + ReportsPage-*.js 180 kB + AdminPage-*.js 70 kB + SettingsPage-*.js 25 kB
// Weryfikacja: DevTools → Network — chunk raportów pobierany dopiero przy wejściu na /reports.
export {};`],
  ['Wyjaśnij, czym route.lazy różni się od React.lazy.','Kod i dane równolegle, bez Suspense.',String.raw`// route.lazy: router pobiera moduł trasy RÓWNOLEGLE z loaderem i czeka na oba przed pokazaniem ekranu.
// React.lazy + Suspense: kod pobierany dopiero przy renderze komponentu; dobre dla ciężkich części ekranu.
// Całe ekrany → route.lazy; edytor, wykres, mapa wewnątrz ekranu → React.lazy.
export {};`],
  ['Pokaż kaskadę ładowania przy zbyt drobnym podziale.','A ładuje się, renderuje B, dopiero wtedy B zaczyna się pobierać.',String.raw`// ŹLE: lazy(() => import("./Badge")), lazy(() => import("./Button")) …
// Strona → (pobranie) Panel → render → (pobranie) Badge → render → (pobranie) Tooltip — trzy kolejne żądania.
// DOBRZE: dziel na trasach i ciężkich, rzadko używanych funkcjach; drobne komponenty zostają w pakiecie.
export {};`],
  ['Dodaj komunikat „nowa wersja” z przyciskiem odświeżenia.','window.location.reload w handlerze.',String.raw`export function NewVersionNotice(){
  return <div role="alert">
    <p>Dostępna jest nowa wersja aplikacji.</p>
    <button type="button" onClick={()=>window.location.reload()}>Odśwież stronę</button>
  </div>;
}`],
  ['Pobierz wstępnie kod trasy przy najechaniu na link.','Wywołaj tę samą funkcję importu.',String.raw`import {Link} from "react-router";
const loadReports=()=>import("./pages/ReportsPage"); // przeglądarka zapamięta moduł po pierwszym pobraniu
export function ReportsLink(){
  return <Link to="/reports" onMouseEnter={()=>void loadReports()} onFocus={()=>void loadReports()}>Raporty</Link>;
}`],
  ['Pokaż typ komponentu zwracanego przez lazy w TSX.','LazyExoticComponent zachowuje props.',String.raw`import {lazy,type LazyExoticComponent} from "react";
import type ReportsPage from "./pages/ReportsPage";
export const LazyReports:LazyExoticComponent<typeof ReportsPage>=lazy(()=>import("./pages/ReportsPage"));
// <LazyReports title="Q4" /> — props sprawdzane jak dla zwykłego komponentu`],
  ['Pokaż, jak TSX wykrywa literówkę w nazwie eksportu przy mapowaniu.','m.RichEditr to błąd kompilacji.',String.raw`import {lazy} from "react";
// lazy(() => import("./RichEditor").then(m => ({ default: m.RichEditr }))) — błąd TS: brak eksportu RichEditr.
export const Editor=lazy(()=>import("./RichEditor").then(m=>({default:m.RichEditor})));`],
  ['Zagnieźdź granice Suspense: osobny placeholder dla wykresu wewnątrz ekranu.','Wewnętrzna granica nie zasłania reszty ekranu.',String.raw`import {lazy,Suspense} from "react";
const Chart=lazy(()=>import("./pages/ReportsPage"));
export function ReportScreen(){
  return <section>
    <h1>Raporty</h1>
    <p>Podsumowanie widoczne od razu</p>
    <Suspense fallback={<p aria-busy="true">Ładowanie wykresu…</p>}><Chart/></Suspense>
  </section>;
}`],
  ['Zastąp spinner na całą stronę placeholderem o rozmiarze ładowanej części.','Mniej przeskoków układu (CLS).',String.raw`export function ChartSkeleton(){
  // ta sama wysokość co wykres — strona nie „skacze” po załadowaniu (lekcja 9: CLS)
  return <div aria-busy="true" style={{height:320,borderRadius:8,background:"#eee"}}/>;
}`],
  ['Wyjaśnij, dlaczego lazy musi być wywołane na poziomie modułu.','Tożsamość typu komponentu.',String.raw`// lazy() zwraca nowy typ komponentu. Wywołane w renderze daje przy każdym renderze inny typ,
// więc React traktuje go jak inny komponent: odmontowuje stary (stan znika) i montuje nowy (ponowny import i fallback).
export {};`],
  ['Zapisz kryterium akceptacji podziału kodu DevDesk.','Osobne pliki, mniejszy start, edytor z preload.',String.raw`// 1. Raporty, administracja i ustawienia jako osobne chunki (build + Network).
// 2. Plik startowy mniejszy niż przed zmianą — rozmiary zapisane.
// 3. Edytor opisu ładuje się po kliknięciu, z preload przy najechaniu i fokusie.
// 4. Błąd pobrania chunka pokazuje komunikat o nowej wersji.
export {};`],
];
const quiz=[];function q(...row){quiz.push(row);}
q('Co robi lazy(() => import("./ReportsPage"))?','Tworzy komponent, którego kod pobierany jest przy pierwszym renderze','Pobiera kod od razu przy starcie','Tworzy kopię komponentu','Opóźnia render o sekundę','Vite tworzy osobny chunk dla modułu.');
q('Co tworzy Vite dla dynamicznego import()?','Osobny plik (chunk)','Nic','Plik CSS','Service workera','Pobierany dopiero na żądanie.');
q('Jakiego eksportu oczekuje lazy?','Eksportu domyślnego z komponentem','Dowolnego nazwanego','Tablicy komponentów','Obiektu props','Nazwany mapujesz przez .then.');
q('Jak użyć z lazy modułu z eksportem nazwanym?','import("./X").then(m => ({ default: m.X }))','lazy(m => m.X)','Nie da się','import { X } from lazy','Mapowanie na default.');
q('Co robi <Suspense fallback={...}>?','Pokazuje fallback, dopóki komponenty w środku czekają','Łapie błędy','Opóźnia efekty','Pobiera dane','W React 19 także dane przez use.');
q('Gdzie wywołać lazy?','Na poziomie modułu','W ciele komponentu','W useEffect','W handlerze','Inaczej przy każdym renderze powstaje nowy komponent.');
q('Co się stanie, gdy lazy wywołasz w ciele komponentu?','Każdy render tworzy nowy typ — stan się resetuje, import wywoływany ponownie','Nic','Szybsze ładowanie','Błąd kompilacji','Inny typ = inny komponent.');
q('Kiedy lazy wywołuje funkcję ładującą?','Przy pierwszym renderze komponentu','Przy imporcie pliku','Przy starcie aplikacji','Nigdy','Utworzenie lazy niczego nie pobiera.');
q('Ile razy lazy wywoła loader przy wielu renderach tego samego komponentu?','Raz','Przy każdym renderze','Dwa razy','Zero','Wynik jest zapamiętany w obiekcie lazy.');
q('Jaki typ TS ma komponent z lazy?','LazyExoticComponent z props oryginału','any','ComponentType<unknown>','Promise<Component>','Props są sprawdzane.');
q('Co TSX wykrywa w dynamicznym imporcie?','Literówki w nazwie eksportu i brak eksportu domyślnego','Błędy sieci','Rozmiar chunka','Czas ładowania','import() ma typ modułu.');
q('Kiedy w JSX ujawni się literówka w nazwie eksportu?','Dopiero przy pierwszym wejściu na ekran','Przy kompilacji','Przy instalacji','Nigdy','Łatwo przeoczyć rzadko odwiedzany ekran.');
q('Co zwraca route.lazy w React Router?','Obietnicę z polami trasy (Component, loader, action, ErrorBoundary)','Komponent','Element JSX','Ścieżkę','Router łączy je z trasą.');
q('Jaka jest przewaga route.lazy nad React.lazy dla ekranów?','Kod trasy pobierany równolegle z danymi loadera','Mniejszy kod','Brak potrzeby buildu','Działa bez routera','I nie potrzeba osobnego Suspense.');
q('Do czego najlepiej nadaje się React.lazy z Suspense?','Do ciężkich komponentów wewnątrz ekranu (edytor, wykres, mapa)','Do każdego przycisku','Do stylów','Do danych','Ekrany — route.lazy.');
q('Kiedy route.lazy wywołuje funkcję ładującą?','Przy nawigacji na trasę','Przy starcie aplikacji','Przy renderze linku','Nigdy','Test lekcji to potwierdza.');
q('Co daje preload przy najechaniu na przycisk „Edytuj”?','Po kliknięciu kod zwykle już jest pobrany','Edytor otwiera się bez kliknięcia','Zwiększa bundle','Nic','Krótszy lub brak fallbacku.');
q('Dlaczego preload wywołuje się też przy fokusie?','Użytkownicy klawiatury nie najeżdżają myszą','Bo hover nie działa','Bo tak wymaga React','Bez powodu','Dostępność i ta sama korzyść.');
q('Po co preloadable przechowuje jedną obietnicę?','Żeby preload i lazy korzystały z jednego pobrania','Żeby blokować lazy','Żeby buforować props','Bez powodu','promise ??= load().');
q('Czy przeglądarka pobierze ten sam moduł dwa razy przy dwóch import()?','Nie — moduł jest zapamiętany po pierwszym pobraniu','Tak, zawsze','Tylko w dev','Tylko bez cache','Ale własna funkcja ładująca w teście zostanie wywołana dwa razy.');
q('Co dzieje się z jedną granicą Suspense wokół całej aplikacji?','Każde ładowanie zastępuje cały ekran spinnerem','Nic złego','Ładowanie jest szybsze','Błędy są łapane','Lepiej granice bliżej leniwych części.');
q('Gdzie umieścić granicę Suspense dla ekranu?','Wokół treści, tak by nagłówek i menu zostały','Wokół <html>','Wokół każdego przycisku','Nigdzie','Placeholder tylko w miejscu ładowanej części.');
q('Co React robi przy przejściu (startTransition) do leniwego ekranu?','Może zostawić stary ekran zamiast pokazywać fallback','Zawsze pokazuje fallback','Przerywa ładowanie','Rzuca błąd','Zwykle lepsze dla użytkownika.');
q('Co łapie błąd pobrania chunka?','Granica błędu (Error Boundary)','Suspense','try/catch w JSX','useEffect','Rozdział 19.');
q('Kiedy typowo zdarza się błąd pobrania chunka?','Po wdrożeniu nowej wersji, gdy stary plik zniknął z serwera','Przy każdym renderze','Przy błędzie TS','Nigdy','Pokaż „Odśwież stronę”.');
q('Jak komponent klasowy granicy błędu przełącza się na komunikat?','static getDerivedStateFromError zwraca nowy stan','componentDidMount','useErrorBoundary','try/catch w render','Komponenty funkcyjne nie mają odpowiednika.');
q('Po co withRetry przy imporcie?','Żeby ponowić pobranie po chwilowym błędzie sieci','Żeby zwiększyć bundle','Żeby ominąć Suspense','Bez powodu','Po wyczerpaniu prób błąd trafia do granicy błędu.');
q('Co jest błędem „dzielenia wszystkiego”?','Dziesiątki małych żądań i kaskada ładowania','Mniejszy bundle','Szybszy start','Brak błędu','Komponent A ładuje się, potem B…');
q('Na jakich granicach dzielić kod?','Trasy i ciężkie, rzadko używane funkcje','Każdy komponent','Każdy plik CSS','Każdy hook','Główna ścieżka zostaje w pakiecie startowym.');
q('Czym jest kaskada ładowania?','Kolejne leniwe części zaczynają się pobierać dopiero po renderze poprzednich','Równoległe pobieranie','Cache przeglądarki','Lazy w routerze','Sumuje opóźnienia.');
q('Jak zweryfikować, że ekran ładuje się osobno?','Build pokazuje osobny plik, a Network — pobranie przy wejściu','Console.log w komponencie','Profiler Reacta','Lighthouse zawsze','Kryterium akceptacji.');
q('Co pokaże Suspense bez propsa fallback podczas ładowania?','Nic (pusty fragment)','Spinner domyślny','Błąd','Poprzedni ekran zawsze','fallback domyślnie to brak treści.');
q('Czy dwa leniwe komponenty w jednej granicy pokazują treść osobno?','Nie — granica czeka na oba','Tak','Tylko w produkcji','Tylko z SuspenseList','Zagnieźdź granice, żeby pokazywać części osobno.');
q('Co daje zagnieżdżona granica Suspense dla wykresu?','Reszta ekranu jest widoczna, placeholder tylko w miejscu wykresu','Nic','Szybsze ładowanie wykresu','Brak fallbacku','Granica najbliższa leniwemu komponentowi wygrywa.');
q('Która granica Suspense obsługuje zawieszony komponent?','Najbliższa nad nim w drzewie','Najwyższa','Pierwsza w pliku','Wszystkie naraz','Jak w przypadku granic błędów.');
q('Kiedy uruchamiają się efekty leniwego komponentu?','Po załadowaniu i commicie jego treści','Przed pobraniem kodu','Razem z fallbackiem','Nigdy','Efekty należą do commitu.');
q('Czy po pierwszym załadowaniu ponowne wyświetlenie leniwego komponentu pokazuje fallback?','Nie — kod jest już dostępny','Tak, zawsze','Tylko w dev','Tylko z route.lazy','Lazy zapamiętuje wynik.');
q('Czym jest $$typeof komponentu z lazy?','Symbol.for("react.lazy")','Symbol.for("react.element")','"function"','undefined','React rozpoznaje leniwe typy.');
q('Jaki placeholder poprawia CLS?','O wymiarach ładowanej części (szkielet)','Spinner na całą stronę','Brak placeholdera','Biały ekran','Strona nie „skacze”.');
q('Co trzeba zrobić, by Suspense działał z route.lazy?','Nic — router obsługuje ładowanie sam','Owinąć RouterProvider w Suspense','Dodać lazy do każdego linku','Wyłączyć loader','Router czeka na kod i dane.');
q('Co zwraca dynamiczny import()?','Obietnicę z obiektem modułu','Komponent','Plik JS jako tekst','Nic','Pola modułu to jego eksporty.');
q('Czy można użyć lazy dla hooka?','Nie — lazy dotyczy komponentów','Tak','Tylko w TSX','Tylko z use','Logikę ładuj dynamicznym importem w handlerze.');
q('Jak załadować ciężką bibliotekę tylko przy eksporcie PDF?','Dynamiczny import w handlerze kliknięcia','lazy na bibliotece','Statyczny import na górze pliku','Nie da się','const { jsPDF } = await import("jspdf").');
q('Co oznacza aria-busy="true" w placeholderze?','Informuje technologie asystujące, że treść się ładuje','Ukrywa element','Blokuje klik','Nic','Dostępny placeholder.');
q('Czy lazy działa z SSR?','Tak, w React 19 z renderowaniem strumieniowym i Suspense','Nie, nigdy','Tylko w Next.js 12','Tylko w CSR','Szczegóły w rozdziale 18.');
q('Co przekazuje się do lazy?','Funkcję zwracającą obietnicę modułu','Ścieżkę jako tekst','Komponent','Obietnicę','Funkcja wywoływana leniwie.');
q('Dlaczego do lazy nie przekazuje się gotowej obietnicy import("./X")?','Wtedy pobranie zaczęłoby się od razu przy wczytaniu modułu','Bo to błąd składni','Bo obietnica jest za duża','Można','Funkcja opóźnia pobranie.');
q('Co jest efektem dobrego podziału kodu?','Mniejszy plik startowy i szybsze pierwsze wyświetlenie','Więcej żądań przy starcie','Wolniejsza nawigacja zawsze','Brak zmian','Rzadkie ekrany ładują się później.');
q('Jaki koszt ma leniwe ekrany?','Krótkie ładowanie przy pierwszym wejściu','Większy plik startowy','Brak obsługi błędów','Utrata stanu globalnego','Łagodzi go preload.');
q('Kiedy preload może zaszkodzić?','Gdy pobierasz wiele dużych modułów, których użytkownik nie użyje (np. na urządzeniach mobilnych)','Nigdy','Zawsze','Tylko w dev','Preloaduj przy sygnale zamiaru (hover, fokus).');
q('Jakie zdarzenie sygnalizuje zamiar kliknięcia?','Najechanie lub fokus','Scroll','Resize','Load','Typowy moment na preload.');
q('Co robi test „przejście zostawia stary ekran”?','Zmienia ekran w startTransition i sprawdza brak fallbacku','Mierzy czas','Sprawdza CSS','Sprawdza sieć','Stary widok zostaje do załadowania nowego.');
q('Czy route.lazy można łączyć z prefetch linków?','Tak, React Router potrafi pobierać trasy wstępnie','Nie','Tylko w Next.js','Tylko bez loaderów','Np. przy najechaniu na link.');
q('Co trzeba zwrócić z route.lazy, aby trasa miała dane?','Pole loader obok Component','Pole data','Element','Nic','Router uruchomi loader przed renderem.');
q('Dlaczego test lekcji używa deferred() zamiast prawdziwej sieci?','Kontroluje moment załadowania i sprawdza fallback deterministycznie','Bo jsdom nie ma import()','Bo sieć jest szybsza','Bez powodu','Prawdziwy import też jest sprawdzany osobno.');
q('Czy Vitest obsługuje prawdziwy import() leniwych modułów?','Tak — Vite przetwarza dynamiczne importy','Nie','Tylko w Node 12','Tylko w produkcji','Test „prawdziwy dynamiczny import” to pokazuje.');
q('Jak nazywa się zjawisko przeskakiwania treści po załadowaniu?','CLS (Cumulative Layout Shift)','INP','LCP','TTFB','Lekcja 9.');
q('Co zrobić z błędem „Failed to fetch dynamically imported module”?','Złapać w granicy błędu i zaproponować odświeżenie','Zignorować','Przepisać na statyczny import','Wyłączyć Vite','Zwykle po wdrożeniu.');
q('Czy granica błędu łapie błędy w handlerach zdarzeń?','Nie — tylko w renderze, w tym zawieszone ładowanie','Tak','Tylko w klasach','Tylko w TSX','Handler obsługuje błędy sam.');
q('Gdzie zapisać rozmiary bundla przed i po?','W notatce lub README z wynikiem npm run build','W konsoli przeglądarki','W localStorage','Nigdzie','Kryterium akceptacji.');
q('Co pokazuje widok Network dla leniwego ekranu?','Pobranie osobnego pliku JS w chwili wejścia na ekran','Wszystkie pliki przy starcie','Tylko CSS','Brak pobrań','Potwierdzenie podziału.');
q('Czy można przekazać props do komponentu z lazy?','Tak, jak do zwykłego komponentu','Nie','Tylko children','Tylko w TSX','LazyExoticComponent przekazuje props dalej.');
q('Czy lazy wspiera ref?','Tak, ref jest przekazywany do komponentu docelowego (w React 19 ref to zwykły prop)','Nie','Tylko w klasach','Tylko forwardRef','Jak w zwykłym komponencie.');
q('Co jest typowym fallbackiem dla ekranu?','Szkielet układu ekranu lub krótki komunikat z aria-busy','Pusty biały ekran','alert()','Przekierowanie','Zachowuje kontekst dla użytkownika.');
q('Dlaczego nie dzielić głównej ścieżki użytkownika?','Każde leniwe ładowanie na głównej ścieżce dodaje opóźnienie najczęstszej interakcji','Bo jest za mała','Bo lazy jej nie obsługuje','Można bez kosztów','Startowy pakiet powinien ją zawierać.');
q('Co zrobi React, gdy leniwy moduł nie ma eksportu default?','Zgłosi błąd renderu (niepoprawny typ elementu)','Wyrenderuje pusty element','Weźmie pierwszy eksport','Ponowi ładowanie','TSX wykryje to wcześniej.');
q('Ile razy zostanie wywołany loader, gdy dwa różne lazy używają tej samej funkcji ładującej?','Dwa razy — każdy lazy wywołuje ją sam','Raz','Zero','Trzy','preloadable rozwiązuje to jedną obietnicą.');
q('Czy utworzenie lazy bez renderu pobiera kod?','Nie','Tak','Tylko w produkcji','Tylko w TSX','Pobranie przy pierwszym renderze.');
q('Jak React Router wyświetla stan nawigacji w trakcie ładowania trasy?','useNavigation() zwraca state "loading"','Przez Suspense fallback','Przez useLoaderData','Nie wyświetla','Można pokazać pasek postępu.');
q('Co się stanie z fallbackiem, gdy chunk załaduje się bardzo szybko?','Może mignąć — przejścia lub preload temu zapobiegają','Nic, nigdy nie miga','Zostanie na zawsze','Rzuci błąd','Stąd preload i startTransition.');
q('Co sprawdza test route.lazy w lekcji?','Brak pobrania przed nawigacją i jedno pobranie po kliknięciu linku','Rozmiar bundla','Czas ładowania','Kolor linku','vi.fn na funkcji ładującej.');
q('Czy lazy wymaga Reacta 19?','Nie — istnieje od React 16.6','Tak','Tylko z kompilatorem','Tylko w Next.js','W React 19 Suspense obsługuje też dane.');
q('Czy route.lazy wymaga eksportu domyślnego w module?','Nie — sam wybierasz pola i nazwy eksportów','Tak','Tylko dla loadera','Tylko w TSX','Zwracasz obiekt z polami trasy.');
q('Jaki jest główny cel lekcji?','Pobierać kod dopiero wtedy, gdy użytkownik go potrzebuje','Usunąć Suspense','Przyspieszyć render','Zmniejszyć liczbę komponentów','Mniejszy start, sensowne granice.');
q('Co jest wspólne dla Suspense przy kodzie i danych w React 19?','Ten sam mechanizm zawieszania i fallback','Nic','Tylko nazwa','Oba wymagają klasy','Dane przez use — rozdział 17.');
q('Jak oznaczyć w teście, że placeholder zniknął?','queryByText(...) i not.toBeInTheDocument()','getByText rzuca zawsze','toHaveFocus','toBeVisible na fallbacku','queryBy zwraca null.');
q('Co robi findByRole w teście leniwego ekranu?','Czeka asynchronicznie, aż element się pojawi','Rzuca od razu','Pobiera moduł','Mierzy czas','Domyślny limit 1000 ms.');
q('Dlaczego Layout ma granicę błędu wewnątrz main, a nie wokół całej aplikacji?','Nagłówek i nawigacja zostają widoczne przy błędzie ekranu','Bo tak wymaga React','Bo klasa nie może być wyżej','Bez powodu','Użytkownik może przejść gdzie indziej.');
const probes=[
  ['Treść przed załadowaniem kodu','Ładowanie…','Raport','pusty','błąd',String.raw`const chunk=deferred<Mod>();const Page=lazy(()=>chunk.promise);const v=await mount(<Suspense fallback={<p>Ładowanie…</p>}><Page/></Suspense>);console.log(v.host.textContent||"pusty");`],
  ['Treść po załadowaniu kodu','Raport','Ładowanie…','pusty','błąd',String.raw`const chunk=deferred<Mod>();const Page=lazy(()=>chunk.promise);const v=await mount(<Suspense fallback={<p>Ładowanie…</p>}><Page/></Suspense>);await act(async()=>{chunk.resolve({default:()=><p>Raport</p>});await chunk.promise;});console.log(v.host.textContent);`],
  ['Wywołania loadera przy dwóch renderach tego samego lazy','1','2','0','3',String.raw`let calls=0;const Page=lazy(async()=>{calls+=1;return {default:({n}:{n:number})=><p>{n}</p>};});const v=await mount(<Suspense fallback={null}><Page n={1}/></Suspense>);await v.update(<Suspense fallback={null}><Page n={2}/></Suspense>);console.log(calls);`],
  ['Wywołania loadera, gdy lazy powstaje w renderze','2','1','0','3',String.raw`let calls=0;function Host({n}:{n:number}){const Page=lazy(async()=>{calls+=1;return {default:()=><p>{n}</p>};});return <Suspense fallback={null}><Page/></Suspense>;}const v=await mount(<Host n={1}/>);await v.update(<Host n={2}/>);console.log(calls);`],
  ['Utworzenie lazy bez renderu','0','1','2','błąd',String.raw`let calls=0;const Page=lazy(async()=>{calls+=1;return {default:()=>null};});void Page;await Promise.resolve();console.log(calls);`],
  ['Dwa lazy w jednej granicy, załadowany tylko pierwszy','Ładowanie…','A','AB','A|Ładowanie…',String.raw`const a=deferred<Mod>(),b=deferred<Mod>();const A=lazy(()=>a.promise),B=lazy(()=>b.promise);const v=await mount(<Suspense fallback={<p>Ładowanie…</p>}><A/><B/></Suspense>);await act(async()=>{a.resolve({default:()=><p>A</p>});await a.promise;});console.log(v.host.textContent);`],
  ['Zagnieżdżona granica: nagłówek i placeholder wykresu','Raporty|Ładowanie wykresu…','Ładowanie…','Raporty','Ładowanie wykresu…',String.raw`const chart=deferred<Mod>();const Chart=lazy(()=>chart.promise);const v=await mount(<Suspense fallback={<p>Ładowanie…</p>}><h1>Raporty</h1><Suspense fallback={<p>Ładowanie wykresu…</p>}><Chart/></Suspense></Suspense>);console.log([...v.host.children].map(e=>e.textContent).join("|"));`],
  ['Jedna granica wysoko: co widać podczas ładowania wykresu','Ładowanie…','Raporty|Ładowanie…','Raporty','pusty',String.raw`const chart=deferred<Mod>();const Chart=lazy(()=>chart.promise);const v=await mount(<Suspense fallback={<p>Ładowanie…</p>}><h1>Raporty</h1><Chart/></Suspense>);console.log([...v.host.children].filter(e=>getComputedStyle(e).display!=="none").map(e=>e.textContent).join("|")||"pusty");`],
  ['Eksport nazwany zmapowany na default','Edytor','Ładowanie…','pusty','błąd',String.raw`const Editor=lazy(()=>Promise.resolve({RichEditor:()=><p>Edytor</p>}).then(m=>({default:m.RichEditor})));const v=await mount(<Suspense fallback={<p>Ładowanie…</p>}><Editor/></Suspense>);console.log(v.host.textContent);`],
  ['Moduł bez eksportu default','błąd','pusty','Ładowanie…','undefined',String.raw`const Broken=lazy(()=>Promise.resolve({} as Mod));const original=console.error;console.error=()=>{};let result="pusty";try{const host=document.createElement("div");const root=createRoot(host,{onUncaughtError:()=>{result="błąd";}});await act(async()=>root.render(<Suspense fallback={<p>Ładowanie…</p>}><Broken/></Suspense>));if(result!=="błąd")result=host.textContent||"pusty";}catch{result="błąd";}finally{console.error=original;}console.log(result);`],
  ['Przejście do leniwego ekranu: co widać w trakcie','Lista','Ładowanie…','pusty','Admin',String.raw`const chunk=deferred<Mod>();const Admin=lazy(()=>chunk.promise);let go!:()=>void;function App(){const [s,setS]=useState("lista");go=()=>startTransition(()=>setS("admin"));return <Suspense fallback={<p>Ładowanie…</p>}>{s==="lista"?<p>Lista</p>:<Admin/>}</Suspense>;}const v=await mount(<App/>);await act(async()=>{go();});console.log([...v.host.children].filter(e=>getComputedStyle(e).display!=="none").map(e=>e.textContent).join("|")||"pusty");`],
  ['Zwykła aktualizacja do leniwego ekranu: co widać w trakcie','Ładowanie…','Lista','pusty','Admin',String.raw`const chunk=deferred<Mod>();const Admin=lazy(()=>chunk.promise);let go!:()=>void;function App(){const [s,setS]=useState("lista");go=()=>setS("admin");return <Suspense fallback={<p>Ładowanie…</p>}>{s==="lista"?<p>Lista</p>:<Admin/>}</Suspense>;}const v=await mount(<App/>);await act(async()=>{go();});console.log([...v.host.children].filter(e=>getComputedStyle(e).display!=="none").map(e=>e.textContent).join("|")||"pusty");`],
  ['Jedna obietnica dla wielu preload','1','3','0','2',String.raw`let calls=0;let promise:Promise<Mod>|null=null;const load=async():Promise<Mod>=>{calls+=1;return {default:()=>null};};const preload=()=>{promise??=load();return promise;};await Promise.all([preload(),preload(),preload()]);console.log(calls);`],
  ['Dwa lazy z tą samą funkcją ładującą','2','1','0','3',String.raw`let calls=0;const load=async():Promise<Mod>=>{calls+=1;return {default:()=><i>x</i>};};const A=lazy(load),B=lazy(load);await mount(<Suspense fallback={null}><A/><B/></Suspense>);console.log(calls);`],
  ['Typ wartości zwróconej przez lazy','object','function','string','undefined',String.raw`const Page=lazy(async()=>({default:()=>null}));console.log(typeof Page);`],
  ['$$typeof komponentu z lazy','true','false','undefined','błąd',String.raw`const Page=lazy(async()=>({default:()=>null}));console.log(String((Page as unknown as {$$typeof:symbol}).$$typeof===Symbol.for("react.lazy")));`],
  ['Ponowienie po jednym błędzie pobrania','Raport','Ładowanie…','błąd','pusty',String.raw`let attempts=0;const load=async():Promise<Mod>=>{attempts+=1;if(attempts===1)throw new Error("sieć");return {default:()=><p>Raport</p>};};const retry=async()=>{try{return await load();}catch{return await load();}};const Page=lazy(retry);const v=await mount(<Suspense fallback={<p>Ładowanie…</p>}><Page/></Suspense>);console.log(v.host.textContent);`],
  ['Suspense bez fallbacku podczas ładowania','""','"Ładowanie…"','"Raport"','undefined',String.raw`const chunk=deferred<Mod>();const Page=lazy(()=>chunk.promise);const v=await mount(<Suspense><Page/></Suspense>);console.log(JSON.stringify(v.host.textContent));`],
  ['Efekt leniwego komponentu przed załadowaniem','brak','efekt','błąd','undefined',String.raw`const chunk=deferred<Mod>();const Page=lazy(()=>chunk.promise);await mount(<Suspense fallback={<p>…</p>}><Page/></Suspense>);console.log(log.join(",")||"brak");`],
  ['Efekt leniwego komponentu po załadowaniu','efekt','brak','efekt,efekt','błąd',String.raw`log.length=0;const chunk=deferred<Mod>();const Page=lazy(()=>chunk.promise);await mount(<Suspense fallback={<p>…</p>}><Page/></Suspense>);await act(async()=>{chunk.resolve({default:function Loaded(){useEffect(()=>{log.push("efekt");},[]);return <p>ok</p>;}});await chunk.promise;});console.log(log.join(",")||"brak");`],
  ['Ponowne wyświetlenie załadowanego lazy (synchronicznie)','Raport','Ładowanie…','pusty','błąd',String.raw`const Page=lazy(async()=>({default:()=><p>Raport</p>}));const v=await mount(<Suspense fallback={<p>Ładowanie…</p>}><Page/></Suspense>);await v.update(<p>inny</p>);act(()=>{v.root.render(<Suspense fallback={<p>Ładowanie…</p>}><Page/></Suspense>);});console.log(v.host.textContent);`],
  ['Licznik poza granicą Suspense zachowuje stan podczas ładowania','3','0','1','pusty',String.raw`const chunk=deferred<Mod>();const Page=lazy(()=>chunk.promise);let inc!:()=>void;function Counter(){const [n,setN]=useState(0);inc=()=>setN(x=>x+1);return <b>{n}</b>;}const v=await mount(<div><Counter/><Suspense fallback={<i>…</i>}><Page/></Suspense></div>);await act(async()=>{inc();inc();inc();});console.log(v.host.querySelector("b")?.textContent);`],
];
for(const [label,answer,b,c,d,body] of probes)q(label+' — co wypisze kod?',answer,b,c,d,'Wynik pochodzi z uruchomienia na React 19 w Vitest (createRoot + act w jsdom, bez StrictMode).',header+body);
if(exercises.length!==30||quiz.length!==100)throw Error('Niepełny zestaw '+exercises.length+'/'+quiz.length);
const project={'src/pages/ReportsPage.tsx':reports,'src/RichEditor.tsx':editor,'src/preload.ts':preload,'src/DescriptionSection.tsx':section,'src/Layout.tsx':layout,'src/router.tsx':router,'src/lazy.test.tsx':tests};
const mutants=[
  {file:'src/preload.ts',before:'promise??=load();',after:'promise=load();'},
  {file:'src/preload.ts',before:'attempt<=retries',after:'attempt<retries'},
  {file:'src/DescriptionSection.tsx',before:'onMouseEnter={()=>void editor.preload()} ',after:''},
  {file:'src/Layout.tsx',before:'if(this.state.failed)return',after:'if(false)return'},
  {file:'src/Layout.tsx',before:'    <header><strong>DevDesk</strong></header>\n    <main>\n      <ChunkErrorBoundary>\n        <Suspense fallback={<p aria-busy="true">Ładowanie ekranu…</p>}>{children}</Suspense>\n      </ChunkErrorBoundary>\n    </main>',after:'    <Suspense fallback={<p aria-busy="true">Ładowanie ekranu…</p>}>\n    <header><strong>DevDesk</strong></header>\n    <main><ChunkErrorBoundary>{children}</ChunkErrorBoundary></main>\n    </Suspense>'},
  {file:'src/router.tsx',before:'loader:mod.reportsLoader',after:'loader:async()=>({total:0})'},
];
module.exports={exercises,quiz,project,mutants,probeCount:probes.length};
