// Lekcja 15/03: architektura Fiber. Model pętli pracy w czystym TS + obserwacje prawdziwego Reacta 19 w Vitest.
const model=String.raw`export type FiberNode={name:string;child?:FiberNode;sibling?:FiberNode;parent?:FiberNode};
export type Spec={name:string;children?:Spec[]};
// Buduje drzewo jako listę powiązaną: child (pierwsze dziecko), sibling (następne rodzeństwo), parent (return w Reakcie).
export function buildFibers(spec:Spec,parent?:FiberNode):FiberNode{
  const node:FiberNode={name:spec.name,parent};
  let previous:FiberNode|undefined;
  for(const childSpec of spec.children??[]){
    const child=buildFibers(childSpec,node);
    if(previous)previous.sibling=child;else node.child=child;
    previous=child;
  }
  return node;
}
export function* walk(root:FiberNode):Generator<string>{
  let node:FiberNode|undefined=root;
  while(node){
    yield "beginWork "+node.name;
    if(node.child){node=node.child;continue;}
    while(node){
      yield "completeWork "+node.name;
      if(node===root)return;
      if(node.sibling){node=node.sibling;break;}
      node=node.parent;
    }
  }
}
// Jedna jednostka pracy: wejście do węzła, a gdy nie ma dziecka — wyjście w górę aż do rodzeństwa.
export function performUnitOfWork(node:FiberNode,root:FiberNode,log:string[]):FiberNode|null{
  log.push("begin "+node.name);
  if(node.child)return node.child;
  let current:FiberNode|undefined=node;
  while(current){
    log.push("complete "+current.name);
    if(current===root)return null;
    if(current.sibling)return current.sibling;
    current=current.parent;
  }
  return null;
}
export type Work={root:FiberNode;next:FiberNode|null;log:string[]};
export function startWork(root:FiberNode):Work{return {root,next:root,log:[]};}
// Pętla współbieżna: przerwij, gdy shouldYield() — zapamiętaj następny fiber i wróć później. Zwraca true po zakończeniu.
export function workLoop(work:Work,shouldYield:()=>boolean):boolean{
  while(work.next&&!shouldYield())work.next=performUnitOfWork(work.next,work.root,work.log);
  return work.next===null;
}`;
const buffers=String.raw`export type Tree={version:number;labels:readonly string[]};
// Podwójne buforowanie: render buduje WIP, ekran pokazuje current; commit zamienia drzewa.
export class FiberRoot{
  current:Tree;
  commits=0;
  private wip:Tree|null=null;
  constructor(initial:Tree){this.current=initial;}
  beginRender(labels:readonly string[]):Tree{this.wip={version:this.current.version+1,labels:[...labels]};return this.wip;}
  abandon(){this.wip=null;}
  commit(){
    if(!this.wip)throw new Error("Brak gotowego drzewa WIP");
    this.current=this.wip;
    this.wip=null;
    this.commits+=1;
  }
  get hasWorkInProgress(){return this.wip!==null;}
}`;
const interrupt=String.raw`import {useState,useTransition} from "react";
let renderCount=0;
export const slowListRenders={get:()=>renderCount,reset:()=>{renderCount=0;}};
function SlowList({query,delay}:{query:string;delay:number}){
  renderCount+=1; // tylko do obserwacji — w prawdziwym kodzie nie zmieniaj zmiennych w renderze
  const start=performance.now();
  while(performance.now()-start<delay){/* sztuczne spowolnienie */}
  return <p>Wyniki dla „{query}”</p>;
}
export function InterruptDemo({delay=150}:{delay?:number}){
  const [text,setText]=useState("");
  const [query,setQuery]=useState("");
  const [isPending,startTransition]=useTransition();
  return <div>
    <input aria-label="Szukaj" value={text} onChange={e=>{
      setText(e.target.value);                       // pilne — pole reaguje od razu
      startTransition(()=>setQuery(e.target.value)); // niepilne — można przerwać
    }}/>
    {isPending&&<small>Liczenie…</small>}
    <SlowList query={query} delay={delay}/>
  </div>;
}`;
const modelTests=String.raw`import {describe,expect,test} from "vitest";
import {buildFibers,startWork,walk,workLoop,type Spec} from "./fiberModel";
const devdesk:Spec={name:"App",children:[{name:"Header"},{name:"Main",children:[{name:"TicketTable",children:[{name:"Row41"},{name:"Row42"}]}]},{name:"Footer"}]};
describe("drzewo jako lista powiązana",()=>{
  test("child wskazuje pierwsze dziecko, sibling — następne, parent — rodzica",()=>{
    const app=buildFibers(devdesk);
    expect(app.child?.name).toBe("Header");
    expect(app.child?.sibling?.name).toBe("Main");
    expect(app.child?.sibling?.sibling?.name).toBe("Footer");
    expect(app.child?.sibling?.child?.child?.sibling?.parent?.name).toBe("TicketTable");
  });
  test("generator walk odwiedza drzewo w kolejności begin/complete",()=>{
    expect([...walk(buildFibers(devdesk))]).toEqual([
      "beginWork App","beginWork Header","completeWork Header","beginWork Main","beginWork TicketTable",
      "beginWork Row41","completeWork Row41","beginWork Row42","completeWork Row42","completeWork TicketTable",
      "completeWork Main","beginWork Footer","completeWork Footer","completeWork App"]);
  });
  test("generator można wstrzymać po dowolnym kroku i wznowić",()=>{
    const steps=walk(buildFibers(devdesk));
    expect(steps.next().value).toBe("beginWork App");
    expect(steps.next().value).toBe("beginWork Header");
    expect([...steps]).toHaveLength(12);
  });
});
describe("pętla pracy z oddawaniem sterowania",()=>{
  test("praca w kawałkach daje ten sam wynik co praca bez przerw",()=>{
    const whole=startWork(buildFibers(devdesk));
    expect(workLoop(whole,()=>false)).toBe(true);
    const sliced=startWork(buildFibers(devdesk));
    let slices=0;
    for(;;){let budget=3;slices+=1;if(workLoop(sliced,()=>budget--<=0))break;}
    expect(sliced.log).toEqual(whole.log);
    expect(slices).toBeGreaterThan(1);
  });
  test("shouldYield przerywa pracę i zapamiętuje następny fiber",()=>{
    const work=startWork(buildFibers(devdesk));
    let budget=2;
    expect(workLoop(work,()=>budget--<=0)).toBe(false);
    expect(work.log).toEqual(["begin App","begin Header","complete Header"]);
    expect(work.next?.name).toBe("Main");
  });
  test("praca na poddrzewie nie wychodzi poza jego korzeń",()=>{
    const app=buildFibers(devdesk),main=app.child!.sibling!;
    const work=startWork(main);
    workLoop(work,()=>false);
    expect(work.log.at(-1)).toBe("complete Main");
    expect(work.log.join(",")).not.toContain("Footer");
  });
});`;
const bufferTests=String.raw`import {expect,test} from "vitest";
import {FiberRoot} from "./doubleBuffer";
test("render buduje WIP i nie zmienia current przed commitem",()=>{
  const root=new FiberRoot({version:1,labels:["41"]});
  const wip=root.beginRender(["41","42"]);
  expect(wip.version).toBe(2);
  expect(root.current.labels).toEqual(["41"]);
  expect(root.hasWorkInProgress).toBe(true);
});
test("commit zamienia drzewa: WIP staje się current",()=>{
  const root=new FiberRoot({version:1,labels:["41"]});
  root.beginRender(["41","42"]);
  root.commit();
  expect(root.current).toEqual({version:2,labels:["41","42"]});
  expect(root.hasWorkInProgress).toBe(false);
  expect(root.commits).toBe(1);
});
test("porzucony render zostawia ekran bez zmian, nowy render startuje od current",()=>{
  const root=new FiberRoot({version:1,labels:["41"]});
  root.beginRender(["41","42"]);
  root.abandon();
  expect(root.current.labels).toEqual(["41"]);
  expect(root.beginRender(["43"]).version).toBe(2);
  expect(()=>new FiberRoot({version:1,labels:[]}).commit()).toThrow("Brak gotowego drzewa WIP");
});
test("WIP to kopia: późniejsza zmiana tablicy wejściowej nie zmienia drzewa",()=>{
  const root=new FiberRoot({version:1,labels:[]}),labels=["41"];
  root.beginRender(labels);
  labels.push("99");
  root.commit();
  expect(root.current.labels).toEqual(["41"]);
});`;
const reactTests=String.raw`import {StrictMode,Suspense,use,useEffect,useLayoutEffect} from "react";
import {act,render,screen} from "@testing-library/react";
import userEvent from "@testing-library/user-event";
import {expect,test} from "vitest";
import {InterruptDemo,slowListRenders} from "./InterruptDemo";
test("StrictMode w trybie deweloperskim wywołuje render dwa razy, efekt: setup, cleanup, setup",()=>{
  const log:string[]=[];
  function Tracker(){log.push("render");useEffect(()=>{log.push("setup");return ()=>{log.push("cleanup");};},[]);return null;}
  render(<StrictMode><Tracker/></StrictMode>);
  expect(log).toEqual(["render","render","setup","cleanup","setup"]);
});
test("bez StrictMode render i efekt wykonują się raz",()=>{
  const log:string[]=[];
  function Tracker(){log.push("render");useEffect(()=>{log.push("setup");},[]);return null;}
  render(<Tracker/>);
  expect(log).toEqual(["render","setup"]);
});
test("render bez commitu: komponent zawieszony przez use() renderuje się, ale nic nie trafia na ekran",async()=>{
  let resolve!:(value:string)=>void,renders=0,commits=0;
  const promise=new Promise<string>(r=>{resolve=r;});
  function Data(){renders+=1;const value=use(promise);useLayoutEffect(()=>{commits+=1;},[]);return <p>{value}</p>;}
  // Zawieszenie wewnątrz act wymaga await — inaczej React ostrzega i nie dokończy pracy po rozwiązaniu obietnicy.
  await act(async()=>{render(<Suspense fallback={<p>Ładowanie…</p>}><Data/></Suspense>);});
  expect(screen.getByText("Ładowanie…")).toBeInTheDocument();
  expect(renders).toBeGreaterThan(0);
  expect(commits).toBe(0);
  await act(async()=>{resolve("API timeout");await promise;});
  expect(screen.getByText("API timeout")).toBeInTheDocument();
  expect(commits).toBe(1);
});
test("pole reaguje od razu, a lista dostaje wynik przejścia",async()=>{
  slowListRenders.reset();
  const user=userEvent.setup();
  render(<InterruptDemo delay={0}/>);
  await user.type(screen.getByLabelText("Szukaj"),"api");
  expect(screen.getByLabelText("Szukaj")).toHaveValue("api");
  expect(await screen.findByText("Wyniki dla „api”")).toBeInTheDocument();
  expect(screen.queryByText("Liczenie…")).not.toBeInTheDocument();
  expect(slowListRenders.get()).toBeGreaterThanOrEqual(2);
});`;
const header=String.raw`import {StrictMode,Suspense,act,use,useEffect,useLayoutEffect,useState,startTransition,type ReactNode} from "react";
import {createRoot} from "react-dom/client";
import {buildFibers,performUnitOfWork,startWork,walk,workLoop,type Spec} from "./fiberModel";
import {FiberRoot} from "./doubleBuffer";
const tree:Spec={name:"App",children:[{name:"Header"},{name:"Main",children:[{name:"Table"}]}]};
function mount(ui:ReactNode){const host=document.createElement("div");const root=createRoot(host);act(()=>root.render(ui));return {host,root};}
void StrictMode;void Suspense;void use;void useEffect;void useLayoutEffect;void useState;void startTransition;void buildFibers;void performUnitOfWork;void startWork;void walk;void workLoop;void FiberRoot;void tree;void mount;
`;
const exercises=[
  ['Zbuduj z opisu drzewa listę powiązaną fiberów ze wskaźnikami child, sibling i parent.','Pierwsze dziecko trafia do child, kolejne łączysz przez sibling.',model],
  ['Napisz generator walk przechodzący drzewo pętlą i zwracający kroki beginWork/completeWork.','Po dojściu do liścia wychodź w górę, aż znajdziesz rodzeństwo.',model],
  ['Sprawdź testem pełną kolejność kroków dla drzewa DevDesk.','Liść daje beginWork i od razu completeWork.',modelTests],
  ['Wstrzymaj generator po dwóch krokach i dokończ pracę później.','Generator pamięta, gdzie skończył.',modelTests],
  ['Napisz performUnitOfWork zwracające następny fiber do przetworzenia.','Zwróć child, a bez dziecka — rodzeństwo najbliższego przodka.',model],
  ['Napisz workLoop, który przerywa pracę, gdy shouldYield() zwraca true.','Zapamiętaj następny fiber w obiekcie pracy.',model],
  ['Udowodnij, że praca w kawałkach daje ten sam log co praca bez przerw.','Budżet kroków symuluje oddawanie sterowania.',modelTests],
  ['Sprawdź, że po przerwaniu work.next wskazuje fiber, od którego trzeba wznowić.','Po Header następny jest Main.',modelTests],
  ['Sprawdź, że praca na poddrzewie kończy się na jego korzeniu.','Bez warunku current===root pętla przeszłaby do rodzeństwa korzenia.',modelTests],
  ['Zbuduj model podwójnego buforowania: current, WIP, commit i abandon.','Commit to jedyne miejsce, które zmienia current.',buffers],
  ['Sprawdź, że render buduje WIP bez zmiany current.','Ekran pokazuje current do chwili commitu.',bufferTests],
  ['Sprawdź zamianę drzew w commit i licznik commitów.','Po commit WIP znika.',bufferTests],
  ['Sprawdź porzucenie renderu i ponowny start od current.','Porzucony WIP nie zostawia śladu na ekranie.',bufferTests],
  ['Sprawdź, że WIP jest kopią danych wejściowych.','Mutacja tablicy po beginRender nie może zmienić drzewa.',bufferTests],
  ['Zbuduj InterruptDemo z pilną aktualizacją pola i niepilną aktualizacją listy.','startTransition oznacza aktualizację jako przerywalną.',interrupt],
  ['Przetestuj, że pole reaguje od razu, a lista dostaje wynik przejścia.','W teście ustaw delay=0, żeby nie spowalniać przebiegu.',reactTests],
  ['Pokaż testem podwójny render i efekt setup–cleanup–setup w StrictMode.','To celowe ujawnianie nieczystych renderów i brakującego sprzątania.',reactTests],
  ['Pokaż render bez commitu przez komponent zawieszony na use(promise).','useLayoutEffect liczy commity; fallback jest na ekranie.',reactTests],
  ['Przenieś wysyłkę zdarzenia analitycznego z renderu do obsługi zdarzenia.','Akcja użytkownika to jednoznaczny moment wysyłki.',String.raw`import {useState} from "react";
export function TrackedButton({send}:{send:(event:string)=>void}){
  const [count,setCount]=useState(0);
  // ŹLE byłoby: send("render") w ciele komponentu — porzucony lub powtórzony render wyśle zdarzenie fałszywie.
  return <button type="button" onClick={()=>{setCount(c=>c+1);send("click");}}>Kliknięto {count}</button>;
}`],
  ['Wyślij zdarzenie wyświetlenia ekranu w efekcie i opisz zachowanie w StrictMode.','W StrictMode efekt uruchomi się dwa razy w trybie deweloperskim — usługa powinna deduplikować.',String.raw`import {useEffect} from "react";
export function TicketScreen({ticketId,track}:{ticketId:number;track:(event:string)=>void}){
  useEffect(()=>{
    // Po commicie: ekran naprawdę został pokazany. W StrictMode (dev) setup wykona się dwa razy.
    track("ticket_view:"+ticketId);
  },[ticketId,track]);
  return <h1>Ticket #{ticketId}</h1>;
}`],
  ['Wyjaśnij w komentarzu, dlaczego render może być przerwany, a commit nie.','Render pracuje na WIP, commit zmienia ekran.',String.raw`// Render pracuje wyłącznie na drzewie work-in-progress — niczego nie zmienia na ekranie,
// więc można go wstrzymać, wznowić, porzucić albo powtórzyć.
// Commit nanosi zmiany na DOM; przerwany w połowie pokazałby użytkownikowi pół aktualizacji,
// dlatego jest synchroniczny i nieprzerywalny. Stąd wymóg: render czysty, efekty uboczne po commicie.
export {};`],
  ['Wypisz, co przechowuje fiber, a czego nie ma w elemencie.','Stan, hooki, węzeł DOM, flagi, priorytet.',String.raw`// Fiber: type, key, pendingProps/memoizedProps, memoizedState (lista hooków), stateNode (węzeł DOM),
// child/sibling/return (drzewo), flags (praca w commit), lanes (priorytet oczekującej pracy), alternate (para current/WIP).
// Element: tylko type, props, key — jednorazowy opis z JSX.
export {};`],
  ['Napisz test, że licznik renderów w StrictMode jest dwa razy większy niż bez niego.','StrictMode podwaja render tylko w trybie deweloperskim.',String.raw`import {StrictMode} from "react";
import {render} from "@testing-library/react";
import {expect,test} from "vitest";
test("dwa razy więcej renderów w StrictMode",()=>{
  let renders=0;
  function C(){renders+=1;return null;}
  render(<C/>);
  const plain=renders;renders=0;
  render(<StrictMode><C/></StrictMode>);
  expect(renders).toBe(plain*2);
});`],
  ['Przejdź drzewo pętlą bez rekurencji i policz węzły.','Każdy węzeł ma dokładnie jeden krok beginWork.',String.raw`import {buildFibers,walk,type Spec} from "./fiberModel";
export function countNodes(spec:Spec){
  let count=0;
  for(const step of walk(buildFibers(spec)))if(step.startsWith("beginWork"))count+=1;
  return count;
}`],
  ['Znajdź ścieżkę od fibera do korzenia przez wskaźniki parent.','To odpowiednik wskaźnika return w Reakcie.',String.raw`import type {FiberNode} from "./fiberModel";
export function pathToRoot(node:FiberNode){
  const names:string[]=[];
  for(let current:FiberNode|undefined=node;current;current=current.parent)names.push(current.name);
  return names.join(" → ");
}`],
  ['Zasymuluj przerwanie: porzuć WIP po nadejściu pilnej aktualizacji i zacznij od nowa.','Pilna aktualizacja zastępuje rozpoczęty render niepilny.',String.raw`import {FiberRoot} from "./doubleBuffer";
export function urgentWins(){
  const root=new FiberRoot({version:1,labels:["lista"]});
  root.beginRender(["lista","wolny filtr"]); // niepilny render w toku
  root.abandon();                           // przyszło wpisanie znaku
  root.beginRender(["lista","pole: a"]);     // pilny render
  root.commit();
  return root.current;
}`],
  ['Wyjaśnij, dlaczego fibery nie są publicznym API.','Szczegół implementacji może się zmienić; oglądaj je w DevTools.',String.raw`// Fibery nie mają publicznych typów i zmieniają się między wersjami Reacta.
// Nie odczytuj __reactFiber$… z węzłów DOM ani pól wewnętrznych w kodzie aplikacji.
// Model mentalny: fiber = trwała jednostka pracy i stanu; narzędzie: React DevTools (props, hooki, rendered by).
export {};`],
  ['Napisz workLoop z budżetem czasu zamiast liczby kroków.','shouldYield porównuje performance.now() z terminem.',String.raw`import {startWork,workLoop,type FiberNode} from "./fiberModel";
export function renderWithDeadline(root:FiberNode,sliceMs:number){
  const work=startWork(root);
  let slices=0;
  for(;;){
    const deadline=performance.now()+sliceMs;
    slices+=1;
    if(workLoop(work,()=>performance.now()>=deadline))return {log:work.log,slices};
  }
}`],
  ['Pokaż w teście, że useTransition daje isPending tylko w trakcie przejścia.','Po zakończeniu przejścia napis Liczenie… znika.',reactTests],
  ['Porównaj InterruptDemo bez startTransition i opisz różnicę.','Każdy render staje się pilny i blokuje pole.',String.raw`import {useState} from "react";
export function BlockingDemo(){
  const [text,setText]=useState("");
  // Bez transition: query = text, każda zmiana renderuje wolną listę pilnie — pole „zacina się”.
  return <div>
    <input aria-label="Szukaj" value={text} onChange={e=>setText(e.target.value)}/>
    <p>Wyniki dla „{text}”</p>
  </div>;
}`],
];
const quiz=[];function q(...row){quiz.push(row);}
q('Czym jest fiber?','Trwałym wewnętrznym obiektem Reacta dla elementu drzewa: jednostką pracy i miejscem stanu','Elementem z JSX','Węzłem DOM','Wątkiem przeglądarki','Fibery żyją przez całe życie komponentu na danej pozycji.');
q('Od której wersji React używa architektury Fiber?','Od React 16','Od React 18','Od React 19','Od React 0.14','Fiber zastąpił wcześniejszy, rekurencyjny „stack reconciler”.');
q('Gdzie fiber przechowuje stan komponentu funkcyjnego?','W memoizedState — liście hooków','W props','W elemencie','W stateNode','Lekcja 6 pokaże tę listę dokładniej.');
q('Co przechowuje stateNode dla tagu HTML?','Powiązany węzeł DOM','Stan komponentu','Klucz','Priorytet','Dla komponentów funkcyjnych stateNode jest pusty.');
q('Do czego służą wskaźniki child, sibling i return?','Tworzą drzewo jako listę powiązaną','Do przechowywania stanu','Do kolejki aktualizacji','Do stylów','Dzięki nim drzewo można przechodzić pętlą.');
q('Co wskazuje child?','Pierwsze dziecko','Ostatnie dziecko','Wszystkie dzieci','Rodzica','Kolejne dzieci osiąga się przez sibling.');
q('Co wskazuje return?','Rodzica, do którego wraca praca','Wartość zwróconą przez komponent','Następne rodzeństwo','Korzeń aplikacji','Nazwa „return” nawiązuje do powrotu z wywołania.');
q('Do czego służą flags fibera?','Zapisują, co trzeba zrobić w commicie (wstawić, zaktualizować, usunąć, efekty)','Do priorytetu','Do stylów','Do kluczy','Commit przechodzi po fiberach z flagami.');
q('Do czego służą lanes fibera?','Do priorytetu oczekującej pracy','Do kolejności dzieci','Do kluczy','Do efektów','Szczegóły w lekcji 5.');
q('Dlaczego przechodzenie pętlą jest ważne?','Pozwala zapamiętać bieżący fiber i wrócić później, zamiast tkwić w głębokiej rekurencji','Bo rekurencja nie działa w JS','Bo jest zawsze szybsze','Bo DOM tego wymaga','Rekurencji na stosie wywołań nie da się przerwać i wznowić.');
q('Czym jest drzewo current?','Tym, co jest aktualnie na ekranie','Drzewem budowanym w renderze','Drzewem elementów','Drzewem DOM','Current to zatwierdzony stan.');
q('Czym jest drzewo work-in-progress?','Drzewem budowanym w trakcie renderu na podstawie current','Drzewem na ekranie','Kopią DOM','Drzewem elementów','WIP nie dotyka ekranu.');
q('Co dzieje się z drzewami po commicie?','Zamieniają się rolami: WIP staje się current','WIP jest usuwane, current zostaje','Oba są usuwane','Powstaje trzecie drzewo','Technika podobna do podwójnego buforowania w grafice.');
q('Dlaczego render można przerwać?','Pracuje tylko na WIP i niczego nie zmienia na ekranie','Bo jest asynchroniczny w przeglądarce','Bo działa w Web Workerze','Bo commit go powtarza','Porzucony WIP nie zostawia śladu.');
q('Dlaczego commit jest nieprzerywalny?','Użytkownik nie może zobaczyć połowy aktualizacji DOM','Bo jest szybki','Bo React nie umie go przerwać technicznie','Bo przeglądarka blokuje DOM','Zmiany trafiają na ekran naraz.');
q('Co wynika z przerywalności renderu dla kodu komponentów?','Render musi być czysty — bez efektów ubocznych','Render musi być asynchroniczny','Render musi być krótki','Render musi używać klas','Render może być powtórzony lub porzucony.');
q('Co się stanie z efektem ubocznym w porzuconym renderze?','Wykona się, choć użytkownik nigdy nie zobaczył tego ekranu','Nie wykona się','Wykona się po commicie','React go cofnie','Dlatego efekty uboczne należą do efektów lub obsługi zdarzeń.');
q('Co robi StrictMode w trybie deweloperskim z renderem?','Wywołuje go dwa razy, ujawniając nieczystość','Wyłącza go','Przyspiesza go','Pomija efekty','W produkcji nie ma podwójnego renderu.');
q('Co robi StrictMode z efektami przy montowaniu?','Uruchamia setup, cleanup i ponownie setup','Pomija efekty','Uruchamia efekty trzy razy','Uruchamia tylko cleanup','Ujawnia brakujące sprzątanie.');
q('Czy StrictMode wpływa na zachowanie w produkcji?','Nie, podwójne wywołania działają tylko w trybie deweloperskim','Tak, zawsze','Tylko dla efektów','Tylko w React 19','To narzędzie diagnostyczne.');
q('Czym jest „oddanie sterowania” w pętli pracy?','Przerwaniem renderu, by przeglądarka obsłużyła zdarzenia i malowanie','Zakończeniem renderu','Wywołaniem commit','Uruchomieniem efektów','React wraca później do zapamiętanego fibera.');
q('Jak często React w trybie współbieżnym sprawdza, czy oddać sterowanie?','Co kilka milisekund','Co sekundę','Po każdym commicie','Nigdy','Krótkie kawałki pracy utrzymują płynność.');
q('Co robi React, gdy podczas niepilnego renderu przyjdzie pilniejsza aktualizacja?','Porzuca rozpoczęte WIP i zaczyna od nowa z nowym stanem','Kończy stary render, potem nowy','Ignoruje pilną aktualizację','Łączy oba drzewa w commicie','Pilność decyduje o kolejności.');
q('Jak oznaczyć aktualizację jako niepilną?','startTransition / useTransition','flushSync','useEffect','setTimeout','Szczegóły w rozdziale 16.');
q('Co pokazuje isPending z useTransition?','Że przejście jest w toku','Że komponent się montuje','Że jest błąd','Że trwa commit','Można pokazać wskaźnik „Liczenie…”.');
q('Dlaczego pole w InterruptDemo reaguje od razu?','Aktualizacja pola jest pilna, a lista — niepilna i przerywalna','Bo pole jest niekontrolowane','Bo lista renderuje się w Workerze','Bo React pomija render listy','Pilne aktualizacje mają pierwszeństwo.');
q('Co się stanie po usunięciu startTransition w InterruptDemo?','Każdy znak wywoła pilny, nieprzerywalny render wolnej listy i pole zacznie się zacinać','Nic się nie zmieni','Lista przestanie się aktualizować','Pole przestanie działać','Wszystkie aktualizacje będą pilne.');
q('Czy JavaScript w przeglądarce jest wielowątkowy dla renderu Reacta?','Nie, React dzieli pracę na kawałki w jednym wątku','Tak, każdy komponent ma wątek','Tak, w trybie współbieżnym','Tylko w Chrome','Współbieżność to przeplatanie, nie równoległość.');
q('Czym różni się współbieżność od równoległości w Reakcie?','Przeplatanie pracy w jednym wątku zamiast jednoczesnego wykonania','Niczym','Współbieżność używa wielu rdzeni','Równoległość jest wolniejsza','React nie renderuje na wielu wątkach.');
q('Co oznacza beginWork w modelu pętli?','Pracę przy wejściu do węzła: wywołanie komponentu, porównanie dzieci','Commit węzła','Usunięcie węzła','Malowanie','Właściwa nazwa funkcji w kodzie Reacta.');
q('Co oznacza completeWork w modelu pętli?','Pracę przy wyjściu z węzła, np. przygotowanie węzła DOM','Montowanie efektów','Ponowny render','Zamianę drzew','W completeWork powstają m.in. instancje DOM dla nowych tagów.');
q('Kiedy węzeł dostaje completeWork?','Gdy wszystkie jego dzieci zostały ukończone','Przed dziećmi','Tylko dla liści','Po commicie','Liść kończy się od razu po beginWork.');
q('Jaka jest kolejność dla App z dziećmi A i B (liście)?','begin App, begin A, complete A, begin B, complete B, complete App','begin App, complete App, begin A, begin B','begin A, begin B, begin App','complete A, complete B, complete App','Przejście w głąb z powrotem przez rodzeństwo.');
q('Dlaczego render może zostać wykonany kilka razy bez commitu?','Bo może być porzucony i powtórzony, albo zawieszony przez Suspense','Bo React ma błąd','Bo efekty go wywołują','Bo DOM się zmienia','Commit następuje tylko dla ukończonego WIP.');
q('Co obserwujesz, gdy komponent zawiesza się przez use(promise)?','Render został wykonany, ale nic z niego nie trafiło na ekran — widać fallback','Komponent się nie renderuje','Komponent rzuca błąd na ekran','Efekty uruchamiają się od razu','Commit dotyczy fallbacku, nie zawieszonego poddrzewa.');
q('Czy useLayoutEffect uruchamia się dla zawieszonego komponentu?','Nie, dopiero po jego commicie','Tak, zawsze','Tak, ale bez DOM','Tylko w StrictMode','Efekty należą do fazy commit.');
q('Dlaczego licznik renderów w InterruptDemo to tylko obserwacja?','Zmiana zmiennej w renderze łamie zasadę czystości','Bo jest wolny','Bo React go resetuje','Bo jest w module','Wyłącznie do nauki, nie do kodu produkcyjnego.');
q('Czy kod aplikacji powinien czytać __reactFiber z węzła DOM?','Nie, to wewnętrzny szczegół implementacji','Tak, do debugowania produkcji','Tak, w TSX','Tylko w testach','Struktury mogą się zmienić bez ostrzeżenia.');
q('Gdzie bezpiecznie oglądać fibery?','W React DevTools','W konsoli przez __reactFiber','W kodzie produkcyjnym','W DOM','DevTools prezentują props, hooki i relacje.');
q('Co pokazuje opcja „Highlight updates” w DevTools?','Które komponenty renderują się przy aktualizacji','Zmiany CSS','Żądania sieciowe','Błędy','Pomaga porównać wersję z transition i bez.');
q('Co oznacza „alternate” we fiberze?','Wskaźnik na odpowiadający fiber w drugim drzewie (current ↔ WIP)','Inny klucz','Element zastępczy','Węzeł fallback Suspense','Pary fiberów są ponownie używane między renderami.');
q('Dlaczego React ponownie używa fiberów z alternate?','Żeby nie alokować całego drzewa przy każdym renderze','Bo DOM tego wymaga','Bo stan jest w alternate','Bo klucze tego wymagają','Podwójne buforowanie bez ciągłego tworzenia obiektów.');
q('Czy element z JSX jest trwały jak fiber?','Nie, powstaje przy każdym renderze i służy do porównania','Tak','Tylko z kluczem','Tylko w StrictMode','Fibery trwają, elementy nie.');
q('Co to jest pendingProps?','Nowe props dla bieżącego renderu','Props z poprzedniego commitu','Props domyślne','Props kontekstu','Porównywane z memoizedProps.');
q('Co to jest memoizedProps?','Props użyte w ostatnim ukończonym renderze','Props z memo','Props przyszłe','Props z URL','Pozwalają wykryć, czy coś się zmieniło.');
q('Jaki problem rozwiązał Fiber względem starego „stack reconciler”?','Brak możliwości przerwania długiego renderu','Brak kluczy','Brak hooków','Brak JSX','Stary algorytm był rekurencyjny i nieprzerywalny.');
q('Czy hooki istniały przed Fiber?','Nie, hooki pojawiły się w React 16.8 i opierają się na fiberach','Tak, od React 0.1','Tak, w klasach','Tylko w React Native','Lista hooków żyje we fiberze.');
q('Co jest wspólne dla porzuconego renderu i podwójnego renderu w StrictMode?','Render wykonuje się bez odpowiadającego mu commitu','Oba zmieniają DOM','Oba uruchamiają efekty','Oba są błędami','Kod renderu musi to znosić.');
q('Gdzie wysłać zdarzenie analityczne „kliknięto przycisk”?','W obsłudze zdarzenia onClick','W ciele komponentu','W useMemo','W stałej modułu','Akcja użytkownika to jednoznaczny moment.');
q('Gdzie wysłać zdarzenie „wyświetlono ekran”?','W efekcie po commicie, z deduplikacją po stronie usługi','W ciele komponentu','W useMemo','W konstruktorze','Efekt wykonuje się dla ekranu, który naprawdę się pojawił.');
q('Dlaczego efekt wyświetlenia ekranu wywoła się dwa razy w StrictMode (dev)?','StrictMode symuluje odmontowanie i ponowne zamontowanie','Bo efekt ma złe zależności','Bo render jest podwójny','Bo React ma błąd','W produkcji wykona się raz.');
q('Co jest „jednostką pracy” w Reakcie?','Przetworzenie jednego fibera','Cały render aplikacji','Jeden commit','Jedno żądanie HTTP','Podział na jednostki umożliwia przerywanie.');
q('Co zwraca performUnitOfWork w modelu z lekcji?','Następny fiber do przetworzenia albo null','Element','Węzeł DOM','Log','React ma funkcję o tej samej roli.');
q('Dlaczego generator dobrze modeluje przerywalną pracę?','Zatrzymuje się po każdym yield i pamięta miejsce, z którego wznowi','Bo działa w osobnym wątku','Bo jest szybszy od pętli','Bo React używa generatorów wewnątrz','React ma własną pętlę; generator to tylko model mentalny.');
q('Co zrobi workLoop, gdy shouldYield zwraca zawsze false?','Wykona całą pracę bez przerw, jak render synchroniczny','Nie wykona niczego','Wykona jeden krok','Rzuci błąd','Tak wygląda praca w trybie synchronicznym.');
q('Co zrobi workLoop, gdy shouldYield zwraca zawsze true?','Nie wykona żadnej jednostki pracy i zwróci false','Wykona całą pracę','Wykona commit','Porzuci WIP','Sprawdzenie jest przed każdą jednostką.');
q('Czym różni się renderowanie synchroniczne od współbieżnego?','Synchroniczne kończy render bez przerw, współbieżne może oddawać sterowanie','Niczym','Współbieżne używa wątków','Synchroniczne pomija commit','flushSync wymusza tryb synchroniczny.');
q('Które aktualizacje React traktuje jako pilne?','Bezpośrednie reakcje na wpisywanie, kliknięcia, naciśnięcia klawiszy','Wszystkie w startTransition','Pobieranie danych','Aktualizacje z setTimeout zawsze','Pilne aktualizacje mają najwyższy priorytet.');
q('Czy porzucenie renderu oznacza utratę stanu komponentów?','Nie, stan current zostaje; porzucony jest tylko WIP','Tak, zawsze','Tylko dla nowych komponentów','Tak, w StrictMode','Ekran i zatwierdzony stan pozostają nietknięte.');
q('Dlaczego mutacja obiektu props w renderze jest niebezpieczna przy przerywanym renderze?','Zmiana przetrwa porzucony render i zepsuje current','Bo props są zamrożone tylko w produkcji','Bo React kopiuje props','Nie jest niebezpieczna','Czystość renderu chroni przed takimi wyciekami.');
q('Co jest odpowiednikiem completeWork dla nowego <li> w react-dom?','Utworzenie instancji węzła DOM i ustawienie atrybutów (jeszcze poza dokumentem)','Wstawienie do dokumentu','Uruchomienie efektu','Malowanie','Wstawienie do dokumentu następuje w commicie.');
q('Kiedy nowy węzeł DOM trafia do dokumentu?','W fazie commit','W beginWork','W completeWork','Przy tworzeniu elementu','Render przygotowuje, commit wstawia.');
q('Dlaczego React może renderować komponent bez wywoływania efektów?','Efekty należą do commitu, którego dany render mógł nie mieć','Bo efekty są opcjonalne','Bo efekty działają tylko w przeglądarce','Nie może','To podstawa rozdziału 07.');
q('Co ma wspólnego Suspense z architekturą Fiber?','Zawieszony render można porzucić i pokazać fallback, a potem wznowić','Nic','Suspense działa tylko w klasach','Suspense wyłącza Fiber','Przerywalność umożliwia czekanie na dane w renderze.');
q('Jak React pamięta, gdzie przerwał pracę?','Zapamiętuje wskaźnik na następny fiber do przetworzenia','Zapisuje stos wywołań','Zapisuje DOM','Nie pamięta — zaczyna od początku zawsze','W modelu to pole work.next.');
q('Dlaczego czysty render ułatwia testowanie?','Ten sam stan i props dają ten sam wynik, niezależnie od liczby wywołań','Bo testy działają tylko z czystymi komponentami','Bo czysty render jest szybszy','Nie ma związku','Przewidywalność to zysk także poza współbieżnością.');
q('Jaki mechanizm w produkcji może wywołać render bez commitu?','Przerwanie niepilnego renderu albo zawieszenie przez Suspense','Tylko StrictMode','Tylko błąd','Nie ma takiego mechanizmu','StrictMode działa tylko w dev, ale przerwania i Suspense — także w produkcji.');
const probes=[
  ['Pierwszy krok walk','beginWork App','beginWork Header','completeWork App','completeWork Header','console.log(walk(buildFibers(tree)).next().value);'],
  ['Liczba kroków walk','8','4','6','10','console.log([...walk(buildFibers(tree))].length);'],
  ['Ostatni krok walk','completeWork App','completeWork Table','completeWork Main','beginWork Table','console.log([...walk(buildFibers(tree))].at(-1));'],
  ['Trzeci krok walk','completeWork Header','beginWork Main','beginWork Table','completeWork App','const steps=walk(buildFibers(tree));steps.next();steps.next();console.log(steps.next().value);'],
  ['Kroki po wznowieniu','6','8','2','0','const steps=walk(buildFibers(tree));steps.next();steps.next();console.log([...steps].length);'],
  ['Rodzeństwo Header','Main','Table','App','undefined','console.log(buildFibers(tree).child?.sibling?.name);'],
  ['Rodzic Table','Main','App','Header','undefined','console.log(buildFibers(tree).child?.sibling?.child?.parent?.name);'],
  ['Rodzeństwo Main','undefined','Header','Table','App','console.log(String(buildFibers(tree).child?.sibling?.sibling?.name));'],
  ['Następny fiber po liściu Header','Main','App','Table','null','const app=buildFibers(tree),log:string[]=[];console.log(performUnitOfWork(app.child!,app,log)?.name);'],
  ['Log po liściu Header','begin Header,complete Header','begin Header','complete Header','begin Header,begin Main','const app=buildFibers(tree),log:string[]=[];performUnitOfWork(app.child!,app,log);console.log(log.join(","));'],
  ['Następny fiber po ostatnim liściu','null','App','Main','Table','const app=buildFibers(tree),table=app.child!.sibling!.child!,log:string[]=[];console.log(String(performUnitOfWork(table,app,log)?.name??null));'],
  ['Log po ostatnim liściu','begin Table,complete Table,complete Main,complete App','begin Table,complete Table','complete Table,complete Main','begin Table','const app=buildFibers(tree),table=app.child!.sibling!.child!,log:string[]=[];performUnitOfWork(table,app,log);console.log(log.join(","));'],
  ['workLoop z budżetem 1','false','true','undefined','null','const work=startWork(buildFibers(tree));let b=1;console.log(workLoop(work,()=>b--<=0));'],
  ['Następny po budżecie 1','Header','App','Main','null','const work=startWork(buildFibers(tree));let b=1;workLoop(work,()=>b--<=0);console.log(work.next?.name);'],
  ['Liczba jednostek pracy dla drzewa','4','8','3','6','const work=startWork(buildFibers(tree));let units=0;workLoop(work,()=>{units+=1;return false;});console.log(units);'],
  ['Liczba kawałków przy budżecie 1','4','1','8','2','const work=startWork(buildFibers(tree));let slices=0;for(;;){let b=1;slices+=1;if(workLoop(work,()=>b--<=0))break;}console.log(slices);'],
  ['Wersja WIP','2','1','0','3','const root=new FiberRoot({version:1,labels:[]});console.log(root.beginRender(["a"]).version);'],
  ['current przed commitem','a','a,b','pusty','b','const root=new FiberRoot({version:1,labels:["a"]});root.beginRender(["a","b"]);console.log(root.current.labels.join(","));'],
  ['current po commicie','a,b','a','pusty','b','const root=new FiberRoot({version:1,labels:["a"]});root.beginRender(["a","b"]);root.commit();console.log(root.current.labels.join(","));'],
  ['Po porzuceniu','a','a,b','pusty','b','const root=new FiberRoot({version:1,labels:["a"]});root.beginRender(["a","b"]);root.abandon();console.log(root.current.labels.join(","));'],
  ['Commit bez WIP','Brak gotowego drzewa WIP','ok','undefined','null','try{new FiberRoot({version:1,labels:[]}).commit();console.log("ok");}catch(e){console.log((e as Error).message);}'],
  ['Liczba commitów po dwóch renderach i jednym porzuceniu','1','2','0','3','const root=new FiberRoot({version:1,labels:[]});root.beginRender(["a"]);root.abandon();root.beginRender(["b"]);root.commit();console.log(root.commits);'],
  ['Rendery w StrictMode','2','1','4','0','let renders=0;function C(){renders+=1;return null;}mount(<StrictMode><C/></StrictMode>);console.log(renders);'],
  ['Rendery bez StrictMode','1','2','0','4','let renders=0;function C(){renders+=1;return null;}mount(<C/>);console.log(renders);'],
  ['Efekty przy montowaniu w StrictMode','setup,cleanup,setup','setup','setup,setup','cleanup,setup','const log:string[]=[];function C(){useEffect(()=>{log.push("setup");return ()=>{log.push("cleanup");};},[]);return null;}mount(<StrictMode><C/></StrictMode>);console.log(log.join(","));'],
  ['Efekt bez StrictMode','setup','setup,cleanup,setup','setup,setup','pusty','const log:string[]=[];function C(){useEffect(()=>{log.push("setup");return ()=>{log.push("cleanup");};},[]);return null;}mount(<C/>);console.log(log.join(",")||"pusty");'],
  ['Zdarzenia analityczne z renderu w StrictMode','2','1','0','4','let sent=0;function Screen(){sent+=1;return <h1>x</h1>;}mount(<StrictMode><Screen/></StrictMode>);console.log(sent);'],
  ['Fallback przy zawieszeniu','Ładowanie…','API timeout','pusty','błąd','const p=new Promise<string>(()=>{});function Data(){return <p>{use(p)}</p>;}const v=mount(<Suspense fallback={<p>Ładowanie…</p>}><Data/></Suspense>);console.log(v.host.textContent);'],
  ['Commity zawieszonego komponentu','0','1','2','undefined','let commits=0;const p=new Promise<string>(()=>{});function Data(){useLayoutEffect(()=>{commits+=1;},[]);return <p>{use(p)}</p>;}mount(<Suspense fallback={<p>…</p>}><Data/></Suspense>);console.log(commits);'],
  ['Rendery zawieszonego komponentu (więcej niż zero)','true','false','undefined','null','let renders=0;const p=new Promise<string>(()=>{});function Data(){renders+=1;return <p>{use(p)}</p>;}mount(<Suspense fallback={<p>…</p>}><Data/></Suspense>);console.log(renders>0);'],
  ['Kolejność render i layout effect','render,layout','layout,render','render','layout','const log:string[]=[];function C(){log.push("render");useLayoutEffect(()=>{log.push("layout");},[]);return null;}mount(<C/>);console.log(log.join(","));'],
  ['Wynik przejścia po act','b','a','pusty','undefined','let set!:(v:string)=>void;function C(){const [v,setV]=useState("a");set=setV;return <p>{v}</p>;}const view=mount(<C/>);act(()=>{startTransition(()=>set("b"));});console.log(view.host.textContent);'],
  ['Stan po dwóch pilnych aktualizacjach w jednym act','2','1','0','undefined','let inc!:()=>void;function C(){const [n,setN]=useState(0);inc=()=>setN(x=>x+1);return <p>{n}</p>;}const view=mount(<C/>);act(()=>{inc();inc();});console.log(view.host.textContent);'],
];
for(const [label,answer,b,c,d,body] of probes)q(label+' — co wypisze kod?',answer,b,c,d,'Wynik pochodzi z uruchomienia w Vitest: model pętli pracy z lekcji oraz prawdziwy React 19 (tryb deweloperski, jsdom).',header+body);
if(exercises.length!==30||quiz.length!==100)throw Error('Niepełny zestaw '+exercises.length+'/'+quiz.length);
const project={'src/fiberModel.ts':model,'src/doubleBuffer.ts':buffers,'src/InterruptDemo.tsx':interrupt,'src/fiberModel.test.ts':modelTests,'src/doubleBuffer.test.ts':bufferTests,'src/react.test.tsx':reactTests};
const mutants=[
  {file:'src/fiberModel.ts',before:'    if(current===root)return null;\n',after:''},
  {file:'src/fiberModel.ts',before:'while(work.next&&!shouldYield())',after:'while(work.next)'},
  {file:'src/doubleBuffer.ts',before:'    this.current=this.wip;\n',after:''},
  {file:'src/doubleBuffer.ts',before:'labels:[...labels]',after:'labels'},
];
module.exports={exercises,quiz,project,mutants,probeCount:probes.length};
