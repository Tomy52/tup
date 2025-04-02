package Model.Implementations;

import Enums.InvoiceType;

import java.time.YearMonth;

public class CreditCardPayment extends PhysicalPayment {
    private String cardNumber;
    private String cvv;
    private String holder;
    private YearMonth expirationDate;

    public CreditCardPayment(float amount, String dniNumber, InvoiceType invoiceType, int shopId,
                             String holder, String cardNumber, String cvv, YearMonth expirationDate) {
        super(amount, dniNumber, invoiceType, shopId);
        this.cardNumber = cardNumber;
        this.cvv = cvv;
        this.holder = holder;
        this.expirationDate = expirationDate;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public String getHolder() {
        return holder;
    }

    public void setHolder(String holder) {
        this.holder = holder;
    }

    public YearMonth getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(YearMonth expirationDate) {
        this.expirationDate = expirationDate;
    }

    @Override
    public String toString() {
        return super.toString() +
                "\ntitular de la tarjeta: " + holder +
                "\nnumero de tarjeta: " + cardNumber +
                "\ncodigo de seguridad: " + cvv +
                "\nfecha de vencimiento: " + expirationDate;
    }
}
