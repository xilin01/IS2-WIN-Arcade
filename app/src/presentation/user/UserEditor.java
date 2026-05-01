package presentation.user;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import business.player.tPlayer;
import business.user.tUser;
import presentation.Controller;
import presentation.Event;

@SuppressWarnings("serial")
public class UserEditor extends JDialog {
    private JTextField nameField;
    private JTextField passwordField;
    private JCheckBox activeCheckBox; 
    private tUser currentUser;

    public UserEditor(JFrame parent, tUser user) {
        super(parent, user == null ? "Crear Nuevo Jugador" : "Editar Jugador", true);
        this.currentUser = user;
        initGUI();
    }

    private void initGUI() {
        this.setLayout(new BorderLayout());
        this.setSize(350, 220); 
        this.setLocationRelativeTo(getOwner());
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        JPanel userInfoPanel = new JPanel(new GridLayout(4, 2, 10, 15));
        userInfoPanel.setBorder(new EmptyBorder(20, 20, 10, 20));

        // Nombre
        userInfoPanel.add(new JLabel("Nombre del Jugador:"));
        nameField = new JTextField();
        if (currentUser != null) {
            nameField.setText(currentUser.getName());
            nameField.setEditable(false);
        }
        userInfoPanel.add(nameField);

        // Contraseña
        userInfoPanel.add(new JLabel("Contraseña:"));
        passwordField = new JTextField();
        if (currentUser != null) passwordField.setText(currentUser.getPassword());
        userInfoPanel.add(passwordField);

        // Activo
        userInfoPanel.add(new JLabel("Estado de la cuenta:"));
        activeCheckBox = new JCheckBox("Activa");
        activeCheckBox.setSelected(currentUser == null ? true : currentUser.getActive());
        userInfoPanel.add(activeCheckBox);

        this.add(userInfoPanel, BorderLayout.CENTER);
        
        // Editar inventario
        userInfoPanel.add(new JLabel("Modificar inventario: "));
        JButton editInventoryButton = new JButton("Editar inventario");
        editInventoryButton.setEnabled(currentUser != null && currentUser instanceof tPlayer);
        editInventoryButton.addActionListener((e) -> {
        	new InventoryEditorView(this, (tPlayer)currentUser);
        });
        userInfoPanel.add(editInventoryButton);

        // Botones guardar cancelar
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton cancelButton = new JButton("Cancelar");
        JButton saveButton = new JButton("Guardar");

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
            } 
            else JOptionPane.showMessageDialog(this, "El nombre o la contraseña no pueden estar vacíos.", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
        });

        bottomPanel.add(cancelButton);
        bottomPanel.add(saveButton);
        this.add(bottomPanel, BorderLayout.SOUTH);

        this.setVisible(true);
    }
}