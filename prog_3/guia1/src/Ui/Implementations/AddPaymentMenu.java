package Ui.Implementations;

import Enums.InvoiceType;
import Exceptions.ExpiredCardException;
import Exceptions.InvalidAmountException;
import Exceptions.InvalidCardException;
import Exceptions.InvalidDniException;
import Repositories.Implementations.PaymentsRepository;

import java.util.Scanner;

import static Ui.Components.AddCardPaymentMenu.addCardPayment;
import static Ui.Components.AddCashPaymentMenu.addCashPayment;
import static Ui.Components.AddCryptoPaymentMenu.addCryptoPayment;
import static Ui.Components.AddPayPalPaymentMenu.addPayPalPayment;
import static Verifications.Verifications.*;

public class AddPaymentMenu {
    public static void addPaymentMenu(Scanner teclado, PaymentsRepository repository) throws InvalidAmountException, ExpiredCardException, InvalidCardException, InvalidDniException {
        int opcion;

        System.out.println("Ingrese el monto a pagar:");
        float amount = Float.parseFloat(teclado.nextLine());
        if (!verifyPaymentAmount(amount)) throw new InvalidAmountException("El monto debe ser mayor a 0!");

        System.out.println("Ingrese el numero de dni:");
        String dni = teclado.nextLine();
        if (!verifyNumericData(dni,8)) throw new InvalidDniException("El dni es invalido!");

        System.out.println("Ingrese el tipo de factura a emitir (a,b,c,x):");
        InvoiceType invoiceType = InvoiceType.valueOf(teclado.nextLine().toUpperCase());

        System.out.println("Elija un medio de pago:\n");
        System.out.println("1. Efectivo");
        System.out.println("2. Tarjeta de credito");
        System.out.println("3. PayPal");
        System.out.println("4. Crypto");
        System.out.println("\nOpcion elegida: ");
        opcion = Integer.parseInt(teclado.nextLine());

        switch (opcion) {
            case 1:
                addCashPayment(teclado, repository, amount, dni, invoiceType);
                break;
            case 2:
                addCardPayment(teclado, repository, amount, dni, invoiceType);
                break;
            case 3:
                addPayPalPayment(teclado, repository, amount, dni, invoiceType);
                break;
            case 4:
                addCryptoPayment(teclado, repository, amount, dni, invoiceType);
                break;
            default:
                System.out.println("Ingrese una opcion valida");
                break;
        }
    }
}
