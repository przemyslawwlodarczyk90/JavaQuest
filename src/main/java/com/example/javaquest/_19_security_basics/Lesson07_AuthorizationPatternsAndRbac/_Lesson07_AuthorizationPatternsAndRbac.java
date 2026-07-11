package com.example.javaquest._19_security_basics.Lesson07_AuthorizationPatternsAndRbac;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class _Lesson07_AuthorizationPatternsAndRbac {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 7: WZORCE AUTORYZACJI I RBAC ===");

        /*
         * ============================================================
         * 📦 PRZYPOMNIENIE Z LEKCJI 1
         * ============================================================
         * `_19_security_basics/Lesson01` pokazal PROSTA autoryzacje -
         * pole "role" na uzytkowniku ("user"/"admin") i sprawdzenie
         * `if (role.equals("admin"))`. To dziala dla 2 rol, ale co gdy
         * mamy 10 rol i 50 roznych operacji? Prosty `if` staje sie
         * nieczytelnym baleganem. Ta lekcja pokazuje FORMALNE WZORCE
         * organizacji uprawnien: RBAC i ABAC.
         */
        System.out.println("Prosty 'if (role == admin)' nie skaluje sie na wiele rol i operacji - potrzeba formalnego wzorca.");

        demonstrateRbacModel();
        demonstratePrincipleOfLeastPrivilege();
        demonstrateAbacModel();

        /*
         * ============================================================
         * 🔹 RBAC (ROLE-BASED ACCESS CONTROL)
         * ============================================================
         * Model: UZYTKOWNIK -> ROLE -> UPRAWNIENIA (permissions).
         * Uzytkownik NIE dostaje uprawnien BEZPOSREDNIO - dostaje ROLE
         * (np. "EDITOR"), a ROLA ma przypisany ZESTAW uprawnien (np.
         * "articles:read", "articles:write"). Zaleta: latwe zarzadzanie
         * (zmien uprawnienia roli = zmieniasz je dla WSZYSTKICH
         * uzytkownikow tej roli naraz), latwe do zrozumienia,
         * standardowe podejscie w wiekszosci systemow biznesowych.
         *
         * ============================================================
         * 🔹 ABAC (ATTRIBUTE-BASED ACCESS CONTROL)
         * ============================================================
         * Model: decyzja zalezy od ATRYBUTOW - uzytkownika (dzial,
         * lokalizacja), zasobu (wlasciciel, status), KONTEKSTU (pora
         * dnia, adres IP). Przyklad: "pracownik moze edytowac TYLKO
         * WLASNE zamowienia" albo "dostep TYLKO w godzinach pracy" - tego
         * NIE da sie wyrazic prosta rola, bo zalezy od RELACJI miedzy
         * uzytkownikiem a KONKRETNYM zasobem. ABAC jest bardziej
         * ELASTYCZNY, ale i bardziej ZLOZONY niz RBAC.
         *
         * ============================================================
         * 🔹 ZASADA NAJMNIEJSZYCH UPRAWNIEN (PRINCIPLE OF LEAST PRIVILEGE)
         * ============================================================
         * Kazdy uzytkownik/proces powinien miec DOKLADNIE tyle
         * uprawnien, ile potrzebuje do swojej pracy - ANI JEDNEGO
         * WIECEJ. Domyslnie NOWA rola/uzytkownik nie ma ZADNYCH
         * uprawnien - uprawnienia sa DODAWANE jawnie, nigdy odwrotnie
         * (domyslnie "wszystko", potem "odbierane"). Redukuje "blast
         * radius" wlamania/bledu - jesli konto stazysty jest
         * skompromitowane, atakujacy dostaje TYLKO to, co stazysta MIAL,
         * nie cala baze danych.
         */
        System.out.println("\nRBAC = uzytkownik->rola->uprawnienia (proste, standardowe). ABAC = decyzja z atrybutow/kontekstu (elastyczne, zlozone).");

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - RBAC: uzytkownik ma role, rola ma uprawnienia - proste w
         *   zarzadzaniu, standard w wiekszosci systemow.
         * - ABAC: decyzja z atrybutow uzytkownika/zasobu/kontekstu -
         *   elastyczne dla regul typu "tylko wlasne dane".
         * - Zasada najmniejszych uprawnien - domyslnie BRAK dostepu,
         *   dodawaj TYLKO to, co niezbedne.
         * - RBAC i ABAC NIE wykluczaja sie - wiele realnych systemow
         *   laczy oba (rola daje "bazowy" zestaw uprawnien, ABAC dodaje
         *   dodatkowe warunki np. "tylko wlasne zasoby").
         * - Sprawdzanie uprawnien powinno byc scentralizowane (1 metoda/
         *   miejsce), NIE rozproszone po calym kodzie - latwiej to
         *   zweryfikowac i przetestowac.
         */
        System.out.println("\n=== KONIEC LEKCJI 7 ===");
    }

    private static void demonstrateRbacModel() {
        System.out.println("\n=== MODEL RBAC: UZYTKOWNIK -> ROLA -> UPRAWNIENIA ===");

        Map<String, Set<String>> rolePermissions = Map.of(
                "VIEWER", Set.of("articles:read"),
                "EDITOR", Set.of("articles:read", "articles:write"),
                "ADMIN", Set.of("articles:read", "articles:write", "articles:delete", "users:manage")
        );

        Map<String, String> userRoles = Map.of(
                "ala", "VIEWER",
                "jan", "EDITOR",
                "ola", "ADMIN"
        );

        for (Map.Entry<String, String> entry : userRoles.entrySet()) {
            String username = entry.getKey();
            String role = entry.getValue();
            Set<String> permissions = rolePermissions.get(role);
            System.out.println(username + " ma role " + role + " -> uprawnienia: " + permissions);
        }

        boolean canJanDeleteArticles = hasPermission(rolePermissions, userRoles, "jan", "articles:delete");
        System.out.println("Czy 'jan' (EDITOR) moze usuwac artykuly? " + canJanDeleteArticles);
    }

    private static boolean hasPermission(Map<String, Set<String>> rolePermissions, Map<String, String> userRoles,
                                          String username, String requiredPermission) {
        String role = userRoles.get(username);
        if (role == null) {
            return false;
        }
        Set<String> permissions = rolePermissions.getOrDefault(role, Set.of());
        return permissions.contains(requiredPermission);
    }

    private static void demonstratePrincipleOfLeastPrivilege() {
        System.out.println("\n=== ZASADA NAJMNIEJSZYCH UPRAWNIEN W PRAKTYCE ===");

        List<String> newInternRole = List.of(); // NOWY stazysta - BRAK uprawnien domyslnie
        System.out.println("Nowy stazysta domyslnie dostaje uprawnienia: " + newInternRole + " (PUSTE - dodajemy TYLKO to, co potrzebne).");

        List<String> internTaskSpecificPermissions = List.of("articles:read"); // dodane JAWNIE, bo stazysta pisze raport
        System.out.println("Po analizie zadania, jawnie dodano: " + internTaskSpecificPermissions + " - NIC WIECEJ.");

        System.out.println("ZLA praktyka (przeciwienstwo): nowy uzytkownik dostaje rola ADMIN 'na wszelki wypadek', "
                + "bo 'i tak pewnie kiedys bedzie potrzebowal' - to NARUSZA zasade najmniejszych uprawnien.");
    }

    private record Order(String id, String ownerUsername, double amount) {
    }

    private static void demonstrateAbacModel() {
        System.out.println("\n=== MODEL ABAC: DECYZJA Z ATRYBUTOW UZYTKOWNIKA/ZASOBU/KONTEKSTU ===");

        List<Order> orders = List.of(
                new Order("ORD-1", "jan", 150.0),
                new Order("ORD-2", "ala", 900.0)
        );

        String requestingUser = "jan";
        String requestedOrderId = "ORD-2"; // 'jan' probuje zobaczyc zamowienie ALI

        Order requestedOrder = orders.stream().filter(o -> o.id().equals(requestedOrderId)).findFirst().orElseThrow();
        boolean canView = canViewOwnOrderOnly(requestingUser, requestedOrder);

        System.out.println("Uzytkownik '" + requestingUser + "' probuje zobaczyc zamowienie " + requestedOrderId
                + " nalezace do '" + requestedOrder.ownerUsername() + "' -> dostep: " + canView);
        System.out.println("-> to NIE da sie wyrazic prosta rola ('EDITOR' vs 'VIEWER') - zalezy od RELACJI");
        System.out.println("   miedzy KONKRETNYM uzytkownikiem a KONKRETNYM zasobem - to WLASNIE jest ABAC.");
    }

    private static boolean canViewOwnOrderOnly(String requestingUser, Order order) {
        return requestingUser.equals(order.ownerUsername());
    }
}
