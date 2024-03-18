/**
 * The scoreboard interface is used to force implementation of methods to other
 * classes that implement it.
 * 
 * @author gunnarvonbergen
 *
 */
public interface Scoreboard {
    /**
     * Gets the name of the relevant object.
     * 
     * @return a string representing the name of the object.
     */
    public String getName();

    /**
     * Displays stats for the relevant object.
     */
    public void displayStats();

    /**
     * Adds a win to the relevant object.
     */
    public void addWin();

    /**
     * Adds a loss to the relevant object.
     */
    public void addLoss();

    /**
     * Retrieves the number of blackjacks.
     * 
     * @return the number of blackjacks as an integer.
     */
    public int getBlackjacks();

    /**
     * Retrieves the number of games player.
     * 
     * @return the game played count as an integer.
     */
    public int getGamesPlayed();

    /**
     * Retrieves the number of wins.
     * 
     * @return the win count as an integer.
     */
    public int getWins();

    /**
     * Retrieves the number of losses.
     * 
     * @return the loss count as an integer.
     */
    public int getLosses();

    /**
     * Sets the player's rank.
     */
    public void setPlayerRank();

}
