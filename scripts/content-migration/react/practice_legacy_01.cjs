const {lesson}=require('./practice_kit.cjs');
const panel=String.raw`import {Component} from "react";

export type Report={title:string;count:number};
export type LoadReport=(id:number)=>Promise<Report>;
type Props={reportId:number;loadReport:LoadReport;refreshMs?:number};
type State={data:Report|null;loading:boolean;error:unknown;autoRefresh:boolean};

export class ReportPanel extends Component<Props,State>{
  state:State={data:null,loading:true,error:null,autoRefresh:false};
  private timer:ReturnType<typeof setInterval>|undefined;
  private cancelled=false;
  private requestId=0;

  componentDidMount(){
    this.cancelled=false;
    void this.load();
  }

  componentDidUpdate(prevProps:Props,prevState:State){
    if(prevProps.reportId!==this.props.reportId)void this.load();
    if(prevState.autoRefresh!==this.state.autoRefresh)this.toggleTimer();
  }

  componentWillUnmount(){
    clearInterval(this.timer);
    this.cancelled=true;
  }

  async load(){
    const request=++this.requestId;
    this.setState({loading:true,error:null});
    try{
      const data=await this.props.loadReport(this.props.reportId);
      if(!this.cancelled&&request===this.requestId)this.setState({data,loading:false});
    }catch(error){
      if(!this.cancelled&&request===this.requestId)this.setState({error,loading:false});
    }
  }

  toggleTimer(){
    clearInterval(this.timer);
    if(this.state.autoRefresh)this.timer=setInterval(()=>void this.load(),this.props.refreshMs??30_000);
  }

  handleToggle=()=>{
    this.setState(prev=>({autoRefresh:!prev.autoRefresh}));
  };

  render(){
    const {data,loading,error,autoRefresh}=this.state;
    if(loading&&!data)return <p>Ładowanie raportu…</p>;
    if(error||!data)return <p role="alert">Nie udało się wczytać raportu.</p>;
    return (
      <section>
        <h2>{data.title}</h2>
        <label><input type="checkbox" checked={autoRefresh} onChange={this.handleToggle}/> Odświeżaj co 30 s</label>
        <p>Zgłoszeń: {data.count}</p>
      </section>
    );
  }
}`;
const counter=String.raw`import {Component} from "react";

type CounterState={count:number;label:string;range:{from:string;to:string}};

export class Counter extends Component<{step?:number},CounterState>{
  state:CounterState={count:0,label:"Licznik",range:{from:"2026-01-01",to:"2026-12-31"}};

  // Błąd: wszystkie trzy wywołania czytają tę samą wartość this.state.count.
  addThreeWrong=()=>{
    this.setState({count:this.state.count+1});
    this.setState({count:this.state.count+1});
    this.setState({count:this.state.count+1});
  };

  // Forma funkcyjna dostaje najnowszy stan z kolejki.
  addThree=()=>{
    this.setState(prev=>({count:prev.count+1}));
    this.setState(prev=>({count:prev.count+1}));
    this.setState((prev,props)=>({count:prev.count+(props.step??1)}));
  };

  // Scalanie jest płytkie: range jest zastępowany w całości.
  setFromWrong=(from:string)=>{
    this.setState({range:{from}} as Pick<CounterState,"range">);
  };

  setFrom=(from:string)=>{
    this.setState(prev=>({range:{...prev.range,from}}));
  };

  render(){
    const {count,label,range}=this.state;
    return (
      <div>
        <p data-testid="count">{count}</p>
        <p data-testid="label">{label}</p>
        <p data-testid="range">{range.from}–{String(range.to)}</p>
        <button onClick={this.addThreeWrong}>Źle +3</button>
        <button onClick={this.addThree}>Dobrze +3</button>
        <button onClick={()=>this.setFromWrong("2026-06-01")}>Źle zakres</button>
        <button onClick={()=>this.setFrom("2026-06-01")}>Dobrze zakres</button>
      </div>
    );
  }
}`;
const binding=String.raw`import {Component} from "react";

type Props={onResult:(value:string)=>void};

// Metoda przekazana jako handler traci this, więc odczyt this.props kończy się TypeError.
export class Unbound extends Component<Props>{
  handleClick(){
    this.props.onResult("działa");
  }
  render(){
    return <button onClick={this.handleClick}>Bez wiązania</button>;
  }
}

export class Bound extends Component<Props>{
  constructor(props:Props){
    super(props);
    this.handleClick=this.handleClick.bind(this);
  }
  handleClick(){
    this.props.onResult("bind");
  }
  render(){
    return <button onClick={this.handleClick}>Z bind</button>;
  }
}

export class Arrow extends Component<Props>{
  handleClick=()=>{
    this.props.onResult("strzałka");
  };
  render(){
    return <button onClick={this.handleClick}>Ze strzałką</button>;
  }
}`;
const legacy=String.raw`import {Component,PureComponent,createRef} from "react";

export type Row={id:number;title:string};

export class TicketRowLegacy extends PureComponent<{row:Row;onRender?:()=>void}>{
  render(){
    this.props.onRender?.();
    return <li>{this.props.row.title}</li>;
  }
}

type FilterProps={items:Row[]};
type FilterState={query:string;prevItems:Row[]|null};

export class FilteredList extends Component<FilterProps,FilterState>{
  state:FilterState={query:"",prevItems:null};

  // Nowa lista z zewnątrz czyści wyszukiwanie. Dziś zwykle wystarczy reset przez key.
  static getDerivedStateFromProps(props:FilterProps,state:FilterState):Partial<FilterState>|null{
    return props.items!==state.prevItems?{query:"",prevItems:props.items}:null;
  }

  private inputRef=createRef<HTMLInputElement>();

  componentDidMount(){
    this.inputRef.current?.focus();
  }

  render(){
    const visible=this.props.items.filter(item=>item.title.includes(this.state.query));
    return (
      <div>
        <input ref={this.inputRef} aria-label="Szukaj" value={this.state.query} onChange={event=>this.setState({query:event.target.value})}/>
        <ul>{visible.map(item=><li key={item.id}>{item.title}</li>)}</ul>
      </div>
    );
  }
}`;
const order=String.raw`import {Component} from "react";

// Dziennik pokazuje rzeczywistą kolejność metod cyklu życia.
export function createLifecycleLogger(log:string[]){
  return class Logger extends Component<{value:number}>{
    constructor(props:{value:number}){
      super(props);
      log.push("constructor");
    }
    static getDerivedStateFromProps(){
      log.push("getDerivedStateFromProps");
      return null;
    }
    componentDidMount(){log.push("componentDidMount");}
    shouldComponentUpdate(){log.push("shouldComponentUpdate");return true;}
    getSnapshotBeforeUpdate(){log.push("getSnapshotBeforeUpdate");return null;}
    componentDidUpdate(){log.push("componentDidUpdate");}
    componentWillUnmount(){log.push("componentWillUnmount");}
    render(){
      log.push("render");
      return <p>{this.props.value}</p>;
    }
  };
}

export function mutationPitfall(){
  const state={items:["a"]};
  const previous=state.items;
  state.items.push("b");
  return previous===state.items;
}`;
const lab=String.raw`import {act,fireEvent,render,screen} from "@testing-library/react";
import {Counter} from "./Counter";
import {Arrow,Bound,Unbound} from "./Binding";
import {createLifecycleLogger} from "./lifecycle";
import {TicketRowLegacy,FilteredList,type Row} from "./LegacyPatterns";

export function counterAfter(button:string,step?:number){
  const view=render(<Counter step={step}/>);
  try{
    fireEvent.click(screen.getByRole("button",{name:button}));
    return {
      count:screen.getByTestId("count").textContent,
      label:screen.getByTestId("label").textContent,
      range:screen.getByTestId("range").textContent,
    };
  }finally{view.unmount();}
}

export function bindingResult(kind:"unbound"|"bound"|"arrow"){
  const seen:string[]=[];
  const errors:unknown[]=[];
  const onError=(event:ErrorEvent)=>{errors.push(event.error);event.preventDefault();};
  window.addEventListener("error",onError);
  const original=console.error;
  console.error=()=>{};
  const Comp=kind==="unbound"?Unbound:kind==="bound"?Bound:Arrow;
  const view=render(<Comp onResult={value=>seen.push(value)}/>);
  fireEvent.click(screen.getByRole("button"));
  view.unmount();
  console.error=original;
  window.removeEventListener("error",onError);
  return seen[0]??(errors[0] instanceof TypeError?"TypeError":"brak");
}

export function lifecycle(step:"mount"|"update"|"unmount"){
  const log:string[]=[];
  const Logger=createLifecycleLogger(log);
  const view=render(<Logger value={1}/>);
  if(step==="mount"){view.unmount();return log.slice(0,log.indexOf("componentDidMount")+1).join(">");}
  log.length=0;
  view.rerender(<Logger value={2}/>);
  if(step==="update"){const out=log.join(">");view.unmount();return out;}
  log.length=0;
  view.unmount();
  return log.join(">");
}

export function pureRenders(sameObject:boolean){
  let count=0;
  const row:Row={id:1,title:"A"};
  // Stała funkcja: nowa strzałka przy każdym renderze też byłaby zmianą propa.
  const onRender=()=>{count++;};
  const view=render(<TicketRowLegacy row={row} onRender={onRender}/>);
  view.rerender(<TicketRowLegacy row={sameObject?row:{...row}} onRender={onRender}/>);
  view.unmount();
  return count;
}

export function filterAfterNewList(){
  const first=[{id:1,title:"Alfa"},{id:2,title:"Beta"}];
  const view=render(<FilteredList items={first}/>);
  try{
    const input=view.getByRole("textbox") as HTMLInputElement;
    fireEvent.change(input,{target:{value:"Al"}});
    const typed=view.getAllByRole("listitem").length;
    const focused=document.activeElement===input;
    act(()=>{view.rerender(<FilteredList items={[...first]}/>);});
    return {typed,focused,query:input.value,afterNew:view.getAllByRole("listitem").length};
  }finally{view.unmount();}
}`;
const tests=String.raw`import {afterEach,expect,test,vi} from "vitest";
import {act,fireEvent,render,screen} from "@testing-library/react";
import {ReportPanel,type Report} from "./ReportPanel";
import {bindingResult,counterAfter,filterAfterNewList,lifecycle,pureRenders} from "./classLab";

afterEach(()=>{vi.useRealTimers();});

const report=(title:string,count=1):Report=>({title,count});

function deferred<T>(){
  let resolve!:(value:T)=>void,reject!:(error:unknown)=>void;
  const promise=new Promise<T>((yes,no)=>{resolve=yes;reject=no;});
  return {promise,resolve,reject};
}

test("panel pokazuje ładowanie, potem dane",async()=>{
  render(<ReportPanel reportId={1} loadReport={async()=>report("Styczeń",4)}/>);
  expect(screen.getByText("Ładowanie raportu…")).toBeVisible();
  expect(await screen.findByRole("heading",{name:"Styczeń"})).toBeVisible();
  expect(screen.getByText("Zgłoszeń: 4")).toBeVisible();
});

test("błąd pobierania jest pokazany",async()=>{
  render(<ReportPanel reportId={1} loadReport={async()=>{throw new Error("x");}}/>);
  expect(await screen.findByRole("alert")).toHaveTextContent("Nie udało się");
});

test("zmiana raportu pobiera nowe dane",async()=>{
  const load=vi.fn(async(id:number)=>report("Raport "+id));
  const view=render(<ReportPanel reportId={1} loadReport={load}/>);
  await screen.findByRole("heading",{name:"Raport 1"});
  view.rerender(<ReportPanel reportId={2} loadReport={load}/>);
  expect(await screen.findByRole("heading",{name:"Raport 2"})).toBeVisible();
  expect(load).toHaveBeenCalledTimes(2);
});

test("spóźniona odpowiedź starego raportu nie nadpisuje nowego",async()=>{
  const one=deferred<Report>(),two=deferred<Report>();
  const load=(id:number)=>id===1?one.promise:two.promise;
  const view=render(<ReportPanel reportId={1} loadReport={load}/>);
  view.rerender(<ReportPanel reportId={2} loadReport={load}/>);
  await act(async()=>two.resolve(report("Nowy")));
  expect(screen.getByRole("heading",{name:"Nowy"})).toBeVisible();
  await act(async()=>one.resolve(report("Stary")));
  expect(screen.getByRole("heading",{name:"Nowy"})).toBeVisible();
  expect(screen.queryByRole("heading",{name:"Stary"})).toBeNull();
});

test("odmontowanie blokuje późny zapis stanu",async()=>{
  const one=deferred<Report>();
  const setState=vi.spyOn(ReportPanel.prototype,"setState");
  const view=render(<ReportPanel reportId={1} loadReport={()=>one.promise}/>);
  view.unmount();
  setState.mockClear();
  await act(async()=>one.resolve(report("Późny")));
  expect(setState).not.toHaveBeenCalled();
  setState.mockRestore();
});

test("automatyczne odświeżanie startuje, zatrzymuje się i sprząta",async()=>{
  vi.useFakeTimers();
  const load=vi.fn(async()=>report("Raport"));
  const view=render(<ReportPanel reportId={1} loadReport={load} refreshMs={1000}/>);
  await act(async()=>{await vi.advanceTimersByTimeAsync(0);});
  expect(load).toHaveBeenCalledTimes(1);
  fireEvent.click(screen.getByRole("checkbox"));
  await act(async()=>{await vi.advanceTimersByTimeAsync(2000);});
  expect(load).toHaveBeenCalledTimes(3);
  fireEvent.click(screen.getByRole("checkbox"));
  await act(async()=>{await vi.advanceTimersByTimeAsync(2000);});
  expect(load).toHaveBeenCalledTimes(3);
  fireEvent.click(screen.getByRole("checkbox"));
  view.unmount();
  await vi.advanceTimersByTimeAsync(5000);
  expect(load).toHaveBeenCalledTimes(3);
  vi.useRealTimers();
});

test("setState: grupowanie i forma funkcyjna",()=>{
  expect(counterAfter("Źle +3").count).toBe("1");
  expect(counterAfter("Dobrze +3").count).toBe("3");
  expect(counterAfter("Dobrze +3",5).count).toBe("7");
});

test("setState: scalanie płytkie",()=>{
  expect(counterAfter("Dobrze +3").label).toBe("Licznik");
  expect(counterAfter("Źle zakres").range).toBe("2026-06-01–undefined");
  expect(counterAfter("Dobrze zakres").range).toBe("2026-06-01–2026-12-31");
});

test("this w handlerze",()=>{
  expect(bindingResult("unbound")).toBe("TypeError");
  expect(bindingResult("bound")).toBe("bind");
  expect(bindingResult("arrow")).toBe("strzałka");
});

test("kolejność metod cyklu życia",()=>{
  expect(lifecycle("mount")).toBe("constructor>getDerivedStateFromProps>render>componentDidMount");
  expect(lifecycle("update")).toBe("getDerivedStateFromProps>shouldComponentUpdate>render>getSnapshotBeforeUpdate>componentDidUpdate");
  expect(lifecycle("unmount")).toBe("componentWillUnmount");
});

test("PureComponent i stan wyliczany z props",()=>{
  expect(pureRenders(true)).toBe(1);
  expect(pureRenders(false)).toBe(2);
  expect(filterAfterNewList()).toEqual({typed:1,focused:true,query:"",afterNew:2});
});`;
const unsafe=String.raw`// Stare metody i ich zamienniki (przykład do czytania, a nie do kopiowania)
// componentWillMount           -> konstruktor albo componentDidMount
// componentWillReceiveProps    -> componentDidUpdate z porównaniem albo obliczenie w renderze
// componentWillUpdate          -> componentDidUpdate lub getSnapshotBeforeUpdate
// ref="input" i this.refs      -> createRef (string refs usunięto w React 19)
// contextTypes, getChildContext -> createContext (legacy context usunięto w React 19)`;
const exercises=[
['Opisz w słowach, co robi ReportPanel.','Stan, moment pobierania, sprzątanie.','Stan: data, loading, error, autoRefresh. Pobranie: po zamontowaniu i po zmianie reportId. Timer: włącza go zmiana autoRefresh. Sprzątanie: componentWillUnmount czyści timer i ustawia flagę anulowania.'],
['Napisz klasę z typami Component<Props, State>.','Pola instancji zadeklaruj jawnie.',panel],
['Pobierz dane po zamontowaniu.','componentDidMount.',panel],
['Pobierz ponownie po zmianie props.','componentDidUpdate z porównaniem prevProps.',panel],
['Sprzątaj przy odmontowaniu.','Timer i flaga anulowania.',panel],
['Znajdź wyścig przy zmianie reportId.','Flaga cancelled działa tylko po odmontowaniu.','Zmiana reportId podczas ładowania uruchamia drugie żądanie. Jeśli pierwsze odpowie później, nadpisze wynik nowszego raportu. Flaga cancelled działa dopiero po odmontowaniu, więc tego nie obsługuje.'],
['Napraw wyścig licznikiem żądań.','Pole instancji, a nie stan.',panel],
['Przetestuj wyścig dwóch odpowiedzi.','Kontrolowane obietnice pozwalają ustalić kolejność.',tests],
['Pokaż pułapkę kilku setState.','this.state nie zmienia się od razu.',counter],
['Użyj formy funkcyjnej setState.','Najnowszy stan z kolejki.',counter],
['Wykorzystaj props w formie funkcyjnej.','Drugi parametr to aktualne props.',counter],
['Pokaż płytkie scalanie na obiekcie zagnieżdżonym.','range jest zastępowany w całości.',counter],
['Popraw aktualizację zagnieżdżonego obiektu.','Skopiuj poprzednie pola.',counter],
['Użyj callbacku po aktualizacji.','Drugi argument setState.','this.setState({page:1},()=>this.load());\nCallback działa po zastosowaniu zmiany i renderze. W hookach odpowiednikiem bywa Effect, a częściej wywołanie logiki bezpośrednio w handlerze.'],
['Pokaż utratę this w handlerze.','Metoda przekazana bez wiązania.',binding],
['Popraw przez bind w konstruktorze.','Stary, częsty zapis.',binding],
['Popraw przez pole ze strzałką.','Zapis zalecany w kodzie klasowym.',binding],
['Odtwórz kolejność metod cyklu życia.','Dziennik w każdej metodzie.',order],
['Porównaj ze skrótem hooków.','Co odpowiada czemu?','componentDidMount z componentWillUnmount to useEffect ze sprzątaniem i []. componentDidUpdate z porównaniem to useEffect z zależnością. PureComponent i shouldComponentUpdate to memo. getSnapshotBeforeUpdate to useLayoutEffect.'],
['Użyj PureComponent.','Płytkie porównanie props.',legacy],
['Sprawdź liczbę renderów PureComponent.','Nowy obiekt to nowy render.',tests],
['Użyj getDerivedStateFromProps.','Rzadko potrzebne, dziś zwykle key.',legacy],
['Użyj createRef.','Odpowiednik useRef w klasach.',legacy],
['Rozpoznaj przestarzałe API.','Lista metod i ich zamienników.',unsafe],
['Przygotuj się na aktualizację do React 19.','Co trzeba przepisać obowiązkowo?','string refs i legacy context zostały usunięte, więc wymagają przepisania na createRef i createContext. Metody UNSAFE_ nadal działają, ale z ostrzeżeniami i ryzykiem wielokrotnego wywołania. PropTypes nie są już sprawdzane przez Reacta.'],
['Wykaż problem mutacji stanu.','Ta sama referencja ukrywa zmianę przed PureComponent.',order],
['Popraw mutację tablicy w stanie.','Nowa tablica.','// Źle: this.state.items.push(x); this.setState({items:this.state.items});\n// Dobrze:\nthis.setState(prev=>({items:[...prev.items,x]}));\n// Przypisanie this.state=... wolno tylko w konstruktorze.'],
['Uruchom moduł w StrictMode.','Wykrywa przestarzałe metody.','Owiń moduł w <StrictMode>, sprawdź konsolę i usuń ostrzeżenia o metodach UNSAFE_. W trybie deweloperskim render i konstruktor mogą zostać wywołane podwójnie, więc efekty uboczne w nich zostaną ujawnione.'],
['Zamień bind w renderze na pole ze strzałką.','onClick={()=>...} w renderze tworzy nową funkcję.','Nowa funkcja w każdym renderze powoduje zbędne rendery dziecka PureComponent. Pole klasy ze strzałką ma stałą referencję. Bez PureComponent dziecka nie ma to zwykle znaczenia, dlatego mierz, zanim zaczniesz poprawiać cały kod.'],
['Sprawdź kryterium akceptacji lekcji.','Opis, wyścig, bind, mutacje, StrictMode.','Potrafisz opisać każdą klasę w module, wyścig w ReportPanel jest naprawiony, nie ma bind w renderze ani mutacji this.state, a StrictMode nie zgłasza ostrzeżeń o przestarzałych metodach.'],
];
const concepts=[
['Po czym dziedziczy komponent klasowy?','React.Component lub PureComponent','useState','HTMLElement','Proxy','Musi mieć metodę render.'],
['Gdzie są props w klasie?','W this.props','W argumencie render','W this.state','W state.props','Render nie dostaje argumentów.'],
['Jak inicjalizować stan?','W konstruktorze lub polem klasy','W render','Przez useState','W componentDidUpdate','Później tylko przez setState.'],
['Jak zmienić stan?','this.setState','this.state=...','useState','forceUpdate zawsze','Przypisanie wolno tylko w konstruktorze.'],
['Co robi setState z obiektem?','Scala płytko z obecnym stanem','Zastępuje cały stan','Scala głęboko','Usuwa pozostałe pola','Inaczej niż setter useState.'],
['Co robi setter useState?','Zastępuje wartość','Scala płytko','Scala głęboko','Dopisuje pole','Częsta pułapka przy migracji.'],
['Czy zagnieżdżone obiekty są scalane?','Nie','Tak','Tylko dwa poziomy','Tylko w TS','Zastępowany jest cały obiekt.'],
['Czy this.state zmienia się od razu po setState?','Nie, aktualizacja jest grupowana','Tak','Tylko w dev','Tylko w PureComponent','Dlatego forma funkcyjna.'],
['Kiedy forma funkcyjna setState?','Gdy nowy stan zależy od poprzedniego','Nigdy','Zawsze dla tekstu','Tylko w konstruktorze','Dostaje najnowszy stan.'],
['Co dostaje forma funkcyjna?','Poprzedni stan i props','Tylko event','Tylko ref','Element DOM','(prev,props)=>...'],
['Do czego służy drugi argument setState?','Do callbacku po aktualizacji','Do nowego stanu','Do czasu','Do props','Rzadko potrzebny dziś.'],
['Kiedy działa componentDidMount?','Po pierwszym wstawieniu do DOM','Przed render','Przy każdym renderze','Przy odmontowaniu','Miejsce na pobranie i subskrypcje.'],
['Kiedy działa componentDidUpdate?','Po każdej aktualizacji, nie przy montowaniu','Tylko raz','Przed render','Tylko przy props','Porównaj prevProps, by uniknąć pętli.'],
['Co jest ryzykiem setState w componentDidUpdate?','Pętla bez warunku','Brak renderu','Utrata props','Błąd składni','Warunek porównuje poprzednie wartości.'],
['Kiedy działa componentWillUnmount?','Przed usunięciem z DOM','Po usunięciu','Przy każdym renderze','Tylko w błędzie','Miejsce na sprzątanie.'],
['Co jest pierwsze przy montowaniu?','constructor','render','componentDidMount','getSnapshotBeforeUpdate','Potem getDerivedStateFromProps i render.'],
['Co jest między render a componentDidUpdate?','getSnapshotBeforeUpdate','constructor','componentDidMount','useEffect','Tuż przed zmianą DOM.'],
['Do czego służy getSnapshotBeforeUpdate?','Pomiar przed zmianą DOM, np. pozycja przewinięcia','Pobranie danych','Sprzątanie','Walidacja props','Rzadki przypadek.'],
['Do czego służy shouldComponentUpdate?','Decyzja, czy renderować','Pobieranie','Tworzenie refa','Zmiana propów','Zwraca boolean.'],
['Czym jest PureComponent?','Component z płytkim porównaniem props i stanu','Komponent bez stanu','Hook','Komponent serwerowy','Jak memo dla funkcji.'],
['Czym grozi mutacja przy PureComponent?','Zmiana nie zostanie zauważona','Błędem składni','Podwójnym renderem','Niczym','Ta sama referencja wygląda jak brak zmiany.'],
['Co zwraca getDerivedStateFromProps?','Zmianę stanu lub null','Element','Promise','Props','Działa przed każdym renderem.'],
['Czemu getDerivedStateFromProps jest statyczne?','Nie ma dostępu do this','Dla wydajności CSS','Bo tak wygląda TS','Bez powodu','Wymusza czystość.'],
['Co zwykle zastępuje getDerivedStateFromProps?','Obliczenie w renderze lub reset przez key','useEffect zawsze','Redux','CSS','Stan wyliczany rzadko jest potrzebny.'],
['Do czego służy createRef?','Tworzy ref w klasie','Tworzy stan','Tworzy kontekst','Tworzy Portal','Odpowiednik useRef.'],
['Kiedy ref.current jest wypełniony?','Po zamontowaniu','W konstruktorze','W render','Przed render','Dlatego focus w componentDidMount.'],
['Dlaczego handler traci this?','Metoda jest przekazana jako zwykła funkcja','React usuwa this','Bo strict mode','Bo JSX','this zależy od sposobu wywołania.'],
['Jaki błąd daje utracone this?','TypeError przy odczycie','SyntaxError','RangeError','Brak błędu','Cannot read properties of undefined.'],
['Jak związać metodę?','bind w konstruktorze lub pole ze strzałką','bind w render zawsze','use strict','call w JSX','Strzałka bierze this z miejsca definicji.'],
['Czym grozi strzałka w JSX?','Nową funkcją w każdym renderze','Błędem składni','Utratą this','Niczym nigdy','Ma znaczenie przy PureComponent dziecka.'],
['Czy klasy działają w React 19?','Tak','Nie','Tylko w trybie legacy','Tylko bez TS','Nowy kod pisze się funkcjami.'],
['Czy klasa może użyć hooka?','Nie','Tak','Tylko useRef','Tylko useContext','Hooki działają tylko w funkcjach.'],
['Co nadal wymaga klasy?','Granica błędów','Lista','Formularz','Kontekst','Lub biblioteka react-error-boundary.'],
['Jak odczytać kontekst w klasie?','static contextType lub Consumer','useContext','Props zawsze','this.refs','this.context po ustawieniu contextType.'],
['Jaki jest typ Component w TSX?','Component<Props, State>','Component<State>','React<Props>','FC<State>','Sprawdza this.props i setState.'],
['Co przyjmuje setState w TS?','Część pól stanu przez Pick','Tylko pełny stan','any','Tylko funkcję','Odpowiada scalaniu.'],
['Co zgłosi TSX przy setState({autoRefreh:true})?','Błąd kompilacji','Nic','Ostrzeżenie w runtime','Błąd CSS','W JSX przejdzie po cichu.'],
['Czym jest public/private w klasach TS?','Stylem i kontrolą kompilatora','Ochroną w runtime','Wymogiem Reacta','Zmianą działania','private znika po kompilacji.'],
['Czy PropTypes działają w React 19?','Nie są sprawdzane przez Reacta','Tak, zawsze','Tak, w produkcji','Tylko w klasach','Pakiet nadal istnieje, ale React ich nie sprawdza.'],
['Co oznacza UNSAFE_ w nazwie metody?','Może sprawiać problemy przy renderowaniu współbieżnym','Jest usunięta','Jest niebezpieczna dla sieci','Blokuje render','Działa z ostrzeżeniami.'],
['Co zastępuje componentWillMount?','Konstruktor lub componentDidMount','useMemo','render','getSnapshot','Stare pobieranie danych tu było błędem.'],
['Co zastępuje componentWillReceiveProps?','componentDidUpdate, obliczenie w renderze lub key','useState','forceUpdate','Nic','Najczęściej dane wyliczane.'],
['Co usunięto w React 19?','String refs i legacy context','Klasy','Metody cyklu życia','setState','Trzeba przepisać przy aktualizacji.'],
['Co zastępuje string ref?','createRef','this.refs','querySelector','id','ref="input" już nie działa.'],
['Co zastępuje legacy context?','createContext','contextTypes','Props drilling zawsze','Redux','getChildContext zniknął.'],
['Czemu nie pobierać danych w konstruktorze?','Efekt uboczny w fazie renderu','Bo nie ma props','Bo jest za późno','Bez powodu','Konstruktor może działać wielokrotnie w StrictMode.'],
['Co robi StrictMode z konstruktorem w dev?','Może wywołać podwójnie','Usuwa go','Blokuje','Nic','Ujawnia niespodziewane efekty.'],
['Gdzie trzymać identyfikator timera?','W polu instancji','W this.state','W props','W CSS','Zmiana nie powinna powodować renderu.'],
['Gdzie trzymać licznik żądań?','W polu instancji','W stanie','W DOM','W module globalnym','Nie wpływa na widok.'],
['Co powoduje wyścig przy zmianie id?','Starsza odpowiedź może przyjść później','Brak klucza','Brak CSS','Typ number','Klasyczny błąd pobierania.'],
['Czemu flaga cancelled nie naprawia wyścigu id?','Działa tylko przy odmontowaniu','Bo jest liczbą','Bo jest w stanie','Bo jest w props','Potrzebny numer żądania lub AbortController.'],
['Jak inaczej anulować żądanie?','AbortController','clearTimeout','forceUpdate','setState(null)','Przerywa też samą sieć.'],
['Czym jest forceUpdate?','Render bez sprawdzania shouldComponentUpdate','Zmiana stanu','Hook','Reset komponentu','Zwykle oznacza problem w projekcie.'],
['Jak czytać stary moduł?','Stan, cykl życia, sprzątanie','Od CSS','Od testów E2E','Od package.json','Pozwala szybko zrozumieć przepływ.'],
['Co jest bezpieczne przy poprawce klasy?','Mała zmiana z testem zachowania','Przepisanie całości w jednym PR','Usunięcie testów','Zmiana bibliotek','Ryzyko trzymaj pod kontrolą.'],
['Czy klasa i funkcja mogą być w jednym drzewie?','Tak','Nie','Tylko przez HOC','Tylko w React 17','Migracja może być stopniowa.'],
['Jak test RTL traktuje klasę?','Tak samo jak funkcję: sprawdza ekran','Inaczej, wymaga instancji','Nie działa','Tylko przez Enzyme','Dlatego przeżyje migrację.'],
['Czym jest wiązanie w konstruktorze wzorcowo?','this.handle=this.handle.bind(this)','this.handle()','bind(handle)','new handle','Tworzy funkcję ze stałym this.'],
['Dlaczego metody cyklu życia grupują kod źle?','Logika jednego celu leży w wielu metodach','Są wolne','Są usunięte','Mają złe nazwy','Stąd hooki.'],
['Co jest celem tej lekcji?','Czytać i bezpiecznie zmieniać stary kod','Pisać nowe klasy','Usuwać klasy bez planu','Zastąpić testy','Takiego kodu wciąż jest dużo.'],
];
const header=String.raw`import {bindingResult,counterAfter,filterAfterNewList,lifecycle,pureRenders} from "./classLab";
import {mutationPitfall} from "./lifecycle";`;
const L=['constructor>render>componentDidMount','constructor>getDerivedStateFromProps>render>componentDidMount','render>componentDidMount','getDerivedStateFromProps>render'];
const probeRows=[
['Trzy zwykłe setState z tej samej wartości','counterAfter("Źle +3").count','1','this.state nie zmienia się między wywołaniami.',['3','0','2']],
['Trzy setState w formie funkcyjnej','counterAfter("Dobrze +3").count','3','Każde widzi wynik poprzedniego.',['1','0','2']],
['Forma funkcyjna z props.step=5','counterAfter("Dobrze +3",5).count','7','1+1+5.',['3','5','15']],
['Forma funkcyjna z props.step=0','counterAfter("Dobrze +3",0).count','2','1+1+0.',['3','0','1']],
['Czy label zostaje po zmianie licznika?','counterAfter("Dobrze +3").label','Licznik','Scalanie zachowuje pozostałe pola.',['pusty tekst','undefined','3']],
['Zakres po złym setState','counterAfter("Źle zakres").range','2026-06-01–undefined','Obiekt zastąpiony w całości.',['2026-06-01–2026-12-31','2026-01-01–2026-12-31','undefined–undefined']],
['Zakres po dobrym setState','counterAfter("Dobrze zakres").range','2026-06-01–2026-12-31','Kopia poprzednich pól.',['2026-06-01–undefined','2026-01-01–2026-12-31','undefined–undefined']],
['Czy licznik zmienia się przy zmianie zakresu?','counterAfter("Dobrze zakres").count','0','Inne pole.',['1','3','undefined']],
['Metoda bez wiązania','bindingResult("unbound")','TypeError','this jest undefined.',['działa','bind','brak']],
['Metoda z bind','bindingResult("bound")','bind','Stałe this.',['TypeError','strzałka','brak']],
['Pole ze strzałką','bindingResult("arrow")','strzałka','this z instancji.',['TypeError','bind','brak']],
['Kolejność montowania','lifecycle("mount")',L[1],'Render, potem DOM, potem componentDidMount.',L.filter(x=>x!==L[1])],
['Kolejność odmontowania','lifecycle("unmount")','componentWillUnmount','Jedna metoda.',['componentDidUpdate','render','brak']],
['Pierwsza metoda aktualizacji','lifecycle("update").split(">")[0]','getDerivedStateFromProps','Przed każdym renderem.',['render','shouldComponentUpdate','componentDidUpdate']],
['Metoda przed render w aktualizacji','lifecycle("update").split(">")[1]','shouldComponentUpdate','Decyduje o renderze.',['getSnapshotBeforeUpdate','componentDidUpdate','constructor']],
['Metoda po render w aktualizacji','lifecycle("update").split(">")[3]','getSnapshotBeforeUpdate','Pomiar przed zmianą.',['componentDidUpdate','shouldComponentUpdate','constructor']],
['Ostatnia metoda aktualizacji','lifecycle("update").split(">")[4]','componentDidUpdate','Po zmianach w DOM.',['render','getSnapshotBeforeUpdate','componentWillUnmount']],
['Liczba metod przy aktualizacji','lifecycle("update").split(">").length',5,'Pełna ścieżka.'],
['Czy constructor działa przy aktualizacji?','lifecycle("update").includes("constructor")',false,'Instancja już istnieje.'],
['Czy componentDidMount działa przy aktualizacji?','lifecycle("update").includes("componentDidMount")',false,'Tylko raz.'],
['Rendery PureComponent z tym samym obiektem','pureRenders(true)',1,'Płytkie porównanie pomija render, o ile wszystkie props mają te same referencje. Nowa strzałka też by go wymusiła.',['2','0','3']],
['Rendery PureComponent z nowym obiektem','pureRenders(false)',2,'Inna referencja to render, choć treść ta sama.',['1','0','3']],
['Czy mutacja zachowuje referencję?','mutationPitfall()',true,'Dlatego PureComponent nie zauważy zmiany.'],
['Wpisany filtr: pozycje','filterAfterNewList().typed',1,'Filtr działa.'],
['Fokus po montowaniu','filterAfterNewList().focused',true,'createRef i componentDidMount.'],
['Filtr po nowej liście','filterAfterNewList().query','','Stan wyliczony z props czyści pole.',['Al','undefined','Alfa'].concat([])],
['Pozycje po nowej liście','filterAfterNewList().afterNew',2,'Filtr wyczyszczony.'],
['Czy klasa ma this.state po stworzeniu?','(()=>{class A{state={a:1};}return new A().state.a;})()',1,'Pole klasy.'],
['Czy zwykła metoda oderwana traci this?','(()=>{class A{x=1;f(){return this?.x??"brak";}}const f=new A().f;return f();})()','brak','Wywołanie bez obiektu.',['1','undefined','A']],
['Czy strzałkowe pole zachowuje this?','(()=>{class A{x=1;f=()=>this.x;}const f=new A().f;return f();})()',1,'this z instancji.'],
['Czy bind zachowuje this?','(()=>{class A{x=1;f(){return this.x;}}const a=new A();const f=a.f.bind(a);return f();})()',1,'Wiązanie jawne.'],
['Scalanie płytkie w JS','JSON.stringify({...{a:1,b:{c:1,d:2}},...{b:{c:9}}})','{"a":1,"b":{"c":9}}','Tak działa scalanie setState.',['{"a":1,"b":{"c":9,"d":2}}','{"b":{"c":9}}','{"a":1}']],
['Poprawne scalanie zagnieżdżone','JSON.stringify((p=>({...p,b:{...p.b,c:9}}))({a:1,b:{c:1,d:2}}))','{"a":1,"b":{"c":9,"d":2}}','Kopia na obu poziomach.',['{"a":1,"b":{"c":9}}','{"b":{"c":9}}','{"a":1}']],
['Porównanie płytkie: te same referencje','(a=>a===a)({})',true,'Ten sam obiekt.'],
['Porównanie płytkie: kopia','(a=>{const b={...a};return a===b;})({})',false,'Nowy obiekt.'],
['Licznik żądań: ostatnie wygrywa','(()=>{let id=0;const a=++id,b=++id;return [a===id,b===id].join();})()','false,true','Tylko najnowszy numer zapisuje stan.',['true,true','true,false','false,false']],
['Czy clearInterval z undefined rzuca?','(()=>{try{clearInterval(undefined);return "bez błędu";}catch{return "błąd";}})()','bez błędu','Dlatego toggleTimer może wołać zawsze.',['błąd','undefined','null']],
['Czy async load zwraca Promise?','(async function(){})() instanceof Promise',true,'Stąd void this.load().'],
['Liczba metod montowania w dzienniku','lifecycle("mount").split(">").length',4,'Cztery kroki.'],
['Czy render jest przed componentDidMount?','(l=>l.indexOf("render")<l.indexOf("componentDidMount"))(lifecycle("mount"))',true,'DOM powstaje po renderze.'],
];
// Odpowiedź w postaci pustego tekstu jest niedozwolona: pole filtra pokazujemy przez kontrolny zapis.
const fixedRows=probeRows.map(row=>row[0]==='Filtr po nowej liście'?['Długość tekstu filtra po nowej liście','filterAfterNewList().query.length',0,'Stan wyliczony z props czyści pole.']:row);
const project={
'src/ReportPanel.tsx':panel,'src/Counter.tsx':counter,'src/Binding.tsx':binding,'src/LegacyPatterns.tsx':legacy,'src/lifecycle.tsx':order,'src/classLab.tsx':lab,'src/classes.test.tsx':tests,
'README.md':'DevDesk: komponenty klasowe. npm install, npm test. Testy sprawdzają zachowanie na ekranie, więc przeżyją migrację na hooki opisaną w kolejnej lekcji. Zapisy typu "Źle" w plikach są celowymi przykładami do porównania.\n',
};
const mutants=[
{file:'src/ReportPanel.tsx',before:'if(prevProps.reportId!==this.props.reportId)void this.load();',after:''},
{file:'src/ReportPanel.tsx',before:'if(!this.cancelled&&request===this.requestId)this.setState({data,loading:false});',after:'if(!this.cancelled)this.setState({data,loading:false});'},
{file:'src/ReportPanel.tsx',before:'if(!this.cancelled&&request===this.requestId)this.setState({data,loading:false});',after:'if(request===this.requestId)this.setState({data,loading:false});'},
{file:'src/ReportPanel.tsx',before:'clearInterval(this.timer);\n    this.cancelled=true;',after:'this.cancelled=true;'},
{file:'src/ReportPanel.tsx',before:'if(prevState.autoRefresh!==this.state.autoRefresh)this.toggleTimer();',after:''},
{file:'src/Counter.tsx',before:'this.setState(prev=>({range:{...prev.range,from}}));',after:'this.setState({range:{from}} as Pick<CounterState,"range">);'},
{file:'src/Binding.tsx',before:'this.handleClick=this.handleClick.bind(this);',after:''},
{file:'src/LegacyPatterns.tsx',before:'props.items!==state.prevItems?{query:"",prevItems:props.items}:null',after:'null'},
{file:'src/LegacyPatterns.tsx',before:'PureComponent<{row:Row',after:'Component<{row:Row'},
];
module.exports=lesson({exercises,concepts,probeRows:fixedRows,project,mutants,header});
