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
public class LoginView extends JFrame {
    private static final Theme theme = Theme.getInstance();
    
    private static LoginView instance;
    private final JTextField txtName = new JTextField(16);
    private final JPasswordField txtPass = new JPasswordField(16);

    private LoginView() {
        super("Menú de inicio de sesión");
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

        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(new Color(theme.BG_CARD.getRed(), theme.BG_CARD.getGreen(), theme.BG_CARD.getBlue(), 215));
        mainPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(theme.BORDER, 1), BorderFactory.createEmptyBorder(24, 32, 24, 32)));
        GridBagConstraints outer = new GridBagConstraints();
        outer.insets = new Insets(100, 0, 0, 0);
        background.add(mainPanel, outer);

        styleField(txtName);
        styleField(txtPass);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 10, 6, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel txt1 = new JLabel("Usuario:");
        txt1.setForeground(theme.TEXT);
        txt1.setFont(theme.BODY);
        JLabel txt2 = new JLabel("Contraseña:");
        txt2.setForeground(theme.TEXT);
        txt2.setFont(theme.BODY);

        gbc.gridx = 0; gbc.gridy = 0; mainPanel.add(txt1, gbc);
        gbc.gridx = 1; mainPanel.add(txtName, gbc);
        gbc.gridx = 0; gbc.gridy = 1; mainPanel.add(txt2, gbc);
        gbc.gridx = 1; mainPanel.add(txtPass, gbc);

        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        mainPanel.add(theme.primaryBtn("Iniciar sesión",
        		() -> Controller.getInstance().action(Event.LOGIN, new tCredentials(txtName.getText().trim(), new String(txtPass.getPassword())))), gbc);
        gbc.gridy = 3;
        mainPanel.add(theme.secondaryBtn("¿No tienes cuenta? Regístrate", () -> {
            SignUpView.open();
            instance.dispose();
        }), gbc);
    }

    private static void styleField(JTextField field) {
        field.setBackground(theme.BG_ITEM);
        field.setForeground(theme.TEXT);
        field.setCaretColor(theme.TEXT);
        field.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(theme.BORDER, 1), BorderFactory.createEmptyBorder(4, 8, 4, 8)));
    }

    public static void update(int event, Object data) {
        switch(event) {
            case Event.RES_LOGIN_STAFF_SUCCESS:
                Controller.getInstance().action(Event.OPEN_STAFF_VIEW, null);
                instance.dispose();
                break;
            case Event.RES_LOGIN_SUCCESS:
                Controller.getInstance().action(Event.OPEN_PLAYER_VIEW, null);
                instance.dispose();
                break;
            case Event.RES_LOGIN_FAILURE:
                theme.showError(instance, "Usuario o contraseña incorrectos");
                break;
        }
    }
}
