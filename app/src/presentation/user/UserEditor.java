package presentation.user;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import business.player.tPlayer;
import presentation.Controller;
import presentation.Event;
import presentation.Theme;

@SuppressWarnings("serial")
public class UserEditor extends JDialog {
    private JTextField nameField;
    private JTextField passwordField;
    private JCheckBox activeCheckBox;
    private tPlayer currentUser;

    public UserEditor(JFrame parent, tPlayer player) {
        super(parent, player == null ? "Crear Nuevo Jugador" : "Editar Jugador", true);
        this.currentUser = player;
        initGUI();
    }

    private void initGUI() {
        this.setLayout(new BorderLayout());
        this.setSize(380, 240);
        this.setLocationRelativeTo(getOwner());
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.getContentPane().setBackground(Theme.BG);

        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 12));
        formPanel.setBackground(Theme.BG);
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        JLabel nameLabel = new JLabel("Nombre del Jugador:");
        nameLabel.setForeground(Theme.TEXT);
        formPanel.add(nameLabel);

        nameField = new JTextField();
        styleField(nameField);
        if (currentUser != null) {
            nameField.setText(currentUser.getName());
            nameField.setEditable(false);
            nameField.setBackground(Theme.BG_CARD);
            nameField.setForeground(Theme.TEXT_MUTED);
        }
        formPanel.add(nameField);

        JLabel passwordLabel = new JLabel("Contraseña:");
        passwordLabel.setForeground(Theme.TEXT);
        formPanel.add(passwordLabel);

        passwordField = new JTextField();
        styleField(passwordField);
        if (currentUser != null) passwordField.setText(currentUser.getPassword());
        formPanel.add(passwordField);

        JLabel activeLabel = new JLabel("Estado de la cuenta:");
        activeLabel.setForeground(Theme.TEXT);
        formPanel.add(activeLabel);

        activeCheckBox = new JCheckBox("Activa");
        activeCheckBox.setBackground(Theme.BG);
        activeCheckBox.setForeground(Theme.TEXT);
        activeCheckBox.setFocusPainted(false);
        activeCheckBox.setSelected(currentUser == null || currentUser.getActive());
        formPanel.add(activeCheckBox);

        JLabel inventoryLabel = new JLabel("Modificar inventario:");
        inventoryLabel.setForeground(Theme.TEXT);
        formPanel.add(inventoryLabel);

        JButton editInventoryButton = Theme.secondaryBtn("Editar inventario");
        editInventoryButton.setEnabled(currentUser != null);
        editInventoryButton.addActionListener(e -> new InventoryEditorView(this, currentUser));
        formPanel.add(editInventoryButton);

        this.add(formPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        bottomPanel.setBackground(Theme.BG_CARD);
        bottomPanel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Theme.BORDER));

        JButton cancelButton = Theme.secondaryBtn("Cancelar");
        JButton saveButton = Theme.primaryBtn("Guardar");

        cancelButton.addActionListener(e -> dispose());
        saveButton.addActionListener(e -> {
            String name = nameField.getText().trim();
            String password = passwordField.getText().trim();
            boolean isActive = activeCheckBox.isSelected();
            if (!name.isEmpty() && !password.isEmpty()) {
                if (currentUser == null) Controller.getInstance().action(Event.CREATE_USER, new tPlayer(name, password, isActive, new tPlayer.tInventory(name), 0));
                else {
                    currentUser.setPassword(password);
                    currentUser.setActive(isActive);
                    Controller.getInstance().action(Event.UPDATE_USER, currentUser);
                }
                dispose();
            } else JOptionPane.showMessageDialog(this, "El nombre o la contraseña no pueden estar vacíos.", "Error", JOptionPane.ERROR_MESSAGE);
        });

        bottomPanel.add(cancelButton);
        bottomPanel.add(saveButton);
        this.add(bottomPanel, BorderLayout.SOUTH);

        this.setVisible(true);
    }

    private void styleField(JTextField field) {
        field.setBackground(Theme.BG_ITEM);
        field.setForeground(Theme.TEXT);
        field.setCaretColor(Theme.TEXT);
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Theme.BORDER, 1),
            BorderFactory.createEmptyBorder(4, 8, 4, 8)
        ));
    }
}
