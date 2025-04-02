package Model.Implementations;

import Enums.InvoiceType;

public class CryptoPayment extends ElectronicPayment {
    private String walletAddress;

    public CryptoPayment(float amount, String dniNumber, InvoiceType invoiceType, String website, String walletAddress) {
        super(amount, dniNumber, invoiceType, website);
        this.walletAddress = walletAddress;
    }

    public String getWalletAddress() {
        return walletAddress;
    }

    public void setWalletAddress(String walletAddress) {
        this.walletAddress = walletAddress;
    }

    @Override
    public String toString() {
        return super.toString() +
                "\nbilletera de referencia: " + walletAddress;
    }
}
