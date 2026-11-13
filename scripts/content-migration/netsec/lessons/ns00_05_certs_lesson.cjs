// Generuje lekcje _ns_00_wprowadzenie/05_CertificationRoadmap.json.
// Uzycie: node scripts/content-migration/netsec/lessons/ns00_05_certs_lesson.cjs src/main/resources/content/_ns_00_wprowadzenie/05_CertificationRoadmap.json
const { quizBuilder, exerciseBuilder, writeLesson } = require('./lib.cjs');
const { EXAMS, PRACTICAL } = require('./ns00_05_certs_data.cjs');
const { SHORT } = require('./track_meta.cjs');

const img = (file, alt) => ({ image: 'netsec/' + file, imageAlt: alt });
const chl = (n) => `${String(n).padStart(2, '0')} — ${SHORT[n]}`;
const byId = Object.fromEntries(EXAMS.map((e) => [e.id, e]));

// ---------- TEORIA ----------
const theory = [
  { type: 'INTRO', heading: 'Certyfikat to dowód umiejętności, nie cel', ...img('ns00-05-paths.svg', 'Diagram ścieżek: Network+ (N10-009) i CCNA (200-301 v2.0) prowadzą do Security+ (SY0-801), a od niego strzałki do CySA+ (CS0-004), egzaminów praktycznych BTL1 i CCD, SecAI+ (CY0-001) oraz CSSLP, AWS i CKS; dalej SC-200 oraz GCIH i GCFA.'),
    body:
`Rekruterzy w bezpieczeństwie patrzą na certyfikaty jak na **filtr na wejściu** — szczególnie przy pierwszej pracy w nowej dziedzinie. Security+ bywa wymaganiem formalnym (np. w kontraktach obronnych), CySA+ i egzaminy praktyczne pokazują przygotowanie do SOC, a CCNA wciąż otwiera drzwi do sieci. Ale żaden certyfikat nie zastąpi umiejętności, które sprawdzają rozmowy techniczne: analizy logów, czytania pakietów, przeglądu kodu.

W tej lekcji:
- poznasz aktualne wersje egzaminów (stan: **listopad 2026**) i ich domeny z wagami,
- zobaczysz, **które rozdziały toru** przygotowują do której domeny,
- zaplanujesz własną ścieżkę na 12–18 miesięcy.

Ważne: egzaminy zmieniają wersje co ok. 3 lata, a wagi domen i daty wycofania starych wersji publikują wydawcy (CompTIA, Cisco, Microsoft, GIAC, ISC2). **Dane w tej lekcji pochodzą z listopada 2026 — przed zapisem na egzamin zawsze sprawdź aktualne „exam objectives” u wydawcy.**` },

  { type: 'DEFINITION', heading: 'Pojęcia certyfikacyjne', body:
`- **Exam objectives** — oficjalny dokument wydawcy z domenami, celami (np. „4.3 Given a scenario, …”) i wagami; podstawa nauki.
- **Domena** — duży obszar egzaminu z procentową wagą (np. Security Operations 27%).
- **PBQ (performance-based questions)** — zadania praktyczne w symulowanym środowisku: konfiguracja, analiza logów, dopasowanie elementów (CompTIA).
- **Wynik skalowany** — np. 750 na skali 100–900 (Security+); nie jest procentem poprawnych odpowiedzi.
- **Wersja / kod egzaminu** — np. SY0-801 to Security+ wersji 8; starsza wersja jest dostępna jeszcze przez kilka miesięcy po premierze nowej.
- **Odnowienie (CE)** — certyfikaty CompTIA są ważne 3 lata; odnawia się je punktami ciągłego kształcenia (CEU) lub zdaniem wyższego certyfikatu (np. CySA+ odnawia Security+ i Network+).
- **Certyfikat praktyczny** — egzamin w środowisku z narzędziami, gdzie rozwiązujesz śledztwo (BTL1, CCD) lub konfigurujesz system (CKS).
- **Certyfikat dostawcy** — dotyczy konkretnej platformy (Microsoft SC-200, AWS Security Specialty); szczególnie wartościowy, gdy pracodawca używa danego stosu.
- **Certyfikat rozszerzający** — SecAI+ jest projektowany jako uzupełnienie Security+/CySA+ o bezpieczeństwo AI.` },

  { type: 'ANALOGY', heading: 'Prawo jazdy i kategorie', body:
`System certyfikatów przypomina kategorie prawa jazdy:
- **Network+ / CCNA** to kategoria B — podstawa, bez której trudno jechać dalej; uczy przepisów ruchu (protokołów) i zachowania na drodze (sieci).
- **Security+** to kurs bezpiecznej jazdy — ogólne zasady, rozpoznawanie zagrożeń, reagowanie na sytuacje awaryjne.
- **CySA+, BTL1, SC-200** to uprawnienia zawodowe (np. kierowca karetki) — praca pod presją, procedury, konkretne narzędzia.
- **GCIH, GCFA** to specjalistyczne kursy (np. instruktor jazdy w trudnych warunkach) — drogie, ale cenione.
- **Egzamin praktyczny** to jazda po mieście z egzaminatorem; **test teoretyczny** to quiz z przepisów. Pracodawca chce wiedzieć, czy umiesz jeździć, nie tylko czy znasz znaki.

I najważniejsze: prawo jazdy zdobyte bez jazd próbnych daje kierowcę, który zdał egzamin — ale nie kierowcę, któremu powierzysz karetkę. Dlatego w tym torze certyfikat jest **skutkiem** labów, a nie ich zamiennikiem.` },

  { type: 'VISUAL_EXAMPLE', heading: 'Egzaminy i ich domeny (stan: listopad 2026)', ...img('ns00-05-weights.svg', 'Dwa wykresy słupkowe: Security+ SY0-801 z domenami 16, 24, 19, 27 i 14 procent oraz CySA+ CS0-004 z domenami 34, 26, 24 i 16 procent.'),
    body:
`Domeny, wagi i rozdziały toru, które je pokrywają:`,
    code:
EXAMS.map((e) => `${e.name} ${e.code} — od ${e.since} (${e.note})\n` + e.domains.map(([d, w, chs]) => `  ${String(w).padStart(2)}%  ${d.padEnd(40)} rozdz. ${chs.map((c) => String(c).padStart(2, '0')).join(', ')}`).join('\n')).join('\n\n')
+ `\n\nFormat (CompTIA, wg wydawcy): Network+ i Security+ — do 90 pytań, 90 min (próg 720 i 750 w skali 100–900);
CySA+ — do 85 pytań, 165 min, próg 750. Pytania zamknięte + PBQ. Weryfikuj aktualne dane przed zapisem.` },

  { type: 'CODE_BASIC', heading: 'Twoja macierz pokrycia celów egzaminu', body:
`Najlepsze narzędzie przygotowania to tabela: cel egzaminu → lekcja toru → lab/artefakt → pewność (1–5). Możesz ją trzymać w repozytorium dziennika jako CSV i generować raport braków prostym programem w Javie.`,
    code:
`# coverage-secplus.csv
objective,description,chapter,lesson,artifact,confidence
1.1,Security controls,15,02_SecurityControlsTaxonomy,notes/controls.md,4
2.6,AI-related threats,36,02_OwaspLlmTop10AndAtlas,,1
4.4,Security alerting and monitoring,26,02_LogPipelinesAndNormalization,lab-siem/README.md,3
4.9,Incident response,33,01_IncidentResponseLifecycle,ir-tabletop.md,2

// CoverageReport.java — wypisz cele z niską pewnością (Java 21+)
import java.nio.file.*;
public class CoverageReport {
    public static void main(String[] args) throws Exception {
        try (var lines = Files.lines(Path.of(args[0]))) {
            lines.skip(1)
                 .map(l -> l.split(",", -1))
                 .filter(c -> Integer.parseInt(c[5]) <= 2)
                 .forEach(c -> System.out.printf("%-5s %-35s → rozdz. %s %s%n", c[0], c[1], c[2], c[3]));
        }
    }
}` },

  { type: 'STEP_BY_STEP', heading: 'Jak przygotować się do egzaminu z tym torem', ...img('ns00-05-matrix.svg', 'Mapa cieplna: wiersze Network+, CCNA, Security+, CySA+ i SecAI+, kolumny rozdziały 00–40. Network+ i CCNA pokrywają głównie rozdziały 01–09 i 13–14, Security+ rozdziały 15–38, CySA+ rozdziały 18–19, 22, 24, 26–30 i 32–35, SecAI+ rozdziały 35–36 i 38.'),
    body:
`1. **Pobierz aktualne exam objectives** od wydawcy (PDF). Sprawdź kod wersji i datę wycofania poprzedniej.
2. **Zbuduj macierz pokrycia** (cel → lekcja → artefakt → pewność). Użyj mapy z tej lekcji jako punktu startu.
3. **Przerabiaj rozdziały toru w kolejności**, robiąc laby — nie tylko quizy. Każdy lab dopisz do macierzy jako artefakt.
4. **Raz w tygodniu** aktualizuj pewność (1–5) i wybieraj 3 najsłabsze cele na kolejny tydzień.
5. **PBQ**: ćwicz zadania praktyczne — analiza logów, reguły firewalla, dopasowanie kontroli do zagrożeń, interpretacja wyników skanera. Rozdział 40 ma lekcję o strategii PBQ.
6. **Egzaminy próbne** (rozdział 40) — dopiero gdy macierz ma pewność ≥ 3 we wszystkich domenach; analizuj błędne odpowiedzi do źródła (która lekcja?).
7. **Plan na dzień egzaminu**: PBQ możesz oznaczyć do powrotu i zacząć od pytań zamkniętych; pilnuj czasu; czytaj dokładnie słowa kluczowe („BEST”, „FIRST”, „MOST likely”).
8. **Po egzaminie** — utrzymuj certyfikat (CE) przez dalszą naukę: kolejne rozdziały toru, konferencje, kursy, wyższy certyfikat.` },

  { type: 'NOTE', heading: 'Egzaminy praktyczne i certyfikaty dodatkowe', body:
`Poza ścieżką CompTIA/Cisco warto znać egzaminy, które rekruterzy blue team i AppSec cenią za praktykę:`,
    code:
PRACTICAL.map(([n, d, chs]) => `${n}\n   ${d}\n   rozdziały toru: ${chs.map((c) => String(c).padStart(2, '0')).join(', ')}`).join('\n') + `

Platformy treningowe przed egzaminami praktycznymi: CyberDefenders, Blue Team Labs Online,
TryHackMe (ścieżki SOC), LetsDefend. Ceny, formaty i czasy trwania egzaminów sprawdzaj u wydawców.` },

  { type: 'CODE_PRACTICAL', heading: 'Przykładowe plany 12–18 miesięcy', body:
`Trzy realistyczne plany dla Java developera pracującego na etacie (5–7 godzin nauki tygodniowo). Dopasuj tempo do siebie.`,
    code:
`PLAN A — SOC / blue team (ok. 15 miesięcy)
  M1–M4   rozdz. 00–09            → Network+ (N10-009)
  M5–M8   rozdz. 15–21            → Security+ (SY0-801)
  M9–M13  rozdz. 18, 19, 26–30, 33 → CySA+ (CS0-004)
  M14–M15 rozdz. 34, 40            → BTL1 (praktyczny) + portfolio

PLAN B — AppSec / DevSecOps (ok. 12 miesięcy)
  M1–M3   rozdz. 00–07 (szybciej)  → bez egzaminu sieciowego lub Network+
  M4–M7   rozdz. 15–17, 21–24      → Security+ (SY0-801)
  M8–M12  rozdz. 10, 25, 36        → AWS Security Specialty lub CKS; opcjonalnie CSSLP (wymaga doświadczenia)

PLAN C — sieci i architektura (ok. 18 miesięcy)
  M1–M6   rozdz. 00–14 w całości  → CCNA 200-301 v2.0 (od 02.2027) lub Network+
  M7–M10  rozdz. 15–21            → Security+
  M11–M18 rozdz. 13, 14, 20, 21, 38 + automatyzacja → architektura bezpieczeństwa sieci` },

  { type: 'USAGE', heading: 'Jak certyfikaty działają na rynku pracy', ...img('ns00-05-timeline.svg', 'Oś czasu: Network+ N10-009 od 06.2024, SecAI+ CY0-001 od 02.2026, CySA+ CS0-004 od 06.2026, Security+ SY0-801 od 11.2026 (oznaczone jako „dziś”), CCNA 200-301 v2.0 od 02.2027, koniec SY0-701 w wersji angielskiej 06.2027.'),
    body:
`- **Filtr ATS i HR** — wiele ogłoszeń wymienia Security+ lub CySA+; ich brak bywa powodem odrzucenia przed rozmową techniczną, zwłaszcza przy zmianie ścieżki kariery.
- **Wymogi formalne** — w sektorze publicznym, obronnym i u dużych integratorów certyfikaty bywają wymagane kontraktowo.
- **Wiarygodność przy zmianie roli** — Java developer z Security+ i portfolio detekcji jest dla zespołu SOC kandydatem „gotowym do nauki”, a nie „ciekawym tematu”.
- **Certyfikaty dostawców** — SC-200 dla firm z Microsoft Sentinel/Defender, AWS Security Specialty dla firm w AWS — tu liczy się dopasowanie do stosu pracodawcy.
- **Egzaminy praktyczne** — BTL1, CCD i podobne pokazują, że umiesz przeprowadzić śledztwo; świetnie łączą się z portfolio z toru.
- **Wersje** — certyfikat zdany na starszej wersji jest nadal ważny przez okres ważności; nie trzeba czekać na nową wersję, jeśli jesteś przygotowany na starą (SY0-701 dostępny do czerwca 2027 r. w wersji angielskiej — sprawdź u CompTIA).
- **Granice** — rozmowa rekrutacyjna i tak sprawdzi praktykę: zadanie z logami, analiza pcap, przegląd kodu, projekt detekcji.` },

  { type: 'PITFALL', heading: 'Najczęstsze błędy w przygotowaniu do certyfikatów', body:
`- **Nauka z nieaktualnych materiałów** — kursy i książki do poprzedniej wersji (np. SY0-701) nie pokrywają nowych celów (np. bezpieczeństwo AI w SY0-801). Zawsze porównaj z aktualnymi objectives.
- **„Zbieranie” certyfikatów** — pięć certyfikatów bez portfolio i labów nie przekonuje zespołu technicznego.
- **Pomijanie PBQ** — zadania praktyczne potrafią zadecydować o wyniku; ćwicz je świadomie.
- **Brak planu odnowienia** — certyfikaty CompTIA wygasają po 3 latach bez CE; zaplanuj odnawianie (wyższy certyfikat lub punkty CEU).
- **Zbyt szybkie przejście do specjalizacji** — CySA+ bez solidnego Network+/Security+ i części I toru to nauka na pamięć.
- **Dumpy (wycieki pytań)** — naruszają umowy egzaminacyjne (ryzyko unieważnienia certyfikatu) i nie dają umiejętności. To też kwestia etyki zawodowej.
- **Ignorowanie wymagań doświadczenia** — niektóre certyfikaty (np. CSSLP, CISSP) wymagają lat praktyki do pełnego przyznania; zaplanuj je później.
- **Wybór „najpopularniejszego” zamiast pasującego** — SC-200 ma mniejszą wartość w firmie opartej na Elastic/Splunk; najpierw sprawdź stos docelowego pracodawcy.` },

  { type: 'CODE_WRONG', heading: 'Źle: przygotowanie oparte na pamięciówce', body:
`Plan, który daje zaliczenie (czasem), ale nie daje umiejętności — i rozsypuje się na pierwszej rozmowie technicznej.`,
    code:
`Tydzień 1–2:  przewinięcie kursu wideo na 2x
Tydzień 3:    2000 pytań z „banku pytań” z forum (nieznanego pochodzenia)
Tydzień 4:    egzamin
Laby:         brak
Notatki:      brak
Po egzaminie: brak planu odnowienia, brak portfolio
Rozmowa:      „Opisz, jak zbadałbyś alert o podejrzanym procesie PowerShell” → cisza` },

  { type: 'CODE_RIGHT', heading: 'Dobrze: przygotowanie oparte na macierzy pokrycia i labach', body:
`Każdy cel egzaminu ma przypisaną lekcję i artefakt. Pytania próbne służą do diagnozy luk, a nie do zapamiętywania odpowiedzi.`,
    code:
`Tydzień 0:      pobranie objectives SY0-801, macierz pokrycia (CSV w repo)
Tygodnie 1–16:  rozdz. 15–24 i 26 zgodnie z kolejnością; każdy lab → NOTES.md → wpis w macierzy
Co tydzień:     aktualizacja pewności, 3 najsłabsze cele → plan kolejnego tygodnia
Tygodnie 17–18: egzaminy próbne (rozdz. 40), analiza błędów do źródła, ćwiczenie PBQ
Egzamin:        gdy wszystkie domeny mają pewność ≥ 3
Po egzaminie:   plan CE (CySA+ odnawia Security+), artefakty z labów jako portfolio
Rozmowa:        „Pokażę regułę Sigma i notatkę z labu, w którym wykryłem obfuskowany PowerShell (4104)”` },

  { type: 'WHEN_TO_USE', heading: 'Który certyfikat wybrać na start', body:
`- **Nie wiesz jeszcze, czy SOC, AppSec czy sieci** — zacznij od części I toru i **Network+** (szerokie podstawy) albo od razu **Security+**, jeśli sieci znasz dobrze.
- **Celujesz w SOC** — Network+ → Security+ → **CySA+** i/lub **BTL1**; jeśli docelowy pracodawca ma Microsoft Sentinel — **SC-200**.
- **Celujesz w AppSec / DevSecOps** — **Security+**, potem **AWS Security Specialty** lub **CKS** (zależnie od stosu); **CSSLP** po zdobyciu wymaganego doświadczenia.
- **Celujesz w sieci** — **CCNA** (v2.0 od lutego 2027 r. z domeną AI i operacji) lub Network+; potem Security+.
- **Pracujesz z systemami AI / LLM** — po Security+ lub CySA+: **SecAI+**.
- **Masz budżet od pracodawcy i celujesz w IR/DFIR** — **GCIH / GCFA** (GIAC, zwykle z kursem SANS).
- **Nie masz budżetu** — tor + platformy treningowe + portfolio; certyfikaty planuj stopniowo (jeden na kilka miesięcy).` },

  { type: 'SUMMARY', heading: 'Podsumowanie', body:
`- Stan na listopad 2026: Network+ **N10-009** (N10-010 zapowiedziany), CCNA **200-301 v2.0** od 3.02.2027, Security+ **SY0-801** od 17.11.2026 (SY0-701 do 11.06.2027), CySA+ **CS0-004** od 23.06.2026, SecAI+ **CY0-001**.
- Wagi domen: Security+ V8 — 16/24/19/27/14; CySA+ V4 — 34/26/24/16; CCNA v2.0 — 25/25/20/20/10; Network+ — 23/20/19/14/24; SecAI+ — 17/40/24/19.
- Praktyczne: BTL1, CCD, SC-200, GCIH/GCFA; dla developera: CSSLP, AWS Security Specialty, CKS.
- Przygotowanie: objectives → macierz pokrycia → laby toru → PBQ → egzaminy próbne → egzamin → odnawianie (CE).
- Certyfikat to dowód umiejętności — najsilniejszy w połączeniu z portfolio artefaktów z labów.
- Zawsze weryfikuj wersje, wagi i daty u wydawcy przed zapisem.
- To koniec rozdziału 00. Następny krok: **rozdział 01 — fundamenty sieci i modele warstwowe**.` },
];

