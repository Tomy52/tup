package View;

import Controller.Implementation.UserController;
import Model.Implementation.Users.User;

import java.util.Optional;
import java.util.Scanner;

public class Menu {
    private final UserController userController;
    private final Scanner scanner;
    private Optional<User> loggedUser = Optional.empty();

    public Menu() {
        userController = new UserController();
        scanner = new Scanner(System.in);
    }

    public void mostrarMenu() {
        int opcion;
        do {
            System.out.println("1. Agregar Usuario");
            System.out.println("2. Iniciar Sesión");
            System.out.println("3. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (opcion) {
                case 1:
                    agregarUsuario();
                    break;
                case 2:
                    iniciarSesion();
                    break;
                case 3:
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
            }
        } while (opcion != 3);
    }

    private void agregarUsuario() {
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Apellido: ");
        String apellido = scanner.nextLine();
        System.out.print("DNI: ");
        String dni = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();

        userController.agregar(nombre, apellido, dni, email);
        System.out.println("Usuario agregado exitosamente.");
    }

    private void iniciarSesion() {
        System.out.print("Usuario: ");
        String username = scanner.nextLine();
        System.out.print("Contraseña: ");
        String password = scanner.nextLine();

        loggedUser = userController.login(username,password);

        System.out.println("Hola " + loggedUser.get().getNombre());
    }
}