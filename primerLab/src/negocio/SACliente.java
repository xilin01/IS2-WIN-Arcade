package negocio;

import com.modeliosoft.modelio.javadesigner.annotations.objid;

@objid ("adece493-ec9f-4c1f-9e56-03888713105a")
public interface SACliente {
    @objid ("81478c33-59ab-4792-be0c-0deaf4289b97")
    int altaCliente(final tCliente cliente);

    @objid ("d1ffc0c1-632f-4a76-a638-a5da04458559")
    tCliente leerCliente(final String dni);

    @objid ("28d3ed5e-7b8b-4f7f-acb8-503f5fe5ceea")
    int actualizarCliente(final tCliente cliente);

    @objid ("a139f3de-0de4-48a6-8b87-3f9c5f42d1c8")
    int borrarCliente(final String dni);

}
