package com.example.javaquest.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * React (react-router-dom, BrowserRouter) obsluguje trasy typu "/rozdzial/_01_fundamentals"
 * WYLACZNIE po stronie klienta - bez tego kontrolera twarde przeladowanie strony (F5) albo
 * bezposrednie wejscie pod taki adres trafialoby w domyslny handler zasobow statycznych
 * Springa, ktory nie zna sciezki "/rozdzial/..." i zwrocilby 404, zamiast oddac
 * "index.html" (a wtedy dopiero React Router przejalby routing po swojej stronie).
 *
 * <p>Mapowania SA jawnie wypisane (nie ogolny "/**") - zeby nigdy przypadkiem nie
 * przeslonic "/api/**" (REST API platformy) ani "/actuator/**". Dopisuj tu KAZDA nowa
 * trase najwyzszego poziomu dodawana w "frontend/src/App.jsx".
 */
@Controller
class SpaFallbackController {

    @GetMapping("/rozdzial/**")
    String forwardToIndex() {
        return "forward:/index.html";
    }
}
