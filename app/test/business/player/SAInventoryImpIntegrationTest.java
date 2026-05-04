package business.player;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import business.game.tGame;

// Step 3 — real chain: SAInventoryImp → DAOInventoryImp → MySQL.
// @BeforeEach inserts test player; @AfterEach cleans up in FK order.
// Uses seed-data game Blackjack/10 to satisfy gamesInventory FK constraints.
class SAInventoryImpIntegrationTest {

    private static final String URL         = "jdbc:mysql://localhost/e4win";
    private static final String DB_USER     = "root";
    private static final String DB_PASS     = "equipo4";
    private static final String TEST_PLAYER = "T_inv";
    private static final tGame  BJ          = new tGame(tGame.BLACKJACK, 10);

    private SAInventoryImp sut;

    @BeforeEach
    void setUp() throws Exception {
        try (Connection conn = DriverManager.getConnection(URL, DB_USER, DB_PASS)) {
            try (PreparedStatement u = conn.prepareStatement(
                     "INSERT INTO user (name, password, active) VALUES (?, 'pass', TRUE)");
                 PreparedStatement p = conn.prepareStatement(
                     "INSERT INTO player (name, points) VALUES (?, 0)")) {
                u.setString(1, TEST_PLAYER);
                u.executeUpdate();
                p.setString(1, TEST_PLAYER);
                p.executeUpdate();
            }
        }
        sut = new SAInventoryImp();
    }

    @AfterEach
    void tearDown() throws Exception {
        try (Connection conn = DriverManager.getConnection(URL, DB_USER, DB_PASS)) {
            for (String sql : new String[]{
                "DELETE FROM productsInventory WHERE playerName = ?",
                "DELETE FROM gamesInventory     WHERE playerName = ?",
                "DELETE FROM player             WHERE name       = ?",
                "DELETE FROM user               WHERE name       = ?"
            }) {
                try (PreparedStatement pst = conn.prepareStatement(sql)) {
                    pst.setString(1, TEST_PLAYER);
                    pst.executeUpdate();
                }
            }
        }
    }

    private void insertGameRow(int amount) throws Exception {
        try (Connection conn = DriverManager.getConnection(URL, DB_USER, DB_PASS);
             PreparedStatement pst = conn.prepareStatement(
                 "INSERT INTO gamesInventory (playerName, gameName, gameBasePoints, amount) VALUES (?, ?, ?, ?)")) {
            pst.setString(1, TEST_PLAYER);
            pst.setString(2, BJ.getName());
            pst.setInt(3, BJ.getBasePoints());
            pst.setInt(4, amount);
            pst.executeUpdate();
        }
    }

    private int readAmount() throws Exception {
        try (Connection conn = DriverManager.getConnection(URL, DB_USER, DB_PASS);
             PreparedStatement pst = conn.prepareStatement(
                 "SELECT amount FROM gamesInventory WHERE playerName = ? AND gameName = ? AND gameBasePoints = ?")) {
            pst.setString(1, TEST_PLAYER);
            pst.setString(2, BJ.getName());
            pst.setInt(3, BJ.getBasePoints());
            ResultSet rs = pst.executeQuery();
            return rs.next() ? rs.getInt("amount") : -1;
        }
    }

    @Test
    void consumeGame_amountThree_decrementsToTwo() throws Exception {
        insertGameRow(3);
        sut.consumeGame(TEST_PLAYER, BJ);
        assertEquals(2, readAmount());
    }

    @Test
    void consumeGame_amountOne_removesRowFromDB() throws Exception {
        insertGameRow(1);
        sut.consumeGame(TEST_PLAYER, BJ);
        assertEquals(-1, readAmount());
    }

    @Test
    void consumeGame_gameNotInInventory_inventoryUnchanged() throws Exception {
        sut.consumeGame(TEST_PLAYER, BJ);
        assertEquals(-1, readAmount());
    }

    @Test
    void consumeGame_amountOne_playerStillExists() throws Exception {
        insertGameRow(1);
        sut.consumeGame(TEST_PLAYER, BJ);
        try (Connection conn = DriverManager.getConnection(URL, DB_USER, DB_PASS);
             PreparedStatement pst = conn.prepareStatement(
                 "SELECT name FROM player WHERE name = ?")) {
            pst.setString(1, TEST_PLAYER);
            ResultSet rs = pst.executeQuery();
            assertTrue(rs.next());
        }
    }
}
