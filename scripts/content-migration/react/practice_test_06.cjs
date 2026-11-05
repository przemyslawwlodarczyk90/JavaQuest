const base=require('./practice_test_05.cjs');
const api=String.raw`import type {Priority,Status,Ticket} from "./ticketTypes";
// W Node fetch nie zna adresu strony, dlatego klient używa adresu absolutnego.
export const API="http://devdesk.test/api";
export type TicketPage={items:Ticket[];total:number;page:number;pageSize:number};
export class ApiError extends Error{
  constructor(readonly status:number,readonly fieldErrors:Record<string,string>={}){super("HTTP "+status);this.name="ApiError";}
}
async function send<T>(path:string,init:RequestInit={}):Promise<T>{
  const headers:Record<string,string>={Accept:"application/json"};
  if(init.body)headers["Content-Type"]="application/json";
  const response=await fetch(API+path,{...init,headers});
  if(!response.ok){
    let fieldErrors:Record<string,string>={};
    try{const body=await response.json() as {fieldErrors?:Record<string,string>};fieldErrors=body.fieldErrors??{};}catch{/* treść błędu nie jest JSON */}
    throw new ApiError(response.status,fieldErrors);
  }
  return await response.json() as T;
}
export function fetchTickets({status,page}:{status?:Status;page:number}){
  const params=new URLSearchParams({page:String(page)});
  if(status)params.set("status",status);
  return send<TicketPage>("/tickets?"+params.toString());
}
export function patchTicket(id:number,patch:Partial<Pick<Ticket,"status"|"priority">>){
  return send<Ticket>("/tickets/"+id,{method:"PATCH",body:JSON.stringify(patch)});
}
export function createTicket(values:{title:string;priority:Priority}){
  return send<Ticket>("/tickets",{method:"POST",body:JSON.stringify(values)});
}`;
const mocks=String.raw`import {http,HttpResponse} from "msw";
import {setupServer} from "msw/node";
import {API,type TicketPage} from "./ticketApi";
import {makeTicket,type Priority,type Ticket} from "./ticketTypes";
const seed=():Ticket[]=>[
  makeTicket(),
  makeTicket({id:7,title:"Dokumentacja",status:"CLOSED",priority:"LOW"}),
  makeTicket({id:9,title:"Logowanie SSO",status:"IN_PROGRESS",priority:"NORMAL"}),
];
let db=seed();
// Handlery zmieniają bazę w pamięci, więc każdy test musi zaczynać od świeżej kopii.
export function resetDb(){db=seed();}
export const handlers=[
  http.get(API+"/tickets",({request})=>{
    const url=new URL(request.url),status=url.searchParams.get("status"),page=Number(url.searchParams.get("page")??"1"),pageSize=10;
    const matching=status?db.filter(ticket=>ticket.status===status):db;
    return HttpResponse.json<TicketPage>({items:matching.slice((page-1)*pageSize,page*pageSize),total:matching.length,page,pageSize});
  }),
  http.patch(API+"/tickets/:id",async({request,params})=>{
    const id=Number(params.id),patch=await request.json() as Partial<Ticket>,index=db.findIndex(ticket=>ticket.id===id);
    if(index<0)return HttpResponse.json({message:"Nie ma ticketu "+id},{status:404});
    const saved={...db[index],...patch};db=db.map(ticket=>ticket.id===id?saved:ticket);
    return HttpResponse.json(saved);
  }),
  http.post(API+"/tickets",async({request})=>{
    const body=await request.json() as {title?:string;priority?:Priority},title=(body.title??"").trim();
    if(title.length<5)return HttpResponse.json({fieldErrors:{title:"Tytuł musi mieć co najmniej 5 znaków."}},{status:400});
    const ticket=makeTicket({id:100+db.length,title,priority:body.priority??"NORMAL"});db=[...db,ticket];
    return HttpResponse.json(ticket,{status:201});
  }),
];
export const server=setupServer(...handlers);`;
const setup=String.raw`import {afterAll,afterEach,beforeAll} from "vitest";
import {cleanup} from "@testing-library/react";
import "@testing-library/jest-dom/vitest";
import {resetDb,server} from "./mswServer";
// MSW 3: onUnhandledFrame (w MSW 2 ta opcja nazywała się onUnhandledRequest).
beforeAll(()=>server.listen({onUnhandledFrame:"error"}));
afterEach(()=>{cleanup();server.resetHandlers();resetDb();});
afterAll(()=>server.close());`;
const page=String.raw`import {useMutation,useQuery,useQueryClient} from "@tanstack/react-query";
import {useSearchParams} from "react-router";
import {PriorityBadge,StatusBadge} from "./badges";
import {fetchTickets,patchTicket,type TicketPage} from "./ticketApi";
import {statuses,type Priority,type Status} from "./ticketTypes";
function parseStatus(raw:string|null):Status|undefined{return statuses.find(status=>status===raw);}
export function TicketsPage(){
  const [params,setParams]=useSearchParams();
  const status=parseStatus(params.get("status")),page=Math.max(1,Math.floor(Number(params.get("page")))||1);
  const key=["tickets",{status:status??null,page}] as const;
  const client=useQueryClient();
  const query=useQuery({queryKey:key,queryFn:()=>fetchTickets({status,page})});
  const close=useMutation({
    mutationFn:(id:number)=>patchTicket(id,{status:"CLOSED"}),
    onSuccess:saved=>{client.setQueryData<TicketPage>(key,old=>old&&{...old,items:old.items.map(t=>t.id===saved.id?saved:t)});},
  });
  const raise=useMutation({
    mutationFn:({id,priority}:{id:number;priority:Priority})=>patchTicket(id,{priority}),
    onMutate:async({id,priority})=>{
      await client.cancelQueries({queryKey:key});
      const previous=client.getQueryData<TicketPage>(key);
      client.setQueryData<TicketPage>(key,old=>old&&{...old,items:old.items.map(t=>t.id===id?{...t,priority}:t)});
      return {previous};
    },
    onError:(_error,_variables,context)=>{if(context?.previous)client.setQueryData(key,context.previous);},
    onSettled:()=>client.invalidateQueries({queryKey:["tickets"]}),
  });
  if(query.isPending)return <p aria-label="Ładowanie ticketów" aria-busy="true">Ładowanie…</p>;
  if(query.isError)return <div role="alert"><p>Nie udało się wczytać ticketów.</p><button type="button" onClick={()=>void query.refetch()}>Spróbuj ponownie</button></div>;
  if(query.data.items.length===0)return <section><p>Brak ticketów pasujących do filtrów.</p><button type="button" onClick={()=>setParams({})}>Wyczyść filtry</button></section>;
  return <section>
    {raise.isError&&<p role="alert">Nie zapisano priorytetu. Przywrócono poprzednią wartość.</p>}
    {close.isError&&<p role="alert">Nie udało się zamknąć ticketu.</p>}
    <table><caption>Tickety ({query.data.total})</caption>
      <thead><tr><th scope="col">Tytuł</th><th scope="col">Status</th><th scope="col">Priorytet</th><th scope="col">Akcje</th></tr></thead>
      <tbody>{query.data.items.map(ticket=><tr key={ticket.id}>
        <td>{ticket.title}</td><td><StatusBadge status={ticket.status}/></td><td><PriorityBadge priority={ticket.priority}/></td>
        <td>
          {ticket.status!=="CLOSED"&&<button type="button" disabled={close.isPending} onClick={()=>close.mutate(ticket.id)}>Zamknij</button>}
          {ticket.priority!=="HIGH"&&<button type="button" onClick={()=>raise.mutate({id:ticket.id,priority:"HIGH"})}>Podnieś priorytet</button>}
        </td>
      </tr>)}</tbody>
    </table>
  </section>;
}`;
const form=String.raw`import {useMutation,useQueryClient} from "@tanstack/react-query";
import {useId,useState,type FormEvent} from "react";
import {ApiError,createTicket} from "./ticketApi";
export function CreateTicketForm(){
  const id=useId(),client=useQueryClient();
  const [title,setTitle]=useState("");
  const create=useMutation({mutationFn:createTicket,onSuccess:()=>{setTitle("");return client.invalidateQueries({queryKey:["tickets"]});}});
  const fieldError=create.error instanceof ApiError?create.error.fieldErrors.title:undefined;
  const formError=create.error&&!fieldError?"Nie udało się utworzyć ticketu.":undefined;
  function submit(event:FormEvent<HTMLFormElement>){event.preventDefault();create.mutate({title,priority:"NORMAL"});}
  return <form noValidate onSubmit={submit}>
    <label htmlFor={id}>Tytuł</label>
    <input id={id} value={title} aria-invalid={!!fieldError} aria-describedby={fieldError?id+"-error":undefined} onChange={event=>setTitle(event.currentTarget.value)}/>
    {fieldError&&<p id={id+"-error"}>{fieldError}</p>}
    {formError&&<p role="alert">{formError}</p>}
    {create.isSuccess&&<p role="status">Utworzono ticket #{create.data.id}.</p>}
    <button type="submit" disabled={create.isPending}>{create.isPending?"Zapisywanie…":"Utwórz"}</button>
  </form>;
}`;
const header=String.raw`// @vitest-environment jsdom
import {screen,waitFor,waitForElementToBeRemoved,within} from "@testing-library/react";
import userEvent from "@testing-library/user-event";
import {http,HttpResponse} from "msw";
import {expect,test} from "vitest";
import {server} from "./mswServer";
import {renderWithProviders} from "./renderWithProviders";
import {API,type TicketPage} from "./ticketApi";
import {makeTicket} from "./ticketTypes";
import {TicketsPage} from "./TicketsPage";
const empty:TicketPage={items:[],total:0,page:1,pageSize:10};
`;
const listStates=header+String.raw`test("pokazuje ładowanie, a potem tickety z API",async()=>{
  renderWithProviders(<TicketsPage/>,{route:"/tickets"});
  expect(screen.getByLabelText("Ładowanie ticketów")).toBeInTheDocument();
  expect(await screen.findByRole("row",{name:/API timeout/})).toBeInTheDocument();
  expect(screen.queryByLabelText("Ładowanie ticketów")).not.toBeInTheDocument();
});
test("waitForElementToBeRemoved czeka na zniknięcie wskaźnika",async()=>{
  renderWithProviders(<TicketsPage/>);
  await waitForElementToBeRemoved(()=>screen.queryByLabelText("Ładowanie ticketów"));
  expect(screen.getByRole("table",{name:"Tickety (3)"})).toBeInTheDocument();
  expect(screen.getAllByRole("row")).toHaveLength(4);
});`;
const listErrors=header+String.raw`test("błąd 500 pokazuje komunikat, a ponowienie wysyła drugie żądanie",async()=>{
  let calls=0;
  server.use(http.get(API+"/tickets",()=>{calls+=1;return calls===1?HttpResponse.json({message:"Błąd"},{status:500}):HttpResponse.json(empty);}));
  const user=userEvent.setup();
  renderWithProviders(<TicketsPage/>);
  expect(await screen.findByRole("alert")).toHaveTextContent("Nie udało się wczytać ticketów.");
  expect(screen.queryByRole("table")).not.toBeInTheDocument();
  await user.click(screen.getByRole("button",{name:"Spróbuj ponownie"}));
  expect(await screen.findByText("Brak ticketów pasujących do filtrów.")).toBeInTheDocument();
  expect(calls).toBe(2);
});
test("bez retry:false test błędu czekałby na ponowienia — tu jest jedno żądanie",async()=>{
  let calls=0;
  server.use(http.get(API+"/tickets",()=>{calls+=1;return HttpResponse.json({message:"Błąd"},{status:503});}));
  renderWithProviders(<TicketsPage/>);
  await screen.findByRole("alert");
  expect(calls).toBe(1);
});`;
const listFilters=header+String.raw`test("filtr z adresu trafia do zapytania",async()=>{
  const seen:string[]=[];
  server.use(http.get(API+"/tickets",({request})=>{seen.push(new URL(request.url).searchParams.toString());return HttpResponse.json(empty);}));
  renderWithProviders(<TicketsPage/>,{route:"/tickets?status=OPEN&page=2"});
  expect(await screen.findByText("Brak ticketów pasujących do filtrów.")).toBeInTheDocument();
  expect(seen).toEqual(["page=2&status=OPEN"]);
});
test("niepoprawny status z adresu nie trafia do API",async()=>{
  const seen:string[]=[];
  server.use(http.get(API+"/tickets",({request})=>{seen.push(new URL(request.url).search);return HttpResponse.json(empty);}));
  renderWithProviders(<TicketsPage/>,{route:"/tickets?status=HACK&page=-4"});
  await screen.findByText("Brak ticketów pasujących do filtrów.");
  expect(seen).toEqual(["?page=1"]);
});
test("pusty stan: Wyczyść filtry pobiera pełną listę",async()=>{
  server.use(http.get(API+"/tickets",()=>HttpResponse.json(empty),{once:true}));
  const user=userEvent.setup();
  renderWithProviders(<TicketsPage/>,{route:"/tickets?status=OPEN"});
  await user.click(await screen.findByRole("button",{name:"Wyczyść filtry"}));
  expect(await screen.findByRole("table",{name:"Tickety (3)"})).toBeInTheDocument();
});`;
const closeTests=header+String.raw`test("zamknięcie wysyła PATCH i odświeża status w tym samym wierszu",async()=>{
  const sent:unknown[]=[];
  server.use(http.patch(API+"/tickets/:id",async({request,params})=>{
    sent.push({id:params.id,body:await request.json()});
    return HttpResponse.json(makeTicket({status:"CLOSED"}));
  }));
  const user=userEvent.setup();
  renderWithProviders(<TicketsPage/>);
  const row=await screen.findByRole("row",{name:/API timeout/});
  await user.click(within(row).getByRole("button",{name:"Zamknij"}));
  expect(await within(row).findByText("Zamknięty")).toBeInTheDocument();
  expect(sent).toEqual([{id:"42",body:{status:"CLOSED"}}]);
  expect(within(row).queryByRole("button",{name:"Zamknij"})).not.toBeInTheDocument();
});
test("błąd zamknięcia pokazuje komunikat i zostawia status",async()=>{
  server.use(http.patch(API+"/tickets/:id",()=>HttpResponse.json({message:"Błąd"},{status:500})));
  const user=userEvent.setup();
  renderWithProviders(<TicketsPage/>);
  const row=await screen.findByRole("row",{name:/API timeout/});
  await user.click(within(row).getByRole("button",{name:"Zamknij"}));
  expect(await screen.findByRole("alert")).toHaveTextContent("Nie udało się zamknąć ticketu.");
  expect(within(row).getByText("Otwarty")).toBeInTheDocument();
});
test("cache po zamknięciu zawiera odpowiedź serwera",async()=>{
  const user=userEvent.setup();
  const {queryClient}=renderWithProviders(<TicketsPage/>);
  const row=await screen.findByRole("row",{name:/API timeout/});
  await user.click(within(row).getByRole("button",{name:"Zamknij"}));
  await within(row).findByText("Zamknięty");
  const cached=queryClient.getQueryData<TicketPage>(["tickets",{status:null,page:1}]);
  expect(cached?.items.find(ticket=>ticket.id===42)?.status).toBe("CLOSED");
});`;
const optimistic=header+String.raw`const rows=()=>HttpResponse.json<TicketPage>({items:[makeTicket(),makeTicket({id:9,title:"Logowanie SSO",status:"IN_PROGRESS",priority:"NORMAL"})],total:2,page:1,pageSize:10});
test("plakietka zmienia się od razu, a po 500 wraca, zanim przyjdzie ponowne pobranie",async()=>{
  let releasePatch!:()=>void,releaseRefetch!:()=>void,gets=0;
  const patchGate=new Promise<void>(resolve=>{releasePatch=resolve;}),refetchGate=new Promise<void>(resolve=>{releaseRefetch=resolve;});
  server.use(
    http.get(API+"/tickets",async()=>{gets+=1;if(gets>1)await refetchGate;return rows();}),
    http.patch(API+"/tickets/:id",async()=>{await patchGate;return HttpResponse.json({message:"Błąd"},{status:500});}),
  );
  const user=userEvent.setup();
  renderWithProviders(<TicketsPage/>);
  const row=await screen.findByRole("row",{name:/Logowanie SSO/});
  await user.click(within(row).getByRole("button",{name:"Podnieś priorytet"}));
  expect(await within(row).findByText("Wysoki")).toBeInTheDocument();
  releasePatch();
  expect(await within(row).findByText("Zwykły")).toBeInTheDocument();
  await waitFor(()=>expect(gets).toBe(2));
  // onSettled zwraca obietnicę invalidateQueries: mutacja jest pending, dopóki refetch trwa.
  expect(screen.queryByRole("alert")).not.toBeInTheDocument();
  releaseRefetch();
  expect(await screen.findByRole("alert")).toHaveTextContent("Przywrócono poprzednią wartość");
});
test("udana zmiana zostaje po odpowiedzi serwera i odświeżeniu",async()=>{
  const user=userEvent.setup();
  renderWithProviders(<TicketsPage/>);
  const row=await screen.findByRole("row",{name:/Logowanie SSO/});
  await user.click(within(row).getByRole("button",{name:"Podnieś priorytet"}));
  expect(await within(row).findByText("Wysoki")).toBeInTheDocument();
  await waitFor(()=>expect(within(row).queryByRole("button",{name:"Podnieś priorytet"})).not.toBeInTheDocument());
  expect(screen.queryByRole("alert")).not.toBeInTheDocument();
});`;
const formTests=String.raw`// @vitest-environment jsdom
import {screen} from "@testing-library/react";
import userEvent from "@testing-library/user-event";
import {http,HttpResponse} from "msw";
import {expect,test} from "vitest";
import {CreateTicketForm} from "./CreateTicketForm";
import {server} from "./mswServer";
import {renderWithProviders} from "./renderWithProviders";
import {API} from "./ticketApi";
import {makeTicket} from "./ticketTypes";
test("400 z serwera pokazuje błąd przy polu tytułu",async()=>{
  const user=userEvent.setup();
  renderWithProviders(<CreateTicketForm/>);
  const input=screen.getByLabelText("Tytuł");
  await user.type(input,"abc");
  await user.click(screen.getByRole("button",{name:"Utwórz"}));
  expect(await screen.findByText("Tytuł musi mieć co najmniej 5 znaków.")).toBeInTheDocument();
  expect(input).toHaveAttribute("aria-invalid","true");
  expect(input).toHaveAccessibleDescription("Tytuł musi mieć co najmniej 5 znaków.");
  expect(input).toHaveValue("abc");
});
test("udane utworzenie wysyła JSON, pokazuje status i czyści pole",async()=>{
  const bodies:unknown[]=[];
  server.use(http.post(API+"/tickets",async({request})=>{bodies.push(await request.json());return HttpResponse.json(makeTicket({id:103,title:"Nowy ticket"}),{status:201});}));
  const user=userEvent.setup();
  renderWithProviders(<CreateTicketForm/>);
  await user.type(screen.getByLabelText("Tytuł"),"Nowy ticket");
  await user.click(screen.getByRole("button",{name:"Utwórz"}));
  expect(await screen.findByRole("status")).toHaveTextContent("Utworzono ticket #103.");
  expect(bodies).toEqual([{title:"Nowy ticket",priority:"NORMAL"}]);
  expect(screen.getByLabelText("Tytuł")).toHaveValue("");
});
test("w trakcie wysyłania przycisk jest zablokowany",async()=>{
  let release!:()=>void;const gate=new Promise<void>(resolve=>{release=resolve;});
  server.use(http.post(API+"/tickets",async()=>{await gate;return HttpResponse.json(makeTicket({id:104}),{status:201});}));
  const user=userEvent.setup();
  renderWithProviders(<CreateTicketForm/>);
  await user.type(screen.getByLabelText("Tytuł"),"Długi tytuł");
  await user.click(screen.getByRole("button",{name:"Utwórz"}));
  expect(await screen.findByRole("button",{name:"Zapisywanie…"})).toBeDisabled();
  release();
  expect(await screen.findByRole("button",{name:"Utwórz"})).toBeEnabled();
});`;
const clientTests=String.raw`// @vitest-environment node
import {http,HttpResponse} from "msw";
import {expect,test,vi} from "vitest";
import {server} from "./mswServer";
import {API,ApiError,createTicket,fetchTickets,patchTicket} from "./ticketApi";
test("klient wysyła parametry i zwraca stronę",async()=>{
  const page=await fetchTickets({status:"CLOSED",page:1});
  expect(page.items.map(ticket=>ticket.id)).toEqual([7]);
  expect(page.total).toBe(1);
});
test("patchTicket wysyła JSON z nagłówkiem",async()=>{
  const seen:{type:string|null;body:string}[]=[];
  server.use(http.patch(API+"/tickets/:id",async({request})=>{seen.push({type:request.headers.get("content-type"),body:await request.text()});return HttpResponse.json({id:42});}));
  await patchTicket(42,{priority:"LOW"});
  expect(seen).toEqual([{type:"application/json",body:'{"priority":"LOW"}'}]);
});
test("400 bez JSON daje ApiError z pustymi błędami pól",async()=>{
  server.use(http.post(API+"/tickets",()=>new HttpResponse("zła prośba",{status:400})));
  const error=await createTicket({title:"Długi tytuł",priority:"LOW"}).catch((reason:unknown)=>reason);
  expect(error).toBeInstanceOf(ApiError);
  expect(error).toMatchObject({status:400,fieldErrors:{}});
});
test("nieobsłużone żądanie kończy się błędem",async()=>{
  vi.spyOn(console,"error").mockImplementation(()=>{});
  await expect(fetch(API+"/nieznane")).rejects.toThrow();
});
test("błąd sieci to nie ApiError",async()=>{
  server.use(http.get(API+"/tickets",()=>HttpResponse.error()));
  const error=await fetchTickets({page:1}).catch((reason:unknown)=>reason);
  expect(error).toBeInstanceOf(TypeError);
  expect(error).not.toBeInstanceOf(ApiError);
});`;
const handlerTests=String.raw`// @vitest-environment jsdom
import {screen} from "@testing-library/react";
import {http,HttpResponse} from "msw";
import {expect,test} from "vitest";
import {server} from "./mswServer";
import {renderWithProviders} from "./renderWithProviders";
import {API,createTicket,fetchTickets,type TicketPage} from "./ticketApi";
import {TicketsPage} from "./TicketsPage";
const page=(total:number):TicketPage=>({items:[],total,page:1,pageSize:10});
test("ostatnie server.use ma pierwszeństwo, a resetHandlers je usuwa",async()=>{
  server.use(http.get(API+"/tickets",()=>HttpResponse.json(page(1))));
  server.use(http.get(API+"/tickets",()=>HttpResponse.json(page(2))));
  expect((await fetchTickets({page:1})).total).toBe(2);
  server.resetHandlers();
  expect((await fetchTickets({page:1})).total).toBe(3);
});
test("test A tworzy ticket w bazie handlerów",async()=>{
  await createTicket({title:"Tymczasowy",priority:"LOW"});
  expect((await fetchTickets({page:1})).total).toBe(4);
});
test("test B widzi świeżą bazę po resetDb",async()=>{
  expect((await fetchTickets({page:1})).total).toBe(3);
});
test("dwa komponenty z tym samym kluczem wysyłają jedno żądanie",async()=>{
  let gets=0;
  server.use(http.get(API+"/tickets",()=>{gets+=1;return HttpResponse.json(page(0));}));
  renderWithProviders(<><TicketsPage/><TicketsPage/></>);
  expect(await screen.findAllByText("Brak ticketów pasujących do filtrów.")).toHaveLength(2);
  expect(gets).toBe(1);
});`;
const exercises=[
  ['Napisz klienta API ticketów z absolutnym adresem bazowym i klasą ApiError przenoszącą błędy pól.','W Node fetch nie rozwiąże ścieżki względnej; nagłówek Content-Type dodaj tylko, gdy jest body.',api],
  ['Przygotuj handlery MSW z mutowalną bazą w pamięci, walidacją POST i funkcją resetDb.','Handler PATCH zwraca 404 dla nieznanego ID; POST zwraca 400 z fieldErrors.',mocks],
  ['Podłącz cykl życia MSW w pliku setup: listen z onUnhandledFrame, resetHandlers, close.','Po każdym teście przywróć handlery i dane — oba źródła stanu mogą przeciec.',setup],
  ['Zbuduj TicketsPage na TanStack Query: ładowanie, błąd z ponowieniem, pusty stan i tabela.','Klucz zapytania zawiera status i stronę odczytane z adresu.',page],
  ['Dodaj formularz tworzenia ticketu, który pokazuje błąd 400 przy polu tytułu.','Rozróżnij ApiError z fieldErrors od ogólnego błędu formularza.',form],
  ['Przetestuj przejście od stanu ładowania do wiersza z danymi z MSW.','Pierwszą asercję zrób synchronicznie, drugą przez findByRole.',listStates],
  ['Poczekaj na zniknięcie wskaźnika ładowania przez waitForElementToBeRemoved i policz wiersze.','Callback musi zwrócić element obecny w chwili wywołania.',listStates],
  ['Przetestuj błąd 500 i ponowienie, licząc wywołania handlera.','Pierwsze wywołanie zwraca 500, drugie pustą stronę.',listErrors],
  ['Sprawdź, że błąd ma rolę alert, tabela nie jest pokazana, a bez ponowień jest jedno żądanie.','retry:false w renderWithProviders decyduje o liczbie wywołań.',listErrors],
  ['Odczytaj w handlerze searchParams i sprawdź, że filtr z adresu trafił do API.','Porównaj pełny łańcuch parametrów, nie tylko obecność statusu.',listFilters],
  ['Sprawdź, że niepoprawny status i ujemna strona z adresu nie trafiają do API.','Parsowanie adresu jest kontraktem ekranu.',listFilters],
  ['Przetestuj pusty stan jednorazowym handlerem i przycisk Wyczyść filtry.','Opcja {once:true} sprawia, że kolejne żądanie trafi do domyślnego handlera.',listFilters],
  ['Przetestuj zamknięcie ticketu: wysłany PATCH i nowy status w tym samym wierszu.','Zbieraj dane w handlerze, a asercje wykonuj w treści testu.',closeTests],
  ['Sprawdź, że po zamknięciu w wierszu znika przycisk Zamknij.','Ogranicz zapytanie do wiersza przez within.',closeTests],
  ['Przetestuj błąd zamknięcia: komunikat w roli alert i niezmieniony status.','Odpowiedź 500 z server.use dotyczy tylko tego testu.',closeTests],
  ['Użyj queryClient zwróconego z renderWithProviders, by sprawdzić cache po mutacji.','Klucz musi być identyczny z kluczem ekranu.',closeTests],
  ['Steruj czasem odpowiedzi PATCH bramką Promise, by uchwycić stan optymistyczny.','Zwolnij bramkę dopiero po asercji pośredniego stanu.',optimistic],
  ['Udowodnij wycofanie optymistycznej zmiany, gdy ponowne pobranie jeszcze trwa.','Zatrzymaj drugi GET, żeby rollback nie mógł być zastąpiony refetchem.',optimistic],
  ['Sprawdź udaną optymistyczną zmianę: plakietka zostaje i nie ma komunikatu błędu.','Czekaj na zniknięcie przycisku zamiast na losową pauzę.',optimistic],
  ['Przetestuj błąd 400 formularza: komunikat powiązany z polem i aria-invalid.','toHaveAccessibleDescription sprawdza powiązanie aria-describedby.',formTests],
  ['Przetestuj udane utworzenie: treść POST, komunikat statusu i wyczyszczone pole.','Odczytaj request.json() w handlerze i porównaj z oczekiwanym obiektem.',formTests],
  ['Sprawdź, że przycisk jest zablokowany w trakcie wysyłania formularza.','Bramka w handlerze POST trzyma mutację w stanie pending.',formTests],
  ['Przetestuj klienta bez Reacta w środowisku node: parametry i odpowiedź.','Domyślne handlery wystarczą — filtr CLOSED zwraca jeden ticket.',clientTests],
  ['Sprawdź nagłówek Content-Type i surową treść JSON wysyłaną przez patchTicket.','request.text() pokazuje dokładny łańcuch body.',clientTests],
  ['Zamień odpowiedź 400 bez JSON na ApiError z pustymi błędami pól.','Parsowanie treści błędu nie może przesłonić statusu.',clientTests],
  ['Udowodnij, że nieobsłużone żądanie kończy się błędem przy onUnhandledFrame: "error".','Wycisz console.error szpiegiem przywracanym po teście.',clientTests],
  ['Rozróżnij błąd sieci (HttpResponse.error) od błędu HTTP.','Błąd sieci odrzuca fetch, zanim powstanie Response.',clientTests],
  ['Sprawdź, że ostatnie server.use ma pierwszeństwo, a resetHandlers je usuwa.','Nowe handlery są dopisywane na początek listy.',handlerTests],
  ['Udowodnij izolację: dane utworzone w jednym teście nie przeciekają do następnego.','resetDb w afterEach przywraca bazę handlerów.',handlerTests],
  ['Sprawdź, że dwa komponenty z tym samym kluczem zapytania wysyłają jedno żądanie.','TanStack Query deduplikuje zapytania w locie.',handlerTests],
];
const quiz=[];function q(...row){quiz.push(row);}
q('Co robi setupServer z msw/node?','Przechwytuje żądania wychodzące z procesu testów i odpowiada handlerami','Uruchamia prawdziwy serwer HTTP na porcie 8080','Rejestruje Service Workera w jsdom','Podmienia moduł klienta API przez vi.mock','W Node MSW działa przez interceptory żądań w procesie; nie potrzebuje Service Workera ani portu.');
q('Dlaczego te same handlery mogą działać w przeglądarce i w testach?','Opisują kontrakt HTTP niezależnie od środowiska uruchomienia','Bo testy zawsze uruchamiają przeglądarkę','Bo msw/node kopiuje Service Workera do Node','Bo handlery są zapisywane w localStorage','Handler opisuje metodę, ścieżkę i odpowiedź; setupWorker i setupServer tylko inaczej je podłączają.');
q('Gdzie wywołać server.listen()?','W beforeAll pliku setup','W każdym teście przed render','W afterEach','W komponencie aplikacji','Jedno uruchomienie na plik testów wystarcza; reset handlerów robi się po każdym teście.');
q('Po co server.resetHandlers() w afterEach?','Usuwa nadpisania z server.use, przywracając domyślne handlery','Zamyka serwer na stałe','Czyści cache TanStack Query','Resetuje zegar fake timers','Bez resetu scenariusz błędu z jednego testu wyciekałby do następnego.');
q('Jak w MSW 3 nazywa się opcja reakcji na żądanie bez handlera?','onUnhandledFrame','onUnhandledRequest','onMissingHandler','strictRequests','W MSW 2 była to onUnhandledRequest; w starszych poradnikach zobaczysz tę nazwę.');
q('Co daje onUnhandledFrame: "error"?','Nieoczekiwane żądanie kończy się błędem zamiast cicho przejść do sieci','Każde żądanie dostaje status 500','Wyłącza wszystkie handlery','Ukrywa ostrzeżenia w konsoli','Test nie przegapi wywołania, którego nikt nie zaplanował.');
q('Co robi server.use(handler)?','Dodaje handler przed domyślnymi na czas testu','Zastępuje na stałe całą listę handlerów','Wysyła żądanie testowe','Uruchamia drugi serwer','Nadpisanie obowiązuje do najbliższego resetHandlers.');
q('Który handler wygra, gdy dwa server.use pasują do tego samego żądania?','Dodany później','Dodany wcześniej','Domyślny z setupServer','Losowy','server.use dopisuje na początek listy, więc ostatnie nadpisanie jest sprawdzane jako pierwsze.');
q('Co robi opcja {once:true} handlera?','Handler obsługuje tylko pierwsze pasujące żądanie','Handler działa tylko w pierwszym teście pliku','Wymusza jedną odpowiedź na cały proces','Wyłącza handler po resetDb','Po jednym użyciu kolejne żądania trafiają do następnego pasującego handlera.');
q('Kiedy wywołać server.close()?','W afterAll','Po każdej asercji','W beforeEach','Nigdy, bo zamyka Vitest','Zamknięcie zwalnia interceptory po zakończeniu pliku testów.');
q('Dlaczego mockowanie modułu klienta API (vi.mock) jest słabszym testem niż MSW?','Sprawdza wywołanie funkcji, a nie ścieżkę komponent → klient → fetch → odpowiedź','Bo vi.mock nie działa z TypeScriptem','Bo MSW nie wymaga asercji','Bo vi.mock zawsze wysyła żądanie','Zmiana parametrów zapytania lub klienta nie zostanie wykryta albo zepsuje test mimo działającej aplikacji.');
q('Co tracisz, mockując globalny fetch przez vi.spyOn?','Niezależność testu od sposobu wykonania żądania','Możliwość renderowania komponentu','Typy Reacta','Dostęp do screen','Przejście z fetch na inną bibliotekę HTTP psuje test, choć zachowanie się nie zmieniło.');
q('Dlaczego klient w lekcji używa absolutnego API?','W Node fetch nie ma adresu strony, więc ścieżka względna jest niepoprawna','Bo MSW obsługuje tylko HTTPS','Bo jsdom blokuje ścieżki względne w przeglądarce','Bo TanStack Query wymaga domeny','Node fetch nie zna location; w aplikacji adres bazowy zwykle pochodzi z konfiguracji.');
q('Na co czeka await screen.findByRole(...)?','Aż element się pojawi, domyślnie do około 1 sekundy','Na zakończenie wszystkich żądań w procesie','Na następny render bez limitu','Na kliknięcie użytkownika','findBy to getBy opakowane w waitFor.');
q('Czym różni się queryBy od getBy przy braku elementu?','queryBy zwraca null, getBy rzuca błąd','queryBy czeka, getBy nie','getBy zwraca null, queryBy rzuca','Nie różnią się','queryBy służy do asercji nieobecności.');
q('Co wolno umieścić w callbacku waitFor?','Asercje, które mają w końcu przejść','Kliknięcia użytkownika','Wywołania mutacji','Zmianę handlerów MSW','Callback jest powtarzany; efekty uboczne wykonałyby się wiele razy.');
q('Co się stanie, gdy waitForElementToBeRemoved dostanie callback zwracający null już na starcie?','Rzuci błąd, bo element musi istnieć w chwili wywołania','Natychmiast przejdzie','Poczeka do timeoutu i przejdzie','Wyrenderuje element ponownie','To zabezpieczenie przed testem, który niczego nie sprawdza.');
q('Dlaczego renderWithProviders tworzy QueryClient wewnątrz funkcji?','Każdy test dostaje pusty cache','Żeby testy dzieliły dane','Żeby wyłączyć React','Bo QueryClient jest singletonem w bibliotece','Wspólny klient przenosiłby dane i błędy między testami.');
q('Po co retry:false w testach TanStack Query?','Test błędu nie czeka na domyślne ponowienia','Żeby wyłączyć cache','Żeby zapytania szły synchronicznie','Żeby MSW nie logował','Domyślnie zapytanie ponawia się trzy razy z rosnącym opóźnieniem.');
q('Jak sprawdzić, co komponent wysłał do serwera?','Zebrać request w handlerze i porównać w treści testu','Odczytać prywatny stan useMutation','Sprawdzić tylko komunikat na ekranie','Przeczytać kod komponentu w teście','Test mutacji sprawdza obie strony: wysłane dane i widok.');
q('Co się dzieje z expect rzuconym w handlerze MSW?','Wyjątek zamienia się w odpowiedź błędu, a test może go nie zgłosić czytelnie','Test zawsze natychmiast się kończy z tym komunikatem','MSW ignoruje wyjątek i zwraca 200','Vitest powtarza handler','Dlatego w handlerze zbiera się dane, a asercje robi w teście.');
q('Jak odczytać parametry zapytania w handlerze?','new URL(request.url).searchParams','params.searchParams','request.query','request.body.params','params zawiera tylko parametry ścieżki, np. :id.');
q('Jakiego typu jest params.id w handlerze /tickets/:id?','string','number','bigint','Zawsze undefined','Parametry ścieżki są tekstem; zamiana na liczbę należy do handlera.');
q('Co zwraca HttpResponse.error()?','Błąd sieci, który odrzuca fetch','Odpowiedź 500 z JSON','Odpowiedź 404','Pustą odpowiedź 204','Symuluje zerwane połączenie, a nie błędny status HTTP.');
q('Czym różni się 500 od HttpResponse.error() dla fetch?','500 daje Response z ok=false, error() odrzuca obietnicę fetch','Niczym','error() daje status 0 w Response','500 odrzuca fetch','Klient musi obsłużyć obie ścieżki osobno.');
q('Po co resetDb obok resetHandlers?','Handlery mutujące bazę w pamięci zostawiłyby zmiany dla kolejnych testów','resetHandlers nie istnieje w MSW 3','Bo resetDb zamyka serwer','Żeby przyspieszyć jsdom','resetHandlers przywraca funkcje, ale nie dane, które te funkcje zmieniły.');
q('Dlaczego test pustego stanu używa jednorazowego handlera?','Kliknięcie Wyczyść filtry powinno pobrać pełną listę z domyślnego handlera','Bo pusty stan wymaga dwóch serwerów','Bo once przyspiesza render','Bo inaczej MSW rzuca błąd typu','Drugie żądanie pokazuje, że przycisk naprawdę zmienia zapytanie.');
q('Dlaczego test błędu liczy wywołania handlera?','Potwierdza, że ponowienie wysłało nowe żądanie','Bo MSW wymaga licznika','Żeby zmierzyć wydajność','Żeby wyłączyć retry','Sama zmiana widoku mogłaby pochodzić z cache.');
q('Co sprawdza test z route "/tickets?status=OPEN&page=2"?','Że stan adresu przekłada się na parametry żądania','Że router działa w przeglądarce','Że serwer filtruje po stronie bazy','Że komponent nie używa sieci','Ekran odczytuje filtry z URL, a test potwierdza je na granicy HTTP.');
q('Dlaczego ekran nie wysyła status=HACK?','Parsuje status do znanej listy i odrzuca resztę','Bo MSW usuwa nieznane parametry','Bo URLSearchParams filtruje wartości','Bo React Router waliduje enumy','Kontrakt ekranu: tylko znane statusy trafiają do API.');
q('Co daje waitForElementToBeRemoved w porównaniu z findBy danych?','Wyraża oczekiwanie na zakończenie stanu, a nie na konkretny wiersz','Działa synchronicznie','Nie wymaga await','Pomija limit czasu','Przydaje się, gdy potem sprawdzasz kilka elementów naraz.');
q('Jak uchwycić stan pośredni aktualizacji optymistycznej?','Zatrzymać odpowiedź handlera bramką Promise do czasu asercji','Dodać sleep 500 ms','Wyłączyć MSW','Użyć snapshotu','Bramka daje deterministyczną kontrolę nad kolejnością.');
q('Dlaczego test rollbacku blokuje drugi GET?','Bez tego refetch po onSettled ukryłby brak wycofania','Bo GET nie może być dwa razy','Bo cache jest wyłączony','Bo onError nie działa z GET','Inaczej mutant bez rollbacku przeszedłby test dzięki odświeżeniu danych.');
q('Po co cancelQueries w onMutate?','Trwające pobranie nie nadpisze optymistycznej wartości','Żeby anulować mutację','Żeby wyczyścić cały cache','Żeby zablokować przycisk','Odpowiedź ze starym stanem przyszłaby po optymistycznej zmianie.');
q('Co zwraca onMutate w przykładzie?','Kontekst z poprzednią stroną danych','Nowy QueryClient','Odpowiedź serwera','Liczbę żądań','onError dostaje ten kontekst i przywraca dane.');
q('Po co invalidateQueries w onSettled?','Po sukcesie lub błędzie dane są zsynchronizowane z serwerem','Żeby anulować błąd','Żeby wyłączyć retry','Żeby usunąć handler MSW','Optymistyczny stan jest tymczasowy; źródłem prawdy pozostaje serwer.');
q('Dlaczego w teście zamknięcia używa się within(row)?','Asercja dotyczy konkretnego wiersza, a nie dowolnego tekstu na stronie','Bo screen nie widzi tabel','Bo within czeka dłużej','Bo row jest w portalu','Status „Zamknięty” mógłby istnieć w innym wierszu.');
q('Co zwraca renderWithProviders oprócz wyniku render?','queryClient użyty w teście','Instancję serwera MSW','Router przeglądarkowy','Store Zustand','Dostęp do klienta pozwala sprawdzić cache tam, gdzie to część kontraktu.');
q('Kiedy sprawdzanie cache QueryClient jest uzasadnione?','Gdy kontraktem jest aktualizacja danych bez ponownego pobrania','Zawsze zamiast sprawdzania ekranu','Nigdy','Tylko w testach e2e','Podstawą pozostaje widok; cache uzupełnia obraz.');
q('Co potwierdza toHaveAccessibleDescription na polu tytułu?','Komunikat jest powiązany z polem przez aria-describedby','Że pole ma placeholder','Że komunikat ma czerwony kolor','Że pole jest wymagane','Czytnik ekranu ogłosi błąd razem z polem.');
q('Dlaczego formularz po 400 zachowuje wpisany tekst?','Użytkownik poprawia dane zamiast wpisywać je od nowa','Bo React nie może czyścić pól','Bo MSW blokuje zmianę stanu','Bo 400 cofa render','Czyszczenie należy tylko do ścieżki sukcesu.');
q('Jak przetestować zablokowany przycisk podczas zapisu?','Zatrzymać odpowiedź POST bramką i sprawdzić stan przed jej zwolnieniem','Kliknąć dwa razy i liczyć milisekundy','Ustawić retry:true','Sprawdzić klasę CSS','Stan pending jest widoczny tylko, gdy odpowiedź jeszcze nie przyszła.');
q('Dlaczego klienta API można testować w środowisku node?','Nie renderuje DOM, a MSW działa w Node','Bo node ma jsdom','Bo fetch istnieje tylko w node','Bo React wymaga node','Lżejsze środowisko pasuje do czystego kodu sieciowego.');
q('Co sprawdza test nagłówka Content-Type?','Że serwer dostanie JSON opisany poprawnym typem','Że przeglądarka pokaże pole','Że MSW konwertuje body','Że odpowiedź ma status 200','Bez nagłówka wiele backendów (np. Spring) odrzuci treść.');
q('Co się stanie, gdy treść błędu 400 nie jest JSON?','Klient zachowa status i zwróci puste fieldErrors','Klient rzuci SyntaxError zamiast ApiError','Fetch się zawiesi','Formularz pokaże sukces','Parsowanie treści błędu jest w try/catch.');
q('Dlaczego dwa TicketsPage z tym samym kluczem wysyłają jedno żądanie?','TanStack Query deduplikuje zapytanie w locie','MSW łączy żądania','React renderuje komponent raz','fetch ma wbudowany cache','Obaj obserwatorzy dzielą jeden wpis cache.');
q('Czy dane utworzone przez POST w jednym teście powinny być widoczne w następnym?','Nie, testy muszą być niezależne','Tak, testy w pliku dzielą bazę celowo','Tak, jeśli są w tym samym describe','Tylko w trybie watch','resetDb po każdym teście daje powtarzalny start.');
q('Co jest dowodem rzeczywistego działania ponowienia w teście błędu?','Druga odpowiedź handlera widoczna na ekranie i licznik 2','Sam fakt kliknięcia','Brak ostrzeżeń w konsoli','Zmiana tekstu przycisku','Obie strony: żądanie i widok.');
q('Dlaczego nie czekać w teście przez setTimeout(500)?','Test zależałby od szybkości maszyny, a nie od warunku','Bo setTimeout nie działa w jsdom','Bo Vitest zabrania timerów','Bo MSW wtedy się wyłącza','findBy i waitFor czekają dokładnie tyle, ile trzeba.');
q('Co może się stać, gdy zapomnisz await przed findByRole?','Asercja dostanie Promise i test może przejść bez sprawdzenia','Test zawsze się zawiesi','React rzuci błąd kompilacji','MSW zwróci 404','Brak await to klasyczny fałszywie zielony test.');
q('Dlaczego test sprawdza zniknięcie przycisku Podnieś priorytet po sukcesie?','To obserwowalny skutek nowego priorytetu zamiast pauzy','Bo przycisk zawsze znika po kliknięciu','Bo tak działa waitFor','Bo inaczej MSW nie odpowie','Warunek końcowy zamiast arbitralnego czasu.');
q('Co w TSX chroni przed handlerem zwracającym zły kształt danych?','Typ HttpResponse.json<TicketPage>(...)','vi.mock','StrictMode','jest-dom','Ten sam typ kontraktu obowiązuje w kliencie i w mockach.');
q('Jak w JSX pilnować zgodności mocków z backendem?','Ręcznie porównywać odpowiedzi API z handlerami (np. testem kontraktu)','Nie trzeba, MSW sam pobiera schemat','Przez PropTypes w handlerach','Przez snapshot DOM','Bez typów zgodność zapewnia proces i testy kontraktu.');
q('Dlaczego test filtrów porównuje pełny łańcuch parametrów?','Wykrywa zarówno brak, jak i nadmiarowe parametry','Bo searchParams nie ma metody get','Bo MSW sortuje parametry','Bo tak jest szybciej','Pełne porównanie łapie np. status=undefined.');
q('Jaki problem wykrywa mutant klienta traktujący tylko statusy ≥500 jako błąd?','Odpowiedź 400 zostałaby potraktowana jak sukces','Brak cache','Brak routera','Zły kolor przycisku','Test formularza z błędem 400 musi go odrzucić.');
q('Jaki problem wykrywa mutant bez params.set("status")?','Filtr z adresu nie trafia do serwera','Brak paginacji w UI','Błąd typów','Brak nagłówka Accept','Test filtrów porównuje parametry w handlerze.');
q('Dlaczego test błędu zamknięcia sprawdza status „Otwarty”?','Nieudany zapis nie może zmienić widoku jak sukces','Bo alert zawsze zawiera status','Bo MSW zwraca stary status','Bo retry przywraca widok','Komunikat bez sprawdzenia danych byłby połowicznym testem.');
q('Kiedy MSW wypisuje błąd o nieobsłużonym żądaniu?','Gdy żaden handler nie pasuje do metody i adresu','Gdy handler zwraca 404','Gdy test trwa dłużej niż sekundę','Gdy komponent się odmontuje','404 z handlera to obsłużone żądanie.');
q('Dlaczego test nieobsłużonego żądania wycisza console.error?','MSW raportuje błąd w konsoli, a test sprawdza samo odrzucenie','Bo console.error psuje jsdom','Bo inaczej fetch przejdzie','Bo Vitest zabrania logów','Szpieg jest przywracany przez restoreMocks.');
q('Czy MSW w testach Node sprawdza CORS przeglądarki?','Nie','Tak, zawsze','Tylko dla PATCH','Tylko z jsdom','CORS to mechanizm przeglądarki; sprawdzisz go w e2e lub ręcznie.');
q('Czego nie dowodzą testy z MSW?','Że prawdziwy backend zwraca dane w tym samym kształcie','Że komponent wysyła poprawne parametry','Że ekran pokazuje błąd','Że ponowienie działa','Zgodność z backendem sprawdza test kontraktu lub e2e.');
q('Po co waitFor(()=>expect(gets).toBe(2)) w teście rollbacku?','Potwierdza, że refetch po onSettled rzeczywiście ruszył','Przyspiesza test','Zastępuje rollback','Wyłącza bramkę','Test opisuje całą sekwencję, a nie tylko końcowy wygląd.');
q('Dlaczego w teście rollbacku komunikat błędu mutacji pojawia się dopiero po zwolnieniu drugiego GET?','onSettled zwraca obietnicę invalidateQueries, więc mutacja pozostaje pending do końca refetchu','Bo onError nie wywołuje się przy 500','Bo MSW opóźnia wszystkie alerty','Bo React nie renderuje dwóch alertów','Zwrócona obietnica wydłuża mutację; to świadoma decyzja, którą test powinien znać, a nie omijać pauzą.');
q('Dlaczego TicketsPage aktualizuje cache po zamknięciu przez setQueryData?','Wiersz pokazuje odpowiedź serwera bez dodatkowego GET','Bo invalidateQueries nie istnieje','Bo setQueryData wysyła PATCH','Bo cache jest wyłączony','Test zamknięcia oczekuje nowego statusu po odpowiedzi PATCH.');
q('Czym jest obsłużony błąd HTTP w kliencie lekcji?','ApiError ze statusem i błędami pól','Zwykły string','Obiekt Response','null','Komponenty rozpoznają błąd przez instanceof ApiError.');
q('Dlaczego formularz rozróżnia fieldError od formError?','Błąd walidacji pola trafia przy polu, inne błędy jako ogólny alert','Bo React wymaga dwóch stanów','Bo MSW zwraca dwa błędy','Bo aria-invalid działa tylko z formError','Komunikat ma trafić tam, gdzie użytkownik może go naprawić.');
const probes=[
  ['Domyślna lista','3','2','0','10','console.log((await fetchTickets({page:1})).items.length);'],
  ['Filtr CLOSED','1','3','0','2','console.log((await fetchTickets({status:"CLOSED",page:1})).items.length);'],
  ['Tytuł po filtrze IN_PROGRESS','Logowanie SSO','API timeout','Dokumentacja','undefined','console.log((await fetchTickets({status:"IN_PROGRESS",page:1})).items[0].title);'],
  ['Druga strona','0','3','10','1','console.log((await fetchTickets({page:2})).items.length);'],
  ['Total na drugiej stronie','3','0','10','13','console.log((await fetchTickets({page:2})).total);'],
  ['Parametry w handlerze','page=1&status=OPEN','status=OPEN&page=1','page=1','status=OPEN','let seen="";server.use(http.get(API+"/tickets",({request})=>{seen=new URL(request.url).searchParams.toString();return HttpResponse.json({items:[],total:0,page:1,pageSize:10});}));await fetchTickets({status:"OPEN",page:1});console.log(seen);'],
  ['Brak statusu w zapytaniu','page=1','page=1&status=','page=1&status=undefined','status=undefined&page=1','let seen="";server.use(http.get(API+"/tickets",({request})=>{seen=new URL(request.url).searchParams.toString();return HttpResponse.json({items:[],total:0,page:1,pageSize:10});}));await fetchTickets({page:1});console.log(seen);'],
  ['Status 500 w ApiError','500','200','inny','0','server.use(http.get(API+"/tickets",()=>HttpResponse.json({},{status:500})));try{await fetchTickets({page:1});console.log("sukces");}catch(error){console.log(error instanceof ApiError?String(error.status):"inny");}'],
  ['Błąd pola z 400','Tytuł musi mieć co najmniej 5 znaków.','undefined','HTTP 400','Brak','try{await createTicket({title:"abc",priority:"LOW"});console.log("Brak");}catch(error){console.log(error instanceof ApiError?String(error.fieldErrors.title):"Brak");}'],
  ['Trim po stronie serwera','[Nowy ticket]','[  Nowy ticket  ]','[Nowy ticket  ]','[undefined]','console.log("["+(await createTicket({title:"  Nowy ticket  ",priority:"LOW"})).title+"]");'],
  ['Status utworzonego ticketu','OPEN','CLOSED','IN_PROGRESS','undefined','console.log((await createTicket({title:"Nowy ticket",priority:"LOW"})).status);'],
  ['Lista po utworzeniu','4','3','5','1','await createTicket({title:"Nowy ticket",priority:"LOW"});console.log((await fetchTickets({page:1})).total);'],
  ['resetDb po utworzeniu','3','4','0','5','await createTicket({title:"Nowy ticket",priority:"LOW"});resetDb();console.log((await fetchTickets({page:1})).total);'],
  ['PATCH zmienia status','CLOSED','OPEN','IN_PROGRESS','undefined','console.log((await patchTicket(42,{status:"CLOSED"})).status);'],
  ['PATCH zachowuje tytuł','API timeout','undefined','Pusty','Dokumentacja','console.log((await patchTicket(42,{status:"CLOSED"})).title);'],
  ['PATCH nieistniejącego ID','404','500','200','400','try{await patchTicket(999,{status:"CLOSED"});console.log("200");}catch(error){console.log(error instanceof ApiError?String(error.status):"inny");}'],
  ['Body wysłane w PATCH','{"priority":"LOW"}','{"status":"CLOSED"}','priority=LOW','{}','let body="";server.use(http.patch(API+"/tickets/:id",async({request})=>{body=await request.text();return HttpResponse.json({id:42});}));await patchTicket(42,{priority:"LOW"});console.log(body);'],
  ['Content-Type w POST','application/json','text/plain;charset=UTF-8','null','multipart/form-data','let type:string|null="";server.use(http.post(API+"/tickets",({request})=>{type=request.headers.get("content-type");return HttpResponse.json({id:1},{status:201});}));await createTicket({title:"Nowy ticket",priority:"LOW"});console.log(String(type));'],
  ['Content-Type w GET','null','application/json','text/plain','undefined','let type:string|null="";server.use(http.get(API+"/tickets",({request})=>{type=request.headers.get("content-type");return HttpResponse.json({items:[],total:0,page:1,pageSize:10});}));await fetchTickets({page:1});console.log(String(type));'],
  ['Metoda zamknięcia','PATCH','POST','PUT','GET','let method="";server.use(http.all(API+"/tickets/:id",({request})=>{method=request.method;return HttpResponse.json({id:42});}));await patchTicket(42,{status:"CLOSED"});console.log(method);'],
  ['Typ parametru ścieżki','string:42','number:42','string:undefined','number:NaN','let seen="";server.use(http.patch(API+"/tickets/:id",({params})=>{seen=typeof params.id+":"+String(params.id);return HttpResponse.json({id:42});}));await patchTicket(42,{status:"CLOSED"});console.log(seen);'],
  ['Handler jednorazowy','0,3','0,0','3,3','3,0','server.use(http.get(API+"/tickets",()=>HttpResponse.json({items:[],total:0,page:1,pageSize:10}),{once:true}));const first=(await fetchTickets({page:1})).total,second=(await fetchTickets({page:1})).total;console.log(first+","+second);'],
  ['resetHandlers w teście','3','0','undefined','1','server.use(http.get(API+"/tickets",()=>HttpResponse.json({items:[],total:0,page:1,pageSize:10})));server.resetHandlers();console.log((await fetchTickets({page:1})).total);'],
  ['Kolejność server.use','2','1','3','0','server.use(http.get(API+"/tickets",()=>HttpResponse.json({items:[],total:1,page:1,pageSize:10})));server.use(http.get(API+"/tickets",()=>HttpResponse.json({items:[],total:2,page:1,pageSize:10})));console.log((await fetchTickets({page:1})).total);'],
  ['Nieobsłużone żądanie','odrzucone','przepuszczone','404','200','const original=console.error;console.error=()=>{};try{await fetch(API+"/nieznane");console.log("przepuszczone");}catch{console.log("odrzucone");}finally{console.error=original;}'],
  ['Błąd sieci','TypeError','ApiError','inny','RangeError','server.use(http.get(API+"/tickets",()=>HttpResponse.error()));try{await fetchTickets({page:1});console.log("sukces");}catch(error){console.log(error instanceof ApiError?"ApiError":error instanceof TypeError?"TypeError":"inny");}'],
  ['Błąd bez JSON','0','1','undefined','4','server.use(http.get(API+"/tickets",()=>new HttpResponse("oops",{status:500})));try{await fetchTickets({page:1});console.log("sukces");}catch(error){console.log(error instanceof ApiError?String(Object.keys(error.fieldErrors).length):"inny");}'],
  ['Status utworzenia','201','200','204','400','const response=await fetch(API+"/tickets",{method:"POST",headers:{"Content-Type":"application/json"},body:JSON.stringify({title:"Długi tytuł",priority:"LOW"})});console.log(response.status);'],
  ['response.ok dla 404','false','true','undefined','404','const response=await fetch(API+"/tickets/999",{method:"PATCH",headers:{"Content-Type":"application/json"},body:"{}"});console.log(response.ok);'],
  ['Licznik równoległych żądań','2','1','0','3','let calls=0;server.use(http.get(API+"/tickets",()=>{calls+=1;return HttpResponse.json({items:[],total:0,page:1,pageSize:10});}));await Promise.all([fetchTickets({page:1}),fetchTickets({page:1})]);console.log(calls);'],
  ['ID nowego ticketu','103','100','4','42','console.log((await createTicket({title:"Nowy ticket",priority:"LOW"})).id);'],
  ['Filtr po zamknięciu','2','1','3','0','await patchTicket(42,{status:"CLOSED"});console.log((await fetchTickets({status:"CLOSED",page:1})).total);'],
  ['Komunikat ApiError','HTTP 400','Bad Request','Tytuł musi mieć co najmniej 5 znaków.','undefined','try{await createTicket({title:"abc",priority:"LOW"});}catch(error){console.log(error instanceof Error?error.message:"inny");}'],
  ['Nazwa błędu','ApiError','Error','TypeError','HttpError','try{await createTicket({title:"abc",priority:"LOW"});}catch(error){console.log(error instanceof Error?error.name:"inny");}'],
];
for(const [label,answer,b,c,d,body] of probes)q(label+' — jaki wynik?',answer,b,c,d,'Wynik pochodzi z prawdziwego wywołania fetch przechwyconego przez MSW w Vitest; handlery i baza są resetowane po każdym teście.',`import {http,HttpResponse} from "msw";import {resetDb,server} from "./mswServer";import {API,ApiError,createTicket,fetchTickets,patchTicket} from "./ticketApi";${body}`);
if(exercises.length!==30||quiz.length!==100)throw Error('Niepełny zestaw '+exercises.length+'/'+quiz.length);
const manifest=JSON.parse(base.project['package.json']);manifest.devDependencies.msw='3.0.3';
const project={...Object.fromEntries(Object.entries(base.project).filter(([file])=>!file.includes('.test.')||file==='src/WatchToggle.test.tsx')),
  'src/ticketApi.ts':api,'src/mswServer.ts':mocks,'src/testSetup.ts':setup,'src/TicketsPage.tsx':page,'src/CreateTicketForm.tsx':form,
  'src/listStates.test.tsx':listStates,'src/listErrors.test.tsx':listErrors,'src/listFilters.test.tsx':listFilters,'src/closeTicket.test.tsx':closeTests,
  'src/optimisticPriority.test.tsx':optimistic,'src/createForm.test.tsx':formTests,'src/apiClient.test.ts':clientTests,'src/handlers.test.tsx':handlerTests,
  'package.json':JSON.stringify(manifest,null,2)+'\n',
  'README.md':base.project['README.md']+' Testy asynchroniczne: MSW 3 (setupServer z msw/node) przechwytuje prawdziwe żądania fetch; handlery i baza w pamięci są resetowane po każdym teście, a QueryClient jest świeży i bez retry. To nie jest dowód zgodności z prawdziwym backendem ani CORS przeglądarki.'};
const mutants=[
  {file:'src/TicketsPage.tsx',before:'if(context?.previous)client.setQueryData(key,context.previous);',after:'void context;'},
  {file:'src/ticketApi.ts',before:'if(status)params.set("status",status);',after:''},
  {file:'src/ticketApi.ts',before:'if(!response.ok){',after:'if(response.status>=500){'},
  {file:'src/TicketsPage.tsx',before:'client.setQueryData<TicketPage>(key,old=>old&&{...old,items:old.items.map(t=>t.id===saved.id?saved:t)});',after:'void saved;'},
];
module.exports={api,mocks,setup,page,form,listStates,listErrors,listFilters,closeTests,optimistic,formTests,clientTests,handlerTests,exercises,quiz,project,mutants,vitestQuiz:true,quizEnvironment:'jsdom'};
