const panel='import type {ReactNode} from "react";\nimport styles from "./Panel.module.css";\nexport function Panel({title,actions,children}:{title:string;actions?:ReactNode;children:ReactNode}){return <section className={styles.panel}><header className={styles.header}><h2 className={styles.title}>{title}</h2>{actions}</header><div className={styles.body}>{children}</div></section>;}';
const badge='import clsx from "clsx";\nimport type {ReactNode} from "react";\nimport styles from "./Badge.module.css";\ntype Tone="neutral"|"success"|"warning"|"danger";\nexport function Badge({tone="neutral",children}:{tone?:Tone;children:ReactNode}){return <span className={clsx(styles.badge,styles[tone])}>{children}</span>;}';
const row='import clsx from "clsx";\nimport styles from "./TicketRow.module.css";\ntype Ticket={id:number;title:string;status:"OPEN"|"CLOSED"};\nexport function TicketRow({ticket,selected}:{ticket:Ticket;selected:boolean}){return <tr className={clsx(styles.row,selected&&styles.selected,ticket.status==="CLOSED"&&styles.closed)}><td>{ticket.id}</td><td>{ticket.title}</td></tr>;}';
const project={
 'src/Panel.tsx':panel,
 'src/Panel.module.css':'.panel { border: 1px solid #d1d5db; border-radius: 8px; }\n.header { display: flex; justify-content: space-between; padding: 1rem; }\n.title { font-size: 1rem; margin: 0; }\n.body { padding: 1rem; }\n:global(.dark) .panel { background: #111827; color: #f9fafb; }',
 'src/Badge.tsx':badge,
 'src/Badge.module.css':'.badge { display: inline-block; padding: 0.125rem 0.5rem; border-radius: 4px; }\n.neutral { background: #f3f4f6; }\n.success { background: #dcfce7; }\n.warning { background: #fef9c3; }\n.danger { background: #fee2e2; }',
 'src/TicketRow.tsx':row,
 'src/TicketRow.module.css':'.row td { padding: 0.75rem; }\n.row:hover { background: #f9fafb; }\n.selected { outline: 2px solid #2563eb; }\n.closed td { color: #6b7280; text-decoration: line-through; }',
 'src/StatCard.tsx':'import styles from "./StatCard.module.css";\nexport function StatCard({label,value}:{label:string;value:number}){return <article className={styles.card}><h3 className={styles.label}>{label}</h3><strong className={styles.value}>{value}</strong></article>;}',
 'src/StatCard.module.css':'.card { padding: 1rem; border: 1px solid #d1d5db; }\n.label { font-size: 0.875rem; }\n.value { font-size: 2rem; }',
 'src/TicketCard.tsx':'import styles from "./TicketCard.module.css";\nexport function TicketCard(){return <article className={styles.card}><h2 className={styles.title}>API</h2></article>;}',
 'src/TicketCard.module.css':'.card { padding: 1rem; }\n.title { font-size: 1.5rem; }',
 'src/shared.module.css':'.base { border-radius: 6px; padding: 0.5rem; }',
 'src/Notice.module.css':'.notice { composes: base from "./shared.module.css"; border: 1px solid #2563eb; }',
 'src/Notice.tsx':'import styles from "./Notice.module.css";\nexport function Notice(){return <p className={styles.notice}>Gotowe</p>;}',
 'src/App.css':'* { box-sizing: border-box; }\nbody { margin: 0; font-family: system-ui, sans-serif; }\n.app-shell { max-width: 72rem; margin: 0 auto; padding: 1rem; }\nbutton:focus-visible { outline: 3px solid #2563eb; outline-offset: 2px; }',
 'src/App.tsx':'import "./App.css";\nimport {Panel} from "./Panel";\nimport {Badge} from "./Badge";\nimport {StatCard} from "./StatCard";\nimport {TicketRow} from "./TicketRow";\nimport {TicketCard} from "./TicketCard";\nimport {Notice} from "./Notice";\nexport default function App(){return <main className="app-shell"><Panel title="Statystyki" actions={<button type="button">Nowy</button>}><StatCard label="Otwarte" value={0}/><Badge tone="success">Gotowe</Badge></Panel><Panel title="Tickety"><table><tbody><TicketRow ticket={{id:1,title:"API",status:"CLOSED"}} selected/></tbody></table></Panel><TicketCard/><Notice/></main>;}'
};
const exercises=[
 ['Utwórz Panel.module.css i zaimportuj mapę klas w Panel.tsx.','Potrzebujesz importu domyślnego.',panel],
 ['Przenieś panel__header do lokalnej header i użyj jej w JSX.','Zaktualizuj równocześnie selektor i odwołanie.','import styles from "./Panel.module.css";\nexport function Header(){return <header className={styles.header}>Panel</header>;}\n/* .header { padding: 1rem; } */'],
 ['Przenieś Panel z title, actions i children na cztery lokalne klasy.','Zachowaj istniejący kontrakt kompozycji.',panel],
 ['Dodaj TicketCard z własną title o rozmiarze innym niż Panel.title.','Dwa moduły mogą mieć taką samą lokalną nazwę.',project['src/TicketCard.tsx']+'\n/* TicketCard.module.css: .card { padding: 1rem; } .title { font-size: 1.5rem; } */'],
 ['Zaplanuj test braku kolizji dwóch title.','Porównaj klasy DOM i obliczone rozmiary fontów.','Wyrenderuj oba komponenty razem: Panel.title 1rem i TicketCard.title 1.5rem. Ich wygenerowane nazwy mają być różne. Zmiana fontu w module TicketCard nie powinna zmienić nagłówka Panelu.'],
 ['Połącz row i warunkową selected przez clsx.','Przekaż do clsx odczytane wartości mapy.','import clsx from "clsx";\nimport styles from "./TicketRow.module.css";\nexport function Row({selected}:{selected:boolean}){return <tr className={clsx(styles.row,selected&&styles.selected)}><td>API</td></tr>;}'],
 ['Dodaj niezależną klasę closed na podstawie statusu ticketu.','Selected i closed mogą występować równocześnie.',row],
 ['Odczytaj lokalną klasę row-selected przez zapis nawiasowy.','Myślnik w zapisie kropkowym stałby się odejmowaniem.','import styles from "./TicketRow.module.css";\nexport function Row(){return <tr className={styles["row-selected"]}><td>API</td></tr>;}\n/* .row-selected { background: #eff6ff; } */'],
 ['Zmień row-selected na rowSelected i popraw JSX.','Nie zakładaj automatycznej konwersji nazw.','import styles from "./TicketRow.module.css";\nexport function Row(){return <tr className={styles.rowSelected}><td>API</td></tr>;}\n/* .rowSelected { background: #eff6ff; } */'],
 ['Zbuduj Badge z klasą bazową oraz wariantem styles[tone].','Zastosuj unię czterech wariantów.',badge],
 ['Dodaj CSS czterech wariantów Badge z osobną bazą.','Padding jest wspólny, tła zależą od tone.',project['src/Badge.module.css']],
 ['Przenieś StatCard do osobnego modułu: card, label i value.','Zero pozostaje widoczne.',project['src/StatCard.tsx']],
 ['Rozdziel reguły globalne i komponentowe po migracji.','Reset i body dotyczą całej aplikacji.','App.css: reset, body, układ app-shell. Panel.module.css: panel, header, title, body. Badge.module.css: badge i warianty. StatCard.module.css: card, label, value. TicketRow.module.css: row, selected, closed.'],
 ['Wyjaśnij, czy domyślne typy Vite znają rzeczywiste klasy pliku.','To słownik, nie automatycznie wygenerowany dokładny zbiór.','Domyślna deklaracja to readonly słownik string→string. Styles.tittle może przejść kompilację, mimo że arkusz ma tylko title. Odczyt nieistniejącego klucza w runtime daje undefined.'],
 ['Napraw styles.tittle przy istniejącej klasie title.','Sprawdź arkusz, nie tylko brak błędów TS.','import styles from "./Panel.module.css";\nexport function Title(){return <h2 className={styles.title}>Panel</h2>;}'],
 ['Porównaj podpowiedzi edytora i generowanie dokładnych deklaracji CSS.','Kontrola w CI wymaga osobnego sprawdzenia.','Plugin językowy może dać podpowiedzi w edytorze. Generator dokładnych .module.css.d.ts może zapewnić tsc znajomość kluczy. Same podpowiedzi nie dowodzą kontroli w CI; sprawdzaj też aktualność deklaracji.'],
 ['Skomponuj notice z lokalnej base w tym samym pliku.','Composes należy do reguły klasy.','/* Notice.module.css */\n.base { padding: 0.5rem; border-radius: 6px; }\n.notice { composes: base; border: 1px solid #2563eb; }'],
 ['Skomponuj notice z base w shared.module.css.','Ścieżka jest relatywna do pliku CSS.','/* shared.module.css: .base { padding: 0.5rem; } */\n/* Notice.module.css: .notice { composes: base from "./shared.module.css"; border: 1px solid #2563eb; } */'],
 ['Wyjaśnij, dlaczego eksport notice może zawierać kilka tokenów.','Nie obcinaj napisu do pierwszej nazwy.','Composes dołącza nazwę bazy do eksportowanej wartości klasy notice. Cały napis należy przekazać do className, inaczej można zgubić style bazowe.'],
 ['Dodaj panel dla globalnego motywu dark na przodku.','Tylko dark ma wyjść z lokalnego zakresu.','/* Panel.module.css */\n:global(.dark) .panel { background: #111827; color: #f9fafb; }'],
 ['Odróżnij lokalną .dark od :global(.dark).','Zgodność nazw źródłowych nie oznacza zgodności wygenerowanych klas.','Lokalna dark zostaje przekształcona. Globalna dark pozostaje dosłowną nazwą pasującą np. do html.dark. Lokalna klasa nie podłączy się automatycznie do globalnej klasy motywu.'],
 ['Zdiagnozuj globalne działanie samego selektora h2 w module.','CSS Modules nie tworzy Shadow DOM.','Sam h2 nie ma lokalnej klasy ograniczającej dopasowanie. Użyj .title albo .panel h2 z lokalnym panelem. Moduły lokalizują nazwy, nie izolują wszystkich reguł i dziedziczenia.'],
 ['Przyjmij zewnętrzną className i zachowaj lokalną bazę Panelu.','Rodzic może przekazać klasę z własnego modułu.','import clsx from "clsx";\nimport type {ReactNode} from "react";\nimport styles from "./Panel.module.css";\nexport function Panel({className,children}:{className?:string;children:ReactNode}){return <section className={clsx(styles.panel,className)}>{children}</section>;}'],
 ['Połącz lokalną badge z dosłowną globalną analytics-target.','Zwykły napis JSX nie jest automatycznie hashowany.','import clsx from "clsx";\nimport styles from "./Badge.module.css";\nexport function Badge(){return <span className={clsx(styles.badge,"analytics-target")}>Info</span>;}'],
 ['Zapisz minimalny Panel w JSX ze stylami modułowymi.','Import klasy działa jak w TSX, bez składni typów.','import styles from "./Panel.module.css";\nexport function Panel(){return <section className={styles.panel}><h2 className={styles.title}>Panel</h2></section>;}'],
 ['Napraw className="styles.title".','Cudzysłów tworzy dosłowną nazwę.','Użyj className={styles.title}. Klamry wykonują odczyt wartości z mapy modułu. Napis styles.title nie jest tym odczytem.'],
 ['Zaplanuj usunięcie starych reguł globalnych po migracji.','Najpierw wyszukaj wszystkie odwołania.','Przenieś reguły, zaktualizuj użycia i usuń wyłącznie nieużywane selektory panel__*, badge* i row*. Zachowaj reset oraz używane globalne reguły. Sprawdź produkcyjny build i wygląd wariantów.'],
 ['Zaprojektuj macierz testów selected i CLOSED bez stałego hasha.','Oba warunki są niezależne.','Sprawdź cztery kombinacje, porównując użycie eksportów row, selected i closed oraz wygląd. Nie zapisuj na sztywno sufiksów generowanych przez bundler; mogą zmienić się wraz z kodem lub konfiguracją.'],
 ['Wyjaśnij, czy usunięcie JSX komponentu gwarantuje zniknięcie jego CSS z paczki.','Sprawdź cały graf importów.','Nie zawsze. Arkusz może nadal być importowany gdzie indziej. Usuń nieużywane zależności i sprawdź build. Lokalizacja nazw nie usuwa automatycznie plików źródłowych ani wszystkich nieużywanych reguł.'],
 ['Oddaj migrację Panel, Badge, StatCard i TicketRow oraz demonstrację dwóch title i composes.','Osobne moduły współistnieją z globalnym resetem.','Pliki projektu:\n'+Object.entries(project).map(([file,code])=>`--- ${file} ---\n${code}`).join('\n\n')],
];
const quiz=`
Jakie rozszerzenie włącza CSS Modules w standardowym Vite?|.module.css|.local.js|.css.tsx|.module.json|Vite rozpoznaje konwencję module.css.
Co daje import domyślny modułu CSS?|Mapowanie lokalnych nazw na wygenerowane klasy|Komponent React|Tablicę użytkowników|Funkcję useStyles obowiązkowo|Obiekt pozwala odczytywać klasy.
Jak przypisać lokalną title w JSX?|className={styles.title}|className="title" zawsze|style={styles}|key={styles.title}|ClassName otrzymuje wygenerowany napis.
Czy dwa moduły mogą definiować title?|Tak|Nie|Tylko w JSX|Tylko dla h2|Nazwy są lokalizowane osobno.
Czy wpisywać wygenerowany hash ręcznie?|Nie|Tak|Tylko w TSX|Tylko w produkcji|Należy korzystać z eksportów styles.
Czy sufiks klasy jest stabilnym API?|Nie|Tak|Tylko dla Panelu|Tylko bez CSS|Może zmieniać się z zawartością i konfiguracją.
Czy standardowy Vite wymaga dodatkowego silnika runtime CSS Modules?|Nie|Tak|Tylko w TSX|Tylko dla Badge|Obsługa jest częścią procesu budowania.
Czy CSS Modules zmienia składnię komponentów React?|Nie|Tak|Tylko dla children|Tylko dla key|Komponenty nadal używają JSX.
Co przekazać do clsx dla lokalnej row?|styles.row|Dosłowny napis styles.row|Cały styles|Nazwę pliku CSS|Clsx łączy odczytane wartości.
Czy clsx hashuje nazwy?|Nie|Tak|Tylko w Vite|Tylko dla tablic|Transformacją zajmuje się narzędzie CSS Modules.
Jak odczytać row-selected?|styles["row-selected"]|styles.row.selected|styles(row-selected)|styles.row-selected jako jeden identyfikator|Nawiasy obsługują klucz z myślnikiem.
Dlaczego camelCase ułatwia odczyt klasy?|Pozwala użyć zapisu kropkowego|Nadaje globalny zasięg|Usuwa potrzebę CSS|Dodaje hover|RowSelected jest poprawnym identyfikatorem.
Czy zakładać automatyczną konwersję row-selected na rowSelected?|Nie|Tak|Tylko w TSX|Tylko dla Badge|Konwersja wymaga odpowiedniej konfiguracji.
Jak wygląda domyślny typ styles w Vite?|Readonly słownik tekstów|Dokładny zbiór klas pliku|ReactNode|Tablica CSSProperties|Typ nie analizuje rzeczywistych selektorów.
Czy styles.tittle musi wywołać błąd tsc bez dodatkowych deklaracji?|Nie|Tak|Tylko w strict|Tylko w produkcji|Słownik string pozwala odczytać dowolny klucz.
Co daje dokładny module.css.d.ts?|Możliwość sprawdzania kluczy przez TS|Automatyczny dobór kolorów|Pobieranie CSS przez API|Nowy komponent|Deklaracja może wymieniać konkretne eksporty.
Czy podpowiedzi pluginu edytora dowodzą kontroli w CI?|Nie|Tak|Tylko w Vite|Tylko w TSX|Language service i tsc uruchomiony w CI to różne mechanizmy.
Co trzeba kontrolować przy generowanych deklaracjach?|Aktualność względem CSS|Tylko wersję Reacta|Wyłącznie długość hasha|Login użytkownika|Nieaktualna deklaracja może ukryć usuniętą klasę.
Czy unia tone dowodzi istnienia styles[tone] w arkuszu?|Nie|Tak|Tylko z clsx|Tylko dla danger|Unia ogranicza props, ale domyślny słownik nie analizuje CSS.
Jak zachować bazę Badge przy wariancie?|Połączyć styles.badge i styles[tone]|Użyć wyłącznie wariantu|Zmienić key|Zmienić children na CSS|Baza odpowiada za wspólne reguły.
Co robi odczyt styles[tone]?|Pobiera wartość pod wskazanym kluczem|Generuje regułę w runtime|Mutuje arkusz|Wywołuje komponent|To zwykły odczyt obiektu JS.
Gdzie zostawić reset i body?|W świadomie globalnym arkuszu|W każdym module po kopii|W props Panelu|W module danych|Reguły dotyczą całej strony.
Czy BEM jest zakazany w CSS Modules?|Nie|Tak|Tylko w JSX|Tylko dla h2|Można zachować konwencję, choć izolacja pozwala skracać nazwy.
Co robi composes: base?|Dołącza bazową klasę do eksportu|Kopiuje props React|Tworzy Base.tsx|Ustawia stan|Kompozycja rozszerza zestaw klas elementu.
Gdzie zapisuje się composes?|W regule klasy modułu|W useEffect|W key|W aria-label|Składnię przetwarza CSS Modules.
Jak wskazać bazę z innego pliku?|Przez composes z from i ścieżką|Przez useState|Przez style.base|Przez props.key|Klauzula from wskazuje źródłowy moduł.
Czy eksport skomponowanej klasy może mieć kilka tokenów?|Tak|Nie|Tylko w JSX|Tylko bez clsx|Wartość może zawierać własną nazwę i nazwę bazy.
Czy można bezpiecznie obciąć taki eksport do pierwszego tokenu?|Nie|Tak|Tylko przy border|Tylko w TSX|Można utracić style skomponowanej bazy.
Czy composes to dziedziczenie komponentów React?|Nie|Tak|Tylko dla Panelu|Tylko z children|Mechanizm dotyczy klas CSS.
Co oznacza :global(.dark)?|Dosłowną globalną klasę dark|Lokalną hashowaną dark|Nowy prop|Wyłączenie CSS|Wybrana nazwa nie jest lokalizowana.
Która klasa w :global(.dark) .panel pozostaje lokalna?|panel|dark|Obie zawsze globalne|Żadna|Global obejmuje tylko dark.
Czy lokalna .dark automatycznie pasuje do html.dark?|Nie|Tak|Tylko w Vite|Tylko w TSX|Lokalna nazwa jest przekształcana.
Czy CSS Modules tworzy Shadow DOM?|Nie|Tak|Tylko w React 19|Tylko dla Panelu|Izolacja nazw nie tworzy osobnego drzewa DOM.
Czy sam selektor h2 w module jest ograniczony do komponentu?|Nie|Tak|Tylko w Vite|Tylko w TSX|Nie zawiera lokalnej klasy ograniczającej dopasowanie.
Jak ograniczyć h2 do lokalnego panelu?|Selektorem .panel h2|Nazwą pliku Panel.tsx|Key na h2|Importem type|Lokalna klasa warunkuje przodka.
Czy globalne reguły nadal mogą wpływać na lokalnie stylowany element?|Tak|Nie|Tylko bez clsx|Tylko na serwerze|Kaskada i dziedziczenie nadal działają.
Czy moduł rozwiązuje wszystkie konflikty między własnymi regułami?|Nie|Tak|Tylko dla hover|Tylko przy strict|Specyficzność i kolejność nadal mają znaczenie.
Co zrobić z konfliktem hover i selected?|Ustalić świadomy priorytet selektorów|Zmienić id danych|Dodać losowy hash|Wyłączyć TS|To problem kaskady wewnątrz CSS.
Czy nazwa zmiennej styles czyni zwykły App.css lokalnym?|Nie|Tak|Tylko w JSX|Tylko dla div|Liczy się konwencja i konfiguracja modułu CSS.
Co robi className="styles.title"?|Nadaje dosłowną klasę styles.title|Odczytuje mapę|Importuje arkusz|Zawsze rzuca błąd|Napis w cudzysłowie nie jest wyrażeniem JS.
Czy rodzic może przekazać klasę ze swojego modułu?|Tak|Nie|Tylko przez key|Tylko dla div|Wygenerowana wartość jest napisem.
Czy dodatkowa className rodzica zawsze wygra kolor?|Nie|Tak|Tylko z clsx|Tylko w module|O wyniku decyduje kaskada.
Czy dosłowna analytics-target w JSX jest hashowana przez moduł CSS?|Nie|Tak|Tylko w TSX|Tylko dla Badge|Dowolne napisy JSX nie są mapowane automatycznie.
Czy style dla dynamicznej szerokości można łączyć z modułem?|Tak|Nie|Tylko bez klas|Tylko w JSX|Inline style i lokalne klasy współpracują.
Czy zmienne CSS działają w modułach?|Tak|Nie|Tylko globalnie|Tylko z silnikiem JS|To nadal CSS z lokalnymi nazwami klas.
Co sprawdzić po przeniesieniu Panelu?|Zgodność kluczy JSX i klas arkusza|Wyłącznie nazwę funkcji|Tylko liczbę props|Tylko rozmiar JSON|Literówki mogą dać element bez klasy.
Co potwierdza test dwóch title?|Brak kolizji lokalnych nazw|Brak wszystkich stylów globalnych|Pełną walidację TS|Izolację Shadow DOM|To konkretna korzyść mechanizmu lokalizacji.
Czy snapshot ze stałym hashem najlepiej sprawdza wygląd?|Nie|Tak|Tylko w Vite|Tylko dla h2|Hash jest szczegółem narzędzia, a nie celem testu.
Czy build Vite zastępuje osobny typecheck TS?|Nie|Tak|Tylko z CSS Modules|Tylko przy JSX|Transpilacja i analiza typów to różne zadania.
Czy migracja usuwa potrzebę sprawdzenia focus-visible?|Nie|Tak|Tylko dla Badge|Tylko przy Panelu|Moduły nie zapewniają automatycznie dostępności.
Czy klasa selected sama tworzy stan React?|Nie|Tak|Tylko w module|Tylko z clsx|Props lub stan decydują o przypisaniu klasy.
Ile kombinacji selected i CLOSED należy sprawdzić?|4|2|3|8|Dwa niezależne booleany dają cztery kombinacje.
Czy zero w StatCard powinno zniknąć po migracji?|Nie|Tak|Tylko w dark|Tylko w module|Zero pozostaje poprawną statystyką.
Czy JSX i TSX mają różny mechanizm lokalizacji klas?|Nie|Tak|Tylko przy composes|Tylko dla Badge|Różnica dotyczy typów, nie transformacji CSS.
Czy usunięcie jednego użycia komponentu gwarantuje usunięcie CSS z buildu?|Nie|Tak|Tylko w TSX|Tylko przy BEM|Arkusz może pozostać importowany gdzie indziej.
Co zrobić ze starymi nieużywanymi regułami po migracji?|Usunąć po sprawdzeniu wszystkich odwołań|Zachować wszystkie na zawsze|Zmienić je w key|Skopiować wszędzie|Sprzątanie wymaga znajomości rzeczywistych użyć.
Czy styles należy mutować w renderze?|Nie|Tak|Tylko dla selected|Tylko gdy TS pozwala|Mapa klas nie jest stanem komponentu.
Czy readonly deklaracji wykonuje Object.freeze w runtime?|Nie|Tak|Tylko w Vite|Tylko dla string|Ograniczenie typu nie wykonuje kodu zamrażającego.
Względem czego rozwiązuje się from przy composes?|Pliku CSS|Bieżącego loginu|Props children|Adresu API|To zależność modułu stylów.
Co potwierdza ukończenie migracji?|Osobne moduły, zgodne użycia i brak kolizji|Sama zmiana rozszerzeń|Tylko instalacja clsx|Tylko widoczne hashe|Trzeba sprawdzić źródła i wynik renderowania.
`.trim().split('\n').map(line=>line.split('|'));
// Jawne, niezależne przypadki odczytów i kompozycji klas. Mapa symuluje eksport bundlera.
const cases=[
 ['Odczyt lokalnej title','styles.title','P_title','title','undefined','styles.title','Kropka odczytuje wartość klucza title.'],
 ['Odczyt lokalnej body','styles.body','P_body','body','P_title','undefined','Klucze title i body wskazują różne wartości.'],
 ['Literówka tittle','styles.tittle','undefined','P_title','tittle','null','Brakujący klucz nie tworzy automatycznej klasy.'],
 ['Zapis nawiasowy title','styles["title"]','P_title','title','undefined','P_body','Zapis nawiasowy odczytuje ten sam klucz.'],
 ['Nazwa z myślnikiem','styles["row-selected"]','R_selected','row-selected','undefined','R_row','Myślnik wymaga zapisu nawiasowego.'],
 ['Klucz camelCase','styles.rowSelected','R_selected','rowSelected','undefined','R_row','Mapa jawnie zawiera klucz camelCase.'],
 ['Baza wiersza','clsx(styles.row)','R_row','row','undefined','R_selected','Clsx zachowuje odczytaną bazę.'],
 ['Selected wyłączone','clsx(styles.row,false&&styles.selected)','R_row','R_row R_selected','false','undefined','Fałszywy argument zostaje pominięty.'],
 ['Selected włączone','clsx(styles.row,true&&styles.selected)','R_row R_selected','R_row','R_selected','true','Baza i wariant zostają połączone.'],
 ['Zamknięty bez zaznaczenia','clsx(styles.row,false&&styles.selected,styles.closed)','R_row R_closed','R_closed','R_row R_selected','R_row','Closed nie wymaga selected.'],
 ['Zaznaczony i zamknięty','clsx(styles.row,styles.selected,styles.closed)','R_row R_selected R_closed','R_row','R_selected R_closed','R_closed','Wszystkie trzy eksporty są niezależne.'],
 ['Brakujący wariant przez clsx','clsx(styles.row,styles.missing)','R_row','R_row undefined','undefined','missing','Clsx pomija undefined.'],
 ['Brakujący wariant przez szablon','`${styles.row} ${styles.missing}`','R_row undefined','R_row','undefined','missing','Interpolacja zamienia undefined w dosłowny tekst.'],
 ['Dwa identyczne eksporty','clsx(styles.row,styles.row)','R_row R_row','R_row','row row','undefined','Clsx nie deduplikuje nazw.'],
 ['Pusta className rodzica','clsx(styles.panel,"")','P_panel','P_panel undefined','panel','P_title','Pusty napis jest pomijany.'],
 ['Dodatkowa className rodzica','clsx(styles.panel,"layout")','P_panel layout','layout','P_panel','panel layout','Rodzic może dodać zwykły token.'],
 ['Wariant danger','clsx(styles.badge,styles.danger)','B_badge B_danger','B_danger','badge danger','B_badge','Baza nie znika przy wariancie.'],
 ['Wariant success','clsx(styles.badge,styles.success)','B_badge B_success','B_badge','success','B_danger','Wariant success ma własny eksport.'],
 ['Wariant neutral','clsx(styles.badge,styles.neutral)','B_badge B_neutral','neutral','B_badge B_success','B_neutral','Neutral łączy się z bazą.'],
 ['Odczyt wariantu przez zmienną','styles[tone]','B_danger','danger','B_badge','undefined','Tone ma wartość danger.'],
 ['Nazwa klasy jako zwykły tekst','"styles.title"','styles.title','P_title','title','undefined','Cudzysłów nie wykonuje odczytu obiektu.'],
 ['Dwa moduły title','panelStyles.title===cardStyles.title','false','true','undefined','title','Mapy symulują dwa różne eksporty klas.'],
 ['Lokalna i globalna klasa','clsx(styles.panel,"dark")','P_panel dark','P_panel P_dark','dark','P_panel','Dosłowny napis dark pozostaje dosłowny.'],
 ['Lokalna dark','styles.dark','P_dark','dark','undefined','P_panel','Lokalna dark to inna nazwa niż globalna dark.'],
 ['Eksport kompozycji','styles.notice','N_notice S_base','N_notice','S_base','notice','Skomponowany eksport zawiera dwa tokeny.'],
 ['Kompozycja z dodatkiem','clsx(styles.notice,"wide")','N_notice S_base wide','N_notice wide','S_base wide','wide','Cały eksport kompozycji zostaje zachowany.'],
 ['Utrata bazy przez obcięcie','styles.notice.split(" ")[0]','N_notice','N_notice S_base','S_base','undefined','Pierwszy token nie obejmuje skomponowanej bazy.'],
 ['Liczba tokenów kompozycji','styles.notice.split(" ").length','2','1','0','3','Eksport zawiera nazwę notice i base.'],
 ['Lista klas panelu','[styles.panel,styles.body].join(" ")','P_panel P_body','panel body','P_panel','P_body','Join łączy już wygenerowane napisy.'],
 ['Domyślna wartość brakującego klucza','styles.unknown??styles.neutral','B_neutral','undefined','unknown','B_badge','Operator nullish wybiera fallback.'],
 ['Klasa z obiektu clsx','clsx({[styles.selected]:true})','R_selected','selected','true','undefined','Obliczany klucz staje się nazwą klasy.'],
 ['Wyłączona klasa z obiektu','clsx(styles.row,{[styles.closed]:false})','R_row','R_row R_closed','false','R_closed','False nie dołącza klucza obiektu.'],
 ['Dwie tablice klas','clsx([styles.row],[styles.selected])','R_row R_selected','R_row,R_selected','R_row','R_selected','Clsx obsługuje zagnieżdżone tablice.'],
 ['Tylko globalny token','clsx("analytics-target")','analytics-target','B_analytics-target','undefined','P_panel','Clsx nie przekształca tokenu w lokalny eksport.'],
 ['Wariant warning','clsx(styles.badge,styles.warning)','B_badge B_warning','B_warning','warning','B_badge B_danger','Warning nie jest danger.'],
 ['Odczyt przez sklejenie klucza','styles["row-"+"selected"]','R_selected','undefined','row-selected','R_row','Sklejenie daje istniejący klucz, ale łatwo tu o literówkę.'],
 ['Sprawdzenie obecności poprawnego klucza','Object.hasOwn(styles,"title")','true','false','undefined','P_title','Mapa ma własny klucz title.'],
 ['Sprawdzenie literówki','Object.hasOwn(styles,"tittle")','false','true','undefined','P_title','Mapa nie ma klucza tittle.'],
 ['Porównanie kompozycji z bazą','styles.notice===styles.base','false','true','undefined','N_notice','Skomponowany napis nie jest samą bazą.'],
 ['Zachowanie zwykłego klucza','styles["body"]===styles.body','true','false','undefined','P_body','Oba zapisy odczytują tę samą właściwość.'],
];
const mapping='const styles:{[key:string]:string|undefined;notice:string;selected:string;closed:string}={title:"P_title",body:"P_body",panel:"P_panel",row:"R_row",selected:"R_selected",closed:"R_closed","row-selected":"R_selected",rowSelected:"R_selected",badge:"B_badge",danger:"B_danger",success:"B_success",neutral:"B_neutral",warning:"B_warning",dark:"P_dark",notice:"N_notice S_base",base:"S_base"};\nconst tone="danger";\nconst panelStyles={title:"P_title"},cardStyles={title:"C_title"};';
for(const [name,expr,a,b,c,d,explanation] of cases)quiz.push([`${name}: co wypisze kod z przykładową mapą eksportów?`,a,b,c,d,explanation,'import clsx from "clsx";\n'+mapping+'\nconsole.log('+expr+');']);
module.exports={exercises,quiz,panel,badge,row,project};
