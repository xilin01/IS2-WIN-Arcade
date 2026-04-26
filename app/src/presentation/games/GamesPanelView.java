package presentation.games;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import business.ServiceLocator;
import business.game.tGame;
import business.player.SAPlayer;
import business.player.tPlayer;
import presentation.Controller;
import presentation.Event;
import presentation.user.PlayerView;

@SuppressWarnings("serial")
public class GamesPanelView extends JFrame {
    private String playerName;

    public GamesPanelView(String playerName) {
        super("Panel de juegos");
        this.playerName = playerName;
        initGUI();
    }

    private void initGUI() {
        setLayout(new BorderLayout());
        setSize(650, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        BackgroundPanel mainPanel = new BackgroundPanel("resources/icons/menu_juegos.png");
        mainPanel.setLayout(new BorderLayout());
        setContentPane(mainPanel);

        JPanel gamesListPanel = new JPanel();
        gamesListPanel.setLayout(new BoxLayout(gamesListPanel, BoxLayout.Y_AXIS));
        gamesListPanel.setOpaque(false);
        if(loadGames(gamesListPanel)) {
            JPanel headerPanel = new JPanel();
            headerPanel.setOpaque(false);
            headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
            JLabel title = new JLabel("Selecciona un juego");
            title.setFont(new Font("Arial", Font.BOLD, 28));
            title.setForeground(Color.WHITE);
            headerPanel.add(title);
            mainPanel.add(headerPanel, BorderLayout.NORTH);

            JScrollPane scrollPane = new JScrollPane(gamesListPanel);
            scrollPane.setOpaque(false);
            scrollPane.getViewport().setOpaque(false);
            scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));
            mainPanel.add(scrollPane, BorderLayout.CENTER);
        }
        
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        bottomPanel.setOpaque(false);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        JButton backButton = new JButton("<- Volver");
        backButton.addActionListener(e -> {
        	new PlayerView(playerName);
            dispose();
        });
        bottomPanel.add(backButton);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private boolean loadGames(JPanel gamesListPanel) {
        boolean hasGames = false;
        SAPlayer saPlayer = ServiceLocator.get(SAPlayer.class);
        tPlayer player = saPlayer.readPlayer(playerName);

        if(player.getInventory() != null && !player.getInventory().getGames().isEmpty()) {
        	hasGames = true;
            for(Map.Entry<tGame, Integer> entry: player.getInventory().getGames().entrySet()) {
                gamesListPanel.add(createGameCard(entry.getKey(), entry.getValue()));
                gamesListPanel.add(Box.createRigidArea(new Dimension(0, 15)));
            }
        }
        return hasGames;
    }

    private JPanel createGameCard(tGame game, int amount) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2, true), BorderFactory.createEmptyBorder(15, 20, 15, 20)));
        card.setBackground(new Color(0, 0, 0, 180));
        card.setMaximumSize(new Dimension(500, 80));

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setOpaque(false);
        JLabel nameLabel = new JLabel(game.getName() + " (" + game.getBasePoints() + " p)");
        nameLabel.setFont(new Font("Arial", Font.BOLD, 18));
        nameLabel.setForeground(Color.WHITE);
        JLabel amountLabel = new JLabel("x" + amount);
        amountLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        amountLabel.setForeground(Color.LIGHT_GRAY);
        infoPanel.add(nameLabel);
        infoPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        infoPanel.add(amountLabel);
        card.add(infoPanel, BorderLayout.CENTER);

        JButton playButton = new JButton("Jugar");
        playButton.addActionListener(e -> {
            if(game.getName().equals("Blackjack")) Controller.getInstance().action(Event.BLACKJACK_PLAY, game.getBasePoints());
            else Controller.getInstance().action(Event.ROULETTE_PLAY, game.getBasePoints());
            dispose();
        });
        card.add(playButton, BorderLayout.EAST);
        return card;
    }
}
