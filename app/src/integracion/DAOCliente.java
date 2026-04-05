package integracion;

import java.util.Collection;
import com.modeliosoft.modelio.javadesigner.annotations.objid;
import negocio.tCliente;

@objid ("5a46a1fb-6472-4cac-8fc4-f920325561e2")
public interface DAOCliente {
	
    /**
     * Inserta un cliente a la base de datos.
     * 
     * Argumentos:
     * Cliente -> cliente a insertar en la base de datos
     * 
     * Return:
     * int -> numero de filas afectadas por la inserción
     */
    @objid ("0dda0d1c-df49-4e08-8f8c-92c11adaf514")
    int createCliente(final tCliente cliente);

    /**
     * Lee los datos de un cliente en la base de datos.
     * 
     * Argumentos:
     * string dni -> El dni del usuario que se quiere leer
     * 
     * Return:
     * tCliente -> un tCliente con los valores de la bbdd asociados al dni
     */
    @objid ("a22eec57-7fb2-480a-a47d-5434a9e9fc37")
    tCliente readCliente(final String dni);

    /**
     * Actualiza los datos de un cliente existente en la base de datos.
     * 
     * Argumentos:
     * tCliente cliente -> El cliente con los nuevos datos a actualizar
     * 
     * Return:
     * int -> El número de filas afectadas por la actualización
     */
    @objid ("bf596e07-16c1-49fc-b60f-c64cb21ae15f")
    int updateCliente(final tCliente cliente);

    /**
     * Elimina un cliente de la base de datos.
     * 
     * Argumentos:
     * String dni -> El dni del cliente que se quiere eliminar
     * 
     * Return:
     * int -> El número de filas afectadas por el borrado
     */
    @objid ("85fe8495-34fe-4aae-89b5-4967f516b4d2")
    int deleteCliente(final String dni);

	Collection<tCliente> readAllClientes();
}
