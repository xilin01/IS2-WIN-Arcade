package presentacion;

import java.util.Collection;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import negocio.SACliente;
import negocio.SAClienteImp;
import negocio.tCliente;

@objid ("89dcc83d-dee8-45f7-aee4-45b7f33b671f")
public class Controlador {
    @objid ("473dcd65-39b0-4cac-b363-8e9abe4bf896")
    private SACliente saCliente;

    @objid ("7d6f7991-228b-4d7a-a5ff-094d1cd303bf")
    public static void accion(final int evento, final tCliente datos) {
		switch (evento) {
		
			case Evento.ALTA_CLIENTE: { // 1 = ALTA_CLIENTES
				// TBD (To Be Developed)
				break;
			}
			case Evento.MOSTRAR_CLIENTES: { // 2 = MOSTRAR_CLIENTES
				SACliente SACliente = new SAClienteImp();
				Collection<tCliente> res = SACliente.leerTodosClientes();
				if (!res.isEmpty())
					VistaCliente.actualizar(Evento.RES_MOSTRAR_CLIENTES_OK, res);
				else
					VistaCliente.actualizar(Evento.RES_MOSTRAR_CLIENTES_KO, null);
				break;
			}
			case Evento.BUSCAR_CLIENTE: { // 3 = BUSCAR_CLIENTE
				// TBD
				break;
			}
			case Evento.ELIMINAR_CLIENTE: { // 4 = ELIMINAR_CLIENTE
				// TBD
				break;
			}
			case Evento.MODIFICAR_CLIENTE: { // 5 = MODIFICAR_CLIENTE
				// TBD
				break;
			}
		}
	}

}
