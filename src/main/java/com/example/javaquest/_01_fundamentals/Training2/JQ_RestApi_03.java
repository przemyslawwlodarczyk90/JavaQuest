package com.example.javaquest._01_fundamentals.Training2;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class JQ_RestApi_03 {

    /*
     * ĆWICZENIE 3:
     * Utwórz endpoint GET pod adresem /api/user/{id}
     * który zwróci napis "User ID: {id}".
     *
     * Przykład:
     *   GET http://localhost:8080/api/user/5
     *   → "User ID: 5"
     *
     * Podpowiedź: użyj adnotacji @PathVariable
     */

    @GetMapping("/{id}")
    public String getUserById(@PathVariable int id) {
        // TODO: zaimplementuj tutaj
        return "User ID: " + id;
    }
}
