import javax.swing.*;
import java.awt.*;

public class PalabraManualWindow extends JFrame {
    private String jugador1;
    private String jugador2;

    public PalabraManualWindow(String jugador1, String jugador2) {
        this.jugador1 = jugador1;
        this.jugador2 = jugador2;

        setTitle("Jugador 2 introduce la palabra");
        setSize(350, 150);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JLabel lbl = new JLabel("Jugador 2 (" + jugador2 + "), ingresa la palabra:");
        JTextField txtPalabra = new JTextField();

        JButton btnEmpezar = new JButton("Empezar Juego");

        btnEmpezar.addActionListener(e -> {
            String palabra = txtPalabra.getText().trim();

            if (palabra.isEmpty() || !palabra.matches("[a-zA-ZñÑáéíóúÁÉÍÓÚ]+")) {
                JOptionPane.showMessageDialog(this, "Ingrese una palabra válida (solo letras).");
                return;
            }

            dispose();
            new GameWindow(palabra, jugador1, jugador2);
        });

        setLayout(new GridLayout(3,1,5,5));
        add(lbl);
        add(txtPalabra);
        add(btnEmpezar);

        setVisible(true);
    }
}
