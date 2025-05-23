package Model.Implementation.Mascota;

public class Mascota {
    private int id;
    private String nombre;
    private String especie;
    private String raza;
    private int edad;
    private int idDuenio;

    public Mascota() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public int getIdDuenio() {
        return idDuenio;
    }

    public void setIdDuenio(int idDuenio) {
        this.idDuenio = idDuenio;
    }

    @Override
    public String toString() {
        return "Mascota: \n" +
                "ID: " + getId() + "\n" +
                "Nombre: " + getNombre() + "\n" +
                "Especie: " + getEspecie() + "\n" +
                "Raza: " + getRaza() + "\n" +
                "Edad: " + getEdad() + "\n" +
                "ID Duenio: " + getIdDuenio();
    }
}
