package business;

import com.modeliosoft.modelio.javadesigner.annotations.objid;

@objid ("6412596d-6469-4893-83ec-0812aedc297c")
public class tUser {
    @objid ("a669f9bd-cd6d-4a2a-9563-a8dcabdec084")
    private String name;
    @objid ("260eec0d-4175-4e20-9b2b-1377f14b222d")
    private String password;
    @objid ("aa5ac128-fc37-43d5-9b0b-e1ad1cf37078")
    private boolean active;

    @objid ("f892fd71-665f-4d31-9c25-cabf1bac8380")
    public tUser(String name, String password, boolean active) {
        this.name = name;
        this.password = password;
        this.active = active;
    }

    @objid ("fd80bb17-715e-42c1-bf63-d72b4892bdbd")
    public String getName() {
        return name;
    }

    @objid ("b79640c1-a703-44e3-95f7-1a98ea1fbd64")
    public String getPassword() {
        return password;
    }

    @objid ("ed0382d6-7c44-4506-80a8-0b45f436dd51")
    public boolean getActive() {
        return active;
    }

    @objid ("6f438a8a-aa91-4c36-8208-14cb4e1c64b7")
    public void setName(String name) {
        this.name = name;
    }

    @objid ("9d99a30a-1a67-4059-878e-6d7ca6e80ef0")
    public void setPassword(String password) {
        this.password = password;
    }

    @objid ("c50ba3c0-81ce-4202-9a78-82ab19c38db0")
    public void setActive(boolean active) {
        this.active = active;
    }

    @objid ("b599eb48-e160-45b3-82ee-c4a135a3e6d0")
    @Override
    public String toString() {
    	return "[NAME: " + name + ", ACTIVE: " + active + "]";
    }
}
