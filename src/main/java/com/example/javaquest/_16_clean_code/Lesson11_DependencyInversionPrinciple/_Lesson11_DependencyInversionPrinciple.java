package com.example.javaquest._16_clean_code.Lesson11_DependencyInversionPrinciple;

public class _Lesson11_DependencyInversionPrinciple {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 11: DEPENDENCY INVERSION PRINCIPLE (DIP) ===");

        /*
         * ============================================================
         * 🔹 DEFINICJA DIP (OSTATNIA LITERA SOLID)
         * ============================================================
         * - Sformulowanie Roberta C. Martina, 2 czesci:
         *   1) "Moduly wysokopoziomowe NIE powinny zalezec od modulow
         *      niskopoziomowych - OBA powinny zalezec od ABSTRAKCJI."
         *   2) "Abstrakcje NIE powinny zalezec od szczegolow -
         *      SZCZEGOLY powinny zalezec od abstrakcji."
         * - "Modul wysokopoziomowy" = kod zawierajacy WAZNA logike
         *   biznesowa/polityke (np. "wyslij powiadomienie o zamowieniu").
         *   "Modul niskopoziomowy" = konkretny SZCZEGOL techniczny (np.
         *   "jak dokladnie wyslac e-mail przez SMTP").
         * - "Inwersja" w nazwie: BEZ tej zasady zaleznosc plynie od
         *   wysokiego poziomu DO niskiego (logika biznesowa zalezy od
         *   szczegolu technicznego). Z zasada - obie strony zalezna od
         *   wspolnej ABSTRAKCJI (interfejsu) - kierunek zaleznosci
         *   "odwraca sie" wzgledem tego, co intuicyjne.
         */
        System.out.println("DIP: modul wysokopoziomowy (logika) i niskopoziomowy (szczegol) zalezna OBA od abstrakcji.");

        /*
         * ============================================================
         * 🔍 PRZYKLAD NARUSZENIA DIP: NotificationService ZALEZNY OD EmailSender
         * ============================================================
         * - BadNotificationService (modul WYSOKOPOZIOMOWY - logika "wyslij
         *   powiadomienie o zamowieniu") tworzy w SOBIE (`new EmailSender()`)
         *   konkretna, niskopoziomowa implementacje wysylki e-mail.
         * - Zaleznosc plynie WPROST: logika biznesowa -> konkretny szczegol
         *   techniczny (EmailSender, klasa niskiego poziomu).
         * - Problem: jesli firma chce DODAC powiadomienia SMS (bez usuwania
         *   e-maila), trzeba ZMODYFIKOWAC kod BadNotificationService - a to
         *   jednoczesnie naruszenie Open/Closed Principle (Lesson08)!
         */
        demonstrateBadExample();

        /*
         * ============================================================
         * 🔍 DLACZEGO TO PROBLEM W PRAKTYCE
         * ============================================================
         * - Testowanie: chcac przetestowac logike BadNotificationService BEZ
         *   wysylania prawdziwych e-maili, NIE mamy jak "podmienic" EmailSender
         *   na atrape (mock) - jest on twardo "wstrzykniety" (`new EmailSender()`
         *   w srodku klasy).
         * - Rozszerzalnosc: kazdy nowy kanal powiadomien (SMS, push,
         *   Slack) wymaga zmiany ISTNIEJACEGO, dzialajacego kodu
         *   BadNotificationService - ryzyko zepsucia czegos, co juz dziala.
         * - Ta sama logika, co niepoprawny kod z Lesson08 (OCP) - DIP i OCP
         *   czesto ida w parze: odwrocenie zaleznosci na abstrakcje jest
         *   JEDNYM ze sposobow OSIAGNIECIA otwartosci na rozszerzenia.
         */
        demonstrateTestingProblem();

        /*
         * ============================================================
         * 📌 REFAKTORYZACJA: WPROWADZENIE ABSTRAKCJI MessageSender
         * ============================================================
         * - Wprowadzamy interfejs `MessageSender` (abstrakcja) z metoda
         *   `send(String recipient, String message)`.
         * - GoodNotificationService (modul WYSOKOPOZIOMOWY) zalezy TERAZ
         *   TYLKO od interfejsu MessageSender - NIE wie NIC o tym, czy pod
         *   spodem jest e-mail, SMS, czy cokolwiek innego.
         * - EmailMessageSender i SmsMessageSender (moduly NISKOPOZIOMOWE,
         *   SZCZEGOLY) implementuja MessageSender - to ONE teraz "zalezna
         *   od abstrakcji", zgodnie z druga czescia definicji DIP.
         * - Konkretna implementacje PRZEKAZUJEMY z zewnatrz przez
         *   KONSTRUKTOR (constructor injection) - GoodNotificationService
         *   dostaje gotowy MessageSender, zamiast tworzyc go samodzielnie
         *   przez `new`.
         */
        demonstrateGoodExample();

