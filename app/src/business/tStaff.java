package business;

public class tStaff extends tUser {

	public tStaff(String name, String password, boolean active) {
		super(name, password, active);
	}

	@Override
	public AuthResult toAuthResult() {
		return AuthResult.staffOk(getName());
	}
}
