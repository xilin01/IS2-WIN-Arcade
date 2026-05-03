package business.auth;

import business.user.SAUser;
import business.user.SAUserImp;
import business.user.tUser;

public class SAAuthImp implements SAAuth {
    private final SAUser saUser;

    public SAAuthImp() {
        this.saUser = new SAUserImp();
    }

    SAAuthImp(SAUser saUser) {
        this.saUser = saUser;
    }

    @Override
    public AuthResult authenticate(String name, String password) {
    	if(name == null || name.trim().isEmpty() || password == null || password.trim().isEmpty()) throw new IllegalArgumentException("Invalid args for authenticate in SAAuthImp");
        tUser user = saUser.readUser(name);
        if(user == null || !user.getActive() || !user.getPassword().equals(password)) return AuthResult.failure();
        return user.toAuthResult();
    }
}
