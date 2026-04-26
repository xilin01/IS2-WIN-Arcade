package business.auth;

public interface SAAuth {
	
    AuthResult authenticate(String name, String password);
}
