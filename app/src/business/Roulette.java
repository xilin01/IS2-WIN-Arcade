package business;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

public class Roulette extends ChanceGame {
    public static class Bet {
    	private final String description;
        private final int multiplier;
        private final Predicate<Integer> winCondition;

        private Bet(String description, int multiplier, Predicate<Integer> winCondition) {
        	this.description = description;
            this.multiplier = multiplier;
            this.winCondition = winCondition;
        }
        
        public static Bet straight(int n1) {
            return new Bet("Sencillo al " + n1, 35, n -> n == n1);
        }
        /* Por ahora no lo hacemos porque es dificil de implementar
        public static Bet doble(int n1, int n2) {
            return new Bet("Doble al " + n1 + " y " + n2, 17, n -> n == n1 || n == n2);
        }
        
        public static triple?

        public static Bet corner(int n1, int n2, int n3, int n4) {
            return new Bet("Esquina al " + n1 + ", " + n2 + ", " + n3 + " y " + n4, 8, n -> n == n1 || n == n2 || n == n3 || n == n4);
        }
		*/
        
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
            List<Integer> reds = Arrays.asList(1, 3, 5, 7, 9, 12, 14, 16, 18, 19, 21, 23, 25, 27, 30, 32, 34, 36);
            return new Bet("Rojo", 1, n -> reds.contains(n));
        }

        public static Bet black() {
        	List<Integer> blacks = Arrays.asList(2, 4, 6, 8, 10, 11, 13, 15, 17, 20, 22, 24, 26, 28, 29, 31, 33, 35);
            return new Bet("Negro", 1, n -> blacks.contains(n));
        }

        public static Bet even() {
            return new Bet("Par", 1, n -> n != 0 && n % 2 == 0);
        }

        public static Bet odd() {
            return new Bet("Impar", 1, n -> n != 0 && n % 2 == 1);
        }

        public static Bet low() { // 1-18
            return new Bet("1-18", 1, n -> 1 <= n && n <= 18);
        }

        public static Bet high() { // 19-36
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

    private static final String NAME = "Ruleta";
    
    private Bet bet;
    private int winningNumber;

    public Roulette(int basePoints, Bet bet) {
        super(NAME, basePoints, bet.getMultiplier());
        this.bet = bet;
        winningNumber = -1;
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
