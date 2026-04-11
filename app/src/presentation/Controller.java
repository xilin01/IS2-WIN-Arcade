package presentation;

import java.util.Collection;
import com.modeliosoft.modelio.javadesigner.annotations.objid;

import business.SAUser;
import business.SAUserImp;
import business.User;

@objid ("89dcc83d-dee8-45f7-aee4-45b7f33b671f")
public class Controller {
    @objid ("473dcd65-39b0-4cac-b363-8e9abe4bf896")
    private SAUser saUsuario;

    @objid ("7d6f7991-228b-4d7a-a5ff-094d1cd303bf")
    public static void accion(final int evento, final User datos) {
		switch (evento) {
			case Event.ALTA_USUARIO: { // 1 = ALTA_USUARIOS
				// TBD (To Be Developed)
				break;
			}
			case Event.MOSTRAR_USUARIOS: { // 2 = MOSTRAR_USUARIOS
				SAUser SAUsuario = new SAUserImp();
				Collection<User> res = SAUsuario.leerTodosUsuarios();
				
				if (!res.isEmpty()) UserView.actualizar(Event.RES_MOSTRAR_USUARIOS_OK, res);
				else UserView.actualizar(Event.RES_MOSTRAR_USUARIOS_KO, null);
				break;
			}
			case Event.BUSCAR_USUARIO: { // 3 = BUSCAR_USUARIO
				// TBD
				break;
			}
			case Event.ELIMINAR_USUARIO: { // 4 = ELIMINAR_USUARIO
				// TBD
				break;
			}
			case Event.MODIFICAR_USUARIO: { // 5 = MODIFICAR_USUARIO
				// TBD
				break;
			}
		}
	}
}