        /*
         * ============================================================
         * 🔍 CONSTRUCTOR INJECTION - RECZNIE, BEZ FRAMEWORKA
         * ============================================================
         * - "Wstrzykiwanie zaleznosci" (dependency injection) to NIE
         *   magia frameworka - to zwykla technika: obiekt DOSTAJE swoje
         *   zaleznosci jako argumenty konstruktora, zamiast tworzyc je
         *   samodzielnie przez `new`.
         * - W tym kursie widziales juz DI "recznie" (bez kontenera) - a w
         *   `_13_libraries/Lesson18_WhyDependencyInjection` i
         *   `Lesson19_GuiceBasics` poznasz, jak KONTENER DI (np. Google
         *   Guice) automatyzuje TWORZENIE i PRZEKAZYWANIE tych zaleznosci
         *   na duza skale - ale sama ZASADA (zaleznosc od interfejsu, nie
         *   konkretnej klasy) to WLASNIE DIP, ktora poznajesz tutaj.
         * - Podobnie Spring (poznany na koncu tego kursu) opiera CALY swoj
         *   model komponentow (`@Autowired`, `@Service`) na DIP - kontener
         *   Springa to "automatyczny dostawca" zaleznosci zgodnych z
         *   abstrakcjami.
         */
        System.out.println("\nConstructor injection = recznie przekazana zaleznosc - fundament pod Guice/Spring DI.");

        /*
         * ============================================================
         * 🔍 DIP UMOZLIWIA LATWE TESTOWANIE (TEST DOUBLE)
         * ============================================================
         * - Skoro GoodNotificationService zalezy od INTERFEJSU
         *   MessageSender, mozemy w tescie podac WLASNA, prosta
         *   implementacje (np. `RecordingMessageSender`), ktora zamiast
         *   naprawde wysylac wiadomosc, TYLKO ZAPAMIETUJE, ze zostala
         *   wywolana - bez potrzeby prawdziwej sieci/SMTP/frameworka do
         *   mockowania.
         */
        demonstrateTestDoubleForTesting();

