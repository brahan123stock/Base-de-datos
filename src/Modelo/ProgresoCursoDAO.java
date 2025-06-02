package Modelo;

import java.sql.*;
import javax.swing.table.DefaultTableModel;

import java.sql.*;
import javax.swing.table.DefaultTableModel;

public class ProgresoCursoDAO {
    private Connection con;

    public ProgresoCursoDAO() {
        con = new ConexionBD().getConexion();
    }

    public DefaultTableModel listarProgresos(int usuarioId) {
        DefaultTableModel modelo = new DefaultTableModel(new Object[]{"Curso", "Progreso", "Contenido"}, 0);
        String sql = "SELECT c.nombre AS nombre_curso, p.progreso, p.contenido " +
                     "FROM progreso p INNER JOIN curso c ON p.curso_id = c.id " +
                     "WHERE p.usuario_id = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, usuarioId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                modelo.addRow(new Object[]{
                    rs.getString("nombre_curso"),
                    rs.getString("progreso"),
                    rs.getString("contenido")
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return modelo;
    }

    public boolean guardarProgreso(int usuarioId, int cursoId, String progreso, String contenido) {
        String sql = "INSERT INTO progreso (usuario_id, curso_id, progreso, contenido) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, usuarioId);
            ps.setInt(2, cursoId);
            ps.setString(3, progreso);
            ps.setString(4, contenido);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}

