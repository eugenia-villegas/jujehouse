/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia;

import excepciones.PersistenciaExcepcion;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
/**
 *
 * @author Euge
 */
public class ConexionSQLite {
    private static final String URL = "jdbc:sqlite:jujehouse.db";

    public static Connection conectar() throws PersistenciaExcepcion {
        try {
            Connection conn = DriverManager.getConnection(URL);
            try (Statement stmt = conn.createStatement()) {
                stmt.execute("PRAGMA foreign_keys = ON");
                stmt.execute("CREATE TABLE IF NOT EXISTS jugadores ("
                        + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + "nombre TEXT NOT NULL UNIQUE)");
                stmt.execute("CREATE TABLE IF NOT EXISTS partidas ("
                        + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + "jugador_id INTEGER NOT NULL, "
                        + "puntaje INTEGER NOT NULL, "
                        + "oleada_alcanzada INTEGER NOT NULL, "
                        + "victoria INTEGER NOT NULL, "
                        + "fecha TEXT NOT NULL, "
                        + "FOREIGN KEY (jugador_id) REFERENCES jugadores(id))");
            }
            return conn;
        } catch (SQLException e) {
            throw new PersistenciaExcepcion("No se pudo conectar con la base de datos", e);
        }
    }
}
