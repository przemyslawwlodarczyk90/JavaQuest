// Generuje lekcje _ns_00_wprowadzenie/03_EthicsScopeAndLaw.json.
// Uzycie: node scripts/content-migration/netsec/lessons/ns00_03_ethics_lesson.cjs src/main/resources/content/_ns_00_wprowadzenie/03_EthicsScopeAndLaw.json
const { quizBuilder, exerciseBuilder, writeLesson } = require('./lib.cjs');

const img = (file, alt) => ({ image: 'netsec/' + file, imageAlt: alt });

// ---------- TEORIA ----------
const theory = [
  { type: 'INTRO', heading: 'Ta sama czynność: praca obrońcy albo przestępstwo', ...img('ns00-03-decision-tree.svg', 'Drzewo decyzji: pytanie, czy system jest wyłącznie mój — jeśli tak, ćwiczę w izolowanym labie; jeśli nie, czy mam pisemną zgodę właściciela — bez niej STOP; ze zgodą, czy czynność mieści się w zakresie i oknie czasowym — jeśli nie, STOP i pisemne rozszerzenie zakresu, jeśli tak, działam według zasad zaangażowania, loguję czynności i zgłaszam krytyczne odkrycia.'),
    body:
`Skan portów, przechwycenie ruchu, próba logowania z typowymi hasłami, odczyt cudzego pliku konfiguracyjnego — technicznie to te same czynności, które wykonuje analityk SOC, pentester i przestępca. Różnica nie leży w narzędziach, tylko w **uprawnieniu, zakresie i celu**.

Ta lekcja jest krótka w poleceniach, ale jedna z najważniejszych w całym torze, bo:
- **chroni Ciebie** — „tylko sprawdzałem” nie jest obroną; nieuprawniony dostęp do systemu jest w Polsce przestępstwem,
- **chroni innych** — testy bez zgody mogą położyć produkcję, ujawnić dane osobowe, uruchomić procedurę incydentu u kogoś innego,
- **jest kompetencją zawodową** — każda praca obrońcy (testy, przechwytywanie ruchu, analiza skrzynek pracowników w incydencie, symulacje phishingu, purple team) wymaga podstawy prawnej i udokumentowanej zgody. Egzaminy Security+ i CySA+ pytają o zasady zaangażowania, ujawnianie podatności i zgodność z prawem.

Zasada toru jest prosta: **wszystkie ćwiczenia ofensywne wykonujesz wyłącznie we własnym, izolowanym laboratorium, na danych syntetycznych.** Gdy przypadkiem znajdziesz problem w cudzym systemie — nie „sprawdzasz dalej”, tylko zgłaszasz go odpowiedzialnie.

**Zastrzeżenie:** lekcja omawia przepisy w uproszczeniu, w celach edukacyjnych. Nie jest poradą prawną; przepisy się zmieniają — w realnych sprawach konsultuj się z prawnikiem i działem prawnym pracodawcy.` },

  { type: 'DEFINITION', heading: 'Pojęcia: autoryzacja, zakres, zasady zaangażowania, ujawnianie', body:
`- **Autoryzacja** — wyraźna, najlepiej pisemna zgoda **właściciela systemu** (lub osoby uprawnionej do jej udzielenia) na określone czynności. Zgoda kolegi z zespołu czy administratora bez uprawnień do decyzji — nie wystarcza.
- **Zakres (scope)** — jawnie wyliczone systemy, adresy, domeny, aplikacje i konta, których dotyczy zgoda; wszystko inne jest **poza zakresem**.
- **Zasady zaangażowania (Rules of Engagement, RoE)** — jak wolno działać: metody, intensywność, okno czasowe, wyłączenia (np. DoS, socjotechnika), kontakty, procedura przerwania, postępowanie z danymi.
- **Bug bounty** — program, w którym organizacja zaprasza badaczy do szukania podatności na określonych zasadach i nagradza zgłoszenia. Regulamin programu jest zakresem i RoE.
- **VDP (Vulnerability Disclosure Policy)** — publiczna polityka przyjmowania zgłoszeń podatności (często bez nagród), z deklaracją, że zgłaszający działający w dobrej wierze nie będzie ścigany (*safe harbor*).
- **security.txt** — plik \`/.well-known/security.txt\` (RFC 9116) z kontaktem do zgłaszania problemów bezpieczeństwa.
- **CVD (Coordinated Vulnerability Disclosure)** — skoordynowane ujawnianie: zgłoszenie, naprawa, potem publikacja w uzgodnionym terminie; w UE wyznaczone zespoły CSIRT pełnią rolę koordynatorów (w Polsce m.in. CERT Polska).
- **Dane syntetyczne** — wygenerowane dane testowe zamiast prawdziwych danych osobowych.
- **Kontratyp** — okoliczność wyłączająca bezprawność czynu (w kontekście tej lekcji: art. 269c Kodeksu karnego — o ścisłych warunkach, omówione dalej).` },

  { type: 'ANALOGY', heading: 'Ślusarz, klucz i cudze drzwi', body:
`Ślusarz potrafi otworzyć niemal każdy zamek. Gdy otwiera drzwi **na zlecenie właściciela**, który pokazał dokument, jest fachowcem. Gdy otwiera **cudze drzwi „żeby sprawdzić, czy zamek jest dobry”**, jest włamywaczem — nawet jeśli niczego nie zabrał i zostawił kartkę „polecam lepszy zamek”.

Kilka dalszych podobieństw:
- **Zlecenie ma zakres** — wymiana zamka w drzwiach wejściowych nie upoważnia do otwierania sejfu w sypialni.
- **Zlecenie ma czas** — umówiona wizyta we wtorek nie oznacza wejścia w niedzielę o 3:00.
- **Zgoda musi pochodzić od właściciela** — lokator, który „pozwolił”, nie może zgodzić się na otwarcie piwnicy sąsiada.
- **Gdy zauważysz otwarte okno w cudzym domu**, możesz zadzwonić do właściciela albo administracji — ale nie wchodzisz do środka, żeby „zrobić zdjęcie dowodu”.

Obrońca w firmie jest jak ślusarz zatrudniony przez zarządcę budynku: ma szerokie uprawnienia, ale też procedury, które mówią, kiedy i jak z nich korzystać.` },

  { type: 'VISUAL_EXAMPLE', heading: 'Mapa przepisów Kodeksu karnego', ...img('ns00-03-law-map.svg', 'Tabela przepisów: art. 267 nieuprawniony dostęp, podsłuch i ujawnienie; art. 268 niszczenie lub utrudnianie dostępu do istotnej informacji; art. 268a niszczenie i zakłócanie danych informatycznych; art. 269 sabotaż danych szczególnego znaczenia; art. 269a zakłócanie pracy systemu lub sieci; art. 269b narzędzia i hasła do tych przestępstw; art. 269c niekaralność działań w celu zabezpieczenia systemu przy ścisłych warunkach.'),
    body:
`Przestępstwa przeciwko ochronie informacji opisuje rozdział XXXIII Kodeksu karnego. W uproszczeniu (sprawdzaj zawsze aktualny tekst ustawy):`,
    code:
`art. 267   § 1 uzyskanie informacji przez przełamanie lub ominięcie zabezpieczenia
           § 2 uzyskanie dostępu do całości lub części systemu informatycznego bez uprawnienia
               (samo „wejście” jest czynem zabronionym — nie trzeba niczego kraść)
           § 3 zakładanie lub posługiwanie się urządzeniem podsłuchowym lub oprogramowaniem
               do uzyskania informacji (np. nieuprawnione przechwytywanie ruchu)
           § 4 ujawnienie innej osobie informacji tak uzyskanej
           ściganie co do zasady na wniosek pokrzywdzonego
art. 268   niszczenie, uszkadzanie, usuwanie lub zmiana istotnej informacji, utrudnianie zapoznania się z nią
art. 268a  niszczenie, uszkadzanie, usuwanie, zmiana danych informatycznych, utrudnianie dostępu do nich,
           zakłócanie ich przetwarzania (np. szyfrowanie danych przez ransomware)
art. 269   jak wyżej wobec danych o szczególnym znaczeniu (obronność, administracja, komunikacja) — surowsze kary
art. 269a  istotne zakłócenie pracy systemu lub sieci (np. atak DoS)
art. 269b  wytwarzanie, pozyskiwanie, zbywanie lub udostępnianie narzędzi przystosowanych do popełniania
           powyższych przestępstw oraz haseł i kodów dostępu
art. 269c  nie podlega karze (za czyny z art. 267 § 2 lub 269a), kto działa WYŁĄCZNIE w celu zabezpieczenia
           systemu lub opracowania metody zabezpieczenia, NIEZWŁOCZNIE powiadomi dysponenta o zagrożeniach,
           a jego działanie nie naruszyło interesu publicznego ani prywatnego i nie wyrządziło szkody
           (kontratyp o wąskich warunkach — to nie „licencja na testowanie”)

Inne źródła obowiązków: RODO (dane osobowe), Kodeks pracy (monitoring pracowników), ustawa o KSC
(obowiązki podmiotów kluczowych i ważnych), umowy i NDA, regulaminy programów bug bounty.` },

  { type: 'CODE_BASIC', heading: 'Plik security.txt: jak firma mówi „zgłaszaj tutaj”', body:
`Jako developer możesz sprawić, że Twoja aplikacja ułatwia odpowiedzialne zgłaszanie. Plik \`/.well-known/security.txt\` (RFC 9116) wskazuje kontakt i politykę. W Spring Boot wystarczy plik statyczny w \`src/main/resources/static/.well-known/security.txt\`.`,
    code:
`Contact: mailto:security@javaquest.example
Contact: https://javaquest.example/security/report
Expires: 2027-11-13T00:00:00.000Z
Encryption: https://javaquest.example/security/pgp-key.txt
Acknowledgments: https://javaquest.example/security/hall-of-fame
Preferred-Languages: pl, en
Policy: https://javaquest.example/security/vdp
Canonical: https://javaquest.example/.well-known/security.txt

# Pole Expires jest wymagane (RFC 9116) — plik z przeterminowaną datą traktuje się jako nieaktualny.
# Najlepiej podpisać plik OpenPGP (cleartext signature).
# Sprawdzenie u siebie: curl -s https://<twoja-domena>/.well-known/security.txt` },

  { type: 'STEP_BY_STEP', heading: 'Przypadkiem znalazłeś podatność w cudzym systemie — co robisz?', ...img('ns00-03-disclosure.svg', 'Pięć kroków skoordynowanego ujawniania: odkrycie bez eksploatacji i kopiowania danych, zgłoszenie przez security.txt, VDP lub CERT Polska, potwierdzenie i ocena przez właściciela, naprawa, publikacja po naprawie lub w uzgodnionym terminie. Poniżej czerwona lista zakazów i zielona lista dobrych praktyk.'),
    body:
`Scenariusz: korzystając normalnie z serwisu sklepu, zauważasz, że zmiana numeru zamówienia w URL pokazuje cudze zamówienie (BOLA/IDOR).
1. **Zatrzymaj się.** Nie sprawdzaj kolejnych numerów, nie pobieraj danych „na dowód”, nie automatyzuj. Każda dalsza czynność zwiększa ryzyko prawne i szkodę dla osób, których dane widzisz.
2. **Zanotuj minimalne fakty**: data i godzina, adres URL (z zamaskowanymi danymi), co zrobiłeś, co zobaczyłeś. Nie zapisuj cudzych danych osobowych.
3. **Znajdź oficjalny kanał**: \`/.well-known/security.txt\`, strona „bezpieczeństwo/VDP”, program bug bounty. Jeśli go nie ma lub nikt nie odpowiada — zgłoś do **CERT Polska** (koordynacja ujawnień).
4. **Zgłoś rzeczowo**: opis problemu, kroki do odtworzenia na *własnych* danych (np. dwa Twoje konta), potencjalny wpływ, Twój kontakt. Bez gróźb, bez żądania pieniędzy, bez terminów-ultimatum.
5. **Zachowaj poufność** do czasu naprawy i uzgodnienia publikacji (typowo do 90 dni).
6. **Nie kontynuuj testów** bez zgody — nawet jeśli firma nie odpowiada.
7. **Po naprawie** możesz — za zgodą lub zgodnie z polityką — opisać przypadek (bez danych osobowych).

Jeśli to system Twojego **pracodawcy** — zgłoś wewnętrznie (zespół bezpieczeństwa, przełożony) zgodnie z polityką firmy; nie „testuj dalej na własną rękę”.` },

  { type: 'NOTE', heading: 'Dokument zakresu i zasady zaangażowania', ...img('ns00-03-scope-roe.svg', 'Lista elementów dokumentu zakresu: strony i podpisy, cel, zakres in scope, wyłączenia out of scope, okno czasowe, dozwolone metody, kontakty i eskalacja, dane i poufność, raport.'),
    body:
`Każda czynność testowa poza własnym labem — pentest, skan podatności, ćwiczenie purple team, symulacja phishingu, przechwytywanie ruchu w sieci firmowej — zaczyna się od dokumentu. Minimalny szablon:`,
    code:
`ZAKRES I ZASADY ZAANGAŻOWANIA — ćwiczenie purple team „Detekcja ruchu bocznego”
Zleceniodawca:   Dyrektor ds. bezpieczeństwa, JavaQuest Sp. z o.o. (podpis)
Wykonawca:       Zespół SOC (lider: …)
Cel:             Sprawdzenie, czy SIEM wykrywa techniki T1021.002 (SMB) i T1047 (WMI)
Zakres:          VLAN 30 (10.30.0.0/24) — stacje testowe WS-T01…WS-T05; konto testowe svc-purple
Poza zakresem:   produkcja, kontrolery domeny produkcyjne, systemy płatności, DoS, socjotechnika
Okno:            2026-11-20 09:00–15:00 CET; przerwanie: telefon do dyżurnego SOC
Metody:          Atomic Red Team (lista testów w załączniku), bez eksfiltracji danych
Dane:            wyniki i logi w repozytorium SOC (dostęp ograniczony), usunięcie po 90 dniach
Eskalacja:       znalezisko krytyczne → natychmiast lider SOC + CISO
Raport:          do 2026-11-27, retest po wdrożeniu poprawek` },

  { type: 'CODE_PRACTICAL', heading: 'Lab-zero jako dokument zakresu (ćwiczenie)', body:
`Ćwiczenie z planu lekcji: przygotuj dokument zakresu dla testów we własnym labie. Choć lab jest Twój, pisanie zakresu buduje nawyk i pokazuje, czego brakuje w typowych zleceniach.`,
    code:
`# scope.md — lab-zero (własny, izolowany)
Właściciel i wykonawca: ja (laptop prywatny, WSL2 + Docker)
Cel:            ćwiczenie analizy ruchu TLS i testów bezpieczeństwa API (rozdz. 07, 23)
Zakres:         kontenery projektu lab-zero: caddy, app, postgres;
                sieci: lab-zero_edge, lab-zero_backend; port hosta 127.0.0.1:8443
Poza zakresem:  sieć domowa i jej urządzenia, router, Internet, inne projekty Dockera,
                konta i usługi w chmurze, dowolne systemy osób trzecich
Okno:           sesje ćwiczeniowe; po każdej: docker compose down -v
Metody:         curl, openssl, ZAP baseline, tcpdump w kontenerze netshoot
Dane:           wyłącznie syntetyczne (Datafaker); pcap poza repozytorium, usuwane po ćwiczeniu
Bezpieczeństwo: porty tylko na 127.0.0.1; ZAP tylko na https://127.0.0.1:8443
Kontakt w razie problemu: n/d (lab prywatny) — w firmie: dyżurny SOC` },

  { type: 'USAGE', heading: 'Etyka i prawo w codziennej pracy obrońcy', body:
`Obrońca ma często *więcej* dostępu niż ktokolwiek w firmie — i dlatego musi być wzorem dyscypliny:
- **Monitoring i przechwytywanie ruchu** — tylko w zakresie polityki bezpieczeństwa, z informacją dla pracowników o monitoringu (Kodeks pracy, art. 22³ — monitoring poczty służbowej wymaga m.in. określenia celu i zakresu oraz poinformowania pracowników) i zgodnie z RODO.
- **Dochodzenia wewnętrzne** — dostęp do skrzynek, plików i logów konkretnej osoby tylko w ramach procedury (zgłoszenie, zatwierdzenie przez dział prawny/HR, minimalny zakres, dokumentacja). Ciekawość nie jest podstawą prawną.
- **Skanowanie podatności i purple team** — zawsze z zatwierdzonym zakresem i oknem; nawet „wewnętrzny” skan może zakłócić systemy produkcyjne (stare urządzenia OT, drukarki).
- **Symulacje phishingu** — za zgodą kierownictwa, z planem komunikacji, bez zawstydzania pracowników i bez zbierania prawdziwych haseł.
- **Dane w incydencie** — dowody to często dane osobowe; dostęp według potrzeby, przechowywanie zabezpieczone, retencja zgodna z polityką; naruszenie ochrony danych może wymagać zgłoszenia do PUODO w 72 h.
- **Threat intelligence i OSINT** — zbieranie informacji z otwartych źródeł też ma granice: nie logujesz się na cudze konta, nie kupujesz wykradzionych danych, nie „wchodzisz” na infrastrukturę napastnika.
- **Kodeksy etyki zawodowej** — certyfikaty (np. ISC2, CompTIA) wymagają przestrzegania kodeksu etyki; jego naruszenie może skutkować odebraniem certyfikatu.` },

  { type: 'PITFALL', heading: 'Najczęstsze pułapki etyczne i prawne', body:
`- **„Tylko sprawdzałem, czy działa”** — art. 267 § 2 penalizuje sam nieuprawniony dostęp do systemu; intencja „pomocy” nie zmienia oceny, jeśli nie są spełnione warunki kontratypu.
- **Zgoda od niewłaściwej osoby** — administrator, kolega czy klient korzystający z usługi chmurowej nie może zgodzić się na test infrastruktury dostawcy (np. hostingu). Dostawcy chmury mają własne zasady testów.
- **Wyjście poza zakres** — „przy okazji” sprawdzony serwer sąsiedni, inna subdomena, test DoS mimo wyłączenia — to działanie bez autoryzacji.
- **Zbieranie „dowodów”** — pobranie bazy klientów, by udowodnić podatność, zamienia zgłoszenie w wyciek danych.
- **Publikacja przed naprawą** — „full disclosure” bez uzgodnienia naraża użytkowników i może naruszać prawo.
- **Żądanie zapłaty za nieujawnienie** — to wymuszenie, nie bug bounty.
- **Udostępnianie narzędzi i haseł** — rozpowszechnianie wykradzionych haseł lub narzędzi do popełniania przestępstw ma osobną penalizację (art. 269b).
- **Laby z prawdziwymi danymi** — kopia produkcji na laptopie to naruszenie zasad ochrony danych, nawet bez złych intencji.
- **Testy z sieci firmowej na systemy zewnętrzne** — ruch pochodzi z adresu firmy; to firma odbierze skargę lub zgłoszenie incydentu.` },

  { type: 'CODE_WRONG', heading: 'Źle: „pomocne” zgłoszenie, które jest problemem', body:
`Przykład wiadomości, która mimo dobrych intencji zawiera kilka poważnych błędów: dalsza eksploatacja, pobranie danych, presja czasowa i oczekiwanie zapłaty.`,
    code:
`Temat: Wasz sklep jest dziurawy!!!

Cześć, zmieniając numer zamówienia w URL, pobrałem skryptem 12 000 zamówień waszych klientów
(w załączniku CSV z imionami, adresami i telefonami jako dowód).
Sprawdziłem też panel /admin — hasło admin123 działa :)
Macie 48 godzin na odpowiedź, inaczej publikuję wszystko na forum.
Oczekuję nagrody 5000 zł za moją pracę.` },

  { type: 'CODE_RIGHT', heading: 'Dobrze: rzeczowe, minimalne i poufne zgłoszenie', body:
`To samo odkrycie zgłoszone odpowiedzialnie: bez dalszej eksploatacji, bez cudzych danych, z krokami odtworzenia na własnych kontach i propozycją koordynacji.`,
    code:
`Temat: [Bezpieczeństwo] Możliwy dostęp do cudzych zamówień przez identyfikator w URL

Dzień dobry,
13.11.2026 ok. 14:05, korzystając z własnego konta, zauważyłem, że po zmianie identyfikatora
w adresie /orders/{id} serwis wyświetla zamówienie innego klienta. Wyświetliłem jedno zamówienie,
nie zapisywałem jego danych i nie kontynuowałem prób.

Kroki do odtworzenia (na dwóch kontach testowych, które Państwo kontrolują):
1. Zaloguj się na konto A i otwórz własne zamówienie /orders/1001.
2. Zaloguj się na konto B i otwórz /orders/1001.
Oczekiwane: 403/404. Obserwowane: dane zamówienia konta A.

Możliwy wpływ: ujawnienie danych osobowych klientów (brak kontroli dostępu do obiektu — BOLA).
Chętnie odpowiem na pytania. Nie będę publikować informacji przed naprawą i uzgodnieniem terminu.
Jeśli nie otrzymam odpowiedzi, przekażę zgłoszenie do koordynacji CERT Polska.
— Imię Nazwisko, kontakt` },

  { type: 'WHEN_TO_USE', heading: 'Kiedy wolno, a kiedy trzeba się zatrzymać', body:
`- **Wolno**: dowolne ćwiczenia we własnym, izolowanym labie na danych syntetycznych; platformy treningowe zgodnie z ich regulaminem (CyberDefenders, BTLO, TryHackMe); programy bug bounty i VDP — ściśle w ich zakresie; testy w pracy na podstawie pisemnego zlecenia i RoE.
- **Wolno, ale z procedurą**: przechwytywanie ruchu i monitoring w firmie (polityka, informacja dla pracowników, RODO), dochodzenia wewnętrzne (dział prawny/HR), symulacje phishingu (zgoda kierownictwa), skany podatności (zatwierdzony zakres i okno).
- **Zatrzymaj się i zapytaj**: gdy czynność wykracza poza pisemny zakres, dotyczy systemu dostawcy lub klienta, może zakłócić produkcję albo ujawnić dane osobowe.
- **Nie wolno**: testy cudzych systemów bez zgody („tylko port scan”, „tylko jedno hasło”), korzystanie z wykradzionych danych, rozpowszechnianie narzędzi i haseł do popełniania przestępstw, publikacja podatności przed naprawą bez uzgodnienia, żądanie zapłaty za milczenie.
- **Przy wątpliwościach**: zatrzymaj się, udokumentuj, skonsultuj (przełożony, dział prawny, CERT Polska). Koszt pytania jest zawsze mniejszy niż koszt pomyłki.` },

  { type: 'SUMMARY', heading: 'Podsumowanie', body:
`- Różnica między pracą obrońcy a przestępstwem to **autoryzacja, zakres i cel** — nie narzędzia.
- Zgoda: pisemna, od właściciela systemu; zakres i okno wyliczone jawnie; RoE określa metody, wyłączenia, kontakty i postępowanie z danymi.
- Kodeks karny (rozdz. XXXIII): art. 267 (dostęp, podsłuch, ujawnienie), 268–268a (dane), 269 (dane szczególnego znaczenia), 269a (zakłócanie systemów), 269b (narzędzia i hasła), 269c (kontratyp o wąskich warunkach).
- Odkrycie w cudzym systemie: zatrzymaj się, minimalne notatki, oficjalny kanał (security.txt, VDP, CERT Polska), poufność, bez danych i bez żądań.
- W pracy obrońcy etyka dotyczy też monitoringu, dochodzeń, symulacji phishingu i danych w incydentach (RODO, Kodeks pracy).
- Ćwiczenia ofensywne w tym torze: **wyłącznie** we własnym, izolowanym labie, na danych syntetycznych.
- Następna lekcja: **podróż jednego żądania** — od URL do wiersza w bazie, krok po kroku.` },
];

