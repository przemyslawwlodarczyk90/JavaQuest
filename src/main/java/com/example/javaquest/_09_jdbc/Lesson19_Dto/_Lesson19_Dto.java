package com.example.javaquest._09_jdbc.Lesson19_Dto;

import java.util.Objects;

public class _Lesson19_Dto {

    public static void main(String[] args) {

        /*
         * ============================================================
         * 📦 CZYM JEST DTO (Data Transfer Object)?
         * ============================================================
         * DTO to obiekt, którego JEDYNYM zadaniem jest PRZENOSZENIE
         * DANYCH pomiędzy warstwami aplikacji (albo przez sieć - np.
         * jako JSON w odpowiedzi API). DTO celowo NIE MA logiki
         * biznesowej - to "kontener na dane", skrojony pod KONKRETNY
         * przypadek użycia (np. "dane potrzebne, żeby założyć konto"
         * albo "dane, które wolno pokazać klientowi w odpowiedzi API").
         *
         * 🔍 DTO vs MODEL DOMENOWY (Lesson18) - różne CELE
         * - MODEL DOMENOWY (np. User) - reprezentuje pojęcie biznesowe,
         *   ma metody biznesowe, żyje "wewnątrz" aplikacji, może zawierać
         *   dane WRAŻLIWE potrzebne do działania systemu (np. hash hasła).
         * - DTO (np. UserResponse) - reprezentuje dane w KONKRETNYM
         *   kontekście transportu (np. odpowiedź API), zawiera TYLKO to,
         *   co odbiorca powinien zobaczyć - nic więcej, nic mniej.
         *
         * Te dwa obiekty czasem wyglądają podobnie, ale ich cel jest inny
         * - i to jest w porządku, że mają różne pola.
         *
         * 🔹 WEJŚCIE vs WYJŚCIE
         * - DTO WEJŚCIOWE (np. CreateUserRequest) - dane, które PRZYCHODZĄ
         *   do aplikacji (np. z formularza rejestracji) - zawiera hasło
         *   W POSTACI JAWNEJ (jeszcze niezahaszowane), bo tak podał je
         *   użytkownik.
         * - DTO WYJŚCIOWE (np. UserResponse) - dane, które WYCHODZĄ z
         *   aplikacji (np. w odpowiedzi API) - NIGDY nie powinno zawierać
         *   hasła ani jego hasha, tokenów sesji itp.
         */

        System.out.println("=== KROK 1: DTO WEJSCIOWE - dane z formularza rejestracji ===");
        CreateUserRequest request = new CreateUserRequest("ania@example.com", "Ania", "MojeTajneHaslo123");
        System.out.println("Otrzymano zadanie rejestracji: " + request.email() + " / " + request.firstName());
        // Zwroc uwage: NIE wypisujemy request.plainPassword() do logow - to tez
        // jest wazna zasada bezpieczenstwa (haslo w logach = wyciek danych).

        /*
         * ============================================================
         * 🔹 KROK 2: "ZAPIS" - budowa ENCJI DOMENOWEJ z DTO wejściowego
         * ============================================================
         * W realnej aplikacji tutaj hasło zostałoby zahaszowane (np.
         * BCrypt) i zapisane w bazie przez JDBC. Tu symulujemy hashowanie
         * prostą metodą - ważny jest SAM FAKT, że encja domenowa User
         * przechowuje passwordHash, a NIE hasło w postaci jawnej.
         */

        User savedUser = createUserFromRequest(request);
        System.out.println("\n=== KROK 2: ENCJA DOMENOWA po zapisie ===");
        System.out.println("Encja User (dane WEWNETRZNE aplikacji):");
        System.out.println(" id=" + savedUser.id());
        System.out.println(" email=" + savedUser.email());
        System.out.println(" firstName=" + savedUser.firstName());
        System.out.println(" passwordHash=" + savedUser.passwordHash());
        // ^ encja MA passwordHash - jest potrzebny do logowania (weryfikacji hasla)

        /*
         * ============================================================
         * 🔹 KROK 3: DTO WYJŚCIOWE - ochrona przed ujawnieniem pól
         * ============================================================
         * Gdybyśmy zwrócili z API bezpośrednio obiekt User, klient
         * dostałby też passwordHash w odpowiedzi JSON - poważny błąd
         * bezpieczeństwa (nawet hash hasła NIGDY nie powinien opuszczać
         * serwera). Dlatego mapujemy encję na DTO WYJŚCIOWE (UserResponse),
         * które PO PROSTU NIE MA pola na hasło - nie trzeba pamiętać, żeby
         * je "wyczyścić", bo klasa go w ogóle nie definiuje.
         */

        UserResponse response = toResponse(savedUser);
        System.out.println("\n=== KROK 3: DTO WYJSCIOWE (to, co faktycznie wraca z API) ===");
        System.out.println(response);
        System.out.println("Czy UserResponse ma jakiekolwiek pole zwiazane z haslem? NIE - "
                + "klasa UserResponse w ogole nie definiuje takiego pola.");

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - DTO (Data Transfer Object) = obiekt do PRZENOSZENIA danych
         *   między warstwami/przez sieć, bez logiki biznesowej
         * - DTO WEJŚCIOWE (np. CreateUserRequest) - dane przychodzące do
         *   aplikacji (mogą zawierać dane tymczasowe, np. jawne hasło)
         * - DTO WYJŚCIOWE (np. UserResponse) - dane wychodzące z aplikacji,
         *   celowo OKROJONE do tego, co wolno zobaczyć odbiorcy
         * - Model domenowy (User) i DTO wyjściowe (UserResponse) mają
         *   RÓŻNE cele, dlatego mogą (i często powinny) mieć różne pola -
         *   np. User ma passwordHash, UserResponse go NIE MA
         * - Ochrona przed wyciekiem danych: nie zwracaj z API surowej
         *   encji domenowej - zawsze mapuj ją na dedykowane DTO wyjściowe
         */
    }

    /** DTO WEJŚCIOWE - dane z formularza rejestracji (jeszcze jawne hasło). */
    private record CreateUserRequest(String email, String firstName, String plainPassword) {
    }

    /** DTO WYJŚCIOWE - dane bezpieczne do pokazania klientowi (BEZ hasła/hasha). */
    private record UserResponse(long id, String email, String firstName) {
    }

    /** Encja domenowa - dane WEWNĘTRZNE aplikacji, zawiera passwordHash. */
    private record User(long id, String email, String firstName, String passwordHash) {
    }

    /** Symulacja zapisu: buduje encję domenową z DTO wejściowego, hashując hasło. */
    private static User createUserFromRequest(CreateUserRequest request) {
        Objects.requireNonNull(request.plainPassword(), "Haslo jest wymagane");
        String passwordHash = fakeHash(request.plainPassword());
        long generatedId = 1L; // w realnym kodzie: id nadane przez baze danych (np. AUTO_INCREMENT)
        return new User(generatedId, request.email(), request.firstName(), passwordHash);
    }

    /** Mapuje encję domenową na DTO wyjściowe - CELOWO pomija passwordHash. */
    private static UserResponse toResponse(User user) {
        return new UserResponse(user.id(), user.email(), user.firstName());
    }

    /** Uproszczona "funkcja haszująca" wyłącznie na potrzeby demonstracji. */
    private static String fakeHash(String plainPassword) {
        return "hash(" + plainPassword.length() + "znakow)#" + Math.abs(plainPassword.hashCode());
    }
}
