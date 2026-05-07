package integration.user;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Collection;
import business.auth.AuthResult;
import business.player.tPlayer;
import business.user.tUser;

class DAOUserImpTest {
    private static final String URL  = "jdbc:mysql://localhost/e4win";
    private static final String USER = "root";
    private static final String PASS = "equipo4";

    private Connection conn;
    private DAOUserImp dao;

    @BeforeEach
    void setUp() throws Exception {
        conn = DriverManager.getConnection(URL, USER, PASS);
        conn.setAutoCommit(false);
        dao = new DAOUserImp(conn);
    }

    @AfterEach
    void tearDown() throws Exception {
        conn.rollback();
        conn.setAutoCommit(true);
        conn.close();
    }

    private void insertUser(String name, String password, boolean active) throws Exception {
        PreparedStatement pst = conn.prepareStatement("INSERT INTO user (name, password, active) VALUES (?, ?, ?)");
        
        pst.setString(1, name);
        pst.setString(2, password);
        pst.setBoolean(3, active);
        pst.executeUpdate();
        pst.close();
    }

    private void insertStaff(String name, String password) throws Exception {
        insertUser(name, password, true);
        PreparedStatement pst = conn.prepareStatement("INSERT INTO staff (name) VALUES (?)");
        pst.setString(1, name);
        pst.executeUpdate();
        pst.close();
    }

    @Test
    void createUser_newPlayer_returnsOneRow() throws Exception {
        tPlayer player = new tPlayer("T_player", "pass", true, new tPlayer.tInventory("T_player"), 0);
        
        assertEquals(1, dao.createUser(player));
    }

    @Test
    void createUser_newPlayer_canBeReadBack() throws Exception {
        tPlayer player = new tPlayer("T_player", "pass123", true, new tPlayer.tInventory("T_player"), 0);
        
        dao.createUser(player);
        tUser read = dao.readUser("T_player");
        assertNotNull(read);
        assertEquals("T_player", read.getName());
        assertEquals("pass123", read.getPassword());
        assertTrue(read.getActive());
    }

    @Test
    void readUser_existingPlayer_returnsPlayerOutcome() throws Exception {
        insertUser("T_player", "pass", true);
        tUser user = dao.readUser("T_player");
        assertNotNull(user);
        assertEquals(AuthResult.Outcome.PLAYER_OK, user.toAuthResult().getOutcome());
    }

    @Test
    void readUser_existingStaff_returnsStaffOutcome() throws Exception {
        insertStaff("T_staff", "adminpass");
        tUser user = dao.readUser("T_staff");
        assertNotNull(user);
        assertEquals(AuthResult.Outcome.STAFF_OK, user.toAuthResult().getOutcome());
    }

    @Test
    void readUser_unknownUser_returnsNull() {
        assertNull(dao.readUser("T_ghost"));
    }

    @Test
    void readUser_inactiveUser_returnsUserWithActiveFalse() throws Exception {
        insertUser("T_inactive", "pass", false);
        tUser user = dao.readUser("T_inactive");
        assertNotNull(user);
        assertFalse(user.getActive());
    }

    @Test
    void updateUser_changesPassword_returnsOneRow() throws Exception {
        insertUser("T_player", "oldpass", true);
        tPlayer updated = new tPlayer("T_player", "newpass", true, new tPlayer.tInventory("T_player"), 0);
        assertEquals(1, dao.updateUser(updated));
    }

    @Test
    void updateUser_changesPassword_isPersistedOnRead() throws Exception {
        insertUser("T_player", "oldpass", true);
        tPlayer updated = new tPlayer("T_player", "newpass", true, new tPlayer.tInventory("T_player"), 0);
        dao.updateUser(updated);
        assertEquals("newpass", dao.readUser("T_player").getPassword());
    }

    @Test
    void updateUser_unknownUser_returnsZeroRows() throws Exception {
        tPlayer ghost = new tPlayer("T_ghost", "pass", true, new tPlayer.tInventory("T_ghost"), 0);
        
        assertEquals(0, dao.updateUser(ghost));
    }

    @Test
    void deleteUser_existingUser_returnsOneRow() throws Exception {
        insertUser("T_player", "pass", true);
        assertEquals(1, dao.deleteUser("T_player"));
    }

    @Test
    void deleteUser_setsActiveFalse() throws Exception {
        insertUser("T_player", "pass", true);
        dao.deleteUser("T_player");
        assertFalse(dao.readUser("T_player").getActive());
    }

    @Test
    void deleteUser_doesNotRemoveRow() throws Exception {
        insertUser("T_player", "pass", true);
        dao.deleteUser("T_player");
        assertNotNull(dao.readUser("T_player"));
    }

    @Test
    void deleteUser_unknownUser_returnsZeroRows() {
        assertEquals(0, dao.deleteUser("T_ghost"));
    }

    @Test
    void readAllUsers_includesInsertedUsers() throws Exception {
        int before = dao.readAllUsers().size();
        
        insertUser("T_p1", "pass1", true);
        insertUser("T_p2", "pass2", true);
        Collection<tUser> all = dao.readAllUsers();
        assertEquals(before + 2, all.size());
    }

    @Test
    void readAllUsers_includesInactiveUsers() throws Exception {
        int before = dao.readAllUsers().size();
        
        insertUser("T_inactive", "pass", false);
        assertEquals(before + 1, dao.readAllUsers().size());
    }

    @Test
    void readAllUsers_staffAppearsAsStaff() throws Exception {
        insertStaff("T_staff", "adminpass");
        tUser found = dao.readAllUsers().stream()
            .filter(u -> "T_staff".equals(u.getName()))
            .findFirst()
            .orElse(null);
        assertNotNull(found);
        assertEquals(AuthResult.Outcome.STAFF_OK, found.toAuthResult().getOutcome());
    }
}
