package presentation.user;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
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
import presentation.games.BackgroundPanel;

@SuppressWarnings("serial")
public class InventoryView extends JFrame {

    public InventoryView() {
        super("Inventario");
        initGUI();
    }

    private void initGUI() {
    	setResizable(false);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	
        BackgroundPanel background = new BackgroundPanel("resources/icons/zona_inventario.png");
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

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        topPanel.setOpaque(false);
        JLabel pointsLabel = new JLabel(Controller.getInstance().getUsername() + " (" + player.getPoints() + "pts)");
        pointsLabel.setFont(new Font("Arial", Font.BOLD, 18));
        pointsLabel.setForeground(Color.YELLOW);
        pointsLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        topPanel.add(pointsLabel);
        mainPanel.add(topPanel, BorderLayout.NORTH);
        
        JTabbedPane tabbedPane = new JTabbedPane();
        JPanel gamesPanel = new JPanel();
        gamesPanel.setLayout(new BoxLayout(gamesPanel, BoxLayout.Y_AXIS));
        gamesPanel.setOpaque(false);
        if(!inventory.getGames().isEmpty()) {
            for(Map.Entry<tGame, Integer> entry: inventory.getGames().entrySet()) gamesPanel.add(createGamePanel(entry.getKey(), entry.getValue()));
        }
        else {
            JPanel emptyPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            emptyPanel.setOpaque(false);
            JLabel label = new JLabel("No tienes juegos en tu inventario");
            label.setForeground(Color.WHITE);
            emptyPanel.add(label);
            gamesPanel.add(emptyPanel);
        }
        tabbedPane.addTab("Juegos", new JScrollPane(gamesPanel));
        JPanel productsPanel = new JPanel();
        productsPanel.setLayout(new BoxLayout(productsPanel, BoxLayout.Y_AXIS));
        productsPanel.setOpaque(false);
        if(!inventory.getProducts().isEmpty()) {
            for(Map.Entry<tProduct, Integer> entry: inventory.getProducts().entrySet()) productsPanel.add(createProductPanel(entry.getKey(), entry.getValue()));
        }
        else {
            JPanel emptyPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            emptyPanel.setOpaque(false);
            JLabel label = new JLabel("No has canjeado productos todavía");
            label.setForeground(Color.WHITE);
            emptyPanel.add(label);
            productsPanel.add(emptyPanel);
        }
        tabbedPane.addTab("Productos", new JScrollPane(productsPanel));
        mainPanel.add(tabbedPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        bottomPanel.setOpaque(false);
        JButton backButton = new JButton("Atrás");
        backButton.setPreferredSize(new Dimension(150, 40)); 
        backButton.setFont(new Font("Arial", Font.BOLD, 14));
        backButton.addActionListener(e -> {
        	Controller.getInstance().action(Event.OPEN_PLAYER_VIEW, null);
            dispose();
        });
        bottomPanel.add(backButton);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private JPanel createGamePanel(tGame game, int amount) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(255, 255, 255, 180));
        panel.setBorder(BorderFactory.createCompoundBorder( BorderFactory.createEmptyBorder(5, 5, 5, 5), BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1)));
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));

        JPanel infoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        infoPanel.setOpaque(false);
        JLabel titleLabel = new JLabel("   " + amount + "x " + game.getName() + " (" + game.getBasePoints() + "pts)");
        titleLabel.setFont(titleLabel.getFont().deriveFont(Font.BOLD, 14f));
        infoPanel.add(titleLabel);
        panel.add(infoPanel, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createProductPanel(tProduct prod, int amount) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(255, 255, 255, 180));
        panel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5), BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1)));
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));

        JPanel infoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        infoPanel.setOpaque(false);
        JLabel titleLabel = new JLabel("   " + amount + "x " + prod.getName());
        titleLabel.setFont(titleLabel.getFont().deriveFont(Font.BOLD, 14f));
        infoPanel.add(titleLabel);
        panel.add(infoPanel, BorderLayout.CENTER);
        return panel;
    }
}
