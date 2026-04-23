package presentation.auth;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import presentation.Controller;
import presentation.Event;

public class SignUpView extends JFrame {

    private static SignUpView instance;

    private final JTextField txtName = new JTextField(16);
    private final JPasswordField txtPassword = new JPasswordField(16);
    private final JPasswordField txtConfirm = new JPasswordField(16);

    private SignUpView() {
        super("Registro");
        initGUI();
    }

    public static void open() {
        if (instance == null || !instance.isDisplayable()) {
            instance = new SignUpView();
        }
        instance.setVisible(true);
    }

    private void initGUI() {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(360, 280);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 10, 6, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.weightx = 0; gbc.gridx = 0; gbc.gridy = 0; add(new JLabel("Usuario:"), gbc);
        gbc.weightx = 1; gbc.gridx = 1; add(txtName, gbc);
        gbc.weightx = 0; gbc.gridx = 0; gbc.gridy = 1; add(new JLabel("Contraseña:"), gbc);
        gbc.weightx = 1; gbc.gridx = 1; add(txtPassword, gbc);
        gbc.weightx = 0; gbc.gridx = 0; gbc.gridy = 2; add(new JLabel("Confirmar contraseña:"), gbc);
        gbc.weightx = 1; gbc.gridx = 1; add(txtConfirm, gbc);

        JButton btnSignUp = new JButton("Registrarse");
        JButton btnBack = new JButton("Volver al inicio de sesión");
        gbc.weightx = 1; gbc.gridwidth = 2;
        gbc.gridx = 0; gbc.gridy = 3; add(btnSignUp, gbc);
        gbc.gridy = 4; add(btnBack, gbc);

        btnSignUp.addActionListener(e -> {
            String name = txtName.getText().trim();
            String password = new String(txtPassword.getPassword());
            String confirm = new String(txtConfirm.getPassword());

            if (name.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(instance, "El usuario y la contraseña no pueden estar vacíos.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (!password.equals(confirm)) {
                JOptionPane.showMessageDialog(instance, "Las contraseñas no coinciden.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            Controller.action(Event.SIGN_UP, new SignUpCredentials(name, password));
        });

        btnBack.addActionListener(e -> {
            instance.dispose();
            LoginView.open();
        });
    }

    public static void update(int event, Object data) {
        switch (event) {
            case Event.RES_SIGN_UP_SUCCESS -> {
                JOptionPane.showMessageDialog(instance, "Registro completado. Ya puedes iniciar sesión.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                instance.dispose();
                LoginView.open();
            }
            case Event.RES_SIGN_UP_FAILURE ->
                JOptionPane.showMessageDialog(instance, "El nombre de usuario ya existe.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
