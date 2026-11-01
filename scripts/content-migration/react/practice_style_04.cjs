const button='import clsx from "clsx";\nimport type {ComponentProps} from "react";\ntype Variant="primary"|"secondary"|"danger";\nconst VARIANTS:Record<Variant,string>={primary:"bg-blue-600 text-white hover:bg-blue-700",secondary:"border border-gray-300 bg-white text-gray-800 hover:bg-gray-50",danger:"bg-red-600 text-white hover:bg-red-700"};\ntype Props=ComponentProps<"button">&{variant?:Variant};\nexport function Button({variant="primary",type="button",className,children,...rest}:Props){return <button {...rest} type={type} className={clsx("rounded-md px-3 py-1.5 text-sm font-medium focus-visible:outline-2 focus-visible:outline-offset-2 disabled:opacity-50",VARIANTS[variant],className)}>{children}</button>;}';
const stat='type Props={label:string;value:number;hint?:string};\nexport function StatCard({label,value,hint}:Props){return <article className="min-w-36 rounded-lg border border-gray-200 bg-white p-4 shadow-sm dark:border-slate-700 dark:bg-slate-800 dark:text-slate-100"><h3 className="text-sm text-gray-600 dark:text-slate-300">{label}</h3><strong className="mt-1 block text-2xl font-semibold">{value}</strong>{hint&&<small className="text-xs text-gray-600 dark:text-slate-300">{hint}</small>}</article>;}';
const panel='import type {ReactNode} from "react";\nexport function Panel({title,actions,children}:{title:string;actions?:ReactNode;children:ReactNode}){return <section className="rounded-card border border-gray-200 bg-surface p-4 dark:border-slate-700 dark:bg-slate-800 dark:text-slate-100"><header className="mb-4 flex items-center justify-between gap-2"><h2 className="text-lg font-semibold">{title}</h2>{actions}</header>{children}</section>;}';
const css='@import "tailwindcss";\n@custom-variant dark (&:where([data-theme=dark], [data-theme=dark] *));\n@theme { --color-brand:#2563eb; --color-surface:#f9fafb; --radius-card:8px; }';
const config='import {defineConfig} from "vite";\nimport react from "@vitejs/plugin-react";\nimport tailwindcss from "@tailwindcss/vite";\nexport default defineConfig({plugins:[react(),tailwindcss()]});';
const project={
 'vite.config.ts':config,'src/index.css':css,'src/Button.tsx':button,'src/Panel.tsx':panel,'src/StatCard.tsx':stat,
 'src/App.tsx':'import "./index.css";\nimport {Button} from "./Button";\nimport {Panel} from "./Panel";\nimport {StatCard} from "./StatCard";\nexport default function App(){return <div className="grid min-h-screen grid-cols-1 bg-white text-slate-900 md:grid-cols-[220px_1fr] dark:bg-slate-900 dark:text-slate-100"><aside className="hidden border-r border-gray-200 p-4 md:block dark:border-slate-700">Filtry</aside><main className="space-y-4 p-4"><h1 className="text-2xl font-semibold">DevDesk</h1><button type="button" className="rounded-md border px-3 py-2 focus-visible:outline-2" onClick={()=>{document.documentElement.dataset.theme=document.documentElement.dataset.theme==="dark"?"light":"dark";}}>Przełącz motyw</button><Panel title="Statystyki" actions={<Button>Nowy</Button>}><div className="grid grid-cols-1 gap-4 md:grid-cols-3"><StatCard label="Otwarte" value={0}/><StatCard label="W toku" value={2}/><StatCard label="Zamknięte" value={3}/></div></Panel></main></div>;}'
};
const exercises=[
 ['Opisz instalację Tailwind 4 w Vite bez instrukcji właściwych wyłącznie wersji 3.','Potrzebne są paczki, plugin i import CSS.','W katalogu projektu: npm install tailwindcss @tailwindcss/vite. W vite.config dodaj import tailwindcss from "@tailwindcss/vite" i plugins:[react(),tailwindcss()]. W arkuszu wejściowym dodaj @import "tailwindcss"; i zaimportuj arkusz w aplikacji.'],
 ['Przygotuj vite.config.ts z React i Tailwind 4.','Nie usuwaj wtyczki React.',config],
 ['Dodaj arkusz wejściowy z Tailwind, tokenami brand/surface/card i motywem atrybutowym.','Użyj dyrektyw wersji 4.','/* index.css */\n'+css],
 ['Zbuduj StatCard z liczbą, etykietą i opcjonalnym hint.','Zero jest widoczne; hint jest tekstem.',stat],
 ['Zbuduj Panel z lokalnymi klasami narzędziowymi i slotem actions.','CSS Modules nie jest potrzebny do tego wariantu.',panel],
 ['Zbuduj mapę trzech wariantów Button z pełnymi klasami.','Nie sklejaj bg z nazwą koloru.',button],
 ['Przekaż disabled i onClick do natywnego button w komponencie Button.','Stan disabled musi być atrybutem, nie tylko klasą.',button],
 ['Dodaj widoczny fokus przycisku dla klawiatury.','Klasy focus-visible opisują stan natywnego elementu.','export function Action(){return <button type="button" className="rounded-md px-3 py-2 focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-blue-600">Akcja</button>;}'],
 ['Napisz sidebar ukryty na małym ekranie i widoczny od md.','Wariant md jest min-width przy domyślnej konfiguracji.','export function Sidebar(){return <aside className="hidden p-4 md:block">Filtry</aside>;}'],
 ['Zbuduj mobile-first grid jednej kolumny, od md 220px + reszta.','W arbitrary value spację zastępuje podkreślnik.','import type {ReactNode} from "react";\nexport function Layout({children}:{children:ReactNode}){return <div className="grid grid-cols-1 md:grid-cols-[220px_1fr]">{children}</div>;}'],
 ['Dodaj rząd trzech statystyk dopiero od md.','Na małym ekranie zostaw jedną kolumnę.','export function Stats(){return <div className="grid grid-cols-1 gap-4 md:grid-cols-3"><p>Otwarte</p><p>W toku</p><p>Zamknięte</p></div>;}'],
 ['Utwórz element o szerokości dokładnie 220px przez arbitrary value.','Pełna klasa ma wystąpić dosłownie.','export function Fixed(){return <div className="w-[220px]">220px</div>;}'],
 ['Napraw dynamiczne bg-${color}-500 przez pełne klasy w mapie.','Ogranicz color do trzech obsługiwanych wartości.','type Color="green"|"red"|"gray";\nconst DOT:Record<Color,string>={green:"bg-green-500",red:"bg-red-500",gray:"bg-gray-400"};\nexport function Dot({color}:{color:Color}){return <span aria-hidden="true" className={`inline-block h-2 w-2 rounded-full ${DOT[color]}`}/>;}'],
 ['Obsłuż dowolny kolor z danych bez generowania dowolnej nazwy klasy Tailwind.','Styl inline może uzupełniać klasy narzędziowe.','export function ColorDot({color}:{color:string}){return <span aria-hidden="true" className="inline-block h-2 w-2 rounded-full" style={{backgroundColor:color}}/>;}'],
 ['Użyj własnego --color-brand w klasie przycisku.','Przestrzeń nazw color udostępnia narzędzia kolorystyczne.','export function Brand(){return <button type="button" className="rounded-md bg-brand px-3 py-2 text-white focus-visible:outline-2">Marka</button>;}\n/* @theme { --color-brand:#2563eb; } */'],
 ['Użyj własnego --radius-card w Panelu.','Token radius mapuje się na rounded.','export function Card(){return <article className="rounded-card border p-4">Karta</article>;}\n/* @theme { --radius-card:8px; } */'],
 ['Dodaj ciemne powierzchnie i ciemny kolor tekstu bez zmieniania danych.','Dark jest wariantem wyglądu.','export function Surface(){return <section className="bg-white p-4 text-slate-900 dark:bg-slate-900 dark:text-slate-100">Treść</section>;}'],
 ['Napisz prosty przycisk ustawiający data-theme w handlerze.','Ten wariant używa jawnego atrybutu, bez zapamiętywania.','export function ThemeButton(){return <button type="button" className="rounded-md border px-3 py-2 focus-visible:outline-2" onClick={()=>{const root=document.documentElement;root.dataset.theme=root.dataset.theme==="dark"?"light":"dark";}}>Przełącz motyw</button>;}'],
 ['Wyjaśnij ograniczenie custom-variant dark opartego wyłącznie na data-theme.','Nie obiecuj automatycznego fallbacku systemowego.','Wariant atrybutowy reaguje na wskazany selektor. Brak data-theme=dark nie uruchamia go na podstawie systemu automatycznie. Jeśli wymagany jest wybór system, trzeba rozstrzygnąć go i ustawić atrybut albo zaprojektować dodatkową politykę CSS/JS.'],
 ['Zdiagnozuj literówkę text-gray-50O z literą O.','ClassName pozostaje tekstem także w TSX.','TypeScript zwykle nie zgłosi tej literówki w zwykłym napisie. Popraw na zamierzoną istniejącą klasę, np. text-gray-500. Podpowiedzi edytora i kontrola wynikowego CSS pomagają, ale nie są pełną gwarancją poprawności.'],
 ['Wyjaśnij różnicę między sortowaniem klas a rozwiązywaniem ich konfliktów.','Prettier i clsx nie są menedżerem kaskady.','Plugin Prettiera porządkuje zapis klas. Clsx składa warunkowe napisy. Żadne z nich samo nie gwarantuje usunięcia konfliktu p-2 z p-4 ani tego, że ostatni token wygra regułę CSS.'],
 ['Wykryj, czemu po dodaniu klas nie pojawiły się żadne style.','Sprawdź całą ścieżkę generowania.','Sprawdź instalację Tailwind 4, plugin w konfiguracji Vite, import tailwindcss w arkuszu i import arkusza do aplikacji. Następnie sprawdź pełną nazwę klasy, zakres wykrywanych źródeł i wynikowy arkusz w DevTools.'],
 ['Sprawdź brak generacji sklejanego wariantu w produkcyjnym buildzie.','Samo istnienie napisu w runtime nie dowodzi obecności CSS.','Użyj koloru nieobecnego w innych pełnych klasach źródeł. Zbuduj projekt, sprawdź brak odpowiedniej reguły i zastąp sklejanie mapą pełnych nazw. Jeśli reguła istnieje z innego źródła, błąd może być przypadkowo maskowany.'],
 ['Zbuduj JSX/JSDoc StatCard z typowaną liczbą.','Typy JS zapisz w komentarzu, klasy pozostają napisami.','/** @param {{label:string,value:number}} props */\nexport function StatCard({label,value}){return <article className="rounded-lg border p-4"><h3 className="text-sm">{label}</h3><strong className="block text-2xl">{value}</strong></article>;}'],
 ['Zaprojektuj test responsywności sidebara po obu stronach md.','Domyślny breakpoint może być zmieniony w projekcie.','Przy niezmienionych breakpointach sprawdź viewport poniżej md i od md. Sidebar hidden md:block ma być odpowiednio ukryty i widoczny. Sprawdź też grid-cols-1 kontra md:grid-cols-[220px_1fr] oraz brak poziomego przewijania.'],
 ['Zaprojektuj macierz wariantów przycisku i disabled.','Trzy warianty i dwa stany dają sześć kombinacji.','Dla primary, secondary i danger sprawdź enabled/disabled: właściwe kolory, natywny disabled, fokus dla dostępnych przycisków i callback tylko przy dozwolonej aktywacji. Zweryfikuj osobno jasny/ciemny wygląd.'],
 ['Porównaj build Tailwind i CSS Modules uczciwie.','Nie zakładaj z góry, że któryś wynik zawsze będzie mniejszy.','Porównaj równoważny widok, produkcyjne buildy i ten sam sposób kompresji. Zapisz rozmiar CSS i JS oraz ograniczenia pomiaru. Różnice treści, konfiguracji i współdzielenia klas wpływają na wynik.'],
 ['Wyjaśnij, dlaczego klasy Tailwind nie zastępują semantycznego HTML.','Styl przycisku nie daje elementowi div zachowania button.','Do akcji użyj button, do nawigacji linku. Klasy kontrolują wygląd, a natywne elementy dają semantykę i obsługę klawiatury. Nie naprawiaj niepoprawnego HTML samym zestawem utility classes.'],
 ['Zaplanuj pracę eksperymentalną bez przypadkowego nadpisania głównej wersji kursu.','Treść zadania może proponować gałąź, ale nie wymaga resetów ani usuwania cudzych zmian.','Zapisz pracę na osobnej gałęzi zgodnie z konwencją repozytorium, porównaj widok i testy. Decyzję o wdrożeniu podejmij na podstawie konwencji zespołu i kosztów utrzymania. Nie usuwaj automatycznie niezapisanej pracy.'],
 ['Oddaj dashboard Tailwind 4: Panel, Button, StatCard, sidebar i motyw atrybutowy.','Pełne klasy muszą być wykrywalne, a plugin musi przetworzyć CSS.','Pliki projektu:\n'+Object.entries(project).map(([file,code])=>`--- ${file} ---\n${code}`).join('\n\n')],
];
const quiz=`
Co opisuje podejście utility-first?|Składanie wyglądu z małych klas|Brak CSS w przeglądarce|Komponenty bez HTML|Wyłącznie inline style|Klasy narzędziowe odpowiadają konkretnym regułom.
Jakie paczki wskazuje instalacja Tailwind 4 z Vite?|tailwindcss i @tailwindcss/vite|Wyłącznie @types/tailwind|Wyłącznie react-dom|tailwind-runtime-react obowiązkowo|Plugin integruje generowanie z Vite.
Jaki import trafia do arkusza wejściowego wersji 4?|@import "tailwindcss"|import React from tailwind|@useState tailwind|@module tailwind|To wejście CSS dla Tailwind 4.
Czy należy zachować plugin React przy dodaniu Tailwind?|Tak|Nie|Tylko w JSX|Tylko w TSX|Wtyczki obsługują różne zadania.
Czy tutorial wersji 3 jest zawsze właściwą instrukcją instalacji wersji 4?|Nie|Tak|Tylko na Windows|Tylko bez CSS|Konfiguracja zmienia się między głównymi wersjami.
Czy samo className="p-4" bez wygenerowanego arkusza daje padding Tailwind?|Nie|Tak|Tylko w TSX|Tylko na div|Klasa potrzebuje odpowiadającej reguły CSS.
Co Tailwind analizuje, by znaleźć klasy?|Tekst źródeł w objętym wykrywaniem zakresie|Wyłącznie wynik każdego renderu runtime|Stan React w przeglądarce|Id użytkowników|Pełne nazwy muszą być wykrywalne w źródłach.
Czy Tailwind ogólnie interpretuje sklejanie bg z kolorem jak JavaScript?|Nie|Tak|Tylko w TSX|Tylko z Vite|Skanowanie nie jest wykonywaniem dowolnego kodu JS.
Jak uniknąć problemu składania prefiksu bg z nazwą koloru?|Użyć mapy pełnych klas|Dodać losowy key|Zmienić typ na any|Wywołać render dwa razy|Mapa zawiera kompletne nazwy w źródle.
Czy klasa sklejona w runtime może przypadkiem działać?|Tak, jeśli reguła powstała z innego źródła|Nie, nigdy|Tylko bez CSS|Tylko dla div|Inna pełna nazwa może maskować błąd generowania.
Jak obsłużyć dowolny kolor z API?|Np. przez inline backgroundColor|Generować dowolne nazwy bg w runtime i zakładać CSS|Zamienić kolor w key|Wyłącznie przez useEffect|Dynamiczna wartość może współpracować z utility classes.
Co daje Record<Variant,string> w mapie Button?|Wymaga wpisów dla wariantów|Waliduje istnienie każdej klasy CSS|Tworzy CSS w runtime|Sprawdza kontrast|Record kontroluje strukturę mapowania TypeScript.
Czy unia wariantu waliduje każdy token klasy?|Nie|Tak|Tylko w strict|Tylko dla danger|ClassName nadal zawiera zwykłe napisy.
Czy TS wykryje text-gray-50O w napisie?|Zwykle nie|Zawsze tak|Tylko w JSX|Tylko z CSSProperties|Litera O może przejść analizę typu string.
Co może dać IntelliSense edytora?|Podpowiedzi i podgląd reguł|Gwarancję wszystkich testów CI|Automatyczne role|Nowy DOM|Wsparcie edytora pomaga pisać klasy.
Do czego służy prettier-plugin-tailwindcss?|Porządkowania zapisu klas|Generowania danych|Autoryzacji|Zastąpienia wszystkich testów|Formatowanie nie dowodzi działania interfejsu.
Czy clsx rozwiązuje konflikty p-2 i p-4?|Nie|Tak|Tylko w Tailwind 4|Tylko dla Button|Clsx składa napisy, nie analizuje reguł utility.
Czy ostatni token w className zawsze wygrywa kaskadę?|Nie|Tak|Tylko w JSX|Tylko dla paddingu|Kolejność reguł CSS nie wynika wprost z kolejności tokenów.
Co oznacza mobile-first?|Baza dla małego ekranu, warianty od breakpointów|Baza wyłącznie dla telefonu|Brak desktopu|Breakpoint zawsze max-width|Prefiksy np. md zwykle zaczynają działać od progu wzwyż.
Czy md:block oznacza tylko dokładną szerokość md?|Nie|Tak|Tylko na Windows|Tylko w TSX|Reguła działa od breakpointu, jeśli nie ograniczono jej dalej.
Czy md jest niezmiennym progiem w każdym projekcie?|Nie|Tak|Tylko w React|Tylko bez CSS|Konfiguracja tokenów breakpointów może go zmienić.
Co opisuje dark: w klasie?|Wariant aktywacji ciemnego wyglądu|Rolę admina|Stan disabled|Każdą ciemną wartość z API|Sposób aktywacji zależy od konfiguracji dark.
Na czym opiera się custom-variant dark z data-theme?|Na pasującym atrybucie lub przodku|Zawsze na localStorage|Na nazwie komponentu|Na typie Theme|Selektor wariantu określa aktywację.
Czy własny atrybutowy dark automatycznie zachowuje systemowy fallback?|Nie należy tego zakładać|Tak zawsze|Tylko z TSX|Tylko dla slate|Trzeba jawnie zaprojektować obsługę systemu.
Jak w Tailwind 4 definiować własne tokeny narzędzi?|Przez @theme|Przez useState|Przez key|Przez props CSSProperties|Theme udostępnia tokeny w przestrzeniach nazw.
Jaki token udostępnia bg-brand?|--color-brand|--space-brand|--radius-brand|--font-brand|Przestrzeń color tworzy klasy kolorystyczne.
Jaki token udostępnia rounded-card?|--radius-card|--color-card|--space-card|--text-card-only|Radius odpowiada za zaokrąglenie.
Czy bg-brand i text-brand mogą korzystać z tego samego tokenu?|Tak|Nie|Tylko w TSX|Tylko dla blue|Różne narzędzia korzystają z przestrzeni color.
Co oznaczają nawiasy w w-[220px]?|Wartość dowolną|Tablicę ReactNode|Id elementu|Typ generyczny|Arbitrary value podaje konkretną wartość CSS.
Po co podkreślnik w grid-cols-[220px_1fr]?|Koduje spację wartości CSS|Nadaje key|Włącza dark|Usuwa pierwszą kolumnę|Wartość docelowa zawiera dwie kolumny oddzielone spacją.
Czy stale powtarzany długi zestaw klas można zamknąć w komponencie?|Tak|Nie|Tylko z CSS Modules|Tylko w JSX|Komponenty nadal służą wielokrotnemu użyciu.
Czy Tailwind zakazuje własnego CSS?|Nie|Tak|Tylko z Vite|Tylko przy @theme|Podejścia mogą współistnieć świadomie.
Czy Tailwind zawsze daje mniejszy CSS niż CSS Modules?|Nie|Tak|Tylko w produkcji|Tylko z React|Rozmiar zależy od zakresu widoku i konfiguracji.
Jak uczciwie porównać rozmiary dwóch podejść?|Równoważne produkcyjne buildy i tę samą kompresję|Dev CSS z minifikowanym buildem|Różne widoki|Wyłącznie liczbę klas JSX|Warunki pomiaru muszą być porównywalne.
Czy klasy disabled:opacity-50 same wyłączają button?|Nie|Tak|Tylko w React 19|Tylko przy danger|Natywny atrybut disabled jest nadal potrzebny.
Czy utility classes zastępują semantykę button?|Nie|Tak|Tylko z focus-visible|Tylko przy onClick|Wygląd nie daje sam zachowania klawiatury.
Czy hover zastępuje focus-visible?|Nie|Tak|Tylko w TSX|Tylko przy niebieskim|Nawigacja klawiaturą wymaga własnego sygnału fokusu.
Czy ukryty sidebar powinien nadal zajmować kolumnę w mobilnym gridzie?|Nie w przedstawionym układzie jednej kolumny|Zawsze tak|Tylko dla admina|Tylko w dark|Layout bazowy ma jedną kolumnę.
Czy value=0 w StatCard powinno pozostać widoczne?|Tak|Nie|Tylko w TSX|Tylko w dark|Zero jest poprawnym wynikiem.
Czy opcjonalny hint:string musi wyświetlać pusty napis jako osobny small?|Nie|Tak|Tylko w JSX|Tylko w dark|Pusty hint może nie tworzyć dodatkowej treści w tym kontrakcie.
Czy focus-visible:outline-2 gwarantuje właściwy kontrast obrysu?|Nie|Tak|Tylko w dark|Tylko z brand|Trzeba dobrać kolor i sprawdzić wynik.
Czy hidden md:block zapewnia całe projektowanie mobilnego menu?|Nie|Tak|Tylko w Vite|Tylko dla aside|Samo ukrycie nie daje alternatywnej nawigacji.
Co sprawdzić po obu stronach breakpointu?|Widoczność sidebara i liczbę kolumn|Wyłącznie długość pliku|Tylko typ props|Wyłącznie nazwę klasy|Responsywność jest wynikiem działania reguł.
Czy można zbudować className mapą zależną od props?|Tak, z pełnych nazw|Nie|Tylko w JSX|Tylko bez TS|Dane wybierają kompletny zestaw klas.
Czy typ string sprawdza poprawność nazwy klasy?|Nie|Tak|Tylko w strict|Tylko przy clsx|String określa rodzaj wartości, nie semantykę utility.
Co sprawdzić, gdy pojedyncza nowa klasa nie działa?|Pisownię, wykrywanie źródeł i wynikowy CSS|Tylko wersję Reacta|Wyłącznie id elementu|Tylko liczbę dzieci|Brak reguły może wynikać z kilku etapów.
Czy tekst klas z zewnętrznej bazy zawsze zostanie zeskanowany przy buildzie?|Nie|Tak|Tylko w TSX|Tylko w React|Build nie zna dowolnych danych pojawiających się później.
Czy mapa wariantów potrzebuje modyfikować props?|Nie|Tak|Tylko dla danger|Tylko w JSX|Wybór klasy jest czystym obliczeniem.
Czy dodanie onClick={handler()} jest poprawnym przekazaniem callbacku?|Nie|Tak|Tylko z Tailwind|Tylko przy Button|Wywołanie następuje podczas renderu; trzeba przekazać funkcję.
Czy type=button warto zachować w ogólnym Button?|Tak jako domyślne|Nie, zawsze submit|Tylko w JSX|Tylko dla danger|Zapobiega przypadkowemu submitowi w formularzu.
Czy rodzic może celowo przekazać type=submit w przygotowanym Button?|Tak|Nie|Tylko bez clsx|Tylko w JSX|Domyślne button nie blokuje jawnej wartości props.
Czy ComponentProps<"button"> pozwala przekazać aria-label?|Tak|Nie|Tylko w dark|Tylko z CSSProperties|Typ odwzorowuje props natywnego przycisku.
Czy dark:bg-slate-800 zmienia dane ticketów?|Nie|Tak|Tylko dla CLOSED|Tylko w JSX|Motyw dotyczy prezentacji.
Czy brak błędów TS dowodzi wygenerowania bg-brand?|Nie|Tak|Tylko w strict|Tylko z Record|Potrzebna jest poprawna konfiguracja CSS.
Czy własny token musi być użyty, aby interesujące utility znalazło się w wyniku?|Trzeba uwzględnić zasady generowania i użycie klas|Wszystkie możliwe klasy są zawsze emitowane|Nigdy nie jest emitowany|React generuje je przy kliknięciu|Weryfikuj faktyczny wynik buildu.
Czy można mierzyć responsywność samym statycznym HTML?|Nie w pełni|Tak zawsze|Tylko bez dark|Tylko w TSX|Działanie media queries wymaga odpowiedniego środowiska renderowania.
Czy formatowanie klas automatycznie naprawia brak pluginu Vite?|Nie|Tak|Tylko z Prettier|Tylko w JSX|To odrębne etapy narzędziowe.
Czy CSS Modules jest nieprofesjonalną alternatywą Tailwinda?|Nie|Tak|Tylko w małych projektach|Tylko dla h2|Oba podejścia mają zastosowania i kompromisy.
Co decyduje o wyborze podejścia w istniejącym projekcie?|Konwencja zespołu i wymagania|Wyłącznie moda|Wyłącznie długość JSX|Zawsze nazwa React|Spójność i koszty utrzymania są istotne.
Co potwierdza ukończenie widoku Tailwind?|Karty, panele, przyciski, responsywność i motyw działają|Samo npm install|Wyłącznie klasy w JSX|Tylko brak błędów TS|Kryteria obejmują wynik, nie sam zapis.
`.trim().split('\n').map(line=>line.split('|'));
const utilities=`
flex|Ustawia display:flex|Ustawia grid|Wyłącza kliknięcia|Zmienia font|To narzędzie układu flex.
grid|Ustawia display:grid|Ustawia flex|Ukrywa element|Ustawia kolor|To narzędzie układu grid.
items-center|Centruje elementy na osi poprzecznej flex|Centruje tekst zawsze|Nadaje auto margin|Zmienia id|Align-items dotyczy wyrównania elementów.
justify-between|Rozsuwa elementy na osi głównej|Zmienia rozmiar fontu|Włącza dark|Ukrywa pierwszy element|Justify-content rozdziela wolne miejsce.
gap-2|Dodaje odstęp ze skali między elementami|Dodaje obramowanie|Ustawia dwie kolumny|Zmienia opacity|Gap dotyczy przerw w układzie.
grid-cols-1|Definiuje jedną kolumnę grid|Definiuje jeden wiersz|Ustawia flex|Ukrywa sidebar|Cols dotyczy kolumn.
md:grid-cols-3|Od md ustawia trzy kolumny|Tylko poniżej md ustawia trzy kolumny|Ustawia trzy wiersze zawsze|Zmienia dane|Prefiks jest wariantem responsywnym.
grid-cols-[220px_1fr]|Definiuje kolumnę 220px i elastyczną|Ustawia 220 kolumn|Ustawia dwa rzędy|Nadaje 220% szerokości|Wartość dowolna opisuje template kolumn.
grid-rows-[56px_1fr]|Definiuje wiersz 56px i elastyczny|Ustawia 56 kolumn|Ukrywa wiersze|Włącza compact|Rows dotyczy wierszy siatki.
min-h-screen|Ustawia minimalną wysokość viewportu|Zawsze ustawia dokładnie 100px|Ogranicza szerokość|Ukrywa overflow|Min-height pozwala treści rosnąć ponad minimum.
hidden|Ustawia display:none|Ustawia opacity:0 bez zmiany układu|Zmienia kolor na biały|Wyłącza jedynie hover|Element przestaje zajmować miejsce w układzie.
md:block|Od md ustawia display:block|Tylko poniżej md ukrywa|Włącza disabled|Ustawia border|Nadpisuje display od progu.
p-4|Padding 1rem przy domyślnej skali|Margin 4px|Padding 4%|Cztery kolumny|Przy niezmienionej skali odstępów p-4 odpowiada 1rem.
px-3|Padding poziomy ze skali|Margin pionowy|Font 3px|Trzy kolumny|Px wybiera lewy i prawy padding.
py-2|Padding pionowy ze skali|Padding tylko prawy|Dwa wiersze|Wysokość 2%|Py wybiera górę i dół.
mt-1|Górny margines ze skali|Górny padding|Grubość fontu|Minimalna szerokość|Mt oznacza margin-top.
mb-4|Dolny margines ze skali|Lewy padding|Obramowanie 4px zawsze|Cztery badge|Mb oznacza margin-bottom.
rounded-lg|Zaokrąglenie ze skali radius|Duży font|Szerokość viewportu|Ciemne tło|Rounded dotyczy promieni rogów.
rounded-full|Maksymalne zaokrąglenie rogów|Pełna szerokość|Pełna wysokość|Wyłączenie border|Przy kwadratowym elemencie daje kształt koła.
rounded-card|Zaokrąglenie z tokenu radius-card|Kolor z color-card|Nowy komponent Card|Grubość tekstu|Własny token rozszerza przestrzeń radius.
border|Dodaje szerokość obramowania|Wybiera wyłącznie kolor tła|Ustawia rolę|Ukrywa element|Wygląd border wymaga też właściwego koloru i stylu.
border-gray-200|Ustawia kolor obramowania|Samodzielnie zmienia status|Ustawia padding 200px|Nadaje tło|Przestrzeń border-kolor wybiera kolor ramki.
shadow-sm|Dodaje cień ze skali|Zmniejsza font|Ustawia key|Zmienia rolę|Shadow opisuje cień elementu.
bg-white|Białe tło|Biały tekst|Biały border wyłącznie|Opacity zero|Bg wybiera background-color.
text-white|Biały kolor tekstu|Białe tło|Wysokość viewportu|Wyłączenie tekstu|Text z nazwą koloru dotyczy foreground.
text-sm|Rozmiar tekstu ze skali|Kolor primary|Ukrycie tekstu|Mały border|Text z nazwą rozmiaru steruje typografią.
text-2xl|Duży rozmiar ze skali typografii|Dwie kolumny|Podwójny border|Kolor warning|Nie oznacza liczby rekordów.
font-semibold|Grubość fontu|Rozmiar elementu|Kolor fontu|Margines fontu|Font-weight opisuje nasycenie pisma.
block|Display:block|Font-weight:bold|Kolor czarny|Disable button|To klasa sposobu wyświetlania.
w-[220px]|Szerokość 220px|Minimalna wysokość 220px|220% szerokości|Dwie kolumny|W podaje width z wartością dowolną.
h-2|Wysokość ze skali odstępów|Dwa nagłówki|Dwie kolumny|Font h2|H steruje height.
w-2|Szerokość ze skali odstępów|Dwa wiersze|Waga fontu|Margines|W steruje width.
hover:bg-gray-50|Tło podczas hover|Stały kolor tekstu|Wyłączenie kliknięcia|Automatyczny fokus|Prefiks ogranicza regułę do stanu hover.
focus-visible:outline-2|Szerokość obrysu przy focus-visible|Tło zawsze|Wyłączenie fokusu|Dwa przyciski|Wariant dotyczy widocznego fokusu.
focus-visible:outline-offset-2|Odsunięcie obrysu przy focus-visible|Wysokość przycisku|Opacity 0.2|Dwie kolumny|Offset nie jest kolorem ani szerokością obrysu.
disabled:opacity-50|Połowa krycia przy disabled|Wyłączenie przez samą nazwę|Stałe 50px szerokości|Zmiana roli|Wariant reaguje na stan disabled.
dark:bg-slate-800|Tło slate-800 przy aktywnym dark|Zawsze ciemny tekst|Status CLOSED|Automatyczny localStorage|Dark zależy od konfiguracji aktywacji motywu.
dark:text-slate-100|Kolor tekstu w dark|Tło w light|Wysokość 100px|Szerokość 100%|Reguła łączy motyw i właściwość koloru tekstu.
bg-brand|Tło z tokenu color-brand|Font z tokenu brand|Klasa generowana przez React|Obramowanie obowiązkowo|Własny token koloru jest źródłem wartości.
bg-surface|Tło z tokenu color-surface|Automatyczny komponent Surface|Wyłączenie dark|Pobranie danych|Nazwa semantyczna nie zmienia mechanizmu utility.
`.trim().split('\n').map(line=>line.split('|'));
for(const [name,a,b,c,d,explanation] of utilities)quiz.push([`Co oznacza klasa ${name} w Tailwind 4 przy opisanej, niezmienionej konfiguracji?`,a,b,c,d,explanation]);
module.exports={exercises,quiz,button,stat,panel,css,config,project};
