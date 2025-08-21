package com.example.javaquest._01_fundamentals.Training2;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/greet")
public class JQ_RestApi_02 {

    /*
     * ĆWICZENIE 2:
     * Utwórz endpoint GET pod adresem /api/greet?name=Kuba
     * który zwróci napis "Hello Kuba".
     *
     * Przykład:
     *   GET http://localhost:8080/api/greet?name=Kuba
     *   → "Hello Kuba"
     *
     * Podpowiedź: użyj adnotacji @RequestParam
     */

    @GetMapping ("api/greet?name=Kuba")
    public String greet(@RequestParam String name) {

        return "Hello " + name;
    }
}
