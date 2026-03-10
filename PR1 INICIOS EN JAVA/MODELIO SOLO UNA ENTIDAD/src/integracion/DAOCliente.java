package integracion;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import negocio.tCliente;

@objid ("5a46a1fb-6472-4cac-8fc4-f920325561e2")
public interface DAOCliente {
    @objid ("0dda0d1c-df49-4e08-8f8c-92c11adaf514")
    int createCliente(final tCliente cliente);

    @objid ("a22eec57-7fb2-480a-a47d-5434a9e9fc37")
    tCliente readCliente();

    @objid ("bf596e07-16c1-49fc-b60f-c64cb21ae15f")
    int updateCliente(final tCliente cliente);

    @objid ("85fe8495-34fe-4aae-89b5-4967f516b4d2")
    int deleteCliente(final String dni);

}
