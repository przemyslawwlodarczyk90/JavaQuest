package com.example.javaquest._01_fundamentals.Training_3;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Ćwiczenie 3:
 * Endpoint GET z parametrem query.
 *
 * Zadanie:
 * - Otwórz w przeglądarce/Postmanie: http://localhost:8080/hello?name=Jan
 *   powinno zwrócić "Hello, Jan!"
 * - Sprawdź, co się stanie, gdy nie podasz parametru (np. http://localhost:8080/hello).
 * - Dodaj drugi endpoint GET pod adresem /sum?x=5&y=10,
 *   który zwróci wynik dodawania liczb.
 */
@RestController
public class Exercise3Controller {

    @GetMapping("/hello")
    public String sayHello(@RequestParam String name) {
        return "Hello, " + name + "!";
    }


    @GetMapping("/sum")
    public String sum(@RequestParam Integer x, @RequestParam Integer y) {
        return "Sum: " + (x + y);
    }
}
