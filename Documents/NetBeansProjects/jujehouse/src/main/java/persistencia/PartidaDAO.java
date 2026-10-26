
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia;

import excepciones.PersistenciaExcepcion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author Euge
 */
public class PartidaDAO {
    public void guardar(String nombreJugador, int puntaje, int oleada, boolean victoria)
            throws PersistenciaExcepcion {
        try (Connection conn = ConexionSQLite.conectar()) {
            int jugadorId = obtenerOCrearJugador(conn, nombreJugador);
            String sql = "INSERT INTO partidas (jugador_id, puntaje, oleada_alcanzada, victoria, fecha) "
                    + "VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, jugadorId);
                ps.setInt(2, puntaje);
                ps.setInt(3, oleada);
                ps.setInt(4, victoria ? 1 : 0);
                ps.setString(5, LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            throw new PersistenciaExcepcion("No se pudo guardar la partida", e);
        }
    }

    private int obtenerOCrearJugador(Connection conn, String nombre) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(
                "INSERT OR IGNORE INTO jugadores (nombre) VALUES (?)")) {
            ps.setString(1, nombre);
            ps.executeUpdate();
        }
        try (PreparedStatement ps = conn.prepareStatement(
                "SELECT id FROM jugadores WHERE nombre = ?")) {
            ps.setString(1, nombre);
            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                return rs.getInt("id");
            }
        }
    }

    public String obtenerRanking() throws PersistenciaExcepcion {
        String sql = "SELECT j.nombre, p.puntaje, p.oleada_alcanzada "
                + "FROM partidas p JOIN jugadores j ON j.id = p.jugador_id "
                + "ORDER BY p.puntaje DESC LIMIT 5";
        StringBuilder ranking = new StringBuilder();
        try (Connection conn = ConexionSQLite.conectar();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            int posicion = 1;
            while (rs.next()) {
                ranking.append(posicion++).append(". ")
                       .append(rs.getString("nombre")).append(" - ")
                       .append(rs.getInt("puntaje")).append(" pts (oleada ")
                       .append(rs.getInt("oleada_alcanzada")).append(")\n");
            }
        } catch (SQLException e) {
            throw new PersistenciaExcepcion("No se pudo leer el ranking", e);
        }
        return ranking.toString();
    }
}
