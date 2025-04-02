package Model.Implementations;

import Enums.InvoiceType;

public class CashPayment extends PhysicalPayment{
    private float changeAmount;

    public CashPayment(float amount, String dniNumber, InvoiceType invoiceType, int shopId, float changeAmount) {
        super(amount, dniNumber, invoiceType, shopId);
        this.changeAmount = changeAmount;
    }

    public float getChangeAmount() {
        return changeAmount;
    }

    public void setChangeAmount(float changeAmount) {
        this.changeAmount = changeAmount;
    }

    @Override
    public String toString() {
        return super.toString() +
                "\nmonto vuelto: " + changeAmount;
    }
}