// ---------- ZADANIA ----------
const { exercises, ex } = exerciseBuilder();
const scen = [
  ['Kolega z pracy prosi: „przeskanuj nmapem serwer mojego prywatnego bloga, pozwalam”. Blog stoi na współdzielonym hostingu.', 'Kolega może zgodzić się tylko na to, co należy do niego. Serwer i sieć należą do dostawcy hostingu, a skan dotknie też innych klientów. Bez zgody dostawcy (lub zgodnie z jego polityką testów) — nie skanuj infrastruktury; możesz testować aplikację kolegi w zakresie, na który dostawca pozwala, np. na lokalnej kopii.'],
  ['Na rozmowie rekrutacyjnej zostajesz poproszony, by „pokazać umiejętności” na stronie firmy rekrutującej.', 'Bez pisemnej zgody i zakresu — odmów i zaproponuj alternatywę: zadanie na przygotowanym środowisku firmy lub omówienie swoich labów i portfolio. Ustna prośba rekrutera nie jest autoryzacją właściciela systemu.'],
  ['W pracy zauważasz, że serwer partnera biznesowego (inna firma) ma otwarty panel administracyjny bez hasła.', 'Nie loguj się i nie „sprawdzaj”. Zgłoś wewnętrznie (zespół bezpieczeństwa, opiekun relacji z partnerem), by przekazali informację oficjalnym kanałem do partnera. Udokumentuj, jak zauważyłeś problem.'],
  ['Program bug bounty obejmuje *.sklep.example, wyklucza ataki DoS i socjotechnikę. Znajdujesz subdomenę stara.sklep.example z panelem Jenkins.', 'Subdomena pasuje do *.sklep.example, więc formalnie jest w zakresie — ale sprawdź listę wyłączeń i zasady (np. czy systemy stron trzecich są wyłączone). Testuj w granicach regulaminu (bez DoS), zgłoś znalezisko; przy wątpliwościach zapytaj program przed dalszymi krokami.'],
  ['Twój zespół SOC chce przeprowadzić ćwiczenie purple team w piątek o 16:00, bo „wtedy jest mało ruchu”. Dokument RoE określa okno pon.–czw. 9–15.', 'Piątek 16:00 jest poza zatwierdzonym oknem. Trzeba pisemnie zmienić RoE (zgoda zleceniodawcy) albo przesunąć ćwiczenie. Działanie poza oknem to działanie bez autoryzacji, a dodatkowo dyżur i inne zespoły mogą potraktować je jak prawdziwy incydent.'],
  ['Podczas analizy incydentu masz dostęp do skrzynki pocztowej pracownika podejrzanego o wyciek. Zauważasz w niej prywatną korespondencję medyczną.', 'Ogranicz się do zakresu dochodzenia (np. wiadomości z załącznikami wysłane na zewnątrz w okresie incydentu), nie czytaj i nie kopiuj korespondencji prywatnej niezwiązanej ze sprawą, udokumentuj zakres przeglądu. Dostęp musi wynikać z procedury zatwierdzonej przez dział prawny/HR; dane szczególnych kategorii (zdrowie) podlegają szczególnej ochronie RODO.'],
];
for (const [s, a] of scen) ex(`Oceń sytuację i opisz właściwe postępowanie: ${s}`, 'Zadaj trzy pytania: czyj to system, czy jest pisemna zgoda właściciela, czy czynność mieści się w zakresie.', a);
ex('Przygotuj dokument zakresu (scope.md) dla testów lab-zero według szablonu z lekcji: cel, zakres, wyłączenia, okno, metody, dane, bezpieczeństwo.', 'Wymień jawnie kontenery, sieci i porty; wyłącz wszystko, co nie jest Twoim labem.', 'Zakres: kontenery caddy/app/postgres projektu lab-zero, sieci lab-zero_edge/backend, port 127.0.0.1:8443. Wyłączenia: sieć domowa, router, Internet, inne projekty Dockera, chmura, systemy osób trzecich. Okno: sesje ćwiczeniowe. Metody: curl, openssl, ZAP baseline, tcpdump. Dane: syntetyczne; pcap poza repo. Bezpieczeństwo: porty na 127.0.0.1, sprzątanie down -v.');
ex('Dodaj do aplikacji Spring Boot plik /.well-known/security.txt zgodny z RFC 9116 i sprawdź curl, że jest serwowany z poprawnym Content-Type.', 'Plik w src/main/resources/static/.well-known/security.txt; pole Expires jest wymagane.', 'Zawartość: Contact, Expires (data w przyszłości, maks. rok), Policy, Preferred-Languages, Canonical. curl -i https://127.0.0.1:8443/.well-known/security.txt → 200, Content-Type: text/plain. Jeśli Spring Security blokuje ścieżkę, dodaj permitAll dla /.well-known/**.');
ex('Napisz test integracyjny (MockMvc lub WebTestClient), który sprawdza, że security.txt istnieje i pole Expires nie jest przeterminowane.', 'Sparsuj linię „Expires:” i porównaj z Instant.now().', 'mockMvc.perform(get("/.well-known/security.txt")).andExpect(status().isOk()).andDo(r -> {\n  String body = r.getResponse().getContentAsString();\n  String exp = body.lines().filter(l -> l.startsWith("Expires:")).findFirst().orElseThrow().substring(8).trim();\n  assertThat(Instant.parse(exp)).isAfter(Instant.now());\n});\nTest przypomni o odnowieniu pliku, zanim stanie się nieaktualny.');
ex('Przeczytaj regulamin dowolnego publicznego programu bug bounty (np. na platformie HackerOne, Bugcrowd lub YesWeHack) i wypisz: zakres, wyłączenia, zakazane techniki, zasady safe harbor i kanał zgłoszeń.', 'Szukaj sekcji „Scope”, „Out of scope”, „Rules”, „Safe harbor”.', 'Typowo: zakres — domeny i aplikacje wymienione z nazwy; wyłączenia — systemy stron trzecich, środowiska pracowników; zakazane — DoS, socjotechnika, fizyczne testy, dostęp do danych innych użytkowników ponad minimum; safe harbor — zobowiązanie organizacji do nieścigania działań zgodnych z regulaminem; kanał — formularz platformy.');
ex('Na podstawie mapy przepisów przypisz artykuł Kodeksu karnego do każdego czynu: a) atak DDoS na serwis gminy, b) instalacja keyloggera na komputerze współlokatora, c) zaszyfrowanie danych firmy ransomware, d) sprzedaż na forum zestawu wykradzionych haseł, e) zalogowanie się na cudze konto pocztowe zgadniętym hasłem.', 'Kierunek: dostęp (267), dane (268a), zakłócenie (269a), narzędzia/hasła (269b).', 'a) art. 269a (zakłócenie pracy systemu/sieci); b) art. 267 § 3 (oprogramowanie do przechwytywania informacji) i potencjalnie § 1/§ 2; c) art. 268a (zmiana/utrudnienie dostępu do danych), przy danych szczególnego znaczenia art. 269; d) art. 269b (udostępnianie haseł i kodów dostępu); e) art. 267 (uzyskanie dostępu do informacji/systemu bez uprawnienia). Opis uproszczony — ocena prawna zależy od okoliczności.');
ex('Wyjaśnij własnymi słowami warunki kontratypu z art. 269c KK i podaj przykład, kiedy NIE będzie on miał zastosowania mimo dobrych intencji.', 'Wymień: cel, powiadomienie, brak szkody i naruszenia interesu.', 'Warunki (w uproszczeniu): działanie wyłącznie w celu zabezpieczenia systemu lub opracowania metody zabezpieczenia; niezwłoczne powiadomienie dysponenta systemu o zagrożeniach; brak naruszenia interesu publicznego lub prywatnego i brak szkody. Nie zadziała np. gdy osoba pobrała dane klientów „na dowód” (naruszenie interesu prywatnego), zwlekała z powiadomieniem, opublikowała podatność lub zakłóciła działanie usługi. Kontratyp dotyczy wymienionych czynów (art. 267 § 2, 269a), nie wszystkich.');
ex('Przygotuj wewnętrzną procedurę (6–8 kroków) dla developera, który zauważy podatność w systemie własnej firmy.', 'Kanał zgłoszeń, dokumentacja, poufność, brak dalszych testów.', '1) Nie kontynuuj testów poza normalnym użyciem; 2) zanotuj fakty (czas, URL, kroki, wpływ) bez kopiowania danych; 3) zgłoś do zespołu bezpieczeństwa (adres/ticket typu security, nie publiczny kanał zespołu); 4) jeśli podatność jest krytyczna i aktywnie wykorzystywana — dyżur bezpieczeństwa telefonicznie; 5) nie dziel się szczegółami poza zespołem do czasu naprawy; 6) współpracuj przy odtworzeniu na środowisku testowym; 7) po naprawie — retest i lessons learned; 8) dodaj test regresji.');
ex('Zaprojektuj szablon zgody na symulację phishingu w firmie: kto zatwierdza, cele, zakres (kto jest celem), metody, ochrona pracowników, komunikacja po ćwiczeniu.', 'Pomyśl o HR, dziale prawnym i godności pracowników.', 'Zatwierdzają: zarząd/CISO, konsultacja z HR i działem prawnym (i radą pracowników, jeśli istnieje). Cel: mierzenie odsetka zgłoszeń i kliknięć, edukacja. Zakres: wybrane działy, wyłączenia (np. osoby na zwolnieniu, nowi pracownicy w pierwszym tygodniu). Metody: bez zbierania prawdziwych haseł (strona edukacyjna zamiast formularza z hasłem), bez treści wykorzystujących tematy wrażliwe. Ochrona: wyniki zagregowane, bez publicznego wskazywania osób. Komunikacja: krótkie szkolenie po kliknięciu, podsumowanie dla wszystkich.');
ex('Wyjaśnij, dlaczego test penetracyjny infrastruktury w chmurze publicznej wymaga sprawdzenia polityki dostawcy chmury, nawet jeśli testujesz własne konto.', 'Model współodpowiedzialności: Ty masz swoje zasoby, dostawca — platformę.', 'Zasoby na koncie (instancje, aplikacje) są Twoje, ale działają na infrastrukturze dostawcy współdzielonej z innymi klientami. Dostawcy publikują zasady testów (np. dozwolone usługi, zakaz DoS, zakaz testowania infrastruktury samej platformy). Działanie wbrew nim może naruszyć umowę i skutkować blokadą konta, a w skrajnych przypadkach dotknąć innych klientów.');
ex('Zrób listę 10 rodzajów czynności z tego toru, które wykonasz wyłącznie we własnym labie, i 5 takich, które w pracy wymagałyby pisemnej zgody.', 'Przejrzyj plan toru: rozdziały 18, 20, 23, 32, 37.', 'Tylko lab: ARP spoofing między kontenerami, SYN flood w labie, Kerberoasting na labowym AD, uruchamianie próbek malware, SQL injection na podatnej aplikacji, skan nmap labu, przechwytywanie ruchu z hasłami testowymi, emulacja przeciwnika (Caldera), testy DoS z netem, fałszowanie XFF. W pracy z pisemną zgodą: skany podatności sieci firmowej, purple team, symulacje phishingu, przechwytywanie ruchu w sieci produkcyjnej, testy aplikacji produkcyjnej (DAST).');
ex('Dlaczego zgłoszenie podatności powinno zawierać kroki odtworzenia na kontach zgłaszającego (lub testowych), a nie na danych innych użytkowników?', 'Myśl o minimalizacji szkody i dowodzie.', 'Kroki na własnych kontach dowodzą istnienia podatności bez ujawniania cudzych danych — minimalizują naruszenie prywatności i ryzyko prawne zgłaszającego, a dla właściciela są łatwe do zweryfikowania. Załączanie cudzych danych zamienia zgłoszenie w incydent wycieku (który firma może musieć zgłosić do PUODO).');
ex('Przygotuj krótką odpowiedź, którą jako zespół bezpieczeństwa firmy wyślesz badaczowi zgłaszającemu podatność przez security.txt (potwierdzenie przyjęcia).', 'Podziękowanie, numer sprawy, kolejne kroki, prośba o poufność.', 'Dzień dobry, dziękujemy za zgłoszenie. Nadaliśmy mu numer SEC-2026-0142. Analizujemy problem i odezwiemy się w ciągu 5 dni roboczych z oceną. Prosimy o nieujawnianie szczegółów do czasu naprawy — uzgodnimy z Panem/Panią termin publikacji. Jeśli w trakcie weryfikacji natrafi Pan/Pani na dane innych użytkowników, prosimy ich nie zapisywać. Zgodnie z naszą polityką VDP działania w dobrej wierze w jej zakresie nie będą przez nas ścigane.');
ex('Czym różni się VDP od programu bug bounty? Kiedy firma powinna mieć VDP, nawet jeśli nie stać jej na bug bounty?', 'Nagrody, zakres, formalność.', 'VDP: polityka przyjmowania zgłoszeń, zwykle bez nagród, z deklaracją dobrej wiary (safe harbor) i kanałem kontaktu. Bug bounty: aktywne zapraszanie badaczy z nagrodami, często przez platformę, ze szczegółowym zakresem. VDP powinna mieć każda organizacja z usługami w Internecie — to tani sposób, by zgłoszenia trafiały do właściwych osób zamiast na fora; dla producentów oprogramowania obsługa podatności jest też wymogiem CRA.');
ex('Analityk SOC podczas huntu znajduje serwer C2 napastnika w Internecie z otwartym panelem logowania. Kolega proponuje „zalogować się i zobaczyć, co ukradli”. Co odpowiesz?', 'Hack back i granice OSINT.', 'Nie: logowanie do infrastruktury napastnika to nieuprawniony dostęp do cudzego systemu (często przejętego serwera niewinnej osoby), może zniszczyć dowody i narazić firmę prawnie. Właściwie: zachowaj IoC (adres, domena, certyfikat), zablokuj komunikację u siebie, zgłoś do CERT Polska lub organów ścigania (przez dział prawny), współpracuj z dostawcą hostingu przez kanały abuse.');
ex('Zapisz w dzienniku swoje osobiste zasady etyczne jako obrońcy (5–7 punktów) i umieść je w README repozytorium.', 'Odwołaj się do lekcji: zgoda, zakres, dane, ujawnianie, lab.', 'Przykład: 1) Ćwiczenia ofensywne wyłącznie w moim izolowanym labie lub z pisemną zgodą. 2) Działam tylko w uzgodnionym zakresie i oknie. 3) Nie kopiuję cudzych danych, używam danych syntetycznych. 4) Podatności zgłaszam odpowiedzialnie, bez żądań i przed publikacją. 5) Dostęp z racji pracy wykorzystuję tylko w celu i zakresie zadania. 6) Przy wątpliwościach zatrzymuję się i pytam. 7) Dokumentuję swoje działania.');
ex('Porównaj dwa scenariusze z perspektywy RODO: a) w labie używasz wygenerowanej listy 10 000 klientów (Datafaker), b) w labie używasz anonimizowanej kopii produkcji. Czy b) jest bezpieczne?', 'Pseudonimizacja a anonimizacja; ryzyko reidentyfikacji.', 'a) Dane syntetyczne nie dotyczą realnych osób — poza zakresem RODO. b) Prawdziwa anonimizacja (nieodwracalna) wyłącza dane z RODO, ale w praktyce „anonimizowane” kopie często są tylko pseudonimizowane (da się odtworzyć tożsamość z kombinacji pól: kod pocztowy, data urodzenia, płeć) — wtedy nadal są danymi osobowymi. W labach preferuj dane syntetyczne.');
ex('W firmie objętej KSC (podmiot ważny) planujesz test odporności aplikacji. Jakie dodatkowe kwestie organizacyjne warto uwzględnić poza zgodą właściciela systemu?', 'Zarządzanie zmianą, wpływ na ciągłość, informowanie SOC/dostawców.', 'Zgłoszenie do zarządzania zmianą i ocena wpływu na dostępność usług kluczowych; uzgodnienie okna z biznesem; poinformowanie SOC i dostawców usług zarządzanych (MSSP), by nie uruchamiać procedury incydentu; zgoda dostawców chmury/hostingu; plan przerwania i kontakty; obsługa danych zgodna z polityką; raport jako dowód dla audytu (wymogi KSC/NIS2 dotyczące testowania i zarządzania ryzykiem).');
ex('Napisz krótką notatkę (do dziennika) o tym, czym różni się etyczny haker (pentester) od obrońcy blue team w zakresie potrzebnych zgód i dokumentów.', 'Pentester działa z zewnątrz systemu na zlecenie; obrońca — wewnątrz organizacji.', 'Pentester: każde zlecenie to osobna umowa, zakres i RoE, często NDA; działa w wyznaczonym oknie i raportuje. Obrońca: działa na podstawie stanowiska, polityk bezpieczeństwa i procedur (monitoring, IR, dochodzenia), ale czynności nadzwyczajne (purple team, skany produkcji, dostęp do danych konkretnych osób) wymagają osobnej zgody i dokumentacji. Obu obowiązuje minimalizacja danych i poufność.');
ex('Przygotuj listę kontrolną „przed każdym ćwiczeniem w labie” (5 pytań), która wymusza izolację i legalność.', 'Pytania tak/nie, które zadajesz przed docker compose up.', '1) Czy wszystkie cele ćwiczenia to moje kontenery/VM w labie? 2) Czy porty są opublikowane tylko na 127.0.0.1 (lub wcale)? 3) Czy ruch ćwiczeniowy nie może wyjść do Internetu ani LAN (internal, brak tras)? 4) Czy używam wyłącznie danych syntetycznych? 5) Czy wiem, jak posprzątać (down -v, przywrócenie migawki) po ćwiczeniu?');
ex('Twój znajomy prowadzi mały sklep internetowy i prosi, żebyś „rzucił okiem na bezpieczeństwo”. Przygotuj krótką pisemną zgodę, którą powinien podpisać, zanim cokolwiek sprawdzisz.', 'Kto jest właścicielem? Czy sklep stoi na platformie SaaS (np. hostowany sklep), która ma własne zasady?', 'Elementy: dane stron; potwierdzenie, że znajomy jest właścicielem/administratorem sklepu i może udzielić zgody (a jeśli sklep działa na platformie SaaS — że zasady platformy na to pozwalają lub test ogranicza się do konfiguracji konta); zakres (domena, panel, konta testowe), wyłączenia (DoS, dane klientów, płatności), okno czasowe, metody, obsługa danych, kontakt, podpis i data.');
ex('Wyjaśnij, dlaczego regulamin platformy treningowej (np. TryHackMe, CyberDefenders) jest dla Ciebie „dokumentem zakresu”. Co zwykle zabrania?', 'Platformy udostępniają maszyny celowo do ataku — ale tylko te.', 'Platforma jest właścicielem maszyn ćwiczeniowych i w regulaminie udziela zgody na ich testowanie. Zwykle zabrania: atakowania infrastruktury samej platformy i innych użytkowników, wykorzystywania zasobów do ataków na zewnątrz, publikowania rozwiązań aktywnych wyzwań (zależnie od zasad), automatyzacji obciążającej platformę.');
ex('Opisz, jak bezpiecznie przechowywać materiały z ćwiczeń purple team w firmie (logi, pcap, raporty), uwzględniając RODO i poufność.', 'Dostęp, szyfrowanie, retencja, minimalizacja.', 'Repozytorium z kontrolą dostępu (tylko zespół), szyfrowanie w spoczynku, oznaczenie klasyfikacji (np. poufne), minimalizacja (przefiltrowane wycinki zamiast pełnych przechwyceń), retencja zgodna z RoE (np. 90 dni), rejestr usunięcia, brak kopii w ticketach i na czatach, pseudonimizacja danych osób w raporcie.');
ex('Przeczytaj fragment własnego kodu lub projektu, nad którym pracujesz, i wskaż jedno miejsce, gdzie dodanie security.txt, VDP lub kontaktu bezpieczeństwa ułatwiłoby odpowiedzialne zgłaszanie problemów.', 'Strona produktu, repozytorium open source (SECURITY.md), aplikacja webowa.', 'Przykłady: plik SECURITY.md w repozytorium GitHub (zakładka Security → Security policy) z kanałem zgłoszeń i czasem odpowiedzi; security.txt w aplikacji Spring Boot; strona /security z polityką VDP. Dla producentów oprogramowania to także element zgodności z CRA (obsługa podatności).');
ex('Wyjaśnij różnicę między testem „black box”, „grey box” i „white box” oraz jak wpływa ona na treść dokumentu zakresu.', 'Ile informacji i dostępów dostaje wykonawca?', 'Black box: wykonawca nie zna szczegółów (symulacja zewnętrznego napastnika) — zakres musi jasno wyliczyć cele i wyłączenia, by nie testował przypadkiem cudzych systemów. Grey box: część wiedzy i konta testowe — RoE wskazuje konta i role. White box: pełny dostęp (kod, architektura, konfiguracje) — dokument określa zasady dostępu do kodu i danych (NDA, przechowywanie). We wszystkich przypadkach wymagana jest autoryzacja właściciela.');
if (exercises.length !== 30) throw new Error('zadania ' + exercises.length);

