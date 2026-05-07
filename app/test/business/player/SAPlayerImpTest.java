package business.player;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import integration.player.DAOPlayer;

class SAPlayerImpTest {
    private DAOPlayer daoStub;
    private SAPlayerImp sut;

    @BeforeEach
    void setUp() {
        daoStub = mock(DAOPlayer.class);
        sut = new SAPlayerImp(daoStub);
    }

    private tPlayer playerWithPoints(String name, int points) {
        return new tPlayer(name, "pass", true, new tPlayer.tInventory(name), points);
    }

    @Test
    void addPoints_positiveAmount_updatesPlayer() {
        when(daoStub.readPlayer("ana")).thenReturn(playerWithPoints("ana", 100));
        sut.addPoints("ana", 50);
        ArgumentCaptor<tPlayer> captor = ArgumentCaptor.forClass(tPlayer.class);
        verify(daoStub).updatePlayer(captor.capture());
        assertEquals(150, captor.getValue().getPoints());
    }

    @Test
    void addPoints_zero_updatesPlayerWithSamePoints() {
        when(daoStub.readPlayer("ana")).thenReturn(playerWithPoints("ana", 100));
        sut.addPoints("ana", 0);
        ArgumentCaptor<tPlayer> captor = ArgumentCaptor.forClass(tPlayer.class);
        verify(daoStub).updatePlayer(captor.capture());
        assertEquals(100, captor.getValue().getPoints());
    }

    @Test
    void addPoints_unknownPlayer_doesNotCallUpdate() {
        when(daoStub.readPlayer("ghost")).thenReturn(null);
        sut.addPoints("ghost", 50);
        verify(daoStub, never()).updatePlayer(any());
    }

    @Test
    void readPlayer_existingPlayer_returnsPlayer() {
        tPlayer expected = playerWithPoints("ana", 200);
        
        when(daoStub.readPlayer("ana")).thenReturn(expected);
        assertEquals(expected, sut.readPlayer("ana"));
    }

    @Test
    void readPlayer_unknownPlayer_returnsNull() {
        when(daoStub.readPlayer("ghost")).thenReturn(null);
        assertNull(sut.readPlayer("ghost"));
    }

    @Test
    void updatePlayer_delegatesToDAO() {
        tPlayer player = playerWithPoints("ana", 100);
        
        sut.updatePlayer(player);
        verify(daoStub, times(1)).updatePlayer(player);
    }
}
