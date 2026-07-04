package com.example.javaquest._07_servlets.Lesson19_JSP;

public class _Exercises_Lesson19_JSP {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)
    // UWAGA: ta lekcja jest teoretyczna (bez żywego Tomcata/Jaspera) - ćwiczenia
    // polegają na napisaniu fragmentów JSP jako tekstu (np. text block) i wypisaniu ich
    // przez System.out.println, DOKŁADNIE w duchu przykładów z lekcji teoretycznej.

    static class Exercise01_WriteScriptletHelloJsp {
        /*
         * 🧪 Zadanie 1:
         * Napisz (jako String/text block) minimalny fragment .jsp ze scriptletem: kod
         * "<% String name = "Kasia"; %>" oraz wyrażenie "<%= name %>" umieszczone
         * wewnątrz nagłówka <h1>. Wypisz cały fragment na konsolę przez
         * System.out.println.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_WriteElSingleValue {
        /*
         * 🧪 Zadanie 2:
         * Napisz fragment JSP pokazujący odczyt pojedynczego atrybutu przez Expression
         * Language: "${message}" wewnątrz znacznika <p>. Wypisz go na konsolę.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_WriteElNestedProperty {
        /*
         * 🧪 Zadanie 3:
         * Napisz fragment JSP z EL odczytującym zagnieżdżoną właściwość obiektu, np.
         * "${user.address.city}". Wypisz fragment oraz krótki komentarz (println)
         * wyjaśniający, jakie wywołania getterów (getUser().getAddress().getCity())
         * reprezentuje to wyrażenie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_WriteJstlIfBlock {
        /*
         * 🧪 Zadanie 4:
         * Napisz fragment JSP z dyrektywą taglib JSTL na górze oraz blokiem
         * "<c:if test=\"${empty cart}\">Koszyk jest pusty</c:if>". Wypisz go na
         * konsolę.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_WriteJstlForEachOverList {
        /*
         * 🧪 Zadanie 5:
         * Napisz fragment JSP z pętlą "<c:forEach var=\"item\" items=\"${products}\">"
         * renderującą nazwę każdego produktu wewnątrz <li>. Wypisz cały fragment.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_WriteElWithParamImplicitObject {
        /*
         * 🧪 Zadanie 6:
         * Napisz fragment JSP wykorzystujący niejawny obiekt EL "param" do odczytania
         * parametru zapytania, np. "${param.search}", umieszczony jako wartość atrybutu
         * value w znaczniku <input>. Wypisz fragment.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_WriteScriptletVsElComparison {
        /*
         * 🧪 Zadanie 7:
         * Napisz DWA fragmenty JSP realizujące to samo zadanie (wyświetlenie listy
         * imion): jeden przy użyciu klasycznych scriptletów (<% %> / <%= %>), drugi przy
         * użyciu EL+JSTL (bez scriptletów). Wypisz oba fragmenty jeden pod drugim z
         * etykietami "WERSJA SCRIPTLET" / "WERSJA EL+JSTL".
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_WriteJstlChoiceBlock {
        /*
         * 🧪 Zadanie 8:
         * Napisz fragment JSP z "<c:choose><c:when test=\"${status == 'ACTIVE'}\">...
         * </c:when><c:otherwise>...</c:otherwise></c:choose>" wyświetlającym różny
         * komunikat w zależności od wartości "status". Wypisz cały fragment.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_WriteElArithmeticExpression {
        /*
         * 🧪 Zadanie 9:
         * Napisz fragment JSP pokazujący proste wyrażenia arytmetyczne/porównania w EL,
         * np. "${price * quantity}" oraz "${quantity > 10}". Wypisz fragment wraz z
         * krótkim komentarzem (println) wyjaśniającym, co każde wyrażenie oblicza.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_WriteDirectiveAndDeclarationSection {
        /*
         * 🧪 Zadanie 10:
         * Napisz fragment JSP z dyrektywą strony "<%@ page contentType=\"text/html;
         * charset=UTF-8\" %>" oraz deklaracją "<%! int counter = 0; %>". Wypisz fragment
         * i osobnym komentarzem/println wyjaśnij różnicę między dyrektywą, deklaracją a
         * scriptletem.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_FullPageMixingDirectiveElAndJstl {
        /*
         * 🧪 Zadanie 11:
         * Napisz JEDEN, spójny fragment JSP łączący: dyrektywę strony, dyrektywę
         * taglib, wyrażenie EL oraz pętlę "<c:forEach>" - jako niewielką stronę listy
         * produktów. Wypisz cały fragment jednym blokiem.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_RefactorScriptletPageToElJstl {
        /*
         * 🧪 Zadanie 12:
         * Najpierw napisz fragment JSP z logiką filtrowania/sortowania listy realizowaną
         * scriptletami (surowa Java w <% %>). Następnie napisz jego "zmigrowany"
         * odpowiednik zakładający, że filtrowanie/sortowanie zostało przeniesione do
         * serwletu, a strona używa WYŁĄCZNIE EL/JSTL. Wypisz oba do porównania.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_WriteElImplicitObjectsShowcase {
        /*
         * 🧪 Zadanie 13:
         * Napisz fragment JSP demonstrujący co najmniej 4 różne niejawne obiekty EL:
         * "${param.x}", "${sessionScope.x}", "${requestScope.x}",
         * "${applicationScope.x}" - każdy z jednolinijkowym komentarzem opisującym jego
         * zasięg. Wypisz cały fragment.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_WriteJstlForEachWithVarStatus {
        /*
         * 🧪 Zadanie 14:
         * Napisz fragment JSP z "<c:forEach var=\"item\" items=\"${items}\"
         * varStatus=\"status\">" wypisującym numer indeksu (status.index/status.count)
         * obok każdego elementu oraz naprzemienny styl wiersza na podstawie
         * "status.count % 2". Wypisz fragment.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_WriteFormPostingBackToServletWithEl {
        /*
         * 🧪 Zadanie 15:
         * Napisz fragment JSP z formularzem HTML <form>, którego action wskazuje na
         * realny serwlet (np. "/submit-order" znany z wcześniejszych lekcji rozdziału),
         * z polem input wstępnie wypełnionym wartością EL "${order.quantity}". Dodaj
         * komentarz przypominający, że formularz i tak wysyła POST do zwykłego
         * HttpServlet.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_WriteJstlNestedForEachOverMap {
        /*
         * 🧪 Zadanie 16:
         * Napisz fragment JSP z zagnieżdżonymi pętlami "<c:forEach>": zewnętrzna po
         * "${categories}", wewnętrzna po "${category.products}". Dodaj krótki komentarz
         * o tym, jak EL odwołuje się do wpisów/kluczy Map. Wypisz fragment.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_WriteConditionalClassAttributeWithEl {
        /*
         * 🧪 Zadanie 17:
         * Napisz fragment JSP używający EL wewnątrz atrybutu HTML do warunkowego
         * ustawienia klasy CSS, np. class="${item.active ? 'row-active' :
         * 'row-inactive'}". Wypisz fragment.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_WriteJstlSetAndOutTags {
        /*
         * 🧪 Zadanie 18:
         * Napisz fragment JSP z "<c:set var=\"total\" value=\"${price * qty}\" />"
         * poprzedzającym "<c:out value=\"${total}\" />", zestawiony z prostym
         * "${total}". Dodaj komentarz wyjaśniający, po co istnieje <c:out> (domyślne
         * escapowanie znaków specjalnych HTML).
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_WriteJspIncludeDirectiveVsJstlImport {
        /*
         * 🧪 Zadanie 19:
         * Napisz DWA fragmenty JSP: jeden ze statyczną dyrektywą
         * "<%@ include file=\"header.jsp\" %>", drugi z dynamicznym znacznikiem akcji
         * "<jsp:include page=\"header.jsp\" />". Wypisz oba wraz z krótkim komentarzem o
         * różnicy: dołączenie w czasie kompilacji vs w czasie zadania.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_WriteExceptionHandlingPageDirective {
        /*
         * 🧪 Zadanie 20:
         * Napisz fragment JSP strony błędu z dyrektywą "<%@ page isErrorPage=\"true\"
         * %>" oraz wyrażeniem EL "${pageContext.exception.message}" wyświetlającym
         * komunikat złapanego wyjątku. Dodaj komentarz wyjaśniający powiązanie
         * errorPage/isErrorPage.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_WriteFullMvcStyleJspControllerFlowDescription {
        /*
         * 🧪 Zadanie 21:
         * Napisz krótki opis (jako tekst/println) oraz towarzyszący fragment JSP
         * ilustrujący klasyczny wzorzec "Model 2": serwlet (jak te z Lekcji 13/17)
         * wykonuje logikę i robi forward() do strony JSP, która renderuje WYŁĄCZNIE
         * przez EL/JSTL, bez żadnej logiki biznesowej. Wypisz opis oraz fragment JSP.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_WriteBeforeAfterMigrationScriptletsToJstl {
        /*
         * 🧪 Zadanie 22:
         * Napisz "bałaganiarski" fragment JSP realizujący filtrowanie i sortowanie listy
         * przez scriptlety, a następnie jego "zmigrowaną" wersję zakładającą przeniesienie
         * tej logiki do serwletu (jak w Zadaniu 21). Wypisz oba oraz listę punktowaną
         * (println) konkretnych problemów, które migracja rozwiązuje.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_WriteJspCustomTagConceptExplanation {
        /*
         * 🧪 Zadanie 23:
         * Napisz fragment JSP szkicujący użycie WYMYŚLONEGO, własnego znacznika, np.
         * "<my:userCard user=\"${user}\" />", a następnie punktowane (println)
         * wyjaśnienie, co koncepcyjnie musiałaby implementować klasa obsługi takiego
         * znacznika (SimpleTagSupport). To ćwiczenie ma charakter WYŁĄCZNIE opisowy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_WriteJspVsThymeleafComparisonTable {
        /*
         * 🧪 Zadanie 24:
         * Wypisz sformatowaną tekstową tabelę (String.format lub text block) zestawiającą
         * fragment JSP oparty na scriptletach z koncepcyjnie równoważnym fragmentem
         * Thymeleaf (nawiązując do wzmianki z podsumowania lekcji), dla tego samego
         * prostego zadania (wyświetlenie listy). Wyłącznie porównanie opisowe, bez
         * uruchamiania Thymeleaf.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_WriteJspVsRestJsonComparison {
        /*
         * 🧪 Zadanie 25:
         * Wypisz tekstowe porównanie: (a) fragment JSP renderujący listę produktów jako
         * HTML przez JSTL, oraz (b) te same dane wyrażone jako treść odpowiedzi JSON
         * (nawiązując do lekcji Gson/Jackson z rozdziału _04_io), którą osobny frontend
         * renderowałby samodzielnie. Zilustruj podział "serwer renderuje HTML" vs
         * "serwer zwraca dane" z podsumowania lekcji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_WriteSecurityCaveatScriptletExample {
        /*
         * 🧪 Zadanie 26:
         * Napisz fragment JSP (celowo zła praktyka, wyłącznie ilustracyjnie) ze
         * scriptletem wklejającym nieoescapowane dane od użytkownika bezpośrednio do
         * HTML (wzorzec podatny na XSS), a następnie poprawioną wersję używającą
         * "<c:out>" (które domyślnie escapuje) dla TYCH SAMYCH danych. Wypisz oba wraz z
         * komentarzem o podatności i poprawce.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_WriteJspTagFileConceptOutline {
        /*
         * 🧪 Zadanie 27:
         * Napisz opisowy szkic (pseudo-kod/komentarze, bez realnych plików) tego, jak
         * powtarzający się fragment strony (np. nagłówek/stopka używane w przykładach z
         * tej lekcji) mógłby zostać wydzielony do pliku znacznika (.tag), by uniknąć
         * duplikacji między wieloma stronami JSP. Ćwiczenie wyłącznie opisowe.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_WriteFullOrderConfirmationJspPage {
        /*
         * 🧪 Zadanie 28:
         * Napisz JEDEN, realistyczny, kompletny fragment JSP strony potwierdzenia
         * zamówienia łączący: dyrektywy strony/taglib, kilka wyrażeń EL odczytujących
         * zagnieżdżone właściwości obiektu zamówienia, pętlę "<c:forEach>" po pozycjach
         * zamówienia z sumą bieżącą przez "<c:set>", oraz warunkowy komunikat przez
         * "<c:choose>" dla pustego i niepustego zamówienia. Wypisz cały fragment.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_WriteMigrationPlanBulletList {
        /*
         * 🧪 Zadanie 29:
         * Wypisz uporządkowaną, punktowaną listę tekstową (println) planu migracji
         * hipotetycznej, "starej" aplikacji opartej na scriptletach w stronę wzorca
         * "serwlet robi logikę + widok wyłącznie EL/JSTL" z Zadania 21 - z konkretnymi,
         * uporządkowanymi krokami (identyfikacja scriptletów, przeniesienie logiki do
         * serwletów, zastąpienie EL, wprowadzenie JSTL, testowanie każdej strony).
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_CapstoneAnnotatedJspKnowledgeSummary {
        /*
         * 🧪 Zadanie 30:
         * Kapstone: wypisz JEDEN, skonsolidowany raport (println/text block), który dla
         * KAŻDEGO głównego pojęcia JSP omówionego w tych ćwiczeniach (scriptlety, EL,
         * JSTL forEach/if/choose/set/out, dyrektywy page/taglib, include vs jsp:include,
         * własne znaczniki, wzorzec MVC, zagrożenie XSS) zawiera jednolinijkowe
         * podsumowanie ORAZ mały ilustracyjny fragment kodu - Twoją własną "ściągawkę"
         * syntetyzującą całą lekcję.
         */
        public static void main(String[] args) { }
    }
}
