const fs = require('fs');
const ex = [
['Wyświetl bieżący czas działania, liczbę sesji i trzy średnie obciążenia.', 'Użyj podstawowego programu uptime.', 'uptime'],
['Pokaż czas działania w czytelnej postaci bez średnich obciążenia.', 'Opcja pretty skraca wynik do czasu działania.', 'uptime -p'],
['Sprawdź datę i godzinę startu systemu.', 'Opcja since pokazuje moment startu.', 'uptime -s'],
['Odczytaj surowe dane o średnim obciążeniu z procfs.', 'Plik zawiera pięć pól.', 'cat /proc/loadavg'],
['Wyświetl tylko trzy średnie z pliku loadavg.', 'Wytnij pierwsze trzy pola przez awk.', "awk '{print $1, $2, $3}' /proc/loadavg"],
['Wyświetl bieżącą liczbę zadań gotowych do pracy i wszystkich zadań z czwartego pola loadavg.', 'Podziel pole zawierające ukośnik.', "awk '{split($4,a,\"/\"); print \"runnable=\"a[1], \"tasks=\"a[2]}' /proc/loadavg"],
['Pokaż ostatni przydzielony PID zapisany w loadavg.', 'To piąte pole, nie PID najbardziej obciążającego procesu.', "awk '{print $5}' /proc/loadavg"],
['Sprawdź liczbę procesorów logicznych dostępnych dla bieżącego procesu.', 'nproc uwzględnia ograniczenia dostępności procesorów.', 'nproc'],
['Porównaj liczbę dostępnych procesorów z liczbą wszystkich zainstalowanych procesorów logicznych.', 'Użyj obu wariantów nproc.', 'nproc\nnproc --all'],
['Pokaż architekturę i topologię procesorów.', 'lscpu opisuje również wątki i rdzenie.', 'lscpu'],
['Wylicz iloraz średniej jednominutowej i liczby dostępnych procesorów; nie traktuj go jako procentu CPU.', 'Przekaż wynik nproc jako zmienną awk.', 'awk -v n="$(nproc)" \'{printf "load1/cpus=%.2f\\n", $1/n}\' /proc/loadavg'],
['Wyświetl użytkowników i ich bieżące polecenia wraz z nagłówkiem uptime.', 'Program w łączy te dane.', 'w'],
['Pokaż sesje użytkowników bez nagłówka.', 'Opcja -h programu w usuwa nagłówek.', 'w -h'],
['Wyświetl stan, PID, użytkownika i polecenie procesów.', 'Użyj jawnych kolumn ps zamiast grep po całym tekście.', 'ps -eo state,pid,user,cmd'],
['Wyświetl procesy w stanie D bez dopasowania litery D w nazwie polecenia.', 'Filtruj dokładnie pierwszą kolumnę.', 'ps -eo state,pid,user,cmd | awk \'$1 == "D"\''],
['Pokaż procesy w stanie R.', 'Porównuj stan w pierwszym polu.', 'ps -eo state,pid,user,cmd | awk \'$1 == "R"\''],
['Policz widoczne procesy według stanu, pomijając nagłówek.', 'ps z state= nie wypisuje nagłówka.', 'ps -eo state= | sort | uniq -c | sort -rn'],
['Pokaż stany i identyfikatory wszystkich widocznych wątków.', 'Opcja -L włącza wątki; lwp to identyfikator wątku.', 'ps -eL -o state,pid,lwp,comm'],
['Zbierz trzy próbki vmstat co sekundę.', 'Pierwszy raport ma inną podstawę czasową niż następne.', 'vmstat 1 3'],
['Z drugiego raportu vmstat pokaż r, b, us, sy, id i wa.', 'Użyj dwóch raportów i ostatniej linii.', 'vmstat 1 2 | tail -1 | awk \'{print "r="$1, "b="$2, "us="$13, "sy="$14, "id="$15, "wa="$16}\''],
['Odczytaj presję CPU, jeżeli jądro udostępnia PSI.', 'Sprawdź czytelność pliku przed cat.', 'if [ -r /proc/pressure/cpu ]; then cat /proc/pressure/cpu; fi'],
['Odczytaj presję pamięci i wejścia/wyjścia, jeśli pliki istnieją.', 'Sprawdzaj osobno oba pliki.', 'for zasob in memory io; do\n  if [ -r "/proc/pressure/$zasob" ]; then cat "/proc/pressure/$zasob"; fi\ndone'],
['Wyświetl wiersze some ze wszystkich dostępnych plików PSI wraz z nazwą zasobu.', 'grep z kilkoma plikami pokazuje nazwy.', 'grep "^some " /proc/pressure/cpu /proc/pressure/memory /proc/pressure/io'],
['Pokaż pierwszą i drugą liczbę z /proc/uptime.', 'To czas działania oraz łączny czas idle procesorów.', 'cat /proc/uptime'],
['Zapisz pięć próbek loadavg co sekundę w nowym katalogu tymczasowym i pokaż ścieżkę.', 'mktemp -d tworzy własny katalog; nie nadpisuj istniejących raportów.', 'katalog=$(mktemp -d)\nfor i in 1 2 3 4 5; do\n  printf "%s " "$(date -Is)" >> "$katalog/load.txt"\n  cat /proc/loadavg >> "$katalog/load.txt"\n  sleep 1\ndone\nprintf "%s\\n" "$katalog/load.txt"'],
['Przelicz hipotetyczne load 6.0 na 2 i 8 procesorów logicznych.', 'To obliczenie modelu, nie rzeczywisty pomiar CPU.', 'awk \'BEGIN {printf "2 CPUs: %.2f; 8 CPUs: %.2f\\n", 6/2, 6/8}\''],
['Dla hipotetycznej próbki 8.0 4.0 1.0 wypisz rosnacy, jeśli średnia krótkoterminowa przewyższa długoterminową.', 'Porównaj pierwsze i trzecie pole.', 'printf "8.0 4.0 1.0\\n" | awk \'$1 > $3 {print "rosnacy"}\''],
['Dla próbki 1.0 4.0 8.0 wypisz malejacy przy spadku średniej krótkoterminowej.', 'Warunek jest odwrotny niż przy trendzie rosnącym.', 'printf "1.0 4.0 8.0\\n" | awk \'$1 < $3 {print "malejacy"}\''],
['Znajdź ścieżkę cgroup bieżącej powłoki, aby sprawdzić zakres jej ograniczeń.', 'Nie zakładaj z góry ścieżki system.slice.', 'cat /proc/$$/cgroup'],
['Zbierz pojedynczy raport top do diagnostyki razem z loadavg i liczbą dostępnych CPU.', 'Tryb batch nie wymaga interaktywnego terminala.', 'uptime\nnproc\ntop -bn1 | head -20']
];
const facts = [
['Co liczy load average w Linuksie?', 'Zadania działające lub gotowe do pracy oraz w nieprzerywalnym oczekiwaniu', 'Tylko procent zajętej pamięci|Wyłącznie liczbę użytkowników|Wyłącznie procesy zombie', 'Wliczane są stany R i D, dlatego wynik nie jest procentem CPU.'],
['W jakiej kolejności uptime pokazuje średnie?', '1, 5 i 15 minut', '15, 5 i 1 minuta|1, 10 i 60 sekund|5, 10 i 30 minut', 'Trzy wartości odnoszą się do krótkiego, średniego i długiego okna.'],
['Czy load average jest zwykłą średnią arytmetyczną próbek z ostatniej minuty?', 'Nie, to średnia wygładzana wykładniczo', 'Tak, zawsze z dokładnie 60 próbek|Tak, z wszystkich próbek od startu|Nie, to maksimum z ostatniej sekundy', 'Starsze próbki stopniowo tracą wagę; nie ma ostrego odcięcia po 60 sekundach.'],
['Co oznacza stan R?', 'Zadanie pracuje na CPU lub jest gotowe do uruchomienia', 'Proces został usunięty|Proces czeka wyłącznie na hasło|Proces jest zombie', 'Stan runnable obejmuje także oczekiwanie na przydział procesora.'],
['Co oznacza stan D?', 'Nieprzerywalne oczekiwanie, często związane z I/O', 'Zakończony proces bez rodzica|Zawsze pełne wykorzystanie CPU|Zawsze oczekiwanie na klawiaturę', 'Powód oczekiwania trzeba ustalić osobno; sam stan nie wskazuje konkretnego dysku.'],
['Czy stan S zwykle wlicza się do load average?', 'Nie, przerywalny sen nie jest stanem R ani D', 'Tak, wszystkie śpiące zadania są liczone|Tak, ale tylko procesy roota|Tak, jeśli nazwa zawiera java', 'Zwykłe oczekiwanie procesu na zdarzenie nie obciąża tej metryki.'],
['Czy proces zombie zwykle zwiększa load average?', 'Nie, stan Z nie oznacza zadania oczekującego na CPU', 'Tak, każdy zombie liczy się jak dwa rdzenie|Tak, bo stale wykonuje kod|Tak, wyłącznie po restarcie', 'Zombie jest zakończonym procesem oczekującym na odebranie statusu przez rodzica.'],
['Czy load average liczy tylko osobne procesy?', 'Nie, uwzględnia zadania, w tym wątki', 'Tak, wątki nigdy nie mają stanu R|Tak, każda JVM zawsze wnosi najwyżej jeden|Nie, liczy wyłącznie pliki', 'Wielowątkowy proces może wnieść wiele aktywnych zadań.'],
['Co pokazuje nproc?', 'Liczbę jednostek przetwarzania dostępnych bieżącemu procesowi', 'Liczbę wszystkich działających procesów|Liczbę plików w procfs|Procent wolnej pamięci', 'Dostępność może być ograniczona przez affinity i inne mechanizmy środowiska.'],
['Co pokazuje nproc --all?', 'Liczbę zainstalowanych jednostek przetwarzania', 'Wszystkie PID-y w systemie|Wyłącznie fizyczne gniazda CPU|Liczbę użytkowników', 'Wariant --all pomija część ograniczeń dostępności bieżącego procesu.'],
['Dlaczego procesory logiczne nie są tym samym co fizyczne rdzenie?', 'SMT może udostępniać kilka wątków sprzętowych na jeden rdzeń', 'Każdy procesor logiczny jest osobnym serwerem|Rdzeń fizyczny jest zawsze plikiem|Linux nie obsługuje SMT', 'Topologię można sprawdzić w lscpu; wydajność SMT nie jest prostym podwojeniem.'],
['Który program pokazuje topologię CPU?', 'lscpu', 'lsblk|lsusb|lsmod', 'lscpu opisuje gniazda, rdzenie i wątki sprzętowe.'],
['Gdzie odczytasz surowe load average?', '/proc/loadavg', '/etc/loadavg|/var/log/loadavg.conf|/dev/loadavg', 'To wirtualny plik udostępniany przez jądro.'],
['Co znaczy pole 3/420 w loadavg?', 'Trzy aktualnie runnable zadania z 420 zadań', '3 procent CPU z 420 procent maksymalnych|3 użytkowników i 420 plików|Trzeci rdzeń ma 420 MHz', 'To chwilowy licznik zadań, a nie czwarta średnia obciążenia.'],
['Co oznacza ostatnie pole loadavg?', 'Ostatnio przydzielony PID', 'PID najbardziej obciążającego procesu|Liczbę rdzeni|Liczbę sekund do restartu', 'Nie służy do wskazywania winnego dużego obciążenia.'],
['Co pokazuje uptime -p?', 'Czytelny czas działania', 'Listę procesów|Presję dysku|Zawartość swap', 'Opcja pretty ogranicza wynik do czasu działania.'],
['Co pokazuje uptime -s?', 'Datę i godzinę startu systemu', 'Sumę wszystkich PID-ów|Tylko bieżącą sekundę|Listę usług', 'Opcja since pomaga ustalić, czy nastąpił restart.'],
['Czy liczba users z uptime jest liczbą wszystkich kont?', 'Nie, odnosi się do zalogowanych sesji', 'Tak, liczy wpisy passwd|Tak, liczy także zablokowane konta|Nie, to liczba procesów roota', 'Jedna osoba może mieć kilka sesji; konta bez sesji nie podnoszą licznika.'],
['Które narzędzie pokazuje sesje i ich bieżące polecenia?', 'w', 'wc|which|whereis', 'Nagłówek w zawiera także dane uptime.'],
['Co robi w -h?', 'Pomija nagłówek', 'Wyłącza historię powłoki|Wyświetla tylko root|Usuwa sesje', 'Opcja zmienia sposób wyświetlania, nie działanie zalogowanych procesów.'],
['Czy load 4 na czterech CPU dowodzi 100% wykorzystania CPU?', 'Nie, część zadań może być w stanie D', 'Tak, niezależnie od I/O|Tak, każdy load jest procentem|Nie, zawsze oznacza 4% CPU', 'Potrzebne są metryki wykorzystania CPU i oczekiwania na zasoby.'],
['Co sugeruje wysoki load przy niskim us+sy i wysokim wa?', 'Warto sprawdzić I/O oraz zadania D', 'Na pewno trzeba dokupić CPU|Na pewno zabrakło kont użytkowników|Na pewno wyłączono wszystkie dyski', 'To hipoteza diagnostyczna, którą potwierdza się dodatkowymi pomiarami.'],
['Czy samo wa wskazuje dokładny dysk odpowiedzialny za problem?', 'Nie, potrzebna jest diagnostyka urządzeń i procesów', 'Tak, numer dysku jest zakodowany w procentach|Tak, zawsze winne jest sda|Tak, zawsze winny jest swap', 'iostat i dane procesów pozwalają zawęzić przyczynę.'],
['Które stany procesów warto zestawić z wysokim load?', 'R i D', 'Wyłącznie Z|Wyłącznie T|Wyłącznie I', 'R pomaga wykryć presję CPU, a D nieprzerywalne oczekiwanie.'],
['Dlaczego grep D po całym ps daje fałszywe dopasowania?', 'Litera D może wystąpić w poleceniu lub innych kolumnach', 'grep zawsze zmienia stan procesów|ps szyfruje stany|D nie jest literą ASCII', 'Należy filtrować kolumnę stanu zamiast dowolnego tekstu.'],
['Po co ps -eL przy diagnozie JVM?', 'Aby zobaczyć osobne wątki', 'Aby zakończyć wszystkie JVM|Aby zmienić limit pamięci|Aby uruchomić profiler heap', 'Load może wynikać z wielu runnable wątków jednego procesu.'],
['Co oznacza r w vmstat?', 'Zadania runnable', 'Liczbę restartów|Rozmiar dysku|Liczbę czytelników pliku', 'To licznik kolejki gotowej do pracy, przydatny obok liczby CPU.'],
['Co oznacza b w vmstat?', 'Zadania w nieprzerywalnym śnie', 'Procent buforów CPU|Liczbę połączeń HTTP|Liczbę błędnych haseł', 'Wysokie b może towarzyszyć problemom z I/O.'],
['Dlaczego pierwszy raport vmstat wymaga innej interpretacji niż następne?', 'Wiele jego pól przedstawia średnie od startu systemu', 'Pierwszy raport zawsze zawiera losowe dane|Pierwszy raport dotyczy wyłącznie roota|Pierwszy raport usuwa cache', 'Następne raporty przy interwale pokazują aktywność z okresu próbkowania.'],
['Co oznacza id w vmstat?', 'Procent czasu idle CPU', 'Identyfikator dysku|PID jądra|Liczbę sesji', 'To metryka czasu CPU; nie jest średnim obciążeniem.'],
['Co oznacza st w vmstat na maszynie wirtualnej?', 'Czas CPU zabrany przez hiperwizor', 'Liczbę plików stat|Liczbę uruchomionych usług|Rozmiar stosu', 'Wysoki steal może wskazywać współdzielenie zasobów hosta.'],
['Co oznacza us w vmstat?', 'Czas CPU w kodzie użytkownika', 'Liczbę użytkowników|Sekundy działania UPS|Rozmiar swap', 'Kod aplikacji wykonuje się w przestrzeni użytkownika.'],
['Co oznacza sy w vmstat?', 'Czas CPU w kodzie systemowym', 'Liczbę dowiązań symbolicznych|Wersję systemu|Liczbę gniazd sieciowych', 'Obsługa wywołań systemowych zwiększa czas jądra.'],
['Co mierzy PSI?', 'Czas przestojów zadań spowodowanych presją na zasoby', 'Wyłącznie procent zapełnienia dysku|Wyłącznie temperaturę CPU|Wyłącznie liczbę zalogowanych kont', 'PSI mierzy wpływ braku zasobów na postęp pracy.'],
['Gdzie znajdują się systemowe pliki PSI?', '/proc/pressure/', '/etc/pressure/|/dev/pressure/|/home/pressure/', 'Jądro udostępnia osobne pliki cpu, memory i io.'],
['Co oznacza some w PSI?', 'Czas, gdy przynajmniej część zadań czekała na zasób', 'Wszystkie zadania zakończyły się poprawnie|Nie istnieją procesy|Zawsze brak jakiejkolwiek presji', 'Some nie wymaga zatrzymania wszystkich aktywnych zadań naraz.'],
['Co oznacza full dla presji pamięci lub I/O?', 'Czas, gdy wszystkie niebezczynne zadania jednocześnie były zatrzymane przez zasób', 'Zawsze 100% zapełnienia dysku|Liczbę wszystkich plików|Zawsze kompletne użycie RAM', 'Full opisuje utratę postępu całej aktywnej pracy w danym zakresie.'],
['Jakie okna mają avg10, avg60 i avg300 w PSI?', '10, 60 i 300 sekund', '10, 60 i 300 minut|1, 5 i 15 minut|10, 60 i 300 godzin', 'Są inne niż okna load average.'],
['W jakiej jednostce podawane są średnie PSI?', 'W procentach czasu', 'W megabajtach|W liczbie rdzeni|W żądaniach na sekundę', 'Przykładowo avg10=20 oznacza presję przez około 20% czasu w tym trendzie.'],
['W jakiej jednostce jest total w PSI?', 'Mikrosekundy skumulowanego czasu przestojów', 'Megabajty pamięci|Liczba sesji|Stopnie Celsjusza', 'To licznik kumulacyjny; różnica dwóch odczytów umożliwia analizę okresu.'],
['Czy systemowe cpu full równe zero wyklucza presję CPU?', 'Nie, systemowe CPU full jest niezdefiniowane i raportowane jako zero', 'Tak, zawsze oznacza brak kolejki|Tak, cpu some też musi być zerowe|Nie, zero zawsze oznacza uszkodzone jądro', 'Do systemowej presji CPU używa się przede wszystkim some.'],
['Co może oznaczać brak /proc/pressure?', 'PSI może być wyłączone lub niedostępne w danym środowisku', 'Na pewno brak procesora|Na pewno serwer nie działa|Na pewno dysk został sformatowany', 'Dostępność zależy od jądra i konfiguracji; brak pliku nie dowodzi braku presji.'],
['Gdzie cgroup v2 może udostępniać presję danej usługi?', 'W plikach cpu.pressure, memory.pressure i io.pressure jej cgroup', 'Wyłącznie w crontab|Wyłącznie w passwd|Wyłącznie w pliku JAR', 'Zakres cgroup pozwala odróżnić presję usługi od całego systemu.'],
['Dlaczego nie należy zawsze porównywać hostowego load z limitem CPU kontenera?', 'Metryki mogą mieć różny zakres i nie odzwierciedlać tylko kontenera', 'Kontener zawsze ma własne fizyczne CPU|Load w kontenerze zawsze równa się zeru|CPU kontenera nie ma limitów', 'Trzeba ustalić namespace i zakres metryk oraz ograniczenia cgroup.'],
['Czy samo nproc ujawnia wszystkie możliwe ograniczenia wydajności kontenera?', 'Nie, trzeba sprawdzić także limity cgroup i współdzielenie zasobów', 'Tak, ujawnia nawet limity dysku|Tak, pokazuje wszystkie limity sieci|Nie, zawsze zwraca jeden', 'Liczba dostępnych CPU nie opisuje całego budżetu zasobów.'],
['Dlaczego warto zapisać czas przy próbce?', 'Aby powiązać obciążenie z logami i zdarzeniami', 'Aby wyzerować load|Aby zatrzymać czas systemowy|Aby usunąć zadania D', 'Korelacja czasowa pomaga ustalić przyczynę skoku obciążenia.'],
['Dlaczego jeden odczyt ps może nie wyjaśnić load sprzed minuty?', 'ps pokazuje stan chwilowy, a load ma pamięć wcześniejszych stanów', 'ps zawsze pokazuje poprzedni dzień|Load nigdy się nie zmienia|ps mierzy tylko pamięć dysku', 'Proces powodujący skok może już zakończyć pracę.'],
['Czy rosnące load1 względem load15 dowodzi ciągłego wzrostu w każdej sekundzie?', 'Nie, wskazuje raczej różnicę krótkiego i długiego trendu', 'Tak, nigdy nie ma wahań|Tak, to dokładna pochodna obciążenia|Nie, zawsze wskazuje spadek', 'Wygładzanie ukrywa krótkie zmiany i nie opisuje każdego momentu.'],
['Dlaczego wysoki load po zakończeniu ciężkiego zadania nie znika od razu?', 'Średnie wygładzane opadają stopniowo', 'Zadanie musi nadal istnieć jako zombie|Load jest zapisywane na zawsze|Jądro czeka na restart cron', 'Dłuższe średnie zwykle reagują wolniej.'],
['Co daje top -bn1?', 'Jeden raport top w trybie batch', 'Sto raportów bez przerwy|Uruchomienie procesu z najwyższym priorytetem|Wyłączenie sortowania procesów w jądrze', 'To wygodny odczyt do skryptu bez interaktywnego interfejsu.'],
['Czy wysoki load sam w sobie wystarcza do zabicia procesu?', 'Nie, trzeba ustalić przyczynę i wpływ na usługę', 'Tak, zabij losowy PID|Tak, zawsze zabij PostgreSQL|Tak, zawsze restartuj host', 'Diagnostyka powinna poprzedzać działania przerywające pracę.'],
['Dlaczego nie testujemy wysokiego load nieograniczonym fork bomb?', 'Może uniemożliwić pracę systemu i wyczerpać zasoby', 'Fork bomb mierzy tylko wolny dysk|Fork bomb jest wymagany przez uptime|Fork bomb nigdy nie tworzy procesów', 'Do nauki wystarczają bezpieczne odczyty i kontrolowane modele.'],
['Jak interpretować niskie load przy złej latencji aplikacji?', 'Nie wyklucza problemu aplikacji, sieci lub zewnętrznej zależności', 'Dowodzi pełnej sprawności wszystkich usług|Dowodzi braku ruchu HTTP|Dowodzi zerowej liczby błędów', 'Metryka hosta nie zastępuje pomiaru latencji i błędów usługi.'],
['Czy sama wysoka liczba procesów oznacza wysoki load?', 'Nie, większość może spać w stanie S', 'Tak, każdy proces stale zajmuje rdzeń|Tak, liczba PID-ów jest load1|Nie, procesy nigdy nie wpływają na load', 'Liczy się stan zadań, a nie tylko ich istnienie.'],
['Dlaczego stan D nie musi oznaczać wyłącznie lokalnego dysku?', 'Oczekiwanie może wiązać się także z NFS lub inną operacją jądra', 'Stan D dotyczy tylko drukarki|Stan D jest stanem protokołu DNS|Stan D oznacza zawsze uszkodzony SSD', 'Przyczyny nieprzerywalnego oczekiwania są różne.'],
['Czy kill -9 gwarantuje natychmiastowe zniknięcie zadania D?', 'Nie, zadanie może oczekiwać na powrót z nieprzerywalnej operacji', 'Tak, zawsze natychmiast|Tak, ale tylko po renice|Nie, SIGKILL działa wyłącznie na zombie', 'Sygnał może czekać na obsłużenie po zakończeniu oczekiwania.'],
['Co pomaga ustalić, czy presja dotyczy CPU czy I/O?', 'Zestawienie vmstat, stanów zadań, PSI i metryk urządzeń', 'Wyłącznie nazwa hosta|Wyłącznie liczba kont|Wyłącznie data instalacji', 'Różne metryki uzupełniają się i pozwalają sprawdzić hipotezę.'],
['Dlaczego liczba sesji może być zerowa w działającym kontenerze?', 'Środowisko może nie rejestrować sesji jak pełny system z terminalami', 'Zero zawsze oznacza zatrzymany kontener|Zero oznacza brak użytkownika root|Zero oznacza brak procesów', 'Mechanizm sesji i narzędzia logowania różnią się między środowiskami.'],
['Co oznacza load1/cpus równe 2 przy zadaniach czysto CPU?', 'Średnio około dwa runnable zadania na jednostkę CPU, co sugeruje kolejkę', 'Dokładnie 2% użycia CPU|Dokładnie dwa fizyczne serwery|Dokładnie 2 GB RAM', 'Interpretacja zależy od rodzaju zadań; obecność D zmienia wniosek.'],
['Dlaczego wartości /proc/loadavg z dwóch poleceń mogą się różnić?', 'Odczyty wykonano w różnych chwilach', 'cat zawsze losuje wynik|awk zawsze dodaje procesy D|Plik jest zwykłym logiem edytowanym przez użytkownika', 'Wirtualne dane jądra zmieniają się podczas pracy.']
];
if (ex.length !== 30 || facts.length !== 60) throw new Error(`${ex.length}/${facts.length}`);
const scenarios = [];
const models = [
 [1, 0.25, 0.5, 1, 'malejący'], [2, 1, 2, 4, 'malejący'], [4, 2, 3, 6, 'malejący'], [8, 4, 8, 12, 'malejący'],
 [2, 6, 3, 1, 'rosnący'], [4, 8, 4, 2, 'rosnący'], [8, 16, 8, 4, 'rosnący'], [16, 12, 6, 3, 'rosnący'],
 [4, 4, 4, 4, 'zbliżony'], [8, 8, 8, 8, 'zbliżony']
];
for (const [cpus,l1,l5,l15,trend] of models) {
  scenarios.push([`Host ma ${cpus} CPU logicznych i load ${l1}, ${l5}, ${l15}. Ile wynosi load1/CPU?`, String(l1/cpus), [String(l1*cpus+1), String(l5+l15+10), String(l1/cpus+0.125)].join('|'), 'Dzielimy pierwszą średnią przez liczbę CPU; wynik nie jest procentem użycia CPU.']);
  scenarios.push([`Dla load ${l1}, ${l5}, ${l15} jaki jest trend krótkiej średniej względem długiej?`, trend, ['rosnący','malejący','zbliżony','niemożliwy do odczytania bez PID'].filter(x=>x!==trend).join('|'), 'Porównujemy pierwszą i trzecią wartość. Trend nie gwarantuje jednostajnej zmiany w każdej sekundzie.']);
}
const cases = [
 ['load=12 na 4 CPU, us=4%, sy=6%, wa=70%, b=10', 'Zbadać opóźnienia I/O i zadania D', 'Dokupić CPU bez dalszych pomiarów|Uznać 12 za 12% CPU|Zabić losowy proces'],
 ['load=12 na 4 CPU, us=90%, sy=9%, r=12, b=0', 'Zbadać procesy i wątki intensywnie korzystające z CPU', 'Szukać wyłącznie brakującego montowania|Uznać obciążenie za 12%|Wyczyścić historię powłoki'],
 ['load=0.2, aplikacja czeka 10 sekund na odpowiedź zewnętrznego API', 'Sprawdzić czasy i błędy zewnętrznej zależności', 'Uznać aplikację za sprawną na podstawie load|Zwiększyć liczbę użytkowników|Usunąć procfs'],
 ['load1=1, load15=15, ciężki eksport skończył się przed chwilą', 'Uwzględnić stopniowe opadanie długiej średniej', 'Uznać eksport za nadal aktywny bez sprawdzenia|Zrestartować cron|Podzielić load15 przez PID'],
 ['ps pokazuje 800 zadań S i tylko 1 zadanie R', 'Duża liczba śpiących zadań sama nie oznacza wysokiego load', 'Każde S liczy się jak aktywny rdzeń|Load musi wynosić 801|Wszystkie zadania S są zombie'],
 ['jedna JVM ma 20 wątków R na 4 CPU', 'Obejrzeć wątki i ich zużycie CPU', 'Założyć że JVM wnosi najwyżej jedno zadanie|Liczba wątków nie wpływa na load|Zwiększyć rozmiar logu jako pewne rozwiązanie'],
 ['io some=60%, io full=40%', 'Duża część czasu obejmuje przestoje I/O, także całej aktywnej pracy', 'Dysk jest zapełniony dokładnie w 40%|Wszystkie dane zostały utracone|Load wynosi dokładnie 60'],
 ['cpu some=30%, systemowe cpu full=0%', 'Nie wykluczać presji CPU mimo full=0', 'Uznać some za błąd pomiaru|Uznać że żadne zadanie nie czeka|Uznać 30 za liczbę rdzeni'],
 ['kontener ma limit zasobów, a procfs pokazuje obciążenie hosta', 'Sprawdzić zakres metryk i dane cgroup kontenera', 'Dzielić wszystko przez jeden bez sprawdzenia|Uznać hostowe load za własne CPU procentowe|Wyłączyć limity wszystkich kontenerów'],
 ['vmstat pokazuje wysokie st na maszynie wirtualnej', 'Sprawdzić dostępność CPU po stronie hiperwizora', 'Uznać st za wykorzystanie swap|Zwiększyć retencję cron|Zignorować metrykę bo dotyczy plików']
];
for (const [context,good,bad] of cases) {
 scenarios.push([`Który następny krok jest uzasadniony: ${context}?`,good,bad,'Wniosek powinien wynikać z rodzaju oczekiwania i zakresu pomiaru, a potem zostać sprawdzony dodatkowymi danymi.']);
}
const parse = [
 ['0.50 1.25 2.00 2/300 9001','Jaka jest średnia pięciominutowa?','1.25','0.50|2.00|300'],
 ['2.50 1.00 0.25 3/450 12000','Ile zadań jest teraz runnable?','3','450|12000|2.50'],
 ['1.00 2.00 3.00 4/600 13000','Jaki jest ostatni przydzielony PID?','13000','4|600|3.00'],
 ['0.10 0.20 0.30 1/200 7000','Ile jest wszystkich zadań w czwartym polu?','200','1|7000|0.30'],
 ['4.00 3.00 2.00 5/900 16000','Jaka jest średnia piętnastominutowa?','2.00','4.00|3.00|900'],
 ['some avg10=25.00 avg60=10.00 avg300=5.00 total=1000000','Jaki procent czasu pokazuje avg10?','25%','10%|5%|1000000%'],
 ['some avg10=2.00 avg60=4.00 avg300=8.00 total=9000000','Ile sekund trwa okno avg300?','300','8|9000000|18000'],
 ['some avg10=0.00 avg60=0.00 avg300=0.00 total=2000000','Ile sekund przestojów reprezentuje total?','2','2000000|2000|0'],
 ['some avg10=10.00 avg60=5.00 avg300=1.00 total=3000000','Która średnia opisuje najkrótszy trend?','avg10','avg60|avg300|total'],
 ['1.00 1.00 1.00 2/100 4321','Czy 4321 musi być PID-em winnego obciążenia?','Nie, to ostatni przydzielony PID','Tak, zawsze|Tak, jeśli istnieje|Tak, ale tylko dla roota']
];
for(const [input,q,good,bad] of parse) scenarios.push([`Odczyt: ${input}. ${q}`,good,bad,'Interpretuj wskazane pole według formatu metryki; nie myl średnich, liczników zadań i czasu.']);
const questions=[...facts,...scenarios];
if(questions.length!==100) throw new Error(questions.length);
const output=ex.map(([p,h,s])=>`#EX\nP: ${p}\nH: ${h}\nS:\n${s}\n`).join('')+questions.map(([q,g,b,e])=>`#Q\nQ: ${q}\n+ ${g}\n${b.split('|').map(x=>'- '+x).join('\n')}\nE: ${e}\n`).join('');
fs.writeFileSync('scripts/content-migration/lx_practice/_lx_28_01.txt', output);
