package Repositories.Implementations;

import Model.Implementations.Payment;
import Repositories.Interfaces.Repository;

import java.util.ArrayList;

public class PaymentsRepository implements Repository<Payment> {
    private final ArrayList<Payment> payments = new ArrayList<>();

    @Override
    public boolean add(Payment payment){
        payments.add(payment);
        return true;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for(Payment payment: payments) {
            result.append(payment).append("\n");
        }
        return result.toString();
    }
}
