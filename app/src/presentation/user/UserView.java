package presentation.user;

import java.awt.Dimension;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import presentation.Event;

public class UserView {

    public static void update(int event, Object data) {
        switch(event) {
            case Event.RES_CREATE_USER_SUCCESS: JOptionPane.showMessageDialog(null, "Usuario registrado con éxito."); break;
            case Event.RES_CREATE_USER_FAILURE: JOptionPane.showMessageDialog(null, "No se ha podido dar de alta al usuario, el nombre ya existe."); break;
            case Event.RES_LIST_USERS_SUCCESS: {
                JTextArea textArea = new JTextArea(data.toString());
                
                textArea.setLineWrap(true);
                textArea.setEditable(false);
                textArea.setWrapStyleWord(true);
                JScrollPane scrollPane = new JScrollPane(textArea);
                scrollPane.setPreferredSize(new Dimension(500, 200));
                JOptionPane.showMessageDialog(null, scrollPane, "Usuarios", JOptionPane.DEFAULT_OPTION);
                break;
            }
            case Event.RES_LIST_USERS_FAILURE: JOptionPane.showMessageDialog(null, "No se ha podido encontrar ningún usuario."); break;
            case Event.RES_FIND_USER_SUCCESS: JOptionPane.showMessageDialog(null, "Usuario encontrado:\n" + data.toString()); break;
            case Event.RES_FIND_USER_FAILURE: JOptionPane.showMessageDialog(null, "No se ha podido encontrar el usuario."); break;
            case Event.RES_DELETE_USER_SUCCESS: JOptionPane.showMessageDialog(null, "Usuario desactivado correctamente."); break;
            case Event.RES_DELETE_USER_FAILURE: JOptionPane.showMessageDialog(null, "No se ha podido desactivar el usuario."); break;
            case Event.RES_UPDATE_USER_SUCCESS: JOptionPane.showMessageDialog(null, "Usuario modificado correctamente."); break;
            case Event.RES_UPDATE_USER_FAILURE: JOptionPane.showMessageDialog(null, "No se ha podido encontrar el usuario."); break;
        }
    }
}
