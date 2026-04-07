package integracion;

import java.util.Collection;
import com.modeliosoft.modelio.javadesigner.annotations.objid;
import negocio.tUsuario;

@objid ("5a46a1fb-6472-4cac-8fc4-f920325561e2")
public interface DAOUsuario {
	
    /**
     * Inserta un usuario a la base de datos.
     * 
     * Argumentos:
     * Usuario -> usuario a insertar en la base de datos
     * 
     * Return:
     * int -> numero de filas afectadas por la inserción
     */
    @objid ("0dda0d1c-df49-4e08-8f8c-92c11adaf514")
    int createUsuario(final tUsuario usuario);

    /**
     * Lee los datos de un usuario en la base de datos.
     * 
     * Argumentos:
     * string dni -> El dni del usuario que se quiere leer
     * 
     * Return:
     * tUsuario -> un tUsuario con los valores de la bbdd asociados al dni
     */
    @objid ("a22eec57-7fb2-480a-a47d-5434a9e9fc37")
    tUsuario readUsuario(final String dni);

    /**
     * Actualiza los datos de un usuario existente en la base de datos.
     * 
     * Argumentos:
     * tUsuario usuario -> El usuario con los nuevos datos a actualizar
     * 
     * Return:
     * int -> El número de filas afectadas por la actualización
     */
    @objid ("bf596e07-16c1-49fc-b60f-c64cb21ae15f")
    int updateUsuario(final tUsuario usuario);

    /**
     * Elimina un usuario de la base de datos.
     * 
     * Argumentos:
     * String dni -> El dni del usuario que se quiere eliminar
     * 
     * Return:
     * int -> El número de filas afectadas por el borrado
     */
    @objid ("85fe8495-34fe-4aae-89b5-4967f516b4d2")
    int deleteUsuario(final String dni);

	Collection<tUsuario> readAllUsuarios();
}
