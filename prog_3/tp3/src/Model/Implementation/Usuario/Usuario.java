package Model.Implementation.Usuario;

public class Usuario {
    private int id_usuario;
    private String nombre;
    private String apellido;
    private String dni;
    private String email;
    private String fecha_creacion;
    private NivelPermisoUsuario nivelPermisos;

    public Usuario(int id_usuario, String nombre, String apellido, String dni, String email, String fecha_creacion) {
        this.id_usuario = id_usuario;
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.email = email;
        this.fecha_creacion = fecha_creacion;
    }

    public Usuario(String nombre, String apellido, String dni, String email, String fecha_creacion) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.email = email;
        this.fecha_creacion = fecha_creacion;
    }

    public Usuario(String nombre, String apellido, String dni, String email) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.email = email;
    }

    public Usuario() {}

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFecha_creacion() {
        return fecha_creacion;
    }

    public void setFecha_creacion(String fecha_creacion) {
        this.fecha_creacion = fecha_creacion;
    }

    public NivelPermisoUsuario getNivelPermisos() {
        return nivelPermisos;
    }

    public void setNivelPermisos(String nivelPermisos) {
        this.nivelPermisos = NivelPermisoUsuario.valueOf(nivelPermisos);
    }

    @Override
    public String toString() {
        return "Id usuario: " + id_usuario + "\n" +
                "Nombre: " + nombre + "\n" +
                "Apellido: " + apellido + "\n" +
                "Documento: " + dni + "\n" +
                "Email: " + email + "\n" +
                "Fecha de creacion: " + fecha_creacion + "\n" +
                "Nivel de permisos: " + nivelPermisos;
    }
}
