import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class LoginForm extends JFrame {
    private JTextField txtUsuario;
    private JPasswordField txtContrasena;

    public LoginForm() {
        setTitle("Iniciar Sesión");
        setSize(300, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel lblUsuario = new JLabel("Usuario:");
        txtUsuario = new JTextField();
        JLabel lblContrasena = new JLabel("Contraseña:");
        txtContrasena = new JPasswordField();

        JButton btnLogin = new JButton("Iniciar Sesión");
        JButton btnRegistro = new JButton("Registrarse");
        JButton btnRanking = new JButton("Ver Ranking");

        panel.add(lblUsuario);
        panel.add(txtUsuario);
        panel.add(lblContrasena);
        panel.add(txtContrasena);
        panel.add(btnLogin);
        panel.add(btnRegistro);
        panel.add(btnRanking);

        add(panel);

        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String usuario = txtUsuario.getText();
                String contrasena = new String(txtContrasena.getPassword());

                if (validarUsuario(usuario, contrasena)) {
                    JOptionPane.showMessageDialog(LoginForm.this, "¡Inicio de sesión exitoso!");
                    dispose();
                    new ModeSelectionWindow(usuario);  // Redirige a selección de modo
                } else {
                    JOptionPane.showMessageDialog(LoginForm.this, "Credenciales incorrectas.");
                }
            }
        });

        btnRegistro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new RegisterForm();
                dispose();
            }
        });

        btnRanking.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new RankingWindow(); // Muestra la ventana de ranking
            }
        });

        setVisible(true);
    }

    private boolean validarUsuario(String usuario, String contrasena) {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT * FROM Usuarios WHERE nom = ? AND contra = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, usuario);
            ps.setString(2, contrasena);
            ResultSet rs = ps.executeQuery();
            return rs.next(); // true si encontró un usuario
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al validar usuario: " + e.getMessage());
            return false;
        }
    }
}
