const {lesson}=require('./practice_kit.cjs');
const iconButton=String.raw`import type {ComponentProps,ReactNode} from "react";

type Props=Omit<ComponentProps<"button">,"children"|"aria-label">&{label:string;icon:ReactNode};

export function IconButton({label,icon,type="button",...rest}:Props){
  return (
    <button {...rest} type={type} aria-label={label} title={label}>
      <span aria-hidden="true">{icon}</span>
    </button>
  );
}`;
const textField=String.raw`import {useId,type ComponentProps} from "react";

type Props=Omit<ComponentProps<"input">,"id"|"aria-invalid"|"aria-describedby">&{label:string;hint?:string;error?:string};

export function TextField({label,hint,error,...inputProps}:Props){
  const id=useId();
  const hintId=hint?id+"-hint":undefined;
  const errorId=error?id+"-error":undefined;
  const describedBy=[hintId,errorId].filter(Boolean).join(" ")||undefined;
  return (
    <div className="field">
      <label htmlFor={id}>{label}</label>
      {hint&&<p id={hintId}>{hint}</p>}
      <input {...inputProps} id={id} aria-invalid={Boolean(error)} aria-describedby={describedBy}/>
      {error&&<p id={errorId}>{error}</p>}
    </div>
  );
}`;
const avatar=String.raw`export type AvatarProps={src:string;alt:string};

// alt jest wymagany. Pusty tekst to świadoma decyzja: obraz dekoracyjny, a nazwa stoi obok.
export function Avatar({src,alt}:AvatarProps){
  return <img src={src} alt={alt} width={32} height={32}/>;
}`;
const routeHeading=String.raw`import {useEffect,useRef} from "react";
import {useLocation} from "react-router";

export function RouteHeading({children}:{children:string}){
  const ref=useRef<HTMLHeadingElement>(null);
  const {pathname}=useLocation();
  useEffect(()=>{ref.current?.focus();},[pathname]);
  return <h1 ref={ref} tabIndex={-1}>{children}</h1>;
}`;
const layout=String.raw`import type {ReactNode} from "react";

export function Layout({nav,children}:{nav:ReactNode;children:ReactNode}){
  return (
    <>
      <a className="skip-link" href="#main">Przejdź do treści</a>
      <header><nav aria-label="Główna">{nav}</nav></header>
      <main id="main" tabIndex={-1}>{children}</main>
    </>
  );
}`;
const contrast=String.raw`function channel(value:number){
  const c=value/255;
  return c<=0.04045?c/12.92:((c+0.055)/1.055)**2.4;
}

export function luminance(hex:string){
  if(!/^#[0-9a-f]{6}$/i.test(hex))throw Error("Kolor w postaci #rrggbb");
  const [r,g,b]=[1,3,5].map(at=>channel(parseInt(hex.slice(at,at+2),16)));
  return 0.2126*r+0.7152*g+0.0722*b;
}

export function contrast(a:string,b:string){
  const [high,low]=[luminance(a),luminance(b)].sort((x,y)=>y-x);
  return (high+0.05)/(low+0.05);
}

export function meetsAA(foreground:string,background:string,{large=false}:{large?:boolean}={}){
  return contrast(foreground,background)>=(large?3:4.5);
}`;
const badge=String.raw`export const statusPalette={
  open:{label:"Otwarty",color:"#1d4ed8",background:"#dbeafe"},
  urgent:{label:"Pilny",color:"#991b1b",background:"#fee2e2"},
  closed:{label:"Zamknięty",color:"#166534",background:"#dcfce7"},
} as const;

export type TicketStatus=keyof typeof statusPalette;

export function StatusBadge({status}:{status:TicketStatus}){
  const {label,color,background}=statusPalette[status];
  return <span style={{color,background}}>{label}</span>;
}`;
const focusCss=String.raw`/* focus.css */
:focus-visible {
  outline: 3px solid var(--focus-ring, #2563eb);
  outline-offset: 2px;
}

.skip-link {
  position: absolute;
  left: 8px;
  top: -48px;
}

.skip-link:focus {
  top: 8px;
}`;
const lab=String.raw`import axe from "axe-core";
import type {ReactElement} from "react";
import {render} from "@testing-library/react";

const auditOptions={rules:{"color-contrast":{enabled:false},region:{enabled:false}}};

// jsdom nie liczy stylów, więc kontrast sprawdza osobna funkcja, a nie axe.
export async function violations(element:ReactElement){
  const view=render(element);
  try{
    const result=await axe.run(view.container,auditOptions);
    return result.violations.map(item=>item.id).sort().join(",")||"brak";
  }finally{view.unmount();}
}`;
const tests=String.raw`import {useState} from "react";
import {expect,test,vi} from "vitest";
import {render,screen} from "@testing-library/react";
import userEvent from "@testing-library/user-event";
import {Link,MemoryRouter,Route,Routes} from "react-router";
import {IconButton} from "./IconButton";
import {TextField} from "./TextField";
import {Avatar} from "./Avatar";
import {RouteHeading} from "./RouteHeading";
import {Layout} from "./Layout";
import {contrast,meetsAA} from "./contrast";
import {StatusBadge,statusPalette,type TicketStatus} from "./StatusBadge";
import {violations} from "./a11yLab";

test("przycisk ikonowy ma nazwę, typ button i ukrytą ikonę",()=>{
  render(<IconButton label="Zamknij ticket" icon="✕" onClick={()=>{}}/>);
  const button=screen.getByRole("button",{name:"Zamknij ticket"});
  expect(button).toHaveAttribute("aria-label","Zamknij ticket"); // samo title też dałoby nazwę, ale tylko awaryjnie
  expect(button).toHaveAttribute("type","button");
  expect(button.querySelector("span")).toHaveAttribute("aria-hidden","true");
});

test("przycisk ikonowy działa z klawiatury",async()=>{
  const user=userEvent.setup(),onClick=vi.fn();
  render(<IconButton label="Zamknij" icon="✕" onClick={onClick}/>);
  await user.tab();
  expect(screen.getByRole("button")).toHaveFocus();
  await user.keyboard("{Enter}");
  await user.keyboard(" ");
  expect(onClick).toHaveBeenCalledTimes(2);
});

test("pole łączy etykietę, podpowiedź i błąd",()=>{
  render(<TextField label="E-mail" hint="Służbowy adres" error="Podaj adres"/>);
  const input=screen.getByLabelText("E-mail");
  expect(input).toHaveAccessibleDescription("Służbowy adres Podaj adres");
  expect(input).toHaveAttribute("aria-invalid","true");
});

test("pole bez błędu nie ma pustego opisu",()=>{
  render(<TextField label="E-mail"/>);
  const input=screen.getByLabelText("E-mail");
  expect(input).not.toHaveAttribute("aria-describedby");
  expect(input).toHaveAttribute("aria-invalid","false");
});

test("dwa pola mają osobne identyfikatory",()=>{
  render(<><TextField label="A"/><TextField label="B"/></>);
  expect(screen.getByLabelText("A").id).not.toBe(screen.getByLabelText("B").id);
});

test("awatar przekazuje tekst alternatywny",()=>{
  render(<Avatar src="/a.png" alt="Ala Kot"/>);
  expect(screen.getByRole("img",{name:"Ala Kot"})).toBeVisible();
});

test("po zmianie trasy fokus trafia na nagłówek ekranu",async()=>{
  const user=userEvent.setup();
  function Screen({title,to}:{title:string;to:string}){
    return <><RouteHeading>{title}</RouteHeading><Link to={to}>Dalej</Link></>;
  }
  render(
    <MemoryRouter initialEntries={["/a"]}>
      <Routes>
        <Route path="/a" element={<Screen title="Pierwszy" to="/b"/>}/>
        <Route path="/b" element={<Screen title="Drugi" to="/a"/>}/>
      </Routes>
    </MemoryRouter>,
  );
  expect(screen.getByRole("heading",{name:"Pierwszy"})).toHaveFocus();
  await user.click(screen.getByRole("link",{name:"Dalej"}));
  expect(screen.getByRole("heading",{name:"Drugi"})).toHaveFocus();
});

test("nagłówek trwały w layoucie reaguje na sam pathname",async()=>{
  const user=userEvent.setup();
  function Shell(){
    const [title]=useState("Tickety");
    return <><Link to="/tickets/2">Następny</Link><RouteHeading>{title}</RouteHeading></>;
  }
  render(<MemoryRouter initialEntries={["/tickets/1"]}><Shell/></MemoryRouter>);
  await user.click(screen.getByRole("link"));
  expect(screen.getByRole("heading")).toHaveFocus();
});

test("pierwszy Tab trafia w link do treści, a cel może przyjąć fokus",async()=>{
  const user=userEvent.setup();
  render(<Layout nav={<a href="/x">Tickety</a>}><p>Treść</p></Layout>);
  await user.tab();
  expect(screen.getByRole("link",{name:"Przejdź do treści"})).toHaveFocus();
  expect(screen.getByRole("main")).toHaveAttribute("tabindex","-1");
  expect(screen.getByRole("main").id).toBe("main");
});

test("kontrast liczy skrajne i progowe wartości",()=>{
  expect(contrast("#000000","#ffffff")).toBeCloseTo(21,5);
  expect(contrast("#ffffff","#000000")).toBeCloseTo(21,5);
  expect(meetsAA("#767676","#ffffff")).toBe(true);
  expect(meetsAA("#777777","#ffffff")).toBe(false);
  expect(meetsAA("#777777","#ffffff",{large:true})).toBe(true);
  expect(()=>contrast("red","#ffffff")).toThrow();
});

test("cała paleta plakietek spełnia próg AA i ma tekst",()=>{
  for(const status of Object.keys(statusPalette) as TicketStatus[]){
    const {color,background,label}=statusPalette[status];
    expect(meetsAA(color,background)).toBe(true);
    const view=render(<StatusBadge status={status}/>);
    expect(view.getByText(label)).toBeVisible();
    view.unmount();
  }
});

test("poprawne komponenty nie mają naruszeń axe",async()=>{
  expect(await violations(<><IconButton label="Zamknij" icon="✕"/><TextField label="E-mail" hint="Służbowy" error="Błąd"/><Avatar src="/a.png" alt="Ala"/></>)).toBe("brak");
});`;
const exercises=[
['Zastąp klikalny div zamknięcia ticketu komponentem IconButton z wymaganą nazwą.','Nazwę dostaje przycisk, a sama ikona jest ukryta przed czytnikiem.',iconButton],
['Ustaw domyślny type="button" w IconButton.','Bez tego przycisk w formularzu wysyła formularz.',iconButton],
['Napisz TextField z etykietą powiązaną przez useId.','Sztywne id powtórzy się przy dwóch instancjach.',textField],
['Połącz podpowiedź i błąd z polem przez aria-describedby.','Identyfikatory łącz spacją, a przy ich braku nie ustawiaj atrybutu.',textField],
['Zablokuj nadpisanie id i aria-describedby w typie TextField.','Omit w typie props chroni powiązania komponentu.',textField],
['Dodaj Avatar z wymaganym alt.','Pusty alt jest dozwolony, ale musi być świadomą decyzją.',avatar],
['Rozstrzygnij, kiedy awatar ma pusty alt.','Pomyśl, co usłyszy czytnik, gdy nazwisko stoi tuż obok obrazu.','Gdy nazwisko agenta jest obok obrazu, użyj alt="" — inaczej czytnik przeczyta je dwa razy. Gdy awatar stoi sam w wierszu listy, alt niesie informację, więc podaj nazwisko. Nazwa pliku, „obrazek” ani „avatar” nigdy nie są dobrym tekstem alternatywnym.'],
['Zastąp usunięty outline widocznym stylem fokusu.','Użyj :focus-visible, żeby pierścień pojawiał się przy klawiaturze.',focusCss],
['Dodaj link „Przejdź do treści” widoczny dopiero po Tab.','Nie ukrywaj go przez display:none, bo nie przyjmie fokusu.',focusCss],
['Przygotuj layout z punktami orientacyjnymi i celem linku.','main potrzebuje id i tabIndex={-1}, aby przyjąć fokus programowo.',layout],
['Przenoś fokus na nagłówek po zmianie trasy SPA.','Efekt zależy od pathname, a h1 musi mieć tabIndex={-1}.',routeHeading],
['Sprawdź testem, że fokus przechodzi na nagłówek także przy trwałym layoucie.','Komponent nie musi się montować ponownie, zmienia się tylko adres.',tests],
['Napisz funkcję luminancji według definicji WCAG.','Najpierw linearyzacja kanałów, potem wagi 0,2126, 0,7152 i 0,0722.',contrast],
['Napisz funkcję kontrastu niezależną od kolejności kolorów.','Jaśniejsza luminancja zawsze trafia do licznika.',contrast],
['Dodaj sprawdzanie progów AA dla zwykłego i dużego tekstu.','Progi to 4,5:1 oraz 3:1.',contrast],
['Zdefiniuj paletę plakietek statusów jako jedno źródło danych.','Test może wtedy przejść po każdym statusie, bez kopiowania kolorów.',badge],
['Pokaż status tekstem, nie samym kolorem.','Informacja przekazana wyłącznie kolorem łamie zasadę postrzegalności.',badge],
['Przetestuj kontrast całej palety w jednej pętli.','Nowy status zostanie sprawdzony automatycznie.',tests],
['Przetestuj obsługę Enter i Spacji w przycisku.','Sprawdzaj fokus przez user.tab, a nie samym kliknięciem.',tests],
['Przetestuj kolejność pierwszego Tab na layoucie.','Pierwszy fokus to link pomijający nawigację.',tests],
['Uruchom axe w teście i wypisz identyfikatory naruszeń.','Czytelny raport jest przydatniejszy niż samo true lub false.',lab],
['Wyłącz w jsdom regułę kontrastu i zapisz dlaczego.','jsdom nie liczy stylów, więc taki wynik nic by nie znaczył.',lab],
['Pokaż, czego nie wykryje axe w klikalnym divie.','Sam div z onClick nie ma roli, więc automat nie wie, że miał być przyciskiem.','Axe nie zgłasza <div onClick>, bo w HTML to zwykły kontener i nie widzi w nim przycisku. Problem ujawnia dopiero przejście klawiaturą: elementu nie ma w kolejności Tab. Dlatego audyt zawsze obejmuje testy ręczne, a regułę no-static-element-interactions ustawiasz w eslint-plugin-jsx-a11y.'],
['Wybierz między a href i button dla czterech akcji DevDesk.','Czy akcja zmienia adres, czy wykonuje coś na stronie?','Przejście do szczegółów ticketu i pobranie załącznika pod adresem to a href, bo liczą się historia i otwarcie w nowej karcie. Zamknięcie ticketu oraz otwarcie modala to button, bo wykonują akcję na stronie. Wygląd nie decyduje o elemencie: link można ostylować jak przycisk.'],
['Zamień ten fragment na poprawny semantycznie.','Sprawdź element akcji, etykietę i zapis informacji o stanie.','<button type="button" onClick={closeTicket}>Zamknij</button> zastępuje div z onClick. <label htmlFor={id}>E-mail</label><input id={id}/> zastępuje sam placeholder. <strong>Pilny</strong> razem z kolorem przekazuje stan także bez widzenia barw.'],
['Włącz eslint-plugin-jsx-a11y w konfiguracji flat.','Reguły recommended obejmują oba warianty: JSX i TSX.','// eslint.config.js\nimport jsxA11y from "eslint-plugin-jsx-a11y";\nexport default [jsxA11y.flatConfigs.recommended];\n// Lint wykrywa m.in. alt-text, label-has-associated-control i no-static-element-interactions. Nie zastępuje testu klawiaturą.'],
['Wymuś dostępność typami w JSX i TSX.','Porównaj, co pilnuje kompilator, a co linter.','W TSX label:string w IconButton i alt:string w Avatar powodują błąd kompilacji przy braku nazwy. W JSX to samo pilnuje eslint-plugin-jsx-a11y na surowym HTML oraz PropTypes lub testy na komponentach projektu. Najlepiej mieć oba: typy dla własnych komponentów, linter dla HTML.'],
['Opisz listę kontroli klawiaturą ekranu listy ticketów.','Odłóż mysz i zapisz konkretne punkty.','1. Pierwszy Tab pokazuje link do treści. 2. Kolejność odpowiada kolejności wizualnej. 3. Pierścień fokusu jest widoczny na każdym elemencie. 4. Enter i Spacja działają na przyciskach. 5. Nie ma pułapek klawiaturowych. 6. Po przejściu do szczegółów fokus jest na nagłówku.'],
['Zmapuj znalezione problemy na kryteria WCAG.','Użyj numerów kryteriów, które da się potem wpisać do raportu.','Brak alt: 1.1.1. Sam kolor: 1.4.1. Niski kontrast: 1.4.3. Brak obsługi klawiatury: 2.1.1. Niewidoczny fokus: 2.4.7. Zbyt mały cel dotyku: 2.5.8. Brak lang: 3.1.1. Pole bez etykiety: 3.3.2. Przycisk bez nazwy: 4.1.2.'],
['Sformułuj kryterium akceptacji dostępności zadania.','Ma dać się sprawdzić, a nie brzmieć „ma być dostępne”.','Wszystkie akcje ekranu wykonasz klawiaturą z widocznym fokusem; przyciski ikonowe mają nazwy; pola mają etykiety i powiązane błędy; po nawigacji fokus jest na nagłówku; tekst ma kontrast co najmniej 4,5:1; testy axe i lint a11y przechodzą bez zgłoszeń.'],
];
const concepts=[
['Co oznacza skrót POUR w WCAG?','Postrzegalność, funkcjonalność, zrozumiałość, solidność','Wydajność, użyteczność, odporność, rzetelność','Cztery poziomy zgodności dokumentu','Zestaw reguł formatowania kodu','Cztery zasady porządkują wszystkie kryteria WCAG.'],
['Który poziom WCAG jest celem większości regulacji?','AA','A','AAA','Poziom jest dowolny, liczy się wynik Lighthouse','Regulacje i standardy na ogół odnoszą się do poziomu AA.'],
['Jaki kontrast wymagany jest dla zwykłego tekstu na poziomie AA?','4,5:1','3:1','7:1','21:1','Próg 4,5:1 obowiązuje zwykły tekst, a 3:1 duży tekst.'],
['Jaki próg dotyczy dużego tekstu i elementów interfejsu?','3:1','4,5:1','7:1','1,5:1','Duży tekst i elementy interfejsu mają niższy próg 3:1.'],
['Do której zasady POUR należy obsługa klawiaturą?','Funkcjonalności','Postrzegalności','Zrozumiałości','Solidności','Funkcjonalność obejmuje klawiaturę, fokus i czas na działanie.'],
['Do której zasady należy atrybut lang strony?','Zrozumiałości','Postrzegalności','Funkcjonalności','Solidności','Język dokumentu pozwala czytnikowi wybrać właściwą wymowę.'],
['Jaki minimalny rozmiar celu dotyku wprowadza WCAG 2.2 na poziomie AA?','24×24 px','16×16 px','44×44 px na poziomie AA','100×100 px','Kryterium 2.5.8 wymaga co najmniej 24×24 px, z wyjątkami. 44×44 px to poziom AAA.'],
['Jaki akt UE obowiązuje wiele usług cyfrowych od 2025 r.?','Europejski akt o dostępności (EAA)','RODO','DORA','Akt o usługach cyfrowych jako jedyny wymóg dostępności','EAA rozszerza wymogi dostępności na wiele produktów i usług.'],
['Dlaczego button jest lepszy od div z onClick?','Ma fokus, obsługę Enter i Spacji oraz rolę','Renderuje się szybciej','Automatycznie ma ładny wygląd','Blokuje podwójne kliknięcia','Element natywny daje zachowanie, które w div trzeba odtwarzać ręcznie.'],
['Kiedy użyjesz a href zamiast button?','Gdy akcja jest nawigacją do adresu','Gdy element ma wyglądać jak tekst','Gdy akcja wysyła dane na serwer','Gdy przycisk jest mały','Link daje historię, nową kartę i kopiowanie adresu.'],
['Czy placeholder zastępuje etykietę pola?','Nie: znika przy pisaniu i bywa słabo czytelny','Tak, zawsze','Tak, jeśli ma wysoki kontrast','Tak, w polach e-mail','Etykieta musi być stale dostępna, a placeholder tylko pomaga.'],
['Co daje label powiązany z polem przez htmlFor?','Nazwę pola i większy obszar kliknięcia','Automatyczną walidację','Wyłączenie autouzupełniania','Tylko wygląd pogrubionego tekstu','Kliknięcie etykiety przenosi fokus do pola, a czytnik odczytuje nazwę.'],
['Po co nagłówki i punkty orientacyjne?','Czytnik ekranu pozwala po nich skakać','Wyłącznie dla SEO','React lepiej dzięki nim renderuje','Zastępują linki nawigacji','Struktura dokumentu jest dla czytnika tym, czym układ wizualny dla oczu.'],
['Które zdanie o obrazie dekoracyjnym jest prawdziwe?','Powinien mieć pusty alt=""','Powinien nie mieć atrybutu alt','Powinien mieć alt="obraz"','Powinien mieć title zamiast alt','Pusty alt mówi czytnikowi, że może obraz pominąć. Brak atrybutu sprawia, że czytnik może odczytać nazwę pliku.'],
['Dlaczego *:focus{outline:none} jest szkodliwe?','Użytkownik klawiatury przestaje widzieć, gdzie jest','Spowalnia rysowanie strony','Blokuje klawisz Tab','Usuwa element z drzewa dostępności','Kryterium 2.4.7 wymaga widocznego fokusu.'],
['Czym :focus-visible różni się od :focus?','Pokazuje styl, gdy przeglądarka uzna, że wskaźnik fokusu jest potrzebny','Działa tylko na linkach','Działa tylko po kliknięciu myszą','Niczym, to alias','Heurystyka przeglądarki pokazuje pierścień m.in. przy nawigacji klawiaturą.'],
['Dlaczego link pomijający nie może mieć display:none?','Taki element nie przyjmie fokusu','Bo display:none spowalnia stronę','Bo przeglądarka go usunie z historii','Bo przestanie działać CSS','Elementy z display:none nie uczestniczą w kolejności Tab.'],
['Po co main ma tabIndex={-1} przy linku pomijającym?','Aby mógł przyjąć fokus programowo, nie wchodząc w kolejność Tab','Aby wejść na początek kolejności Tab','Aby wyłączyć klawiaturę w main','Aby stać się przyciskiem','Wartość -1 pozwala na focus() bez dokładania przystanku Tab.'],
['Dlaczego tabIndex={5} to zwykle błąd?','Zaburza naturalną kolejność Tab','Jest niezgodny z React','Blokuje fokus','Tworzy pułapkę klawiaturową z definicji','Dodatni tabindex wymusza globalną kolejność, trudną do utrzymania.'],
['Co dzieje się z fokusem po zmianie trasy SPA bez dodatkowego kodu?','Nowa strona nie jest ogłaszana i fokus może zostać na starym miejscu lub zniknąć','Przeglądarka przenosi go na h1','React ustawia go na body i czyta stronę','Router zawsze ogłasza tytuł','SPA nie przeładowuje dokumentu, więc nawigację trzeba obsłużyć samemu.'],
['Dlaczego h1 w RouteHeading ma tabIndex={-1}?','Nagłówek domyślnie nie przyjmuje fokusu','Aby dało się go kliknąć','Aby wypadł z drzewa dostępności','Aby przyspieszyć efekt','Bez tego ref.current.focus() nic nie zrobi.'],
['Czemu zależność efektu w RouteHeading to pathname?','Layout może zostać zamontowany, a zmienia się tylko adres','Bo tak wymaga ESLint','Bo inaczej efekt nie uruchomi się przy montowaniu','Bo ref zmienia się przy każdym renderze','Bez pathname fokus przeniesie się tylko przy pierwszym montowaniu.'],
['Co oznacza pierwsza zasada ARIA?','Nie używaj ARIA, jeśli istnieje element HTML o tym znaczeniu','Zawsze dodawaj role do elementów','Każdy element potrzebuje aria-label','ARIA zastępuje semantyczny HTML','Natywny element ma zachowanie, którego ARIA nie dodaje.'],
['Co zmienia role="button" na divie?','Tylko semantykę: klawiaturę i fokus nadal trzeba dopisać','Dodaje fokus i klawiaturę','Zamienia element na button w DOM','Włącza obsługę formularza','ARIA informuje czytnik, ale nie dodaje zachowania.'],
['Dlaczego aria-hidden="true" na elemencie z fokusem jest błędem?','Fokus trafia na coś niewidocznego dla czytnika','Przycisk przestaje działać','Element znika wizualnie','React zgłasza wyjątek','Użytkownik czytnika wchodzi na element bez nazwy i treści.'],
['Dlaczego ikona w przycisku ma aria-hidden?','Znak lub SVG nie powinien być czytany obok aria-label','Żeby była mniejsza','Żeby nie przyjmowała kliknięć','Żeby React jej nie renderował','Nazwę przycisku niesie etykieta, a ikona jest dekoracją.'],
['Co daje wymagany label:string w IconButton w TSX?','Przycisk bez nazwy się nie skompiluje','Automatyczne tłumaczenie etykiety','Sprawdzenie kontrastu','Dostępność całej aplikacji','Typy zamieniają zasadę w kontrakt komponentu.'],
['Czego typ TS nie sprawdzi w tekście alternatywnym?','Czy tekst jest sensowny','Czy prop został podany','Czy jest typu string','Czy nie jest liczbą','Kompilator przepuści alt="obraz123.png".'],
['Co robi eslint-plugin-jsx-a11y?','Statycznie sprawdza typowe błędy dostępności w JSX','Uruchamia czytnik ekranu','Mierzy kontrast w przeglądarce','Generuje napisy do wideo','Lint działa dla JSX i TSX, ale nie widzi zachowania.'],
['Które narzędzie sprawdzi kontrast z prawdziwymi stylami?','Przeglądarka: DevTools, Lighthouse lub axe w Playwright','axe w jsdom','tsc','eslint-plugin-jsx-a11y','jsdom nie oblicza pełnych stylów, więc kontrast wymaga przeglądarki.'],
['Czy wynik axe równy zero naruszeń oznacza dostępność?','Nie: automat wykrywa tylko część problemów','Tak, axe sprawdza wszystko','Tak, jeśli działa w przeglądarce','Tak, dla poziomu AA','Kolejność fokusu czy sens tekstów wymagają człowieka.'],
['Jak przekazać status pilny bez polegania na kolorze?','Dodać tekst lub kształt obok koloru','Użyć bardziej nasyconej czerwieni','Dodać animację koloru','Zwiększyć kontrast tła','Kryterium 1.4.1 mówi, że kolor nie może być jedynym nośnikiem informacji.'],
['Czym jest pułapka klawiaturowa?','Fokus wchodzi w element i nie da się z niego wyjść klawiaturą','Klawisz o dwóch funkcjach','Skrót kolidujący z przeglądarką','Formularz bez Enter','Kryterium 2.1.2 zabrania takich pułapek.'],
['Który zapis poprawnie łączy opis z polem?','aria-describedby="hint error" na input','aria-label="hint error" na p','htmlFor="hint" na input','for="error" na div','aria-describedby przyjmuje listę identyfikatorów rozdzieloną spacjami.'],
['Co oznacza aria-invalid={true}?','Pole ma błędną wartość','Pole jest wyłączone','Pole jest wymagane','Pole ma być pominięte','Stan powinien odzwierciedlać błąd walidacji.'],
['Dlaczego w TextField używamy useId, a nie stałego id?','Dwa pola nie mogą dzielić tego samego id','Stałe id jest zabronione w JSX','useId skraca kod HTML','useId zapisuje stan formularza','Powtórzone id psuje label i aria-describedby.'],
['Dlaczego w TextField spread inputProps stoi przed id?','Żeby przekazany prop nie nadpisał powiązań','Taka kolejność przyspiesza render','Spread zawsze musi być pierwszy','Inaczej React zgłasza błąd','Późniejsze atrybuty wygrywają w JSX.'],
['Co robi DevTools w panelu Accessibility?','Pokazuje nazwę i rolę z drzewa dostępności','Czyta stronę na głos','Zapisuje testy axe','Blokuje elementy bez ARIA','Drzewo dostępności to widok, który dostają technologie wspomagające.'],
['Kiedy title wystarcza jako jedyna nazwa przycisku?','Nie należy na nim polegać: bywa niedostępny z klawiatury i dotyku','Zawsze','Gdy przycisk jest ikonowy','Gdy tekst ma ponad 10 znaków','Dymek title jest dodatkiem, a nazwę daje aria-label lub tekst.'],
['Co czytnik przeczyta w przycisku <button><svg>…</svg></button> bez nazwy?','Co najwyżej „przycisk”','Nazwę pliku SVG','Tytuł strony','Kolor ikony','Bez tekstu i aria-label nie ma z czego policzyć nazwy.'],
['Jaki element jest odpowiedni dla danych o wierszach i kolumnach?','table z th scope','siatka div z CSS','lista ul z wierszami','canvas','Czytnik odczytuje nagłówki kolumn dzięki semantyce tabeli.'],
['Co jest prawidłowe dla kolejności nagłówków?','Bez przeskoków poziomów, zgodnie ze strukturą','Dobór poziomu według rozmiaru czcionki','Tylko h1 na całej stronie w każdym widoku','h6 dla głównych sekcji','Nagłówek opisuje strukturę, a rozmiar ustawia CSS.'],
['Co jest najlepszym pierwszym krokiem audytu DevDesk?','Odłożyć mysz i przejść główne ścieżki klawiaturą','Włączyć aria-label wszędzie','Zmienić wszystkie div na section','Napisać tylko test Lighthouse','Taki test od razu ujawnia największe blokady.'],
['Dlaczego lang na html ma znaczenie?','Czytnik dobiera wymowę i język','Zmienia walutę','Zmienia kierunek renderowania Reacta','Wymusza tłumaczenie strony','Kryterium 3.1.1 wymaga języka strony.'],
['Co jest prawdą o dodaniu role="link" na a href?','Jest zbędne: to duplikacja semantyki natywnej','Jest wymagane przez WCAG','Włącza klawiaturę','Zmienia kolor linku','Element a z href już ma rolę linku.'],
['Który wariant jest poprawny dla akcji „Usuń” w tabeli?','button z nazwą zawierającą kontekst, np. „Usuń ticket 12”','div z ikoną kosza','a href="#" z onClick','span z title="Usuń"','Na liście kilku przycisków nazwa powinna mówić, którego dotyczy wiersza.'],
['Czemu a href="#" z onClick to antywzorzec?','Udaje nawigację, zmienia adres i zaciemnia semantykę akcji','Jest niezgodne z JSX','Wymaga aria-label','Blokuje fokus','Akcję na stronie obsługuje button.'],
['Co zrobi Spacja na fokusowanym linku?','Domyślnie przewinie stronę, nie aktywuje linku','Aktywuje link jak Enter','Nic, zawsze','Otworzy nową kartę','Linki aktywuje Enter, a przyciski także Spacja. To dobrze pokazuje różnicę ról.'],
['Kiedy disabled na przycisku jest problemem?','Gdy użytkownik nie dostaje wyjaśnienia, a przycisk znika z kolejności Tab','Nigdy nie jest','Zawsze narusza WCAG','Gdy przycisk ma tekst','Warto rozważyć walidację po kliknięciu i widoczny komunikat zamiast cichej blokady.'],
['Jaki atrybut wiąże pole z jego błędem?','aria-describedby','aria-owns','aria-flowto','aria-controls','Treść błędu staje się opisem odczytywanym przy polu.'],
['Co oznacza „nazwa dostępna” (accessible name)?','Tekst, którym technologia wspomagająca nazywa element','Atrybut name formularza','Identyfikator id','Nazwa komponentu React','Nazwę liczą przeglądarki, m.in. z label, aria-label i treści.'],
['Który wariant ma pierwszeństwo przy obliczaniu nazwy przycisku?','aria-labelledby przed aria-label i treścią','title przed wszystkim','Treść przed aria-labelledby','data-label','Algorytm nazwy zaczyna od aria-labelledby, a title jest wyjściem ostatniej szansy.'],
['Czemu nazwa wizualna powinna zawierać się w aria-label?','Użytkownik sterujący głosem wypowiada to, co widzi','Bo inaczej TypeScript zgłosi błąd','Bo React scali te atrybuty','Bo tak działa CSS','Kryterium 2.5.3 opisuje zgodność etykiety wizualnej z nazwą.'],
['Co jest dowodem wykonania kryterium akceptacji tej lekcji?','Testy i przejście klawiaturą całej ścieżki','Komentarz „dostępne” w kodzie','Wysoki wynik wydajności','Użycie biblioteki UI','Kryterium ma dać się sprawdzić zachowaniem, nie deklaracją.'],
['Czy biblioteka komponentów zapewnia dostępność aplikacji?','Pomaga, ale trzeba poprawnie jej użyć i sprawdzić całość','Tak, automatycznie','Tak, jeśli jest popularna','Tak, gdy ma typy TS','Złożenie gotowych komponentów nadal wymaga etykiet i zarządzania fokusem.'],
['Dlaczego test getByRole sprawdza też dostępność?','Odnajduje element tak, jak widzi go drzewo dostępności','Bo uruchamia axe','Bo renderuje w prawdziwym czytniku','Bo sprawdza kontrast','Brak nazwy lub roli sprawi, że zapytanie nie znajdzie elementu.'],
['Co z fokusem, gdy usuwasz element, który go miał?','Przenieś fokus w sensowne miejsce, np. na kolejny wiersz','Nic: przeglądarka wybierze następny element','Odśwież stronę','Ukryj kursor','Bez tego fokus wraca na body i użytkownik traci pozycję.'],
['Który tekst alternatywny jest najlepszy dla wykresu trendu ticketów?','Krótki wniosek, np. „Liczba otwartych ticketów rosła przez 3 tygodnie”','„wykres.png”','„Obraz”','Pusty alt','Alternatywa powinna przekazywać to, co obraz ma pokazać.'],
['Co sprawdzisz w trybie wymuszonych kolorów lub przy powiększeniu 200%?','Czy treść i fokus nadal są czytelne, a układ się nie rozsypuje','Czy aplikacja renderuje się szybciej','Czy klucze tłumaczeń są poprawne','Czy typy TypeScript się kompilują','Dostępność obejmuje też osoby korzystające z powiększenia i własnych ustawień kolorów.'],
['Które zdanie o dostępności w projekcie jest prawdziwe?','Taniej wbudować ją od początku niż poprawiać na końcu','Zajmuje się nią tylko QA','Dotyczy tylko osób niewidomych','Da się ją dodać jedną biblioteką','Decyzje o komponentach mnożą się w całym kodzie, także przy klawiaturze i przy urazach.'],
];
const header=String.raw`import {contrast,meetsAA} from "./contrast";
import {violations} from "./a11yLab";
import {render} from "@testing-library/react";
import {IconButton} from "./IconButton";
import {TextField} from "./TextField";
import {statusPalette} from "./StatusBadge";
function nameOf(jsx:React.ReactElement){const view=render(jsx);const text=view.container.querySelector("button")?.getAttribute("aria-label");view.unmount();return text??"brak";}
function attr(label:string,props:object,name:string){const view=render(<TextField label={label} {...props}/>);const value=view.getByLabelText(label).getAttribute(name);view.unmount();return value??"brak";}`;
const probeRows=[
['Kontrast czerni na bieli zaokrąglony do całości','Math.round(contrast("#000000","#ffffff"))',21,'To maksymalna wartość w skali WCAG.',['1','4.5','100']],
['Kontrast bieli na czerni (odwrócona kolejność)','Math.round(contrast("#ffffff","#000000"))',21,'Jaśniejszy kolor zawsze trafia do licznika.',['1','0','7']],
['Kontrast tego samego koloru','contrast("#777777","#777777")',1,'Identyczne kolory dają najniższą wartość skali.',['0','21','4.5']],
['Czy #767676 na białym spełnia AA dla zwykłego tekstu?','meetsAA("#767676","#ffffff")',true,'Wartość to około 4,54:1, czyli tuż nad progiem.'],
['Czy #777777 na białym spełnia AA dla zwykłego tekstu?','meetsAA("#777777","#ffffff")',false,'Około 4,48:1 to tuż poniżej progu 4,5:1.'],
['Czy #777777 na białym spełnia AA dla dużego tekstu?','meetsAA("#777777","#ffffff",{large:true})',true,'Duży tekst ma próg 3:1.'],
['Czy #999999 na białym spełnia AA dla dużego tekstu?','meetsAA("#999999","#ffffff",{large:true})',false,'Wynik to około 2,85:1, więc poniżej nawet niższego progu.'],
['Czy biały na #2563eb spełnia AA?','meetsAA("#ffffff","#2563eb")',true,'Ten niebieski daje ponad 5:1 z białym tekstem.'],
['Czy biały na #60a5fa spełnia AA?','meetsAA("#ffffff","#60a5fa")',false,'Jasny niebieski z białym tekstem jest za słaby.'],
['Czy kolory plakietki „Pilny” spełniają AA?','meetsAA(statusPalette.urgent.color,statusPalette.urgent.background)',true,'Paleta jest dobrana i sprawdzona testem.'],
['Czy kolory plakietki „Otwarty” spełniają AA?','meetsAA(statusPalette.open.color,statusPalette.open.background)',true,'Ciemny niebieski na jasnym tle przechodzi próg.'],
['Czy kolory plakietki „Zamknięty” spełniają AA?','meetsAA(statusPalette.closed.color,statusPalette.closed.background)',true,'Ciemna zieleń na jasnym tle przechodzi próg.'],
['Jaką regułę naruszy obraz bez alt?','await violations(<img src="/a.png"/>)','image-alt','axe wykrywa brak tekstu alternatywnego.',['label','button-name','brak']],
['Ile naruszeń zgłosi axe dla obrazu z pustym alt?','await violations(<img src="/a.png" alt=""/>)','brak','Pusty alt jest poprawnym oznaczeniem obrazu dekoracyjnego.',['image-alt','label','button-name']],
['Jaką regułę naruszy automat dla przycisku z samą ikoną?','await violations(<button><svg/></button>)','button-name','Przycisk bez nazwy to częsty błąd wykrywany automatycznie.',['image-alt','label','brak']],
['Jaką regułę naruszy link bez treści?','await violations(<a href="/x"><svg/></a>)','link-name','Link potrzebuje nazwy tak jak przycisk.',['button-name','image-alt','brak']],
['Jaką regułę naruszy input bez etykiety i placeholdera?','await violations(<input/>)','label','Pole formularza bez nazwy jest wykrywane.',['image-alt','button-name','brak']],
['Co zgłosi axe dla pola z samym placeholderem?','await violations(<input placeholder="E-mail"/>)','brak','Placeholder liczy się do nazwy, więc automat milczy. To nadal zła praktyka, wykrywana przez człowieka.',['label','image-alt','button-name']],
['Co zgłosi axe dla klikalnego diva?','await violations(<div onClick={()=>{}}>Zamknij</div>)','brak','Automat nie wie, że div miał być przyciskiem. Dlatego potrzebny jest test klawiaturą.',['button-name','label','aria-command-name']],
['Ile przycisków w drzewie dostępności ma ten fragment z aria-hidden?','(()=>{const v=render(<div aria-hidden="true"><button>OK</button></div>);const n=v.queryAllByRole("button").length;v.unmount();return n;})()',0,'Przycisk zniknął z drzewa dostępności, a mimo to Tab nadal na niego trafi. Taki element to błąd, który trzeba wychwycić w przeglądzie i teście klawiaturą.'],
['Co zgłosi axe dla nieznanej roli?','await violations(<div role="klikacz">x</div>)','aria-roles','Wartość role musi istnieć w specyfikacji.',['aria-allowed-role','button-name','brak']],
['Co zgłosi axe dla aria-selected na zwykłym divie?','await violations(<div aria-selected="true">x</div>)','aria-allowed-attr','Atrybut stanu musi pasować do roli elementu.',['aria-roles','label','brak']],
['Co zgłosi axe dla poprawnego IconButton?','await violations(<IconButton label="Zamknij" icon="✕"/>)','brak','Komponent ma nazwę, typ i ukrytą ikonę.',['button-name','aria-hidden-focus','label']],
['Co zgłosi axe dla poprawnego TextField z błędem?','await violations(<TextField label="E-mail" error="Błąd"/>)','brak','Etykieta i powiązany błąd spełniają reguły.',['label','aria-valid-attr-value','aria-allowed-attr']],
['Co zgłosi axe, gdy aria-describedby wskazuje nieistniejące id?','await violations(<><label htmlFor="x">A</label><input id="x" aria-describedby="nie-ma"/></>)','brak','W tym przebiegu axe nie zgłasza tego jako pewnego naruszenia, więc zero naruszeń nie dowodzi poprawnej relacji. Sprawdź ją przez toHaveAccessibleDescription.',['label','aria-allowed-attr','aria-valid-attr-value']],
['Co zawiera aria-label komponentu IconButton?','nameOf(<IconButton label="Zamknij ticket" icon="✕"/>)','Zamknij ticket','Nazwa pochodzi z wymaganego propa, a nie z ikony.',['✕','brak','button']],
['Jaką wartość ma aria-invalid przy błędzie?','attr("E",{error:"Zły"},"aria-invalid")','true','Stan odzwierciedla istnienie błędu.',['false','brak','invalid']],
['Jaką wartość ma aria-invalid bez błędu?','attr("E",{},"aria-invalid")','false','Boolean jest zapisany jawnie jako tekst.',['true','brak','undefined']],
['Czy pole bez hint i błędu ma aria-describedby?','attr("E",{},"aria-describedby")','brak','Pusty tekst zamieniamy na undefined, aby nie zostawiać pustego atrybutu.',['true','false','pusty tekst']],
['Ile identyfikatorów ma aria-describedby z hint i błędem?','attr("E",{hint:"H",error:"B"},"aria-describedby").split(" ").length',2,'Atrybut przyjmuje listę identyfikatorów.'],
['Czy hint trafia do opisu pola?','(()=>{const v=render(<TextField label="E" hint="Służbowy"/>);const t=v.getByLabelText("E").getAttribute("aria-describedby")===v.getByText("Służbowy").id;v.unmount();return t;})()',true,'Opis wiąże się przez identyfikator elementu podpowiedzi.'],
['Czy spread nadpisze type przycisku podany jawnie?','(()=>{const v=render(<IconButton label="W" icon="i" type="submit"/>);const t=v.getByRole("button").getAttribute("type");v.unmount();return t;})()','submit','Domyślny type="button" można świadomie zmienić.',['button','brak','reset']],
['Jaka jest rola natywnego elementu a z href?','(()=>{const v=render(<a href="/x">X</a>);const n=v.queryAllByRole("link").length;v.unmount();return n;})()',1,'href nadaje elementowi a rolę linku.'],
['Ile ról link ma a bez href?','(()=>{const v=render(<a>X</a>);const n=v.queryAllByRole("link").length;v.unmount();return n;})()',0,'Element a bez href nie jest linkiem.'],
['Ile przycisków znajdzie getByRole dla klikalnego diva?','(()=>{const v=render(<div onClick={()=>{}}>X</div>);const n=v.queryAllByRole("button").length;v.unmount();return n;})()',0,'onClick nie nadaje roli, więc test też ujawnia problem.'],
['Jaką wartość tabIndex ma natywny button?','(()=>{const v=render(<button>X</button>);const t=v.getByRole("button").tabIndex;v.unmount();return t;})()',0,'Natywny przycisk jest w kolejności Tab bez atrybutu.'],
['Jaką wartość tabIndex ma zwykły div?','(()=>{const v=render(<div data-testid="d">X</div>);const t=v.getByTestId("d").tabIndex;v.unmount();return t;})()',-1,'Div nie przyjmuje fokusu klawiaturą.',['0','1','undefined']],
['Czy input z etykietą ma nazwę dostępną z label?','(()=>{const v=render(<TextField label="E-mail"/>);const n=v.queryAllByRole("textbox",{name:"E-mail"}).length;v.unmount();return n;})()',1,'Rola i nazwa są widoczne w drzewie dostępności.'],
['Ile textboxów o nazwie znajdziesz przy samym placeholderze i nazwie „E-mail”?','(()=>{const v=render(<input placeholder="E-mail"/>);const n=v.queryAllByRole("textbox",{name:"E-mail"}).length;v.unmount();return n;})()',0,'Testing Library nie liczy placeholdera do nazwy, choć axe go przepuścił. Narzędzia różnią się w tym punkcie, a widoczna etykieta rozwiązuje problem w obu.'],
['Czy luminancja odrzuca kolor w nieobsługiwanym zapisie?','(()=>{try{contrast("red","#ffffff");return "przyjęty";}catch{return "odrzucony";}})()','odrzucony','Funkcja jawnie wymaga zapisu #rrggbb.',['przyjęty','21','1']],
];
const project={
'src/IconButton.tsx':iconButton,'src/TextField.tsx':textField,'src/Avatar.tsx':avatar,'src/RouteHeading.tsx':routeHeading,'src/Layout.tsx':layout,
'src/contrast.ts':contrast,'src/StatusBadge.tsx':badge,'src/focus.css':focusCss,'src/a11yLab.tsx':lab,'src/a11y.test.tsx':tests,
'README.md':'DevDesk: fundamenty dostępności. npm install, npm test. Kontrast liczy funkcja wg WCAG, bo jsdom nie oblicza stylów. Test klawiaturą w jsdom nie zastępuje przejścia ekranów w prawdziwej przeglądarce.\n',
};
const mutants=[
{file:'src/IconButton.tsx',before:'aria-label={label} ',after:''},
{file:'src/IconButton.tsx',before:'type="button",',after:''},
{file:'src/TextField.tsx',before:'htmlFor={id}',after:''},
{file:'src/TextField.tsx',before:'.join(" ")||undefined',after:'.join(" ")'},
{file:'src/TextField.tsx',before:'aria-describedby={describedBy}',after:''},
{file:'src/RouteHeading.tsx',before:'[pathname]',after:'[]'},
{file:'src/RouteHeading.tsx',before:' tabIndex={-1}',after:''},
{file:'src/Layout.tsx',before:' tabIndex={-1}',after:''},
{file:'src/contrast.ts',before:'.sort((x,y)=>y-x)',after:''},
{file:'src/contrast.ts',before:'large?3:4.5',after:'large?3:3'},
];
module.exports=lesson({exercises,concepts,probeRows,project,mutants,header});
