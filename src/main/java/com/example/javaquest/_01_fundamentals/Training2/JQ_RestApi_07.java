package com.example.javaquest._01_fundamentals.Training2;

import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/users")
public class JQ_RestApi_07 {

    /*
     * ĆWICZENIE 7:
     * Utwórz endpoint DELETE pod adresem /api/users/{id}
     * który usunie użytkownika o podanym ID z listy
     * i zwróci zaktualizowaną listę w formacie JSON.
     *
     * Przykład:
     *   DELETE http://localhost:8080/api/users/2
     *
     *   → [
     *        { "id": 1, "name": "Kuba" },
     *        { "id": 3, "name": "Przemek" }
     *      ]
     *
     * Podpowiedź: użyj adnotacji @DeleteMapping i usuń element z listy users
     * np. przez removeIf(user -> user.id == id).
     */

    static class User {
        public int id;
        public String name;

        User() {}
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

    @DeleteMapping("/{id}")
    public List<User> deleteUser(@PathVariable int id) {
        // TODO: zaimplementuj tutaj
        users.removeIf(user -> user.id ==id);
        return users;
    }
}
