package business.player;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import java.util.HashMap;
import java.util.Map;
import business.game.tGame;
import business.player.tPlayer.tInventory;
import integration.player.DAOInventory;

class SAInventoryImpTest {
    private DAOInventory daoStub;
    private SAInventoryImp sut;

    @BeforeEach
    void setUp() {
        daoStub = mock(DAOInventory.class);
        sut = new SAInventoryImp(daoStub);
    }

    private tInventory inventoryWithGame(tGame game, int amount) {
        Map<tGame, Integer> games = new HashMap<>();
        
        games.put(game, amount);
        return new tInventory("player1", games, new HashMap<>());
    }

    @Test
    void consumeGame_gameWithAmountGreaterThanOne_decrementsAmount() {
        tGame game = new tGame(tGame.BLACKJACK, 10);
        
        when(daoStub.readInventory("player1")).thenReturn(inventoryWithGame(game, 3));
        sut.consumeGame("player1", game);
        ArgumentCaptor<tInventory> captor = ArgumentCaptor.forClass(tInventory.class);
        verify(daoStub).updateInventory(captor.capture());
        assertEquals(2, captor.getValue().getGames().get(game));
    }

    @Test
    void consumeGame_gameWithAmountOne_removesEntry() {
        tGame game = new tGame(tGame.BLACKJACK, 10);
        
        when(daoStub.readInventory("player1")).thenReturn(inventoryWithGame(game, 1));
        sut.consumeGame("player1", game);
        ArgumentCaptor<tInventory> captor = ArgumentCaptor.forClass(tInventory.class);
        verify(daoStub).updateInventory(captor.capture());
        assertFalse(captor.getValue().getGames().containsKey(game));
    }

    @Test
    void consumeGame_gameNotInInventory_doesNotCallUpdateInventory() {
        when(daoStub.readInventory("player1")).thenReturn(new tInventory("player1"));
        sut.consumeGame("player1", new tGame(tGame.BLACKJACK, 10));
        verify(daoStub, never()).updateInventory(any());
    }

    @Test
    void consumeGame_gameNotInInventory_doesNotThrow() {
        when(daoStub.readInventory("player1")).thenReturn(new tInventory("player1"));
        assertDoesNotThrow(() -> sut.consumeGame("player1", new tGame(tGame.BLACKJACK, 10)));
    }

    @Test
    void updateInventory_callsDAO() {
        tInventory inv = new tInventory("player1");
        
        sut.updateInventory(inv);
        verify(daoStub, times(1)).updateInventory(inv);
    }
}
