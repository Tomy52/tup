package Excepciones;

public class ElementoInexistenteException extends Exception {
    public ElementoInexistenteException(String message) {
        super("No existe el item con el ID especificado!");
    }
}
