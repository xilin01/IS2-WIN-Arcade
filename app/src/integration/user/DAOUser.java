package integration.user;

import java.util.Collection;
import business.user.tUser;

public interface DAOUser {

    int createUser(tUser user);
    tUser readUser(String name);
    int updateUser(tUser user);
    int deleteUser(String name);
    Collection<tUser> readAllUsers();
}
