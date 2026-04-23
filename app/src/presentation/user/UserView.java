package presentation.user;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import presentation.Controller;
import presentation.Event;

@SuppressWarnings("serial")
public class UserView extends JFrame {

    public UserView() {
        super();
        initGUI();
    }

    void initGUI() {
        JFrame mainFrame = new JFrame();

        mainFrame.setTitle("Menu CRUDs Usuario");
        mainFrame.setBounds(100, 100, 450, 300);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.getContentPane().setLayout(new FlowLayout());
        mainFrame.setVisible(true);

        JButton createButton = new JButton();
        createButton.setText("Alta Usuario");
        createButton.setBounds(153, 180, 132, 32);
        mainFrame.getContentPane().add(createButton);

        JButton readAllButton = new JButton();
        readAllButton.setText("Mostrar usuarios");
        readAllButton.setBounds(253, 380, 132, 32);
        mainFrame.getContentPane().add(readAllButton);

        // Listener's
        createButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // TBD (To Be Developed)
            }
        });
        readAllButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Controller.action(Event.LIST_USERS, null);
            }
        });
    }

    public static void update(int event, Object data) {
        switch (event) {
            case Event.RES_CREATE_USER_SUCCESS: {
                JOptionPane.showMessageDialog(null, "Usuario registrado con éxito.");
                break;
            }
            case Event.RES_CREATE_USER_FAILURE: {
                JOptionPane.showMessageDialog(null, "No se ha podido dar de alta al usuario. El nombre ya existe.");
                break;
            }
            case Event.RES_LIST_USERS_SUCCESS: {
                JTextArea textArea = new JTextArea(data.toString());
                textArea.setLineWrap(true);
                textArea.setEditable(false);
                textArea.setWrapStyleWord(true);
                JScrollPane scrollPane = new JScrollPane(textArea);
                scrollPane.setPreferredSize(new Dimension(500, 200));
                JOptionPane.showMessageDialog(null, scrollPane, "USUARIOS", JOptionPane.DEFAULT_OPTION);
                break;
            }
            case Event.RES_LIST_USERS_FAILURE: {
                JOptionPane.showMessageDialog(null, "No se ha podido encontrar ningun usuario");
                break;
            }
            case Event.RES_FIND_USER_SUCCESS: {
                JOptionPane.showMessageDialog(null, "Usuario encontrado:\n" + data.toString());
                break;
            }
            case Event.RES_FIND_USER_FAILURE: {
                JOptionPane.showMessageDialog(null, "No se ha podido encontrar el usuario");
                break;
            }
            case Event.RES_DELETE_USER_SUCCESS: {
                JOptionPane.showMessageDialog(null, "Usuario eliminado correctamente");
                break;
            }
            case Event.RES_DELETE_USER_FAILURE: {
                JOptionPane.showMessageDialog(null, "No se ha podido encontrar el usuario");
                break;
            }
            case Event.RES_UPDATE_USER_SUCCESS: {
                JOptionPane.showMessageDialog(null, "Usuario modificado correctamente");
                break;
            }
            case Event.RES_UPDATE_USER_FAILURE: {
                JOptionPane.showMessageDialog(null, "No se ha podido encontrar el usuario");
                break;
            }
        }
    }
}
