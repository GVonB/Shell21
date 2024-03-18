import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * A player object is the core of a blackjack game. Responsible for holding the
 * playHand() method, these objects contain almost all of the information needed
 * to run a run a hand.
 * 
 * @author gunnarvonbergen
 * 
 */
public class Player implements Scoreboard, Cloneable, Serializable {

    // Instance variables

    private static final long serialVersionUID = 1L;

    private boolean busted = false;

    private boolean isPush = false;

    private final String NAME;

    private int wins;

    private int losses;

    private int blackjacks;

    // A push does not count as a game played, simply a continued hand.
    private int gamesPlayed;

    private String playerRank;

    private double winLossRatio;

    private ArrayList<Card> hand = new ArrayList<Card>();

    /**
     * A player object hold important stats that track a player's data across
     * multiple hands and games.
     * 
     * @param name       is the player's name.
     * @param wins       is the number of wins the player has had.
     * @param losses     is the number of losses the player has had.
     * @param blackjacks is the number of blackjacks the player has had.
     */
    public Player(String name, int wins, int losses, int blackjacks) {
        this.NAME = name;
        this.wins = wins;
        this.losses = losses;
        this.blackjacks = blackjacks;
        this.gamesPlayed = wins + losses;
        this.winLossRatio = ((double) wins) / losses;
        setPlayerRank();
    }

    /**
     * This is a copy constructor that allows the option for cloning a player.
     * 
     * @param other is the player object that is intended to be copied.
     */
    public Player(Player other) {
        // Copies instance variables, then calls the player rank calculation.
        this.NAME = other.NAME;
        this.wins = other.wins;
        this.losses = other.losses;
        this.blackjacks = other.blackjacks;
        this.gamesPlayed = other.gamesPlayed;
        this.winLossRatio = other.winLossRatio;
        this.setPlayerRank();

        // Creates a deep copy of the hand using the card object's clone method.
        this.hand = new ArrayList<Card>();
        for (Card card : other.hand) {
            this.hand.add(card.clone());
        }
    }

    /**
     * Displays important stats for the player. Including their name, wins,
     * losses, W/L ratio, blackjacks, games played, and player rank.
     */
    @Override
    public void displayStats() {
        setValues(); // Ensures that gamesPlayed and W/L ratio are set.
        System.out.println("+------------------------------+");
        System.out.format("| Stats for %-18s |\n", NAME);
        System.out.println("+--------------+---------------+");
        System.out.format("| %-13s %-14s |\n", "Wins:", wins);
        System.out.format("| %-13s %-14s |\n", "Losses:", losses);
        System.out.format("| %-13s %-14.2f |\n", "W/L Ratio:", winLossRatio);
        System.out.println("+--------------+---------------+");
        System.out.format("| %-13s %-14s |\n", "Blackjacks:", blackjacks);
        System.out.format("| %-13s %-14s |\n", "Games Played:", gamesPlayed);
        System.out.println("+--------------+---------------+");
        System.out.format("| %-13s %-14s |\n", "Player Rank: ", playerRank);
        System.out.println("+------------------------------+");
    }

    /**
     * Player rank is set based on performance (win loss ratio) but only if the
     * player has played sufficient games.
     */
    public void setPlayerRank() {
        if (gamesPlayed >= 20) {
            if (winLossRatio >= 1) {
                playerRank = "S - Amazing!!!";
            } else if (winLossRatio >= .8) {
                playerRank = "A - Great!";
            } else if (winLossRatio >= .6) {
                playerRank = "B - Good :)";
            } else if (winLossRatio >= .4) {
                playerRank = "C - Not Great :/";
            } else {
                playerRank = "D - Bad :(";
            }
        } else {
            playerRank = "N/A Play More!";
        }
    }

