package Ui.Implementations;

import Repository.Implementations.Phonebook;
import Ui.Interfaces.Menu;

import java.util.HashMap;
import java.util.Scanner;

public class PhonebookMenu implements Menu {
    @Override
    public void displayMenu() {
        Phonebook book = new Phonebook(new HashMap<>());
        Scanner scanner = new Scanner(System.in);
        int choice;
        do {
            System.out.println("Menu:");
            System.out.println("1. Agregar un nombre y numero de telefono");
            System.out.println("2. Buscar un numero de telefono en base a un nombre");
            System.out.println("3. Modificar un numero de telefono en base a un nombre");
            System.out.println("4. Eliminar un contacto en base al nombre");
            System.out.println("5. Ver todos los contactos de la agenda");
            System.out.println("6. Salir");
            System.out.print("Seleccione una opcion: ");
            choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    System.out.print("Ingrese el nombre: ");
                    String contactName = scanner.nextLine();
                    System.out.print("Ingrese el numero de telefono: ");
                    String contactPhoneNumber = scanner.nextLine();
                    if (book.add(contactName, contactPhoneNumber)) {
                        System.out.println("Contacto agregado exitosamente.");
                    } else {
                        System.out.println("El contacto ya existe.");
                    }
                    break;
                case 2:
                    System.out.print("Ingrese el nombre: ");
                    String contactNameToSearch = scanner.nextLine();
                    System.out.println("Numero de telefono: " + book.find(contactNameToSearch));
                    break;
                case 3:
                    System.out.print("Ingrese el nombre: ");
                    String contactNameToChangePhoneNumber = scanner.nextLine();
                    System.out.print("Ingrese el nuevo numero de telefono: ");
                    String newPhoneNumber = scanner.nextLine();
                    if (book.modify(contactNameToChangePhoneNumber, newPhoneNumber)) {
                        System.out.println("Numero de telefono modificado exitosamente.");
                    } else {
                        System.out.println("El contacto no existe.");
                    }
                    break;
                case 4:
                    System.out.print("Ingrese el nombre: ");
                    String contactNameToDelete = scanner.nextLine();
                    if (book.delete(contactNameToDelete)) {
                        System.out.println("Contacto eliminado exitosamente.");
                    } else {
                        System.out.println("El contacto no existe.");
                    }
                    break;
                case 5:
                    System.out.println(book);
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