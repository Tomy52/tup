package Ui.Implementations;
import Model.Implementations.Task;
import Repository.Implementations.Tasks;
import Ui.Interfaces.Menu;

import java.util.ArrayList;
import java.util.Scanner;

public class TasksMenu implements Menu {
    @Override
    public void displayMenu() {
        Tasks tasks = new Tasks(new ArrayList<>());
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("Menu:");
            System.out.println("1. Crear nueva tarea");
            System.out.println("2. Ver tareas existentes");
            System.out.println("3. Marcar tarea como completada");
            System.out.println("4. Modificar descripcion de una tarea");
            System.out.println("5. Salir");
            System.out.print("Seleccione una opcion: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Ingrese la descripcion de la tarea: ");
                    String description = scanner.nextLine();
                    tasks.add(new Task(description));
                    System.out.println("Tarea agregada exitosamente.");
                    break;
                case 2:
                    System.out.println("Tareas existentes:");
                    System.out.println(tasks);
                    break;
                case 3:
                    System.out.print("Ingrese el indice de la tarea a marcar como completada: ");
                    int taskIndexToMarkComplete = scanner.nextInt();
                    tasks.markAsComplete(taskIndexToMarkComplete);
                    System.out.println("Tarea marcada como completada.");
                    break;
                case 4:
                    System.out.print("Ingrese el indice de la tarea a modificar: ");
                    int taskIndexToModify = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Ingrese la nueva descripcion: ");
                    String newDescription = scanner.nextLine();
                    tasks.modify(taskIndexToModify, newDescription);
                    System.out.println("Descripcion de la tarea modificada.");
                    break;
                case 5:
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("Opcion no valida.");
            }
        } while (choice != 5);

        scanner.close();
    }
}