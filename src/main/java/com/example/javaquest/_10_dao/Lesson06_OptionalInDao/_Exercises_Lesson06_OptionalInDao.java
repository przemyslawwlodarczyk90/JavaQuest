package com.example.javaquest._10_dao.Lesson06_OptionalInDao;

public class _Exercises_Lesson06_OptionalInDao {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_FindByIdReturnsOptional {
        /*
         * 🧪 Zadanie 1:
         * Na bazie "jdbc:h2:mem:l06ex01;DB_CLOSE_DELAY=-1" z tabelą users (id, email,
         * first_name), zaimplementuj UserJdbcDao.findById(long id) zwracającą
         * Optional<User>. Zapisz 2 użytkowników, wywołaj findById dla istniejącego
         * i nieistniejącego id i wypisz oba wyniki Optional (bez rozpakowania).
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_FindByEmailReturnsOptional {
        /*
         * 🧪 Zadanie 2:
         * Rozbuduj UserJdbcDao o findByEmail(String email) zwracającą
         * Optional<User> (bo e-mail identyfikuje dokładnie 0 albo 1 wynik).
         * Sprawdź wynik dla istniejącego i nieistniejącego adresu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_OrElseThrowForExistingId {
        /*
         * 🧪 Zadanie 3:
         * Użyj findById(id).orElseThrow(...) z własnym komunikatem
         * NoSuchElementException dla ISTNIEJĄCEGO id i wypisz wynik - pokaż, że
         * orElseThrow po prostu zwraca wartość, gdy jest obecna, bez rzucania
         * wyjątku.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_OrElseThrowForMissingId {
        /*
         * 🧪 Zadanie 4:
         * Użyj findById(999).orElseThrow(...) dla NIEISTNIEJĄCEGO id, złap
         * NoSuchElementException w try-catch i wypisz jego komunikat.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_OrElseWithDefaultUser {
        /*
         * 🧪 Zadanie 5:
         * Zdefiniuj "gościa zastępczego" User(-1L, "brak@example.com", "Gosc") i
         * użyj findById(999).orElse(gosc) - wypisz wynik i porównaj z
         * findById(istniejace_id).orElse(gosc) (nie powinien zwrócić gościa).
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_IfPresentPrintsWelcomeMessage {
        /*
         * 🧪 Zadanie 6:
         * Użyj findById(id).ifPresent(user -> ...) do wypisania powitalnego
         * komunikatu TYLKO dla istniejącego użytkownika. Wywołaj to samo dla
         * nieistniejącego id i pokaż, że NIC się nie wypisuje.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_IsPresentAndIsEmptyChecks {
        /*
         * 🧪 Zadanie 7:
         * Sprawdź metody isPresent() i isEmpty() na Optional<User> zwróconym z
         * findById dla istniejącego i nieistniejącego id - wypisz wszystkie 4
         * kombinacje (isPresent/isEmpty x istnieje/nie istnieje).
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_GetThrowsWhenEmpty {
        /*
         * 🧪 Zadanie 8:
         * Wywołaj findById(999).get() (BEZ sprawdzenia isPresent()) i złap
         * NoSuchElementException rzucany przez sam Optional.get(). Wypisz jego
         * komunikat i porównaj w komentarzu z orElseThrow (get() nie pozwala na
         * własny komunikat).
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_MapOptionalToDifferentType {
        /*
         * 🧪 Zadanie 9:
         * Użyj findById(id).map(User::email) do wyciągnięcia Optional<String> z
         * samym e-mailem, bez pobierania całego obiektu User. Wypisz wynik dla
         * istniejącego i nieistniejącego id.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_FindByEmailUsedForLoginCheck {
        /*
         * 🧪 Zadanie 10:
         * Napisz metodę boolean canLogin(UserJdbcDao dao, String email) w main
         * zwracającą findByEmail(email).isPresent(). Przetestuj dla 3 e-maili,
         * z czego 1 nieistniejący.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_OrElseGetWithLazySupplier {
        /*
         * 🧪 Zadanie 11:
         * Użyj findById(999).orElseGet(() -> ...) z Supplierem, który WYPISUJE
         * log "tworze wartosc domyslna" PRZED zwróceniem domyślnego User. Porównaj
         * z orElse(wartosc) dla istniejącego id - pokaż w komentarzu, że orElseGet
         * NIE wywołuje Suppliera, gdy wartość jest obecna (leniwość), a orElse
         * zawsze tworzy swój argument.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_ChainedOptionalOperations {
        /*
         * 🧪 Zadanie 12:
         * Zbuduj łańcuch: findById(id).map(User::email).filter(email ->
         * email.endsWith("@example.com")).ifPresentOrElse(email ->
         * System.out.println("OK: " + email), () -> System.out.println("brak
         * dopasowania")). Przetestuj dla e-maila z tą domeną i innego.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_ServiceMethodUsingOrElseThrowForBusinessRule {
        /*
         * 🧪 Zadanie 13:
         * Napisz metodę User requireUser(UserJdbcDao dao, long id) rzucającą
         * WŁASNY, niesprawdzany wyjątek UserNotFoundException (dziedziczy po
         * RuntimeException) przez orElseThrow(UserNotFoundException::new) (albo
         * lambdą z komunikatem). Użyj jej w main dla istniejącego i
         * nieistniejącego id.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_FindByEmailUsedBeforeInsertToAvoidDuplicate {
        /*
         * 🧪 Zadanie 14:
         * Napisz registerUser(dao, email, name) w main, która NAJPIERW wywołuje
         * findByEmail(email) - jeśli Optional jest present, wypisuje "e-mail juz
         * zajety" i nie wstawia; jeśli empty, wywołuje save(). Przetestuj na 4
         * e-mailach, z czego 1 duplikat.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_OptionalOfNullableForPotentiallyNullColumn {
        /*
         * 🧪 Zadanie 15:
         * Dodaj do tabeli users NULLABLE kolumnę phone VARCHAR(20). Napisz
         * findPhoneById(long id) zwracającą Optional<String> zbudowane przez
         * Optional.ofNullable(rs.getString("phone")) (bo sama kolumna może być
         * NULL, niezależnie od tego, czy użytkownik istnieje). Przetestuj dla
         * użytkownika z numerem i bez.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_CompareThreeStylesOnSameData {
        /*
         * 🧪 Zadanie 16:
         * Dla LISTY 5 id (część istniejąca, część nie), zastosuj DO KAŻDEGO z nich
         * WSZYSTKIE trzy style (orElseThrow w try-catch, orElse, ifPresent) i
         * wypisz wynik każdego stylu dla każdego id w tabeli/formacie
         * porównawczym.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_OptionalAvoidsNullPointerException {
        /*
         * 🧪 Zadanie 17:
         * Napisz metodę "błędną" (BEZ Optional, zwracającą null z findByIdRaw)
         * i pokaż, że wywołanie .email() na jej wyniku dla nieistniejącego id
         * rzuca NullPointerException. Następnie napisz "poprawną" wersję z
         * Optional i pokaż, że kompilator/API wymusza świadomą obsługę
         * (orElseThrow) zamiast NPE.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_OptionalWithCustomExceptionMessage {
        /*
         * 🧪 Zadanie 18:
         * Napisz findById(id).orElseThrow(() -> new NoSuchElementException(
         * "Uzytkownik o id=" + id + " nie zostal znaleziony w systemie")) -
         * komunikat MUSI zawierać konkretne id. Przetestuj dla 3 różnych,
         * nieistniejących id i sprawdź, że komunikat każdorazowo się różni.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_FindByEmailThenUpdateViaOptional {
        /*
         * 🧪 Zadanie 19:
         * Napisz metodę updateNameByEmail(dao, email, newName) w main - wywołuje
         * findByEmail(email), jeśli present, buduje nowy User z podmienionym
         * first_name i wywołuje update(); jeśli empty, wypisuje "nie znaleziono
         * uzytkownika o takim e-mailu". Przetestuj dla istniejącego i
         * nieistniejącego e-maila.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_OrToTryAlternativeLookup {
        /*
         * 🧪 Zadanie 20:
         * Użyj Optional.or(...) (Java 9+) do zbudowania łańcucha: najpierw
         * findById(id), a jeśli empty, spróbuj findByEmail(fallbackEmail) jako
         * "drugą szansę". Przetestuj scenariusz, gdzie pierwsze wyszukiwanie
         * zawodzi, ale drugie się powodzi.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_FullLoginFlowUsingOptional {
        /*
         * 🧪 Zadanie 21:
         * Zbuduj pełny przepływ "logowania": login(dao, email) - findByEmail
         * ->.orElseThrow(LoginFailedException) - a Controller (metoda
         * handleLoginRequest) łapie ten wyjątek i tłumaczy na "401 Unauthorized".
         * Przetestuj dla 3 e-maili (2 udane logowania, 1 nieudane).
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_OptionalChainAcrossTwoDaos {
        /*
         * 🧪 Zadanie 22:
         * Tabele users (id, email) i profiles (id, user_id, bio). ProfileDao z
         * findByUserId(long) -> Optional<Profile>. Napisz getUserBio(userDao,
         * profileDao, userId) zwracającą Optional<String> łączące
         * userDao.findById(userId) i profileDao.findByUserId(userId) TYLKO gdy
         * OBA są present (flatMap). Przetestuj dla użytkownika z profilem i bez.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_OptionalUsedInBatchProcessingWithCounters {
        /*
         * 🧪 Zadanie 23:
         * Dla listy 10 id (część istniejąca, część nie), wywołaj findById dla
         * każdego i zlicz do dwóch liczników (znalezieni/nieznalezieni) używając
         * isPresent()/isEmpty(). Wypisz raport z liczbami i procentowym udziałem.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_CustomExceptionHierarchyWithOrElseThrow {
        /*
         * 🧪 Zadanie 24:
         * Zdefiniuj UserNotFoundException(long id) z komunikatem generowanym
         * automatycznie w konstruktorze. Napisz DWIE metody Service korzystające z
         * orElseThrow(() -> new UserNotFoundException(id)): getUserOrThrow i
         * deleteUserOrThrow (ta druga: sprawdza istnienie PRZED deleteById).
         * Przetestuj obie dla istniejącego i nieistniejącego id.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_OptionalPerformanceVsRawNullChecks {
        /*
         * 🧪 Zadanie 25:
         * Zmierz (System.nanoTime()) czas 1000 wywołań findById(id).isPresent()
         * (wersja z Optional) oraz 1000 wywołań odpowiednika zwracającego wprost
         * User albo null z jawnym "if (user != null)". Wypisz porównanie czasów i
         * skomentuj, że różnica jest znikoma - Optional to narzędzie CZYTELNOŚCI,
         * nie wydajności.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_OptionalWrappingAggregateQuery {
        /*
         * 🧪 Zadanie 26:
         * Napisz findOldestUser(UserJdbcDao) zwracającą Optional<User> (SELECT ...
         * ORDER BY id LIMIT 1) - działa poprawnie NAWET gdy tabela jest pusta
         * (Optional.empty() zamiast wyjątku). Przetestuj na pustej tabeli i na
         * tabeli z 3 użytkownikami.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_RetryWithOptionalFallbackChain {
        /*
         * 🧪 Zadanie 27:
         * Zbuduj łańcuch TRZECH prób wyszukiwania (np. findByEmail, potem
         * findByPhoneIfPresent, potem findByBackupEmail) łączonych przez
         * .or(...).or(...) - każda następna próba wykonuje się TYLKO, jeśli
         * poprzednia była empty. Zademonstruj scenariusz, gdzie dopiero trzecia
         * próba się powodzi.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_OptionalInStreamFilterMapCombo {
        /*
         * 🧪 Zadanie 28:
         * Dla listy 10 id, użyj Stream.map(dao::findById).filter(Optional::isPresent)
         * .map(Optional::get) do zbudowania List<User> zawierającej tylko
         * ISTNIEJĄCYCH użytkowników z tej listy id (pomijając te, które dały
         * empty). Wypisz wynikową listę i jej rozmiar.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_OptionalDrivenCacheLookup {
        /*
         * 🧪 Zadanie 29:
         * Zaimplementuj prosty "cache" (HashMap<Long, User> w pamięci) przed
         * bazą danych: metoda findByIdCached(dao, id) NAJPIERW sprawdza cache
         * (Optional.ofNullable(cache.get(id))), a jeśli empty, woła
         * dao.findById(id) i - jeśli present - ZAPISUJE wynik w cache przed
         * zwróceniem. Zademonstruj, że drugie wywołanie dla tego samego id NIE
         * odpytuje bazy (dodaj log w DAO, który powinien się wykonać tylko raz).
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_FullServiceLayerWrappingAllOptionalStyles {
        /*
         * 🧪 Zadanie 30:
         * Zbuduj UserService z TRZEMA metodami, każda demonstrującą inny styl
         * obsługi Optional z DAO: getUserStrict(id) (orElseThrow),
         * getUserOrGuest(id) (orElse z gościem zastępczym), notifyIfExists(id)
         * (ifPresent wypisujący log). Wywołaj wszystkie trzy metody dla listy 5 id
         * (mix istniejących/nieistniejących) i podsumuj wyniki w raporcie.
         */
        public static void main(String[] args) { }
    }
}
