package business.auth;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import business.user.SAUserImp;

class SAAuthImpIntegrationTest {
    private SAAuthImp auth;

    @BeforeEach
    void setUp() {
        auth = new SAAuthImp(new SAUserImp());
    }

    @Test
    void authenticate_seededPlayer_returnsPlayerOk() {
        AuthResult result = auth.authenticate("Jug1", "jug1");
        
        assertEquals(AuthResult.Outcome.PLAYER_OK, result.getOutcome());
        assertEquals("Jug1", result.getPayload());
    }

    @Test
    void authenticate_seededStaff_returnsStaffOk() {
        AuthResult result = auth.authenticate("Xiang", "xiang");
        
        assertEquals(AuthResult.Outcome.STAFF_OK, result.getOutcome());
        assertEquals("Xiang", result.getPayload());
    }

    @Test
    void authenticate_wrongPassword_returnsFailure() {
        AuthResult result = auth.authenticate("Jug1", "wrongpass");
        
        assertEquals(AuthResult.Outcome.FAILURE, result.getOutcome());
    }

    @Test
    void authenticate_unknownUser_returnsFailure() {
        AuthResult result = auth.authenticate("T_nobody", "anypass");
        
        assertEquals(AuthResult.Outcome.FAILURE, result.getOutcome());
    }

    @Test
    void authenticate_nullUsername_throwsIllegalArgument() {
        assertThrows(IllegalArgumentException.class, () -> auth.authenticate(null, "pass"));
    }
}
