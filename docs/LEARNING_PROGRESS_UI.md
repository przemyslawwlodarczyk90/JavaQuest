# Interfejs i globalne postępy

`GET /api/mastery/summary` zwraca prywatne podsumowanie konta z tokenu:
`points`, `expiringSoon`, `completedLessons`, `totalLessons`, `courses`.
Każdy kurs zawiera `track`, `points`, `completedLessons`, `totalLessons`.
Odpowiedź nie ujawnia wewnętrznych dat Mastery.

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

Weryfikacja: `LearningSummaryServiceTest`, istniejące `MasteryPolicyTest`,
`MasteryServiceTest`, rozszerzony `QuizMasteryIntegrationTest`, frontendowe
`npm run build`, `npm run lint`, `npm run test:api`. Podgląd responsywny
sprawdzany w przeglądarce na danych testowych, niezależnie od kont użytkowników.
