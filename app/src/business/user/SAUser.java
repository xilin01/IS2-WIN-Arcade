package business.user;

import java.util.Collection;

public interface SAUser {
	
    int createUser(tUser user);
    tUser readUser(String name);
    int updateUser(tUser user);
    int deleteUser(String name);
    Collection<tUser> readAllUsers();
}
