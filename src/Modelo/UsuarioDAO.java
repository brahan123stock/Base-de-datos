package Modelo;

import java.sql.*;

public class UsuarioDAO {
    private Connection con;

    public UsuarioDAO() {
        con = new ConexionBD().getConexion();
    }

    public int registrarUsuario(String nombre, String dni, String email) {
        String sql = "INSERT INTO Usuario(nombre, dni, email) VALUES (?, ?, ?)";
        try (PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, nombre);
            ps.setString(2, dni);
            ps.setString(3, email);
            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Fallo el registro del usuario, no se insertaron filas.");
            }
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1); // Retorna el id generado
                } else {
                    throw new SQLException("Fallo obtener el ID del usuario.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return -1; // Error
        }
    }
}
