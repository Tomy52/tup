package Model.Implementation.Cuenta;

import Model.Implementation.ConexionMySQL;
import Model.Interface.Dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
}
