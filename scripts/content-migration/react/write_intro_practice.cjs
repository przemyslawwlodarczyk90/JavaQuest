// Uzupełnia pierwszą, orientacyjną lekcję React (20 zadań / 60 pytań).
// Teoria pozostaje bez zmian. Zadania korzystają z JavaScriptu znanego przed kursem.
const fs = require('node:fs');
const path = require('node:path');
const assert = require('node:assert/strict');
const exercises = [];
const quiz = [];
const exercise = (prompt, hint, solution) => exercises.push({ prompt, hint, solution });
const question = (question, answer, distractors, explanation, code) => {
  const correctIndex = quiz.length % 4;
  const answers = [...distractors];
  answers.splice(correctIndex, 0, answer);
  quiz.push({ question, ...(code ? { code } : {}),
    options: Object.fromEntries(answers.map((text, i) => ['ABCD'[i], text])),
    correct: 'ABCD'[correctIndex], explanation });
};

exercise('Opisz w trzech zdaniach cel DevDesk, jego użytkownika i dwie czynności, które ma umożliwiać. Nie opisuj jeszcze implementacji.',
  'DevDesk służy zespołowi obsługującemu zgłoszenia. Oddziel potrzebę użytkownika od nazwy biblioteki.',
  'DevDesk to panel do obsługi zgłoszeń zespołu IT. Korzysta z niego osoba, która przegląda problemy i organizuje pracę nad nimi. Użytkownik może wyszukać zgłoszenie oraz zmienić jego status.');
exercise('Rozpisz docelowy ekran DevDesk na pięć części interfejsu i przypisz każdej jedną odpowiedzialność.',
  'Spójrz na nawigację, filtry, statystyki, tabelę i paginację.',
  'Nawigacja — przechodzenie między widokami. Panel filtrów — wybór kryteriów. Statystyki — podsumowanie zgłoszeń. Tabela — prezentacja wierszy zgłoszeń. Paginacja — wybór strony wyników. To wstępny podział, nie wymóg utworzenia od razu pięciu komponentów.');
exercise('Utwórz obiekt ticket w JavaScripcie: id 7, tytuł „Błąd eksportu”, opis „Plik CSV jest pusty.”, status OPEN, priorytet HIGH, brak przypisanej osoby, data 2026-10-01T09:30:00Z i tag export. Uwzględnij wszystkie pola modelu z lekcji.',
  'Brak osoby zapisz jako null, a tags jako tablicę.',
  "const ticket = {\n  id: 7,\n  title: 'Błąd eksportu',\n  description: 'Plik CSV jest pusty.',\n  status: 'OPEN',\n  priority: 'HIGH',\n  assignee: null,\n  createdAt: '2026-10-01T09:30:00Z',\n  tags: ['export'],\n};");
exercise('Dla ticket = { id: 7, title: „Błąd eksportu”, status: „OPEN” } wypisz w konsoli tekst „#7: Błąd eksportu (OPEN)”. Użyj wartości pól, bez wpisywania całego wyniku na sztywno.',
  'Użyj literału szablonowego i odczytu pól obiektu.',
  "const ticket = { id: 7, title: 'Błąd eksportu', status: 'OPEN' };\nconsole.log(`#${ticket.id}: ${ticket.title} (${ticket.status})`);");
exercise('Masz ticket z assignee równym null. Zbuduj zmienną assigneeLabel, która zawiera nazwę osoby albo „Nieprzypisane”, gdy osoba jest null. Sprawdź również assignee = „anna”.',
  'Operator ?? wybiera wartość zastępczą dla null i undefined.',
  "const ticket = { assignee: null };\nconst assigneeLabel = ticket.assignee ?? 'Nieprzypisane';\nconsole.log(assigneeLabel); // Nieprzypisane\nconst assignedTicket = { assignee: 'anna' };\nconsole.log(assignedTicket.assignee ?? 'Nieprzypisane'); // anna");
exercise('Zapisz tablicę trzech zgłoszeń zawierających tylko id i status. Użyj kolejno statusów OPEN, IN_PROGRESS i CLOSED, a potem wypisz liczbę elementów.',
  'To uproszczone dane do ćwiczenia JavaScriptu, nie pełne obiekty Ticket.',
  "const tickets = [\n  { id: 1, status: 'OPEN' },\n  { id: 2, status: 'IN_PROGRESS' },\n  { id: 3, status: 'CLOSED' },\n];\nconsole.log(tickets.length); // 3");
