package presentation.user;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import business.player.tPlayer;
import business.user.SAUser;
import business.user.SAUserImp;
import business.user.tUser;

public class UsersManagerView extends JFrame {

    private JPanel userListPanel;
    private SAUser saUser;
    private String currentUserName;

    public UsersManagerView(String currentUserName) {
        super();
        this.currentUserName = currentUserName;
        this.saUser = new SAUserImp(); 
        initGUI();
    }

    private void initGUI() {
        this.setLayout(new BorderLayout());
        this.setTitle("Gestor de Usuarios");
        this.setBounds(100, 100, 550, 600);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Boton crear nuevo player
        JPanel createUserPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton createUserButton = new JButton("CREAR NUEVO USUARIO");
        createUserButton.setPreferredSize(new Dimension(200, 35));
        createUserButton.addActionListener((e) -> {
            new UserEditor(this, null);
            updateList(); 
        });
        createUserPanel.add(createUserButton);
        this.add(createUserPanel, BorderLayout.NORTH);

        // Lista de usuarios(jugadores)
        userListPanel = new JPanel();
        userListPanel.setLayout(new BoxLayout(userListPanel, BoxLayout.Y_AXIS));
        
        JPanel listWrapper = new JPanel(new BorderLayout());
        listWrapper.setBorder(new EmptyBorder(10, 10, 10, 10));
        JScrollPane userScrollPane = new JScrollPane(userListPanel);
        listWrapper.add(userScrollPane, BorderLayout.CENTER);
        
        this.add(listWrapper, BorderLayout.CENTER);

        // Boton back
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton backButton = new JButton("Back");
        bottomPanel.add(backButton);
        this.add(bottomPanel, BorderLayout.SOUTH);

        backButton.addActionListener((e) -> {
            new StaffView(currentUserName); 
            
            dispose(); 
        });

        updateList();
        this.setVisible(true);
    }

    // Fila de la lista / Informacion de un usuario
    private JPanel userElement(tUser user) {
        JPanel userPanel = new JPanel(new BorderLayout());
        userPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        userPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createEmptyBorder(5, 5, 5, 5),
            BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1)
        ));

        JPanel userInfo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        String statusText = user.getActive() ? "(Activo)" : "(Inactivo)";
        JLabel nameLabel = new JLabel("  " + user.getName() + "  " + statusText);
        
        if (!user.getActive()) {
            nameLabel.setForeground(Color.GRAY);
        }
        userInfo.add(nameLabel);
        userPanel.add(userInfo, BorderLayout.CENTER);

        // Botones
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JButton editButton = new JButton("Editar"); 
        editButton.addActionListener((e) -> {
            new UserEditor(this, user);
            updateList();
        });

        JButton deleteButton = new JButton("X");
        deleteButton.setForeground(Color.RED);

        deleteButton.addActionListener((e) -> {
            int confirm = javax.swing.JOptionPane.showConfirmDialog(
                this, 
                "¿Borrar definitivamente al usuario '" + user.getName() + "'?",
                "Confirmar",
                javax.swing.JOptionPane.YES_NO_OPTION
            );

            if (confirm == javax.swing.JOptionPane.YES_OPTION) {
                this.saUser.deleteUser(user.getName()); 
                updateList();
            }
        });

        buttonsPanel.add(editButton);
        buttonsPanel.add(deleteButton);
        userPanel.add(buttonsPanel, BorderLayout.EAST);

        return userPanel;
    }

    private void updateList() {
        userListPanel.removeAll();
        for (tUser u : saUser.readAllUsers()) {
            if (u instanceof tPlayer) {
                userListPanel.add(userElement(u));
            }
        }
        userListPanel.revalidate();
        userListPanel.repaint();
    }
}