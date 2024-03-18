import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

public class JUnitTestClass {

    // DECK TESTS
    
    @Test
    public void testDeckGet() {
        Deck testDeck = new Deck();
        assertTrue(testDeck.getCards().get(0) instanceof Card);
    }
    
    @Test
    public void testDraw() {
        // Create a deck with known cards
        Deck deck = new Deck();
        ArrayList<Card> cards = deck.getCards();
        cards.clear(); // Remove the default cards
        cards.add(new Card("Ace", "Hearts"));

        // Draw the first card
        Card drawnCard = deck.draw();

        // Assert that the drawn card is the expected card
        Card expectedCard = new Card("Ace", "Hearts");
        assertEquals(expectedCard.getValue(), drawnCard.getValue());
        
        // Assert that the deck now has 2 cards remaining
        assertEquals(0, cards.size());
    }
    
    @Test (expected = IndexOutOfBoundsException.class)
    public void testIndexOutOfBoundsDeck() {
        Deck deck = new Deck();
        ArrayList<Card> cards = deck.getCards();
        cards.clear();

        // Draw the first card
        Card drawnCard = deck.draw();
    }
    
    
    //
    // END DECK TESTS
    //
    

}
