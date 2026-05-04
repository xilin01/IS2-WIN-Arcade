package presentation.user;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import images.IMAGES;
import presentation.Controller;
import presentation.Event;
import presentation.Theme;

@SuppressWarnings("serial")
public class PlayerView extends JFrame {
    private static final Theme theme = Theme.getInstance();

    public PlayerView() {
        super("Menú principal - Jugador");
        initGUI();
    }

    private void initGUI() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        JPanel background = theme.backgroundPanel(IMAGES.class.getResource("menu_principal.png"));
        background.setLayout(new GridBagLayout());
        setContentPane(background);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setPreferredSize(new Dimension(640, 480));
        mainPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(80, 0, 0, 0);
        background.add(mainPanel, gbc);

        // Top bar: user info + logout
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(theme.BG_CARD);
        topBar.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(theme.BORDER, 1), BorderFactory.createEmptyBorder(10, 18, 10, 14)));

        JPanel userInfo = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 0));
        userInfo.setOpaque(false);
        JLabel userLabel = new JLabel(Controller.getInstance().getUsername());
        userLabel.setForeground(theme.TEXT);
        userLabel.setFont(theme.HEADING);
        JLabel pointsLabel = new JLabel("  " + Controller.getInstance().getPlayerPoints(Controller.getInstance().getUsername()) + " pts");
        pointsLabel.setForeground(theme.GOLD);
        pointsLabel.setFont(theme.HEADING);
        userInfo.add(userLabel);
        userInfo.add(pointsLabel);
        topBar.add(userInfo, BorderLayout.CENTER);

        JButton logoutBtn = theme.dangerBtn("Cerrar sesión", () -> {
            Controller.getInstance().action(Event.LOGOUT, null);
            dispose();
        });
        logoutBtn.setPreferredSize(new Dimension(140, 36));
        JPanel logoutPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        logoutPanel.setOpaque(false);
        logoutPanel.add(logoutBtn);
        topBar.add(logoutPanel, BorderLayout.EAST);
        mainPanel.add(topBar, BorderLayout.NORTH);

        // Center: Jugar + Inventario/ Tienda grouped together
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setOpaque(false);
        centerPanel.add(Box.createVerticalGlue());

        JButton playButton = theme.primaryBtn("Jugar", () -> {
            Controller.getInstance().action(Event.OPEN_GAMES_PANEL, null);
            dispose();
        });
        playButton.setPreferredSize(new Dimension(280, 110));
        playButton.setMaximumSize(new Dimension(280, 110));
        playButton.setAlignmentX(0.5f);
        playButton.setFont(theme.TITLE);
        centerPanel.add(playButton);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 18)));

        JPanel secondaryRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 16, 0));
        secondaryRow.setOpaque(false);
        JButton inventoryButton = theme.secondaryBtn("Inventario", () -> {
            Controller.getInstance().action(Event.OPEN_INVENTORY, null);
            dispose();
        });
        JButton storeButton = theme.secondaryBtn("Tienda", () -> {
            Controller.getInstance().action(Event.OPEN_STORE, null);
            dispose();
        });
        Dimension secSize = new Dimension(150, 52);
        inventoryButton.setPreferredSize(secSize);
        storeButton.setPreferredSize(secSize);
        secondaryRow.add(inventoryButton);
        secondaryRow.add(storeButton);
        centerPanel.add(secondaryRow);
        centerPanel.add(Box.createVerticalGlue());
        mainPanel.add(centerPanel, BorderLayout.CENTER);

        setVisible(true);
    }
}
