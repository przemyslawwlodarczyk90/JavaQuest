package com.example.javaquest._13_libraries.Lesson06_CommonsLang3;

public class _Exercises_Lesson06_CommonsLang3 {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_IsBlankVsIsEmpty {
        /*
         * 🧪 Zadanie 1:
         * Dla tablicy String[] wartosci = {null, "", "   ", "tekst"} wypisz dla
         * kazdego elementu wynik StringUtils.isEmpty(x) ORAZ StringUtils.isBlank(x)
         * obok siebie, zeby zobaczyc roznice.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_TrimNullSafe {
        /*
         * 🧪 Zadanie 2:
         * Zmienna String tekst = null. Wywolaj StringUtils.trim(tekst) i
         * StringUtils.trimToEmpty(tekst) - wypisz oba wyniki i zademonstruj, ze
         * zadne z nich nie rzuca NullPointerException (w przeciwienstwie do
         * tekst.trim()).
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_CapitalizeSentenceWords {
        /*
         * 🧪 Zadanie 3:
         * Dla zdania "apache commons to biblioteka pomocnicza" rozbij je na slowa
         * (String.split), kazde slowo przepusc przez StringUtils.capitalize i
         * polacz z powrotem spacjami (StringUtils.join).
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_AbbreviateLongTitles {
        /*
         * 🧪 Zadanie 4:
         * Dla listy tytulow {"Wprowadzenie do Apache Commons Lang3", "Java",
         * "Programowanie obiektowe w praktyce"} wypisz kazdy skrocony do 15 znakow
         * przez StringUtils.abbreviate.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_ReverseAndRepeat {
        /*
         * 🧪 Zadanie 5:
         * Dla slowa "kolekcje" wypisz StringUtils.reverse(slowo) oraz
         * StringUtils.repeat(slowo, 3) (bez separatora, potem z separatorem "-").
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_SubstringBetweenHtmlTag {
        /*
         * 🧪 Zadanie 6:
         * Dla stringa "<title>Java Quest</title>" wyciagnij zawartosc miedzy
         * tagami przez StringUtils.substringBetween(s, "<title>", "</title>").
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_DefaultIfNullForConfig {
        /*
         * 🧪 Zadanie 7:
         * Zmienna String port = null reprezentuje brakujaca konfiguracje. Uzyj
         * ObjectUtils.defaultIfNull(port, "8080") i wypisz efektywna wartosc portu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_FirstNonNullFallbackChain {
        /*
         * 🧪 Zadanie 8:
         * Zasymuluj lancuch: String parametrUzytkownika = null, zmiennaSrodowiskowa
         * = null, wartoscDomyslna = "UTC". Uzyj ObjectUtils.firstNonNull na
         * wszystkich trzech i wypisz, ktora wartosc zostala wybrana.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_ValidateNotNullForMethodArgument {
        /*
         * 🧪 Zadanie 9:
         * Napisz metode createUser(String login), ktora na poczatku wywoluje
         * Validate.notNull(login, "Login nie moze byc null"). Wywolaj ja raz z
         * "admin" (sukces) i raz z null (zlap i wypisz komunikat wyjatku).
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_GenerateRandomTestPassword {
        /*
         * 🧪 Zadanie 10:
         * Wygeneruj 5 przykladowych "hasel testowych" przez
         * RandomStringUtils.randomAlphanumeric(10) i wypisz je wszystkie w petli.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_ArrayUtilsContainsAndIndexOf {
        /*
         * 🧪 Zadanie 11:
         * Dla tablicy String[] jezyki = {"Java", "Kotlin", "Python", "Go"} sprawdz
         * ArrayUtils.contains(jezyki, "Python") i ArrayUtils.indexOf(jezyki,
         * "Go") - wypisz wyniki.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_ArrayUtilsAddAndRemove {
        /*
         * 🧪 Zadanie 12:
         * Dla tablicy int[] oceny = {2, 3, 4} dodaj element 5 przez
         * ArrayUtils.add, potem usun element o indeksie 0 przez ArrayUtils.remove
         * - wypisz tablice po kazdym kroku (ArrayUtils.toString).
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_ValidateIsTrueForAgeRange {
        /*
         * 🧪 Zadanie 13:
         * Napisz metode setWiek(int wiek), ktora woluje Validate.isTrue(wiek >= 0
         * && wiek <= 130, "Nieprawidlowy wiek: %d", wiek). Przetestuj wartosciami
         * 25 (sukces) i -3 (zlap wyjatek).
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_RandomNumericIdsGenerator {
        /*
         * 🧪 Zadanie 14:
         * Wygeneruj 10 "numerow zamowien" jako RandomStringUtils.randomNumeric(8) i
         * sprawdz (StringUtils.isNumeric), ze kazdy skladasie WYLACZNIE z cyfr.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_RandomUtilsDiceSimulator {
        /*
         * 🧪 Zadanie 15:
         * Zasymuluj rzut kostka 20 razy uzywajac RandomUtils.nextInt(1, 7) - policz
         * ile razy wypadla kazda wartosc 1-6 i wypisz statystyke.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_JoinCsvLineFromArray {
        /*
         * 🧪 Zadanie 16:
         * Dla tablicy String[] dane = {"Kowalski", "Jan", "32"} zbuduj linie CSV
         * przez StringUtils.join(dane, ";") i wypisz wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_TrimUserInputList {
        /*
         * 🧪 Zadanie 17:
         * Dla tablicy String[] wejscie = {"  Jan  ", null, " Anna", "Piotr "}
         * przepusc kazdy element przez StringUtils.trimToEmpty i wypisz oczyszczona
         * liste (bez wyjatkow mimo elementu null).
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_ValidateNotEmptyForCollection {
        /*
         * 🧪 Zadanie 18:
         * Napisz metode processOrders(java.util.List<String> zamowienia), ktora
         * woluje Validate.notEmpty(zamowienia, "Lista zamowien nie moze byc
         * pusta"). Przetestuj lista pusta (zlap wyjatek) i lista z 2 elementami.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_BuildRandomTestUserRecord {
        /*
         * 🧪 Zadanie 19:
         * Zbuduj "rekord testowy uzytkownika": login = randomAlphabetic(6), haslo =
         * randomAlphanumeric(12), pin = randomNumeric(4) - wypisz wszystkie trzy
         * pola w jednym komunikacie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_CompareJdkVsCommonsLangIsBlank {
        /*
         * 🧪 Zadanie 20:
         * Bez terminala: napisz w komentarzu (min. 4 zdania), kiedy String
         * tekst.isBlank() (JDK 11+) NIE wystarczy i trzeba uzyc
         * StringUtils.isBlank(tekst) - podaj konkretny przyklad z kodu (np. dana z
         * formularza HTTP, ktora moze byc null).
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_ValidateUtilityClassForRegistration {
        /*
         * 🧪 Zadanie 21:
         * Napisz metode registerUser(String login, String email, int wiek), ktora
         * na wejsciu waliduje WSZYSTKIE trzy parametry przez Validate (notNull,
         * notBlank, isTrue dla wieku 18-120) - kazdy z czytelnym komunikatem.
         * Przetestuj min. 3 przypadki bledne i jeden poprawny.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_NullSafeUserProfileFormatter {
        /*
         * 🧪 Zadanie 22:
         * Napisz metode formatProfile(String imie, String nazwisko, String miasto),
         * ktora dla KAZDEGO parametru mogacego byc null lub blank uzywa
         * ObjectUtils.defaultIfNull/StringUtils.defaultIfBlank z wartoscia "brak
         * danych" i zwraca sformatowany opis profilu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_ArrayUtilsBasedUniqueTagsBuilder {
        /*
         * 🧪 Zadanie 23:
         * Zaczynajac od pustej tablicy String[] tagi = {}, dodawaj kolejno tagi
         * "java", "commons", "java" (duplikat!) przez ArrayUtils.add, ale TYLKO
         * jesli ArrayUtils.contains jeszcze go nie ma - wypisz finalna tablice bez
         * duplikatow.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_TestDataGeneratorForNUsers {
        /*
         * 🧪 Zadanie 24:
         * Napisz metode generujaca N "uzytkownikow testowych" (login, haslo, wiek
         * jako RandomUtils.nextInt(18, 80)) i zwracajaca je jako
         * java.util.List<String> opisow - wygeneruj 5 rekordow i wypisz.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_StringUtilsBasedCsvLineParser {
        /*
         * 🧪 Zadanie 25:
         * Dla linii CSV "Jan; ;Kowalski;" (z pustymi/blank polami) rozbij po ";"
         * (String.split), a nastepnie dla kazdego pola uzyj StringUtils.isBlank do
         * oznaczenia go jako "(puste)" w wypisywanym wyniku.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_ValidateGuardedBankAccountClass {
        /*
         * 🧪 Zadanie 26:
         * Zaprojektuj klase BankAccount z metoda withdraw(double kwota), ktora
         * uzywa Validate.isTrue do sprawdzenia, ze kwota > 0 ORAZ kwota <= saldo -
         * przetestuj min. 3 scenariusze (poprawna wyplata, ujemna kwota, kwota
         * wieksza niz saldo).
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_ArrayUtilsMatrixRowOperations {
        /*
         * 🧪 Zadanie 27:
         * Dla "macierzy" int[][] macierz reprezentujacej 3 wiersze, dla kazdego
         * wiersza uzyj ArrayUtils.reverse (odwraca w miejscu) i wypisz macierz
         * przed i po operacji (ArrayUtils.toString dla kazdego wiersza).
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_CombineStringUtilsAndValidateForCommandParser {
        /*
         * 🧪 Zadanie 28:
         * Napisz prosty "parser komend" parseCommand(String linia): najpierw
         * Validate.notBlank(linia), potem StringUtils.trim, StringUtils.split po
         * spacji na czesci - pierwsza czesc to nazwa komendy (StringUtils.upperCase),
         * reszta to argumenty. Przetestuj na "  dodaj  plik.txt  ".
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_RandomDataSetGeneratorForReportDemo {
        /*
         * 🧪 Zadanie 29:
         * Zbuduj 10-elementowa "tabele raportowa" (id = randomNumeric(5), nazwa =
         * randomAlphabetic(8), wartosc = RandomUtils.nextInt(100, 10000)) i wypisz
         * ja w czytelnym formacie tabelarycznym (String.format/printf).
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_MiniValidationFrameworkOnTopOfValidate {
        /*
         * 🧪 Zadanie 30:
         * Zbuduj klase FormValidator z metodami validateLogin(String),
         * validateEmail(String), validateAge(int) - kazda oparta o Validate
         * (notBlank/isTrue/matches wyrazenie regularne dla emaila) - zbierajaca
         * WSZYSTKIE bledy w java.util.List<String> zamiast przerywac na
         * pierwszym (przechwytuj kazdy wyjatek Validate osobno) - przetestuj na
         * formularzu z 2 bledami i 1 poprawnym polem.
         */
        public static void main(String[] args) { }
    }
}
