package business.game;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

public class Roulette extends ChanceGame {
    public static class Bet {
    	public static class tBet {
        	public Bets betName;
        	public int[] data;
        	
        	public tBet(Bets betName, int[] data) {
            	if(betName == null || data == null) throw new IllegalArgumentException("Invalid args for Builder in tBet");
        		this.betName = betName;
        		this.data = data;
        	}
        }
    	
        private static final Set<Integer> RED_NUMBERS = new HashSet<>(Arrays.asList(1, 3, 5, 7, 9, 12, 14, 16, 18, 19, 21, 23, 25, 27, 30, 32, 34, 36));
        private static final Set<Integer> BLACK_NUMBERS = new HashSet<>(Arrays.asList(2, 4, 6, 8, 10, 11, 13, 15, 17, 20, 22, 24, 26, 28, 29, 31, 33, 35));

        private final String description;
        private final int multiplier;
        private final Predicate<Integer> winCondition;
        public enum Bets {
            Sencillo {
                @Override public Bet create(int[] data) { return Bet.straight(data[0]); }
            },
            Calle {
                @Override public Bet create(int[] data) { return Bet.street(data[0], data[1], data[2]); }
            },
            Columna {
                @Override public Bet create(int[] data) { return Bet.column(data[0]); }
            },
            Docena {
                @Override public Bet create(int[] data) { return Bet.dozen(data[0]); }
            },
            Rojo {
                @Override public Bet create(int[] data) { return Bet.red(); }
            },
            Negro {
                @Override public Bet create(int[] data) { return Bet.black(); }
            },
            Par {
                @Override public Bet create(int[] data) { return Bet.even(); }
            },
            Impar {
                @Override public Bet create(int[] data) { return Bet.odd(); }
            },
            Bajo {
                @Override public Bet create(int[] data) { return Bet.low(); }
            },
            Alto {
                @Override public Bet create(int[] data) { return Bet.high(); }
            };

            public abstract Bet create(int[] data);
        }

        private Bet(String description, int multiplier, Predicate<Integer> winCondition) {
        	if(description == null || multiplier < 1 || winCondition == null) throw new IllegalArgumentException("Invalid args for Builder in Bet");
            this.description = description;
            this.multiplier = multiplier;
            this.winCondition = winCondition;
        }

        public static Bet straight(int n1) {
        	if(n1 <= -1 || 37 <= n1) throw new IllegalArgumentException("Invalid arg for straight in Bet");
            return new Bet("Sencillo al " + n1, 35, n -> n == n1);
        }

        public static Bet street(int n1, int n2, int n3) {
        	if(n2 != n1 + 1 || n3 != n2 + 1) throw new IllegalArgumentException("Invalid args for street in Bet");
            return new Bet("Calle al " + n1 + ", " + n2 + " y " + n3, 11, n -> n == n1 || n == n2 || n == n3);
        }

        public static Bet column(int col) { // col = 1, 2 o 3
        	if(col != 1 && col != 2 && col != 3) throw new IllegalArgumentException("Invalid arg for column in Bet");
            return new Bet("Columna " + col, 2, n -> n != 0 && (n % 3) == (col % 3));
        }

        public static Bet dozen(int dozen) { // dozen = 1, 2 o 3
        	if(dozen != 1 && dozen != 2 && dozen != 3) throw new IllegalArgumentException("Invalid arg for dozen in Bet");
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
        	if(number <= -1 || 37 <= number) throw new IllegalArgumentException("Invalid arg for isWinner in Bet");
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
    	if(bet == null) throw new IllegalArgumentException("Invalid arg for toBet in Roulette");
        return bet.betName.create(bet.data);
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
