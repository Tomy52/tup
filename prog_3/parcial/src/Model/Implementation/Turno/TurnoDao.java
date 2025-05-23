package Model.Implementation.Turno;

import Model.Implementation.DBConnection;
import Model.Interface.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TurnoDao implements Dao<Turno> {
    private final Connection conexion;

    public TurnoDao() {
        this.conexion = DBConnection.getInstance().getConnection();
    }

    @Override
    public void agregar(Turno turno) {
        String agregarTurnoQuery = "INSERT INTO turno(id_mascota,id_veterinario,fecha_hora,estado) VALUES(?,?,?,?)";

        try (PreparedStatement statement = conexion.prepareStatement(agregarTurnoQuery)) {
            statement.setInt(1,turno.getIdMascota());
            statement.setInt(2,turno.getIdVeterinario());
            statement.setTimestamp(3,turno.getFechaHora());
            statement.setString(4,turno.getEstado().toString());
            statement.execute();
        } catch (SQLException e) {
            System.out.println("Error al agregar turno: " + e.getMessage());
        }
    }

    private Turno mapear(ResultSet resultSet) throws SQLException {
        Turno turno = new Turno();

        turno.setId(resultSet.getInt("id"));
        turno.setIdMascota(resultSet.getInt("id_mascota"));
        turno.setIdVeterinario(resultSet.getInt("id_veterinario"));
        turno.setFechaHora(resultSet.getTimestamp("fecha_hora"));
        turno.setEstado(EstadoTurno.valueOf(resultSet.getString("estado").toUpperCase()));

        return turno;
    }

    @Override
    public Optional<Turno> obtenerUno(int id) {
        Optional<Turno> turno = Optional.empty();
        String obtenerUnTurnoQuery = "SELECT * FROM turno WHERE id = ?";

        try (PreparedStatement statement = conexion.prepareStatement(obtenerUnTurnoQuery)) {
            statement.setInt(1,id);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                turno = Optional.of(mapear(resultSet));
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener turno: " + e.getMessage());
        }

        return turno;
    }

    @Override
    public List<Turno> obtenerTodos() {
        List<Turno> turnos = new ArrayList<>();
        String obtenerTodosTurnosQuery = "SELECT * FROM turno";

        try (PreparedStatement statement = conexion.prepareStatement(obtenerTodosTurnosQuery)) {
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                turnos.add(mapear(resultSet));
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener turnos: " + e.getMessage());
        }

        return turnos;
    }

    @Override
    public void modificar(int id, Turno new_data) {
        String modificarTurnoQuery = "UPDATE turno SET id_mascota = ?, id_veterinario = ?, fecha_hora = ?, estado = ? WHERE id = ?";

        try (PreparedStatement statement = conexion.prepareStatement(modificarTurnoQuery)) {
            statement.setInt(1, new_data.getIdMascota());
            statement.setInt(2,new_data.getIdVeterinario());
            statement.setTimestamp(3,new_data.getFechaHora());
            statement.setString(4,new_data.getEstado().toString());
            statement.setInt(5,id);
            statement.execute();
        } catch (SQLException e) {
            System.out.println("Error al modificar turno: " + e.getMessage());
        }
    }

    @Override
    public void eliminar(int id) {
        String eliminarTurnoQuery = "DELETE FROM turno WHERE id = ?";

        try (PreparedStatement statement = conexion.prepareStatement(eliminarTurnoQuery)) {
            statement.setInt(1,id);
            statement.execute();
        } catch (SQLException e) {
            System.out.println("Error al eliminar turno: " + e.getMessage());
        }
    }
}
