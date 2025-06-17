import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class ModeSelectionWindow extends JFrame {
    private String jugador;

    public ModeSelectionWindow(String jugador) {
        this.jugador = jugador;
        setTitle("Modo de Juego - " + jugador);
        setSize(300, 180);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JButton btn1vsMaquina = new JButton("Jugar contra la máquina");
        JButton btn1vs1 = new JButton("Jugar 1 vs 1");

        btn1vsMaquina.addActionListener(e -> {
            String palabraAleatoria = PalabraService.obtenerPalabraAleatoria();
            dispose();
            new GameWindow(palabraAleatoria, jugador);
        });

        btn1vs1.addActionListener(e -> {
            dispose();
            new LoginJugador2Window(jugador);
        });

        setLayout(new GridLayout(3,1,10,10));
        add(new JLabel("Selecciona modo de juego:", SwingConstants.CENTER));
        add(btn1vsMaquina);
        add(btn1vs1);

        setVisible(true);
    }
}
