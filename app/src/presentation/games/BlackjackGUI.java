package presentation.games;

import java.util.List;
import business.game.CardGame.Cards;

public interface BlackjackGUI {
	
	void update(List<Cards> croupierDeck, List<Cards> playerDeck, int croupierPoints, int playerPoints, boolean showFullCroupier);
	void end(String message);
}
