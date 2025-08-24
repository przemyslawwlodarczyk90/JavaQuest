//package com.example.javaquest._01_fundamentals.Training_3;
//
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
///**
// * Ćwiczenie 5:
// * Kalkulator z wyborem operacji przez parametr query.
// *
// * Zadanie:
// * - Wywołaj w przeglądarce/Postmanie np.:
// *   http://localhost:8080/calc?x=10&y=5&op=add   → "Result: 15"
// *   http://localhost:8080/calc?x=10&y=5&op=sub   → "Result: 5"
// *   http://localhost:8080/calc?x=10&y=5&op=mul   → "Result: 50"
// *   http://localhost:8080/calc?x=10&y=5&op=div   → "Result: 2"
// *
// * - Obsłuż przypadek, gdy użytkownik poda nieznaną operację (np. `op=xyz`).
// * - Obsłuż przypadek dzielenia przez zero (np. `y=0` i `op=div`).
// */
//@RestController
//public class Exercise5Controller {
//    @GetMapping("/calc")
//    public String calculate(
//            @RequestParam Integer x,
//            @RequestParam Integer y,
//            @RequestParam String op) {
//
//        while(op.equalsIgnoreCase("add") || op.equalsIgnoreCase("sub")
//                || op.equalsIgnoreCase("mul") || op.equalsIgnoreCase("div")){
//        switch (op.toLowerCase()) {
//            case "add":
//                return "Result: " + (x + y);
//            case "sub":
//                return "Result: " + (x - y);
//            case "mul":
//                return "Result: " + (x * y);
//            case "div":
//                if (y == 0) {
//                    return "Error: Division by zero!";
//                }
//                try{
//                return "Result: " + (x / y)
//                }
//                    catch (NullPointerException e){
//                        e.printStackTrace;
//                }
//            };
//            default:
//                return "Error: Unknown operation '" + op + "'";
//        }}
//        ;
//    }
//}
