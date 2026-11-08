const {lesson}=require('./practice_kit.cjs');
const model=String.raw`export type TicketStatus="OPEN"|"IN_PROGRESS"|"CLOSED";
export type Board=Record<TicketStatus,number[]>;
export type Ticket={id:number;title:string;priority:"LOW"|"HIGH";assignee:string|null};

export const COLUMNS:readonly TicketStatus[]=["OPEN","IN_PROGRESS","CLOSED"];
export const WIP_LIMITS:Partial<Record<TicketStatus,number>>={IN_PROGRESS:5};
export const COLUMN_TITLE:Record<TicketStatus,string>={OPEN:"Otwarte",IN_PROGRESS:"W toku",CLOSED:"Zamknięte"};

export function findColumn(board:Board,ticketId:number):TicketStatus|undefined{
  return COLUMNS.find(column=>board[column].includes(ticketId));
}

export function canDrop(board:Board,ticketId:number,toColumn:TicketStatus):boolean{
  const limit=WIP_LIMITS[toColumn];
  return findColumn(board,ticketId)===toColumn||limit===undefined||board[toColumn].length<limit;
}

// Odrzucenie zwraca ten sam obiekt: po referencji poznajesz, że nic się nie stało.
export function moveTicket(board:Board,ticketId:number,toColumn:TicketStatus,toIndex:number):Board{
  const fromColumn=findColumn(board,ticketId);
  if(!fromColumn||!canDrop(board,ticketId,toColumn))return board;
  const without=board[fromColumn].filter(id=>id!==ticketId);
  const target=fromColumn===toColumn?without:board[toColumn];
  const index=Math.max(0,Math.min(toIndex,target.length));
  return {...board,[fromColumn]:without,[toColumn]:target.toSpliced(index,0,ticketId)};
}

// Pozycja w pełnej kolumnie, gdy użytkownik widzi tylko część kart.
export function fullIndex(column:readonly number[],visible:readonly number[],visibleIndex:number){
  if(visibleIndex>=visible.length){
    const last=visible[visible.length-1];
    return last===undefined?column.length:column.indexOf(last)+1;
  }
  return column.indexOf(visible[visibleIndex]);
}

export function visibleIds(ids:readonly number[],tickets:Record<number,Ticket>,onlyMine:string|null){
  return onlyMine===null?[...ids]:ids.filter(id=>tickets[id]?.assignee===onlyMine);
}`;
const hook=String.raw`import {useMutation,useQuery,useQueryClient} from "@tanstack/react-query";
import {moveTicket,type Board,type TicketStatus} from "./board";

export type Move={ticketId:number;status:TicketStatus;position:number};

const KEY=["board"];

export async function fetchBoard():Promise<Board>{
  const response=await fetch(new URL("/api/board",window.location.href));
  if(!response.ok)throw Error("Serwer zwrócił "+response.status);
  return response.json();
}

export async function saveMove({ticketId,status,position}:Move){
  const response=await fetch(new URL("/api/tickets/"+ticketId,window.location.href),{
    method:"PATCH",
    headers:{"Content-Type":"application/json"},
    body:JSON.stringify({status,position}),
  });
  if(!response.ok)throw Error("Serwer zwrócił "+response.status);
}

export function useBoard(onFailure:(message:string)=>void){
  const client=useQueryClient();
  const query=useQuery({queryKey:KEY,queryFn:fetchBoard});
  const mutation=useMutation({
    mutationFn:saveMove,
    onMutate:async(move:Move)=>{
      await client.cancelQueries({queryKey:KEY});
      const previous=client.getQueryData<Board>(KEY);
      if(previous)client.setQueryData<Board>(KEY,moveTicket(previous,move.ticketId,move.status,move.position));
      return {previous};
    },
    onError:(_error,_move,context)=>{
      if(context?.previous)client.setQueryData(KEY,context.previous);
      onFailure("Nie udało się przenieść ticketu");
    },
    onSettled:()=>client.invalidateQueries({queryKey:KEY}),
  });
  return {board:query.data,isPending:query.isPending,move:mutation.mutate};
}`;
const card=String.raw`import {useId,useState} from "react";
import {Link} from "react-router";
import {motion} from "motion/react";
import {useSortable} from "@dnd-kit/sortable";
import {CSS} from "@dnd-kit/utilities";
import {canDrop,COLUMN_TITLE,COLUMNS,findColumn,type Board,type Ticket,type TicketStatus} from "./board";

type Props={ticket:Ticket;board:Board;onMoveTo:(id:number,column:TicketStatus)=>void};

export function CardBody({ticket}:{ticket:Ticket}){
  return (
    <>
      <Link to={"/tickets/"+ticket.id}>{ticket.title}</Link>
      <span>{ticket.priority==="HIGH"?"Wysoki":"Niski"}</span>
      <span>{ticket.assignee??"Nieprzypisany"}</span>
    </>
  );
}

export function TicketCard({ticket,board,onMoveTo}:Props){
  const {attributes,listeners,setNodeRef,setActivatorNodeRef,transform,transition,isDragging}=useSortable({id:ticket.id});
  const [open,setOpen]=useState(false);
  const menuId=useId();
  const current=findColumn(board,ticket.id);
  return (
    <li ref={setNodeRef} style={{transform:CSS.Transform.toString(transform),transition,opacity:isDragging?0.4:1}}>
      <motion.div initial={{opacity:0}} animate={{opacity:1}}>
        <button type="button" ref={setActivatorNodeRef} aria-label={"Przenieś: "+ticket.title} {...attributes} {...listeners}>⠿</button>
        <CardBody ticket={ticket}/>
        <button type="button" aria-expanded={open} aria-controls={menuId} onClick={()=>setOpen(value=>!value)}>Przenieś do…</button>
        <ul id={menuId} hidden={!open}>
          {COLUMNS.filter(column=>column!==current).map(column=>{
            const allowed=canDrop(board,ticket.id,column);
            return (
              <li key={column}>
                <button type="button" disabled={!allowed} onClick={()=>{onMoveTo(ticket.id,column);setOpen(false);}}>
                  {COLUMN_TITLE[column]}{allowed?"":" (limit osiągnięty)"}
                </button>
              </li>
            );
          })}
        </ul>
      </motion.div>
    </li>
  );
}`;
const column=String.raw`import {useDroppable} from "@dnd-kit/core";
import {SortableContext,verticalListSortingStrategy} from "@dnd-kit/sortable";
import {COLUMN_TITLE,WIP_LIMITS,type TicketStatus} from "./board";
import type {ReactNode} from "react";

export function BoardColumn({id,count,ids,children}:{id:TicketStatus;count:number;ids:readonly number[];children:ReactNode}){
  const {setNodeRef,isOver}=useDroppable({id});
  const limit=WIP_LIMITS[id];
  return (
    <section ref={setNodeRef} aria-label={COLUMN_TITLE[id]} className={isOver?"column column--over":"column"}>
      <h2>{COLUMN_TITLE[id]} <span>{limit===undefined?count:count+" / "+limit}</span></h2>
      <SortableContext items={[...ids]} strategy={verticalListSortingStrategy}>
        <ol>{children}</ol>
      </SortableContext>
    </section>
  );
}`;
const boardView=String.raw`import {useRef,useState} from "react";
import {DndContext,DragOverlay,KeyboardSensor,PointerSensor,closestCorners,useSensor,useSensors,type Announcements,type DragEndEvent,type DragOverEvent,type DragStartEvent} from "@dnd-kit/core";
import {sortableKeyboardCoordinates} from "@dnd-kit/sortable";
import {MotionConfig,motion} from "motion/react";
import {canDrop,COLUMN_TITLE,COLUMNS,findColumn,moveTicket,fullIndex,visibleIds,type Board,type Ticket,type TicketStatus} from "./board";
import {BoardColumn} from "./BoardColumn";
import {CardBody,TicketCard} from "./TicketCard";
import {useBoard} from "./useBoard";

const isColumn=(id:unknown):id is TicketStatus=>COLUMNS.includes(id as TicketStatus);

export function KanbanBoard({tickets,me}:{tickets:Record<number,Ticket>;me:string}){
  const [notice,setNotice]=useState("");
  const {board,isPending,move}=useBoard(setNotice);
  const [preview,setPreview]=useState<Board|null>(null);
  const [activeId,setActiveId]=useState<number|null>(null);
  const [onlyMine,setOnlyMine]=useState(false);
  const start=useRef<Board|null>(null);
  const sensors=useSensors(
    useSensor(PointerSensor,{activationConstraint:{distance:5}}),
    useSensor(KeyboardSensor,{coordinateGetter:sortableKeyboardCoordinates}),
  );
  if(isPending||!board)return <p role="status">Ładowanie tablicy…</p>;
  const shown=preview??board;
  const owner=onlyMine?me:null;

  function target(overId:unknown,from:Board):{column:TicketStatus;index:number}|null{
    if(isColumn(overId))return {column:overId,index:from[overId].length};
    const column=findColumn(from,Number(overId));
    return column?{column,index:from[column].indexOf(Number(overId))}:null;
  }

  function onDragStart(event:DragStartEvent){
    start.current=board!;
    setPreview(board!);
    setActiveId(Number(event.active.id));
  }

  function onDragOver(event:DragOverEvent){
    if(!event.over||!preview)return;
    const id=Number(event.active.id),place=target(event.over.id,preview);
    if(!place||findColumn(preview,id)===place.column)return;
    setPreview(moveTicket(preview,id,place.column,place.index));
  }

  function finish(){
    setPreview(null);
    setActiveId(null);
  }

  function onDragEnd(event:DragEndEvent){
    const id=Number(event.active.id),before=start.current!;
    const place=event.over?target(event.over.id,preview??before):null;
    finish();
    if(!place)return;
    if(!canDrop(before,id,place.column)){
      setNotice("Kolumna „"+COLUMN_TITLE[place.column]+"” ma już 5 kart. Limit nie pozwala dodać kolejnej.");
      return;
    }
    if(moveTicket(before,id,place.column,place.index)!==before)move({ticketId:id,status:place.column,position:place.index});
  }

  function moveTo(id:number,column:TicketStatus){
    if(!canDrop(board!,id,column)){
      setNotice("Kolumna „"+COLUMN_TITLE[column]+"” ma już 5 kart. Limit nie pozwala dodać kolejnej.");
      return;
    }
    move({ticketId:id,status:column,position:board![column].length});
    setNotice("Przeniesiono „"+tickets[id].title+"” do kolumny „"+COLUMN_TITLE[column]+"”.");
  }

  const titleOf=(id:string|number)=>isColumn(id)?COLUMN_TITLE[id]:tickets[Number(id)]?.title??String(id);
  const announcements:Announcements={
    onDragStart:({active})=>"Podniesiono „"+titleOf(active.id)+"”.",
    onDragOver:({active,over})=>over?"„"+titleOf(active.id)+"” nad „"+titleOf(over.id)+"”.":"„"+titleOf(active.id)+"” poza tablicą.",
    onDragEnd:({active,over})=>over?"Upuszczono „"+titleOf(active.id)+"” na „"+titleOf(over.id)+"”.":"Upuszczono „"+titleOf(active.id)+"” poza tablicą.",
    onDragCancel:({active})=>"Anulowano przenoszenie „"+titleOf(active.id)+"”.",
  };

  return (
    <MotionConfig reducedMotion="user">
      <label><input type="checkbox" checked={onlyMine} onChange={event=>setOnlyMine(event.currentTarget.checked)}/> Tylko moje</label>
      <DndContext sensors={sensors} collisionDetection={closestCorners} onDragStart={onDragStart} onDragOver={onDragOver} onDragEnd={onDragEnd} onDragCancel={finish} accessibility={{announcements}}>
        {COLUMNS.map(column=>{
          const ids=visibleIds(shown[column],tickets,owner);
          return (
            <BoardColumn key={column} id={column} count={shown[column].length} ids={ids}>
              {ids.map(id=><TicketCard key={id} ticket={tickets[id]} board={shown} onMoveTo={moveTo}/>)}
            </BoardColumn>
          );
        })}
        <DragOverlay dropAnimation={null}>
          {activeId!==null?<motion.div animate={{scale:1.03}}><CardBody ticket={tickets[activeId]}/></motion.div>:null}
        </DragOverlay>
      </DndContext>
      <p role="status">{notice}</p>
    </MotionConfig>
  );
}

export {fullIndex};`;
const lab=String.raw`import type {Board} from "./board";

export const full=():Board=>({OPEN:[1,2],IN_PROGRESS:[10,11,12,13,14],CLOSED:[]});
export const small=():Board=>({OPEN:[1,2,3],IN_PROGRESS:[4],CLOSED:[]});
export const people={1:{id:1,title:"A",priority:"LOW",assignee:"ala"},2:{id:2,title:"B",priority:"HIGH",assignee:"ola"},3:{id:3,title:"C",priority:"LOW",assignee:"ala"}} as const;`;
const tests=String.raw`import {afterAll,afterEach,beforeAll,beforeEach,expect,test} from "vitest";
import {fireEvent,render,screen,waitFor,within} from "@testing-library/react";
import userEvent from "@testing-library/user-event";
import {QueryClient,QueryClientProvider} from "@tanstack/react-query";
import {MemoryRouter} from "react-router";
import {MotionGlobalConfig} from "motion/react";
import {http,HttpResponse} from "msw";
import {setupServer} from "msw/node";
import {canDrop,fullIndex,moveTicket,visibleIds,type Board,type Ticket,type TicketStatus} from "./board";
import {KanbanBoard} from "./KanbanBoard";
import {full,small} from "./boardLab";

MotionGlobalConfig.skipAnimations=true;

const tickets:Record<number,Ticket>={};
for(const id of [1,2,3,4,5,6,10,11,12,13,14])tickets[id]={id,title:"Ticket "+id,priority:id%2?"HIGH":"LOW",assignee:id%2?"ala":"ola"};

let stored:Board;
let patches:unknown[];
const server=setupServer(
  http.get("*/api/board",()=>HttpResponse.json(stored)),
  http.patch("*/api/tickets/:id",async({request,params})=>{
    const body=await request.json() as {status:TicketStatus;position:number};
    patches.push({id:params.id,...body});
    stored=moveTicket(stored,Number(params.id),body.status,body.position);
    return HttpResponse.json({});
  }),
);
beforeAll(()=>server.listen({onUnhandledFrame:"error"}));
beforeEach(()=>{stored=small();patches=[];});
afterEach(()=>server.resetHandlers());
afterAll(()=>server.close());

function show(){
  const client=new QueryClient({defaultOptions:{queries:{retry:false},mutations:{retry:false}}});
  render(<QueryClientProvider client={client}><MemoryRouter><KanbanBoard tickets={tickets} me="ala"/></MemoryRouter></QueryClientProvider>);
  return client;
}
const column=(name:string)=>screen.getByRole("region",{name});
// dnd-kit ma własny region status, więc pytamy o komunikat tablicy.
const notice=()=>document.querySelector("p[role=status]")!;
async function openMenu(user:ReturnType<typeof userEvent.setup>,title:string){
  const card=screen.getByRole("link",{name:title}).closest("li")!;
  await user.click(within(card).getByRole("button",{name:"Przenieś do…"}));
  return card as HTMLElement;
}

test("przeniesienie w kolumnie, między kolumnami i na koniec pustej",()=>{
  const board=small();
  expect(moveTicket(board,1,"OPEN",2).OPEN).toEqual([2,3,1]);
  const between=moveTicket(board,1,"IN_PROGRESS",0);
  expect(between.OPEN).toEqual([2,3]);
  expect(between.IN_PROGRESS).toEqual([1,4]);
  expect(moveTicket(board,1,"CLOSED",99).CLOSED).toEqual([1]);
  expect(board).toEqual(small());
});

test("odrzucenie przez limit zwraca ten sam obiekt",()=>{
  const board=full();
  expect(moveTicket(board,1,"IN_PROGRESS",0)).toBe(board);
  expect(canDrop(board,1,"IN_PROGRESS")).toBe(false);
  expect(canDrop(board,10,"IN_PROGRESS")).toBe(true);
  expect(moveTicket(board,10,"IN_PROGRESS",4).IN_PROGRESS).toEqual([11,12,13,14,10]);
  expect(moveTicket(board,999,"OPEN",0)).toBe(board);
});

test("filtr nie zmienia danych, a pozycja dotyczy pełnej kolumny",()=>{
  const owners={1:{id:1,title:"A",priority:"LOW",assignee:"ala"},2:{id:2,title:"B",priority:"LOW",assignee:"ola"},3:{id:3,title:"C",priority:"LOW",assignee:"ala"}} as Record<number,Ticket>;
  const ids=[1,2,3];
  expect(visibleIds(ids,owners,"ala")).toEqual([1,3]);
  expect(visibleIds(ids,owners,null)).toEqual([1,2,3]);
  expect(ids).toEqual([1,2,3]);
  expect(fullIndex(ids,[1,3],1)).toBe(2);
  expect(fullIndex(ids,[1,3],2)).toBe(3);
  expect(fullIndex(ids,[],0)).toBe(3);
});

test("tablica pokazuje karty, liczniki i link do szczegółów",async()=>{
  show();
  expect(await screen.findByRole("link",{name:"Ticket 1"})).toHaveAttribute("href","/tickets/1");
  expect(column("Otwarte")).toHaveTextContent("Otwarte 3");
  expect(column("W toku")).toHaveTextContent("W toku 1 / 5");
  expect(column("Zamknięte")).toHaveTextContent("Zamknięte 0");
});

test("menu przenosi optymistycznie i zapisuje status oraz pozycję",async()=>{
  const user=userEvent.setup();
  show();
  await screen.findByRole("link",{name:"Ticket 1"});
  const card=await openMenu(user,"Ticket 1");
  await user.click(within(card).getByRole("button",{name:"Zamknięte"}));
  expect(await within(column("Zamknięte")).findByRole("link",{name:"Ticket 1"})).toBeVisible();
  await waitFor(()=>expect(patches).toEqual([{id:"1",status:"CLOSED",position:0}]));
  expect(notice()).toHaveTextContent("Przeniesiono „Ticket 1”");
});

test("karta zmienia kolumnę, zanim serwer odpowie",async()=>{
  let release=()=>{};
  const gate=new Promise<void>(done=>{release=done;});
  server.use(http.patch("*/api/tickets/:id",async()=>{await gate;return HttpResponse.json({});}));
  const user=userEvent.setup();
  show();
  await screen.findByRole("link",{name:"Ticket 1"});
  const card=await openMenu(user,"Ticket 1");
  await user.click(within(card).getByRole("button",{name:"Zamknięte"}));
  expect(await within(column("Zamknięte")).findByRole("link",{name:"Ticket 1"})).toBeVisible();
  release();
});

test("błąd serwera przywraca planszę i pokazuje komunikat",async()=>{
  const user=userEvent.setup();
  show();
  await screen.findByRole("link",{name:"Ticket 1"});
  // Pada też odświeżenie: stan może wrócić wyłącznie dzięki kontekstowi z onMutate.
  server.use(
    http.patch("*/api/tickets/:id",()=>new HttpResponse(null,{status:500})),
    http.get("*/api/board",()=>new HttpResponse(null,{status:500})),
  );
  const card=await openMenu(user,"Ticket 1");
  await user.click(within(card).getByRole("button",{name:"Zamknięte"}));
  await waitFor(()=>expect(notice()).toHaveTextContent("Nie udało się przenieść ticketu"));
  await waitFor(()=>expect(within(column("Otwarte")).getByRole("link",{name:"Ticket 1"})).toBeVisible());
  expect(within(column("Zamknięte")).queryByRole("link")).toBeNull();
});

test("menu wyłącza pełną kolumnę, a pełny licznik to pokazuje",async()=>{
  stored=full();
  const user=userEvent.setup();
  show();
  await screen.findByRole("link",{name:"Ticket 1"});
  const card=await openMenu(user,"Ticket 1");
  expect(within(card).getByRole("button",{name:"W toku (limit osiągnięty)"})).toBeDisabled();
  expect(column("W toku")).toHaveTextContent("5 / 5");
  expect(patches).toEqual([]);
});

test("filtr „Tylko moje” ukrywa karty i przywraca je w tej samej kolejności",async()=>{
  show();
  await screen.findByRole("link",{name:"Ticket 1"});
  const before=within(column("Otwarte")).getAllByRole("link").map(link=>link.textContent);
  fireEvent.click(screen.getByRole("checkbox",{name:"Tylko moje"}));
  expect(within(column("Otwarte")).getAllByRole("link").map(link=>link.textContent)).toEqual(["Ticket 1","Ticket 3"]);
  expect(column("Otwarte")).toHaveTextContent("Otwarte 3");
  fireEvent.click(screen.getByRole("checkbox",{name:"Tylko moje"}));
  expect(within(column("Otwarte")).getAllByRole("link").map(link=>link.textContent)).toEqual(before);
});

test("uchwyt przyjmuje klawiaturę, a Escape nie zapisuje niczego",async()=>{
  show();
  const handle=await screen.findByRole("button",{name:"Przenieś: Ticket 1"});
  handle.focus();
  fireEvent.keyDown(handle,{code:"Space"});
  await new Promise(done=>setTimeout(done,0));
  fireEvent.keyDown(document,{code:"Escape"});
  await new Promise(done=>setTimeout(done,0));
  expect(within(column("Otwarte")).getAllByRole("link").map(link=>link.textContent)).toEqual(["Ticket 1","Ticket 2","Ticket 3"]);
  expect(patches).toEqual([]);
});`;
const exercises=[
['Zdefiniuj model planszy osobno od danych ticketów.','Status wynika z kolumny: nie trzymaj go w dwóch miejscach.',model],
['Napisz czystą funkcję moveTicket.','Bez mutacji, z ograniczeniem indeksu.',model],
['Odrzuć przeniesienie ponad limit WIP.','Zwróć ten sam obiekt planszy.',model],
['Dopuść zmianę kolejności w pełnej kolumnie.','Limit dotyczy nowej karty, a nie tej już obecnej.',model],
['Przetestuj moveTicket dla czterech przypadków.','W kolumnie, między kolumnami, pusta, limit.',tests],
['Wylicz widoczne karty bez usuwania ich z planszy.','Filtr to wyliczenie w renderze.',model],
['Przelicz pozycję z widoku przefiltrowanego.','Do API idzie pozycja w pełnej kolumnie.',model],
['Pobieraj planszę przez useQuery.','Serwer jest źródłem prawdy.',hook],
['Napisz optymistyczny zapis w onMutate.','Zapamiętaj poprzednią planszę i ustaw nową.',hook],
['Przywróć stan w onError i pokaż komunikat.','Kontekst przekazuje poprzednią planszę.',hook],
['Zsynchronizuj się z serwerem w onSettled.','Unieważnienie działa po sukcesie i błędzie.',hook],
['Anuluj trwające zapytania przed zmianą cache.','Spóźniona odpowiedź nadpisałaby podgląd.',hook],
['Zbuduj kartę z uchwytem i linkiem do szczegółów.','Zwykłe kliknięcie w tytuł nie zaczyna przeciągania.',card],
['Dodaj menu „Przenieś do…”.','Alternatywa bez przeciągania wymagana przez WCAG 2.5.7.',card],
['Wyłącz w menu kolumnę, która osiągnęła limit.','canDrop pilnuje tej samej reguły co przeciąganie.',card],
['Animuj wejście na wewnętrznym elemencie karty.','Element z setNodeRef należy do dnd-kit.',card],
['Zbuduj kolumnę z licznikiem i podświetleniem.','Kolumna jest też celem upuszczenia.',column],
['Złóż tablicę z DndContext.','closestCorners i próg aktywacji.',boardView],
['Pokazuj podgląd między kolumnami przez onDragOver.','Podgląd jest osobny, API jeszcze nic nie dostaje.',boardView],
['Zapisuj w API dopiero w onDragEnd.','Jedno żądanie na jedno przeniesienie.',boardView],
['Przywróć stan w onDragCancel.','Podgląd znika, plansza z serwera zostaje.',boardView],
['Odrzuć szóstą kartę z komunikatem w regionie.','Status jest stale w DOM.',boardView],
['Napisz polskie komunikaty czytnika.','Także dla anulowania.',boardView],
['Dodaj filtr „Tylko moje”.','Licznik nadal pokazuje pełną liczbę.',boardView],
['Obsłuż ograniczony ruch i podgląd bez animacji powrotu.','MotionConfig i dropAnimation={null}.',boardView],
['Przetestuj wycofanie przy błędzie 500.','Karta wraca, komunikat widoczny.',tests],
['Przetestuj zapis przez MSW.','Sprawdź status i pozycję w treści żądania.',tests],
['Sprawdź w przeglądarce scenariusz bez sieci.','DevTools, Network, Offline.','Włącz Offline, przenieś kartę, sprawdź powrót i komunikat „Nie udało się przenieść ticketu”. Porównaj układ z poprzednim stanem. Powtórz myszą i przez menu. Przeciąganie myszą i dotykiem nie jest pokryte w jsdom, więc ten scenariusz wykonaj ręcznie lub w Playwright.'],
['Wypisz, gdzie jeszcze musi być limit WIP.','Interfejs nie jest granicą bezpieczeństwa.','W interfejsie przeciągania, w menu „Przenieś do…” oraz na backendzie. Klient z innej karty lub bezpośrednie żądanie ominie interfejs, więc serwer musi odrzucić zmianę, a klient obsłużyć to przez wycofanie.'],
['Sprawdź kryteria zaliczenia bossa.','Dziewięć kryteriów i scenariusz bez sieci.','moveTicket jest czysty i przetestowany; kliknięcie nie startuje przeciągania; klawiatura i Escape działają; błąd sieci przywraca stan; limit odrzuca szóstą kartę z komunikatem; menu respektuje limit; filtr nie zmienia kolejności; ograniczony ruch usuwa przesunięcia; JSX i TSX mają tę samą logikę.'],
];
const concepts=[
['Jak reprezentować planszę?','Obiekt kolumna do tablicy id','Status w każdej karcie','Tablica kolumn w DOM','Tekst w URL','Zmiana to tylko małe tablice id.'],
['Skąd status ticketu?','Z kolumny, w której jest','Z osobnego pola obok kolumny','Z koloru','Z tytułu','Dwa źródła się rozjeżdżają.'],
['Co zwraca moveTicket przy odrzuceniu?','Ten sam obiekt','Pusty obiekt','Kopię','null','Porównanie referencji ujawnia brak zmiany.'],
['Co oznacza limit WIP?','Maksymalną liczbę kart w trakcie pracy','Limit zapytań API','Limit użytkowników','Limit kolumn','Ogranicza pracę równoległą.'],
['Czy zmiana kolejności w pełnej kolumnie jest dozwolona?','Tak','Nie','Tylko przez menu','Tylko bez TS','Liczba kart się nie zmienia.'],
['Dlaczego moveTicket ogranicza indeks?','Nieprawidłowa pozycja nie może psuć listy','Dla CSS','Dla SEO','Bo TS wymaga','toSpliced z dowolnym indeksem też by zadziałał, ale jawność chroni kontrakt.'],
['Co robi toSpliced?','Zwraca nową tablicę ze zmianą','Mutuje tablicę','Usuwa ostatni element','Sortuje','Niemutowalna wersja splice.'],
['Gdzie sprawdzić limit oprócz przeciągania?','W menu i na backendzie','Nigdzie','Tylko w CSS','Tylko w testach','Każda ścieżka zmiany musi go respektować.'],
['Co robi onMutate?','Zapisuje stary stan i ustawia nowy','Wysyła żądanie','Sprawdza typy','Wyświetla toast','To początek optymistycznej aktualizacji.'],
['Co robi onError?','Przywraca stan z kontekstu','Ponawia bez końca','Usuwa cache','Odświeża stronę','Stan ma być identyczny jak przed próbą.'],
['Co robi onSettled?','Unieważnia zapytanie po sukcesie lub błędzie','Tylko po sukcesie','Blokuje UI','Zamyka modal','Synchronizuje stan z serwerem.'],
['Po co cancelQueries w onMutate?','Spóźniona odpowiedź nie nadpisze podglądu','Aby wyłączyć sieć','Aby usunąć dane','Aby zablokować mutację','To typowy wyścig przy optymizmie.'],
['Kiedy wysłać PATCH?','W onDragEnd','W onDragOver','W onDragStart','Przy każdym render','Jedno żądanie na przeniesienie.'],
['Co musi zrobić onDragCancel?','Wyrzucić podgląd','Zapisać zmianę','Wysłać PATCH','Usunąć kartę','Stan wraca do sprzed podniesienia.'],
['Co jest podglądem w tej tablicy?','Osobna plansza w stanie na czas przeciągania','Cache serwera','DOM','Sesja','Oddziela chwilowy ruch od zapisanego stanu.'],
['Dlaczego nie mieszać podglądu z cache?','Anulowanie i błąd wymagają czystego stanu','Bo cache nie działa','Bo TS','Bo CSS','Łatwiej przywrócić poprzednią wersję.'],
['Jak filtrować „Tylko moje”?','Wyliczyć widoczne id w renderze','Usunąć karty z planszy','Zmienić dane serwera','Ukryć całą kolumnę','Dane i kolejność pozostają nienaruszone.'],
['Jaki błąd powoduje filtr usuwający karty?','Utratę kolejności po wyłączeniu','Brak key','Błąd CSS','Brak sieci','Stan powinien zachować wszystko.'],
['Co pokazuje licznik przy aktywnym filtrze?','Pełną liczbę kart w kolumnie','Liczbę widocznych z definicji błędnie','Zero','Numer kolumny','Limit WIP dotyczy całej kolumny.'],
['Dlaczego pozycja przy filtrze wymaga przeliczenia?','Widok ma inne indeksy niż pełna kolumna','Bo API tak chce zawsze','Bo TS','Bo CSS','Inaczej karta wyląduje w złym miejscu.'],
['Dlaczego nie dawać layout z Motion na kartę sortowaną?','Dwie biblioteki sterują transform','Bo Motion nie działa w li','Bo brak key','Bo limit WIP','Karty drgają.'],
['Gdzie Motion może animować kartę?','Wewnętrzny element, DragOverlay i liczniki','Na elemencie z setNodeRef','W onDragOver','W cache','Podział ról usuwa konflikt.'],
['Co animuje dnd-kit?','Przesuwanie podczas przeciągania','Wejście nowej karty','Licznik','Toast','Transform z useSortable.'],
['Kiedy CSS wystarcza?','hover i podświetlenie kolumny','Wyjście z AnimatePresence','Layout listy','Gest przesunięcia','Proste stany nie potrzebują biblioteki.'],
['Co robi MotionConfig reducedMotion="user"?','Wyłącza przesunięcia i skalowanie przy ustawieniu','Wyłącza dnd','Wyłącza API','Wymusza animacje','Przenikanie zostaje.'],
['Co robi dropAnimation={null}?','Usuwa animację powrotu podglądu','Blokuje upuszczenie','Wyłącza overlay','Wyłącza klawiaturę','Pomaga spełnić ograniczony ruch.'],
['Jak zwykłe kliknięcie nie zaczyna przeciągania?','Próg aktywacji czujnika','Własny setTimeout','Blokada linków','CSS','activationConstraint rozdziela gesty.'],
['Jak przewijać palcem na telefonie?','Osobny uchwyt i touch-action tylko na nim','touch-action:none na całej liście','Wyłączyć scroll','Sam overlay','Karta nie blokuje przewijania.'],
['Jak wygląda przeniesienie klawiaturą?','Tab, spacja, strzałki, spacja','Enter i myszka','Sam Tab','Skrót Ctrl+S','Escape anuluje.'],
['Gdzie trafia komunikat o limicie?','Do stale obecnego regionu','Do alertu przeglądarki','Do konsoli','Do tytułu strony','Czytnik i wzrok dostają tę samą informację.'],
['Co zawiera menu „Przenieś do…” dla pełnej kolumny?','Wyłączoną pozycję z wyjaśnieniem','Brak informacji','Ukrytą kartę','Błąd 500','Użytkownik wie dlaczego.'],
['Czy menu może wysłać zmianę do pełnej kolumny?','Nie, też używa canDrop','Tak','Tylko bez filtra','Tylko z klawiatury','Jedna reguła dla wszystkich ścieżek.'],
['Czemu id kolumn i kart nie mogą się mieszać?','Cel musi być jednoznaczny','Dla CSS','Dla SEO','Dla TS Vite','Unia statusów i liczby są rozróżnialne.'],
['Jak rozpoznać cel: kolumna czy karta?','Funkcja sprawdzająca unię statusów','Pozycją kursora ręcznie','Długością tekstu','Klasą CSS','Oba przypadki dają inny indeks.'],
['Jaki indeks dostaje upuszczenie na kolumnę?','Koniec kolumny','Zero zawsze','Losowy','-1','Pusta kolumna też to obsługuje.'],
['Jak test sprawdza wycofanie?','MSW zwraca 500 i karta wraca','Tylko typy','Zrzut ekranu','Sprawdzenie CSS','Prawdziwa mutacja i prawdziwy cache.'],
['Co sprawdza brak zapisów po Escape?','Lista żądań PATCH pozostaje pusta','Kolor kolumny','Stan tooltipu','Plik CSS','Podgląd nie może trafić do API.'],
['Czego nie sprawdzi jsdom w tablicy?','Prawdziwego przeciągania i kolizji','Ról','Tekstu','Żądań MSW','Zerowe prostokąty uniemożliwiają to.'],
['Jak sprawdzić przeciąganie?','Playwright lub ręcznie w przeglądarce','Tylko RTL','Tylko tsc','Tylko Lighthouse','Potrzebny jest realny układ.'],
['Co zrobić, gdy serwer odrzuci limit?','Wycofać i pokazać powód','Zignorować','Zostawić kartę','Odświeżyć stronę','Backend jest ostateczną instancją.'],
['Jak nazwać komunikat po sukcesie przez menu?','Przeniesiono tytuł do kolumny','Sukces','OK','200','Treść mówi, co się stało.'],
['Dlaczego menu zamyka się po wyborze?','Działanie jest zakończone','Bo CSS','Bo TS','Bo limit','Stan menu to osobna sprawa od planszy.'],
['Co robi hidden na liście menu?','Usuwa z widoku i drzewa dostępności','Tylko zmienia kolor','Usuwa z DOM','Blokuje klucze','Przyciski nie są dostępne przed otwarciem.'],
['Jakie atrybuty ma przycisk menu?','aria-expanded i aria-controls','aria-pressed zawsze','aria-sort','aria-valuenow','To wzorzec disclosure.'],
['Po co Link w karcie zamiast onClick?','Daje historię i otwieranie w nowej karcie','Jest krótszy','Blokuje klawiaturę','Działa tylko w dnd','Nawigacja to link.'],
['Co zrobić z danymi ticketów w cache?','Trzymać osobno lub w słowniku','Kopiować do każdej kolumny','Wstawić do DOM','Trzymać w CSS','Przeniesienie nie dotyka danych karty.'],
['Co oznacza „stan identyczny jak przed próbą”?','Ta sama kolejność i kolumny po wycofaniu','Podobny układ','Odświeżona strona','Pusta tablica','Kryterium z bossa jest dosłowne.'],
['Jaki problem ma Partial<Record<…>> dla limitów?','Limit może nie istnieć i trzeba to obsłużyć','Żaden wymóg obsługi','Błąd kompilacji zawsze','Blokuje TS','undefined oznacza brak limitu.'],
['Jaki typ wymusza wszystkie kolumny?','Record<TicketStatus,number[]>','Partial','any','unknown','Brak kolumny to błąd kompilacji.'],
['Jaki typ ma zdarzenie końca przeciągania?','DragEndEvent','MouseEvent','SubmitEvent','ChangeEvent','Typ biblioteki dnd-kit.'],
['Co da TSX przy unii statusów?','Literówka w statusie jest błędem','Automatyczny zapis','Lepszą animację','Brak testów','Reguły biznesowe nadal testujesz.'],
['Czego nie zapewni TSX?','Że limit działa na backendzie','Nazwy pól','Typu zdarzenia','Unii statusów','To logika sprawdzana testami.'],
['Co pokazuje podświetlenie column--over?','Że kolumna jest celem','Że jest pusta','Że ma błąd','Że jest filtrowana','Prosty stan w CSS.'],
['Co pokazuje licznik 5 / 5?','Pełną kolumnę','Pięć stron','Pięć sekund','Pięć błędów','Użytkownik widzi limit przed próbą.'],
['Co należy pokazać przy pustej kolumnie?','Obszar upuszczenia z czytelnym nagłówkiem','Nic','Błąd','Ukrytą kolumnę','Musi dać się tam upuścić.'],
['Co testujesz przy filtrze?','Ukrycie i powrót w tej samej kolejności','Tylko CSS','Tylko typ','Tylko sieć','To dokładnie kryterium akceptacji.'],
['Czy można zapisać kolejność przez sam indeks w widoku?','Nie przy filtrze','Tak zawsze','Tak, w TSX','Tak, w MSW','Dlatego jest funkcja fullIndex.'],
['Co jest głównym celem bossa?','Połączenie gestów, dostępności i optymizmu','Największa liczba bibliotek','Najdłuższa animacja','Najmniej testów','Każdy gest ma alternatywę.'],
['Co wyróżnia dobrą animację w bossie?','Stan zmienia się od razu, a ruch to warstwa prezentacji','Blokuje kliknięcia','Trwa sekundę','Zmienia dane','Wspólny mianownik rozdziału.'],
['Co zrobić, gdy czytnik nie ogłasza upuszczenia?','Sprawdzić komunikaty i region w przeglądarce','Pominąć','Dodać alert()','Zmienić kolory','Test tekstu nie gwarantuje odczytu.'],
];
const header=String.raw`import {canDrop,findColumn,fullIndex,moveTicket,visibleIds,WIP_LIMITS,type Ticket} from "./board";
import {full,people,small} from "./boardLab";
const owners=people as unknown as Record<number,Ticket>;`;
const j=(expr)=>`${expr}.join()||"pusto"`;
const probeRows=[
['Kolumna OPEN po przeniesieniu 1 na koniec',j('moveTicket(small(),1,"OPEN",2).OPEN'),'2,3,1','Karta została wyjęta i wstawiona.',['1,2,3','3,2,1','2,1,3']],
['Kolumna OPEN po przeniesieniu 3 na początek',j('moveTicket(small(),3,"OPEN",0).OPEN'),'3,1,2','Pozostałe się przesuwają.',['1,2,3','1,3,2','2,3,1']],
['Kolumna IN_PROGRESS po wstawieniu na 0',j('moveTicket(small(),1,"IN_PROGRESS",0).IN_PROGRESS'),'1,4','Indeks wstawienia.',['4,1','1','4']],
['Kolumna IN_PROGRESS po wstawieniu na 1',j('moveTicket(small(),1,"IN_PROGRESS",1).IN_PROGRESS'),'4,1','Na koniec.',['1,4','1','4']],
['Źródło po przeniesieniu',j('moveTicket(small(),1,"IN_PROGRESS",0).OPEN'),'2,3','Karta nie może być dwa razy.',['1,2,3','1,2','1,3']],
['Pusta kolumna po wstawieniu',j('moveTicket(small(),1,"CLOSED",0).CLOSED'),'1','Pusta kolumna przyjmuje kartę.',['pusto','1,2','4']],
['Indeks ponad długość',j('moveTicket(small(),1,"IN_PROGRESS",99).IN_PROGRESS'),'4,1','Ograniczony do końca.',['1,4','pusto','1']],
['Indeks ujemny',j('moveTicket(small(),1,"IN_PROGRESS",-5).IN_PROGRESS'),'1,4','Ograniczony do początku.',['4,1','pusto','4']],
['Czy pełna kolumna zwraca ten sam obiekt?','(()=>{const b=full();return moveTicket(b,1,"IN_PROGRESS",0)===b;})()',true,'Limit odrzuca zmianę.'],
['Czy nieznana karta zwraca ten sam obiekt?','(()=>{const b=small();return moveTicket(b,99,"OPEN",0)===b;})()',true,'Brak karty.'],
['Czy zmiana kolejności w pełnej kolumnie przechodzi?',j('moveTicket(full(),10,"IN_PROGRESS",4).IN_PROGRESS'),'11,12,13,14,10','Limit dotyczy nowych kart.',['10,11,12,13,14','pusto','10']],
['Czy moveTicket mutuje wejście?','(()=>{const b=small();moveTicket(b,1,"CLOSED",0);return b.OPEN.length;})()',3,'Zwraca nowy obiekt.'],
['Czy nietknięta kolumna zachowuje referencję?','(()=>{const b=small();return moveTicket(b,1,"CLOSED",0).IN_PROGRESS===b.IN_PROGRESS;})()',true,'Spread kopiuje tylko górny poziom.'],
['Kolumna karty 4','findColumn(small(),4)','IN_PROGRESS','Wyszukiwanie po listach.',['OPEN','CLOSED','undefined']],
['Kolumna nieznanej karty','String(findColumn(small(),99))','undefined','Brak w planszy.',['OPEN','null','CLOSED']],
['Czy można dodać do pełnej kolumny?','canDrop(full(),1,"IN_PROGRESS")',false,'Limit 5.'],
['Czy karta z pełnej kolumny może w niej zostać?','canDrop(full(),10,"IN_PROGRESS")',true,'Nie zwiększa liczby.'],
['Czy można dodać do kolumny bez limitu?','canDrop(full(),1,"CLOSED")',true,'Brak limitu.'],
['Czy można dodać do niepełnej kolumny z limitem?','canDrop(small(),1,"IN_PROGRESS")',true,'Jest miejsce.'],
['Jaki jest limit W toku?','WIP_LIMITS.IN_PROGRESS',5,'Z briefu.'],
['Jaki limit ma OPEN?','String(WIP_LIMITS.OPEN)','undefined','Partial: brak limitu.',['0','5','null']],
['Widoczne dla ala',j('visibleIds([1,2,3],owners,"ala")'),'1,3','Tylko przypisane.',['1,2,3','2','pusto']],
['Widoczne dla ola',j('visibleIds([1,2,3],owners,"ola")'),'2','Tylko przypisane.',['1,3','1,2,3','pusto']],
['Widoczne bez filtra',j('visibleIds([1,2,3],owners,null)'),'1,2,3','Filtr wyłączony.',['1,3','2','pusto']],
['Widoczne dla nieznanej osoby',j('visibleIds([1,2,3],owners,"ela")'),'pusto','Brak dopasowań.',['1,2,3','1,3','2']],
['Czy filtr mutuje wejście?','(()=>{const a=[1,2,3];visibleIds(a,owners,"ala");return a.length;})()',3,'filter tworzy nową tablicę.'],
['Czy kolejność jest zachowana?',j('visibleIds([3,2,1],owners,"ala")'),'3,1','Kolejność z planszy.',['1,3','3,2,1','pusto']],
['Pozycja pełna dla widocznego indeksu 0','fullIndex([1,2,3],[1,3],0)',0,'Karta 1 stoi pierwsza.'],
['Pozycja pełna dla widocznego indeksu 1','fullIndex([1,2,3],[1,3],1)',2,'Karta 3 jest trzecia w pełnej kolumnie.'],
['Pozycja pełna na końcu widoku','fullIndex([1,2,3],[1,3],2)',3,'Za ostatnią widoczną.'],
['Pozycja pełna przy pustym widoku','fullIndex([1,2,3],[],0)',3,'Na koniec kolumny.'],
['Pozycja pełna, gdy koniec widoku jest w środku','fullIndex([1,2,3],[1,2],2)',2,'Tuż za ostatnią widoczną.'],
['Pozycja pełna w pustej kolumnie','fullIndex([],[],0)',0,'Pierwsza karta.'],
['Liczba kart po przeniesieniu w sumie','(()=>{const b=moveTicket(small(),1,"CLOSED",0);return b.OPEN.length+b.IN_PROGRESS.length+b.CLOSED.length;})()',4,'Nic nie ginie.'],
['Czy pełna kolumna ma 5 kart?','full().IN_PROGRESS.length',5,'Dane testowe bossa.'],
['Czy karta jest tylko w jednej kolumnie?','(()=>{const b=moveTicket(small(),1,"CLOSED",0);return [b.OPEN,b.IN_PROGRESS,b.CLOSED].filter(c=>c.includes(1)).length;})()',1,'Model nie dopuszcza duplikatu.'],
['Kolumna po dwóch przeniesieniach',j('moveTicket(moveTicket(small(),1,"CLOSED",0),2,"CLOSED",0).CLOSED'),'2,1','Nowsza na początku.',['1,2','2','pusto']],
['Tytuł z danych owners','owners[2].title','B','Dane ticketów są osobno.',['A','C','undefined']],
['Czy OPEN w pełnej tablicy przyjmie kartę z W toku?','canDrop(full(),10,"OPEN")',true,'Brak limitu w OPEN.'],
['Czy przeniesienie z pełnej zwalnia miejsce?',j('moveTicket(full(),10,"OPEN",0).IN_PROGRESS'),'11,12,13,14','Teraz można dodać nową kartę.',['10,11,12,13,14','pusto','10']],
];
const project={
'src/board.ts':model,'src/useBoard.ts':hook,'src/TicketCard.tsx':card,'src/BoardColumn.tsx':column,'src/KanbanBoard.tsx':boardView,'src/boardLab.ts':lab,'src/board.test.tsx':tests,
'README.md':'DevDesk: boss Kanban. npm install, npm test. Vitest sprawdza czysty model, prawdziwą mutację z MSW i wycofanie, menu „Przenieś do…”, limit i filtr. jsdom nie ma układu: przeciąganie myszą, dotykiem i strzałkami oraz ogłaszanie przez czytnik sprawdź w przeglądarce, ręcznie lub w Playwright.\n',
};
const mutants=[
{file:'src/board.ts',before:'findColumn(board,ticketId)===toColumn||',after:''},
{file:'src/board.ts',before:'if(!fromColumn||!canDrop(board,ticketId,toColumn))return board;',after:'if(!fromColumn)return board;'},
{file:'src/board.ts',before:'const target=fromColumn===toColumn?without:board[toColumn];',after:'const target=board[toColumn];'},
{file:'src/board.ts',before:'column.indexOf(last)+1',after:'column.indexOf(last)'},
{file:'src/useBoard.ts',before:'if(context?.previous)client.setQueryData(KEY,context.previous);',after:''},
{file:'src/useBoard.ts',before:'onFailure("Nie udało się przenieść ticketu");',after:''},
{file:'src/useBoard.ts',before:'if(previous)client.setQueryData<Board>(KEY,moveTicket(previous,move.ticketId,move.status,move.position));',after:''},
{file:'src/TicketCard.tsx',before:'disabled={!allowed} ',after:''},
{file:'src/KanbanBoard.tsx',before:'visibleIds(shown[column],tickets,owner)',after:'shown[column]'},
{file:'src/BoardColumn.tsx',before:'limit===undefined?count:count+" / "+limit',after:'count'},
];
module.exports=lesson({exercises,concepts,probeRows,project,mutants,header});
