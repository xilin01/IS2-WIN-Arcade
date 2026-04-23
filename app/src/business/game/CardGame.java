package business.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public abstract class CardGame extends Game {
    public enum Cards {
        C1, C2, C3, C4, C5, C6, C7, C8, C9, C10, C11, C12, C13, // Corazones
        D1, D2, D3, D4, D5, D6, D7, D8, D9, D10, D11, D12, D13, // Diamantes
        P1, P2, P3, P4, P5, P6, P7, P8, P9, P10, P11, P12, P13, // Picas
        T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13; // Tréboles

        public int getValue() {
            return ordinal() % 13 + 1;
        }
    };

    protected List<Cards> deck;

    protected CardGame(String name, int basePoints) {
        super(name, basePoints);
        deck = new ArrayList<>(Arrays.asList(Cards.values()));
        Collections.shuffle(deck, RAND);
    }

    protected Cards getRandomCard() {
        if(deck.isEmpty()) throw new IllegalStateException("The deck is empty");
        return deck.removeLast();
    }
}
