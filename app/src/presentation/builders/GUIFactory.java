package presentation.builders;

import java.util.List;
import java.util.Map;
import javax.swing.JFrame;
import org.json.JSONObject;
import presentation.Event;

// Patrón Singleton
public final class GUIFactory {
    private static final Factory<JFrame> INSTANCE = new BuilderBasedFactory<>(List.of(
	    new BlackjackGUIBuilder(),
	    new GamesPanelViewBuilder(),
	    new InventoryViewBuilder(),
	    new PackageManagerViewBuilder(),
	    new PlayerManagerViewBuilder(),
	    new PlayerViewBuilder(),
	    new ProductManagerViewBuilder(),
	    new RouletteGUIBuilder(),
	    new StaffViewBuilder(),
	    new StoreViewBuilder()
    ));
    private static final Map<Integer, String> EventView = Map.of(
	    Event.OPEN_PLAYER_VIEW, "PlayerView",
	    Event.OPEN_GAMES_PANEL, "GamesPanelView",
	    Event.OPEN_INVENTORY, "InventoryView",
	    Event.OPEN_STORE, "StoreView",
	    Event.OPEN_STAFF_VIEW, "StaffView",
	    Event.OPEN_PACKAGE_MANAGER, "PackageManagerView",
	    Event.OPEN_PRODUCT_MANAGER, "ProductManagerView",
	    Event.OPEN_PLAYER_MANAGER, "PlayerManagerView",
	    Event.BLACKJACK_PLAY, "BlackjackGUI",
	    Event.ROULETTE_PLAY, "RouletteGUI"
    );
    
	public static JFrame createView(final int event, JSONObject data) {
		return INSTANCE.createInstance(new JSONObject().put("name", EventView.get(event)).put("data", data));
	}
}
