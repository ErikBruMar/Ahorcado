import java.sql.*;

public class PuntajeService {

    public static void actualizarPuntaje(String jugador, int puntos) {
        try (Connection con = DBConnection.getConnection()) {
            con.setAutoCommit(false);

            // Obtener puntaje actual
            int puntajeActual = 0;
            try (PreparedStatement stmt1 = con.prepareStatement("SELECT puntaje FROM Usuarios WHERE nom=? FOR UPDATE")) {
                stmt1.setString(1, jugador);
                ResultSet rs = stmt1.executeQuery();
                if (rs.next()) {
                    puntajeActual = rs.getInt("puntaje");
                } else {
                    // Si no existe jugador, no hacer nada
                    con.rollback();
                    return;
                }
            }

            // Actualizar puntaje
            int nuevoPuntaje = puntajeActual + puntos;
            if (nuevoPuntaje < 0) nuevoPuntaje = 0; // no negativos

            try (PreparedStatement stmt2 = con.prepareStatement("UPDATE Usuarios SET puntaje=? WHERE nom=?")) {
                stmt2.setInt(1, nuevoPuntaje);
                stmt2.setString(2, jugador);
                stmt2.executeUpdate();
            }
            con.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
