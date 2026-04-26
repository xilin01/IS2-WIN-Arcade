package presentation.user;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import business.ServiceLocator;
import business.game.tGame;
import business.player.SAPlayer;
import business.player.tPlayer;
import business.product.tProduct;

@SuppressWarnings("serial")
public class InventoryView extends JFrame {
    private String playerName;

    public InventoryView(String playerName) {
        super("Inventario");
        this.playerName = playerName;
        initGUI();
    }

    private void initGUI() {
        setLayout(new BorderLayout());
        setSize(500, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        tPlayer.tInventory inventory = ServiceLocator.get(SAPlayer.class).readPlayer(playerName).getInventory();
        JTabbedPane tabbedPane = new JTabbedPane();
        
        JPanel gamesPanel = new JPanel();
        gamesPanel.setLayout(new BoxLayout(gamesPanel, BoxLayout.Y_AXIS));
        if(!inventory.getGames().isEmpty()) {
            for(Map.Entry<tGame, Integer> entry: inventory.getGames().entrySet()) gamesPanel.add(createGamePanel(entry.getKey(), entry.getValue()));
        } 
        else {
            JPanel emptyPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            
            emptyPanel.add(new JLabel("No tienes juegos en tu inventario..."));
            gamesPanel.add(emptyPanel);
        }
        tabbedPane.addTab("Juegos", new JScrollPane(gamesPanel));

        JPanel productsPanel = new JPanel();
        productsPanel.setLayout(new BoxLayout(productsPanel, BoxLayout.Y_AXIS));
        if(!inventory.getProducts().isEmpty()) {
            for(Map.Entry<tProduct, Integer> entry: inventory.getProducts().entrySet()) productsPanel.add(createProductPanel(entry.getKey(), entry.getValue()));
        } 
        else {
            JPanel emptyPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            
            emptyPanel.add(new JLabel("No has canjeado productos todavía..."));
            productsPanel.add(emptyPanel);
        }
        tabbedPane.addTab("Productos", new JScrollPane(productsPanel));

        add(tabbedPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton backButton = new JButton("<- Volver");
        backButton.addActionListener(e -> {
            new PlayerView(playerName);
            dispose();
        });
        bottomPanel.add(backButton);
        add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private JPanel createGamePanel(tGame game, int amount) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createCompoundBorder( BorderFactory.createEmptyBorder(5, 5, 5, 5), BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1)));
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));

        JPanel infoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel titleLabel = new JLabel("   " + amount + "x " + game.getName() + " (" + game.getBasePoints() + "p)");
        titleLabel.setFont(titleLabel.getFont().deriveFont(Font.BOLD, 14f));
        infoPanel.add(titleLabel);
        panel.add(infoPanel, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createProductPanel(tProduct prod, int amount) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5), BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1)));
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));

        JPanel infoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel titleLabel = new JLabel("   " + amount + "x " + prod.getName());
        titleLabel.setFont(titleLabel.getFont().deriveFont(Font.BOLD, 14f));
        infoPanel.add(titleLabel);
        panel.add(infoPanel, BorderLayout.CENTER);
        return panel;
    }
}