    /**
     * This void method is responsible for calling all methods relating to a
     * hand playing out. The functions will loop until a player wins, loses, or
     * draws against the dealer.
     * 
     * @param deck   the deck object being played with containing the list of
     *               cards.
     * @param dealer the dealer being played against.
     */
    public void playHand(Deck deck, Dealer dealer) {
        System.out.println();
        drawCard(deck);

        while (true) {
            drawCard(deck);
            System.out.println();

            if (calculateHandValue() == 21) {
                blackjackCheck(); // Prints blackjack! if hand size of 2.
                addWin();
                addBlackjack();
                break;
            } else if (calculateHandValue() > 21) {
                printBust();
                addLoss();
                dealer.addWin();
                break;
            }

            callDealer(deck, dealer);

            if (!decideToHit()) {
                dealer.playHand(deck, this);
                checkStand(dealer);
                break;
            }
        }
    }

    /**
     * Calls on the dealer to draw a card. This call on setFirstCardDealt() is
     * used to make sure that the dealer doesn't tell the player the cards
     * before the player does.
     * 
     * @param deck   the deck object containing the array list of cards for the
     *               game.
     * @param dealer the dealer object being played against.
     */
    public void callDealer(Deck deck, Dealer dealer) {
        dealer.drawCard(deck);
        dealer.setFirstCardDealt(true);
    }

    /**
     * Prompts the user to hit or stand and prints their existing hand value.
     */
    public void printHandQuery() {
        System.out.println("Your hand total is " + calculateHandValue());
        System.out.print("Would you like to hit or stand (H/S)? ");
    }

    /**
     * Draws a card from the passed deck object. Adds the drawn card to the
     * player's hand.
     * 
     * @param deck the deck object containing the current game's cards.
     */
    public void drawCard(Deck deck) {
        hand.add(deck.draw());
        System.out.println("Drew Card: ");
        hand.get(hand.size() - 1).displayCard();

    }

    /**
     * Handles calculations when standing, compares values of hands to the
     * passed deal object.
     * 
     * @param dealer the dealer containing the hand to compare to.
     */
    public void checkStand(Dealer dealer) {
        System.out.println(); // Creates a gap in console for readability.
        for (int i = 1; i < dealer.getHand().size(); i++) {
            System.out.println("The dealer drew a(n) "
                    + dealer.getHand().get(i).toString());
        }
        if (calculateHandValue() > dealer.calculateHandValue()) {
            System.out.println("Your hand is higher value! You Win!");
            addWin();
            dealer.addLoss();
        } else if (!dealer.getBusted()
                && (calculateHandValue() < dealer.calculateHandValue())) {
            System.out
                    .printf("The dealer's hand total of %d is higher value! Dealer Wins!\n",
                            dealer.calculateHandValue());
            addLoss();
            dealer.addWin();
        } else if (dealer.getBusted()) {
            System.out.println("Dealer busted! You Win!");
        } else {
            System.out.println("Tie! It's a push!");
        }
    }

    /**
     * Prompts the user to hit or stand and returns a boolean value representing
     * their choice.
     * 
     * @return a boolean value representing whether the player wants to hit or
     *         not.
     */
    public boolean decideToHit() {
        Scanner hitIn = new Scanner(System.in);
        printHandQuery();
        while (true) {
            String userInput = hitIn.next();
            if (userInput.equalsIgnoreCase("S")) {
                return false;
            } else if (userInput.equalsIgnoreCase("H")) {
                return true;
            } else {
                System.out.println(
                        "Enter a valid choice \"H\" to hit or \"S\" to stand.");
            }
        }
    }

    /**
     * Calculates the player's current hand value. Calls to manipulate ace
     * values if the player would've busted.
     * 
     * @return the numerical value of a hand.
     */
    public int calculateHandValue() {
        int pointValue = 0;
        int numAces = 0;
        for (Card card : hand) {
            if (card.isAce()) {
                pointValue += 11;
                numAces++;
            } else {
                pointValue += card.getValue();
            }
        }
        for (int i = 0; i < numAces; i++) {
            if (pointValue > 21) {
                // Account for the fact that 11 was added earlier.
                pointValue -= 10;
            }
        }
        return pointValue;
    }

