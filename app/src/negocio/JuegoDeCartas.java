package negocio;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public abstract class JuegoDeCartas extends Juego{
	protected Set<Cards> deck;

	protected static enum Cards {
		C1, C2, C3, C4, C5, C6, C7, C8, C9, C10, C11, C12, C13, //Corazones
		D1, D2, D3, D4, D5, D6, D7, D8, D9, D10, D11, D12, D13, //Diamantes
		P1, P2, P3, P4, P5, P6, P7, P8, P9, P10, P11, P12, P13, //Picas
		T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13;  //Tréboles
		
		public int getValue() {
			return ordinal() % 13 + 1;
		}
		
	};
								 
	 public JuegoDeCartas(Integer id, String nombre) {
			super(id, nombre);
			deck = getNewCards();
	}
								
	protected Set<Cards> getNewCards(){
		Set<Cards> aux = new HashSet<Cards>();
		for(Cards c: Cards.values()) aux.add(c);
		return aux;
	}
								
	protected Cards getCard(Set<Cards> deck) {
		Iterator<Cards> it = deck.iterator();
		for(int i = 0; i < RAND.nextInt(deck.size()) - 1; i++) it.next();

		Cards carta = it.next();
		deck.remove(carta);
		return carta;
	}
	

}
