package business.player;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

class SAPlayerImpIntegrationTest {
    private static final String URL         = "jdbc:mysql://localhost/e4win";
    private static final String DB_USER     = "root";
    private static final String DB_PASS     = "equipo4";
    private static final String TEST_PLAYER = "T_player";

    private SAPlayerImp sut;

    @BeforeEach
    void setUp() throws Exception {
        try(Connection conn = DriverManager.getConnection(URL, DB_USER, DB_PASS)) {
            try(PreparedStatement u = conn.prepareStatement("INSERT INTO user (name, password, active) VALUES (?, 'pass', TRUE)");
                 PreparedStatement p = conn.prepareStatement("INSERT INTO player (name, points) VALUES (?, 200)")) {
                u.setString(1, TEST_PLAYER);
                u.executeUpdate();
                p.setString(1, TEST_PLAYER);
                p.executeUpdate();
            }
        }
        sut = new SAPlayerImp();
    }

    @AfterEach
    void tearDown() throws Exception {
        try(Connection conn = DriverManager.getConnection(URL, DB_USER, DB_PASS)) {
            for(String sql : new String[]{
                "DELETE FROM productsInventory  WHERE playerName = ?",
                "DELETE FROM gamesInventory     WHERE playerName = ?",
                "DELETE FROM player             WHERE name       = ?",
                "DELETE FROM user               WHERE name       = ?"}) {
	                try(PreparedStatement pst = conn.prepareStatement(sql)) {
	                    pst.setString(1, TEST_PLAYER);
	                    pst.executeUpdate();
	                }
            }
        }
    }

    @Test
    void addPoints_100_playerHas300InDB() {
        sut.addPoints(TEST_PLAYER, 100);
        assertEquals(300, sut.readPlayer(TEST_PLAYER).getPoints());
    }

    @Test
    void addPoints_0_pointsUnchangedInDB() {
        sut.addPoints(TEST_PLAYER, 0);
        assertEquals(200, sut.readPlayer(TEST_PLAYER).getPoints());
    }

    @Test
    void addPoints_unknownPlayer_noRowsAffected() {
        assertDoesNotThrow(() -> sut.addPoints("T_ghost", 100));
        assertNull(sut.readPlayer("T_ghost"));
    }

    @Test
    void readPlayer_existingPlayer_allFieldsCorrect() {
        tPlayer player = sut.readPlayer(TEST_PLAYER);
        
        assertNotNull(player);
        assertEquals(TEST_PLAYER, player.getName());
        assertEquals(200, player.getPoints());
        assertNotNull(player.getInventory());
    }
}
