package presentation.builders;

import javax.swing.JFrame;
import org.json.JSONObject;

import presentation.player.PlayerView;

public class PlayerViewBuilder extends Builder<JFrame>{

	public PlayerViewBuilder() {
		super("PlayerView");
	}

	@Override
	protected JFrame createInstance(JSONObject data) {
		if(data == null || !data.isEmpty()) throw new IllegalArgumentException("Invalid data for createInstance in PlayerViewBuilder");
		return new PlayerView();
	}
}
