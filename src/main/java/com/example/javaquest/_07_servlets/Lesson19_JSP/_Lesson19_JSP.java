package com.example.javaquest._07_servlets.Lesson19_JSP;

public class _Lesson19_JSP {

    public static void main(String[] args) {

        /*
         * ============================================================
         * 📦 CZYM JEST JSP (JavaServer Pages)?
         * ============================================================
         * JSP to technologia pozwalajaca pisac strony jako mieszanke
         * zwyklego HTML-a i osadzonego kodu Javy, w plikach z rozszerzeniem
         * .jsp. To, co czyni JSP wyjatkowym, to fakt, ze taki plik NIE JEST
         * interpretowany "na zywo" - silnik JSP (w Tomcacie nazywa sie
         * Jasper) KOMPILUJE kazdy plik .jsp do zwyklej klasy Javy
         * implementujacej Servlet (dokladnie te same API, ktore poznalismy
         * w calym tym rozdziale!), a nastepnie ta wygenerowana klasa
         * dziala jak kazdy inny serwlet - ma swoj cykl zycia, obsluguje
         * zadania HTTP itd.
         *
         * Innymi slowy: JSP to "cukier skladniowy" na serwletach - wygodny
         * sposob pisania widoku HTML z osadzona logika, ktory i tak
         * konczy jako zwykly .java -> .class w tle.
         *
         * UWAGA: ta lekcja jest CELOWO inna niz poprzednie - NIE uruchamiamy
         * tu prawdziwego JSP. Wymagaloby to dodatkowej zaleznosci
         * (tomcat-embed-jasper), fizycznego pliku .jsp na dysku oraz
         * pelnej struktury katalogow webapp (docBase) - znacznie wiecej
         * "ceremonii" niz reszta tego kursu, a JSP samo w sobie jest dzis
         * technologia RZADZIEJ uzywana w nowych projektach (patrz sekcja
         * na koncu). Dlatego ta lekcja ma charakter WYLACZNIE teoretyczny -
         * pokazujemy skladnie i idee na przykladach jako tekst, bez
         * uruchamiania serwera.
         */

        System.out.println("=== Lekcja 19: JSP (JavaServer Pages) - lekcja teoretyczna ===\n");

        /*
         * ============================================================
         * 🔹 PRZYKLADOWY PLIK .jsp – HTML + OSADZONA JAVA
         * ============================================================
         * Klasyczny plik JSP miesza znaczniki HTML ze "scriptletami"
         * (kod Javy w <% %>) oraz wyrazeniami (<%= %>), ktore wstawiaja
         * wynik prosto do wygenerowanego HTML-a:
         */
        String classicJsp = """
                <html>
                <body>
                    <h1>Lista uzytkownikow</h1>
                    <%
                        List<String> users = (List<String>) request.getAttribute("users");
                    %>
                    <ul>
                        <% for (String user : users) { %>
                            <li><%= user %></li>
                        <% } %>
                    </ul>
                </body>
                </html>
                """;
        System.out.println("--- Przyklad 'klasycznego' JSP ze scriptletami ---");
        System.out.println(classicJsp);
        System.out.println("(Scriptlety <% %> to surowy kod Javy WEWNATRZ HTML-a - to jeden z");
        System.out.println(" glownych powodow, dla ktorych JSP wypadlo z lask - patrz podsumowanie)");

        /*
         * ============================================================
         * 🔍 EXPRESSION LANGUAGE (EL) – ${...}
         * ============================================================
         * Expression Language (EL) to prostszy, DEKLARATYWNY sposob
         * odwolywania sie do danych w JSP, bez pisania jawnego kodu Javy.
         * Skladnia ${wyrazenie} pozwala odczytac atrybuty requestu, sesji,
         * kontekstu aplikacji, wlasciwosci obiektow (jak gettery, ale bez
         * "get" i nawiasow) czy elementy kolekcji/map:
         */
        String elExample = """
                <p>Witaj, ${user.imie}!</p>
                <p>Twoj koszyk zawiera ${koszyk.liczbaProduktow} produktow.</p>
                <p>Parametr z URL: ${param.id}</p>
                """;
        System.out.println("\n--- Przyklad Expression Language (EL) ---");
        System.out.println(elExample);
        System.out.println("(${user.imie} to w praktyce user.getImie() - EL sam wywoluje gettery)");

        /*
         * ============================================================
         * 🔍 JSTL – JSP Standard Tag Library (logika BEZ scriptletow)
         * ============================================================
         * JSTL to biblioteka gotowych znacznikow (tagow) pokrywajacych
         * najczestsze potrzeby (petle, warunki, formatowanie), ktore
         * pozwalaja CALKOWICIE wyeliminowac surowy kod Javy (<% %>) ze
         * strony JSP - zamiast petli for w Javie uzywamy <c:forEach>,
         * zamiast if-a <c:if> itd. To krok w strone "czystszego" HTML-a:
         */
        String jstlExample = """
                <%@ taglib prefix="c" uri="jakarta.tags.core" %>

                <ul>
                    <c:forEach var="user" items="${users}">
                        <li>${user}</li>
                    </c:forEach>
                </ul>

                <c:if test="${not empty users}">
                    <p>Liczba uzytkownikow: ${users.size()}</p>
                </c:if>
                """;
        System.out.println("\n--- Przyklad JSTL (bez scriptletow) ---");
        System.out.println(jstlExample);
        System.out.println("(To samo co wyzej, ale bez ani jednej linijki surowej Javy w widoku)");

        System.out.println("\n=== KONIEC LEKCJI 19 ===");

        /*
         * ============================================================
         * 📌 PODSUMOWANIE – DLACZEGO JSP DZIS RZADZIEJ SIE UZYWA?
         * ============================================================
         * - JSP kompiluje sie do zwyklego serwletu (ta sama technologia,
         *   ktora poznalismy w calym rozdziale) - ALE mieszanie znacznikow
         *   HTML z kodem Javy w tym samym pliku (zwlaszcza przez
         *   scriptlety <% %>) LAMIE separacje warstwy widoku od logiki
         *   biznesowej - kod robi sie trudny do czytania, utrzymania i
         *   TESTOWANIA (nie mozna latwo napisac testu jednostkowego dla
         *   fragmentu JSP).
         * - EL (${...}) i JSTL (<c:forEach>, <c:if>...) powstaly WLASNIE
         *   po to, zeby ograniczyc uzycie scriptletow - ale nawet z nimi
         *   JSP wciaz miesza "co pokazac" z "jak to wyrenderowac" w
         *   jednym pliku .jsp.
         * - Nowoczesne podejscia poszly w dwie glowne strony:
         *   1) Silniki szablonow po stronie serwera z czystsza separacja
         *      logiki i widoku (np. Thymeleaf w ekosystemie Spring) -
         *      szablon to zwykly, poprawny HTML z atrybutami, a nie
         *      mieszanka jezykow.
         *   2) Architektura REST API (serwer zwraca dane, np. JSON -
         *      widzielismy to w rozdziale _04_io przy okazji Gson/Jackson)
         *      + oddzielny frontend (React, Angular, Vue), ktory sam
         *      renderuje widok w przegladarce. To kierunek, w ktora
         *      zmierza wiekszosc nowych projektow - i w ktora zmierza
         *      tez dalsza czesc tego kursu.
         * - JSP wciaz "dziala" i mozna je spotkac w starszych, utrzymywanych
         *   projektach - dlatego warto rozumiec jego idee (i to, ze to
         *   "tylko" serwlet pod maska), ale w NOWYCH projektach rzadko
         *   jest to dzis pierwszy wybor.
         */
    }
}
