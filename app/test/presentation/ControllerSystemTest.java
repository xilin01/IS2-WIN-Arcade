package presentation;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import business.auth.SAAuth;
import business.auth.SAAuthImp;
import business.game.SAGame;
import business.game.SAGameImp;
import business.game.tGame;
import business.packages.SAGamesPackage;
import business.packages.SAGamesPackageImp;
import business.packages.tGamesPackage;
import business.player.SAInventory;
import business.player.SAInventoryImp;
import business.player.SAPlayer;
import business.player.SAPlayerImp;
import business.product.SAProduct;
import business.product.SAProductImp;
import business.product.tProduct;
import business.ServiceLocator;
import business.store.SAStore;
import business.store.SAStoreImp;
import business.user.SAUser;
import business.user.SAUserImp;

class ControllerSystemTest {
    private static Controller ctrl;

    @BeforeAll
    static void bootstrapServices() {
        ServiceLocator.register(SAAuth.class, new SAAuthImp());
        ServiceLocator.register(SAUser.class, new SAUserImp());
        SAPlayer saPlayer     = new SAPlayerImp();
        SAInventory saInv     = new SAInventoryImp();
        ServiceLocator.register(SAPlayer.class, saPlayer);
        ServiceLocator.register(SAInventory.class, saInv);
        ServiceLocator.register(SAGame.class, new SAGameImp());
        ServiceLocator.register(SAGamesPackage.class, new SAGamesPackageImp());
        ServiceLocator.register(SAProduct.class, new SAProductImp());
        ServiceLocator.register(SAStore.class, new SAStoreImp(saPlayer, saInv));
        ctrl = Controller.getInstance();
    }

    @Test
    void serviceLocator_allServicesResolvable() {
        assertDoesNotThrow(() -> ServiceLocator.get(SAAuth.class));
        assertDoesNotThrow(() -> ServiceLocator.get(SAUser.class));
        assertDoesNotThrow(() -> ServiceLocator.get(SAPlayer.class));
        assertDoesNotThrow(() -> ServiceLocator.get(SAInventory.class));
        assertDoesNotThrow(() -> ServiceLocator.get(SAGame.class));
        assertDoesNotThrow(() -> ServiceLocator.get(SAGamesPackage.class));
        assertDoesNotThrow(() -> ServiceLocator.get(SAProduct.class));
        assertDoesNotThrow(() -> ServiceLocator.get(SAStore.class));
    }

    @Test
    void getPlayer_seededPlayer_returnsNotNull() {
        assertNotNull(ctrl.getPlayer("Jug1"));
    }

    @Test
    void getPlayer_seededPlayer_returnsCorrectName() {
        assertEquals("Jug1", ctrl.getPlayer("Jug1").getName());
    }

    @Test
    void getPlayerPoints_seededPlayer_returnsNonNegative() {
        assertTrue(ctrl.getPlayerPoints("Jug1") >= 0);
    }

    @Test
    void getAllPlayers_containsSeededPlayers() {
        boolean hasJug1 = ctrl.getAllPlayers().stream().anyMatch(p -> "Jug1".equals(p.getName()));
        
        assertTrue(hasJug1);
    }

    @Test
    void getAllPackages_returnsSeededPackages() {
        assertFalse(ctrl.getAllPackages().isEmpty());
    }

    @Test
    void getAllProducts_returnsSeededProducts() {
        assertFalse(ctrl.getAllProducts().isEmpty());
    }

    @Test
    void getAllGames_returnsSeededGames() {
        assertFalse(ctrl.getAllGames().isEmpty());
    }

    @Test
    void action_createProduct_appearsInList() {
        int before = ctrl.getAllProducts().size();
        
        ctrl.action(Event.CREATE_PRODUCT, new tProduct("T_sysprod", 10));
        try {
            assertEquals(before + 1, ctrl.getAllProducts().size());
        }
        finally {
            ctrl.action(Event.DELETE_PRODUCT, "T_sysprod");
        }
    }

    @Test
    void action_deleteProduct_removedFromList() {
        ctrl.action(Event.CREATE_PRODUCT, new tProduct("T_sysprod", 10));
        int before = ctrl.getAllProducts().size();
        ctrl.action(Event.DELETE_PRODUCT, "T_sysprod");
        assertEquals(before - 1, ctrl.getAllProducts().size());
    }

    @Test
    void action_updateProduct_priceChangedInList() {
        ctrl.action(Event.CREATE_PRODUCT, new tProduct("T_sysprod", 10));
        try {
            ctrl.action(Event.UPDATE_PRODUCT, new tProduct("T_sysprod", 99));
            boolean updated = ctrl.getAllProducts().stream()
                .anyMatch(p -> "T_sysprod".equals(p.getName()) && p.getPrice() == 99);
            assertTrue(updated);
        }
        finally {
            ctrl.action(Event.DELETE_PRODUCT, "T_sysprod");
        }
    }

    @Test
    void action_createPackage_appearsInList() {
        int before = ctrl.getAllPackages().size();
        
        ctrl.action(Event.CREATE_PACKAGE, new tGamesPackage("T_syspkg", 5.00, new HashMap<>()));
        try {
            assertEquals(before + 1, ctrl.getAllPackages().size());
        }
        finally {
            ctrl.action(Event.DELETE_PACKAGE, "T_syspkg");
        }
    }

    @Test
    void action_deletePackage_removedFromList() {
        ctrl.action(Event.CREATE_PACKAGE, new tGamesPackage("T_syspkg", 5.00, new HashMap<>()));
        int before = ctrl.getAllPackages().size();
        ctrl.action(Event.DELETE_PACKAGE, "T_syspkg");
        assertEquals(before - 1, ctrl.getAllPackages().size());
    }

    @Test
    void action_createGame_appearsInList() {
        int before = ctrl.getAllGames().size();
        
        ctrl.action(Event.CREATE_GAME, new tGame("T_sysgame", 99));
        try {
            assertEquals(before + 1, ctrl.getAllGames().size());
        }
        finally {
            ServiceLocator.get(SAGame.class).deleteGame("T_sysgame", 99);
        }
    }
}
