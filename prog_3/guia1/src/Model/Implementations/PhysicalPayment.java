package Model.Implementations;

import Enums.InvoiceType;

public class PhysicalPayment extends Payment{
    private int shopId;

    public PhysicalPayment(float amount, String dniNumber, InvoiceType invoiceType, int shopId) {
        super(amount, dniNumber, invoiceType);
        this.shopId = shopId;
    }

    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }

    @Override
    public String toString() {
        return super.toString() +
                "\nid tienda: " + shopId;
    }
}
