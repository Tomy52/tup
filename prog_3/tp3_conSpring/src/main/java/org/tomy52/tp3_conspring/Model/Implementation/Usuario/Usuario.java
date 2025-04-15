package org.tomy52.tp3_conspring.Model.Implementation.Usuario;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class Usuario {
    private int id_usuario;
    private String nombre;
    private String apellido;
    private String dni;
    private String email;
    private String fecha_creacion;
    private NivelPermisoUsuario nivelPermisos;

    public String getNivelPermisosString() {
        return nivelPermisos.toString();
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
