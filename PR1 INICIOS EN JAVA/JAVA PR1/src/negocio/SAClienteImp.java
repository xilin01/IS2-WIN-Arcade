package negocio;

import java.util.Collection;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import integracion.DAOCliente;

@objid ("3e41cb74-2903-4975-b822-3a84bb853f25")
public class SAClienteImp implements SACliente {
    @objid ("d83d92ae-22a6-4757-abcf-c7ba3c7147fa")
    private DAOCliente daoCliente;

    /**
     * Valida los datos y registra un nuevo cliente en el sistema.
     * 
     * Argumentos:
     * tCliente cliente -> El cliente que se quiere dar de alta
     * 
     * Return:
     * int -> El número de filas afectadas
     */
    @objid ("03ba48e0-e6e9-446c-aa3d-df7741d311e4")
    public int altaCliente(final tCliente cliente) {
        // TODO Auto-generated return
        return 0;
    }

    /**
     * Valida el dni y busca los datos de un cliente en el sistema.
     * 
     * Argumentos:
     * String dni -> El dni del cliente que se quiere consultar
     * 
     * Return:
     * tCliente -> El objeto con los datos del cliente, o null si no existe
     */
    @objid ("41363b00-839c-4740-93ad-d35ef7555d06")
    public tCliente leerCliente(final String dni) {
        // TODO Auto-generated return
        return null;
    }

    /**
     * Valida los datos y actualiza la información de un cliente en el sistema.
     * 
     * Argumentos:
     * tCliente cliente -> El cliente con los datos modificados
     * 
     * Return:
     * int -> El numero de filas afectadas por el update
     */
    @objid ("8d9a7111-3e7a-444c-b943-a7fc48fbdb02")
    public int actualizarCliente(final tCliente cliente) {
        // TODO Auto-generated return
        return 0;
    }
    
    @Override
	public Collection<tCliente> leerTodosClientes() {
		return daoCliente.readAllClientes(); 
	}

    /**
     * Valida el dni y elimina un cliente del sistema.
     * 
     * Argumentos:
     * String dni -> El dni del cliente a borrar
     * 
     * Return:
     * int -> El numero de filas afectadas por el delete
     */
    @objid ("af684d06-6239-4850-89d9-b4b20ea53d1d")
    public int borrarCliente(final String dni) {
        // TODO Auto-generated return
        return 0;
    }

    @objid ("f3ca4813-ec77-4455-a092-46f5071ca40f")
    public SAClienteImp() {
        this.daoCliente = new integracion.DAOClienteImp();
    }

}
