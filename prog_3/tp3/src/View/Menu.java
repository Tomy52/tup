package View;

import Controller.Exception.NoAutorizadoException;
import Controller.Exception.NoEncontradoException;
import Controller.Implementation.UsuarioController;
import Model.Implementation.Usuario.Usuario;

import javax.security.auth.login.CredentialException;
import java.util.Scanner;

public class Menu {
    private final UsuarioController controlador;
    private final Scanner scanner;

    public Menu() {
        controlador = new UsuarioController();
        scanner = new Scanner(System.in);
    }

    public void mostrarMenu() {
        int opcion;
        do {
            System.out.println("1. Iniciar sesion");
            System.out.println("2. Registrar usuario");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1 -> iniciarSesion();
                case 2 -> agregarUsuario();
                case 0 -> System.out.println("Saliendo...");

                default -> System.out.println("Opción no válida. Intente de nuevo.");
            }
        } while (opcion != 0 && controlador.obtenerLogueado().getId_usuario() == 0);
        mostrarMenuLogueado();
    }

    public void mostrarMenuLogueado() {
        int opcion;
        if (controlador.obtenerLogueado().getId_usuario() != 0) {
            do {
                System.out.println("1. Agregar Usuario");
                System.out.println("2. Listar usuarios registrados");
                System.out.println("3. Buscar usuario por dni o email");
                System.out.println("4. Modificar usuario por su id");
                System.out.println("0. Salir");
                System.out.print("Seleccione una opción: ");
                opcion = scanner.nextInt();
                scanner.nextLine();

                switch (opcion) {
                    case 1 -> agregarUsuario();
                    case 2 -> verTodosLosUsuarios();
                    case 3 -> verUsuario();
                    case 4 -> modificarUsuario();
                    case 0 -> System.out.println("Saliendo...");

                    default -> System.out.println("Opción no válida. Intente de nuevo.");
                }
            } while (opcion != 0);
        }
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

        controlador.agregar(nombre, apellido, dni, email);
        System.out.println("Usuario agregado exitosamente.");
    }

    private void iniciarSesion() {
        System.out.print("Usuario: ");
        String username = scanner.nextLine();
        System.out.print("Contraseña: ");
        String pass = scanner.nextLine();

        try {
            controlador.iniciarSesion(username,pass);
            System.out.println("Hola " + controlador.obtenerLogueado().getNombre());
        } catch (CredentialException e) {
            System.out.println(e.getMessage());
        }

    }

    private void verTodosLosUsuarios() {
        try {
            for (Usuario usuario: controlador.obtenerTodos()) {
                System.out.println("\n" + usuario + "\n");
            }
        } catch (NoAutorizadoException e) {
            System.out.println(e.getMessage());
        }
    }

    private void verUsuario() {
        String dni = "";
        String email = "";

        System.out.print("Documento: ");
        dni = scanner.nextLine();

        if (dni.isBlank()) {
            System.out.print("Email: ");
            email = scanner.nextLine();
        }

        try {
            System.out.println(controlador.buscar(dni,email));
        } catch (NoAutorizadoException | NoEncontradoException e) {
            System.out.println(e.getMessage());
        }
    }

    private void modificarUsuario() {
        try {
            int opcion;
            String parametro = "";

            System.out.print("Usuario a modificar (id): ");
            int id_usuario = Integer.parseInt(scanner.nextLine());

            System.out.println("Parametro a modificar: ");
            System.out.println("1. Nombre");
            System.out.println("2. Apellido");
            System.out.println("3. Documento");
            System.out.println("4. Email");
            System.out.println("5. Usuario");
            System.out.println("6. Clave");
            System.out.println("7. Permiso");
            System.out.print("Seleccione una opción: ");

            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1 -> parametro = "nombre";
                case 2 -> parametro = "apellido";
                case 3 -> parametro = "dni";
                case 4 -> parametro = "email";
                case 5 -> parametro = "username";
                case 6 -> parametro = "pass";
                case 7 -> parametro = "permiso";

                default -> System.out.println("Opción no válida. Intente de nuevo.");
            }

            System.out.println("Nuevo valor: ");
            String valor = scanner.nextLine();

            controlador.modificar(parametro,valor,id_usuario);
        } catch (NoAutorizadoException e) {
            System.out.println(e.getMessage());
        }
    }
}