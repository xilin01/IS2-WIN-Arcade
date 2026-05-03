package business.game;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class RouletteTest {

    // --- Guard clause ---

    @Test
    void getWinningNumber_beforePlay_throwsIllegalState() {
        Roulette r = new Roulette(50, Roulette.Bet.red());
        assertThrows(IllegalStateException.class, r::getWinningNumber);
    }

    // --- State after play() ---

    @Test
    void afterPlay_winningNumberInRange() {
        Roulette r = new Roulette(50, Roulette.Bet.red());
        r.play();
        int n = r.getWinningNumber();
        assertTrue(n >= 0 && n <= 36, "Winning number out of range: " + n);
    }

    // --- Bet.isWinner — all 10 bet types (fully deterministic) ---

    @Test
    void redBet_redNumber_wins() {
        Roulette.Bet bet = Roulette.Bet.red();
        assertTrue(bet.isWinner(1));   // 1 is red
        assertFalse(bet.isWinner(2));  // 2 is black
        assertFalse(bet.isWinner(0));  // 0 is neither
    }

    @Test
    void blackBet_blackNumber_wins() {
        Roulette.Bet bet = Roulette.Bet.black();
        assertTrue(bet.isWinner(2));
        assertFalse(bet.isWinner(1));
        assertFalse(bet.isWinner(0));
    }

    @Test
    void evenBet_evenNumber_wins() {
        Roulette.Bet bet = Roulette.Bet.even();
        assertTrue(bet.isWinner(4));
        assertFalse(bet.isWinner(3));
        assertFalse(bet.isWinner(0));  // 0 is excluded by n != 0 condition
    }

    @Test
    void oddBet_oddNumber_wins() {
        Roulette.Bet bet = Roulette.Bet.odd();
        assertTrue(bet.isWinner(3));
        assertFalse(bet.isWinner(4));
        assertFalse(bet.isWinner(0));
    }

    @Test
    void lowBet_boundaries() {
        Roulette.Bet bet = Roulette.Bet.low();
        assertTrue(bet.isWinner(1));   // lower bound
        assertTrue(bet.isWinner(18));  // upper bound
        assertFalse(bet.isWinner(19)); // just above
        assertFalse(bet.isWinner(0));
    }

    @Test
    void highBet_boundaries() {
        Roulette.Bet bet = Roulette.Bet.high();
        assertTrue(bet.isWinner(19));  // lower bound
        assertTrue(bet.isWinner(36));  // upper bound
        assertFalse(bet.isWinner(18)); // just below
        assertFalse(bet.isWinner(0));
    }

    @Test
    void straightBet_exactNumber_wins() {
        Roulette.Bet bet = Roulette.Bet.straight(7);
        assertTrue(bet.isWinner(7));
        assertFalse(bet.isWinner(6));
        assertFalse(bet.isWinner(8));
        assertFalse(bet.isWinner(0));
    }

    @Test
    void streetBet_anyOfThreeNumbers_wins() {
        Roulette.Bet bet = Roulette.Bet.street(1, 2, 3);
        assertTrue(bet.isWinner(1));
        assertTrue(bet.isWinner(2));
        assertTrue(bet.isWinner(3));
        assertFalse(bet.isWinner(4));
    }

    @Test
    void columnBet_correctColumn_wins() {
        // column(1): n % 3 == 1 → 1, 4, 7, 10...
        Roulette.Bet bet = Roulette.Bet.column(1);
        assertTrue(bet.isWinner(1));
        assertTrue(bet.isWinner(4));
        assertFalse(bet.isWinner(2));
        assertFalse(bet.isWinner(0));
    }

    @Test
    void dozenBet_firstDozen_boundaries() {
        Roulette.Bet bet = Roulette.Bet.dozen(1);
        assertTrue(bet.isWinner(1));   // lower bound
        assertTrue(bet.isWinner(12));  // upper bound
        assertFalse(bet.isWinner(13)); // just above
        assertFalse(bet.isWinner(0));
    }

    // --- Multiplier ---

    @Test
    void straightBet_multiplierIs35() {
        assertEquals(35, Roulette.Bet.straight(7).getMultiplier());
    }

    @Test
    void redBet_multiplierIs1() {
        assertEquals(1, Roulette.Bet.red().getMultiplier());
    }
}
