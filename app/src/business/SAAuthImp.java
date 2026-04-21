package business;

public class SAAuthImp implements SAAuth {

    private final SAUser saUser;

    public SAAuthImp() {
        this.saUser = new SAUserImp();
    }

    @Override
    public AuthResult authenticate(String name, String password) {
        tUser user = saUser.readUser(name);
        if (user == null || !user.getActive() || !user.getPassword().equals(password))
            return AuthResult.failure();
        return user.toAuthResult();
    }
}
