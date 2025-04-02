package Ui.Components;

import Enums.InvoiceType;
import Model.Implementations.PayPalPayment;
import Repositories.Implementations.PaymentsRepository;

import java.util.Scanner;

public class AddPayPalPaymentMenu {
    public static void addPayPalPayment(Scanner teclado, PaymentsRepository repository, float amount, String dni, InvoiceType invoiceType) {
        System.out.println("Ingrese el sitio web donde se quiere hacer el pago:");
        String website = teclado.nextLine();

        System.out.println("Ingrese el mail con el que realizara el pago:");
        String emailAddress = teclado.nextLine();

        repository.add(new PayPalPayment(amount,dni,invoiceType,website,emailAddress));
    }
}
