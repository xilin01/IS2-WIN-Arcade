package business;

import java.util.ArrayList;
import java.util.List;

// Por el MVC, esto será llamado por un controlador gigante (?)
public class Blackjack extends CardGame {
	private class Hand {
		private List<Cards> cards;
		private int points;
		private int aces;
		
		private Hand() {
			cards = new ArrayList<>();
			points = 0;
			aces = 0;
		}
		
		private void addCard(Cards card) {
			cards.add(card);
			int value = card.getValue();
			if(value == 1) { // As
				points += 11;
				aces++;
			}
			else if(value <= 10) points += value;
			else points += 10; // Figuras			
			while(points > BLACKJACK_LIMIT && aces > 0) {
				points -= 10;
				aces--;
			}
		}
		
		private int getPoints() {
			return points;
		}
		
		private int getStatus() {
			if(points == BLACKJACK_LIMIT) return WON;
			else if(points > BLACKJACK_LIMIT) return LOST;
			else return CONTINUE;
		}
	}
	
	private static final String NAME = "Blackjack";
	private static final int BLACKJACK_LIMIT = 21;
	private static final int CROUPIER_LIMIT = 17;
	//
	private static final int LOST = -1;
	private static final int CONTINUE = 0;
	private static final int WON = 1;
	
	private Hand croupier;
	private Hand player;
	
	public Blackjack() {
		super(NAME);
		croupier = new Hand();
		player = new Hand();
	}

	@Override
	public int play() {
		croupier.addCard(getRandomCard());
		int visiblePoints = croupier.getPoints();
		croupier.addCard(getRandomCard()); // Carta oculta del croupier
		player.addCard(getRandomCard());
		player.addCard(getRandomCard());
		return visiblePoints; 
	}
	
	public int croupierPlay() {
		while(croupier.getPoints() < CROUPIER_LIMIT && croupier.getStatus() == CONTINUE) croupier.addCard(getRandomCard());
		return CONTINUE;
	}
	
	public int playerHit() {
		player.addCard(getRandomCard());
		return player.getStatus();
	}
	
	public boolean hasPlayerWon() {
		int playerPoints = player.getPoints(), croupierPoints = croupier.getPoints();
		
		if(playerPoints > BLACKJACK_LIMIT) return false;
		if(croupierPoints > BLACKJACK_LIMIT) return true;
		return playerPoints > croupierPoints;
	}
	
	public List<Cards> getPlayerCards() {
		return new ArrayList<>(player.cards);
	}
	
	public List<Cards> getCroupierCards() {
		return new ArrayList<>(croupier.cards);
	}
	
	public int getPlayerPoints() {
		return player.getPoints();
	}
	
	public int getCroupierPoints() {
		return croupier.getPoints();
	}
}
