package Model.Implementation.Cuenta;

import Model.Implementation.ConexionMySQL;
import Model.Interface.Dao;

import java.sql.*;
import java.util.*;

public class CuentaDao implements Dao<Cuenta> {
    private final Connection conexion;

    public CuentaDao() {
        conexion = ConexionMySQL.getInstancia().getConexion();
    }

    @Override
    public void agregar(Cuenta cuenta) {
        String agregarCuentaQuery = "CALL crearCuenta(?,?)";

        try (PreparedStatement statement = conexion.prepareStatement(agregarCuentaQuery)) {
            statement.setInt(1,cuenta.getId_usuario());
            statement.setString(2,cuenta.getTipoCuenta().toString());
            statement.execute();
        } catch (SQLException e) {
            System.out.println("Error agregando cuenta: " + e.getMessage());
        }
    }

    @Override
    public void eliminar(int id_cuenta) {
        String eliminarCuentaQuery = "CALL eliminarCuenta(?)";
        try (PreparedStatement statement = conexion.prepareStatement(eliminarCuentaQuery)) {
            statement.setInt(1,id_cuenta);
        } catch (SQLException e) {
            System.out.println("Error agregando cuenta: " + e.getMessage());
        }
    }

    private Cuenta mapearCuenta(ResultSet resultSet) throws SQLException {
        Cuenta cuenta = new Cuenta();
        cuenta.setId_cuenta(resultSet.getInt("id_cuenta"));
        cuenta.setId_usuario(resultSet.getInt("id_usuario"));
        cuenta.setTipoCuenta(resultSet.getString("tipo"));
        cuenta.setSaldo(resultSet.getDouble("saldo"));
        cuenta.setFecha_creacion(resultSet.getString("fecha_creacion"));
        return cuenta;
    }

    @Override
    public List<Cuenta> obtenerTodos() {
        String obtenerCuentasQuery = "SELECT * FROM cuentas";
        List<Cuenta> resultados = new ArrayList<>();

        try (Statement statement = conexion.createStatement()) {
            ResultSet resultSet = statement.executeQuery(obtenerCuentasQuery);
            while (resultSet.next()) {
                resultados.add(mapearCuenta(resultSet));
            }
        } catch (SQLException e) {
            System.out.println("Error obteniendo cuentas: " + e.getMessage());
        }
        return resultados;
    }

    public Map<Integer,Cuenta> obtenerDeUsuario(int id_usuario) {
        String obtenerCuentasDeUsuarioQuery = "CALL obtenerCuentasDeUsuario(?)";
        Map<Integer,Cuenta> resultados = new HashMap<>();

        try (PreparedStatement statement = conexion.prepareStatement(obtenerCuentasDeUsuarioQuery)) {
            statement.setInt(1,id_usuario);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Cuenta cuenta = mapearCuenta(resultSet);
                resultados.put(cuenta.getId_cuenta(),cuenta);
            }
        } catch (SQLException e) {
            System.out.println("Error obteniendo cuentas: " + e.getMessage());
        }
        return resultados;
    }

    public void depositar(int id_cuenta, double monto) {
        String depositarQuery = "CALL depositar(?,?)";

        try(PreparedStatement statement = conexion.prepareStatement(depositarQuery)) {
            statement.setInt(1,id_cuenta);
            statement.setDouble(2,monto);
            statement.execute();
        } catch (SQLException e) {
            System.out.println("Error depositando: " + e.getMessage());
        }
    }

    public Optional<Cuenta> obtenerCuenta(int id_cuenta) {
        Optional<Cuenta> resultado = Optional.empty();
        String obtenerCuentaQuery = "CALL buscarCuenta(?)";

        try (PreparedStatement statement = conexion.prepareStatement(obtenerCuentaQuery)) {
            statement.setInt(1,id_cuenta);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()) {
                resultado = Optional.of(mapearCuenta(resultSet));
            }
        } catch (SQLException e) {
            System.out.println("Error obteniendo cuenta: " + e.getMessage());
        }

        return resultado;
    }

    public void transferir(int id_cuentaOrigen, int id_cuentaDestino, double monto) {
        String transferirQuery = "CALL transferir(?,?,?)";

        try (PreparedStatement statement = conexion.prepareStatement(transferirQuery)) {
            statement.setInt(1,id_cuentaOrigen);
            statement.setInt(2,id_cuentaDestino);
            statement.setDouble(3,monto);
            statement.execute();
        } catch (SQLException e) {
            System.out.println("Error obteniendo cuenta: " + e.getMessage());
        }
    }
}
