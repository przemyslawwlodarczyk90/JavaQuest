# Interfejs i globalne postępy

`GET /api/mastery/summary` zwraca prywatne podsumowanie konta z tokenu:
`points`, `expiringSoon`, `completedLessons`, `totalLessons`, `courses`.
Każdy kurs zawiera `track`, `points`, `completedLessons`, `totalLessons`.
Odpowiedź nie ujawnia wewnętrznych dat Mastery.

Panel statystyk jest domyślnie zwinięty. Każde otwarcie losuje nowy cytat przez
`GET /api/mastery/quote?exclude=<id>`; poprzednie ID jest zapamiętywane lokalnie
na czas sesji. `platform/Utill/MotivationalQuotes.java` zawiera 50 autentycznych
fragmentów z 6 polskich wydań: polskie oryginały oraz opublikowany przekład
Mariana Reitera. Nie ma własnych tłumaczeń. Zachowano historyczną pisownię wydań;
podział wersów zastąpiono spacjami. Autor, dzieło, adres i lokalizacja pozostają
w klasie do weryfikacji; panel pokazuje tylko cytat i autora.
`node scripts/verify_motivational_quotes.mjs` porównuje wszystkie polskie
fragmenty z wydaniami Wolnych Lektur i Wikiźródeł. Nie opieramy przypisań
wyłącznie na agregatorach cytatów.

Nagłówek oddziela „Twoja nauka” od „Postępy i punkty” odstępem 24 px;
na małym ekranie tekst może przejść do następnego wiersza. Zakładki kursów
zawijają się zamiast rozszerzać stronę poza szerokość telefonu.

`GET /api/mastery/rules` zasila instrukcję aktualnymi parametrami konfiguracji.
Od 2026-10-25 ważność 2★ wynosi 60 dni, a 3★ 120 dni, zgodnie z wyborem właściciela.
Odstępy do zdobywania/odnawiania poziomów pozostają 20 godzin, 6 dni i 30 dni.
Zmiana retencji obowiązuje również przy odtwarzaniu dotychczasowej historii quizów;
nie wymaga migracji salda, bo punkty nie są przechowywane osobno.

`GET /api/mastery/expiring` zwraca prywatną listę lekcji, które utracą punkty
w ciągu 7 dni, posortowaną według dokładnej granicy najbliższego spadku.
Porządek rozstrzygają następnie slugi rozdziału i lekcji. Daty graniczne
pozostają wewnętrzne; klient otrzymuje tylko liczbę dni, poziom i punkty zagrożone.
Lista używa jednego odczytu historii oraz jednej projekcji tytułów zaliczonych lekcji,
bez pobierania treści lub osobnych zapytań dla każdej pozycji.

Punkt oznacza aktualnie ważną gwiazdkę. Podsumowanie odtwarza istniejącą
historię zaliczonych quizów przez `MasteryPolicy`; nie zapisuje nowego salda.
Pierwsza gwiazdka pozostaje trwała zgodnie z dotychczasową polityką.
`expiringSoon` oznacza spadek sumy w ciągu kolejnych 7 dni bez powtórek,
z zachowaniem ścisłych granic wygasania obecnej polityki.

Odczyt wykonuje cztery zapytania: metadane rozdziałów, liczby lekcji według
rozdziału, liczby ukończonych istniejących lekcji użytkownika i projekcję
zaliczonych podejść. Nie pobiera treści lekcji ani pytań i nie wykonuje
osobnych zapytań dla każdego kursu. Kursy wynikają z metadanych rozdziałów.

Nagłówek i panel korzystają ze wspólnego `ProgressProvider`. Stan jest
odświeżany po quizie, zmianie ukończenia, powrocie do okna oraz co minutę
w widocznej karcie. Wylogowanie i zmiana konta odcinają stare odpowiedzi.

„Moje materiały” otwiera modalny panel przy prawej krawędzi. Poszczególne
sekcje są rozwijane; na małych ekranach panel zajmuje pełną szerokość.
Natywny dialog zapewnia obsługę klawiatury, blokowanie tła i powrót fokusu.
Potwierdzenia edycji i usuwania zachowują istniejącą obsługę.

Przy starcie `ContentReset` odtwarza wyłącznie teorię, zadania i pytania.
`ContentSeeder` aktualizuje metadane rozdziałów i lekcji po slugach oraz
dodaje brakujące wpisy. Identyfikatory istniejących lekcji pozostają stałe:
`lesson_materials.lesson_id` jest kluczem obcym i nie wolno usuwać tych lekcji
podczas odświeżania treści. Istniejące materiały nie wymagają migracji.
Test `LessonMaterialIntegrationTest.materialsSurviveTheStartupContentRefresh`
sprawdza zapis pliku i linku przed ponownym przejściem całej inicjalizacji treści,
zachowanie identyfikatorów, pobieranie pliku oraz prywatność po odświeżeniu.

Weryfikacja: `LearningSummaryServiceTest`, istniejące `MasteryPolicyTest`,
`MasteryServiceTest`, rozszerzony `QuizMasteryIntegrationTest`, frontendowe
`npm run build`, `npm run lint`, `npm run test:api`. Podgląd responsywny
sprawdzany w przeglądarce na danych testowych, niezależnie od kont użytkowników.
2026-10-25: 37 testów backendu przeszło bez błędów (w tym prywatność API,
kolejność wygasania, granice retencji i ponowne zaliczenia). Build, lint i test
API frontendu przeszły; sprawdzono szerokości desktopową, 820 px i 390 px.
Skrypt źródeł potwierdził wszystkie 50 fragmentów w 6 polskich wydaniach.
