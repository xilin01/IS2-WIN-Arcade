package presentation.games;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import business.game.tGame;
import business.player.tPlayer;
import images.IMAGES;
import presentation.Controller;
import presentation.Event;
import presentation.Theme;

@SuppressWarnings("serial")
public class GamesPanelView extends JFrame {
    private static final Theme theme = Theme.getInstance();

    public GamesPanelView() {
        super("Menú de juegos");
        initGUI();
    }

    private void initGUI() {
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        JPanel background = theme.backgroundPanel(IMAGES.class.getResource("menu_juegos.png"));
        background.setLayout(new BorderLayout());
        setContentPane(background);

        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(theme.BG_CARD);
        topBar.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, theme.GOLD), BorderFactory.createEmptyBorder(14, 20, 14, 16)));

        JLabel title = new JLabel("Selecciona un juego");
        title.setFont(theme.HEADING);
        title.setForeground(theme.TEXT);
        topBar.add(title, BorderLayout.WEST);

        JPanel rightInfo = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        rightInfo.setOpaque(false);
        JLabel userPointsLabel = new JLabel(Controller.getInstance().getUsername() + " | " + Controller.getInstance().getPlayerPoints(Controller.getInstance().getUsername()) + "pts   ");
        userPointsLabel.setFont(theme.BODY);
        userPointsLabel.setForeground(theme.GOLD);
        rightInfo.add(userPointsLabel);
        JButton backBtn = theme.secondaryBtn("Atrás", () -> {
            Controller.getInstance().action(Event.OPEN_PLAYER_VIEW, null);
            dispose();
        });
        backBtn.setPreferredSize(new Dimension(110, 36));
        rightInfo.add(backBtn);
        topBar.add(rightInfo, BorderLayout.EAST);
        background.add(topBar, BorderLayout.NORTH);

        JPanel gamesListPanel = new JPanel();
        gamesListPanel.setLayout(new BoxLayout(gamesListPanel, BoxLayout.Y_AXIS));
        gamesListPanel.setOpaque(false);
        gamesListPanel.setBorder(BorderFactory.createEmptyBorder(20, 60, 20, 60));

        tPlayer player = Controller.getInstance().getPlayer(Controller.getInstance().getUsername());
        if(player.getInventory() != null && !player.getInventory().getGames().isEmpty()) {
            for(Map.Entry<tGame, Integer> entry: player.getInventory().getGames().entrySet()) {
                gamesListPanel.add(createGameCard(entry.getKey(), entry.getValue()));
                gamesListPanel.add(Box.createRigidArea(new Dimension(0, 12)));
            }
        }
        else {
            JPanel emptyPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            emptyPanel.setOpaque(false);
            JLabel emptyLabel = new JLabel("No tienes juegos en tu inventario");
            emptyLabel.setFont(theme.HEADING);
            emptyLabel.setForeground(theme.TEXT_MUTED);
            emptyPanel.add(emptyLabel);
            gamesListPanel.add(emptyPanel);
        }

        JScrollPane scrollPane = new JScrollPane(gamesListPanel);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        background.add(scrollPane, BorderLayout.CENTER);

        setVisible(true);
    }

    private JPanel createGameCard(tGame game, int amount) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(theme.BG_CARD);
        card.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(theme.BORDER, 1), BorderFactory.createEmptyBorder(14, 20, 14, 16)));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setOpaque(false);

        JLabel nameLabel = new JLabel(game.getName());
        nameLabel.setFont(theme.HEADING);
        nameLabel.setForeground(theme.TEXT);
        infoPanel.add(nameLabel);
        infoPanel.add(Box.createRigidArea(new Dimension(0, 4)));

        JLabel detailLabel = new JLabel(game.getBasePoints() + " puntos base  ·  x" + amount + " disponibles");
        detailLabel.setFont(theme.BODY);
        detailLabel.setForeground(theme.TEXT_MUTED);
        infoPanel.add(detailLabel);
        card.add(infoPanel, BorderLayout.CENTER);

        JButton playButton = theme.primaryBtn("Jugar", () -> {
            if(game.getName().equals(tGame.ROULETTE)) Controller.getInstance().action(Event.ROULETTE_PLAY, game.getBasePoints());
            else Controller.getInstance().action(Event.BLACKJACK_PLAY, game.getBasePoints());
            dispose();
        });
        playButton.setPreferredSize(new Dimension(100, 40));
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        btnPanel.setOpaque(false);
        btnPanel.add(playButton);
        card.add(btnPanel, BorderLayout.EAST);
        return card;
    }
}
