package business.builders;

import java.util.List;

import javax.swing.JFrame;

import org.json.JSONObject;

import business.game.CardGame.Cards;
import presentation.games.BlackjackGUI;

public class BlackJackGUIBuilder extends Builder<JFrame>{

	public BlackJackGUIBuilder() {
		super("BlackJackGUI");
	}

	@SuppressWarnings("unchecked")
	@Override
	protected JFrame createInstance(JSONObject data) {
		if(!(data.has("CroupierDeck") && data.has("PlayerDeck") && data.has("CroupierPoints") && data.has("PlayerPoints")))
			throw new IllegalArgumentException("Invalid data for BlackJackGUI");
		return new BlackjackGUI((Cards)data.get("CroupierDeck"),(List<Cards>) data.get("PlayerDeck"), data.getInt("CroupierPoints"), data.getInt("PlayerPoints"));
	}

}
