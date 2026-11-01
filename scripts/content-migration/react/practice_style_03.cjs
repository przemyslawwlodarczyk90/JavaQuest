const dark='--bg:#0f172a;--surface:#1e293b;--text:#e5e7eb;--text-muted:#94a3b8;--border:#334155;--color-primary:#60a5fa;';
const tokens=':root { --color-primary:#2563eb;--color-danger:#dc2626;--color-warning:#d97706;--color-success:#16a34a;--bg:#ffffff;--surface:#f9fafb;--text:#111827;--text-muted:#6b7280;--border:#e5e7eb;--space-1:4px;--space-2:8px;--space-3:12px;--space-4:16px;--space-6:24px;--space-8:32px;--radius:8px;--font-sm:0.875rem;--font-md:1rem;--font-lg:1.5rem;--line-height:1.5;--shadow-1:0 1px 3px rgb(0 0 0 / 0.12);--duration-fast:120ms;color-scheme:light; }\n:root[data-theme="dark"] {'+dark+'color-scheme:dark;}\n@media (prefers-color-scheme:dark) { :root:not([data-theme]) {'+dark+'color-scheme:dark;} }\nbody {background:var(--bg);color:var(--text);font-family:system-ui,sans-serif;line-height:var(--line-height);margin:0;}';
const helpers='export type Theme="light"|"dark";\nexport type ThemeChoice=Theme|"system";\nexport function resolveTheme(choice:ThemeChoice,systemDark:boolean):Theme{return choice==="system"?(systemDark?"dark":"light"):choice;}\nexport function nextTheme(theme:Theme):Theme{return theme==="light"?"dark":"light";}\nexport function applyChoice(root:HTMLElement,choice:ThemeChoice):void{if(choice==="system")delete root.dataset.theme;else root.dataset.theme=choice;}';
const toggle='import {useState} from "react";\ntype Theme="light"|"dark";\nexport function ThemeToggle({initialTheme}:{initialTheme:Theme}){const [theme,setTheme]=useState<Theme>(initialTheme);function toggle(){const next:Theme=theme==="light"?"dark":"light";document.documentElement.dataset.theme=next;setTheme(next);}return <button type="button" onClick={toggle} aria-label="Przełącz motyw">{theme==="light"?"Ciemny":"Jasny"}</button>;}';
const panelCss='.panel { background:var(--surface);color:var(--text);border:1px solid var(--border);border-radius:var(--radius);padding:var(--space-4);box-shadow:var(--shadow-1); }\n.title {font-size:var(--font-lg);margin:0 0 var(--space-2);}\n.muted {color:var(--text-muted);font-size:var(--font-sm);}\nbutton {padding:var(--space-2) var(--space-4);border:1px solid var(--border);border-radius:var(--radius);background:var(--surface);color:var(--text);}\nbutton:focus-visible {outline:2px solid var(--color-primary);outline-offset:2px;}';
const project={
 'src/styles/tokens.css':tokens,
 'src/ThemeToggle.tsx':toggle,
 'src/Panel.module.css':panelCss,
 'src/App.tsx':'import styles from "./Panel.module.css";\nimport {ThemeToggle} from "./ThemeToggle";\nexport default function App({initialTheme}:{initialTheme:"light"|"dark"}){return <main className={styles.panel}><h1 className={styles.title}>DevDesk</h1><p className={styles.muted}>Tokeny i motyw</p><ThemeToggle initialTheme={initialTheme}/></main>;}',
 'src/main.tsx':'import "./styles/tokens.css";\nimport {createRoot} from "react-dom/client";\nimport App from "./App";\n// Prosta wersja: system wybiera motyw raz przy starcie, kliknięcia dają wybór jawny.\nconst initialTheme=window.matchMedia("(prefers-color-scheme: dark)").matches?"dark":"light";\ndocument.documentElement.dataset.theme=initialTheme;\nconst container=document.getElementById("root");if(!container)throw Error("Brak root");createRoot(container).render(<App initialTheme={initialTheme}/>);'
};
const exercises=[
 ['Zapisz centralny tokens.css z kolorami, powierzchniami, tekstem, obramowaniem i skalą odstępów.','Tokeny odzwierciedlają decyzje projektowe.','/* tokens.css */\n'+tokens],
 ['Zastąp surowe wartości Panelu przez tokeny surface, border, radius i space-4.','Komponent nie musi znać nazwy aktualnego motywu.','/* Panel.module.css */\n'+panelCss],
 ['Wybierz semantyczne nazwy tokenów dla błędu i tła powierzchni. Wyjaśnij rolę tokenów palety.','Semantyka i surowa paleta mogą współistnieć.','Komponent używa --color-danger i --surface. Można mieć też bazowe --red-600 jako paletę, a --color-danger powiązać z właściwym odcieniem. Zmiana znaczenia lub motywu nie wymaga zmiany nazw w każdym komponencie.'],
 ['Zbuduj skalę odstępów 4,8,12,16,24,32px i użyj dwóch tokenów w padding.','Wybór ze skali zwiększa spójność.','/* :root { --space-1:4px;--space-2:8px;--space-3:12px;--space-4:16px;--space-6:24px;--space-8:32px; } .panel {padding:var(--space-2) var(--space-4);} */'],
 ['Dodaj tokeny font-sm/md/lg i bezwymiarową wysokość linii.','Line-height może pozostać mnożnikiem.','/* :root {--font-sm:0.875rem;--font-md:1rem;--font-lg:1.5rem;--line-height:1.5;} body {font-size:var(--font-md);line-height:var(--line-height);} */'],
 ['Dodaj token radius, shadow-1 oraz duration-fast.','Tokeny nie ograniczają się do kolorów.','/* :root {--radius:8px;--shadow-1:0 1px 3px rgb(0 0 0 / 0.12);--duration-fast:120ms;} .panel {border-radius:var(--radius);box-shadow:var(--shadow-1);} */'],
 ['Nadpisz tokeny ciemnego motywu na html[data-theme="dark"].','Nie zmieniaj selektorów każdego komponentu.','/* :root[data-theme="dark"] {'+dark+'} */'],
 ['Dodaj systemowy tryb ciemny wyłącznie przy braku jawnego atrybutu motywu.','Jawny light i dark mają pierwszeństwo nad systemem.','/* @media (prefers-color-scheme:dark) { :root:not([data-theme]) {'+dark+'} } */'],
 ['Rozpisz macierz brak/light/dark × jasny/ciemny system.','Brak wyboru oznacza system; jawny wybór wygrywa.','Brak+jasny→light, brak+ciemny→dark. Light+jasny→light i light+ciemny→light. Dark+jasny→dark i dark+ciemny→dark.'],
 ['Napisz czystą funkcję resolveTheme dla light/dark/system i preferencji systemowej boolean.','Funkcja nie czyta document ani window.',helpers],
 ['Napisz nextTheme ograniczone do unii light/dark.','Nie akceptuj dowolnego napisu.',helpers],
 ['Zaimplementuj applyChoice: light/dark ustawiają dataset, system usuwa atrybut.','Własność dataset.theme odpowiada data-theme.',helpers],
 ['Napisz kontrolowany wybór trzech opcji bez własnego stanu.','Rodzic otrzymuje semantyczny wybór, CSS może obsługiwać system.','type ThemeChoice="light"|"dark"|"system";\nexport function ThemeChoices({value,onChange}:{value:ThemeChoice;onChange:(choice:ThemeChoice)=>void}){const choices:readonly ThemeChoice[]=["light","dark","system"];return <div role="group" aria-label="Motyw">{choices.map(choice=><button key={choice} type="button" aria-pressed={value===choice} onClick={()=>onChange(choice)}>{choice}</button>)}</div>;}'],
 ['Napisz prosty ThemeToggle z początkowym motywem przekazanym przez bootstrap.','Ten wariant synchronizuje dwa stany przy starcie i po kliknięciu.',toggle],
 ['Przygotuj bootstrap: odczyt systemu raz, zgodny atrybut html i initialTheme.','Kod startowy działa w przeglądarce przed renderem. Nie obiecuj dalszego śledzenia systemu.',project['src/main.tsx']],
 ['Wyjaśnij ryzyko useState("light") przy starcie interfejsu w ciemnym motywie systemowym.','Etykieta i CSS mogą opisywać dwa różne stany.','CSS może już wyświetlać dark, a stan React pozostaje light. Pierwsze kliknięcie może ustawić dark zamiast przełączyć na light. Ustal zgodną wartość początkową albo modeluj wybór system oddzielnie od motywu wynikowego.'],
 ['Wyjaśnij ograniczenie wersji wybierającej system tylko przy bootstrapie.','To poprawny jawnie ograniczony wariant, nie pełny menedżer motywów.','Zmiana systemu po starcie nie aktualizuje automatycznie stanu React. Gdy bootstrap ustawi jawny atrybut, CSS też nie śledzi już systemu. Pełne śledzenie i synchronizacja należą do późniejszej lekcji o efektach.'],
 ['Umieść zapis document.dataset w dozwolonym miejscu prostego przełącznika.','Render i funkcje aktualizujące stan mają być czyste.',toggle],
 ['Wykaż błędem TS, że Theme nie akceptuje blue.','Nie rzutuj błędnej wartości na poprawny typ.','type Theme="light"|"dark";\nconst valid:Theme="dark";\n// @ts-expect-error Blue nie należy do unii\nconst invalid:Theme="blue";'],
 ['Zapisz JSX/JSDoc kontrolowany przycisk motywu.','Typy JS mogą pochodzić z komentarza.','/** @param {{theme:"light"|"dark",onToggle:()=>void}} props */\nexport function ThemeButton({theme,onToggle}){return <button type="button" onClick={onToggle}>{theme==="light"?"Ciemny":"Jasny"}</button>;}'],
 ['Dodaj fallback do --border przez var.','Fallback pomaga przy brakującej zmiennej, nie jest oceną kontrastu.','/* .panel { border:1px solid var(--border,#d1d5db); } */'],
 ['Zdiagnozuj --surface:20px użyte jako background.','Istniejąca zmienna może mieć wartość niepasującą do miejsca użycia.','Podstawienie 20px nie jest poprawną wartością tła koloru. Fallback var dla istniejącej zmiennej nie musi uratować reguły. Popraw definicję tokenu i sprawdź computed style.'],
 ['Zbuduj lokalny kompaktowy zakres tokenu odstępu.','Zmienne mogą dziedziczyć się w poddrzewie.','/* .compact { --space-4:8px; } .panel { padding:var(--space-4); } */\n// Umieść panel wewnątrz elementu compact.'],
 ['Przygotuj plan audytu kontrastu text-muted na surface w obu motywach.','Sama zamiana kolorów nie dowodzi czytelności.','Dla light i dark odczytaj wynikowe kolory text-muted i surface, sprawdź kontrast odpowiednim narzędziem dla rozmiaru i rodzaju tekstu. Popraw tokeny, jeśli wynik jest niewystarczający. Sprawdź także focus, border oraz badge.'],
 ['Zastąp warunki theme w każdym Panelu wspólnymi zmiennymi.','React nie musi rozsyłać motywu, jeśli służy on tylko wyglądowi.','Usuń ternary wybierające kolory w Panelu. Użyj background:var(--surface), color:var(--text), border-color:var(--border). Zmieniaj wartości tokenów na przodku; przeglądarka przelicza style.'],
 ['Wskaż, kiedy motyw może być potrzebny także w JavaScripcie.','Biblioteka może nie przyjmować var jako koloru.','Np. wykres canvas wymaga rzeczywistych wartości koloru. Potrzebna jest wtedy warstwa dostarczająca je bibliotece i aktualizująca przy zmianie motywu. Same dekoracyjne Panele nadal mogą używać CSS.'],
 ['Dodaj color-scheme zgodne z aktywnym motywem.','Natywne kontrolki powinny współgrać z własnymi powierzchniami.','/* :root {color-scheme:light;} :root[data-theme="dark"] {color-scheme:dark;} @media(prefers-color-scheme:dark){:root:not([data-theme]){color-scheme:dark;}} */'],
 ['Zaprojektuj test czystości renderu ThemeToggle.','W czasie renderu nie powinien zmieniać html.','Zapisz początkowy dataset.theme, wyrenderuj komponent bez kliknięcia i potwierdź brak zapisu. Następnie kliknij, sprawdź jeden zapis next i zgodną etykietę po ponownym renderze. Nie umieszczaj zapisu DOM w updaterze setTheme.'],
 ['Zaplanuj audyt surowych kolorów w modułach po migracji.','Rozróżnij definicje tokenów i ich użycie.','Wyszukaj hex, rgb i hsl w module.css. Przenieś decyzje kolorystyczne do tokens.css i zastąp je var. Nie usuwaj mechanicznie liczb niezwiązanych z tokenami, np. liczby kolumn. Sprawdź oba motywy po zmianie.'],
 ['Oddaj spójną miniaplikację z tokenami, modułem Panelu i przełącznikiem.','Wariant wybiera system raz przy starcie; zapis wyboru po odświeżeniu nie jest jeszcze wymagany.','Pliki projektu:\n'+Object.entries(project).map(([file,code])=>`--- ${file} ---\n${code}`).join('\n\n')],
];
const quiz=`
Co jest tokenem projektowym?|Nazwana decyzja wyglądu|Token autoryzacji|Id komponentu|Key listy|Token opisuje powtarzalną wartość projektu.
Która nazwa ma znaczenie semantyczne?|--color-danger|--hex-dc2626|--red-number-1|--rgb-220|Nazwa mówi o roli koloru.
Czy tokeny palety i semantyczne mogą współistnieć?|Tak|Nie|Tylko w JSX|Tylko bez motywów|Semantyka może odwoływać się do bazowej palety.
Gdzie zwykle umieszcza się domyślne tokeny aplikacji?|Na :root|Na każdym td osobno|W key|W aria-label|Korzeń dostarcza dziedziczone wartości.
Jak odczytać token surface?|var(--surface)|props.surface w CSS|token(surface)|style.surface()|Var odczytuje właściwość własną CSS.
Jak rozpoczyna się nazwa własnej właściwości CSS?|Od --|Od $$|Od @|Od #|Dwa myślniki odróżniają custom property.
Czy nazwy własnych właściwości są wrażliwe na wielkość liter?|Tak|Nie|Tylko w TSX|Tylko dla kolorów|--Surface i --surface są różnymi nazwami.
Jak nazwać tło głównej strony w modelu lekcji?|--bg|--space-4|--radius|--duration-fast|Token bg odpowiada za tło aplikacji.
Jak nazwać tło paneli w modelu lekcji?|--surface|--text|--font-sm|--border-width-only|Surface opisuje powierzchnię komponentów.
Jak nazwać mniej wyróżniony tekst?|--text-muted|--surface|--bg|--space-1|Muted określa rolę tekstu.
Czy token może opisywać czas animacji?|Tak|Nie|Tylko z JS|Tylko z Context|Duration-fast jest decyzją projektową.
Czy token może opisywać cień?|Tak|Nie|Tylko globalny reset|Tylko w JSX|Shadow jest powtarzalną decyzją wyglądu.
Co daje skala odstępów?|Spójny ograniczony zestaw wartości|Losowe odstępy|Automatyczny układ każdej strony|Brak potrzeby CSS|Komponenty wybierają ze wspólnego słownika.
Ile wynosi space-4 w podanej skali?|16px|4px|12px|24px|Skala przypisuje space-4 do szesnastu pikseli.
Ile wynosi space-6 w podanej skali?|24px|6px|16px|32px|Nazwa jest krokiem skali, nie dosłowną liczbą pikseli.
Czy line-height:1.5 musi mieć px?|Nie|Tak|Tylko w dark|Tylko w modułach|Może być bezwymiarowym mnożnikiem.
Co zmienia motyw oparty na tokenach?|Wartości wspólnych zmiennych|Nazwy wszystkich komponentów|Id użytkowników|Klucze danych|Komponenty zachowują te same odwołania var.
Czy Panel musi otrzymywać theme, jeśli motyw dotyczy tylko CSS?|Nie|Tak|Tylko w TSX|Tylko bez modules|Dziedziczenie zmiennych może wystarczyć.
Jaki atrybut oznacza jawny wybór ciemnego motywu?|data-theme="dark"|key="dark"|role="dark"|aria-theme="dark"|Selektor arkusza odnosi się do data-theme.
Co oznacza dataset.theme?|Właściwość odpowiadającą data-theme|Stan React automatycznie|Id tokenu|Globalny Context|Dataset jest API atrybutów data-*.
Jak wrócić do systemu przy selektorze braku atrybutu?|Usunąć data-theme|Ustawić dark|Ustawić dowolny tekst|Dodać key|Brak jawnego wyboru uruchamia regułę systemową.
Czy data-theme="" jest brakiem atrybutu?|Nie|Tak|Tylko w JSX|Tylko w TSX|Pusty atrybut nadal istnieje i nie pasuje do :not([data-theme]).
Co wykrywa prefers-color-scheme?|Preferencję jasnego lub ciemnego wyglądu|Rolę użytkownika|Stan połączenia|Kolor ostatniego kliknięcia|To cecha mediów związana z preferencją wyglądu.
Kiedy działa reguła :root:not([data-theme])?|Gdy korzeń nie ma atrybutu data-theme|Gdy ma dowolny dark|Zawsze|Tylko w JSX|Selektor sprawdza nieobecność atrybutu.
Czy jawny light powinien wygrywać z ciemnym systemem?|Tak|Nie|Tylko przy adminie|Tylko w TSX|Preferencja użytkownika ma pierwszeństwo w przyjętym modelu.
Czy jawny dark powinien działać przy jasnym systemie?|Tak|Nie|Tylko po restarcie|Tylko bez CSS|Wybór jawny nie zależy od systemowego fallbacku.
Czy brak wyboru i ciemny system daje dark?|Tak|Nie|Tylko po kliknięciu|Tylko z Context|Media query stosuje ciemne tokeny.
Czy zmiana samych tokenów CSS wymaga renderu wszystkich Paneli React?|Nie|Tak|Tylko z modules|Tylko w przeglądarce|Przeglądarka przelicza style niezależnie od renderowania React.
Czy przełącznik z useState może sam się ponownie wyrenderować?|Tak|Nie|Tylko bez CSS|Tylko z Context|Setter aktualizuje stan komponentu.
Co opisuje Theme="light" lub "dark"?|Motyw wynikowy|Preferencję system bez rozstrzygnięcia|Kolor tekstu wyłącznie|Stan przycisku disabled|To dwa konkretne warianty wyglądu.
Co dodaje ThemeChoice z system?|Możliwość wybrania automatycznej preferencji|Nowy kolor niebieski|Autoryzację|Nową rolę|Wybór system różni się od konkretnego efektu light/dark.
Dlaczego jawny useState<Theme> jest użyteczny?|Ogranicza późniejsze wartości|Automatycznie zapisuje CSS|Śledzi system bez kodu|Zamraża DOM|Generyk opisuje zamkniętą unię stanu.
Czy useState("light") samo gwarantuje unię dwóch motywów?|Nie|Tak|Tylko w strict|Tylko z Vite|Wnioskowanie stanu tekstowego zwykle daje string.
Gdzie wolno zapisać atrybut html w prostym toggle?|W handlerze zdarzenia|W każdym renderze|W czystym resolverze|W obiekcie types|Handler jest odpowiednim miejscem reakcji na kliknięcie.
Czy updater setTheme powinien zmieniać document?|Nie|Tak|Tylko w dark|Tylko w JSX|Updater ma być czysty i może być wywoływany kontrolnie więcej niż raz.
Dlaczego nie startować bezmyślnie z light w ciemnym interfejsie?|Stan i etykieta mogą nie zgadzać się z CSS|Light nie jest poprawnym typem|CSS zabrania ciemnego|React automatycznie rzuci wyjątek|Pierwsze kliknięcie może nie dać oczekiwanej zmiany.
Co zapewnia initialTheme zgodne z bootstrapem?|Spójny start etykiety i atrybutu|Wieczne śledzenie systemu|Zapis localStorage|Automatyczne testy kontrastu|Wersja startowa rozwiązuje początkową zgodność.
Czy bootstrap odczytujący system raz śledzi późniejsze zmiany?|Nie|Tak|Tylko w TSX|Tylko dla dark|Dalsza synchronizacja wymaga dodatkowego mechanizmu.
Czy lekcja wymaga już localStorage?|Nie|Tak|Tylko w JSX|Tylko dla light|Zapamiętywanie wyboru jest odłożone do efektów.
Czy CSS zmienne mogą mieć lokalny zakres w poddrzewie?|Tak|Nie|Tylko na html|Tylko z Context|Nadpisana wartość dziedziczy się do potomków.
Co może robić klasa compact z tokenami?|Zmniejszać skalę odstępów poddrzewa|Zmieniać role użytkowników|Usuwać style|Dodawać id|Gęstość interfejsu to inne zastosowanie tokenów.
Czy CSS Modules blokuje dziedziczenie tokenów?|Nie|Tak|Tylko przy dark|Tylko w TSX|Lokalizacja klas nie usuwa dziedziczenia custom properties.
Jak zapisać fallback dla border?|var(--border,#d1d5db)|var(border) default|border ?? #d1d5db|fallback(--border) wyłącznie|Drugi argument var jest wartością awaryjną.
Czy fallback var zawsze ratuje istniejącą zmienną z niepasującą wartością?|Nie|Tak|Tylko dla background|Tylko w TSX|Zmienna może istnieć, lecz dawać niepoprawną wartość docelowej właściwości.
Czy --surface:20px pasuje jako kolor background?|Nie|Tak|Tylko w dark|Tylko w modules|Długość nie jest kolorem tła.
Czy samo użycie semantycznych nazw gwarantuje kontrast?|Nie|Tak|Tylko w dark|Tylko dla muted|Trzeba sprawdzić konkretne wartości i kontekst.
Co sprawdzić dla muted na surface?|Kontrast wynikowych kolorów|Tylko nazwy zmiennych|Wyłącznie liczbę tokenów|Tylko wersję TS|Semantyka nazwy nie dowodzi czytelności.
Czy kontrast sprawdza się w obu motywach?|Tak|Nie|Tylko w light|Tylko przy braku CSS|Pary kolorów zmieniają się między motywami.
Czy sam kolor powinien przekazywać błąd?|Nie|Tak|Tylko czerwony|Tylko w dark|Potrzebny jest też tekst lub inny czytelny sygnał.
Po co color-scheme przy motywie?|Pomaga dopasować natywne kontrolki|Tworzy tokeny automatycznie|Zastępuje wszystkie kolory|Tworzy Context|Przeglądarka może używać odpowiedniego wyglądu własnych elementów.
Czy color-scheme zastępuje definicję surface i text?|Nie|Tak|Tylko w Chrome|Tylko w light|Własne powierzchnie nadal wymagają reguł.
Kiedy JS może potrzebować rzeczywistych kolorów?|Gdy biblioteka canvas wymaga wartości kolorów|Zawsze dla każdego h2|Wyłącznie dla zwykłego Panelu|Nigdy|Nie każda biblioteka rozumie odwołania var.
Czy Context jest obowiązkowy do prostego jasny/ciemny w CSS?|Nie|Tak|Tylko z React|Tylko w TSX|Do samego wyglądu często wystarczy atrybut i zmienne.
Czy zmiana tokenu primary wymaga edycji wszystkich komponentów?|Nie, jeśli używają tokenu|Tak|Tylko w JSX|Tylko w dark|Wspólny słownik centralizuje decyzję.
Czy każda liczba w module CSS musi być tokenem?|Nie|Tak|Tylko w TSX|Tylko przy grid|Tokenizuje się decyzje projektowe, nie mechanicznie całą składnię.
Co audytować po migracji kolorów?|Surowe kolory pozostawione w modułach|Tylko nazwy komponentów|Wyłącznie id ticketów|Tylko liczbę importów|Hex/rgb/hsl mogą wskazać pominięte decyzje.
Czy definicje tokenów mogą zawierać surowe kolory?|Tak|Nie|Tylko bez motywów|Tylko z JS|Wspólny słownik musi gdzieś przypisywać konkretne wartości.
Czy test HTML sam dowodzi kontrastu w przeglądarce?|Nie|Tak|Tylko w dark|Tylko dla muted|Potrzebne są wynikowe style i odpowiednia analiza.
Czy kontrolowany wybór motywu musi mieć własny useState?|Nie|Tak|Tylko w JSX|Tylko dla system|Może otrzymywać value i onChange od rodzica.
Co jest celem tokenów i motywów w tej lekcji?|Spójny wygląd i poprawne pierwszeństwo wyboru|Tylko ciemne tło|Wyłącznie nowy hook|Automatyczna autoryzacja|Ważne są nazwy, wartości, zakres i działanie wyboru.
`.trim().split('\n').map(line=>line.split('|'));
const codeCases=[
 ['Motyw jasny systemu','resolveTheme("system",false)','light','dark','system','undefined','System jasny rozstrzyga wybór automatyczny.'],
 ['Motyw ciemny systemu','resolveTheme("system",true)','dark','light','system','null','System ciemny wybiera dark.'],
 ['Jawny light przy ciemnym systemie','resolveTheme("light",true)','light','dark','system','undefined','Wybór jawny wygrywa.'],
 ['Jawny dark przy jasnym systemie','resolveTheme("dark",false)','dark','light','system','undefined','Wybór jawny nie potrzebuje systemu.'],
 ['Jawny light przy jasnym systemie','resolveTheme("light",false)','light','dark','system','null','Jawny light pozostaje light.'],
 ['Jawny dark przy ciemnym systemie','resolveTheme("dark",true)','dark','light','system','null','Jawny dark pozostaje dark.'],
 ['Przełączenie light','nextTheme("light")','dark','light','system','undefined','Binary toggle wybiera drugi motyw.'],
 ['Przełączenie dark','nextTheme("dark")','light','dark','system','null','Dark przechodzi na light.'],
 ['Dwa przełączenia light','nextTheme(nextTheme("light"))','light','dark','system','undefined','Dwa kroki wracają do początku.'],
 ['Dwa przełączenia dark','nextTheme(nextTheme("dark"))','dark','light','system','null','Parzysta liczba przełączeń zachowuje motyw.'],
 ['Trzy przełączenia light','nextTheme(nextTheme(nextTheme("light")))','dark','light','system','null','Nieparzysta liczba zmienia motyw.'],
 ['Trzy przełączenia dark','nextTheme(nextTheme(nextTheme("dark")))','light','dark','system','undefined','Trzeci krok ponownie zmienia wynik.'],
 ['System po zmianie preferencji','resolveTheme("system",false)!==resolveTheme("system",true)','true','false','undefined','system','Tryb automatyczny zależy od preferencji.'],
 ['Light niezależny od systemu','resolveTheme("light",false)===resolveTheme("light",true)','true','false','undefined','dark','Jawny wybór nie zależy od systemu.'],
 ['Dark niezależny od systemu','resolveTheme("dark",false)===resolveTheme("dark",true)','true','false','undefined','light','System nie nadpisuje wyboru dark.'],
 ['Etykieta akcji z light','nextTheme("light")==="dark"?"Ciemny":"Jasny"','Ciemny','Jasny','System','light','Etykieta opisuje motyw docelowy.'],
 ['Etykieta akcji z dark','nextTheme("dark")==="dark"?"Ciemny":"Jasny"','Jasny','Ciemny','System','dark','Akcja z dark prowadzi do light.'],
 ['Automat jasny a jawny jasny','resolveTheme("system",false)===resolveTheme("light",true)','true','false','undefined','system','Wynik może być ten sam mimo różnych preferencji.'],
 ['Automat ciemny a jawny ciemny','resolveTheme("system",true)===resolveTheme("dark",false)','true','false','undefined','system','Ten sam wygląd nie oznacza tej samej polityki.'],
 ['Porównanie przeciwnych jawnych motywów','resolveTheme("light",true)===resolveTheme("dark",true)','false','true','undefined','system','Jawne motywy są różne.'],
];
for(const [label,expression,a,b,c,d,explanation] of codeCases)quiz.push([`${label}: co wypisze kod?`,a,b,c,d,explanation,helpers+'\nconsole.log('+expression+');']);
const policyCases=[
 ['Brak atrybutu i jasny system','system',false,'light'],['Brak atrybutu i ciemny system','system',true,'dark'],
 ['Jawny light mimo systemu dark','light',true,'light'],['Jawny dark mimo systemu light','dark',false,'dark'],
 ['Jawny light przy systemie light','light',false,'light'],['Jawny dark przy systemie dark','dark',true,'dark'],
];
// Przypadki polityki wiążą wynik z konkretnym zapisem atrybutu, nie z samą nazwą trybu.
for(const [label,choice,systemDark,answer] of policyCases)quiz.push([`${label}: jaki atrybut ustawić dla preferencji ${choice}, a jaki będzie wygląd?`,choice==='system'?`Brak atrybutu; wygląd ${answer}`:`data-theme=${choice}; wygląd ${answer}`,`data-theme=system; wygląd system`,`data-theme=${answer==='light'?'dark':'light'}; wygląd ${answer==='light'?'dark':'light'}`,`Brak CSS i brak motywu`,'System oznacza usunięcie atrybutu; light i dark są jawnym wyborem. Preferencja systemowa dark='+systemDark+'.']);
quiz.push(...`
Root ma space-4=16px, a przodek compact nadpisuje na 8px. Jaki padding dostanie panel wewnątrz compact używający var(--space-4)?|8px|16px zawsze|4px|0px|Najbliższe odziedziczone nadpisanie obowiązuje w poddrzewie.
Panel poza compact nadal używa space-4 z root. Jaki padding dostanie?|16px|8px|4px|0px|Lokalne nadpisanie nie zmienia tokenu poza swoim zakresem.
Var(--missing,red) przy niezdefiniowanej --missing daje jaki kolor?|red|Przezroczysty zawsze|Napis missing|Losowy|Przy braku wartości używany jest fallback.
Czy zapis --brand:var(--color-primary) wymaga skopiowania hex do każdego Panelu?|Nie|Tak|Tylko w dark|Tylko bez TS|Token może odwoływać się do innego tokenu.
Co stanie się z nieznanym data-theme=blue przy selektorach tylko light/dark i braku media dla istniejącego atrybutu?|Pozostaną bazowe tokeny bez reguły systemowej|Automatycznie włączy się dark|CSS rzuci wyjątek JS|Usunie się html|Nieznana wartość nie pasuje do dark, a sam atrybut wyklucza gałąź braku wyboru.
Czy TypeScriptowa unia Theme waliduje zewnętrzny atrybut HTML w runtime?|Nie|Tak|Tylko na html|Tylko z strict|Dane spoza kodu mogą wymagać walidacji.
Dlaczego nie ustawiać data-theme na przypadkowym rodzeństwie Panelu?|Zmienne nie odziedziczą się do Panelu z rodzeństwa|Rodzeństwo zawsze jest niewidoczne|React zabrania dataset|Zmieni to key|Dziedziczenie przebiega od przodków do potomków.
Co wyjaśnia test jasnego systemu z jawnym dark?|Pierwszeństwo wyboru użytkownika|Tylko kolor domyślny|Wyłącznie działanie state|Tylko strukturę tabeli|Macierz musi obejmować konflikt systemu i wyboru jawnego.
Czy dekoracyjna zmiana tokenu zachowuje semantykę przycisku?|Tak, jeśli użyto nadal natywnego button|Nie, zmienia go w div|Tylko dla primary|Tylko w TSX|Sposób doboru kolorów nie zmienia rodzaju elementu.
Dlaczego nie zapisywać document w resolveTheme?|Resolver ma być czystym obliczeniem|Document nie istnieje w żadnej przeglądarce|Theme nie może być stringiem|CSS wymaga hooka|Czystą funkcję łatwo testować bez DOM.
Czy applyChoice(root,system) powinno przypisać dosłowny napis system do atrybutu w przyjętym CSS?|Nie, usuwa atrybut|Tak, zawsze|Tylko w light|Tylko w JSX|Selektor automatu opiera się na nieobecności atrybutu.
Czy wybór w stanie React automatycznie zmienia globalne data-theme?|Nie|Tak|Tylko w TSX|Tylko w strict|Potrzebny jest jawny mechanizm aplikowania wyboru.
Czy zapis atrybutu automatycznie aktualizuje useState przełącznika?|Nie|Tak|Tylko dla dark|Tylko w JSX|Dwa źródła mogą się rozjechać bez synchronizacji.
Co odróżnia test statycznego renderu od testu kliknięcia motywu?|Kliknięcie sprawdza zapis atrybutu i zmianę etykiety|Nic|Tylko liczba plików|Wyłącznie kompilator|Sam HTML początkowy nie dowodzi działania handlera.
`.trim().split('\n').map(line=>line.split('|')));
module.exports={exercises,quiz,tokens,helpers,toggle,project};