exercise('Dla uproszczonych danych [{ id: 1, status: „OPEN” }, { id: 2, status: „CLOSED” }, { id: 3, status: „OPEN” }] wylicz liczbę zgłoszeń o statusie dokładnie OPEN. Nie licz IN_PROGRESS jako OPEN.',
  'Połącz filter z length. Wynik ma wynosić 2.',
  "const tickets = [{ id: 1, status: 'OPEN' }, { id: 2, status: 'CLOSED' }, { id: 3, status: 'OPEN' }];\nconst openCount = tickets.filter(ticket => ticket.status === 'OPEN').length;\nconsole.log(openCount); // 2");
exercise('Dla [{ id: 1, priority: „HIGH” }, { id: 2, priority: „LOW” }, { id: 3, priority: „CRITICAL” }] zbuduj tablicę pilnych zgłoszeń. Na potrzeby zadania pilne oznacza HIGH lub CRITICAL.',
  'W predykacie filter połącz dwa porównania operatorem ||.',
  "const tickets = [{ id: 1, priority: 'HIGH' }, { id: 2, priority: 'LOW' }, { id: 3, priority: 'CRITICAL' }];\nconst urgent = tickets.filter(ticket => ticket.priority === 'HIGH' || ticket.priority === 'CRITICAL');\nconsole.log(urgent.map(ticket => ticket.id)); // [1, 3]");
exercise('Dla [{ id: 1, assignee: „anna” }, { id: 2, assignee: null }, { id: 3, assignee: „jan” }] wyznacz zgłoszenia osoby zapisanej w currentUser = „anna”. Nie zmieniaj tablicy wejściowej.',
  'Porównuj assignee z currentUser, zamiast wpisywać nazwę osoby wewnątrz predykatu.',
  "const tickets = [{ id: 1, assignee: 'anna' }, { id: 2, assignee: null }, { id: 3, assignee: 'jan' }];\nconst currentUser = 'anna';\nconst mine = tickets.filter(ticket => ticket.assignee === currentUser);\nconsole.log(mine); // [{ id: 1, assignee: 'anna' }]");
exercise('Wydziel uproszczone dane [{ id: 42, title: „API timeout” }] do modułu data/tickets.js jako eksport nazwany tickets. Pokaż import w pliku src/App.jsx, zakładając, że moduł jest pod src/data/tickets.js.',
  'Ścieżka względna zaczyna się od ./data/. Nie potrzebujesz jeszcze renderowania JSX.',
  "// src/data/tickets.js\nexport const tickets = [{ id: 42, title: 'API timeout' }];\n\n// src/App.jsx\nimport { tickets } from './data/tickets.js';\nconsole.log(tickets[0].title); // API timeout");
exercise('Odtwórz komponent StatCard w JSX z lekcji. Ma przyjmować label i value oraz wyświetlać je w span i strong wewnątrz div o klasie stat-card.',
  'Użyj destrukturyzacji argumentu, className i nawiasów klamrowych dla wartości.',
  'export function StatCard({ label, value }) {\n  return (\n    <div className="stat-card">\n      <span>{label}</span>\n      <strong>{value}</strong>\n    </div>\n  );\n}');
exercise('Zapisz dwa przykłady użycia StatCard: karta „Otwarte” z wartością 4 i karta „Pilne” z wartością 2. Przekaż liczby jako liczby JavaScriptu, nie napisy.',
  'Tekst może być w cudzysłowie, liczba powinna być w klamrach JSX.',
  '<StatCard label="Otwarte" value={4} />\n<StatCard label="Pilne" value={2} />\n// Dwa miejsca użycia tego samego komponentu, z różnymi danymi.');
exercise('Przepisz StatCard na TSX: dodaj StatCardProps z label: string i value: number. Zachowaj taki sam wynik wizualny jak w JSX.',
  'Typ opisuje argument komponentu. Nie dodawaj etykiet typów do wyświetlanego tekstu.',
  'type StatCardProps = { label: string; value: number };\n\nexport function StatCard({ label, value }: StatCardProps) {\n  return (\n    <div className="stat-card">\n      <span>{label}</span>\n      <strong>{value}</strong>\n    </div>\n  );\n}');
exercise('Dla typu StatCardProps = { label: string; value: number } popraw oba wywołania: <StatCard label="Otwarte" /> oraz <StatCard label="Pilne" value="2" />. Wyjaśnij osobno oba błędy.',
  'Pierwsze wywołanie pomija wymagane pole, drugie przekazuje napis zamiast liczby.',
  '<StatCard label="Otwarte" value={4} />\n<StatCard label="Pilne" value={2} />\n// W pierwszym brakowało value. W drugim "2" miało typ string, a wymagany jest number.');
