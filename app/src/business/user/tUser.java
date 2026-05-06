package business.user;

import business.auth.AuthResult;
import business.player.tPlayer;
import business.staff.tStaff;

public abstract class tUser {
    protected String name;
    protected String password;
    protected boolean active;

    public tUser(String name, String password, boolean active) {
    	if(name == null || name.trim().isEmpty() || password == null || password.trim().isEmpty()) throw new IllegalArgumentException("Invalid args for Builder in tUser");
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
    	if(name == null || name.trim().isEmpty()) throw new IllegalArgumentException("Invalid arg for setName in tUser");
        this.name = name;
    }

    public void setPassword(String password) {
    	if(password == null || password.trim().isEmpty()) throw new IllegalArgumentException("Invalid arg for setPassword in tUser");
        this.password = password;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public tPlayer asPlayer() {
        return null;
    }

    public tStaff asStaff() {
        return null;
    }
    
    abstract public AuthResult toAuthResult();
}
