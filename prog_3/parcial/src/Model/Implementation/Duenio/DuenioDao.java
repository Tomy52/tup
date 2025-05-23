package Model.Implementation.Duenio;

import Model.Implementation.DBConnection;
import Model.Implementation.Duenio.Duenio;
import Model.Interface.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DuenioDao implements Dao<Duenio> {
    private final Connection conexion;

    public DuenioDao() {
        this.conexion = DBConnection.getInstance().getConnection();
    }

    @Override
    public void agregar(Duenio duenio) {
        String agregarDuenioQuery = "INSERT INTO duenio(nombre_completo,dni,telefono) VALUES(?,?,?)";

        try (PreparedStatement statement = conexion.prepareStatement(agregarDuenioQuery)) {
            statement.setString(1,duenio.getNombreCompleto());
            statement.setString(2,duenio.getDni());
            statement.setString(3,duenio.getTelefono());
            statement.execute();
        } catch (SQLException e) {
            System.out.println("Error al agregar duenio: " + e.getMessage());
        }
    }

    private Duenio mapear(ResultSet resultSet) throws SQLException {
        Duenio duenio = new Duenio();

        duenio.setId(resultSet.getInt("id"));
        duenio.setNombreCompleto(resultSet.getString("nombre_completo"));
        duenio.setDni(resultSet.getString("dni"));
        duenio.setTelefono(resultSet.getString("telefono"));

        return duenio;
    }

    @Override
    public Optional<Duenio> obtenerUno(int id) {
        Optional<Duenio> duenio = Optional.empty();
        String obtenerUnDuenioQuery = "SELECT * FROM duenio WHERE id = ?";

        try (PreparedStatement statement = conexion.prepareStatement(obtenerUnDuenioQuery)) {
            statement.setInt(1,id);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                duenio = Optional.of(mapear(resultSet));
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener duenio: " + e.getMessage());
        }

        return duenio;
    }

    @Override
    public List<Duenio> obtenerTodos() {
        List<Duenio> duenios = new ArrayList<>();
        String obtenerTodosDueniosQuery = "SELECT * FROM duenio";

        try (PreparedStatement statement = conexion.prepareStatement(obtenerTodosDueniosQuery)) {
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                duenios.add(mapear(resultSet));
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener duenios: " + e.getMessage());
        }

        return duenios;
    }

    @Override
    public void modificar(int id, Duenio new_data) {
        String modificarDuenioQuery = "UPDATE duenio SET nombre_completo = ?, dni = ?, telefono = ? WHERE id = ?";

        try (PreparedStatement statement = conexion.prepareStatement(modificarDuenioQuery)) {
            statement.setString(1, new_data.getNombreCompleto());
            statement.setString(2,new_data.getDni());
            statement.setString(3,new_data.getTelefono());
            statement.setInt(4,id);
            statement.execute();
        } catch (SQLException e) {
            System.out.println("Error al modificar duenio: " + e.getMessage());
        }
    }

    @Override
    public void eliminar(int id) {
        String eliminarDuenioQuery = "DELETE FROM duenio WHERE id = ?";

        try (PreparedStatement statement = conexion.prepareStatement(eliminarDuenioQuery)) {
            statement.setInt(1,id);
            statement.execute();
        } catch (SQLException e) {
            System.out.println("Error al eliminar duenio: " + e.getMessage());
        }
    }
}
