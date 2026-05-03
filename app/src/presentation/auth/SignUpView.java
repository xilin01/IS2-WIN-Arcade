package presentation.auth;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import business.auth.tCredentials;
import presentation.Controller;
import presentation.Event;
import presentation.Theme;
import presentation.games.BackgroundPanel;

@SuppressWarnings("serial")
public class SignUpView extends JFrame {
    private static final Theme theme = Theme.getInstance();
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

        BackgroundPanel background = new BackgroundPanel("resources/icons/menu_inicio.png");
        background.setLayout(new GridBagLayout());
        setContentPane(background);

        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(new Color(theme.BG_CARD.getRed(), theme.BG_CARD.getGreen(), theme.BG_CARD.getBlue(), 215));
        mainPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(theme.BORDER, 1),
            BorderFactory.createEmptyBorder(24, 32, 24, 32)
        ));
        GridBagConstraints outer = new GridBagConstraints();
        outer.insets = new Insets(100, 0, 0, 0);
        background.add(mainPanel, outer);

        styleField(txtName);
        styleField(txtPassword);
        styleField(txtConfirm);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 10, 6, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel txt1 = new JLabel("Usuario:");
        txt1.setForeground(theme.TEXT); txt1.setFont(theme.BODY);
        JLabel txt2 = new JLabel("Contraseña:");
        txt2.setForeground(theme.TEXT); txt2.setFont(theme.BODY);
        JLabel txt3 = new JLabel("Confirmar contraseña:");
        txt3.setForeground(theme.TEXT); txt3.setFont(theme.BODY);

        gbc.weightx = 0; gbc.gridx = 0; gbc.gridy = 0; mainPanel.add(txt1, gbc);
        gbc.weightx = 1; gbc.gridx = 1; mainPanel.add(txtName, gbc);
        gbc.weightx = 0; gbc.gridx = 0; gbc.gridy = 1; mainPanel.add(txt2, gbc);
        gbc.weightx = 1; gbc.gridx = 1; mainPanel.add(txtPassword, gbc);
        gbc.weightx = 0; gbc.gridx = 0; gbc.gridy = 2; mainPanel.add(txt3, gbc);
        gbc.weightx = 1; gbc.gridx = 1; mainPanel.add(txtConfirm, gbc);

        gbc.weightx = 1; gbc.gridwidth = 2;
        gbc.gridx = 0; gbc.gridy = 3;
        mainPanel.add(theme.primaryBtn("Registrarse", () -> {
            String name = txtName.getText().trim();
            String password = new String(txtPassword.getPassword());
            String confirm = new String(txtConfirm.getPassword());
            if(name.isEmpty() || password.isEmpty()) theme.showError(instance, "El usuario o la contraseña no pueden estar vacíos");
            else if(!password.equals(confirm)) theme.showError(instance, "Las contraseñas no coinciden");
            else Controller.getInstance().action(Event.SIGN_UP, new tCredentials(name, password));
        }), gbc);
        gbc.gridy = 4;
        mainPanel.add(theme.secondaryBtn("Volver al inicio de sesión", () -> {
            LoginView.open();
            instance.dispose();
        }), gbc);
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

    public static void update(int event, Object data) {
        switch(event) {
            case Event.RES_SIGN_UP_SUCCESS:
                theme.showInfo(instance, "Registro completado, ya puedes iniciar sesión");
                LoginView.open();
                instance.dispose();
                break;
            case Event.RES_SIGN_UP_FAILURE:
                theme.showError(instance, "El nombre de usuario ya existe");
                break;
        }
    }
}
