package com.example.javaquest._16_clean_code.Lesson13_DryKissYagni;

public class _Lesson13_DryKissYagni {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 13: DRY, KISS, YAGNI ===");

        /*
         * ============================================================
         * 📦 TRZY ZASADY, KTORE CHRONIA PRZED NIEPOTRZEBNA ZLOZONOSCIA
         * ============================================================
         * - DRY (Don't Repeat Yourself) - nie duplikuj TEJ SAMEJ wiedzy/logiki
         *   biznesowej w wielu miejscach.
         * - KISS (Keep It Simple, Stupid) - wybieraj najprostsze rozwiazanie,
         *   ktore dziala, zamiast "sprytnego".
         * - YAGNI (You Aren't Gonna Need It) - nie buduj abstrakcji "na
         *   przyszlosc", dopoki nie masz KONKRETNEGO, dzisiejszego powodu.
         * - Wszystkie trzy to rozne kwity na jeden problem: kod jest droższy
         *   w utrzymaniu niz musi byc, gdy dodajemy do niego rzeczy, ktorych
         *   dzis nikt nie potrzebuje (duplikacje, sprytne sztuczki, warstwy
         *   abstrakcji bez realnego uzytku).
         */
        System.out.println("DRY - nie powielaj logiki. KISS - wybieraj prostote. YAGNI - nie buduj na zapas.");

        /*
         * ============================================================
         * 🔹 PROBLEM (DRY): TA SAMA WALIDACJA POWIELONA W 3 MIEJSCACH
         * ============================================================
         * - registerUser, updateUserEmail i importUserFromCsv kazde "na
         *   wlasna reke" sprawdzaja, czy e-mail jest poprawny i czy wiek
         *   miesci sie w rozsadnym zakresie.
         * - To NIE jest przypadkowe podobienstwo - to JEDNA reguła biznesowa
         *   ("co to znaczy poprawny e-mail/wiek uzytkownika") rozmnożona
         *   kopiuj-wklej w 3 miejscach.
         * - Skutek: gdy reguła sie zmieni (np. dopuszczamy dluzsze domeny,
         *   albo min. wiek zmienia sie z 0 na 13), trzeba pamietac, zeby
         *   poprawic WSZYSTKIE 3 miejsca. Latwo o jedno przeoczone miejsce -
         *   to zrodlo bledow, ktore ida w produkcje niezauwazone.
         */
        demonstrateDuplicatedValidationProblem();

        /*
         * ============================================================
         * 🔍 ROZWIAZANIE (DRY): JEDNA METODA WALIDUJACA, WYWOLYWANA 3 RAZY
         * ============================================================
         * - Logika walidacji wyciagnieta do jednej metody `validateEmail` i
         *   jednej `validateAge` - kazde z 3 miejsc uzycia teraz tylko WOLA
         *   te metody, zamiast powtarzac ich tresc.
         * - Zmiana reguly biznesowej to teraz zmiana W JEDNYM miejscu - i
         *   automatycznie dziala wszedzie, gdzie ta metoda jest uzywana.
         * - To jest wlasnie sens DRY: "kazdy fragment wiedzy powinien miec
         *   jedna, jednoznaczna, autorytatywna reprezentacje w systemie"
         *   (definicja Andy'ego Hunta i Dave'a Thomasa, ksiazka "The
         *   Pragmatic Programmer").
         */
        demonstrateExtractedValidationSolution();

