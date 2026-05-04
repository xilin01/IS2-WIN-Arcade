package presentation.games;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import business.game.CardGame.Cards;
import cards.CARDS;
import images.IMAGES;
import presentation.Controller;
import presentation.Event;
import presentation.Theme;

@SuppressWarnings("serial")
public class BlackjackGUI extends JFrame {
    private static final Theme theme = Theme.getInstance();
    private static final int CARD_HEIGHT = 200;
    private static final int CARD_WIDTH = (int)(CARD_HEIGHT / 1.4);

    private JPanel croupierDeckPanel;
    private JPanel playerDeckPanel;
    private JLabel playerScore;
    private JLabel croupierScore;

    public BlackjackGUI(Cards croupierCard, List<Cards> playerDeck, int croupierPoints, int playerPoints) {
        super("Blackjack");
        initGUI(croupierCard, playerDeck, croupierPoints, playerPoints);
    }

    private void initGUI(Cards croupierCard, List<Cards> playerDeck, int croupierPoints, int playerPoints) {
    	JPanel mainPanel = theme.backgroundPanel(IMAGES.class.getResource("fondo_blackjack.png"));
        mainPanel.setLayout(new BorderLayout());
        setContentPane(mainPanel);

        croupierDeckPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 20));
        croupierDeckPanel.setPreferredSize(new Dimension(0, 400));
        croupierDeckPanel.setOpaque(false);
        mainPanel.add(croupierDeckPanel, BorderLayout.NORTH);

        playerDeckPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 20));
        playerDeckPanel.setPreferredSize(new Dimension(0, 250));
        playerDeckPanel.setOpaque(false);
        mainPanel.add(playerDeckPanel, BorderLayout.SOUTH);

        JPanel choiceButtonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 8));
        choiceButtonsPanel.setOpaque(false);
        mainPanel.add(choiceButtonsPanel, BorderLayout.CENTER);
        JButton hitButton = theme.primaryBtn("Pedir carta", () -> Controller.getInstance().action(Event.BLACKJACK_HIT, null));
        JButton standButton = theme.primaryBtn("Plantarse", () -> Controller.getInstance().action(Event.BLACKJACK_STAND, null));
        choiceButtonsPanel.add(hitButton);
        choiceButtonsPanel.add(standButton);

        JPanel backPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 12));
        backPanel.setPreferredSize(new Dimension(130, 0));
        backPanel.setOpaque(false);
        mainPanel.add(backPanel, BorderLayout.WEST);
        backPanel.add(theme.secondaryBtn("Atrás", this::giveUp));

        JPanel scoresAuxiliarPanel = new JPanel();
        scoresAuxiliarPanel.setOpaque(false);
        mainPanel.add(scoresAuxiliarPanel, BorderLayout.EAST);

        JPanel scoresPanel = new JPanel();
        scoresPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(theme.BORDER, 1), BorderFactory.createEmptyBorder(10, 14, 10, 14)));
        scoresPanel.setBackground(theme.BG_CARD);
        scoresPanel.setLayout(new BoxLayout(scoresPanel, BoxLayout.PAGE_AXIS));
        croupierScore = new JLabel();
        croupierScore.setFont(theme.BODY);
        croupierScore.setForeground(theme.TEXT);
        scoresPanel.add(croupierScore);
        playerScore = new JLabel();
        playerScore.setFont(theme.BODY);
        playerScore.setForeground(theme.GOLD);
        scoresPanel.add(playerScore);
        scoresAuxiliarPanel.add(scoresPanel);

        showInitDecks(croupierCard, playerDeck);
        setScores(croupierPoints, playerPoints, true);

        setVisible(true);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
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
        ImageIcon icon = new ImageIcon(CARDS.class.getResource(cardName + ".png"));
        Image img = icon.getImage().getScaledInstance(CARD_WIDTH, CARD_HEIGHT, Image.SCALE_SMOOTH);
        
        deckPanel.add(new JLabel(new ImageIcon(img)));
    }

    public void end(String message) {
        theme.showInfo(this, message);
        Controller.getInstance().action(Event.OPEN_GAMES_PANEL, null);
        dispose();
    }

    private void giveUp() {
        if(theme.confirm(this, "¿Estás seguro de que quieres abandonar?")) {
            Controller.getInstance().action(Event.OPEN_GAMES_PANEL, null);
            dispose();
        }
    }
}
