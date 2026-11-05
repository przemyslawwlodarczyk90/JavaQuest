// Boss 14/09: testy do cudzego komponentu AssignDialog. Ta sama specyfikacja działa dla oryginału i wersji na TanStack Query.
const base=require('./practice_test_06.cjs');
const dialog=String.raw`import {useEffect,useRef,useState} from "react";
export type UserHit={login:string;name:string};
export type AssignDialogProps={ticketId:number;onAssigned:(login:string)=>void};
export function AssignDialog({ticketId,onAssigned}:AssignDialogProps){
  const [open,setOpen]=useState(false);
  const [q,setQ]=useState("");
  const [users,setUsers]=useState<UserHit[]>([]);
  const [active,setActive]=useState(0);
  const [selected,setSelected]=useState<UserHit|null>(null);
  const [saving,setSaving]=useState(false);
  const [error,setError]=useState<string|null>(null);
  const openerRef=useRef<HTMLButtonElement>(null);
  useEffect(()=>{
    if(q.trim().length<2){setUsers([]);return;}
    const controller=new AbortController();
    const id=setTimeout(()=>{
      fetch("/api/users?q="+encodeURIComponent(q.trim()),{signal:controller.signal})
        .then(r=>r.json() as Promise<UserHit[]>).then(list=>{setUsers(list);setActive(0);}).catch(()=>{});
    },250);
    return ()=>{clearTimeout(id);controller.abort();};
  },[q]);
  function close(){setOpen(false);setQ("");setSelected(null);setError(null);openerRef.current?.focus();}
  async function save(){
    if(!selected)return;
    setSaving(true);setError(null);
    const res=await fetch("/api/tickets/"+ticketId,{method:"PATCH",headers:{"Content-Type":"application/json"},body:JSON.stringify({assignee:selected.login})});
    setSaving(false);
    if(!res.ok){setError("Nie udało się przypisać ticketu.");return;}
    onAssigned(selected.login);close();
  }
  return <>
    <button ref={openerRef} type="button" onClick={()=>setOpen(true)}>Przypisz…</button>
    {open&&<div role="dialog" aria-modal="true" aria-label="Przypisz ticket" onKeyDown={e=>{if(e.key==="Escape")close();}}>
      <label htmlFor="assign-q">Szukaj osoby</label>
      <input id="assign-q" autoFocus value={q} onChange={e=>setQ(e.target.value)} onKeyDown={e=>{
        if(e.key==="ArrowDown")setActive(i=>Math.min(i+1,users.length-1));
        if(e.key==="ArrowUp")setActive(i=>Math.max(i-1,0));
        if(e.key==="Enter"&&users[active])setSelected(users[active]);
      }}/>
      {q.trim().length<2&&<p>Wpisz co najmniej 2 znaki</p>}
      <ul role="listbox" aria-label="Wyniki">
        {users.map((u,i)=><li key={u.login} role="option" aria-selected={selected?.login===u.login} className={i===active?"active":""} onClick={()=>setSelected(u)}>{u.name}</li>)}
      </ul>
      {error&&<p role="alert">{error}</p>}
      <button type="button" disabled={!selected||saving} onClick={()=>void save()}>{saving?"Zapisywanie…":"Przypisz"}</button>
    </div>}
  </>;
}`;
const refactor=String.raw`import {useMutation,useQuery} from "@tanstack/react-query";
import {useEffect,useId,useRef,useState} from "react";
import type {AssignDialogProps,UserHit} from "./AssignDialog";
function useDebounced<T>(value:T,ms:number){
  const [debounced,setDebounced]=useState(value);
  useEffect(()=>{const id=setTimeout(()=>setDebounced(value),ms);return ()=>clearTimeout(id);},[value,ms]);
  return debounced;
}
// Refaktoryzacja zaplanowana przez zespół: TanStack Query zamiast ręcznego fetch, aria-activedescendant dla klawiatury.
export function AssignDialogQuery({ticketId,onAssigned}:AssignDialogProps){
  const [open,setOpen]=useState(false),[q,setQ]=useState(""),[active,setActive]=useState(0),[selected,setSelected]=useState<UserHit|null>(null);
  const openerRef=useRef<HTMLButtonElement>(null),prefix=useId();
  const term=useDebounced(q.trim(),250),enabled=open&&q.trim().length>=2&&term.length>=2;
  const query=useQuery({queryKey:["users",term],enabled,queryFn:async({signal})=>{
    const response=await fetch("/api/users?q="+encodeURIComponent(term),{signal});
    return await response.json() as UserHit[];
  }});
  const users=enabled?query.data??[]:[];
  useEffect(()=>{setActive(0);},[query.data]);
  const assign=useMutation({
    mutationFn:async(login:string)=>{
      const response=await fetch("/api/tickets/"+ticketId,{method:"PATCH",headers:{"Content-Type":"application/json"},body:JSON.stringify({assignee:login})});
      if(!response.ok)throw new Error("HTTP "+response.status);
      return login;
    },
    onSuccess:login=>{onAssigned(login);close();},
  });
  function close(){setOpen(false);setQ("");setSelected(null);assign.reset();openerRef.current?.focus();}
  const optionId=(index:number)=>prefix+"-option-"+index;
  return <>
    <button ref={openerRef} type="button" onClick={()=>setOpen(true)}>Przypisz…</button>
    {open&&<div role="dialog" aria-modal="true" aria-label="Przypisz ticket" onKeyDown={e=>{if(e.key==="Escape")close();}}>
      <label htmlFor={prefix+"-q"}>Szukaj osoby</label>
      <input id={prefix+"-q"} autoFocus value={q} aria-controls={prefix+"-list"} aria-activedescendant={users[active]?optionId(active):undefined} onChange={e=>setQ(e.target.value)} onKeyDown={e=>{
        if(e.key==="ArrowDown")setActive(i=>Math.min(i+1,users.length-1));
        if(e.key==="ArrowUp")setActive(i=>Math.max(i-1,0));
        if(e.key==="Enter"&&users[active])setSelected(users[active]);
      }}/>
      {q.trim().length<2&&<p>Wpisz co najmniej 2 znaki</p>}
      <ul id={prefix+"-list"} role="listbox" aria-label="Wyniki">
        {users.map((u,i)=><li key={u.login} id={optionId(i)} role="option" aria-selected={selected?.login===u.login} onClick={()=>setSelected(u)}>{u.name}</li>)}
      </ul>
      {assign.isError&&<p role="alert">Nie udało się przypisać ticketu.</p>}
      <button type="button" disabled={!selected||assign.isPending} onClick={()=>{if(selected)assign.mutate(selected.login);}}>{assign.isPending?"Zapisywanie…":"Przypisz"}</button>
    </div>}
  </>;
}`;
const handlers=String.raw`import {delay,http,HttpResponse} from "msw";
import type {UserHit} from "./AssignDialog";
export const people:UserHit[]=[{login:"anna",name:"Anna Nowak"},{login:"andrzej",name:"Andrzej Lis"},{login:"ola",name:"Ola Kowal"}];
// Domyślne handlery boss: wyszukiwanie po imieniu lub loginie i zapis przypisania.
export const assignHandlers=[
  http.get("/api/users",async({request})=>{
    const q=(new URL(request.url).searchParams.get("q")??"").toLowerCase();
    await delay(10);
    return HttpResponse.json(people.filter(p=>p.name.toLowerCase().includes(q)||p.login.includes(q)));
  }),
  http.patch("/api/tickets/:id",async({request,params})=>{
    const body=await request.json() as {assignee?:string};
    return HttpResponse.json({id:Number(params.id),assignee:body.assignee??null});
  }),
];`;
const renderHelper=String.raw`import {QueryClient,QueryClientProvider} from "@tanstack/react-query";
import {render} from "@testing-library/react";
import userEvent from "@testing-library/user-event";
import {vi} from "vitest";
import type {ComponentType} from "react";
import type {AssignDialogProps} from "./AssignDialog";
// Wspólny render dla obu implementacji: provider nie szkodzi wersji z fetch, a jest wymagany przez wersję z TanStack Query.
export function renderAssign(Dialog:ComponentType<AssignDialogProps>,options:Parameters<typeof userEvent.setup>[0]={}){
  const onAssigned=vi.fn<(login:string)=>void>();
  const client=new QueryClient({defaultOptions:{queries:{retry:false},mutations:{retry:false}}});
  const user=userEvent.setup(options);
  render(<QueryClientProvider client={client}><Dialog ticketId={42} onAssigned={onAssigned}/></QueryClientProvider>);
  return {user,onAssigned};
}`;
const spec=String.raw`import {act,screen,waitFor,within} from "@testing-library/react";
import {http,HttpResponse} from "msw";
import {axe} from "vitest-axe";
import {describe,expect,test,vi} from "vitest";
import type {ComponentType} from "react";
import type {AssignDialogProps} from "./AssignDialog";
import {server} from "./mswServer";
import {renderAssign} from "./renderAssign";
type Sent={id:string;body:unknown};
function capturePatches(){
  const sent:Sent[]=[];
  server.use(http.patch("/api/tickets/:id",async({request,params})=>{sent.push({id:String(params.id),body:await request.json()});return HttpResponse.json({id:Number(params.id)});}));
  return sent;
}
const saveButton=()=>within(screen.getByRole("dialog",{name:"Przypisz ticket"})).getByRole("button",{name:/^(Przypisz|Zapisywanie…)$/});
// Specyfikacja zachowania z briefu. Nazwy testów czytane po kolei opisują komponent.
export function describeAssignDialog(name:string,Dialog:ComponentType<AssignDialogProps>){
  describe(name,()=>{
    async function openAndSearch(text="an"){
      const rendered=renderAssign(Dialog);
      await rendered.user.click(screen.getByRole("button",{name:"Przypisz…"}));
      await rendered.user.type(screen.getByLabelText("Szukaj osoby"),text);
      return rendered;
    }
    test("otwiera się przyciskiem Przypisz… z fokusem w polu wyszukiwania",async()=>{
      const {user}=renderAssign(Dialog);
      expect(screen.queryByRole("dialog")).not.toBeInTheDocument();
      await user.click(screen.getByRole("button",{name:"Przypisz…"}));
      expect(screen.getByRole("dialog",{name:"Przypisz ticket"})).toBeInTheDocument();
      expect(screen.getByLabelText("Szukaj osoby")).toHaveFocus();
    });
    test("poniżej 2 znaków pokazuje podpowiedź i nie pyta API",async()=>{
      vi.useFakeTimers({shouldAdvanceTime:true});
      try{
        let calls=0;
        server.use(http.get("/api/users",()=>{calls+=1;return HttpResponse.json([]);}));
        const {user}=renderAssign(Dialog,{advanceTimers:vi.advanceTimersByTime});
        await user.click(screen.getByRole("button",{name:"Przypisz…"}));
        await user.type(screen.getByLabelText("Szukaj osoby"),"a");
        expect(screen.getByText("Wpisz co najmniej 2 znaki")).toBeInTheDocument();
        await act(()=>vi.advanceTimersByTimeAsync(1000));
        expect(calls).toBe(0);
        expect(screen.queryAllByRole("option")).toHaveLength(0);
      }finally{vi.useRealTimers();}
    });
    test("od 2 znaków pokazuje wyniki wyszukiwania z API",async()=>{
      const seen:string[]=[];
      server.events.on("request:start",({request})=>{if(request.url.includes("/api/users"))seen.push(new URL(request.url).search);});
      try{
        await openAndSearch("an");
        const options=await screen.findAllByRole("option");
        expect(options.map(option=>option.textContent)).toEqual(["Anna Nowak","Andrzej Lis"]);
        expect(screen.queryByText("Wpisz co najmniej 2 znaki")).not.toBeInTheDocument();
        expect(seen).toEqual(["?q=an"]);
      }finally{server.events.removeAllListeners();}
    });
    test("Przypisz jest zablokowane, dopóki nikt nie jest wybrany",async()=>{
      await openAndSearch("an");
      await screen.findAllByRole("option");
      expect(saveButton()).toBeDisabled();
    });
    test("kliknięcie osoby zaznacza ją i odblokowuje Przypisz",async()=>{
      const {user}=await openAndSearch("an");
      await user.click(await screen.findByRole("option",{name:"Andrzej Lis"}));
      expect(screen.getByRole("option",{name:"Andrzej Lis"})).toHaveAttribute("aria-selected","true");
      expect(screen.getByRole("option",{name:"Anna Nowak"})).toHaveAttribute("aria-selected","false");
      expect(saveButton()).toBeEnabled();
    });
    test("strzałka w dół i Enter wybierają osobę z klawiatury",async()=>{
      const {user}=await openAndSearch("an");
      await screen.findAllByRole("option");
      await user.keyboard("{ArrowDown}{Enter}");
      expect(screen.getByRole("option",{name:"Andrzej Lis"})).toHaveAttribute("aria-selected","true");
    });
    test("strzałka w dół zatrzymuje się na ostatniej osobie",async()=>{
      const {user}=await openAndSearch("an");
      await screen.findAllByRole("option");
      await user.keyboard("{ArrowDown}{ArrowDown}{ArrowDown}{Enter}");
      expect(screen.getByRole("option",{name:"Andrzej Lis"})).toHaveAttribute("aria-selected","true");
    });
    test("strzałka w górę nie schodzi przed pierwszą osobę",async()=>{
      const {user}=await openAndSearch("an");
      await screen.findAllByRole("option");
      await user.keyboard("{ArrowUp}{ArrowUp}{Enter}");
      expect(screen.getByRole("option",{name:"Anna Nowak"})).toHaveAttribute("aria-selected","true");
    });
    test("Przypisz wysyła PATCH z assignee, wywołuje onAssigned, zamyka okno i oddaje fokus",async()=>{
      const sent=capturePatches();
      const {user,onAssigned}=await openAndSearch("an");
      await user.click(await screen.findByRole("option",{name:"Andrzej Lis"}));
      await user.click(saveButton());
      await waitFor(()=>expect(screen.queryByRole("dialog")).not.toBeInTheDocument());
      expect(sent).toEqual([{id:"42",body:{assignee:"andrzej"}}]);
      expect(onAssigned).toHaveBeenCalledExactlyOnceWith("andrzej");
      expect(screen.getByRole("button",{name:"Przypisz…"})).toHaveFocus();
    });
    test("podczas zapisu przycisk jest zablokowany",async()=>{
      let release!:()=>void;const gate=new Promise<void>(resolve=>{release=resolve;});
      server.use(http.patch("/api/tickets/:id",async()=>{await gate;return HttpResponse.json({id:42});}));
      const {user}=await openAndSearch("an");
      await user.click(await screen.findByRole("option",{name:"Anna Nowak"}));
      await user.click(saveButton());
      expect(await screen.findByRole("button",{name:"Zapisywanie…"})).toBeDisabled();
      release();
      await waitFor(()=>expect(screen.queryByRole("dialog")).not.toBeInTheDocument());
    });
    test("błąd zapisu pokazuje komunikat, zostawia okno i nie woła onAssigned",async()=>{
      server.use(http.patch("/api/tickets/:id",()=>HttpResponse.json({message:"Błąd"},{status:500})));
      const {user,onAssigned}=await openAndSearch("an");
      await user.click(await screen.findByRole("option",{name:"Anna Nowak"}));
      await user.click(saveButton());
      expect(await screen.findByRole("alert")).toHaveTextContent("Nie udało się przypisać ticketu.");
      expect(screen.getByRole("dialog",{name:"Przypisz ticket"})).toBeInTheDocument();
      expect(saveButton()).toBeEnabled();
      expect(onAssigned).not.toHaveBeenCalled();
    });
    test("Escape zamyka okno bez zapisu i oddaje fokus na Przypisz…",async()=>{
      const sent=capturePatches();
      const {user,onAssigned}=await openAndSearch("an");
      await screen.findAllByRole("option");
      // Wybór klawiaturą: fokus zostaje w polu (kliknięcie <li> wyrzuca fokus z okna — patrz zgłoszenia).
      await user.keyboard("{Enter}");
      expect(screen.getByRole("option",{name:"Anna Nowak"})).toHaveAttribute("aria-selected","true");
      await user.keyboard("{Escape}");
      expect(screen.queryByRole("dialog")).not.toBeInTheDocument();
      expect(screen.getByRole("button",{name:"Przypisz…"})).toHaveFocus();
      expect(sent).toEqual([]);
      expect(onAssigned).not.toHaveBeenCalled();
    });
    test("po ponownym otwarciu pole jest puste, a wybór wyczyszczony",async()=>{
      const {user}=await openAndSearch("an");
      await screen.findAllByRole("option");
      await user.keyboard("{Enter}{Escape}");
      await user.click(screen.getByRole("button",{name:"Przypisz…"}));
      expect(screen.getByLabelText("Szukaj osoby")).toHaveValue("");
      expect(screen.getByText("Wpisz co najmniej 2 znaki")).toBeInTheDocument();
      expect(screen.queryAllByRole("option")).toHaveLength(0);
    });
    test("wyścig: spóźniona odpowiedź dla „an” nie nadpisuje wyników dla „ann”",async()=>{
      let releaseOld!:()=>void,oldStarted=false,oldDone=false;
      const oldGate=new Promise<void>(resolve=>{releaseOld=resolve;});
      server.use(http.get("/api/users",async({request})=>{
        const q=new URL(request.url).searchParams.get("q");
        if(q==="an"){oldStarted=true;await oldGate;oldDone=true;return HttpResponse.json([{login:"anna",name:"Anna Nowak"},{login:"andrzej",name:"Andrzej Lis"}]);}
        return HttpResponse.json([{login:"anna",name:"Anna Nowak"}]);
      }));
      const {user}=renderAssign(Dialog);
      await user.click(screen.getByRole("button",{name:"Przypisz…"}));
      const input=screen.getByLabelText("Szukaj osoby");
      await user.type(input,"an");
      // Czekamy, aż żądanie dla „an” naprawdę dotrze do handlera (po 250 ms opóźnienia), dopiero potem dopisujemy „n”.
      await waitFor(()=>expect(oldStarted).toBe(true));
      await user.type(input,"n");
      expect(await screen.findByRole("option",{name:"Anna Nowak"})).toBeInTheDocument();
      await waitFor(()=>expect(screen.getAllByRole("option")).toHaveLength(1));
      releaseOld();
      await waitFor(()=>expect(oldDone).toBe(true));
      // Punkt odniesienia: stara odpowiedź została wysłana; jeden obrót pętli zdarzeń na ewentualną aktualizację.
      await act(()=>new Promise(resolve=>setTimeout(resolve,20)));
      expect(screen.getAllByRole("option").map(option=>option.textContent)).toEqual(["Anna Nowak"]);
    });
    test("otwarte okno z wynikami nie ma naruszeń axe poza znanymi zgłoszeniami",async()=>{
      await openAndSearch("an");
      await screen.findAllByRole("option");
      const results=await axe(document.body,{rules:{region:{enabled:false}}});
      expect(results.violations.map(violation=>violation.id)).toEqual([]);
    });
  });
}`;
const testOriginal=String.raw`// @vitest-environment jsdom
import {AssignDialog} from "./AssignDialog";
import {describeAssignDialog} from "./assignDialogSpec";
describeAssignDialog("AssignDialog (oryginał z fetch)",AssignDialog);`;
const testRefactor=String.raw`// @vitest-environment jsdom
import {AssignDialogQuery} from "./AssignDialogQuery";
import {describeAssignDialog} from "./assignDialogSpec";
// Kryterium 9: te same testy, bez zmian, dla komponentu po refaktoryzacji.
describeAssignDialog("AssignDialog (po refaktoryzacji na TanStack Query)",AssignDialogQuery);`;
const todoList=String.raw`import {describe,test} from "vitest";
// Krok 1: specyfikacja w nazwach, zanim powstanie kod testów.
describe("AssignDialog — specyfikacja",()=>{
  test.todo("otwiera się przyciskiem Przypisz… z fokusem w polu wyszukiwania");
  test.todo("poniżej 2 znaków pokazuje podpowiedź i nie pyta API");
  test.todo("od 2 znaków pokazuje wyniki wyszukiwania z API");
  test.todo("Przypisz jest zablokowane, dopóki nikt nie jest wybrany");
  test.todo("kliknięcie osoby zaznacza ją i odblokowuje Przypisz");
  test.todo("strzałka w dół i Enter wybierają osobę z klawiatury");
  test.todo("strzałka w dół zatrzymuje się na ostatniej osobie");
  test.todo("strzałka w górę nie schodzi przed pierwszą osobę");
  test.todo("Przypisz wysyła PATCH z assignee, wywołuje onAssigned, zamyka okno i oddaje fokus");
  test.todo("podczas zapisu przycisk jest zablokowany");
  test.todo("błąd zapisu pokazuje komunikat, zostawia okno i nie woła onAssigned");
  test.todo("Escape zamyka okno bez zapisu i oddaje fokus na Przypisz…");
  test.todo("wyścig: spóźniona odpowiedź dla „an” nie nadpisuje wyników dla „ann”");
  test.todo("otwarte okno z wynikami nie ma naruszeń axe");
});`;
const report=String.raw`# Zgłoszenia dla zespołu po przeglądzie AssignDialog

1. Brak aria-activedescendant: strzałki zmieniają aktywną osobę tylko klasą CSS „active”; czytnik ekranu nie ogłasza kandydata. (Wersja po refaktoryzacji wiąże pole z listą przez aria-controls i aria-activedescendant.)
2. Elementy option reagują tylko na kliknięcie myszą; obsługa klawiatury istnieje wyłącznie w polu wyszukiwania — to akceptowalny wzorzec comboboxa, ale pole powinno mieć role="combobox" i aria-expanded.
3. Dialog nie zatrzymuje fokusu (focus trap): Tab wychodzi poza okno mimo aria-modal="true".
4. Stałe id="assign-q": dwa okna na jednej stronie dadzą duplikat id i zepsute powiązanie etykiety (useId rozwiązuje problem).
5. Błąd sieci przy wyszukiwaniu jest cicho połykany (.catch(()=>{})); użytkownik widzi pustą listę zamiast komunikatu.
6. Błąd sieci przy zapisie (odrzucony fetch) zostawia saving=true — przycisk zostaje zablokowany na zawsze.
7. Kliknięcie osoby myszą przenosi fokus na body (<li> nie przyjmuje fokusu), więc Escape przestaje zamykać okno — wykryte podczas pisania testów. Poprawka: onMouseDown z preventDefault na opcji albo przeniesienie obsługi Escape na document, gdy okno jest otwarte.`;
const mutantList=String.raw`// Pięć celowych błędów z briefu: każdy musi zostać wykryty przez co najmniej jeden test.
export const mutants=[
  {opis:"brak controller.abort() — wyścig odpowiedzi",przed:"return ()=>{clearTimeout(id);controller.abort();};",po:"return ()=>{clearTimeout(id);};",wykrywa:"wyścig: spóźniona odpowiedź dla „an”…"},
  {opis:"brak ograniczenia Math.min przy strzałce w dół",przed:"Math.min(i+1,users.length-1)",po:"i+1",wykrywa:"strzałka w dół zatrzymuje się na ostatniej osobie"},
  {opis:"brak przywrócenia fokusu",przed:"openerRef.current?.focus();",po:"",wykrywa:"Escape zamyka okno… oddaje fokus"},
  {opis:"pominięte sprawdzenie !res.ok",przed:"if(!res.ok){setError(\"Nie udało się przypisać ticketu.\");return;}",po:"",wykrywa:"błąd zapisu pokazuje komunikat…"},
  {opis:"zmieniony tekst podpowiedzi",przed:"Wpisz co najmniej 2 znaki",po:"Wpisz min. 2 znaki",wykrywa:"poniżej 2 znaków pokazuje podpowiedź…"},
];`;
const exercises=[
  ['Przepisz komponent AssignDialog z briefu do TSX bez zmiany zachowania.','Typy UserHit i AssignDialogProps są publicznym kontraktem.',dialog],
  ['Zacznij od specyfikacji: wypisz zachowania z briefu jako test.todo.','Nazwy testów czytane po kolei mają tworzyć opis komponentu.',todoList],
  ['Przygotuj domyślne handlery MSW: wyszukiwanie osób i zapis przypisania.','Wyszukiwanie filtruje po imieniu lub loginie.',handlers],
  ['Napisz render pomocniczy dla dowolnej implementacji okna z providerem TanStack Query.','Przyjmij komponent jako argument, żeby testy działały dla obu wersji.',renderHelper],
  ['Sprawdź otwarcie przyciskiem Przypisz… i fokus w polu wyszukiwania.','Okno nie istnieje przed kliknięciem.',spec],
  ['Sprawdź podpowiedź poniżej 2 znaków i brak żądania do API przy fałszywych zegarach.','userEvent.setup({advanceTimers}) — inaczej wpisywanie się zawiesi.',spec],
  ['Sprawdź wyniki wyszukiwania i parametr q wysłany do API.','server.events pozwala obserwować żądania bez zmiany handlera.',spec],
  ['Sprawdź blokadę przycisku Przypisz bez wyboru osoby.','Zawęź wyszukiwanie przycisku do dialogu.',spec],
  ['Sprawdź wybór myszą przez aria-selected, a nie klasę active.','Sprawdź też, że druga osoba nie jest zaznaczona.',spec],
  ['Sprawdź wybór klawiaturą: strzałka w dół i Enter.','user.keyboard przyjmuje sekwencję klawiszy.',spec],
  ['Sprawdź, że strzałka w dół zatrzymuje się na ostatniej osobie.','Trzy strzałki przy dwóch wynikach wykrywają brak ograniczenia.',spec],
  ['Sprawdź, że strzałka w górę nie schodzi przed pierwszą osobę.','Enter po dwóch strzałkach w górę wybiera pierwszą osobę.',spec],
  ['Przechwyć PATCH w handlerze i sprawdź ciało, onAssigned, zamknięcie i fokus.','Asercje rób w teście, w handlerze tylko zbieraj dane.',spec],
  ['Sprawdź blokadę przycisku podczas zapisu bramką Promise.','Zwolnij bramkę i poczekaj na zamknięcie okna.',spec],
  ['Sprawdź błąd zapisu: komunikat, otwarte okno, brak onAssigned.','Test błędu bez sprawdzenia otwartego okna jest niepełny.',spec],
  ['Sprawdź Escape: zamknięcie bez zapisu i fokus na Przypisz….','Pusta lista przechwyconych PATCH to dowód braku zapisu.',spec],
  ['Sprawdź reset pola i wyboru po ponownym otwarciu okna.','Zachowanie close() jest częścią kontraktu.',spec],
  ['Napisz test wyścigu „an” → „ann” ze spóźnioną odpowiedzią dla „an”.','Wstrzymaj starą odpowiedź bramką i zwolnij ją po wynikach dla „ann”.',spec],
  ['Dodaj audyt axe otwartego okna z wynikami.','Regułę region wyłącz świadomie — okno jest fragmentem strony.',spec],
  ['Uruchom tę samą specyfikację dla oryginalnego komponentu.','Jedna funkcja describeAssignDialog, dwa pliki testów.',testOriginal],
  ['Przepisz komponent na TanStack Query z debounce i aria-activedescendant.','Klucz zapytania zawiera opóźniony tekst wyszukiwania.',refactor],
  ['Uruchom tę samą specyfikację bez zmian dla wersji po refaktoryzacji.','Testy zachowania nie wiedzą, jak komponent pobiera dane.',testRefactor],
  ['Wprowadź mutant bez controller.abort() i wskaż test, który go wykrywa.','Bez przerwania stara odpowiedź nadpisuje nowe wyniki.',mutantList],
  ['Wprowadź mutant bez Math.min i wskaż test, który go wykrywa.','Indeks poza listą sprawia, że Enter niczego nie wybiera.',mutantList],
  ['Wprowadź mutant bez przywrócenia fokusu i wskaż test, który go wykrywa.','toHaveFocus na przycisku Przypisz….',mutantList],
  ['Wprowadź mutant bez sprawdzenia !res.ok i wskaż test, który go wykrywa.','Odpowiedź 500 zostałaby uznana za sukces.',mutantList],
  ['Wprowadź mutant ze zmienionym tekstem podpowiedzi i wskaż test, który go wykrywa.','Tekst widoczny dla użytkownika jest kontraktem.',mutantList],
  ['Spisz problemy dostępności i odporności komponentu do zgłoszenia zespołowi.','Nie wszystko wykryje axe — dopisz wnioski z czytania kodu.',report],
  ['Wyjaśnij, dlaczego test wyścigu ma punkt odniesienia zamiast długiej pauzy.','Flaga ustawiana w handlerze mówi, że stara odpowiedź już wyszła.',spec],
  ['Wyjaśnij, dlaczego testy nie sprawdzają klasy active ani setUsers.','Szczegóły implementacji znikną przy refaktoryzacji.',spec],
];
const quiz=[];function q(...row){quiz.push(row);}
q('Od czego zacząć testowanie cudzego komponentu?','Od zrozumienia zachowania z kodu i działania, zapisanego jako lista testów','Od 100% pokrycia linii','Od snapshotu całego DOM','Od mockowania wszystkich hooków','Specyfikacja zachowania jest celem; pokrycie jest skutkiem ubocznym.');
q('Po co test.todo na początku pracy?','Lista zachowań jest widoczna w raporcie jako niezaimplementowana','Wyłącza testy','Uruchamia testy w tle','Generuje kod testu','Widać postęp i luki w specyfikacji.');
q('Dlaczego testy mają przetrwać refaktoryzację na TanStack Query?','Opisują zachowanie, a nie sposób pobierania danych','Bo TanStack Query ma te same funkcje co fetch','Bo refaktoryzacja nie zmienia kodu','Bo testy mockują hooki','To właśnie jest cel zabezpieczenia przed refaktoryzacją.');
q('Jaki test pęknie przy każdej refaktoryzacji?','Sprawdzający, że setUsers wywołano po 250 ms','Sprawdzający widoczne opcje','Sprawdzający wysłany PATCH','Sprawdzający fokus po Escape','Szczegół implementacji nie jest kontraktem.');
q('Dlaczego wybór sprawdzamy przez aria-selected, a nie klasę active?','aria-selected jest semantyką dostępną dla użytkownika, klasa to szczegół stylu','Bo klasa nie istnieje w DOM','Bo aria-selected jest szybsze','Bo klasa zmienia się losowo','Po refaktoryzacji klasy może nie być.');
q('Jak w jednym zestawie przetestować dwie implementacje?','Funkcją tworzącą testy, która przyjmuje komponent jako argument','Kopiując plik testów','Przez vi.mock modułu','Snapshotem','describeAssignDialog(name, Component) uruchamia tę samą specyfikację.');
q('Po co render pomocniczy zawsze dodaje QueryClientProvider?','Wersja po refaktoryzacji go wymaga, a oryginałowi nie szkodzi','Bo fetch go potrzebuje','Bo MSW wymaga providera','Żeby wyłączyć retry w fetch','Wspólne środowisko pozwala uruchomić te same testy.');
q('Dlaczego retry:false także dla mutacji?','Test błędu zapisu nie czeka na ponowienia','Bo mutacje nie mają retry','Żeby wyłączyć cache','Żeby przyspieszyć fetch','Domyślnie mutacje nie ponawiają, ale jawna konfiguracja usuwa wątpliwości.');
q('Dlaczego w briefie zakazane jest vi.mock na fetch?','Test ma przejść też dla wersji, która pobiera dane inaczej','Bo vi.mock nie działa w jsdom','Bo fetch nie istnieje w testach','Bo MSW tego wymaga','MSW mockuje sieć, więc implementacja może się zmienić.');
q('Co trzeba przekazać do userEvent.setup przy fałszywych zegarach?','advanceTimers: vi.advanceTimersByTime','delay: null zawsze','pointerEventsCheck: 0','skipHover: true','Bez tego user-event czeka na timery, które nie płyną.');
q('Po co shouldAdvanceTime:true w vi.useFakeTimers?','Zegar płynie w tle, więc biblioteki czekające na czas nie zawisną','Wyłącza fałszywe zegary','Przyspiesza fetch','Zatrzymuje Date.now','Pozwala łączyć kontrolę czasu z kodem, który czeka na realny upływ.');
q('Jak udowodnić, że poniżej 2 znaków nie ma żądania?','Przesunąć zegar poza opóźnienie i sprawdzić licznik wywołań handlera','Sprawdzić brak opcji natychmiast','Odczytać stan komponentu','Sprawdzić klasę active','Bez przesunięcia czasu brak żądania niczego nie dowodzi.');
q('Dlaczego advanceTimersByTimeAsync opakowane jest w act?','Aktualizacje Reacta po timerach mają zostać przetworzone przed asercją','Bo inaczej timer się nie uruchomi','Bo act włącza MSW','Bo act zatrzymuje zegar','act synchronizuje zmiany stanu z asercjami.');
q('Co daje server.events.on("request:start")?','Obserwację żądań bez zmiany handlerów','Nowy handler','Zatrzymanie żądań','Zmianę odpowiedzi','Po teście trzeba usunąć nasłuch.');
q('Dlaczego test sprawdza parametr q w adresie żądania?','Komponent ma wysłać przycięty i zakodowany tekst wyszukiwania','Bo MSW tego wymaga','Żeby sprawdzić CSS','Bo q jest w body','To kontrakt z API: GET /api/users?q=.');
q('Dlaczego przycisk zapisu szukamy w obrębie dialogu?','Nazwa „Przypisz…” przycisku otwierającego też zawiera „Przypisz”','Bo screen nie widzi przycisków','Bo dialog ma inną stronę','Bo within jest szybsze','Zawężenie usuwa niejednoznaczność.');
q('Co sprawdza test Przypisz przy sukcesie?','PATCH z assignee, onAssigned z loginem, zamknięcie okna i fokus na przycisku otwierającym','Tylko zamknięcie okna','Tylko wywołanie fetch','Tylko onAssigned','Każde zdanie briefu ma swój dowód.');
q('Co jest dowodem, że Escape nie zapisał zmian?','Pusta lista przechwyconych PATCH i brak wywołania onAssigned','Zamknięte okno','Brak alertu','Fokus na przycisku','Zamknięcie okna nie mówi, czy coś wysłano.');
q('Czego brakuje w teście błędu, który sprawdza tylko komunikat?','Sprawdzenia, że okno zostało otwarte i onAssigned nie wywołano','Snapshotu','Liczby renderów','Klasy active','Komunikat mógłby się pojawić przy zamkniętym oknie.');
q('Jak sprawdzić blokadę przycisku podczas zapisu?','Wstrzymać odpowiedź PATCH bramką i sprawdzić przycisk „Zapisywanie…”','Kliknąć dwa razy szybko','Ustawić retry','Sprawdzić klasę disabled','Stan pending istnieje tylko przed odpowiedzią.');
q('Na czym polega wyścig „an” → „ann”?','Starsza, wolniejsza odpowiedź mogłaby nadpisać nowsze wyniki','Na dwóch kliknięciach','Na błędzie 500','Na zbyt krótkim debounce','Kolejność odpowiedzi nie musi odpowiadać kolejności żądań.');
q('Co chroni oryginalny komponent przed wyścigiem?','controller.abort() w sprzątaniu efektu','setTimeout','encodeURIComponent','setActive(0)','Przerwane żądanie nie aktualizuje stanu.');
q('Co chroni wersję z TanStack Query przed wyścigiem?','Wyniki są zapisywane pod kluczem zapytania, a ekran czyta tylko bieżący klucz','abort w useEffect','Ręczny licznik żądań','Wyłączony cache','Odpowiedź dla „an” trafia do innego wpisu cache.');
q('Dlaczego test wyścigu czeka, aż żądanie „an” wyjdzie?','Bez tego debounce anulowałby „an”, zanim powstanie wyścig','Bo MSW wymaga pauzy','Żeby przyspieszyć test','Bo user.type jest synchroniczny','Scenariusz musi naprawdę wytworzyć dwa żądania.');
q('Po co flaga oldDone w teście wyścigu?','Wyznacza moment, po którym stara odpowiedź już została wysłana','Liczy rendery','Blokuje fokus','Zastępuje asercję','Asercja nieobecności potrzebuje punktu odniesienia.');
q('Dlaczego test wyścigu nie używa pauzy 500 ms?','Wynik zależałby od szybkości maszyny i wydłużał test','Bo setTimeout nie działa w jsdom','Bo MSW nie wspiera delay','Bo test musi trwać krócej niż 1 ms','Bramka daje kontrolę nad kolejnością.');
q('Co wykrywa mutant bez controller.abort()?','Test wyścigu — lista pokazuje dwie osoby zamiast jednej','Test fokusu','Test podpowiedzi','Test axe','Stara odpowiedź nadpisuje wyniki.');
q('Co wykrywa mutant bez Math.min?','Test zatrzymania strzałki na ostatniej osobie','Test Escape','Test błędu','Test axe','Indeks wychodzi poza listę i Enter niczego nie wybiera.');
q('Co wykrywa mutant bez przywrócenia fokusu?','Testy Escape i sukcesu sprawdzające fokus na Przypisz…','Test podpowiedzi','Test wyścigu','Test parametru q','Fokus po zamknięciu okna to część dostępności.');
q('Co wykrywa mutant bez sprawdzenia !res.ok?','Test błędu zapisu','Test klawiatury','Test podpowiedzi','Test parametru q','Odpowiedź 500 zostałaby uznana za sukces.');
q('Co wykrywa mutant ze zmienionym tekstem podpowiedzi?','Testy sprawdzające „Wpisz co najmniej 2 znaki”','Test wyścigu','Test zapisu','Żaden — tekst to szczegół','Tekst widoczny dla użytkownika jest kontraktem.');
q('Dlaczego dowód skuteczności testów wymaga mutantów?','Zielone testy mogą niczego nie sprawdzać; mutant pokazuje, że test łapie błąd','Bo pokrycie jest za niskie','Bo mutanty przyspieszają testy','Bo Vitest tego wymaga','Test, który przechodzi dla zepsutego kodu, nie chroni.');
q('Dlaczego mutant musi oblać test zachowania, a nie np. kompilację?','Błąd kompilacji nie dowodzi, że asercja wykrywa usterkę','Bo kompilacja jest wolniejsza','Bo TypeScript ignoruje mutanty','Bo mutanty nie kompilują się nigdy','Mutant ma być poprawnym kodem z błędnym zachowaniem.');
q('Który problem dostępności oryginału wykryjesz czytając kod, a nie axe?','Brak aria-activedescendant przy obsłudze strzałkami','Brak etykiety pola','Brak nazwy dialogu','Brak roli listbox','axe nie wie, że strzałki zmieniają aktywną opcję.');
q('Dlaczego stałe id="assign-q" to ryzyko?','Dwa okna na stronie dadzą duplikat id i zepsute powiązanie etykiety','Bo id musi być liczbą','Bo axe zabrania id','Bo React usuwa id','useId generuje unikalne identyfikatory.');
q('Jaki błąd odporności ma save() w oryginale?','Odrzucony fetch zostawia saving=true i blokuje przycisk na zawsze','Brak debounce','Brak klucza listy','Brak etykiety','Brak try/finally wokół fetch.');
q('Co oznacza aria-modal="true" bez pułapki fokusu?','Deklaracja modalności nie zatrzymuje Tab w oknie','Okno jest w pełni dostępne','Fokus jest zawsze w oknie','To błąd składni','Zachowanie trzeba zaimplementować, np. natywnym <dialog>.');
q('Dlaczego test axe wyłącza regułę region?','Okno renderowane w teście jest fragmentem bez punktów orientacyjnych strony','Bo region psuje dialogi','Bo axe 4 nie ma tej reguły','Żeby test przeszedł mimo błędów','Wyłączenie jest świadome i opisane.');
q('Co zrobić z problemami spoza zakresu briefu?','Spisać je i zgłosić zespołowi, nie mieszając z testami zachowania','Naprawić bez słowa','Zignorować','Wyłączyć testy','Zgłoszenie zostawia decyzję właścicielom komponentu.');
q('Czy testy powinny sprawdzać liczbę renderów komponentu?','Nie, to szczegół implementacji','Tak, zawsze','Tylko w StrictMode','Tylko po refaktoryzacji','Liczba renderów zmieni się po przejściu na TanStack Query.');
q('Dlaczego test otwarcia sprawdza brak dialogu przed kliknięciem?','Udowadnia, że to kliknięcie otwiera okno','Bo dialog zawsze istnieje','Żeby sprawdzić CSS','Bo screen tego wymaga','Stan „przed” czyni asercję „po” znaczącą.');
q('Co daje toHaveBeenCalledExactlyOnceWith("andrzej")?','Sprawdza jedno wywołanie z dokładnym argumentem','Sprawdza dowolne wywołanie','Sprawdza brak wywołania','Sprawdza typ argumentu','Łączy liczbę wywołań i argumenty.');
q('Dlaczego po zapisie czekamy na zniknięcie dialogu przez waitFor?','Zamknięcie następuje po asynchronicznej odpowiedzi','Bo dialog znika natychmiast','Bo waitFor jest szybszy od expect','Żeby wyłączyć MSW','Asercja z ponawianiem czeka na skutek odpowiedzi.');
q('Co jest publicznym API komponentu w briefie TSX?','Props ticketId i onAssigned oraz kontrakt HTTP','Stan users i active','Klasa CSS active','Funkcja save','Testy piszesz wobec tego, co widzi użytkownik i rodzic.');
q('Jak sprawdzić, że wynik z klawiatury jest właściwą osobą?','aria-selected="true" na opcji o nazwie tej osoby','Klasa active na pierwszym li','Wartość pola wyszukiwania','Liczba opcji','Nazwa opcji jest stabilna i dostępna.');
q('Dlaczego scenariusz klawiatury wybiera Enterem w polu wyszukiwania?','Komponent obsługuje strzałki i Enter w polu, jak combobox','Bo opcje mają tabIndex','Bo Enter zamyka okno','Bo listbox przechwytuje fokus','Fokus pozostaje w polu przez całą interakcję.');
q('Czy test „Przypisz zablokowane bez wyboru” jest potrzebny, skoro jest test kliknięcia?','Tak, potwierdza stan początkowy przed wyborem','Nie, dubluje test','Tylko w TSX','Tylko z fałszywymi zegarami','Bez niego mutant z zawsze aktywnym przyciskiem mógłby przejść.');
q('Dlaczego przy ponownym otwarciu oczekujemy pustej listy?','close() czyści tekst, a poniżej 2 znaków lista jest pusta','Bo API zwraca pustą listę','Bo React usuwa opcje','Bo cache jest wyłączony','Reset stanu po zamknięciu jest częścią zachowania.');
q('Co jest sygnałem, że test kopiuje implementację?','Odwołuje się do nazw stanów, funkcji wewnętrznych albo dokładnych opóźnień','Używa getByRole','Sprawdza tekst komunikatu','Używa MSW','Takie testy łamią się przy refaktoryzacji.');
q('Który brak await jest najgroźniejszy w tych testach?','Przed user-event lub findBy — test przechodzi przypadkiem','Przed render','Przed describe','Przed import','Asercja wykonuje się, zanim akcja zadziała.');
q('Dlaczego użyć HttpResponse z MSW zamiast vi.fn jako fetch?','Komponent przechodzi przez prawdziwy fetch, nagłówki i parsowanie JSON','Bo vi.fn nie zwraca obietnic','Bo MSW jest szybszy','Bo fetch nie istnieje w Node','Mock sieci nie wie, czym komponent wysyła żądanie.');
q('Czy względny adres /api/users działa z MSW w jsdom?','Tak, jest rozwiązywany względem location strony testowej','Nie, zawsze trzeba adresu absolutnego','Tylko w przeglądarce','Tylko z setupWorker','W czystym Node bez jsdom potrzebny jest adres absolutny.');
q('Co wnosi wersja po refaktoryzacji poza TanStack Query?','Powiązanie pola z listą przez aria-controls i aria-activedescendant','Klasy CSS','Krótszy debounce','Usunięcie Escape','Refaktoryzacja to okazja do naprawy zgłoszonych problemów.');
q('Kiedy boss jest zaliczony?','Gdy wszystkie kryteria są spełnione, mutanty wykryte, a testy przechodzą po refaktoryzacji','Gdy pokrycie przekracza 90%','Gdy testy są zielone raz','Gdy jest snapshot','Kryteria opisują jakość, a nie ilość testów.');
q('Co mierzy test jakości zestawu w tym zadaniu?','Wykrywanie celowych błędów i odporność na refaktoryzację','Liczbę linii testów','Czas wykonania','Liczbę plików','To dwie strony dobrego zestawu testów zachowania.');
q('Dlaczego debounce sprawdzamy fałszywym zegarem tylko w jednym teście?','Pozostałe testy opisują zachowanie i mogą czekać przez findBy','Bo fałszywe zegary są zakazane','Bo debounce nie istnieje','Bo testy z zegarem nie działają z MSW','Kontrola czasu jest potrzebna tam, gdzie czas jest istotą scenariusza.');
q('Co zrobić, gdy test przechodzi dla mutanta?','Wzmocnić asercje lub dodać scenariusz, który ujawnia ten błąd','Usunąć mutanta','Zwiększyć timeout','Dodać snapshot','Mutant pokazuje lukę w specyfikacji.');
q('Dlaczego przegląd kodu cudzego komponentu też jest częścią zadania?','Nie każdy problem da się wykryć testem lub audytem automatycznym','Bo testy są opcjonalne','Bo axe wszystko wykrywa','Bo zespół tego nie zrobi','Raport dla zespołu uzupełnia testy.');
q('Jak poznać zachowanie komponentu, którego nie pisałeś?','Czytając kod i obserwując działanie w przeglądarce, a potem zapisując zachowania jako testy','Tylko z nazw zmiennych','Tylko z dokumentacji, której brak','Z liczby linii kodu','Eksperymenty pokazują zachowania, których kod nie ujawnia na pierwszy rzut oka.');
q('Co zrobić z zachowaniem, które wygląda na błąd, a nie jest w briefie?','Opisać je w zgłoszeniu i zapytać zespół, zanim zabetonujesz je testem','Utrwalić testem jako kontrakt','Naprawić po cichu','Usunąć z komponentu','Test charakteryzujący nie powinien utrwalać błędów bez decyzji.');
q('Czym są testy charakteryzujące (characterization tests)?','Testami utrwalającymi obecne zachowanie przed zmianą kodu','Testami wydajności','Testami wizualnymi','Testami typów','Chronią przed niezamierzoną zmianą zachowania podczas refaktoryzacji.');
q('Dlaczego testy boss działają na prawdziwym fetch przechwyconym przez MSW?','Refaktoryzacja na TanStack Query nadal wysyła te same żądania HTTP','Bo fetch jest szybszy','Bo TanStack Query nie używa fetch','Bo vi.mock jest zabroniony w Vitest','Granica sieci jest wspólna dla obu implementacji.');
q('Co oznacza, że test jest „odporny na refaktoryzację”?','Przechodzi bez zmian po zmianie implementacji z tym samym zachowaniem','Nigdy nie oblewa','Działa bez Reacta','Nie używa asercji','To główna miara jakości testów zachowania.');
q('Dlaczego w specyfikacji nie ma testu „po 250 ms wysyła żądanie”?','Dokładne opóźnienie to szczegół; test sprawdza brak żądania poniżej 2 znaków i wyniki od 2 znaków','Bo debounce nie działa','Bo fałszywe zegary są zakazane','Bo 250 ms to za krótko','Opóźnienie może się zmienić bez zmiany zachowania dla użytkownika.');
q('Dlaczego test wyścigu jest najcenniejszy przy refaktoryzacji?','Wyścigi łatwo wprowadzić przy zmianie sposobu pobierania danych','Bo jest najkrótszy','Bo sprawdza CSS','Bo nie używa MSW','Nowa implementacja musi zachować ochronę przed starymi odpowiedziami.');
q('Co robi user.keyboard("{ArrowDown}{Enter}")?','Naciska strzałkę w dół, a potem Enter w elemencie z fokusem','Wpisuje tekst ArrowDown','Klika pierwszy przycisk','Zamyka dialog','Nawiasy klamrowe oznaczają nazwy klawiszy.');
q('Gdzie musi być fokus, żeby strzałki działały w AssignDialog?','W polu wyszukiwania, które obsługuje onKeyDown','Na liście opcji','Na przycisku Przypisz','Na body','autoFocus ustawia go po otwarciu okna.');
q('Dlaczego Escape działa, choć obsługę ma div dialogu?','Zdarzenie klawiatury z pola bąbelkuje do rodzica','Bo div ma fokus','Bo Escape jest globalny w React','Bo user-event wysyła je do dialogu','Bąbelkowanie zdarzeń pozwala obsłużyć klawisz na kontenerze.');
q('Jaki problem ma lista opcji obsługiwana tylko kliknięciem?','Użytkownik klawiatury wybiera wyłącznie przez pole i strzałki — trzeba to zakomunikować semantyką comboboxa','Opcje są niewidoczne','Kliknięcie nie działa w jsdom','Brak klucza','Wzorzec combobox wymaga ról i atrybutów ARIA.');
q('Czemu służy plik zgłoszeń dla zespołu?','Przekazuje problemy wykryte poza zakresem testów do decyzji właścicieli','Zastępuje testy','Jest konfiguracją Vitest','Jest wymagany przez MSW','Testy i przegląd kodu uzupełniają się.');
q('Dlaczego mutanty są stosowane po kolei, a nie wszystkie naraz?','Każdy mutant musi zostać wykryty samodzielnie','Bo naraz się nie kompilują','Bo Vitest uruchamia jeden plik','Żeby było szybciej','Kilka błędów naraz mogłoby maskować lukę w teście jednego z nich.');
q('Co jeśli test wykrywa mutanta tylko przez przekroczenie czasu?','Lepiej dodać asercję, która wprost nazywa oczekiwane zachowanie','To idealny wynik','Należy zwiększyć timeout','Należy usunąć test','Czytelny komunikat porażki przyspiesza diagnozę.');
q('Po co sprawdzać, że druga osoba ma aria-selected="false"?','Wykrywa błąd zaznaczania wielu osób naraz','Bo aria-selected jest wymagane','Żeby przyspieszyć test','Bo klasa active tego wymaga','Asercja „tylko ta osoba” jest silniejsza.');
q('Co daje uruchomienie specyfikacji dla wersji po refaktoryzacji w CI?','Gwarancję, że zmiana implementacji nie zmieniła zachowania','Szybsze testy','Mniejszy bundle','Brak potrzeby MSW','To bezpośrednia realizacja kryterium 9.');
q('Dlaczego onAssigned jest szpiegiem vi.fn z typem?','Sprawdzasz kontrakt z rodzicem, a typ pilnuje sygnatury callbacku','Bo vi.fn jest szybsze','Bo rodzic nie istnieje','Żeby wyłączyć TypeScript','vi.fn<(login:string)=>void>() zgłosi niezgodny typ w czasie kompilacji.');
q('Dlaczego test zapisu zbiera PATCH w tablicy sent?','Porównanie całej tablicy wykrywa też nadmiarowe lub powtórzone żądania','Bo MSW zwraca tablice','Bo body jest tablicą','Żeby ominąć await','toEqual na liście jest ścisłe co do liczby żądań.');
q('Jaki błąd ujawniło pisanie testu Escape po kliknięciu osoby?','Kliknięcie <li> wyrzuca fokus z okna, więc Escape przestaje działać','Escape zawsze zapisuje zmiany','Kliknięcie nie zaznacza osoby','Lista znika po kliknięciu','Element bez tabIndex nie przyjmuje fokusu, a zdarzenie klawiatury trafia do body.');
q('Co zrobić po dodaniu nasłuchu server.events w teście?','Usunąć go w finally (removeAllListeners)','Nic, znika sam','Zamknąć serwer','Wywołać resetDb','Nasłuch przeciekłby do kolejnych testów.');
q('Jaki jest główny rezultat bossa dla zespołu?','Zestaw testów zachowania, dowód ich skuteczności i lista zgłoszeń','Nowy komponent','Wyższy próg pokrycia','Usunięcie starego kodu','Zespół może bezpiecznie przepisać komponent.');
const probes=[
  ['Wyniki dla „an”','2','1','3','0','console.log((await (await fetch("/api/users?q=an")).json()).length);'],
  ['Wyniki dla „ann”','1','2','0','3','console.log((await (await fetch("/api/users?q=ann")).json()).length);'],
  ['Szukanie po loginie','Ola Kowal','Anna Nowak','Andrzej Lis','Pusty','const list:{name:string}[]=await (await fetch("/api/users?q=ola")).json();console.log(list.map(p=>p.name).join(",")||"Pusty");'],
  ['Wielkość liter w wyszukiwaniu','2','0','1','3','console.log((await (await fetch("/api/users?q=AN")).json()).length);'],
  ['Odpowiedź PATCH','andrzej','anna','null','undefined','const body=await (await fetch("/api/tickets/42",{method:"PATCH",headers:{"Content-Type":"application/json"},body:JSON.stringify({assignee:"andrzej"})})).json();console.log(String(body.assignee));'],
  ['Przycisk zapisu po otwarciu','true','false','undefined','null','const user=userEvent.setup();render(createElement(AssignDialog,{ticketId:42,onAssigned:()=>{}}));await user.click(screen.getByRole("button",{name:"Przypisz…"}));console.log(String(within(screen.getByRole("dialog")).getByRole("button",{name:"Przypisz"}).hasAttribute("disabled")));'],
  ['Fokus po otwarciu','INPUT','BUTTON','BODY','UL','const user=userEvent.setup();render(createElement(AssignDialog,{ticketId:42,onAssigned:()=>{}}));await user.click(screen.getByRole("button",{name:"Przypisz…"}));console.log(document.activeElement?.tagName);'],
  ['Podpowiedź po jednym znaku','widoczna','ukryta','błąd','pusta lista bez tekstu','const user=userEvent.setup();render(createElement(AssignDialog,{ticketId:42,onAssigned:()=>{}}));await user.click(screen.getByRole("button",{name:"Przypisz…"}));await user.type(screen.getByLabelText("Szukaj osoby"),"a");console.log(screen.queryByText("Wpisz co najmniej 2 znaki")?"widoczna":"ukryta");'],
  ['Podpowiedź po spacjach','widoczna','ukryta','błąd','pusta lista bez tekstu','const user=userEvent.setup();render(createElement(AssignDialog,{ticketId:42,onAssigned:()=>{}}));await user.click(screen.getByRole("button",{name:"Przypisz…"}));await user.type(screen.getByLabelText("Szukaj osoby"),"a  ");console.log(screen.queryByText("Wpisz co najmniej 2 znaki")?"widoczna":"ukryta");'],
  ['Liczba opcji po „an”','2','1','0','3','const user=userEvent.setup();render(createElement(AssignDialog,{ticketId:42,onAssigned:()=>{}}));await user.click(screen.getByRole("button",{name:"Przypisz…"}));await user.type(screen.getByLabelText("Szukaj osoby"),"an");console.log((await screen.findAllByRole("option")).length);'],
  ['Wybór klawiaturą bez strzałek','Anna Nowak','Andrzej Lis','brak','Ola Kowal','const user=userEvent.setup();render(createElement(AssignDialog,{ticketId:42,onAssigned:()=>{}}));await user.click(screen.getByRole("button",{name:"Przypisz…"}));await user.type(screen.getByLabelText("Szukaj osoby"),"an");await screen.findAllByRole("option");await user.keyboard("{Enter}");console.log(screen.queryAllByRole("option",{selected:true}).map(o=>o.textContent).join(",")||"brak");'],
  ['Pięć strzałek w dół','Andrzej Lis','Anna Nowak','brak','Ola Kowal','const user=userEvent.setup();render(createElement(AssignDialog,{ticketId:42,onAssigned:()=>{}}));await user.click(screen.getByRole("button",{name:"Przypisz…"}));await user.type(screen.getByLabelText("Szukaj osoby"),"an");await screen.findAllByRole("option");await user.keyboard("{ArrowDown}{ArrowDown}{ArrowDown}{ArrowDown}{ArrowDown}{Enter}");console.log(screen.queryAllByRole("option",{selected:true}).map(o=>o.textContent).join(",")||"brak");'],
  ['Zaznaczonych po kliknięciu','1','2','0','3','const user=userEvent.setup();render(createElement(AssignDialog,{ticketId:42,onAssigned:()=>{}}));await user.click(screen.getByRole("button",{name:"Przypisz…"}));await user.type(screen.getByLabelText("Szukaj osoby"),"an");await user.click(await screen.findByRole("option",{name:"Anna Nowak"}));console.log(screen.getAllByRole("option",{selected:true}).length);'],
  ['onAssigned po sukcesie','anna','andrzej','brak','undefined','let got="brak";const user=userEvent.setup();render(createElement(AssignDialog,{ticketId:42,onAssigned:(login:string)=>{got=login;}}));await user.click(screen.getByRole("button",{name:"Przypisz…"}));await user.type(screen.getByLabelText("Szukaj osoby"),"an");await user.click(await screen.findByRole("option",{name:"Anna Nowak"}));await user.click(within(screen.getByRole("dialog")).getByRole("button",{name:"Przypisz"}));await waitFor(()=>{if(screen.queryByRole("dialog"))throw new Error("otwarte");});console.log(got);'],
  ['onAssigned po błędzie 500','brak','anna','andrzej','undefined','server.use(http.patch("/api/tickets/:id",()=>HttpResponse.json({},{status:500})));let got="brak";const user=userEvent.setup();render(createElement(AssignDialog,{ticketId:42,onAssigned:(login:string)=>{got=login;}}));await user.click(screen.getByRole("button",{name:"Przypisz…"}));await user.type(screen.getByLabelText("Szukaj osoby"),"an");await user.click(await screen.findByRole("option",{name:"Anna Nowak"}));await user.click(within(screen.getByRole("dialog")).getByRole("button",{name:"Przypisz"}));await screen.findByRole("alert");console.log(got);'],
  ['Dialog po błędzie 500','otwarty','zamknięty','usunięty z DOM i ponownie dodany','ukryty CSS','server.use(http.patch("/api/tickets/:id",()=>HttpResponse.json({},{status:500})));const user=userEvent.setup();render(createElement(AssignDialog,{ticketId:42,onAssigned:()=>{}}));await user.click(screen.getByRole("button",{name:"Przypisz…"}));await user.type(screen.getByLabelText("Szukaj osoby"),"an");await user.click(await screen.findByRole("option",{name:"Anna Nowak"}));await user.click(within(screen.getByRole("dialog")).getByRole("button",{name:"Przypisz"}));await screen.findByRole("alert");console.log(screen.queryByRole("dialog")?"otwarty":"zamknięty");'],
  ['Fokus po Escape','Przypisz…','Szukaj osoby','body','Przypisz','const user=userEvent.setup();render(createElement(AssignDialog,{ticketId:42,onAssigned:()=>{}}));await user.click(screen.getByRole("button",{name:"Przypisz…"}));await user.keyboard("{Escape}");console.log(document.activeElement?.textContent||"body");'],
  ['Liczba PATCH po Escape','0','1','2','undefined','let patches=0;server.use(http.patch("/api/tickets/:id",()=>{patches+=1;return HttpResponse.json({});}));const user=userEvent.setup();render(createElement(AssignDialog,{ticketId:42,onAssigned:()=>{}}));await user.click(screen.getByRole("button",{name:"Przypisz…"}));await user.type(screen.getByLabelText("Szukaj osoby"),"an");await user.click(await screen.findByRole("option",{name:"Anna Nowak"}));await user.keyboard("{Escape}");console.log(patches);'],
  ['Escape po kliknięciu osoby myszą','otwarty','zamknięty','usunięty z DOM i ponownie dodany','ukryty CSS','const user=userEvent.setup();render(createElement(AssignDialog,{ticketId:42,onAssigned:()=>{}}));await user.click(screen.getByRole("button",{name:"Przypisz…"}));await user.type(screen.getByLabelText("Szukaj osoby"),"an");await user.click(await screen.findByRole("option",{name:"Anna Nowak"}));await user.keyboard("{Escape}");console.log(screen.queryByRole("dialog")?"otwarty":"zamknięty");'],
  ['Kodowanie parametru q','?q=a%26b','?q=a&b','?q=a+b','?q=a%20b','let seen="";server.use(http.get("/api/users",({request})=>{seen=new URL(request.url).search;return HttpResponse.json([]);}));const user=userEvent.setup();render(createElement(AssignDialog,{ticketId:42,onAssigned:()=>{}}));await user.click(screen.getByRole("button",{name:"Przypisz…"}));await user.type(screen.getByLabelText("Szukaj osoby"),"a&b");await waitFor(()=>{if(!seen)throw new Error("czekam");});console.log(seen);'],
  ['Przycięcie tekstu w zapytaniu','?q=an','?q=%20an%20','?q=+an+','?q=an%20','let seen="";server.use(http.get("/api/users",({request})=>{seen=new URL(request.url).search;return HttpResponse.json([]);}));const user=userEvent.setup();render(createElement(AssignDialog,{ticketId:42,onAssigned:()=>{}}));await user.click(screen.getByRole("button",{name:"Przypisz…"}));await user.type(screen.getByLabelText("Szukaj osoby")," an ");await waitFor(()=>{if(!seen)throw new Error("czekam");});console.log(seen);'],
];
for(const [label,answer,b,c,d,body] of probes)q(label+' — jaki wynik?',answer,b,c,d,'Wynik pochodzi z prawdziwego renderu w jsdom, user-event i odpowiedzi MSW w Vitest; nie z odczytu stanu komponentu.',`import {render,screen,waitFor,within} from "@testing-library/react";import userEvent from "@testing-library/user-event";import {http,HttpResponse} from "msw";import {createElement} from "react";import {AssignDialog} from "./AssignDialog";import {server} from "./mswServer";${body}`);
if(exercises.length!==30||quiz.length!==100)throw Error('Niepełny zestaw '+exercises.length+'/'+quiz.length);
const mocks=base.project['src/mswServer.ts'];
const serverWithAssign=mocks.replace('import {makeTicket,type Priority,type Ticket} from "./ticketTypes";','import {makeTicket,type Priority,type Ticket} from "./ticketTypes";\nimport {assignHandlers} from "./assignHandlers";').replace('export const server=setupServer(...handlers);','export const server=setupServer(...handlers,...assignHandlers);');
if(serverWithAssign===mocks||!serverWithAssign.includes('...assignHandlers'))throw Error('Nie podłączono handlerów boss');
const manifest=JSON.parse(base.project['package.json']);Object.assign(manifest.devDependencies,{'vitest-axe':'0.1.0','axe-core':'4.14.0'});
const project={...Object.fromEntries(Object.entries(base.project).filter(([file])=>!file.includes('.test.')||file==='src/WatchToggle.test.tsx')),
  'src/mswServer.ts':serverWithAssign,'src/assignHandlers.ts':handlers,'src/AssignDialog.tsx':dialog,'src/AssignDialogQuery.tsx':refactor,'src/renderAssign.tsx':renderHelper,
  'src/assignDialogSpec.tsx':spec,'src/AssignDialog.test.tsx':testOriginal,'src/AssignDialogQuery.test.tsx':testRefactor,'src/assignSpec.todo.test.ts':todoList,'src/assignMutants.ts':mutantList,
  'docs/zgloszenia-assign-dialog.md':report,'package.json':JSON.stringify(manifest,null,2)+'\n',
  'README.md':base.project['README.md']+' Boss: AssignDialog ma jedną specyfikację zachowania uruchamianą dla oryginału (fetch) i wersji po refaktoryzacji (TanStack Query); pięć celowych błędów z briefu musi zostać wykrytych. Lista problemów dla zespołu: docs/zgloszenia-assign-dialog.md.'};
const mutants=[
  {file:'src/AssignDialog.tsx',before:'return ()=>{clearTimeout(id);controller.abort();};',after:'return ()=>{clearTimeout(id);};'},
  {file:'src/AssignDialog.tsx',before:'Math.min(i+1,users.length-1)',after:'i+1'},
  {file:'src/AssignDialog.tsx',before:'setError(null);openerRef.current?.focus();}',after:'setError(null);}'},
  {file:'src/AssignDialog.tsx',before:'if(!res.ok){setError("Nie udało się przypisać ticketu.");return;}',after:''},
  {file:'src/AssignDialog.tsx',before:'<p>Wpisz co najmniej 2 znaki</p>',after:'<p>Wpisz min. 2 znaki</p>'},
];
module.exports={dialog,refactor,handlers,renderHelper,spec,testOriginal,testRefactor,todoList,report,mutantList,exercises,quiz,project,mutants,vitestQuiz:true,quizEnvironment:'jsdom'};
