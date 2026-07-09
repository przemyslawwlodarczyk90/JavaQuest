package com.example.javaquest._13_libraries.Lesson11_GuavaPreconditionsAndCache;

public class _Exercises_Lesson11_GuavaPreconditionsAndCache {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_CheckNotNullOnValidValue {
        /*
         * 🧪 Zadanie 1:
         * Utworz zmienna String nazwaProduktu = "Laptop" i przepusc ja przez
         * Preconditions.checkNotNull(...), zapisujac wynik do nowej zmiennej -
         * wypisz ja, aby pokazac ze metoda zwraca ta sama referencje.
         */
        public static void main(String[] args) {
            // TODO: twoj kod tutaj
        }
    }

    static class Exercise02_CheckNotNullThrowsOnNull {
        /*
         * 🧪 Zadanie 2:
         * Wywolaj Preconditions.checkNotNull(null) w bloku try-catch i wypisz
         * nazwe zlapanego wyjatku (powinien to byc NullPointerException).
         */
        public static void main(String[] args) {
            // TODO: twoj kod tutaj
        }
    }

    static class Exercise03_CheckArgumentPositiveNumber {
        /*
         * 🧪 Zadanie 3:
         * Majac int cena = -20, uzyj Preconditions.checkArgument(...) aby
         * sprawdzic, ze cena >= 0 - w try-catch zlap wyjatek i wypisz jego nazwe.
         */
        public static void main(String[] args) {
            // TODO: twoj kod tutaj
        }
    }

    static class Exercise04_CheckArgumentValidEmail {
        /*
         * 🧪 Zadanie 4:
         * Majac String email = "bezmalpy.pl", uzyj checkArgument(...) aby
         * sprawdzic, czy email.contains("@") - zlap i wypisz wyjatek gdy
         * warunek nie jest spelniony.
         */
        public static void main(String[] args) {
            // TODO: twoj kod tutaj
        }
    }

    static class Exercise05_CheckStateOnSimpleFlag {
        /*
         * 🧪 Zadanie 5:
         * Utworz boolean polaczonyZBaza = false i uzyj Preconditions.checkState(...)
         * aby sprawdzic ten warunek przed "wykonaniem zapytania" - zlap
         * IllegalStateException w try-catch i wypisz jego nazwe.
         */
        public static void main(String[] args) {
            // TODO: twoj kod tutaj
        }
    }

    static class Exercise06_BuildBasicCache {
        /*
         * 🧪 Zadanie 6:
         * Uzyj CacheBuilder.newBuilder().maximumSize(10).build(...) z prostym
         * CacheLoader<Integer, Integer>, ktory zwraca key * 2. Wywolaj
         * cache.get(5) dwukrotnie i wypisz oba wyniki.
         */
        public static void main(String[] args) {
            // TODO: twoj kod tutaj
        }
    }

    static class Exercise07_LoadingCacheGetIfPresent {
        /*
         * 🧪 Zadanie 7:
         * Zbuduj LoadingCache<String, Integer> zwracajaca dlugosc stringa jako
         * wartosc (CacheLoader.from(String::length)). Sprawdz getIfPresent("abc")
         * PRZED i PO wywolaniu get("abc") - wypisz oba wyniki (null vs wartosc).
         */
        public static void main(String[] args) {
            // TODO: twoj kod tutaj
        }
    }

    static class Exercise08_InvalidateSingleEntry {
        /*
         * 🧪 Zadanie 8:
         * Zbuduj prosty LoadingCache<Integer,Integer> (key -> key*key). Wywolaj
         * get(4), potem invalidate(4), a nastepnie getIfPresent(4) - wypisz
         * wynik pokazujacy ze wpis zostal usuniety (null).
         */
        public static void main(String[] args) {
            // TODO: twoj kod tutaj
        }
    }

    static class Exercise09_CacheLoaderCountsCalls {
        /*
         * 🧪 Zadanie 9:
         * Zbuduj LoadingCache<Integer,String>, ktorego CacheLoader zlicza (przez
         * zewnetrzny licznik, np. int[] licznik = {0}) ile razy .load(...) zostalo
         * faktycznie wywolane. Wywolaj get(1) trzykrotnie i wypisz koncowa
         * wartosc licznika (powinna byc 1, nie 3 - dzieki cache).
         */
        public static void main(String[] args) {
            // TODO: twoj kod tutaj
        }
    }

