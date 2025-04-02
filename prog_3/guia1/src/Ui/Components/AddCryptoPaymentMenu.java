package Ui.Components;

import Enums.InvoiceType;
import Model.Implementations.PayPalPayment;
import Repositories.Implementations.PaymentsRepository;

import java.util.Scanner;

public class AddCryptoPaymentMenu {
    public static void addCryptoPayment(Scanner teclado, PaymentsRepository repository, float amount, String dni, InvoiceType invoiceType) {
        System.out.println("Ingrese el sitio web donde se quiere hacer el pago:");
        String website = teclado.nextLine();

        System.out.println("Ingrese la direccion de la billetera con la que realizara el pago:");
        String walletAddress = teclado.nextLine();

        repository.add(new PayPalPayment(amount,dni,invoiceType,website,walletAddress));
    }
}
