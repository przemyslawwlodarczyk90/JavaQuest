package com.example.javaquest.platform.content;

/**
 * Typ bloku teorii lekcji - kazdy typ renderowany jest innym komponentem po stronie
 * frontendu (patrz EDU_PLATFORM_PLAN.md, sekcja 4.1), zeby notatka byla wizualna, nie
 * jedna sciana tekstu.
 */
public enum ContentBlockType {
    /** Krotkie, konkretne wyjasnienie pojecia. */
    CONCEPT,
    /** Plastyczna analogia (np. "JVM to konsola do gier") - serce "graficznej notatki". */
    ANALOGY,
    /** Fragment kodu Java z komentarzem. */
    CODE_EXAMPLE,
    /** Prosty schemat/diagram opisany tekstowo (np. ASCII, krok po kroku). */
    DIAGRAM
}
