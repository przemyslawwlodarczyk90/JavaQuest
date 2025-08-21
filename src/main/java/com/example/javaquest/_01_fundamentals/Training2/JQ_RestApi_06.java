package com.example.javaquest._01_fundamentals.Training2;

import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/users")
public class JQ_RestApi_06 {

    /*
     * ĆWICZENIE 6:
     * Utwórz endpoint POST pod adresem /api/users
     * który przyjmie obiekt JSON z polami "id" i "name"
     * i doda go do listy użytkowników.
     *
     * Następnie zwróć aktualną listę użytkowników w formacie JSON.
     *
     * Przykład:
     *   POST http://localhost:8080/api/users
     *   Body (JSON):
     *     { "id": 4, "name": "Wiktoria" }
     *
     *   → [
     *        { "id": 1, "name": "Kuba" },
     *        { "id": 2, "name": "Dorota" },
     *        { "id": 3, "name": "Przemek" },
     *        { "id": 4, "name": "Wiktoria" }
     *      ]
     *
     * Podpowiedź: użyj @PostMapping i @RequestBody.
     */

    static class User {
        public int id;
        public String name;

        User() {} // wymagany pusty konstruktor dla JSON
        User(int id, String name) {
            this.id = id;
            this.name = name;
        }
    }

    private List<User> users = new ArrayList<>(Arrays.asList(
            new User(1, "Kuba"),
            new User(2, "Dorota"),
            new User(3, "Przemek")
    ));

    @PostMapping
    public List<User> addUser(@RequestBody User user) {
        users.add(user);
        return users;
    }
}
