import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Deck objects represent a deck of cards for a game of Blackjack. Each deck has
 * 52 standard playing cards, represented by card objects. The deck is able to
 * be shuffled and dealt.
 * 
 * @author gunnarvonbergen
 *
 */
public class Deck implements Cloneable, Serializable {

    // Creates an array list of cards. Meets instance variable and array of
    // objects requirement.

    private static final long serialVersionUID = -5052081388121950965L;

    private ArrayList<Card> cards = new ArrayList<Card>();

    private int remainingCards = 52;

    /**
     * No parameter constructor generates a new array list of cards that
     * represents a full deck.
     */
    public Deck() {
        String[] ranks = { "Ace", "2", "3", "4", "5", "6", "7", "8", "9", "10",
                "Jack", "Queen", "King" };
        String[] suits = { "Hearts", "Diamonds", "Spades", "Clubs" };
        for (String suit : suits) {
            for (String rank : ranks) {
                cards.add(new Card(rank, suit));
            }
        }
    }

    /**
     * Copy constructor that allows for creating deep copies through the clone()
     * method.
     * 
     * @param other the deck intended to be copied.
     */
    public Deck(Deck other) { // Copy constructor
        // Allows for copying of already dealt decks. Might not be useful now,
        // but potentially in the future.
        this.remainingCards = other.remainingCards;
        this.cards = new ArrayList<Card>(other.cards.size());
        for (Card card : other.cards) {
            this.cards.add(card.clone());
        }
    }

    /**
     * Returns the removed card from the array list.
     * 
     * @return a card object taken from the "top of the deck".
     */
    public Card draw() {
        if (cards.isEmpty()) {
            System.out.println("Deck is empty!?!");
        }
        // Removes the "top" card from the deck and returns it.
        return cards.remove(0);
    }

    /**
     * The shuffle method will loop through an array list of cards, generate a
     * random number between 0 and 51, swapping the current card with the card
     * at the randomly generated index.
     * 
     * Currently, I am using the Collections.shuffle() method instead.
     */
    public void shuffle() {
        /*
         * for (int i = 0; i < 52; i++) { int randomIndex = (int) (Math.random()
         * * 51); Card temp = cards.get(randomIndex); cards.set(i,
         * cards.get(randomIndex)); cards.set(randomIndex, temp); }
         */
        Collections.shuffle(this.cards);
    }

    /**
     * Displays all cards in the deck object's array list of cards.
     */
    public void displayDeck() {
        for (Card c : cards) {
            c.displayCard();
        }
    }

    /**
     * Gets the array list of cards. Allows for the private array list to be
     * referenced.
     * 
     * @return an array list of cards that represents the deck.
     */
    public ArrayList<Card> getCards() {
        return cards;
    }

    /**
     * Clone method can be used to create a copy of a deck. Potential uses
     * include the ability of making two players play the same hand or a replay
     * system of sorts.
     */
    @Override
    public Deck clone() {
        return new Deck(this);
    }

    /**
     * Displays the remaining cards in the deck. It is ineffective to list every
     * card in the deck.
     */
    @Override
    public String toString() {
        if (remainingCards == 1) {
            return String.format("This deck has %s card left.", remainingCards);
        }
        return String.format("This deck has %s cards left.", remainingCards);
    }
}
