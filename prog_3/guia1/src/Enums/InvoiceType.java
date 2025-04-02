package Enums;

public enum InvoiceType {
    A("a"),
    B("b"),
    C("c"),
    X("x");

    final String type;

    InvoiceType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