    /**
     * Sets important values that need to be updated each loop.
     */
    public void setValues() {
        winLossRatio = (double) wins / losses;
        gamesPlayed = wins + losses;
    }

    /**
     * Provides access to the hand array list.
     * 
     * @return the player's array list of cards.
     */
    public ArrayList<Card> getHand() {
        return hand;
    }

    /**
     * Returns the player's win loss ratio.
     * 
     * @return the winLossRatio as a double.
     */
    public double getWinLoss() {
        return winLossRatio;
    }

    /**
     * Returns the letter representation of the player's rank.
     * 
     * @see displayStats() for a detailed player description.
     * @return a char representing the player's rank.
     */
    public String getRank() {
        // Returns "N/A" if not enough games (hands) have been played.
        if (playerRank.charAt(0) == 'N') {
            return "Unranked";
        }
        return playerRank.substring(0, 1);
    }

    /**
     * Adds a win to the player's stats.
     */
    @Override
    public void addWin() {
        wins++;
    }

    /**
     * Adds a loss to the player's stats.
     */
    @Override
    public void addLoss() {
        losses++;
    }

    /**
     * Returns the total number of blackjacks a player has had.
     * 
     * @return an integer representing the number of blackjack a player has had.
     */
    @Override
    public int getBlackjacks() {
        return blackjacks;
    }

    /**
     * Adds a blackjack to the player's stats.
     */
    public void addBlackjack() {
        blackjacks++;
    }

    /**
     * Returns the total number of games (hands) a player has played.
     * 
     * @return an integer representing the number of games (hands) a player has
     *         played.
     */
    @Override
    public int getGamesPlayed() {
        return gamesPlayed;
    }

    /**
     * Returns the total number of wins a player has.
     * 
     * @return an integer representing the number of wins a player has.
     */
    @Override
    public int getWins() {
        return wins;
    }

    /**
     * Returns the total number of losses a player has.
     * 
     * @return an integer representing the number of losses a player has.
     */
    @Override
    public int getLosses() {
        return losses;
    }

    /**
     * Returns the name of the player.
     * 
     * @return a string representing the name of the player.
     */
    public String getName() {
        return NAME;
    }

    /**
     * Clone method is used to create a deep copy of a player object using the
     * copy constructor.
     * 
     * @return a new Player object that deep copies all attributes.
     */
    @Override
    public Player clone() {
        return new Player(this);
    }

    /**
     * Retrieves the player's busted status.
     * 
     * @return true if the player busted.
     */
    public boolean getBusted() {
        return busted;
    }

    /**
     * Sets the player's busted status.
     * 
     * @param value true if the player has busted.
     */
    public void setBusted(boolean value) {
        busted = value;
    }

    /**
     * Sets whether or not the hand is a push.
     * 
     * @param value true if the hand is a push.
     */
    public void setPush(boolean value) {
        isPush = value;
    }

    /**
     * Checks if the hand is a push.
     * 
     * @return true if the hand is a push.
     */
    public boolean isPush() {
        return isPush;
    }

    /**
     * Checks if the player has achieved a legitimate blackjack (not just 21
     * points).
     */
    public void blackjackCheck() {
        if (getHand().size() == 2) {
            System.out.println("Blackjack! You Win!");
        } else {
            System.out.println("21! You Win!");
        }
    }

    /**
     * Prints a standard message that informs the user of their hand value and
     * the fact that they busted.
     */
    public void printBust() {
        System.out.printf("Busted! Your hand value was %d. You Lose!\n",
                calculateHandValue());
    }

    /**
     * Used to format print statements for players. Used only for printing the
     * players name and rank.
     * 
     * @see displayStats() for a detailed player description.
     */
    @Override
    public String toString() {
        // getRank() is used inside the same class to get just the letter
        // representation of rank. "Unranked" will be used if the player is not
        // yet ranked.
        return String.format("[%s]%s", this.getRank(), NAME);
    }

}
