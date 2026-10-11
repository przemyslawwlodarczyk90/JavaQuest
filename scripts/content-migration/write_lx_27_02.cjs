// Autorska pula cwiczen i pytan do lekcji o skladni crontaba.
// Uruchomienie zapisuje zrodlo TXT; konwersje wykonuje lx_build_practice.js.
const fs = require('fs');
const schedules = [
  ['co minutę', '* * * * *', 'W każdej minucie', 'Wyłącznie o północy', 'Raz na godzinę', 'Raz na dobę', '30 10 11 10 *', true],
  ['co godzinę o pełnej godzinie', '0 * * * *', 'O minucie 0 każdej godziny', 'Co minutę', 'Tylko o godzinie 0', 'Co 60 dni', '01 10 11 10 *', false],
  ['codziennie o 02:30', '30 2 * * *', 'Codziennie o 02:30', 'Codziennie o 30:02', 'Co dwie godziny w minucie 30', 'Drugiego dnia miesiąca o północy', '30 14 11 10 *', false],
  ['codziennie o 14:05', '5 14 * * *', 'Codziennie o 14:05', 'Codziennie o 05:14', 'Co 14 minut', 'Piątego dnia miesiąca o 14:00', '05 14 11 10 *', true],
  ['co pięć minut', '*/5 * * * *', 'W minutach 0, 5, 10, …, 55 każdej godziny', 'Pięć minut po starcie demona i potem co godzinę', 'W każdej piątej godzinie', 'Tylko w minucie 5', '17 10 11 10 *', false],
  ['co kwadrans', '*/15 * * * *', 'W minutach 0, 15, 30 i 45', 'W minucie 15 każdej godziny', 'Co 15 sekund', 'Piętnastego dnia miesiąca', '45 10 11 10 *', true],
  ['co dwadzieścia minut', '*/20 * * * *', 'W minutach 0, 20 i 40', 'W minutach 20 i 40, bez minuty 0', 'Co 20 godzin', 'Tylko o 20:00', '50 10 11 10 *', false],
  ['w minutach 10 i 40 każdej godziny', '10,40 * * * *', 'Dwa razy na godzinę: w minutach 10 i 40', 'W każdej minucie od 10 do 40', 'O 10:40 raz dziennie', 'Co 40 minut od minuty 10', '40 10 11 10 *', true],
  ['codziennie o 06:00 i 18:00', '0 6,18 * * *', 'Codziennie o 06:00 i 18:00', 'Co minutę między 6 a 18', 'O 06:18', 'Szóstego i osiemnastego dnia miesiąca', '00 12 11 10 *', false],
  ['o pełnej godzinie od 08:00 do 17:00 codziennie', '0 8-17 * * *', 'Dziesięć razy dziennie, od 08:00 do 17:00 włącznie', 'Co minutę od 8 do 17', 'Raz dziennie o 08:17', 'Dziewięć razy, bez 17:00', '00 17 11 10 *', true],
  ['co kwadrans w godzinach 8–17 w dni robocze', '*/15 8-17 * * mon-fri', 'Co kwadrans od 08:00 do 17:45, od poniedziałku do piątku', 'Co kwadrans do 17:00, także w weekendy', 'Raz dziennie o 08:15', 'Co 15 godzin w dni robocze', '45 17 12 10 mon', true],
  ['w poniedziałki o 07:00', '0 7 * * 1', 'W każdy poniedziałek o 07:00', 'Codziennie o 07:01', 'Pierwszego dnia miesiąca o 07:00', 'W każdą niedzielę o 07:00', '00 07 13 10 tue', false],
  ['w piątki o 18:30', '30 18 * * fri', 'W każdy piątek o 18:30', 'Codziennie o 18:30', 'W piątki o 18:00', 'W piątki co 30 godzin', '30 18 16 10 fri', true],
  ['w soboty i niedziele o 09:00', '0 9 * * sat,sun', 'W weekendy o 09:00', 'W dni robocze o 09:00', 'Codziennie między 9 a 24', 'Tylko w soboty o 09:00', '00 09 12 10 mon', false],
  ['w niedziele o 23:10', '10 23 * * 0', 'W niedziele o 23:10', 'W poniedziałki o 23:10', 'Codziennie o 23:10', 'Zapis jest błędny, bo 0 nie oznacza dnia tygodnia', '10 23 11 10 sun', true],
  ['w niedziele o 04:00, używając numeru 7', '0 4 * * 7', 'W niedziele o 04:00', 'Siódmego dnia miesiąca o 04:00', 'Co siedem godzin', 'W soboty o 04:00', '00 04 12 10 mon', false],
  ['pierwszego dnia każdego miesiąca o 00:10', '10 0 1 * *', 'Pierwszego dnia miesiąca o 00:10', 'Każdego poniedziałku o 00:10', 'Codziennie o 01:10', 'Co dziesięć miesięcy', '10 00 01 11 sun', true],
  ['piętnastego dnia każdego miesiąca o 12:00', '0 12 15 * *', 'Piętnastego dnia każdego miesiąca o 12:00', 'Co piętnaście minut o godzinie 12', 'Codziennie o 12:15', 'Dwunastego dnia miesiąca o 15:00', '00 12 14 10 wed', false],
  ['pierwszego i piętnastego dnia miesiąca o 03:00', '0 3 1,15 * *', 'W dniach 1 i 15 każdego miesiąca o 03:00', 'Codziennie od dnia 1 do 15 o 03:00', 'Co piętnaście dni od startu crona', 'W poniedziałki o 03:15', '00 03 15 10 thu', true],
  ['codziennie w styczniu o 05:00', '0 5 * jan *', 'Każdego dnia stycznia o 05:00', 'Pierwszego stycznia o 05:00', 'Codziennie o 05:01', 'W każdy poniedziałek stycznia', '00 05 11 10 sun', false],
  ['pierwszego stycznia o 00:00', '0 0 1 1 *', 'Raz w roku: 1 stycznia o północy', 'Codziennie o 01:01', 'Pierwszego dnia każdego miesiąca', 'W każdy poniedziałek stycznia', '00 00 01 01 fri', true],
  ['pierwszego dnia każdego kwartału o 00:00', '0 0 1 */3 *', '1 stycznia, kwietnia, lipca i października o północy', '1 marca, czerwca, września i grudnia', 'Co 90 dni od instalacji tabeli', 'W każdy trzeci dzień miesiąca', '00 00 01 11 sun', false],
  ['codziennie o 08:00 od czerwca do sierpnia', '0 8 * 6-8 *', 'Codziennie w czerwcu, lipcu i sierpniu o 08:00', 'Codziennie między 6 a 8 rano', 'Tylko 6 i 8 sierpnia', 'Co osiem miesięcy', '00 08 15 07 wed', true],
  ['w poniedziałki i środy o 16:00', '0 16 * * mon,wed', 'W poniedziałki i środy o 16:00', 'Od poniedziałku do środy codziennie', 'W pierwszy i trzeci dzień miesiąca', 'Co 16 godzin', '00 16 13 10 tue', false],
  ['co dziesięć minut w pierwszej połowie każdej godziny, włącznie z minutą 30', '0-30/10 * * * *', 'W minutach 0, 10, 20 i 30', 'W minutach 10, 20, 30 i 40', 'W każdej minucie od 0 do 30', 'W minutach 0 i 30', '30 10 11 10 sun', true],
  ['w minutach 5, 15, 25, 35, 45 i 55', '5-55/10 * * * *', 'Co dziesięć minut, zaczynając od minuty 5', 'W minutach 0, 10, 20, 30, 40 i 50', 'Co pięć minut do 55', 'Raz na godzinę o minucie 55', '00 10 11 10 sun', false],
  ['codziennie o 00:00, 06:00, 12:00 i 18:00', '0 */6 * * *', 'Cztery razy na dobę, w godzinach 0, 6, 12 i 18', 'Co sześć minut', 'Tylko o 06:00', 'Sześć godzin po starcie demona', '00 18 11 10 sun', true],
  ['o 22:00 w dni od pierwszego do siódmego każdego miesiąca', '0 22 1-7 * *', 'W dniach 1–7 każdego miesiąca o 22:00', 'Tylko w pierwszy poniedziałek miesiąca', 'W każdym dniu tygodnia przez cały miesiąc', 'Co siedem dni od pierwszego dnia miesiąca', '00 22 08 10 thu', false],
  ['o 03:00 w dni 1–7 miesiąca lub w dowolny poniedziałek', '0 3 1-7 * 1', 'W dniach 1–7 oraz w każdy poniedziałek o 03:00', 'Tylko w pierwszy poniedziałek miesiąca', 'Tylko w poniedziałki przypadające w dniach 1–7', 'W poniedziałki poza dniami 1–7', '00 03 12 10 mon', true],
  ['o 08:00 trzynastego dnia miesiąca lub w dowolny piątek', '0 8 13 * fri', 'Każdego trzynastego oraz w każdy piątek o 08:00', 'Tylko w piątek trzynastego', 'Tylko trzynastego, gdy nie jest piątkiem', 'W piątki o 08:13', '00 08 14 10 wed', false],
];
const facts = [
 ['Ile pól czasu ma klasyczny crontab użytkownika?', 'Pięć', 'Cztery', 'Sześć, z sekundami', 'Siedem', 'Pola to minuta, godzina, dzień miesiąca, miesiąc i dzień tygodnia. Polecenie występuje po nich.'],
 ['Która kolejność pól jest poprawna?', 'Minuta, godzina, dzień miesiąca, miesiąc, dzień tygodnia', 'Sekunda, minuta, godzina, dzień, miesiąc', 'Godzina, minuta, miesiąc, dzień miesiąca, dzień tygodnia', 'Dzień tygodnia, miesiąc, dzień miesiąca, godzina, minuta', 'Klasyczny cron zaczyna od minut; kolejność pól ma znaczenie.'],
 ['Jaki jest zakres pola minuty?', '0–59', '1–60', '0–60', '1–59', 'Minuta 60 nie istnieje w tej składni, a minuta 0 oznacza pełną godzinę.'],
 ['Jaki jest zakres pola godziny?', '0–23', '1–24', '0–24', '1–12', 'Cron używa zegara 24-godzinnego z północą oznaczoną jako 0.'],
 ['Jaki zakres liczbowy dopuszcza pole dnia miesiąca?', '1–31', '0–30', '0–31', '1–365', 'Poprawność składni nie gwarantuje istnienia daty: 31 lutego nie występuje.'],
 ['Jakie numery miesięcy można podać?', '1–12', '0–11', '0–12', '1–13', 'Styczeń ma numer 1, grudzień 12.'],
 ['Co oznacza gwiazdka w polu miesiąca?', 'Każdy miesiąc', 'Tylko bieżący miesiąc', 'Brak uruchomień', 'Miesiąc startu demona', 'Gwiazdka dopuszcza każdą wartość danego pola.'],
 ['Co oznacza przecinek w polu czasu?', 'Listę wskazanych wartości', 'Zakres obejmujący wszystkie wartości pomiędzy', 'Krok powtarzania', 'Komentarz', 'Lista 2,8 wybiera wyłącznie 2 i 8, w przeciwieństwie do zakresu 2-8.'],
 ['Czy zakres 8-17 w polu godziny obejmuje 17?', 'Tak, oba końce zakresu są włączone', 'Nie, końcowa wartość jest wyłączona', 'Tylko przy kroku /1', 'Tylko w weekendy', 'Zakres w cronie obejmuje wartości od początku do końca włącznie.'],
 ['Do czego służy ukośnik w zapisie */10?', 'Określa krok w obrębie danego pola', 'Dzieli czas przez liczbę procesów', 'Oddziela miesiąc od dnia', 'Wprowadza ścieżkę polecenia', 'Krok dotyczy zakresu pojedynczego pola; nie jest odstępem odmierzanym od ostatniego wykonania.'],
 ['Dlaczego */7 w polu minuty nie zapewnia zawsze siedmiu minut przerwy?', 'Sekwencja minut zaczyna się ponownie w każdej godzinie', 'Cron zaokrągla 7 do 5', 'Krok działa tylko w polu miesiąca', 'Cron wykonuje zadania co siedem sekund', 'Pasują minuty 0,7,14,…,56; między 56 a 0 następnej godziny są cztery minuty.'],
 ['Co oznacza */2 w polu miesiąca?', 'Styczeń, marzec, maj, lipiec, wrzesień i listopad', 'Luty, kwiecień, czerwiec, sierpień, październik i grudzień', 'Co dwa miesiące od instalacji tabeli', 'Dwa razy w każdym miesiącu', 'Krok rozpoczyna się od minimum pola: dla miesięcy jest to 1.'],
 ['Czy cron uruchomi wpis z datą 31 lutego?', 'Nie, taka data nie istnieje', 'Tak, 28 lutego', 'Tak, 1 marca', 'Tak, w ostatnim dniu lutego', 'Cron dopasowuje istniejące daty i nie przesuwa automatycznie nieistniejącego dnia na koniec miesiąca.'],
 ['Czy wpis 0 0 29 2 * wykona się każdego roku?', 'Nie, tylko gdy w lutym wystąpi dzień 29', 'Tak, zawsze 28 lutego', 'Tak, zawsze 1 marca', 'Nie wykona się nigdy', '29 lutego występuje w latach przestępnych.'],
 ['Jaka relacja łączy ograniczony dzień miesiąca i ograniczony dzień tygodnia w klasycznym cronie?', 'LUB', 'Zawsze I', 'Wykluczające LUB', 'Pole dnia tygodnia jest ignorowane', 'Jeżeli oba pola dnia są ograniczone, pasujący dzień miesiąca lub dzień tygodnia wystarcza.'],
 ['Ile uruchomień o 03:00 będzie jednego dnia, gdy w 0 3 1-7 * 1 pasują oba pola dnia?', 'Jedno', 'Dwa', 'Siedem', 'Zero', 'Reguła LUB dopasowuje termin; nie tworzy dwóch kopii zadania w tej samej minucie.'],
 ['Jaki wpis uruchamia zadanie w pierwszy poniedziałek miesiąca o 03:00?', '0 3 1-7 * * [ "$(date +\\%u)" = 1 ] && /home/anna/bin/raport.sh', '0 3 1-7 * 1 /home/anna/bin/raport.sh', '0 3 * * 1 /home/anna/bin/raport.sh', '0 3 1 * * /home/anna/bin/raport.sh', 'Cron ogranicza dni do 1–7, a test w powłoce wybiera poniedziałek. Znak procentu trzeba w poleceniu crona poprzedzić ukośnikiem odwrotnym.'],
 ['Dlaczego date +%u trzeba zapisać w poleceniu crona jako date +\\%u?', 'Niechroniony procent ma specjalne znaczenie dla crona', 'Cron nie obsługuje dat', 'Powłoka zawsze usuwa procent', 'Procent oznacza modulo w każdym poleceniu', 'Cron przetwarza procent w części polecenia; poprzedzenie go ukośnikiem odwrotnym pozwala przekazać go do date.'],
 ['Czemu odpowiada @hourly?', '0 * * * *', '* 0 * * *', '0 0 * * *', '60 * * * *', 'Skrót oznacza pełną godzinę, nie godzinę po poprzednim wykonaniu.'],
 ['Czemu odpowiada @daily?', '0 0 * * *', '0 * * * *', '* * 1 * *', '24 0 * * *', 'Zadanie startuje codziennie o północy według strefy używanej przez demona.'],
 ['Który skrót jest synonimem @daily?', '@midnight', '@noon', '@day', '@everyday', '@midnight i @daily opisują ten sam harmonogram.'],
 ['Czemu odpowiada @weekly?', '0 0 * * 0', '0 0 7 * *', '0 0 * * 1', '0 7 * * *', 'Tygodniowy skrót oznacza północ w niedzielę.'],
 ['Czemu odpowiada @monthly?', '0 0 1 * *', '0 0 * 1 *', '0 0 30 * *', '0 1 * * *', 'Miesięczny skrót wybiera pierwszy dzień każdego miesiąca.'],
 ['Czemu odpowiada @yearly?', '0 0 1 1 *', '0 0 31 12 *', '0 0 * 1 *', '0 0 1 * *', 'Roczny skrót oznacza północ 1 stycznia.'],
 ['Który skrót jest synonimem @yearly?', '@annually', '@annual', '@year', '@once', '@annually jest drugim zapisem tego samego rocznego harmonogramu.'],
 ['Kiedy wykonuje się zadanie @reboot?', 'Po starcie demona cron', 'Co minutę do pierwszego restartu', 'Przy każdym logowaniu użytkownika', 'Tylko po zmianie pliku crontab', 'Nazwa sugeruje rozruch systemu, ale zdarzeniem jest start demona; szczegóły zachowania zależą od implementacji.'],
 ['Czy @reboot gwarantuje gotową sieć i bazę danych?', 'Nie, sam skrót nie deklaruje zależności od usług', 'Tak, czeka na wszystkie usługi', 'Tak, czeka pięć minut', 'Tak, ale tylko dla roota', 'Do zależności między usługami lepiej użyć odpowiedniej konfiguracji systemd.'],
 ['Jak wyłączyć wysyłanie poczty przez cron w tabeli?', 'MAILTO=""', 'MAILTO=false', 'MAILTO=off', 'NO_MAIL=1', 'Pusta wartość MAILTO wyłącza pocztę; tekst false lub off może zostać potraktowany jako adres odbiorcy.'],
 ['Po co jawnie ustawiać PATH w crontabie?', 'Aby określić katalogi wyszukiwania programów przez zadania', 'Aby wskazać katalog roboczy zadania', 'Aby ustawić strefę czasową', 'Aby wyłączyć logowanie', 'PATH dotyczy wyszukiwania programów. Nie jest tym samym co bieżący katalog.'],
 ['Jak wybrać Bash jako powłokę poleceń w tabeli?', 'SHELL=/bin/bash', 'PATH=/bin/bash', 'BASH=true', 'USER=bash', 'SHELL wskazuje powłokę uruchamiającą część polecenia wpisu.'],
 ['Czy CRON_TZ=Europe/Warsaw działa w każdej implementacji crona?', 'Nie, obsługę trzeba sprawdzić w dokumentacji lokalnego demona', 'Tak, jest obowiązkowe wszędzie', 'Tak, ale wyłącznie dla roota', 'Nie, CRON_TZ nigdy nie istnieje', 'CRON_TZ jest rozszerzeniem dostępnym m.in. w Cronie; nie wolno zakładać jego obsługi w każdym demonie.'],
 ['Czy ustawienie TZ w środowisku polecenia zawsze zmienia jego harmonogram?', 'Nie, strefa programu i strefa dopasowania harmonogramu to różne rzeczy', 'Tak, zawsze zmienia wszystkie wpisy demona', 'Tak, ale dopiero po wykonaniu zadania', 'TZ służy wyłącznie do ustawienia języka', 'Zmienna TZ może wpłynąć na date i inne programy, bez zmiany strefy, według której cron dopasowuje pola czasu.'],
 ['Jak sprawdzić strefę czasową systemu z systemd?', 'timedatectl', 'crontab -z', 'timezone --cron', 'date --set-cron-zone', 'timedatectl pokazuje strefę systemu; osobno należy sprawdzić ewentualne rozszerzenia demona.'],
 ['Gdzie umieścić komentarz opisujący wpis?', 'W osobnej linii zaczynającej się od #', 'W szóstym polu czasu', 'Zamiast pola miesiąca', 'Przed minutą w tej samej linii', 'Osobna linia komentarza jest czytelna i nie staje się częścią polecenia.'],
 ['Czy komentarz po poleceniu jest usuwany przez parser crontaba?', 'Nie, trafia do części polecenia i może zostać zinterpretowany przez powłokę', 'Tak, cron usuwa zawsze wszystko po #', 'Tak, ale tylko w niedziele', 'Powoduje zawsze odrzucenie całej tabeli', 'W komentarzu na osobnej linii decyduje cron; wewnątrz polecenia znaczenie # zależy od powłoki i cytowania.'],
 ['Ile pól czasu ma wyrażenie Spring @Scheduled(cron=...)?', 'Sześć, z sekundami na początku', 'Pięć, jak klasyczny cron', 'Siedem, z obowiązkowym rokiem', 'Cztery, bez dnia tygodnia', 'Przenosząc harmonogram między Springiem i crontabem trzeba uwzględnić pole sekund.'],
 ['Jak zapisać codziennie 02:30 w wyrażeniu Springa?', '0 30 2 * * *', '30 2 * * *', '30 0 2 * * *', '0 2 30 * * *', 'Pierwsze 0 oznacza sekundy; kolejne pola to minuta 30 i godzina 2.'],
 ['Czy MON#1 można bezpośrednio wkleić do klasycznego crontaba?', 'Nie, to rozszerzenie spoza klasycznej składni crona', 'Tak, oznacza każdy poniedziałek', 'Tak, oznacza pierwszy dzień miesiąca', 'Tak, ale tylko w polu minuty', 'Rozszerzenia obsługiwane przez Spring lub Quartz nie są automatycznie dostępne w demonie cron.'],
 ['Jak sprawdzać następne terminy klasycznego harmonogramu za pomocą croniter?', 'Utworzyć iterator z wyrażenia i daty bazowej, następnie wywołać get_next', 'Uruchomić crontab --next', 'Uruchomić cron --dry-run-next', 'Odczytać pole NextElapse w crontab -l', 'Croniter oblicza terminy; nie uruchamia zadania i wymaga osobnej instalacji biblioteki.'],
 ['Czy systemd-analyze calendar przyjmuje bezpośrednio */5 * * * *?', 'Nie, trzeba podać wyrażenie kalendarzowe systemd', 'Tak, wszystkie składnie są identyczne', 'Tak, jeśli poprzedzić wpis sudo', 'Tak, jeśli dopisać nazwę użytkownika', 'Składnia OnCalendar różni się od pięciu pól crona. Narzędzie pomaga po przełożeniu harmonogramu.'],
];
// Niezalezna kontrola dopasowan uzytych w pytaniach scenariuszowych.
const aliases = {jan:1,mon:1,tue:2,wed:3,thu:4,fri:5,sat:6,sun:0};
const value = x => aliases[x] ?? Number(x);
function matchField(field, actual, min, max, weekday = false) {
  return field.split(',').some(part => {
    const [range, stepText] = part.split('/');
    const step = Number(stepText || 1);
    const bounds = range === '*' ? [min,max] : range.includes('-') ? range.split('-').map(value) : [value(range),value(range)];
    for (let n = bounds[0]; n <= bounds[1]; n += step) if ((weekday && n === 7 ? 0 : n) === actual) return true;
    return false;
  });
}
for (const row of schedules) {
  const [,expression,,,,,instant,expected] = row;
  const f = expression.split(' ');
  const t = instant.split(' ').map(x => x === '*' ? 0 : value(x));
  const dom = matchField(f[2],t[2],1,31);
  const dow = matchField(f[4],t[4],0,7,true);
  const days = f[2] !== '*' && f[4] !== '*' ? dom || dow : dom && dow;
  const actual = matchField(f[0],t[0],0,59) && matchField(f[1],t[1],0,23) && matchField(f[3],t[3],1,12) && days;
  if (actual !== expected) throw new Error(`Bledny scenariusz: ${expression} / ${instant}`);
}
let out = '';
const addQ = (q, good, bad, explanation) => { out += `#Q\nQ: ${q}\n+ ${good}\n${bad.map(x => '- ' + x).join('\n')}\nE: ${explanation}\n`; };
for (const [description, expression] of schedules) {
  out += `#EX\nP: Przygotuj wpis crontaba użytkownika uruchamiający /home/anna/bin/raport.sh ${description}. Zapisz go w pliku ~/harmonogram-proba.txt i wyświetl plik do przeglądu.\nH: Kolejność pól: minuta, godzina, dzień miesiąca, miesiąc, dzień tygodnia. Użyj printf; plik jest szkicem tabeli.\nS:\n$ printf '%s\\n' '${expression} /home/anna/bin/raport.sh' > ~/harmonogram-proba.txt\n$ cat ~/harmonogram-proba.txt\n${expression} /home/anna/bin/raport.sh\n`;
}
for (const [description, expression, good, ...rest] of schedules) {
  const [b1, b2, b3, instant, matches] = rest;
  addQ(`Jak odczytać harmonogram ${expression}?`, good, [b1,b2,b3], `Wpis oznacza: ${description}. Pola czytamy w kolejności minuta, godzina, dzień miesiąca, miesiąc, dzień tygodnia; przy dwóch ograniczonych polach dnia obowiązuje LUB.`);
  const [minute,hour,day,month,dow] = instant.split(' ');
  const dayName = {mon:'poniedziałek',tue:'wtorek',wed:'środa',thu:'czwartek',fri:'piątek',sat:'sobota',sun:'niedziela','*':'dzień tygodnia dowolny'}[dow];
  addQ(`Dla wpisu ${expression} rozważ lokalny czas: miesiąc ${month}, dzień ${day}, ${dayName}, godzina ${hour}:${minute}. Czy pola harmonogramu pasują? Pomijamy zmiany czasu i dostępność demona.`, matches ? 'Tak, wszystkie wymagane warunki czasu są spełnione' : 'Nie, ten termin nie pasuje do harmonogramu', [matches ? 'Nie, ten termin nie pasuje do harmonogramu' : 'Tak, wszystkie wymagane warunki czasu są spełnione', 'Nie można ocenić pól bez znajomości PID demona', 'Wpis pasuje wyłącznie wtedy, gdy użytkownik jest zalogowany'], `Harmonogram oznacza: ${description}. Podany termin ${matches ? 'spełnia' : 'nie spełnia'} te warunki. Dopasowanie pól jest niezależne od PID i logowania użytkownika.`);
}
for (const [q,good,b1,b2,b3,e] of facts) addQ(q,good,[b1,b2,b3],e);
fs.writeFileSync('scripts/content-migration/lx_practice/_lx_27_02.txt', out, 'utf8');
console.log(`Zapisano ${schedules.length} cwiczen i ${schedules.length * 2 + facts.length} pytan.`);
