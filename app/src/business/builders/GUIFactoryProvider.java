package business.builders;

import java.util.List;
import javax.swing.JFrame;

public final class GUIFactoryProvider {

    private static final Factory<JFrame> INSTANCE =
        new BuilderBasedFactory<>(
            List.of(
                new BlackJackGUIBuilder(),
                new GamesPanelViewBuilder(),
                new InventoryViewBuilder(),
                new PackageManagerViewBuilder(),
                new PlayerManagerViewBuilder(),
                new PlayerViewBuilder(),
                new ProductManagerViewBuilder(),
                new RouletteGUIBuilder(),
                new StaffViewBuilder(),
                new StoreViewBuilder()
            )
        );

    private GUIFactoryProvider() {}

    public static Factory<JFrame> get() {
        return INSTANCE;
    }
}
