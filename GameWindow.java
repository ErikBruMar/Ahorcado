import javax.swing.*;
import java.awt.*;

public class GameWindow extends JFrame {
    private String palabra;
    private StringBuilder progreso;
    private int errores;
    private JLabel labelPalabra;
    private JLabel labelErrores;
    private JTextField campoLetra;
    private String jugadorAdivina;
    private String jugadorPalabra; // null si modo 1vsMaquina
    private JLabel imagenAhorcado;
    private boolean modo1vs1;

    public GameWindow(String palabra, String jugadorAdivina, String jugadorPalabra) {
        this.palabra = palabra.toUpperCase();
        this.jugadorAdivina = jugadorAdivina;
        this.jugadorPalabra = jugadorPalabra;
        this.progreso = new StringBuilder("_".repeat(palabra.length()));
        this.errores = 0;
        this.modo1vs1 = (jugadorPalabra != null);

        initUI();
    }

    // Constructor modo 1vsMáquina
    public GameWindow(String palabra, String jugadorAdivina) {
        this(palabra, jugadorAdivina, null);
    }

    private void initUI() {
        setTitle("Ahorcado - Jugador: " + jugadorAdivina);
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panelTop = new JPanel(new BorderLayout());
        labelPalabra = new JLabel(progreso.toString(), SwingConstants.CENTER);
        labelPalabra.setFont(new Font("Monospaced", Font.BOLD, 40));
        panelTop.add(labelPalabra, BorderLayout.NORTH);

        labelErrores = new JLabel("Errores: 0", SwingConstants.CENTER);
        labelErrores.setFont(new Font("SansSerif", Font.PLAIN, 18));
        panelTop.add(labelErrores, BorderLayout.SOUTH);

        imagenAhorcado = new JLabel();
        imagenAhorcado.setHorizontalAlignment(SwingConstants.CENTER);
        actualizarImagenAhorcado();

        JPanel panelInferior = new JPanel(new FlowLayout());

        JLabel labelLetra = new JLabel("Ingresa una letra:");
        campoLetra = new JTextField(2);
        campoLetra.setFont(new Font("SansSerif", Font.PLAIN, 24));

        JButton btnProbar = new JButton("Probar letra");
        btnProbar.setFocusPainted(false);
        btnProbar.addActionListener(e -> probarLetra());

        panelInferior.add(labelLetra);
        panelInferior.add(campoLetra);
        panelInferior.add(btnProbar);

        add(panelTop, BorderLayout.NORTH);
        add(imagenAhorcado, BorderLayout.CENTER);
        add(panelInferior, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void probarLetra() {
        String letra = campoLetra.getText().toUpperCase();
        campoLetra.setText("");

        if (letra.length() != 1 || !letra.matches("[A-ZÑ]")) {
            JOptionPane.showMessageDialog(this, "Ingrese una letra válida (A-Z, Ñ).");
            return;
        }

        boolean acierto = false;
        for (int i = 0; i < palabra.length(); i++) {
            if (palabra.charAt(i) == letra.charAt(0)) {
                progreso.setCharAt(i, letra.charAt(0));
                acierto = true;
            }
        }

        if (!acierto) {
            errores++;
        }

        labelPalabra.setText(progreso.toString());
        labelErrores.setText("Errores: " + errores);
        actualizarImagenAhorcado();

        if (progreso.toString().equals(palabra)) {
            if (modo1vs1) {
                PuntajeService.actualizarPuntaje(jugadorAdivina, 10);
                PuntajeService.actualizarPuntaje(jugadorPalabra, -10);
                JOptionPane.showMessageDialog(this, "¡Ganaste, " + jugadorAdivina + "! La palabra era: " + palabra + ".\nLe has robado 10 puntos a " + jugadorPalabra + ".");
            } else {
                PuntajeService.actualizarPuntaje(jugadorAdivina, 10);
                JOptionPane.showMessageDialog(this, "¡Ganaste! La palabra era: " + palabra);
            }
            dispose();
            new LoginForm();
        } else if (errores >= 6) {
            if (modo1vs1) {
                PuntajeService.actualizarPuntaje(jugadorAdivina, -10);
                PuntajeService.actualizarPuntaje(jugadorPalabra, 10);
                JOptionPane.showMessageDialog(this, "¡Perdiste, " + jugadorAdivina + "! La palabra era: " + palabra + ".\n" + jugadorPalabra + " te ha robado 10 puntos.");
            } else {
                PuntajeService.actualizarPuntaje(jugadorAdivina, -5);
                JOptionPane.showMessageDialog(this, "¡Perdiste! La palabra era: " + palabra);
            }
            dispose();
            new LoginForm();
        }
    }

    private void actualizarImagenAhorcado() {
        String ruta = "img/ahorcado" + errores + ".png";
        ImageIcon icon = new ImageIcon(ruta);
        Image img = icon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
        imagenAhorcado.setIcon(new ImageIcon(img));
    }
}
