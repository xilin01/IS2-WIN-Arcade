package business.game;

import java.util.ArrayList;
import java.util.List;

public class Blackjack extends CardGame {
	public enum GameResult {LOST, CONTINUE, WON}
	
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
        	if(card == null) throw new IllegalArgumentException("Invalid arg for addCard in Hand");
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

        private GameResult getStatus() {
            if(points == BLACKJACK_LIMIT) return GameResult.WON;
            else if(points > BLACKJACK_LIMIT) return GameResult.LOST;
            else return GameResult.CONTINUE;
        }
    }
    
    private static final int BLACKJACK_LIMIT = 21;
    private static final int CROUPIER_LIMIT = 17;

    private Hand croupier;
    private Hand player;

    public Blackjack(int basePoints) {
        super(basePoints);
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
   
    public Cards getCroupierVisibleCard() {
        if(croupier.cards == null || croupier.cards.isEmpty()) throw new IllegalStateException("The croupier's hand is empty");
        return croupier.cards.get(0);
    }

    public int getCroupierVisiblePoints() {
        int v = getCroupierVisibleCard().getValue();
        if (v == 1) return 11;
        if (v > 10) return 10;
        return v;
    }

    public GameResult croupierPlay() {
    	if(getCroupierPoints() < getPlayerPoints()) {
    		while(croupier.getPoints() < CROUPIER_LIMIT) croupier.addCard(getRandomCard());
    	}
        return croupier.getStatus();
    }

    public GameResult playerHit() {
        player.addCard(getRandomCard());
        return player.getStatus();
    }

    public GameResult getResult() {
        int playerPoints = player.getPoints(), croupierPoints = croupier.getPoints();

        if(playerPoints > BLACKJACK_LIMIT) return GameResult.LOST;
        if(playerPoints == croupierPoints) return GameResult.CONTINUE; // Empate
        if(croupierPoints > BLACKJACK_LIMIT) return GameResult.WON;
        return playerPoints > croupierPoints ? GameResult.WON : GameResult.LOST;
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
