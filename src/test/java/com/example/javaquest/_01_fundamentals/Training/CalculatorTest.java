package com.example.javaquest._01_fundamentals.Training;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest {

    Calculator calculator;

    @BeforeEach
    void setUp() {
        calculator = new Calculator();
    }

  @Test
    void checkaddition(){
        int a = 2;
        int b = 2;

       int result = calculator.add(a,b);

        assertEquals(4,result);

    }

    @Test
     void checkMultiplying(){
        int a = 2;
        int b = 3;

        int result = calculator.multiply(a,b);

        assertEquals(6,result);
        assertNotEquals(7, result);
    }






}