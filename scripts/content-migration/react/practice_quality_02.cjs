const {lesson}=require('./practice_kit.cjs');
const tabs=String.raw`import {useId,useRef,useState,type KeyboardEvent,type ReactNode} from "react";

export type Tab={id:string;title:string;content:ReactNode};

export function Tabs({tabs,label}:{tabs:readonly Tab[];label:string}){
  const [active,setActive]=useState(0);
  const baseId=useId();
  const refs=useRef<(HTMLButtonElement|null)[]>([]);

  function focusTab(index:number){
    const next=(index+tabs.length)%tabs.length;
    setActive(next);
    refs.current[next]?.focus();
  }

  function onKeyDown(event:KeyboardEvent<HTMLDivElement>){
    if(event.key==="ArrowRight")focusTab(active+1);
    else if(event.key==="ArrowLeft")focusTab(active-1);
    else if(event.key==="Home")focusTab(0);
    else if(event.key==="End")focusTab(tabs.length-1);
    else return;
    event.preventDefault();
  }

  return (
    <div>
      <div role="tablist" aria-label={label} onKeyDown={onKeyDown}>
        {tabs.map((tab,index)=>(
          <button
            key={tab.id}
            type="button"
            role="tab"
            id={baseId+"-tab-"+index}
            ref={element=>{refs.current[index]=element;}}
            aria-selected={index===active}
            aria-controls={baseId+"-panel-"+index}
            tabIndex={index===active?0:-1}
            onClick={()=>setActive(index)}
          >
            {tab.title}
          </button>
        ))}
      </div>
      {tabs.map((tab,index)=>(
        <div
          key={tab.id}
          role="tabpanel"
          id={baseId+"-panel-"+index}
          aria-labelledby={baseId+"-tab-"+index}
          hidden={index!==active}
          tabIndex={0}
        >
          {tab.content}
        </div>
      ))}
    </div>
  );
}`;
const announcer=String.raw`import {createContext,useCallback,useContext,useState,type ReactNode} from "react";

const AnnounceContext=createContext<(message:string)=>void>(()=>{});

export function Announcer({children}:{children:ReactNode}){
  const [message,setMessage]=useState("");
  const [alertMessage,setAlertMessage]=useState("");
  const announce=useCallback((text:string)=>{
    setAlertMessage("");
    setMessage("");
    requestAnimationFrame(()=>setMessage(text));
  },[]);
  const announceError=useCallback((text:string)=>{
    setAlertMessage("");
    requestAnimationFrame(()=>setAlertMessage(text));
  },[]);
  return (
    <AnnounceContext.Provider value={announce}>
      <ErrorContext.Provider value={announceError}>
        {children}
        <div role="status" aria-live="polite" className="visually-hidden">{message}</div>
        <div role="alert" className="visually-hidden">{alertMessage}</div>
      </ErrorContext.Provider>
    </AnnounceContext.Provider>
  );
}

const ErrorContext=createContext<(message:string)=>void>(()=>{});
export const useAnnounce=()=>useContext(AnnounceContext);
export const useAnnounceError=()=>useContext(ErrorContext);`;
const disclosure=String.raw`import {useId,useRef,useState,type KeyboardEvent,type ReactNode} from "react";

export function Disclosure({label,children}:{label:string;children:ReactNode}){
  const [open,setOpen]=useState(false);
  const id=useId();
  const trigger=useRef<HTMLButtonElement>(null);

  function onKeyDown(event:KeyboardEvent<HTMLDivElement>){
    if(event.key==="Escape"&&open){
      setOpen(false);
      trigger.current?.focus();
    }
  }

  return (
    <div onKeyDown={onKeyDown}>
      <button ref={trigger} type="button" aria-expanded={open} aria-controls={id} onClick={()=>setOpen(value=>!value)}>
        {label}
      </button>
      <div id={id} hidden={!open}>{children}</div>
    </div>
  );
}`;
const menu=String.raw`import {useEffect,useId,useRef,useState,type KeyboardEvent} from "react";

export type MenuAction={id:string;label:string;onSelect:()=>void};

export function ActionMenu({label,actions}:{label:string;actions:readonly MenuAction[]}){
  const [open,setOpen]=useState(false);
  const [active,setActive]=useState(0);
  const menuId=useId();
  const trigger=useRef<HTMLButtonElement>(null);
  const items=useRef<(HTMLLIElement|null)[]>([]);

  useEffect(()=>{if(open)items.current[active]?.focus();},[open,active]);

  function close(){
    setOpen(false);
    setActive(0);
    trigger.current?.focus();
  }

  function onMenuKeyDown(event:KeyboardEvent<HTMLUListElement>){
    const count=actions.length;
    if(event.key==="ArrowDown")setActive(index=>(index+1)%count);
    else if(event.key==="ArrowUp")setActive(index=>(index-1+count)%count);
    else if(event.key==="Home")setActive(0);
    else if(event.key==="End")setActive(count-1);
    else if(event.key==="Escape")close();
    else if(event.key==="Enter"||event.key===" "){
      actions[active].onSelect();
      close();
    }else return;
    event.preventDefault();
  }

  return (
    <div>
      <button ref={trigger} type="button" aria-haspopup="menu" aria-expanded={open} aria-controls={menuId} onClick={()=>setOpen(value=>!value)}>
        {label}
      </button>
      <ul role="menu" id={menuId} aria-label={label} hidden={!open} onKeyDown={onMenuKeyDown}>
        {actions.map((action,index)=>(
          <li
            key={action.id}
            role="menuitem"
            ref={element=>{items.current[index]=element;}}
            tabIndex={index===active?0:-1}
            onClick={()=>{action.onSelect();close();}}
          >
            {action.label}
          </li>
        ))}
      </ul>
    </div>
  );
}`;
const toggle=String.raw`import {useState} from "react";

// Nazwa stała, a stan niesie aria-pressed. Zmiana nazwy razem ze stanem dałaby sprzeczny komunikat.
export function PinButton({initial=false}:{initial?:boolean}){
  const [pressed,setPressed]=useState(initial);
  return <button type="button" aria-pressed={pressed} onClick={()=>setPressed(value=>!value)}>Przypnij ticket</button>;
}`;
const css=String.raw`/* a11y.css */
.visually-hidden {
  position: absolute;
  width: 1px;
  height: 1px;
  padding: 0;
  margin: -1px;
  overflow: hidden;
  clip-path: inset(50%);
  white-space: nowrap;
  border: 0;
}`;
const lab=String.raw`import {fireEvent,render,screen,within} from "@testing-library/react";
import {Tabs,type Tab} from "./Tabs";
import {Disclosure} from "./Disclosure";
import {ActionMenu} from "./ActionMenu";
import {PinButton} from "./PinButton";

export const demoTabs:Tab[]=[
  {id:"opis",title:"Opis",content:"Treść opisu"},
  {id:"komentarze",title:"Komentarze",content:"Treść komentarzy"},
  {id:"historia",title:"Historia",content:"Treść historii"},
];

export function observeTabs(keys:string[]=[]){
  const view=render(<Tabs label="Sekcje ticketu" tabs={demoTabs}/>);
  try{
    const list=within(view.container).getByRole("tablist");
    for(const key of keys)fireEvent.keyDown(list,{key});
    const all=within(view.container).getAllByRole("tab",{hidden:true});
    const selected=all.find(tab=>tab.getAttribute("aria-selected")==="true")!;
    const panels=[...view.container.querySelectorAll("[role=tabpanel]")] as HTMLElement[];
    const visible=panels.filter(panel=>!panel.hidden);
    return {
      selected:selected.textContent,
      focused:document.activeElement?.textContent??"brak",
      tabStops:all.filter(tab=>tab.tabIndex===0).length,
      visiblePanels:visible.length,
      panelText:visible[0]?.textContent??"brak",
      linked:visible[0]?.getAttribute("aria-labelledby")===selected.id,
      controls:selected.getAttribute("aria-controls")===visible[0]?.id,
      listName:list.getAttribute("aria-label"),
    };
  }finally{view.unmount();}
}

export function observeDisclosure(steps:("click"|"escape")[]){
  const view=render(<Disclosure label="Akcje"><button>Przypisz</button></Disclosure>);
  try{
    const trigger=view.getByRole("button",{name:"Akcje"});
    trigger.focus();
    for(const step of steps){
      if(step==="click")fireEvent.click(trigger);
      else fireEvent.keyDown(trigger,{key:"Escape"});
    }
    return {
      expanded:trigger.getAttribute("aria-expanded"),
      inner:view.queryAllByRole("button",{name:"Przypisz"}).length,
      focusOnTrigger:document.activeElement===trigger,
    };
  }finally{view.unmount();}
}

export function observeMenu(keys:string[]=[]){
  const calls:string[]=[];
  const actions=["Przypisz","Zamknij","Usuń"].map(label=>({id:label,label,onSelect:()=>calls.push(label)}));
  const view=render(<ActionMenu label="Akcje" actions={actions}/>);
  try{
    const trigger=view.getByRole("button",{name:"Akcje"});
    fireEvent.click(trigger);
    const menu=view.container.querySelector("[role=menu]") as HTMLElement;
    for(const key of keys)fireEvent.keyDown(menu,{key});
    return {
      focused:document.activeElement?.textContent??"brak",
      expanded:trigger.getAttribute("aria-expanded"),
      haspopup:trigger.getAttribute("aria-haspopup"),
      calls:calls.join(",")||"brak",
      menuVisible:!menu.hidden,
      focusOnTrigger:document.activeElement===trigger,
    };
  }finally{view.unmount();}
}

export function observePin(clicks:number){
  const view=render(<PinButton/>);
  try{
    const button=screen.getByRole("button",{name:"Przypnij ticket"});
    for(let i=0;i<clicks;i++)fireEvent.click(button);
    return {pressed:button.getAttribute("aria-pressed"),name:button.textContent};
  }finally{view.unmount();}
}`;
const tests=String.raw`import {expect,test,vi} from "vitest";
import {act,render,screen,waitFor} from "@testing-library/react";
import userEvent from "@testing-library/user-event";
import {Tabs} from "./Tabs";
import {Announcer,useAnnounce,useAnnounceError} from "./Announcer";
import {Disclosure} from "./Disclosure";
import {ActionMenu} from "./ActionMenu";
import {PinButton} from "./PinButton";
import {demoTabs} from "./ariaLab";

test("zakładki mają role, stan i powiązania wzorca APG",()=>{
  render(<Tabs label="Sekcje ticketu" tabs={demoTabs}/>);
  expect(screen.getByRole("tablist",{name:"Sekcje ticketu"})).toBeVisible();
  const first=screen.getByRole("tab",{name:"Opis",selected:true});
  expect(first).toHaveAttribute("tabindex","0");
  expect(screen.getByRole("tab",{name:"Komentarze"})).toHaveAttribute("tabindex","-1");
  expect(screen.getByRole("tabpanel",{name:"Opis"})).toHaveTextContent("Treść opisu");
  expect(screen.getAllByRole("tabpanel")).toHaveLength(1);
});

test("strzałki zawijają się, a Home i End skaczą na skraje",async()=>{
  const user=userEvent.setup();
  render(<Tabs label="Sekcje" tabs={demoTabs}/>);
  await user.tab();
  expect(screen.getByRole("tab",{name:"Opis"})).toHaveFocus();
  await user.keyboard("{ArrowLeft}");
  expect(screen.getByRole("tab",{name:"Historia",selected:true})).toHaveFocus();
  await user.keyboard("{ArrowRight}");
  expect(screen.getByRole("tab",{name:"Opis",selected:true})).toHaveFocus();
  await user.keyboard("{End}");
  expect(screen.getByRole("tab",{name:"Historia",selected:true})).toHaveFocus();
  await user.keyboard("{Home}");
  expect(screen.getByRole("tab",{name:"Opis",selected:true})).toHaveFocus();
});

test("Tab z zakładek przechodzi do panelu, nie do kolejnej zakładki",async()=>{
  const user=userEvent.setup();
  render(<Tabs label="Sekcje" tabs={demoTabs}/>);
  await user.tab();
  await user.tab();
  expect(screen.getByRole("tabpanel",{name:"Opis"})).toHaveFocus();
});

test("region na żywo istnieje przed komunikatem i ogłasza go po zmianie",async()=>{
  function Save(){
    const announce=useAnnounce();
    return <button onClick={()=>announce("Ticket zapisany")}>Zapisz</button>;
  }
  render(<Announcer><Save/></Announcer>);
  const region=screen.getByRole("status");
  expect(region).toBeEmptyDOMElement();
  await userEvent.setup().click(screen.getByRole("button"));
  await waitFor(()=>expect(region).toHaveTextContent("Ticket zapisany"));
  expect(screen.getByRole("status")).toBe(region);
});

test("powtórzony komunikat najpierw czyści region",async()=>{
  const frames:FrameRequestCallback[]=[];
  const raf=vi.spyOn(window,"requestAnimationFrame").mockImplementation(callback=>{frames.push(callback);return frames.length;});
  let send:(text:string)=>void=()=>{};
  function Grab(){send=useAnnounce();return null;}
  render(<Announcer><Grab/></Announcer>);
  act(()=>send("Zapisano"));
  act(()=>frames.shift()!(0));
  expect(screen.getByRole("status")).toHaveTextContent("Zapisano");
  act(()=>send("Zapisano"));
  expect(screen.getByRole("status")).toBeEmptyDOMElement();
  act(()=>frames.shift()!(0));
  expect(screen.getByRole("status")).toHaveTextContent("Zapisano");
  raf.mockRestore();
});

test("błąd trafia do regionu alert, nie do status",async()=>{
  function Fail(){
    const announceError=useAnnounceError();
    return <button onClick={()=>announceError("Nie zapisano")}>Zapisz</button>;
  }
  render(<Announcer><Fail/></Announcer>);
  await userEvent.setup().click(screen.getByRole("button"));
  await waitFor(()=>expect(screen.getByRole("alert")).toHaveTextContent("Nie zapisano"));
  expect(screen.getByRole("status")).toBeEmptyDOMElement();
});

test("disclosure pokazuje stan i oddaje fokus po Escape",async()=>{
  const user=userEvent.setup();
  render(<Disclosure label="Akcje"><button>Przypisz</button></Disclosure>);
  const trigger=screen.getByRole("button",{name:"Akcje"});
  expect(trigger).toHaveAttribute("aria-expanded","false");
  expect(screen.queryByRole("button",{name:"Przypisz"})).toBeNull();
  await user.click(trigger);
  expect(trigger).toHaveAttribute("aria-expanded","true");
  await user.click(screen.getByRole("button",{name:"Przypisz"}));
  await user.keyboard("{Escape}");
  expect(trigger).toHaveAttribute("aria-expanded","false");
  expect(trigger).toHaveFocus();
});

test("menu obsługuje strzałki, wybór i powrót fokusu",async()=>{
  const user=userEvent.setup(),assign=vi.fn(),close=vi.fn();
  render(<ActionMenu label="Akcje" actions={[{id:"a",label:"Przypisz",onSelect:assign},{id:"c",label:"Zamknij",onSelect:close}]}/>);
  const trigger=screen.getByRole("button",{name:"Akcje"});
  await user.click(trigger);
  expect(screen.getByRole("menuitem",{name:"Przypisz"})).toHaveFocus();
  await user.keyboard("{ArrowDown}");
  expect(screen.getByRole("menuitem",{name:"Zamknij"})).toHaveFocus();
  await user.keyboard("{ArrowDown}");
  expect(screen.getByRole("menuitem",{name:"Przypisz"})).toHaveFocus();
  await user.keyboard("{ArrowUp}{Enter}");
  expect(close).toHaveBeenCalledTimes(1);
  expect(assign).not.toHaveBeenCalled();
  expect(trigger).toHaveFocus();
  expect(trigger).toHaveAttribute("aria-expanded","false");
});

test("Escape zamyka menu bez wykonania akcji",async()=>{
  const user=userEvent.setup(),assign=vi.fn();
  render(<ActionMenu label="Akcje" actions={[{id:"a",label:"Przypisz",onSelect:assign}]}/>);
  await user.click(screen.getByRole("button",{name:"Akcje"}));
  await user.keyboard("{Escape}");
  expect(assign).not.toHaveBeenCalled();
  expect(screen.getByRole("button",{name:"Akcje"})).toHaveFocus();
});

test("przycisk przełączany odzwierciedla stan przez aria-pressed",async()=>{
  const user=userEvent.setup();
  render(<PinButton/>);
  const button=screen.getByRole("button",{name:"Przypnij ticket",pressed:false});
  await user.click(button);
  expect(screen.getByRole("button",{name:"Przypnij ticket",pressed:true})).toBe(button);
});`;
const exercises=[
['Rozróżnij role, stany i właściwości ARIA na przykładzie zakładek.','Co opisuje typ elementu, co się zmienia w czasie, a co jest relacją?','Rola to role="tab" i role="tabpanel". Stan to aria-selected, który zmienia się razem ze stanem Reacta. Właściwości i relacje to aria-controls oraz aria-labelledby, które łączą zakładkę z panelem po identyfikatorze.'],
['Napisz zakładki z rolami tablist, tab i tabpanel.','Każda zakładka wskazuje swój panel, a panel swoją zakładkę.',tabs],
['Dodaj roving tabindex do listy zakładek.','Tylko aktywna zakładka ma tabIndex={0}.',tabs],
['Obsłuż ArrowLeft i ArrowRight z zawijaniem.','Indeks licz modulo długość listy, także dla wartości ujemnej.',tabs],
['Dodaj klawisze Home i End.','Nieobsłużone klawisze nie powinny wywoływać preventDefault.',tabs],
['Generuj identyfikatory relacji przez useId.','Dwie instancje zakładek nie mogą mieć tych samych id.',tabs],
['Sprawdź zakładki testem zachowania.','Pytaj o role i stan, np. getByRole("tab",{selected:true}).',tests],
['Sprawdź, że Tab z listy zakładek przechodzi do panelu.','To efekt roving tabindex i tabIndex={0} na panelu.',tests],
['Wybierz między aktywacją automatyczną a ręczną zakładek.','Pomyśl o kosztownym ładowaniu treści panelu.','Automatyczna aktywacja, czyli zmiana panelu przy samej strzałce, jest dobra, gdy treść pojawia się od razu. Gdy przełączenie pobiera dane, ręczna aktywacja jest lepsza: strzałka przenosi fokus, a Enter lub Spacja dopiero wybiera. Inaczej samo przechodzenie po zakładkach wywoła serię żądań.'],
['Zbuduj Announcer z regionem renderowanym od początku.','Region musi istnieć w DOM, zanim zmieni się jego treść.',announcer],
['Spraw, by powtórzony komunikat został ogłoszony drugi raz.','Sama identyczna wartość stanu nie zmieni DOM.',announcer],
['Dodaj osobny kanał błędów przez role="alert".','Błąd krytyczny może przerwać czytanie, zwykłe potwierdzenie nie.',announcer],
['Napraw warunkowy region na żywo.','Nie renderuj go razem z komunikatem.','// Źle: element pojawia się razem z treścią i część czytników milczy.\n{saved&&<div role="status">Zapisano</div>}\n// Dobrze: kontener jest stale w DOM, a zmienia się tylko jego treść.\n<div role="status">{saved?"Zapisano":""}</div>'],
['Przetestuj powtórne ogłoszenie tej samej treści.','Zastąp requestAnimationFrame kontrolowaną kolejką.',tests],
['Wybierz polite lub assertive dla czterech komunikatów DevDesk.','Czy wiadomość musi przerwać użytkownikowi czytanie?','„Zapisano ticket” i „Znaleziono 12 wyników” to polite. Utrata połączenia podczas pisania komentarza i wygaśnięcie sesji to role="alert", bo użytkownik musi od razu zareagować. Assertive używany wszędzie przerywa czytanie i staje się szumem.'],
['Dodaj klasę visually-hidden.','display:none ukryłby tekst również przed czytnikiem.',css],
['Uzasadnij wybór między visually-hidden, hidden i aria-hidden.','Porównaj, kto widzi treść: oczy, a kto czytnik.','visually-hidden: niewidoczne dla oczu, dostępne dla czytnika, np. dodatkowy kontekst przycisku. hidden lub display:none: niewidoczne dla obu i bez fokusu. aria-hidden="true": widoczne dla oczu, niedostępne dla czytnika, np. ikona dekoracyjna; nie wolno go stosować na elementach z fokusem.'],
['Napisz wzorzec disclosure dla listy akcji.','Przycisk i aria-expanded zwykle wystarczą.',disclosure],
['Oddawaj fokus do przycisku po Escape.','Bez tego fokus zostanie na ukrytym elemencie.',disclosure],
['Zbuduj menu z rolami menu i menuitem.','Pełny wzorzec wymaga obsługi strzałek, Escape i wyboru.',menu],
['Ustaw fokus na pierwszej pozycji po otwarciu menu.','Efekt działa po commicie, gdy element jest już widoczny.',menu],
['Dodaj przycisk przełączany z aria-pressed.','Nazwa pozostaje stała, a stan niesie atrybut.',toggle],
['Oceń, czy menu akcji ticketu powinno używać role="menu".','Czy użytkownik oczekuje zachowania menu aplikacji?','Dla kilku akcji w panelu ticketu prostszy jest disclosure z listą zwykłych przycisków: mniej obsługi klawiatury i mniej błędów. role="menu" ma sens przy zachowaniu menu aplikacji, z pełną obsługą strzałek. Sama rola bez klawiatury to obietnica bez pokrycia.'],
['Wypisz polecenia NVDA do sprawdzenia ekranu ticketu.','Wystarczą trzy polecenia na start.','Insert+strzałka w dół czyta dalej, H przechodzi do następnego nagłówka, D do następnego punktu orientacyjnego. Tab sprawdza kolejność fokusu. Zapisuj, co słychać: nazwę ekranu, nagłówki, nazwy przycisków i stan zakładek.'],
['Zapisz, co powinien usłyszeć użytkownik na zakładce Komentarze.','Rola, nazwa, stan i pozycja.','Przykładowo: „Komentarze, zakładka, wybrana, 2 z 3”. Dokładne brzmienie zależy od czytnika i przeglądarki, ale nazwa, rola i stan muszą się w nim znaleźć. Liczbę pozycji czytnik wylicza z tablist.'],
['Sprawdź drzewo dostępności w DevTools.','Panel Accessibility pokazuje policzoną nazwę i rolę.','Otwórz Elements, wybierz element i zakładkę Accessibility. Porównaj Name i Role z oczekiwaniami. Pusta nazwa przycisku to błąd do naprawy. Włącz pełne drzewo dostępności, aby zobaczyć cały ekran tak, jak widzą go technologie wspomagające.'],
['Wyjaśnij, co TSX wychwyci w atrybutach ARIA.','Literówka nazwy to jedno, poprawność wzorca to drugie.','TSX zgłosi aria-selcted oraz aria-live="loud". Nie zgłosi aria-selected na elemencie bez pasującej roli ani braku obsługi klawiatury. Poprawność wzorca sprawdzają testy i czytnik ekranu.'],
['Dodaj test stanu aria-pressed i aria-expanded.','Zapytaj o stan w getByRole, a nie o implementację.',tests],
['Napisz plan przeglądu ekranu ticketu czytnikiem.','Ma powstać lista problemów z kryteriami.','1. Włącz czytnik i przejdź nagłówki. 2. Przejdź punkty orientacyjne. 3. Sprawdź zakładki strzałkami. 4. Zapisz ticket i wysłuchaj ogłoszenia. 5. Wywołaj błąd zapisu. 6. Każdy problem zapisz jako: kroki, słyszany wynik, oczekiwanie i kryterium WCAG, np. 4.1.3 dla komunikatów stanu.'],
['Sformułuj kryterium akceptacji komponentów ARIA.','Zachowanie sprawdzalne testem i czytnikiem.','Zakładki działają strzałkami, Home i End, a Tab przechodzi do panelu. Zapis i błędy są ogłaszane przez stale wyrenderowane regiony. Menu oddaje fokus po Escape. Testy RTL sprawdzają role i stany, a ekran został przejrzany czytnikiem ekranu z zapisanymi wynikami.'],
];
const concepts=[
['Co oznacza skrót ARIA?','Accessible Rich Internet Applications','Automated React Interface Attributes','Advanced Role Inheritance API','Accessible Responsive Input Adapter','ARIA opisuje role, stany i relacje elementów.'],
['Jaki dokument opisuje wzorce komponentów ARIA?','WAI-ARIA Authoring Practices (APG)','Dokumentacja React DOM','Specyfikacja CSS','Raport Lighthouse','APG podaje wymagane role i obsługę klawiatury.'],
['Który atrybut to stan, a nie relacja?','aria-expanded','aria-controls','aria-labelledby','aria-describedby','Stan zmienia się w czasie i musi odzwierciedlać stan Reacta.'],
['Który atrybut wskazuje relację z elementem nazywającym?','aria-labelledby','aria-pressed','aria-busy','aria-checked','Wartością jest identyfikator elementu będącego nazwą.'],
['Na czym polega roving tabindex?','Tylko jeden element grupy jest w kolejności Tab, a resztą steruje się strzałkami','Każdy element ma tabIndex={0}','Tab przechodzi w odwrotnej kolejności','Fokus przenosi się automatycznie co sekundę','Grupa zajmuje jeden przystanek Tab, a strzałki poruszają się wewnątrz niej.'],
['Co powinno dziać się po Tab z aktywnej zakładki?','Fokus przechodzi do panelu lub następnego elementu','Fokus idzie na następną zakładkę','Zakładka się zamyka','Fokus wraca na początek strony','Strzałki poruszają się w zakładkach, a Tab wychodzi z grupy.'],
['Dlaczego panel zakładki ma tabIndex={0}?','Gdy nie zawiera elementów z fokusem, klawiatura też może do niego dojść','Aby był pierwszy w kolejności Tab','Aby był ukryty','React tego wymaga','APG zaleca fokusowalny panel, jeśli nie ma w nim elementów interaktywnych.'],
['Jaką rolę ma kontener zakładek?','tablist','tabs','menu','navigation','Właściwa rola kontenera to tablist.'],
['Jaką nazwę powinien mieć tablist?','Opisową: przez aria-label lub aria-labelledby','Żadnej','Taką samą jak pierwsza zakładka','Nazwę komponentu React','Nazwa wyjaśnia, czego dotyczą zakładki.'],
['Co powinien zawierać aria-controls zakładki?','Identyfikator jej panelu','Tekst tytułu panelu','Indeks zakładki','Nazwę funkcji obsługi','Relacja opiera się na id elementu.'],
['Skąd wziąć identyfikatory w React dla ARIA?','Z useId','Z Math.random w renderze','Ze stałego tekstu w kodzie komponentu','Z indeksu tablicy bez prefiksu','useId jest stabilny i zgodny z SSR, a Math.random psuje hydratację.'],
['Po co preventDefault na obsłużonych klawiszach?','Aby strzałki i Home nie przewijały strony','Aby zatrzymać render','Aby zablokować Tab w całej aplikacji','Aby fokus znikał','Nieobsłużonych klawiszy nie blokujemy, żeby np. Tab dalej działał.'],
['Czym jest region na żywo?','Elementem, którego zmianę treści czytnik ogłasza bez przenoszenia fokusu','Elementem animowanym','Kanałem WebSocket','Listą z automatycznym odświeżaniem','aria-live, status i alert ogłaszają zmiany.'],
['Dlaczego warunkowy role="status" często nie działa?','Czytnik musi zarejestrować region przed zmianą jego treści','React nie renderuje ról warunkowo','Status wymaga JavaScriptu serwerowego','Rola działa tylko na body','Treść dodana razem z elementem może nie zostać ogłoszona.'],
['Która wartość aria-live jest domyślnie właściwa dla potwierdzeń?','polite','assertive','off','rude','Polite czeka na przerwę w odczycie.'],
['Jaką implicit wartość aria-live ma role="alert"?','assertive','polite','off','Nie ma żadnej','alert przerywa użytkownikowi czytanie, więc używaj go oszczędnie.'],
['Jaką implicit wartość aria-live ma role="status"?','polite','assertive','off','Nie ma żadnej','Status to spokojne ogłoszenie stanu.'],
['Które kryterium WCAG dotyczy komunikatów stanu?','4.1.3','1.1.1','2.4.7','1.4.3','Komunikaty stanu muszą być dostępne bez przenoszenia fokusu.'],
['Czemu w Announcer najpierw czyścisz wiadomość?','Inaczej ta sama treść nie zmieni DOM i nie zostanie ogłoszona','Aby oszczędzić pamięć','Aby region zniknął z DOM','Aby wymusić przeładowanie strony','Czyszczenie i ustawienie w kolejnej klatce to dwie zmiany treści.'],
['Czemu toast znikający po 2 sekundach jest problemem?','Czytnik lub osoba czytająca wolniej może nie zdążyć','Bo zajmuje zbyt dużo pamięci','Bo React nie obsługuje timerów','Bo blokuje Tab','Ważna informacja nie powinna znikać po krótkim czasie bez innego śladu.'],
['Czym różni się visually-hidden od display:none?','Pierwsze zostawia tekst dla czytnika','Niczym','display:none zostawia tekst dla czytnika','Pierwsze usuwa element z DOM','Klasa ukrywa wizualnie, ale zostawia treść w drzewie dostępności.'],
['Co robi aria-hidden="true"?','Usuwa element z drzewa dostępności, ale nie z widoku','Ukrywa element wizualnie','Usuwa element z DOM','Wyłącza kliknięcia','Dlatego nadaje się do dekoracji, a nie do elementów z fokusem.'],
['Czym różni się aria-disabled od disabled?','aria-disabled tylko opisuje stan, element zostaje w kolejności Tab i trzeba zablokować akcję','Niczym','disabled tylko opisuje stan','aria-disabled usuwa element z DOM','Natywny disabled blokuje zdarzenia i fokus, a ARIA jest tylko opisem.'],
['Co opisuje aria-busy?','Treść jest w trakcie aktualizacji','Przycisk jest wciśnięty','Pole jest wymagane','Element jest ukryty','Stan pomaga oznaczyć ładowany region.'],
['Do czego służy aria-pressed?','Do przycisku przełączanego','Do rozwijanej listy','Do zakładki','Do pola tekstowego','Stan mówi, czy przycisk jest wciśnięty.'],
['Do czego służy aria-expanded?','Do elementu pokazującego lub ukrywającego inną treść','Do pola formularza z błędem','Do zakładki aktywnej','Do obrazka','Stan informuje, czy powiązana treść jest rozwinięta.'],
['Czego nie zmieniamy razem z aria-pressed?','Nazwy przycisku','Wartości aria-pressed','Wyglądu wciśnięcia','Stanu Reacta','„Przypnij, wciśnięty” jest jasne, ale „Odepnij, wciśnięty” brzmi sprzecznie.'],
['Co oznacza aria-haspopup="menu"?','Przycisk otwiera menu','Element jest dialogiem','Element ma zagnieżdżone pole','Lista jest posortowana','Czytnik zapowiada wyskakujące menu.'],
['Czego oczekuje użytkownik po role="menu"?','Obsługi strzałek i zachowania menu aplikacji','Zwykłych linków w kolejności Tab','Automatycznego zamknięcia po sekundzie','Pola wyszukiwania','Rola deklaruje model interakcji, który trzeba zaimplementować.'],
['Kiedy disclosure jest lepszy od role="menu"?','Gdy to prosta lista akcji, które mogą być zwykłymi przyciskami','Gdy akcji jest bardzo dużo i potrzeba podmenu','Gdy nie używasz klawiatury','Nigdy','Mniej semantyki oznacza mniej obietnic do spełnienia.'],
['Dokąd wraca fokus po Escape w menu?','Do przycisku, który je otworzył','Na początek strony','Na pierwszy link','Na body','Użytkownik musi pozostać w tym samym miejscu.'],
['Co jest błędne w <a role="button" href="/x">?','Rola mówi „przycisk”, a element zachowuje się jak link','Nic','Brak aria-label','Brak type','Spacja nie aktywuje linku, więc rola obiecuje coś, czego nie ma.'],
['Co oznacza „role bez klawiatury to obietnica bez pokrycia”?','Rola zapowiada zachowanie, które musisz sam zaimplementować','Że role są zabronione','Że klawiatura działa automatycznie po dodaniu roli','Że czytnik ignoruje role','ARIA zmienia wyłącznie to, co słyszy użytkownik.'],
['Czy TSX wychwyci aria-selcted?','Tak: nieznany atrybut to błąd kompilacji','Nie, żaden atrybut nie jest sprawdzany','Tylko w trybie produkcyjnym','Tylko z ESLint','Typy React znają atrybuty aria.'],
['Czy TSX wychwyci role="tab" poza tablist?','Nie: poprawność wzorca sprawdza test lub axe','Tak, zawsze','Tak, z noImplicitAny','Tak, dzięki AriaRole','Typ AriaRole sprawdza nazwę roli, nie strukturę.'],
['Jaki typ React opisuje wartość role?','AriaRole','HTMLRole','ReactAttribute','DomRole','AriaRole daje podpowiedzi, a przy tym dopuszcza dowolny tekst.'],
['Jaki typ opisuje wartości aria-live?','"off" | "polite" | "assertive"','boolean','number','"on" | "off"','Inna wartość to błąd w TSX.'],
['Co robi NVDA po wciśnięciu H?','Przechodzi do następnego nagłówka','Wyłącza mowę','Otwiera pomoc systemu','Czyta adres strony','To jedno z podstawowych poleceń przeglądania.'],
['Jak włączyć VoiceOver na macOS?','Cmd+F5','Ctrl+Alt+Del','F12','Shift+Tab','To standardowy skrót VoiceOver.'],
['Jak włączyć Narratora w Windows?','Ctrl+Win+Enter','Alt+F4','Win+D','Ctrl+Shift+Esc','Narrator jest wbudowany w Windows.'],
['Dlaczego warto testować kilkoma czytnikami?','Różnią się interpretacją ARIA w parach z przeglądarkami','Bo każdy ma inny DOM','Bo axe tego wymaga','Bo React renderuje inaczej w każdym','Dobry zestaw to np. NVDA z Firefoksem lub Chrome oraz VoiceOver z Safari.'],
['Co pokazuje drzewo dostępności?','Role, nazwy i stany dostępne technologiom wspomagającym','Kolejność renderowania Fibera','Wynik Lighthouse','Listę zdarzeń React','To widok strony z perspektywy czytnika.'],
['Czy aria-label działa na każdym elemencie tak samo dobrze?','Nie: na elementach bez odpowiedniej roli bywa ignorowany','Tak','Tak, ale tylko na div','Tak, gdy ma ponad 3 słowa','Etykieta powinna trafiać na elementy z rolą interaktywną lub orientacyjną.'],
['Który wybór jest najlepszy dla etykiety pokazanej też tekstem?','aria-labelledby do widocznego nagłówka','Powtórzenie tekstu w aria-label','Atrybut title','Atrybut alt','Jedno źródło tekstu ogranicza rozjeżdżanie się nazw i tłumaczeń.'],
['Co sprawdza getByRole("tab",{selected:true})?','Rolę i stan z drzewa dostępności','Klasę CSS active','Kolor zakładki','Stan Reacta wprost','Test widzi to, co dostaje użytkownik czytnika.'],
['Które rozwiązanie jest poprawne dla licznika wyników filtru?','Stały status z tekstem „Znaleziono 12 wyników”','alert przy każdym znaku','Przeniesienie fokusu na licznik','Brak ogłoszenia, bo jest widoczny','Polite status informuje bez przerywania pisania.'],
['Jak ograniczyć zalew ogłoszeń przy wpisywaniu w filtr?','Ogłaszać z opóźnieniem, po przerwie w pisaniu','Używać assertive','Ogłaszać każdą literę','Wyłączyć filtr','Odłożenie ogłoszenia zmniejsza szum.'],
['Co oznacza aria-current="page" w nawigacji?','Link wskazuje bieżącą stronę','Link jest wyłączony','Link otworzy nową kartę','Strona się ładuje','To stan przydatny w menu i ścieżce nawigacji.'],
['Jaki atrybut ma dialog modalny?','aria-modal="true" z role="dialog" i nazwą','aria-live="polite"','role="alert" zawsze','aria-pressed','Natywny dialog z showModal rozwiązuje wiele z tych kwestii.'],
['Co daje natywny element dialog otwarty przez showModal?','Tło inertne, Escape i obsługę fokusu','Automatyczne tłumaczenia','Walidację formularza','Brak potrzeby nazwy','Nadal trzeba mu nadać nazwę, np. przez aria-labelledby.'],
['Co robi atrybut inert?','Blokuje interakcję i wyłącza element z fokusu oraz drzewa dostępności','Tylko zmienia kolor','Wyłącza CSS','Ładuje element leniwie','To dobry sposób na zablokowanie tła pod warstwą.'],
['Co jest potrzebne przy aria-activedescendant?','Fokus zostaje na kontenerze, a atrybut wskazuje aktywną opcję','Fokus przechodzi po wszystkich opcjach','Tab w każdej opcji','role="alert" na opcji','To alternatywa dla roving tabindex, np. w comboboxie.'],
['Jaką rolę ma pole z podpowiedziami agentów?','combobox','textbox zawsze','listbox zawsze','menu','Wzorzec łączy pole, listę i relacje stanu.'],
['Co oznacza, że błędna ARIA jest gorsza niż jej brak?','Wprowadza w błąd zamiast dawać neutralną strukturę','Że ARIA jest zakazana','Że React ją usuwa','Że strona działa wolniej','Fałszywa informacja powoduje działania niezgodne z oczekiwaniem.'],
['Jaki sposób testu najlepiej wykryje, że toast nie jest ogłaszany?','Przejście z czytnikiem ekranu i test regionu w RTL','Tylko Lighthouse','Tylko tsc','Sprawdzenie kolorów','Sama obecność tekstu w DOM nie dowodzi ogłoszenia.'],
['Co powinien zawierać zapis wyniku przeglądu czytnikiem?','Kroki, czytnik, przeglądarkę, słyszany wynik i oczekiwanie','Sam wynik punktowy','Zrzut ekranu kodu','Nazwy komponentów','Wynik zależy od pary czytnik-przeglądarka.'],
['Kiedy tooltip jest niewłaściwym miejscem na kluczową informację?','Gdy nie da się go wywołać dotykiem lub klawiaturą','Zawsze, gdy ma tekst','Tylko w ciemnym motywie','Gdy jest krótszy niż 5 słów','Treść dostępna tylko po najechaniu wyklucza część użytkowników.'],
['Jaki atrybut wiąże tooltip z przyciskiem?','aria-describedby','aria-pressed','aria-current','aria-sort','Tooltip uzupełnia opis, a nazwę daje sam przycisk.'],
['Jaki atrybut opisuje sortowanie kolumny tabeli?','aria-sort na th','aria-pressed na td','role="sort"','aria-order','Stan aria-sort jest przypisany do nagłówka kolumny.'],
['Co jest prawdą o komponentach Radix lub React Aria?','Dają gotowe wzorce, ale nazwy i poprawne użycie należą do Ciebie','Zwalniają z testów','Działają tylko bez TypeScriptu','Zastępują czytniki ekranu','Biblioteka rozwiązuje trudne zachowania, nie całą aplikację.'],
];
const header=String.raw`import {observeTabs,observeDisclosure,observeMenu,observePin} from "./ariaLab";`;
const probeRows=[
['Która zakładka jest aktywna na początku?','observeTabs().selected','Opis','Domyślnie aktywna jest pierwsza.',['Komentarze','Historia','brak']],
['Ile elementów listy zakładek jest w kolejności Tab?','observeTabs().tabStops',1,'To istota roving tabindex.'],
['Ile paneli jest widocznych?','observeTabs().visiblePanels',1,'Pozostałe panele mają atrybut hidden.'],
['Czy panel jest powiązany z aktywną zakładką?','observeTabs().linked',true,'aria-labelledby wskazuje zakładkę.'],
['Czy zakładka wskazuje widoczny panel?','observeTabs().controls',true,'aria-controls wskazuje panel.'],
['Jaka jest nazwa listy zakładek?','observeTabs().listName','Sekcje ticketu','Lista dostała aria-label z propa.',['Opis','tablist','brak']],
['Co jest aktywne po ArrowRight?','observeTabs(["ArrowRight"]).selected','Komentarze','Strzałka przechodzi do następnej zakładki.',['Opis','Historia','brak']],
['Co ma fokus po ArrowRight?','observeTabs(["ArrowRight"]).focused','Komentarze','Fokus przechodzi razem z aktywną zakładką.',['Opis','Historia','brak']],
['Co jest aktywne po ArrowLeft z pierwszej zakładki?','observeTabs(["ArrowLeft"]).selected','Historia','Indeks zawija się na koniec.',['Opis','Komentarze','brak']],
['Co jest aktywne po trzech ArrowRight?','observeTabs(["ArrowRight","ArrowRight","ArrowRight"]).selected','Opis','Po ostatniej zakładce wracamy na początek.',['Komentarze','Historia','brak']],
['Co jest aktywne po End?','observeTabs(["End"]).selected','Historia','End przechodzi do ostatniej.',['Opis','Komentarze','brak']],
['Co jest aktywne po End i Home?','observeTabs(["End","Home"]).selected','Opis','Home przechodzi do pierwszej.',['Historia','Komentarze','brak']],
['Jaka treść panelu jest widoczna po End?','observeTabs(["End"]).panelText','Treść historii','Widoczny panel odpowiada aktywnej zakładce.',['Treść opisu','Treść komentarzy','brak']],
['Ile stopów Tab jest po ArrowRight?','observeTabs(["ArrowRight"]).tabStops',1,'Roving tabindex przenosi jedyny stop.'],
['Co jest aktywne po klawiszu ArrowDown?','observeTabs(["ArrowDown"]).selected','Opis','Zakładki poziome w tym komponencie nie obsługują tego klawisza.',['Komentarze','Historia','brak']],
['Co jest aktywne po klawiszu a?','observeTabs(["a"]).selected','Opis','Nieobsłużony klawisz nic nie zmienia.',['Komentarze','Historia','brak']],
['Jaką wartość aria-expanded ma disclosure na początku?','observeDisclosure([]).expanded','false','Treść jest zwinięta.',['true','brak','undefined']],
['Ile wewnętrznych przycisków widać przed otwarciem?','observeDisclosure([]).inner',0,'Atrybut hidden usuwa treść z drzewa dostępności.'],
['Jaką wartość aria-expanded ma disclosure po kliknięciu?','observeDisclosure(["click"]).expanded','true','Stan ARIA odzwierciedla stan Reacta.',['false','brak','undefined']],
['Ile wewnętrznych przycisków widać po kliknięciu?','observeDisclosure(["click"]).inner',1,'Rozwinięta treść wraca do drzewa dostępności.'],
['Jaką wartość aria-expanded ma disclosure po drugim kliknięciu?','observeDisclosure(["click","click"]).expanded','false','Przycisk przełącza stan.',['true','brak','undefined']],
['Jaką wartość aria-expanded ma disclosure po Escape?','observeDisclosure(["click","escape"]).expanded','false','Escape zamyka rozwiniętą treść.',['true','brak','undefined']],
['Czy po Escape fokus jest na przycisku?','observeDisclosure(["click","escape"]).focusOnTrigger',true,'Fokus wraca do elementu otwierającego.'],
['Jakie jest aria-haspopup przycisku menu?','observeMenu().haspopup','menu','Atrybut zapowiada typ wyskakującego elementu.',['true','dialog','brak']],
['Która pozycja menu ma fokus po otwarciu?','observeMenu().focused','Przypisz','Fokus idzie na pierwszą pozycję.',['Akcje','Usuń','brak']],
['Która pozycja ma fokus po ArrowDown?','observeMenu(["ArrowDown"]).focused','Zamknij','Strzałka przesuwa aktywną pozycję.',['Przypisz','Usuń','Akcje']],
['Która pozycja ma fokus po ArrowUp z pierwszej?','observeMenu(["ArrowUp"]).focused','Usuń','Lista zawija się na koniec.',['Przypisz','Zamknij','Akcje']],
['Która pozycja ma fokus po End?','observeMenu(["End"]).focused','Usuń','End przechodzi na ostatnią pozycję.',['Przypisz','Zamknij','Akcje']],
['Jaka akcja wykona się po ArrowDown i Enter?','observeMenu(["ArrowDown","Enter"]).calls','Zamknij','Enter wybiera aktywną pozycję.',['Przypisz','Usuń','brak']],
['Jaka akcja wykona się po Spacji na pierwszej pozycji?','observeMenu([" "]).calls','Przypisz','Spacja działa jak Enter.',['Zamknij','Usuń','brak']],
['Jaka akcja wykona się po Escape?','observeMenu(["Escape"]).calls','brak','Escape zamyka menu bez wyboru.',['Przypisz','Zamknij','Usuń']],
['Jaką wartość aria-expanded ma menu po wyborze?','observeMenu(["Enter"]).expanded','false','Po wyborze menu się zamyka.',['true','brak','undefined']],
['Czy po wyborze fokus wraca do przycisku?','observeMenu(["Enter"]).focusOnTrigger',true,'Użytkownik zostaje w tym samym miejscu.'],
['Czy menu jest widoczne po Escape?','observeMenu(["Escape"]).menuVisible',false,'Lista dostaje atrybut hidden.'],
['Czy menu jest widoczne po otwarciu?','observeMenu().menuVisible',true,'Kliknięcie przełącza widoczność.'],
['Jaka jest wartość aria-pressed na początku?','observePin(0).pressed','false','Przycisk nie jest wciśnięty.',['true','brak','mixed']],
['Jaka jest wartość aria-pressed po kliknięciu?','observePin(1).pressed','true','Stan odzwierciedla useState.',['false','brak','mixed']],
['Jaka jest wartość aria-pressed po dwóch kliknięciach?','observePin(2).pressed','false','Drugie kliknięcie wraca do stanu początkowego.',['true','brak','mixed']],
['Jaka nazwa ma przycisk po wciśnięciu?','observePin(1).name','Przypnij ticket','Nazwa zostaje stała, a stan niesie atrybut.',['Odepnij ticket','brak','true']],
['Ile zakładek jest w kolejności Tab po End?','observeTabs(["End"]).tabStops',1,'Jedyny stop przeszedł na ostatnią zakładkę.'],
];
const project={
'src/Tabs.tsx':tabs,'src/Announcer.tsx':announcer,'src/Disclosure.tsx':disclosure,'src/ActionMenu.tsx':menu,'src/PinButton.tsx':toggle,'src/a11y.css':css,'src/ariaLab.tsx':lab,'src/aria.test.tsx':tests,
'README.md':'DevDesk: wzorce ARIA. npm install, npm test. Test RTL sprawdza role, stany i klawiaturę. Ogłaszanie przez czytnik trzeba jeszcze sprawdzić ręcznie w NVDA, VoiceOver lub Narratorze.\n',
};
const mutants=[
{file:'src/Tabs.tsx',before:'tabIndex={index===active?0:-1}',after:'tabIndex={0}'},
{file:'src/Tabs.tsx',before:'(index+tabs.length)%tabs.length',after:'Math.max(0,Math.min(index,tabs.length-1))'},
{file:'src/Tabs.tsx',before:'hidden={index!==active}',after:''},
{file:'src/Tabs.tsx',before:'refs.current[next]?.focus();',after:''},
{file:'src/Tabs.tsx',before:'aria-labelledby={baseId+"-tab-"+index}',after:''},
{file:'src/Announcer.tsx',before:'    setMessage("");\n',after:''},
{file:'src/Announcer.tsx',before:'requestAnimationFrame(()=>setAlertMessage(text));',after:'requestAnimationFrame(()=>setMessage(text));'},
{file:'src/Disclosure.tsx',before:'trigger.current?.focus();',after:''},
{file:'src/Disclosure.tsx',before:'aria-expanded={open}',after:''},
{file:'src/ActionMenu.tsx',before:'(index-1+count)%count',after:'Math.max(index-1,0)'},
{file:'src/ActionMenu.tsx',before:'    trigger.current?.focus();\n',after:''},
{file:'src/ActionMenu.tsx',before:'useEffect(()=>{if(open)items.current[active]?.focus();},[open,active]);',after:''},
{file:'src/PinButton.tsx',before:'aria-pressed={pressed}',after:''},
];
module.exports=lesson({exercises,concepts,probeRows,project,mutants,header});
