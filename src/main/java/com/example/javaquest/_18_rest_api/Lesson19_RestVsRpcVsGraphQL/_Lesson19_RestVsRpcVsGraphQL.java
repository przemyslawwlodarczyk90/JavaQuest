package com.example.javaquest._18_rest_api.Lesson19_RestVsRpcVsGraphQL;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class _Lesson19_RestVsRpcVsGraphQL {

    record User(int id, String name, String email, String address, String phone, List<String> orderIds) {
    }

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 19: REST vs RPC vs GraphQL ===");

        /*
         * ============================================================
         * 📦 PO 18 LEKCJACH O REST, WARTO ZOBACZYC ALTERNATYWY
         * ============================================================
         * REST to NIE jedyny sposob budowania API - to 1 z kilku
         * dominujacych stylow. Zrozumienie ALTERNATYW pomaga swiadomie
         * ocenic, KIEDY REST jest wlasciwym wyborem, a kiedy NIE.
         */

        demonstrateRpcStyle();
        demonstrateRestStyle();
        demonstrateGraphQLStyle();
        demonstrateOverFetchingProblem();
        demonstrateUnderFetchingProblem();
        printComparisonTable();
        demonstrateWhenToChooseWhat();

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - RPC (Remote Procedure Call, np. gRPC) - klient wywoluje
         *   ZDALNA FUNKCJE (nie zasob) - "createUser(...)",
         *   "getUserOrders(...)" - scisly, silnie typowany kontrakt
         *   (np. Protocol Buffers), czesto binarny i BARDZO wydajny -
         *   dominujacy wybor do komunikacji MIEDZY mikroserwisami
         *   (wewnetrznie, nie dla publicznych API).
         * - REST - zasoby+czasowniki HTTP (caly ten rozdzial) -
         *   wykorzystuje CACHE'OWANIE HTTP "za darmo" (Lesson11),
         *   prostszy do zrozumienia, ale podatny na OVER-FETCHING
         *   (za duzo danych) i UNDER-FETCHING (za malo, trzeba
         *   dodatkowych requestow).
         * - GraphQL - 1 endpoint, klient PRECYZYJNIE okresla, JAKICH
         *   pol potrzebuje w zapytaniu - rozwiazuje over/under-fetching,
         *   ale TRACI natywne cache'owanie HTTP (prawie zawsze POST) i
         *   wymaga bardziej zlozonej implementacji serwera (resolvers).
         * - Wybor NIE jest "REST jest najlepszy" ani "GraphQL jest
         *   najlepszy" - to zalezy od KONTEKSTU: publiczne API z
         *   naciskiem na prostote/cache -> REST. Zlozony klient z wieloma
         *   roznymi "widokami" tych samych danych -> GraphQL. Komunikacja
         *   wewnetrzna miedzy serwisami z naciskiem na wydajnosc -> RPC/gRPC.
         * - Kolejna, ostatnia lekcja tego rozdzialu (Lesson20): best
         *   practices REST + kapszton laczacy caly rozdzial.
         */
        System.out.println("\n=== KONIEC LEKCJI 19 ===");
    }

    private static void demonstrateRpcStyle() {
        System.out.println("\n=== STYL RPC: WYWOLUJESZ FUNKCJE, NIE ZASOBY ===");
        System.out.println("gRPC (Protocol Buffers, HTTP/2):  rpc GetUser(GetUserRequest) returns (UserResponse);");
        System.out.println("JSON-RPC (starsza forma):         POST / cialo={\"method\":\"getUser\",\"params\":{\"id\":42}}");
        System.out.println("-> URI/endpoint NIE identyfikuje zasobu - identyfikuje METODE do wywolania (por. Lesson02: Poziom 0 Richardsona).");
    }

    private static void demonstrateRestStyle() {
        System.out.println("\n=== STYL REST (PRZYPOMNIENIE Z CALEGO ROZDZIALU) ===");
        System.out.println("GET /users/42          -> zasob 'user' o ID 42");
        System.out.println("GET /users/42/orders    -> zasob 'orders' NALEZACY DO usera 42");
        System.out.println("-> WIELE endpointow, kazdy zwraca STALY, z gory zdefiniowany ksztalt danych (Lesson08).");
    }

    private static void demonstrateGraphQLStyle() {
        System.out.println("\n=== STYL GraphQL: 1 ENDPOINT, KLIENT WYBIERA POLA ===");
        String graphqlQuery = """
                query {
                  user(id: 42) {
                    name
                    orders {
                      id
                      total
                    }
                  }
                }""";
        System.out.println("POST /graphql z cialem-zapytaniem (jezyk zapytan GraphQL):");
        System.out.println(graphqlQuery.indent(2));
        System.out.println("-> KLIENT decyduje, ktore pola dostanie w odpowiedzi (tu: TYLKO name i orders.id/total) - NIE caly obiekt User.");
    }

    private static void demonstrateOverFetchingProblem() {
        System.out.println("\n=== PROBLEM REST: OVER-FETCHING (ZA DUZO DANYCH) ===");

        User fullUser = new User(42, "Ala Kowalska", "ala@example.com", "ul. Prosta 1", "123456789", List.of("1", "2"));
        System.out.println("Klient potrzebuje TYLKO 'name' (np. do naglowka strony), ale GET /users/42 w REST zwraca CALY obiekt:");
        System.out.println("  " + fullUser);
        System.out.println("-> serwer NIE WIE, ktorych pol klient faktycznie potrzebuje - zwraca WSZYSTKO, marnujac transfer.");
    }

    private static void demonstrateUnderFetchingProblem() {
        System.out.println("\n=== PROBLEM REST: UNDER-FETCHING (ZA MALO, TRZEBA WIECEJ REQUESTOW) ===");
        System.out.println("Klient potrzebuje: dane usera + jego zamowienia + adresy dostawy KAZDEGO zamowienia.");
        System.out.println("W REST to typowo WIELE osobnych requestow:");
        System.out.println("  1. GET /users/42");
        System.out.println("  2. GET /users/42/orders");
        System.out.println("  3. GET /orders/1/shipping-address");
        System.out.println("  4. GET /orders/2/shipping-address");
        System.out.println("-> 4 osobne 'podroze' siec-serwer-siec, gdzie GraphQL zalatwilby to 1 zapytaniem z zagniezdzonymi polami.");
    }

    private static void printComparisonTable() {
        System.out.println("\n=== TABELA POROWNAWCZA (bez akcentow, dla czytelnosci konsoli) ===");
        Map<String, String[]> rows = new LinkedHashMap<>();
        rows.put("Endpointy", new String[]{"wiele (1 per zasob)", "wiele (1 per funkcja)", "1 (wszystko przez query)"});
        rows.put("Over/under-fetching", new String[]{"CZESTY problem", "rzadszy (kontrakt precyzyjny)", "ROZWIAZANY (klient wybiera pola)"});
        rows.put("Cache HTTP", new String[]{"NATYWNE (GET+ETag, Lesson11)", "TRUDNE (zwykle POST)", "TRUDNE (prawie zawsze POST)"});
        rows.put("Typowy protokol", new String[]{"HTTP/1.1 lub 2 + JSON", "HTTP/2 + Protobuf (gRPC)", "HTTP + JSON"});
        rows.put("Typowy uzytek", new String[]{"publiczne API, CRUD", "wewnetrzna komunikacja miedzyserwisowa", "zlozone klienty (mobile/SPA) z wieloma widokami"});

        System.out.printf("%-22s | %-28s | %-32s | %-30s%n", "Kryterium", "REST", "RPC/gRPC", "GraphQL");
        rows.forEach((key, values) -> System.out.printf("%-22s | %-28s | %-32s | %-30s%n", key, values[0], values[1], values[2]));
    }

    private static void demonstrateWhenToChooseWhat() {
        System.out.println("\n=== KIEDY WYBRAC KTORY STYL? ===");
        System.out.println("REST    - publiczne API, standardowy CRUD, potrzebujesz cache'owania HTTP i prostoty dla integratorow.");
        System.out.println("RPC/gRPC- komunikacja WEWNETRZNA miedzy mikroserwisami, gdzie liczy sie WYDAJNOSC i scisly kontrakt typow.");
        System.out.println("GraphQL - zlozony klient (np. aplikacja mobilna) potrzebujacy ROZNYCH 'widokow' tych samych danych,");
        System.out.println("          gotowy zrezygnowac z prostego cache'owania HTTP w zamian za elastycznosc zapytan.");
    }
}
