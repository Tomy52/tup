package Model.Implementation.Duenio;

public class Duenio {
    private int id;
    private String nombreCompleto;
    private String dni;
    private String telefono;

    public Duenio() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    @Override
    public String toString() {
        return "Duenio: \n" +
                "ID: " + getId() + "\n" +
                "Nombre: " + getNombreCompleto() + "\n" +
                "DNI: " + getDni() + "\n" +
                "Telefono: " + getTelefono();
    }

    public String mostrarNombreYTelefono() {
        return "Duenio: \n" +
                "Nombre: " + getNombreCompleto() + "\n" +
                "Telefono: " + getTelefono();
    }
}
