package Model.Implementations;

import Enums.InvoiceType;

public abstract class Payment {
    int transactionId;
    int currentTransactionCount = 0;
    float amount;
    String dniNumber;
    InvoiceType invoiceType;

    public Payment(float amount, String dniNumber, InvoiceType invoiceType) {
        currentTransactionCount++;
        transactionId = currentTransactionCount;
        this.amount = amount;
        this.dniNumber = dniNumber;
        this.invoiceType = invoiceType;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public String getDniNumber() {
        return dniNumber;
    }

    public void setDniNumber(String dniNumber) {
        this.dniNumber = dniNumber;
    }

    public InvoiceType getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(InvoiceType invoiceType) {
        this.invoiceType = invoiceType;
    }

    @Override
    public String toString() {
        return "Pago id: " + transactionId +
                "\ntipo de pago: " + getClass() +
                "\nmonto: " + amount +
                "\nnumero de dni: " + dniNumber +
                "\ntipo de factura: " + invoiceType;
    }
}
