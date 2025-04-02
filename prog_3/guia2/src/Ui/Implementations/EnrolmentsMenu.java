package Ui.Implementations;

import Repository.Implementations.Enrolments;
import Ui.Interfaces.Menu;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class EnrolmentsMenu implements Menu {
    private final Enrolments enrolments;
    private final Scanner scanner;

    public EnrolmentsMenu() {
        Set<String> enrolmentsSet = new HashSet<>();
        this.enrolments = new Enrolments(enrolmentsSet);
        this.scanner = new Scanner(System.in);
    }

    @Override
    public void displayMenu() {
        int choice;
        do {
            System.out.println("Menu de inscripciones:");
            System.out.println("1. Agregar un nombre a la lista de inscriptos");
            System.out.println("2. Verificar si alguien ya esta inscripto");
            System.out.println("3. Eliminar un nombre de la lista");
            System.out.println("4. Mostrar la lista de inscriptos");
            System.out.println("5. Salir");
            System.out.print("Seleccione una opcion: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    addEnrolment();
                    break;
                case 2:
                    isEnrolled();
                    break;
                case 3:
                    deleteEnrolment();
                    break;
                case 4:
                    displayEnrolled();
                    break;
                case 5:
                    System.out.println("Saliendo del menu...");
                    break;
                default:
                    System.out.println("Opcion invalida. Intente nuevamente.");
            }
        } while (choice != 5);
    }

    private void addEnrolment() {
        System.out.print("Ingrese el nombre a inscribir: ");
        String name = scanner.nextLine();
        enrolments.enrol(name);
        System.out.println("Nombre agregado a la lista de inscriptos.");
    }

    private void isEnrolled() {
        System.out.print("Ingrese el nombre a verificar: ");
        String name = scanner.nextLine();
        if (enrolments.isEnrolled(name)) {
            System.out.println(name + " ya esta inscripto.");
        } else {
            System.out.println(name + " no esta inscripto.");
        }
    }

    private void deleteEnrolment() {
        System.out.print("Ingrese el nombre a eliminar: ");
        String name = scanner.nextLine();
        enrolments.delete(name);
        System.out.println("Nombre eliminado de la lista de inscriptos.");
    }

    private void displayEnrolled() {
        System.out.println("Lista de inscriptos:");
        System.out.println(enrolments);
    }
}