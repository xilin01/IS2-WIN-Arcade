package business.game;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

public class Roulette extends ChanceGame {
    public static class Bet {
        private static final Set<Integer> RED_NUMBERS = new HashSet<>(Arrays.asList(1, 3, 5, 7, 9, 12, 14, 16, 18, 19, 21, 23, 25, 27, 30, 32, 34, 36));
        private static final Set<Integer> BLACK_NUMBERS = new HashSet<>(Arrays.asList(2, 4, 6, 8, 10, 11, 13, 15, 17, 20, 22, 24, 26, 28, 29, 31, 33, 35));

        private final String description;
        private final int multiplier;
        private final Predicate<Integer> winCondition;
        public enum Bets {Sencillo, Calle, Columna, Docena, Rojo, Negro, Par, Impar, Low, High};
        
        public static class tBet{
        	public Bets betName;
        	public int[] data;
        	
        	public tBet(Bets betName, int[] data) {
        		this.betName = betName;
        		this.data = data;
        	}
        }

        private Bet(String description, int multiplier, Predicate<Integer> winCondition) {
            this.description = description;
            this.multiplier = multiplier;
            this.winCondition = winCondition;
        }

        public static Bet straight(int n1) {
            return new Bet("Sencillo al " + n1, 35, n -> n == n1);
        }

        public static Bet street(int n1, int n2, int n3) {
            return new Bet("Calle al " + n1 + ", " + n2 + " y " + n3, 11, n -> n == n1 || n == n2 || n == n3);
        }

        public static Bet column(int col) { // col = 1, 2 o 3
            return new Bet("Columna " + col, 2, n -> n != 0 && (n % 3) == (col % 3));
        }

        public static Bet dozen(int dozen) { // dozen = 1, 2 o 3
            int start = (dozen - 1) * 12 + 1, end = start + 11;
            return new Bet("Docena " + dozen, 2, n -> start <= n && n <= end);
        }

        public static Bet red() {
            return new Bet("Rojo", 1, n -> RED_NUMBERS.contains(n));
        }

        public static Bet black() {
            return new Bet("Negro", 1, n -> BLACK_NUMBERS.contains(n));
        }

        public static Bet even() {
            return new Bet("Par", 1, n -> n != 0 && n % 2 == 0);
        }

        public static Bet odd() {
            return new Bet("Impar", 1, n -> n != 0 && n % 2 == 1);
        }

        public static Bet low() {
            return new Bet("1-18", 1, n -> 1 <= n && n <= 18);
        }

        public static Bet high() {
            return new Bet("19-36", 1, n -> 19 <= n && n <= 36);
        }

        public int getMultiplier() {
            return multiplier;
        }

        public boolean isWinner(int number) {
            return winCondition.test(number);
        }

        @Override
        public String toString() {
            return description + " (Premio: " + multiplier + " a 1)";
        }
    }

    private Bet bet;
    private int winningNumber;

    public Roulette(int basePoints, Bet bet) {
        super(basePoints, bet.getMultiplier());
        this.bet = bet;
        winningNumber = -1;
    }
    
    public Roulette(int basePoints, Bet.tBet bet) {
    	this(basePoints, toBet(bet));
    }
    
    public static Bet toBet(Bet.tBet bet) {
    	switch(bet.betName) {
	    	case Sencillo: return Bet.straight(bet.data[0]); 
	    	case Calle: return Bet.street(bet.data[0], bet.data[1], bet.data[2]); 
	    	case Columna: return Bet.column(bet.data[0]); 
	    	case Docena: return Bet.dozen(bet.data[0]); 
	    	case Rojo: return Bet.red();
	    	case Negro: return Bet.black(); 
	    	case Par: return Bet.even();
	    	case Impar: return Bet.odd();
	    	case Low: return Bet.low(); 
	    	case High: return Bet.high();
	    	default: return Bet.red();
    	}
    }

	@Override
    public int play() {
        winningNumber = RAND.nextInt(37);
        return bet.isWinner(winningNumber) ? getPointsWon() : 0;
    }

    public int getWinningNumber() {
        if(winningNumber == -1) throw new IllegalStateException("Play the roulette to know the winning number");
        return winningNumber;
    }
}