    static class Exercise10_RecordStatsBasicHitMiss {
        /*
         * 🧪 Zadanie 10:
         * Zbuduj LoadingCache z .recordStats() (key -> key+1). Wywolaj get(1)
         * dwukrotnie i get(2) raz - wypisz stats().hitCount() i
         * stats().missCount() (powinno byc odpowiednio 1 i 2).
         */
        public static void main(String[] args) {
            // TODO: twoj kod tutaj
        }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_ErrorMessageWithSinglePlaceholder {
        /*
         * 🧪 Zadanie 11:
         * Majac int wiek = 200, uzyj checkArgument(wiek <= 130, "Wiek %s jest
         * nieprawdopodobny", wiek) w try-catch - wypisz e.getMessage() aby
         * pokazac ze %s zostal podstawiony wartoscia.
         */
        public static void main(String[] args) {
            // TODO: twoj kod tutaj
        }
    }

    static class Exercise12_ErrorMessageWithMultiplePlaceholders {
        /*
         * 🧪 Zadanie 12:
         * Majac String haslo = "1234" oraz int minDlugosc = 8, uzyj checkArgument
         * z komunikatem zawierajacym DWA %s (dlugosc hasla i wymagane minimum) -
         * zlap wyjatek i wypisz pelny komunikat.
         */
        public static void main(String[] args) {
            // TODO: twoj kod tutaj
        }
    }

    static class Exercise13_CheckElementIndexVsCheckPositionIndex {
        /*
         * 🧪 Zadanie 13:
         * Majac liste o rozmiarze 5, wywolaj checkElementIndex(5, 5) ORAZ
         * checkPositionIndex(5, 5) w osobnych blokach try-catch - wypisz ktora z
         * nich rzuca wyjatek, a ktora przechodzi (i skomentuj dlaczego w println).
         */
        public static void main(String[] args) {
            // TODO: twoj kod tutaj
        }
    }

    static class Exercise14_ValidateBankAccountWithdrawal {
        /*
         * 🧪 Zadanie 14:
         * Napisz prosta klase Konto z polem saldo (double) i metoda wyplac(kwota),
         * ktora uzywa checkArgument(kwota > 0, ...) ORAZ checkState(saldo >= kwota, ...)
         * - przetestuj oba przypadki bledu (ujemna kwota, za male saldo) w try-catch.
         */
        public static void main(String[] args) {
            // TODO: twoj kod tutaj
        }
    }

    static class Exercise15_ValidateRectangleConstructor {
        /*
         * 🧪 Zadanie 15:
         * Napisz klase Prostokat(double szerokosc, double wysokosc), ktorej
         * konstruktor uzywa checkArgument dla OBU wymiarow (musza byc > 0) z
         * czytelnymi komunikatami %s - przetestuj z poprawnymi i niepoprawnymi
         * danymi.
         */
        public static void main(String[] args) {
            // TODO: twoj kod tutaj
        }
    }

    static class Exercise16_ExpireAfterWriteDemo {
        /*
         * 🧪 Zadanie 16:
         * Zbuduj LoadingCache<String,String> z expireAfterWrite(200, TimeUnit.MILLISECONDS).
         * Wstaw wartosc przez get("k"), odczekaj (Thread.sleep) 250ms i sprawdz
         * getIfPresent("k") - wypisz wynik pokazujacy ze wpis wygasl.
         */
        public static void main(String[] args) {
            // TODO: twoj kod tutaj
        }
    }

    static class Exercise17_ExpireAfterAccessDemo {
        /*
         * 🧪 Zadanie 17:
         * Zbuduj LoadingCache<String,String> z expireAfterAccess(200, TimeUnit.MILLISECONDS).
         * Wywoluj get("k") dwukrotnie w odstepie 100ms (wpis powinien PRZETRWAC),
         * a potem odczekaj 250ms bez odczytu i sprawdz getIfPresent("k") - wypisz
         * oba etapy.
         */
        public static void main(String[] args) {
            // TODO: twoj kod tutaj
        }
    }

