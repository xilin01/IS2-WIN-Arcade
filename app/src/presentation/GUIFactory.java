package presentation;

import java.util.Map;
import java.util.function.Supplier;
import javax.swing.JFrame;
import business.game.Blackjack;
import presentation.games.BlackjackGUI;
import presentation.games.BlackjackGUIImp;
import presentation.games.GamesPanelView;
import presentation.games.RouletteGUI;
import presentation.games.RouletteGUIImp;
import presentation.player.InventoryView;
import presentation.player.PlayerView;
import presentation.player.StoreView;
import presentation.staff.StaffView;
import presentation.staff.packagesManager.PackageManagerView;
import presentation.staff.playersManager.PlayerManagerView;
import presentation.staff.productsManager.ProductManagerView;

public final class GUIFactory {
    private static final Map<Integer, Supplier<JFrame>> VIEWS = Map.of(
        Event.OPEN_PLAYER_VIEW,     PlayerView::new,
        Event.OPEN_GAMES_PANEL,     GamesPanelView::new,
        Event.OPEN_INVENTORY,       InventoryView::new,
        Event.OPEN_STORE,           StoreView::new,
        Event.OPEN_STAFF_VIEW,      StaffView::new,
        Event.OPEN_PACKAGE_MANAGER, PackageManagerView::new,
        Event.OPEN_PRODUCT_MANAGER, ProductManagerView::new,
        Event.OPEN_PLAYER_MANAGER,  PlayerManagerView::new
    );

    private GUIFactory() {}

    public static JFrame createView(int event) {
        Supplier<JFrame> creator = VIEWS.get(event);
        if (creator == null) throw new IllegalArgumentException("Unknown view event: " + event);
        return creator.get();
    }

    public static BlackjackGUI createBlackjackView(Blackjack blackjack) {
        return new BlackjackGUIImp(
            blackjack.getCroupierVisibleCard(),
            blackjack.getPlayerCards(),
            blackjack.getCroupierVisiblePoints(),
            blackjack.getPlayerPoints()
        );
    }

    public static RouletteGUI createRouletteView() {
        return new RouletteGUIImp();
    }
}
