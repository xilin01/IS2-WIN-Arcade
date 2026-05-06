package presentation.builders;

import javax.swing.JFrame;
import org.json.JSONObject;

import presentation.games.RouletteGUIImp;

public class RouletteGUIBuilder extends Builder<JFrame>{

	public RouletteGUIBuilder() {
		super("RouletteGUI");
	}

	@Override
	protected JFrame createInstance(JSONObject data) {
		if(data == null || !data.isEmpty()) throw new IllegalArgumentException("Invalid data for createInstance in RouletteGUIBuilder");
		return new RouletteGUIImp();
	}
}
