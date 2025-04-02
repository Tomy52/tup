package Model.Implementations;

import Enums.InvoiceType;

public class ElectronicPayment extends Payment{
    private String sourceWebsite;

    public ElectronicPayment(float amount, String dniNumber, InvoiceType invoiceType, String sourceWebsite) {
        super(amount, dniNumber, invoiceType);
        this.sourceWebsite = sourceWebsite;
    }

    public String getSourceWebsite() {
        return sourceWebsite;
    }

    public void setSourceWebsite(String sourceWebsite) {
        this.sourceWebsite = sourceWebsite;
    }

    @Override
    public String toString() {
        return super.toString() +
                "\nsitio web de referencia: " + sourceWebsite;
    }
}
