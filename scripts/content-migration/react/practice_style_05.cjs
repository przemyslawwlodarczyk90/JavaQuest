// Lekcja przeglądowa: 20 zadań / 60 pytań. API przykładów: styled-components 6.
const card='import styled from "styled-components";\nexport const Card=styled.article<{$highlight:boolean}>`border:1px solid #d1d5db;border-radius:8px;padding:16px;background:${p=>p.$highlight?"#eff6ff":"white"};`;\nconst Value=styled.strong`display:block;font-size:1.5rem;`;\nexport function StatCard({label,value,highlight=false}:{label:string;value:number;highlight?:boolean}){return <Card $highlight={highlight}><h3>{label}</h3><Value>{value}</Value></Card>;}';
const theme='import styled,{ThemeProvider} from "styled-components";\ndeclare module "styled-components"{export interface DefaultTheme{colors:{primary:string;surface:string;text:string};}}\nexport const ThemedCard=styled.section`background:${p=>p.theme.colors.surface};color:${p=>p.theme.colors.text};border:1px solid ${p=>p.theme.colors.primary};padding:16px;`;\nexport function Example(){return <ThemeProvider theme={{colors:{primary:"#2563eb",surface:"#f9fafb",text:"#111827"}}}><ThemedCard>Motyw</ThemedCard></ThemeProvider>;}';
const moduleVersion='import clsx from "clsx";\nimport styles from "./StatCard.module.css";\nexport function StatCard({label,value,highlight=false}:{label:string;value:number;highlight?:boolean}){return <article className={clsx(styles.card,highlight&&styles.highlight)}><h3>{label}</h3><strong className={styles.value}>{value}</strong></article>;}\n/* .card {border:1px solid #d1d5db;border-radius:8px;padding:16px;background:white;} .highlight {background:#eff6ff;} .value {display:block;font-size:1.5rem;} */';
const exercises=[
 ['Przeczytaj tagged template styled.article i wskaż, co powstaje.','To fabryka komponentu ze stylami, nie sam napis.','Styled.article z szablonem tworzy komponent renderujący article z przypisanymi stylami. Interpolowane funkcje mogą odczytać jego props. Komponent deklarujemy na poziomie modułu.'],
 ['Napisz StatCard ze styled-components 6 i opcjonalnym highlight.','Wewnętrzny transient prop ma prefiks $.',card],
 ['Dodaj typ boolean dla wymaganego $highlight wewnętrznego Card.','Opcjonalność publicznego highlight nie oznacza opcjonalności wewnętrznego props.',card],
 ['Sprawdź, że publiczny StatCard bez highlight przekazuje false do Card.','Domyślna wartość jest ustalana przed renderem wewnętrznego komponentu.',card+'\nexport function Example(){return <StatCard label="Otwarte" value={0}/>;}'],
 ['Wykaż błędem TS, że Card nie akceptuje tekstowego $highlight.','Umieść @ts-expect-error nad świadomie błędnym użyciem.',card+'\n// @ts-expect-error Wymagany boolean\nconst invalid=<Card $highlight="yes"/>;'],
 ['Zaplanuj kontrolę transient prop w DOM.','Sprawdź rzeczywisty znacznik, nie tylko JSX.','Wyrenderuj Card z $highlight=true i false. W obu przypadkach HTML nie powinien zawierać atrybutu $highlight. Wariant powinien zmienić regułę tła. Porównaj oba wyniki i style zebrane przez bibliotekę.'],
 ['Przenieś Card z wnętrza StatCard na poziom modułu.','Tożsamość typu komponentu ma być stabilna.',card],
 ['Wyjaśnij ryzyko definiowania styled.button wewnątrz renderu rodzica.','Nowy typ elementu to więcej niż nowa instancja JSX.','Każdy render może utworzyć nowy komponent, co grozi remountem poddrzewa i utratą lokalnego stanu lub fokusu. Definicję przenieś poza funkcję, a zmienne wartości przekazuj przez props.'],
 ['Dodaj ThemeProvider i typ DefaultTheme z trzema kolorami.','Rozszerzenie modułu powinno mieć znany kształt.',theme],
 ['Odczytaj primary, surface i text z p.theme w stylach.','Nie kopiuj kolorów do każdej deklaracji.',theme],
 ['Przepisz StatCard na CSS Modules z zachowaniem publicznych props.','Transient prop zastąp warunkową klasą.',moduleVersion],
 ['Zaplanuj porównanie obu wersji karty bez zależności od wygenerowanych nazw.','Celem jest zachowanie interfejsu i semantyki.','Dla highlight=false/true oraz value=0/3 porównaj treść, article, wygląd tła i typografię. Nie wymagaj identycznych hashy klas. Sprawdź też brak transient props w DOM.'],
 ['Wyjaśnij różnicę runtime CSS-in-JS i ekstrakcji podczas budowania.','Porównuj mechanizm generowania, nie sam język pliku.','Klasyczny runtime CSS-in-JS wykonuje część pracy stylów podczas działania lub renderowania aplikacji. Ekstrakcja build-time tworzy statyczny arkusz podczas budowania. Obie wersje nadal wymagają pobrania i zastosowania CSS przez przeglądarkę.'],
 ['Rozpoznaj plik Card.css.ts w vanilla-extract i opisz eksport style().','Wymagana jest integracja z bundlerem.','Style zapisuje się w TS przez API biblioteki. Integracja wykonuje ekstrakcję do CSS przy buildzie, a eksportowana stała reprezentuje nazwę klasy. Samo rozszerzenie .css.ts bez konfiguracji nie zapewnia takiej transformacji.'],
 ['Zapisz plan migracji stałych stylów Card do tokenów CSS.','Zachowaj mały publiczny interfejs komponentu.','Wyodrębnij surface, border, radius i odstępy do tokenów. Card używa var lub lokalnych klas CSS Modules. Zostaw highlight jako wariant semantyczny. Porównaj oba motywy i usuń nieużywane zależności dopiero po audycie innych komponentów.'],
 ['Wyjaśnij, czy styled-components jest z zasady niezdolne do SSR.','Sprawdź dokumentację danej wersji i frameworka.','Nie. Biblioteka udostępnia obsługę SSR, np. zbieranie stylów. Integracja wymaga właściwej konfiguracji. Nie utożsamiaj SSR z RSC ani nie zakładaj identycznej obsługi we wszystkich wersjach i frameworkach.'],
 ['Zaplanuj pomiar kosztu stylów na dużej liście kart.','Porównaj te same dane i warunki.','Zmierz produkcyjne warianty, liczbę reguł, rozmiary JS/CSS i profil renderowania dla reprezentatywnej listy. Nie ogłaszaj zwycięzcy na podstawie samej długości kodu. Oddziel generowanie stylów od kosztu layoutu i malowania.'],
 ['Zapisz stylowany natywny przycisk z :hover i :focus-visible.','Nie zastępuj semantyki button elementem div.','import styled from "styled-components";\nexport const Button=styled.button`padding:8px 12px;border:1px solid #2563eb;&:hover {background:#eff6ff;}&:focus-visible {outline:2px solid #2563eb;outline-offset:2px;}`;\nexport function Example(){return <Button type="button">Akcja</Button>;}'],
 ['Porównaj rolę ThemeProvider i zmiennych CSS.','Wybór narzędzia wynika z potrzeb i istniejącego projektu.','ThemeProvider przekazuje obiekt JS do komponentów korzystających z mechanizmu biblioteki. Zmienne CSS korzystają z kaskady i dziedziczenia. Oba podejścia mogą współistnieć; samo malowanie powierzchni nie musi wymagać dostarczania motywu w JS do każdego Panelu.'],
 ['Oddaj podsumowanie eksperymentu: karta styled-components i odpowiednik CSS Modules.','Uzasadnij decyzję dla konkretnego projektu, bez twierdzeń o uniwersalnym zwycięzcy.','Zachowaj label, value i highlight w obu wariantach. Sprawdź treść, style i DOM, potem oceń integrację z motywem, SSR/frameworkiem, zależności oraz wyniki pomiarów. W istniejącym projekcie uwzględnij spójność zespołu.'],
];
const quiz=`
Co tworzy styled.div z szablonem?|Komponent ze stylami|Wyłącznie napis klasy|Obiekt User|Hook stanu|Fabryka zwraca stylowany komponent.
Jak nazywa się składnia z funkcją przed backtickami?|Tagged template literal|Zwykły import JSON|Dekorator klasy React|CSS Modules|To mechanizm JavaScriptu użyty przez bibliotekę.
Co może odczytać interpolowana funkcja stylów?|Props stylowanego komponentu|Wyłącznie globalny CSS|Tylko key listy|Wyłącznie DOM id|Funkcja otrzymuje dane props.
Co oznacza $highlight w styled-components 6?|Transient prop stylów|Globalny stan React|Automatyczny atrybut HTML|Token autoryzacji|Prefiks $ filtruje ten prop przy przekazywaniu do DOM.
Czy $highlight powinien trafić do article jako atrybut?|Nie|Tak|Tylko gdy true|Tylko w TSX|Transient prop służy wewnętrznej prezentacji.
Czy zwykłe highlight bez prefiksu zawsze zachowa się identycznie w każdej bibliotece?|Nie|Tak|Tylko w JSX|Tylko z CSS|Zasady filtrowania zależą od narzędzia i konfiguracji.
Gdzie definiować Card=styled.article?|Na poziomie modułu|W każdym renderze rodzica|W map dla każdego rekordu|W handlerze hover|Definicja typu komponentu powinna być stabilna.
Co grozi przy definicji styled wewnątrz renderu?|Nowy typ i remount poddrzewa|Wyłącznie inne odstępy|Brak jakiegokolwiek ryzyka|Automatyczny memo|Tożsamość typu ma znaczenie dla Reacta.
Czy nowy element JSX jest tym samym problemem co nowy typ komponentu?|Nie|Tak|Tylko w TSX|Tylko przy Card|Tworzenie elementów JSX w renderze jest normalne.
Co opisuje styled.article<{$highlight:boolean}>?|Typ props stylowanego komponentu|Rzeczywisty kolor zawsze|Typ dokumentu HTML|Tablicę CSS|Generyk udostępnia kontrakt props dla stylów i użyć.
Czy tekst yes spełnia boolean $highlight?|Nie|Tak|Tylko w JSX|Tylko gdy niepusty|TypeScript rozróżnia string i boolean.
Po co publiczny highlight=false?|Zapewnia domyślny wariant|Zamraża DOM|Wyłącza SSR|Usuwa label|Brak props daje zwykłe tło.
Czy value=0 powinno zniknąć w karcie?|Nie|Tak|Tylko w TSX|Tylko bez highlight|Zero jest poprawną wartością.
Co robi ThemeProvider w zwykłym drzewie klienta?|Dostarcza motyw komponentom korzystającym z kontekstu biblioteki|Automatycznie zmienia wszystkie globalne reguły|Tworzy CSS Modules|Nadaje id|Style mogą odczytywać theme.
Jak odczytać surface z motywu w interpolacji?|p.theme.colors.surface|p.children.surface zawsze|document.key|ReactNode.surface|Kształt odwołania wynika z modelu motywu.
Do czego służy rozszerzenie DefaultTheme?|Typowaniu obiektu motywu|Tworzeniu nowego providera runtime|Nadaniu hashy|Zmianie HTML|Deklaracja modułu opisuje typy.
Czy deklaracja DefaultTheme sama tworzy obiekt motywu?|Nie|Tak|Tylko w TSX|Tylko z SSR|Wartość nadal trzeba przekazać w runtime.
Czy ThemeProvider gwarantuje kontrast kolorów?|Nie|Tak|Tylko dla primary|Tylko w dark|Potrzebna jest kontrola konkretnych wartości.
Co opisuje klasyczny runtime CSS-in-JS?|Część pracy stylów przy działaniu aplikacji|Wyłącznie statyczny CSS bez JS|Brak CSS w przeglądarce|Nowy język HTML|Biblioteka obsługuje style w runtime/renderowaniu.
Gdzie w klasycznej aplikacji klienta można zobaczyć generowane reguły?|W elementach style lub mechanizmie arkuszy zarządzanym przez bibliotekę|Wyłącznie w props.key|Tylko w JSON danych|W loginie użytkownika|DevTools pokazuje wynik zastosowania biblioteki.
Czy liczba znaczników style jest zawsze identyczna w każdej konfiguracji?|Nie|Tak|Tylko w React 19|Tylko dla Card|Strategia wstrzykiwania i środowisko mają znaczenie.
Co robi build-time extraction?|Generuje statyczny CSS podczas budowania|Wyłącza stylowanie|Mutuje props|Wymaga hover state|Ekstrakcja przenosi generowanie stylów do narzędzi budujących.
Które rozszerzenie jest typowe dla vanilla-extract?|.css.ts|.styled.json|.module.jsx|.theme.html|Style zapisuje się w plikach TS przetwarzanych przez integrację.
Co eksportuje style() w użyciu vanilla-extract?|Nazwę klasy wynikowej|Komponent React zawsze|Stan hover|Motyw systemu|Wartość można przypisać do className.
Czy sama nazwa .css.ts wystarczy bez integracji bundlera?|Nie|Tak|Tylko w Vite|Tylko w TSX|Potrzebny jest właściwy etap transformacji.
Czy zero-runtime znaczy zero kosztu CSS w przeglądarce?|Nie|Tak|Tylko w dark|Tylko z TS|Przeglądarka nadal stosuje style, liczy layout i maluje.
Czy CSS Modules wymaga runtime generowania klas przy każdym renderze?|Nie|Tak|Tylko z clsx|Tylko w JSX|Transformacja klas zachodzi w procesie narzędziowym.
Czy Tailwind i CSS Modules są tym samym API?|Nie|Tak|Tylko w Vite|Tylko dla Panelu|Oba mogą emitować CSS przy buildzie, ale zapis i integracja różnią się.
Czy runtime CSS-in-JS z zasady uniemożliwia SSR?|Nie|Tak|Tylko dla styled-components|Tylko dla ThemeProvider|Biblioteki oferują integracje SSR wymagające konfiguracji.
Czy SSR jest tożsame z React Server Components?|Nie|Tak|Tylko w TSX|Tylko przy CSS|To różne mechanizmy i ograniczenia.
Czy działanie biblioteki w jednym frameworku dowodzi identycznej integracji wszędzie?|Nie|Tak|Tylko w React 19|Tylko w Vite|Wersja i środowisko muszą być sprawdzone.
Co zachować przy migracji StatCard do CSS Modules?|Publiczne label/value/highlight i semantykę|Konkretne hashe klas|Wszystkie wewnętrzne props $|Nazwę arkusza style w head|Wewnętrzna technika nie powinna zmieniać kontraktu użytkownika.
Co zastąpi $highlight w module CSS?|Warunkowa klasa highlight|Nowy stan globalny|Losowy key|Id rekordu|Props nadal może sterować wyborem wariantu.
Czy można używać tokenów CSS w stylach biblioteki JS?|Tak|Nie|Tylko w JSX|Tylko bez ThemeProvider|Zwykłe wartości var mogą współpracować z generowanym CSS.
Czy ThemeProvider i tokeny CSS mogą współistnieć?|Tak|Nie|Tylko dla primary|Tylko w SSR|Warstwy mogą mieć różne odpowiedzialności.
Co jest lepszym testem migracji niż konkretny hash?|Treść, semantyka i wygląd wariantów|Nazwa wygenerowanego sufiksu|Wyłącznie liczba importów|Tylko długość pliku|Test powinien sprawdzać wymagane zachowanie.
Ile kombinacji dają highlight false/true i value 0/3?|4|2|3|8|Dwa niezależne zestawy po dwie wartości dają cztery przypadki.
Czy test HTML sam mierzy koszt runtime stylów?|Nie|Tak|Tylko z SSR|Tylko dla Card|Pomiar wydajności wymaga właściwego środowiska i metryk.
Jak porównywać wydajność dwóch podejść?|Na równoważnych produkcyjnych scenariuszach|Tylko po długości JSX|Na innych danych|Wyłącznie po popularności|Warunki pomiaru muszą być porównywalne.
Czy sama biblioteka zawsze jest największym kosztem dużej listy?|Nie|Tak|Tylko w TSX|Tylko z theme|Render, layout, obrazy i dane też wpływają na koszt.
Czy wybierać podejście wyłącznie na podstawie trendu?|Nie|Tak|Tylko w nowym projekcie|Tylko dla CSS|Ważne są wymagania, integracja i utrzymanie.
Co uwzględnić w istniejącym projekcie przed zmianą biblioteki?|Spójność i koszt migracji|Wyłącznie nowość API|Tylko rozmiar hasha|Tylko nazwę firmy|Migracja może kosztować więcej niż korzyść.
Czy stylowany button nadal potrzebuje type=button dla zwykłej akcji?|Tak|Nie|Tylko w CSS Modules|Tylko bez onClick|Stylowanie nie zmienia domyślnej semantyki formularza.
Czy &:hover w szablonie zastępuje potrzebę useState dla samej zmiany tła?|Tak|Nie|Tylko w TSX|Tylko bez ThemeProvider|Reguła CSS obsługuje prostą prezentację hover.
Czy &:focus-visible nadal trzeba zaprojektować świadomie?|Tak|Nie|Tylko w CSS Modules|Tylko w dark|Biblioteka nie dobiera automatycznie czytelnego fokusu.
Czy stylowany div sam staje się przyciskiem?|Nie|Tak|Tylko z onClick|Tylko z border|Wygląd i click handler nie zastępują semantyki natywnej kontrolki.
Czy className trzeba przekazać dalej, gdy styluje się własny komponent?|Tak, aby klasa mogła trafić do właściwego DOM|Nie, zawsze dzieje się samo|Tylko w JSX|Tylko dla Card|Własny komponent musi obsłużyć kontrakt biblioteki.
Czy TypeScript wykonuje walidację wszystkich interpolowanych kolorów runtime?|Nie|Tak|Tylko dla string|Tylko w strict|Typ string nie jest walidatorem poprawnego koloru.
Czy transient props są uniwersalną zasadą każdej biblioteki CSS-in-JS?|Nie|Tak|Tylko w JSX|Tylko w SSR|Konkretna konwencja $ jest częścią API styled-components.
Czy literówka $higlight musi być równie łatwa do wykrycia w nieotypowanym JSX?|Nie|Tak|Tylko w dark|Tylko dla Card|Typowane props TSX dają dodatkową analizę nazw.
Co należy sprawdzić w DOM przy $highlight?|Brak tego atrybutu na elemencie|Obecność id highlight|Zawsze nowy article|Tylko liczbę dzieci|Transient prop ma pozostać szczegółem komponentu stylów.
Czy filtracja props zastępuje test poprawnego tła?|Nie|Tak|Tylko w TSX|Tylko w dark|Brak atrybutu i właściwy wygląd są osobnymi warunkami.
Czy dynamiczny inline width można zostawić po migracji do modułów?|Tak|Nie|Tylko przy value=0|Tylko w JSX|Dynamiczne dane mogą nadal wymagać stylu inline.
Czy usunąć styled-components z projektu po migracji jednej karty bez audytu?|Nie|Tak|Tylko w Vite|Tylko w TSX|Inne komponenty mogą nadal zależeć od biblioteki.
Co zrobić przed usunięciem zależności?|Sprawdzić wszystkie importy i użycia|Zmienić key|Usunąć dane|Wyłączyć lint|Audyt zapobiega uszkodzeniu innych widoków.
Czy generowanie stylów przy buildzie wymaga narzędzi w pipeline?|Tak|Nie|Tylko w JSX|Tylko w dark|Transformacja musi być zintegrowana z budowaniem.
Czy nazwa zero-runtime oznacza brak JavaScriptu w całej aplikacji React?|Nie|Tak|Tylko w TSX|Tylko z Vite|Określenie dotyczy odpowiedniej warstwy generowania stylów.
Czy poprawny wynik tsc dowodzi zgodności SSR z frameworkiem?|Nie|Tak|Tylko w strict|Tylko przy ThemeProvider|Integrację trzeba sprawdzić w danym środowisku.
Co powinno zawierać uzasadnienie wyboru stylowania?|Wymagania, kompromisy i wyniki sprawdzeń|Wyłącznie nazwę najnowszej biblioteki|Tylko liczbę klas|Tylko trend|Decyzja ma wynikać z konkretnego projektu.
Co potwierdza opanowanie lekcji przeglądowej?|Odczyt stylów, migracja karty i rozumienie miejsca generacji CSS|Tylko instalacja pakietu|Wyłącznie znajomość nazwy|Tylko ciemne tło|Lekcja uczy orientacji i praktycznego rozpoznawania mechanizmów.
`.trim().split('\n').map(line=>line.split('|'));
module.exports={exercises,quiz,card,theme,moduleVersion};