exercise('Zapisz typy TicketStatus i TicketPriority dokładnie według modelu z lekcji. Podaj jedną odrzuconą wartość dla każdego typu.',
  'Statusy: OPEN, IN_PROGRESS, CLOSED. Priorytety: LOW, MEDIUM, HIGH, CRITICAL.',
  "type TicketStatus = 'OPEN' | 'IN_PROGRESS' | 'CLOSED';\ntype TicketPriority = 'LOW' | 'MEDIUM' | 'HIGH' | 'CRITICAL';\n// status 'DONE' i priorytet 'URGENT' nie należą do tych typów.");
exercise('Zapisz pełny typ Ticket z lekcji. Zwróć szczególną uwagę na assignee, createdAt i tags. Nie implementuj walidacji danych z API.',
  'Osoba to string | null, data jest napisem, a tagi tablicą napisów.',
  "type TicketStatus = 'OPEN' | 'IN_PROGRESS' | 'CLOSED';\ntype TicketPriority = 'LOW' | 'MEDIUM' | 'HIGH' | 'CRITICAL';\ntype Ticket = {\n  id: number;\n  title: string;\n  description: string;\n  status: TicketStatus;\n  priority: TicketPriority;\n  assignee: string | null;\n  createdAt: string;\n  tags: string[];\n};");
exercise('Zaplanuj przejście lekcji praktycznej od rozgrzewki do sprawdzenia wyniku. Wymień osiem etapów z teorii i zapisz, co robisz, gdy kryterium akceptacji nie jest spełnione.',
  'Po modelu mentalnym jest przykład, porównanie JSX i TSX, a następnie wspólna budowa.',
  '1. Rozgrzewka z pamięci. 2. Problem w DevDesk. 3. Model mentalny. 4. Działający przykład. 5. Porównanie JSX i TSX. 6. Budowanie funkcji krok po kroku. 7. Analiza pułapek. 8. Sprawdzenie kryterium akceptacji. Gdy wynik nie działa, wracam do budowy i diagnozuję różnicę względem przykładu.');
exercise('W poprzedniej lekcji zepsułeś kod i nie możesz zacząć następnej. Opisz plan odzyskania działającego punktu startowego bez usuwania własnej pracy i bez zakładania istnienia gotowej gałęzi z rozwiązaniem.',
  'Treść lekcji zawiera kod startowy. Git ułatwia porównanie, ale najpierw zachowaj aktualne pliki.',
  'Zachowuję obecną pracę w osobnej kopii lub własnym commicie. Odtwarzam kod startowy podany w treści następnej lekcji, uruchamiam go i sprawdzam wynik. Porównuję różnice z moją wersją, a po ukończeniu lekcji zapisuję kolejny commit. Nie zakładam, że gotowy checkpoint istnieje w repozytorium.');
exercise('Zaprojektuj krótkie zadanie Boss przenoszące kartę StatCard z DevDesk do katalogu produktów. Podaj dwa zestawy props i dwa mierzalne kryteria akceptacji. Nie dodawaj nowych hooków.',
  'Zmień domenę, pozostaw mechanizm przekazywania label i value.',
  'Zadanie: pokaż statystyki katalogu za pomocą StatCard. Dane: label="Produkty", value={12} oraz label="Dostępne", value={9}. Kryteria: ekran pokazuje obie etykiety z właściwymi liczbami; obie karty korzystają z tej samej implementacji StatCard. Zmiana domeny sprawdza rozumienie mechanizmu, a nie pamięć nazwy „ticket”.');
exercise('Przygotuj małe podsumowanie DevDesk w JavaScripcie. Użyj trzech uproszczonych zgłoszeń: (1, OPEN, HIGH, anna), (2, CLOSED, LOW, null), (3, IN_PROGRESS, CRITICAL, anna). Wylicz total, open, urgent i mine dla currentUser = „anna”. Pilne oznacza HIGH lub CRITICAL, a open oznacza wyłącznie OPEN. Podaj wyniki i zaplanuj odpowiadające im cztery karty StatCard.',
  'W każdym obiekcie użyj pól id, status, priority i assignee. Połącz length, filter i porównania poznane wcześniej w JavaScripcie.',
  "const tickets = [\n  { id: 1, status: 'OPEN', priority: 'HIGH', assignee: 'anna' },\n  { id: 2, status: 'CLOSED', priority: 'LOW', assignee: null },\n  { id: 3, status: 'IN_PROGRESS', priority: 'CRITICAL', assignee: 'anna' },\n];\nconst currentUser = 'anna';\nconst stats = {\n  total: tickets.length,\n  open: tickets.filter(t => t.status === 'OPEN').length,\n  urgent: tickets.filter(t => t.priority === 'HIGH' || t.priority === 'CRITICAL').length,\n  mine: tickets.filter(t => t.assignee === currentUser).length,\n};\nconsole.log(stats); // { total: 3, open: 1, urgent: 2, mine: 2 }\n// W JSX: StatCard z label Wszystkie/Otwarte/Pilne/Moje\n// i value odpowiednio stats.total/stats.open/stats.urgent/stats.mine.");

