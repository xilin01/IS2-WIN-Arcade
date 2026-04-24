package presentation.game;

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
public class BlackJackGUI extends JFrame {
    private static final int cardHeight = 200;
    private static final int cardWidth = (int) (cardHeight / 1.4);

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
        this.setVisible(true);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800, 600);

        BackgroundPanel mainPanel = new BackgroundPanel("resources/icons/fondo_blackjack.png");
        mainPanel.setLayout(new BorderLayout());
        setContentPane(mainPanel);

        //Mano del croupier.
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
        hitButton.addActionListener((a) -> Controller.getInstance().action(Event.BLACKJACK_HIT, null));
        choiceButtonsPanel.add(hitButton);

        JButton standButton = new JButton("Plantarse");
        standButton.addActionListener((a) -> Controller.getInstance().action(Event.BLACKJACK_STAND, null));
        choiceButtonsPanel.add(standButton);

        JPanel backPanel = new JPanel();
        backPanel.setPreferredSize(new Dimension(100, 0));
        backPanel.setOpaque(false);
        mainPanel.add(backPanel, BorderLayout.WEST);

        JButton backButton = new JButton("Abandonar");
        backButton.addActionListener((a) -> giveUp());
        backPanel.add(backButton);

        JPanel scoresPanel = new JPanel();
        scoresPanel.setLayout(new BoxLayout(scoresPanel, BoxLayout.PAGE_AXIS));
        scoresPanel.setPreferredSize(new Dimension(100, 0));
        scoresPanel.setOpaque(false);
        mainPanel.add(scoresPanel, BorderLayout.EAST);

        croupierScore = new JLabel();
        croupierScore.setFont(font);
        croupierScore.setForeground(Color.WHITE);
        scoresPanel.add(croupierScore);

        playerScore = new JLabel();
        playerScore.setFont(font);
        playerScore.setForeground(Color.WHITE);
        scoresPanel.add(playerScore);

        showInitDecks(croupierCard, playerDeck);
        setScores(croupierPoints, playerPoints, true);
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
            croupierDeckPanel.revalidate(); croupierDeckPanel.repaint();
        }

        playerDeckPanel.removeAll();
        for(Cards c: playerDeck) showCard(playerDeckPanel, c);
        playerDeckPanel.revalidate(); playerDeckPanel.repaint();
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
        Image img = icon.getImage().getScaledInstance(cardWidth, cardHeight, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(img);
        deckPanel.add(new JLabel(scaledIcon));
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
        if(option == JOptionPane.OK_OPTION) end("Abandonaste la partida. Rendirse no es la solucion.");
    }
}
