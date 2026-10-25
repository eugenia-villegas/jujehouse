/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.SQLException;
/**
 *
 * @author Euge
 */
public class PruebaConexion {
    public static void main(String[] args) {
        String url = "jdbc:sqlite:jujehouse.db";

        try (Connection conn = DriverManager.getConnection(url); Statement stmt = conn.createStatement()) {
            System.out.println("Conexion exitosa");
            
        } catch (SQLException e) {
            System.out.println("Error de conexion: " + e.getMessage());
        }
    }
}