// Cel kursu i organizacja pracy (1–12).
question('Co jest głównym projektem budowanym w tym torze?', 'Panel obsługi zgłoszeń DevDesk', ['Silnik bazy danych', 'Kompilator TypeScriptu', 'System operacyjny'], 'Kolejne mechanizmy Reacta są wprowadzane przy rozbudowie jednego panelu zgłoszeń.');
question('Jaki efekt najlepiej potwierdza ukończenie lekcji praktycznej?', 'Uruchomiona funkcja spełnia kryterium akceptacji', ['Przeczytanie wszystkich nagłówków', 'Skopiowanie kodu bez uruchomienia', 'Zapamiętanie nazw wszystkich bibliotek'], 'Praktyczny wynik sprawdza się w działającej aplikacji, według warunków z lekcji.');
question('Jaką wiedzę JavaScriptu zakłada początek kursu?', 'Między innymi map/filter, moduły ES i async/await', ['Wyłącznie deklarowanie zmiennych', 'Wewnętrzną implementację Fiber', 'Pisanie rozszerzeń przeglądarki'], 'Kurs korzysta z fundamentów JavaScriptu zamiast objaśniać je od początku.');
question('W jakiej kolejności lekcja zaleca pracę nad dwiema wersjami przykładu?', 'Najpierw JSX, następnie TSX i analiza różnic', ['Najpierw bajtkod, potem JSX', 'Tylko TSX bez uruchamiania', 'Tylko czytanie obu plików'], 'Wersja JSX pozwala zobaczyć mechanizm, a TSX pokazuje dodatkowy opis typów.');
question('Czemu służy rozgrzewka przed nowym materiałem?', 'Przypomnieniu wiedzy z wcześniejszych lekcji z pamięci', ['Instalacji wszystkich pakietów ekosystemu', 'Ominięciu trudnych tematów', 'Zastąpieniu ćwiczeń samą teorią'], 'Rozgrzewka wymaga odtworzenia wiedzy przed zaglądaniem do treści.');
question('Co robisz, jeśli kryterium akceptacji nie jest spełnione?', 'Wracasz do budowy funkcji i sprawdzasz błąd', ['Uznajesz samo przeczytanie za ukończenie', 'Zmieniasz kryterium tak, by pasowało do błędu', 'Od razu przepisujesz całość w innym frameworku'], 'Kryterium stanowi konkretny punkt odniesienia do poprawienia implementacji.');
question('Co wyróżnia zadanie Boss?', 'Zastosowanie poznanej wiedzy w innej domenie z mniejszą liczbą podpowiedzi', ['Wyłącznie większa liczba linijek do przepisania', 'Obowiązek użycia nieomówionej biblioteki', 'Zakaz uruchamiania aplikacji'], 'Zmiana domeny sprawdza samodzielne użycie mechanizmu, a nie kopiowanie kroków.');
question('Po co zachowywać własny commit po lekcji?', 'Aby móc wrócić do zapisanego stanu i porównywać zmiany', ['Aby zastąpić sprawdzanie wyniku w przeglądarce', 'Aby TypeScript działał bez typów', 'Aby automatycznie powstały testy'], 'Commit zapisuje stan projektu; sam nie dowodzi poprawności działania.');
question('Co według lekcji pomaga ruszyć dalej po błędzie w poprzednim etapie?', 'Kod startowy podany w treści kolejnej lekcji', ['Automatyczne naprawienie każdego błędu przez React', 'Usunięcie całego projektu bez kopii', 'Pomijanie kolejnych ćwiczeń'], 'Działający punkt startowy pozwala kontynuować naukę i porównać własną wersję.');
question('Jak najlepiej wykorzystać quiz po lekcji?', 'Najpierw odpowiedzieć bez zaglądania do teorii, potem przeanalizować wyjaśnienia', ['Kopiować odpowiedzi przed przeczytaniem pytania', 'Oceniać wiedzę tylko czasem czytania', 'Zawsze pomijać błędne odpowiedzi bez analizy'], 'Próba odtworzenia z pamięci pozwala zauważyć luki i je uzupełnić.');
question('Co oznacza zalecana kolejność tematów w opisie kursu?', 'Platforma sugeruje ścieżkę, ale można wejść w dowolny temat', ['Każda kolejna lekcja wymaga płatnego odblokowania', 'Nie można powtórzyć wcześniejszej lekcji', 'Kolejność nie ma żadnego związku z trudnością'], 'Treść mówi o otwartych lekcjach i podpowiadanej kolejności nauki.');
question('Uczeń rozumie przykład na ekranie, lecz nie potrafi go odtworzyć. Jaki krok odpowiada metodzie kursu?', 'Samodzielnie zbudować mały przykład i sprawdzić wynik', ['Uznać materiał za opanowany', 'Czytać wyłącznie listę nazw hooków', 'Zrezygnować z kryterium akceptacji'], 'Rozpoznanie kodu podczas czytania nie zastępuje umiejętności jego użycia.');

