package presentation.user;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.Collection;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import business.player.tPlayer;
import presentation.Controller;
import presentation.Event;
import presentation.Theme;

@SuppressWarnings("serial")
public class UsersManagerView extends JFrame {
    private JPanel userListPanel;

    public UsersManagerView() {
        super("Gestor de usuarios");
        initGUI();
    }

    private void initGUI() {
        this.setLayout(new BorderLayout());
        this.setSize(550, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().setBackground(Theme.BG);

        JPanel header = Theme.header("Gestión de Usuarios");
        JPanel headerRight = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        headerRight.setBackground(Theme.BG_CARD);
        JButton createUserButton = Theme.primaryBtn("Crear Usuario");
        JButton backButton = Theme.secondaryBtn("Volver");
        headerRight.add(createUserButton);
        headerRight.add(backButton);
        header.add(headerRight, BorderLayout.EAST);
        this.add(header, BorderLayout.NORTH);

        userListPanel = new JPanel();
        userListPanel.setLayout(new BoxLayout(userListPanel, BoxLayout.Y_AXIS));
        userListPanel.setBackground(Theme.BG);
        userListPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JScrollPane scrollPane = new JScrollPane(userListPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(Theme.BG);
        this.add(scrollPane, BorderLayout.CENTER);

        createUserButton.addActionListener(e -> {
            new UserEditor(this, null);
            updateList();
        });
        backButton.addActionListener(e -> {
            new StaffView();
            dispose();
        });

        updateList();
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setVisible(true);
    }

    private JPanel userElement(tPlayer user) {
        JPanel userPanel = new JPanel(new BorderLayout());
        userPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 62));
        userPanel.setBackground(Theme.BG_ITEM);
        userPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createEmptyBorder(4, 0, 4, 0),
            BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Theme.BORDER, 1),
                BorderFactory.createEmptyBorder(10, 16, 10, 16)
            )
        ));

        JPanel userInfo = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        userInfo.setBackground(Theme.BG_ITEM);

        JLabel nameLabel = new JLabel(user.getName());
        nameLabel.setFont(Theme.BODY);
        nameLabel.setForeground(user.getActive() ? Theme.TEXT : Theme.TEXT_MUTED);

        JLabel statusLabel = new JLabel(user.getActive() ? "Activo" : "Inactivo");
        statusLabel.setFont(Theme.BODY);
        statusLabel.setForeground(user.getActive() ? Theme.SUCCESS : Theme.TEXT_MUTED);

        userInfo.add(nameLabel);
        userInfo.add(statusLabel);
        userPanel.add(userInfo, BorderLayout.CENTER);

        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        buttonsPanel.setBackground(Theme.BG_ITEM);

        JButton editButton = Theme.secondaryBtn("Editar");
        editButton.addActionListener(e -> {
            new UserEditor(this, user);
            updateList();
        });

        boolean isActive = user.getActive();
        JButton toggleButton = isActive ? Theme.dangerBtn("Desactivar") : Theme.successBtn("Activar");
        toggleButton.addActionListener(e -> {
            String action = isActive ? "Desactivar" : "Activar";
            if (Theme.confirm(this, action + " al usuario " + user.getName())) {
                if (isActive) {
                    Controller.getInstance().action(Event.DELETE_USER, user.getName());
                } else {
                    user.setActive(true);
                    Controller.getInstance().action(Event.UPDATE_USER, user);
                }
                updateList();
            }
        });

        buttonsPanel.add(editButton);
        buttonsPanel.add(toggleButton);
        userPanel.add(buttonsPanel, BorderLayout.EAST);

        return userPanel;
    }

    private void updateList() {
        userListPanel.removeAll();
        Collection<tPlayer> players = Controller.getInstance().getAllPlayers();
        for (tPlayer p : players) userListPanel.add(userElement(p));
        userListPanel.revalidate();
        userListPanel.repaint();
    }
}
