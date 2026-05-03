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

    private static final Theme theme = Theme.getInstance();
    private JPanel userListPanel;

    public UsersManagerView() {
        super("Gestor de usuarios");
        initGUI();
    }

    private void initGUI() {
        this.setLayout(new BorderLayout());
        this.setSize(550, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().setBackground(theme.BG);

        JPanel header = theme.header("Gestion de Usuarios");
        JPanel headerRight = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        headerRight.setBackground(theme.BG_CARD);
        headerRight.add(theme.primaryBtn("Crear Usuario", () -> {
            UserEditor.show(this, null);
            updateList();
        }));
        headerRight.add(theme.secondaryBtn("Volver", () -> {
            new StaffView();
            dispose();
        }));
        header.add(headerRight, BorderLayout.EAST);
        this.add(header, BorderLayout.NORTH);

        userListPanel = new JPanel();
        userListPanel.setLayout(new BoxLayout(userListPanel, BoxLayout.Y_AXIS));
        userListPanel.setBackground(theme.BG);
        userListPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JScrollPane scrollPane = new JScrollPane(userListPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(theme.BG);
        this.add(scrollPane, BorderLayout.CENTER);

        updateList();
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setVisible(true);
    }

    private JPanel userElement(tPlayer user) {
        JPanel userPanel = new JPanel(new BorderLayout());
        userPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 62));
        userPanel.setBackground(theme.BG_ITEM);
        userPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createEmptyBorder(4, 0, 4, 0),
            BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(theme.BORDER, 1),
                BorderFactory.createEmptyBorder(10, 16, 10, 16)
            )
        ));

        JPanel userInfo = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        userInfo.setBackground(theme.BG_ITEM);

        JLabel nameLabel = new JLabel(user.getName());
        nameLabel.setFont(theme.BODY);
        nameLabel.setForeground(user.getActive() ? theme.TEXT : theme.TEXT_MUTED);

        JLabel statusLabel = new JLabel(user.getActive() ? "Activo" : "Inactivo");
        statusLabel.setFont(theme.BODY);
        statusLabel.setForeground(user.getActive() ? theme.SUCCESS : theme.TEXT_MUTED);

        userInfo.add(nameLabel);
        userInfo.add(statusLabel);
        userPanel.add(userInfo, BorderLayout.CENTER);

        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        buttonsPanel.setBackground(theme.BG_ITEM);

        buttonsPanel.add(theme.secondaryBtn("Editar", () -> {
            UserEditor.show(this, user);
            updateList();
        }));

        boolean isActive = user.getActive();
        JButton toggleButton = isActive
            ? theme.dangerBtn("Desactivar", () -> {
                if (theme.confirm(this, "Desactivar al usuario " + user.getName())) {
                    Controller.getInstance().action(Event.DELETE_PLAYER, user.getName());
                    updateList();
                }
              })
            : theme.successBtn("Activar", () -> {
                if (theme.confirm(this, "Activar al usuario " + user.getName())) {
                    user.setActive(true);
                    Controller.getInstance().action(Event.UPDATE_PLAYER, user);
                    updateList();
                }
              });
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
