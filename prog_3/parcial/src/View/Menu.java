package View;

import Controller.DuenioController;
import Controller.Exception.DuenioNoExisteException;
import Controller.Exception.MascotaNoExisteException;
import Controller.Exception.TurnoNoExisteException;
import Controller.Exception.VeterinarioNoExisteException;
import Controller.MascotaController;
import Controller.TurnoController;
import Controller.VeterinarioController;
import Model.Implementation.Mascota.Mascota;
import Model.Implementation.Turno.EstadoTurno;
import Model.Implementation.Veterinario.EspecialidadVeterinario;

import java.sql.Timestamp;
import java.util.Scanner;

public class Menu {
    private final Scanner scanner;
    private final DuenioController duenioController;
    private final MascotaController mascotaController;
    private final VeterinarioController veterinarioController;
    private final TurnoController turnoController;

    public Menu() {
        this.scanner = new Scanner(System.in);
        this.duenioController = new DuenioController();
        this.mascotaController = new MascotaController(duenioController);
        this.veterinarioController = new VeterinarioController();
        this.turnoController = new TurnoController(mascotaController,veterinarioController);
    }

    public void menu() {
        int opcion;
        do {
            System.out.println("--- SISTEMA DE GESTION DE TURNOS VETERINARIOS ---");
            System.out.println("1. Duenios");
            System.out.println("2. Mascotas");
            System.out.println("3. Veterinarios");
            System.out.println("4. Turnos");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1 -> menuDuenios(duenioController);
                case 2 -> menuMascotas(mascotaController);
                case 3 -> menuVeterinarios(veterinarioController);
                case 4 -> menuTurnos(turnoController);
                case 0 -> System.out.println("Saliendo...");

                default -> System.out.println("Por favor ingrese una opcion valida");
            }
        } while (opcion != 0);
    }

    public void menuDuenios(DuenioController duenioController) {
        int opcion;
        do {
            System.out.println("--- GESTION DE DUENIOS ---");
            System.out.println("1. Registrar duenio");
            System.out.println("2. Ver un duenio por ID");
            System.out.println("3. Ver todos los duenios");
            System.out.println("4. Modificar un duenio por ID");
            System.out.println("5. Eliminar un duenio por ID");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    String nombreCompletoAgregar;
                    String dniAgregar;
                    String telefonoAgregar;

                    System.out.println("--- REGISTRO DUENIO ---");

                    System.out.println("Nombre del duenio: ");
                    nombreCompletoAgregar = scanner.nextLine();

                    System.out.println("DNI del duenio: ");
                    dniAgregar = scanner.nextLine();

                    System.out.println("Telefono del duenio: ");
                    telefonoAgregar = scanner.nextLine();

                    duenioController.agregar(nombreCompletoAgregar,dniAgregar,telefonoAgregar);
                    break;
                case 2:
                    try {
                        int idDuenioVer;

                        System.out.println("--- MUESTRA DUENIO ---");

                        System.out.println("ID del duenio a mostrar: ");
                        idDuenioVer = Integer.parseInt(scanner.nextLine());

                        System.out.println(duenioController.obtenerUno(idDuenioVer));
                    } catch (DuenioNoExisteException e) {
                        System.out.println("Error al mostrar duenio: " + e.getMessage());
                    }
                    break;
                case 3:
                    System.out.println("--- MUESTRA TODOS DUENIOS ---");
                    System.out.println(duenioController.obtenerTodos());
                    break;
                case 4:
                    int idDuenioModificar;
                    String nombreCompletoModificar;
                    String dniModificar;
                    String telefonoModificar;

                    System.out.println("--- MODIFICACION DUENIO ---");

                    System.out.println("ID duenio a modificar: ");
                    idDuenioModificar = Integer.parseInt(scanner.nextLine());

                    System.out.println("Nombre del duenio: ");
                    nombreCompletoModificar = scanner.nextLine();

                    System.out.println("DNI del duenio: ");
                    dniModificar = scanner.nextLine();

                    System.out.println("Telefono del duenio: ");
                    telefonoModificar = scanner.nextLine();

                    duenioController.modificar(idDuenioModificar,nombreCompletoModificar,dniModificar,telefonoModificar);
                    break;
                case 5:
                    int idDuenioEliminar;

                    System.out.println("--- ELIMINACION DUENIO ---");

                    System.out.println("Ingrese el ID del duenio a eliminar: ");
                    idDuenioEliminar = Integer.parseInt(scanner.nextLine());

                    duenioController.eliminar(idDuenioEliminar);
                    break;
                case 0:
                    System.out.println("Volviendo al menu principal...");
                    break;
                default:
                    System.out.println("Por favor ingrese una opcion valida");
                    break;
            }
        } while (opcion != 0);
    }

    public void menuMascotas(MascotaController mascotaController) {
        int opcion;
        do {
            System.out.println("--- GESTION DE MASCOTAS ---");
            System.out.println("1. Registrar mascota");
            System.out.println("2. Ver una mascota por ID");
            System.out.println("3. Ver todas las mascotas");
            System.out.println("4. Modificar una mascota por ID");
            System.out.println("5. Eliminar una mascota por ID");
            System.out.println("6. Mostrar mascotas de un duenio por su ID");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    String nombreAgregar;
                    String especieAgregar;
                    String razaAgregar;
                    int edadAgregar;
                    int idDuenioAgregar;

                    System.out.println("--- REGISTRO MASCOTA ---");

                    System.out.println("Nombre de la mascota: ");
                    nombreAgregar = scanner.nextLine();

                    System.out.println("Especie: ");
                    especieAgregar = scanner.nextLine();

                    System.out.println("Raza: ");
                    razaAgregar = scanner.nextLine();

                    System.out.println("Edad: ");
                    edadAgregar = Integer.parseInt(scanner.nextLine());

                    System.out.println("ID del duenio de la mascota: ");
                    idDuenioAgregar = Integer.parseInt(scanner.nextLine());

                    mascotaController.agregar(nombreAgregar,especieAgregar,razaAgregar,edadAgregar,idDuenioAgregar);
                    break;
                case 2:
                    try {
                        int idMascotaVer;

                        System.out.println("--- MUESTRA MASCOTA ---");

                        System.out.println("ID de la mascota a mostrar: ");
                        idMascotaVer = Integer.parseInt(scanner.nextLine());

                        System.out.println(mascotaController.obtenerUno(idMascotaVer));
                    } catch (MascotaNoExisteException e) {
                        System.out.println("Error al mostrar mascota: " + e.getMessage());
                    }
                    break;
                case 3:
                    System.out.println("--- MUESTRA TODAS MASCOTAS ---");
                    System.out.println(mascotaController.obtenerTodos());
                    break;
                case 4:
                    int idMascotaModificar;
                    String nombreModificar;
                    String especieModificar;
                    String razaModificar;
                    int edadModificar;
                    int idDuenioModificar;

                    System.out.println("--- MODIFICACION MASCOTA ---");

                    System.out.println("ID de la mascota a modificar: ");
                    idMascotaModificar = Integer.parseInt(scanner.nextLine());

                    System.out.println("Nombre de la mascota: ");
                    nombreModificar = scanner.nextLine();

                    System.out.println("Especie: ");
                    especieModificar = scanner.nextLine();

                    System.out.println("Raza: ");
                    razaModificar = scanner.nextLine();

                    System.out.println("Edad: ");
                    edadModificar = Integer.parseInt(scanner.nextLine());

                    System.out.println("ID del duenio de la mascota: ");
                    idDuenioModificar = Integer.parseInt(scanner.nextLine());

                    mascotaController.modificar(idMascotaModificar,nombreModificar,especieModificar,razaModificar,edadModificar,idDuenioModificar);
                    break;
                case 5:
                    int idMascotaEliminar;

                    System.out.println("--- ELIMINACION MASCOTA ---");

                    System.out.println("Ingrese el ID de la mascota a eliminar: ");
                    idMascotaEliminar = Integer.parseInt(scanner.nextLine());

                    mascotaController.eliminar(idMascotaEliminar);
                    break;
                case 6:
                    int idDuenioMascotaMostrar;

                    System.out.println("--- MUESTRA MASCOTAS POR ID DUENIO ---");

                    System.out.println("Ingrese el ID del duenio de las mascotas que desea ver: ");
                    idDuenioMascotaMostrar = Integer.parseInt(scanner.next());

                    System.out.println(mascotaController.verPorDuenio(idDuenioMascotaMostrar));
                    break;
                case 0:
                    System.out.println("Volviendo al menu principal...");
                    break;
                default:
                    System.out.println("Por favor ingrese una opcion valida");
                    break;
            }
        } while (opcion != 0);
    }

    public void menuVeterinarios(VeterinarioController veterinarioController) {
        int opcion;
        do {
            System.out.println("--- GESTION DE VETERINARIOS ---");
            System.out.println("1. Registrar veterinario");
            System.out.println("2. Ver un veterinario por ID");
            System.out.println("3. Ver todos los veterinarios");
            System.out.println("4. Modificar un veterinario por ID");
            System.out.println("5. Eliminar un veterinario por ID");
            System.out.println("6. Ver veterinarios por especialidad");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    String nombreAgregar;
                    String matriculaAgregar;
                    EspecialidadVeterinario especialidadAgregar;

                    System.out.println("--- REGISTRO VETERINARIO ---");

                    System.out.println("Nombre: ");
                    nombreAgregar = scanner.nextLine();

                    System.out.println("Matricula: ");
                    matriculaAgregar = scanner.nextLine();

                    System.out.println("Especialidad: ");
                    System.out.println("Por ejemplo: neurologia,cirugia,kinesiologia");
                    especialidadAgregar = EspecialidadVeterinario.valueOf(scanner.nextLine().toUpperCase());

                    veterinarioController.agregar(nombreAgregar,matriculaAgregar,especialidadAgregar);
                    break;
                case 2:
                    try {
                        int idVeterinarioVer;

                        System.out.println("--- MUESTRA VETERINARIO ---");

                        System.out.println("ID del veterinario a mostrar: ");
                        idVeterinarioVer = Integer.parseInt(scanner.nextLine());

                        System.out.println(veterinarioController.obtenerUno(idVeterinarioVer));
                    } catch (VeterinarioNoExisteException e) {
                        System.out.println("Error al mostrar veterinario: " + e.getMessage());
                    }
                    break;
                case 3:
                    System.out.println("--- MUESTRA TODOS VETERINARIOS ---");
                    System.out.println(veterinarioController.obtenerTodos());
                    break;
                case 4:
                    int idVeterinarioModificar;
                    String nombreModificar;
                    String matriculaModificar;
                    EspecialidadVeterinario especialidadModificar;

                    System.out.println("--- MODIFICACION VETERINARIO ---");

                    System.out.println("ID del veterinario a modificar: ");
                    idVeterinarioModificar = Integer.parseInt(scanner.nextLine());

                    System.out.println("Nombre: ");
                    nombreModificar = scanner.nextLine();

                    System.out.println("Matricula: ");
                    matriculaModificar = scanner.nextLine();

                    System.out.println("Especialidad: ");
                    System.out.println("Por ejemplo: neurologia,cirugia,kinesiologia");
                    especialidadModificar = EspecialidadVeterinario.valueOf(scanner.nextLine().toUpperCase());

                    veterinarioController.modificar(idVeterinarioModificar,nombreModificar,matriculaModificar,especialidadModificar);
                    break;
                case 5:
                    int idVeterinarioEliminar;

                    System.out.println("--- ELIMINACION VETERINARIO ---");

                    System.out.println("Ingrese el ID del veterinario a eliminar: ");
                    idVeterinarioEliminar = Integer.parseInt(scanner.nextLine());

                    veterinarioController.eliminar(idVeterinarioEliminar);
                    break;
                case 6:
                    EspecialidadVeterinario especialidadVeterinarioVer;

                    System.out.println("--- MUESTRA VETERINARIOS POR ESPECIALIDAD ---");

                    System.out.println("Ingrese la especialidad de los veterinarios que desea ver: ");
                    System.out.println("Por ejemplo: neurologia,cirugia,kinesiologia");
                    especialidadVeterinarioVer = EspecialidadVeterinario.valueOf(scanner.nextLine().toUpperCase());

                    System.out.println(veterinarioController.verPorEspecialidad(especialidadVeterinarioVer));
                    break;
                case 0:
                    System.out.println("Volviendo al menu principal...");
                    break;
                default:
                    System.out.println("Por favor ingrese una opcion valida");
                    break;
            }
        } while (opcion != 0);
    }

    public void menuTurnos(TurnoController turnoController) {
        int opcion;
        do {
            System.out.println("--- GESTION DE TURNOS ---");
            System.out.println("1. Registrar turno");
            System.out.println("2. Ver un turno por ID");
            System.out.println("3. Ver todos los turnos");
            System.out.println("4. Modificar un turno por ID");
            System.out.println("5. Eliminar un turno por ID");
            System.out.println("6. Finalizar un turno por ID");
            System.out.println("7. Cancelar un turno por ID");
            System.out.println("8. Ver turnos por estado");
            System.out.println("9. Ver turnos por mascota");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    int idMascotaAgregar;
                    int idVeterinarioAgregar;
                    Timestamp fechaHoraAgregar;

                    System.out.println("--- REGISTRO TURNO ---");

                    System.out.println("ID de la mascota: ");
                    idMascotaAgregar = Integer.parseInt(scanner.nextLine());

                    System.out.println("ID del veterinario: ");
                    idVeterinarioAgregar = Integer.parseInt(scanner.nextLine());

                    System.out.println("Fecha y hora del turno: ");
                    System.out.println("Por ejemplo:");
                    System.out.println("1969-12-31 23:59:59.9");
                    fechaHoraAgregar = Timestamp.valueOf(scanner.nextLine());

                    turnoController.agregar(idMascotaAgregar,idVeterinarioAgregar,fechaHoraAgregar);
                    break;
                case 2:
                    try {
                        int idTurnoVer;

                        System.out.println("--- MUESTRA TURNO ---");

                        System.out.println("ID del turno a mostrar: ");
                        idTurnoVer = Integer.parseInt(scanner.nextLine());

                        System.out.println(turnoController.obtenerUno(idTurnoVer));
                    } catch (TurnoNoExisteException e) {
                        System.out.println("Error al mostrar turno: " + e.getMessage());
                    }
                    break;
                case 3:
                    System.out.println("--- MUESTRA TODOS TURNOS ---");
                    System.out.println(turnoController.obtenerTodos());
                    break;
                case 4:
                    int idTurnoModificar;
                    int idMascotaModificar;
                    int idVeterinarioModificar;
                    Timestamp fechaHoraModificar;
                    EstadoTurno estadoModificar;

                    System.out.println("--- MODIFICACION TURNO ---");

                    System.out.println("ID del turno a modificar: ");
                    idTurnoModificar = Integer.parseInt(scanner.nextLine());

                    System.out.println("ID de la mascota: ");
                    idMascotaModificar = Integer.parseInt(scanner.nextLine());

                    System.out.println("ID del veterinario: ");
                    idVeterinarioModificar = Integer.parseInt(scanner.nextLine());

                    System.out.println("Fecha y hora del turno: ");
                    System.out.println("Por ejemplo:");
                    System.out.println("1969-12-31 23:59:59.9");
                    fechaHoraModificar = Timestamp.valueOf(scanner.nextLine());

                    System.out.println("Estado del turno: ");
                    System.out.println("Pendiente, Realizado, Cancelado");
                    estadoModificar = EstadoTurno.valueOf(scanner.nextLine().toUpperCase());

                    turnoController.modificar(idTurnoModificar, idMascotaModificar,idVeterinarioModificar,fechaHoraModificar,estadoModificar);
                    break;
                case 5:
                    int idTurnoEliminar;

                    System.out.println("--- ELIMINACION TURNO ---");

                    System.out.println("Ingrese el ID del turno a eliminar: ");
                    idTurnoEliminar = Integer.parseInt(scanner.nextLine());

                    turnoController.eliminar(idTurnoEliminar);
                    break;
                case 6:
                    int idTurnoFinalizar;

                    System.out.println("--- FINALIZACION TURNO ----");

                    System.out.println("Ingrese el ID del turno a finalizar: ");
                    idTurnoFinalizar = Integer.parseInt(scanner.nextLine());

                    turnoController.finalizarTurno(idTurnoFinalizar);
                    break;
                case 7:
                    int idTurnoCancelar;

                    System.out.println("--- CANCELACION TURNO ----");

                    System.out.println("Ingrese el ID del turno a cancelar: ");
                    idTurnoCancelar = Integer.parseInt(scanner.nextLine());

                    turnoController.cancelarTurno(idTurnoCancelar);
                    break;
                case 8:
                    EstadoTurno estadoTurnoVer;

                    System.out.println("--- MUESTRA TURNOS POR ESTADO ---");

                    System.out.println("Ingrese el estado de los turnos que desea ver: ");
                    System.out.println("Pendiente, Realizado, Cancelado");
                    estadoTurnoVer = EstadoTurno.valueOf(scanner.nextLine().toUpperCase());

                    System.out.println(turnoController.verPorEstado(estadoTurnoVer));
                    break;
                case 9:
                    try {
                        int idMascotaTurnosVer;
                        Mascota mascota;

                        System.out.println("--- MUESTRA TURNOS POR ID MASCOTA ---");

                        System.out.println("Ingrese el ID de la mascota de la cual desea ver sus turnos: ");
                        idMascotaTurnosVer = Integer.parseInt(scanner.nextLine());
                        mascota = mascotaController.obtenerUno(idMascotaTurnosVer);
                        System.out.println(mascotaController.verInfoDuenioMascota(mascota.getIdDuenio()));

                        System.out.println(turnoController.verPorMascota(idMascotaTurnosVer));
                    } catch (MascotaNoExisteException e) {
                        System.out.println("Error obteniendo mascota: " + e.getMessage());
                    }
                    break;
                case 0:
                    System.out.println("Volviendo al menu principal...");
                    break;
                default:
                    System.out.println("Por favor ingrese una opcion valida");
                    break;
            }
        } while (opcion != 0);
    }
}
