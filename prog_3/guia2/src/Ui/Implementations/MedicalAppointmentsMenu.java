package Ui.Implementations;

import Model.Implementations.Patient;
import Repository.Implementations.MedicalAppointments;
import Ui.Interfaces.Menu;

import java.util.PriorityQueue;
import java.util.Scanner;

public class MedicalAppointmentsMenu implements Menu {
    private final MedicalAppointments medicalAppointments;
    private final Scanner scanner;

    public MedicalAppointmentsMenu() {
        PriorityQueue<Patient> appointmentsQueue = new PriorityQueue<>((p1, p2) -> Integer.compare(p2.getPriorityLevel(), p1.getPriorityLevel()));
        this.medicalAppointments = new MedicalAppointments(appointmentsQueue);
        this.scanner = new Scanner(System.in);
    }

    @Override
    public void displayMenu() {
        int choice;
        do {
            System.out.println("Menu de Medical Appointments:");
            System.out.println("1. Agregar un paciente");
            System.out.println("2. Atender al paciente con mayor prioridad");
            System.out.println("3. Ver al siguiente paciente en la fila");
            System.out.println("4. Salir");
            System.out.print("Seleccione una opcion: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    addPatient();
                    break;
                case 2:
                    checkPatient();
                    break;
                case 3:
                    peekPatient();
                    break;
                case 4:
                    System.out.println("Saliendo del menu...");
                    break;
                default:
                    System.out.println("Opcion invalida. Intente nuevamente.");
            }
        } while (choice != 4);
    }

    private void addPatient() {
        System.out.print("Ingrese el nombre del paciente: ");
        String patientName = scanner.nextLine();
        System.out.print("Ingrese el nivel de prioridad del paciente: ");
        int priorityLevel = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        Patient patient = new Patient(patientName, priorityLevel);
        medicalAppointments.add(patient);
        System.out.println("Paciente agregado a la lista de citas.");
    }

    private void checkPatient() {
        Patient patient = medicalAppointments.peek();
        if (patient != null) {
            medicalAppointments.remove(patient);
            System.out.println("Atendiendo al paciente: " + patient.getName());
        } else {
            System.out.println("No hay pacientes en la lista de citas.");
        }
    }

    private void peekPatient() {
        Patient patient = medicalAppointments.peek();
        if (patient != null) {
            System.out.println("El siguiente paciente en la fila es: " + patient.getName());
        } else {
            System.out.println("No hay pacientes en la lista de citas.");
        }
    }
}