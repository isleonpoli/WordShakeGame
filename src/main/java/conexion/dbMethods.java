package conexion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.usuario;

public class dbMethods {

    public static boolean verificarUsuario(String username) {
        String sql = "SELECT COUNT(*) FROM users WHERE username = ?";

        try (Connection conn = conexionSQL.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }

        } catch (SQLException e) {
            
        }

        return false;
    }

    public static boolean registrarUsuario(usuario usuario) {
        if (verificarUsuario(usuario.getUsername())) {
            return false;
        }
        String sql = "INSERT INTO users (username, password) VALUES (?, ?)";

        try (Connection conn = conexionSQL.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, usuario.getUsername());
            pstmt.setString(2, usuario.getPassword());
            int filasAfectadas = pstmt.executeUpdate();

            return filasAfectadas > 0;

        } catch (SQLException e) {
            return false;
        }
    }

    public static boolean verificarLogin(usuario usuario) {
        String sql = "SELECT COUNT(*) FROM users WHERE username = ? AND password = ?";

        try (Connection conn = conexionSQL.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, usuario.getUsername());
            pstmt.setString(2, usuario.getPassword());
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }

        } catch (SQLException e) {
            
        }

        return false;
    }

    
    public static Integer obtenerPuntajePalabra(String palabra) {
        String sql = "SELECT score FROM words WHERE word = ?";
        
        try (Connection conn = conexionSQL.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, palabra);
            ResultSet rs = stmt.executeQuery();
    
            if (rs.next()) {
                return rs.getInt("score");
            }
    
        } catch (SQLException e) {
           
        }
    
        return null; // Si no se encontró o hubo error
    }

    
    public static void actualizarPuntajeUsuario(String username, int nuevoScore) {
        String selectSql = "SELECT score FROM users WHERE username = ?";
        String updateSql = "UPDATE users SET score = ? WHERE username = ?";

        try (Connection conn = conexionSQL.getConnection();
            PreparedStatement selectStmt = conn.prepareStatement(selectSql)) {

            selectStmt.setString(1, username);
            ResultSet rs = selectStmt.executeQuery();

            if (rs.next()) {
                int scoreActual = rs.getInt("score");

                // Solo actualizar si el nuevo puntaje es mayor
                if (nuevoScore > scoreActual) {
                    try (PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {
                        updateStmt.setInt(1, nuevoScore);
                        updateStmt.setString(2, username);
                        updateStmt.executeUpdate();
                        System.out.println("Nuevo puntaje actualizado: " + nuevoScore);
                    }
                } else {
                    System.out.println("El puntaje no fue actualizado porque no es mayor al anterior.");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    
}
