package business.user;

import java.util.Collection;
import integration.user.DAOUser;
import integration.user.DAOUserImp;

public class SAUserImp implements SAUser {
    private DAOUser daoUser;

    public SAUserImp() {
        daoUser = new DAOUserImp();
    }

    public int createUser(tUser user) {
        if(daoUser.readUser(user.getName()) == null) return daoUser.createUser(user);
        return -1;
    }

    public tUser readUser(String name) {
        return daoUser.readUser(name);
    }

    public int updateUser(tUser user) {
        if(daoUser.readUser(user.getName()) != null) return daoUser.updateUser(user);
        return -1;
    }

    public int deleteUser(String name) {
        if(daoUser.readUser(name) != null) return daoUser.deleteUser(name);
        return -1;
    }

    @Override
    public Collection<tUser> readAllUsers() {
        return daoUser.readAllUsers();
    }
}
