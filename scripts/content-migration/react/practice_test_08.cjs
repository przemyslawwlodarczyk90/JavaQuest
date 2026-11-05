// Lekcja 14/08: dostępność (axe), testy wizualne i Storybook. Projekt rozszerza aplikację E2E z lekcji 07.
const base=require('./practice_test_07.cjs');
const styles=String.raw`:root{
  color-scheme:light dark;
  --bg:#ffffff;--surface:#f6f8fa;--text:#1f2328;--text-muted:#59636e;--border:#d1d9e0;--accent:#0969da;
  --space-1:4px;--space-2:8px;--space-3:12px;--radius:6px;
  --open-bg:#dafbe1;--open-fg:#116329;--progress-bg:#fff8c5;--progress-fg:#7d4e00;--closed-bg:#eaeef2;--closed-fg:#424a53;
}
@media (prefers-color-scheme:dark){
  :root{
    --bg:#0d1117;--surface:#151b23;--text:#f0f6fc;--text-muted:#9198a1;--border:#3d444d;--accent:#4493f8;
    --open-bg:#12261e;--open-fg:#3fb950;--progress-bg:#272115;--progress-fg:#d29922;--closed-bg:#212830;--closed-fg:#9198a1;
  }
}
*{box-sizing:border-box}
body{margin:0;background:var(--bg);color:var(--text);font:14px/1.5 system-ui,sans-serif}
header,main{padding:var(--space-3)}
a{color:var(--accent)}
.muted{color:var(--text-muted)}
/* Cyfry o stałej szerokości: maska na <time> ma ten sam rozmiar przy każdej godzinie. */
time{font-variant-numeric:tabular-nums}
table{border-collapse:collapse;width:100%;background:var(--surface)}
caption{text-align:left;padding:var(--space-2) 0;font-weight:600}
th,td{padding:var(--space-2) var(--space-3);border-bottom:1px solid var(--border);text-align:left}
.badge{display:inline-block;padding:0 var(--space-2);border-radius:999px;font-size:12px;font-weight:600}
.badge-open{background:var(--open-bg);color:var(--open-fg)}
.badge-in_progress{background:var(--progress-bg);color:var(--progress-fg)}
.badge-closed{background:var(--closed-bg);color:var(--closed-fg)}
.toast{display:flex;gap:var(--space-2);align-items:center;padding:var(--space-2) var(--space-3);border:1px solid var(--border);border-radius:var(--radius);background:var(--surface)}
.icon-button{display:inline-grid;place-items:center;width:24px;height:24px;padding:0}
.card{border:1px solid var(--border);border-radius:var(--radius);padding:var(--space-3);background:var(--surface)}
button{font:inherit;color:var(--text);background:var(--surface);border:1px solid var(--border);border-radius:var(--radius);padding:var(--space-1) var(--space-3)}
input,select{font:inherit;color:var(--text);background:var(--bg);border:1px solid var(--border);border-radius:var(--radius);padding:var(--space-1) var(--space-2)}`;
const badge=String.raw`import type {Status} from "./api";
const labels:Record<Status,string>={OPEN:"Otwarty",IN_PROGRESS:"W toku",CLOSED:"Zamknięty"};
export function StatusBadge({status}:{status:Status}){
  return <span className={"badge badge-"+status.toLowerCase()}>{labels[status]}</span>;
}`;
const toast=String.raw`import type {ReactNode} from "react";
export function Toast({children,onClose}:{children:ReactNode;onClose:()=>void}){
  return <div role="status" className="toast">
    <span>{children}</span>
    {/* Ikona bez tekstu: nazwę przycisku daje wyłącznie aria-label. */}
    <button type="button" className="icon-button" aria-label="Zamknij powiadomienie" onClick={onClose}>
      <svg aria-hidden="true" viewBox="0 0 10 10" width="10" height="10"><path d="M1 1l8 8M9 1l-8 8" stroke="currentColor" strokeWidth="1.5"/></svg>
    </button>
  </div>;
}`;
const watch=String.raw`import {useState} from "react";
export function WatchToggle(){
  const [watching,setWatching]=useState(false);
  return <button type="button" aria-pressed={watching} onClick={()=>setWatching(value=>!value)}>{watching?"Obserwujesz":"Obserwuj"}</button>;
}`;
const listPage=String.raw`import {useEffect,useRef,useState} from "react";
import {Link,useNavigate,useSearchParams} from "react-router";
import {AuthError,deleteTicket,listTickets,type Ticket} from "./api";
import {NewTicketDialog} from "./NewTicketDialog";
import {StatusBadge} from "./StatusBadge";
import {Toast} from "./Toast";
type Load={kind:"loading"}|{kind:"error"}|{kind:"ready";tickets:Ticket[];at:Date};
export function TicketsPage(){
  const navigate=useNavigate(),[params,setParams]=useSearchParams();
  const status=params.get("status")??"",q=params.get("q")??"";
  const [load,setLoad]=useState<Load>({kind:"loading"}),[attempt,setAttempt]=useState(0);
  const [created,setCreated]=useState<Ticket|null>(null),[hidden,setHidden]=useState<Ticket|null>(null);
  const pending=useRef<number|null>(null);
  useEffect(()=>{
    const controller=new AbortController();
    setLoad({kind:"loading"});
    listTickets({status,q},controller.signal).then(tickets=>setLoad({kind:"ready",tickets,at:new Date()})).catch((reason:unknown)=>{
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
    {created&&<Toast onClose={()=>setCreated(null)}>Utworzono ticket #{created.id}.</Toast>}
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
    {load.kind==="ready"&&<table>
      <caption>Lista ticketów <span className="muted">· odświeżono <time dateTime={load.at.toISOString()}>{load.at.toLocaleTimeString("pl-PL")}</time></span></caption>
      <thead><tr><th scope="col">Tytuł</th><th scope="col">Status</th><th scope="col">Akcje</th></tr></thead>
      <tbody>{load.tickets.filter(t=>t.id!==hidden?.id).map(ticket=><tr key={ticket.id}>
        <td><Link to={"/tickets/"+ticket.id}>{ticket.title}</Link></td><td><StatusBadge status={ticket.status}/></td>
        <td><button type="button" disabled={hidden!==null} onClick={()=>remove(ticket)}>Usuń</button></td>
      </tr>)}</tbody>
    </table>}
  </main>;
}`;
const detailsPage=String.raw`import {useEffect,useId,useState} from "react";
import {Link,useNavigate,useParams} from "react-router";
import {AuthError,closeTicket,getTicket,type Ticket} from "./api";
import {StatusBadge} from "./StatusBadge";
export function TicketDetails(){
  const id=Number(useParams().id),navigate=useNavigate(),headingId=useId();
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
    <section className="card" aria-labelledby={headingId}>
      <h1 id={headingId}>#{ticket.id} {ticket.title}</h1>
      <p>Status: <StatusBadge status={ticket.status}/></p>
      {ticket.status!=="CLOSED"&&<button type="button" onClick={()=>void closeTicket(ticket.id).then(setTicket,()=>setError("Nie udało się zamknąć ticketu."))}>Zamknij</button>}
    </section>
    <Link to="/tickets">Wróć do listy</Link>
  </main>;
}`;
const main=String.raw`import {StrictMode} from "react";
import {createRoot} from "react-dom/client";
import {RouterProvider} from "react-router";
import {router} from "./App";
import "./styles.css";
createRoot(document.getElementById("root")!).render(<StrictMode><RouterProvider router={router}/></StrictMode>);`;
const vitestConfig=String.raw`import {defineConfig} from "vitest/config";
// Vitest bierze tylko src/; katalog e2e należy do Playwright.
export default defineConfig({test:{environment:"jsdom",include:["src/**/*.test.tsx"],setupFiles:["./src/testSetup.ts"],restoreMocks:true}});`;
const testSetup=String.raw`import {afterEach} from "vitest";
import {cleanup} from "@testing-library/react";
import "@testing-library/jest-dom/vitest";
afterEach(()=>{cleanup();document.body.innerHTML="";});`;
const a11yTests=String.raw`import {render} from "@testing-library/react";
import {MemoryRouter} from "react-router";
import {axe} from "vitest-axe";
import {expect,test} from "vitest";
import {LoginPage} from "./LoginPage";
import {StatusBadge} from "./StatusBadge";
import {Toast} from "./Toast";
test("strona logowania nie ma naruszeń dostępności",async()=>{
  const {container}=render(<MemoryRouter><LoginPage/></MemoryRouter>);
  const results=await axe(container);
  expect(results.violations).toEqual([]);
});
test("toast z przyciskiem-ikoną ma nazwę przycisku",async()=>{
  const {container}=render(<Toast onClose={()=>{}}>Utworzono ticket #100.</Toast>);
  const results=await axe(container);
  expect(results.violations.map(violation=>violation.id)).toEqual([]);
});
test("plakietki wszystkich statusów przechodzą audyt",async()=>{
  const {container}=render(<ul>{(["OPEN","IN_PROGRESS","CLOSED"] as const).map(status=><li key={status}><StatusBadge status={status}/></li>)}</ul>);
  expect((await axe(container)).violations).toEqual([]);
});
test("audyt naprawdę wykrywa przycisk-ikonę bez nazwy",async()=>{
  const {container}=render(<main><button type="button"><svg aria-hidden="true" width="10" height="10"/></button></main>);
  const results=await axe(container);
  expect(results.violations.map(violation=>violation.id)).toContain("button-name");
});`;
const badgeStories=String.raw`import type {Meta,StoryObj} from "@storybook/react-vite";
import {StatusBadge} from "./StatusBadge";
const meta={title:"DevDesk/StatusBadge",component:StatusBadge} satisfies Meta<typeof StatusBadge>;
export default meta;
type Story=StoryObj<typeof meta>;
export const Open:Story={args:{status:"OPEN"}};
export const InProgress:Story={args:{status:"IN_PROGRESS"}};
export const Closed:Story={args:{status:"CLOSED"}};`;
const toastStories=String.raw`import type {Meta,StoryObj} from "@storybook/react-vite";
import {expect,fn,userEvent,within} from "storybook/test";
import {Toast} from "./Toast";
const meta={title:"DevDesk/Toast",component:Toast,args:{children:"Utworzono ticket #100.",onClose:fn()}} satisfies Meta<typeof Toast>;
export default meta;
type Story=StoryObj<typeof meta>;
export const Created:Story={};
export const CloseClick:Story={
  play:async({canvasElement,args})=>{
    const canvas=within(canvasElement);
    await userEvent.click(canvas.getByRole("button",{name:"Zamknij powiadomienie"}));
    await expect(args.onClose).toHaveBeenCalledTimes(1);
  },
};`;
const watchStories=String.raw`import type {Meta,StoryObj} from "@storybook/react-vite";
import {expect,userEvent,within} from "storybook/test";
import {WatchToggle} from "./WatchToggle";
const meta={title:"DevDesk/WatchToggle",component:WatchToggle} satisfies Meta<typeof WatchToggle>;
export default meta;
export const WatchInteraction:StoryObj<typeof meta>={
  play:async({canvasElement})=>{
    const canvas=within(canvasElement);
    await userEvent.click(canvas.getByRole("button",{name:/obserwuj/i}));
    await expect(canvas.getByRole("button",{name:/obserwujesz/i})).toHaveAttribute("aria-pressed","true");
  },
};`;
const storyTests=String.raw`import {composeStories} from "@storybook/react";
import {axe} from "vitest-axe";
import {expect,test} from "vitest";
import * as badgeStories from "./StatusBadge.stories";
import * as toastStories from "./Toast.stories";
import * as watchStories from "./WatchToggle.stories";
// Portable stories: te same historie co w Storybooku, uruchomione w Vitest razem z funkcją play.
// Historia to fragment strony, więc wyłączamy regułę region (treść poza punktami orientacyjnymi) — sprawdza ją audyt całych stron w E2E.
const storyAudit={rules:{region:{enabled:false}}};
const all={...composeStories(badgeStories),...composeStories(toastStories),...composeStories(watchStories)};
for(const [name,Story] of Object.entries(all)){
  test("historia "+name+" działa i przechodzi audyt axe",async()=>{
    await Story.run();
    expect((await axe(document.body,storyAudit)).violations.map(violation=>violation.id)).toEqual([]);
  });
}`;
const sbMain=String.raw`import type {StorybookConfig} from "@storybook/react-vite";
const config:StorybookConfig={
  stories:["../src/**/*.stories.@(ts|tsx)"],
  addons:["@storybook/addon-a11y","@storybook/addon-vitest"],
  framework:{name:"@storybook/react-vite",options:{}},
};
export default config;`;
const sbPreview=String.raw`import type {Preview} from "@storybook/react-vite";
import "../src/styles.css";
const preview:Preview={
  // "error": naruszenie axe w historii oznacza nieudany test historii.
  parameters:{a11y:{test:"error"}},
};
export default preview;`;
const a11ySpec=String.raw`import AxeBuilder from "@axe-core/playwright";
import {expect,test} from "./fixtures";
const pages=[{path:"/tickets",ready:"table"},{path:"/tickets/42",ready:"region"}] as const;
for(const theme of ["light","dark"] as const){
  for(const {path,ready} of pages){
    test("strona "+path+" w motywie "+theme+" nie ma naruszeń WCAG A/AA",async({page})=>{
      await page.emulateMedia({colorScheme:theme});
      await page.goto(path);
      await page.getByRole(ready).waitFor();
      const results=await new AxeBuilder({page}).withTags(["wcag2a","wcag2aa"]).analyze();
      expect(results.violations.map(v=>v.id+": "+v.nodes.length+" el.")).toEqual([]);
    });
  }
}
test.describe("bez sesji",()=>{
  test.use({storageState:{cookies:[],origins:[]}});
  test("strona logowania nie ma naruszeń WCAG A/AA",async({page})=>{
    await page.goto("/login");
    await page.getByRole("heading",{name:"Logowanie"}).waitFor();
    const results=await new AxeBuilder({page}).withTags(["wcag2a","wcag2aa"]).analyze();
    expect(results.violations).toEqual([]);
  });
});
test("audyt wykrywa celowo dodany przycisk bez nazwy",async({page})=>{
  await page.goto("/tickets");
  await page.getByRole("table").waitFor();
  await page.evaluate(()=>{
    const button=document.createElement("button");
    button.id="bez-nazwy";button.innerHTML='<svg aria-hidden="true" width="10" height="10"></svg>';
    document.querySelector("main")?.append(button);
  });
  const results=await new AxeBuilder({page}).include("#bez-nazwy").analyze();
  expect(results.violations.map(v=>v.id)).toContain("button-name");
});`;
const visualSpec=String.raw`import {expect,test} from "./fixtures";
for(const theme of ["light","dark"] as const){
  test("tabela ticketów w motywie "+theme,async({page})=>{
    await page.emulateMedia({colorScheme:theme,reducedMotion:"reduce"});
    await page.goto("/tickets");
    const table=page.getByRole("table");
    await expect(table.getByRole("link",{name:"API timeout"})).toBeVisible();
    // Godzina odświeżenia zmienia się przy każdym uruchomieniu — maskujemy ją.
    await expect(table).toHaveScreenshot("tickets-"+theme+".png",{mask:[table.locator("time")],maxDiffPixelRatio:0.01});
  });
  test("karta ticketu w motywie "+theme,async({page})=>{
    await page.emulateMedia({colorScheme:theme,reducedMotion:"reduce"});
    await page.goto("/tickets/42");
    const card=page.getByRole("region",{name:/API timeout/});
    await expect(card).toBeVisible();
    await expect(card).toHaveScreenshot("ticket-card-"+theme+".png",{maxDiffPixelRatio:0.01});
  });
}`;
const excludeSnippet=String.raw`import AxeBuilder from "@axe-core/playwright";
import {expect,test} from "./fixtures";
test("strona partnerów bez naruszeń poza logo partnera",async({page})=>{
  await page.goto("/tickets");
  await page.getByRole("table").waitFor();
  const results=await new AxeBuilder({page})
    .withTags(["wcag2a","wcag2aa"])
    // Logo partnera ma kolory narzucone umową; wyjątek dotyczy tylko tego elementu (zgłoszenie DEV-311).
    .exclude("[data-partner-logo]")
    .analyze();
  expect(results.violations).toEqual([]);
});`;
const eslintSnippet=String.raw`// eslint.config.js — reguły dostępności JSX już w edytorze
import jsxA11y from "eslint-plugin-jsx-a11y";
export default [
  jsxA11y.flatConfigs.recommended,
  {rules:{"jsx-a11y/no-autofocus":"warn"}},
];`;
const commands=String.raw`# pierwsze uruchomienie lub zamierzona zmiana wyglądu: zapis wzorców
npx playwright test e2e/visual.spec.ts --update-snapshots
# zwykły przebieg: porównanie ze wzorcami, raport HTML pokazuje obraz różnic
npx playwright test
npx playwright show-report
# Storybook i testy historii
npx storybook dev -p 6006
npx vitest run src/stories.test.tsx`;
const exercises=[
  ['Zdefiniuj tokeny kolorów i odstępów dla motywu jasnego i ciemnego z kontrastem WCAG AA.','Ciemny motyw nadpisuje te same zmienne w prefers-color-scheme.',styles],
  ['Napisz komponent StatusBadge z klasą zależną od statusu i polską etykietą.','Klasa badge-in_progress musi mieć zdefiniowane kolory w obu motywach.',badge],
  ['Zbuduj Toast z przyciskiem-ikoną, którego nazwę daje aria-label.','SVG oznacz aria-hidden, żeby nie był odczytywany.',toast],
  ['Napisz przełącznik obserwowania z aria-pressed.','Nazwa przycisku zmienia się razem ze stanem.',watch],
  ['Skonfiguruj Vitest tak, by nie uruchamiał specyfikacji Playwright z katalogu e2e.','include ogranicza testy do src.',vitestConfig],
  ['Przygotuj plik setup: jest-dom, cleanup i czyszczenie body po historiach.','Historie renderowane przez run() trafiają do document.body.',testSetup],
  ['Dodaj test axe strony logowania w teście komponentu.','Router jest potrzebny, bo strona używa useNavigate.',a11yTests],
  ['Sprawdź audytem axe toast z przyciskiem-ikoną.','Lista identyfikatorów naruszeń daje czytelny wynik.',a11yTests],
  ['Udowodnij, że audyt w jsdom wykrywa przycisk-ikonę bez nazwy.','Test negatywny chroni przed audytem, który nic nie sprawdza.',a11yTests],
  ['Napisz historie CSF dla trzech statusów plakietki.','satisfies Meta<typeof StatusBadge> zachowuje typy args.',badgeStories],
  ['Napisz historię Toast z funkcją play i szpiegiem fn() dla onClose.','Args z fn() są dostępne w play.',toastStories],
  ['Napisz historię interakcji WatchToggle sprawdzającą aria-pressed.','Szukaj przycisku po nazwie, nie po klasie.',watchStories],
  ['Uruchom wszystkie historie w Vitest przez composeStories i sprawdź je axe.','Story.run() renderuje historię i wykonuje play.',storyTests],
  ['Skonfiguruj Storybook z frameworkiem react-vite i dodatkiem dostępności.','stories wskazuje pliki *.stories.tsx w src.',sbMain],
  ['Ustaw w preview globalne style i a11y.test: "error".','Historie mają wyglądać jak aplikacja, więc importują te same tokeny.',sbPreview],
  ['Dodaj audyt @axe-core/playwright dwóch stron w obu motywach.','emulateMedia przed goto ustawia motyw dla CSS.',a11ySpec],
  ['Zbadaj stronę logowania bez sesji audytem axe w E2E.','Pusty storageState w test.use.',a11ySpec],
  ['Udowodnij, że audyt E2E wykrywa wstrzyknięty przycisk bez nazwy.','include ogranicza analizę do jednego elementu.',a11ySpec],
  ['Dodaj test wizualny tabeli w obu motywach z maską godziny.','Najpierw czekaj na dane, potem rób zrzut.',visualSpec],
  ['Dodaj test wizualny karty ticketu w obu motywach.','Region z aria-labelledby ma nazwę z nagłówka.',visualSpec],
  ['Ustal szerokość cyfr godziny, by maska miała stały rozmiar.','font-variant-numeric:tabular-nums w tokenach.',styles],
  ['Pokaż w tabeli godzinę odświeżenia w <time> i plakietki statusów.','dateTime trzyma wartość maszynową, treść — czytelną.',listPage],
  ['Opakuj szczegóły ticketu w region z nazwą z nagłówka h1.','useId łączy aria-labelledby z nagłówkiem.',detailsPage],
  ['Zaimportuj tokeny CSS w punkcie wejścia aplikacji.','Bez importu testy E2E sprawdzają nieostylowaną stronę.',main],
  ['Wyłącz regułę axe punktowo dla jednego elementu z uzasadnieniem.','exclude zamiast disableRules dla całej strony.',excludeSnippet],
  ['Zapisz polecenia zatwierdzania wzorców i oglądania raportu różnic.','--update-snapshots tylko przy zamierzonej zmianie wyglądu.',commands],
  ['Włącz reguły jsx-a11y w ESLint.','Część problemów wychodzi już w edytorze.',eslintSnippet],
  ['Ogranicz zmienność zrzutu: motyw, reducedMotion i stały stan danych.','Fikstura resetuje dane, emulateMedia ustala motyw.',visualSpec],
  ['Sprawdź kontrast wszystkich plakietek na prawdziwej stronie w obu motywach.','Lista ticketów zawiera wszystkie trzy statusy.',a11ySpec],
  ['Uruchom historię Toast z play jako test i sprawdź wywołanie onClose.','Ten sam plik historii służy dokumentacji i testom.',storyTests],
];
const quiz=[];function q(...row){quiz.push(row);}
q('Czym jest axe-core?','Silnikiem reguł dostępności sprawdzającym wyrenderowany DOM','Biblioteką komponentów','Narzędziem do zrzutów ekranu','Wtyczką do Vite','axe sprawdza m.in. nazwy, etykiety, ARIA i kontrast.');
q('Jaką część problemów dostępności wykrywa automat?','Część — szacunkowo około jednej trzeciej','Wszystkie','Żadne','Tylko problemy kontrastu','Kolejność fokusu i zrozumiałość komunikatów wymagają ręcznego audytu.');
q('Czego axe nie sprawdzi?','Czy kolejność fokusu ma sens i czy komunikat jest zrozumiały','Czy przycisk ma nazwę','Czy pole ma etykietę','Czy id są unikalne','To zadania testów interakcji i ręcznego audytu.');
q('Dlaczego kontrast lepiej sprawdzać w E2E niż w jsdom?','Przeglądarka liczy prawdziwe style i kolory','jsdom nie ma DOM','axe nie działa w Vitest','Kontrast zależy od serwera','jsdom nie oblicza stylów kaskadowych jak przeglądarka.');
q('Jak zapisać audyt w teście komponentu?','const results=await axe(container); expect(results.violations).toEqual([])','expect(container).toBeAccessible()','axe.render(container)','await screen.audit()','Pusta lista naruszeń to warunek przejścia.');
q('Co robi new AxeBuilder({page}).analyze()?','Audytuje aktualny stan strony w prawdziwej przeglądarce','Uruchamia serwer','Robi zrzut ekranu','Instaluje axe w aplikacji','Wynik zawiera listę naruszeń z selektorami.');
q('Co robi withTags(["wcag2a","wcag2aa"])?','Ogranicza reguły do poziomów WCAG A i AA','Wyłącza kontrast','Dodaje reguły eksperymentalne','Zmienia język raportu','Dobór tagów ustala, jakiego standardu pilnujesz.');
q('Dlaczego wynik formatujemy jako "id: liczba el."?','Raport porażki jest czytelny bez pełnego obiektu','Bo axe zwraca tekst','Żeby ukryć naruszenia','Bo expect nie porównuje obiektów','Krótka lista mówi od razu, co poprawić.');
q('Jaką regułę narusza przycisk z samą ikoną SVG bez aria-label?','button-name','color-contrast','label','duplicate-id','Przycisk musi mieć nazwę dostępności.');
q('Czy przycisk z tekstem „✕” ma nazwę dostępności?','Tak, „✕”, ale nie opisuje działania','Nie, znaki specjalne są pomijane','Tak, „Zamknij” automatycznie','Nie, tylko aria-label daje nazwę','Formalnie przejdzie audyt, ale czytnik przeczyta niezrozumiały symbol.');
q('Po co aria-hidden na ikonie SVG w przycisku?','Ikona jest dekoracyjna, nazwę daje aria-label przycisku','Żeby ukryć przycisk','Żeby axe pominął przycisk','Żeby ikona była niewidoczna','Czytnik nie powinien odczytywać grafiki.');
q('Po co test negatywny „audyt wykrywa przycisk bez nazwy”?','Udowadnia, że konfiguracja audytu naprawdę coś sprawdza','Żeby zwiększyć pokrycie','Żeby wyłączyć regułę','Bo axe tego wymaga','Audyt, który niczego nie znajduje, może być źle podpięty.');
q('Co daje include("#bez-nazwy") w AxeBuilder?','Ogranicza analizę do wskazanego elementu','Dodaje element do strony','Wyklucza element','Zmienia tagi','Pozwala sprawdzić konkretny fragment.');
q('Kiedy użyć exclude zamiast disableRules?','Gdy wyjątek dotyczy jednego, uzasadnionego elementu','Zawsze, żeby test przeszedł','Gdy strona jest pusta','Nigdy','Wyłączenie reguły dla całej strony przestaje jej pilnować wszędzie.');
q('Co jest złego w disableRules(["color-contrast"]) dla całej aplikacji?','Kontrast przestaje być sprawdzany wszędzie','Nic, to zalecane','Test staje się wolniejszy','axe przestaje działać','Wyjątek ma być punktowy i opisany.');
q('Jak TypeScript pomaga w dostępności?','Typami atrybutów ARIA i wymaganymi props, np. label','Automatycznie dodaje alt','Sprawdza kontrast','Generuje role','Pomoc jest pośrednia; audyt sprawdza wynik.');
q('Co wykrywa eslint-plugin-jsx-a11y?','Część problemów w kodzie JSX, np. img bez alt','Kontrast kolorów','Kolejność fokusu w przeglądarce','Brak tłumaczeń','Działa w edytorze, zanim kod trafi do testów.');
q('Czy audyt axe różni się dla aplikacji w JSX i TSX?','Nie, analizuje wyrenderowany DOM','Tak, TSX ma więcej reguł','Tak, JSX nie jest wspierany','Tylko w Storybook','Język źródeł nie ma znaczenia dla DOM.');
q('Na czym polega test wizualny?','Porównaniu zrzutu ekranu z zatwierdzonym wzorcem','Sprawdzeniu ról ARIA','Porównaniu HTML','Pomiarze wydajności','Różnica powyżej progu oznacza porażkę.');
q('Co robi toHaveScreenshot przy pierwszym uruchomieniu bez wzorca?','Zapisuje obraz i zgłasza brak wzorca do zatwierdzenia','Zawsze przechodzi','Pobiera wzorzec z sieci','Pomija test','Wzorce zatwierdzasz świadomie przez --update-snapshots.');
q('Kiedy używać --update-snapshots?','Gdy zmiana wyglądu jest zamierzona i sprawdzona','Gdy test wizualny nie przechodzi z nieznanego powodu','Przy każdym uruchomieniu','W CI automatycznie','Aktualizacja wzorców to decyzja, nie naprawa testu.');
q('Dlaczego testy wizualne są kruche?','Różne systemy i przeglądarki renderują czcionki inaczej','Bo Playwright jest niestabilny','Bo CSS się nie zmienia','Bo zrzuty są w JPG','Stałe środowisko (np. kontener w CI) ogranicza różnice.');
q('Po co maska na elemencie <time>?','Dane zmienne nie powodują fałszywych różnic','Żeby ukryć błąd dostępności','Żeby przyspieszyć zrzut','Żeby zmienić motyw','Maska zakrywa obszar jednolitym kolorem.');
q('Dlaczego cyfry godziny mają tabular-nums?','Szerokość tekstu i maski jest stała niezależnie od godziny','Żeby poprawić kontrast','Bo Playwright tego wymaga','Żeby zmienić czcionkę','Inna szerokość godziny przesunęłaby układ i zmieniła zrzut.');
q('Co robi page.emulateMedia({colorScheme:"dark"})?','Ustawia prefers-color-scheme strony na ciemny','Zmienia motyw systemu','Odwraca kolory zrzutu','Wyłącza CSS','Media query w CSS reaguje jak u użytkownika z ciemnym motywem.');
q('Po co reducedMotion:"reduce" w teście wizualnym?','Ogranicza animacje, które zmieniają klatkę zrzutu','Przyspiesza sieć','Wyłącza JavaScript','Zmienia rozmiar okna','Mniej ruchu to stabilniejszy obraz.');
q('Co oznacza maxDiffPixelRatio:0.01?','Dopuszcza różnicę do 1% pikseli','Wymaga identycznych zrzutów','Dopuszcza 10% różnic','Zmniejsza rozdzielczość','Niski próg pilnuje zmian, a wysoki je ukrywa.');
q('Co jest złego w progu 30% różnic?','Test przestaje wykrywać realne zmiany','Jest za surowy','Spowalnia test','Wyłącza maski','Zamiast podnosić próg, stabilizuj środowisko.');
q('Dlaczego zrzut robimy elementowi, a nie całej stronie?','Mniejszy obszar to mniej zmiennych i czytelniejsza różnica','Bo całej strony nie da się zrzucić','Bo element ma lepszy kontrast','Bo maski działają tylko na elementach','Testuj wybrane, stabilne widoki.');
q('Dlaczego przed zrzutem tabeli czekamy na link API timeout?','Zrzut ma pokazać załadowane dane, nie stan ładowania','Bo toHaveScreenshot nie czeka wcale','Bo link zmienia motyw','Żeby zamaskować link','Asercja gotowości czyni zrzut deterministycznym.');
q('Gdzie Playwright zapisuje wzorce zrzutów?','W katalogu *-snapshots obok pliku testu','W dist','W node_modules','W storageState','Wzorce trafiają do repozytorium razem z testem.');
q('Dlaczego nazwa wzorca zawiera nazwę przeglądarki i systemu?','Renderowanie różni się między platformami','Żeby był unikalny w CI','Bo tak wymaga Git','Żeby zawierał datę','Wzorzec z Linuksa nie pasuje do Windows.');
q('Co pokaże raport po nieudanym teście wizualnym?','Oczekiwany obraz, aktualny i obraz różnic','Tylko komunikat tekstowy','Kod CSS','Listę naruszeń axe','Różnice są zaznaczone na trzecim obrazie.');
q('Jaki błąd wykryje test wizualny, a nie wykryje getByRole?','Rozjechany układ po zmianie tokenu odstępów','Brak nazwy przycisku','Brak etykiety pola','Zły tekst nagłówka','Role i teksty mogą zostać, a układ się zmienić.');
q('Jaki błąd wykryje audyt kontrastu w ciemnym motywie?','Jasny tekst na zbyt jasnym tle po zmianie tokenu','Brak klucza listy','Błąd typów','Zły adres API','Dlatego audyt biegnie w obu motywach.');
q('Czym jest Storybook?','Osobną aplikacją do pracy nad komponentami w izolacji','Frameworkiem testów E2E','Biblioteką stanu','Generatorem API','Każdy komponent ma tam zapisane stany — historie.');
q('Czym jest historia (story)?','Zapisanym stanem komponentu z konkretnymi args','Testem E2E','Commitem w Git','Plikiem CSS','Np. plakietka w każdym statusie.');
q('Co eksportuje domyślnie plik CSF?','Metadane komponentu (meta)','Pierwszą historię','Funkcję play','Konfigurację Vite','Nazwane eksporty to historie.');
q('Po co satisfies Meta<typeof StatusBadge>?','Sprawdza meta i zachowuje dokładny typ dla StoryObj','Zamienia komponent na klasę','Wyłącza typy args','Generuje dokumentację','StoryObj<typeof meta> zna wymagane args.');
q('Co robi funkcja play w historii?','Wykonuje interakcję na wyrenderowanej historii','Uruchamia serwer Storybooka','Robi zrzut ekranu','Zmienia motyw','Storybook pokazuje przebieg i może uruchomić ją jako test.');
q('Skąd w play pochodzą expect, userEvent i within?','Z modułu storybook/test','Z vitest','Z @playwright/test','Z react-dom','To instrumentowane wersje narzędzi testowych.');
q('Co daje fn() jako wartość args?','Szpiega funkcji, którego wywołania można sprawdzić w play','Pustą funkcję bez śladu','Mock modułu','Nowy komponent','Panel Actions też pokazuje wywołania.');
q('Czym są portable stories?','Historie uruchamiane poza Storybookiem, np. w Vitest','Historie eksportowane do PDF','Historie w innym repozytorium','Historie bez args','composeStories łączy historie z adnotacjami projektu.');
q('Co robi Story.run() z composeStories?','Renderuje historię i wykonuje jej funkcję play','Uruchamia Storybook w przeglądarce','Zapisuje wzorzec zrzutu','Tylko zwraca element','To pozwala używać historii jako testów.');
q('Dlaczego setup czyści document.body po teście?','Historie z run() renderują się w body i zostałyby dla kolejnego audytu','Bo jsdom tego wymaga','Żeby usunąć style','Bo cleanup nie istnieje','Audyt body nie może widzieć pozostałości poprzedniej historii.');
q('Co daje parametr a11y.test: "error" w Storybook?','Naruszenie axe w historii oznacza nieudany test historii','Ukrywa panel dostępności','Wyłącza axe','Pokazuje tylko ostrzeżenia','Tryb "todo" tylko raportuje.');
q('Po co preview importuje styles.css?','Historie mają wyglądać jak w aplikacji, więc używają tych samych tokenów','Żeby Storybook się uruchomił','Żeby wyłączyć motyw ciemny','Bo CSF wymaga CSS','Inaczej audyt i zrzuty historii nie odpowiadają produkcji.');
q('Jaką wartość ma Storybook dla zespołu poza testami?','Żywą dokumentację wariantów komponentów','Zastępuje backend','Generuje testy E2E','Kompiluje aplikację','To przewodnik stylu w wersji profesjonalnej.');
q('Dlaczego Vitest ma include tylko dla src?','Specyfikacje Playwright w e2e nie mogą być uruchamiane przez Vitest','Bo src jest szybszy','Bo e2e nie istnieje','Żeby pominąć historie','Oba narzędzia mają inne API test().');
q('Dlaczego aria-pressed w WatchToggle?','Przekazuje czytnikowi stan przełącznika','Zmienia kolor','Blokuje kliknięcie','Daje nazwę przyciskowi','Przycisk przełączający ma stan dostępności.');
q('Czy zmiana nazwy przycisku z „Obserwuj” na „Obserwujesz” wystarcza zamiast aria-pressed?','Pomaga, ale aria-pressed jawnie komunikuje stan przełącznika','Tak, aria-pressed jest zbędne zawsze','Nie, nazwa nie może się zmieniać','Tylko w Storybook','Oba sygnały razem są najczytelniejsze.');
q('Dlaczego karta ticketu ma aria-labelledby wskazujące h1?','Sekcja staje się regionem z nazwą, łatwym do znalezienia i zrzutu','Żeby zmienić styl','Bo h1 tego wymaga','Żeby ukryć nagłówek','getByRole("region",{name}) opiera się na tej nazwie.');
q('Kiedy sekcja ma rolę region?','Gdy ma nazwę dostępności','Zawsze','Nigdy','Gdy ma klasę card','Bez nazwy <section> nie jest punktem orientacyjnym.');
q('Który test wykryje usunięcie aria-label z przycisku toastu?','Test axe toastu w Vitest','Test wizualny tabeli','Test resetu danych','Test kontrastu ciemnego motywu','Reguła button-name zgłosi przycisk bez nazwy.');
q('Który test wykryje zmianę --space-3 z 12 na 16 px?','Test wizualny tabeli i karty','Test axe logowania','Test historii plakietki','Test negatywny button-name','Odstępy zmieniają układ, a nie role.');
q('Który test wykryje zbyt jasny --text-muted?','Audyt E2E reguły color-contrast','Test historii WatchToggle','Test jsdom bez stylów','Test liczby wierszy','Kontrast wymaga prawdziwego renderowania.');
q('Który test wykryje biały --surface w ciemnym motywie?','Zrzut i audyt kontrastu w ciemnym motywie','Test w jasnym motywie','Test logowania','Test komponentu w jsdom','Błąd istnieje tylko w jednym motywie.');
q('Dlaczego audyty biegną w obu motywach?','Każdy motyw ma osobne kolory i może łamać kontrast niezależnie','Bo axe wymaga dwóch przebiegów','Żeby przyspieszyć test','Bo jasny motyw nie istnieje','Ciemny motyw to inny zestaw tokenów.');
q('Jak zatwierdzić zamierzoną zmianę wyglądu po przeglądzie różnic?','Uruchomić testy z --update-snapshots i zacommitować nowe wzorce','Usunąć test','Podnieść próg do 50%','Wyłączyć maski','Zmiana wzorców przechodzi przez przegląd kodu.');
q('Gdzie uruchamiać testy wizualne w CI?','W jednym, stałym środowisku (np. kontenerze)','Na dowolnym runnerze','Na komputerach programistów','W jsdom','Wzorce muszą powstawać tam, gdzie są porównywane.');
q('Czy automatyczny audyt zastępuje test klawiaturą i czytnikiem ekranu?','Nie','Tak','Tylko w TSX','Tylko z Storybook','Ręczny audyt pozostaje częścią procesu (rozdział 19).');
q('Co znaczy wynik violations: [] w axe?','Brak wykrytych naruszeń w sprawdzonych regułach','Strona jest w pełni dostępna','Audyt się nie wykonał','Wszystkie reguły pominięto','Brak wykrytych błędów nie dowodzi pełnej dostępności.');
q('Co jeszcze zwraca axe oprócz violations?','Między innymi passes, incomplete i inapplicable','Tylko violations','Zrzut ekranu','Kod CSS','incomplete wymaga ręcznej oceny.');
q('Co oznaczają wyniki incomplete w axe?','Automat nie mógł rozstrzygnąć — potrzebna ocena człowieka','Błąd instalacji','Brak naruszeń','Naruszenia krytyczne','Często dotyczy np. kontrastu tekstu na obrazku.');
q('Dlaczego audyt strony czeka na tabelę lub region przed analyze?','Audyt stanu ładowania nie sprawdziłby docelowego widoku','Bo axe nie działa bez tabeli','Żeby zmienić motyw','Bo AxeBuilder wymaga waitFor','Analizujesz to, co zobaczy użytkownik.');
q('Dlaczego test logowania w E2E ma pusty storageState?','Zalogowany użytkownik zostałby przekierowany z /login lub zobaczyłby inny widok','Bo axe nie lubi ciasteczek','Bo login jest w iframe','Bo setup nie działa','Testujesz stronę w stanie, w jakim widzi ją gość.');
q('Czy Storybook zastępuje testy komponentów w Vitest?','Nie, uzupełnia je; historie mogą być testami','Tak, zawsze','Tylko dla formularzy','Tylko w TS','Portable stories łączą oba światy.');
q('Po co dodatek @storybook/addon-vitest?','Uruchamia historie jako testy przez Vitest','Instaluje React','Generuje CSS','Zastępuje Playwright','W lekcji ten sam efekt dają portable stories.');
q('Czy testy wizualne powinny obejmować każdy ekran?','Nie, wybrane stabilne widoki o dużym ryzyku zmian','Tak, wszystkie','Tylko strony błędów','Tylko formularze','Każdy zrzut to koszt utrzymania wzorca.');
q('Dlaczego dane w teście wizualnym muszą być stałe?','Inne dane dają inny obraz niezależnie od CSS','Bo serwer tego wymaga','Żeby przyspieszyć zrzut','Bo maski nie działają z danymi','Fikstura resetuje bazę przed każdym testem.');
q('Co zrobić, gdy test wizualny różni się tylko na jednym komputerze?','Ustalić stałe środowisko i generować wzorce tylko tam','Podnieść próg','Usunąć wzorzec','Wyłączyć czcionki','Różnice platform to znana cecha zrzutów.');
q('Co wykrywa reguła label?','Pole formularza bez etykiety','Przycisk bez nazwy','Zły kontrast','Brak alt','Etykieta musi być programowo powiązana z polem.');
q('Co wykrywa reguła duplicate-id-aria?','Powtórzone id, do których odwołują się atrybuty ARIA','Brak id','Za długie id','id z polskimi znakami','Powtórzenia psują powiązania aria-labelledby i aria-describedby.');
q('Dlaczego useId jest dobry dla powiązań ARIA?','Daje unikalne id także dla wielu instancji komponentu','Bo jest szybszy','Bo zmienia się co render','Bo axe go wymaga','Stałe id w komponencie wielokrotnego użytku dałoby duplikaty.');
q('Jaki jest minimalny kontrast tekstu normalnego wg WCAG AA?','4.5:1','3:1','7:1','2:1','Duży tekst wymaga 3:1, AAA podnosi wymagania.');
q('Czy mały tekst plakietki (12 px) może mieć kontrast 3:1?','Nie, potrzebuje 4.5:1','Tak, plakietki są wyjątkiem','Tak, bo jest pogrubiony','Tak, w ciemnym motywie','Próg 3:1 dotyczy dużego tekstu.');
q('Co sprawdza historia CloseClick?','Że kliknięcie przycisku-ikony wywołuje onClose dokładnie raz','Że toast ma zielone tło','Że toast znika po 5 s','Że axe jest zainstalowany','Interakcja i kontrakt callbacku w jednej historii.');
q('Dlaczego test historii uruchamia też axe?','Każdy zapisany stan komponentu powinien być dostępny','Bo run() tego wymaga','Żeby zmierzyć czas','Bo Storybook nie ma panelu a11y','Historie to dobry katalog stanów do audytu.');
q('Co robi test negatywny w E2E przez page.evaluate?','Dodaje do strony przycisk bez nazwy i sprawdza, czy axe go zgłosi','Usuwa przyciski ze strony','Zmienia motyw','Wyłącza CSS','Potwierdza, że AxeBuilder rzeczywiście audytuje stronę.');
q('Kiedy test wizualny jest lepszy niż asercja na stylu (toHaveCSS)?','Gdy liczy się całościowy wygląd wielu elementów','Gdy sprawdzasz jedną właściwość koloru','Nigdy','Gdy testujesz logikę','Pojedynczą właściwość sprawdzisz tańszą asercją.');
q('Co oznacza „biały pasek pod nagłówkiem” w ciemnym motywie dla procesu?','Błąd wizualny jednego motywu, który wyłapie zrzut w tym motywie','Błąd logiki','Błąd sieci','Błąd typów','Dlatego zrzuty robimy w obu motywach.');
q('Dlaczego wzorce zrzutów trafiają do repozytorium?','Recenzent widzi zmianę obrazu razem ze zmianą kodu','Bo Playwright nie umie ich generować','Żeby zwiększyć rozmiar repozytorium','Bo CI ich nie potrzebuje','Wzorzec to część oczekiwanego zachowania.');
q('Co powinien zawierać komentarz przy wyjątku exclude?','Powód i odniesienie do zgłoszenia lub decyzji','Datę uruchomienia testu','Nazwę przeglądarki','Nic','Wyjątek bez uzasadnienia szybko staje się regułą.');
q('Jaki jest koszt utrzymania testów wizualnych?','Przegląd i aktualizacja wzorców przy każdej zamierzonej zmianie wyglądu','Brak kosztów','Konieczność pisania CSS w testach','Wyłączenie testów jednostkowych','Dlatego wybierz niewiele stabilnych widoków.');
q('Czym różni się vitest-axe od @axe-core/playwright?','Pierwszy audytuje DOM w jsdom w teście komponentu, drugi prawdziwą stronę w przeglądarce','Niczym','vitest-axe sprawdza kontrast lepiej','@axe-core/playwright działa tylko w Storybooku','Oba używają axe-core, ale w innym środowisku renderowania.');
q('Dlaczego test axe w jsdom nadal ma sens, skoro nie sprawdza kontrastu?','Szybko łapie brak nazw, etykiet i błędy ARIA już na poziomie komponentu','Bo sprawdza też układ','Bo zastępuje E2E','Nie ma sensu','Tani test blisko kodu wykrywa najczęstsze problemy.');
q('Co zrobić z naruszeniem znalezionym przez axe w komponencie bibliotecznym?','Poprawić komponent, aby wszystkie użycia skorzystały z poprawki','Wyłączyć regułę w każdym teście','Usunąć test','Dodać waitForTimeout','Naprawa u źródła usuwa problem w całej aplikacji.');
q('Dlaczego audyt historii w Vitest wyłącza regułę region?','Historia to fragment strony bez punktów orientacyjnych; regułę sprawdza audyt całych stron','Bo region nie istnieje w axe 4','Żeby test był szybszy','Bo Storybook blokuje tę regułę na stałe','Wyłączenie jest świadome, opisane i ograniczone do audytu fragmentów.');
q('Co oznacza tag wcag2aa w axe?','Reguły odpowiadające poziomowi AA standardu WCAG 2.0','Reguły eksperymentalne','Tylko kontrast','Najlepsze praktyki spoza WCAG','Tagi pozwalają dobrać zakres audytu do wymagań.');
q('Dlaczego zrzut karty nie ma maski?','Karta nie zawiera danych zmiennych w czasie','Bo maski nie działają w regionach','Bo karta jest zawsze jasna','Bo próg jest wysoki','Maskuj tylko to, co naprawdę się zmienia.');
const codeQuestions=[
  ['Co jest problemem tego audytu?','Wyłącza kontrast dla całej strony, więc test niczego już nie pilnuje w tym zakresie','Za mało tagów WCAG','Brak await przed goto','Zły import AxeBuilder','Wyjątek ma dotyczyć konkretnego elementu przez exclude.',String.raw`import AxeBuilder from "@axe-core/playwright";
import {expect,test} from "@playwright/test";
test("audyt",async({page})=>{
  await page.goto("/tickets");
  const results=await new AxeBuilder({page}).disableRules(["color-contrast"]).analyze();
  expect(results.violations).toEqual([]);
});`],
  ['Dlaczego ten test wizualny będzie niestabilny?','Zawiera zmienną godzinę bez maski i nie czeka na dane','Bo używa toHaveScreenshot','Bo motyw jest jasny','Bo próg jest zbyt niski','Zamaskuj time i poczekaj na gotowy widok.',String.raw`import {expect,test} from "@playwright/test";
test("tabela",async({page})=>{
  await page.goto("/tickets");
  await expect(page.getByRole("table")).toHaveScreenshot("tabela.png");
});`],
  ['Co jest złego w tym progu?','Dopuszcza 30% różnic, więc nie wykryje realnych zmian','Jest zbyt surowy','Wyłącza maski','Wymaga ciemnego motywu','Stabilizuj środowisko zamiast podnosić tolerancję.',String.raw`import {expect,test} from "@playwright/test";
test("karta",async({page})=>{
  await page.goto("/tickets/42");
  await expect(page.getByRole("region")).toHaveScreenshot("karta.png",{maxDiffPixelRatio:0.3});
});`],
  ['Co zgłosi axe dla tego komponentu?','Naruszenie button-name — przycisk nie ma nazwy','color-contrast','label','Brak naruszeń','SVG z aria-hidden nie daje nazwy; potrzebny aria-label.',String.raw`export function CloseButton({onClose}:{onClose:()=>void}){
  return <button type="button" onClick={onClose}><svg aria-hidden="true" width="10" height="10"/></button>;
}`],
  ['Co robi ta historia?','Klika przycisk i sprawdza, że onClose wywołano raz','Robi zrzut ekranu toastu','Wyłącza audyt','Renderuje toast bez args','play wykonuje interakcję na wyrenderowanej historii.',String.raw`import type {Meta,StoryObj} from "@storybook/react-vite";
import {expect,fn,userEvent,within} from "storybook/test";
import {Toast} from "./Toast";
const meta={component:Toast,args:{children:"Zapisano",onClose:fn()}} satisfies Meta<typeof Toast>;
export default meta;
export const CloseClick:StoryObj<typeof meta>={play:async({canvasElement,args})=>{
  await userEvent.click(within(canvasElement).getByRole("button",{name:"Zamknij powiadomienie"}));
  await expect(args.onClose).toHaveBeenCalledTimes(1);
}};`],
  ['Co sprawdza ten test Vitest?','Uruchamia wszystkie historie plakietki wraz z play i audytuje je axe','Uruchamia Storybook w przeglądarce','Robi zrzuty ekranu historii','Tylko importuje historie','composeStories + run() to portable stories.',String.raw`import {composeStories} from "@storybook/react";
import {axe} from "vitest-axe";
import {expect,test} from "vitest";
import * as stories from "./StatusBadge.stories";
for(const [name,Story] of Object.entries(composeStories(stories)))test(name,async()=>{
  await Story.run();
  expect((await axe(document.body)).violations).toEqual([]);
});`],
  ['Dlaczego ten audyt może przegapić problem w tabeli?','Analizuje stronę przed załadowaniem danych','Bo brak tagów','Bo używa dark','Bo axe nie sprawdza tabel','Najpierw poczekaj na docelowy widok.',String.raw`import AxeBuilder from "@axe-core/playwright";
import {expect,test} from "@playwright/test";
test("lista",async({page})=>{
  await page.goto("/tickets");
  expect((await new AxeBuilder({page}).analyze()).violations).toEqual([]);
});`],
  ['Co sprawdza ten fragment?','Audyt w ciemnym motywie strony szczegółów','Zrzut w jasnym motywie','Logowanie','Tylko kolor tła','emulateMedia przed goto ustawia prefers-color-scheme.',String.raw`import AxeBuilder from "@axe-core/playwright";
import {expect,test} from "@playwright/test";
test("dark",async({page})=>{
  await page.emulateMedia({colorScheme:"dark"});
  await page.goto("/tickets/42");
  await page.getByRole("region").waitFor();
  expect((await new AxeBuilder({page}).withTags(["wcag2aa"]).analyze()).violations).toEqual([]);
});`],
  ['Dlaczego ta asercja daje czytelny raport?','Mapuje naruszenia na „id: liczba elementów”','Bo ukrywa szczegóły','Bo porównuje liczby','Bo usuwa duplikaty','Raport mówi od razu, którą regułę i ile elementów poprawić.',String.raw`import AxeBuilder from "@axe-core/playwright";
import {expect,test} from "@playwright/test";
test("raport",async({page})=>{
  await page.goto("/tickets");
  const results=await new AxeBuilder({page}).analyze();
  expect(results.violations.map(v=>v.id+": "+v.nodes.length+" el.")).toEqual([]);
});`],
  ['Co zrobi ten test komponentu, gdy pole nie ma etykiety?','Nie przejdzie — axe zgłosi regułę label','Przejdzie, bo jsdom nie ma etykiet','Rzuci błąd kompilacji','Pominie pole','Etykieta to warunek reguły label.',String.raw`import {render} from "@testing-library/react";
import {axe} from "vitest-axe";
import {expect,test} from "vitest";
test("pole",async()=>{
  const {container}=render(<form><input name="title"/></form>);
  expect((await axe(container)).violations.map(v=>v.id)).toEqual([]);
});`],
];
for(const [question,answer,b,c,d,explanation,code] of codeQuestions)q(question,answer,b,c,d,explanation,code);
if(exercises.length!==30||quiz.length!==100)throw Error('Niepełny zestaw '+exercises.length+'/'+quiz.length);
const manifest=JSON.parse(base.project['package.json']);
manifest.scripts={...manifest.scripts,test:'vitest run',storybook:'storybook dev -p 6006'};
Object.assign(manifest.devDependencies,{'@axe-core/playwright':'4.14.0','axe-core':'4.14.0','vitest-axe':'0.1.0',vitest:'4.1.12',jsdom:'27.4.0','@testing-library/react':'16.3.3','@testing-library/dom':'10.4.2','@testing-library/jest-dom':'6.9.1',storybook:'10.6.6','@storybook/react':'10.6.6','@storybook/react-vite':'10.6.6'});
const keep=['.gitignore','index.html','server.mjs','src/api.ts','src/LoginPage.tsx','src/NewTicketDialog.tsx','src/App.tsx','playwright.config.ts','e2e/auth.setup.ts','e2e/fixtures.ts'];
const project={...Object.fromEntries(keep.map(file=>[file,base.project[file]])),
  'package.json':JSON.stringify(manifest,null,2)+'\n',
  'src/styles.css':styles,'src/StatusBadge.tsx':badge,'src/Toast.tsx':toast,'src/WatchToggle.tsx':watch,'src/TicketsPage.tsx':listPage,'src/TicketDetails.tsx':detailsPage,'src/main.tsx':main,
  'vitest.config.ts':vitestConfig,'src/testSetup.ts':testSetup,'src/a11y.test.tsx':a11yTests,
  'src/StatusBadge.stories.tsx':badgeStories,'src/Toast.stories.tsx':toastStories,'src/WatchToggle.stories.tsx':watchStories,'src/stories.test.tsx':storyTests,
  '.storybook/main.ts':sbMain,'.storybook/preview.ts':sbPreview,
  'e2e/a11y.spec.ts':a11ySpec,'e2e/visual.spec.ts':visualSpec,
  'README.md':'DevDesk: dostępność i wygląd. npm run test (Vitest: axe w jsdom i historie Storybooka przez composeStories), npm run test:e2e (Playwright: axe na prawdziwych stronach w obu motywach i zrzuty ekranu). Wzorce zrzutów powstają poleceniem npx playwright test --update-snapshots w tym samym środowisku, w którym są porównywane. Audyt automatyczny nie zastępuje testu klawiaturą i czytnikiem ekranu. Weryfikacja lekcji uruchamia Chromium (Playwright 1.64.1, axe-core 4.14.0, Storybook 10.6.6).\n'};
const mutants=[
  {suite:'vitest',file:'src/Toast.tsx',before:' aria-label="Zamknij powiadomienie"',after:''},
  {suite:'e2e',file:'src/styles.css',before:'--text-muted:#59636e',after:'--text-muted:#a0a8b0'},
  {suite:'e2e',file:'src/styles.css',before:'--space-3:12px',after:'--space-3:16px'},
  {suite:'e2e',file:'src/styles.css',before:'--surface:#151b23',after:'--surface:#ffffff'},
];
module.exports={styles,badge,toast,watch,listPage,detailsPage,main,vitestConfig,testSetup,a11yTests,badgeStories,toastStories,watchStories,storyTests,sbMain,sbPreview,a11ySpec,visualSpec,exercises,quiz,project,mutants,e2eTests:11,vitestTests:10,visual:true};