// Model danych (13–30).
question('Jakie pole identyfikuje zgłoszenie w modelu Ticket?', 'id', ['title', 'tags', 'assignee'], 'Identyfikator jest osobnym polem; tytuł i osoba nie pełnią tej roli.');
question('Jaki typ ma id w pokazanym modelu TypeScript?', 'number', ['string[]', 'boolean', 'Date'], 'W definicji Ticket pole id jest liczbą.');
question('Które pole przechowuje krótki tytuł zgłoszenia?', 'title', ['description', 'priority', 'createdAt'], 'Title jest tytułem, description dłuższym opisem.');
question('Które pole odpowiada opisowi problemu?', 'description', ['id', 'status', 'tags'], 'Model rozdziela tytuł i szczegółowy opis problemu.');
question('Który zestaw zawiera wyłącznie dozwolone statusy z lekcji?', 'OPEN, IN_PROGRESS, CLOSED', ['OPEN, DONE, CLOSED', 'LOW, HIGH, CRITICAL', 'NEW, ACTIVE, ARCHIVED'], 'TicketStatus jest unią trzech konkretnych napisów.');
question('Który status nie należy do modelu z lekcji?', 'DONE', ['OPEN', 'IN_PROGRESS', 'CLOSED'], 'Zakończone zgłoszenie ma w tym modelu status CLOSED.');
question('Który zestaw opisuje priorytety TicketPriority?', 'LOW, MEDIUM, HIGH, CRITICAL', ['OPEN, IN_PROGRESS, CLOSED', 'MINOR, NORMAL, BLOCKER', 'ONE, TWO, THREE'], 'W kursie priorytet ma cztery jawnie nazwane wartości.');
question('Czy status i priority są tym samym pojęciem?', 'Nie: status opisuje etap pracy, a priority ważność zgłoszenia', ['Tak: oba muszą mieć wartość OPEN', 'Tak: oba są identyfikatorem', 'Nie: priority przechowuje datę'], 'Zgłoszenie może być jednocześnie otwarte i mieć wysoki priorytet.');
question('Co oznacza assignee: null w przykładzie?', 'Brak przypisanej osoby', ['Zgłoszenie jest zamknięte', 'Priorytet jest niski', 'Zgłoszenie nie ma identyfikatora'], 'Null reprezentuje brak osoby, niezależnie od statusu i priorytetu.');
question('Jaki typ pozwala przechować nazwę osoby lub jej brak?', 'string | null', ['string[]', 'number | boolean', 'Date'], 'Unia string | null odpowiada obu dopuszczonym postaciom wartości.');
question('Jak zapisano createdAt w modelu Ticket?', 'Jako string z datą w formacie ISO', ['Jako obiekt Date', 'Jako boolean', 'Jako tablicę numerów miesięcy'], 'Definicja z lekcji używa string; nie tworzy automatycznie obiektu Date.');
question('Która wartość pasuje do tags: string[]?', "['api', 'export']", ["'api,export'", '42', "{ api: true }"], 'String[] oznacza tablicę napisów, a nie jeden napis z przecinkiem.');
question('Jak reprezentować brak tagów przy wymaganym polu tags: string[]?', 'Pustą tablicą []', ['Usunięciem wymaganego pola', 'Wartością null bez zmiany typu', 'Liczbą 0'], 'Pusta tablica spełnia string[], nie wymaga pomijania pola.');
question('Którego pola nie ma w modelu Ticket przedstawionym w lekcji?', 'price', ['description', 'createdAt', 'priority'], 'Price pasowałoby do innej domeny; nie występuje w pokazanym modelu zgłoszenia.');
question('Który obiekt rozdziela tytuł i opis zgodnie z przeznaczeniem pól?', "{ title: 'Błąd logowania', description: 'Po wysłaniu formularza pojawia się 500.' }", ["{ title: 500, description: false }", "{ status: 'Błąd logowania', title: 'OPEN' }", "{ tags: 'Błąd logowania', id: null }"], 'Tytuł krótko nazywa problem, a opis podaje szczegóły; oba są napisami.');
question('Czy dwie różne osoby mogą mieć zgłoszenia o identycznym title?', 'Tak; to id służy do identyfikowania zgłoszenia', ['Nie; title musi być globalnie unikalne według podanego typu', 'Nie; React odrzuca powtórzone teksty', 'Tak, ale tylko bez tags'], 'Typ title: string nie narzuca unikalności tytułów.');
question('Która para pól umożliwia znalezienie „moich otwartych zgłoszeń”?', 'assignee i status', ['createdAt i description', 'tags i id bez sprawdzania osoby', 'title i priority bez sprawdzania statusu'], 'Potrzebne są oba warunki: odpowiednia osoba i otwarty status.');
question('Czy definicja Ticket sama sprawdzi poprawność daty ISO w dowolnym napisie?', 'Nie; pole createdAt: string nie weryfikuje formatu daty', ['Tak; każda wartość string jest poprawną datą', 'Tak; przeglądarka odrzuca każdy niepoprawny napis', 'Nie; bo w modelu nie ma createdAt'], 'Typ string sprawdza rodzaj wartości, nie jej zgodność z formatem daty.');