    static class Exercise18_MaximumSizeEvictionDemo {
        /*
         * 🧪 Zadanie 18:
         * Zbuduj LoadingCache<Integer,Integer> z maximumSize(3) (key -> key).
         * Wywolaj get() dla kluczy 1..6 (6 roznych kluczy), a nastepnie sprawdz
         * ktore z pierwszych kluczy (np. 1, 2) zostaly wypchniete - wypisz wyniki
         * getIfPresent dla wszystkich 6 kluczy.
         */
        public static void main(String[] args) {
            // TODO: twoj kod tutaj
        }
    }

    static class Exercise19_RemovalListenerLogsCause {
        /*
         * 🧪 Zadanie 19:
         * Zbuduj LoadingCache<Integer,String> z maximumSize(2) oraz RemovalListener,
         * ktory wypisuje klucz i notification.getCause() dla kazdego usuniecia.
         * Wstaw 4 rozne klucze (wymuszajac eviction) i na koniec jawnie wywolaj
         * invalidate(...) na jednym z pozostalych - porownaj przyczyny.
         */
        public static void main(String[] args) {
            // TODO: twoj kod tutaj
        }
    }

    static class Exercise20_GetUncheckedVsGet {
        /*
         * 🧪 Zadanie 20:
         * Zbuduj LoadingCache<Integer,Integer> (key -> key*10). Porownaj wywolanie
         * .get(3) (w try-catch na ExecutionException) z .getUnchecked(3) (bez
         * checked wyjatku) - wypisz oba wyniki i skomentuj roznice w println.
         */
        public static void main(String[] args) {
            // TODO: twoj kod tutaj
        }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_ValidationLibraryForUserRegistration {
        /*
         * 🧪 Zadanie 21:
         * Napisz metode rejestrujUzytkownika(String login, String email, int wiek),
         * ktora na starcie uzywa KOMPLETU walidacji Preconditions: checkNotNull
         * dla login/email, checkArgument dla dlugosci loginu (>=3), formatu email
         * (zawiera "@") i wieku (13-120), z czytelnymi komunikatami %s. Przetestuj
         * z co najmniej 4 roznymi zestawami danych (w tym poprawnym).
         */
        public static void main(String[] args) {
            // TODO: twoj kod tutaj
        }
    }

    static class Exercise22_StateMachineWithCheckState {
        /*
         * 🧪 Zadanie 22:
         * Zamodeluj prosta maszyne stanow Zamowienie (NOWE -> OPLACONE -> WYSLANE)
         * jako klase z enum Status i metodami oplac()/wyslij(), z checkState
         * pilnujacym poprawnej kolejnosci przejsc (np. nie mozna wyslac
         * nieoplaconego zamowienia). Przetestuj poprawna sciezke i naruszenie
         * kolejnosci (try-catch).
         */
        public static void main(String[] args) {
            // TODO: twoj kod tutaj
        }
    }

    static class Exercise23_SafeArrayAccessWithElementIndex {
        /*
         * 🧪 Zadanie 23:
         * Napisz metode bezpiecznyDostep(int[] tablica, int index) uzywajaca
         * checkElementIndex(index, tablica.length, "zly indeks") przed dostepem
         * do tablica[index] - przetestuj z poprawnym i niepoprawnym indeksem
         * (w tym indeksem ujemnym).
         */
        public static void main(String[] args) {
            // TODO: twoj kod tutaj
        }
    }

    static class Exercise24_ImmutableValueObjectWithPreconditions {
        /*
         * 🧪 Zadanie 24:
         * Napisz niemutowalna klase ZakresDat(LocalDate od, LocalDate do),
         * ktorej konstruktor uzywa checkNotNull dla obu dat ORAZ checkArgument
         * sprawdzajacego ze "od" jest przed "do" (lub rowne) - przetestuj
         * poprawny zakres i odwrocony (od > do).
         */
        public static void main(String[] args) {
            // TODO: twoj kod tutaj
        }
    }

    static class Exercise25_RefactorManualIfsToPreconditions {
        /*
         * 🧪 Zadanie 25:
         * Dana jest metoda z recznymi if-ami rzucajacymi wyjatki (np.
         * `if (x == null) throw new NullPointerException("x null");
         * if (y < 0) throw new IllegalArgumentException("y<0");`) - przepisz
         * ja na wersje uzywajaca Preconditions.checkNotNull/checkArgument,
         * zachowujac identyczne zachowanie. Wypisz obie wersje i porownaj
         * dzialanie na tych samych danych testowych.
         */
        public static void main(String[] args) {
            // TODO: twoj kod tutaj
        }
    }

