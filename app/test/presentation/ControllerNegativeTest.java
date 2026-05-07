package presentation;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import business.auth.SAAuth;
import business.auth.SAAuthImp;
import business.game.SAGame;
import business.game.SAGameImp;
import business.packages.SAGamesPackage;
import business.packages.SAGamesPackageImp;
import business.player.SAInventory;
import business.player.SAInventoryImp;
import business.player.SAPlayer;
import business.player.SAPlayerImp;
import business.product.SAProduct;
import business.product.SAProductImp;
import business.ServiceLocator;
import business.store.SAStore;
import business.store.SAStoreImp;
import business.user.SAUser;
import business.user.SAUserImp;

class ControllerNegativeTest {
    private static Controller ctrl;

    @BeforeAll
    static void bootstrapServices() {
        ServiceLocator.register(SAAuth.class, new SAAuthImp());
        ServiceLocator.register(SAUser.class, new SAUserImp());
        SAPlayer saPlayer = new SAPlayerImp();
        SAInventory saInv  = new SAInventoryImp();
        ServiceLocator.register(SAPlayer.class, saPlayer);
        ServiceLocator.register(SAInventory.class, saInv);
        ServiceLocator.register(SAGame.class, new SAGameImp());
        ServiceLocator.register(SAGamesPackage.class, new SAGamesPackageImp());
        ServiceLocator.register(SAProduct.class, new SAProductImp());
        ServiceLocator.register(SAStore.class, new SAStoreImp(saPlayer, saInv));
        ctrl = Controller.getInstance();
    }

    @Test
    void action_unknownEvent_doesNotThrow() {
        assertDoesNotThrow(() -> ctrl.action(Integer.MAX_VALUE, null));
    }

    @Test
    void action_unknownEvent_printsToStderr() {
        ByteArrayOutputStream errContent = new ByteArrayOutputStream();
        PrintStream original = System.err;
        
        System.setErr(new PrintStream(errContent));
        try {
            ctrl.action(Integer.MAX_VALUE, null);
        }
        finally {
            System.setErr(original);
        }
        assertTrue(errContent.toString().length() > 0);
    }

    @Test
    void action_blackjackHit_beforePlay_doesNotThrow() {
        assertDoesNotThrow(() -> ctrl.action(Event.BLACKJACK_HIT, null));
    }

    @Test
    void action_blackjackStand_beforePlay_doesNotThrow() {
        assertDoesNotThrow(() -> ctrl.action(Event.BLACKJACK_STAND, null));
    }

    @Test
    void action_wrongDataType_throwsClassCast() {
        assertThrows(ClassCastException.class, () -> ctrl.action(Event.CREATE_GAME, "notAGame"));
    }

    @Test
    void getPlayer_nullUsername_doesNotThrow() {
        assertDoesNotThrow(() -> ctrl.getPlayer(null));
    }
}
