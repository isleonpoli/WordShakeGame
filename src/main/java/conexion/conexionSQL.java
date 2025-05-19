package conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class conexionSQL {

    private static final String URL = "jdbc:postgresql://127.0.0.1/WordShake";
    private static final String USER = "postgres";
    private static final String PASSWORD = "BaseDatos";

    private static Connection connection;

    // Conexión a la base de datos
    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                // No es necesario cargar el controlador manualmente con Maven
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Conexión a la base de datos establecida.");
            } catch (SQLException e) {
                throw new SQLException("Error al conectar con la base de datos", e);
            }
        }
        return connection;
    }

    // Método para cerrar la conexión
    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Conexión a la base de datos cerrada.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
  
}