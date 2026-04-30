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

@SuppressWarnings("serial")
public class SignUpView extends JFrame {
    private static SignUpView instance;
    private final JTextField txtName = new JTextField(16);
    private final JPasswordField txtPassword = new JPasswordField(16);
    private final JPasswordField txtConfirm = new JPasswordField(16);

    private SignUpView() {
        super("Menú de registro");
        initGUI();
    }

    public static void open() {
        if(instance == null || !instance.isDisplayable()) instance = new SignUpView();
        instance.setVisible(true);
    }

    private void initGUI() {
    	setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
    	
        BackgroundPanel background = new BackgroundPanel("resources/icons/entrada.png");
        background.setLayout(new GridBagLayout());
        setContentPane(background);
        
        GridBagConstraints gbcCentrado = new GridBagConstraints();
        
        JPanel mainPanel = new JPanel();
        mainPanel.setVisible(true);
        mainPanel.setSize(360, 280);
        mainPanel.setLayout(new GridBagLayout());
        mainPanel.setBackground(new Color(0, 0, 0, 150));
        gbcCentrado.insets = new Insets(100, 0, 0, 0); 
        background.add(mainPanel, gbcCentrado);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 10, 6, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        JLabel txt1 = new JLabel("Usuario:"); txt1.setForeground(Color.WHITE);
        JLabel txt2 = new JLabel("Contraseña:"); txt2.setForeground(Color.WHITE);
        JLabel txt3 = new JLabel("Confirmar contraseña:"); txt3.setForeground(Color.WHITE);
        gbc.weightx = 0; gbc.gridx = 0; gbc.gridy = 0; mainPanel.add(txt1, gbc);
        gbc.weightx = 1; gbc.gridx = 1; mainPanel.add(txtName, gbc);
        gbc.weightx = 0; gbc.gridx = 0; gbc.gridy = 1; mainPanel.add(txt2, gbc);
        gbc.weightx = 1; gbc.gridx = 1; mainPanel.add(txtPassword, gbc);
        gbc.weightx = 0; gbc.gridx = 0; gbc.gridy = 2; mainPanel.add(txt3, gbc);
        gbc.weightx = 1; gbc.gridx = 1; mainPanel.add(txtConfirm, gbc);

        JButton btnSignUp = new JButton("Registrarse");
        btnSignUp.addActionListener(e -> {
            String name = txtName.getText().trim();
            String password = new String(txtPassword.getPassword());
            String confirm = new String(txtConfirm.getPassword());

            if(name.isEmpty() || password.isEmpty()) JOptionPane.showMessageDialog(instance, "El usuario o la contraseña no pueden estar vacíos.", "Error", JOptionPane.ERROR_MESSAGE);
            else if(!password.equals(confirm)) JOptionPane.showMessageDialog(instance, "Las contraseñas no coinciden.", "Error", JOptionPane.ERROR_MESSAGE);
            else Controller.getInstance().action(Event.SIGN_UP, new tCredentials(name, password));
        });
        JButton btnBack = new JButton("Volver al inicio de sesión");
        btnBack.addActionListener(e -> {
            LoginView.open();
            instance.dispose();
        });
        gbc.weightx = 1; gbc.gridwidth = 2;
        gbc.gridx = 0; gbc.gridy = 3; mainPanel.add(btnSignUp, gbc);
        gbc.gridy = 4; mainPanel.add(btnBack, gbc);
    }
    
    public static void update(int event, Object data) {
        switch(event) {
            case Event.RES_SIGN_UP_SUCCESS:
                JOptionPane.showMessageDialog(instance, "Registro completado, ya puedes iniciar sesión.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                LoginView.open();
                instance.dispose();
                break;
            case Event.RES_SIGN_UP_FAILURE: JOptionPane.showMessageDialog(instance, "El nombre de usuario ya existe.", "Error", JOptionPane.ERROR_MESSAGE); break;
        }
    }
}
