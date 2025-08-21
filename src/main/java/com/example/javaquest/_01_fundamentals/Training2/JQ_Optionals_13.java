import java.util.*;

public class JQ_Optionals_13 {

    /*
     * ĆWICZENIE:
     * Napisz metodę getFirstPositive(List<Integer> numbers), która:
     * 1. Szuka pierwszej liczby dodatniej.
     * 2. Jeśli znajdzie → zwraca ją jako String.
     * 3. Jeśli nie znajdzie → zwraca napis "brak dodatniej".
     *
     * Podpowiedź: użyj stream(), filter(), findFirst(), map(), orElse().
     */

    public static String getFirstPositive(List<Integer> numbers) {
        // TODO: zaimplementuj tutaj
        return numbers.stream()
                .filter(a->a>0)
                .findFirst()
                .map(String::valueOf)
                .orElse("brak dodatniej");
    }

    public static void main(String[] args) {
        List<Integer> numbers1 = Arrays.asList(-5, -3, 0, 7, -1);
        List<Integer> numbers2 = Arrays.asList(-10, -20, -30);

        System.out.println(getFirstPositive(numbers1)); // powinno zwrócić "7"
        System.out.println(getFirstPositive(numbers2)); // powinno zwrócić "brak dodatniej"
    }
}
