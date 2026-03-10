package negocio;

import java.util.Collection;
import integracion.DAOCliente;
import integracion.DAOClienteImp;

import com.modeliosoft.modelio.javadesigner.annotations.objid;

@objid ("3e41cb74-2903-4975-b822-3a84bb853f25")
public class SAClienteImp implements SACliente {
    @objid ("03ba48e0-e6e9-446c-aa3d-df7741d311e4")
    public int altaCliente(final tCliente cliente) {
        // TODO Auto-generated return
        return 0;
    }

    @objid ("41363b00-839c-4740-93ad-d35ef7555d06")
    public tCliente leerCliente() {
        // TODO Auto-generated return
        return null;
    }

    @objid ("8d9a7111-3e7a-444c-b943-a7fc48fbdb02")
    public int actualizarCliente(final tCliente cliente) {
        // TODO Auto-generated return
        return 0;
    }

    @objid ("af684d06-6239-4850-89d9-b4b20ea53d1d")
    public int borrarCliente(final String dni) {
        // TODO Auto-generated return
        return 0;
    }

	@Override
	public Collection<tCliente> leerTodosClientes() {
		DAOCliente clienteDao = new DAOClienteImp(); 
		return clienteDao.readAllClientes(); 
	}

}
