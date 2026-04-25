package presentation.game;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import business.game.Roulette;
import business.game.Roulette.Bet.Bets;
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
    
    private class ImputDialog extends JDialog{
    	private Bets betName;
    	private DefaultComboBoxModel <String> select;
    	private JLabel text;
    	
    	public ImputDialog(Bets betName) {
    		this.betName = betName;
    		select = new DefaultComboBoxModel<>();
    		text = new JLabel();
    		initGUI();
    	}
    	
    	private void initGUI() {
    		switch (betName){
    		case Sencillo:{
    			for(int i = 0; i < 36; i++) select.addElement(""+i); 
    			text.setText("Elige el número (del 0 al 36)");
    			break;
    		}
            case Calle:{
            	for(int i = 1; i <= 34; i+=3) select.addElement(i+","+(i+1)+","+(i+2)); 
            	text.setText("Elige calle de entre las opciones");
            	break;
            }
            case Columna: 
            case Docena: {
            	for(int i = 1; i <= 3; i++) select.addElement(""+i); 
            	text.setText("Elige entre una de las tres");
            	break;
            }
			default: break;
    		}
  
    		setTitle("Panel de seleccion");
    		JPanel mainPanel = new JPanel();
    		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
    		setContentPane(mainPanel);
    		
    		mainPanel.add(text);
    		mainPanel.add(new JComboBox<>(select));
    		JButton goButton = new JButton("Listo");
    		goButton.addActionListener(a -> ready());
    	}
    	
    	private void ready() {
    		switch (betName) {
    		case Columna:
    		case Docena:
            case Sencillo: {
                bet = new tBet(betName, new int[]{ (Integer)select.getSelectedItem() });
                break;
            }
            case Calle: {
                String[] parts = ((String)select.getSelectedItem()).split(",");
                bet = new tBet(betName, new int[]{ Integer.parseInt(parts[0]),
                    Integer.parseInt(parts[1]),Integer.parseInt(parts[2])});
                break;
            }
			default: break;
    		}
    		setVisible(false);
    		dispose();
    	}

    }

    private RuletaRotada ruleta;
    private static final Bets[] bets = Bets.values();
    private tBet bet;
    JButton playButton;

    public RouletteGUI() {
        super("Roulette");
        initGUI();
    }

    private void initGUI() {
        this.setVisible(true);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800, 600);

        BackgroundPanel mainPanel = new BackgroundPanel("resources/icons/fondo_blackjack.png");
        mainPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
        setContentPane(mainPanel);

        ruleta = new RuletaRotada();
        mainPanel.add(ruleta);

        JPanel optionsPanel = new JPanel(new GridLayout(2, 1, 20, 20));
        mainPanel.add(optionsPanel);

        playButton = new JButton();
        playButton.setIcon(new ImageIcon("resources/icons/play.png")); 
        playButton.setEnabled(false);
        playButton.addActionListener(a -> playRoulette());
        optionsPanel.add(playButton);

        JButton backButton = new JButton("Abandonar");
        backButton.addActionListener(a -> giveUp());
        optionsPanel.add(backButton);

        JPanel betsPanel = new JPanel(new GridLayout(5, 2, 1, 1));
        mainPanel.add(betsPanel);
        for (int i = 0; i < bets.length; i++) {
            Bets betName = bets[i];
            JButton betButton = new JButton(""+betName);
            betButton.addActionListener(a -> setBet(betName));
            betsPanel.add(betButton);
        }
    }

    private void setBet(Bets betName) {
        switch (betName) {
            case Sencillo:
            case Calle: 
            case Columna: 
            case Docena: new ImputDialog(betName); break;
            default: bet = new tBet(betName, new int[]{}); break;
        }
        playButton.setEnabled(true);
    }

    private void playRoulette() { //en setBet se activa el boton. 
        Controller.getInstance().action(Event.ROULETTE_ROLL, bet);
        playButton.setEnabled(false);
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
