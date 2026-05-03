package presentation.user;

import presentation.Event;
import presentation.Theme;

public class UserView {
    private static final Theme theme = Theme.getInstance();

    public static void update(int event, Object data) {
        switch(event) {
            case Event.RES_CREATE_PLAYER_SUCCESS: theme.showInfo(null, "Usuario registrado con éxito."); break;
            case Event.RES_CREATE_PLAYER_FAILURE: theme.showError(null, "No se ha podido dar de alta al usuario, el nombre ya existe."); break;
            case Event.RES_DELETE_PLAYER_SUCCESS: theme.showInfo(null, "Usuario desactivado correctamente."); break;
            case Event.RES_DELETE_PLAYER_FAILURE: theme.showError(null, "No se ha podido desactivar el usuario."); break;
            case Event.RES_UPDATE_PLAYER_SUCCESS: theme.showInfo(null, "Usuario modificado correctamente."); break;
            case Event.RES_UPDATE_PLAYER_FAILURE: theme.showError(null, "No se ha podido encontrar el usuario."); break;
        }
    }
}
