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
        return daoUser.createUser(user);
    }

    public tUser readUser(String name) {
        return daoUser.readUser(name);
    }

    public int updateUser(tUser user) {
        return daoUser.updateUser(user);
    }

    public int deleteUser(String name) {
        return daoUser.deleteUser(name);
    }

    @Override
    public Collection<tUser> readAllUsers() {
        return daoUser.readAllUsers();
    }
}
