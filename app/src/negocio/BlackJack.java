package negocio;

import java.util.ArrayList;
import java.util.List;

//Por el modelo vista controlador, esto será llamado por un controlador gigante. 
public class BlackJack extends JuegoDeCartas{
	private static final Integer ID = 0;
	private static final String NOMBRE = "BlackJack";
	
	private static final int HA_GANADO = 0;
	private static final int HA_PERDIDO = 1;
	private static final int CONTINUA = 2;
	
	private class Person {
		List<Cards> personDeck;
		int points;
		
		private Person() {
			personDeck = new ArrayList<>();
			points = 0;
		}
		
		private Cards addCard(){
			Cards card = getCard(BlackJack.this.deck);
			personDeck.add(card);
			return card;
		}
		
		private int addPoints(Cards card, boolean asIs1) {
			if(card.getValue() == 1) {
				points += asIs1 ? 1 : 11;
				return pointsControl();
			}
			else return addPoints(card);
		}
		
		private int addPoints(Cards card) {
			int value = card.getValue();
			if(value > 9) value = 10;
			points += value;
			return pointsControl();
		}
		
		private int pointsControl() {
			if(points == 21) return HA_GANADO;
			else if(points > 21) return HA_PERDIDO;
			else return CONTINUA;
		}
	}
	
	private Person croupier, player;
	
	public BlackJack() {
		super(ID, NOMBRE);
		croupier = new Person();
		player = new Person();
	}

	@Override
	public int jugar() { //devuelve puntos iniciales del croupier
		croupierAddPoints(croupierWantsCard());
		int puntosVisibles = croupier.points;
		croupierAddPoints(croupierWantsCard());
		return puntosVisibles; //una carta esta oculta. La puntuacion tamb.
	}
	
	public int croupierPlay() {
		while(croupier.points < 17) {
			int jugada = croupierAddPoints(croupierWantsCard());
			if(jugada != CONTINUA) return jugada;
		}
		return CONTINUA;
	}
	
	public Cards playerWantsCard() {
		return player.addCard();
	}
	
	public int playerAddPoints(Cards c, boolean asIs1) {
		return player.addPoints(c, asIs1);
	}
	
	public int playerAddPoints(Cards c) {
		return player.addPoints(c);
	}
	
	public Cards croupierWantsCard() {
		return croupier.addCard();
	}
	
	public int croupierAddPoints(Cards c) {
		return croupier.addPoints(c, 11 + croupier.points > 21);
	}
	
	public boolean playerWon() {
		return player.points > croupier.points;
	}
	
	public List<Cards> getPlayerCards(){
		return new ArrayList<> (player.personDeck);
	}
	
	public List<Cards> getCroupierCards(){
		return new ArrayList<> (croupier.personDeck);
	}
	
	public int getPlayerPoints() {
		return player.points;
	}
	
	public int getCroupierPoints() {
		return croupier.points;
	}
}
