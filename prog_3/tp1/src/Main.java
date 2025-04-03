import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
//        1. Filtrar números pares
//        Dada una lista de numeros enteros, utiliza filter para obtener solo
//        los numeros pares y guardalos en una nueva lista.

        System.out.println("Ejercicio 1:");

        List<Integer> numerosSinFiltrar = new ArrayList<>();
        List<Integer> numerosFiltrados;
        Random random = new Random();

        for (int i = 0; i < 10; i++) {
            numerosSinFiltrar.add(random.nextInt(100) + 1);
        }

        numerosFiltrados = numerosSinFiltrar.stream()
                .filter(Filtros.esPar)
                .toList();

        numerosFiltrados
                .forEach(System.out::println);


//        2. Transformar una lista de nombres a mayúsculas
//        Usa map para convertir cada nombre de una lista en su version en mayusculas.
        System.out.println("Ejercicio 2:");

        List<String> nombres = new ArrayList<>();

        nombres.add("juan");
        nombres.add("maria");
        nombres.add("pedro");
        nombres.add("ana");
        nombres.add("luis");

        nombres.stream()
                .map(String::toUpperCase)
                .forEach(System.out::println);

//        3. Ordenar una lista de números
//        Usa sorted para ordenar una lista de numeros enteros de menor a mayor.

//        Usa la coleccion del ejercicio 1

        System.out.println("Ejercicio 3:");

        numerosSinFiltrar.stream()
                .sorted()
                .forEach(System.out::println);

//        4. Contar elementos mayores a un valor dado
//        Dada una lista de numeros, usa filter y count para contar
//        cuantos valores son mayores que 7.

//        Usa la coleccion del ejercicio 1

        System.out.println("Ejercicio 4:");

        Long numerosMayoresASiete = numerosSinFiltrar.stream()
                .filter(Filtros.esMayorQueSiete)
                .count();

        System.out.println(numerosMayoresASiete);

//        5. Obtener los primeros 5 elementos de una lista
//        Usa limit para extraer solo los primeros 5 elementos de una lista de numeros.

//        Usa la coleccion del ejercicio 1

        System.out.println("Ejercicio 5:");

        numerosSinFiltrar.stream()
                .limit(5)
                .forEach(System.out::println);

//        6. Convertir una lista de palabras en su longitud
//        Usa map para transformar una lista de palabras
//        en una lista con la longitud de cada palabra.

//        Usa la coleccion del ejercicio 2

        System.out.println("Ejercicio 6:");

        nombres.stream()
                .map(String::length)
                .forEach(System.out::println);

//        7. Concatenar nombres con una separación
//        Dada una lista de nombres, usa reduce para
//        concatenarlos en un solo String separados por comas.

//        Usa la coleccion del ejercicio 2

        System.out.println("Ejercicio 7:");

        String nombresConcatenados = nombres.stream()
                .reduce((nombre1, nombre2) -> nombre1 + "," + nombre2)
                .orElse("");

        System.out.println(nombresConcatenados);

//        8. Eliminar duplicados de una lista
//        Usa distinct para remover duplicados de una lista de numeros enteros.

        System.out.println("Ejercicio 8:");

        List<Integer> numerosDuplicados = new ArrayList<>();
        numerosDuplicados.add(1);
        numerosDuplicados.add(1);
        numerosDuplicados.add(3);

        System.out.println("Numeros duplicados: " + numerosDuplicados);


        List<Integer> numerosSinDuplicar = numerosDuplicados.stream()
                .distinct()
                .toList();

        System.out.println("Numeros sin duplicar: " + numerosSinDuplicar);

//        9. Obtener los 3 números más grandes de una lista
//        Usa sorted y limit para encontrar los 3 números más
//        grandes en una lista de enteros.

//        Usa la coleccion del ejercicio 1

        System.out.println("Ejercicio 9:");

        numerosSinFiltrar.stream()
                .sorted(Comparator.reverseOrder())
                .limit(3)
                .forEach(System.out::println);

//        10. Agrupar palabras por su longitud
//        Usa Collectors.groupingBy para agrupar una lista de
//        palabras según su cantidad de caracteres.

//        Usa la coleccion del ejercicio 2

        System.out.println("Ejercicio 10");

        Map<Integer,List<String>> nombresAgrupadosPorLongitud = new HashMap<>();

        nombresAgrupadosPorLongitud = nombres.stream()
                .collect(Collectors.groupingBy(String::length));

        System.out.println(nombresAgrupadosPorLongitud);

//        11. Encontrar el producto de todos los números de una lista
//        Usa reduce para calcular el producto de todos los
//        numeros de una lista.

//        Usa la coleccion del ejercicio 8

        Integer productoDeNumeros = numerosSinDuplicar.stream()
                .reduce(1,(n1,n2) -> n1 * n2);

        System.out.println(productoDeNumeros);

//        12. Obtener el nombre mas largo de una lista
//        Usa reduce para encontrar el nombre con más caracteres
//        en una lista de nombres.

//        Usa la coleccion del ejercicio 2

        System.out.println("Ejercicio 12:");

        String nombreMasLargo = nombres.stream()
                .reduce((nombre1,nombre2) -> nombre1.length() > nombre2.length() ? nombre1 : nombre2 ).orElse("");

        System.out.println(nombreMasLargo);

//        13. Convertir una lista de enteros en una cadena separada
//        por guiones

//        Usa map y Collectors.joining para convertir una lista
//        de enteros en una cadena con valores separados por -

//        Usa la coleccion del ejercicio 1

        System.out.println("Ejercicio 13:");

        String separadosConGuion = numerosSinFiltrar.stream()
                .map(Object::toString)
                .collect(Collectors.joining("-"));

        System.out.println(separadosConGuion);

//        14. Agrupar una lista de números en pares e impares
//        Usa Collectors.partitioningBy para separar los
//        numeros de una lista en pares e impares.

//        Usa la coleccion del ejercicio 1

        System.out.println("Ejercicio 14:");

        Map<Boolean,List<Integer>> numerosParesEImpares = numerosSinFiltrar.stream()
                .collect(Collectors.partitioningBy(n -> n % 2 == 0));

        System.out.println(numerosParesEImpares);

//        15. Obtener la suma de los cuadrados de los números impares
//        Usa filter, map y reduce para obtener la suma de los
//        cuadrados de los números impares de una lista.

//        Usa la coleccion del ejercicio 1

        System.out.println("Ejercicio 15:");

        Integer sumaCuadrados;

        sumaCuadrados = numerosSinFiltrar.stream()
                .filter(Filtros.esImpar)
                .map(Matematica::cuadrado)
                .reduce(0, Integer::sum);

        System.out.println(sumaCuadrados);


    }
}