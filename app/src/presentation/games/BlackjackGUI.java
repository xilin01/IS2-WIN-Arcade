package presentation.games;

import java.awt.BorderLayout;
import java.awt.Color;
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
import business.game.CardGame.Cards;
import presentation.Controller;
import presentation.Event;

@SuppressWarnings("serial")
public class BlackjackGUI extends JFrame {
    private static final int CARD_HEIGHT = 200;
    private static final int CARD_WIDTH = (int)(CARD_HEIGHT / 1.4);
    private static final Font FONT = new Font("", Font.PLAIN, 15);

    private JPanel croupierDeckPanel;
    private JPanel playerDeckPanel;
    private JLabel playerScore;
    private JLabel croupierScore;

    public BlackjackGUI(Cards croupierCard, List<Cards> playerDeck, int croupierPoints, int playerPoints) {
        super("Blackjack");
        initGUI(croupierCard, playerDeck, croupierPoints, playerPoints);
    }

    private void initGUI(Cards croupierCard, List<Cards> playerDeck, int croupierPoints, int playerPoints) {
        BackgroundPanel mainPanel = new BackgroundPanel("resources/icons/fondo_juegos.png");
        mainPanel.setLayout(new BorderLayout());
        setContentPane(mainPanel);

        // Mano del croupier
        croupierDeckPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 20));
        croupierDeckPanel.setPreferredSize(new Dimension(0, 250));
        croupierDeckPanel.setOpaque(false);
        mainPanel.add(croupierDeckPanel, BorderLayout.NORTH);

        playerDeckPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 20));
        playerDeckPanel.setPreferredSize(new Dimension(0, 250));
        playerDeckPanel.setOpaque(false);
        mainPanel.add(playerDeckPanel, BorderLayout.SOUTH);

        JPanel choiceButtonsPanel = new JPanel(new FlowLayout());
        choiceButtonsPanel.setOpaque(false);
        mainPanel.add(choiceButtonsPanel, BorderLayout.CENTER);

        JButton hitButton = new JButton("Pedir carta");
        hitButton.addActionListener(a -> Controller.getInstance().action(Event.BLACKJACK_HIT, null));
        choiceButtonsPanel.add(hitButton);

        JButton standButton = new JButton("Plantarse");
        standButton.addActionListener(a -> Controller.getInstance().action(Event.BLACKJACK_STAND, null));
        choiceButtonsPanel.add(standButton);

        JPanel backPanel = new JPanel();
        backPanel.setPreferredSize(new Dimension(100, 0));
        backPanel.setOpaque(false);
        mainPanel.add(backPanel, BorderLayout.WEST);

        JButton backButton = new JButton("Abandonar");
        backButton.addActionListener(a -> giveUp());
        backPanel.add(backButton);

        JPanel scoresPanel = new JPanel();
        scoresPanel.setLayout(new BoxLayout(scoresPanel, BoxLayout.PAGE_AXIS));
        scoresPanel.setPreferredSize(new Dimension(100, 0));
        scoresPanel.setOpaque(false);
        mainPanel.add(scoresPanel, BorderLayout.EAST);

        croupierScore = new JLabel();
        croupierScore.setFont(FONT);
        croupierScore.setForeground(Color.WHITE);
        scoresPanel.add(croupierScore);

        playerScore = new JLabel();
        playerScore.setFont(FONT);
        playerScore.setForeground(Color.WHITE);
        scoresPanel.add(playerScore);

        showInitDecks(croupierCard, playerDeck);
        setScores(croupierPoints, playerPoints, true);
        
        this.setVisible(true);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800, 600);
    }

    private void showInitDecks(Cards croupierCard, List<Cards> playerDeck) {
        showCard(croupierDeckPanel, croupierCard);
        showCard(croupierDeckPanel, null);
        for(Cards c: playerDeck) showCard(playerDeckPanel, c);
    }

    public void update(List<Cards> croupierDeck, List<Cards> playerDeck, int croupierPoints, int playerPoints, boolean showFullCroupier) {
        showDecks(croupierDeck, playerDeck, showFullCroupier);
        setScores(croupierPoints, playerPoints, showFullCroupier);
    }

    private void showDecks(List<Cards> croupierDeck, List<Cards> playerDeck, boolean showFullCroupier) {
        if(showFullCroupier) {
            croupierDeckPanel.removeAll();
            for(Cards c: croupierDeck) showCard(croupierDeckPanel, c);
            croupierDeckPanel.revalidate(); 
            croupierDeckPanel.repaint();
        }
        playerDeckPanel.removeAll();
        for(Cards c: playerDeck) showCard(playerDeckPanel, c);
        playerDeckPanel.revalidate();
        playerDeckPanel.repaint();
    }

    private void setScores(int croupierPoints, int playerPoints, boolean showFullCroupier) {
        if(showFullCroupier) {
            croupierScore.setText("Croupier: " + croupierPoints);
            croupierScore.repaint();
        }
        playerScore.setText("Player: " + playerPoints);
        playerScore.repaint();
    }

    private void showCard(JPanel deckPanel, Cards card) {
        String cardName = (card == null ? "J" : ""+card);
        ImageIcon icon = new ImageIcon("resources/cards/"+cardName+".png");
        Image img = icon.getImage().getScaledInstance(CARD_WIDTH, CARD_HEIGHT, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(img);
        
        deckPanel.add(new JLabel(scaledIcon));
    }
    
    public void end(String message) {
        JOptionPane.showMessageDialog(this, message);
        new GamesPanelView();
        dispose();
    }
    
    private void giveUp() {
        int option = JOptionPane.showConfirmDialog(this, "¿Estás seguro de que quieres abandonar?\n La partida se perderá",
                "Confirmar", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if(option == JOptionPane.OK_OPTION) {
        	new GamesPanelView();
            dispose();
        }
    }
}
