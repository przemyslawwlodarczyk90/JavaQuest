const {lesson}=require('./practice_kit.cjs');
const toasts=String.raw`import {AnimatePresence,motion} from "motion/react";

export type ToastItem={id:string;message:string};
type ToastsProps={toasts:readonly ToastItem[];onDismiss:(id:string)=>void};

export function Toasts({toasts,onDismiss}:ToastsProps){
  return (
    <ol className="toasts" aria-live="polite">
      <AnimatePresence initial={false}>
        {toasts.map(toast=>(
          <motion.li
            key={toast.id}
            layout
            initial={{opacity:0,y:16,scale:0.95}}
            animate={{opacity:1,y:0,scale:1}}
            exit={{opacity:0,x:80,pointerEvents:"none",transition:{duration:0.15}}}
            transition={{type:"spring",stiffness:500,damping:35}}
          >
            {toast.message}
            <button type="button" aria-label={"Zamknij: "+toast.message} onClick={()=>onDismiss(toast.id)}>×</button>
          </motion.li>
        ))}
      </AnimatePresence>
    </ol>
  );
}`;
const tabs=String.raw`import {motion} from "motion/react";
import {useState} from "react";

export const TABS=[
  {id:"details",label:"Szczegóły"},
  {id:"activity",label:"Aktywność"},
  {id:"history",label:"Historia"},
] as const;

export type TabId=(typeof TABS)[number]["id"];

export function AnimatedTabs({onChange}:{onChange?:(id:TabId)=>void}){
  const [active,setActive]=useState<TabId>("details");
  return (
    <div role="tablist" aria-label="Sekcje ticketu">
      {TABS.map(tab=>(
        <button
          key={tab.id}
          role="tab"
          type="button"
          aria-selected={active===tab.id}
          onClick={()=>{setActive(tab.id);onChange?.(tab.id);}}
          style={{position:"relative"}}
        >
          {tab.label}
          {active===tab.id&&(
            <motion.span data-testid="underline" layoutId="tab-underline" transition={{type:"spring",stiffness:500,damping:40}}/>
          )}
        </button>
      ))}
    </div>
  );
}`;
const panels=String.raw`import {AnimatePresence,motion} from "motion/react";

// mode="wait": stara treść wychodzi, zanim wejdzie nowa.
export function TabPanel({active,content}:{active:string;content:string}){
  return (
    <AnimatePresence mode="wait" initial={false}>
      <motion.section
        key={active}
        aria-label={active}
        initial={{opacity:0}}
        animate={{opacity:1}}
        exit={{opacity:0}}
        transition={{duration:0.12}}
      >
        {content}
      </motion.section>
    </AnimatePresence>
  );
}`;
const staggered=String.raw`import {motion,type Variants} from "motion/react";

const list:Variants={
  hidden:{},
  visible:{transition:{staggerChildren:0.04,delayChildren:0.05}},
};

const item:Variants={
  hidden:{opacity:0,y:8},
  visible:{opacity:1,y:0,transition:{duration:0.2,ease:"easeOut"}},
};

export const STAGGER_LIMIT=15;

// Wejście kolejne ma sens tylko dla widocznego początku. Długa lista czekałaby kilka sekund.
export function splitForStagger<T>(items:readonly T[],limit=STAGGER_LIMIT){
  return {animated:items.slice(0,limit),rest:items.slice(limit)};
}

export function StaggeredList({items}:{items:readonly {id:number;title:string}[]}){
  const {animated,rest}=splitForStagger(items);
  return (
    <motion.ul variants={list} initial="hidden" animate="visible">
      {animated.map(entry=><motion.li key={entry.id} variants={item}>{entry.title}</motion.li>)}
      {rest.map(entry=><li key={entry.id}>{entry.title}</li>)}
    </motion.ul>
  );
}`;
const swipe=String.raw`import {motion,useReducedMotion,type PanInfo} from "motion/react";

export const OFFSET_LIMIT=120;
export const VELOCITY_LIMIT=600;

export function shouldDismiss(offsetX:number,velocityX:number){
  return Math.abs(offsetX)>OFFSET_LIMIT||Math.abs(velocityX)>VELOCITY_LIMIT;
}

type SwipeCardProps={ticket:{id:number;title:string};onDismiss:(id:number)=>void};

export function SwipeCard({ticket,onDismiss}:SwipeCardProps){
  const reduced=useReducedMotion();

  function handleDragEnd(_:MouseEvent|TouchEvent|PointerEvent,info:PanInfo){
    if(shouldDismiss(info.offset.x,info.velocity.x))onDismiss(ticket.id);
  }

  return (
    <motion.article
      drag={reduced?false:"x"}
      dragConstraints={{left:0,right:0}}
      dragElastic={0.6}
      onDragEnd={handleDragEnd}
      exit={{opacity:0,x:300}}
    >
      <h3>{ticket.title}</h3>
      <button type="button" onClick={()=>onDismiss(ticket.id)}>Odrzuć</button>
    </motion.article>
  );
}`;
const press=String.raw`import {motion} from "motion/react";
import type {ReactNode} from "react";

export function PressableButton({children,onClick}:{children:ReactNode;onClick:()=>void}){
  return (
    <motion.button type="button" whileHover={{scale:1.03}} whileTap={{scale:0.97}} onClick={onClick}>
      {children}
    </motion.button>
  );
}`;
const root=String.raw`import {LazyMotion,MotionConfig,domAnimation} from "motion/react";
import * as m from "motion/react-m";
import type {ReactNode} from "react";

export function MotionRoot({children}:{children:ReactNode}){
  return <MotionConfig reducedMotion="user">{children}</MotionConfig>;
}

// LazyMotion ogranicza kod biblioteki: strict zgłasza błąd przy pełnym komponencie motion.
export function LightFade({children}:{children:ReactNode}){
  return (
    <LazyMotion features={domAnimation} strict>
      <m.div initial={{opacity:0}} animate={{opacity:1}}>{children}</m.div>
    </LazyMotion>
  );
}`;
const lab=String.raw`import {fireEvent,render} from "@testing-library/react";
import {useState} from "react";
import {Toasts,type ToastItem} from "./Toasts";
import {AnimatedTabs,type TabId} from "./AnimatedTabs";
import {StaggeredList} from "./StaggeredList";
import {SwipeCard} from "./SwipeCard";

export function Stack({initial}:{initial:ToastItem[]}){
  const [items,setItems]=useState(initial);
  return <Toasts toasts={items} onDismiss={id=>setItems(list=>list.filter(item=>item.id!==id))}/>;
}

const pair=[{id:"a",message:"Pierwszy"},{id:"b",message:"Drugi"}];

export function toastsRightAfterDismiss(){
  const view=render(<Stack initial={pair}/>);
  try{
    fireEvent.click(view.getByRole("button",{name:"Zamknij: Pierwszy"}));
    return view.getAllByRole("listitem").length;
  }finally{view.unmount();}
}

export function observeTabs(click?:string){
  const seen:TabId[]=[];
  const view=render(<AnimatedTabs onChange={id=>seen.push(id)}/>);
  try{
    if(click)fireEvent.click(view.getByRole("tab",{name:click}));
    return {
      selected:view.getAllByRole("tab").filter(tab=>tab.getAttribute("aria-selected")==="true").map(tab=>tab.textContent).join(","),
      underlines:view.queryAllByTestId("underline").length,
      underlineIn:view.getByTestId("underline").closest("button")!.textContent,
      changes:seen.join(",")||"brak",
    };
  }finally{view.unmount();}
}

export function listSizes(count:number){
  const items=Array.from({length:count},(_,index)=>({id:index,title:"T"+index}));
  const view=render(<StaggeredList items={items}/>);
  try{return view.getAllByRole("listitem").length;}finally{view.unmount();}
}

export function dismissByButton(){
  const seen:number[]=[];
  const view=render(<SwipeCard ticket={{id:7,title:"Awaria"}} onDismiss={id=>seen.push(id)}/>);
  try{
    fireEvent.click(view.getByRole("button",{name:"Odrzuć"}));
    return seen.join(",")||"brak";
  }finally{view.unmount();}
}`;
const tests=String.raw`import {beforeAll,expect,test,vi} from "vitest";
import {fireEvent,render,screen,waitFor} from "@testing-library/react";
import {AnimatePresence,MotionGlobalConfig} from "motion/react";
import {Toasts} from "./Toasts";
import {AnimatedTabs} from "./AnimatedTabs";
import {TabPanel} from "./TabPanel";
import {splitForStagger,StaggeredList} from "./StaggeredList";
import {shouldDismiss,SwipeCard} from "./SwipeCard";
import {PressableButton} from "./PressableButton";
import {LightFade,MotionRoot} from "./MotionRoot";
import {Stack} from "./motionLab";

// Animacje kończą się od razu, ale logika obecności i usuwania z DOM działa normalnie.
beforeAll(()=>{MotionGlobalConfig.skipAnimations=true;});

const two=[{id:"a",message:"Pierwszy"},{id:"b",message:"Drugi"}];

test("usunięty toast zostaje w DOM na czas wyjścia, a potem znika",async()=>{
  render(<Stack initial={two}/>);
  fireEvent.click(screen.getByRole("button",{name:"Zamknij: Pierwszy"}));
  expect(screen.getByText("Pierwszy")).toBeInTheDocument();
  await waitFor(()=>expect(screen.queryByText("Pierwszy")).toBeNull());
  expect(screen.getByText("Drugi")).toBeVisible();
});

test("toast przekazuje identyfikator do onDismiss",()=>{
  const onDismiss=vi.fn();
  render(<Toasts toasts={two} onDismiss={onDismiss}/>);
  fireEvent.click(screen.getByRole("button",{name:"Zamknij: Drugi"}));
  expect(onDismiss).toHaveBeenCalledWith("b");
});

test("lista toastów ma region na żywo przed pojawieniem się treści",()=>{
  const view=render(<Toasts toasts={[]} onDismiss={()=>{}}/>);
  expect(view.container.querySelector("ol")).toHaveAttribute("aria-live","polite");
});

test("warunkowy AnimatePresence też usuwa element po wyjściu",async()=>{
  const {motion}=await import("motion/react");
  const Box=({show}:{show:boolean})=><AnimatePresence>{show&&<motion.p key="p" exit={{opacity:0}}>Treść</motion.p>}</AnimatePresence>;
  const view=render(<Box show/>);
  view.rerender(<Box show={false}/>);
  expect(screen.getByText("Treść")).toBeInTheDocument();
  await waitFor(()=>expect(screen.queryByText("Treść")).toBeNull());
});

test("zakładki: jeden stan i jedno podkreślenie pod aktywną",()=>{
  const onChange=vi.fn();
  render(<AnimatedTabs onChange={onChange}/>);
  expect(screen.getByRole("tab",{name:"Szczegóły",selected:true})).toBeVisible();
  fireEvent.click(screen.getByRole("tab",{name:"Historia"}));
  expect(screen.getByRole("tab",{name:"Historia",selected:true})).toBeVisible();
  expect(screen.getAllByTestId("underline")).toHaveLength(1);
  expect(screen.getByTestId("underline").closest("button")).toHaveTextContent("Historia");
  expect(onChange).toHaveBeenCalledWith("history");
});

test("mode wait: stara treść znika, zanim pojawi się nowa",async()=>{
  const view=render(<TabPanel active="a" content="Stara"/>);
  view.rerender(<TabPanel active="b" content="Nowa"/>);
  expect(screen.getByText("Stara")).toBeInTheDocument();
  expect(screen.queryByText("Nowa")).toBeNull();
  expect(await screen.findByText("Nowa")).toBeInTheDocument();
  expect(screen.queryByText("Stara")).toBeNull();
});

test("lista dzieli elementy na animowane i pozostałe, bez zgubienia żadnego",()=>{
  const {animated,rest}=splitForStagger([1,2,3],2);
  expect(animated).toEqual([1,2]);
  expect(rest).toEqual([3]);
  const items=Array.from({length:20},(_,index)=>({id:index,title:"T"+index}));
  render(<StaggeredList items={items}/>);
  expect(screen.getAllByRole("listitem")).toHaveLength(20);
});

test("progi gestu działają w obie strony",()=>{
  expect(shouldDismiss(120,0)).toBe(false);
  expect(shouldDismiss(121,0)).toBe(true);
  expect(shouldDismiss(-121,0)).toBe(true);
  expect(shouldDismiss(0,601)).toBe(true);
  expect(shouldDismiss(0,-601)).toBe(true);
  expect(shouldDismiss(0,600)).toBe(false);
});

test("karta ma przycisk jako alternatywę dla gestu",()=>{
  const onDismiss=vi.fn();
  render(<SwipeCard ticket={{id:7,title:"Awaria"}} onDismiss={onDismiss}/>);
  fireEvent.click(screen.getByRole("button",{name:"Odrzuć"}));
  expect(onDismiss).toHaveBeenCalledWith(7);
});

test("przycisk z gestami pozostaje zwykłym przyciskiem",()=>{
  const onClick=vi.fn();
  render(<PressableButton onClick={onClick}>Zapisz</PressableButton>);
  const button=screen.getByRole("button",{name:"Zapisz"});
  expect(button).toHaveAttribute("type","button");
  fireEvent.click(button);
  expect(onClick).toHaveBeenCalledOnce();
});

test("konfiguracja korzenia i lżejszy wariant renderują treść",()=>{
  render(<MotionRoot><LightFade>Lekko</LightFade></MotionRoot>);
  expect(screen.getByText("Lekko")).toBeInTheDocument();
});`;
const exercises=[
['Zainstaluj Motion i owiń aplikację w MotionConfig.','reducedMotion="user" obsługuje ustawienie systemu w jednym miejscu.',root],
['Przepisz toasty na AnimatePresence i motion.li.','Brak stanu fazy i onAnimationEnd.',toasts],
['Dodaj layout, aby pozostałe toasty zsuwały się płynnie.','Motion mierzy stary i nowy układ.',toasts],
['Zablokuj kliknięcia w toaście, który wychodzi.','pointerEvents:"none" w stanie exit.',toasts],
['Zapewnij unikalny klucz elementom AnimatePresence.','Indeks tablicy pomyliłby wychodzący element.',toasts],
['Przetestuj obecność toastu podczas wyjścia.','Tuż po usunięciu ze stanu element jeszcze jest w DOM.',tests],
['Przetestuj warunkowe dziecko AnimatePresence.','AnimatePresence zostaje w drzewie, a warunkowe jest dziecko.',tests],
['Napisz zakładki z podkreśleniem layoutId.','Podkreślenie renderuje się tylko pod aktywną zakładką.',tabs],
['Wyprowadź typ TabId z listy zakładek.','as const i typeof zapobiegają rozjazdowi.',tabs],
['Przetestuj jedno podkreślenie pod wybraną zakładką.','Liczy się element i jego rodzic.',tests],
['Zmień treść zakładki przez mode="wait" i key.','Klucz mówi, że to inny element.',panels],
['Przetestuj kolejność w trybie wait.','Nowa treść pojawia się po wyjściu starej.',tests],
['Dodaj wejście listy przez warianty.','Rodzic przełącza nazwę, a dzieci ją dziedziczą.',staggered],
['Ogranicz staggerChildren do pierwszych elementów.','Długa lista nie powinna czekać kilku sekund.',staggered],
['Sprawdź, że podział listy nie gubi elementów.','Animowane i pozostałe razem dają całość.',tests],
['Napisz funkcję progu gestu.','Offset albo prędkość, w obu kierunkach.',swipe],
['Dodaj kartę przesuwaną z obsługą PanInfo.','Sprawdzaj offset i velocity w onDragEnd.',swipe],
['Wyłącz gest przy ograniczonym ruchu.','useReducedMotion zwraca wartość z ustawienia.',swipe],
['Zostaw zwykły przycisk jako alternatywę gestu.','Gest to skrót, a nie jedyna droga.',swipe],
['Dodaj reakcję przycisku przez whileHover i whileTap.','Pozostaje prawdziwym button.',press],
['Zmniejsz koszt biblioteki przez LazyMotion.','strict pilnuje użycia komponentu m.',root],
['Wybierz CSS albo Motion dla pięciu efektów.','Biblioteka ma dawać wartość, a nie zastępować CSS wszędzie.','Hover i rozwinięcie panelu: CSS. Toast z wyjściem: Motion lub stan z CSS. Zsuwanie listy po usunięciu: Motion, bo w CSS prawie niewykonalne. Podkreślenie zakładki: Motion layoutId lub View Transitions. Gest przesunięcia: Motion.'],
['Oceń motion.tr w tabeli ticketów.','Tabela układa kolumny inaczej niż bloki.','Animacja układu może zmieniać szerokości kolumn i zniekształcać wiersze. Najpierw sprawdź efekt. Jeśli zawodzi, animuj tylko opacity przy wyjściu, a zsuwanie pomiń. Prostszy efekt jest lepszy niż zepsuty układ tabeli.'],
['Znajdź przyczyny braku animacji exit.','Trzy typowe błędy.','1. AnimatePresence jest wewnątrz warunku albo znikającego komponentu. 2. Dzieci nie mają stabilnego, unikalnego key. 3. Bezpośrednie dziecko to własny komponent, który nie przekazuje exit do elementu motion. Warunkowe ma być dziecko, a AnimatePresence zostaje w drzewie.'],
['Napraw kod, w którym AnimatePresence jest w warunku.','Który element znika razem z warunkiem?','// Źle: {visible&&<AnimatePresence><motion.div exit={{opacity:0}}/></AnimatePresence>}\n// Dobrze:\n<AnimatePresence>{visible&&<motion.div key="panel" exit={{opacity:0}}/>}</AnimatePresence>'],
['Uwzględnij dostępność wychodzącego elementu.','Element jest w DOM przez czas animacji.','Podczas wyjścia czytnik może go odczytać, a przyciski działają. Dodaj pointerEvents:"none" w exit, krótko animuj wyjście i pilnuj, by fokus nie został na znikającym elemencie.'],
['Sprawdź zachowanie z włączonym ograniczeniem ruchu.','Brak przesunięć i skalowania, zostaje przenikanie.','Włącz emulację prefers-reduced-motion w DevTools i przejdź toasty, zakładki oraz listę. Oczekiwany wynik: bez transformacji i układu, przenikanie opacity zostaje, a gest jest wyłączony, ale przycisk Odrzuć działa.'],
['Sprawdź w przeglądarce, czego nie pokaże jsdom.','Testy pomijają ruch.','W testach animacje są wyłączone, więc sprawdzają logikę obecności. Płynność, sprężyny, gest i zachowanie layout oglądasz w przeglądarce, najlepiej także na słabszym telefonie.'],
['Oceń koszt paczki po dodaniu Motion.','Porównaj wynik przed i po.','Zbuduj aplikację przed i po dodaniu biblioteki oraz porównaj pliki gzip. Przy ograniczonym budżecie użyj LazyMotion z domAnimation i komponentów m. Mierz rzeczywisty build, zamiast podawać stałą liczbę kB z pamięci.'],
['Sprawdź kryterium akceptacji lekcji.','Wyjście, zakładki, gest i ograniczony ruch.','Toasty i wiersze wychodzą z animacją, a lista zsuwa się płynnie. Podkreślenie przesuwa się między zakładkami. Karta odrzuca się gestem i przyciskiem. Przy ograniczonym ruchu nie ma przesunięć. W kodzie nie ma ręcznego stanu faz.'],
];
const concepts=[
['Skąd importujesz Motion w React?','motion/react','react/motion','framer/react','motion/dom-only','To aktualna ścieżka biblioteki.'],
['Co to jest motion.div?','Element HTML z dodatkowymi props animacji','Osobny kontener React','Komponent klasowy','Wirtualna lista','Pozostałe atrybuty działają normalnie.'],
['Co opisuje initial?','Stan przy pojawieniu się','Stan docelowy','Stan wyjścia','Czas animacji','initial={false} wyłącza wejście przy pierwszym renderze.'],
['Co opisuje animate?','Stan docelowy','Stan początkowy','Sposób usunięcia','Klucz','Zmiana wartości uruchamia animację.'],
['Co opisuje exit?','Stan przy usuwaniu','Stan początkowy','Czas sieci','Rolę ARIA','Działa wewnątrz AnimatePresence.'],
['Co robi AnimatePresence?','Przytrzymuje usuwane dziecko do końca animacji','Renderuje wszystko po stronie serwera','Wyłącza animacje','Tworzy Portal','Rozpoznaje dzieci po key.'],
['Czego wymagają dzieci AnimatePresence?','Unikalnego, stabilnego key','Własnej klasy','ref','Numeru indeksu','Po kluczu biblioteka rozpoznaje wychodzący element.'],
['Dlaczego indeks tablicy jest złym key?','Po usunięciu indeksy się przesuwają','Jest liczbą','Jest wolny','Powoduje błąd składni','Wychodziłby niewłaściwy element.'],
['Gdzie powinno być AnimatePresence?','Stale w drzewie, a warunkowe jest dziecko','Wewnątrz warunku','W każdym dziecku','W useEffect','Inaczej znika razem z dzieckiem.'],
['Co oznacza initial={false} na AnimatePresence?','Elementy obecne na starcie nie animują wejścia','Wyłącza exit','Wyłącza key','Tworzy nowy stan','Pierwszy render aplikacji pozostaje spokojny.'],
['Co oznacza mode="wait"?','Nowe dziecko wchodzi po wyjściu starego','Oba jednocześnie','Czeka na fetch','Wyłącza exit','Przydatne przy podmianie treści.'],
['Co oznacza mode="popLayout"?','Wychodzący element wypada z układu, a pozostałe od razu się przesuwają','Czeka na wyjście','Wyłącza layout','Dotyczy tylko list numerowanych','Dobre przy listach z exit i layout.'],
['Co robi prop layout?','Animuje zmianę pozycji i rozmiaru po renderze','Zmienia CSS grid','Tworzy układ strony','Wyłącza transform','Motion mierzy stary i nowy układ.'],
['Czego używa animacja układu?','Transformacji','top i left','Wyłącznie width','Canvas','Dlatego jest wydajna, choć pomiar kosztuje.'],
['Co robi layoutId?','Łączy dwa elementy jako jeden przy przejściu','Nadaje id w DOM','Tworzy klucz React','Włącza SSR','Klasyczny efekt wspólnego elementu.'],
['Co robi LayoutGroup?','Grupuje komponenty reagujące razem na zmiany układu','Tworzy CSS grid','Sortuje listę','Wczytuje czcionki','Np. dwie listy obok siebie.'],
['Kiedy animacja układu kosztuje najwięcej?','Przy bardzo długich listach','Przy jednym elemencie','Zawsze tak samo','Tylko w Safari','Mierzenie wszystkich elementów zajmuje czas.'],
['Co opisuje transition z type:"spring"?','Fizykę sprężyny','Czas stały','Tylko opacity','Kolejność Tab','stiffness i damping wpływają na ruch.'],
['Czym są warianty?','Nazwanymi stanami animacji','Plikami CSS','Typami TS','Motywami','Rodzic przełącza nazwę.'],
['Jak dzieci dostają wariant rodzica?','Dziedziczą nazwę, jeśli mają te same warianty','Przez kontekst ręczny','Przez props zawsze','Nie dostają','Stąd krótki kod list.'],
['Co robi staggerChildren?','Uruchamia dzieci kolejno z opóźnieniem','Sortuje dzieci','Usuwa dzieci','Grupuje klucze','Lista wjeżdża element po elemencie.'],
['Czemu ograniczać stagger do ~15 elementów?','Reszta czekałaby długo i spowalniała odbiór','Bo limit biblioteki','Bo CSS','Bo klucze się kończą','1000 × 40 ms to 40 s czekania.'],
['Co robi whileTap?','Ustawia stan na czas naciśnięcia','Wykonuje kliknięcie','Blokuje kliknięcie','Tworzy formularz','Stan wraca po zakończeniu gestu.'],
['Co robi whileHover?','Ustawia stan na czas najechania','Zawsze działa na dotyku','Wykonuje fetch','Zmienia DOM','Na urządzeniach dotykowych zachowanie jest inne.'],
['Co robi drag="x"?','Pozwala przeciągać w poziomie','Włącza dnd-kit','Blokuje przewijanie całej strony','Zmienia kolejność','Ogranicza oś ruchu.'],
['Co opisuje dragConstraints?','Granice przeciągania','Czas animacji','Wartości stanu','Rolę elementu','Z dragElastic daje efekt powrotu.'],
['Co zawiera PanInfo?','offset, velocity i point','Tylko element','Tylko klucz','Stan Reacta','To parametry gestu w onDragEnd.'],
['Dlaczego sprawdzamy też velocity?','Szybki krótki ruch też oznacza odrzucenie','Dla kolorów','Bo offset nie działa','Dla SSR','To naturalne zachowanie dotykowe.'],
['Dlaczego Math.abs w progu?','Przesunięcie może być w obie strony','Aby zmienić typ','Bo liczba jest ułamkiem','Bo tak wymaga TS','Bez tego lewa strona by nie działała.'],
['Czy gest może być jedyną drogą?','Nie, zawsze zostaw przycisk','Tak, na telefonie','Tak, jeśli jest podpowiedź','Tak, w PWA','Klawiatura i czytniki nie wykonają gestu.'],
['Co robi MotionConfig reducedMotion="user"?','Szanuje ustawienie systemu w całej aplikacji','Wyłącza wszystkie animacje zawsze','Zmienia motyw','Włącza gesty','Transformacje i układ są wyłączane.'],
['Co zostaje przy reducedMotion="user"?','Przenikanie opacity','Skalowanie','Przesunięcia','Sprężyny układu','Zmiana stanu pozostaje widoczna.'],
['Co zwraca useReducedMotion?','Czy użytkownik ogranicza ruch','Stan animacji','Czas','Element DOM','Do decyzji w kodzie, np. wyłączenia gestu.'],
['Do czego służy LazyMotion?','Do doładowania funkcji i mniejszej paczki','Do odkładania renderu','Do lazy obrazów','Do lazy tras','Używa komponentów m.'],
['Co oznacza strict w LazyMotion?','Błąd przy użyciu pełnego motion','Tryb ścisły Reacta','Wyłączenie gestów','Dłuższe animacje','Pilnuje, by nie stracić oszczędności.'],
['Co zawiera domAnimation?','Podstawowe animacje bez układu i przeciągania','Wszystkie funkcje','Tylko gesty','Tylko SVG','domMax dodaje drag i layout.'],
['Kiedy nie używać Motion?','Proste przejścia, które zrobi CSS','Zsuwanie listy','Wyjście elementu','Gest przesunięcia','Biblioteka ma koszt paczki.'],
['Czy TSX wyłapie animate={{opactiy:1}}?','Tak','Nie','Tylko w produkcji','Tylko z ESLint','W JSX literówka nic nie zrobi.'],
['Jaki typ opisuje warianty?','Variants','Motion','AnimateProps','StyleObj','Eksportowany z motion/react.'],
['Jaki typ opisuje transition?','Transition','Timing','Ease','Duration','Eksportowany z motion/react.'],
['Czy motion.li przyjmuje onClick i className?','Tak, z poprawnymi typami','Nie','Tylko className','Tylko w JSX','Props łączą atrybuty i animacje.'],
['Co jest ryzykiem podczas exit?','Element nadal działa i może być odczytany','Znika od razu','Traci klucz','Blokuje React','Stąd pointerEvents:"none".'],
['Co zrobić, gdy własny komponent jest bezpośrednim dzieckiem AnimatePresence?','Pilnować, by exit był na elemencie motion wewnątrz','Dodać drugi AnimatePresence','Usunąć key','Użyć indeksu','Pośredni div z innym kluczem psuje efekt.'],
['Co jest ryzykiem motion.tr?','Zmiana szerokości kolumn','Brak fokusu','Brak key','Brak TS','Sprawdź efekt i w razie czego animuj tylko opacity.'],
['Czy stan powinien być usuwany dopiero po exit?','Nie, stan zmienia się od razu, a element chwilowo zostaje','Tak','Tak, w setTimeout','Tak, w onAnimationEnd','To istotna przewaga nad ręcznym stanem faz.'],
['Co robi onExitComplete?','Wywołuje się po zakończeniu wyjść','Rozpoczyna exit','Usuwa element z danych','Wyłącza key','Przydatne np. do przesunięcia fokusu.'],
['Co testujesz w jsdom przy Motion?','Logikę obecności i dostępność','Płynność ruchu','Kontrast','FPS','Ruch sprawdza się w przeglądarce.'],
['Co robi MotionGlobalConfig.skipAnimations?','Pomija animacje, np. w testach','Włącza wszystkie','Usuwa AnimatePresence','Blokuje setState','Elementy szybko osiągają stan końcowy.'],
['Czy wychodzący element jest w DOM tuż po zmianie stanu?','Tak, do końca wyjścia','Nie','Tylko w produkcji','Tylko z layout','Test powinien to uwzględnić.'],
['Jak test czeka na usunięcie?','Przez waitFor lub findBy','Przez stały sleep','Bez czekania','Przez reload','Asynchroniczne oczekiwanie jest stabilniejsze.'],
['Co powinno mieć aria-live w liście toastów?','Stale obecny ol','Każdy li osobno','Tylko przycisk','Tylko body','Region ma istnieć przed zmianą.'],
['Czy animacja layout rozwiązuje kolejność czytania?','Nie, zmienia tylko wygląd przejścia','Tak','Tak, z ARIA','Tak, w grid','Kolejność DOM dalej decyduje.'],
['Co wybrać przy częstym otwieraniu menu?','Krótką animację lub jej brak','Sprężynę 2 s','Stagger 100 elementów','Paralaksę','Częste akcje męczą przy długim ruchu.'],
['Jaki problem daje sprężyna z małym damping?','Długie oscylacje rozpraszają','Błąd kompilacji','Brak klucza','Brak exit','Parametry dobieraj do znaczenia elementu.'],
['Czy można animować SVG przez Motion?','Tak, np. motion.path','Nie','Tylko w canvas','Tylko CSS','pathLength jest przydatne przy rysowaniu.'],
['Do czego służy useAnimate?','Do imperatywnych sekwencji','Do stanu formularzy','Do fetch','Do i18n','Gdy potrzebna jest ręczna kontrola.'],
['Do czego służy useScroll?','Do animacji zależnych od przewijania','Do przewijania listy wirtualnej','Do testów','Do klawiatury','Uważaj na ruch przy ograniczeniu.'],
['Czy paralaksa jest bezpieczna przy ograniczeniu ruchu?','Nie, należy ją wyłączyć','Tak','Tak, gdy powolna','Tak, gdy w tle','To typowy wyzwalacz problemów.'],
['Co przenieść do CSS po dodaniu Motion?','Nic nie trzeba przenosić, ale hover i rozwinięcie mogą tam zostać','Wszystkie gesty','Wszystkie exit','AnimatePresence','Biblioteka uzupełnia CSS.'],
['Jak ocenić wartość biblioteki?','Porównać uproszczenie kodu i koszt paczki','Po liczbie gwiazdek','Po długości nazwy','Po modzie','Decyzja powinna mieć dane.'],
];
const header=String.raw`import {dismissByButton,listSizes,observeTabs,toastsRightAfterDismiss} from "./motionLab";
import {OFFSET_LIMIT,shouldDismiss,VELOCITY_LIMIT} from "./SwipeCard";
import {splitForStagger,STAGGER_LIMIT} from "./StaggeredList";
import {TABS} from "./AnimatedTabs";`;
const probeRows=[
['Ile elementów listy widać tuż po zamknięciu toastu?','toastsRightAfterDismiss()',2,'Wychodzący element zostaje w DOM do końca animacji.'],
['Która zakładka jest aktywna na starcie?','observeTabs().selected','Szczegóły','Stan początkowy.',['Aktywność','Historia','brak']],
['Ile podkreśleń jest na starcie?','observeTabs().underlines',1,'Tylko pod aktywną zakładką.'],
['Gdzie jest podkreślenie na starcie?','observeTabs().underlineIn','Szczegóły','Renderuje się w aktywnym przycisku.',['Aktywność','Historia','brak']],
['Która zakładka jest aktywna po kliknięciu Historii?','observeTabs("Historia").selected','Historia','Kliknięcie zmienia stan.',['Szczegóły','Aktywność','brak']],
['Ile podkreśleń jest po zmianie?','observeTabs("Historia").underlines',1,'layoutId przenosi jedno podkreślenie.'],
['Gdzie jest podkreślenie po kliknięciu Aktywności?','observeTabs("Aktywność").underlineIn','Aktywność','Nowy element dostaje ten sam layoutId.',['Szczegóły','Historia','brak']],
['Jaką wartość zgłasza onChange?','observeTabs("Historia").changes','history','Przekazujemy identyfikator.',['Historia','details','brak']],
['Co zgłasza onChange bez kliknięcia?','observeTabs().changes','brak','Render nie zmienia wyboru.',['details','history','Szczegóły']],
['Ile zakładek jest na liście?','TABS.length',3,'Lista jest źródłem typu i widoku.'],
['Jaki jest identyfikator drugiej zakładki?','TABS[1].id','activity','as const zachowuje dosłowny typ.',['Aktywność','history','details']],
['Ile elementów renderuje lista 5?','listSizes(5)',5,'Wszystkie w grupie animowanej.'],
['Ile elementów renderuje lista 15?','listSizes(15)',15,'Dokładnie na progu.'],
['Ile elementów renderuje lista 40?','listSizes(40)',40,'Podział nie gubi elementów.'],
['Ile animowanych z 40?','splitForStagger(Array(40).fill(0)).animated.length',15,'Domyślny limit.'],
['Ile pozostałych z 40?','splitForStagger(Array(40).fill(0)).rest.length',25,'Reszta bez opóźnienia.'],
['Ile pozostałych z 10?','splitForStagger(Array(10).fill(0)).rest.length',0,'Lista krótsza od limitu.'],
['Ile animowanych z limitem 3?','splitForStagger([1,2,3,4],3).animated.length',3,'Limit można przekazać.'],
['Jaki element jest pierwszy w reszcie?','splitForStagger(["a","b","c"],2).rest[0]','c','Kolejność jest zachowana.',['a','b','brak']],
['Jaką wartość ma limit?','STAGGER_LIMIT',15,'Stała dokumentuje decyzję.'],
['Jaki jest próg przesunięcia?','OFFSET_LIMIT',120,'Z treści lekcji.'],
['Jaki jest próg prędkości?','VELOCITY_LIMIT',600,'Piksele na sekundę.'],
['Czy 120 px odrzuca kartę?','shouldDismiss(120,0)',false,'Próg trzeba przekroczyć.'],
['Czy 121 px odrzuca kartę?','shouldDismiss(121,0)',true,'Powyżej progu.'],
['Czy -121 px odrzuca kartę?','shouldDismiss(-121,0)',true,'Math.abs obejmuje oba kierunki.'],
['Czy 50 px i prędkość 601 odrzuca kartę?','shouldDismiss(50,601)',true,'Szybki ruch wystarcza.'],
['Czy 50 px i prędkość -601 odrzuca kartę?','shouldDismiss(50,-601)',true,'Kierunek nie ma znaczenia.'],
['Czy 50 px i prędkość 600 odrzuca kartę?','shouldDismiss(50,600)',false,'Prędkość trzeba przekroczyć.'],
['Czy brak ruchu odrzuca kartę?','shouldDismiss(0,0)',false,'Samo puszczenie nic nie robi.'],
['Czy przycisk odrzuca kartę?','dismissByButton()','7','Przycisk przekazuje identyfikator ticketu.',['brak','0','Awaria']],
['Ile animowanych z pustej listy?','splitForStagger([]).animated.length',0,'Pusta lista jest poprawna.'],
['Czy podział mutuje wejście?','(()=>{const a=[1,2,3];splitForStagger(a,1);return a.length;})()',3,'slice tworzy nowe tablice.'],
['Czy suma części równa się całości?','(()=>{const s=splitForStagger(Array(33).fill(0));return s.animated.length+s.rest.length;})()',33,'Żaden element się nie gubi.'],
['Czy offset 0 i prędkość 601 odrzuca?','shouldDismiss(0,601)',true,'Sama prędkość wystarcza.'],
['Czy offset 119 odrzuca?','shouldDismiss(119,0)',false,'Poniżej progu.'],
['Która zakładka jest aktywna po kliknięciu Szczegółów?','observeTabs("Szczegóły").selected','Szczegóły','Ponowny wybór pozostawia stan.',['Aktywność','Historia','brak']],
['Co zgłosi onChange po wybraniu aktywnej?','observeTabs("Szczegóły").changes','details','Zdarzenie wywołuje się też dla aktywnej.',['brak','Szczegóły','history']],
['Jaki jest tekst etykiety pierwszej zakładki?','TABS[0].label','Szczegóły','Etykieta i identyfikator są rozdzielone.',['details','Historia','brak']],
['Czy lista 1 renderuje jeden element?','listSizes(1)',1,'Najmniejszy niepusty przypadek.'],
['Ile pozostałych z 16?','splitForStagger(Array(16).fill(0)).rest.length',1,'Tylko jeden ponad limit.'],
];
const project={
'src/Toasts.tsx':toasts,'src/AnimatedTabs.tsx':tabs,'src/TabPanel.tsx':panels,'src/StaggeredList.tsx':staggered,'src/SwipeCard.tsx':swipe,'src/PressableButton.tsx':press,'src/MotionRoot.tsx':root,'src/motionLab.tsx':lab,'src/motion.test.tsx':tests,
'README.md':'DevDesk: Motion. npm install, npm test. Testy włączają MotionGlobalConfig.skipAnimations i sprawdzają obecność elementów, role oraz progi gestu. Płynność, sprężyny, sam gest przeciągania i animacje układu wymagają przeglądarki, a jsdom ich nie odtworzy.\n',
};
const mutants=[
{file:'src/Toasts.tsx',before:'import {AnimatePresence,motion} from "motion/react";',after:'import {motion} from "motion/react";import {Fragment as AnimatePresence} from "react";'},
{file:'src/AnimatedTabs.tsx',before:'{active===tab.id&&(',after:'{true&&('},
{file:'src/AnimatedTabs.tsx',before:'onChange?.(tab.id);',after:''},
{file:'src/TabPanel.tsx',before:'<AnimatePresence mode="wait" initial',after:'<AnimatePresence initial'},
{file:'src/StaggeredList.tsx',before:'{rest.map(entry=><li key={entry.id}>{entry.title}</li>)}',after:''},
{file:'src/SwipeCard.tsx',before:'Math.abs(offsetX)>OFFSET_LIMIT',after:'offsetX>OFFSET_LIMIT'},
{file:'src/SwipeCard.tsx',before:'Math.abs(velocityX)>VELOCITY_LIMIT',after:'velocityX>VELOCITY_LIMIT'},
{file:'src/SwipeCard.tsx',before:'<button type="button" onClick={()=>onDismiss(ticket.id)}>Odrzuć</button>',after:''},
{file:'src/PressableButton.tsx',before:'type="button" ',after:''},
];
module.exports=lesson({exercises,concepts,probeRows,project,mutants,header});
