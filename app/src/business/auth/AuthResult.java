package business.auth;

public class AuthResult {
    public enum Outcome {STAFF_OK, PLAYER_OK, FAILURE}

    private final Outcome outcome;
    private final Object payload;

    private AuthResult(Outcome outcome, Object payload) {
    	if(outcome == null || payload == null) throw new IllegalArgumentException("Invalid args for Builder in AuthResult");
        this.outcome = outcome;
        this.payload = payload;
    }

    public static AuthResult staffOk(String staffName) {
    	if(staffName == null) throw new IllegalArgumentException("Invalid arg for staffOk in AuthResult");
        return new AuthResult(Outcome.STAFF_OK, staffName);
    }

    public static AuthResult playerOk(String playerName) {
    	if(playerName == null) throw new IllegalArgumentException("Invalid arg for playerOk in AuthResult");
        return new AuthResult(Outcome.PLAYER_OK, playerName);
    }

    public static AuthResult failure() {
        return new AuthResult(Outcome.FAILURE, null);
    }

    public Outcome getOutcome() {
    	return outcome;
    }
    
    public Object getPayload() {
    	return payload;
    }
}
