import java.util.List;
import java.util.function.Predicate;

public class Filtros {
    public static Predicate<Integer> esPar = new Predicate<Integer>() {
        @Override
        public boolean test(Integer n) {
            return n % 2 == 0;
        }
    };

    public static Predicate<Integer> esImpar = new Predicate<Integer>() {
        @Override
        public boolean test(Integer n) {
            return n % 2 != 0;
        }
    };

    public static Predicate<Integer> esMayorQueSiete = new Predicate<Integer>() {
        @Override
        public boolean test(Integer n) {
            return n > 7;
        }
    };
}
