package com.example.javaquest._01_fundamentals.Training2;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class JQ_RestApi_04 {

    /*
     * ĆWICZENIE 4:
     * Utwórz endpoint POST pod adresem /api/user
     * który przyjmie obiekt JSON z polami "id" i "name",
     * a następnie zwróci tekst: "User {id}: {name}".
     *
     * Przykład:
     *   POST http://localhost:8080/api/user
     *   Body (JSON):
     *     {
     *       "id": 10,
     *       "name": "Kuba"
     *     }
     *   → "User 10: Kuba"
     *
     * Podpowiedź: użyj adnotacji @PostMapping i @RequestBody.
     */

    static class User {
        public int id;
        public String name;
    }

    @PostMapping
    public String createUser(@RequestBody User user) {
        // TODO: zaimplementuj tutaj
        return "User: " + user.id + user.name;
    }
}
