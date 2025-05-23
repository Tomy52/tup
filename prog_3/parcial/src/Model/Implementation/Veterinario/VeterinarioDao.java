package Model.Implementation.Veterinario;

import Model.Implementation.DBConnection;
import Model.Interface.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class VeterinarioDao implements Dao<Veterinario> {
    private final Connection conexion;

    public VeterinarioDao() {
        this.conexion = DBConnection.getInstance().getConnection();
    }

    @Override
    public void agregar(Veterinario veterinario) {
        String agregarVeterinarioQuery = "INSERT INTO veterinario(nombre,matricula,especialidad) VALUES(?,?,?)";

        try (PreparedStatement statement = conexion.prepareStatement(agregarVeterinarioQuery)) {
            statement.setString(1,veterinario.getNombre());
            statement.setString(2,veterinario.getMatricula());
            statement.setString(3,veterinario.getEspecialidad().toString());
            statement.execute();
        } catch (SQLException e) {
            System.out.println("Error al agregar veterinario: " + e.getMessage());
        }
    }

    private Veterinario mapear(ResultSet resultSet) throws SQLException {
        Veterinario veterinario = new Veterinario();

        veterinario.setId(resultSet.getInt("id"));
        veterinario.setNombre(resultSet.getString("nombre"));
        veterinario.setMatricula(resultSet.getString("matricula"));
        veterinario.setEspecialidad(EspecialidadVeterinario.valueOf(resultSet.getString("especialidad").toUpperCase()));

        return veterinario;
    }

    @Override
    public Optional<Veterinario> obtenerUno(int id) {
        Optional<Veterinario> veterinario = Optional.empty();
        String obtenerUnVeterinarioQuery = "SELECT * FROM veterinario WHERE id = ?";

        try (PreparedStatement statement = conexion.prepareStatement(obtenerUnVeterinarioQuery)) {
            statement.setInt(1,id);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                veterinario = Optional.of(mapear(resultSet));
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener veterinario: " + e.getMessage());
        }

        return veterinario;
    }

    @Override
    public List<Veterinario> obtenerTodos() {
        List<Veterinario> veterinarios = new ArrayList<>();
        String obtenerTodosVeterinariosQuery = "SELECT * FROM veterinario";

        try (PreparedStatement statement = conexion.prepareStatement(obtenerTodosVeterinariosQuery)) {
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                veterinarios.add(mapear(resultSet));
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener veterinarios: " + e.getMessage());
        }

        return veterinarios;
    }

    @Override
    public void modificar(int id, Veterinario new_data) {
        String modificarVeterinarioQuery = "UPDATE veterinario SET nombre = ?, matricula = ?, especialidad = ? WHERE id = ?";

        try (PreparedStatement statement = conexion.prepareStatement(modificarVeterinarioQuery)) {
            statement.setString(1, new_data.getNombre());
            statement.setString(2,new_data.getMatricula());
            statement.setString(3,new_data.getEspecialidad().toString());
            statement.setInt(4,id);
            statement.execute();
        } catch (SQLException e) {
            System.out.println("Error al modificar veterinario: " + e.getMessage());
        }
    }

    @Override
    public void eliminar(int id) {
        String eliminarVeterinarioQuery = "DELETE FROM veterinario WHERE id = ?";

        try (PreparedStatement statement = conexion.prepareStatement(eliminarVeterinarioQuery)) {
            statement.setInt(1,id);
            statement.execute();
        } catch (SQLException e) {
            System.out.println("Error al eliminar veterinario: " + e.getMessage());
        }
    }
}

