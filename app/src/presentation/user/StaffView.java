package presentation.user;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import presentation.Controller;
import presentation.Event;
import presentation.Theme;

@SuppressWarnings("serial")
public class StaffView extends JFrame {
    private static final Theme theme = Theme.getInstance();

    public StaffView() {
        super("Menú principal - Personal");
        initGUI();
    }

    private void initGUI() {
        this.setLayout(new BorderLayout());
        this.setSize(650, 400);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().setBackground(theme.BG);
        
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 25, 15));
        topPanel.setOpaque(false);
        JLabel userLabel = new JLabel(Controller.getInstance().getUsername());
        userLabel.setForeground(theme.GOLD);
        userLabel.setFont(theme.BODY);
        JPanel userBackground = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0)); 
        userBackground.setBackground(theme.BG_ITEM);
        userBackground.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(theme.BORDER, 1), BorderFactory.createEmptyBorder(6, 14, 6, 14)));
        userBackground.add(userLabel);
        topPanel.add(userBackground);
        topPanel.add(theme.secondaryBtn("Cerrar sesión", () -> {
            Controller.getInstance().action(Event.LOGOUT, null);
            dispose();
        }));
        this.add(topPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBackground(theme.BG);
        centerPanel.setBorder(BorderFactory.createEmptyBorder(80, 0, 80, 0));

        JLabel titleLabel = new JLabel("Panel del personal", SwingConstants.CENTER);
        titleLabel.setFont(theme.TITLE);
        titleLabel.setForeground(theme.GOLD);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        centerPanel.add(titleLabel);
        centerPanel.add(Box.createVerticalStrut(8));

        JLabel subtitleLabel = new JLabel("Sistema de gestión de la recreativa", SwingConstants.CENTER);
        subtitleLabel.setFont(theme.BODY);
        subtitleLabel.setForeground(theme.TEXT_MUTED);
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        centerPanel.add(subtitleLabel);
        centerPanel.add(Box.createVerticalStrut(60));

        Dimension btnSize = new Dimension(300, 52);
        JButton packagesBtn = theme.primaryBtn("GESTIONAR PAQUETES", () -> {
            Controller.getInstance().action(Event.OPEN_PACKAGE_MANAGER, null);
            dispose();
        });
        packagesBtn.setPreferredSize(btnSize);
        packagesBtn.setMaximumSize(btnSize);
        packagesBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        centerPanel.add(packagesBtn);
        centerPanel.add(Box.createVerticalStrut(14));

        JButton productsBtn = theme.primaryBtn("GESTIONAR PRODUCTOS", () -> {
        	Controller.getInstance().action(Event.OPEN_PRODUCT_MANAGER, null);
            dispose();
        });
        productsBtn.setPreferredSize(btnSize);
        productsBtn.setMaximumSize(btnSize);
        productsBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        centerPanel.add(productsBtn);
        centerPanel.add(Box.createVerticalStrut(14));

        JButton usersBtn = theme.primaryBtn("GESTIONAR JUGADORES", () -> {
        	Controller.getInstance().action(Event.OPEN_PLAYER_MANAGER, null);
            dispose();
        });
        usersBtn.setPreferredSize(btnSize);
        usersBtn.setMaximumSize(btnSize);
        usersBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        centerPanel.add(usersBtn);
        this.add(centerPanel, BorderLayout.CENTER);

        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setVisible(true);
    }
}
