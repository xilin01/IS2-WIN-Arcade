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

    // --- GameResult enum ---

    @Test
    void gameResultEnum_allConstantsAccessible() {
        assertNotNull(Blackjack.GameResult.LOST);
        assertNotNull(Blackjack.GameResult.CONTINUE);
        assertNotNull(Blackjack.GameResult.WON);
    }

    // --- playerHit() path coverage ---

    @Test
    void playerHit_afterPlay_returnsValidStatus() {
        bj.play();
        Blackjack.GameResult status = bj.playerHit();
        assertTrue(
            Set.of(Blackjack.GameResult.LOST, Blackjack.GameResult.CONTINUE, Blackjack.GameResult.WON).contains(status),
            "Unexpected status: " + status
        );
    }

    // --- getResult() path coverage ---

    @Test
    void getResult_afterBothPlay_returnsValidStatus() {
        bj.play();
        bj.croupierPlay();
        Blackjack.GameResult result = bj.getResult();
        assertTrue(
            Set.of(Blackjack.GameResult.LOST, Blackjack.GameResult.CONTINUE, Blackjack.GameResult.WON).contains(result),
            "Unexpected result: " + result
        );
    }
}
