package business.store;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import java.util.HashMap;
import java.util.Map;
import business.game.tGame;
import business.packages.tGamesPackage;
import business.player.SAInventory;
import business.player.SAPlayer;
import business.player.tPlayer;
import business.product.tProduct;

class SAStoreImpTest {

    private SAPlayer playerStub;
    private SAInventory inventoryStub;
    private SAStoreImp store;

    @BeforeEach
    void setUp() {
        playerStub = mock(SAPlayer.class);
        inventoryStub = mock(SAInventory.class);
        store = new SAStoreImp(playerStub, inventoryStub);
    }

    private tPlayer playerWithPoints(String name, int points) {
        return new tPlayer(name, "password", true, new tPlayer.tInventory(name), points);
    }

    @Test
    void buyProduct_sufficientPoints_returnsTrue() {
        when(playerStub.readPlayer("ana")).thenReturn(playerWithPoints("ana", 500));
        assertTrue(store.buyProduct("ana", new tProduct("item", 100)));
    }

    @Test
    void buyProduct_insufficientPoints_returnsFalse() {
        when(playerStub.readPlayer("ana")).thenReturn(playerWithPoints("ana", 50));
        assertFalse(store.buyProduct("ana", new tProduct("item", 100)));
    }

    @Test
    void buyProduct_exactPoints_returnsTrue() {
        when(playerStub.readPlayer("ana")).thenReturn(playerWithPoints("ana", 100));
        assertTrue(store.buyProduct("ana", new tProduct("item", 100)));
    }

    @Test
    void buyProduct_deductsPointsCorrectly() {
        when(playerStub.readPlayer("ana")).thenReturn(playerWithPoints("ana", 500));
        store.buyProduct("ana", new tProduct("item", 100));
        ArgumentCaptor<tPlayer> captor = ArgumentCaptor.forClass(tPlayer.class);
        verify(playerStub).updatePlayer(captor.capture());
        assertEquals(400, captor.getValue().getPoints());
    }

    @Test
    void buyProduct_insufficientPoints_doesNotCallUpdatePlayer() {
        when(playerStub.readPlayer("ana")).thenReturn(playerWithPoints("ana", 50));
        store.buyProduct("ana", new tProduct("item", 100));
        verify(playerStub, never()).updatePlayer(any());
    }

    @Test
    void buyPackage_returnsTrue() {
        when(playerStub.readPlayer("ana")).thenReturn(playerWithPoints("ana", 0));
        assertTrue(store.buyPackage("ana", new tGamesPackage("Pack vacío", 0.0, new HashMap<>())));
    }

    @Test
    void buyPackage_callsUpdateInventory() {
        tPlayer player = playerWithPoints("ana", 0);
        when(playerStub.readPlayer("ana")).thenReturn(player);
        Map<tGame, Integer> games = new HashMap<>();
        games.put(new tGame(tGame.BLACKJACK, 50), 2);
        store.buyPackage("ana", new tGamesPackage("Pack básico", 0.0, games));
        verify(inventoryStub, times(1)).updateInventory(player.getInventory());
    }

    @Test
    void buyPackage_addsGamesToPlayerInventory() {
        tPlayer player = playerWithPoints("ana", 0);
        when(playerStub.readPlayer("ana")).thenReturn(player);
        tGame game = new tGame(tGame.BLACKJACK, 50);
        Map<tGame, Integer> games = new HashMap<>();
        games.put(game, 3);
        store.buyPackage("ana", new tGamesPackage("Pack básico", 0.0, games));
        assertEquals(3, player.getInventory().getGames().get(game));
    }
}
