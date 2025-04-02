package Ui.Implementations;

import Repositories.Implementations.PaymentsRepository;
import Ui.Interfaces.Menu;

import java.util.Scanner;

import static Ui.Implementations.AddPaymentMenu.addPaymentMenu;

public class PaymentsMenu implements Menu<PaymentsRepository> {
    private final Scanner teclado = new Scanner(System.in);
    private final PaymentsRepository repository;

    public PaymentsMenu(PaymentsRepository repository) {
        this.repository = repository;
    }

    public void display() {
        int opcion;

        System.out.println("Bienvenido al sistema de gestion de pagos\n");
        do {
            System.out.println("Elija una opcion:\n");
            System.out.println("1. Agregar un pago");
            System.out.println("2. Ver pagos");
            System.out.println("0. Salir\n");
            System.out.println("\nOpcion elegida: ");
            opcion = Integer.parseInt(teclado.nextLine());

            switch (opcion) {
                case 1:
                    try {
                        addPaymentMenu(teclado,repository);
                        System.out.println("Pago agregado!");
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 2:
                    viewPayments();
                    break;
                case 0:
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("Ingrese una opcion valida");
                    break;
            }
        } while (opcion != 0);
    }

    //opc 2
    public void viewPayments() {
        System.out.println(repository);
    }
}
