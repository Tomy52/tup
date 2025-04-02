package Ui.Components;

import Enums.InvoiceType;
import Exceptions.InvalidAmountException;
import Model.Implementations.CashPayment;
import Repositories.Implementations.PaymentsRepository;

import java.util.Scanner;

import static Verifications.Verifications.verifyChangeAmount;

public class AddCashPaymentMenu {
    public static void addCashPayment(Scanner teclado, PaymentsRepository repository, float amount, String dni, InvoiceType invoiceType) throws InvalidAmountException {
        System.out.println("Ingrese el id del negocio:");
        int shopId = Integer.parseInt(teclado.nextLine());

        System.out.println("Ingrese el vuelto:");
        float changeAmount = Float.parseFloat(teclado.nextLine());
        if (!verifyChangeAmount(amount)) throw new InvalidAmountException("El vuelto debe ser mayor o igual a 0!");

        repository.add(new CashPayment(amount,dni,invoiceType,shopId,changeAmount));
    }
}
