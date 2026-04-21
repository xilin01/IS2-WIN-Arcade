package presentation;

import java.util.Collection;
import com.modeliosoft.modelio.javadesigner.annotations.objid;
import business.SAUser;
import business.SAAuth;
import business.AuthResult;
import business.ServiceLocator;
import business.tUser;

@objid ("89dcc83d-dee8-45f7-aee4-45b7f33b671f")
public class Controller {

    @objid ("7d6f7991-228b-4d7a-a5ff-094d1cd303bf")
    public static void action(final int event, final Object data) {
		switch (event) {
			case Event.CREATE_USER: {
				SAUser saUser = ServiceLocator.get(SAUser.class);
				tUser newUser = (tUser) data;
				int res = saUser.createUser(newUser);
				if (res > 0) UserView.update(Event.RES_CREATE_USER_SUCCESS, res);
				else         UserView.update(Event.RES_CREATE_USER_FAILURE, null);
				break;
			}
			case Event.LIST_USERS: {
				SAUser saUser = ServiceLocator.get(SAUser.class);
				Collection<tUser> res = saUser.readAllUsers();
				if (!res.isEmpty()) UserView.update(Event.RES_LIST_USERS_SUCCESS, res);
				else                UserView.update(Event.RES_LIST_USERS_FAILURE, null);
				break;
			}
			case Event.FIND_USER: {
				SAUser saUser = ServiceLocator.get(SAUser.class);
				String name = (String) data;
				tUser user = saUser.readUser(name);
				if (user != null) UserView.update(Event.RES_FIND_USER_SUCCESS, user);
				else              UserView.update(Event.RES_FIND_USER_FAILURE, null);
				break;
			}
			case Event.DELETE_USER: {
				SAUser saUser = ServiceLocator.get(SAUser.class);
				String name = (String) data;
				int res = saUser.deleteUser(name);
				if (res > 0) UserView.update(Event.RES_DELETE_USER_SUCCESS, data);
				else         UserView.update(Event.RES_DELETE_USER_FAILURE, null);
				break;
			}
			case Event.UPDATE_USER: {
				SAUser saUser = ServiceLocator.get(SAUser.class);
				tUser modUser = (tUser) data;
				int res = saUser.updateUser(modUser);
				if (res > 0) UserView.update(Event.RES_UPDATE_USER_SUCCESS, data);
				else         UserView.update(Event.RES_UPDATE_USER_FAILURE, null);
				break;
			}
			case Event.LOGIN: {
				SAAuth saAuth = ServiceLocator.get(SAAuth.class);
				LoginCredentials credentials = (LoginCredentials) data;
				AuthResult result = saAuth.authenticate(credentials.name(), credentials.password());
				int loginEvent = switch (result.getOutcome()) {
					case STAFF_OK  -> Event.RES_LOGIN_STAFF_SUCCESS;
					case PLAYER_OK -> Event.RES_LOGIN_SUCCESS;
					case FAILURE   -> Event.RES_LOGIN_FAILURE;
				};
				LoginView.update(loginEvent, result.getPayload());
				break;
			}
			case Event.LOGOUT: {
				LoginView.open();
				break;
			}
			case Event.OPEN_PACKAGE_MANAGER: {
				String username = (String) data;
				new PackageManagerView(username);
				break;
			}
			case Event.CLOSE_PACKAGE_MANAGER: {
				String username = (String) data;
				new StaffView(username);
				break;
			}
		}
	}
}
