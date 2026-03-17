package negocio;

import com.modeliosoft.modelio.javadesigner.annotations.objid;

@objid ("6412596d-6469-4893-83ec-0812aedc297c")
public class tCliente {
    @objid ("875bd37e-a168-4a27-b799-60641af9d538")
    private String dni;

    @objid ("a669f9bd-cd6d-4a2a-9563-a8dcabdec084")
    private String nombre;

    @objid ("260eec0d-4175-4e20-9b2b-1377f14b222d")
    private String contrasenya;

    @objid ("aa5ac128-fc37-43d5-9b0b-e1ad1cf37078")
    private boolean activo;

    /**
     * Crea una nueva instancia de cliente con todos sus atributos.
     * 
     * Argumentos:
     * String dni -> El dni del cliente
     * String nombre -> El nombre de usuario del cliente
     * String contrasenya -> La contraseña del cliente
     * boolean activo -> El estado del cliente (true si está activo, false si no)
     */
    @objid ("f892fd71-665f-4d31-9c25-cabf1bac8380")
    public tCliente(final String dni, final String nombre, final String contrasenya, final boolean activo) {
        this.dni = dni;
        this.nombre = nombre;
        this.contrasenya = contrasenya;
        this.activo = activo;
    }

    @objid ("a3d871a2-741b-4e4b-9650-fdfb570ee58e")
    public String getDni() {
        return this.dni;
    }

    @objid ("fd80bb17-715e-42c1-bf63-d72b4892bdbd")
    public String getNombre() {
        return this.nombre;
    }

    @objid ("b79640c1-a703-44e3-95f7-1a98ea1fbd64")
    public String getContrasenya() {
        return this.contrasenya;
    }

    @objid ("ed0382d6-7c44-4506-80a8-0b45f436dd51")
    public boolean getActivo() {
        return this.activo;
    }

    @objid ("a62ae138-7a4f-4336-a2d3-8158c8099da7")
    public void setDni(final String dni) {
        this.dni = dni;
    }

    @objid ("6f438a8a-aa91-4c36-8208-14cb4e1c64b7")
    public void setNombre(final String nombre) {
        this.nombre = nombre;
    }

    @objid ("9d99a30a-1a67-4059-878e-6d7ca6e80ef0")
    public void setContrasenya(final String contrasenya) {
        this.contrasenya = contrasenya;
    }

    @objid ("c50ba3c0-81ce-4202-9a78-82ab19c38db0")
    public void setActivo(final boolean activo) {
        this.activo = activo;
    }

    @objid ("b599eb48-e160-45b3-82ee-c4a135a3e6d0")
    public String toString() {
        // TODO Auto-generated return
        return null;
    }

}
