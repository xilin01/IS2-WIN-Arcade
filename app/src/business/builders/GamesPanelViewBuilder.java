package business.builders;


import javax.swing.JFrame;

import org.json.JSONObject;

import presentation.games.GamesPanelView;

public class GamesPanelViewBuilder extends Builder<JFrame>{

	public GamesPanelViewBuilder() {
		super("GamesPanelViewBuilder");
	}

	@Override
	protected JFrame createInstance(JSONObject data) {
		if(data == null || !data.isEmpty()) throw new IllegalArgumentException("Invalid data for GamesPanelViewBuilder");
		return new GamesPanelView();
	}
}
