package Verifications;

import java.time.YearMonth;

public class Verifications {
    public static boolean verifyPaymentAmount(float amount) {
        return !(amount <= 0);
    }

    public static boolean verifyChangeAmount(float amount) {
        return !(amount < 0);
    }

    public static boolean verifyExpirationDate(YearMonth date) {
        return date.isAfter(YearMonth.now());
    }

    public static boolean verifyNumericData(String data, int length) {
        boolean valid = true;

        if (data.length() != length) valid = false;
        if (!data.matches("[0-9]+")) valid = false;

        return valid;
    }
}
