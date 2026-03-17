package negocio;

import com.modeliosoft.modelio.javadesigner.annotations.objid;

@objid ("adece493-ec9f-4c1f-9e56-03888713105a")
public interface SACliente {
    /**
     * Valida los datos y registra un nuevo cliente en el sistema.
     * 
     * Argumentos:
     * tCliente cliente -> El cliente que se quiere dar de alta
     * 
     * Return:
     * int -> El número de filas afectadas
     */
    @objid ("81478c33-59ab-4792-be0c-0deaf4289b97")
    int altaCliente(final tCliente cliente);

    /**
     * Valida el dni y busca los datos de un cliente en el sistema.
     * 
     * Argumentos:
     * String dni -> El dni del cliente que se quiere consultar
     * 
     * Return:
     * tCliente -> El objeto con los datos del cliente, o null si no existe
     */
    @objid ("d1ffc0c1-632f-4a76-a638-a5da04458559")
    tCliente leerCliente(final String dni);

    /**
     * Valida los datos y actualiza la información de un cliente en el sistema.
     * 
     * Argumentos:
     * tCliente cliente -> El cliente con los datos modificados
     * 
     * Return:
     * int -> El numero de filas afectadas por el update
     */
    @objid ("28d3ed5e-7b8b-4f7f-acb8-503f5fe5ceea")
    int actualizarCliente(final tCliente cliente);

    /**
     * Valida el dni y elimina un cliente del sistema.
     * 
     * Argumentos:
     * String dni -> El dni del cliente a borrar
     * 
     * Return:
     * int -> El numero de filas afectadas por el delete
     */
    @objid ("a139f3de-0de4-48a6-8b87-3f9c5f42d1c8")
    int borrarCliente(final String dni);

}
