package presentation;

import java.util.Collection;
import com.modeliosoft.modelio.javadesigner.annotations.objid;
import business.SAUser;
import business.SAUserImp;
import business.tUser;

// POR TRADUCIR!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
@objid ("89dcc83d-dee8-45f7-aee4-45b7f33b671f")
public class Controller {
    @objid ("473dcd65-39b0-4cac-b363-8e9abe4bf896")
    private static SAUser saUser = new SAUserImp();
    
    @objid ("7d6f7991-228b-4d7a-a5ff-094d1cd303bf")
    public static void action(final int event, final Object data) {
		switch(event) {
			case Event.ALTA_USUARIO: { // 1 = ALTA_USUARIOS
				tUser newUser = (tUser)data;
				int res = saUser.createUser(newUser);
				
				if (res > 0) {
					UserView.actualizar(Event.RES_ALTA_USUARIO_OK, res); // ¿Datos?
				}
				else {
					UserView.actualizar(Event.RES_ALTA_USUARIO_KO, null);
				}
				break;
			}
			case Event.MOSTRAR_USUARIOS: {
				Collection<tUser> res = saUser.readAllUsers();
				
				if(!res.isEmpty()) UserView.actualizar(Event.RES_MOSTRAR_USUARIOS_OK, res);
				else UserView.actualizar(Event.RES_MOSTRAR_USUARIOS_KO, null);
				break;
			}
			case Event.BUSCAR_USUARIO: { // 3 = BUSCAR_USUARIO
				String name = (String) data;
				tUser user = saUser.readUser(name);
				
				if (user != null) UserView.actualizar(Event.RES_BUSCAR_USUARIO_OK, user);
				else UserView.actualizar(Event.RES_BUSCAR_USUARIO_KO, null);
				break;
			}
			case Event.ELIMINAR_USUARIO: { // 4 = ELIMINAR_USUARIO
				String name = (String) data;
				int res = saUser.deleteUser(name);
				
				if (res > 0) UserView.actualizar(Event.RES_ELIMINAR_USUARIO_OK, data);
				else UserView.actualizar(Event.RES_ELIMINAR_USUARIO_KO, null);
				break;
			}
			case Event.MODIFICAR_USUARIO: { // 5 = MODIFICAR_USUARIO
				tUser modUser = (tUser) data;
				int res = saUser.updateUser(modUser);
				
				if (res > 0) UserView.actualizar(Event.RES_MODIFICAR_USUARIO_OK, data);
				else UserView.actualizar(Event.RES_MODIFICAR_USUARIO_KO, null);
				break;
			}
			case Event.LOGIN: {
			    tUser credentials = (tUser) data;
			    tUser user = saUser.readUser(credentials.getName());

			    if (user != null && user.getPassword().equals(credentials.getPassword()) && user.getActive()) {
			        LoginView.actualizar(Event.RES_LOGIN_OK, user);
			    } else {
			    	LoginView.actualizar(Event.RES_LOGIN_KO, null);
			    }
			    break;
			}
			case Event.STAFF_LOGIN: {
			    tUser credentials = (tUser) data;
			    tUser user = saUser.readUser(credentials.getName());

			    if (user != null && user.getPassword().equals(credentials.getPassword()) && user.getActive()) {
			        LoginView.actualizar(Event.RES_LOGIN_STAFF_OK, user);
			    } else {
			    	LoginView.actualizar(Event.RES_LOGIN_KO, null);
			    }
			    break;
			}
		}
	}
}
