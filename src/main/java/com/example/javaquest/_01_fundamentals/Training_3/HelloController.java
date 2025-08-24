package com.example.javaquest._01_fundamentals.Training_3;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Ćwiczenie 1:
 * Stwórz prosty endpoint GET, który zwróci stały napis.
 *
 * Zadanie:
 * - Uruchom aplikację i sprawdź w przeglądarce lub Postmanie endpoint: http://localhost:8080/hello
 * - Zmień zwracaną wartość na inny napis (np. swoje imię).
 * - Dodaj drugi endpoint GET pod adresem /bye, który zwróci "Goodbye!".
 */
@RestController
public class HelloController {

    @GetMapping("/hello")
    public String sayHello() {
        return "Hello, world!";
    }

    @GetMapping("/goodbye")
    public String goodbye(){
        return "Goodbye";
    }
}
