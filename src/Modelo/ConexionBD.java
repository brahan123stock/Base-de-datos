package Modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import java.sql.*;

public class ConexionBD {
    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=appsalud;encrypt=false;trustServerCertificate=true;";
    private static final String USER = "sa";
    private static final String PASS = "admin123";

    private Connection con;

    public ConexionBD() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con = DriverManager.getConnection(URL, USER, PASS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Connection getConexion() {
        return con;
    }
}
