package presentation.auth;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import business.auth.tCredentials;
import presentation.Controller;
import presentation.Event;
import presentation.games.BackgroundPanel;
import presentation.user.PlayerView;
import presentation.user.StaffView;

@SuppressWarnings("serial")
public class LoginView extends JFrame {
    private static LoginView instance;
    private final JTextField txtName = new JTextField(16);
    private final JPasswordField txtPass = new JPasswordField(16);

    private LoginView() {
        super("Menú de incio de sesión");
        initGUI();
    }

    public static void open() {
        if(instance == null || !instance.isDisplayable()) instance = new LoginView();
        instance.setVisible(true);
    }

    private void initGUI() {
    	setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        
        BackgroundPanel background = new BackgroundPanel("resources/icons/menu_inicio.png");
        background.setLayout(new GridBagLayout());
        setContentPane(background);
        
        GridBagConstraints gbcCentrado = new GridBagConstraints();
        
    	JPanel mainPanel = new JPanel();
        mainPanel.setSize(320, 200);
        mainPanel.setLayout(new GridBagLayout());
        mainPanel.setBackground(new Color(0, 0, 0, 150));
        gbcCentrado.insets = new Insets(100, 0, 0, 0); 
        background.add(mainPanel, gbcCentrado);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 10, 6, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        JLabel txt1 = new JLabel("Usuario:"); txt1.setForeground(Color.WHITE);
        JLabel txt2 = new JLabel("Contraseña:"); txt2.setForeground(Color.WHITE);
        gbc.gridx = 0; gbc.gridy = 0; mainPanel.add(txt1, gbc);
        gbc.gridx = 1; mainPanel.add(txtName, gbc);
        gbc.gridx = 0; gbc.gridy = 1; mainPanel.add(txt2, gbc);
        gbc.gridx = 1; mainPanel.add(txtPass, gbc);

        JButton btnLogin = new JButton("Iniciar sesión");
        btnLogin.addActionListener(e -> Controller.getInstance().action(Event.LOGIN, new tCredentials(txtName.getText().trim(), new String(txtPass.getPassword()))));
        JButton btnSignUp = new JButton("¿No tienes cuenta? Regístrate");
        btnSignUp.addActionListener(e -> {
            SignUpView.open();
            instance.dispose();
        });
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        mainPanel.add(btnLogin, gbc);
        gbc.gridy = 3;
        mainPanel.add(btnSignUp, gbc);
    }

    public static void update(int event, Object data) {
        switch(event) {
            case Event.RES_LOGIN_STAFF_SUCCESS:
                new StaffView();
                instance.dispose();
                break;
            case Event.RES_LOGIN_SUCCESS:
                new PlayerView();
                instance.dispose();
                break;
            case Event.RES_LOGIN_FAILURE: JOptionPane.showMessageDialog(instance, "Usuario o contraseña incorrectos.", "Error", JOptionPane.ERROR_MESSAGE); break;
        }
    }
}