// JSX i TSX (31–42).
question('Co oznacza rozszerzenie .jsx w przykładach kursu?', 'JavaScript zawierający składnię JSX', ['Plik z samymi typami TypeScript', 'Dokument JSON z konfiguracją', 'Gotowy plik binarny'], 'Wariant JSX używa JavaScriptu i zapisu interfejsu JSX.');
question('Co wyróżnia wersję .tsx karty z lekcji?', 'Dodany opis typów props', ['Inna obowiązkowa zawartość wizualna', 'Zakaz korzystania z JavaScriptu', 'Automatyczna baza zgłoszeń'], 'W przykładzie TSX dopisano typ argumentu, zachowując ten sam interfejs.');
question('Jakie props przyjmuje pokazany StatCard?', 'label i value', ['status i assignee', 'id i tags', 'children i createdAt jako jedyne pola'], 'Przykładowa karta prezentuje etykietę oraz wartość.');
question('Jaki typ ma value w StatCardProps?', 'number', ['string', 'boolean', 'string[]'], 'Typ karty wymaga liczby dla value i napisu dla label.');
question('Które wywołanie pasuje do StatCardProps = { label: string; value: number }?', '<StatCard label="Otwarte" value={3} />', ['<StatCard label="Otwarte" value="3" />', '<StatCard value={3} />', '<StatCard label={3} value="Otwarte" />'], 'Klamry przekazują liczbę 3, a etykieta jest napisem.');
question('Dlaczego pominięcie value w typowanej karcie jest błędem?', 'Value jest wymaganym polem StatCardProps', ['Każdy komponent React musi mieć props value', 'JSX nie pozwala na mniej niż dwa atrybuty', 'Label musi być liczbą'], 'Wymagalność wynika z konkretnego typu, nie z ogólnego wymagania Reacta.');
question('Co wyświetla span w pokazanym StatCard?', 'Wartość label', ['Wartość value', 'Zawsze tekst „label”', 'Cały obiekt Ticket'], 'Zapis {label} wstawia wartość zmiennej do zawartości elementu.');
question('Co wyświetla strong w pokazanym StatCard?', 'Wartość value', ['Nazwę typu StatCardProps', 'Datę createdAt', 'Zawsze tekst „value”'], 'Zapis {value} wskazuje na przekazany prop.');
question('Jaki atrybut JSX wykorzystano do przypisania klasy stat-card?', 'className', ['cssClass', 'classId', 'styleName'], 'Przykład używa className="stat-card".');
question('Jaki wynik wizualny powinny dawać odpowiadające sobie wersje JSX i TSX z lekcji przy tych samych danych?', 'Tę samą kartę z tą samą etykietą i liczbą', ['TSX musi dodać widoczny panel typów', 'JSX musi ukryć etykietę', 'TSX zawsze pokazuje inną liczbę'], 'Opis typów nie jest dodatkową treścią interfejsu.');
question('Po co oglądać oba warianty przykładu?', 'Aby odróżnić mechanizm interfejsu od dodanych opisów typów', ['Aby nauczyć się dwóch różnych bibliotek UI', 'Aby zrezygnować z pisania kodu', 'Aby każdy ekran renderować dwa razy obok siebie'], 'Porównanie pokazuje, co dopisuje TypeScript do tego samego rozwiązania.');
question('Którą niezgodność wychwyci sprawdzenie typów dla TicketStatus?', "Przypisanie 'DONE' do zmiennej TicketStatus", ['Nieprawidłowe hasło użytkownika', 'Niedostępność serwera', 'Zbyt wolne łącze internetowe'], 'Unia literałów ogranicza dopuszczone napisy, ale nie sprawdza infrastruktury.');

