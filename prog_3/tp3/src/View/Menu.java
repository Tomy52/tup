package View;

import Controller.Exception.NoAutorizadoException;
import Controller.Exception.NoEncontradoException;
import Controller.Exception.NoExisteException;
import Controller.Implementation.CuentaController;
import Controller.Implementation.UsuarioController;
import Model.Implementation.Cuenta.Cuenta;
import Model.Implementation.Usuario.Usuario;

import javax.security.auth.login.CredentialException;
import java.util.Scanner;

public class Menu {
    private final UsuarioController controladorUsuarios;
    private final CuentaController controladorCuentas;
    private final Scanner scanner;

    public Menu() {
        controladorUsuarios = new UsuarioController();
        controladorCuentas = new CuentaController(controladorUsuarios);
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
        } while (opcion != 0 && controladorUsuarios.obtenerLogueado().getId_usuario() == 0);
        mostrarMenuLogueado();
    }

    public void mostrarMenuLogueado() {
        int opcion;
        if (controladorUsuarios.obtenerLogueado().getId_usuario() != 0) {
            do {
                System.out.println("1. Agregar Usuario");
                System.out.println("2. Listar usuarios registrados");
                System.out.println("3. Buscar usuario por dni o email");
                System.out.println("4. Modificar usuario por su id");
                System.out.println("5. Eliminar usuario por su id");
                System.out.println("6. Agregar/abrir una cuenta");
                System.out.println("7. Ver todas las cuentas");
                System.out.println("8. Ver las cuentas de un usuario");
                System.out.println("9. Ver el saldo total de un usuario");
                System.out.println("10. Depositar en una cuenta");
                System.out.println("11. Transferir a otra cuenta");
                System.out.println("12. Ver usuarios totales por permiso");
                System.out.println("13. Ver cuentas totales por tipo");
                System.out.println("14. Ver usuario con mayor saldo");
                System.out.println("15. Ver usuarios ordenados por saldo");
                System.out.println("16. Buscar usuarios por id");
                System.out.println("17. Cual es mi id?");
                System.out.println("0. Salir");
                System.out.print("Seleccione una opción: ");
                opcion = scanner.nextInt();
                scanner.nextLine();

                switch (opcion) {
                    case 1 -> agregarUsuario();
                    case 2 -> verTodosLosUsuarios();
                    case 3 -> verUsuario();
                    case 4 -> modificarUsuario();
                    case 5 -> eliminarUsuario();
                    case 6 -> agregarCuenta();
                    case 7 -> verTodasLasCuentas();
                    case 8 -> verCuentasDeUsuario();
                    case 9 -> verSaldoTotalDeUsuario();
                    case 10 -> depositar();
                    case 11 -> transferir();
                    case 12 -> verUsuariosTotalesPorPermiso();
                    case 13 -> verCuentasTotalesPorTipo();
                    case 14 -> verUsuarioMasRico();
                    case 15 -> verUsuariosPorRiqueza();
                    case 16 -> verUsuarioPorId();
                    case 17 -> cualEsMiId();
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

        controladorUsuarios.agregar(nombre, apellido, dni, email);
        System.out.println("Usuario agregado exitosamente.");
    }

    private void iniciarSesion() {
        System.out.print("Usuario: ");
        String username = scanner.nextLine();
        System.out.print("Contraseña: ");
        String pass = scanner.nextLine();

        try {
            controladorUsuarios.iniciarSesion(username,pass);
            System.out.println("Hola " + controladorUsuarios.obtenerLogueado().getNombre());
        } catch (CredentialException e) {
            System.out.println(e.getMessage());
        }

    }

    private void verTodosLosUsuarios() {
        try {
            for (Usuario usuario: controladorUsuarios.obtenerTodos()) {
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
            System.out.println(controladorUsuarios.buscar(dni,email));
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

            controladorUsuarios.modificar(parametro,valor,id_usuario);
            System.out.println("Usuario modificado exitosamente.");
        } catch (NoAutorizadoException e) {
            System.out.println(e.getMessage());
        }
    }

    private void eliminarUsuario() {
        System.out.print("Id usuario: ");
        int id = Integer.parseInt(scanner.nextLine());

        try {
            controladorUsuarios.eliminar(id);
            System.out.println("Usuario eliminado exitosamente.");
        } catch (NoAutorizadoException e) {
            System.out.println(e.getMessage());
        }
    }

    private void agregarCuenta() {
        int opcion;
        String tipoCuenta = "";

        System.out.print("Id usuario titular: ");
        int id_usuario = Integer.parseInt(scanner.nextLine());

        System.out.println("Tipo cuenta (caja de ahorro o cuenta corriente): ");
        System.out.println("1. Caja de ahorro");
        System.out.println("2. Cuenta corriente");
        System.out.print("Seleccione una opción: ");

        opcion = scanner.nextInt();
        scanner.nextLine();

        switch (opcion) {
            case 1 -> tipoCuenta = "CAJA_AHORRO";
            case 2 -> tipoCuenta = "CUENTA_CORRIENTE";
            default -> System.out.println("Opción no válida. Intente de nuevo.");
        }

        try {
            controladorCuentas.agregar(id_usuario,tipoCuenta);
            System.out.println("Cuenta agregada exitosamente.");
        } catch (NoAutorizadoException e) {
            System.out.println(e.getMessage());
        }

    }

    private void verTodasLasCuentas() {
        try {
            for (Cuenta cuenta: controladorCuentas.obtenerTodos()) {
                System.out.println("\n" + cuenta + "\n");
            }
        } catch (NoAutorizadoException e) {
            System.out.println(e.getMessage());
        }
    }

    private void verCuentasDeUsuario() {
        System.out.print("Id usuario titular: ");
        int id_usuario = Integer.parseInt(scanner.nextLine());

        try {
            for (Cuenta cuenta: controladorCuentas.obtenerDeUsuario(id_usuario)) {
                System.out.println("\n" + cuenta + "\n");
            }
        } catch (NoAutorizadoException e) {
            System.out.println(e.getMessage());
        }
    }

    private void verSaldoTotalDeUsuario() {
        System.out.print("Id usuario titular: ");
        int id_usuario = Integer.parseInt(scanner.nextLine());

        try {
            System.out.println("Saldo total: " + controladorCuentas.obtenerSaldoDeUsuario(id_usuario));
        } catch (NoAutorizadoException e) {
            System.out.println(e.getMessage());
        }
    }

    private void depositar() {
        System.out.print("Id cuenta: ");
        int id_cuenta = Integer.parseInt(scanner.nextLine());

        System.out.print("Monto a depositar: ");
        double monto = Double.parseDouble(scanner.nextLine());

        try {
            controladorCuentas.depositar(id_cuenta,monto);
            System.out.println("Deposito exitoso.");
        } catch (NoAutorizadoException | NoExisteException e) {
            System.out.println(e.getMessage());
        }
    }

    private void transferir() {
        System.out.print("Id cuenta origen: ");
        int id_cuentaOrigen = Integer.parseInt(scanner.nextLine());

        System.out.print("Id cuenta destino: ");
        int id_cuentaDestino = Integer.parseInt(scanner.nextLine());

        System.out.print("Monto a transferir: ");
        double monto = Double.parseDouble(scanner.nextLine());

        try {
            controladorCuentas.transferir(id_cuentaOrigen, id_cuentaDestino, monto);
            System.out.println("Transferencia exitosa.");
        } catch (NoAutorizadoException | NoExisteException e) {
            System.out.println(e.getMessage());
        }
    }

    private void verUsuariosTotalesPorPermiso() {
        try {
            System.out.println(controladorUsuarios.obtenerTotalPorPermiso());
        } catch (NoAutorizadoException e) {
            System.out.println(e.getMessage());
        }
    }

    private void verCuentasTotalesPorTipo() {
        try {
            System.out.println(controladorCuentas.obtenerTotalPorTipo());
        } catch (NoAutorizadoException e) {
            System.out.println(e.getMessage());
        }
    }

    private void verUsuarioMasRico() {
        try {
            System.out.println(controladorCuentas.obtenerUsuarioMasRico());
        } catch (NoAutorizadoException e) {
            System.out.println(e.getMessage());
        }
    }

    private void verUsuariosPorRiqueza() {
        try {
            System.out.println("Id's usuarios (ordenados por saldo): ");
            System.out.println(controladorCuentas.obtenerUsuariosPorRiqueza());
        } catch (NoAutorizadoException e) {
            System.out.println(e.getMessage());
        }
    }

    private void verUsuarioPorId() {
        System.out.print("Id usuario: ");
        int id_usuario = Integer.parseInt(scanner.nextLine());

        try {
            System.out.println(controladorUsuarios.obtener(id_usuario));
        } catch (NoAutorizadoException e) {
            System.out.println(e.getMessage());
        }
    }

    private void cualEsMiId() {
        System.out.println("Su id de usuario es: " + controladorUsuarios.obtenerIdPropio());
    }
}