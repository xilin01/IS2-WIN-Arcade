package business;

import java.util.Collection;
import com.modeliosoft.modelio.javadesigner.annotations.objid;
import integration.DAOUser;
import integration.DAOUserImp;

@objid ("3e41cb74-2903-4975-b822-3a84bb853f25")
public	 class SAUserImp implements SAUser {
    @objid ("d83d92ae-22a6-4757-abcf-c7ba3c7147fa")
    private DAOUser daoUser;
    
    @objid ("f3ca4813-ec77-4455-a092-46f5071ca40f")
    public SAUserImp() {
        daoUser = new DAOUserImp();
    }
    
    @objid ("03ba48e0-e6e9-446c-aa3d-df7741d311e4")
    public int createUser(tUser user) {
        if(daoUser.readUser(user.getName()) == null) return daoUser.createUser(user);
        return -1;
    }

    @objid ("41363b00-839c-4740-93ad-d35ef7555d06")
    public tUser readUser(String name) {
        return daoUser.readUser(name);
    }

    @objid ("8d9a7111-3e7a-444c-b943-a7fc48fbdb02")
    public int updateUser(tUser user) {
        if(daoUser.readUser(user.getName()) != null) return daoUser.updateUser(user);
        return -1;
    }
    
    @objid ("af684d06-6239-4850-89d9-b4b20ea53d1d")
    public int deleteUser(String name) {
        if(daoUser.readUser(name) != null) return daoUser.deleteUser(name);
        return -1;
    }

    @Override
	public Collection<tUser> readAllUsers() {
		return daoUser.readAllUsers();
	}
}
