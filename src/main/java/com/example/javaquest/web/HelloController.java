package com.example.javaquest.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Minimalny endpoint API dla frontendu React zbudowanego w katalogu "frontend/"
 * (patrz frontend/vite.config.js - "npm run build" laduje wynik do
 * src/main/resources/static, skad JavaQuestApplication serwuje go domyslnie
 * pod "/"). Ten kontroler istnieje wylacznie po to, zeby zademonstrowac, ze
 * frontend i backend faktycznie ze soba rozmawiaja - nie jest czescia zadnego
 * rozdzialu kursu.
 */
@RestController
public class HelloController {

    @GetMapping("/api/hello")
    public String hello() {
        return "Czesc z Spring Boota! (" + java.time.LocalDateTime.now() + ")";
    }
}