        /*
         * ============================================================
         * 📌 OSTRZEZENIE: DUPLIKACJA PRZYPADKOWA VS DUPLIKACJA RZECZYWISTA
         * ============================================================
         * - DRY dotyczy duplikacji WIEDZY/REGULY BIZNESOWEJ, nie duplikacji
         *   TEKSTU kodu. Dwa fragmenty kodu moga wygladac identycznie DZIS,
         *   a mimo to reprezentowac DWIE ROZNE reguly, ktore z czasu na czas
         *   zaczna sie roznic.
         * - Ponizej: calculateStandardShippingFee i calculateGiftWrapFee maja
         *   dzis IDENTYCZNY wzor (waga * 2.0 + 5.0) - to CZYSTY PRZYPADEK.
         *   Pierwsza to polityka cenowa dzialu logistyki, druga - dzialu
         *   marketingu. Gdyby ktos "z troski o DRY" polaczyl je w jedna
         *   metode `calculateFee`, to zmiana ceny wysylki (dzial logistyki)
         *   przypadkiem zmienilaby TEZ cene pakowania na prezent (dzial
         *   marketingu) - a to dwie ZUPELNIE ROZNE decyzje biznesowe.
         * - Zasada: laczysz kod, gdy reprezentuje TEN SAM powod zmiany.
         *   Zostawiasz duplikat (na razie), gdy podobienstwo jest
         *   przypadkowe - "nadgorliwe DRY" tworzy fałszywe sprzezenie
         *   (coupling) miedzy niezwiazanymi ze soba czesciami systemu.
         */
        demonstrateAccidentalVsRealDuplication();

        /*
         * ============================================================
         * 🔹 PROBLEM (KISS): "SPRYTNY" KOD Z BITOWA SZTUCZKA
         * ============================================================
         * - swapSmart zamienia miejscami 2 elementy tablicy BEZ zmiennej
         *   pomocniczej, uzywajac klasycznej sztuczki XOR swap.
         * - Wyglada imponujaco, ale ma UKRYTA PULAPKE: gdy oba indeksy sa
         *   TAKIE SAME (i == j, np. bo element juz jest na wlasciwym
         *   miejscu), sztuczka XOR ZERUJE wartosc zamiast zostawic ja bez
         *   zmian. To realny, znany blad tej techniki.
         * - A co najwazniejsze: NIE jest szybsza niz prosta wersja - obie
         *   to operacja O(1) na 3 przypisaniach. "Sprytnosc" tutaj nie daje
         *   ZADNEJ korzysci wydajnosciowej, a jedynie ryzyko bledu i gorsza
         *   czytelnosc.
         */
        demonstrateCleverButRiskyCode();

        /*
         * ============================================================
         * 🔍 ROZWIAZANIE (KISS): PROSTA ZAMIANA PRZEZ ZMIENNA POMOCNICZA
         * ============================================================
         * - swapSimple robi dokladnie to samo, ale przez zwykla zmienna
         *   `temp` - jest czytelna "na pierwszy rzut oka" i DZIALA POPRAWNIE
         *   rowniez, gdy i == j (bo po prostu nic sie nie zmienia).
         * - Ta sama zlozonosc czasowa (O(1)), zero ukrytych pulapek.
         * - KISS nie mowi "pisz naiwny/gorszy kod" - mowi "nie kupuj
         *   zlozonosci, za ktora nie placisz zadna realna korzyscia".
         */
        demonstrateSimpleReadableCode();

        /*
         * ============================================================
         * 🔹 PROBLEM (YAGNI): ABSTRAKCJA "NA PRZYSZLOSC" BEZ POTRZEBY
         * ============================================================
         * - Potrzeba dzis: wyslac e-mail z potwierdzeniem rejestracji.
         *   Jedna implementacja, zaden sygnal, ze wkrotce dojdzie SMS,
         *   push, czy cokolwiek innego.
         * - A jednak zbudowano: interfejs NotificationSender + fabryke
         *   NotificationSenderFactory + strategie EmailNotificationStrategy
         *   (jedyna, ktora kiedykolwiek istnieje) - 3 typy kodu, zeby
         *   osiagnac to, co 1 klasa zrobilaby rownie dobrze.
         * - Koszt: kazdy, kto czyta ten kod, musi przeskoczyc przez
         *   interfejs -> fabryke -> strategie, zeby dojsc do miejsca, gdzie
         *   NAPRAWDE cos sie dzieje. To "podatek" placony za elastycznosc,
         *   ktorej nikt jeszcze nie potrzebuje.
         */
        demonstrateOverengineeredAbstraction();

