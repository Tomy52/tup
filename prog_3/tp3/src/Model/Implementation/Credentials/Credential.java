package Model.Implementation.Credentials;

public class Credential {
    private int id_credencial;
    private int id_usuario;
    private String username;
    private String pass;
    private PermissionLevel permiso;

    public Credential(int id_credencial, int id_usuario, String username, String pass, PermissionLevel permiso) {
        this.id_credencial = id_credencial;
        this.id_usuario = id_usuario;
        this.username = username;
        this.pass = pass;
        this.permiso = permiso;
    }

    public Credential() {
    }

    public int getId_credencial() {
        return id_credencial;
    }

    public void setId_credencial(int id_credencial) {
        this.id_credencial = id_credencial;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public PermissionLevel getPermiso() {
        return permiso;
    }

    public void setPermiso(PermissionLevel permiso) {
        this.permiso = permiso;
    }
}
