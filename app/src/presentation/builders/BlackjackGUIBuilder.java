package presentation.builders;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import org.json.JSONObject;
import business.game.CardGame.Cards;
import presentation.games.BlackjackGUIImp;

public class BlackjackGUIBuilder extends Builder<JFrame> {

	public BlackjackGUIBuilder() {
		super("BlackjackGUI");
	}
	
	@Override
	protected JFrame createInstance(JSONObject data) {
		if(!data.has("CroupierDeck") || !data.has("PlayerDeck") || !data.has("CroupierPoints") || !data.has("PlayerPoints"))
			throw new IllegalArgumentException("Invalid data for createInstance in BlackjackGUIBuilder");
		return new BlackjackGUIImp(getCroupierDeck(data), getPlayerDeck(data), data.getInt("CroupierPoints"), data.getInt("PlayerPoints"));
	}
	
	private Cards getCroupierDeck(JSONObject data) {
		return Cards.valueOf(""+data.get("CroupierDeck"));
	}
	
	private List<Cards> getPlayerDeck(JSONObject data) {
	    List<Cards> playerDeck = new ArrayList<>();
	    
	    for(Object card: data.getJSONArray("PlayerDeck")) playerDeck.add(Cards.valueOf(""+card));
	    return playerDeck;
	}
}
