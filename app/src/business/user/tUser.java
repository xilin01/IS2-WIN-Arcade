package business.user;

import business.auth.AuthResult;

public abstract class tUser {
    protected String name;
    protected String password;
    protected boolean active;

    public tUser(String name, String password, boolean active) {
        this.name = name;
        this.password = password;
        this.active = active;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public boolean getActive() {
        return active;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "[NAME: " + name + ", ACTIVE: " + active + "]";
    }
    
    abstract public AuthResult toAuthResult();
}
