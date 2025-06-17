import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class RegisterForm extends JFrame {
    private JTextField txtUser;
    private JPasswordField txtPass;

    public RegisterForm() {
        setTitle("Registro");
        setSize(300, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JLabel lblUser = new JLabel("Usuario:");
        txtUser = new JTextField();

        JLabel lblPass = new JLabel("Contraseña:");
        txtPass = new JPasswordField();

        JButton btnRegistrar = new JButton("Registrar");
        btnRegistrar.addActionListener(e -> registrar());

        setLayout(new GridLayout(5,1,5,5));
        add(lblUser);
        add(txtUser);
        add(lblPass);
        add(txtPass);
        add(btnRegistrar);

        setVisible(true);
    }

    private void registrar() {
        String user = txtUser.getText().trim();
        String pass = new String(txtPass.getPassword());

        if (user.isEmpty() || pass.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe completar usuario y contraseña.");
            return;
        }

        try (Connection con = DBConnection.getConnection()) {
            // Verificar si usuario existe
            try (PreparedStatement psCheck = con.prepareStatement("SELECT * FROM Usuarios WHERE nom=?")) {
                psCheck.setString(1, user);
                ResultSet rs = psCheck.executeQuery();
                if (rs.next()) {
                    JOptionPane.showMessageDialog(this, "El usuario ya existe.");
                    return;
                }
            }

            // Insertar usuario nuevo con puntaje 0
            try (PreparedStatement psInsert = con.prepareStatement("INSERT INTO Usuarios (nom, contra, puntaje) VALUES (?, ?, 0)")) {
                psInsert.setString(1, user);
                psInsert.setString(2, pass);
                psInsert.executeUpdate();
            }

            JOptionPane.showMessageDialog(this, "Usuario registrado exitosamente.");
            dispose();
            new LoginForm();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al registrar usuario.");
            e.printStackTrace();
        }
    }
}
