package com.example.javaquest._28_java_evolution.Lesson08_Java10LocalVariableTypeInference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class _Lesson08_Java10LocalVariableTypeInference {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 8: Java 10 (marzec 2018) - wnioskowanie typu 'var' ===");

        /*
         * ============================================================
         * 📦 PRZYPOMNIENIE - JUZ POZNANE W `_14_advancedjava/Lesson23_VarAndTypeInference`
         * ============================================================
         * Java 10 TO PIERWSZA wersja NIE-LTS PO Java 9 (dokladnie 6
         * miesiecy pozniej - Lesson01 pokazal TEN rytm). GLOWNA
         * funkcja: `var` - LOKALNE wnioskowanie typu (compiler SAM
         * WYWNIOSKOWUJE typ Z PRAWEJ strony `=`) - WAZNE: `var` TO
         * NIE "typ dynamiczny" (JAK W Pythonie/JavaScript) - typ jest
         * NADAL USTALONY W CZASIE KOMPILACJI, TYLKO NIE MUSISZ go
         * PISAC JAWNIE.
         *
         * 🔍 TA lekcja NIE POWTARZA SZCZEGOLOW (`_14_advancedjava/
         * Lesson23` JUZ przedyskutowal KIEDY uzywac `var`, a KIEDY
         * NIE) - TU demo POKAZUJE DOKLADNIE, ILE znakow `var`
         * OSZCZEDZA PRZY DLUGICH, GENERYCZNYCH typach.
         */
        System.out.println("Java 10 (marzec 2018): var - LOKALNE wnioskowanie typu (compile-time, NIE dynamiczne typowanie). Pelna teoria: _14_advancedjava/Lesson23.");

        demonstrateVerboseVsVar();

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - `var lista = new ArrayList<String>();` - typ `ArrayList
         *   <String>` WYWNIOSKOWANY Z PRAWEJ strony, NIE trzeba
         *   PISAC go 2 RAZY.
         * - `var` DZIALA TYLKO DLA ZMIENNYCH LOKALNYCH (NIGDY DLA pol
         *   klasy, parametrow metod - z WYJATKIEM lambd OD Javy 11,
         *   Lesson09, ANI typu zwracanego).
         * - `var` WYMAGA inicjalizacji W TEJ SAMEJ linii (`var x;`
         *   BEZ wartosci - BLAD kompilacji).
         * - POGLEBIONA teoria (KIEDY UZYWAC, a KIEDY NIE - CZYTELNOSC
         *   kontra ZWIEZLOSC): `_14_advancedjava/Lesson23`.
         */
        System.out.println("\n=== KONIEC LEKCJI 8 ===");
    }

    private static void demonstrateVerboseVsVar() {
        System.out.println("\n--- PRZED Java 10: JAWNY, DLUGI typ generyczny (powtorzony 2 razy) ---");
        Map<String, List<Integer>> scoresByPlayerOld = new HashMap<String, List<Integer>>();
        scoresByPlayerOld.put("Anna", new ArrayList<Integer>(List.of(10, 20, 30)));
        System.out.println("Map<String, List<Integer>> scoresByPlayerOld = new HashMap<String, List<Integer>>();");
        System.out.println("Wynik: " + scoresByPlayerOld);

        System.out.println("\n--- PO Java 10: var - typ WYWNIOSKOWANY, ZERO powtorzenia ---");
        var scoresByPlayerNew = new HashMap<String, List<Integer>>();
        scoresByPlayerNew.put("Anna", new ArrayList<>(List.of(10, 20, 30)));
        System.out.println("var scoresByPlayerNew = new HashMap<String, List<Integer>>();");
        System.out.println("Wynik: " + scoresByPlayerNew);

        assertThat(scoresByPlayerOld).isEqualTo(scoresByPlayerNew);

        System.out.println("\n--- var TO STATYCZNE typowanie 'pod maska' (NIE dynamiczne!) ---");
        var count = 5; // Kompilator WYWNIOSKOWAL "int" - PROBA count = "tekst" BYLABY bledem kompilacji.
        System.out.println("var count = 5; -> WYWNIOSKOWANY typ TO int (STATYCZNY, USTALONY NA ZAWSZE PRZY kompilacji).");
        assertThat(count).isInstanceOf(Integer.class);
    }
}
