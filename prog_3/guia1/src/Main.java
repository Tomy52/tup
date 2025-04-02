import Repositories.Implementations.PaymentsRepository;
import Ui.Implementations.PaymentsMenu;

public class Main {
    public static void main(String[] args) {
        PaymentsMenu paymentsMenu = new PaymentsMenu(new PaymentsRepository());

        paymentsMenu.display();
    }
}