package business;

import java.util.Collection;
import com.modeliosoft.modelio.javadesigner.annotations.objid;

import integration.DAOUser;

@objid ("3e41cb74-2903-4975-b822-3a84bb853f25")
public class SAUserImp implements SAUser {
    @objid ("d83d92ae-22a6-4757-abcf-c7ba3c7147fa")
    private DAOUser daoUsuario;

    @objid ("03ba48e0-e6e9-446c-aa3d-df7741d311e4")
    public int altaUsuario(final User usuario) {
        // TODO Auto-generated return
        return 0;
    }

    @objid ("41363b00-839c-4740-93ad-d35ef7555d06")
    public User leerUsuario(final String dni) {
        // TODO Auto-generated return
        return null;
    }

    @objid ("8d9a7111-3e7a-444c-b943-a7fc48fbdb02")
    public int actualizarUsuario(final User usuario) {
        // TODO Auto-generated return
        return 0;
    }
    
    @Override
	public Collection<User> leerTodosUsuarios() {
		return daoUsuario.readAllUsuarios(); 
	}

    @objid ("af684d06-6239-4850-89d9-b4b20ea53d1d")
    public int borrarUsuario(final String dni) {
        // TODO Auto-generated return
        return 0;
    }

    @objid ("f3ca4813-ec77-4455-a092-46f5071ca40f")
    public SAUserImp() {
        this.daoUsuario = new integration.DAOUserImp();
    }
}
