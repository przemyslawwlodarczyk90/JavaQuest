const {lesson}=require('./practice_kit.cjs');
const boundary=String.raw`import {Component,type ErrorInfo,type ReactNode} from "react";

type FallbackArgs={error:unknown;reset:()=>void};
type Props={
  children:ReactNode;
  fallback:(args:FallbackArgs)=>ReactNode;
  onError?:(error:unknown,componentStack?:string|null)=>void;
};
type State={failed:boolean;error:unknown};

export class ErrorBoundary extends Component<Props,State>{
  state:State={failed:false,error:null};

  static getDerivedStateFromError(error:unknown):State{
    return {failed:true,error};
  }

  componentDidCatch(error:unknown,info:ErrorInfo){
    this.props.onError?.(error,info.componentStack);
  }

  reset=()=>this.setState({failed:false,error:null});

  render(){
    if(this.state.failed)return this.props.fallback({error:this.state.error,reset:this.reset});
    return this.props.children;
  }
}`;
const messages=String.raw`export function errorMessage(error:unknown){
  if(error instanceof Error)return error.message;
  if(typeof error==="string")return error;
  return "Nieznany błąd";
}

export type UserFacing="validation"|"notFound"|"offline"|"unexpected";

// Przewidywalne stany należą do komponentu. Do granicy trafia to, czego użytkownik sam nie naprawi.
export function classifyStatus(status:number|null):UserFacing{
  if(status===null)return "offline";
  if(status===400||status===422)return "validation";
  if(status===404)return "notFound";
  return "unexpected";
}

export function goesToBoundary(status:number|null){
  return classifyStatus(status)==="unexpected";
}`;
const panel=String.raw`import {useState} from "react";
import {ErrorBoundary,useErrorBoundary,type FallbackProps} from "react-error-boundary";
import {classifyStatus,errorMessage,goesToBoundary} from "./errors";

export type Assignee={id:number;name:string};
export type TicketWithAssignee={id:number;title:string;assignee:Assignee|null};

export function TicketError({error,resetErrorBoundary}:FallbackProps){
  return (
    <div role="alert">
      <p>Nie udało się wyświetlić ticketu.</p>
      <details><summary>Szczegóły</summary><code>{errorMessage(error)}</code></details>
      <button type="button" onClick={resetErrorBoundary}>Spróbuj ponownie</button>
    </div>
  );
}

export function AssigneeCard({ticket,save}:{ticket:TicketWithAssignee;save:()=>Promise<number|null>}){
  const {showBoundary}=useErrorBoundary();
  const [notice,setNotice]=useState("");

  async function reassign(){
    const status=await save();
    if(status!==null&&status<400){setNotice("Zapisano");return;}
    if(goesToBoundary(status)){showBoundary(new Error("Serwer zwrócił "+status));return;}
    setNotice(classifyStatus(status)==="offline"?"Brak połączenia. Spróbuj później.":"Popraw dane i spróbuj ponownie.");
  }

  return (
    <article>
      <h3>{ticket.title}</h3>
      <p>Przypisany: {ticket.assignee?.name??"nikt"}</p>
      <button type="button" onClick={reassign}>Przypisz ponownie</button>
      <p role="status">{notice}</p>
    </article>
  );
}

export function TicketPanel({ticket,save,onRetry,onError}:{ticket:TicketWithAssignee;save:()=>Promise<number|null>;onRetry?:()=>void;onError?:(error:unknown)=>void}){
  return (
    <ErrorBoundary FallbackComponent={TicketError} onReset={onRetry} onError={onError} resetKeys={[ticket.id]}>
      <AssigneeCard ticket={ticket} save={save}/>
    </ErrorBoundary>
  );
}`;
const row=String.raw`import {ErrorBoundary} from "react-error-boundary";

export type RawTicket={id:number;title:string;assignee:{name:string}};

function Row({ticket}:{ticket:RawTicket}){
  return <li>{ticket.title}: {ticket.assignee.name}</li>;
}

export function TicketList({tickets,onError}:{tickets:readonly RawTicket[];onError?:(error:unknown)=>void}){
  return (
    <ul>
      {tickets.map(ticket=>(
        <ErrorBoundary key={ticket.id} onError={onError} fallback={<li role="alert">Nie udało się wyświetlić ticketu {ticket.id}</li>}>
          <Row ticket={ticket}/>
        </ErrorBoundary>
      ))}
    </ul>
  );
}`;
const monitoring=String.raw`export type Reporter={
  capture:(error:unknown,context:{kind:"caught"|"uncaught"|"recoverable";componentStack?:string})=>void;
};

type RootInfo={componentStack?:string};

// Jedno miejsce wysyłki błędów Reacta. Te same trzy opcje przyjmuje createRoot.
export function rootOptions(reporter:Reporter){
  const handler=(kind:"caught"|"uncaught"|"recoverable")=>(error:unknown,info:RootInfo)=>
    reporter.capture(error,{kind,componentStack:info.componentStack});
  return {
    onCaughtError:handler("caught"),
    onUncaughtError:handler("uncaught"),
    onRecoverableError:handler("recoverable"),
  };
}

const PERSONAL=[/(email=)[^&#]+/gi,/(token=)[^&#]+/gi];

export function scrubUrl(url:string){
  return PERSONAL.reduce((value,pattern)=>value.replace(pattern,"$1[ukryte]"),url);
}

type SentryEvent={request?:{url?:string};user?:{id?:string|number;email?:string;ip_address?:string|null}};

export function beforeSend<T extends SentryEvent>(event:T):T{
  if(event.request?.url)event.request.url=scrubUrl(event.request.url);
  if(event.user)event.user={id:event.user.id};
  return event;
}`;
const sentry=String.raw`// main.tsx
import * as Sentry from "@sentry/react";
import {StrictMode} from "react";
import {createRoot} from "react-dom/client";
import {beforeSend} from "./monitoring";

declare function App():React.JSX.Element;

Sentry.init({
  dsn:"https://public@sentry.example/1",
  environment:"production",
  release:"devdesk@1.4.0",
  enabled:true,
  integrations:[Sentry.browserTracingIntegration()],
  tracesSampleRate:0.1,
  beforeSend,
});

createRoot(document.getElementById("root")!,{
  onUncaughtError:Sentry.reactErrorHandler((error,info)=>{
    console.error("Nieobsłużony błąd",error,info.componentStack);
  }),
  onCaughtError:Sentry.reactErrorHandler(),
  onRecoverableError:Sentry.reactErrorHandler(),
}).render(<StrictMode><App/></StrictMode>);`;
const lab=String.raw`import {useEffect,useState} from "react";
import {act,fireEvent,render} from "@testing-library/react";
import {ErrorBoundary} from "./ErrorBoundary";

export function Bomb({kind}:{kind:"render"|"effect"|"cleanup"|"none"}){
  if(kind==="render")throw new Error("render");
  useEffect(()=>{
    if(kind==="effect")throw new Error("effect");
    return ()=>{if(kind==="cleanup")throw new Error("cleanup");};
  },[kind]);
  return <p>Działa</p>;
}

function ClickBomb(){
  return <button onClick={()=>{throw new Error("handler");}}>Klik</button>;
}

function Toggle(){
  const [on,setOn]=useState(false);
  return <button onClick={()=>setOn(true)}>{on?<Bomb kind="render"/>:"Włącz"}</button>;
}

function quiet<T>(work:()=>T){
  const original=console.error;
  console.error=()=>{};
  try{return work();}finally{console.error=original;}
}

export function outcome(element:React.ReactNode){
  return quiet(()=>{
    const view=render(<ErrorBoundary fallback={({error})=><p>Fallback: {String((error as Error)?.message??error)}</p>}>{element}</ErrorBoundary>);
    const text=view.container.textContent??"";
    view.unmount();
    return text;
  });
}

export function clickOutcome(){
  return quiet(()=>{
    const onError=(event:ErrorEvent)=>event.preventDefault();
    window.addEventListener("error",onError);
    const view=render(<ErrorBoundary fallback={()=><p>Fallback</p>}><ClickBomb/></ErrorBoundary>);
    fireEvent.click(view.getByRole("button"));
    const text=view.container.textContent??"";
    view.unmount();
    window.removeEventListener("error",onError);
    return text;
  });
}

export function toggleOutcome(){
  return quiet(()=>{
    const view=render(<ErrorBoundary fallback={()=><p>Fallback</p>}><Toggle/></ErrorBoundary>);
    fireEvent.click(view.getByRole("button"));
    const text=view.container.textContent??"";
    view.unmount();
    return text;
  });
}

export async function thrownValue(value:unknown){
  function Thrower():null{throw value;}
  return quiet(()=>{
    let seen:unknown="brak";
    const view=render(<ErrorBoundary fallback={({error})=>{seen=error;return <p>F</p>;}}><Thrower/></ErrorBoundary>);
    view.unmount();
    return seen;
  });
}

export function resetOutcome(){
  return quiet(()=>{
    let reset=()=>{};
    let broken=true;
    function Flaky(){
      if(broken)throw new Error("raz");
      return <p>Naprawione</p>;
    }
    const view=render(<ErrorBoundary fallback={args=>{reset=args.reset;return <p>Fallback</p>;}}><Flaky/></ErrorBoundary>);
    const before=view.container.textContent;
    broken=false;
    act(()=>reset());
    const after=view.container.textContent;
    view.unmount();
    return before+"→"+after;
  });
}

export function nestedOutcome(){
  return quiet(()=>{
    const view=render(
      <ErrorBoundary fallback={()=><p>Zewnętrzna</p>}>
        <p>Menu</p>
        <ErrorBoundary fallback={()=><p>Wewnętrzna</p>}><Bomb kind="render"/></ErrorBoundary>
      </ErrorBoundary>,
    );
    const text=view.container.textContent;
    view.unmount();
    return text;
  });
}

export function fallbackThrows(){
  return quiet(()=>{
    const view=render(
      <ErrorBoundary fallback={()=><p>Zewnętrzna</p>}>
        <ErrorBoundary fallback={()=>{throw new Error("w fallbacku");}}><Bomb kind="render"/></ErrorBoundary>
      </ErrorBoundary>,
    );
    const text=view.container.textContent;
    view.unmount();
    return text;
  });
}`;
const tests=String.raw`import {expect,test,vi} from "vitest";
import {act,render,screen,waitFor} from "@testing-library/react";
import userEvent from "@testing-library/user-event";
import {ErrorBoundary} from "./ErrorBoundary";
import {TicketPanel,type TicketWithAssignee} from "./TicketPanel";
import {TicketList,type RawTicket} from "./TicketList";
import {classifyStatus,errorMessage,goesToBoundary} from "./errors";
import {beforeSend,rootOptions,scrubUrl} from "./monitoring";
import {createRoot} from "react-dom/client";
import {Bomb,clickOutcome,nestedOutcome,outcome,resetOutcome,toggleOutcome} from "./errorLab";

const ticket=(over:Partial<TicketWithAssignee>={}):TicketWithAssignee=>({id:1,title:"Awaria",assignee:{id:7,name:"Ala"},...over});

test("własna granica łapie render i efekt, ale nie handler",()=>{
  expect(outcome(<Bomb kind="render"/>)).toBe("Fallback: render");
  expect(outcome(<Bomb kind="effect"/>)).toBe("Fallback: effect");
  expect(outcome(<Bomb kind="none"/>)).toBe("Działa");
  expect(clickOutcome()).toBe("Klik");
  expect(toggleOutcome()).toBe("Fallback");
});

test("granica raportuje błąd razem ze ścieżką komponentów",()=>{
  const onError=vi.fn(),spy=vi.spyOn(console,"error").mockImplementation(()=>{});
  render(<ErrorBoundary onError={onError} fallback={()=><p>F</p>}><Bomb kind="render"/></ErrorBoundary>);
  expect(onError).toHaveBeenCalledTimes(1);
  expect((onError.mock.calls[0][0] as Error).message).toBe("render");
  expect(onError.mock.calls[0][1]).toContain("Bomb");
  spy.mockRestore();
});

test("reset i zagnieżdżone granice",()=>{
  expect(resetOutcome()).toBe("Fallback→Naprawione");
  expect(nestedOutcome()).toBe("MenuWewnętrzna");
});

test("rzucone null też oznacza awarię",()=>{
  const spy=vi.spyOn(console,"error").mockImplementation(()=>{});
  function Thrower():null{throw null;}
  render(<ErrorBoundary fallback={()=><p>Fallback</p>}><Thrower/></ErrorBoundary>);
  expect(screen.getByText("Fallback")).toBeVisible();
  spy.mockRestore();
});

test("zły ticket psuje tylko swój wiersz i jest raportowany",()=>{
  const spy=vi.spyOn(console,"error").mockImplementation(()=>{}),onError=vi.fn();
  const tickets=[{id:1,title:"A",assignee:{name:"Ala"}},{id:2,title:"B",assignee:null},{id:3,title:"C",assignee:{name:"Ola"}}] as unknown as RawTicket[];
  render(<TicketList tickets={tickets} onError={onError}/>);
  expect(screen.getByText("A: Ala")).toBeVisible();
  expect(screen.getByText("C: Ola")).toBeVisible();
  expect(screen.getByRole("alert")).toHaveTextContent("ticketu 2");
  expect(onError).toHaveBeenCalledTimes(1);
  spy.mockRestore();
});

test("panel obsługuje brak przypisania w samym komponencie",()=>{
  render(<TicketPanel ticket={ticket({assignee:null})} save={async()=>200}/>);
  expect(screen.getByText("Przypisany: nikt")).toBeVisible();
  expect(screen.queryByRole("alert")).toBeNull();
});

test("błąd 500 z handlera trafia do granicy, a ponowienie wraca do treści",async()=>{
  const user=userEvent.setup(),spy=vi.spyOn(console,"error").mockImplementation(()=>{}),onRetry=vi.fn(),onError=vi.fn();
  render(<TicketPanel ticket={ticket()} save={async()=>500} onRetry={onRetry} onError={onError}/>);
  await user.click(screen.getByRole("button",{name:"Przypisz ponownie"}));
  expect(await screen.findByRole("alert")).toHaveTextContent("Nie udało się");
  expect(screen.getByText("Serwer zwrócił 500")).toBeInTheDocument();
  expect(onError).toHaveBeenCalled();
  await user.click(screen.getByRole("button",{name:"Spróbuj ponownie"}));
  expect(onRetry).toHaveBeenCalled();
  expect(screen.getByRole("heading",{name:"Awaria"})).toBeVisible();
  spy.mockRestore();
});

test("błąd walidacji i brak sieci zostają w komponencie",async()=>{
  const user=userEvent.setup();
  const view=render(<TicketPanel ticket={ticket()} save={async()=>422}/>);
  await user.click(screen.getByRole("button",{name:"Przypisz ponownie"}));
  expect(await screen.findByRole("status")).toHaveTextContent("Popraw dane");
  expect(screen.queryByRole("alert")).toBeNull();
  view.unmount();
  render(<TicketPanel ticket={ticket()} save={async()=>null}/>);
  await user.click(screen.getByRole("button",{name:"Przypisz ponownie"}));
  await waitFor(()=>expect(screen.getByRole("status")).toHaveTextContent("Brak połączenia"));
});

test("zmiana ticketu resetuje granicę przez resetKeys",async()=>{
  const user=userEvent.setup(),spy=vi.spyOn(console,"error").mockImplementation(()=>{});
  const view=render(<TicketPanel ticket={ticket()} save={async()=>500}/>);
  await user.click(screen.getByRole("button",{name:"Przypisz ponownie"}));
  await screen.findByRole("alert");
  view.rerender(<TicketPanel ticket={ticket({id:2,title:"Inny"})} save={async()=>200}/>);
  expect(screen.getByRole("heading",{name:"Inny"})).toBeVisible();
  spy.mockRestore();
});

test("klasyfikacja i komunikaty błędów",()=>{
  expect(classifyStatus(422)).toBe("validation");
  expect(classifyStatus(404)).toBe("notFound");
  expect(classifyStatus(null)).toBe("offline");
  expect(goesToBoundary(500)).toBe(true);
  expect(goesToBoundary(400)).toBe(false);
  expect(errorMessage(new Error("x"))).toBe("x");
  expect(errorMessage("tekst")).toBe("tekst");
  expect(errorMessage({code:404})).toBe("Nieznany błąd");
});

test("opcje korzenia przekazują rodzaj błędu i prawdziwe zgłoszenie z createRoot",async()=>{
  const capture=vi.fn(),spy=vi.spyOn(console,"error").mockImplementation(()=>{});
  const host=document.createElement("div");
  const root=createRoot(host,rootOptions({capture}));
  await act(async()=>{root.render(<ErrorBoundary fallback={()=><p>F</p>}><Bomb kind="render"/></ErrorBoundary>);});
  expect(capture.mock.calls[0][1].kind).toBe("caught");
  expect(capture.mock.calls[0][1].componentStack).toContain("Bomb");
  capture.mockClear();
  // Wewnątrz act React przekazuje niezłapany błąd do testu zamiast do onUncaughtError, więc ten krok działa poza act.
  const environment=globalThis as {IS_REACT_ACT_ENVIRONMENT?:boolean};
  environment.IS_REACT_ACT_ENVIRONMENT=false;
  root.render(<Bomb kind="render"/>);
  await vi.waitFor(()=>expect(capture).toHaveBeenCalled());
  expect(capture.mock.calls[0][1].kind).toBe("uncaught");
  root.unmount();
  environment.IS_REACT_ACT_ENVIRONMENT=true;
  spy.mockRestore();
});

test("dane osobowe nie wychodzą do monitoringu",()=>{
  expect(scrubUrl("https://a/x?email=a@b.pl&page=1")).toBe("https://a/x?email=[ukryte]&page=1");
  expect(scrubUrl("https://a/x?token=abc")).toBe("https://a/x?token=[ukryte]");
  const event=beforeSend({request:{url:"https://a?email=a@b.pl"},user:{id:7,email:"a@b.pl",ip_address:"1.2.3.4"}});
  expect(event.user).toEqual({id:7});
  expect(event.request?.url).toContain("[ukryte]");
});`;
const exercises=[
['Odtwórz biały ekran z assignee: null.','Bez granicy React odmontuje całe drzewo.','Ticket z assignee: null i odczyt ticket.assignee.name rzuca TypeError w renderze. React nie zostawia zepsutego interfejsu, więc odmontowuje korzeń: znika też nagłówek i menu. Zapisz to jako test, zanim dodasz granicę.'],
['Napisz granicę błędu jako klasę.','getDerivedStateFromError zmienia stan, a componentDidCatch raportuje.',boundary],
['Obsłuż rzucenie wartości innej niż Error.','Flaga stanu odróżnia awarię od błędu równego null.',boundary],
['Pokaż, co łapie, a czego nie łapie granica.','Porównaj render, efekt i handler.',lab],
['Sprawdź testem granice zagnieżdżone.','Awarię obsługuje najbliższa granica.',tests],
['Napisz bezpieczny odczyt komunikatu błędu.','error ma typ unknown.',messages],
['Oddziel przewidywalne stany od awarii.','404 i walidacja to normalne stany interfejsu.',messages],
['Zbuduj TicketError z ponowieniem.','Fallback mówi, co się stało i co można zrobić.',panel],
['Dodaj granicę z resetKeys.','Zmiana ticketu czyści awarię poprzedniego.',panel],
['Przekaż błąd handlera do granicy przez showBoundary.','Tylko dla błędów, których użytkownik nie naprawi.',panel],
['Zostaw błąd walidacji w komponencie.','Status pokazuje, co poprawić.',panel],
['Obsłuż brak przypisania bez wyjątku.','Stan null jest przewidywalny: nie potrzebuje granicy.',panel],
['Dodaj granicę wokół każdego wiersza listy.','Jeden zły wiersz nie zabiera pozostałych.',row],
['Przetestuj listę z jednym złym ticketem.','Rzutowanie w teście udaje niezgodną odpowiedź API.',tests],
['Rozmieść granice w drzewie DevDesk.','Aplikacja, trasa, widżet, wiersz.','Aplikacja: ostatnia linia obrony z odświeżeniem. Trasa: errorElement routera, menu zostaje. Widżet: komentarze padają, a szczegóły ticketu zostają. Wiersz: zły ticket nie zabiera listy. Granica wokół każdego przycisku to przesada, bo nie ma sensownego komunikatu.'],
['Napisz centralne raportowanie opcjami korzenia.','Trzy opcje dla trzech rodzajów błędów.',monitoring],
['Sprawdź opcje korzenia na prawdziwym createRoot.','caught dla granicy, uncaught bez niej.',tests],
['Podłącz Sentry do createRoot.','reactErrorHandler pasuje do opcji React 19.',sentry],
['Usuń dane osobowe z adresów.','Wzorce obejmują email i token.',monitoring],
['Zostaw w zdarzeniu Sentry tylko identyfikator użytkownika.','beforeSend to ostatnia chwila na zmianę danych.',monitoring],
['Dodaj kontekst błędu: użytkownik, tag, ślad.','Same wywołania często nie wystarczają.','Sentry.setUser({id:user.id}) po zalogowaniu i Sentry.setUser(null) po wylogowaniu. Sentry.setTag("ticketId",id) na stronie szczegółów. Sentry.addBreadcrumb({category:"action",message:"Zmieniono status na CLOSED"}) przy akcjach. Bez e-maila, chyba że pozwala na to polityka prywatności.'],
['Skonfiguruj mapy źródeł dla monitoringu.','Mapa ma trafić do Sentry, nie na serwer.','Włącz build.sourcemap i @sentry/vite-plugin z tokenem z CI. Wtyczka wysyła mapy i usuwa je po wysyłce z katalogu dist. Ustaw release zgodny z wdrożeniem. Sprawdź błąd z builda: stos powinien wskazać AssigneeCard.tsx i właściwą linię.'],
['Wyjaśnij, czemu release musi pasować do map źródeł.','Który plik mapuje się na który build?','Sentry dopasowuje stos do map przez release i identyfikatory plików. Inna wartość oznacza nierozwinięty stos z index-3f2a.js. Jako release ustaw wersję lub commit i używaj tej samej wartości w buildzie oraz w Sentry.init.'],
['Wyłącz wysyłkę w trybie deweloperskim.','Nie zaśmiecaj monitoringu lokalną pracą.','Sentry.init({enabled:import.meta.env.PROD,...}). W testach nie inicjalizuj Sentry albo przekaż atrapę reportera przez rootOptions. Tryb środowiska podawaj osobno przez environment, aby filtrować błędy w panelu.'],
['Ustal próbkowanie śledzenia wydajności.','Błędy i śledzenia to dwie różne rzeczy.','tracesSampleRate:0.1 oznacza pomiar około 10% transakcji, by ograniczyć koszty. Błędy są domyślnie wysyłane wszystkie, chyba że ustawisz sampleRate. Przy dużym ruchu oceń koszt i przydatność, a nie wyłączaj śledzenia na ślepo.'],
['Znajdź problem w fallbacku () => null.','Co widzi użytkownik, a co zespół?','Użytkownik widzi dziurę w interfejsie bez wyjaśnienia, a bez onError zespół nic nie wie. Fallback ma mówić, co się stało i co zrobić (ponów, odśwież, wróć), a każdy złapany błąd ma trafić do monitoringu.'],
['Wyjaśnij, co dzieje się z błędem w samym fallbacku.','Granica nie łapie własnych błędów.','Błąd w fallbacku trafia do następnej granicy wyżej. Bez niej aplikacja się odmontowuje. Dlatego fallback ma być prosty i nie powinien zależeć od danych, które właśnie zawiodły.'],
['Przetestuj błąd w sprzątaniu efektu.','Cleanup uruchamia się przy zmianie lub odmontowaniu.','Render z kind="cleanup" i rerender z inną wartością wykona cleanup. Wyjątek zostanie przekazany do najbliższej granicy. Testuj to tak samo jak inne ścieżki: fallback jest widoczny, a raport zawiera komponent.'],
['Zaplanuj sprawdzenie na buildzie produkcyjnym.','Tryb deweloperski zachowuje się inaczej.','Zbuduj aplikację z mapami, uruchom vite preview, wywołaj błąd na tickecie testowym. Sprawdź w Sentry: stos z plikiem źródłowym, ścieżkę komponentów, release, environment, brak e-maila i brak tokenu w adresie.'],
['Sprawdź kryterium akceptacji lekcji.','Trzy zachowania i jeden dowód z produkcji.','Ticket z assignee: null psuje tylko swój wiersz. Komentarze można ponowić bez przeładowania. Błąd z builda produkcyjnego pojawia się w Sentry ze stosem wskazującym plik źródłowy i ścieżką komponentów, bez danych osobowych.'],
];
const concepts=[
['Co robi React po wyjątku w renderze bez granicy?','Odmontowuje całe drzewo','Renderuje poprzedni stan','Pomija komponent','Odświeża stronę','Zepsuty interfejs uznaje za gorszy od pustego.'],
['Czym jest granica błędu?','Komponentem łapiącym błędy poddrzewa i pokazującym fallback','Blokiem try/catch w JSX','Funkcją w useEffect','Pakietem Sentry','Reszta aplikacji działa dalej.'],
['Które metody tworzą granicę?','getDerivedStateFromError i componentDidCatch','useEffect i useState','render i useMemo','onError i onCatch','Granica jest na razie tylko komponentem klasowym.'],
['Do czego służy getDerivedStateFromError?','Przełącza stan na awarię w fazie renderu','Wysyła raport','Odświeża stronę','Resetuje granicę','Musi być czysty, bo działa w renderze.'],
['Do czego służy componentDidCatch?','Do efektów ubocznych, np. raportowania','Do renderu fallbacku','Do zmiany props','Do tworzenia kontekstu','Działa w fazie commit.'],
['Czy granica złapie błąd z onClick?','Nie','Tak, zawsze','Tylko w trybie deweloperskim','Tylko w TSX','Handler działa poza renderem.'],
['Czy granica złapie błąd z setTimeout?','Nie, jeśli nie przekażesz go do Reacta','Tak','Tylko w StrictMode','Tylko po await','Asynchroniczny kod poza Reactem jest poza granicą.'],
['Czy granica złapie błąd z useEffect?','Tak','Nie','Tylko sprzątanie','Tylko w Next','Ciało i sprzątanie efektów są objęte.'],
['Czy granica złapie odrzucone use(promise)?','Tak','Nie','Tylko przez Suspense','Tylko w SSR','Suspense obsługuje oczekiwanie, a granica odrzucenie.'],
['Czy granica złapie błąd z akcji useActionState?','Tak, wyjątek trafia do najbliższej granicy','Nie','Tylko po await','Tylko w formularzu GET','Przewidywalne wyniki lepiej zwracać w stanie akcji.'],
['Czy granica łapie błąd w sobie samej?','Nie, łapie ją granica wyżej','Tak','Tylko w fallbacku','Tak, ale raz','Dotyczy to też fallbacku.'],
['Czy granica działa przy renderze na serwerze?','Nie jak w kliencie: SSR ma własną obsługę błędów','Tak, identycznie','Tak, dzięki hydratacji','Tak, tylko w Next','Serwer i klient obsługują awarię inaczej.'],
['Co daje biblioteka react-error-boundary?','Gotową granicę, reset, resetKeys i useErrorBoundary','Sanityzację','Monitoring','Tłumaczenia','Nie trzeba samemu pisać klasy.'],
['Do czego służy resetKeys?','Automatycznie resetuje granicę po zmianie wartości','Zapisuje błędy','Blokuje ponowienia','Zmienia klucz komponentu w DOM','Inny ticket nie powinien dziedziczyć awarii.'],
['Do czego służy resetErrorBoundary?','Czyści awarię i ponownie renderuje dzieci','Odświeża stronę','Usuwa błąd z Sentry','Zamyka kartę','To podstawa przycisku „Spróbuj ponownie”.'],
['Do czego służy onReset?','Do posprzątania, np. odświeżenia danych, przed ponownym renderem','Do raportowania','Do logowania','Do budowy fallbacku','Bez usunięcia przyczyny błąd wróci.'],
['Do czego służy showBoundary?','Przekazuje błąd z handlera do najbliższej granicy','Pokazuje modal','Włącza Sentry','Odświeża listę','Daje wspólny ekran dla poważnych błędów.'],
['Który błąd pasuje do granicy?','Niespodziewane 500 przy zapisie','Błąd walidacji pola','Brak wyników wyszukiwania','Pusty koszyk','Przewidywalne stany obsługuje komponent.'],
['Co zrobić z 404 dla ticketu?','Pokazać normalny stan „nie znaleziono”','Rzucić wyjątek zawsze','Pokazać biały ekran','Zignorować','To przewidywalny wynik.'],
['Jaki typ ma error w catch i w granicy TSX?','unknown','Error','string','any zawsze','Rzucić można dowolną wartość.'],
['Dlaczego error.message bywa undefined?','Rzucono np. tekst lub obiekt','Bo TS usuwa pola','Bo Sentry je czyści','Bo React je anonimizuje','Sprawdzaj instanceof Error.'],
['Dlaczego stan samego error nie wystarcza w granicy?','throw null dałoby stan wyglądający jak brak awarii','Bo Error jest klasą','Bo tak wymaga JS','Bo to spowolni render','Flaga failed rozróżnia te sytuacje.'],
['Czy własny kod powinien rzucać tekst?','Nie, rzucaj Error lub klasę potomną','Tak, jest prostszy','Tak, w TS','Zawsze obiekt','Dzięki temu masz stos wywołań.'],
['Gdzie stawiać granice?','Tam, gdzie awaria ma sensowną wyspę i komunikat','Wokół każdego przycisku','Tylko jedną w App','Tylko w teście','Warstwy: aplikacja, trasa, widżet, wiersz.'],
['Co zostaje, gdy granica trasy łapie błąd?','Nagłówek i menu poza granicą','Nic','Tylko stopka','Tylko komunikat Sentry','Użytkownik może przejść gdzie indziej.'],
['Co opisuje onCaughtError?','Błąd złapany przez granicę','Błąd niezłapany','Błąd hydratacji','Błąd sieci','Użytkownik widzi fallback.'],
['Co opisuje onUncaughtError?','Błąd niezłapany przez żadną granicę','Każdy błąd fetch','Błąd z try/catch','Błąd serwera','Aplikacja została odmontowana.'],
['Co opisuje onRecoverableError?','Błąd, z którego React sam się podniósł','Błąd krytyczny','Błąd w Sentry','Błąd w Node','Np. niezgodność hydratacji.'],
['Gdzie ustawiasz te trzy opcje?','W createRoot lub hydrateRoot','W ErrorBoundary','W package.json','W vite.config','To centralne miejsce raportowania.'],
['Co zawiera info.componentStack?','Ścieżkę komponentów, gdzie wystąpił błąd','Stos serwera','Listę props','Historię Git','To duże ułatwienie w diagnozie.'],
['Czy console.error w produkcji wystarcza?','Nie, nikt nie widzi konsoli użytkownika','Tak','Tak, jeśli jest czerwony','Tak, z mapami źródeł','Monitoring zbiera błędy centralnie.'],
['Co zbiera Sentry?','Błędy ze stosem, przeglądarką i śladami','Tylko logi serwera','Tylko metryki CPU','Kod źródłowy repozytorium','Breadcrumbs pokazują kroki przed błędem.'],
['Czym są breadcrumbs?','Krokami poprzedzającymi błąd','Nawigacją na stronie','Mapami źródeł','Tagami HTML','Pomagają odtworzyć sytuację.'],
['Po co Sentry.setTag?','Aby filtrować błędy, np. po tickecie','Aby ustawić kolor','Aby zmienić release','Aby wysłać e-mail','Tag to pole do wyszukiwania.'],
['Co powinien zawierać setUser domyślnie?','Sam identyfikator','Adres i e-mail','Hasło','Token','Minimalizuj dane osobowe.'],
['Co zrobić po wylogowaniu?','Sentry.setUser(null)','Nic','Sentry.close zawsze','Wyczyścić mapy źródeł','Kolejne błędy nie powinny wskazywać poprzedniej osoby.'],
['Po co mapy źródeł w monitoringu?','Aby stos wskazywał oryginalny plik i linię','Aby przyspieszyć stronę','Aby ukryć kod','Aby włączyć React','Bez nich widać zminifikowany kod.'],
['Gdzie nie publikować map źródeł?','Na publicznym serwerze, gdy nie jest to świadoma decyzja','W Sentry','W artefakcie CI','W prywatnym magazynie','Wysyłaj do monitoringu i usuwaj z dist.'],
['Czy DSN jest tajny?','Nie, identyfikuje projekt','Tak','Tak, w produkcji','Tylko dla Vite','Nadal warto ograniczyć dozwolone domeny.'],
['Co robi beforeSend?','Pozwala zmienić lub odrzucić zdarzenie przed wysyłką','Wysyła zdarzenie dwa razy','Zmienia kod źródłowy','Wyłącza mapy','To miejsce na usuwanie danych osobowych.'],
['Co zrobić z adresem z ?email=?','Usunąć wartość przed wysyłką','Wysłać bez zmian','Zaszyfrować w kliencie','Dodać do tagu','Parametry adresu często zawierają dane.'],
['Co oznacza tracesSampleRate:0.1?','Około 10% transakcji ma pomiar wydajności','10% błędów jest wysyłanych','10 sekund próbki','Limit 10 zdarzeń','To dotyczy śledzenia, nie samego raportu błędu.'],
['Co oznacza release?','Wersję aplikacji przypisaną do zdarzeń','Datę wdrożenia serwera','Numer buildu CSS','Nazwę środowiska','Pozwala wykryć regresję po wdrożeniu.'],
['Co oznacza environment?','Środowisko, np. production','Wersję Reacta','Przeglądarkę','Region','Oddziela błędy środowisk.'],
['Co jest złe w fallbacku () => null?','Tworzy cichą dziurę w interfejsie','Jest za krótki','Nie kompiluje się','Nie ma typu','Użytkownik nie wie, co się stało.'],
['Co powinien zawierać dobry fallback?','Co się stało i co można zrobić','Pełny stos użytkownikowi','Tylko emoji','Tylko kod HTTP','Szczegóły techniczne warto ukryć lub ograniczyć.'],
['Jaką rolę powinien mieć fallback?','alert, aby został ogłoszony','banner','presentation','tooltip','Nowy komunikat powinien dotrzeć do czytnika.'],
['Czy pokazywać error.message użytkownikowi?','Ostrożnie: może zawierać szczegóły techniczne','Zawsze w całości','Nigdy nic nie wolno mówić','Zawsze w alert()','Zachowaj przyjazny tekst, a szczegóły wyślij do monitoringu.'],
['Dlaczego ponowienie bez zmiany często zawodzi?','Przyczyna nadal istnieje','Bo reset nie działa','Bo React blokuje render','Bo Sentry odrzuca','onReset powinien naprawić dane, np. odświeżyć zapytanie.'],
['Jak często użytkownik powinien móc ponawiać?','Z rozsądnym ograniczeniem i wyjściem, np. powrotem do listy','Bez końca, w pętli automatycznej','Nigdy','Tylko po przeładowaniu','Automatyczna pętla może obciążyć serwer.'],
['Jak zachowa się rzucone null w granicy klasowej z flagą?','Pokaże fallback','Pokaże dzieci','Rzuci TypeError','Odświeży stronę','Flaga oddziela awarię od wartości błędu.'],
['Jak testować fallback bez hałasu w konsoli?','Wyciszyć console.error tylko na czas testu','Wyłączyć konsolę globalnie na zawsze','Usunąć granicę','Pominąć test','React loguje złapane błędy, więc test może je wyciszyć.'],
['Co sprawdza test z prawdziwym createRoot?','Że opcje korzenia dostają właściwy rodzaj błędu','Tylko typy','Tylko CSS','Tylko Sentry','Atrapa reportera zastępuje usługę.'],
['Co jest najlepszym dowodem działania monitoringu?','Błąd z builda produkcyjnego widoczny w panelu','Sam kod init','Log w dev','Zielony test jednostkowy','Tryb deweloperski nie przetestuje map ani konfiguracji.'],
['Dlaczego adresy mają znaczenie w RODO?','Mogą zawierać dane osobowe','Bo są długie','Bo zwiększają paczkę','Bo tak działa HTTP','Monitoring to przekazanie danych do zewnętrznej usługi.'],
['Czy granica zastępuje walidację danych API?','Nie, łapie skutki, gdy typy i kontrakt zawiodą','Tak','Tak, w TSX','Tak, z Zod','Walidacja na wejściu daje czytelniejszy błąd.'],
['Co pomaga wykryć niezgodną odpowiedź API wcześniej?','Parsowanie schematem, np. Zod','Tylko CSS','Tylko memo','useRef','Błąd dotyczy granicy systemu, a nie głębokiego komponentu.'],
['Kiedy wywołać Sentry.init?','Jak najwcześniej przed renderem','W każdym komponencie','Po pierwszym błędzie','W useEffect','Inaczej wczesne błędy umkną.'],
['Czy ten sam błąd złapany przez granicę trafi do onError i onCaughtError?','Tak: to różne poziomy obsługi','Nie, tylko onError','Nie, tylko w produkcji','Nie, to niemożliwe','Uważaj na podwójne raporty przy łączeniu obu.'],
['Jak uniknąć podwójnych raportów?','Raportować centralnie w opcjach korzenia','Wyłączyć onError i Sentry','Wysyłać dwa razy','Dodać drugi DSN','Jedno miejsce wysyłki jest łatwiejsze do utrzymania.'],
];
const header=String.raw`import {clickOutcome,fallbackThrows,nestedOutcome,outcome,resetOutcome,thrownValue,toggleOutcome,Bomb} from "./errorLab";
import {classifyStatus,errorMessage,goesToBoundary} from "./errors";
import {beforeSend,scrubUrl} from "./monitoring";`;
const probeRows=[
['Co pokaże granica przy błędzie renderu?','outcome(<Bomb kind="render"/>)','Fallback: render','Render jest objęty granicą.',['Działa','pusty tekst','Fallback: effect']],
['Co pokaże granica przy błędzie efektu?','outcome(<Bomb kind="effect"/>)','Fallback: effect','Efekt też jest objęty.',['Działa','pusty tekst','Fallback: render']],
['Co pokaże granica bez błędu?','outcome(<Bomb kind="none"/>)','Działa','Dzieci renderują się normalnie.',['Fallback: render','pusty tekst','null']],
['Co pokaże granica po błędzie w handlerze?','clickOutcome()','Klik','Handler jest poza renderem, więc interfejs zostaje.',['Fallback','pusty tekst','Fallback: handler']],
['Co pokaże granica, gdy kliknięcie doprowadzi do błędu renderu?','toggleOutcome()','Fallback','Zmiana stanu powoduje render, a on jest objęty.',['Włącz','pusty tekst','Działa']],
['Co pokaże granica po resecie po naprawie?','resetOutcome()','Fallback→Naprawione','Reset ponownie renderuje dzieci.',['Fallback→Fallback','Naprawione→Fallback','Naprawione→Naprawione']],
['Która granica obsłuży błąd zagnieżdżony?','nestedOutcome()','MenuWewnętrzna','Najbliższa granica, a reszta zostaje.',['Zewnętrzna','Menu','Wewnętrzna']],
['Która granica obsłuży błąd rzucony w fallbacku?','fallbackThrows()','Zewnętrzna','Granica nie łapie własnych błędów.',['Wewnętrzna','pusty tekst','w fallbacku']],
['Jaką wartość dostanie fallback po throw "tekst"?','await thrownValue("tekst")','tekst','Rzucić można dowolną wartość.',['Error: tekst','undefined','null']],
['Jaką wartość dostanie fallback po throw null?','await thrownValue(null)','null','Dlatego potrzebna jest flaga awarii.',['undefined','brak','Error']],
['Jaki komunikat ma Error?','errorMessage(new Error("x"))','x','Odczyt po instanceof Error.',['Error: x','Nieznany błąd','undefined']],
['Jaki komunikat ma tekst?','errorMessage("awaria")','awaria','Tekst jest przekazywany bez zmian.',['Nieznany błąd','undefined','Error: awaria']],
['Jaki komunikat ma obiekt?','errorMessage({code:404})','Nieznany błąd','Obiekt nie ma pewnego pola message.',['[object Object]','404','undefined']],
['Jaki komunikat ma null?','errorMessage(null)','Nieznany błąd','Bezpieczne wyjście dla dowolnej wartości.',['null','pusty tekst','undefined']],
['Klasyfikacja 400','classifyStatus(400)','validation','Użytkownik może to poprawić.',['unexpected','notFound','offline']],
['Klasyfikacja 422','classifyStatus(422)','validation','Błąd danych formularza.',['unexpected','notFound','offline']],
['Klasyfikacja 404','classifyStatus(404)','notFound','Normalny stan interfejsu.',['unexpected','validation','offline']],
['Klasyfikacja null','classifyStatus(null)','offline','Brak odpowiedzi serwera.',['unexpected','validation','notFound']],
['Klasyfikacja 500','classifyStatus(500)','unexpected','Użytkownik sam tego nie naprawi.',['validation','notFound','offline']],
['Klasyfikacja 403','classifyStatus(403)','unexpected','W tym prostym modelu nie ma osobnej kategorii.',['validation','notFound','offline']],
['Czy 500 trafia do granicy?','goesToBoundary(500)',true,'Niespodziewany błąd serwera.'],
['Czy 422 trafia do granicy?','goesToBoundary(422)',false,'Zostaje w formularzu.'],
['Czy 404 trafia do granicy?','goesToBoundary(404)',false,'To normalny stan.'],
['Czy brak sieci trafia do granicy?','goesToBoundary(null)',false,'Pokazujemy informację o połączeniu.'],
['Co zrobi scrubUrl z e-mailem?','scrubUrl("https://a/?email=a@b.pl")','https://a/?email=[ukryte]','Wartość jest usuwana, a nazwa zostaje.',['https://a/','https://a/?email=a@b.pl','https://a/?[ukryte]']],
['Czy scrubUrl zachowa pozostałe parametry?','scrubUrl("https://a/?email=a@b.pl&page=2")','https://a/?email=[ukryte]&page=2','Wzorzec kończy się na ampersandzie.',['https://a/?email=[ukryte]','https://a/?page=2','https://a/?[ukryte]&page=2']],
['Co zrobi scrubUrl z tokenem?','scrubUrl("https://a/?token=x")','https://a/?token=[ukryte]','Tokeny też nie mogą wychodzić.',['https://a/?token=x','https://a/','https://a/?[ukryte]']],
['Czy wielkość liter parametru ma znaczenie?','scrubUrl("https://a/?EMAIL=x")','https://a/?EMAIL=[ukryte]','Flaga i pomija wielkość liter.',['https://a/?EMAIL=x','https://a/','https://a/?email=[ukryte]']],
['Co zrobi scrubUrl z adresem bez danych?','scrubUrl("https://a/?page=2")','https://a/?page=2','Bez dopasowania nic się nie zmienia.',['https://a/','https://a/?[ukryte]','null']],
['Czy e-mail zostanie w user?','"email" in beforeSend({user:{id:1,email:"a@b.pl"}}).user!',false,'Zostawiamy sam identyfikator.'],
['Czy IP zostanie w user?','"ip_address" in beforeSend({user:{id:1,ip_address:"1.2.3.4"}}).user!',false,'Adres IP to też dana osobowa.'],
['Czy id zostanie w user?','beforeSend({user:{id:7,email:"a"}}).user!.id',7,'Identyfikator pozwala powiązać błędy.'],
['Czy zdarzenie bez użytkownika przejdzie?','beforeSend({} as {user?:{id?:number}}).user===undefined',true,'beforeSend nie tworzy pustego użytkownika.'],
['Czy beforeSend czyści adres?','beforeSend({request:{url:"https://a/?token=x"}}).request!.url','https://a/?token=[ukryte]','Adres zdarzenia też zawiera dane.',['https://a/?token=x','https://a/','undefined']],
['Która kategoria ma 401 w tym modelu?','classifyStatus(401)','unexpected','Sesję obsłuż osobno, gdy aplikacja jej wymaga.',['validation','notFound','offline']],
['Ile znaków ma wiadomość pustego Error?','errorMessage(new Error("")).length',0,'Własny fallback nie powinien polegać tylko na tym tekście.'],
['Czy 503 trafia do granicy?','goesToBoundary(503)',true,'To w tym modelu niespodziewana awaria.'],
['Czy 200 uznaje model za awarię do granicy?','goesToBoundary(200)',true,'Funkcję wywołujemy dopiero po sprawdzeniu sukcesu: to ograniczenie modelu warto znać.'],
['Jaką kategorię ma status 0?','classifyStatus(0)','unexpected','Tylko null oznacza brak połączenia w tym modelu.',['offline','validation','notFound']],
['Co zwraca reset po stałym błędzie?','outcome(<Bomb kind="render"/>).startsWith("Fallback")',true,'Bez usunięcia przyczyny fallback wraca przy każdym renderze.'],
];
const project={
'src/ErrorBoundary.tsx':boundary,'src/errors.ts':messages,'src/TicketPanel.tsx':panel,'src/TicketList.tsx':row,'src/monitoring.ts':monitoring,'src/main.sentry.tsx':sentry,'src/errorLab.tsx':lab,'src/errors.test.tsx':tests,
'README.md':'DevDesk: granice błędów i monitoring. npm install, npm test. Testy używają atrapy reportera na prawdziwym createRoot, ale nie wysyłają niczego do Sentry. Plik main.sentry.tsx jest sprawdzany typami. Wysyłkę i mapy źródeł sprawdź na własnym projekcie Sentry po zbudowaniu aplikacji.\n',
};
const mutants=[
{file:'src/ErrorBoundary.tsx',before:'if(this.state.failed)',after:'if(this.state.error)'},
{file:'src/ErrorBoundary.tsx',before:'this.props.onError?.(error,info.componentStack);',after:''},
{file:'src/ErrorBoundary.tsx',before:'reset=()=>this.setState({failed:false,error:null});',after:'reset=()=>{};'},
{file:'src/TicketPanel.tsx',before:'resetKeys={[ticket.id]}',after:''},
{file:'src/TicketPanel.tsx',before:'if(goesToBoundary(status)){showBoundary(new Error("Serwer zwrócił "+status));return;}',after:''},
{file:'src/TicketPanel.tsx',before:'ticket.assignee?.name??"nikt"',after:'ticket.assignee!.name'},
{file:'src/TicketPanel.tsx',before:' onReset={onRetry}',after:''},
{file:'src/TicketList.tsx',before:'<ErrorBoundary key={ticket.id} onError={onError} fallback={<li role="alert">Nie udało się wyświetlić ticketu {ticket.id}</li>}>',after:'<ErrorBoundary key={ticket.id} fallback={<li role="alert">Nie udało się wyświetlić ticketu {ticket.id}</li>}>'},
{file:'src/errors.ts',before:'status===400||status===422',after:'status===400'},
{file:'src/monitoring.ts',before:'event.user={id:event.user.id}',after:'event.user={...event.user}'},
{file:'src/monitoring.ts',before:'onUncaughtError:handler("uncaught")',after:'onUncaughtError:handler("caught")'},
];
module.exports=lesson({exercises,concepts,probeRows,project,mutants,header});
