package presentation.game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import business.game.CardGame.Cards;
import business.game.Roulette;
import business.game.Roulette.Bet.tBet;
import presentation.Controller;
import presentation.Event;

@SuppressWarnings("serial")
public class RouletteGUI extends JFrame {
	
	@SuppressWarnings("unused")
	private class RuletaRotada extends JPanel {
	    private Image image;
	    private double angle; // grados rad

	    public RuletaRotada() {
	    	ImageIcon icon = new ImageIcon("resources/icons/ruleta.png");
	        this.image = icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
	        this.angle = 0;
	        setPreferredSize(new Dimension(100, 100)); // tu tamaño exacto
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
	        this.angle = (this.angle + 1) % (2*Math.PI);
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
        
        //Zona ruleta
        ruleta = new RuletaRotada();
        mainPanel.add(ruleta);
        
        //botones play y giveUp();
        JPanel optionsPanel = new JPanel();
        optionsPanel.setLayout(new BoxLayout(optionsPanel, BoxLayout.Y_AXIS));
        mainPanel.add(optionsPanel);
        
        JButton playButton = new JButton();
        playButton.setIcon(new ImageIcon("")); //TODO icono play
        playButton.addActionListener(a -> playRoulette());
        optionsPanel.add(playButton);
        
        JButton backButton = new JButton("Abandonar");
        backButton.addActionListener((a) -> giveUp());
        optionsPanel.add(backButton);
        
        
        //botones de apuestas. 
        JPanel betsPanel = new JPanel(new GridLayout(2, 5, 1, 1));
        mainPanel.add(betsPanel);
        for(int i = 0; i < 10; i++) {
        	String bet = bets[i];
        	JButton betButton = new JButton(bet);
        	betButton.addActionListener((a) -> setBet(bet));
        	betsPanel.add(betButton);
        }
    }
    
    private void playRoulette() {
    	Controller.action(Event.ROULETTE_ROLL, bet);
    }
    
    private void setBet(String s) {
    	bet = s;
    }

    public void end(String message) {
        JOptionPane.showMessageDialog(this, message);
        Controller.action(Event.GAMES_PANEL, null); //TODO redirige al panel de juegos
        dispose();
    }

    private void giveUp() {
        int option = JOptionPane.showConfirmDialog(this,
                "¿Estás seguro de que quieres abandonar? \nSe perderá la partida",
                "Confirmar salida", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if(option == JOptionPane.OK_OPTION) end("Abandonaste la partida. Rendirse no es la solucion.");
    }

	public void showResult(int puntos, int winningNumber) {
		JOptionPane.showMessageDialog(this, (puntos == 0? "Perdiste. El número fue " + winningNumber : 
			"Ganaste con el " + winningNumber));
	}
}

