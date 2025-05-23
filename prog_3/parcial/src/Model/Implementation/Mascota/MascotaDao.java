package Model.Implementation.Mascota;

import Model.Implementation.DBConnection;
import Model.Interface.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MascotaDao implements Dao<Mascota> {
    private final Connection conexion;

    public MascotaDao() {
        this.conexion = DBConnection.getInstance().getConnection();
    }

    @Override
    public void agregar(Mascota mascota) {
        String agregarMascotaQuery = "INSERT INTO mascota(nombre,especie,raza,edad,id_duenio) VALUES(?,?,?,?,?)";

        try (PreparedStatement statement = conexion.prepareStatement(agregarMascotaQuery)) {
            statement.setString(1,mascota.getNombre());
            statement.setString(2,mascota.getEspecie());
            statement.setString(3,mascota.getRaza());
            statement.setInt(4,mascota.getEdad());
            statement.setInt(5,mascota.getIdDuenio());
            statement.execute();
        } catch (SQLException e) {
            System.out.println("Error al agregar mascota: " + e.getMessage());
        }
    }

    private Mascota mapear(ResultSet resultSet) throws SQLException {
        Mascota mascota = new Mascota();

        mascota.setId(resultSet.getInt("id"));
        mascota.setNombre(resultSet.getString("nombre"));
        mascota.setEspecie(resultSet.getString("especie"));
        mascota.setRaza(resultSet.getString("raza"));
        mascota.setEdad(resultSet.getInt("edad"));
        mascota.setIdDuenio(resultSet.getInt("id_duenio"));

        return mascota;
    }

    @Override
    public Optional<Mascota> obtenerUno(int id) {
        Optional<Mascota> mascota = Optional.empty();
        String obtenerUnMascotaQuery = "SELECT * FROM mascota WHERE id = ?";

        try (PreparedStatement statement = conexion.prepareStatement(obtenerUnMascotaQuery)) {
            statement.setInt(1,id);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                mascota = Optional.of(mapear(resultSet));
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener mascota: " + e.getMessage());
        }

        return mascota;
    }

    @Override
    public List<Mascota> obtenerTodos() {
        List<Mascota> mascotas = new ArrayList<>();
        String obtenerTodosMascotasQuery = "SELECT * FROM mascota";

        try (PreparedStatement statement = conexion.prepareStatement(obtenerTodosMascotasQuery)) {
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                mascotas.add(mapear(resultSet));
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener mascotas: " + e.getMessage());
        }

        return mascotas;
    }

    @Override
    public void modificar(int id, Mascota new_data) {
        String modificarMascotaQuery = "UPDATE mascota SET nombre = ?, especie = ?, raza = ?, edad = ?, id_duenio = ? WHERE id = ?";

        try (PreparedStatement statement = conexion.prepareStatement(modificarMascotaQuery)) {
            statement.setString(1, new_data.getNombre());
            statement.setString(2,new_data.getEspecie());
            statement.setString(3,new_data.getRaza());
            statement.setInt(4,new_data.getEdad());
            statement.setInt(5,new_data.getIdDuenio());
            statement.setInt(6,id);
            statement.execute();
        } catch (SQLException e) {
            System.out.println("Error al modificar mascota: " + e.getMessage());
        }
    }

    @Override
    public void eliminar(int id) {
        String eliminarMascotaQuery = "DELETE FROM mascota WHERE id = ?";

        try (PreparedStatement statement = conexion.prepareStatement(eliminarMascotaQuery)) {
            statement.setInt(1,id);
            statement.execute();
        } catch (SQLException e) {
            System.out.println("Error al eliminar mascota: " + e.getMessage());
        }
    }
}
