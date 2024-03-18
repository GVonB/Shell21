import java.io.Serializable;

/**
 * The dealer class is responsible for handling dealer specific function while
 * still being able to perform those of a player.
 * 
 * @author gunnarvonbergen
 *
 */
public class Dealer extends Player implements Cloneable, Serializable {

    private static final long serialVersionUID = -2632099891304507210L;

    // Can be displayed if needed for testing
    private int gamesDealt = getWins() + getLosses();

    // Used in the player class
    private boolean firstCardDealt = false;

    /**
     * 
     * Creates a new Dealer object with the given name, wins, losses, and
     * blackjacks. Calls on the constructor of the Player class to initialize
     * instance variables.
     * 
     * @param NAME       the name of the dealer
     * @param wins       the number of wins the dealer has
     * @param losses     the number of losses the dealer has
     * @param blackjacks the number of blackjacks the dealer has
     */
    public Dealer(final String NAME, int wins, int losses, int blackjacks) {
        // Calls on player constructor to initialize instance variables.
        super(NAME, wins, losses, blackjacks);
    }

    /**
     * Copy constructor for creating a deep copy of a dealer object.
     * 
     * @param other the dealer object to copy from.
     */
    public Dealer(Dealer other) {
        super(other.getName(), other.getWins(), other.getLosses(),
                other.getBlackjacks());
        for (Card card : other.getHand()) {
            this.getHand().add(card.clone());
        }

    }

    /**
     * Plays a dealers hand against a player.
     * 
     * @param deck   the deck being used to hold the current cards.
     * @param player the player being played against.
     */
    public void playHand(Deck deck, Player player) {

        while (true) {
            if (calculateHandValue() > 21) {
                player.addWin();
                addLoss();
                setBusted(true);
                break;
            } else if (calculateHandValue() >= 17) {
                if (calculateHandValue() == 21) {
                    if (getHand().size() == 2) {
                        System.out.println(
                                "Dealer got a blackjack! Dealer wins!");
                    }
                    /*
                     * else { System.out.println("Dealer got 21! Dealer wins!");
                     * 
                     * Temporarily removing, seems redundant }
                     */
                    addWin();
                    addBlackjack();
                }
                break;
            }
            drawCard(deck);
        }

    }

    /**
     * Draws a card from the passed deck's array list of cards.
     * 
     * @param deck the deck containg the cards to be drawn from.
     */
    @Override
    public void drawCard(Deck deck) {
        this.getHand().add(deck.draw());
        if (getHand().size() == 1) {
            System.out.println("Dealer drew a(n) "
                    + getHand().get(getHand().size() - 1).toString());
            System.out.println();

        }

    }

    /**
     * Loops through all cards a dealer has in their hand and displays each
     * card.
     */
    public void displayHand() {
        for (Card c : this.getHand()) {
            c.displayCard();
        }
    }

    /**
     * Sets the status of whether or not the dealer has already been dealt a
     * card.
     * 
     * @param value true if the first card has been dealt.
     */
    public void setFirstCardDealt(boolean value) {
        firstCardDealt = value;
    }

    /**
     * The number of games the dealer has dealt.
     * 
     * @return the number of games the dealer has dealt as an integer.
     */
    public int getGamesDealt() {
        return gamesDealt;
    }

    /**
     * Calls on the copy constructor to create a deep copy of a dealer object.
     */
    public Dealer clone() {
        return new Dealer(this);
    }

    /**
     * Provides a way to easily print the dealer's simple stats. DisplayStats()
     * in the parent player class is also available for detailed stats.
     */
    @Override
    public String toString() {
        return String.format("The Dealer - %d Games Dealt", getGamesDealt());
    }

}
