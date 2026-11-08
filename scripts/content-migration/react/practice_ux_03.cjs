const {lesson}=require('./practice_kit.cjs');
const model=String.raw`import {arrayMove} from "@dnd-kit/sortable";

export type TicketStatus="OPEN"|"IN_PROGRESS"|"CLOSED";
export type Board=Record<TicketStatus,string[]>;
export const COLUMNS:readonly TicketStatus[]=["OPEN","IN_PROGRESS","CLOSED"];

export function reorder(items:readonly string[],activeId:string,overId:string|null){
  if(!overId||activeId===overId)return items;
  const from=items.indexOf(activeId),to=items.indexOf(overId);
  if(from<0||to<0)return items;
  return arrayMove([...items],from,to);
}

export function shift(items:readonly string[],id:string,delta:-1|1){
  const from=items.indexOf(id),to=from+delta;
  if(from<0||to<0||to>=items.length)return items;
  return arrayMove([...items],from,to);
}

export function isColumn(id:string):id is TicketStatus{
  return (COLUMNS as readonly string[]).includes(id);
}

export function findColumn(board:Board,id:string):TicketStatus|undefined{
  if(isColumn(id))return id;
  return COLUMNS.find(column=>board[column].includes(id));
}

// onDragOver: gdy karta wchodzi nad inną kolumnę, od razu przenosimy ją w stanie.
export function dragOver(board:Board,activeId:string,overId:string|null):Board{
  if(!overId)return board;
  const from=findColumn(board,activeId),to=findColumn(board,overId);
  if(!from||!to||from===to)return board;
  const overIndex=isColumn(overId)?board[to].length:board[to].indexOf(overId);
  return {
    ...board,
    [from]:board[from].filter(id=>id!==activeId),
    [to]:board[to].toSpliced(overIndex,0,activeId),
  };
}

// onDragEnd: ostateczna pozycja w kolumnie.
export function dragEnd(board:Board,activeId:string,overId:string|null):Board{
  if(!overId)return board;
  const column=findColumn(board,activeId);
  if(!column||findColumn(board,overId)!==column||isColumn(overId))return board;
  return {...board,[column]:[...reorder(board[column],activeId,overId)]};
}`;
const announcements=String.raw`import type {Announcements} from "@dnd-kit/core";

export function createAnnouncements(titleOf:(id:string|number)=>string):Announcements{
  return {
    onDragStart:({active})=>"Podniesiono „"+titleOf(active.id)+"”.",
    onDragOver:({active,over})=>over?"„"+titleOf(active.id)+"” nad „"+titleOf(over.id)+"”.":"„"+titleOf(active.id)+"” poza listą.",
    onDragEnd:({active,over})=>over?"Upuszczono „"+titleOf(active.id)+"” na miejsce „"+titleOf(over.id)+"”.":"Upuszczono „"+titleOf(active.id)+"” poza listą. Kolejność bez zmian.",
    onDragCancel:({active})=>"Anulowano przenoszenie „"+titleOf(active.id)+"”.",
  };
}

export const screenReaderInstructions={
  draggable:"Aby podnieść, naciśnij spację. Strzałki przesuwają, spacja upuszcza, Escape anuluje.",
};`;
const queue=String.raw`import {useState} from "react";
import {DndContext,DragOverlay,KeyboardSensor,PointerSensor,closestCenter,useSensor,useSensors,type DragEndEvent,type DragStartEvent} from "@dnd-kit/core";
import {SortableContext,sortableKeyboardCoordinates,useSortable,verticalListSortingStrategy} from "@dnd-kit/sortable";
import {CSS} from "@dnd-kit/utilities";
import {reorder,shift} from "./boardModel";
import {createAnnouncements,screenReaderInstructions} from "./dndAnnouncements";

export type Ticket={id:string;title:string};

function SortableRow({ticket,first,last,onShift}:{ticket:Ticket;first:boolean;last:boolean;onShift:(id:string,delta:-1|1)=>void}){
  const {attributes,listeners,setNodeRef,setActivatorNodeRef,transform,transition,isDragging}=useSortable({id:ticket.id});
  return (
    <li ref={setNodeRef} style={{transform:CSS.Transform.toString(transform),transition,opacity:isDragging?0.4:1}}>
      <button type="button" ref={setActivatorNodeRef} aria-label={"Przenieś: "+ticket.title} {...attributes} {...listeners}>⠿</button>
      <span>{ticket.title}</span>
      <button type="button" aria-label={"W górę: "+ticket.title} disabled={first} onClick={()=>onShift(ticket.id,-1)}>↑</button>
      <button type="button" aria-label={"W dół: "+ticket.title} disabled={last} onClick={()=>onShift(ticket.id,1)}>↓</button>
    </li>
  );
}

export function SortableQueue({initial}:{initial:readonly Ticket[]}){
  const [tickets,setTickets]=useState(initial);
  const [activeId,setActiveId]=useState<string|null>(null);
  const [status,setStatus]=useState("");
  const sensors=useSensors(
    useSensor(PointerSensor,{activationConstraint:{distance:5}}),
    useSensor(KeyboardSensor,{coordinateGetter:sortableKeyboardCoordinates}),
  );
  const ids=tickets.map(ticket=>ticket.id);
  const titleOf=(id:string|number)=>tickets.find(ticket=>ticket.id===String(id))?.title??String(id);
  const byId=(order:readonly string[])=>order.map(id=>tickets.find(ticket=>ticket.id===id)!);

  function onDragStart(event:DragStartEvent){
    setActiveId(String(event.active.id));
  }

  function onDragEnd(event:DragEndEvent){
    setActiveId(null);
    setTickets(byId(reorder(ids,String(event.active.id),event.over?String(event.over.id):null)));
  }

  function onShift(id:string,delta:-1|1){
    const next=shift(ids,id,delta);
    setTickets(byId(next));
    setStatus(titleOf(id)+": pozycja "+(next.indexOf(id)+1)+" z "+next.length);
  }

  return (
    <DndContext
      sensors={sensors}
      collisionDetection={closestCenter}
      onDragStart={onDragStart}
      onDragEnd={onDragEnd}
      onDragCancel={()=>setActiveId(null)}
      accessibility={{announcements:createAnnouncements(titleOf),screenReaderInstructions}}
    >
      <SortableContext items={ids} strategy={verticalListSortingStrategy}>
        <ol aria-label="Kolejka na dziś">
          {tickets.map((ticket,index)=>(
            <SortableRow key={ticket.id} ticket={ticket} first={index===0} last={index===tickets.length-1} onShift={onShift}/>
          ))}
        </ol>
      </SortableContext>
      <DragOverlay dropAnimation={null}>{activeId?<div data-testid="overlay">{titleOf(activeId)}</div>:null}</DragOverlay>
      <p role="status">{status}</p>
    </DndContext>
  );
}`;
const column=String.raw`import {useDroppable} from "@dnd-kit/core";
import {SortableContext,useSortable,verticalListSortingStrategy} from "@dnd-kit/sortable";
import {CSS} from "@dnd-kit/utilities";
import type {TicketStatus} from "./boardModel";

function Card({id}:{id:string}){
  const {attributes,listeners,setNodeRef,transform,transition}=useSortable({id});
  return <li ref={setNodeRef} style={{transform:CSS.Transform.toString(transform),transition}} {...attributes} {...listeners}>{id}</li>;
}

// Pusta kolumna nie ma elementów, nad które można najechać, więc sama jest obszarem upuszczenia.
export function Column({id,title,ids}:{id:TicketStatus;title:string;ids:readonly string[]}){
  const {setNodeRef,isOver}=useDroppable({id});
  return (
    <section aria-label={title} ref={setNodeRef} data-over={isOver}>
      <h3>{title} ({ids.length})</h3>
      <SortableContext items={[...ids]} strategy={verticalListSortingStrategy}>
        <ul>{ids.map(card=><Card key={card} id={card}/>)}</ul>
      </SortableContext>
    </section>
  );
}`;
const board=String.raw`import {useRef,useState} from "react";
import {DndContext,KeyboardSensor,PointerSensor,closestCorners,useSensor,useSensors,type DragEndEvent,type DragOverEvent} from "@dnd-kit/core";
import {sortableKeyboardCoordinates} from "@dnd-kit/sortable";
import {Column} from "./Column";
import {COLUMNS,dragEnd,dragOver,type Board,type TicketStatus} from "./boardModel";

const TITLES:Record<TicketStatus,string>={OPEN:"Otwarte",IN_PROGRESS:"W toku",CLOSED:"Zamknięte"};

export function MiniBoard({initial}:{initial:Board}){
  const [board,setBoard]=useState(initial);
  const before=useRef(initial);
  const sensors=useSensors(
    useSensor(PointerSensor,{activationConstraint:{distance:5}}),
    useSensor(KeyboardSensor,{coordinateGetter:sortableKeyboardCoordinates}),
  );
  return (
    <DndContext
      sensors={sensors}
      collisionDetection={closestCorners}
      onDragStart={()=>{before.current=board;}}
      onDragOver={(event:DragOverEvent)=>setBoard(current=>dragOver(current,String(event.active.id),event.over?String(event.over.id):null))}
      onDragEnd={(event:DragEndEvent)=>setBoard(current=>dragEnd(current,String(event.active.id),event.over?String(event.over.id):null))}
      onDragCancel={()=>setBoard(before.current)}
    >
      {COLUMNS.map(column=><Column key={column} id={column} title={TITLES[column]} ids={board[column]}/>)}
    </DndContext>
  );
}`;
const lab=String.raw`import {fireEvent,render} from "@testing-library/react";
import {SortableQueue} from "./SortableQueue";

export const tickets=[{id:"a",title:"Awaria"},{id:"b",title:"Błąd"},{id:"c",title:"Cennik"}];

export function observeQueue(clicks:string[]=[]){
  const view=render(<SortableQueue initial={tickets}/>);
  try{
    for(const name of clicks)fireEvent.click(view.getByRole("button",{name}));
    return {
      order:view.getAllByRole("listitem").map(item=>item.querySelector("span")!.textContent).join(","),
      status:view.container.querySelector("p[role=status]")!.textContent||"brak",
      upFirstDisabled:(view.getAllByRole("button",{name:/^W górę/})[0] as HTMLButtonElement).disabled,
      downLastDisabled:(view.getAllByRole("button",{name:/^W dół/}).at(-1) as HTMLButtonElement).disabled,
      handleRole:view.getAllByRole("button",{name:/^Przenieś/})[0].getAttribute("aria-roledescription")??"brak",
    };
  }finally{view.unmount();}
}`;
const tests=String.raw`import {expect,test} from "vitest";
import {act,fireEvent,render,screen,waitFor,within} from "@testing-library/react";
import userEvent from "@testing-library/user-event";
import {SortableQueue} from "./SortableQueue";
import {MiniBoard} from "./MiniBoard";
import {dragEnd,dragOver,findColumn,reorder,shift,type Board} from "./boardModel";
import {createAnnouncements} from "./dndAnnouncements";
import {tickets} from "./dndLab";

const board=():Board=>({OPEN:["1","2"],IN_PROGRESS:["3"],CLOSED:[]});
const titles=()=>screen.getAllByRole("listitem").map(item=>item.querySelector("span")!.textContent);

test("reorder przenosi bez mutacji i ignoruje brak celu",()=>{
  const items=["a","b","c"];
  expect(reorder(items,"a","c")).toEqual(["b","c","a"]);
  expect(items).toEqual(["a","b","c"]);
  expect(reorder(items,"a",null)).toBe(items);
  expect(reorder(items,"a","a")).toBe(items);
  expect(reorder(items,"a","x")).toBe(items);
});

test("przenoszenie przyciskami zatrzymuje się na krańcach",()=>{
  expect(shift(["a","b"],"b",-1)).toEqual(["b","a"]);
  const items=["a","b"];
  expect(shift(items,"a",-1)).toBe(items);
  expect(shift(items,"b",1)).toBe(items);
});

test("kolumna karty i kolumna jako cel",()=>{
  expect(findColumn(board(),"3")).toBe("IN_PROGRESS");
  expect(findColumn(board(),"CLOSED")).toBe("CLOSED");
  expect(findColumn(board(),"9")).toBeUndefined();
});

test("onDragOver przenosi kartę nad inną kartę lub pustą kolumnę",()=>{
  const start=board();
  expect(dragOver(start,"1","3")).toEqual({OPEN:["2"],IN_PROGRESS:["1","3"],CLOSED:[]});
  expect(dragOver(start,"1","CLOSED")).toEqual({OPEN:["2"],IN_PROGRESS:["3"],CLOSED:["1"]});
  expect(dragOver(start,"1","IN_PROGRESS")).toEqual({OPEN:["2"],IN_PROGRESS:["3","1"],CLOSED:[]});
  expect(dragOver(start,"1","2")).toBe(start);
  expect(dragOver(start,"1",null)).toBe(start);
  expect(start).toEqual(board());
});

test("onDragEnd ustala pozycję w kolumnie",()=>{
  const start=board();
  expect(dragEnd(start,"1","2")).toEqual({OPEN:["2","1"],IN_PROGRESS:["3"],CLOSED:[]});
  expect(dragEnd(start,"1",null)).toBe(start);
  expect(dragEnd(start,"1","CLOSED")).toBe(start);
  expect(dragEnd(start,"1","OPEN")).toBe(start);
});

test("alternatywa bez przeciągania zmienia kolejność i ogłasza pozycję",async()=>{
  const user=userEvent.setup();
  render(<SortableQueue initial={tickets}/>);
  expect(screen.getByRole("button",{name:"W górę: Awaria"})).toBeDisabled();
  expect(screen.getByRole("button",{name:"W dół: Cennik"})).toBeDisabled();
  await user.click(screen.getByRole("button",{name:"W dół: Awaria"}));
  expect(titles()).toEqual(["Błąd","Awaria","Cennik"]);
  expect(within(screen.getByText(/pozycja/).parentElement!).getByText("Awaria: pozycja 2 z 3")).toBeVisible();
});

test("uchwyt przyjmuje fokus i ma polską instrukcję",async()=>{
  render(<SortableQueue initial={tickets}/>);
  const handle=screen.getByRole("button",{name:"Przenieś: Awaria"});
  await userEvent.setup().tab();
  expect(handle).toHaveFocus();
  expect(handle).toHaveAttribute("aria-roledescription","sortable");
  expect(document.getElementById(handle.getAttribute("aria-describedby")!)).toHaveTextContent("naciśnij spację");
});

// KeyboardSensor podłącza nasłuch klawiszy w kolejnym zadaniu, więc Escape wysyłamy po jednym obrocie pętli.
const tick=()=>act(async()=>{await new Promise(done=>setTimeout(done,0));});

test("spacja podnosi kartę klawiaturą, Escape anuluje bez zmiany kolejności",async()=>{
  render(<SortableQueue initial={tickets}/>);
  const handle=screen.getByRole("button",{name:"Przenieś: Awaria"});
  handle.focus();
  fireEvent.keyDown(handle,{code:"Space"});
  expect(screen.getByTestId("overlay")).toHaveTextContent("Awaria");
  await tick();
  fireEvent.keyDown(document,{code:"Escape"});
  await tick();
  expect(screen.queryByTestId("overlay")).toBeNull();
  expect(titles()).toEqual(["Awaria","Błąd","Cennik"]);
});

test("komunikaty używają tytułów, nie identyfikatorów",()=>{
  const messages=createAnnouncements(id=>id==="a"?"Awaria":"Błąd");
  const active={id:"a"} as never,over={id:"b"} as never;
  expect(messages.onDragStart({active})).toBe("Podniesiono „Awaria”.");
  expect(messages.onDragEnd({active,over})).toContain("na miejsce „Błąd”");
  expect(messages.onDragEnd({active,over:null})).toContain("Kolejność bez zmian");
});

test("tablica ma kolumny z licznikami, także pustą",()=>{
  render(<MiniBoard initial={board()}/>);
  expect(screen.getByRole("region",{name:"Otwarte"})).toHaveTextContent("Otwarte (2)");
  expect(screen.getByRole("region",{name:"Zamknięte"})).toHaveTextContent("Zamknięte (0)");
});`;
const exercises=[
['Uzasadnij wybór dnd-kit zamiast natywnego HTML5 DnD.','Dotyk, klawiatura, podgląd.','Natywny Drag and Drop nie działa na ekranach dotykowych, nie obsługuje klawiatury, a podgląd trudno ostylować. dnd-kit daje czujniki dla wskaźnika i klawiatury, komunikaty dla czytników i DragOverlay.'],
['Napisz czystą funkcję reorder.','Biblioteka nie zmienia danych: robisz to sam w onDragEnd.',model],
['Obsłuż upuszczenie poza listą.','over może być null.',model],
['Dodaj przenoszenie o jedną pozycję dla przycisków.','Kraniec listy nie zmienia kolejności.',model],
['Zbuduj SortableQueue z DndContext i czujnikami.','activationConstraint oddziela kliknięcie od przeciągania.',queue],
['Dodaj useSortable i uchwyt przenoszenia.','setActivatorNodeRef wskazuje element, który dostanie fokus po zakończeniu.',queue],
['Zastosuj transform i transition z useSortable.','CSS.Transform.toString zamienia wartość na tekst.',queue],
['Dodaj DragOverlay z podglądem karty.','Nie jest przycinany przez overflow kolumny.',queue],
['Dodaj przyciski W górę i W dół jako alternatywę.','WCAG 2.5.7 wymaga alternatywy dla przeciągania.',queue],
['Ogłoś nową pozycję po użyciu przycisku.','Region status jest stale w DOM.',queue],
['Zablokuj W górę na pierwszej i W dół na ostatniej pozycji.','disabled wynika z indeksu.',queue],
['Napisz polskie komunikaty dnd-kit.','Domyślne są po angielsku i z technicznymi ID.',announcements],
['Dodaj polską instrukcję klawiatury.','screenReaderInstructions podpina się do aria-describedby uchwytu.',announcements],
['Przetestuj podniesienie spacją.','KeyboardSensor reaguje na kod klawisza.',tests],
['Przetestuj anulowanie Escape.','Kolejność ma zostać bez zmian.',tests],
['Rozpoznaj kolumnę po id karty lub kolumny.','Cel może być kartą albo samą kolumną.',model],
['Napisz dragOver przenoszący między kolumnami.','Zmiana działa na żywo, zanim upuścisz.',model],
['Napisz dragEnd ustalający pozycję w kolumnie.','Nie przenoś tutaj między kolumnami drugi raz.',model],
['Zbuduj kolumnę jako obszar useDroppable.','Pusta kolumna nie ma kart, nad które można najechać.',column],
['Złóż tablicę z closestCorners.','Lepiej sprawdza się w wielu kolumnach niż closestCenter.',board],
['Przywróć planszę po anulowaniu.','Zapamiętaj stan z chwili podniesienia.',board],
['Wyjaśnij, czemu zapisujesz do API w onDragEnd.','Co by się stało w onDragOver?','onDragOver wywołuje się przy każdym wejściu nad inny element, więc każde przesunięcie wysłałoby żądanie. Zapisuj raz w onDragEnd, a w onDragCancel przywróć stan sprzed podniesienia, bo zmiany z podglądu są już w stanie.'],
['Rozwiąż konflikt identyfikatorów kart i kolumn.','5 i "5" to pułapka.','Trzymaj jeden rodzaj id albo nadawaj prefiksy: ticket-5, column-OPEN. W tej lekcji karty mają inne wartości niż unia statusów. dnd-kit przekazuje UniqueIdentifier, czyli string lub number, więc porównanie 5 z "5" po cichu zwróci false.'],
['Dobierz activationConstraint na telefon.','Lista musi się dalej przewijać.','Na dotyku użyj TouchSensor z activationConstraint:{delay:200,tolerance:8}, a uchwyt ustaw z touch-action:none. Karta bez uchwytu zablokowałaby przewijanie palcem. Dystans 5 px wystarcza dla myszy, aby zwykłe kliknięcie nie zaczynało przeciągania.'],
['Zaplanuj klawiaturę między kolumnami.','Co przeskakuje strzałka?','sortableKeyboardCoordinates porusza po elementach sortowanych, a strzałki w bok przechodzą do sąsiedniej kolumny, gdy są tam obszary. Sprawdź to w przeglądarce: test w jsdom nie liczy układu, więc nie potwierdzi kierunku ruchu.'],
['Wyjaśnij ograniczenia testu w jsdom.','Co widzi test, a czego nie?','Elementy mają zerowe prostokąty, więc kolizje nie działają jak w przeglądarce. Dlatego testuję czyste funkcje, role, instrukcje, podniesienie i anulowanie. Prawdziwe przeciąganie sprawdza Playwright z page.mouse lub ręczne przejście, także na telefonie.'],
['Napisz scenariusz Playwright przeciągania.','Ruch potrzebuje pośrednich kroków.','// e2e/queue.spec.ts\nawait handle.hover();\nawait page.mouse.down();\nawait page.mouse.move(x,y,{steps:12});\nawait page.mouse.up();\nawait expect(items).toHaveText(["Błąd","Awaria","Cennik"]);\n// Bez kroków nie zostanie przekroczony próg aktywacji.'],
['Dostosuj styl podniesionej karty do ograniczonego ruchu.','Ruch ma pozostać zrozumiały.','Przy prefers-reduced-motion wyłącz animację upuszczenia: <DragOverlay dropAnimation={reduced?null:undefined}> i rezygnuj ze skalowania. Karta nadal może mieć cień i obramowanie, aby było widać, co jest podniesione.'],
['Znajdź błędy w tej implementacji.','Sprawdź dane, over i klucze.','1. Mutowanie tablicy zamiast arrayMove lub kopii. 2. Brak sprawdzenia over===null. 3. key równy indeksowi. 4. Karta jako uchwyt bez alternatywy. 5. Brak onDragCancel. 6. Identyfikatory techniczne w komunikatach.'],
['Sprawdź kryterium akceptacji lekcji.','Mysz, dotyk, klawiatura, czytnik i alternatywa.','Lista i tablica działają myszą, dotykiem i klawiaturą. Zwykłe kliknięcie nie zaczyna przeciągania. Komunikaty są po polsku i z tytułami. Istnieje alternatywa bez gestu. Escape przywraca stan. Testy czystych funkcji przechodzą.'],
];
const concepts=[
['Dlaczego natywne HTML5 DnD nie wystarcza?','Nie działa na dotyku ani z klawiaturą','Nie istnieje w Chrome','Wymaga serwera','Działa tylko w SVG','Podgląd też trudno ostylować.'],
['Co robi DndContext?','Wyznacza obszar działania przeciągania','Zapisuje dane','Renderuje listę','Tworzy historię','Przyjmuje czujniki, kolizje i zdarzenia.'],
['Czym są czujniki?','Źródłami gestu: wskaźnik lub klawiatura','Rolami ARIA','Sensorami GPS','Testami','Decydują, jak zaczyna się przeciąganie.'],
['Po co activationConstraint distance?','Kliknięcie nie zaczyna przeciągania','Blokuje klawiaturę','Przyspiesza render','Ogranicza listę','Potrzebne jest przesunięcie o kilka pikseli.'],
['Jak klawiatura podnosi element w KeyboardSensor?','Spacja lub Enter','Tab','Strzałka','Shift','Strzałki przesuwają, a Escape anuluje.'],
['Co robi Escape w trakcie przeciągania?','Anuluje','Upuszcza','Przenosi do końca','Zamyka kartę','Wywołuje onDragCancel.'],
['Co zwraca useSortable?','setNodeRef, attributes, listeners, transform i transition','Tablicę danych','Nową listę','Tylko ref','To kompletny zestaw dla elementu.'],
['Do czego służy setActivatorNodeRef?','Wskazuje uchwyt, który dostaje fokus po zakończeniu','Tworzy węzeł DOM','Zmienia rolę','Rejestruje kolumnę','Ważne dla klawiatury przy osobnym uchwycie.'],
['Kto zmienia dane po upuszczeniu?','Twój kod w onDragEnd','dnd-kit sam','React sam','MSW','Biblioteka podaje tylko active i over.'],
['Co zawiera event.over?','Cel lub null','Zawsze element','Tablicę danych','Nazwę akcji','Upuszczenie poza obszarem daje null.'],
['Co robi arrayMove?','Zwraca nową tablicę z przeniesionym elementem','Mutuje wejście','Kasuje element','Sortuje rosnąco','Niemutowalnie zmienia kolejność.'],
['Co robi SortableContext?','Łączy listę identyfikatorów z elementami sortowanymi','Renderuje tabelę','Zapisuje kolejność','Pobiera dane','Przyjmuje items i strategy.'],
['Co musi być w items?','Identyfikatory w aktualnej kolejności','Elementy DOM','Klucze CSS','Dowolne wartości','Kolejność decyduje o przesuwaniu sąsiadów.'],
['Co to jest strategy w SortableContext?','Sposób przesuwania sąsiadów, np. pionowy','Algorytm kolizji','Plan wdrożenia','Tryb Reacta','verticalListSortingStrategy dla listy.'],
['Do czego służy useDroppable?','Do obszaru upuszczenia','Do przeciąganego elementu','Do listy tras','Do cache','Pusta kolumna musi być obszarem.'],
['Czemu pusta kolumna wymaga useDroppable?','Nie ma kart, nad które można najechać','Bo ma klasę','Bo tak działa CSS','Bo inaczej nie ma nagłówka','Cel to wtedy sama kolumna.'],
['Co daje DragOverlay?','Podgląd ponad stroną','Zapis danych','Tło modala','Kontekst','Nie jest przycinany przez overflow.'],
['Który algorytm kolizji jest polecany dla wielu kolumn?','closestCorners','closestCenter zawsze','rectIntersection zawsze','Żaden','Lepiej odzwierciedla układ kolumn.'],
['Kiedy używasz onDragOver?','Przy przenoszeniu między listami na żywo','Przy zwykłej pojedynczej liście zawsze','Do zapisu w API','Do ładowania danych','Nowa kolumna robi miejsce od razu.'],
['Gdzie zapisać zmianę w API?','W onDragEnd','W onDragOver','W onDragStart','W render','Inaczej powstanie seria żądań.'],
['Co musi zrobić onDragCancel?','Przywrócić stan sprzed podniesienia','Nic','Zapisać stan','Wyczyścić całą planszę','Zmiany z podglądu są już w stanie.'],
['Jak reprezentować planszę?','Obiekt kolumna do tablicy identyfikatorów','Pola status w wielu miejscach','Mapa DOM','Tekst w URL','Kolejność karty to kolejność id.'],
['Skąd brać status karty?','Z kolumny','Z osobnego pola w karcie obok kolumny','Z koloru','Z testu','Dwa źródła się rozjadą.'],
['Jaki problem ma mieszanie 5 i "5"?','Porównanie po cichu zwraca false','Błąd kompilacji zawsze','Wolniejszy render','Brak klucza','Używaj jednego rodzaju lub prefiksów.'],
['Jaki typ ma active.id?','UniqueIdentifier','string zawsze','number zawsze','Element','Jest to string lub number.'],
['Co wymusi TSX przy over?','Sprawdzenie null','Rzutowanie','Brak over','Nowy kontekst','W JSX dostajesz TypeError po upuszczeniu obok.'],
['Co opisuje kryterium WCAG 2.5.7?','Alternatywa dla przeciągania','Kontrast','Język','Tytuł strony','Akcja nie może wymagać samego przeciągania.'],
['Czy obsługa klawiatury w dnd-kit zwalnia z przycisków?','Nie, część osób potrzebuje prostszej drogi','Tak','Tak, w TSX','Tak, na telefonie','Drżenie rąk, przełączniki i głos to częsty przypadek.'],
['Jak zrobić alternatywę bez gestu?','Menu „Przenieś do…” lub przyciski ↑ ↓','Dłuższy timeout','Podwójne kliknięcie','Tylko instrukcja','Ma działać bez precyzyjnego ruchu.'],
['Skąd biorą się domyślne komunikaty dnd-kit?','Z biblioteki, po angielsku i z ID','Z WCAG','Z przeglądarki','Z React','Podmień je przez accessibility.'],
['Gdzie podajesz własne komunikaty?','accessibility.announcements w DndContext','aria-live na każdej karcie','useSortable','SortableContext','Biblioteka ma własny region na żywo.'],
['Czego używa uchwyt dla roli?','role i aria-roledescription z attributes','Tylko title','Tylko class','Tylko data-id','attributes uzupełnia semantykę.'],
['Czemu tytuł zamiast id w komunikacie?','Czytnik powinien mówić o treści','Bo id jest tajne','Bo id ma cyfry','Bo dnd-kit tak wymaga','„Podniesiono ticket 41” nic nie mówi.'],
['Dlaczego uchwyt, a nie cała karta?','Treść i linki karty działają normalnie, a na telefonie lista się przewija','Bo karta nie ma listeners','Bo tak szybciej','Bo CSS','Osobny uchwyt rozdziela gest od reszty.'],
['Jak przewijać listę na telefonie?','touch-action tylko na uchwycie i TouchSensor z opóźnieniem','touch-action:none na całej stronie','Wyłączyć przewijanie','Tylko CSS grid','Inaczej przeciąganie blokuje przewijanie.'],
['Co oznacza opóźnienie w TouchSensor?','Dotyk musi chwilę trwać, zanim zacznie się przeciąganie','Animację','Czas żądania','Czas testu','tolerance ogranicza przypadkowe anulowanie.'],
['Czego nie zrobi jsdom w teście dnd?','Nie policzy prostokątów ani kolizji','Nie uruchomi Reacta','Nie znajdzie roli','Nie wyśle zdarzeń','Prawdziwe przeciąganie sprawdza przeglądarka.'],
['Jak test może sprawdzić logikę bez układu?','Testując czyste funkcje','Tylko migawką','Nie może','Mockując React','To główny powód wydzielenia funkcji.'],
['Czemu w Playwright potrzebne są kroki przy mouse.move?','Aby przekroczyć próg i wywołać zdarzenia pośrednie','Dla kolorów','Dla ARIA','Dla sieci','Jeden skok może nie zadziałać.'],
['Czy nowa tablica z reorder musi być zwracana przy braku zmiany?','Lepiej zwrócić tę samą referencję','Musi być nowa','Trzeba zwrócić null','Trzeba rzucić wyjątek','React pominie zbędną aktualizację.'],
['Co pokazuje isDragging?','Element jest w trakcie przeciągania','Karta jest zamknięta','Lista się ładuje','Element ma błąd','Przydatne do osłabienia oryginału.'],
['Czemu oryginał ma opacity 0.4 przy overlay?','Pokazuje miejsce, a overlay pokazuje ruch','Bo inaczej znika z DOM','Bo CSS tego wymaga','Bo dnd-kit nie działa inaczej','To czytelny wzorzec wizualny.'],
['Co robi dropAnimation={null}?','Wyłącza animację powrotu overlay','Blokuje upuszczenie','Usuwa kolumnę','Włącza spring','Przydatne przy ograniczonym ruchu.'],
['Dlaczego nie dawać layout z Motion na element sortowany?','Obie biblioteki sterują transform','Bo nie ma key','Bo Motion nie działa w li','Bo tak wolniej renderuje fetch','Karty zaczną drgać.'],
['Co jest prawdą o kolejności w DOM po przeciągnięciu?','Zmienia się po aktualizacji stanu','Zmienia się podczas przesuwania zawsze','Nie zmienia się nigdy','Wymaga reload','Transform tylko przesuwa wygląd w trakcie.'],
['Dlaczego kolejność Tab się zmieni po upuszczeniu?','Bo zmienia się DOM','Bo tabindex rośnie','Bo CSS order','Bo overlay','To pożądane, bo zgadza się z wizualną.'],
['Czego unikać przy CSS order na liście?','Rozjazdu między kolejnością wizualną a DOM','Zmiany koloru','Flexboxa','Kluczy','Fokus i czytnik idą według DOM.'],
['Jakie informacje ogłaszać po przesunięciu?','Tytuł i pozycję','Współrzędne','Nazwę klasy','Tylko „OK”','Użytkownik musi wiedzieć, gdzie jest karta.'],
['Jak sprawdzić, że Escape przywraca stan w tablicy?','Zapamiętać planszę w onDragStart i porównać','Odświeżyć stronę','Tylko test CSS','Nie da się','Bez tego karta zostaje w ostatniej kolumnie.'],
['Skąd pochodzi pozycja zapisywana w API?','Z pełnej listy, a nie z widoku przefiltrowanego','Z indeksu w filtrze','Ze współrzędnych','Z klasy','Inaczej karta wyląduje w złym miejscu.'],
['Czym różni się arrayMove od toSpliced?','arrayMove przenosi w jednej liście, toSpliced wstawia element','Niczym','toSpliced mutuje','arrayMove usuwa elementy','Przy przenoszeniu między listami przydaje się filter i toSpliced.'],
['Jak uniknąć ponownego utworzenia czujników?','useSensors zapamiętuje konfigurację','Tworzyć w useEffect','Nie używać','Wynieść do JSON','To API biblioteki.'],
['Co oznacza sortableKeyboardCoordinates?','Getter pozycji dla ruchu klawiaturą po liście','Skrót klawiszowy','Rola ARIA','Algorytm sortowania','Bez niego krok to stała odległość.'],
['Czy dnd-kit wymaga własnego CSS kolumn?','Tak, biblioteka nie dostarcza stylów','Nie, ma gotowe','Tylko w trybie ciemnym','Tylko dla kart','Wygląd należy do Ciebie.'],
['Jak zamknąć gest na telefonie przez tekst?','Instrukcja i alternatywa widoczne w interfejsie','Tylko dźwięk','Tylko tooltip','Brak','Gest nie jest oczywisty dla każdego.'],
['Co testuje model dragOver przy celu w tej samej kolumnie?','Brak zmiany: zwraca tę samą referencję','Nową planszę','Wyjątek','Pustą planszę','Pozycję ustali dopiero dragEnd.'],
['Co oznacza „nad kolumną” w dragOver?','Dodanie na koniec','Dodanie na początek','Brak zmiany','Usunięcie','Indeks to długość listy docelowej.'],
['Co sprawdzić po dodaniu Kanbana?','Klawiaturę, dotyk, czytnik i wycofanie','Tylko wygląd','Tylko typy','Tylko Lighthouse','Największe ryzyko to brak obsługi poza myszą.'],
['Co jest błędem w key={index} na sortowanej liście?','Klucz zmienia się razem z pozycją','Nic','Spowalnia CSS','Blokuje fokus','Stan elementu pomyli się z pozycją.'],
['Jak rozpoznać kolumnę docelową?','Po id kolumny lub po karcie w niej','Po współrzędnych ręcznie','Po tytule strony','Po klasie','findColumn obsługuje oba przypadki.'],
];
const header=String.raw`import {dragEnd,dragOver,findColumn,isColumn,reorder,shift,type Board} from "./boardModel";
import {createAnnouncements,screenReaderInstructions} from "./dndAnnouncements";
import {observeQueue} from "./dndLab";
const board=():Board=>({OPEN:["1","2"],IN_PROGRESS:["3"],CLOSED:[]});
const msg=createAnnouncements(id=>"T"+id);`;
const probeRows=[
['Wynik przeniesienia a na c','reorder(["a","b","c"],"a","c").join()','b,c,a','Pozostałe elementy się zsuwają.',['c,b,a','a,c,b','a,b,c']],
['Wynik przeniesienia c na a','reorder(["a","b","c"],"c","a").join()','c,a,b','Element trafia na miejsce celu.',['a,c,b','b,c,a','a,b,c']],
['Długość po przeniesieniu','reorder(["a","b"],"a","b").length',2,'Nic się nie gubi.'],
['Czy brak celu zwraca to samo?','(()=>{const a=["x"];return reorder(a,"x",null)===a;})()',true,'Upuszczenie poza listą.'],
['Czy ten sam cel zwraca to samo?','(()=>{const a=["x"];return reorder(a,"x","x")===a;})()',true,'Bez zmiany stanu.'],
['Czy nieznany element zwraca to samo?','(()=>{const a=["x"];return reorder(a,"y","x")===a;})()',true,'Brak indeksu.'],
['Przenieś b w górę','shift(["a","b"],"b",-1).join()','b,a','Delta -1.',['a,b','b','a']],
['Przenieś a w dół','shift(["a","b"],"a",1).join()','b,a','Delta 1.',['a,b','b','a']],
['Przenieś a w górę z początku','shift(["a","b"],"a",-1).join()','a,b','Kraniec.',['b,a','a','b']],
['Przenieś b w dół z końca','shift(["a","b"],"b",1).join()','a,b','Kraniec.',['b,a','a','b']],
['Czy OPEN jest kolumną?','isColumn("OPEN")',true,'Unia statusów.'],
['Czy "3" jest kolumną?','isColumn("3")',false,'To karta.'],
['Kolumna karty 2','findColumn(board(),"2")','OPEN','Szukanie po listach.',['IN_PROGRESS','CLOSED','undefined']],
['Kolumna dla celu CLOSED','findColumn(board(),"CLOSED")','CLOSED','Sama kolumna jest celem.',['OPEN','IN_PROGRESS','undefined']],
['Kolumna nieznanej karty','String(findColumn(board(),"9"))','undefined','Brak dopasowania.',['OPEN','null','CLOSED']],
['dragOver na inną kartę: miejsce','dragOver(board(),"1","3").IN_PROGRESS.join()','1,3','Wstawienie w miejscu karty.',['3,1','3','1']],
['dragOver na kolumnę: miejsce','dragOver(board(),"1","IN_PROGRESS").IN_PROGRESS.join()','3,1','Na koniec kolumny.',['1,3','3','1']],
['dragOver do pustej kolumny','dragOver(board(),"1","CLOSED").CLOSED.join()','1','Pusta kolumna jako cel.',['pusty tekst','1,2','3']],
['dragOver usuwa ze źródła','dragOver(board(),"1","CLOSED").OPEN.join()','2','Karta nie może być dwa razy.',['1,2','1','pusty tekst']],
['dragOver w tej samej kolumnie zwraca to samo?','(()=>{const b=board();return dragOver(b,"1","2")===b;})()',true,'Pozycję ustala dragEnd.'],
['dragOver bez celu zwraca to samo?','(()=>{const b=board();return dragOver(b,"1",null)===b;})()',true,'over może być null.'],
['Czy dragOver mutuje wejście?','(()=>{const b=board();dragOver(b,"1","CLOSED");return b.OPEN.length;})()',2,'Stan jest niemutowalny.'],
['dragEnd w kolumnie','dragEnd(board(),"1","2").OPEN.join()','2,1','Ostateczna pozycja.',['1,2','1','2']],
['dragEnd na kolumnę zwraca to samo?','(()=>{const b=board();return dragEnd(b,"1","OPEN")===b;})()',true,'Cel nie jest kartą.'],
['dragEnd na kartę w innej kolumnie zwraca to samo?','(()=>{const b=board();return dragEnd(b,"1","3")===b;})()',true,'Zmianę kolumny zrobił dragOver.'],
['Komunikat podniesienia','msg.onDragStart({active:{id:"a"} as never})','Podniesiono „Ta”.','Tytuł zamiast id.',['Podniesiono a','Picked up a','Upuszczono „Ta”.']],
['Komunikat poza listą','msg.onDragOver({active:{id:"a"} as never,over:null})','„Ta” poza listą.','over równe null.',['„Ta” nad „null”.','pusty tekst','Upuszczono „Ta”.']],
['Komunikat anulowania','msg.onDragCancel({active:{id:"a"} as never,over:null})','Anulowano przenoszenie „Ta”.','Escape jest ogłaszany.',['Upuszczono „Ta”.','Podniesiono „Ta”.','pusty tekst']],
['Czy instrukcja wspomina Escape?','screenReaderInstructions.draggable.includes("Escape")',true,'Użytkownik musi znać wyjście.'],
['Początkowa kolejność kolejki','observeQueue().order','Awaria,Błąd,Cennik','Z props.',['Cennik,Błąd,Awaria','Błąd,Awaria,Cennik','brak']],
['Kolejność po W dół: Awaria','observeQueue(["W dół: Awaria"]).order','Błąd,Awaria,Cennik','Przycisk zmienia stan.',['Awaria,Błąd,Cennik','Awaria,Cennik,Błąd','brak']],
['Kolejność po W górę: Cennik','observeQueue(["W górę: Cennik"]).order','Awaria,Cennik,Błąd','Delta -1.',['Awaria,Błąd,Cennik','Cennik,Awaria,Błąd','brak']],
['Ogłoszenie po przesunięciu','observeQueue(["W dół: Awaria"]).status','Awaria: pozycja 2 z 3','Pozycja jest liczona od 1.',['Awaria: pozycja 1 z 3','brak','Awaria']],
['Status na początku','observeQueue().status','brak','Region jest pusty.',['Awaria: pozycja 1 z 3','Awaria','Kolejka']],
['Czy pierwsze W górę jest zablokowane?','observeQueue().upFirstDisabled',true,'Kraniec listy.'],
['Czy ostatnie W dół jest zablokowane?','observeQueue().downLastDisabled',true,'Kraniec listy.'],
['Jaki opis roli ma uchwyt?','observeQueue().handleRole','sortable','attributes z useSortable.',['draggable','button','brak']],
['Dwa przesunięcia w dół','observeQueue(["W dół: Awaria","W dół: Awaria"]).order','Błąd,Cennik,Awaria','Awaria na końcu.',['Awaria,Błąd,Cennik','Błąd,Awaria,Cennik','brak']],
['Ogłoszenie po dwóch przesunięciach','observeQueue(["W dół: Awaria","W dół: Awaria"]).status','Awaria: pozycja 3 z 3','Region ma ostatnią wartość.',['Awaria: pozycja 2 z 3','brak','Awaria']],
['Czy dragEnd zachowuje inne kolumny?','dragEnd(board(),"1","2").IN_PROGRESS.join()','3','Zmienia tylko jedną kolumnę.',['1,2','pusty tekst','2,1']],
];
const project={
'src/boardModel.ts':model,'src/dndAnnouncements.ts':announcements,'src/SortableQueue.tsx':queue,'src/Column.tsx':column,'src/MiniBoard.tsx':board,'src/dndLab.tsx':lab,'src/dnd.test.tsx':tests,
'README.md':'DevDesk: przeciąganie. npm install, npm test. jsdom ma zerowe prostokąty, więc testy obejmują czyste funkcje, role, instrukcje, podniesienie i anulowanie klawiaturą. Rzeczywiste przeciąganie myszą i palcem, ruch strzałkami oraz odczyt komunikatów przez czytnik sprawdź w przeglądarce, ręcznie lub w Playwright. Same teksty komunikatów sprawdza test funkcji createAnnouncements.\n',
};
const mutants=[
{file:'src/boardModel.ts',before:'if(!overId||activeId===overId)return items;',after:'if(!overId)return items;'},
{file:'src/boardModel.ts',before:'if(from<0||to<0||to>=items.length)return items;',after:''},
{file:'src/boardModel.ts',before:'overIndex=isColumn(overId)?board[to].length:board[to].indexOf(overId)',after:'overIndex=board[to].indexOf(overId)'},
{file:'src/boardModel.ts',before:'[from]:board[from].filter(id=>id!==activeId),',after:''},
{file:'src/boardModel.ts',before:'||isColumn(overId))return board;',after:')return board;'},
{file:'src/SortableQueue.tsx',before:' disabled={first}',after:''},
{file:'src/SortableQueue.tsx',before:'next.indexOf(id)+1',after:'next.indexOf(id)'},
{file:'src/SortableQueue.tsx',before:'accessibility={{announcements:createAnnouncements(titleOf),screenReaderInstructions}}',after:''},
{file:'src/dndAnnouncements.ts',before:'"Podniesiono „"+titleOf(active.id)+"”."',after:'"Podniesiono „"+active.id+"”."'},
];
module.exports=lesson({exercises,concepts,probeRows,project,mutants,header});
