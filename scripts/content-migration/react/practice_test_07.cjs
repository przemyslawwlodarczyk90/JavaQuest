// Lekcja 14/07: Playwright. Projekt jest samodzielny: aplikacja Vite + mały serwer Node z API i sesją w ciasteczku.
const server=String.raw`// server.mjs — serwer statyczny dla dist/ i API DevDesk w pamięci (tylko do lokalnych testów E2E).
import {createServer} from "node:http";
import {readFile} from "node:fs/promises";
import {extname,join,normalize} from "node:path";
import {randomUUID} from "node:crypto";
const root=join(import.meta.dirname,"dist");
const seed=()=>[
  {id:42,title:"API timeout",status:"OPEN",priority:"HIGH"},
  {id:7,title:"Dokumentacja",status:"CLOSED",priority:"LOW"},
  {id:9,title:"Logowanie SSO",status:"IN_PROGRESS",priority:"NORMAL"},
];
let tickets=seed(),nextId=100;
const sessions=new Set();
const types={".html":"text/html; charset=utf-8",".js":"text/javascript; charset=utf-8",".css":"text/css; charset=utf-8",".svg":"image/svg+xml"};
function json(res,status,body,headers={}){res.writeHead(status,{"Content-Type":"application/json",...headers});res.end(body===undefined?"":JSON.stringify(body));}
async function readJson(req){let raw="";for await(const chunk of req)raw+=chunk;try{return raw?JSON.parse(raw):{};}catch{return null;}}
function session(req){const match=/(?:^|;\s*)sid=([^;]+)/.exec(req.headers.cookie??"");return match&&sessions.has(match[1])?match[1]:null;}
async function api(req,res,url){
  if(req.method==="POST"&&url.pathname==="/api/login"){
    const body=await readJson(req);
    if(body?.login!=="anna"||body?.password!==(process.env.E2E_PASSWORD??"test-haslo"))return json(res,401,{message:"Błędny login lub hasło."});
    const sid=randomUUID();sessions.add(sid);
    return json(res,204,undefined,{"Set-Cookie":"sid="+sid+"; HttpOnly; Path=/; SameSite=Lax"});
  }
  if(req.method==="POST"&&url.pathname==="/api/test/reset"&&process.env.E2E==="1"){tickets=seed();nextId=100;return json(res,204);}
  const sid=session(req);
  if(!sid)return json(res,401,{message:"Wymagane logowanie."});
  if(req.method==="POST"&&url.pathname==="/api/logout"){sessions.delete(sid);return json(res,204,undefined,{"Set-Cookie":"sid=; Max-Age=0; Path=/"});}
  if(req.method==="GET"&&url.pathname==="/api/me")return json(res,200,{login:"anna"});
  if(url.pathname==="/api/tickets"){
    if(req.method==="GET"){
      const status=url.searchParams.get("status"),q=(url.searchParams.get("q")??"").toLowerCase();
      return json(res,200,tickets.filter(t=>(!status||t.status===status)&&t.title.toLowerCase().includes(q)));
    }
    if(req.method==="POST"){
      const body=await readJson(req),title=String(body?.title??"").trim();
      if(title.length<5)return json(res,400,{fieldErrors:{title:"Tytuł musi mieć co najmniej 5 znaków."}});
      const ticket={id:nextId++,title,status:"OPEN",priority:["LOW","NORMAL","HIGH"].includes(body.priority)?body.priority:"NORMAL"};
      tickets=[...tickets,ticket];return json(res,201,ticket);
    }
  }
  const match=/^\/api\/tickets\/(\d+)$/.exec(url.pathname);
  if(match){
    const id=Number(match[1]),ticket=tickets.find(t=>t.id===id);
    if(!ticket)return json(res,404,{message:"Nie ma ticketu."});
    if(req.method==="GET")return json(res,200,ticket);
    if(req.method==="PATCH"){const body=await readJson(req);const saved={...ticket,...(body?.status?{status:body.status}:{})};tickets=tickets.map(t=>t.id===id?saved:t);return json(res,200,saved);}
    if(req.method==="DELETE"){tickets=tickets.filter(t=>t.id!==id);return json(res,204);}
  }
  return json(res,404,{message:"Nieznany adres API."});
}
createServer(async(req,res)=>{
  const url=new URL(req.url??"/","http://localhost");
  if(url.pathname==="/health"){res.end("ok");return;}
  if(url.pathname.startsWith("/api/"))return api(req,res,url);
  const file=normalize(join(root,url.pathname));
  try{if(!file.startsWith(root)||!extname(file))throw new Error("SPA");res.writeHead(200,{"Content-Type":types[extname(file)]??"application/octet-stream"});res.end(await readFile(file));}
  catch{res.writeHead(200,{"Content-Type":types[".html"]});res.end(await readFile(join(root,"index.html")));}
}).listen(Number(process.env.PORT??4317),"127.0.0.1");`;
const apiClient=String.raw`export type Status="OPEN"|"IN_PROGRESS"|"CLOSED";
export type Priority="LOW"|"NORMAL"|"HIGH";
export type Ticket={id:number;title:string;status:Status;priority:Priority};
export class AuthError extends Error{constructor(){super("Wymagane logowanie");this.name="AuthError";}}
export class ApiError extends Error{constructor(readonly status:number){super("HTTP "+status);this.name="ApiError";}}
async function send<T>(path:string,init:RequestInit={}):Promise<T>{
  const response=await fetch(path,{...init,headers:init.body?{"Content-Type":"application/json"}:{}});
  if(response.status===401)throw new AuthError();
  if(!response.ok)throw new ApiError(response.status);
  return (response.status===204?undefined:await response.json()) as T;
}
export const login=(login:string,password:string)=>send<void>("/api/login",{method:"POST",body:JSON.stringify({login,password})});
export const logout=()=>send<void>("/api/logout",{method:"POST"});
export const me=()=>send<{login:string}>("/api/me");
export function listTickets({status,q}:{status:string;q:string},signal?:AbortSignal){
  const params=new URLSearchParams();if(status)params.set("status",status);if(q)params.set("q",q);
  return send<Ticket[]>("/api/tickets?"+params.toString(),{signal});
}
export const getTicket=(id:number)=>send<Ticket>("/api/tickets/"+id);
export const closeTicket=(id:number)=>send<Ticket>("/api/tickets/"+id,{method:"PATCH",body:JSON.stringify({status:"CLOSED"})});
export const createTicket=(values:{title:string;priority:Priority})=>send<Ticket>("/api/tickets",{method:"POST",body:JSON.stringify(values)});
export const deleteTicket=(id:number)=>send<void>("/api/tickets/"+id,{method:"DELETE"});`;
const loginPage=String.raw`import {useId,useState,type FormEvent} from "react";
import {useNavigate} from "react-router";
import {AuthError,login} from "./api";
export function LoginPage(){
  const id=useId(),navigate=useNavigate();
  const [error,setError]=useState("");
  async function submit(event:FormEvent<HTMLFormElement>){
    event.preventDefault();
    const data=new FormData(event.currentTarget);
    try{await login(String(data.get("login")),String(data.get("password")));navigate("/tickets",{replace:true});}
    catch(reason){setError(reason instanceof AuthError?"Błędny login lub hasło.":"Logowanie nie powiodło się.");}
  }
  return <main><h1>Logowanie</h1><form onSubmit={submit}>
    <label htmlFor={id+"-login"}>Login</label><input id={id+"-login"} name="login" autoComplete="username"/>
    <label htmlFor={id+"-password"}>Hasło</label><input id={id+"-password"} name="password" type="password" autoComplete="current-password"/>
    {error&&<p role="alert">{error}</p>}
    <button type="submit">Zaloguj</button>
  </form></main>;
}`;
const dialog=String.raw`import {useId,useRef,useState,type FormEvent} from "react";
import {createTicket,type Priority,type Ticket} from "./api";
export function NewTicketDialog({onCreated}:{onCreated:(ticket:Ticket)=>void}){
  const id=useId(),dialogRef=useRef<HTMLDialogElement>(null);
  const [error,setError]=useState(""),[busy,setBusy]=useState(false);
  async function submit(event:FormEvent<HTMLFormElement>){
    event.preventDefault();
    const form=event.currentTarget,data=new FormData(form),title=String(data.get("title")??"").trim();
    if(title.length<5){setError("Tytuł musi mieć co najmniej 5 znaków.");return;}
    setBusy(true);
    try{const ticket=await createTicket({title,priority:String(data.get("priority")) as Priority});form.reset();setError("");dialogRef.current?.close();onCreated(ticket);}
    catch{setError("Nie udało się utworzyć ticketu.");}
    finally{setBusy(false);}
  }
  return <>
    <button type="button" onClick={()=>{setError("");dialogRef.current?.showModal();}}>+ Nowy ticket</button>
    <dialog ref={dialogRef} aria-labelledby={id+"-heading"}>
      <h2 id={id+"-heading"}>Nowy ticket</h2>
      <form noValidate onSubmit={submit}>
        <label htmlFor={id+"-title"}>Tytuł</label><input id={id+"-title"} name="title" aria-invalid={!!error} aria-describedby={error?id+"-error":undefined}/>
        <label htmlFor={id+"-priority"}>Priorytet</label>
        <select id={id+"-priority"} name="priority" defaultValue="NORMAL"><option value="LOW">Niski</option><option value="NORMAL">Zwykły</option><option value="HIGH">Wysoki</option></select>
        {error&&<p id={id+"-error"} role="alert">{error}</p>}
        <button type="submit" disabled={busy}>Utwórz</button>
        <button type="button" onClick={()=>dialogRef.current?.close()}>Anuluj</button>
      </form>
    </dialog>
  </>;
}`;
const listPage=String.raw`import {useEffect,useRef,useState} from "react";
import {Link,useNavigate,useSearchParams} from "react-router";
import {AuthError,deleteTicket,listTickets,type Ticket} from "./api";
import {NewTicketDialog} from "./NewTicketDialog";
const labels={OPEN:"Otwarty",IN_PROGRESS:"W toku",CLOSED:"Zamknięty"} as const;
type Load={kind:"loading"}|{kind:"error"}|{kind:"ready";tickets:Ticket[]};
export function TicketsPage(){
  const navigate=useNavigate(),[params,setParams]=useSearchParams();
  const status=params.get("status")??"",q=params.get("q")??"";
  const [load,setLoad]=useState<Load>({kind:"loading"}),[attempt,setAttempt]=useState(0);
  const [created,setCreated]=useState<Ticket|null>(null),[hidden,setHidden]=useState<Ticket|null>(null);
  const pending=useRef<number|null>(null);
  useEffect(()=>{
    const controller=new AbortController();
    setLoad({kind:"loading"});
    listTickets({status,q},controller.signal).then(tickets=>setLoad({kind:"ready",tickets})).catch((reason:unknown)=>{
      if(controller.signal.aborted)return;
      if(reason instanceof AuthError){navigate("/login",{replace:true});return;}
      setLoad({kind:"error"});
    });
    return ()=>controller.abort();
  },[status,q,attempt,navigate]);
  useEffect(()=>()=>{if(pending.current!==null)window.clearTimeout(pending.current);},[]);
  function update(name:string,value:string){
    setParams(previous=>{const next=new URLSearchParams(previous);if(value)next.set(name,value);else next.delete(name);return next;},{replace:true});
  }
  function remove(ticket:Ticket){
    if(pending.current!==null)return;
    setHidden(ticket);
    pending.current=window.setTimeout(()=>{
      pending.current=null;
      void deleteTicket(ticket.id).then(()=>{setLoad(current=>current.kind==="ready"?{...current,tickets:current.tickets.filter(t=>t.id!==ticket.id)}:current);setHidden(null);});
    },5000);
  }
  function undo(){
    if(pending.current!==null)window.clearTimeout(pending.current);
    pending.current=null;setHidden(null);
  }
  return <main>
    <h1>Tickety</h1>
    <NewTicketDialog onCreated={ticket=>{setCreated(ticket);setAttempt(n=>n+1);}}/>
    {created&&<p role="status">Utworzono ticket #{created.id}.</p>}
    <form role="search" onSubmit={event=>event.preventDefault()}>
      <label htmlFor="filter-status">Status</label>
      <select id="filter-status" value={status} onChange={event=>update("status",event.currentTarget.value)}>
        <option value="">Wszystkie</option><option value="OPEN">Otwarte</option><option value="IN_PROGRESS">W toku</option><option value="CLOSED">Zamknięte</option>
      </select>
      <label htmlFor="filter-q">Szukaj</label>
      <input id="filter-q" type="search" value={q} onChange={event=>update("q",event.currentTarget.value)}/>
    </form>
    {hidden&&<div role="status" aria-label="Usuwanie">Usunięto „{hidden.title}”. <button type="button" onClick={undo}>Cofnij</button></div>}
    {load.kind==="loading"&&<p aria-busy="true">Ładowanie ticketów…</p>}
    {load.kind==="error"&&<div role="alert"><p>Nie udało się wczytać ticketów.</p><button type="button" onClick={()=>setAttempt(n=>n+1)}>Spróbuj ponownie</button></div>}
    {load.kind==="ready"&&<table><caption>Lista ticketów</caption>
      <thead><tr><th scope="col">Tytuł</th><th scope="col">Status</th><th scope="col">Akcje</th></tr></thead>
      <tbody>{load.tickets.filter(t=>t.id!==hidden?.id).map(ticket=><tr key={ticket.id}>
        <td><Link to={"/tickets/"+ticket.id}>{ticket.title}</Link></td><td>{labels[ticket.status]}</td>
        <td><button type="button" disabled={hidden!==null} onClick={()=>remove(ticket)}>Usuń</button></td>
      </tr>)}</tbody>
    </table>}
  </main>;
}`;
const detailsPage=String.raw`import {useEffect,useState} from "react";
import {Link,useNavigate,useParams} from "react-router";
import {AuthError,closeTicket,getTicket,type Ticket} from "./api";
const labels={OPEN:"Otwarty",IN_PROGRESS:"W toku",CLOSED:"Zamknięty"} as const;
export function TicketDetails(){
  const id=Number(useParams().id),navigate=useNavigate();
  const [ticket,setTicket]=useState<Ticket|null>(null),[error,setError]=useState("");
  useEffect(()=>{
    let active=true;
    getTicket(id).then(value=>{if(active)setTicket(value);}).catch((reason:unknown)=>{
      if(!active)return;
      if(reason instanceof AuthError)navigate("/login",{replace:true});else setError("Nie znaleziono ticketu.");
    });
    return ()=>{active=false;};
  },[id,navigate]);
  if(error)return <main><p role="alert">{error}</p><Link to="/tickets">Wróć do listy</Link></main>;
  if(!ticket)return <main><p aria-busy="true">Ładowanie…</p></main>;
  return <main>
    <h1>#{ticket.id} {ticket.title}</h1>
    <p>Status: {labels[ticket.status]}</p>
    {ticket.status!=="CLOSED"&&<button type="button" onClick={()=>void closeTicket(ticket.id).then(setTicket,()=>setError("Nie udało się zamknąć ticketu."))}>Zamknij</button>}
    <Link to="/tickets">Wróć do listy</Link>
  </main>;
}`;
const app=String.raw`import {useEffect,useState} from "react";
import {Navigate,Outlet,createBrowserRouter,useNavigate} from "react-router";
import {AuthError,logout,me} from "./api";
import {LoginPage} from "./LoginPage";
import {TicketDetails} from "./TicketDetails";
import {TicketsPage} from "./TicketsPage";
function Layout(){
  const navigate=useNavigate(),[user,setUser]=useState<string|null>(null);
  useEffect(()=>{me().then(value=>setUser(value.login),(reason:unknown)=>{if(reason instanceof AuthError)navigate("/login",{replace:true});});},[navigate]);
  return <>
    <header>{user&&<button type="button" onClick={()=>void logout().then(()=>navigate("/login",{replace:true}))}>Wyloguj ({user})</button>}</header>
    <Outlet/>
  </>;
}
export const router=createBrowserRouter([
  {path:"/login",element:<LoginPage/>},
  {element:<Layout/>,children:[
    {path:"/",element:<Navigate to="/tickets" replace/>},
    {path:"/tickets",element:<TicketsPage/>},
    {path:"/tickets/:id",element:<TicketDetails/>},
  ]},
]);`;
const main=String.raw`import {StrictMode} from "react";
import {createRoot} from "react-dom/client";
import {RouterProvider} from "react-router";
import {router} from "./App";
createRoot(document.getElementById("root")!).render(<StrictMode><RouterProvider router={router}/></StrictMode>);`;
const config=String.raw`import {defineConfig,devices} from "@playwright/test";
const port=Number(process.env.PORT??4317),baseURL="http://127.0.0.1:"+port;
export default defineConfig({
  testDir:"./e2e",
  // Jedna instancja serwera ma wspólne dane, więc testy biegną po kolei i każdy resetuje bazę.
  fullyParallel:false,
  workers:1,
  retries:process.env.CI?2:0,
  reporter:[["list"],["json",{outputFile:"e2e-results.json"}]],
  use:{baseURL,trace:"retain-on-failure"},
  webServer:{command:"node server.mjs",url:baseURL+"/health",reuseExistingServer:!process.env.CI,env:{PORT:String(port),E2E:"1"}},
  projects:[
    {name:"setup",testMatch:/.*\.setup\.ts/},
    {name:"chromium",use:{...devices["Desktop Chrome"],storageState:"playwright/.auth/agent.json"},dependencies:["setup"]},
  ],
});`;
const authSetup=String.raw`import {expect,test as setup} from "@playwright/test";
const authFile="playwright/.auth/agent.json";
setup("logowanie agenta",async({page})=>{
  await page.goto("/login");
  await page.getByLabel("Login").fill("anna");
  await page.getByLabel("Hasło").fill(process.env.E2E_PASSWORD??"test-haslo");
  await page.getByRole("button",{name:"Zaloguj"}).click();
  await expect(page.getByRole("button",{name:/Wyloguj/})).toBeVisible();
  await page.context().storageState({path:authFile});
});`;
const fixtures=String.raw`import {expect,test as base} from "@playwright/test";
// Automatyczna fikstura: każdy test zaczyna od tych samych danych na serwerze.
export const test=base.extend<{resetData:void}>({
  resetData:[async({request},use)=>{
    const response=await request.post("/api/test/reset");
    expect(response.status()).toBe(204);
    await use();
  },{auto:true}],
});
export {expect};`;
const createSpec=String.raw`import {expect,test} from "./fixtures";
test("użytkownik tworzy ticket i widzi go na liście oraz w szczegółach",async({page})=>{
  await page.goto("/tickets");
  await page.getByRole("button",{name:"+ Nowy ticket"}).click();
  const dialog=page.getByRole("dialog",{name:"Nowy ticket"});
  await dialog.getByLabel("Tytuł").fill("Eksport CSV ucina polskie znaki");
  await dialog.getByLabel("Priorytet").selectOption("HIGH");
  await dialog.getByRole("button",{name:"Utwórz"}).click();
  await expect(dialog).toBeHidden();
  await expect(page.getByRole("status")).toContainText(/Utworzono ticket #\d+/);
  await page.getByRole("link",{name:"Eksport CSV ucina polskie znaki"}).click();
  await expect(page).toHaveURL(/\/tickets\/\d+$/);
  await expect(page.getByRole("heading",{level:1})).toContainText("Eksport CSV ucina polskie znaki");
});
test("za krótki tytuł zostawia otwarty dialog z komunikatem przy polu",async({page})=>{
  await page.goto("/tickets");
  await page.getByRole("button",{name:"+ Nowy ticket"}).click();
  const dialog=page.getByRole("dialog",{name:"Nowy ticket"});
  await dialog.getByLabel("Tytuł").fill("abc");
  await dialog.getByRole("button",{name:"Utwórz"}).click();
  await expect(dialog).toBeVisible();
  await expect(dialog.getByLabel("Tytuł")).toHaveAccessibleDescription("Tytuł musi mieć co najmniej 5 znaków.");
});`;
const filterSpec=String.raw`import {expect,test} from "./fixtures";
test("filtr i wyszukiwanie są w adresie i przetrwają odświeżenie",async({page})=>{
  await page.goto("/tickets");
  await page.getByLabel("Status").selectOption("OPEN");
  await page.getByLabel("Szukaj").fill("api");
  await expect(page).toHaveURL(/status=OPEN/);
  await expect(page).toHaveURL(/q=api/);
  const rows=page.getByRole("row").filter({has:page.getByRole("link")});
  await expect(rows).toHaveCount(1);
  await page.reload();
  await expect(page.getByLabel("Status")).toHaveValue("OPEN");
  await expect(page.getByLabel("Szukaj")).toHaveValue("api");
  await expect(rows).toHaveCount(1);
  await expect(rows.first()).toContainText("API timeout");
});
test("wejście z gotowym adresem pokazuje przefiltrowaną listę",async({page})=>{
  await page.goto("/tickets?status=CLOSED");
  await expect(page.getByRole("link",{name:"Dokumentacja"})).toBeVisible();
  await expect(page.getByRole("link",{name:"API timeout"})).toHaveCount(0);
});`;
const closeSpec=String.raw`import {expect,test} from "./fixtures";
test("zamknięcie z karty szczegółów jest trwałe po odświeżeniu",async({page})=>{
  await page.goto("/tickets");
  await page.getByRole("link",{name:"API timeout"}).click();
  await expect(page).toHaveURL(/\/tickets\/42$/);
  await page.getByRole("button",{name:"Zamknij"}).click();
  await expect(page.getByText("Status: Zamknięty")).toBeVisible();
  await page.reload();
  await expect(page.getByText("Status: Zamknięty")).toBeVisible();
  await expect(page.getByRole("button",{name:"Zamknij"})).toHaveCount(0);
});`;
const undoSpec=String.raw`import {expect,test} from "./fixtures";
test("Cofnij przywraca wiersz i nie wysyła DELETE",async({page})=>{
  const deletes:string[]=[];
  page.on("request",request=>{if(request.method()==="DELETE")deletes.push(request.url());});
  await page.clock.install();
  await page.goto("/tickets");
  const row=page.getByRole("row",{name:/Dokumentacja/});
  await row.getByRole("button",{name:"Usuń"}).click();
  await expect(row).toBeHidden();
  await page.getByRole("button",{name:"Cofnij"}).click();
  await expect(row).toBeVisible();
  await page.clock.runFor(6000);
  expect(deletes).toEqual([]);
  await page.reload();
  await expect(page.getByRole("link",{name:"Dokumentacja"})).toBeVisible();
});
test("bez cofnięcia ticket znika po 5 sekundach także po odświeżeniu",async({page})=>{
  await page.clock.install();
  await page.goto("/tickets");
  await page.getByRole("row",{name:/Dokumentacja/}).getByRole("button",{name:"Usuń"}).click();
  const deleted=page.waitForResponse(response=>response.request().method()==="DELETE");
  await page.clock.runFor(5000);
  expect((await deleted).status()).toBe(204);
  await page.reload();
  await expect(page.getByRole("link",{name:"Dokumentacja"})).toHaveCount(0);
});`;
const errorSpec=String.raw`import {expect,test} from "./fixtures";
const ticketList=(url:URL)=>url.pathname==="/api/tickets";
test("błąd 500 pokazuje kartę błędu, a ponowienie ładuje dane",async({page})=>{
  await page.route(ticketList,route=>route.fulfill({status:500,json:{message:"Awaria"}}));
  await page.goto("/tickets");
  await expect(page.getByRole("alert")).toContainText("Nie udało się wczytać ticketów.");
  await page.unroute(ticketList);
  await page.getByRole("button",{name:"Spróbuj ponownie"}).click();
  await expect(page.getByRole("link",{name:"API timeout"})).toBeVisible();
  await expect(page.getByRole("alert")).toHaveCount(0);
});
test("route może podejrzeć żądanie i przepuścić je do serwera",async({page})=>{
  const seen:string[]=[];
  await page.route(ticketList,async route=>{seen.push(new URL(route.request().url()).search);await route.continue();});
  await page.goto("/tickets?status=IN_PROGRESS");
  await expect(page.getByRole("link",{name:"Logowanie SSO"})).toBeVisible();
  expect(seen).toContain("?status=IN_PROGRESS");
});`;
const authSpec=String.raw`import {expect,test} from "./fixtures";
test.describe("bez zapisanej sesji",()=>{
  test.use({storageState:{cookies:[],origins:[]}});
  test("chroniona lista przekierowuje do logowania",async({page})=>{
    await page.goto("/tickets");
    await expect(page).toHaveURL(/\/login$/);
    await expect(page.getByRole("heading",{name:"Logowanie"})).toBeVisible();
  });
  test("błędne hasło pokazuje komunikat",async({page})=>{
    await page.goto("/login");
    await page.getByLabel("Login").fill("anna");
    await page.getByLabel("Hasło").fill("zle-haslo");
    await page.getByRole("button",{name:"Zaloguj"}).click();
    await expect(page.getByRole("alert")).toHaveText("Błędny login lub hasło.");
  });
  test("wylogowanie własnej sesji nie unieważnia sesji z projektu setup",async({page})=>{
    await page.goto("/login");
    await page.getByLabel("Login").fill("anna");
    await page.getByLabel("Hasło").fill("test-haslo");
    await page.getByRole("button",{name:"Zaloguj"}).click();
    await page.getByRole("button",{name:"Wyloguj (anna)"}).click();
    await expect(page).toHaveURL(/\/login$/);
    await page.goto("/tickets");
    await expect(page).toHaveURL(/\/login$/);
  });
});
test("zapisana sesja otwiera listę bez logowania",async({page})=>{
  await page.goto("/tickets");
  await expect(page.getByRole("button",{name:"Wyloguj (anna)"})).toBeVisible();
  await expect(page.getByRole("heading",{name:"Tickety"})).toBeVisible();
});`;
const firefoxConfig=String.raw`import {defineConfig,devices} from "@playwright/test";
export default defineConfig({
  testDir:"./e2e",
  use:{baseURL:"http://127.0.0.1:4317",trace:"on-first-retry"},
  retries:process.env.CI?2:0,
  webServer:{command:"node server.mjs",url:"http://127.0.0.1:4317/health",reuseExistingServer:!process.env.CI},
  projects:[
    {name:"setup",testMatch:/.*\.setup\.ts/},
    {name:"chromium",use:{...devices["Desktop Chrome"],storageState:"playwright/.auth/agent.json"},dependencies:["setup"]},
    {name:"firefox",use:{...devices["Desktop Firefox"],storageState:"playwright/.auth/agent.json"},dependencies:["setup"]},
  ],
});`;
const detailRouteSpec=String.raw`import {expect,test} from "./fixtures";
test("nieznany ticket pokazuje komunikat i link powrotu",async({page})=>{
  await page.route(url=>url.pathname==="/api/tickets/999",route=>route.fulfill({status:404,json:{message:"Brak"}}));
  await page.goto("/tickets/999");
  await expect(page.getByRole("alert")).toHaveText("Nie znaleziono ticketu.");
  await page.getByRole("link",{name:"Wróć do listy"}).click();
  await expect(page).toHaveURL(/\/tickets$/);
});`;
const exercises=[
  ['Skonfiguruj Playwright: katalog e2e, baseURL, webServer uruchamiający aplikację i trace przy błędzie.','webServer czeka na adres url, zanim wystartują testy.',config],
  ['Dodaj projekt setup z logowaniem i zapisem storageState oraz zależność projektu chromium.','Projekt chromium wskazuje plik sesji i dependencies: ["setup"].',config],
  ['Napisz test logowania w projekcie setup, który zapisuje stan przeglądarki do pliku.','Zanim zapiszesz stan, poczekaj na widoczny dowód zalogowania.',authSetup],
  ['Utwórz automatyczną fiksturę resetującą dane serwera przed każdym testem.','Fikstura {auto:true} działa bez jawnego użycia w teście.',fixtures],
  ['Napisz ścieżkę: utworzenie ticketu w dialogu, komunikat, przejście do szczegółów.','Zawęź lokatory do dialogu, a URL sprawdź przez toHaveURL.',createSpec],
  ['Sprawdź walidację w dialogu: za krótki tytuł zostawia dialog otwarty z opisem błędu pola.','toHaveAccessibleDescription potwierdza powiązanie komunikatu z polem.',createSpec],
  ['Przetestuj filtr i wyszukiwanie zapisane w adresie, także po odświeżeniu strony.','Po reload sprawdź wartości kontrolek i liczbę wierszy.',filterSpec],
  ['Wejdź bezpośrednio na adres z filtrem i sprawdź przefiltrowaną listę.','toHaveCount(0) to asercja nieobecności z ponawianiem.',filterSpec],
  ['Odfiltruj wiersze danych od wiersza nagłówka lokatorem filter({has}).','Wiersz danych zawiera link, nagłówek — nie.',filterSpec],
  ['Zamknij ticket z karty szczegółów i potwierdź trwałość zmiany po odświeżeniu.','Odświeżenie pokazuje stan serwera, nie tylko stan Reacta.',closeSpec],
  ['Przetestuj Cofnij przy usuwaniu: wiersz wraca, a DELETE nie zostaje wysłany.','page.clock.install przed goto pozwala przesuwać czas strony.',undoSpec],
  ['Przetestuj usunięcie bez cofnięcia: po 5 s strona wysyła DELETE i wiersz nie wraca po reload.','Zacznij czekać na odpowiedź, zanim przesuniesz zegar.',undoSpec],
  ['Wymuś błąd 500 przez page.route i sprawdź ponowienie po unroute.','Ta sama funkcja dopasowania musi trafić do route i unroute.',errorSpec],
  ['Podejrzyj żądanie w page.route i przepuść je do prawdziwego serwera.','route.continue() wysyła żądanie dalej bez zmian.',errorSpec],
  ['Wyłącz zapisaną sesję w grupie testów i sprawdź przekierowanie do logowania.','test.use({storageState:{cookies:[],origins:[]}}) dotyczy całego describe.',authSpec],
  ['Sprawdź komunikat przy błędnym haśle.','toHaveText porównuje cały tekst elementu.',authSpec],
  ['Przetestuj wylogowanie na własnej sesji tak, by nie unieważnić sesji z setup.','Wylogowanie wspólnej sesji zepsułoby kolejne testy.',authSpec],
  ['Sprawdź, że zapisana sesja otwiera listę bez logowania.','To test samej konfiguracji storageState.',authSpec],
  ['Wymuś 404 dla szczegółów przez page.route i sprawdź link powrotu.','Funkcja dopasowania dostaje obiekt URL.',detailRouteSpec],
  ['Dodaj projekt Firefox korzystający z tej samej sesji.','Każdy projekt przeglądarki zależy od setup.',firefoxConfig],
  ['Napisz serwer testowy z sesją w ciasteczku HttpOnly i endpointem resetu dostępnym tylko w E2E.','Reset włączaj zmienną środowiskową ustawianą przez webServer.',server],
  ['Napisz klienta API rozróżniającego 401 od innych błędów.','AuthError pozwala ekranom przekierować do logowania.',apiClient],
  ['Zbuduj stronę logowania z etykietami pól i komunikatem w roli alert.','Lokatory getByLabel wymagają powiązanych etykiet.',loginPage],
  ['Zbuduj dialog nowego ticketu na natywnym <dialog> z nazwą z nagłówka.','aria-labelledby daje dialogowi nazwę dostępności.',dialog],
  ['Zbuduj listę z filtrami w adresie, kartą błędu i usuwaniem z cofnięciem.','setParams z replace:true nie zaśmieca historii przy każdym znaku.',listPage],
  ['Zbuduj kartę szczegółów z nagłówkiem h1 i przyciskiem Zamknij.','Nagłówek poziomu 1 jest stabilnym punktem asercji.',detailsPage],
  ['Połącz trasy w routerze z układem, który przekierowuje bez sesji.','Layout pobiera /api/me i reaguje na AuthError.',app],
  ['Podłącz RouterProvider w punkcie wejścia aplikacji.','StrictMode nie zmienia zachowania testów E2E.',main],
  ['Użyj trace: "retain-on-failure", by po nieudanym teście mieć plik trace.zip.','Ślad pozostaje tylko dla testów, które nie przeszły.',config],
  ['Uruchamiaj testy po kolei, gdy serwer ma wspólny stan, i opisz to w konfiguracji.','workers:1 i fullyParallel:false zapobiegają wyścigom danych.',config],
];
const quiz=[];function q(...row){quiz.push(row);}
q('Czego nie zobaczą testy komponentów w jsdom, a zobaczy Playwright?','Prawdziwego CSS, nawigacji, ciasteczek i zachowania przeglądarki','Wyniku renderu komponentu','Ról ARIA','Tekstu w DOM','jsdom nie liczy układu ani nie wykonuje prawdziwej nawigacji i sieci przeglądarki.');
q('Co przekazuje Playwright do testu w obiekcie page?','Kartę przeglądarki sterowaną z testu','Komponent React','Obiekt Request','Kopię DOM z jsdom','page to strona w prawdziwej przeglądarce (Chromium, Firefox, WebKit).');
q('Względem czego działa page.goto("/tickets")?','Względem baseURL z konfiguracji','Względem katalogu testów','Względem dysku','Względem adresu ostatniej strony','Ścieżka względna łączy się z use.baseURL.');
q('Kiedy lokator szuka elementu?','Dopiero przy akcji lub asercji','W chwili utworzenia','Tylko raz na test','Przy starcie przeglądarki','Lokator jest leniwy; za każdym razem szuka od nowa, więc nie „starzeje się”.');
q('Na co czeka auto-waiting przed kliknięciem?','Aż element będzie widoczny, aktywny, stabilny i nie zasłonięty','Na stały czas 2 sekund','Na zakończenie wszystkich żądań','Na zdarzenie load','Dlatego ręczne pauzy są zbędne.');
q('Jak długo domyślnie ponawiana jest asercja expect(lokator).toBeVisible()?','Do 5 sekund','Do 1 sekundy','Bez limitu','Nie jest ponawiana','Domyślny timeout asercji Playwright to 5 s.');
q('Co jest złego w page.waitForTimeout(2000) przed asercją?','Test zależy od szybkości maszyny i bywa niestabilny lub wolny','Nic, to zalecana praktyka','Wyłącza auto-waiting na stałe','Rzuca błąd kompilacji','Warunek zamiast czasu: asercje z ponawianiem.');
q('Dlaczego lokator po roli jest lepszy niż #root > div > div:nth-child(3) button?','Przetrwa zmiany układu i sprawdza dostępność','Jest szybszy w CSS','Działa bez przeglądarki','Nie wymaga nazw','Selektor strukturalny pęka przy każdej zmianie DOM.');
q('Co zrobić, gdy elementu nie da się znaleźć po roli?','Poprawić dostępność elementu','Przejść na XPath','Dodać waitForTimeout','Pominąć test','Trudność w znalezieniu po roli to sygnał problemu dla czytników ekranu.');
q('Do czego służy webServer w konfiguracji?','Uruchamia aplikację przed testami i czeka na jej adres','Instaluje przeglądarki','Generuje raport HTML','Uruchamia MSW','Playwright startuje polecenie i czeka na url.');
q('Co robi reuseExistingServer:!process.env.CI?','Lokalnie używa już działającego serwera, w CI uruchamia nowy','Zawsze uruchamia dwa serwery','Wyłącza serwer w CI','Restartuje serwer co test','W CI środowisko ma być czyste i powtarzalne.');
q('Co daje trace: "retain-on-failure"?','Zachowuje ślad tylko dla testów, które nie przeszły','Nagrywa wideo każdego testu','Zatrzymuje test przy błędzie','Wyłącza zrzuty DOM','Ślad zawiera kroki, DOM, sieć i konsolę.');
q('Co daje trace: "on-first-retry"?','Ślad przy pierwszym ponowieniu nieudanego testu','Ślad każdego testu','Brak śladów','Ślad tylko w Firefox','Tani w normalnym przebiegu, a dostępny przy problemie w CI.');
q('Co pokazuje Trace Viewer dla kroku testu?','Zrzut DOM przed i po, sieć i logi konsoli','Tylko nazwę testu','Kod źródłowy Reacta','Wykres pokrycia','Najlepsze narzędzie do analizy błędu z CI.');
q('Do czego służy npx playwright test --ui?','Tryb interaktywny z osią czasu kroków i podglądem','Instalacja przeglądarek','Generowanie konfiguracji','Uruchomienie serwera API','Pozwala uruchamiać pojedyncze testy i oglądać kroki.');
q('Co robi npx playwright codegen?','Nagrywa kliknięcia jako kod testu','Kompiluje TypeScript','Uruchamia testy w CI','Tworzy zrzuty wizualne','Wynik warto uporządkować do lokatorów po rolach.');
q('Po co projekt setup z logowaniem?','Logowanie odbywa się raz, a stan sesji trafia do pliku','Żeby każdy test logował się przez formularz','Żeby uruchomić serwer','Żeby wyłączyć ciasteczka','Pozostałe projekty wczytują storageState.');
q('Co zawiera storageState?','Ciasteczka i localStorage kontekstu przeglądarki','Kod testów','Zrzut ekranu','Historię nawigacji','To stan przeglądarki potrzebny do odtworzenia sesji.');
q('Dlaczego plik playwright/.auth trafia do .gitignore?','Zawiera dane sesji, które nie powinny trafić do repozytorium','Bo jest zbyt duży','Bo Playwright go nie czyta','Bo zawiera kod źródłowy','To odpowiednik sekretu krótkoterminowego.');
q('Jak projekt chromium zależy od setup?','dependencies: ["setup"]','requires: "setup"','before: "setup"','globalSetup w teście','Zależność wymusza kolejność projektów.');
q('Jak wyłączyć zapisaną sesję w grupie testów?','test.use({storageState:{cookies:[],origins:[]}})','test.skip(storageState)','page.clearSession()','context.logout()','Pusty stan oznacza nowego, niezalogowanego użytkownika.');
q('Dlaczego test wylogowania loguje się sam zamiast używać sesji z setup?','Wylogowanie unieważniłoby wspólną sesję dla kolejnych testów','Bo storageState nie działa z wylogowaniem','Bo setup jest wolniejszy','Bo Playwright zabrania wylogowania','Test nie może psuć warunków startowych innych testów.');
q('Po co fikstura resetująca dane serwera?','Każdy test startuje z tych samych danych','Żeby przyspieszyć logowanie','Żeby wyłączyć trace','Żeby zmienić baseURL','Bez resetu testy zależą od kolejności.');
q('Co oznacza {auto:true} przy fiksturze?','Fikstura działa w każdym teście bez jawnego użycia','Fikstura jest asynchroniczna','Fikstura działa tylko w CI','Fikstura zastępuje page','Auto-fikstury nadają się do sprzątania i przygotowania danych.');
q('Dlaczego w lekcji workers:1?','Wspólny stan serwera wywołałby wyścigi między równoległymi testami','Bo Playwright nie obsługuje równoległości','Bo Chromium ma jeden proces','Bo trace wymaga jednego workera','Alternatywą są dane izolowane per test.');
q('Jak zachować równoległość przy wspólnym backendzie?','Izolować dane: unikalne rekordy lub osobne konto per worker','Dodać waitForTimeout','Wyłączyć asercje','Użyć jednego testu','Izolacja danych jest warunkiem bezpiecznej równoległości.');
q('Dlaczego endpoint resetu działa tylko z E2E=1?','Nie może być dostępny w produkcji','Bo Node wymaga flagi','Bo inaczej Vite go usunie','Bo Playwright go wymaga','Endpoint testowy to ryzyko bezpieczeństwa poza środowiskiem testowym.');
q('Co sprawdza toHaveURL(/\\/tickets\\/\\d+$/)?','Że nawigacja trafiła na adres szczegółów z liczbowym ID','Że serwer zwrócił 200','Że link istnieje','Że tytuł jest w nagłówku','Asercja z ponawianiem czeka na zmianę adresu.');
q('Dlaczego test filtrów odświeża stronę?','Udowadnia, że stan jest w adresie, a nie tylko w pamięci komponentu','Bo Playwright wymaga reload','Żeby wyczyścić cache','Żeby wylogować użytkownika','Po reload React startuje od zera.');
q('Co robi page.route(matcher, handler)?','Przechwytuje żądania strony pasujące do dopasowania','Zmienia trasę React Routera','Dodaje trasę na serwerze','Przekierowuje stronę','Pozwala wymusić odpowiedź lub ją podejrzeć.');
q('Czym różni się route.fulfill od route.continue?','fulfill odpowiada od razu, continue przepuszcza żądanie do serwera','Niczym','continue zwraca 500','fulfill przepuszcza żądanie dalej','fulfill wymusza scenariusz, continue pozwala tylko obserwować lub zmienić żądanie.');
q('Dlaczego route i unroute dostają tę samą funkcję dopasowania?','unroute usuwa konkretny handler po tym samym dopasowaniu','Bo inaczej test się nie skompiluje','Bo route działa tylko raz','Bo unroute czyści wszystkie trasy','Ten sam matcher jednoznacznie wskazuje przechwycenie do zdjęcia.');
q('Czym page.route różni się od MSW w testach komponentów?','Działa na poziomie prawdziwej przeglądarki w teście E2E','Jest szybszy w jsdom','Wymaga Service Workera','Działa tylko w Node','To narzędzie Playwright, niezależne od kodu aplikacji.');
q('Co robi page.clock.install()?','Przejmuje zegar strony, by test mógł przesuwać czas','Instaluje przeglądarkę','Ustawia strefę czasową serwera','Wyłącza timery na stałe','Zegar trzeba zainstalować przed załadowaniem kodu strony.');
q('Dlaczego test usuwania używa page.clock.runFor(5000) zamiast czekać 5 sekund?','Test jest szybki i deterministyczny','Bo setTimeout nie działa w Chromium','Bo serwer wymaga zegara','Bo trace nie zapisuje czasu','Przesunięcie zegara uruchamia timery strony od razu.');
q('Dlaczego waitForResponse wywołuje się przed runFor?','Żeby nie przegapić odpowiedzi, która może przyjść natychmiast','Bo inaczej runFor nie działa','Bo DELETE wymaga oczekiwania','Bo test jest w Firefox','Najpierw subskrypcja, potem akcja wywołująca zdarzenie.');
q('Co sprawdza lista deletes w teście Cofnij?','Że po cofnięciu nie wysłano żądania DELETE','Że wiersz ma przycisk','Że tabela ma nagłówek','Że zegar działa','Widok i sieć to dwie strony tego samego zachowania.');
q('Jaki błąd wykrywa test Cofnij po reload?','Timer usunięcia nie został anulowany i serwer skasował rekord','Brak etykiety Szukaj','Zły baseURL','Błąd typu w teście','Tylko reload pokazuje stan serwera.');
q('Do czego służy lokator filter({has: ...})?','Zawęża zbiór elementów do tych, które zawierają wskazany element','Zmienia kolejność wierszy','Sortuje tabelę','Ukrywa elementy','W lekcji odróżnia wiersze danych od nagłówka.');
q('Co robi toHaveCount(0)?','Czeka, aż pasujących elementów nie będzie','Sprawdza raz bez ponawiania','Rzuca, gdy element nie istnieje','Liczy wszystkie elementy strony','Asercja nieobecności z ponawianiem.');
q('Dlaczego dialog szukany jest po roli i nazwie?','Natywny dialog z aria-labelledby ma rolę dialog i nazwę z nagłówka','Bo dialog ma ID w CSS','Bo Playwright nie widzi nagłówków','Bo nazwa jest wymagana w HTML','Nazwa dostępności jest stabilnym punktem odniesienia.');
q('Co sprawdza await expect(dialog).toBeHidden()?','Że dialog został zamknięty po udanym zapisie','Że dialog nie istnieje w kodzie','Że CSS ukrywa przycisk','Że trace się zapisał','Zamknięty <dialog> nie jest widoczny.');
q('Dlaczego dialog.getByLabel("Tytuł") zamiast page.getByLabel("Tytuł")?','Zawęża wyszukiwanie do dialogu i unika niejednoznaczności','Bo page nie widzi etykiet','Bo dialog jest w iframe','Bo getByLabel działa tylko w dialogach','Lokatory można łańcuchować.');
q('Co zrobi selectOption("HIGH") przy <option value="HIGH">Wysoki</option>?','Wybierze opcję po wartości','Rzuci błąd, bo etykieta to Wysoki','Wybierze pierwszą opcję','Wpisze tekst HIGH','selectOption dopasowuje wartość lub etykietę.');
q('Czym fill różni się od pressSequentially?','fill ustawia całą wartość naraz, pressSequentially wpisuje znak po znaku','Niczym','fill nie wywołuje zdarzeń','pressSequentially czyści pole','fill wystarcza w większości formularzy.');
q('Dlaczego testy E2E mogą być w TS przy aplikacji w JSX?','Sterują przeglądarką z zewnątrz i nie importują komponentów','Bo TS kompiluje JSX','Bo Playwright nie obsługuje JS','Bo aplikacja musi być w TS','Ważne są role i nazwy elementów, nie język aplikacji.');
q('Ile testów E2E warto mieć dla kluczowych ścieżek?','Kilka najważniejszych przepływów; resztę pokrywają testy komponentów','Po jednym na każdy komponent','Tyle, ile testów jednostkowych','Żadnych, wystarczą snapshoty','E2E są wolne i droższe w utrzymaniu.');
q('Co daje retries:2 w CI?','Ponowienie testów niestabilnych, z śladem przy ponowieniu','Ukrycie błędów na zawsze','Dwukrotne uruchomienie wszystkich testów','Wyłączenie trace','Ponowienia nie naprawiają testu; flaky test trzeba zdiagnozować.');
q('Dlaczego ciasteczko sesji jest HttpOnly?','JavaScript strony nie może go odczytać','Żeby Playwright go nie widział','Żeby działało tylko w HTTP','Żeby wygasało po minucie','storageState i tak je zapisze, bo czyta je z kontekstu przeglądarki.');
q('Czy storageState zapisze ciasteczko HttpOnly?','Tak, bo czyta stan kontekstu przeglądarki','Nie, HttpOnly jest niewidoczne','Tylko w Firefox','Tylko z flagą --unsafe','To stan przeglądarki, nie document.cookie.');
q('Dlaczego test setup kończy się asercją widoczności przycisku Wyloguj?','Zapisanie stanu przed zalogowaniem dałoby pustą sesję','Bo storageState wymaga przycisku','Żeby zmienić baseURL','Bo test bez asercji się nie uruchomi','Najpierw dowód zalogowania, potem zapis.');
q('Gdzie trzymać hasło do testowego konta w CI?','W zmiennej środowiskowej lub sekrecie CI','W kodzie testu','W pliku storageState','W README','Domyślna wartość jest tylko dla lokalnego środowiska.');
q('Dlaczego serwer testowy w lekcji serwuje dist z fallbackiem do index.html?','Bezpośrednie wejście i reload na /tickets/42 muszą zwrócić aplikację','Bo Vite tego wymaga','Żeby ukryć błędy 404 API','Żeby przyspieszyć build','To ten sam problem co konfiguracja serwera po wdrożeniu SPA.');
q('Jakie zachowanie potwierdza test „zapisana sesja otwiera listę”?','Że konfiguracja storageState rzeczywiście loguje użytkownika','Że serwer resetuje dane','Że lista ma filtr','Że trace działa','Test chroni samą infrastrukturę testów.');
q('Co jest dowodem trwałości zamknięcia ticketu?','Status Zamknięty widoczny po reload','Kliknięcie przycisku bez błędu','Zmiana koloru przycisku','Brak logów w konsoli','Po reload dane pochodzą z serwera.');
q('Co wykrywa mutant, który nie zamyka dialogu po zapisie?','Asercja toBeHidden dla dialogu','Asercja URL','Test błędnego hasła','Test resetu','Kolejne kroki ścieżki opierają się na zamkniętym dialogu.');
q('Co wykrywa mutant klienta, który nie zamienia 401 na AuthError?','Test przekierowania do logowania bez sesji','Test filtrów','Test Cofnij','Test 500','Bez rozróżnienia 401 ekran pokazuje ogólny błąd zamiast przekierować do logowania.');
q('Co wykrywa mutant czytający q z pustej wartości zamiast z adresu?','Test filtrów po odświeżeniu','Test logowania','Test 404','Test usunięcia','Po reload wyszukiwanie musi wrócić z URL.');
q('Czy Playwright zastępuje testy komponentów?','Nie, uzupełnia je o kilka pełnych ścieżek','Tak, zawsze','Tylko w TS','Tylko w CI','Testy komponentów są szybsze i precyzyjniejsze dla przypadków brzegowych.');
q('Dlaczego Playwright sprawdza, czy element nie jest zasłonięty?','Kliknięcie musi trafić w element, jak u użytkownika','Bo CSS jest ignorowany','Żeby przyspieszyć test','Bo jsdom tego wymaga','To jedna z różnic względem testów w jsdom.');
q('Co się stanie, gdy getByRole("status") pasuje do dwóch elementów i wykonasz akcję?','Błąd trybu strict: lokator jest niejednoznaczny','Playwright kliknie pierwszy','Playwright kliknie oba','Test zostanie pominięty','Doprecyzuj lokator nazwą, filtrem albo zakresem.');
q('Jak doprecyzować niejednoznaczny lokator?','Dodać nazwę, filter({hasText}) albo zawęzić do kontenera','Użyć nth-child','Dodać waitForTimeout','Wyłączyć strict globalnie','Precyzyjny lokator opisuje intencję testu.');
q('Kiedy wybrać prawdziwy backend zamiast MSW w E2E?','Gdy chcesz sprawdzić integrację z API, sesją i bazą','Gdy test ma być najszybszy','Gdy backend nie istnieje','Gdy testujesz pojedynczy komponent','Koszt: przygotowanie danych i wolniejsze testy.');
q('Jaką wadę ma MSW w przeglądarce w testach E2E?','Nie sprawdza prawdziwego backendu','Nie działa w Chromium','Wymaga Javy','Blokuje nawigację','Szybkość i powtarzalność kosztem zakresu integracji.');
q('Co robi expect.poll w Playwright?','Ponawia funkcję zwracającą wartość aż asercja przejdzie','Wysyła żądanie co sekundę do serwera','Instaluje zegar','Zastępuje page.route','Przydatne dla wartości spoza DOM, np. z API.');
q('Dlaczego asercje na lokatorach są asynchroniczne (await expect)?','Ponawiają sprawdzanie w czasie','Bo Playwright nie ma synchronicznych asercji','Bo TS tego wymaga','Bo sprawdzają serwer','Bez await asercja może nie zostać wykonana przed końcem testu.');
q('Czym różni się expect(locator).toBeVisible() od expect(await locator.isVisible()).toBe(true)?','Pierwsza ponawia sprawdzanie, druga sprawdza raz','Niczym','Druga ponawia dłużej','Pierwsza działa tylko w Firefox','Jednorazowe sprawdzenie prowadzi do niestabilnych testów.');
q('Co oznacza, że test „czasem przechodzi”?','Zależy od czasu, kolejności lub wspólnych danych','Że Playwright ma błąd','Że trzeba zwiększyć retries','Że test jest dobry','Szukaj przyczyny w trace zamiast maskować ją ponowieniami.');
q('Po co reporter JSON w konfiguracji?','Wynik testów może być przetworzony automatycznie (np. w CI)','Żeby wyłączyć listę','Żeby zapisać trace','Żeby zmienić baseURL','W lekcji weryfikator czyta wynik z pliku JSON.');
q('Jak uruchomić tylko projekt chromium?','npx playwright test --project=chromium','npx playwright chromium','npm test chromium','npx playwright --browser','Zależny projekt setup uruchomi się automatycznie.');
q('Dlaczego test błędu 500 sprawdza na końcu toHaveCount(0) dla alertu?','Po udanym ponowieniu karta błędu ma zniknąć','Bo alert zawsze zostaje','Żeby sprawdzić CSS','Bo route tego wymaga','Sukces musi zastąpić stan błędu.');
q('Jakie przeglądarki obsługuje Playwright?','Chromium, Firefox i WebKit','Tylko Chrome','Tylko Chromium i Edge','Dowolną przez Selenium','Projekty w konfiguracji pozwalają uruchomić te same testy w kilku silnikach.');
q('Co robi npx playwright install chromium?','Pobiera przeglądarkę zgodną z wersją Playwright','Instaluje pakiet @playwright/test','Uruchamia testy w Chromium','Aktualizuje Chrome w systemie','Playwright używa własnych, przypiętych buildów przeglądarek.');
q('Co tworzy npm init playwright@latest?','Konfigurację, katalog testów i przykładowy test oraz pobiera przeglądarki','Nową aplikację React','Serwer API','Plik storageState','To szybki start projektu E2E.');
q('Czym jest izolowany kontekst przeglądarki w Playwright?','Osobny profil z własnymi ciasteczkami i storage dla każdego testu','Nowe okno systemowe','Kopia serwera','Wątek Node','Każdy test dostaje świeży kontekst, więc ciasteczka nie przeciekają.');
q('Skąd test bierze sesję, skoro każdy kontekst jest świeży?','Z pliku storageState wczytanego w use projektu','Z poprzedniego testu','Z ciasteczek systemowej przeglądarki','Z localStorage serwera','Świeży kontekst startuje ze stanu z pliku.');
q('Do czego służy fikstura request w Playwright?','Wysyła żądania HTTP z tym samym baseURL bez otwierania strony','Przechwytuje żądania strony','Tworzy nowy page','Zapisuje trace','W lekcji resetuje dane przez API.');
q('Kiedy test powinien przygotować dane przez API zamiast przez UI?','Gdy przygotowanie nie jest przedmiotem testu','Zawsze, nawet w teście tworzenia','Nigdy','Tylko w Firefox','UI testujesz tam, gdzie sprawdzasz tę ścieżkę; resztę przygotuj szybciej.');
q('Co robi test.describe w Playwright?','Grupuje testy i pozwala ustawić wspólne opcje przez test.use','Uruchamia testy równolegle w innym procesie','Tworzy nowy projekt','Wyłącza fikstury','Opcje z test.use obowiązują w całej grupie.');
q('Co robi page.on("request", ...)?','Rejestruje nasłuch na żądania wysyłane przez stronę','Przechwytuje i blokuje żądania','Zmienia baseURL','Instaluje zegar','Nasłuch obserwuje, page.route może zmieniać odpowiedź.');
q('Dlaczego w teście filtrów najpierw sprawdzasz toHaveURL, a potem wiersze?','Adres jest źródłem prawdy, a lista ma się z nim zgadzać','Bo wiersze ładują się przed adresem','Bo toHaveURL czyści cache','Bo kolejność jest wymagana przez Playwright','Test opisuje kontrakt: URL → zapytanie → widok.');
q('Co oznacza replace:true przy zmianie parametrów wyszukiwania?','Zmiana nie dodaje nowego wpisu w historii przeglądarki','Zastępuje całą stronę','Czyści wszystkie parametry','Wyłącza nawigację wstecz','Przycisk Wstecz nie przechodzi przez każdy wpisany znak.');
q('Co sprawdza nagłówek poziomu 1 w teście szczegółów?','Że otwarto właściwy rekord, a nie tylko zmieniono adres','Że CSS nagłówka jest poprawny','Że serwer zwrócił 201','Że test działa w trybie UI','Adres i treść razem potwierdzają nawigację.');
q('Dlaczego w e2e nie testuje się wszystkich przypadków walidacji formularza?','Są szybciej i dokładniej sprawdzane testami komponentów','Bo Playwright nie wspiera formularzy','Bo walidacja nie działa w przeglądarce','Bo to zabronione','E2E sprawdza, że kluczowa ścieżka działa w całości.');
q('Co jest typowym źródłem niestabilnych testów E2E?','Wspólne dane, ręczne pauzy i kruche selektory','Lokatory po roli','Asercje z ponawianiem','Fikstury z resetem','Te trzy problemy usuwa izolacja danych, warunki i role.');
q('Gdzie po nieudanym teście szukać pliku śladu?','W katalogu test-results konkretnego testu (trace.zip)','W katalogu dist','W node_modules','W pliku storageState','Otwierasz go poleceniem npx playwright show-trace.');
q('Jak otworzyć ślad z CI?','npx playwright show-trace trace.zip albo przez raport HTML','npm run build','npx vite preview','Nie da się poza CI','Ślad jest samodzielnym plikiem do analizy lokalnie.');
q('Czym jest flaky test?','Testem, który przy tym samym kodzie raz przechodzi, a raz nie','Testem pominiętym','Testem bez asercji','Testem w Firefox','Niestabilność trzeba zdiagnozować, a nie ukrywać.');
q('Dlaczego weryfikacja lekcji uruchamia mutanty aplikacji?','Sprawdza, że testy E2E naprawdę wykrywają znane usterki','Żeby przyspieszyć build','Żeby przetestować Playwright','Żeby wygenerować trace dla każdego testu','Test, który przechodzi także dla zepsutego kodu, niczego nie chroni.');
q('Co da się sprawdzić w E2E, a nie w jsdom, na przykładzie natywnego <dialog>?','Prawdziwe showModal, warstwę modalną i zamykanie w przeglądarce','Nazwę komponentu React','Liczbę renderów','Typy TypeScript','Natywne zachowanie elementu zapewnia przeglądarka.');
const codeQuestions=[
  ['Co poprawić w tym teście?','Zastąpić pauzę asercją z ponawianiem na oczekiwanym stanie','Wydłużyć pauzę do 5000 ms','Usunąć await przed click','Zmienić lokator na XPath','Asercja await expect(...).toBeVisible() czeka dokładnie tyle, ile trzeba.',String.raw`import {expect,test} from "@playwright/test";
test("lista",async({page})=>{
  await page.goto("/tickets");
  await page.waitForTimeout(2000);
  expect(await page.getByRole("link",{name:"API timeout"}).isVisible()).toBe(true);
});`],
  ['Co jest błędem w tym teście?','Brak await przed asercją — test może zakończyć się przed sprawdzeniem','Brak waitForTimeout','Użycie getByRole','Zbyt krótki timeout','Asercje na lokatorach zwracają Promise.',String.raw`import {expect,test} from "@playwright/test";
test("dialog",async({page})=>{
  await page.goto("/tickets");
  await page.getByRole("button",{name:"+ Nowy ticket"}).click();
  expect(page.getByRole("dialog",{name:"Nowy ticket"})).toBeVisible();
});`],
  ['Dlaczego ten test może przegapić odpowiedź?','Oczekiwanie na odpowiedź zaczyna się dopiero po akcji, która ją wywołuje','Bo waitForResponse nie istnieje','Bo DELETE nie ma odpowiedzi','Bo runFor nie przesuwa czasu','Najpierw utwórz obietnicę oczekiwania, potem wykonaj akcję.',String.raw`import {expect,test} from "@playwright/test";
test("usunięcie",async({page})=>{
  await page.clock.install();
  await page.goto("/tickets");
  await page.getByRole("button",{name:"Usuń"}).first().click();
  await page.clock.runFor(5000);
  const response=await page.waitForResponse(r=>r.request().method()==="DELETE");
  expect(response.status()).toBe(204);
});`],
  ['Co jest problemem tego lokatora?','Zależy od struktury DOM i pęknie przy zmianie układu','Jest zbyt wolny','Nie działa w Chromium','Nie wspiera kliknięcia','Lokator po roli i nazwie opisuje intencję.',String.raw`import {test} from "@playwright/test";
test("utwórz",async({page})=>{
  await page.goto("/tickets");
  await page.locator("#root > main > button:nth-child(2)").click();
});`],
  ['Co zrobi ten test na wspólnej sesji z setup?','Unieważni sesję używaną przez kolejne testy','Nic, sesje są kopiowane na serwerze','Zaloguje innego użytkownika','Wyłączy trace','Wylogowanie w testach rób na osobnej sesji.',String.raw`import {expect,test} from "@playwright/test";
test("wyloguj",async({page})=>{
  await page.goto("/tickets");
  await page.getByRole("button",{name:/Wyloguj/}).click();
  await expect(page).toHaveURL(/\/login$/);
});`],
  ['Dlaczego unroute w tym teście nie zdejmie przechwycenia?','Dopasowanie w unroute jest inną funkcją niż w route','Bo unroute nie istnieje','Bo fulfill blokuje unroute','Bo route działa tylko raz','Zapisz matcher w stałej i użyj go w obu miejscach.',String.raw`import {test} from "@playwright/test";
test("błąd",async({page})=>{
  await page.route(url=>url.pathname==="/api/tickets",route=>route.fulfill({status:500}));
  await page.goto("/tickets");
  await page.unroute(url=>url.pathname==="/api/tickets");
});`],
  ['Co sprawdza ta konfiguracja projektu?','Projekt chromium czeka na setup i używa zapisanej sesji','Uruchamia dwa serwery','Wyłącza logowanie','Uruchamia testy w Firefox','dependencies wymusza kolejność, storageState przenosi sesję.',String.raw`import {defineConfig,devices} from "@playwright/test";
export default defineConfig({projects:[
  {name:"setup",testMatch:/.*\.setup\.ts/},
  {name:"chromium",use:{...devices["Desktop Chrome"],storageState:"playwright/.auth/agent.json"},dependencies:["setup"]},
]});`],
  ['Co robi ta fikstura?','Resetuje dane przed każdym testem bez jawnego użycia','Loguje użytkownika','Zmienia baseURL','Zapisuje trace','Opcja auto uruchamia fiksturę w każdym teście.',String.raw`import {test as base} from "@playwright/test";
export const test=base.extend<{reset:void}>({reset:[async({request},use)=>{await request.post("/api/test/reset");await use();},{auto:true}]});`],
  ['Co zwróci asercja w tym teście, gdy przycisk istnieje, ale jest wyłączony?','Kliknięcie poczeka na aktywny przycisk, a po limicie czasu test zawiedzie','Kliknie wyłączony przycisk','Pominie kliknięcie bez błędu','Włączy przycisk','Auto-waiting sprawdza, czy element jest aktywny.',String.raw`import {test} from "@playwright/test";
test("zapis",async({page})=>{
  await page.goto("/tickets/42");
  await page.getByRole("button",{name:"Zamknij"}).click({timeout:1000});
});`],
  ['Po co w tym teście jest page.clock.install() przed goto?','Zegar strony musi być przejęty, zanim kod aplikacji ustawi timery','Bo goto wymaga zegara','Żeby przyspieszyć sieć','Żeby zatrzymać serwer','Instalacja po załadowaniu nie obejmie wcześniej utworzonych timerów.',String.raw`import {test} from "@playwright/test";
test("cofnij",async({page})=>{
  await page.clock.install();
  await page.goto("/tickets");
  await page.clock.runFor(5000);
});`],
];
for(const [question,answer,b,c,d,explanation,code] of codeQuestions)q(question,answer,b,c,d,explanation,code);
if(exercises.length!==30||quiz.length!==100)throw Error('Niepełny zestaw '+exercises.length+'/'+quiz.length);
const project={
  'package.json':JSON.stringify({private:true,type:'module',scripts:{dev:'vite',build:'vite build',serve:'node server.mjs','test:e2e':'playwright test'},dependencies:{react:'19.2.8','react-dom':'19.2.8','react-router':'7.18.2'},devDependencies:{'@playwright/test':'1.64.1',vite:'8.3.5','@vitejs/plugin-react':'^5',typescript:'^5.9.3','@types/react':'^19','@types/react-dom':'^19','@types/node':'^24'}},null,2)+'\n',
  '.gitignore':'node_modules\ndist\nplaywright/.auth\ntest-results\nplaywright-report\ne2e-results.json\n',
  'index.html':'<!doctype html><html lang="pl"><head><meta charset="utf-8"><title>DevDesk</title></head><body><div id="root"></div><script type="module" src="/src/main.tsx"></script></body></html>\n',
  'server.mjs':server,'src/api.ts':apiClient,'src/LoginPage.tsx':loginPage,'src/NewTicketDialog.tsx':dialog,'src/TicketsPage.tsx':listPage,'src/TicketDetails.tsx':detailsPage,'src/App.tsx':app,'src/main.tsx':main,
  'playwright.config.ts':config,'e2e/auth.setup.ts':authSetup,'e2e/fixtures.ts':fixtures,'e2e/create-ticket.spec.ts':createSpec,'e2e/filters.spec.ts':filterSpec,'e2e/close-ticket.spec.ts':closeSpec,
  'e2e/undo-delete.spec.ts':undoSpec,'e2e/server-error.spec.ts':errorSpec,'e2e/auth.spec.ts':authSpec,'e2e/ticket-not-found.spec.ts':detailRouteSpec,
  'README.md':'DevDesk E2E: npm install, npx playwright install chromium, npm run build, npm run test:e2e. Serwer server.mjs serwuje dist/ i API w pamięci; endpoint /api/test/reset działa tylko z E2E=1 (ustawia go webServer). Testy biegną sekwencyjnie, bo dzielą stan serwera. Sesja: projekt setup loguje się raz i zapisuje playwright/.auth/agent.json (poza repozytorium). Trace zapisuje się dla nieudanych testów: npx playwright show-trace test-results/<test>/trace.zip. Konfiguracja z Firefoksem jest w ćwiczeniu; weryfikacja lekcji uruchamia Chromium (Playwright 1.64.1). To nie jest test prawdziwego backendu Spring.\n',
};
// Mutanty muszą zostać odrzucone przez testy E2E uruchomione w prawdziwym Chromium.
const mutants=[
  {file:'src/NewTicketDialog.tsx',before:'form.reset();setError("");dialogRef.current?.close();',after:'form.reset();setError("");'},
  {file:'src/TicketsPage.tsx',before:'if(pending.current!==null)window.clearTimeout(pending.current);\n    pending.current=null;setHidden(null);',after:'pending.current=null;setHidden(null);'},
  {file:'src/TicketsPage.tsx',before:'q=params.get("q")??""',after:'q=""'},
  {file:'src/api.ts',before:'if(response.status===401)throw new AuthError();',after:''},
];
module.exports={server,apiClient,loginPage,dialog,listPage,detailsPage,app,main,config,authSetup,fixtures,createSpec,filterSpec,closeSpec,undoSpec,errorSpec,authSpec,detailRouteSpec,firefoxConfig,exercises,quiz,project,mutants};
