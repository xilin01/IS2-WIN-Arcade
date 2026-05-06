package business.store;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.Map;
import business.game.tGame;
import business.packages.tGamesPackage;
import business.player.SAInventoryImp;
import business.player.SAPlayerImp;
import business.product.tProduct;

class SAStoreImpIntegrationTest {

    private static final String URL         = "jdbc:mysql://localhost/e4win";
    private static final String DB_USER     = "root";
    private static final String DB_PASS     = "equipo4";
    private static final String TEST_PLAYER = "T_store";

    private SAStoreImp    store;
    private SAPlayerImp   saPlayer;

    @BeforeEach
    void setUp() throws Exception {
        try (Connection conn = DriverManager.getConnection(URL, DB_USER, DB_PASS)) {
            try (PreparedStatement u = conn.prepareStatement(
                     "INSERT INTO user (name, password, active) VALUES (?, 'pass', TRUE)");
                 PreparedStatement p = conn.prepareStatement(
                     "INSERT INTO player (name, points) VALUES (?, 500)")) {
                u.setString(1, TEST_PLAYER);
                u.executeUpdate();
                p.setString(1, TEST_PLAYER);
                p.executeUpdate();
            }
        }
        saPlayer = new SAPlayerImp();
        store    = new SAStoreImp(saPlayer, new SAInventoryImp());
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

    @Test
    void buyProduct_sufficientPoints_returnsTrue() {
        assertTrue(store.buyProduct(TEST_PLAYER, new tProduct("Taza", 50)));
    }

    @Test
    void buyProduct_sufficientPoints_deductsPointsInDB() {
        store.buyProduct(TEST_PLAYER, new tProduct("Taza", 50));
        assertEquals(450, saPlayer.readPlayer(TEST_PLAYER).getPoints());
    }

    @Test
    void buyProduct_sufficientPoints_addsProductToInventoryInDB() {
        tProduct taza = new tProduct("Taza", 50);
        store.buyProduct(TEST_PLAYER, taza);
        assertEquals(1, saPlayer.readPlayer(TEST_PLAYER).getInventory().getProducts().get(taza));
    }

    @Test
    void buyProduct_insufficientPoints_returnsFalse() {
        assertFalse(store.buyProduct(TEST_PLAYER, new tProduct("Camiseta", 600)));
    }

    @Test
    void buyProduct_insufficientPoints_doesNotChangePointsInDB() {
        store.buyProduct(TEST_PLAYER, new tProduct("Camiseta", 600));
        assertEquals(500, saPlayer.readPlayer(TEST_PLAYER).getPoints());
    }


    @Test
    void buyPackage_returnsTrue() {
        tGamesPackage pack = new tGamesPackage("Pack test", 0.0, new HashMap<>());
        assertTrue(store.buyPackage(TEST_PLAYER, pack));
    }

    @Test
    void buyPackage_addsGamesToInventoryInDB() {
        tGame bj = new tGame(tGame.BLACKJACK, 10);
        Map<tGame, Integer> games = new HashMap<>();
        games.put(bj, 3);
        store.buyPackage(TEST_PLAYER, new tGamesPackage("Pack test", 0.0, games));
        assertEquals(3, saPlayer.readPlayer(TEST_PLAYER).getInventory().getGames().get(bj));
    }

    @Test
    void buyPackage_doesNotDeductPoints() {
        tGame bj = new tGame(tGame.BLACKJACK, 10);
        Map<tGame, Integer> games = new HashMap<>();
        games.put(bj, 2);
        store.buyPackage(TEST_PLAYER, new tGamesPackage("Pack test", 0.0, games));
        assertEquals(500, saPlayer.readPlayer(TEST_PLAYER).getPoints());
    }
}