// Czytanie prostego kodu JS znanego przed kursem (43–54).
question('Co wypisze ten kod?', '42', ['undefined', 'API timeout', '1'], 'Odczyt id zwraca liczbę przypisaną do pola identyfikatora.', "const ticket = { id: 42, title: 'API timeout' };\nconsole.log(ticket.id);");
question('Jaki napis zostanie wypisany?', 'API timeout', ['title', '42', 'undefined'], 'Destrukturyzacja pobiera wartość pola title do zmiennej o tej samej nazwie.', "const { title } = { id: 42, title: 'API timeout' };\nconsole.log(title);");
question('Ile elementów ma wynikowa tablica?', '2', ['1', '3', '0'], 'Filter zachowuje dwa obiekty, których status jest dokładnie OPEN.', "const tickets = [{ status: 'OPEN' }, { status: 'CLOSED' }, { status: 'OPEN' }];\nconsole.log(tickets.filter(t => t.status === 'OPEN').length);");
question('Co zwróci map w tym przykładzie?', "['Eksport', 'Logowanie']", ["[1, 2]", "['title', 'title']", "'Eksport,Logowanie'"], 'Map tworzy tablicę wartości title, a nie jeden połączony napis.', "const tickets = [{ id: 1, title: 'Eksport' }, { id: 2, title: 'Logowanie' }];\nconst result = tickets.map(t => t.title);");
question('Co wypisze kod z operatorem ?? dla braku osoby?', 'Nieprzypisane', ['null', 'assignee', 'false'], 'Operator ?? wybiera prawą stronę, gdy lewa jest null lub undefined.', "const ticket = { assignee: null };\nconsole.log(ticket.assignee ?? 'Nieprzypisane');");
question('Co wypisze ten sam wzorzec dla przypisanego zgłoszenia?', 'anna', ['Nieprzypisane', 'true', 'null'], 'Napis anna nie jest null ani undefined, więc zostaje zachowany.', "const ticket = { assignee: 'anna' };\nconsole.log(ticket.assignee ?? 'Nieprzypisane');");
question('Jaki wynik otrzymasz dla pustej listy zgłoszeń?', '0', ['null', 'undefined', '1'], 'Pusta tablica ma długość zero, także po filtrowaniu.', "const tickets = [];\nconsole.log(tickets.filter(t => t.status === 'OPEN').length);");
question('Które identyfikatory trafią do result przy definicji pilne = HIGH lub CRITICAL?', '[1, 3]', ['[1]', '[2]', '[1, 2, 3]'], 'Warunek || dopuszcza oba jawnie wymienione priorytety.', "const tickets = [{ id: 1, priority: 'HIGH' }, { id: 2, priority: 'LOW' }, { id: 3, priority: 'CRITICAL' }];\nconst result = tickets.filter(t => t.priority === 'HIGH' || t.priority === 'CRITICAL').map(t => t.id);");
question('Które identyfikatory znajdą się w mine?', '[2]', ['[1, 2]', '[3]', '[]'], 'Tylko drugie zgłoszenie ma assignee równe currentUser.', "const currentUser = 'anna';\nconst tickets = [{ id: 1, assignee: null }, { id: 2, assignee: 'anna' }, { id: 3, assignee: 'jan' }];\nconst mine = tickets.filter(t => t.assignee === currentUser).map(t => t.id);");
question('Co wypisze ten odczyt tagu?', 'api', ['export', '0', 'undefined'], 'Indeksy tablic zaczynają się od zera, więc tags[0] jest pierwszym tagiem.', "const ticket = { tags: ['api', 'export'] };\nconsole.log(ticket.tags[0]);");
question('Który import pasuje do tego eksportu nazwanego?', "import { tickets } from './data/tickets.js';", ["import tickets from './data/tickets.js';", "import { Ticket } from './data/tickets.js';", "import './data/tickets.js' as tickets;"], 'Eksport nazwany wymaga odpowiadającej mu nazwy w klamrach importu.', "// data/tickets.js\nexport const tickets = [];");
question('Co wypisze poniższy kod?', '#7: Eksport', ['7: title', '#id: Eksport', 'undefined'], 'Literał szablonowy wstawia wartości pól w miejsca interpolacji.', "const ticket = { id: 7, title: 'Eksport' };\nconsole.log(`#${ticket.id}: ${ticket.title}`);");

