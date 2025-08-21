package com.example.javaquest._01_fundamentals.Training2;

import org.springframework.web.bind.annotation.*;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class JQ_RestApi_10 {

    /*
     * ĆWICZENIE 10:
     * Utwórz endpoint GET pod adresem /api/users/filter?prefix=P
     * który zwróci listę użytkowników, których imię zaczyna się
     * na podany prefiks.
     *
     * Przykład:
     *   GET http://localhost:8080/api/users/filter?prefix=P
     *
     *   → [
     *        { "id": 3, "name": "Przemek" }
     *      ]
     *
     * Podpowiedź: użyj stream(), filter() i collect(Collectors.toList()).
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
            new User(3, "Przemek"),
            new User(4, "Patryk")
    ));

    @GetMapping("/filter")
    public List<User> getUsersByPrefix(@RequestParam String prefix) {
        return users.stream()
                .filter(a->a.name.startsWith(prefix))
                .collect(Collectors.toList());
    }
}
