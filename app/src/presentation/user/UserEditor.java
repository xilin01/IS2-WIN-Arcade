package presentation.user;

import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import business.player.tPlayer;
import presentation.Controller;
import presentation.Event;
import presentation.Theme;

public class UserEditor {

    private static final Theme theme = Theme.getInstance();

    private UserEditor() {}

    public static void show(JFrame parent, tPlayer player) {
        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 12));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        JLabel nameLabel = new JLabel("Nombre del Jugador:");
        nameLabel.setForeground(theme.TEXT);
        formPanel.add(nameLabel);

        JTextField nameField = new JTextField();
        styleField(nameField);
        if (player != null) {
            nameField.setText(player.getName());
            nameField.setEditable(false);
            nameField.setBackground(theme.BG_CARD);
            nameField.setForeground(theme.TEXT_MUTED);
        }
        formPanel.add(nameField);

        JLabel passwordLabel = new JLabel("Contrasena:");
        passwordLabel.setForeground(theme.TEXT);
        formPanel.add(passwordLabel);

        JTextField passwordField = new JTextField();
        styleField(passwordField);
        if (player != null) passwordField.setText(player.getPassword());
        formPanel.add(passwordField);

        JLabel activeLabel = new JLabel("Estado de la cuenta:");
        activeLabel.setForeground(theme.TEXT);
        formPanel.add(activeLabel);

        JCheckBox activeCheckBox = new JCheckBox("Activa");
        activeCheckBox.setBackground(theme.BG);
        activeCheckBox.setForeground(theme.TEXT);
        activeCheckBox.setFocusPainted(false);
        activeCheckBox.setSelected(player == null || player.getActive());
        formPanel.add(activeCheckBox);

        JLabel inventoryLabel = new JLabel("Modificar inventario:");
        inventoryLabel.setForeground(theme.TEXT);
        formPanel.add(inventoryLabel);

        JButton editInventoryButton = theme.secondaryBtn("Editar inventario",
            () -> new InventoryEditorView(parent, player));
        editInventoryButton.setEnabled(player != null);
        formPanel.add(editInventoryButton);

        theme.formDialog(parent, player == null ? "Crear Nuevo Jugador" : "Editar Jugador", formPanel, "Guardar", () -> {
            String name = nameField.getText().trim();
            String password = passwordField.getText().trim();
            boolean isActive = activeCheckBox.isSelected();
            if (!name.isEmpty() && !password.isEmpty()) {
                if (player == null) Controller.getInstance().action(Event.CREATE_PLAYER, new tPlayer(name, password, isActive, new tPlayer.tInventory(name), 0));
                else {
                    player.setPassword(password);
                    player.setActive(isActive);
                    Controller.getInstance().action(Event.UPDATE_PLAYER, player);
                }
                return true;
            }
            JOptionPane.showMessageDialog(parent, "El nombre o la contrasena no pueden estar vacios.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        });
    }

    private static void styleField(JTextField field) {
        field.setBackground(theme.BG_ITEM);
        field.setForeground(theme.TEXT);
        field.setCaretColor(theme.TEXT);
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(theme.BORDER, 1),
            BorderFactory.createEmptyBorder(4, 8, 4, 8)
        ));
    }
}
