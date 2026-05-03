package presentation.user;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import business.game.tGame;
import business.player.tPlayer;
import business.product.tProduct;
import presentation.Controller;
import presentation.Event;
import presentation.Theme;
import presentation.games.BackgroundPanel;

@SuppressWarnings("serial")
public class InventoryView extends JFrame {
    private static final Theme theme = Theme.getInstance();

    public InventoryView() {
        super("Inventario");
        initGUI();
    }

    private void initGUI() {
        setResizable(false);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        BackgroundPanel background = new BackgroundPanel("resources/icons/menu_inventario.png");
        background.setLayout(new GridBagLayout());
        setContentPane(background);

        JPanel mainPanel = new JPanel();
        mainPanel.setPreferredSize(new Dimension(600, 750));
        mainPanel.setMinimumSize(new Dimension(500, 750));
        mainPanel.setOpaque(false);
        mainPanel.setLayout(new BorderLayout());
        background.add(mainPanel, new GridBagConstraints());

        tPlayer player = Controller.getInstance().getPlayer(Controller.getInstance().getUsername());
        tPlayer.tInventory inventory = player.getInventory();

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 8));
        topPanel.setOpaque(false);
        JLabel pointsLabel = new JLabel(Controller.getInstance().getUsername() + " (" + player.getPoints() + "pts)");
        pointsLabel.setFont(theme.HEADING);
        pointsLabel.setForeground(theme.GOLD);
        pointsLabel.setBorder(BorderFactory.createEmptyBorder(6, 10, 6, 10));
        topPanel.add(pointsLabel);
        mainPanel.add(topPanel, BorderLayout.NORTH);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(theme.BODY);
        tabbedPane.setBackground(theme.BG_CARD);
        tabbedPane.setForeground(theme.TEXT);

        JPanel gamesPanel = new JPanel();
        gamesPanel.setLayout(new BoxLayout(gamesPanel, BoxLayout.Y_AXIS));
        gamesPanel.setBackground(theme.BG);
        gamesPanel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        if(!inventory.getGames().isEmpty()) {
            for(Map.Entry<tGame, Integer> entry: inventory.getGames().entrySet()) gamesPanel.add(createItemPanel(entry.getValue() + "x " + entry.getKey().getName() + " (" + entry.getKey().getBasePoints() + "pts)"));
        }
        else {
            gamesPanel.add(emptyLabel("No tienes juegos en tu inventario"));
        }
        JScrollPane gamesScroll = new JScrollPane(gamesPanel);
        gamesScroll.setBorder(BorderFactory.createEmptyBorder());
        gamesScroll.getViewport().setBackground(theme.BG);
        tabbedPane.addTab("Juegos", gamesScroll);

        JPanel productsPanel = new JPanel();
        productsPanel.setLayout(new BoxLayout(productsPanel, BoxLayout.Y_AXIS));
        productsPanel.setBackground(theme.BG);
        productsPanel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        if(!inventory.getProducts().isEmpty()) {
            for(Map.Entry<tProduct, Integer> entry: inventory.getProducts().entrySet()) productsPanel.add(createItemPanel(entry.getValue() + "x " + entry.getKey().getName()));
        }
        else {
            productsPanel.add(emptyLabel("No has canjeado productos todavía"));
        }
        JScrollPane productsScroll = new JScrollPane(productsPanel);
        productsScroll.setBorder(BorderFactory.createEmptyBorder());
        productsScroll.getViewport().setBackground(theme.BG);
        tabbedPane.addTab("Productos", productsScroll);

        mainPanel.add(tabbedPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        bottomPanel.setOpaque(false);
        bottomPanel.add(theme.secondaryBtn("Atrás", () -> {
            Controller.getInstance().action(Event.OPEN_PLAYER_VIEW, null);
            dispose();
        }));
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private JPanel createItemPanel(String text) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(theme.BG_ITEM);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createEmptyBorder(4, 4, 4, 4),
            BorderFactory.createLineBorder(theme.BORDER, 1)
        ));
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 52));
        JLabel label = new JLabel("  " + text);
        label.setFont(theme.BODY);
        label.setForeground(theme.TEXT);
        panel.add(label, BorderLayout.CENTER);
        return panel;
    }

    private JPanel emptyLabel(String text) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel.setOpaque(false);
        JLabel label = new JLabel(text);
        label.setForeground(theme.TEXT_MUTED);
        label.setFont(theme.BODY);
        panel.add(label);
        return panel;
    }
}
