import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class LoginJugador2Window extends JFrame {
    private String jugador1;

    public LoginJugador2Window(String jugador1) {
        this.jugador1 = jugador1;

        setTitle("Login Jugador 2");
        setSize(300, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JLabel lblUser = new JLabel("Usuario:");
        JTextField txtUser = new JTextField();
        JLabel lblPass = new JLabel("Contraseña:");
        JPasswordField txtPass = new JPasswordField();
        JButton btnLogin = new JButton("Login");

        btnLogin.addActionListener(e -> {
            String user = txtUser.getText().trim();
            String pass = new String(txtPass.getPassword());

            if (user.isEmpty() || pass.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Ingrese usuario y contraseña.");
                return;
            }

            if (user.equals(jugador1)) {
                JOptionPane.showMessageDialog(this, "El jugador 2 debe ser diferente al jugador 1.");
                return;
            }

            try (Connection con = DBConnection.getConnection();
                 PreparedStatement stmt = con.prepareStatement("SELECT * FROM Usuarios WHERE nom=? AND contra=?")) {
                stmt.setString(1, user);
                stmt.setString(2, pass);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    JOptionPane.showMessageDialog(this, "Jugador 2 autenticado.");
                    dispose();
                    new PalabraManualWindow(jugador1, user);
                } else {
                    JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrectos.");
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error al conectar con la base de datos.");
                ex.printStackTrace();
            }
        });

        setLayout(new GridLayout(5, 1, 5, 5));
        add(lblUser);
        add(txtUser);
        add(lblPass);
        add(txtPass);
        add(btnLogin);

        setVisible(true);
    }
}
