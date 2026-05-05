package business.builders;

import javax.swing.JFrame;
import org.json.JSONObject;
import presentation.user.PlayerManagerView;

public class PlayerManagerViewBuilder extends Builder<JFrame>{

	public PlayerManagerViewBuilder() {
		super("PlayerManagerView");
	}

	@Override
	protected JFrame createInstance(JSONObject data) {
		if(data == null || !data.isEmpty()) throw new IllegalArgumentException("Invalid data for createInstance in PlayerManagerViewBuilder");
		return new PlayerManagerView();
	}
}
