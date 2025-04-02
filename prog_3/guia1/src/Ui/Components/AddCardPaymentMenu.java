package Ui.Components;

import Enums.InvoiceType;
import Exceptions.ExpiredCardException;
import Exceptions.InvalidCardException;
import Model.Implementations.CreditCardPayment;
import Repositories.Implementations.PaymentsRepository;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import static Verifications.Verifications.verifyExpirationDate;
import static Verifications.Verifications.verifyNumericData;

public class AddCardPaymentMenu {
    public static void addCardPayment(Scanner teclado, PaymentsRepository repository, float amount, String dni, InvoiceType invoiceType) throws InvalidCardException, ExpiredCardException {
        System.out.println("Ingrese el id del negocio:");
        int shopId = Integer.parseInt(teclado.nextLine());

        System.out.println("Ingrese el nombre y apellido del titular de la tarjeta:");
        String cardholder = teclado.nextLine();

        System.out.println("Ingrese el numero de tarjeta (16 digitos):");
        String cardNumber = teclado.nextLine();
        if (!verifyNumericData(cardNumber,16)) throw new InvalidCardException("Numero de tarjeta invalido!");

        System.out.println("Ingrese el numero de seguridad (3 digitos):");
        String cvv = teclado.nextLine();
        if (!verifyNumericData(cvv,3)) throw new InvalidCardException("Numero de tarjeta invalido!");

        System.out.println("Ingrese mes y anio de vencimiento de la tarjeta (MM/AAAA):");
        YearMonth expirationDate = YearMonth.parse(teclado.nextLine(), DateTimeFormatter.ofPattern("MM/yyyy"));
        if (!verifyExpirationDate(expirationDate)) throw new ExpiredCardException("Tarjeta vencida!");

        repository.add(new CreditCardPayment(amount,dni,invoiceType,shopId, cardholder, cardNumber,cvv, expirationDate));
    }
}
