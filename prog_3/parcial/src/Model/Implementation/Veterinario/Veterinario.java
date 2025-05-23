package Model.Implementation.Veterinario;

public class Veterinario {
    private int id;
    private String nombre;
    private String matricula;
    private EspecialidadVeterinario especialidad;

    public Veterinario() {
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

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public EspecialidadVeterinario getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(EspecialidadVeterinario especialidad) {
        this.especialidad = especialidad;
    }

    @Override
    public String toString() {
        return "Veterinario: \n" +
                "ID: " + getId() + "\n" +
                "Nombre: " + getNombre() + "\n" +
                "Matricula: " + getMatricula() + "\n" +
                "Especialidad: " + getEspecialidad();
    }
}
