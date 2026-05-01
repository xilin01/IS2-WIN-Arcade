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
import presentation.product.ProductManagerView;

@SuppressWarnings("serial")
public class StaffView extends JFrame {

    public StaffView() {
        super();
        initGUI();
    }

    private void initGUI() {
        this.setLayout(new BorderLayout());
        this.setTitle("Panel de Personal");
        this.setSize(650, 400);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().setBackground(Theme.BG);

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 12));
        topPanel.setBackground(Theme.BG_CARD);
        topPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Theme.GOLD));

        JLabel userLabel = new JLabel(Controller.getInstance().getUsername());
        userLabel.setForeground(Theme.TEXT_MUTED);
        userLabel.setFont(Theme.BODY);
        JButton logOutButton = Theme.secondaryBtn("Cerrar Sesión");

        topPanel.add(userLabel);
        topPanel.add(logOutButton);
        this.add(topPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBackground(Theme.BG);
        centerPanel.setBorder(BorderFactory.createEmptyBorder(80, 0, 80, 0));

        JLabel titleLabel = new JLabel("Panel de Personal", SwingConstants.CENTER);
        titleLabel.setFont(Theme.TITLE);
        titleLabel.setForeground(Theme.GOLD);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitleLabel = new JLabel("Sistema de Gestión del Casino", SwingConstants.CENTER);
        subtitleLabel.setFont(Theme.BODY);
        subtitleLabel.setForeground(Theme.TEXT_MUTED);
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        centerPanel.add(titleLabel);
        centerPanel.add(Box.createVerticalStrut(8));
        centerPanel.add(subtitleLabel);
        centerPanel.add(Box.createVerticalStrut(60));

        Dimension btnSize = new Dimension(300, 52);

        JButton packagesBtn = Theme.primaryBtn("GESTIONAR PAQUETES");
        packagesBtn.setPreferredSize(btnSize);
        packagesBtn.setMaximumSize(btnSize);
        packagesBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton productsBtn = Theme.primaryBtn("GESTIONAR PRODUCTOS");
        productsBtn.setPreferredSize(btnSize);
        productsBtn.setMaximumSize(btnSize);
        productsBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton usersBtn = Theme.primaryBtn("GESTIONAR USUARIOS");
        usersBtn.setPreferredSize(btnSize);
        usersBtn.setMaximumSize(btnSize);
        usersBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        centerPanel.add(packagesBtn);
        centerPanel.add(Box.createVerticalStrut(14));
        centerPanel.add(productsBtn);
        centerPanel.add(Box.createVerticalStrut(14));
        centerPanel.add(usersBtn);

        this.add(centerPanel, BorderLayout.CENTER);

        packagesBtn.addActionListener(e -> {
            Controller.getInstance().action(Event.OPEN_PACKAGE_MANAGER, null);
            dispose();
        });
        productsBtn.addActionListener(e -> {
            new ProductManagerView();
            dispose();
        });
        usersBtn.addActionListener(e -> {
            new UsersManagerView();
            dispose();
        });
        logOutButton.addActionListener(e -> {
            Controller.getInstance().action(Event.LOGOUT, null);
            dispose();
        });

        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setVisible(true);
    }
}