// Scenariusze i kryteria akceptacji (55–60).
question('Kolega proponuje zastąpić id tytułem, bo „jest czytelniejszy”. Jaka uwaga pasuje do modelu?', 'Tytuły mogą się powtarzać; pozostaw osobny identyfikator', ['React wymaga tytułów będących liczbami', 'Każdy tytuł jest automatycznie unikalny', 'Bez id zgłoszenie automatycznie znika'], 'Czytelny opis i identyfikator pełnią różne funkcje.');
question('Zadanie wymaga policzenia statusu dokładnie OPEN. Dane mają OPEN, IN_PROGRESS i CLOSED. Ile zgłoszeń należy policzyć?', '1', ['2', '3', '0'], 'W tym warunku IN_PROGRESS nie jest równe OPEN; znaczenie „otwarte” musi wynikać z wymagania.');
question('Które kryterium akceptacji dla karty „Moje” jest sprawdzalne?', 'Dla dwóch zgłoszeń przypisanych bieżącej osobie karta pokazuje 2', ['Karta jest nowoczesna', 'Kod wygląda profesjonalnie', 'Wszystkim podoba się kolor'], 'Kryterium wskazuje dane wejściowe i oczekiwany wynik.');
question('Uczeń kopiuje JSX do pliku TSX i zostawia value bez opisu typu. Co najlepiej realizuje cel porównania z lekcji?', 'Dodać StatCardProps i przeanalizować błędy przy niewłaściwym użyciu', ['Zmienić wyłącznie kolor karty', 'Usunąć label, żeby plik był krótszy', 'Dopisać tekst „TypeScript” do każdego elementu'], 'Celem wariantu TSX jest zrozumienie opisu typów i kontroli argumentów.');
question('Masz zbudować analogiczną kartę w katalogu produktów jako Boss. Co przenosisz z DevDesk?', 'Mechanizm komponentu z label i value, zmieniając dane domenowe', ['Obowiązkowo status OPEN dla każdego produktu', 'Wszystkie nazwy ticketów bez zmiany', 'Wymóg używania assignee jako ceny'], 'Transfer wiedzy polega na użyciu poznanego mechanizmu do innego problemu.');
question('Który plan najlepiej łączy praktykę, sprawdzenie i powtórkę?', 'Zbudować funkcję, sprawdzić kryterium, zrobić quiz z pamięci i wrócić do materiału po kilku dniach', ['Tylko przeczytać rozwiązanie raz', 'Zrobić zrzut kodu bez uruchamiania', 'Pominąć zadania i zapamiętać nazwy plików'], 'Kurs łączy działający rezultat z samodzielnym odtwarzaniem i późniejszą powtórką.');

assert.equal(exercises.length, 20);
assert.equal(quiz.length, 60);
for (const item of exercises) {
  for (const field of ['prompt', 'hint', 'solution']) assert.ok(item[field]?.trim());
}
assert.equal(new Set(exercises.map(e => e.prompt)).size, exercises.length);
assert.equal(new Set(quiz.map(q => q.question + '\n' + (q.code ?? ''))).size, quiz.length);
for (const item of quiz) {
  assert.deepEqual(Object.keys(item.options), ['A', 'B', 'C', 'D']);
  assert.equal(new Set(Object.values(item.options)).size, 4);
  assert.ok(item.options[item.correct]);
  assert.ok(item.explanation.trim());
}
const target = path.resolve(__dirname, '../../../src/main/resources/content/_react_00_wprowadzenie/01_CourseAndDevDeskProject.json');
const lesson = JSON.parse(fs.readFileSync(target, 'utf8'));
lesson.exercises = exercises;
lesson.quiz = quiz;
fs.writeFileSync(target, JSON.stringify(lesson, null, 2) + '\n');
console.log(`01_CourseAndDevDeskProject: ${exercises.length} zadań, ${quiz.length} pytań.`);
