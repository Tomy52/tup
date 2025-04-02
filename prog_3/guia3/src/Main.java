import org.w3c.dom.ls.LSOutput;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class Main {
    public static void main(String[] args) {
        //Llamado a funcion estatica

        //Function<Integer,Integer> operacion = Matematica::cuadrado;
        //System.out.println(operacion.apply(5));

        //Llamado a funcion anonima

//        Function<Integer,Integer> operacion = n -> n*n;
//        System.out.println(operacion.apply(5));
//
//        Predicate<Integer> es_par = n -> n % 2 == 0;
//        System.out.println(es_par.test(2));
//
//        Consumer<String> imprimir = mensaje -> System.out.println(mensaje);
//        imprimir.accept("hello world");

        //Supplier<Double> randomi = Math.random();
        //System.out.println(randomi.get());

        //Parte 1 ejercitacion - funciones lambda e interfaces funcionales

        Function<Integer,Integer> duplicar = n -> n * 2;
        System.out.println(duplicar.apply(2));

        Predicate<Integer> es_par = n -> n % 2 == 0;
        System.out.println(es_par.test(2));

        Consumer<String> mostrar = mensaje -> System.out.println(mensaje);
        mostrar.accept("hola");

        //Parte 2 ejercitacion - referencias a metodos

        Consumer<String> mostrarRef = Mensajes::mostrar;
        mostrarRef.accept("hello world");

        Function<Integer,Integer> cuadrado = Matematica::cuadrado;
        System.out.println(cuadrado.apply(5));

        //Parte 3 - stream API

        List<String> palabras = new ArrayList<String>();
        palabras.add("Java");
        palabras.add("Streams");
        palabras.add("es");
        palabras.add("genial");

        palabras.stream()
                .map(String::toUpperCase)
                .forEach(System.out::println);

        palabras.stream()
                .filter(p -> p.length() > 4)
                .forEach(System.out::println);

        List<Integer> enteros = new ArrayList<Integer>();
        enteros.add(2);
        enteros.add(4);
        enteros.add(6);

        int sumaCuadrados = enteros.stream()
                .map(Matematica::cuadrado)
                .reduce(0, Integer::sum);

        System.out.println(sumaCuadrados);

        // Parte 4 ejercitacion - streams combinados

        palabras.stream()
                .filter(palabra -> palabra.startsWith("a") ||
                        palabra.startsWith("e") ||
                        palabra.startsWith("i") ||
                        palabra.startsWith("o") ||
                        palabra.startsWith("u"))
                .forEach(System.out::println);

        // Parte 5 ejercitacion - Optional + lambda

        Integer prueba = null;
        Optional<Integer> opt = Optional.ofNullable(prueba);

        opt.ifPresentOrElse(n -> System.out.println("hay un numero"),
                () -> System.out.println("no hay nada"));

        String palabra = "hola";
        Optional<String> optWord = Optional.of(palabra);

        System.out.println(optWord.map(String::length).orElse(0));
    }
}