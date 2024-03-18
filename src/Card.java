import java.io.Serializable;

/**
 * Card objects represent individual cards from a Blackjack game. They each have
 * a suit and rank. No deck will be generated with duplicate cards. However,
 * cards objects will still have the functionality to be cloned in cases it can
 * be used in the future.
 * 
 * @author gunnarvonbergen
 *
 */
public class Card implements Cloneable, Serializable {

    private static final long serialVersionUID = 1L;
    /*
     * Represents the suit of the card
     */
    private final String SUIT; // "Hearts", "Spades", "Diamonds", or "Clubs".

    /*
     * Represents the rank or face value of the card
     */
    private final String RANK; // "1-10, Jack, Queen, King, Ace".

    /**
     * Standard constructor for a card object.
     * 
     * @param rank the rank of the card.
     * @param suit the suit of the card.
     */
    public Card(String rank, String suit) {
        this.RANK = rank;
        this.SUIT = suit;
    }

    /**
     * Copy constructor used to clone card objects.
     * 
     * @param other the card being copied.
     */
    public Card(Card other) {
        this.RANK = other.RANK;
        this.SUIT = other.SUIT;
    }

    /**
     * Gets the suit of the card as a string.
     * 
     * @return a string representing the suit of the card.
     */
    public String getSuit() {
        return SUIT;
    }

    /**
     * Returns the rank of the card.
     * 
     * @return the rank of the card as a string.
     */
    public String getRank() {
        return RANK;
    }

    /**
     * This method is uesd when values when integer values are needed for cards
     * and not a string value. This method converts face cards to 10 and aces to
     * 1. The calcuations for aces occur during the scoring/hand value counting
     * process.
     * 
     * @return
     */
    public int getValue() {
        if (isAce()) {
            return 1; // Ace calculations will occur elsewhere
        } else if (RANK.equals("King") || RANK.equals("Queen")
                || RANK.equals("Jack")) {
            return 10;
        } else {
            return Integer.parseInt(RANK);
        }
    }

    /**
     * Converts cards to a single character representing its value; useful for
     * face cards.
     * 
     * @return a string representing the rank of a card in a single character.
     */
    public String convertCardValue() {
        if (RANK.equals("King")) {
            return "K";
        } else if (RANK.equals("Queen")) {
            return "Q";
        } else if (RANK.equals("Jack")) {
            return "J";
        } else if (RANK.equals("Ace")) {
            return "A";
        } else if (RANK.equals("10")) {
            return "T";
        } else {
            return RANK;
        }
    }

    /**
     * Displays a nicely formatted version of a card object.
     */
    public void displayCard() {
        String suitSymbol = "";

        // Set suit symbol based on the suit parameter
        if (SUIT.equals("Spades")) {
            suitSymbol = "♠";
        } else if (SUIT.equals("Hearts")) {
            suitSymbol = "♥";
        } else if (SUIT.equals("Diamonds")) {
            suitSymbol = "♦";
        } else if (SUIT.equals("Clubs")) {
            suitSymbol = "♣";
        }

        // Print the card object
        System.out.println("┌───────┐");
        System.out.println("│ " + convertCardValue() + "     │");
        System.out.println("│       │");
        System.out.println("│   " + suitSymbol + "   │");
        System.out.println("│       │");
        System.out.println("│     " + convertCardValue() + " │");
        System.out.println("└───────┘");
    }

    /**
     * This method is strictly written to simplify the looping check to
     * determine whether the true value of a hand. The array list of cards in a
     * player's hand is looped through and if a card is an ace, special
     * calculations will occur to account for the fact that an ace can be a 1 or
     * 11 depending on what benefits the player.
     * 
     * @return Result will be true if the card is an Ace.
     */
    public boolean isAce() {
        return RANK.equals("Ace");
    }

    /**
     * This method simply creates a printable format that describes a card
     * object.
     * 
     * @return String value representing the card.
     */
    @Override
    public String toString() {
        return RANK + " of " + SUIT;
    }

    /**
     * This method clones a card object using the copy constructor.
     * 
     * @return Card object that is a deep copy of the cloned card.
     */
    @Override
    public Card clone() {
        return new Card(this);
    }
}
