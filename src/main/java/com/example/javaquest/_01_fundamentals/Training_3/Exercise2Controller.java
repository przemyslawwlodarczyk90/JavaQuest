package com.example.javaquest._01_fundamentals.Training_3;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * Ćwiczenie 2:
 * Endpoint GET z parametrem w ścieżce.
 *
 * Zadanie:
 * - Otwórz w przeglądarce lub Postmanie: http://localhost:8080/hello/Jan
 *   powinno zwrócić "Hello, Jan!"
 * - Przetestuj z różnymi imionami.
 * - Dodaj drugi endpoint GET pod adresem /bye/{name}, który zwróci "Goodbye, {name}!".
 */
@RestController
public class Exercise2Controller {

    @GetMapping("/hello/{name}")
    public String sayHello(@PathVariable String name) {
        return "Hello, " + name + "!";
    }

    @GetMapping("/bye/{name}")
    public String sayGoodbve(@PathVariable String name) {
        return "Goodbye, " + name + "!";
    }


}