// ---------- ZADANIA ----------
const { exercises, ex } = exerciseBuilder();
const plans = [
  ['Pobierz aktualne exam objectives Security+ SY0-801 od CompTIA i porównaj listę domen i wag z lekcją. Zapisz datę sprawdzenia.', 'Strona certyfikatu na comptia.org → Exam objectives (PDF).', 'Domeny V8: General Security Concepts 16%, Threats, Vulnerabilities and Attacks 24%, Security Architecture 19%, Security Operations 27%, Security Program Management and Oversight 14% (stan z listopada 2026). Jeśli wagi u wydawcy różnią się — źródłem prawdy jest wydawca; zaktualizuj macierz.'],
  ['Zbuduj macierz pokrycia (CSV) dla wybranego egzaminu: co najmniej 15 celów z przypisaną lekcją toru i pewnością 1–5.', 'Kolumny: objective, description, chapter, lesson, artifact, confidence.', 'Przykład wierszy dla CySA+: 1.x Analiza wskaźników złośliwej aktywności → rozdz. 28 (analiza ruchu) / 19 (Linux); 2.x Analiza wyników skanerów → rozdz. 22; 3.x Cykl reagowania → rozdz. 33; 4.x Raportowanie i metryki → rozdz. 26 (metryki SOC). Pewność uczciwie: 1 = nie znam, 5 = umiem pokazać w labie.'],
  ['Uruchom program CoverageReport z lekcji na swojej macierzy i zaplanuj 3 najsłabsze cele na kolejny tydzień.', 'Filtruj confidence ≤ 2.', 'Wynik to lista celów do pracy. Dla każdego zapisz: lekcję toru, lab do wykonania i oczekiwany artefakt (np. „4.4 alerting — lab Wazuh z regułą dla 4625 + zrzut alertu w NOTES.md”).'],
  ['Wybierz jeden z trzech planów (A/B/C) z lekcji i dopasuj go do swojego kalendarza: daty kamieni milowych, liczba godzin tygodniowo, termin egzaminu.', 'Zostaw zapas 20% na nieprzewidziane tygodnie.', 'Przykład (plan A, start 2026-12): Network+ — kwiecień 2027; Security+ — sierpień 2027; CySA+ — luty 2028; BTL1 — kwiecień 2028. 6 h/tydz.: 2 × 1 h teoria, 2 × 1,5 h lab, 1 h powtórki. Każdy kamień milowy z Bossem odpowiednich rozdziałów.'],
  ['Sprawdź u CompTIA zasady odnawiania certyfikatów (CE): okres ważności, wymagana liczba punktów CEU dla Network+, Security+ i CySA+ oraz czy wyższy certyfikat odnawia niższe.', 'Strona Continuing Education Program na comptia.org.', 'Ogólnie (sprawdź aktualne liczby): certyfikaty ważne 3 lata; CEU: Network+ 30, Security+ 50, CySA+ 60; zdanie wyższego certyfikatu z rodziny (np. CySA+) odnawia certyfikaty niższe (Security+, Network+). Zapisz w planie, który certyfikat utrzyma pozostałe.'],
  ['Przeanalizuj 5 ogłoszeń o pracę (SOC, AppSec, DevSecOps) z polskiego rynku: jakie certyfikaty wymieniają jako wymagane, a jakie jako mile widziane?', 'Zrób tabelę: rola, wymagane, mile widziane, technologie.', 'Typowo: SOC L1/L2 — Security+, CySA+, BTL1, znajomość SIEM (Splunk, Sentinel, Elastic), czasem CCNA; AppSec — OWASP, SAST/DAST, Security+, CSSLP mile widziany; DevSecOps — Kubernetes (CKS/CKA), chmura (AWS/Azure), CI/CD. Wnioski: dopasuj ścieżkę do stosu technologii w ogłoszeniach.'],
  ['Dopasuj egzamin praktyczny do roli: a) analityk SOC w firmie z Microsoft 365 i Sentinel, b) incident responder w dużej firmie, c) junior SOC bez budżetu na drogie kursy, d) AppSec w firmie na AWS.', 'Zwróć uwagę na stos technologii i koszt.', 'a) SC-200 (+ CySA+); b) GCIH/GCFA (zwykle finansowane przez pracodawcę), opcjonalnie BTL2; c) BTL1 lub CCD (praktyczne, przystępniejsze cenowo) + platformy treningowe; d) AWS Security Specialty + Security+, opcjonalnie CSSLP po zdobyciu doświadczenia.'],
  ['Napisz w dzienniku, dlaczego korzystanie z „dumpów” (wycieków prawdziwych pytań egzaminacyjnych) jest nieetyczne i ryzykowne.', 'Umowa kandydata (candidate agreement), unieważnienie, wartość wiedzy.', 'Narusza umowę z wydawcą (zakaz ujawniania i korzystania z pytań) — grozi unieważnieniem wyniku i zakazem zdawania; podważa wartość certyfikatu dla wszystkich; nie daje umiejętności, co wychodzi na rozmowie i w pracy. Dla obrońcy, który pracuje z danymi wrażliwymi i zaufaniem, to także test uczciwości zawodowej.'],
  ['Przygotuj listę 10 słów kluczowych z pytań typu „BEST/FIRST/MOST” i opisz, jak zmieniają znaczenie pytania.', 'Pytania wielokrotnie mają kilka „poprawnych” odpowiedzi — liczy się najlepsza w kontekście.', 'Przykład: FIRST — pierwszy krok w procedurze (np. w IR: identyfikacja/izolacja przed usunięciem); BEST — najlepsza z poprawnych (najskuteczniejsza, najmniej kosztowna); MOST likely — najbardziej prawdopodobna przyczyna; LEAST — najmniej skuteczna; PRIMARY — główny cel; NEXT — kolejny krok po opisanym; GREATEST risk; MOST secure; COST-effective; IMMEDIATELY.'],
  ['Zaplanuj, jak każdy kolejny lab toru zamieniać w artefakt portfolio przydatny jednocześnie do egzaminu (macierz) i rekrutacji (README).', 'Jeden artefakt, dwa zastosowania.', 'Dla każdego labu: NOTES.md (dowód nauki, wpis w macierzy z odwołaniem do celu egzaminu), krótkie podsumowanie w README portfolio (problem, co zrobiłem, wynik, narzędzia), zrzuty/fragmenty konfiguracji bez danych wrażliwych. Po roku: 20–30 artefaktów, z których 5 najlepszych opisujesz w CV.'],
];
for (const [q, h, a] of plans) ex(q, h, a);
for (const e of EXAMS) {
  ex(`Dla egzaminu ${e.name} ${e.code} wypisz domeny z wagami i wskaż, która domena powinna dostać najwięcej czasu nauki. Które rozdziały toru ją pokrywają?`, 'Waga domeny to przybliżony udział pytań.', `${e.domains.map(([d, w]) => `${d} ${w}%`).join('; ')}. Najwięcej czasu: ${[...e.domains].sort((a, b) => b[1] - a[1])[0][0]} (${[...e.domains].sort((a, b) => b[1] - a[1])[0][1]}%) — rozdziały ${[...e.domains].sort((a, b) => b[1] - a[1])[0][2].map(chl).join(', ')}.`);
}
const mapq = [
  ['Analiza wskaźników złośliwej aktywności w logach Windows', 'CySA+ — Operacje bezpieczeństwa; rozdziały 18 (Windows/AD), 26 (SIEM), 27 (detekcje)'],
  ['Subnetting i adresacja IPv4/IPv6', 'Network+ — Koncepcje sieci / Implementacja; CCNA — Routing IP; rozdział 03'],
  ['Zagrożenia związane z AI (prompt injection, zatruwanie modeli)', 'Security+ V8 — Zagrożenia, podatności i ataki; SecAI+ — Zabezpieczanie systemów AI; rozdział 36'],
  ['Priorytetyzacja podatności z użyciem CVSS i EPSS', 'CySA+ — Zarządzanie podatnościami; Security+ — Operacje; rozdział 22'],
  ['Konfiguracja OSPF w jednym obszarze', 'CCNA — Routing IP; rozdział 03 (lekcja OSPF)'],
  ['Zarządzanie ryzykiem dostawców i zgodność (NIS2, RODO)', 'Security+ — Zarządzanie programem bezpieczeństwa; CySA+ — Raportowanie; rozdział 38'],
  ['Ocena rekomendacji asystenta AI dla zmiany konfiguracji sieci', 'CCNA v2.0 — AI, operacje i zarządzanie siecią; rozdział 14'],
  ['Cykl reagowania na incydenty i zabezpieczanie dowodów', 'CySA+ — Reagowanie na incydenty; Security+ — Operacje; rozdziały 33 i 34'],
  ['Troubleshooting problemów z kablem, Wi-Fi i usługami sieciowymi', 'Network+ — Troubleshooting sieci (24%); rozdziały 09 i 14'],
  ['Bezpieczny cykl wytwarzania oprogramowania i SBOM', 'Security+ — Architektura; CySA+ — Zarządzanie podatnościami; rozdział 24'],
  ['Zero Trust, ZTNA i SASE', 'Security+ — Ogólne koncepcje i Architektura; CySA+ — Operacje; rozdziały 15 i 21'],
  ['Pisanie raportu z incydentu dla zarządu', 'CySA+ — Raportowanie i komunikacja; rozdział 33 (raportowanie) i 38 (metryki)'],
  ['Hardening serwera Linux według CIS Benchmark', 'Security+ — Operacje; CySA+ — Zarządzanie podatnościami (baseline); rozdział 22'],
  ['Analiza pcap z ruchem C2 (beaconing)', 'CySA+ — Operacje bezpieczeństwa; BTL1/CCD; rozdział 28'],
  ['Konfiguracja Ansible dla przełączników', 'CCNA v2.0 — AI, operacje i zarządzanie; rozdział 14'],
];
for (const [t, a] of mapq) ex(`Do którego egzaminu i domeny pasuje umiejętność: „${t}”? Wskaż rozdział toru.`, 'Skorzystaj z tabeli domen w lekcji.', a + '.');
if (exercises.length !== 30) throw new Error('zadania ' + exercises.length);

