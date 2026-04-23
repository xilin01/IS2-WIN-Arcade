package presentation.user;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import business.user.SAUser;
import business.user.SAUserImp;
import business.player.tPlayer;
import business.user.tUser;

public class UserEditor extends JDialog {

    private JTextField nameField;
    private JTextField passwordField;
    private JCheckBox activeCheckBox; 
    private tUser currentUser; 
    
    private SAUser saUser = new SAUserImp();

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

        JPanel formPanel = new JPanel(new GridLayout(3, 2, 10, 15));
        formPanel.setBorder(new EmptyBorder(20, 20, 10, 20));

        // Nombre
        formPanel.add(new JLabel("Nombre del Jugador:"));
        nameField = new JTextField();
        if (currentUser != null) {
            nameField.setText(currentUser.getName());
            nameField.setEditable(false);
        }
        formPanel.add(nameField);

        // Contraseña
        formPanel.add(new JLabel("Contraseña:"));
        passwordField = new JTextField();
        if (currentUser != null) {
            passwordField.setText(currentUser.getPassword());
        }
        formPanel.add(passwordField);

        // Activo
        formPanel.add(new JLabel("Estado de la cuenta:"));
        activeCheckBox = new JCheckBox("Activa");
        activeCheckBox.setSelected(currentUser == null ? true : currentUser.getActive());
        formPanel.add(activeCheckBox);

        this.add(formPanel, BorderLayout.CENTER);

        // Botones guardar cancelar
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton cancelButton = new JButton("Cancelar");
        JButton saveButton = new JButton("Guardar");

        cancelButton.addActionListener((e) -> dispose());

        saveButton.addActionListener((e) -> {
            String name = nameField.getText().trim();
            String password = passwordField.getText().trim();
            boolean isActive = activeCheckBox.isSelected();
            
            if (!name.isEmpty() && !password.isEmpty()) {
                if (currentUser == null) {
                    tUser newPlayer = new tPlayer(name, password, isActive, new tPlayer.tInventory(name), 0);
                    saUser.createUser(newPlayer); 
                } else {
                    currentUser.setPassword(password);
                    currentUser.setActive(isActive);
                    saUser.updateUser(currentUser); 
                }
                dispose(); 
            } else {
                javax.swing.JOptionPane.showMessageDialog(this, "El nombre y la contraseña no pueden estar vacíos.", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
            }
        });

        bottomPanel.add(cancelButton);
        bottomPanel.add(saveButton);
        this.add(bottomPanel, BorderLayout.SOUTH);

        this.setVisible(true);
    }
}