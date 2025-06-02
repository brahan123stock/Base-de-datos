package Modelo;
import java.sql.*;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;
public class CursoDAO {
    private Connection con;

    public CursoDAO() {
        con = new ConexionBD().getConexion();
    }// Cargar cursos para JComboBox
    public DefaultComboBoxModel<String> listarCursos() {
        DefaultComboBoxModel<String> modelo = new DefaultComboBoxModel<>();
        String sql = "SELECT nombre FROM Curso";
        try (Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                modelo.addElement(rs.getString("nombre"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }return modelo;
    }// Obtener id_curso por nombre
    public int obtenerIdCurso(String nombreCurso) {
    String sql = "SELECT id FROM curso WHERE nombre = ?"; // Cambiado a 'id'
    try (PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setString(1, nombreCurso);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return rs.getInt("id"); // Cambiado a 'id'
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return -1;
}

}
