package org.tomy52.tp3_conspring.Controller.Implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.tomy52.tp3_conspring.Controller.Exception.NoAutorizadoException;
import org.tomy52.tp3_conspring.Controller.Exception.NoExisteException;
import org.tomy52.tp3_conspring.Model.Implementation.Cuenta.Cuenta;
import org.tomy52.tp3_conspring.Model.Implementation.Cuenta.CuentaDao;
import org.tomy52.tp3_conspring.Model.Implementation.Usuario.Usuario;

import java.util.*;
import java.util.stream.Collectors;
import static java.util.Collections.reverseOrder;

@Controller
public class CuentaController {
    private final CuentaDao dao;
    private final UsuarioController controladorUsuario;

    @Autowired
    public CuentaController(CuentaDao dao, UsuarioController controladorUsuario) {
        this.dao = dao;
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

    public List<Cuenta> obtenerDeUsuario(int id_usuario) throws NoAutorizadoException {
        Usuario usuarioLogueado = controladorUsuario.obtenerLogueado();
        if (usuarioLogueado.getNivelPermisos().toString().equals("CLIENTE") &&
                usuarioLogueado.getId_usuario() != id_usuario) {
            throw new NoAutorizadoException("Los clientes solo pueden ver sus cuentas");
        }
        return dao.obtenerDeUsuario(id_usuario).values()
                .stream()
                .toList();
    }

    public double obtenerSaldoDeUsuario(int id_usuario) throws NoAutorizadoException {
        Usuario usuarioLogueado = controladorUsuario.obtenerLogueado();
        if (usuarioLogueado.getNivelPermisos().toString().equals("CLIENTE") &&
                usuarioLogueado.getId_usuario() != id_usuario) {
            throw new NoAutorizadoException("Los clientes solo pueden ver sus cuentas");
        }
        return dao.obtenerDeUsuario(id_usuario).values()
                .stream()
                .mapToDouble(Cuenta::getSaldo)
                .reduce(0, Double::sum);
    }

    public void depositar(int id_cuenta, double monto) throws NoAutorizadoException, NoExisteException {
        Usuario usuarioLogueado = controladorUsuario.obtenerLogueado();
        Optional<Cuenta> cuentaObjetivo = dao.obtenerCuenta(id_cuenta);

        if (cuentaObjetivo.isEmpty()) {
            throw new NoExisteException("La cuenta no existe");
        }

        if (usuarioLogueado.getNivelPermisos().toString().equals("CLIENTE")
                && cuentaObjetivo.get().getId_usuario() != usuarioLogueado.getId_usuario()) {
            throw new NoAutorizadoException("Los clientes solo pueden depositar en cuentas propias");
        }

        if (usuarioLogueado.getNivelPermisos().toString().equals("GESTOR")) {
            int id_usuario_titular = cuentaObjetivo.get().getId_usuario();
            if (!controladorUsuario.esCliente(id_usuario_titular)) {
                throw new NoAutorizadoException("Los gestores solo pueden depositar en cuentas de clientes");
            }
        }

        dao.depositar(id_cuenta,monto);
    }

    public void transferir(int id_cuentaOrigen, int id_cuentaDestino, double monto) throws NoExisteException, NoAutorizadoException {
        Usuario usuarioLogueado = controladorUsuario.obtenerLogueado();
        Optional<Cuenta> cuentaOrigen = dao.obtenerCuenta(id_cuentaOrigen);
        Optional<Cuenta> cuentaDestino = dao.obtenerCuenta(id_cuentaDestino);

        if (cuentaOrigen.isEmpty() || cuentaDestino.isEmpty()) {
            throw new NoExisteException("Alguna de las cuentas no existe");
        }

        if (usuarioLogueado.getNivelPermisos().toString().equals("CLIENTE")
                && cuentaOrigen.get().getId_usuario() != usuarioLogueado.getId_usuario()) {
            throw new NoAutorizadoException("Los clientes solo pueden transferir desde cuentas propias");
        }

        dao.transferir(id_cuentaOrigen,id_cuentaDestino,monto);
    }

    public Map<String,Long> obtenerTotalPorTipo() throws NoAutorizadoException {
        Map<String,Long> totalPorTipo;

        totalPorTipo = obtenerTodos().stream().collect(Collectors.groupingBy(Cuenta::getTipoCuentaString,Collectors.counting()));

        return totalPorTipo;
    }

    public Usuario obtenerUsuarioMasRico() throws NoAutorizadoException {
        Usuario usuarioLogueado = controladorUsuario.obtenerLogueado();

        if (!usuarioLogueado.getNivelPermisos().toString().equals("ADMINISTRADOR")) {
            throw new NoAutorizadoException("El usuario no tiene los permisos necesarios para realizar esta accion");
        }

        List<Integer> idsUsuarios = controladorUsuario.obtenerIdsUsuarios();
        Map<Integer,Double> saldosUsuarios = new HashMap<>();

        //Se ponen los ids de cada usuario en el map como una key
        //Y los saldos como un value
        for (Integer id: idsUsuarios) {
            double saldo = obtenerSaldoDeUsuario(id);
            saldosUsuarios.put(id,saldo);
        }

        //Se obtiene el valor mas alto del map
        double maximo = saldosUsuarios.values().stream().max(Comparator.naturalOrder()).get();

        //Se busca el usuario correspondiente a ese saldo
        int idUsuarioMasRico = saldosUsuarios.entrySet().stream().filter(user -> user.getValue() == maximo).toList().getFirst().getKey();

        //Se devuelve la info de ese usuario
        Usuario usuarioMasRico = controladorUsuario.obtener(idUsuarioMasRico).get();

        return usuarioMasRico;
    }

    public List<Integer> obtenerUsuariosPorRiqueza() throws NoAutorizadoException {
        Usuario usuarioLogueado = controladorUsuario.obtenerLogueado();

        if (!usuarioLogueado.getNivelPermisos().toString().equals("ADMINISTRADOR")) {
            throw new NoAutorizadoException("El usuario no tiene los permisos necesarios para realizar esta accion");
        }

        List<Integer> idsUsuarios = controladorUsuario.obtenerIdsUsuarios();
        Map<Integer,Double> saldosUsuarios = new HashMap<>();

        for (Integer id: idsUsuarios) {
            double saldo = obtenerSaldoDeUsuario(id);
            saldosUsuarios.put(id,saldo);
        }

        //Se ordena por orden inverso, comparando los saldos de cada usuario
        //Luego se deja solo el id de cada usuario y se los devuelve en una lista
        return saldosUsuarios.entrySet().stream().sorted(reverseOrder(Map.Entry.comparingByValue())).map(Map.Entry::getKey).toList();

        //Podria haber hecho como hice en el anterior, pero preferi dejarlo como lista
    }
}
