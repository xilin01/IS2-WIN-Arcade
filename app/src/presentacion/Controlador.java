package presentacion;

import java.util.Collection;
import com.modeliosoft.modelio.javadesigner.annotations.objid;
import negocio.SAUsuario;
import negocio.SAUsuarioImp;
import negocio.tUsuario;

@objid ("89dcc83d-dee8-45f7-aee4-45b7f33b671f")
public class Controlador {
    @objid ("473dcd65-39b0-4cac-b363-8e9abe4bf896")
    private SAUsuario saUsuario;

    @objid ("7d6f7991-228b-4d7a-a5ff-094d1cd303bf")
    public static void accion(final int evento, final tUsuario datos) {
		switch (evento) {
			case Evento.ALTA_USUARIO: { // 1 = ALTA_USUARIOS
				// TBD (To Be Developed)
				break;
			}
			case Evento.MOSTRAR_USUARIOS: { // 2 = MOSTRAR_USUARIOS
				SAUsuario SAUsuario = new SAUsuarioImp();
				Collection<tUsuario> res = SAUsuario.leerTodosUsuarios();
				
				if (!res.isEmpty()) VistaUsuario.actualizar(Evento.RES_MOSTRAR_USUARIOS_OK, res);
				else VistaUsuario.actualizar(Evento.RES_MOSTRAR_USUARIOS_KO, null);
				break;
			}
			case Evento.BUSCAR_USUARIO: { // 3 = BUSCAR_USUARIO
				// TBD
				break;
			}
			case Evento.ELIMINAR_USUARIO: { // 4 = ELIMINAR_USUARIO
				// TBD
				break;
			}
			case Evento.MODIFICAR_USUARIO: { // 5 = MODIFICAR_USUARIO
				// TBD
				break;
			}
		}
	}
}
