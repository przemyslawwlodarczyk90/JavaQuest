package com.example.javaquest._07_servlets.Lesson09_FormParameters;

public class _Exercises_Lesson09_FormParameters {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_SingleTextFieldServlet {
        /*
         * 🧪 Zadanie 1:
         * Uruchom embedded Tomcat (port 0) z serwletem mapowanym na "/kontakt" obsługującym
         * doPost(). Wyślij POST z ciałem "email=jan@test.pl" i nagłówkiem Content-Type
         * "application/x-www-form-urlencoded". Serwlet ma odczytać req.getParameter("email")
         * i odesłać "Otrzymano email: jan@test.pl". Wypisz status i body.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_MultipleDistinctFields {
        /*
         * 🧪 Zadanie 2:
         * Do serwletu z Zadania 1 (lub nowego pod "/rejestracja") wyślij ciało
         * "imie=Anna&nazwisko=Kowalska&wiek=28". Odczytaj wszystkie trzy parametry
         * przez getParameter() i wypisz je w jednej linii tekstu odpowiedzi.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_CheckboxMultiValues {
        /*
         * 🧪 Zadanie 3:
         * Wyślij ciało "jezyk=pl&jezyk=en&jezyk=de" na serwlet, który odczytuje
         * req.getParameterValues("jezyk") i wypisuje wszystkie wartości oddzielone przecinkiem.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_GetParameterVsGetParameterValues {
        /*
         * 🧪 Zadanie 4:
         * Dla tego samego ciała "kolor=czerwony&kolor=zielony&kolor=niebieski" wypisz w
         * odpowiedzi zarówno wynik req.getParameter("kolor") (tylko pierwsza wartość),
         * jak i req.getParameterValues("kolor") (wszystkie trzy) – porównaj obie linie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_ParameterMapIteration {
        /*
         * 🧪 Zadanie 5:
         * Wyślij dowolne ciało z co najmniej 4 różnymi polami (np. "a=1&b=2&c=3&d=4").
         * W serwlecie zaiteruj po req.getParameterMap() (Map<String,String[]>) i wypisz
         * każdy klucz razem z jego wartościami (String.join(", ", values)).
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_MissingParameterNullHandling {
        /*
         * 🧪 Zadanie 6:
         * Wyślij ciało "imie=Jan" (bez pola "nazwisko"). W serwlecie odczytaj
         * req.getParameter("nazwisko") i sprawdź, że zwraca null – wypisz "brak nazwiska",
         * jeśli wartość jest null, zamiast rzucać wyjątek.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_QueryStringSameAsFormBody {
        /*
         * 🧪 Zadanie 7:
         * Wyślij żądanie GET na "/rejestracja?imie=Ola&wiek=22" (parametry w URL-u, bez ciała).
         * Ten sam serwlet (obsługujący też doGet()) ma odczytać req.getParameter("imie") i
         * req.getParameter("wiek") dokładnie tak samo, jak dla parametrów z ciała POST.
         * Wypisz odpowiedź i porównaj z Zadaniem 2.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_SafeNumberParsing {
        /*
         * 🧪 Zadanie 8:
         * Odczytaj parametr "wiek" jako String, spróbuj sparsować go do int (Integer.parseInt)
         * w bloku try/catch. Przetestuj raz z poprawną wartością "wiek=30" (sukces, wypisz liczbę)
         * i raz z niepoprawną "wiek=abc" (przechwycony NumberFormatException, wypisz komunikat błędu).
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_CheckboxPresenceAsBoolean {
        /*
         * 🧪 Zadanie 9:
         * Wyślij dwa osobne żądania: raz z ciałem "zgoda=on", raz z ciałem BEZ pola "zgoda"
         * w ogóle. Serwlet ma zwrócić "Zgoda: TAK" lub "Zgoda: NIE" na podstawie tego, czy
         * req.getParameter("zgoda") != null. Wypisz oba wyniki.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_RequiredFieldsValidation {
        /*
         * 🧪 Zadanie 10:
         * Zbuduj prosty formularz rejestracyjny wymagający pól "imie" i "email".
         * Wyślij raz komplet danych (sukces, "Rejestracja OK"), raz dane z brakującym
         * "email" (serwlet ma wykryć null i odpowiedzieć "Blad: brak pola email").
         * Wypisz oba wyniki.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_QueryPlusBodyCombined {
        /*
         * 🧪 Zadanie 11:
         * Wyślij POST na "/rejestracja?ref=newsletter" (parametr w query stringu) z ciałem
         * "imie=Jan" (parametr w body, Content-Type x-www-form-urlencoded). W serwlecie
         * odczytaj OBA parametry (req.getParameter("ref") i req.getParameter("imie")) –
         * Tomcat łączy oba źródła w jedną pulę parametrów. Wypisz oba odczytane pola.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_MultipleCheckboxGroups {
        /*
         * 🧪 Zadanie 12:
         * Zbuduj "ankietę" z dwoma niezależnymi grupami checkboxów w jednym ciele, np.
         * "kolory=czerwony&kolory=zielony&hobby=sport&hobby=muzyka&hobby=czytanie".
         * W serwlecie odczytaj obie grupy przez getParameterValues i wypisz liczność
         * oraz zawartość każdej z osobna.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_RequiredCheckboxGroupValidation {
        /*
         * 🧪 Zadanie 13:
         * Serwlet ma wymagać co najmniej JEDNEJ zaznaczonej wartości w grupie "zainteresowania".
         * Wyślij raz ciało z co najmniej jedną wartością (status 200), raz ciało w ogóle
         * bez pola "zainteresowania" (serwlet wywołuje resp.sendError(400, "Wybierz co najmniej jedno")).
         * Wypisz oba statusy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_StatelessRequestIsolation {
        /*
         * 🧪 Zadanie 14:
         * Wyślij dwa KOLEJNE żądania POST do TEGO SAMEGO serwletu z różnymi danymi formularza
         * (np. pierwsze "imie=Jan", drugie "imie=Ewa&nazwisko=Nowak"). Zweryfikuj, że druga
         * odpowiedź NIE zawiera żadnych śladów danych z pierwszego żądania (parametry HTTP
         * są przetwarzane niezależnie dla każdego żądania).
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_UrlEncodingSpecialCharacters {
        /*
         * 🧪 Zadanie 15:
         * Zbuduj ciało formularza ręcznie kodując wartość zawierającą spację i znak "&"
         * za pomocą java.net.URLEncoder.encode(wartosc, "UTF-8"), np. dla pola "opis" o
         * wartości "Rower & Kask - super cena". Wyślij takie ciało i zweryfikuj, że serwlet
         * (req.getParameter("opis")) odczytuje oryginalny, zdekodowany tekst poprawnie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_DynamicParameterNamesEnumeration {
        /*
         * 🧪 Zadanie 16:
         * Wyślij ciało z parametrami o nieznanych z góry nazwach (np. "x1=a&x2=b&x3=c").
         * W serwlecie użyj req.getParameterNames() (Enumeration<String>), zbierz nazwy do
         * listy, posortuj alfabetycznie i wypisz razem z wartościami każdego parametru.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_LoginFormSimulation {
        /*
         * 🧪 Zadanie 17:
         * Zbuduj serwlet logowania sprawdzający pola "login" i "haslo" na zgodność z
         * zaszytymi na sztywno wartościami ("admin"/"tajne123"). Wyślij raz poprawne dane
         * (odpowiedź "Zalogowano"), raz błędne hasło (odpowiedź "Nieprawidlowe dane logowania").
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_PolishDiacriticsInFormValues {
        /*
         * 🧪 Zadanie 18:
         * Ustaw req.setCharacterEncoding("UTF-8") na początku doPost() PRZED odczytem
         * jakiegokolwiek parametru. Wyślij zakodowane w UTF-8 ciało z polem
         * "imie=Zażółć%20gęślą%20jaźń" (URLEncoder.encode) i zweryfikuj, że parametr
         * odczytany przez getParameter("imie") zachowuje polskie znaki bez zniekształceń.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_MultiStepFormSingleServlet {
        /*
         * 🧪 Zadanie 19:
         * Zbuduj JEDEN serwlet obsługujący dwa "kroki" formularza na podstawie ukrytego
         * pola "krok". Dla "krok=1" oczekuje pola "imie" i odpowiada "Krok 1 OK, podaj adres".
         * Dla "krok=2" oczekuje pola "adres" i odpowiada "Krok 2 OK, rejestracja zakonczona".
         * Wyślij dwa kolejne żądania (krok=1, potem krok=2) i wypisz obie odpowiedzi.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_FullParameterReportBuilder {
        /*
         * 🧪 Zadanie 20:
         * Wyślij ciało z mieszanką pól pojedynczych i wielowartościowych, np.
         * "imie=Jan&kolor=a&kolor=b&miasto=Warszawa". W serwlecie zbuduj raport tekstowy:
         * dla każdego klucza z getParameterMap() posortowanego alfabetycznie, wartości
         * wielokrotne połącz przecinkami. Wypisz cały raport.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_ShoppingCartParallelArrays {
        /*
         * 🧪 Zadanie 21:
         * Wyślij ciało symulujące koszyk zakupowy: "produkt=Chleb&ilosc=2&produkt=Mleko&ilosc=3"
         * (dwie równoległe tablice tej samej długości uzyskane przez getParameterValues
         * dla "produkt" i "ilosc"). Zweryfikuj w serwlecie, że tablice mają równą długość,
         * zbuduj z nich Map<String,Integer>, policz sumę na podstawie zaszytego cennika
         * (Chleb=5, Mleko=4) i wypisz łączną kwotę.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_MethodOverrideHiddenField {
        /*
         * 🧪 Zadanie 22:
         * Zbuduj serwlet obsługujący TYLKO doPost(), który odczytuje ukryte pole formularza
         * "_method". Jeśli "_method=PUT" (lub "DELETE"), serwlet ma wykonać logikę
         * odpowiadającą tej metodzie (mimo że fizycznie żądanie to POST) – typowy wzorzec
         * obchodzenia ograniczeń formularzy HTML (które nie wysyłają natywnie PUT/DELETE).
         * Wyślij POST z ciałem "_method=DELETE&id=5" i zweryfikuj poprawną obsługę.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_TooManyParametersProtection {
        /*
         * 🧪 Zadanie 23:
         * Zbuduj serwlet odrzucający żądania z więcej niż 20 parametrami
         * (req.getParameterMap().size() > 20 -> resp.sendError(400, "Za duzo pol formularza")).
         * Wygeneruj programowo ciało z 25 różnymi polami (np. "pole1=a&pole2=b&...") i
         * zweryfikuj, że serwlet zwraca status 400.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_NumericRangeValidationReport {
        /*
         * 🧪 Zadanie 24:
         * Zwaliduj pola "wiek" (0-120) i "staz" (0-50) z ciała formularza. Dla wartości spoza
         * zakresu dopisz komunikat błędu do listy. Wyślij ciało z "wiek=200&staz=10" i
         * zweryfikuj, że raport błędów zawiera dokładnie jeden komunikat (o polu "wiek").
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_ComprehensiveMixedFieldReport {
        /*
         * 🧪 Zadanie 25:
         * Zbuduj formularz z co najmniej 5 polami różnych rodzajów: pojedyncze pole tekstowe,
         * pole liczbowe, grupa checkboxów (min. 2 wartości), pole opcjonalne (czasem puste)
         * oraz pole całkowicie pominięte w wysyłce. W jednym raporcie tekstowym użyj razem
         * getParameter, getParameterValues i getParameterMap, jasno oznaczając które pole
         * jakiego API użyło.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_DuplicateCheckboxValueDeduplication {
        /*
         * 🧪 Zadanie 26:
         * Wyślij ciało z przypadkowo zduplikowaną wartością checkboxa:
         * "kolor=czerwony&kolor=czerwony&kolor=niebieski". W serwlecie odczytaj
         * getParameterValues("kolor"), usuń duplikaty (np. przez LinkedHashSet zachowujący
         * kolejność) i wypisz listę unikalnych wartości ("czerwony, niebieski").
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_CsvRowsReconstruction {
        /*
         * 🧪 Zadanie 27:
         * Wyślij ciało z powtórzonym parametrem "wiersz" symulującym wiersze CSV, np.
         * "wiersz=Jan,25,Warszawa&wiersz=Ewa,30,Krakow&wiersz=Piotr,22,Gdansk". Odczytaj
         * wszystkie wystąpienia przez getParameterValues("wiersz"), rozbij każdy po przecinku
         * i wypisz jako sformatowaną tabelę (imię, wiek, miasto).
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_HoneypotAntiSpamField {
        /*
         * 🧪 Zadanie 28:
         * Dodaj do formularza kontaktowego niewidoczne pole "website" (honeypot – prawdziwi
         * użytkownicy nigdy go nie wypełniają, boty tak). Jeśli req.getParameter("website")
         * jest niepuste, serwlet ma odrzucić żądanie (resp.sendError(400, "Wykryto spam")).
         * Przetestuj raz z pustym "website=" (sukces), raz z wypełnionym "website=http://spam.pl" (400).
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_SharedValidationAcrossTwoServlets {
        /*
         * 🧪 Zadanie 29:
         * Zarejestruj DWA serwlety w tym samym kontekście: "/form-a" i "/form-b", oba
         * wymagające pola "email". Wydziel prywatną metodę pomocniczą (może być statyczna
         * klasa/metoda narzędziowa) sprawdzającą obecność i poprawność (zawiera "@") pola
         * "email", wywoływaną z doPost() OBU serwletów. Wyślij po jednym żądaniu do każdego
         * z brakującym/błędnym emailem i zweryfikuj spójne komunikaty błędów z obu serwletów.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_EndToEndValidationSummary {
        /*
         * 🧪 Zadanie 30:
         * Wyślij do JEDNEGO serwletu walidującego formularz (wymagane: "imie", "email";
         * opcjonalne: "wiek" musi być liczbą) sekwencję 4 różnych żądań: (1) w pełni poprawne,
         * (2) z brakującym polem "email", (3) z dodatkowym nieoczekiwanym polem "extra"
         * (powinno być po prostu zignorowane), (4) z niepoprawną wartością "wiek=abc".
         * Zbuduj i wypisz tabelę podsumowującą oczekiwany vs faktyczny wynik każdego z 4 żądań.
         */
        public static void main(String[] args) { }
    }
}
