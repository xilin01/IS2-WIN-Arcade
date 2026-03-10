package negocio;

import com.modeliosoft.modelio.javadesigner.annotations.objid;

@objid ("6412596d-6469-4893-83ec-0812aedc297c")
public class tCliente {
    @objid ("c287c1b8-180d-44b3-a866-6f6f52624372")
    private int id;

    @objid ("875bd37e-a168-4a27-b799-60641af9d538")
    private String dni;

    @objid ("a669f9bd-cd6d-4a2a-9563-a8dcabdec084")
    private String nombre;

    @objid ("6ad33593-503a-4808-84ed-0268c18795fe")
    private boolean activo;

    @objid ("f892fd71-665f-4d31-9c25-cabf1bac8380")
    public tCliente(final int id, final String dni, final String nombre, final boolean activo) {
    	this.id = id;
    	this.dni = dni;
    	this.nombre = nombre;
    	this.activo = activo;
    }

    @objid ("a3d871a2-741b-4e4b-9650-fdfb570ee58e")
    public int getId() {
        return id;
    }

    @objid ("fd80bb17-715e-42c1-bf63-d72b4892bdbd")
    public String getDni() {
        return dni;
    }

    @objid ("b79640c1-a703-44e3-95f7-1a98ea1fbd64")
    public String getNombre() {
        return nombre;
    }

    @objid ("ed0382d6-7c44-4506-80a8-0b45f436dd51")
    public boolean getActivo() {
        return activo;
    }

    @objid ("a62ae138-7a4f-4336-a2d3-8158c8099da7")
    public void setId(final int id) {
    	this.id = id;
    }

    @objid ("6f438a8a-aa91-4c36-8208-14cb4e1c64b7")
    public void setDni(final String dni) {
    	this.dni = dni;
    }

    @objid ("9d99a30a-1a67-4059-878e-6d7ca6e80ef0")
    public void setNombre(final String nombre) {
    	this.nombre = nombre;
    }

    @objid ("c50ba3c0-81ce-4202-9a78-82ab19c38db0")
    public void setActivo(final boolean activo) {
    	this.activo = activo;
    }

    @objid ("b599eb48-e160-45b3-82ee-c4a135a3e6d0")
    public String toString() {
        return "Cliente: " + id + ". Nombre: " + nombre + " DNI: " + dni + " Activo: " + activo;
    }

}
