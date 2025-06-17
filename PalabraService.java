import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PalabraService {

    public static String obtenerPalabraAleatoria() {
        List<String> palabras = new ArrayList<>();
        try (Connection con = DBConnection.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT palabra FROM Palabras")) {

            while (rs.next()) {
                palabras.add(rs.getString("palabra"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (palabras.isEmpty()) {
            return "JAVA"; // fallback
        }
        Random rand = new Random();
        return palabras.get(rand.nextInt(palabras.size()));
    }
}
