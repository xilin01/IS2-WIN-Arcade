package presentation;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import business.CardGame.Cards;

@SuppressWarnings("serial")
public class BlackJackGUI extends JFrame{
	
	private JPanel croupierDeckPanel;
	private JPanel playerDeckPanel;
	
	private JLabel playerScore;
	private JLabel croupierScore;
	private Font font = new Font("", Font.PLAIN, 15);
	
	public BlackJackGUI(Cards croupierCard, List<Cards> playerDeck, int croupierPoints, int playerPoints) {
		super("Black Jack");
		initGUI(croupierCard, playerDeck, croupierPoints, playerPoints);
	}
	
	private void initGUI(Cards croupierCard, List<Cards> playerDeck, int croupierPoints, int playerPoints) {
		this.setLayout(new BorderLayout());
		this.setBounds(100, 100, 650, 400);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		
		//Mano del croupier. 
		croupierDeckPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 20));
		croupierDeckPanel.setPreferredSize(new Dimension(50, 200));
		add(croupierDeckPanel, BorderLayout.NORTH);
		
		playerDeckPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 20));
		playerDeckPanel.setPreferredSize(new Dimension(50, 200));
		add(playerDeckPanel, BorderLayout.SOUTH);
		
		JPanel choiceButtonsPanel = new JPanel();
		choiceButtonsPanel.setLayout(new BoxLayout(choiceButtonsPanel, BoxLayout.LINE_AXIS));
		add(choiceButtonsPanel, BorderLayout.CENTER);
		
		JButton hitButton = new JButton("Pedir carta");
		hitButton.addActionListener((a) -> Controller.action(Event.BLACKJACK_HIT, null));
		choiceButtonsPanel.add(hitButton);
		
		JButton standButton = new JButton("Plantarse");
		standButton.addActionListener((a) -> Controller.action(Event.BLACKJACK_STAND, null));
		choiceButtonsPanel.add(standButton);
		
		JButton backButton = new JButton("Abandonar");
		backButton.addActionListener((a) -> Controller.action(Event.GAMES_PANEL, null));
		add(backButton, BorderLayout.WEST);
		
		
		JPanel scoresPanel = new JPanel();
		scoresPanel.setLayout(new BoxLayout(scoresPanel, BoxLayout.PAGE_AXIS));
		add(scoresPanel, BorderLayout.EAST);
		
		playerScore = new JLabel();
		playerScore.setFont(font);
		scoresPanel.add(playerScore);
		
		croupierScore = new JLabel();
		croupierScore.setFont(font);
		scoresPanel.add(croupierScore);
		
		showInitDecks(croupierCard, playerDeck);
		setScores(croupierPoints, playerPoints);
	}
	
	private void showInitDecks(Cards croupierCard, List<Cards> playerDeck){
		showCard(croupierDeckPanel, croupierCard);
		showCard(croupierDeckPanel, Cards.J);
		for(Cards c: playerDeck) showCard(playerDeckPanel, c);
	}
	
	public void showDecks(List<Cards> croupierDeck, List<Cards> playerDeck) {
		croupierDeckPanel.removeAll();
		playerDeckPanel.removeAll();
		for(Cards c: croupierDeck) showCard(croupierDeckPanel, c);
		for(Cards c: playerDeck) showCard(playerDeckPanel, c);
	}
	
	public void setScores(int croupierPoints, int playerPoints) {
		croupierScore.setText("Croupier: " + croupierPoints);
		playerScore.setText("Player: " + playerPoints);
	}
	
	private void showCard(JPanel deckPanel, Cards card) {
		ImageIcon icon = new ImageIcon("resources/cards/"+card+".png");
		Image img = icon.getImage().getScaledInstance(50, 200, Image.SCALE_SMOOTH);
		ImageIcon scaledIcon = new ImageIcon(img);
        deckPanel.add(new JLabel(scaledIcon));
	}

	public void end(String message) {
		JOptionPane.showMessageDialog(this, message);
	}
	
}
