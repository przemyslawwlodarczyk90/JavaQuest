package com.example.javaquest._01_fundamentals.Training_3;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Ćwiczenie 11 (Twoje zadanie):
 *
 * Cel: stworzyć prosty system produktów z użyciem POST i GET.
 *
 * Zadanie:
 * 1. Utwórz klasę/record Product z polami:
 *    - int id
 *    - String name
 *    - double price
 *
 * 2. W tej klasie stwórz prywatną listę produktów (np. ArrayList).
 *
 * 3. Dodaj endpointy:
 *    - POST /products → przyjmuje JSON z produktem i dodaje go do listy.
 *    - GET /products → zwraca całą listę produktów.
 *
 * 4. Przetestuj w Postmanie:
 *    - POST http://localhost:8080/products
 *      Body (JSON):
 *      {
 *        "id": 1,
 *        "name": "Laptop",
 *        "price": 3999.99
 *      }
 *
 *    - GET http://localhost:8080/products
 *      Oczekiwany wynik:
 *      [
 *        {"id":1,"name":"Laptop","price":3999.99}
 *      ]
 *
 * 5. (opcjonalnie) Dodaj walidację: name nie może być puste, price > 0.
 */
@RestController
public class Exercise11Controller {




    record Product(int id, String name, double price) {}

    // modyfikowalna lista
    private final List<Product> liste = new ArrayList<>(List.of(
            new Product(1, "Towel", 55),
            new Product(2, "Knife", 12),
            new Product(3, "Fork", 13),
            new Product(4, "Plate", 33),
            new Product(5, "Pan", 44)
    ));

    // POST /products -> dodaje nowy produkt
    @PostMapping("/products")
    public ResponseEntity<Product> addProduct(@RequestBody Product product) {
        liste.add(product);
        return ResponseEntity.status(201).body(product);
    }

    // GET /products -> zwraca listę produktów
    @GetMapping("/products")
    public ResponseEntity<List<Product>> getProducts() {
        return ResponseEntity.ok(liste);
    }
}


