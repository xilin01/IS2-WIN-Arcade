package presentation.game;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import business.game.Roulette;
import business.game.Roulette.Bet.tBet;
import presentation.Controller;
import presentation.Event;

@SuppressWarnings("serial")
public class RouletteGUI extends JFrame {

    private class RuletaRotada extends JPanel {
        private Image image;
        private double angle;

        public RuletaRotada() {
            ImageIcon icon = new ImageIcon("resources/icons/ruleta.png");
            this.image = icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            this.angle = 0;
            setPreferredSize(new Dimension(100, 100));
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            int w = getWidth();
            int h = getHeight();
            g2.translate(w / 2, h / 2);
            g2.rotate(angle);
            g2.drawImage(image, -w / 2, -h / 2, null);
            g2.dispose();
        }

        public void advance() {
            this.angle = (this.angle + 1) % (2 * Math.PI);
            repaint();
        }
    }

    private RuletaRotada ruleta;
    private static final String[] bets = Roulette.getBets();
    private tBet bet;

    public RouletteGUI() {
        super("Roulette");
        initGUI();
    }

    private void initGUI() {
        this.setVisible(true);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800, 600);

        BackgroundPanel mainPanel = new BackgroundPanel("");
        mainPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
        setContentPane(mainPanel);

        ruleta = new RuletaRotada();
        mainPanel.add(ruleta);

        JPanel optionsPanel = new JPanel();
        optionsPanel.setLayout(new BoxLayout(optionsPanel, BoxLayout.Y_AXIS));
        mainPanel.add(optionsPanel);

        JButton playButton = new JButton();
        playButton.setIcon(new ImageIcon("")); //TODO icono play
        playButton.addActionListener(a -> playRoulette());
        optionsPanel.add(playButton);

        JButton backButton = new JButton("Abandonar");
        backButton.addActionListener(a -> giveUp());
        optionsPanel.add(backButton);

        JPanel betsPanel = new JPanel(new GridLayout(2, 5, 1, 1));
        mainPanel.add(betsPanel);
        for (int i = 0; i < bets.length; i++) {
            String betName = bets[i];
            JButton betButton = new JButton(betName);
            betButton.addActionListener(a -> setBet(betName));
            betsPanel.add(betButton);
        }
    }

    private void setBet(String betName) {
        switch (betName) {
            case "Sencillo": {
                String input = JOptionPane.showInputDialog(this, "Número (0-36):");
                if (input == null) return;
                bet = new tBet(betName, new int[]{ Integer.parseInt(input.trim()) });
                break;
            }
            case "Calle": {
                String input = JOptionPane.showInputDialog(this, "3 números separados por coma (ej: 1,2,3):");
                if (input == null) return;
                String[] parts = input.split(",");
                bet = new tBet(betName, new int[]{
                    Integer.parseInt(parts[0].trim()),
                    Integer.parseInt(parts[1].trim()),
                    Integer.parseInt(parts[2].trim())
                });
                break;
            }
            case "Columna": {
                String input = JOptionPane.showInputDialog(this, "Columna (1-3):");
                if (input == null) return;
                bet = new tBet(betName, new int[]{ Integer.parseInt(input.trim()) });
                break;
            }
            case "Docena": {
                String input = JOptionPane.showInputDialog(this, "Docena (1-3):");
                if (input == null) return;
                bet = new tBet(betName, new int[]{ Integer.parseInt(input.trim()) });
                break;
            }
            default:
                bet = new tBet(betName, new int[]{});
        }
    }

    private void playRoulette() {
        if (bet == null) {
            JOptionPane.showMessageDialog(this, "Selecciona una apuesta primero.", "Sin apuesta", JOptionPane.WARNING_MESSAGE);
            return;
        }
        Controller.getInstance().action(Event.ROULETTE_ROLL, bet);
    }

    public void end(String message) {
        JOptionPane.showMessageDialog(this, message);
        Controller.getInstance().action(Event.GAMES_PANEL, null); //TODO redirige al panel de juegos
        dispose();
    }

    private void giveUp() {
        int option = JOptionPane.showConfirmDialog(this,
                "¿Estás seguro de que quieres abandonar? \nSe perderá la partida",
                "Confirmar salida", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (option == JOptionPane.OK_OPTION) end("Abandonaste la partida. Rendirse no es la solucion.");
    }

    public void showResult(int puntos, int winningNumber) {
        JOptionPane.showMessageDialog(this, puntos == 0
                ? "Perdiste. El número fue " + winningNumber
                : "Ganaste con el " + winningNumber);
    }
}
