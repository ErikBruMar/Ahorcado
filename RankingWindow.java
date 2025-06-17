import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class RankingWindow extends JFrame {
    public RankingWindow() {
        setTitle("Ranking de Jugadores");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        String[] columnas = {"Usuario", "Puntaje"};
        DefaultTableModel model = new DefaultTableModel(columnas, 0);
        JTable tabla = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(tabla);
        add(scrollPane, BorderLayout.CENTER);

        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT nom, puntaje FROM Usuarios ORDER BY puntaje DESC";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String usuario = rs.getString("nom");
                int puntaje = rs.getInt("puntaje");
                model.addRow(new Object[]{usuario, puntaje});
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al obtener el ranking: " + e.getMessage());
        }

        setVisible(true);
    }
}
