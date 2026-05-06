package presentation.builders;

import javax.swing.JFrame;
import org.json.JSONObject;
import presentation.user.InventoryView;

public class InventoryViewBuilder extends Builder<JFrame>{

	public InventoryViewBuilder() {
		super("InventoryView");
	}

	@Override
	protected JFrame createInstance(JSONObject data) {
		if(data == null || !data.isEmpty()) throw new IllegalArgumentException("Invalid data for createInstance in InventoryViewBuilder");
		return new InventoryView();
	}
}
