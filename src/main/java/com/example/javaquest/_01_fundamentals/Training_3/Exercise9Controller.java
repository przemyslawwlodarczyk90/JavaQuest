//package com.example.javaquest._01_fundamentals.Training_3;
//
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
///**
// * Ćwiczenie 9:
// * Endpoint GET /usersByAge?age=30, który zwraca listę użytkowników w danym wieku.
// *
// * Zadanie:
// * - Wywołaj: http://localhost:8080/usersByAge?age=30
// *   powinno zwrócić listę w JSON:
// *   [
// *     {"id":2,"name":"Bob","email":"bob@example.com","age":30}
// *   ]
// *
// * - Dodaj więcej użytkowników z tym samym wiekiem i sprawdź,
// *   że zwróci wielu (np. dwóch Bobów w wieku 30 lat).
// * - Obsłuż przypadek, gdy lista jest pusta → zwróć 404 z komunikatem:
// *   "Brak użytkowników w wieku X".
// */
//@RestController
//public class Exercise9Controller {
//
//    record User(int id, String name, String email, int age) {}
//
//    private final List<User> users = List.of(
//            new User(1, "Alice", "alice@example.com", 25),
//            new User(2, "Bob", "bob@example.com", 30),
//            new User(3, "Charlie", "charlie@example.com", 35),
//            new User(4, "David", "david@example.com", 30),
//            new User(5, "David", "david@example.com", 30),
//            new User(6, "David", "david@example.com", 30)
//    );
//
//    @GetMapping("/usersByAge")
//    public ResponseEntity<?> getUsersByAge(@RequestParam int age) {
//        List<User> filtered = users.stream()
//                .filter(u -> u.age() == age)
//                .toList();
//
//        if (filtered.isEmpty()) {
//            return ResponseEntity.status(404)
//                    .body("Brak użytkowników w wieku " + age);
//        }
//
//        return ResponseEntity.ok(filtered);
//    }