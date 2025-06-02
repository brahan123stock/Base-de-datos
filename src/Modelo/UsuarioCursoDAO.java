package Modelo;

import java.sql.*;


public class UsuarioCursoDAO {
    private Connection con;

    public UsuarioCursoDAO() {
        con = new ConexionBD().getConexion();
    }

    public boolean registrarUsuarioCurso(int idUsuario, int idCurso) {
        String sql = "INSERT INTO usuario_curso(usuario_id, curso_id) VALUES (?, ?)";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idUsuario);
            ps.setInt(2, idCurso);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