// ---------- QUIZ ----------
const { quiz, add } = quizBuilder(2026111403);
const OK = 'Dozwolone';
const NEED = 'Dozwolone tylko z pisemną zgodą właściciela i w uzgodnionym zakresie';
const NO = 'Niedozwolone';
const PROC = 'Dozwolone w ramach procedury firmy (polityka, zatwierdzenie, minimalny zakres)';
const verdicts = [OK, NEED, NO, PROC];
const cases = [
  ['Skan nmap kontenerów własnego labu na laptopie', OK], ['Skan nmap serwera sklepu internetowego, z którego korzystasz', NO], ['Test SQL injection na OWASP Juice Shop uruchomionym lokalnie', OK],
  ['Test SQL injection na stronie urzędu, „bo wygląda na podatną”', NO], ['Pentest aplikacji klienta na podstawie podpisanej umowy i RoE', NEED], ['Ćwiczenie purple team w sieci firmowej w zatwierdzonym oknie', NEED],
  ['Przechwytywanie ruchu w sieci hotelowej, by „sprawdzić bezpieczeństwo”', NO], ['Przechwytywanie ruchu na własnym bridge’u Dockera', OK], ['Przegląd skrzynki pracownika w dochodzeniu zatwierdzonym przez dział prawny', PROC],
  ['Czytanie poczty kolegi przez administratora z ciekawości', NO], ['Symulacja phishingu zatwierdzona przez zarząd, bez zbierania haseł', PROC], ['Logowanie się na panel serwera C2 napastnika', NO],
  ['Testowanie subdomeny wymienionej w zakresie programu bug bounty, bez DoS', NEED], ['Atak DoS na subdomenę z programu bug bounty, który wyklucza DoS', NO], ['Zgłoszenie zauważonej podatności przez security.txt bez dalszych testów', OK],
  ['Pobranie bazy klientów „na dowód” przed zgłoszeniem podatności', NO], ['Analiza próbki malware w izolowanej VM z migawką', OK], ['Uruchomienie Atomic Red Team na stacji produkcyjnej bez zgody', NO],
  ['Skan podatności całej sieci firmowej przez SOC zgodnie z harmonogramem zatwierdzonym przez CISO', PROC], ['Sprawdzenie, czy hasło „admin123” działa w panelu routera sąsiada', NO],
];
for (const [c, v] of cases) add(`Oceń czynność: ${c}.`, v, verdicts.filter((x) => x !== v), `Ocena: ${v.toLowerCase()}.`);
const arts = [
  ['Zalogowanie się bez uprawnienia do cudzego systemu informatycznego', 'art. 267 § 2'], ['Zainstalowanie oprogramowania przechwytującego cudze hasła', 'art. 267 § 3'], ['Atak DoS zakłócający pracę sieci', 'art. 269a'],
  ['Udostępnienie na forum wykradzionych haseł', 'art. 269b'], ['Zaszyfrowanie danych firmy przez ransomware', 'art. 268a'], ['Niekaralność działań wyłącznie w celu zabezpieczenia systemu przy spełnieniu warunków', 'art. 269c'],
  ['Ujawnienie innej osobie informacji uzyskanej przez nieuprawniony dostęp', 'art. 267 § 4'], ['Zniszczenie danych o szczególnym znaczeniu dla obronności', 'art. 269'],
];
const artAll = ['art. 267 § 2', 'art. 267 § 3', 'art. 267 § 4', 'art. 268a', 'art. 269', 'art. 269a', 'art. 269b', 'art. 269c'];
for (const [d, a] of arts) add(`Który przepis Kodeksu karnego (w uproszczeniu) dotyczy: ${d}?`, a, artAll.filter((x) => x !== a).slice(0, 3), `${d} — ${a} (opis uproszczony).`);
const roe = [['Zakres (in scope)', 'Jawna lista systemów, adresów i aplikacji objętych testem'], ['Wyłączenia (out of scope)', 'Systemy i techniki, których nie wolno testować'], ['Okno czasowe', 'Daty i godziny, w których wolno prowadzić test'], ['Kontakty i eskalacja', 'Osoby do powiadomienia przy problemie lub krytycznym znalezisku'], ['Procedura przerwania', 'Sposób natychmiastowego zatrzymania testu na żądanie'], ['Postępowanie z danymi', 'Zasady przechowywania, dostępu i usuwania dowodów']];
const roeAll = roe.map((x) => x[1]);
for (const [k, d] of roe) add(`Co opisuje element dokumentu RoE: „${k}”?`, d, roeAll.filter((x) => x !== d).slice(0, 3), `${k}: ${d.toLowerCase()}.`);
const terms = [
  ['Czym jest security.txt?', 'Plikiem /.well-known/security.txt z kontaktem do zgłaszania problemów bezpieczeństwa (RFC 9116)', ['Plikiem z hasłami serwera', 'Certyfikatem TLS', 'Listą użytkowników'], 'Pole Expires jest wymagane.'],
  ['Które pole jest wymagane w security.txt według RFC 9116 obok Contact?', 'Expires', ['Encryption', 'Acknowledgments', 'Hiring'], 'Plik bez aktualnej daty Expires uznaje się za nieaktualny.'],
  ['Czym jest VDP?', 'Polityką przyjmowania zgłoszeń podatności, zwykle z deklaracją safe harbor', ['Rodzajem firewalla', 'Programem antywirusowym', 'Protokołem VPN'], 'Vulnerability Disclosure Policy.'],
  ['Co oznacza safe harbor w polityce zgłaszania podatności?', 'Zobowiązanie organizacji, że nie będzie ścigać osób działających w dobrej wierze zgodnie z polityką', ['Bezpieczne przechowywanie haseł', 'Port w VPN', 'Kopię zapasową'], 'Dotyczy tylko działań zgodnych z zasadami polityki.'],
  ['Kto w Polsce może koordynować ujawnienie podatności, gdy właściciel nie odpowiada?', 'CERT Polska (CSIRT)', ['Urząd Skarbowy', 'Dowolne forum internetowe', 'Dostawca Internetu zgłaszającego'], 'W UE wyznaczone CSIRT-y pełnią rolę koordynatorów CVD.'],
  ['Kto może udzielić zgody na test systemu?', 'Właściciel systemu lub osoba uprawniona do takiej decyzji', ['Każdy użytkownik systemu', 'Administrator bez uprawnień decyzyjnych', 'Kolega z zespołu'], 'Zgoda niewłaściwej osoby nie jest autoryzacją.'],
  ['Jaka jest typowa długość okresu poufności w skoordynowanym ujawnianiu?', 'Około 90 dni lub do naprawy, zgodnie z uzgodnieniem', ['24 godziny', '10 lat', 'Nie ma żadnego okresu'], 'Termin ustala się z właścicielem.'],
  ['Co zrobić, gdy podczas normalnego korzystania z serwisu zobaczysz cudze dane?', 'Zatrzymać się, zanotować minimalne fakty i zgłosić oficjalnym kanałem', ['Pobrać wszystkie dane jako dowód', 'Opublikować zrzut ekranu', 'Napisać skrypt sprawdzający kolejne rekordy'], 'Dalsza eksploatacja zwiększa szkodę i ryzyko prawne.'],
  ['Dlaczego żądanie zapłaty za nieujawnienie podatności jest niedopuszczalne?', 'Bo jest formą wymuszenia, a nie zgłoszeniem w dobrej wierze', ['Bo nagrody są zakazane', 'Bo podatności nie mają wartości', 'Bo zgłoszenia muszą być anonimowe'], 'Nagrody wypłaca się w ramach regulaminu bug bounty.'],
  ['Czy ściganie przestępstw z art. 267 KK następuje co do zasady z urzędu?', 'Nie — co do zasady na wniosek pokrzywdzonego', ['Tak, zawsze z urzędu', 'Tylko na wniosek prokuratora UE', 'Nie, to nie jest przestępstwo'], 'To nie zmienia faktu, że czyn jest zabroniony.'],
  ['Na czym polega kontratyp z art. 269c KK w uproszczeniu?', 'Niekaralność działań wyłącznie w celu zabezpieczenia systemu, z niezwłocznym powiadomieniem dysponenta i bez szkody', ['Zgoda na dowolne testy w Internecie', 'Zwolnienie z RODO', 'Prawo do publikacji podatności'], 'Warunki są wąskie i kumulatywne.'],
  ['Co jest wymagane, by monitorować służbową pocztę pracowników zgodnie z Kodeksem pracy?', 'Określenie celu i zakresu monitoringu oraz poinformowanie pracowników', ['Nic — pracodawca może wszystko', 'Zgoda sądu', 'Tylko zgoda działu IT'], 'Art. 22³ KP; do tego obowiązki z RODO.'],
  ['Dlaczego dane syntetyczne są preferowane w labach?', 'Nie dotyczą realnych osób, więc nie tworzą ryzyka naruszenia ochrony danych', ['Bo są dokładniejsze niż produkcyjne', 'Bo wymaga tego Docker', 'Bo są szyfrowane'], 'Kopie produkcji w labie to ryzyko RODO.'],
  ['Kiedy organizacja może musieć zgłosić naruszenie ochrony danych do PUODO?', 'Gdy naruszenie danych osobowych niesie ryzyko dla osób — w ciągu 72 godzin od stwierdzenia', ['Nigdy', 'Tylko po roku', 'Tylko przy atakach DoS'], 'Art. 33 RODO.'],
  ['Czym jest „hack back”?', 'Atakowaniem infrastruktury napastnika w odwecie — nielegalne i ryzykowne', ['Kopią zapasową', 'Rodzajem testu jednostkowego', 'Zgłoszeniem do CERT'], 'Obrońca blokuje, zachowuje dowody i zgłasza — nie atakuje.'],
  ['Dlaczego test z sieci firmowej na cudzy system obciąża firmę?', 'Ruch pochodzi z adresów firmy — to ona odbierze skargę lub zgłoszenie incydentu', ['Bo firma płaci za Internet', 'Bo firewall to blokuje', 'Bo ruch firmowy jest szyfrowany'], 'Atrybucja wskazuje na organizację.'],
  ['Co jest właściwą reakcją na znalezienie wykradzionych haseł firmy na forum?', 'Zgłosić wewnętrznie, wymusić zmianę haseł, przekazać sprawę przez dział prawny/CERT — bez kupowania danych', ['Kupić bazę, by sprawdzić zakres', 'Zalogować się na konta z listy', 'Opublikować link'], 'Kupowanie i używanie wykradzionych danych jest niedopuszczalne.'],
  ['Czy regulamin bug bounty może wykluczać testy socjotechniczne?', 'Tak — wyłączenia z regulaminu są wiążące', ['Nie, socjotechnika zawsze jest dozwolona', 'Tylko w weekendy', 'Tylko dla pracowników'], 'Działania poza zakresem nie są objęte safe harbor.'],
  ['Co oznacza zasada minimalnego dowodu przy zgłaszaniu podatności?', 'Pokazanie problemu najmniejszą możliwą liczbą działań, bez dostępu do danych ponad konieczność', ['Pobranie jak największej ilości danych', 'Brak jakiegokolwiek opisu', 'Zgłoszenie bez kroków odtworzenia'], 'Minimalizuje szkodę i ryzyko prawne.'],
  ['Dlaczego obrońca dokumentuje swoje działania podczas incydentu?', 'Dla rozliczalności, odtworzenia przebiegu i wartości dowodowej', ['Bo tak jest szybciej', 'Bo tego wymaga Wireshark', 'Nie dokumentuje'], 'Dziennik działań to standard pracy IR.'],
  ['Co powinien zrobić developer, który zauważy podatność w systemie własnej firmy?', 'Zgłosić ją wewnętrznie do zespołu bezpieczeństwa i nie testować dalej na własną rękę', ['Opublikować na blogu', 'Zignorować', 'Sprawdzić skalę, pobierając dane klientów'], 'Wewnętrzna procedura zgłaszania.'],
  ['Które z poniższych NIE jest częścią dobrego zgłoszenia podatności?', 'Załączenie danych osobowych innych użytkowników', ['Kroki odtworzenia na własnych kontach', 'Opis potencjalnego wpływu', 'Dane kontaktowe zgłaszającego'], 'Cudze dane zamieniają zgłoszenie w wyciek.'],
];
for (const [q, a, w, e] of terms) add(q, a, w, e);
const reg = [['RODO', 'Ochrona danych osobowych, w tym zgłaszanie naruszeń w 72 h'], ['Kodeks pracy (art. 22³)', 'Monitoring poczty służbowej pracowników'], ['Ustawa o KSC (NIS2)', 'Obowiązki cyberbezpieczeństwa podmiotów kluczowych i ważnych'], ['Kodeks karny, rozdz. XXXIII', 'Przestępstwa przeciwko ochronie informacji']];
const regAll = reg.map((x) => x[1]);
for (const [r, d] of reg) add(`Czego dotyczy w kontekście tej lekcji: ${r}?`, d, regAll.filter((x) => x !== d), `${r}: ${d.toLowerCase()}.`);
const cases2 = [
  ['Uruchomienie Wiresharka na własnym laptopie, by obejrzeć własny ruch HTTPS', OK], ['Podłączenie się do wolnego gniazda w biurze klienta, by „zobaczyć sieć”', NO], ['Odtworzenie ARP spoofing między dwoma kontenerami w izolowanym labie', OK],
  ['Testy obciążeniowe API pracodawcy na środowisku testowym w uzgodnionym oknie', NEED], ['Próba zgadnięcia hasła byłego pracodawcy do jego panelu, „bo pamiętam stare hasła”', NO], ['Skanowanie OSINT własnej domeny firmowej przez SOC (CT logs, DNS)', PROC],
  ['Użycie nmap wobec scanme.nmap.org zgodnie z zasadami opublikowanymi przez właściciela', NEED], ['Wysłanie do kolegów fałszywego maila „z banku” bez wiedzy przełożonych, „żeby ich nauczyć”', NO], ['Test własnego routera domowego narzędziami z labu', OK],
  ['Dostęp do logów VPN konkretnego pracownika w ramach zatwierdzonego dochodzenia', PROC], ['Fuzzing aplikacji klienta po zakończeniu okna testowego z RoE', NO], ['Analiza pliku pcap z platformy CyberDefenders zgodnie z jej regulaminem', OK],
  ['Instalacja agenta EDR na stacjach firmowych przez zespół bezpieczeństwa zgodnie z polityką', PROC], ['Sprawdzenie, czy kamera IP w kawiarni ma domyślne hasło', NO], ['Uruchomienie Caldera na stacjach testowych z listy w podpisanym RoE', NEED],
  ['Pobranie ze sklepu cudzej faktury przez zmianę numeru w URL, by sprawdzić skalę', NO], ['Zgłoszenie podatności do CERT Polska, gdy właściciel nie odpowiada', OK], ['Przegląd reguł firewalla firmy przez audytora wewnętrznego z planu audytu', PROC],
  ['Test socjotechniczny recepcji klienta, gdy RoE wyklucza socjotechnikę', NO], ['Własny serwer VPS: skan portów i testy hardeningu, zgodnie z regulaminem dostawcy VPS', NEED],
];
for (const [c, v] of cases2) add(`Oceń czynność: ${c}.`, v, verdicts.filter((x) => x !== v), `Ocena: ${v.toLowerCase()}.`);
const cvd = ['Odkrycie bez dalszej eksploatacji', 'Zgłoszenie oficjalnym kanałem', 'Potwierdzenie i ocena przez właściciela', 'Naprawa i wdrożenie poprawki', 'Publikacja po naprawie lub w uzgodnionym terminie'];
for (let i = 0; i < cvd.length - 1; i++) add(`Który krok skoordynowanego ujawniania następuje po: „${cvd[i]}”?`, cvd[i + 1], cvd.filter((x, j) => j !== i + 1 && j !== i).slice(0, 3), `Kolejność: ${cvd.join(' → ')}.`);
add('Który krok skoordynowanego ujawniania jest pierwszy?', cvd[0], cvd.slice(1, 4), 'Najpierw zatrzymanie się po odkryciu, bez dalszej eksploatacji.');
const good = [['Kroki odtworzenia na kontach zgłaszającego', true], ['Opis potencjalnego wpływu', true], ['Data i godzina obserwacji', true], ['Załącznik z danymi innych klientów', false], ['Ultimatum: 48 godzin na naprawę albo publikacja', false]];
for (const [g, isGood] of good) add(`Czy element „${g}” powinien znaleźć się w odpowiedzialnym zgłoszeniu podatności?`, isGood ? 'Tak — to dobra praktyka' : 'Nie — to szkodliwe lub nieetyczne', [isGood ? 'Nie — to szkodliwe lub nieetyczne' : 'Tak — to dobra praktyka', 'Tylko jeśli firma płaci nagrody', 'Tylko w zgłoszeniach anonimowych'], isGood ? 'Pomaga właścicielowi zweryfikować problem.' : 'Zwiększa szkodę lub jest formą presji/wymuszenia.');
const who = [
  ['Aplikacja na współdzielonym hostingu', 'Właściciel aplikacji w zakresie aplikacji oraz dostawca hostingu w zakresie infrastruktury (zgodnie z jego zasadami)'],
  ['Sieć firmowa pracodawcy', 'Osoba uprawniona przez pracodawcę (np. CISO, zarząd) zgodnie z polityką'],
  ['Konto w chmurze publicznej Twojej firmy', 'Firma (właściciel konta) w granicach polityki testów dostawcy chmury'],
  ['Maszyny ćwiczeniowe platformy treningowej', 'Platforma — zgodnie ze swoim regulaminem'],
  ['Twój izolowany lab w Dockerze', 'Ty — jako właściciel środowiska'],
];
const whoAll = who.map((x) => x[1]);
for (const [w, a] of who) add(`Kto może udzielić zgody na testy: ${w}?`, a, whoAll.filter((x) => x !== a).slice(0, 3), a + '.');
const fields = [['Contact', 'Sposób kontaktu do zgłaszania problemów bezpieczeństwa'], ['Expires', 'Data, po której plik należy uznać za nieaktualny'], ['Policy', 'Odnośnik do polityki ujawniania podatności'], ['Encryption', 'Klucz do szyfrowania zgłoszeń'], ['Preferred-Languages', 'Preferowane języki zgłoszeń']];
const fieldAll = fields.map((x) => x[1]);
for (const [f, d] of fields) add(`Co oznacza pole „${f}” w pliku security.txt?`, d, fieldAll.filter((x) => x !== d).slice(0, 3), `${f}: ${d.toLowerCase()}.`);
if (quiz.length !== 100) throw new Error('quiz ' + quiz.length);

writeLesson(process.argv[2], theory, exercises, quiz);