        /*
         * ============================================================
         * 🔍 ROZWIAZANIE (YAGNI): PROSTA, BEZPOSREDNIA KLASA
         * ============================================================
         * - EmailNotifier to jedna klasa z jedna metoda `send`. Robi
         *   dokladnie to, czego dzis potrzeba - ani mniej, ani wiecej.
         * - Gdy naprawde pojawi sie 2. sposob powiadamiania (np. SMS) -
         *   WTEDY, majac 2 konkretne implementacje przed oczami, wyciagniecie
         *   wspolnego interfejsu jest latwym, bezpiecznym refaktoringiem
         *   (patrz Lesson16: Extract Class/Extract Interface).
         * - YAGNI nie zabrania abstrakcji - zabrania budowania jej ZANIM
         *   masz co najmniej 2 realne przypadki uzycia, ktore ja uzasadniaja.
         */
        demonstrateSimpleDirectClass();

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - DRY: jedna reguła biznesowa = jedno miejsce w kodzie - ale
         *   uwazaj na duplikacje PRZYPADKOWA (dwa rozne powody zmiany,
         *   ktore dzis akurat wygladaja tak samo).
         * - KISS: wybieraj prostsze rozwiazanie, gdy nie placisz za nie
         *   realna cena wydajnosciowa - "sprytny" kod czesciej ukrywa
         *   bledy niz oszczedza czas procesora.
         * - YAGNI: nie buduj interfejsow/fabryk/strategii "na wszelki
         *   wypadek" - buduj je, gdy masz KONKRETNY, dzisiejszy powod.
         * - W kolejnej lekcji (Lesson14): katalog klasycznych "code smells"
         *   (Fowlera) - sygnalow w kodzie, ktore podpowiadaja, KIEDY warto
         *   siegnac po refaktoring.
         */
        System.out.println("\n=== KONIEC LEKCJI 13 ===");
    }

    // ------------------------------------------------------------------
    // DRY - duplikacja logiki walidacji
    // ------------------------------------------------------------------

    private static void demonstrateDuplicatedValidationProblem() {
        System.out.println("\n=== PROBLEM: WALIDACJA POWIELONA W 3 METODACH ===");
        Duplicated duplicated = new Duplicated();
        System.out.println("registerUser: " + duplicated.registerUser("ala@example.com", 25));
        System.out.println("updateUserEmail: " + duplicated.updateUserEmail("zly-email"));
        System.out.println("importUserFromCsv: " + duplicated.importUserFromCsv("jan@example.com", 200));
    }

    /** "Przed": kazda metoda sama, od nowa, sprawdza email i wiek. */
    static class Duplicated {

        String registerUser(String email, int age) {
            if (email == null || !email.contains("@")) {
                return "BLAD: niepoprawny e-mail";
            }
            if (age < 0 || age > 150) {
                return "BLAD: niepoprawny wiek";
            }
            return "OK: zarejestrowano " + email + " (wiek " + age + ")";
        }

        String updateUserEmail(String email) {
            if (email == null || !email.contains("@")) {
                return "BLAD: niepoprawny e-mail";
            }
            return "OK: zaktualizowano e-mail na " + email;
        }

        String importUserFromCsv(String email, int age) {
            if (email == null || !email.contains("@")) {
                return "BLAD: niepoprawny e-mail";
            }
            if (age < 0 || age > 150) {
                return "BLAD: niepoprawny wiek";
            }
            return "OK: zaimportowano " + email + " (wiek " + age + ")";
        }
    }

    private static void demonstrateExtractedValidationSolution() {
        System.out.println("\n=== ROZWIAZANIE: WSPOLNE METODY validateEmail/validateAge ===");
        Extracted extracted = new Extracted();
        System.out.println("registerUser: " + extracted.registerUser("ala@example.com", 25));
        System.out.println("updateUserEmail: " + extracted.updateUserEmail("zly-email"));
        System.out.println("importUserFromCsv: " + extracted.importUserFromCsv("jan@example.com", 200));
        System.out.println("Reguła walidacji e-maila/wieku istnieje TERAZ w jednym miejscu.");
    }

    /** "Po": jedna reguła walidacji, uzywana z 3 miejsc. */
    static class Extracted {

        private static String validateEmail(String email) {
            if (email == null || !email.contains("@")) {
                return "BLAD: niepoprawny e-mail";
            }
            return null; // brak bledu
        }

        private static String validateAge(int age) {
            if (age < 0 || age > 150) {
                return "BLAD: niepoprawny wiek";
            }
            return null; // brak bledu
        }

        String registerUser(String email, int age) {
            String emailError = validateEmail(email);
            if (emailError != null) return emailError;
            String ageError = validateAge(age);
            if (ageError != null) return ageError;
            return "OK: zarejestrowano " + email + " (wiek " + age + ")";
        }

        String updateUserEmail(String email) {
            String emailError = validateEmail(email);
            if (emailError != null) return emailError;
            return "OK: zaktualizowano e-mail na " + email;
        }

        String importUserFromCsv(String email, int age) {
            String emailError = validateEmail(email);
            if (emailError != null) return emailError;
            String ageError = validateAge(age);
            if (ageError != null) return ageError;
            return "OK: zaimportowano " + email + " (wiek " + age + ")";
        }
    }

    // ------------------------------------------------------------------
    // DRY - duplikacja przypadkowa vs rzeczywista
    // ------------------------------------------------------------------

    private static double calculateStandardShippingFee(double weightKg) {
        // Polityka DZIALU LOGISTYKI - cena wysylki.
        return weightKg * 2.0 + 5.0;
    }

    private static double calculateGiftWrapFee(double weightKg) {
        // Polityka DZIALU MARKETINGU - cena pakowania na prezent.
        // Wzor DZIS identyczny jak wysylka - to PRZYPADEK, nie ta sama reguła!
        return weightKg * 2.0 + 5.0;
    }

    private static void demonstrateAccidentalVsRealDuplication() {
        System.out.println("\n=== DUPLIKACJA PRZYPADKOWA: DWA WZORY, KTORE DZIS WYGLADAJA TAK SAMO ===");
        double weight = 3.0;
        System.out.println("Oplata za wysylke (" + weight + " kg) = " + calculateStandardShippingFee(weight));
        System.out.println("Oplata za pakowanie (" + weight + " kg) = " + calculateGiftWrapFee(weight));
        System.out.println("Wzory sa dzis identyczne, ale to 2 ROZNE decyzje biznesowe (logistyka vs marketing).");
        System.out.println("Gdyby polaczyc je w 1 metode, zmiana ceny wysylki przypadkiem zmienilaby tez cene pakowania.");
    }

    // ------------------------------------------------------------------
    // KISS - "sprytna" sztuczka bitowa vs prosta zamiana
    // ------------------------------------------------------------------

    /** "Sprytna" zamiana bez zmiennej pomocniczej (XOR swap) - ma pulapke przy i == j. */
    private static void swapSmart(int[] arr, int i, int j) {
        arr[i] = arr[i] ^ arr[j];
        arr[j] = arr[i] ^ arr[j];
        arr[i] = arr[i] ^ arr[j];
    }

    /** Znajduje indeks najmniejszego elementu we fragmencie tablicy arr[from..]. */
    private static int indexOfMin(int[] arr, int from) {
        int minIndex = from;
        for (int k = from + 1; k < arr.length; k++) {
            if (arr[k] < arr[minIndex]) {
                minIndex = k;
            }
        }
        return minIndex;
    }

    private static void demonstrateCleverButRiskyCode() {
        System.out.println("\n=== PROBLEM: 'SPRYTNY' XOR SWAP - PULAPKA PRZY i == j ===");
        int[] data = {1, 5, 3}; // 1 to juz najmniejszy element - jest na wlasciwym miejscu (indeks 0)

        int minIndex = indexOfMin(data, 0);
        System.out.println("Najmniejszy element jest juz na indeksie " + minIndex + " (bez zmian potrzebne).");
        System.out.println("Tablica przed 'sprytna' zamiana swapSmart(data, 0, " + minIndex + "): "
                + java.util.Arrays.toString(data));

        swapSmart(data, 0, minIndex); // i == j == 0 - to wlasnie ten przypadek!

        System.out.println("Tablica PO swapSmart(data, 0, 0): " + java.util.Arrays.toString(data)
                + "  <-- BLAD! Element zostal WYZEROWANY zamiast zostac bez zmian.");
    }

    /** Prosta, bezpieczna zamiana przez zmienna pomocnicza. */
    private static void swapSimple(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    private static void demonstrateSimpleReadableCode() {
        System.out.println("\n=== ROZWIAZANIE: PROSTA ZAMIANA PRZEZ ZMIENNA TEMP ===");
        int[] data = {1, 5, 3};
        int minIndex = indexOfMin(data, 0);
        System.out.println("Tablica przed swapSimple(data, 0, " + minIndex + "): " + java.util.Arrays.toString(data));

        swapSimple(data, 0, minIndex); // i == j == 0 - ten sam przypadek, ale BEZ bledu

        System.out.println("Tablica PO swapSimple(data, 0, 0): " + java.util.Arrays.toString(data)
                + "  <-- POPRAWNIE, bez zmian - i ta sama zlozonosc O(1) co wersja 'sprytna'.");
    }

    // ------------------------------------------------------------------
    // YAGNI - nadmierna abstrakcja vs prosta klasa
    // ------------------------------------------------------------------

    /** "Przed": interfejs + fabryka + strategia dla JEDYNEJ istniejacej implementacji. */
    interface NotificationSender {
        String send(String to, String message);
    }

    static class EmailNotificationStrategy implements NotificationSender {
        @Override
        public String send(String to, String message) {
            return "Wyslano e-mail do " + to + ": \"" + message + "\"";
        }
    }

    static class NotificationSenderFactory {
        static NotificationSender create(String channel) {
            // Jedyna galaz, jaka kiedykolwiek istniala - "EMAIL" to jedyny channel.
            if ("EMAIL".equals(channel)) {
                return new EmailNotificationStrategy();
            }
            throw new IllegalArgumentException("Nieznany kanal: " + channel);
        }
    }

    private static void demonstrateOverengineeredAbstraction() {
        System.out.println("\n=== PROBLEM: INTERFEJS + FABRYKA + STRATEGIA DLA 1 IMPLEMENTACJI ===");
        NotificationSender sender = NotificationSenderFactory.create("EMAIL");
        System.out.println(sender.send("ala@example.com", "Witaj w serwisie!"));
        System.out.println("3 typy (interfejs, fabryka, strategia), zeby wyslac 1 e-mail - zaden inny kanal");
        System.out.println("nigdy nie zostal dodany, a kazdy nowy czytelnik musi przejsc przez wszystkie 3 warstwy.");
    }

    /** "Po": jedna, prosta klasa robiaca dokladnie to, czego dzis potrzeba. */
    static class EmailNotifier {
        String send(String to, String message) {
            return "Wyslano e-mail do " + to + ": \"" + message + "\"";
        }
    }

    private static void demonstrateSimpleDirectClass() {
        System.out.println("\n=== ROZWIAZANIE: JEDNA PROSTA KLASA EmailNotifier ===");
        EmailNotifier notifier = new EmailNotifier();
        System.out.println(notifier.send("ala@example.com", "Witaj w serwisie!"));
        System.out.println("Ten sam efekt, bez interfejsu/fabryki/strategii - gdy pojawi sie 2. kanal,");
        System.out.println("wyciagniecie wspolnego interfejsu bedzie prostym refaktoringiem NA PODSTAWIE 2 realnych przypadkow.");
    }
}
