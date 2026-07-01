package com.example.javaquest._01_fundamentals.Lesson01_Variables;

public class _Exercises_Lesson01_Variables {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)
    // 🟡 POZIOM 2 – ŚREDNI (11-20)
    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise01_DeclarePrimitiveTypes {
        /*
         * 🧪 Zadanie 1:
         * Zadeklaruj zmienne każdego typu prymitywnego (byte, short, int, long, float, double, char, boolean)
         * i przypisz im przykładowe wartości. Wypisz każdą na ekranie.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise02_DeclareAndUseVar {
        /*
         * 🧪 Zadanie 2:
         * Użyj słowa kluczowego var do deklaracji co najmniej 3 zmiennych różnych typów
         * (np. int, String, double). Wypisz każdą z nich.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise03_LocalVsField {
        /*
         * 🧪 Zadanie 3:
         * Stwórz zmienną instancyjną (pole klasy) oraz zmienną lokalną wewnątrz main.
         * Wypisz obie. Sprawdź, do której możesz uzyskać dostęp bez tworzenia obiektu.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise04_CompareStrings {
        /*
         * 🧪 Zadanie 4:
         * Stwórz dwa obiekty String: jeden jako literał, drugi przez new String("...").
         * Porównaj je za pomocą == oraz .equals(). Wypisz oba wyniki i wyjaśnij różnicę w komentarzu.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise05_UseNull {
        /*
         * 🧪 Zadanie 5:
         * Zadeklaruj zmienną String i przypisz jej wartość null.
         * Sprawdź warunkiem if, czy jest nullem, i wypisz odpowiedni komunikat.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise06_CompareWrappers {
        /*
         * 🧪 Zadanie 6:
         * Stwórz dwa obiekty Integer o tej samej wartości (np. 1000).
         * Porównaj je przez == i .equals(). Wypisz wyniki.
         * Następnie sprawdź to samo dla wartości 127 (pamięć o Integer cache!).
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise07_WrapperConversion {
        /*
         * 🧪 Zadanie 7:
         * Użyj metod klas Wrapper:
         * - Integer.parseInt("42") → wypisz wynik
         * - Double.parseDouble("3.14") → wypisz wynik
         * - Boolean.parseBoolean("true") → wypisz wynik
         * - Integer.toString(99) → wypisz wynik
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise08_PrintArrayValues {
        /*
         * 🧪 Zadanie 8:
         * Stwórz tablicę int[] z 5 elementami. Wypisz każdy element w pętli for.
         * Następnie wypisz całą tablicę za pomocą Arrays.toString().
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise09_CheckDefaultPrimitiveValues {
        /*
         * 🧪 Zadanie 9:
         * Stwórz klasę z polami int, double, boolean i String (bez inicjalizacji).
         * Utwórz obiekt tej klasy i wypisz domyślne wartości pól.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise10_UseFinalVariable {
        /*
         * 🧪 Zadanie 10:
         * Zadeklaruj zmienną final int MAX_VALUE = 100.
         * Wypisz ją. Spróbuj zmienić jej wartość – co się stanie? (zostaw jako komentarz).
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise11_ParseFromInput {
        /*
         * 🧪 Zadanie 11:
         * Zadeklaruj String "42" i zamień go na int używając Integer.parseInt().
         * Następnie zadeklaruj String "3.14" i zamień go na double.
         * Wypisz oba wyniki.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise12_OverflowCheck {
        /*
         * 🧪 Zadanie 12:
         * Przypisz do zmiennej int wartość Integer.MAX_VALUE.
         * Dodaj do niej 1. Wypisz wynik. Wyjaśnij co się stało (overflow).
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise13_AutoBoxingUnboxingEdgeCase {
        /*
         * 🧪 Zadanie 13:
         * Stwórz Integer boxed = 200; i int unboxed = boxed;
         * Wypisz oba. Następnie stwórz Integer a = null i spróbuj unboxować:
         * int value = a; – co się stanie? Obsłuż wyjątek NullPointerException.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise14_ArrayDefaultValues {
        /*
         * 🧪 Zadanie 14:
         * Stwórz tablicę int[] o rozmiarze 5 bez inicjalizacji elementów.
         * Wypisz wszystkie elementy – jakie mają domyślne wartości?
         * Zrób to samo dla String[] – jaka jest domyślna wartość?
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise15_NullComparisonLogic {
        /*
         * 🧪 Zadanie 15:
         * Stwórz String s = null.
         * Sprawdź za pomocą if, czy jest nullem.
         * Następnie wywołaj s.length() – jakie to da wyjątek? Obsłuż go.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise16_MutabilityTest {
        /*
         * 🧪 Zadanie 16:
         * Stwórz String s = "Hello". Wywołaj s.toUpperCase() i wypisz wynik.
         * Wypisz też oryginalne s. Czy String został zmodyfikowany?
         * Wyjaśnij pojęcie immutability w komentarzu.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise17_ArrayReassignment {
        /*
         * 🧪 Zadanie 17:
         * Stwórz int[] arr1 = {1, 2, 3}.
         * Przypisz int[] arr2 = arr1.
         * Zmień arr2[0] = 99.
         * Wypisz arr1[0] – co się stało? Wyjaśnij pojęcie referencji.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise18_StringVsStringBuilderComparison {
        /*
         * 🧪 Zadanie 18:
         * Stwórz String i złącz do niego 1000 razy słowo "test" w pętli (operator +).
         * Zmierz czas używając System.currentTimeMillis().
         * Następnie zrób to samo ze StringBuilder i append().
         * Porównaj czasy.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise19_FinalArrayMutation {
        /*
         * 🧪 Zadanie 19:
         * Zadeklaruj final int[] arr = {1, 2, 3}.
         * Spróbuj zmienić arr[0] = 99 – czy to działa?
         * Spróbuj przypisać arr = new int[]{4, 5, 6} – co się stanie?
         * Wyjaśnij różnicę między final a niemutowalnością elementów.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise20_TypeInferenceGotcha {
        /*
         * 🧪 Zadanie 20:
         * Użyj var do deklaracji kilku zmiennych i wypisz ich rzeczywisty typ przez .getClass().getSimpleName().
         * np. var x = 3.14; → wypisz "Double".
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise21_ShadowingField {
        /*
         * 🧪 Zadanie 21:
         * W klasie wewnętrznej zdefiniuj pole String name = "Globalne".
         * W metodzie run() zadeklaruj lokalną String name = "Lokalne".
         * Wypisz obie wartości – użyj this.name dla pola klasy.
         */
        public static void main(String[] args) {
            new Helper().run();
        }
        static class Helper {
            String name = "Globalne";
            void run() {
                // TODO: zadeklaruj lokalne name i wypisz oba
            }
        }
    }

    static class Exercise22_SwapWrapperValues {
        /*
         * 🧪 Zadanie 22:
         * Zadeklaruj Integer a = 10, b = 20.
         * Zamień ich wartości bez użycia dodatkowej zmiennej tymczasowej (używając dodawania i odejmowania).
         * Wypisz przed i po zamianie.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise23_CopyVsReference {
        /*
         * 🧪 Zadanie 23:
         * Napisz metodę static void changeInt(int x) { x = 99; }
         * i metodę static void changeName(StringBuilder sb) { sb.append(" Zmieniony"); }.
         * Wywołaj obie i wypisz wyniki przed i po – co się zmieniło, a co nie?
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise24_FinalVsEffectivelyFinal {
        /*
         * 🧪 Zadanie 24:
         * Zadeklaruj int x = 5 (nie final). Użyj go w wyrażeniu lambda: Runnable r = () -> System.out.println(x);
         * Następnie spróbuj zmienić x = 10 przed lambdą – co się stanie?
         * Wyjaśnij pojęcie "effectively final" w komentarzu.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise25_MutateReferenceInsideMethod {
        /*
         * 🧪 Zadanie 25:
         * Stwórz tablicę int[] numbers = {1, 2, 3}.
         * Napisz metodę static void doubleAll(int[] arr) która mnoży każdy element przez 2.
         * Wywołaj metodę i wypisz tablicę przed i po. Wyjaśnij dlaczego wyniki się zmieniły.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise26_CompareFloatingPoints {
        /*
         * 🧪 Zadanie 26:
         * Wykonaj: double result = 0.1 + 0.2;
         * Porównaj result == 0.3 – co zwraca? Wypisz result.
         * Następnie użyj Math.abs(result - 0.3) < 1e-10 jako bezpiecznego porównania.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise27_UninitializedLocalVariable {
        /*
         * 🧪 Zadanie 27:
         * Zadeklaruj lokalną zmienną int x bez inicjalizacji.
         * Spróbuj jej użyć – co mówi kompilator?
         * Popraw błąd, inicjalizując x i wypisz ją.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise28_StringInternEffect {
        /*
         * 🧪 Zadanie 28:
         * Stwórz String a = new String("Hello") i String b = new String("Hello").
         * Porównaj a == b (false). Wywołaj a.intern() i b.intern() i porównaj wyniki przez ==.
         * Wyjaśnij działanie String Pool w komentarzu.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise29_PrimitiveToWrapperEdge {
        /*
         * 🧪 Zadanie 29:
         * Wypisz: Integer.MAX_VALUE, Integer.MIN_VALUE, Long.MAX_VALUE, Double.MAX_VALUE.
         * Następnie: Integer.MAX_VALUE + 1 → co się stanie? I (long) Integer.MAX_VALUE + 1 → co zwróci?
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise30_NullInArray {
        /*
         * 🧪 Zadanie 30:
         * Stwórz tablicę String[] names = {"Anna", null, "Bartek"}.
         * Przejdź przez tablicę w pętli i wypisz długość każdego imienia.
         * Obsłuż wartość null bez rzucania NullPointerException – wypisz "null" zamiast.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }
}