// ---------- QUIZ ----------
const { quiz, add } = quizBuilder(2026111405);
for (const e of EXAMS) {
  for (const [d, w] of e.domains) {
    const wr = [w + 4, w - 4, w + 10, w - 10, w + 2].filter((x) => x > 0 && x !== w).slice(0, 3).map((x) => `${x}%`);
    add(`Jaką wagę ma domena „${d}” w egzaminie ${e.name} ${e.code} (stan: listopad 2026)?`, `${w}%`, wr, `${e.code}: ${d} — ${w}%. Zawsze weryfikuj u wydawcy.`);
  }
}
const codes = [['Security+ wersji 8 (V8)', 'SY0-801'], ['CySA+ wersji 4 (V4)', 'CS0-004'], ['aktualny Network+', 'N10-009'], ['SecAI+', 'CY0-001'], ['CCNA (numer egzaminu)', '200-301']];
const codeAll = ['SY0-801', 'SY0-701', 'CS0-004', 'CS0-003', 'N10-009', 'N10-008', 'CY0-001', '200-301', '350-401'];
for (const [d, c] of codes) add(`Jaki kod ma egzamin: ${d}?`, c, codeAll.filter((x) => x !== c).slice(0, 3), `${d}: ${c}.`);
const dates = [['Od kiedy (według stanu z listopada 2026) dostępny jest Security+ SY0-801?', '17 listopada 2026'], ['Od kiedy obowiązuje CySA+ CS0-004?', '23 czerwca 2026'], ['Od kiedy obowiązuje CCNA 200-301 v2.0?', '3 lutego 2027'], ['Do kiedy można zdawać Security+ SY0-701 w wersji angielskiej?', '11 czerwca 2027'], ['Kiedy wprowadzono SecAI+ CY0-001?', '17 lutego 2026']];
const dateAll = dates.map((x) => x[1]).concat(['1 stycznia 2025']);
for (const [q, a] of dates) add(q, a, dateAll.filter((x) => x !== a).slice(0, 3), `${a} — według danych wydawcy z listopada 2026; sprawdź aktualne terminy.`);
const largest = EXAMS.map((e) => [e, [...e.domains].sort((a, b) => b[1] - a[1])[0]]);
for (const [e, [d]] of largest) add(`Która domena ma największą wagę w egzaminie ${e.name} ${e.code}?`, d, e.domains.map((x) => x[0]).filter((x) => x !== d).slice(0, 3), `Największa waga: ${d}.`);
const prac = PRACTICAL.map(([n, d]) => [n, d]);
const pracAll = prac.map((x) => x[1]);
for (const [n, d] of prac) add(`Czym charakteryzuje się ${n}?`, d, pracAll.filter((x) => x !== d).slice(0, 3), `${n}: ${d}.`);
const facts = [
  ['Co to są PBQ w egzaminach CompTIA?', 'Zadania praktyczne w symulowanym środowisku (konfiguracja, analiza, dopasowanie)', ['Pytania otwarte z esejem', 'Pytania o historię firmy', 'Dodatkowe pytania bez punktów'], 'Performance-based questions.'],
  ['Jaki jest próg zaliczenia Security+ według CompTIA?', '750 w skali 100–900', ['50% poprawnych odpowiedzi', '900 punktów', '700 w skali 0–1000'], 'Wynik jest skalowany, nie procentowy.'],
  ['Jak długo ważne są certyfikaty CompTIA bez odnowienia?', '3 lata', ['1 rok', '10 lat', 'Bezterminowo'], 'Odnawia się je punktami CE lub wyższym certyfikatem.'],
  ['Czy zdanie CySA+ odnawia certyfikat Security+?', 'Tak — wyższy certyfikat z rodziny odnawia niższe', ['Nie, nigdy', 'Tylko po 5 latach', 'Tylko w USA'], 'Sprawdź aktualne zasady CE programu CompTIA.'],
  ['Dlaczego przed nauką trzeba sprawdzić wersję egzaminu?', 'Bo wersje różnią się celami i wagami, a materiały do starszej wersji mogą nie pokrywać nowych tematów', ['Bo starsze wersje są łatwiejsze', 'Bo nowa wersja unieważnia stare certyfikaty', 'Bo wersja decyduje o języku egzaminu'], 'Np. SY0-801 dodaje zagrożenia związane z AI.'],
  ['Który certyfikat jest najbardziej wartościowy, gdy pracodawca używa Microsoft Sentinel i Defender?', 'SC-200', ['CKS', 'CCNA', 'CSSLP'], 'Certyfikaty dostawców mają największą wartość przy dopasowaniu do stosu.'],
  ['Który certyfikat jest rozszerzeniem dla osób z Security+ lub CySA+ pracujących z AI?', 'SecAI+', ['A+', 'Linux+', 'Project+'], 'CY0-001 — bezpieczeństwo systemów AI i AI w operacjach.'],
  ['Który certyfikat jest szczególnie skierowany do osób tworzących oprogramowanie?', 'ISC2 CSSLP', ['Network+', 'BTL1', 'CCNA'], 'Bezpieczny cykl wytwarzania oprogramowania; wymaga doświadczenia zawodowego.'],
  ['Co jest główną zaletą egzaminów praktycznych (np. BTL1)?', 'Sprawdzają umiejętność przeprowadzenia śledztwa z narzędziami, a nie tylko wiedzę', ['Są zawsze tańsze od wszystkich innych', 'Nie wymagają nauki', 'Są uznawane przez wszystkich pracodawców jako obowiązkowe'], 'Dobrze uzupełniają certyfikaty teoretyczne.'],
  ['Co oznacza słowo „FIRST” w pytaniu egzaminacyjnym?', 'Pytanie dotyczy pierwszego właściwego kroku w procedurze', ['Pierwszej odpowiedzi na liście', 'Najtańszego rozwiązania', 'Najnowszej technologii'], 'Kilka odpowiedzi może być poprawnych, ale tylko jedna jest pierwszym krokiem.'],
  ['Dlaczego korzystanie z dumpów egzaminacyjnych jest ryzykowne?', 'Narusza umowę z wydawcą (ryzyko unieważnienia) i nie daje realnych umiejętności', ['Bo dumpy są zawsze błędne', 'Bo są nielegalne tylko w USA', 'Bo wydłużają egzamin'], 'To również kwestia etyki zawodowej.'],
  ['Który element najlepiej uzupełnia certyfikat w procesie rekrutacji do SOC?', 'Portfolio artefaktów z labów (detekcje, raporty, analizy pcap)', ['Lista obejrzanych kursów', 'Zrzut ekranu wyniku egzaminu', 'Liczba certyfikatów'], 'Rozmowy techniczne sprawdzają praktykę.'],
  ['Jaką nową domenę wprowadza CCNA v2.0?', 'AI, operacje i zarządzanie siecią', ['Programowanie w Javie', 'Projektowanie baz danych', 'Grafikę komputerową'], 'Waga 10%; obejmuje m.in. ocenę rekomendacji AI i Ansible.'],
  ['Która domena Network+ N10-009 ma największą wagę?', 'Troubleshooting sieci (24%)', ['Bezpieczeństwo sieci (14%)', 'Operacje sieci (19%)', 'Implementacja sieci (20%)'], 'Koncepcje sieci 23% są tuż za nią.'],
  ['Co jest najlepszym punktem startu przygotowania do dowolnego egzaminu?', 'Aktualne exam objectives wydawcy i macierz pokrycia', ['Losowy bank pytań z forum', 'Najkrótszy kurs wideo', 'Zapis na egzamin bez przygotowania'], 'Objectives są oficjalnym zakresem.'],
];
for (const [q, a, w, e] of facts) add(q, a, w, e);
for (const e of EXAMS) {
  for (const [d, , chs] of e.domains) {
    const c = chs[0];
    const wrongs = Array.from({ length: 41 }, (_, i) => i).filter((x) => !chs.includes(x) && x !== 0).filter((x, i) => (x * 7 + c) % 11 === 0 || x === (c + 12) % 41 || x === (c + 23) % 41).slice(0, 3).map(chl);
    while (wrongs.length < 3) { const x = (c + 5 + wrongs.length * 9) % 41; if (!chs.includes(x) && !wrongs.includes(chl(x))) wrongs.push(chl(x)); else wrongs.push(chl((x + 1) % 41)); }
    add(`Który rozdział toru wspiera domenę „${d}” egzaminu ${e.id}?`, chl(c), wrongs, `Domenę „${d}” (${e.code}) wspierają rozdziały: ${chs.map((x) => String(x).padStart(2, '0')).join(', ')}.`);
  }
}
const skills = [
  ['Subnetting IPv4', 'Network+ / CCNA'], ['Analiza logów Windows i alertów SIEM', 'CySA+'], ['Zabezpieczanie aplikacji opartych na LLM', 'SecAI+'], ['Konfiguracja OSPF i VLAN na urządzeniach Cisco', 'CCNA'],
  ['Kontrole bezpieczeństwa i model CIA', 'Security+'], ['Priorytetyzacja podatności (CVSS, EPSS, KEV)', 'CySA+'], ['Zapytania KQL w Microsoft Sentinel', 'SC-200'], ['Śledztwo na danych z incydentu w 24 godziny', 'BTL1'],
  ['Bezpieczny cykl wytwarzania oprogramowania', 'CSSLP'], ['Bezpieczeństwo klastra Kubernetes w zadaniach praktycznych', 'CKS'], ['IAM, CloudTrail i GuardDuty', 'AWS Security Specialty'], ['Troubleshooting kabli, Wi-Fi i usług sieciowych', 'Network+'],
  ['Zarządzanie ryzykiem, polityki i zgodność', 'Security+'], ['Raportowanie incydentów i metryki dla interesariuszy', 'CySA+'], ['Ansible i ocena rekomendacji asystenta AI w operacjach sieci', 'CCNA'],
];
const skillAll = ['Network+', 'Network+ / CCNA', 'CCNA', 'Security+', 'CySA+', 'SecAI+', 'SC-200', 'BTL1', 'CSSLP', 'CKS', 'AWS Security Specialty'];
for (const [sk, e] of skills) add(`Który egzamin najbardziej bezpośrednio sprawdza umiejętność: „${sk}”?`, e, skillAll.filter((x) => x !== e && !(e.includes(x) || x.includes(e))).slice((sk.length) % 5, (sk.length) % 5 + 3).concat(skillAll.filter((x) => x !== e && !(e.includes(x) || x.includes(e)))).filter((x, i, a) => a.indexOf(x) === i).slice(0, 3), `${sk} → ${e}.`);
add('Ile domen ma egzamin CySA+ CS0-004?', '4', ['5', '3', '6'], 'Operacje, podatności, reagowanie na incydenty, raportowanie i komunikacja.');
add('Ile domen ma egzamin Security+ SY0-801?', '5', ['4', '6', '3'], 'Koncepcje, zagrożenia, architektura, operacje, zarządzanie programem.');
if (quiz.length !== 100) throw new Error('quiz ' + quiz.length);

writeLesson(process.argv[2], theory, exercises, quiz);
