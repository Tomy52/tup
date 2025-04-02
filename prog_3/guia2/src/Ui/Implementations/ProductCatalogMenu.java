package Ui.Implementations;

import Repository.Implementations.Phonebook;
import Ui.Interfaces.Menu;

import java.util.Scanner;
import java.util.TreeMap;

public class ProductCatalogMenu implements Menu {
    @Override
    public void displayMenu() {
        //Implementacion reciclada UNICAMENTE para el ejercicio
        Phonebook catalog = new Phonebook(new TreeMap<>());

        Scanner scanner = new Scanner(System.in);
        int choice;
        do {
            System.out.println("Menu:");
            System.out.println("1. Agregar un producto y su precio");
            System.out.println("2. Buscar el precio de un producto por su nombre");
            System.out.println("3. Modificar el precio de un producto por su nombre");
            System.out.println("4. Eliminar un producto");
            System.out.println("5. Ver todos los productos del catalogo");
            System.out.println("6. Salir");
            System.out.print("Seleccione una opcion: ");
            choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    System.out.print("Ingrese el nombre: ");
                    String productName = scanner.nextLine();
                    System.out.print("Ingrese el precio: ");
                    String productPrice = scanner.nextLine();
                    if (catalog.add(productName, productPrice)) {
                        System.out.println("Producto agregado exitosamente.");
                    } else {
                        System.out.println("El producto ya existe.");
                    }
                    break;
                case 2:
                    System.out.print("Ingrese el nombre: ");
                    String productNameToSearch = scanner.nextLine();
                    System.out.println("Precio: " + catalog.find(productNameToSearch));
                    break;
                case 3:
                    System.out.print("Ingrese el nombre: ");
                    String productNameToChangePrice = scanner.nextLine();
                    System.out.print("Ingrese el nuevo precio: ");
                    String newPrice = scanner.nextLine();
                    if (catalog.modify(productNameToChangePrice, newPrice)) {
                        System.out.println("Precio modificado exitosamente.");
                    } else {
                        System.out.println("El producto no existe.");
                    }
                    break;
                case 4:
                    System.out.print("Ingrese el nombre: ");
                    String productNameToDelete = scanner.nextLine();
                    if (catalog.delete(productNameToDelete)) {
                        System.out.println("Producto eliminado exitosamente.");
                    } else {
                        System.out.println("El producto no existe.");
                    }
                    break;
                case 5:
                    System.out.println(catalog);
                    break;
                case 6:
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("Opcion no valida.");
            }
        } while(choice !=6);

        scanner.close();

    }
}