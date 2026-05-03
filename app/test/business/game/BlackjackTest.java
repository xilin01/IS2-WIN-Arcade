package business.game;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Set;

class BlackjackTest {

    private Blackjack bj;

    @BeforeEach
    void setUp() {
        bj = new Blackjack(100);
    }

    // --- Guard clauses ---

    @Test
    void getCroupierVisibleCard_beforePlay_throwsIllegalState() {
        assertThrows(IllegalStateException.class, bj::getCroupierVisibleCard);
    }

    // --- State after play() ---

    @Test
    void afterPlay_playerHasTwoCards() {
        bj.play();
        assertEquals(2, bj.getPlayerCards().size());
    }

    @Test
    void afterPlay_croupierHasTwoCards() {
        bj.play();
        assertEquals(2, bj.getCroupierCards().size());
    }

    @Test
    void afterPlay_playerPointsPositive() {
        bj.play();
        assertTrue(bj.getPlayerPoints() > 0);
    }

    @Test
    void afterPlay_croupierVisibleCardNotNull() {
        bj.play();
        assertNotNull(bj.getCroupierVisibleCard());
    }

    @Test
    void afterPlay_returnsVisibleCroupierPoints() {
        // Single card: ace=11, 2-10=face value, J/Q/K=10 — always in [2, 11]
        int visible = bj.play();
        assertTrue(visible >= 2 && visible <= 11, "Visible points out of expected range: " + visible);
    }

    // --- Status constants ---

    @Test
    void statusConstants_haveExpectedValues() {
        assertEquals(-1, Blackjack.LOST);
        assertEquals(0, Blackjack.CONTINUE);
        assertEquals(1, Blackjack.WON);
    }

    // --- playerHit() path coverage ---

    @Test
    void playerHit_afterPlay_returnsValidStatus() {
        bj.play();
        int status = bj.playerHit();
        assertTrue(
            Set.of(Blackjack.LOST, Blackjack.CONTINUE, Blackjack.WON).contains(status),
            "Unexpected status: " + status
        );
    }

    // --- getResult() path coverage ---

    @Test
    void getResult_afterBothPlay_returnsValidStatus() {
        bj.play();
        bj.croupierPlay();
        int result = bj.getResult();
        assertTrue(
            Set.of(Blackjack.LOST, Blackjack.CONTINUE, Blackjack.WON).contains(result),
            "Unexpected result: " + result
        );
    }
}
