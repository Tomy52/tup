import Repositorio.Implementaciones.RepositorioBiblioteca;
import Ui.Menu;

public class Main {
    public static void main(String[] args) {
        RepositorioBiblioteca biblioteca = new RepositorioBiblioteca();
        Menu menu = new Menu(biblioteca);

        menu.mostrarMenu();
    }
}