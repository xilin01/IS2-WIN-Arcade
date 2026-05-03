package business.auth;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import business.player.tPlayer;
import business.staff.tStaff;
import business.user.SAUser;

class SAAuthImpTest {

    private SAUser saUserStub;
    private SAAuthImp auth;

    @BeforeEach
    void setUp() {
        saUserStub = mock(SAUser.class);
        auth = new SAAuthImp(saUserStub);
    }

    private tPlayer activePlayer(String name, String password) {
        return new tPlayer(name, password, true, new tPlayer.tInventory(name), 0);
    }

    @Test
    void authenticate_validPlayer_returnsPlayerOk() {
        when(saUserStub.readUser("ana")).thenReturn(activePlayer("ana", "pass123"));
        AuthResult result = auth.authenticate("ana", "pass123");
        assertEquals(AuthResult.Outcome.PLAYER_OK, result.getOutcome());
    }

    @Test
    void authenticate_validPlayer_payloadIsUsername() {
        when(saUserStub.readUser("ana")).thenReturn(activePlayer("ana", "pass123"));
        AuthResult result = auth.authenticate("ana", "pass123");
        assertEquals("ana", result.getPayload());
    }

    @Test
    void authenticate_validStaff_returnsStaffOk() {
        when(saUserStub.readUser("bob")).thenReturn(new tStaff("bob", "adminpass", true));
        AuthResult result = auth.authenticate("bob", "adminpass");
        assertEquals(AuthResult.Outcome.STAFF_OK, result.getOutcome());
    }

    @Test
    void authenticate_wrongPassword_returnsFailure() {
        when(saUserStub.readUser("ana")).thenReturn(activePlayer("ana", "pass123"));
        AuthResult result = auth.authenticate("ana", "wrongpass");
        assertEquals(AuthResult.Outcome.FAILURE, result.getOutcome());
    }

    @Test
    void authenticate_unknownUser_returnsFailure() {
        when(saUserStub.readUser("ghost")).thenReturn(null);
        AuthResult result = auth.authenticate("ghost", "anypass");
        assertEquals(AuthResult.Outcome.FAILURE, result.getOutcome());
    }

    @Test
    void authenticate_inactiveUser_returnsFailure() {
        tPlayer inactive = new tPlayer("ana", "pass123", false, new tPlayer.tInventory("ana"), 0);
        when(saUserStub.readUser("ana")).thenReturn(inactive);
        AuthResult result = auth.authenticate("ana", "pass123");
        assertEquals(AuthResult.Outcome.FAILURE, result.getOutcome());
    }

    @Test
    void authenticate_nullUsername_throwsIllegalArgument() {
        assertThrows(IllegalArgumentException.class, () -> auth.authenticate(null, "pass"));
    }

    @Test
    void authenticate_blankPassword_throwsIllegalArgument() {
        assertThrows(IllegalArgumentException.class, () -> auth.authenticate("ana", ""));
    }
}
