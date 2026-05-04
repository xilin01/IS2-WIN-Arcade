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
        background.setLayout(new BorderLayout());
        setContentPane(background);

        JPanel topBar = new JPanel(new FlowLayout(FlowLayout.RIGHT, 25, 15));
        topBar.setOpaque(false);
        JLabel userLabel = new JLabel(Controller.getInstance().getUsername());
        userLabel.setForeground(theme.GOLD);
        userLabel.setFont(theme.BODY);
        topBar.add(userLabel);
        JLabel pointsLabel = new JLabel("| " + Controller.getInstance().getPlayerPoints(Controller.getInstance().getUsername()) + " pts");
        pointsLabel.setForeground(theme.GOLD);
        pointsLabel.setFont(theme.BODY);
        topBar.add(pointsLabel);
        topBar.add(theme.secondaryBtn("Cerrar sesión", () -> {
            Controller.getInstance().action(Event.LOGOUT, null);
            dispose();
        }));
        background.add(topBar, BorderLayout.NORTH);

        JPanel centerWrapper = new JPanel(new GridBagLayout());
        centerWrapper.setOpaque(false);
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setPreferredSize(new Dimension(640, 480));
        mainPanel.setOpaque(false);
        centerWrapper.add(mainPanel);
        background.add(centerWrapper, BorderLayout.CENTER);

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
