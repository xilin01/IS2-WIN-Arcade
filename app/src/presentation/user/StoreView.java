package presentation.user;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
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
import business.packages.tGamesPackage;
import business.product.tProduct;
import presentation.Controller;
import presentation.Event;
import presentation.games.BackgroundPanel;

@SuppressWarnings("serial")
public class StoreView extends JFrame {
    private JLabel pointsLabel;

    public StoreView() {
        super("Tienda");
        initGUI();
    }

    private void initGUI() {
        setResizable(false);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        BackgroundPanel background = new BackgroundPanel("resources/icons/zona_tienda.png");
        background.setLayout(new GridBagLayout());
        setContentPane(background);
        
        JPanel mainPanel = new JPanel();
        mainPanel.setPreferredSize(new Dimension(600, 750));
        mainPanel.setMinimumSize(new Dimension(500, 750));
        mainPanel.setOpaque(false);
        mainPanel.setLayout(new BorderLayout());
        background.add(mainPanel);

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        topPanel.setOpaque(false);
        pointsLabel = new JLabel(Controller.getInstance().getUsername() + " (" + Controller.getInstance().getPlayerPoints(Controller.getInstance().getUsername()) + "p)");
        pointsLabel.setFont(new Font("Arial", Font.BOLD, 18));
        pointsLabel.setForeground(Color.YELLOW);
        pointsLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        topPanel.add(pointsLabel);
        mainPanel.add(topPanel, BorderLayout.NORTH);

        JTabbedPane tabbedPane = new JTabbedPane();
        JPanel packagesPanel = new JPanel();
        packagesPanel.setLayout(new BoxLayout(packagesPanel, BoxLayout.Y_AXIS));
        packagesPanel.setOpaque(false);
        for(tGamesPackage pack: Controller.getInstance().getAllPackages()) packagesPanel.add(createPackagePanel(pack));
        tabbedPane.addTab("Paquetes de juegos", new JScrollPane(packagesPanel));
        JPanel productsPanel = new JPanel();
        productsPanel.setLayout(new BoxLayout(productsPanel, BoxLayout.Y_AXIS));
        productsPanel.setOpaque(false);
        for(tProduct prod: Controller.getInstance().getAllProducts()) productsPanel.add(createProductPanel(prod));
        tabbedPane.addTab("Productos", new JScrollPane(productsPanel));
        mainPanel.add(tabbedPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        bottomPanel.setOpaque(false);
        JButton backButton = new JButton("<- Volver");
        backButton.setPreferredSize(new Dimension(150, 40));
        backButton.setFont(new Font("Arial", Font.BOLD, 14));
        backButton.addActionListener(e -> {
            new PlayerView();
            dispose();
        });
        bottomPanel.add(backButton);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private JPanel createPackagePanel(tGamesPackage pack) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(255, 255, 255, 180));
        panel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5), BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1)));
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60 + pack.getGames().size() * 20));

        JPanel infoPanel = new JPanel();
        infoPanel.setOpaque(false);
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        JLabel titleLabel = new JLabel(pack.getName() + " (" + pack.getPrice() + "€)");
        titleLabel.setFont(titleLabel.getFont().deriveFont(Font.BOLD, 14f));
        infoPanel.add(titleLabel);
        for(Map.Entry<tGame, Integer> entry: pack.getGames().entrySet()) {
            tGame game = entry.getKey();
            JLabel gameLabel = new JLabel("   • " + entry.getValue() + "x " + game.getName() + " (" + game.getBasePoints() + "p)");
            
            gameLabel.setForeground(Color.DARK_GRAY);
            gameLabel.setFont(gameLabel.getFont().deriveFont(Font.ITALIC, 12f));
            infoPanel.add(gameLabel);
        }
        panel.add(infoPanel, BorderLayout.CENTER);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnPanel.setOpaque(false);
        JButton buyButton = new JButton("Comprar");
        buyButton.addActionListener(e -> Controller.getInstance().action(Event.BUY_PACKAGE, pack));
        btnPanel.add(buyButton);
        panel.add(btnPanel, BorderLayout.EAST);
        return panel;
    }

    private JPanel createProductPanel(tProduct prod) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(255, 255, 255, 180));
        panel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5), BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1)));
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));

        JPanel infoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        infoPanel.setOpaque(false);
        infoPanel.add(new JLabel(prod.getName() + " (" + prod.getPrice() + "p)"));
        panel.add(infoPanel, BorderLayout.CENTER);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnPanel.setOpaque(false);
        JButton buyButton = new JButton("Canjear");
        buyButton.addActionListener(e -> {
        	Controller.getInstance().action(Event.BUY_PRODUCT, prod);
        	pointsLabel.setText(Controller.getInstance().getUsername() + " (" + Controller.getInstance().getPlayerPoints(Controller.getInstance().getUsername()) + "p)");
        });
        btnPanel.add(buyButton);
        panel.add(btnPanel, BorderLayout.EAST);
        return panel;
    }
}
