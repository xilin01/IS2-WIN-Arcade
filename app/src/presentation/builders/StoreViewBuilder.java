package presentation.builders;

import javax.swing.JFrame;
import org.json.JSONObject;

import presentation.player.StoreView;

public class StoreViewBuilder extends Builder<JFrame>{

	public StoreViewBuilder() {
		super("StoreView");
	}

	@Override
	protected JFrame createInstance(JSONObject data) {
		if(data == null || !data.isEmpty()) throw new IllegalArgumentException("Invalid data in createInstance for StoreViewBuilder");
		return new StoreView();
	}
}
