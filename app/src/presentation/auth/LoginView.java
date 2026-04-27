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
import business.auth.tCredentials;
import presentation.Controller;
import presentation.Event;
import presentation.user.PlayerView;
import presentation.user.StaffView;

@SuppressWarnings("serial")
public class LoginView extends JFrame {
    private static LoginView instance;
    private final JTextField txtName = new JTextField(16);
    private final JPasswordField txtPass = new JPasswordField(16);

    private LoginView() {
        super("Login");
        initGUI();
    }

    public static void open() {
        if(instance == null || !instance.isDisplayable()) instance = new LoginView();
        instance.setVisible(true);
    }

    private void initGUI() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(320, 200);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 10, 6, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0; add(new JLabel("Usuario:"), gbc);
        gbc.gridx = 1; add(txtName, gbc);
        gbc.gridx = 0; gbc.gridy = 1; add(new JLabel("Contraseña:"), gbc);
        gbc.gridx = 1; add(txtPass, gbc);

        JButton btnLogin = new JButton("Iniciar sesión");
        JButton btnSignUp = new JButton("¿No tienes cuenta? Regístrate");
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        add(btnLogin, gbc);
        gbc.gridy = 3;
        add(btnSignUp, gbc);

        btnLogin.addActionListener(e -> Controller.getInstance().action(Event.LOGIN, new tCredentials(txtName.getText().trim(), new String(txtPass.getPassword()))));

        btnSignUp.addActionListener(e -> {
            instance.dispose();
            SignUpView.open();
        });
    }

    public static void update(int event, Object data) {
        switch(event) {
            case Event.RES_LOGIN_STAFF_SUCCESS:
                instance.dispose();
                new StaffView();
                break;
            case Event.RES_LOGIN_SUCCESS:
                instance.dispose();
                new PlayerView();
                break;
            case Event.RES_LOGIN_FAILURE: JOptionPane.showMessageDialog(instance, "Usuario o contraseña incorrectos.", "Error", JOptionPane.ERROR_MESSAGE); break;
        }
    }
}