    static class Exercise26_ExpensiveApiCallSimulationWithLoadingCache {
        /*
         * 🧪 Zadanie 26:
         * Zasymuluj "wolne API pogodowe" jako CacheLoader<String, String> (klucz =
         * nazwa miasta, wartosc = "sztuczna" pogoda, z Thread.sleep(50) w load()).
         * Zbuduj LoadingCache z maximumSize(5) i recordStats(), odpytaj 3 miasta
         * po kilka razy w petli (mieszajac kolejnosc) i na koniec wypisz
         * stats().hitRate() oraz calkowity zaoszczedzony czas (przyblizony przez
         * porownanie liczby hitow * 50ms).
         */
        public static void main(String[] args) {
            // TODO: twoj kod tutaj
        }
    }

    static class Exercise27_CacheWithRemovalListenerClosingResources {
        /*
         * 🧪 Zadanie 27:
         * Zamodeluj "polaczenie" jako prosta klase Polaczenie z metoda zamknij()
         * (wypisujaca println). Zbuduj LoadingCache<Integer, Polaczenie> z
         * maximumSize(2) i RemovalListener, ktory przy KAZDYM usunieciu wywoluje
         * notification.getValue().zamknij() - wstaw 4 rozne klucze i zweryfikuj
         * ze wyparte polaczenia zostaly zamkniete.
         */
        public static void main(String[] args) {
            // TODO: twoj kod tutaj
        }
    }

    static class Exercise28_CompareGuavaCacheAndCaffeineApiShape {
        /*
         * 🧪 Zadanie 28:
         * Zbuduj DWA rownowazne cache: Guava LoadingCache (CacheBuilder,
         * expireAfterWrite(200, TimeUnit.MILLISECONDS)) oraz - jesli dostepny w
         * tym projekcie (patrz Lesson27_CaffeineBasics) - Caffeine Cache z
         * analogicznym expireAfterWrite(Duration.ofMillis(200)). Wypisz w
         * komentarzu/println kluczowe roznice API (TimeUnit+long vs Duration,
         * LoadingCache vs Cache/LoadingCache w obu bibliotekach).
         */
        public static void main(String[] args) {
            // TODO: twoj kod tutaj
        }
    }

    static class Exercise29_FullValidationAndCachingPipeline {
        /*
         * 🧪 Zadanie 29:
         * Napisz metode obliczCeneZRabatem(String kodProduktu, int ilosc), ktora
         * NAJPIERW waliduje argumenty Preconditions (kodProduktu niepusty, ilosc > 0),
         * a nastepnie pobiera "cene bazowa" z LoadingCache<String, Double>
         * (CacheLoader symulujacy kosztowne zapytanie o cene) i zwraca
         * cena*ilosc. Przetestuj z poprawnymi danymi ORAZ danymi lamiacymi
         * walidacje.
         */
        public static void main(String[] args) {
            // TODO: twoj kod tutaj
        }
    }

    static class Exercise30_CapstoneInventorySystem {
        /*
         * 🧪 Zadanie 30:
         * Zbuduj mini-system magazynowy laczacy WSZYSTKIE narzedzia z tej lekcji:
         * klase Magazyn z metoda dodajTowar(String kod, int ilosc) walidowana
         * Preconditions (checkNotNull, checkArgument), metoda pobierzCene(kod)
         * czytajaca z LoadingCache<String, Double> (CacheLoader symulujacy wolne
         * zapytanie do "cennika"), metoda wydajTowar(kod, ilosc) uzywajaca
         * checkState (czy jest wystarczajaca ilosc na magazynie) - oraz cache z
         * recordStats() i RemovalListener logujacym kazde usuniecie. Przetestuj
         * pelny scenariusz (dodanie, kilka odczytow cen, proba wydania za duzej
         * ilosci) i wypisz koncowe statystyki cache.
         */
        public static void main(String[] args) {
            // TODO: twoj kod tutaj
        }
    }
}
