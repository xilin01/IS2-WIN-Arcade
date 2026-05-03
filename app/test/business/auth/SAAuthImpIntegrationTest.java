package business.auth;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import business.user.SAUserImp;

// Step 2 — real chain: SAAuthImp → SAUserImp → DAOUserImp → MySQL
// No stubs. Requires the e4win schema seeded from db/create-script.sql.
class SAAuthImpIntegrationTest {

    private SAAuthImp auth;

    @BeforeEach
    void setUp() {
        auth = new SAAuthImp(new SAUserImp());
    }

    // Seed data: Jug1 / jug1 is a player
    @Test
    void authenticate_seededPlayer_returnsPlayerOk() {
        AuthResult result = auth.authenticate("Jug1", "jug1");
        assertEquals(AuthResult.Outcome.PLAYER_OK, result.getOutcome());
        assertEquals("Jug1", result.getPayload());
    }

    // Seed data: Xiang / xiang is a staff member
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
