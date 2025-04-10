package Model;

import javax.xml.transform.Result;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DireccionDAO {
    private final Connection connection;

    public DireccionDAO() {
        connection = ConexionMySQL.getInstance().getConnection();
    }

    public void agregar(String calle, int altura, int alumno_id) {
        String insertDireccionQuery = "INSERT INTO direcciones (calle, altura, alumno_id) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(insertDireccionQuery)) {
            statement.setString(1, calle);
            statement.setInt(2, altura);
            statement.setInt(3, alumno_id);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error agregando nueva direccion: " + e.getMessage());
        }
    }

    public void eliminar(int idDireccionEliminar) {
        String removeDireccionQuery = "DELETE FROM direcciones WHERE id=" + idDireccionEliminar;

        try (Statement statement = connection.createStatement()) {
            statement.execute(removeDireccionQuery);
        } catch (SQLException e) {
            System.out.println("Error removiendo alumno id = " + idDireccionEliminar + " : " + e.getMessage());
        }
    }

    public List<Direccion> obtenerTodos() {
        List<Direccion> lista = new ArrayList<>();
        String sql = "SELECT * FROM direcciones";

        try (Statement statement = connection.createStatement();
             ResultSet resultados = statement.executeQuery(sql)) {
            while (resultados.next()) {
                Direccion direccionAgregar = new Direccion(resultados.getInt("id"),
                        resultados.getString("calle"),
                        resultados.getInt("altura"),
                        resultados.getInt("alumno_id"));
                lista.add(direccionAgregar);
            }
        } catch (SQLException e) {
            System.out.println("Error leyendo usuarios: " + e.getMessage());
        }

        return lista;
    }
}
