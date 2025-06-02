package Modelo;

import java.sql.*;
import javax.swing.table.DefaultTableModel;

public class ForoDAO {
    private Connection con;

    public ForoDAO() {
        con = new ConexionBD().getConexion();
    }

    public boolean guardarPublicacion(String titulo, String nombre, java.sql.Date fecha, String publicacion) {
        String sql = "INSERT INTO Foro(titulo, nombre, fecha, publicacion) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, titulo);
            ps.setString(2, nombre);
            ps.setDate(3, fecha);
            ps.setString(4, publicacion);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public DefaultTableModel listarForos() {
        String[] columnas = {"ID", "Título", "Nombre", "Fecha", "Publicación"};
        DefaultTableModel modelo = new DefaultTableModel(null, columnas);

        String sql = "SELECT * FROM Foro";
        try (Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Object[] fila = {
                    rs.getInt("id"),
                    rs.getString("titulo"),
                    rs.getString("nombre"),
                    rs.getDate("fecha"),
                    rs.getString("publicacion")
                };
                modelo.addRow(fila);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return modelo;
    }

    public DefaultTableModel buscarForos(String texto) {
        String[] columnas = {"ID", "Título", "Nombre", "Fecha", "Publicación"};
        DefaultTableModel modelo = new DefaultTableModel(null, columnas);

        String sql = "SELECT * FROM Foro WHERE titulo LIKE ? OR nombre LIKE ? OR publicacion LIKE ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            String busqueda = "%" + texto + "%";
            ps.setString(1, busqueda);
            ps.setString(2, busqueda);
            ps.setString(3, busqueda);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Object[] fila = {
                    rs.getInt("id"),
                    rs.getString("titulo"),
                    rs.getString("nombre"),
                    rs.getDate("fecha"),
                    rs.getString("publicacion")
                };
                modelo.addRow(fila);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return modelo;
    }
}

