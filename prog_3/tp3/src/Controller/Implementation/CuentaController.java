package Controller.Implementation;

import Controller.Exception.NoAutorizadoException;
import Model.Implementation.Cuenta.Cuenta;
import Model.Implementation.Cuenta.CuentaDao;
import Model.Implementation.Usuario.Usuario;

import java.util.List;

public class CuentaController {
    private final CuentaDao dao;
    private final UsuarioController controladorUsuario;

    public CuentaController(UsuarioController controladorUsuario) {
        this.dao = new CuentaDao();
        this.controladorUsuario = controladorUsuario;
    }

    public void agregar(int id_titular, String tipoCuenta) throws NoAutorizadoException {
        Usuario usuarioLogueado = controladorUsuario.obtenerLogueado();
        if (usuarioLogueado.getNivelPermisos().toString().equals("CLIENTE") &&
            usuarioLogueado.getId_usuario() != id_titular) {
            throw new NoAutorizadoException("Los clientes solo pueden crear cuentas propias");
        }
        Cuenta cuentaNueva = new Cuenta();
        cuentaNueva.setId_usuario(id_titular);
        cuentaNueva.setTipoCuenta(tipoCuenta);
        dao.agregar(cuentaNueva);
    }

    public List<Cuenta> obtenerTodos() throws NoAutorizadoException {
        Usuario usuarioLogueado = controladorUsuario.obtenerLogueado();
        if (usuarioLogueado.getNivelPermisos().toString().equals("CLIENTE")) {
            throw new NoAutorizadoException("Los clientes no pueden ver todas las cuentas");
        }
        return dao.obtenerTodos();
    }
}
