package presentation;

import business.Roulette;

public class RouletteView {

	public void initRouletteView(int basePoints, Roulette.Bet bet) {
		Roulette roulette = new Roulette(basePoints, bet);
		
        System.out.println("========RULETA=========");
        System.out.println("Apuesta del jugador: " + bet.toString());
        int result = roulette.play();
        System.out.println("La bola ha caido en el numero: (" + roulette.getWinningNumber() + ")");
        if(result > 0) System.out.println("Felicidades, has ganado " + result + " puntos!");
        else System.out.println("Lo siento, no has tenido suerta esta vez..");
	}
}
