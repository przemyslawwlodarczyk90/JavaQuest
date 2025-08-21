package com.example.javaquest._01_fundamentals.Training2;

import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/hello")
public class HelloController {

    /*
     * ĆWICZENIE 1:
     * Utwórz prosty endpoint GET pod adresem /api/hello
     * który zwróci napis "Hello World".
     *
     * Podpowiedź: użyj adnotacji @GetMapping
     */

    @GetMapping
    public String sayHello() {
        // TODO: zaimplementuj tutaj
        return "Hello world";
    }
}
