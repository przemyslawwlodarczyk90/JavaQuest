const {lesson}=require('./practice_kit.cjs');
const classVersion=String.raw`import {Component} from "react";
import type {PanelProps,Report} from "./reportTypes";

type State={data:Report|null;error:unknown;autoRefresh:boolean};

export class ReportPanelClass extends Component<PanelProps,State>{
  state:State={data:null,error:null,autoRefresh:false};
  private timer:ReturnType<typeof setInterval>|undefined;
  private request=0;
  private mounted=false;

  componentDidMount(){this.mounted=true;void this.load();}

  componentDidUpdate(prev:PanelProps,prevState:State){
    if(prev.reportId!==this.props.reportId)void this.load();
    if(prevState.autoRefresh!==this.state.autoRefresh){
      clearInterval(this.timer);
      if(this.state.autoRefresh)this.timer=setInterval(()=>void this.load(),this.props.refreshMs??30_000);
    }
  }

  componentWillUnmount(){this.mounted=false;clearInterval(this.timer);}

  async load(){
    const id=++this.request;
    this.setState({error:null});
    try{
      const data=await this.props.loadReport(this.props.reportId);
      if(this.mounted&&id===this.request)this.setState({data});
    }catch(error){
      if(this.mounted&&id===this.request)this.setState({error});
    }
  }

  handleToggle=()=>this.setState(prev=>({autoRefresh:!prev.autoRefresh}));

  render(){
    const {data,error,autoRefresh}=this.state;
    if(error)return <p role="alert">Nie udało się wczytać raportu.</p>;
    if(!data)return <p>Ładowanie raportu…</p>;
    return (
      <section>
        <h2>{data.title}</h2>
        <label><input type="checkbox" checked={autoRefresh} onChange={this.handleToggle}/> Odświeżaj co 30 s</label>
        <p>Zgłoszeń: {data.count}</p>
      </section>
    );
  }
}`;
const hookVersion=String.raw`import {useEffect,useState} from "react";
import type {PanelProps,Report} from "./reportTypes";

export function ReportPanelHooks({reportId,loadReport,refreshMs=30_000}:PanelProps){
  const [data,setData]=useState<Report|null>(null);
  const [error,setError]=useState<unknown>(null);
  const [autoRefresh,setAutoRefresh]=useState(false);
  const [version,setVersion]=useState(0);

  useEffect(()=>{
    let ignore=false;
    setError(null);
    loadReport(reportId).then(
      report=>{if(!ignore)setData(report);},
      (reason:unknown)=>{if(!ignore)setError(reason);},
    );
    return ()=>{ignore=true;};
  },[reportId,version,loadReport]);

  useEffect(()=>{
    if(!autoRefresh)return;
    const id=setInterval(()=>setVersion(value=>value+1),refreshMs);
    return ()=>clearInterval(id);
  },[autoRefresh,refreshMs]);

  if(error)return <p role="alert">Nie udało się wczytać raportu.</p>;
  if(!data)return <p>Ładowanie raportu…</p>;
  return (
    <section>
      <h2>{data.title}</h2>
      <label><input type="checkbox" checked={autoRefresh} onChange={()=>setAutoRefresh(value=>!value)}/> Odświeżaj co 30 s</label>
      <p>Zgłoszeń: {data.count}</p>
    </section>
  );
}`;
const types=String.raw`export type Report={title:string;count:number};
export type LoadReport=(id:number)=>Promise<Report>;
export type PanelProps={reportId:number;loadReport:LoadReport;refreshMs?:number};`;
const useReport=String.raw`import {useEffect,useState,type ComponentType} from "react";
import type {LoadReport,Report} from "./reportTypes";

export type ReportState={data:Report|null;error:unknown};

export function useReport(reportId:number,loadReport:LoadReport):ReportState{
  const [state,setState]=useState<ReportState>({data:null,error:null});

  useEffect(()=>{
    let ignore=false;
    setState({data:null,error:null});
    loadReport(reportId).then(
      data=>{if(!ignore)setState({data,error:null});},
      (error:unknown)=>{if(!ignore)setState({data:null,error});},
    );
    return ()=>{ignore=true;};
  },[reportId,loadReport]);

  return state;
}

// Nakładka zgodna ze starym API: klasy nadal jej używają, dopóki ich nie przepiszesz.
export function withReport<P extends {report:ReportState}>(WrappedComponent:ComponentType<P>,loadReport:LoadReport){
  function WithReport(props:Omit<P,"report">&{reportId:number}){
    const report=useReport(props.reportId,loadReport);
    return <WrappedComponent {...(props as unknown as P)} report={report}/>;
  }
  WithReport.displayName="withReport("+(WrappedComponent.displayName??WrappedComponent.name)+")";
  return WithReport;
}

// Render props: to samo API co dawniej, ale logika jest już w hooku.
export function ReportLoader({reportId,loadReport,children}:{reportId:number;loadReport:LoadReport;children:(state:ReportState)=>React.ReactNode}){
  return <>{children(useReport(reportId,loadReport))}</>;
}`;
const legacyConsumer=String.raw`import {Component} from "react";
import type {ReportState} from "./useReport";

// Starsza klasa nadal działa przez nakładkę withReport.
export class LegacySummary extends Component<{report:ReportState;prefix:string}>{
  render(){
    const {report,prefix}=this.props;
    return <p>{prefix}: {report.data?report.data.title:"brak"}</p>;
  }
}`;
const pitfalls=String.raw`import {useEffect,useRef,useState} from "react";

type Filters={status:string;query:string};

// Setter nie scala: bez kopii znika pole query.
export function mergeWrong(_previous:Filters,status:string){
  return {status} as Filters;
}

export function mergeRight(previous:Filters,status:string):Filters{
  return {...previous,status};
}

export function Subscriber({userId,log}:{userId:number;log:(text:string)=>void}){
  useEffect(()=>{
    log("subscribe "+userId);
    return ()=>log("unsubscribe "+userId);
  },[userId,log]);
  return null;
}

// Wersja z pustą tablicą zależności, czyli dosłowne tłumaczenie componentDidMount.
export function StaleSubscriber({userId,log}:{userId:number;log:(text:string)=>void}){
  useEffect(()=>{
    log("subscribe "+userId);
    return ()=>log("unsubscribe "+userId);
    // eslint-disable-next-line react-hooks/exhaustive-deps -- celowy przykład błędu
  },[]);
  return null;
}

// Pole instancji przepisane na ref: zmiana nie powoduje renderu.
export function ClickTimer({onRender}:{onRender:()=>void}){
  const lastClick=useRef(0);
  onRender();
  return <button onClick={()=>{lastClick.current=Date.now();}}>Ref</button>;
}

// Dosłowne tłumaczenie pola na stan powoduje niepotrzebny render.
export function ClickTimerState({onRender}:{onRender:()=>void}){
  const [,setLastClick]=useState(0);
  onRender();
  return <button onClick={()=>setLastClick(value=>value+1)}>Stan</button>;
}`;
const mapping=String.raw`const MAPPING:Record<string,string>={
  "this.state":"useState lub useReducer",
  "this.setState":"setter z useState, który nie scala",
  "componentDidMount":"useEffect z []",
  "componentWillUnmount":"sprzątanie w useEffect",
  "componentDidUpdate":"useEffect z zależnościami",
  "shouldComponentUpdate":"memo",
  "PureComponent":"memo",
  "getDerivedStateFromProps":"obliczenie w renderze lub key",
  "createRef":"useRef",
  "contextType":"useContext",
  "getSnapshotBeforeUpdate":"useLayoutEffect",
  "componentDidCatch":"brak odpowiednika",
  "getDerivedStateFromError":"brak odpowiednika",
};

export function hookFor(name:string){
  return MAPPING[name]??"nieznane";
}

export type ComponentInfo={name:string;isErrorBoundary:boolean;usedBy:number;changedRecently:boolean;hasBehaviorTests:boolean;usesEnzyme:boolean};

export function migrationDecision(info:ComponentInfo){
  if(info.isErrorBoundary)return "zostaw klasę";
  if(info.usesEnzyme)return "najpierw przepisz testy";
  if(!info.changedRecently)return "zostaw";
  if(!info.hasBehaviorTests)return "najpierw napisz testy";
  return "migruj";
}

// Od liści do korzenia: komponent bez dzieci-klas migrujemy najpierw.
export function migrationOrder(children:Record<string,readonly string[]>){
  const done:string[]=[];
  const pending=new Set(Object.keys(children));
  while(pending.size){
    const ready=[...pending].filter(name=>(children[name]??[]).every(child=>!pending.has(child))).sort();
    if(!ready.length)throw new Error("Cykl zależności");
    for(const name of ready){done.push(name);pending.delete(name);}
  }
  return done;
}`;
const tests=String.raw`import {afterEach,describe,expect,test,vi} from "vitest";
import {act,fireEvent,render,screen} from "@testing-library/react";
import type {ComponentType} from "react";
import {ReportPanelClass} from "./ReportPanelClass";
import {ReportPanelHooks} from "./ReportPanelHooks";
import {ReportLoader,useReport,withReport,type ReportState} from "./useReport";
import {LegacySummary} from "./LegacySummary";
import {ClickTimer,ClickTimerState,mergeRight,mergeWrong,StaleSubscriber,Subscriber} from "./pitfalls";
import {hookFor,migrationDecision,migrationOrder} from "./classToHooks";
import type {PanelProps,Report} from "./reportTypes";

const report=(title:string,count=1):Report=>({title,count});
function deferred<T>(){
  let resolve!:(value:T)=>void;
  const promise=new Promise<T>(yes=>{resolve=yes;});
  return {promise,resolve};
}

afterEach(()=>{vi.useRealTimers();});

// Ten sam zestaw dla obu wersji: testy zachowania mają przejść bez zmian.
const versions:[string,ComponentType<PanelProps>][]=[["klasa",ReportPanelClass],["hooki",ReportPanelHooks]];
describe.each(versions)("ReportPanel: %s",(_name,Panel)=>{
  test("ładowanie i dane",async()=>{
    render(<Panel reportId={1} loadReport={async()=>report("Styczeń",4)}/>);
    expect(screen.getByText("Ładowanie raportu…")).toBeVisible();
    expect(await screen.findByRole("heading",{name:"Styczeń"})).toBeVisible();
    expect(screen.getByText("Zgłoszeń: 4")).toBeVisible();
  });

  test("błąd",async()=>{
    render(<Panel reportId={1} loadReport={async()=>{throw new Error("x");}}/>);
    expect(await screen.findByRole("alert")).toHaveTextContent("Nie udało się");
  });

  test("zmiana raportu i odrzucenie spóźnionej odpowiedzi",async()=>{
    const one=deferred<Report>(),two=deferred<Report>();
    const load=(id:number)=>id===1?one.promise:two.promise;
    const view=render(<Panel reportId={1} loadReport={load}/>);
    view.rerender(<Panel reportId={2} loadReport={load}/>);
    await act(async()=>two.resolve(report("Nowy")));
    expect(screen.getByRole("heading",{name:"Nowy"})).toBeVisible();
    await act(async()=>one.resolve(report("Stary")));
    expect(screen.queryByRole("heading",{name:"Stary"})).toBeNull();
  });

  test("automatyczne odświeżanie i sprzątanie licznika",async()=>{
    vi.useFakeTimers();
    const load=vi.fn(async()=>report("Raport"));
    const view=render(<Panel reportId={1} loadReport={load} refreshMs={1000}/>);
    await act(async()=>{await vi.advanceTimersByTimeAsync(0);});
    expect(load).toHaveBeenCalledTimes(1);
    fireEvent.click(screen.getByRole("checkbox"));
    // Każdy tik osobno: act grupuje aktualizacje, więc dwa tiki naraz dałyby jedno pobranie w wersji z hookami.
    await act(async()=>{await vi.advanceTimersByTimeAsync(1000);});
    await act(async()=>{await vi.advanceTimersByTimeAsync(1000);});
    expect(load).toHaveBeenCalledTimes(3);
    view.unmount();
    expect(vi.getTimerCount()).toBe(0);
    await vi.advanceTimersByTimeAsync(5000);
    expect(load).toHaveBeenCalledTimes(3);
    vi.useRealTimers();
  });
});

test("hook uwspólnia logikę i odrzuca starą odpowiedź",async()=>{
  const one=deferred<Report>(),two=deferred<Report>();
  const load=(id:number)=>id===1?one.promise:two.promise;
  function View({id}:{id:number}){
    const {data}=useReport(id,load);
    return <p>{data?.title??"pusto"}</p>;
  }
  const view=render(<View id={1}/>);
  view.rerender(<View id={2}/>);
  await act(async()=>two.resolve(report("B")));
  await act(async()=>one.resolve(report("A")));
  expect(screen.getByText("B")).toBeVisible();
});

test("nakładka HOC przekazuje stan do starej klasy i zachowuje props",async()=>{
  const Wrapped=withReport(LegacySummary,async id=>report("Raport "+id));
  expect(Wrapped.displayName).toBe("withReport(LegacySummary)");
  render(<Wrapped reportId={7} prefix="Podsumowanie"/>);
  expect(await screen.findByText("Podsumowanie: Raport 7")).toBeVisible();
});

test("render props działają na tym samym hooku",async()=>{
  render(<ReportLoader reportId={1} loadReport={async()=>report("R")}>{(state:ReportState)=><p>{state.data?.title??"czekam"}</p>}</ReportLoader>);
  expect(screen.getByText("czekam")).toBeVisible();
  expect(await screen.findByText("R")).toBeVisible();
});

test("setter nie scala: dosłowne tłumaczenie gubi pole",()=>{
  const start={status:"ALL",query:"awaria"};
  expect(mergeWrong(start,"OPEN").query).toBeUndefined();
  expect(mergeRight(start,"OPEN")).toEqual({status:"OPEN",query:"awaria"});
});

test("zależność userId odnawia subskrypcję, a pusta tablica zostaje przy starej",()=>{
  const good:string[]=[],stale:string[]=[];
  const log=(text:string)=>good.push(text),logStale=(text:string)=>stale.push(text);
  const a=render(<Subscriber userId={1} log={log}/>);
  a.rerender(<Subscriber userId={2} log={log}/>);
  expect(good).toEqual(["subscribe 1","unsubscribe 1","subscribe 2"]);
  const b=render(<StaleSubscriber userId={1} log={logStale}/>);
  b.rerender(<StaleSubscriber userId={2} log={logStale}/>);
  expect(stale).toEqual(["subscribe 1"]);
});

test("pole instancji jako ref nie powoduje renderu",()=>{
  const ref=vi.fn(),state=vi.fn();
  render(<ClickTimer onRender={ref}/>);
  fireEvent.click(screen.getByRole("button",{name:"Ref"}));
  expect(ref).toHaveBeenCalledTimes(1);
  render(<ClickTimerState onRender={state}/>);
  fireEvent.click(screen.getByRole("button",{name:"Stan"}));
  expect(state).toHaveBeenCalledTimes(2);
});

test("mapowanie i decyzja o migracji",()=>{
  expect(hookFor("componentDidCatch")).toBe("brak odpowiednika");
  expect(hookFor("inne")).toBe("nieznane");
  const info={name:"A",isErrorBoundary:false,usedBy:1,changedRecently:true,hasBehaviorTests:true,usesEnzyme:false};
  expect(migrationDecision(info)).toBe("migruj");
  expect(migrationDecision({...info,isErrorBoundary:true})).toBe("zostaw klasę");
  expect(migrationDecision({...info,usesEnzyme:true})).toBe("najpierw przepisz testy");
  expect(migrationDecision({...info,changedRecently:false})).toBe("zostaw");
  expect(migrationDecision({...info,hasBehaviorTests:false})).toBe("najpierw napisz testy");
});

test("kolejność migracji od liści",()=>{
  expect(migrationOrder({Strona:["Tabela"],Tabela:["Wiersz","Filtr"],Wiersz:[],Filtr:[]})).toEqual(["Filtr","Wiersz","Tabela","Strona"]);
  expect(()=>migrationOrder({a:["b"],b:["a"]})).toThrow("Cykl");
});`;
const exercises=[
['Zmień sposób myślenia o kodzie klasy.','Moment czy cel?','Klasa grupuje kod według momentu: montowanie, aktualizacja, odmontowanie. Hooki grupują według celu: synchronizacja z timerem, pobieranie danych. Jeden Effect zastępuje fragmenty trzech metod, więc najpierw nazwij cele, a dopiero potem pisz kod.'],
['Napisz testy zachowania na wersji klasowej.','RTL przed migracją.',tests],
['Uruchom ten sam zestaw na obu wersjach.','describe.each z dwoma komponentami.',tests],
['Przenieś stan na osobne useState.','Niezależne wartości osobno.',hookVersion],
['Połącz pobieranie w jeden Effect.','Zależność od reportId zamiast porównania.',hookVersion],
['Połącz timer w drugi Effect.','Trzy metody stają się jednym celem.',hookVersion],
['Obsłuż wyścig przez zmienną ignore.','Zmienna lokalna Effectu.',hookVersion],
['Zastąp pole timera zmienną lokalną.','Pole instancji bywa niepotrzebne.',hookVersion],
['Odśwież przez licznik wersji.','Zmiana stanu uruchamia Effect pobierania.',hookVersion],
['Wydziel useReport.','Logika kopiowana w czterech klasach trafia do jednego miejsca.',useReport],
['Napisz nakładkę HOC na hooku.','Stare klasy działają bez zmian.',useReport],
['Zachowaj displayName nakładki.','Pomaga w DevTools.',useReport],
['Napisz render props na hooku.','Stare API, nowa logika.',useReport],
['Podłącz starą klasę przez nakładkę.','Przejście bez przepisywania wszystkiego naraz.',legacyConsumer],
['Usuń nakładkę, gdy nikt jej nie używa.','Ma być tymczasowa.','Znajdź użycia przez grep i TS. Gdy zostało ostatnie, przepisz komponent na const report=useReport(id,load) i usuń withReport. Zaplanuj to w zadaniu, bo tymczasowa nakładka łatwo zostaje na lata.'],
['Napraw dosłowne tłumaczenie setState.','Setter nie scala.',pitfalls],
['Napraw Effect z pustą tablicą zależności.','Efekt czyta props.',pitfalls],
['Zamień Effect „po zapisie” na handler.','Logika zdarzenia w zdarzeniu.','// Źle: useEffect(()=>{if(saved)onSaved();},[saved]);\n// Dobrze:\nfunction handleSave(){\n  save();\n  onSaved();\n}\n// Effect jest potrzebny do synchronizacji z zewnętrznym systemem, a nie do reakcji na kliknięcie.'],
['Zamień pole instancji na ref, nie na stan.','Wartości nie ma na ekranie.',pitfalls],
['Zmapuj elementy klasy na hooki.','Tabela do przeglądu kodu.',mapping],
['Zostaw granicę błędów jako klasę.','Brak odpowiednika w hookach.',mapping],
['Zdecyduj o migracji komponentu.','Stabilny kod nie musi się zmieniać.',mapping],
['Ustal kolejność od liści.','Sortowanie zależności.',mapping],
['Przepisz testy Enzyme przed migracją.','wrapper.state() nie przeżyje zmiany.','Sprawdzanie wrapper.state("loading") zastąp asercją, że widać tekst ładowania. wrapper.instance().load() zastąp działaniem użytkownika. Enzyme nie wspiera React 18+, więc i tak trzeba to zrobić przy aktualizacji.'],
['Usuń PropTypes po migracji.','React 19 ich nie sprawdza.','W TSX zastępują je typy props. W JSX przejdź na TS albo waliduj dane z API schematem. Same PropTypes nie dają już sprawdzania przez Reacta, więc pozostawione dają złudne poczucie ochrony.'],
['Rozważ TanStack Query po migracji.','Własny timer można usunąć.','useQuery({queryKey:["report",id],queryFn:()=>loadReport(id),refetchInterval:autoRefresh?30_000:false}). Wyścig, cache i ponowienia obsługuje biblioteka. Zrób to osobnym krokiem, bo pierwszy PR ma tylko zachować zachowanie.'],
['Oceń różnice w typach po migracji.','Czego ubywa?','Znika typ State, deklaracje pól i Component<Props,State>. Zostają typy props i generyk tam, gdzie wartość startowa nie mówi wszystkiego, np. useState<Report|null>(null). Zmienne lokalne Effectu nie potrzebują adnotacji.'],
['Porównaj wynik migracji.','Mierz, a nie zakładaj.','Policz linie i miejsca obsługi timera przed i po, sprawdź liczbę kopii logiki pobierania. Testy muszą przechodzić bez zmian. Wynik zapisz w opisie PR, aby było widać, czy migracja przyniosła korzyść.'],
['Zaplanuj migrację modułu.','Jeden komponent na PR.','1. Testy zachowania. 2. Liście. 3. useReport i nakładka. 4. Pozostałe klasy. 5. Usunięcie nakładki i PropTypes. 6. Ewentualnie TanStack Query. Klasy i funkcje współpracują, więc moduł działa po każdym kroku.'],
['Sprawdź kryterium akceptacji lekcji.','Klasy, hook, testy, wyścig.','Moduł nie ma komponentów klasowych poza granicą błędów, logika pobierania jest w jednym hooku, testy napisane przed migracją przechodzą bez zmian, a wyścig odpowiedzi przy zmianie reportId jest obsłużony.'],
];
const concepts=[
['Jak klasa grupuje kod?','Według momentu w cyklu życia','Według celu','Według pliku','Według typu','Hooki grupują według celu.'],
['Jak hooki grupują kod?','Według celu','Według momentu','Według klasy','Alfabetycznie','Jeden Effect to jedna synchronizacja.'],
['Na co przepisać this.state z niezależnymi polami?','Osobne useState','Jeden obiekt zawsze','Refy','Kontekst','Łatwiej aktualizować.'],
['Kiedy useReducer zamiast wielu useState?','Gdy pola zmieniają się razem','Zawsze','Nigdy','Dla jednego boolean','Jedna akcja może zmienić wiele pól.'],
['Czy setter useState scala obiekt?','Nie','Tak','Tylko płytko','Tylko w TS','Trzeba skopiować poprzednie pola.'],
['Na co przepisać parę mount i unmount?','Jeden useEffect ze sprzątaniem','Dwa useEffect','useMemo','useRef','Podłączenie i odłączenie są razem.'],
['Na co przepisać porównanie prevProps?','useEffect z zależnością','useMemo','useRef','Warunek w render','Porównanie robi React.'],
['Na co przepisać pole instancji?','useRef lub zmienną lokalną Effectu','useState zawsze','Kontekst','Props','Zmiana nie ma powodować renderu.'],
['Na co przepisać PureComponent?','memo','useMemo','useCallback','useRef','Po pomiarze.'],
['Na co przepisać createRef?','useRef','useState','forwardRef','useId','Ten sam cel.'],
['Na co przepisać static contextType?','useContext','useRef','Props','useReducer','Czytelniejszy zapis.'],
['Na co przepisać getSnapshotBeforeUpdate?','useLayoutEffect','useEffect','useMemo','Nic','Pomiar przed malowaniem.'],
['Co nie ma odpowiednika w hookach?','Granica błędów','useEffect','useState','memo','Klasa lub biblioteka.'],
['Na co przepisać getDerivedStateFromProps?','Obliczenie w renderze lub key','useEffect zawsze','useRef','Kontekst','Effect dałby dodatkowy render.'],
['Czym grozi pusta tablica zależności, gdy Effect czyta props?','Zostaje stara wartość','Pętlą','Błędem składni','Niczym','Klasyczny błąd tłumaczenia.'],
['Czy Effect zastąpi callback setState?','Często lepszy jest handler','Tak, zawsze','Nigdy','Tylko w TS','Logika zdarzenia należy do zdarzenia.'],
['Czym grozi pole instancji przepisane na stan?','Niepotrzebnymi renderami','Wyciekiem','Brakiem klucza','Błędem CSS','Wartości nie ma na ekranie.'],
['Czym jest HOC?','Funkcją zwracającą nowy komponent','Hookiem','Plikiem CSS','Rodzajem refa','Dokłada props.'],
['Czym są render props?','Prop będący funkcją zwracającą JSX','Atrybut HTML','Stan','Trasa','Wzorzec współdzielenia logiki.'],
['Jaki problem mają HOC?','Zagnieżdżenie i ukryte pochodzenie props','Są wolne z definicji','Nie działają w 19','Są zabronione','„Wrapper hell”.'],
['Co jest ryzykiem HOC przy nazwach props?','Kolizja','Brak typu','Brak renderu','Błąd CSS','Dwa HOC mogą dać ten sam prop.'],
['Co zastępuje HOC i render props?','Własny hook','Klasa','Redux','Plik JSON','Bez dodatkowych komponentów.'],
['Czy hook działa w klasie?','Nie','Tak','Tylko useRef','Tylko useContext','Stąd nakładka HOC.'],
['Po co nakładka nad hookiem?','Klasy działają w trakcie przejścia','Dla szybkości','Dla CSS','Na stałe','Usuń ją na końcu.'],
['Po co displayName w HOC?','Czytelne DevTools','Dla produkcji','Dla bezpieczeństwa','Dla SSR','Inaczej widać anonimowy komponent.'],
['Kiedy migrować?','Gdy i tak zmieniasz komponent','Zawsze od razu','Nigdy','Tylko w piątek','Migracja bez powodu to ryzyko.'],
['Kiedy zostawić klasę?','Stabilna, przetestowana i nikt jej nie zmienia','Gdy jest krótka','Nigdy','Gdy ma props','Brak korzyści, a jest ryzyko.'],
['Od czego zacząć migrację drzewa?','Od liści','Od korzenia','Od granicy błędów','Od routera','Małe komponenty są najprostsze.'],
['Ile komponentów na pull request?','Jeden','Wszystkie','Dziesięć','Żaden','Łatwo przejrzeć i cofnąć.'],
['Czy klasa i funkcja mogą współpracować?','Tak','Nie','Tylko przez HOC','Tylko w 17','Dlatego migracja może być stopniowa.'],
['Czemu testy przed migracją?','Dowodzą braku regresji','Dla pokrycia','Dla CSS','Bez powodu','Bez nich nie wiesz, czy nic nie zepsułeś.'],
['Które testy przeżyją migrację?','Sprawdzające ekran i działania użytkownika','wrapper.state()','wrapper.instance()','Migawki wewnętrzne','RTL nie zna implementacji.'],
['Dlaczego Enzyme jest problemem?','Sięga do wnętrza i nie wspiera React 18+','Jest wolny','Nie ma typów','Nie działa w CI','Trzeba przepisać testy.'],
['Co robi describe.each w tej lekcji?','Uruchamia te same testy na dwóch wersjach','Tworzy klasy','Mockuje React','Dzieli pliki','Dowód równoważności.'],
['Czy wyścig zniknie sam po migracji?','Nie, trzeba go obsłużyć','Tak','Tak, z memo','Tak, w TS','Zmienna ignore lub AbortController.'],
['Jak działa zmienna ignore?','Sprzątanie Effectu blokuje stary wynik','Blokuje sieć','Usuwa cache','Zmienia zależności','Nowy Effect ma swoją kopię.'],
['Czy ignore przerywa żądanie?','Nie, tylko ignoruje wynik','Tak','Tak, w fetch','Tak, w Node','AbortController przerywa je naprawdę.'],
['Co oznacza licznik version w Effect?','Zmiana wymusza ponowne pobranie','Numer pakietu','Wersję Reacta','Stan timera','Prosty sposób na ponowienie.'],
['Czy timer musi pobierać w callbacku?','Może zmieniać version','Musi','Nie może','Tylko w klasie','Pobieranie zostaje w jednym miejscu.'],
['Czego ubywa w TSX po migracji?','Typów State i pól instancji','Typów props','Unii','Wszystkich typów','Inferencja robi więcej.'],
['Kiedy nadal potrzebny generyk useState?','Gdy startowa wartość nie mówi wszystkiego','Zawsze','Nigdy','Tylko dla boolean','np. Report|null.'],
['Co zrobić z PropTypes?','Usunąć, zastąpić typami lub walidacją','Zostawić dla React 19','Dodać więcej','Przenieść do CSS','React już ich nie sprawdza.'],
['Co oznacza „zachowanie bez zmian” w PR?','Testy przechodzą bez modyfikacji','Brak nowych plików','Brak typów','Brak hooków','Zmiana testów to sygnał ostrzegawczy.'],
['Czy zmieniać bibliotekę w tym samym PR?','Nie, osobnym krokiem','Tak','Zawsze','Tylko Redux','Łatwiej znaleźć przyczynę regresji.'],
['Co daje TanStack Query po migracji?','Cache, wyścig i refetchInterval','Klasy','Lepszy CSS','Brak API','Własny timer znika.'],
['Czym jest kopia logiki w czterech klasach?','Powodem do wydzielenia hooka','Dobrą praktyką','Wymogiem klasy','Problemem CSS','Między klasami trudno współdzielić logikę.'],
['Jaki jest główny zysk po migracji?','Logika jednego celu w jednym miejscu','Mniejszy Node','Lepszy kontrast','Brak testów','Timer nie jest w trzech metodach.'],
['Co jest ryzykiem migracji na ślepo?','Regresje i niekończąca się praca','Mniejsza paczka','Lepsze typy','Więcej testów','Dlatego plan i priorytety.'],
['Jak traktować ostrzeżenia exhaustive-deps?','Jako sygnał błędu, nie do wyciszenia','Wyciszać zawsze','Ignorować','Wyłączyć regułę','Wyjątek wymaga uzasadnienia.'],
['Jak Effect z setInterval sprzątać?','clearInterval w funkcji sprzątającej','W onClick','Nie trzeba','W render','Inaczej powstanie wyciek.'],
['Czy można używać useEffect zamiast obliczenia w renderze?','Lepiej obliczać w renderze','Tak, zawsze','Tak, szybciej','Tak, w TS','Effect dokłada render i opóźnienie.'],
['Czym jest stan wyliczany?','Wartość, którą można obliczyć z props lub stanu','Stan z API','Ref','Klucz','Nie przechowuj go osobno.'],
['Reset formularza po zmianie id?','key na komponencie','Effect z setState','forceUpdate','Klasa','Najprostszy zapis zamiast getDerivedStateFromProps.'],
['Co robi memo?','Pomija render przy tych samych props','Zapisuje dane','Tworzy ref','Zastępuje Effect','Płytkie porównanie.'],
['Co zrobić przed dodaniem memo przy migracji?','Zmierzyć','Dodać wszędzie','Usunąć testy','Dodać useMemo','Bez pomiaru to tylko złożoność.'],
['Co jest pierwszym krokiem w module raportów?','Testy na wersji klasowej','Usunięcie klas','Dodanie Query','Przepisanie HOC','Później dowodzą braku regresji.'],
['Czy granica błędów może mieć funkcyjny fallback?','Tak, sam fallback to zwykły komponent','Nie','Tylko klasa','Tylko HOC','Klasą jest sama granica.'],
['Który komponent przepisać pierwszy?','Liść bez zależności','Strona z pięcioma dziećmi','Granicę błędów','Provider całej aplikacji','Najmniejsze ryzyko.'],
['Jak znaleźć ostatnie użycia nakładki?','Wyszukiwanie i TS','Pamięć','Komentarz','Usunąć i zobaczyć na produkcji','Dopiero potem usuń.'],
['Co oznacza gotowość do usunięcia klasy?','Testy zielone na wersji z hookami','Mniej linii','Nowy CSS','Zgoda lintera','Testy są głównym dowodem.'],
];
const header=String.raw`import {hookFor,migrationDecision,migrationOrder} from "./classToHooks";
import {mergeRight,mergeWrong} from "./pitfalls";
const info={name:"A",isErrorBoundary:false,usedBy:1,changedRecently:true,hasBehaviorTests:true,usesEnzyme:false};
const start={status:"ALL",query:"x"};`;
const H=['useEffect z []','memo','useRef','brak odpowiednika'];
const D=['migruj','zostaw','zostaw klasę','najpierw napisz testy'];
const probeRows=[
['Odpowiednik componentDidMount','hookFor("componentDidMount")','useEffect z []','Montowanie.',['useRef','memo','brak odpowiednika']],
['Odpowiednik componentWillUnmount','hookFor("componentWillUnmount")','sprzątanie w useEffect','Ten sam Effect.',['useRef','memo','brak odpowiednika']],
['Odpowiednik componentDidUpdate','hookFor("componentDidUpdate")','useEffect z zależnościami','Porównanie robi React.',['useRef','memo','brak odpowiednika']],
['Odpowiednik PureComponent','hookFor("PureComponent")','memo','Płytkie porównanie.',['useRef','useEffect z []','brak odpowiednika']],
['Odpowiednik shouldComponentUpdate','hookFor("shouldComponentUpdate")','memo','Też pomija render.',['useRef','useEffect z []','brak odpowiednika']],
['Odpowiednik createRef','hookFor("createRef")','useRef','Ten sam cel.',['memo','useEffect z []','brak odpowiednika']],
['Odpowiednik contextType','hookFor("contextType")','useContext','Odczyt kontekstu.',['useRef','memo','brak odpowiednika']],
['Odpowiednik getSnapshotBeforeUpdate','hookFor("getSnapshotBeforeUpdate")','useLayoutEffect','Pomiar przed malowaniem.',['useRef','memo','brak odpowiednika']],
['Odpowiednik componentDidCatch','hookFor("componentDidCatch")','brak odpowiednika','Klasa lub biblioteka.',H.slice(0,3)],
['Odpowiednik getDerivedStateFromError','hookFor("getDerivedStateFromError")','brak odpowiednika','Klasa lub biblioteka.',H.slice(0,3)],
['Odpowiednik getDerivedStateFromProps','hookFor("getDerivedStateFromProps")','obliczenie w renderze lub key','Zwykle bez stanu.',['useRef','memo','brak odpowiednika']],
['Nieznana nazwa','hookFor("render")','nieznane','Render zostaje jako ciało funkcji.',['useRef','memo','brak odpowiednika']],
['Decyzja: zwykły komponent ze zmianami i testami','migrationDecision(info)','migruj','Warunki spełnione.',D],
['Decyzja: granica błędów','migrationDecision({...info,isErrorBoundary:true})','zostaw klasę','Brak odpowiednika.',D],
['Decyzja: stabilny komponent','migrationDecision({...info,changedRecently:false})','zostaw','Brak powodu.',D],
['Decyzja: brak testów','migrationDecision({...info,hasBehaviorTests:false})','najpierw napisz testy','Bez nich nie ma dowodu.',D],
['Decyzja: Enzyme','migrationDecision({...info,usesEnzyme:true})','najpierw przepisz testy','Testy nie przeżyją migracji.',['migruj','zostaw','najpierw napisz testy']],
['Decyzja: Enzyme i stabilny','migrationDecision({...info,usesEnzyme:true,changedRecently:false})','najpierw przepisz testy','Reguła jest wyżej w tym modelu.',['migruj','zostaw','najpierw napisz testy']],
['Decyzja: granica z Enzyme','migrationDecision({...info,isErrorBoundary:true,usesEnzyme:true})','zostaw klasę','Pierwszy warunek decyduje.',['migruj','zostaw','najpierw przepisz testy']],
['Liście najpierw','migrationOrder({A:["B"],B:[]}).join()','B,A','Dziecko przed rodzicem.',['A,B','A','B']],
['Dwa liście','migrationOrder({A:["B","C"],B:[],C:[]}).join()','B,C,A','Remis alfabetycznie.',['A,B,C','C,B,A','B,A,C']],
['Trzy poziomy','migrationOrder({A:["B"],B:["C"],C:[]}).join()','C,B,A','Od najgłębszego.',['A,B,C','B,C,A','C,A,B']],
['Dziecko spoza listy','migrationOrder({A:["Funkcja"]}).join()','A','Funkcja nie wymaga migracji.',['Funkcja,A','Funkcja','pusto']],
['Pusta lista','migrationOrder({}).length',0,'Nic do zrobienia.'],
['Cykl','(()=>{try{migrationOrder({A:["B"],B:["A"]});return "ok";}catch(e){return (e as Error).message;}})()','Cykl zależności','Sygnał do rozwiązania cyklu.',['ok','A','B']],
['Scalenie: złe, status','mergeWrong(start,"OPEN").status','OPEN','Nowe pole jest.',['ALL','undefined','x']],
['Scalenie: złe, query','String(mergeWrong(start,"OPEN").query)','undefined','Pole zniknęło.',['x','ALL','OPEN']],
['Scalenie: dobre, query','mergeRight(start,"OPEN").query','x','Kopia zachowuje pole.',['undefined','ALL','OPEN']],
['Scalenie: dobre, status','mergeRight(start,"OPEN").status','OPEN','Pole zmienione.',['ALL','x','undefined']],
['Czy poprawne scalenie mutuje wejście?','(()=>{mergeRight(start,"OPEN");return start.status;})()','ALL','Nowy obiekt.',['OPEN','x','undefined']],
['Setter a scalanie w czystym JS','JSON.stringify((prev=>({status:"OPEN"}))({status:"ALL",query:"x"}))','{"status":"OPEN"}','Tak działa dosłowne tłumaczenie.',['{"status":"OPEN","query":"x"}','{"query":"x"}','{}']],
['Spread z nadpisaniem','JSON.stringify({...{a:1,b:2},b:3})','{"a":1,"b":3}','Późniejsze pole wygrywa.',['{"a":1,"b":2}','{"b":3}','{"a":1,"b":2,"b":3}']],
['Zmienna lokalna w domknięciu: ignore','(()=>{let ignore=false;const clean=()=>{ignore=true;};const seen=()=>ignore;clean();return seen();})()',true,'Sprzątanie zmienia flagę tego przebiegu.'],
['Dwa domknięcia mają osobne flagi','(()=>{const make=()=>{let i=false;return {clean(){i=true;},get(){return i;}};};const a=make(),b=make();a.clean();return [a.get(),b.get()].join();})()','true,false','Nowy Effect ma własną flagę.',['true,true','false,false','false,true']],
['Czy useRef zmienia się bez renderu?','(()=>{const ref={current:0};ref.current=5;return ref.current;})()',5,'To zwykły obiekt.'],
['Ile razy klasa wymagała metod dla timera?','["componentDidUpdate","componentWillUnmount","toggleTimer"].length',3,'W hookach jeden Effect.'],
['Liczba Effectów w ReportPanelHooks','["pobieranie","timer"].length',2,'Dwa cele, dwa Effecty.'],
['Czy hookFor rozróżnia wielkość liter?','hookFor("purecomponent")','nieznane','Klucze są dokładne.',['memo','useRef','brak odpowiednika']],
['Czy pusta nazwa jest znana?','hookFor("")','nieznane','Brak wpisu.',['memo','useRef','brak odpowiednika']],
['Kolejność nie zależy od zapisu','migrationOrder({B:[],A:["B"]}).join()','B,A','Wynik wynika z zależności.',['A,B','A','B']],
];
const project={
'src/reportTypes.ts':types,'src/ReportPanelClass.tsx':classVersion,'src/ReportPanelHooks.tsx':hookVersion,'src/useReport.tsx':useReport,'src/LegacySummary.tsx':legacyConsumer,'src/pitfalls.tsx':pitfalls,'src/classToHooks.ts':mapping,'src/migration.test.tsx':tests,
'README.md':'DevDesk: migracja klas na hooki. npm install, npm test. Ten sam zestaw testów działa na wersji klasowej i na hookach, co jest dowodem równoważności w sprawdzonych scenariuszach. Model decyzji o migracji pokazuje kolejność rozumowania i nie zastępuje oceny konkretnego modułu.\n',
};
const mutants=[
{file:'src/ReportPanelHooks.tsx',before:'report=>{if(!ignore)setData(report);}',after:'report=>{setData(report);}'},
{file:'src/ReportPanelHooks.tsx',before:'[reportId,version,loadReport]',after:'[version,loadReport]'},
{file:'src/ReportPanelHooks.tsx',before:'return ()=>clearInterval(id);',after:''},
{file:'src/ReportPanelHooks.tsx',before:'if(error)return <p role="alert">Nie udało się wczytać raportu.</p>;',after:''},
{file:'src/useReport.tsx',before:'data=>{if(!ignore)setState({data,error:null});}',after:'data=>{setState({data,error:null});}'},
{file:'src/useReport.tsx',before:'{...(props as unknown as P)} report={report}',after:'{...({} as unknown as P)} report={report}'},
{file:'src/pitfalls.tsx',before:'[userId,log]',after:'[log]'},
{file:'src/classToHooks.ts',before:'if(info.isErrorBoundary)return "zostaw klasę";',after:''},
{file:'src/classToHooks.ts',before:'if(!ready.length)throw new Error("Cykl zależności");',after:'if(!ready.length)return done;'},
];
module.exports=lesson({exercises,concepts,probeRows,project,mutants,header});
