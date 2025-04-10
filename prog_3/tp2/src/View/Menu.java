package View;

import Controller.AlumnoController;
import Controller.DireccionController;
import Model.Alumno;
import Model.Direccion;

import java.util.List;
import java.util.Scanner;

public class Menu {
    private AlumnoController alumnoController;
    private DireccionController direccionController;
    private Scanner scanner;

    public Menu() {
        alumnoController = new AlumnoController();
        direccionController = new DireccionController();
        scanner = new Scanner(System.in);
    }

    public void mostrarMenu() {
        int opcion;
        do {
            System.out.println("1. Agregar Alumno");
            System.out.println("2. Eliminar Alumno");
            System.out.println("3. Ver Alumnos");
            System.out.println("4. Agregar Dirección");
            System.out.println("5. Eliminar Dirección");
            System.out.println("6. Ver Direcciones");
            System.out.println("7. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (opcion) {
                case 1:
                    agregarAlumno();
                    break;
                case 2:
                    eliminarAlumno();
                    break;
                case 3:
                    verAlumnos();
                    break;
                case 4:
                    agregarDireccion();
                    break;
                case 5:
                    eliminarDireccion();
                    break;
                case 6:
                    verDirecciones();
                    break;
                case 7:
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
            }
        } while (opcion != 7);
    }

    private void agregarAlumno() {
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Apellido: ");
        String apellido = scanner.nextLine();
        System.out.print("Edad: ");
        int edad = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.print("Email: ");
        String email = scanner.nextLine();

        alumnoController.agregarAlumno(nombre, apellido, edad, email);
        System.out.println("Alumno agregado exitosamente.");
    }

    private void eliminarAlumno() {
        System.out.print("ID del Alumno a eliminar: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        alumnoController.eliminarAlumno(id);
        System.out.println("Alumno eliminado exitosamente.");
    }

    private void verAlumnos() {
        List<Alumno> alumnos = alumnoController.listarAlumnos();
        if (alumnos.isEmpty()) {
            System.out.println("No hay alumnos registrados.");
        } else {
            for (Alumno alumno : alumnos) {
                System.out.println("ID: " + alumno.getId() + ", Nombre: " + alumno.getNombre() + ", Apellido: " + alumno.getApellido() + ", Edad: " + alumno.getEdad() + ", Email: " + alumno.getEmail());
            }
        }
    }

    private void agregarDireccion() {
        System.out.print("Calle: ");
        String calle = scanner.nextLine();
        System.out.print("Altura: ");
        int altura = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.print("ID del Alumno: ");
        int alumnoId = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        direccionController.agregarDireccion(calle, altura, alumnoId);
        System.out.println("Dirección agregada exitosamente.");
    }

    private void eliminarDireccion() {
        System.out.print("ID de la Dirección a eliminar: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        direccionController.eliminarDireccion(id);
        System.out.println("Dirección eliminada exitosamente.");
    }

    private void verDirecciones() {
        List<Direccion> direcciones = direccionController.listarDirecciones();
        if (direcciones.isEmpty()) {
            System.out.println("No hay direcciones registradas.");
        } else {
            for (Direccion direccion : direcciones) {
                System.out.println("ID: " + direccion.getId() + ", Calle: " + direccion.getCalle() + ", Altura: " + direccion.getAltura() + ", ID Alumno: " + direccion.getAlumno_id());
            }
        }
    }
}