        /*
         * ============================================================
         * 📌 DIP A ZALEZNOSC OD KONKRETNYCH KLAS (NIE ZAWSZE ZLE)
         * ============================================================
         * - DIP NIE oznacza "kazda klasa musi miec interfejs" - to
         *   powszechne nieporozumienie. Zaleznosc od KONKRETNEJ klasy jest
         *   OK, gdy ta klasa jest STABILNA i mato prawdopodobne, ze
         *   bedziemy potrzebowac jej WYMIENIC (np. `java.lang.String`,
         *   `java.util.ArrayList` jako implementacja `List`).
         * - DIP ma znaczenie TAM, gdzie realnie oczekujemy WIELU
         *   implementacji/mozliwosci PODMIANY (zrodlo danych, kanal
         *   powiadomien, dostawca platnosci) - LUB tam, gdzie chcemy
         *   testowac logike w IZOLACJI od kosztownego/niedeterministycznego
         *   szczegolu (siec, plik, baza danych).
         */
        System.out.println("\nDIP nie znaczy 'kazda klasa = interfejs' - dotyczy miejsc z realna zmiennoscia/potrzeba izolacji w testach.");

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - DIP: logika wysokopoziomowa i szczegoly niskopoziomowe zalezna
         *   OBIE od wspolnej abstrakcji (interfejsu) - nie logika wprost od
         *   szczegolu.
         * - Osiagane przez constructor injection: obiekt DOSTAJE zaleznosc
         *   z zewnatrz, zamiast tworzyc ja przez `new` w srodku.
         * - Korzysci: latwiejsze testowanie (test doubles), latwiejsza
         *   rozszerzalnosc (nowa implementacja bez zmiany logiki - powiazane
         *   z OCP z Lesson08).
         * - To byla OSTATNIA z 5 zasad SOLID (Lesson07-11). W kolejnej
         *   lekcji (Lesson12): sprzezenie (coupling), spojnosc (cohesion) i
         *   Prawo Demeter - jak mierzyc i ograniczac ZALEZNOSCI MIEDZY
         *   klasami na jeszcze bardziej ogolnym poziomie.
         */
        System.out.println("\n=== KONIEC LEKCJI 11 (KONIEC BLOKU SOLID) ===");
    }

    private static void demonstrateBadExample() {
        System.out.println("\n=== ZLY PRZYKLAD: NotificationService ZALEZNY WPROST OD EmailSender ===");

        BadNotificationService service = new BadNotificationService();
        service.notifyOrderShipped("jan@example.com", "12345");
    }

    /** ZLA: wysokopoziomowa logika tworzy KONKRETNA, niskopoziomowa zaleznosc przez `new`. */
    static class EmailSender {
        void sendEmail(String recipient, String message) {
            System.out.println("[EMAIL] Do: " + recipient + ", tresc: " + message);
        }
    }

    static class BadNotificationService {
        private final EmailSender emailSender = new EmailSender(); // sztywna zaleznosc od SZCZEGOLU!

        void notifyOrderShipped(String customerEmail, String orderId) {
            emailSender.sendEmail(customerEmail, "Twoje zamowienie " + orderId + " zostalo wyslane.");
        }
    }

    private static void demonstrateTestingProblem() {
        System.out.println("\n=== PROBLEM: NIE MOZNA PODMIENIC EmailSender NA ATRAPE W TESCIE ===");
        System.out.println("BadNotificationService.emailSender jest tworzone przez `new` W SRODKU klasy -");
        System.out.println("z zewnatrz (np. z testu) nie da sie go podmienic bez modyfikacji kodu klasy.");
        System.out.println("Dodanie kanalu SMS wymagaloby ZMIANY tej samej, dzialajacej klasy (narusza tez OCP z Lesson08).");
    }

    private static void demonstrateGoodExample() {
        System.out.println("\n=== DOBRY PRZYKLAD: GoodNotificationService ZALEZNY OD ABSTRAKCJI MessageSender ===");

        MessageSender email = new EmailMessageSender();
        GoodNotificationService serviceWithEmail = new GoodNotificationService(email);
        serviceWithEmail.notifyOrderShipped("jan@example.com", "12345");

        MessageSender sms = new SmsMessageSender();
        GoodNotificationService serviceWithSms = new GoodNotificationService(sms);
        serviceWithSms.notifyOrderShipped("+48123456789", "67890");

        System.out.println("-> TA SAMA klasa GoodNotificationService obsluzyla email I SMS - bez zadnej zmiany w jej kodzie.");
    }

    /** Abstrakcja - OBIE strony (logika i szczegoly) zalezna od NIEJ, nie od siebie nawzajem. */
    interface MessageSender {
        void send(String recipient, String message);
    }

    /** Modul niskopoziomowy (szczegol) - implementuje abstrakcje. */
    static class EmailMessageSender implements MessageSender {
        @Override
        public void send(String recipient, String message) {
            System.out.println("[EMAIL] Do: " + recipient + ", tresc: " + message);
        }
    }

    /** Modul niskopoziomowy (szczegol) - implementuje TA SAMA abstrakcje. */
    static class SmsMessageSender implements MessageSender {
        @Override
        public void send(String recipient, String message) {
            System.out.println("[SMS] Do: " + recipient + ", tresc: " + message);
        }
    }

    /** Modul wysokopoziomowy - zalezy TYLKO od abstrakcji MessageSender, dostarczonej przez konstruktor. */
    static class GoodNotificationService {
        private final MessageSender messageSender;

        GoodNotificationService(MessageSender messageSender) {
            this.messageSender = messageSender; // constructor injection - zaleznosc PRZEKAZANA, nie utworzona wewnatrz
        }

        void notifyOrderShipped(String recipient, String orderId) {
            messageSender.send(recipient, "Twoje zamowienie " + orderId + " zostalo wyslane.");
        }
    }

    private static void demonstrateTestDoubleForTesting() {
        System.out.println("\n=== TESTOWANIE Z WLASNA, PROSTA IMPLEMENTACJA (TEST DOUBLE) ===");

        RecordingMessageSender recorder = new RecordingMessageSender();
        GoodNotificationService serviceUnderTest = new GoodNotificationService(recorder);

        serviceUnderTest.notifyOrderShipped("test@example.com", "TEST-1");

        System.out.println("RecordingMessageSender zapamietal wywolanie (bez wysylania czegokolwiek naprawde):");
        System.out.println("  odbiorca=" + recorder.lastRecipient + ", wiadomosc=" + recorder.lastMessage);
    }

    /** Test double - implementacja MessageSender uzywana WYLACZNIE do testow, bez prawdziwej wysylki. */
    static class RecordingMessageSender implements MessageSender {
        String lastRecipient;
        String lastMessage;

        @Override
        public void send(String recipient, String message) {
            this.lastRecipient = recipient;
            this.lastMessage = message;
        }
    }
}
