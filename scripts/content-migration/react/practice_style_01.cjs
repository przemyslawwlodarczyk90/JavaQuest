const row = 'import clsx from "clsx";\ntype Ticket={id:number;title:string;status:"OPEN"|"CLOSED"};\nexport function TicketRow({ticket,selected,compact=false}:{ticket:Ticket;selected:boolean;compact?:boolean}){return <tr className={clsx("row",{"row--selected":selected,"row--closed":ticket.status==="CLOSED","row--compact":compact})}><td>{ticket.id}</td><td>{ticket.title}</td></tr>;}';
const progress = 'export function ProgressBar({value,label}:{value:number;label:string}){const percent=Math.min(100,Math.max(0,Number.isFinite(value)?value:0));return <div className="progress" role="progressbar" aria-label={label} aria-valuemin={0} aria-valuemax={100} aria-valuenow={percent}><div className="progress__fill" style={{width:`${percent}%`}}/></div>;}';
const chip = 'import type {CSSProperties} from "react";\ntype ChipStyle=CSSProperties&{"--chip-color":string};\nexport function TagChip({label,color}:{label:string;color:string}){const style:ChipStyle={"--chip-color":color};return <span className="chip" style={style}>{label}</span>;}';
const css = '.row:hover { background: #f9fafb; }\n.row--closed td { color: #6b7280; text-decoration: line-through; }\n.row--selected { outline: 2px solid #2563eb; outline-offset: -2px; }\n.row--compact td { padding: 0.25rem; }\n.progress { height: 0.5rem; background: #e5e7eb; overflow: hidden; border-radius: 4px; }\n.progress__fill { height: 100%; background: #2563eb; }\n.chip { border: 1px solid var(--chip-color); color: var(--chip-color); padding: 0.125rem 0.5rem; }\nbutton:focus-visible { outline: 3px solid #2563eb; outline-offset: 2px; }\nbutton:disabled { opacity: 0.5; cursor: not-allowed; }';
const project = {
 'src/TicketRow.tsx':row,
 'src/ProgressBar.tsx':progress,
 'src/TagChip.tsx':chip,
 'src/App.css':css,
 'src/App.tsx':'import "./App.css";\nimport {TicketRow} from "./TicketRow";\nimport {ProgressBar} from "./ProgressBar";\nimport {TagChip} from "./TagChip";\nexport default function App(){return <main><h1>Stylowanie DevDesk</h1><ProgressBar value={75} label="Otwarte zgłoszenia"/><table><tbody><TicketRow ticket={{id:1,title:"API",status:"CLOSED"}} selected={true} compact/></tbody></table><TagChip label="api" color="#2563eb"/><button type="button">Szczegóły</button></main>;}'
};
const exercises = [
 ['Dodaj globalny arkusz App.css w aplikacji Vite i użyj klasy app-shell.','Arkusz importujesz dla efektu dołączenia stylów.','import "./App.css";\nexport function App(){return <main className="app-shell">DevDesk</main>;}\n/* App.css: .app-shell { max-width: 72rem; margin: 0 auto; } */'],
 ['Zamień HTML class na odpowiedni prop JSX dla panelu z dwiema klasami.','Wartość pozostaje napisem rozdzielonym spacją.','export function Panel(){return <section className="panel panel--compact">Treść</section>;}'],
 ['Napisz Box ze style: szerokość 120px, tło #fff, górny margines 8px.','Nazwy właściwości obiektu są camelCase.','export function Box(){return <div style={{width:120,backgroundColor:"#fff",marginTop:8}}>Box</div>;}'],
 ['Wyjaśnij jednostki width:120, opacity:0.5 i lineHeight:1.5.','Nie każda właściwość liczbowa jest długością.','Width otrzyma 120px. Opacity jest bez jednostki i wyniesie 0.5. Line-height:1.5 jest mnożnikiem rozmiaru fontu, nie 1.5px.'],
 ['Zainstaluj clsx i opisz, co robi z listą klas.','To mały pomocnik do napisów, nie silnik kaskady CSS.','W katalogu projektu uruchom npm install clsx. Następnie import clsx from "clsx". Funkcja składa nazwy klas zależnie od argumentów; nie rozstrzyga konfliktujących reguł CSS.'],
 ['Złóż klasy row, warunkową row--selected i row--compact przez clsx.','Użyj obiektu klasa→warunek.','import clsx from "clsx";\nexport function Row({selected,compact}:{selected:boolean;compact:boolean}){return <div className={clsx("row",{"row--selected":selected,"row--compact":compact})}/>;}'],
 ['Napisz pełny TicketRow z klasą dla CLOSED oraz opcjonalnym compact=false.','Selected jest wymagany, compact ma domyślną wartość.',row],
 ['Wypisz klasy TicketRow dla OPEN i false/false oraz CLOSED i true/true.','Bazowa row pojawia się zawsze.','OPEN, selected=false, compact=false: row. CLOSED, selected=true, compact=true: row row--selected row--closed row--compact.'],
 ['Dodaj CSS wyszarzający i przekreślający komórki zamkniętego wiersza.','Selektor odwołuje się do klasy wiersza oraz jego td.','/* App.css */\n.row--closed td { color: #6b7280; text-decoration: line-through; }'],
 ['Przenieś samą zmianę tła po najechaniu do CSS. Nie dodawaj useState.','Pseudoklasa wystarczy dla zwykłego efektu wizualnego.','export function Row(){return <tr className="row"><td>API</td></tr>;}\n/* .row:hover { background: #f9fafb; } */'],
 ['Dodaj widoczny fokus przycisku bez usuwania obrysu bez zamiennika.','Testuj prawdziwy element focusowalny, nie zwykły tr.','/* button:focus-visible { outline: 3px solid #2563eb; outline-offset: 2px; } */\nexport function Details(){return <button type="button">Szczegóły</button>;}'],
 ['Dodaj natywnie wyłączony przycisk oraz styl :disabled.','Sam wygląd szarego przycisku nie blokuje jego działania.','export function Save({disabled}:{disabled:boolean}){return <button type="button" disabled={disabled}>Zapisz</button>;}\n/* button:disabled { opacity: 0.5; cursor: not-allowed; } */'],
 ['Zbuduj pasek postępu z zakresem 0–100 i etykietą dostępności. Dla NaN i Infinity przyjmij 0.','Zabezpiecz Number.isFinite przed ograniczaniem.',progress],
 ['Podaj wyniki szerokości dla -10, 0, 30, 150, NaN i Infinity według przyjętego ProgressBar.','Nie pomijaj przyjętej obsługi wartości niefinitych.','-10→0%, 0→0%, 30→30%, 150→100%, NaN→0%, Infinity→0%. Wszystkie trzy aria wartości pozostają zgodne z zakresem.'],
 ['Oblicz procent otwartych zgłoszeń z ochroną przed dzieleniem przez zero.','Pusta lista ma dawać 0, a nie NaN.','type Ticket={status:"OPEN"|"CLOSED"};\nexport function openPercent(tickets:readonly Ticket[]){return tickets.length===0?0:tickets.filter(t=>t.status==="OPEN").length/tickets.length*100;}'],
 ['Napisz TagChip ustawiający zmienną CSS --chip-color i typując ją jawnie.','Rozszerz CSSProperties o konkretną właściwość własną.',chip],
 ['Dodaj CSS wykorzystujący --chip-color do obramowania i koloru napisu.','Wartość odczytuje var, nie nazwa props React.','/* .chip { color: var(--chip-color); border: 1px solid var(--chip-color); } */'],
 ['Wprowadź mapę api/ui z kolorem domyślnym dla nieznanego tagu.','Dla nieznanych kluczy typ powinien dopuszczać brak wartości.','export function tagColor(tag:string):string{const colors:Partial<Record<string,string>>={api:"#2563eb",ui:"#9333ea"};return colors[tag]??"#4b5563";}'],
 ['Przyjmij style?:CSSProperties w komponentcie Box i zachowaj width z props. Przy konflikcie styl przekazany przez rodzica wygrywa.','Kolejność spread decyduje o nadpisaniu właściwości obiektu.','import type {CSSProperties,ReactNode} from "react";\nexport function Box({width,style,children}:{width:number;style?:CSSProperties;children:ReactNode}){return <div style={{width,...style}}>{children}</div>;}'],
 ['Napisz Box, w którym width z dedykowanego props wygrywa z style.width.','Odwróć kolejność spread.','import type {CSSProperties} from "react";\nexport function Box({width,style}:{width:number;style?:CSSProperties}){return <div style={{...style,width}}/>;}'],
 ['Popraw literówki widht i backgroundColour w obiekcie style.','Właściwości odpowiadają camelCase DOM CSS.','import type {CSSProperties} from "react";\nexport const style:CSSProperties={width:10,backgroundColor:"red"};'],
 ['Sprawdź, czy TS wykryje className="panle" bez narzędzia generującego typy klas.','ClassName jest zwykłym napisem.','Nie: zwykły className nie jest powiązany przez TypeScript z selektorami arkusza. Potrzebna jest kontrola w przeglądarce lub dodatkowe narzędzie. Nie myl tego z typowaniem właściwości style.'],
 ['Ustal, czy display:"flexx" musi wywołać błąd CSSProperties w używanych typach React.','Sprawdź definicję wartości, zamiast zakładać pełną walidację CSS.','Nie należy na tym polegać. Typy CSS mogą dopuszczać rozszerzalne wartości string; niektóre literówki wartości przechodzą. TypeScript pomaga w nazwach właściwości, ale nie zastępuje przeglądarki ani walidatora CSS. Użyj display:"flex" i sprawdź efekt.'],
 ['Usuń konflikt globalnej klasy .title dwóch komponentów przez nazewnictwo BEM.','Nadaj nazwy wynikające z bloku.','Panel: className="panel__title", CSS .panel__title. TicketCard: className="ticket-card__title", CSS .ticket-card__title. To konwencja ograniczająca kolizje; nie daje automatycznej izolacji nazw.'],
 ['Wyjaśnij, dlaczego import App.css wewnątrz App nie ogranicza .title do App.','Zasięg pliku JS nie jest zasięgiem selektora CSS.','Zwykły import arkusza dołącza globalne reguły do aplikacji. Selektor .title nadal może pasować do elementów innych komponentów. Lokalność wymaga np. CSS Modules lub świadomego zakresu selektorów.'],
 ['Złóż zewnętrzną className z bazową button przez clsx.','Rodzic rozszerza zestaw klas; clsx nie rozwiązuje kaskady.','import clsx from "clsx";\nexport function Button({className}:{className?:string}){return <button type="button" className={clsx("button",className)}>OK</button>;}'],
 ['Napisz JSX/JSDoc wersję ProgressBar z liczbowym value.','Bez adnotacji TypeScript po parametrze.','/** @param {{value:number}} props */\nexport function ProgressBar({value}){const percent=Math.min(100,Math.max(0,Number.isFinite(value)?value:0));return <div className="progress"><div style={{width:`${percent}%`}}/></div>;}'],
 ['Przygotuj plan testu klawiatury dla tabeli z przyciskami.','Wiersz tr domyślnie nie otrzymuje fokusu z Tab.','Przechodź Tab i Shift+Tab po przyciskach oraz linkach w tabeli. Sprawdź widoczny focus-visible, kolejność nawigacji i działanie Enter/Spacji zgodnie z natywnym elementem. Nie oczekuj fokusu zwykłego tr bez dodatkowego modelu interakcji.'],
 ['Rozpisz test macierzy selected, compact i CLOSED dla TicketRow.','To trzy niezależne warunki.','Sprawdź 8 kombinacji trzech booleanów. Row jest zawsze obecne; row--selected dokładnie dla selected, row--compact dokładnie dla compact, row--closed dokładnie dla CLOSED. Zweryfikuj wygląd, bo sama nazwa klasy nie dowodzi działania CSS.'],
 ['Złóż miniaplikację: wiersz, pasek 75%, tag api i dostępny fokus przycisku.','Utwórz osobne pliki z poniższego kompletnego zestawu.','Pliki projektu:\n'+Object.entries(project).map(([file,code])=>`--- ${file} ---\n${code}`).join('\n\n')],
];
const quiz = `
Jaki prop JSX odpowiada HTML class?|className|cssClass|classList wyłącznie|styleName|React przyjmuje nazwy klas przez className.
Jak przekazać dwie klasy do className?|Jako napis rozdzielony spacją|Jako dwa props className|Jako obiekt CSSProperties|Jako liczbę|Atrybut DOM class zawiera listę nazw oddzielonych spacjami.
Co robi import zwykłego App.css w Vite?|Dołącza arkusz globalny|Izoluje każdą klasę do App|Tworzy komponent App|Sprawdza role|Import JS nie zmienia zwykłego CSS w lokalny moduł.
Czy .title ze zwykłego App.css może wpłynąć na inny komponent?|Tak|Nie|Tylko w TSX|Tylko bez clsx|Selektor działa globalnie według reguł CSS.
Co ogranicza kolizje nazw w globalnym CSS?|Konwencja nazewnictwa np. BEM|Samo użycie TSX|Nazwa funkcji React|Key na elemencie|Dłuższe nazwy bloków zmniejszają ryzyko kolizji.
Czy BEM zapewnia automatyczną izolację klas?|Nie|Tak|Tylko z children|Tylko w Vite|BEM jest konwencją, nie mechanizmem transformacji.
Co powinno dominować przy stałym wyglądzie komponentu?|Klasy CSS|Setki inline właściwości w każdym renderze|Globalne zmienne JS|Losowe kolory|Klasy pozwalają opisywać reguły i stany w arkuszu.
Kiedy style jest przydatne?|Dla wartości obliczanej z danych|Do zapisania :hover jako zagnieżdżonego selektora|Do importowania CSS|Do nadawania key|Dynamiczna szerokość jest typowym zastosowaniem inline style.
Jaki typ wartości przyjmuje style na elemencie React?|Obiekt właściwości|Wyłącznie napis CSS|Tablicę nazw klas|Funkcję zwracającą CSS|React DOM używa obiektu style.
Jak zapisać background-color w style?|backgroundColor|background-colour|background_color|BackgroundColor|Właściwości CSS w obiekcie mają zwykle camelCase.
Jak zapisać margin-top w style?|marginTop|margin-top jako zwykły identyfikator|MarginTop|margin_top|W JSX używa się odpowiednika właściwości DOM.
Co oznacza width:120 w style?|120px|120%|120rem|Brak szerokości|Liczbowe długości takie jak width otrzymują px.
Co oznacza width:"50%"?|Połowę wymiaru odniesienia|50px|Błąd składni JSX|Pięćdziesiąt klas|Napis zawiera jawną jednostkę procentową.
Co oznacza opacity:0.5?|Połowę krycia|0.5px|50px|Nieaktywny prop React|Opacity jest bezwymiarową wartością CSS.
Co oznacza lineHeight:1.5?|Mnożnik rozmiaru fontu|1.5px|150px|Błąd TypeScriptu zawsze|Line-height może mieć wartość bez jednostki.
Czy każda liczba w style dostaje px?|Nie|Tak|Tylko w TSX|Tylko w JSX|Istnieją właściwości bez jednostki, np. opacity.
Jakie role pełni CSSProperties?|Opisuje typ obiektu stylu|Ładuje CSS z serwera|Generuje klasy automatycznie|Zastępuje wszystkie testy CSS|To typ React dla właściwości style.
Jak importować CSSProperties w TSX?|Przez import type z react|Z clsx|Z pliku CSS|Jako komponent|Typ jest eksportowany przez typy React.
Czy TS sprawdza istnienie className="panle" w arkuszu globalnym?|Nie|Tak|Tylko dla div|Tylko w trybie strict|ClassName jest napisem bez powiązania z arkuszem.
Czy CSSProperties jest pełnym walidatorem wszystkich wartości CSS?|Nie|Tak|Tylko dla display|Tylko przy braku Vite|Rozszerzalne wartości string mogą przepuszczać literówki.
Co należy zrobić z style={{widht:10}}?|Poprawić na width|Zainstalować inny React|Zmienić key|Dodać children|Widht jest literówką nazwy właściwości.
Czy poprawna nazwa właściwości gwarantuje widoczny efekt?|Nie|Tak|Tylko w TSX|Tylko dla width|Znaczenie ma wartość, układ, inne reguły i obsługa przeglądarki.
Do czego służy clsx?|Warunkowego składania nazw klas|Scalania konfliktujących reguł CSS|Tworzenia komponentów|Pobierania ticketów|Clsx buduje napis z argumentów.
Jak zainstalować clsx w projekcie npm?|npm install clsx|npm create clsx|npm install react-clsx-types obowiązkowo|vite add class|Clsx jest zwykłą zależnością npm.
Czy clsx ma własne typy w paczce?|Tak|Nie|Tylko z @types/react-dom|Tylko jako plik JSON|Paczka udostępnia deklaracje TypeScript.
Co oznacza klucz w obiekcie przekazanym do clsx?|Nazwę klasy|Właściwość CSS|Id rekordu|Nazwę callbacku|Wartość klucza określa, czy nazwa zostanie dodana.
Co oznacza wartość true przy klasie w obiekcie clsx?|Dołączenie nazwy|Usunięcie całego elementu|Nadanie opacity|Wywołanie renderu|Wartość truthy włącza klasę.
Co oznacza false przy klasie w clsx?|Pominięcie nazwy|Napis false w DOM|Przekreślenie tekstu|Błąd TypeScriptu|Klasa z fałszywym warunkiem nie trafia do wyniku.
Czy clsx obsługuje tablice argumentów?|Tak|Nie|Tylko liczby|Tylko JSX|Tablice mogą grupować dalsze wartości klas.
Czy clsx usuwa powtarzające się nazwy klas automatycznie?|Nie|Tak, zawsze|Tylko row|Tylko z TSX|Clsx nie jest deduplikatorem klas.
Czy clsx decyduje, który kolor CSS wygra?|Nie|Tak|Tylko ostatni argument|Tylko w Vite|Konflikty rozstrzyga kaskada CSS.
Czy kolejność klas w className sama ustala kolejność reguł arkusza?|Nie|Tak|Tylko z clsx|Tylko dla tła|Kolejność reguł, specyficzność i inne zasady kaskady są odrębne.
Co musi zawierać TicketRow niezależnie od warunków?|Bazową klasę row|Klasę row--closed|Klasę row--selected|Klasę row--compact|Baza jest podana bez warunku.
Kiedy pojawia się row--closed?|Przy status CLOSED|Przy status OPEN|Przy selected=false|Przy compact=true zawsze|Klasa odzwierciedla status ticketu.
Kiedy pojawia się row--selected?|Gdy selected=true|Gdy id=0|Gdy tytuł jest pusty|Gdy compact niepodane|Selected ma własny niezależny warunek.
Kiedy pojawia się row--compact?|Gdy compact=true|Dla każdego CLOSED|Dla każdego OPEN|Tylko z selected=false|Compact nie zależy od statusu ani selected.
Jaka jest wartość domyślna compact w rozwiązaniu?|false|true|null|1|Brak props nie włącza kompaktowego układu.
Ile kombinacji dają trzy niezależne warunki boolean?|8|3|6|9|Dwa do potęgi trzeciej daje osiem.
Czy zmiana className mutuje ticket?|Nie|Tak|Tylko dla CLOSED|Tylko w JSX|Komponent wylicza prezentację z wejścia.
Jaki selektor przekreśli komórki zamkniętego wiersza?|.row--closed td|.row.closed zawsze|td:closed|row--closed|Selektor potomka wybiera td pod właściwym wierszem.
Jaka właściwość daje przekreślenie?|text-decoration: line-through|font-style: closed|display: strike|opacity: strike|Line-through rysuje linię przez tekst.
Gdzie opisać prostą zmianę tła przy hover?|W CSS przez :hover|W useState obowiązkowo|W key|W typie Ticket|Pseudoklasa wystarcza dla takiej prezentacji.
Czy inline style obsługuje klucz :hover jako zwykły selektor?|Nie|Tak|Tylko dla button|Tylko w TSX|Obiekt style React DOM nie jest arkuszem z selektorami.
Jaki stan powinien mieć widoczny styl dla nawigacji klawiaturą?|:focus-visible|:visited wyłącznie|:empty wyłącznie|:hover wyłącznie|Fokus musi być rozpoznawalny bez myszy.
Czy zwykły tr jest domyślnie dostępny przez Tab?|Nie|Tak|Tylko z className|Tylko dla CLOSED|Tab odwiedza elementy focusowalne, np. przyciski w komórkach.
Na czym testować focus-visible w tabeli z przyciskami?|Na przyciskach w komórkach|Wyłącznie na tbody|Na każdym td automatycznie|Na arkuszu CSS|To interaktywne elementy otrzymują fokus.
Czy usunięcie outline bez zamiennika jest poprawnym celem stylowania?|Nie|Tak, zawsze|Tylko dla selected|Tylko w Vite|Użytkownik klawiatury potrzebuje wskazania fokusu.
Co robi natywny disabled na button?|Wyłącza przycisk|Zmienia tylko kolor|Zmienia key|Usuwa rodzica|Stan semantyczny ma rzeczywiste działanie, nie tylko wygląd.
Czy klasa disabled sama wyłącza przycisk?|Nie|Tak|Tylko z opacity|Tylko w TSX|Nazwa klasy nie zastępuje natywnego atrybutu.
Jaka pseudoklasa wybiera natywnie wyłączony przycisk?|:disabled|:inactive|:closed|:selected|Disabled odzwierciedla stan kontrolki.
Jak stylować placeholder inputa?|Przez ::placeholder|Przez :placeholder zawsze|Przez key|Przez prop hover|Placeholder jest pseudoelementem CSS.
Dlaczego szerokość paska jest dobrym użyciem style?|Wynika z liczby przekazanej w props|Wymaga nowego arkusza dla każdego renderu|Jest rolą użytkownika|Nie można użyć procentów w CSS|Wartość może przyjmować wiele stanów dynamicznych.
Co ogranicza Math.min(100,Math.max(0,value)) dla skończonego value?|Zakres 0–100|Zakres 1–100|Wyłącznie liczby całkowite|Dokładnie 50|Max ustala dolną granicę, min górną.
Jakie width dostanie wartość -10 w pasku?|0%|-10%|10px|100%|Dolna granica wynosi zero.
Jakie width dostanie wartość 150 w pasku?|100%|150%|50%|0%|Górna granica wynosi sto.
Jakie width dostanie wartość 30 w pasku?|30%|30px|0%|100%|Wartość w zakresie nie jest zmieniana.
Co przyjęta wersja zrobi z NaN?|Użyje 0|Użyje 100|Wyświetli napis NaN%|Usunie wszystkie tickety|Number.isFinite odrzuca wartości niefinitych danych.
Co przyjęta wersja zrobi z Infinity?|Użyje 0|Użyje 100 zgodnie z samym min|Użyje Infinity%|Rzuci błąd Reacta zawsze|Jawna polityka dla niefinitych wartości poprzedza ograniczanie.
Czy sam Math.min i Math.max zamieni NaN w 0?|Nie|Tak|Tylko w TSX|Tylko z CSSProperties|NaN propaguje się przez te obliczenia.
Po co role=progressbar?|Nadaje semantykę paska postępu|Ustawia szerokość|Wybiera rolę admin|Włącza animację|Rola opisuje znaczenie dla technologii asystujących.
Skąd wziąć dostępną nazwę paska w rozwiązaniu?|Z aria-label=label|Z key|Z koloru|Z className zawsze|Etykieta wyjaśnia, czego dotyczy postęp.
Jakie aria-valuemin i aria-valuemax ma pasek?|0 i 100|1 i 5|0 i liczba znaków|Null i undefined|Zakres semantyczny odpowiada zakresowi procentów.
Jak powinno wyglądać aria-valuenow?|Zgodnie z ograniczonym percent|Zawsze 100|Jako napis z px|Jak id ticketu|Informacja dostępności ma być zgodna z wizualizacją.
Co zwrócić dla udziału otwartych przy pustej liście?|0|NaN|Infinity|100 zawsze|Jawna gałąź chroni przed dzieleniem przez zero.
Jaki procent daje 3 OPEN na 4 zgłoszenia?|75|25|3|4|Trzy czwarte razy sto to 75.
Jaki procent daje 0 OPEN na 4 zgłoszenia?|0|4|25|100|Zero podzielone przez dodatni total wynosi zero.
Jak zapisuje się nazwę własnej zmiennej CSS?|Z prefiksem --|Z prefiksem $$|Wyłącznie camelCase bez prefiksu|Jak prop React key|Custom properties zaczynają się od dwóch myślników.
Jak odczytać --chip-color w CSS?|var(--chip-color)|props.chipColor|style.chip|read(--chip-color)|Funkcja var pobiera wartość właściwości własnej.
Co daje ustawienie --chip-color na elemencie?|Dostarcza wartość regułom CSS|Tworzy nowy komponent|Zmienia nazwę taga|Dodaje klasę automatycznie|Arkusz może wykorzystać zmienną w kilku właściwościach.
Czy --chip-color ma zostać zamienione na chipColor w obiekcie style?|Nie|Tak|Tylko w TSX|Tylko dla niebieskiego|Nazwa własnej właściwości zachowuje zapis z myślnikami.
Jak jawnie dopuścić --chip-color w typie stylu?|Rozszerzyć CSSProperties o ten klucz|Użyć typu User|Zmienić nazwę na key|Usunąć wszystkie typy|Typ przecięcia może opisać konkretną zmienną.
Czy rzutowanie as CSSProperties sprawdza poprawność koloru w przeglądarce?|Nie|Tak|Tylko dla hex|Tylko z Vite|Asercja typu nie waliduje danych runtime.
Co zastosować dla nieznanego tagu w mapie kolorów?|Kolor domyślny|Losowy id|Mutację etykiety|Błąd renderu obowiązkowo|Fallback utrzymuje przewidywalny wygląd.
Czy Partial<Record<string,string>> dopuszcza brak mapowania?|Tak|Nie|Tylko dla api|Tylko dla ui|Odczyt może zwrócić undefined.
Po co łączyć className rodzica z klasą bazową?|By zachować bazę i rozszerzenie|By zawsze usunąć bazę|By zmienić props na CSS|By wygenerować id|Komponent może zachować swój kontrakt wyglądu i przyjąć dodatkowe klasy.
Co wygra w obiekcie {width,...style}, jeśli style.width istnieje?|Style.width|Zawsze pierwsze width|Żadna wartość|Błąd Reacta|Późniejszy klucz nadpisuje wcześniejszy w obiekcie.
Co wygra w {...style,width}?|Jawne width|Style.width|Losowa wartość|Null zawsze|Ostatnia właściwość obiektu ma pierwszeństwo.
Czy scalenie obiektów style automatycznie scala zagnieżdżone selektory?|Nie|Tak|Tylko hover|Tylko disabled|Zwykły style React DOM nie opisuje zagnieżdżonych selektorów.
Czy CSSProperties jest wartością potrzebną w przeglądarce?|Nie, jest typem|Tak, jest funkcją|Tak, jest arkuszem|Tak, jest komponentem|Import type znika po kompilacji.
Czy JSDoc może typować props JSX?|Tak|Nie|Tylko z rozszerzeniem tsx|Tylko przy braku Reacta|Komentarze mogą opisać kontrakt JavaScriptu dla narzędzi.
Czy typy zastępują kontrolę wizualną CSS?|Nie|Tak|Tylko dla hover|Tylko przy className|Kompilacja nie dowodzi właściwego wyglądu ani kontrastu.
Co sprawdzić oprócz obecności row--closed w DOM?|Rzeczywiste reguły i wygląd komórek|Wyłącznie nazwę funkcji|Tylko długość tytułu|Tylko wersję Node|Brak arkusza lub konflikt reguł może pozbawić klasę efektu.
Czy wygenerowanie klas ma wymagać efektu useEffect?|Nie|Tak|Tylko dla compact|Tylko dla selected|Klasy wylicza się bezpośrednio z props podczas renderu.
Czy dynamiczny kolor należy zapisywać do obiektu ticket z props?|Nie|Tak|Tylko dla api|Tylko przy JSX|Prezentacja nie powinna zmieniać danych wejściowych.
Co zrobić, gdy hover nadpisuje tło selected?|Świadomie ustalić selektory i priorytet CSS|Dodać losowe klasy|Zmienić id ticketu|Usunąć wszystkie testy|To konflikt reguł CSS, nie problem clsx.
Czy !important powinno automatycznie naprawiać każdy konflikt?|Nie|Tak|Tylko w React|Tylko w Vite|Najpierw trzeba zrozumieć zasięg i kaskadę reguł.
Która nazwa pasuje do elementu BEM panelu?|panel__title|title.panel jako nazwa klasy|Panel::title|panel/title|Podwójny podkreślnik wskazuje element bloku.
Która nazwa pasuje do modyfikatora BEM?|row--closed|row:closed jako nazwa klasy|row/closed|row.closed jako jeden token|Podwójny myślnik jest konwencją modyfikatora.
Czy TypeScript sam utworzy CSS dla row--selected?|Nie|Tak|Tylko przy boolean|Tylko w strict|Arkusz należy napisać i dołączyć osobno.
Czy brak klasy CSS musi rzucić wyjątek JS?|Nie|Tak|Tylko dla button|Tylko w produkcji|Nieznana klasa zwykle po prostu nie ma pasujących reguł.
Dlaczego klasa zewnętrzna nie gwarantuje nadpisania koloru bazowego?|O wyniku decyduje kaskada|React usuwa wszystkie klasy rodzica|Clsx blokuje kolory|CSSProperties nadaje key|Sam dodatkowy token nie ustala priorytetu reguł.
Czy styl :hover zapewnia obsługę klawiatury przycisku?|Nie, trzeba też uwzględnić fokus|Tak, zawsze|Tylko gdy tło jest niebieskie|Tylko w JSX|Hover i fokus to różne stany.
Czy CSS :disabled zastępuje atrybut disabled?|Nie|Tak|Tylko dla input|Tylko dla button|Selektor opisuje wygląd istniejącego stanu natywnego.
Co jest minimalnym celem testu paska dla zera?|Szerokość 0% i aria-valuenow=0|Ukrycie całej strony|Width 1px|Usunięcie etykiety|Zero jest prawidłowym stanem postępu.
Czy liczba 75 w width bez jednostki oznacza 75%?|Nie, oznacza 75px|Tak|Tylko w progressbar|Tylko w CSSProperties|Procent wymaga napisu z symbolem procenta.
Czy zmienna CSS może zasilić kilka reguł naraz?|Tak|Nie|Tylko jeden border|Tylko w TSX|Ta sama właściwość może sterować kolorem i obramowaniem.
Czy import globalnego CSS w małym komponencie tworzy Shadow DOM?|Nie|Tak|Tylko z clsx|Tylko w Vite|Zwykły import nie daje izolacji drzewa DOM.
Co potwierdza spełnienie lekcji stylowania?|Warunkowe klasy, pasek z danych i widoczny fokus|Tylko zainstalowane clsx|Tylko brak błędów składni|Wyłącznie kolor tła|Kryteria łączą dane, prezentację i obsługę interakcji.
`.trim().split('\n').map(line=>line.split('|'));
quiz.push(
 ['Jaki napis utworzy podane clsx?','row selected','row false selected','selected','row','Fałszywe i puste wartości zostają pominięte.','import clsx from "clsx";\nconsole.log(clsx("row",false,undefined,{selected:true,closed:false}));'],
 ['Czy podane powtórzenie klasy zostanie usunięte?','Nie, wynik to row row','Tak, wynik to row','Wynik to pusty napis','Funkcja rzuci błąd','Clsx składa wartości bez deduplikacji.','import clsx from "clsx";\nconsole.log(clsx("row","row"));']
);
module.exports={exercises,quiz,row,progress,chip,project};
