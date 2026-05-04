package presentation.user;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
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
import images.IMAGES;
import presentation.Controller;
import presentation.Event;
import presentation.Theme;

@SuppressWarnings("serial")
public class StoreView extends JFrame {
    private static final Theme theme = Theme.getInstance();
    
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

        JPanel background = theme.backgroundPanel(IMAGES.class.getResource("menu_tienda.png"));
        background.setLayout(new GridBagLayout());
        setContentPane(background);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setPreferredSize(new Dimension(640, 780));
        mainPanel.setMinimumSize(new Dimension(500, 600));
        mainPanel.setBackground(theme.BG_CARD);
        mainPanel.setBorder(BorderFactory.createLineBorder(theme.BORDER, 1));
        background.add(mainPanel);

        // Top bar
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(theme.BG_CARD);
        topBar.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, theme.GOLD), BorderFactory.createEmptyBorder(14, 18, 14, 14)));

        JPanel userInfo = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        userInfo.setOpaque(false);
        JLabel userLabel = new JLabel(Controller.getInstance().getUsername());
        userLabel.setFont(theme.HEADING);
        userLabel.setForeground(theme.TEXT);
        pointsLabel = new JLabel(Controller.getInstance().getPlayerPoints(Controller.getInstance().getUsername()) + " pts");
        pointsLabel.setFont(theme.HEADING);
        pointsLabel.setForeground(theme.GOLD);
        userInfo.add(userLabel);
        userInfo.add(pointsLabel);
        topBar.add(userInfo, BorderLayout.CENTER);

        JButton backBtn = theme.secondaryBtn("Volver", () -> {
            Controller.getInstance().action(Event.OPEN_PLAYER_VIEW, null);
            dispose();
        });
        backBtn.setPreferredSize(new Dimension(110, 36));
        JPanel backPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        backPanel.setOpaque(false);
        backPanel.add(backBtn);
        topBar.add(backPanel, BorderLayout.EAST);
        mainPanel.add(topBar, BorderLayout.NORTH);

        // Tabs
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(theme.BODY);
        tabbedPane.setBackground(theme.BG_CARD);
        tabbedPane.setForeground(theme.TEXT);

        JPanel packagesPanel = new JPanel();
        packagesPanel.setLayout(new BoxLayout(packagesPanel, BoxLayout.Y_AXIS));
        packagesPanel.setBackground(theme.BG);
        packagesPanel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        for(tGamesPackage pack: Controller.getInstance().getAllPackages()) packagesPanel.add(createPackagePanel(pack));
        JScrollPane packagesScroll = new JScrollPane(packagesPanel);
        packagesScroll.setBorder(BorderFactory.createEmptyBorder());
        packagesScroll.getViewport().setBackground(theme.BG);
        tabbedPane.addTab("Paquetes de juegos", packagesScroll);

        JPanel productsPanel = new JPanel();
        productsPanel.setLayout(new BoxLayout(productsPanel, BoxLayout.Y_AXIS));
        productsPanel.setBackground(theme.BG);
        productsPanel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        for(tProduct prod: Controller.getInstance().getAllProducts()) productsPanel.add(createProductPanel(prod));
        JScrollPane productsScroll = new JScrollPane(productsPanel);
        productsScroll.setBorder(BorderFactory.createEmptyBorder());
        productsScroll.getViewport().setBackground(theme.BG);
        tabbedPane.addTab("Productos", productsScroll);
        mainPanel.add(tabbedPane, BorderLayout.CENTER);

        setVisible(true);
    }

    private void refreshPoints() {
        pointsLabel.setText(Controller.getInstance().getPlayerPoints(Controller.getInstance().getUsername()) + " pts");
    }

    private JPanel createPackagePanel(tGamesPackage pack) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(theme.BG_ITEM);
        panel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4), BorderFactory.createLineBorder(theme.BORDER, 1)));
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60 + pack.getGames().size() * 20));

        JPanel infoPanel = new JPanel();
        infoPanel.setOpaque(false);
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));

        JLabel titleLabel = new JLabel(pack.getName());
        titleLabel.setFont(theme.HEADING);
        titleLabel.setForeground(theme.TEXT);
        infoPanel.add(titleLabel);

        JLabel priceLabel = new JLabel(pack.getPrice() + "€");
        priceLabel.setFont(theme.BODY);
        priceLabel.setForeground(theme.GOLD);
        infoPanel.add(priceLabel);

        for(Map.Entry<tGame, Integer> entry: pack.getGames().entrySet()) {
            JLabel gameLabel = new JLabel("  • " + entry.getValue() + "x " + entry.getKey().getName() + " (" + entry.getKey().getBasePoints() + "pts)");
            gameLabel.setFont(theme.BODY);
            gameLabel.setForeground(theme.TEXT_MUTED);
            infoPanel.add(gameLabel);
        }
        panel.add(infoPanel, BorderLayout.CENTER);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        btnPanel.setOpaque(false);
        btnPanel.add(theme.primaryBtn("Comprar", () -> Controller.getInstance().action(Event.BUY_PACKAGE, pack)));
        panel.add(btnPanel, BorderLayout.EAST);
        return panel;
    }

    private JPanel createProductPanel(tProduct prod) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(theme.BG_ITEM);
        panel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4), BorderFactory.createLineBorder(theme.BORDER, 1)));
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 52));

        JPanel infoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        infoPanel.setOpaque(false);
        JLabel nameLabel = new JLabel(prod.getName());
        nameLabel.setFont(theme.BODY);
        nameLabel.setForeground(theme.TEXT);
        JLabel priceLabel = new JLabel(prod.getPrice() + " pts");
        priceLabel.setFont(theme.BODY);
        priceLabel.setForeground(theme.GOLD);
        infoPanel.add(nameLabel);
        infoPanel.add(priceLabel);
        panel.add(infoPanel, BorderLayout.CENTER);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        btnPanel.setOpaque(false);
        btnPanel.add(theme.primaryBtn("Canjear", () -> {
            Controller.getInstance().action(Event.BUY_PRODUCT, prod);
            refreshPoints();
        }));
        panel.add(btnPanel, BorderLayout.EAST);
        return panel;
    }
}
