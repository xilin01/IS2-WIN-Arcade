package presentation;

import javax.swing.*;
import java.awt.*;

import business.tPlayer;
import business.tStaff;
import business.tUser;

public class LoginView extends JFrame {

    private static LoginView instance;
	
    public LoginView() {
        super("Login");
        instance = this;
        initGUI();
    }

    private void initGUI() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(320, 200);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 10, 6, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblName = new JLabel("Usuario:");
        JTextField txtName = new JTextField(16);

        JLabel lblPass = new JLabel("Contraseña:");
        JPasswordField txtPass = new JPasswordField(16);

        JButton btnLogin = new JButton("Iniciar sesión");

        // Layout
        gbc.gridx = 0; gbc.gridy = 0; add(lblName, gbc);
        gbc.gridx = 1;                add(txtName, gbc);
        gbc.gridx = 0; gbc.gridy = 1; add(lblPass, gbc);
        gbc.gridx = 1;                add(txtPass, gbc);
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2; add(btnLogin, gbc);

        btnLogin.addActionListener(e -> {
            String name = txtName.getText().trim();
            String pass = new String(txtPass.getPassword());

            // Usamos tStaff como DTO anónimo solo para transportar credenciales
            tUser credentials = new tStaff(name, pass, true);
            Controller.action(Event.LOGIN, credentials);
        });

        setVisible(true);
    }

    public static void actualizar(int evento, Object datos) {
        switch (evento) {
            case Event.RES_LOGIN_STAFF_OK -> {
                instance.dispose();
                new StaffView((String)datos);
            }
            case Event.RES_LOGIN_OK -> {
                instance.dispose();
                //new PlayerView((tPlayer) datos);
            }
            case Event.RES_LOGIN_KO ->
                JOptionPane.showMessageDialog(instance, "Usuario o contraseña incorrectos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}