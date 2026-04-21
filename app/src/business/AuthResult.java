package business;

public class AuthResult {

    public enum Outcome { STAFF_OK, PLAYER_OK, FAILURE }

    private final Outcome outcome;
    private final Object payload;

    private AuthResult(Outcome outcome, Object payload) {
        this.outcome = outcome;
        this.payload = payload;
    }

    public static AuthResult staffOk(String staffName) {
        return new AuthResult(Outcome.STAFF_OK, staffName);
    }

    public static AuthResult playerOk(String playerName) {
        return new AuthResult(Outcome.PLAYER_OK, playerName);
    }

    public static AuthResult failure() {
        return new AuthResult(Outcome.FAILURE, null);
    }

    public Outcome getOutcome() { return outcome; }
    public Object getPayload()  { return payload; }
}
