
/*
 * Name: Gunnar Von Bergen
 * Date: Wed Apr 12
 * Class: CSE 271
 * Instructor: Meisam Amjad
 * Description: This front end class is used to demonstrate the 
 * functionality of the other blackjack related classes I've created. 
 * This class is responsible for importing and saving player data to a 
 * file which can be used to save player's stats.
 */
import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class FrontEnd {

    public static void main(String[] args) {
        playGame();
    }

    /**
     * Starts the general play game loop. Creates an array list of players that
     * will be used to store the data read from the file.
     */
    public static void playGame() {
        ArrayList<Player> playerList = new ArrayList<>();
        // Import players
        Player player = gameSetup(playerList);
        player.setPlayerRank();
        player.displayStats();
        while (innerGameLoop(player, playerList)) {
            player.setPlayerRank();
            player.displayStats();
        }
        gameConclusion(player);
    }

    /**
     * Outputs the ending messages and displays the stats of the passed player.
     * 
     * @param player the player the stats will be displayed for.
     */
    public static void gameConclusion(Player player) {
        System.out.println();
        System.out.println("Here are your stats at the end of this session:");
        player.displayStats();
        System.out.println("Thanks for playing!");
    }

    /**
     * Returns the specified player object from the array list based on matching
     * the desired name. If there is no match, a new player object is created.
     * 
     * @param playerList an array list of player objects that represent the
     *                   total data set.
     * @return a player object that was either retrieved or generated.
     */
    private static Player gameSetup(ArrayList<Player> playerList) {
        String name = requestPlayerName();
        Player player = new Player(name, 0, 0, 0);
        // Import players
        ArrayList<Player> importedPlayers = readPlayers("dataFile.txt");
        for (Player importedPlayer : importedPlayers) {
            if (importedPlayer.getName().equals(name)) {
                player = importedPlayer;
                player.setValues();
            } else {
                playerList.add(importedPlayer);
            }
        }
        return player;
    }

    /**
     * Functions as the looping part of running a blackjack game. Each loop, a
     * new dealer and deck are created and shuffled. In addition, the player is
     * saved and reloaded to ensure up to date data saving.
     * 
     * @param player     The player playing the blackjack game.
     * @param playerList The array list that functions as data storage for all
     *                   players and is written to.
     * @return true if the player would like to loop.
     */
    private static boolean innerGameLoop(Player player,
            ArrayList<Player> playerList) {
        Dealer bjDealer = new Dealer("Dealer", 0, 0, 0);
        Deck bjDeck = new Deck();
        bjDeck.shuffle();
        player.playHand(bjDeck, bjDealer);
        if (!getYNConfirm("Would you like to play again? (Y/N): ")) {
            if (!isInList(player, playerList)) {
                playerList.add(player);
            }
            player.getHand().clear(); // Empty hand before writing data.
            writePlayers("dataFile.txt", playerList);
            return false;
        }
        player.getHand().clear();
        writePlayers("dataFile.txt", playerList);
        return true;
    }

    /**
     * Checks if a player object with a duplicate name is in the given array
     * list.
     * 
     * @param player     the player being checked for.
     * @param playerList the list being looked in.
     * @return true if the player object has a matching named object inside the
     *         array list.
     */
    public static boolean isInList(Player player,
            ArrayList<Player> playerList) {
        for (int i = 0; i < playerList.size(); i++) {
            if (playerList.get(i).getName() == player.getName()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Asks the player to input their name.
     * 
     * @return a String representing the player's name.
     */
    public static String requestPlayerName() {
        Scanner nameInput = new Scanner(System.in);
        System.out.print("Enter your profile/player name: ");
        String name = nameInput.next();
        return name;
    }

    /**
     * Finds the index of a player inside of an array list.
     * 
     * @param name       the commonality being searched for.
     * @param playerList the array list being searched in.
     * @return an integer representing the index of the desired player object.
     */
    public static int indexOfPlayer(String name, ArrayList<Player> playerList) {
        for (int i = 0; i < playerList.size(); i++) {
            if (playerList.get(i).getName() == name) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Saves the passed array list to the specified filename.
     * 
     * @param filename   the location of the data file.
     * @param playerList the array list to be saved.
     */
    public static void writePlayers(String filename,
            ArrayList<Player> playerList) {

        try {
            FileOutputStream fileOut = new FileOutputStream(filename);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(playerList);
            out.flush();
            out.close();
            fileOut.close();

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    /**
     * Imports an array list of players from a specified data file.
     * 
     * @param filename the name of the file to import from.
     * @return an array list of players that have been imported from a data
     *         file.
     */
    public static ArrayList<Player> readPlayers(String filename) {
        ArrayList<Player> importList = new ArrayList<>();

        try {
            FileInputStream readData = new FileInputStream(filename);
            ObjectInputStream readStream = new ObjectInputStream(readData);
            ArrayList<Player> newList = (ArrayList<Player>) (readStream
                    .readObject());
            readStream.close();
            return newList;

        } catch (Exception e) {
            System.out.println(
                    "No existing player found, generating new data...");
            // e.printStackTrace();
        }
        return importList;
    }

    /**
     * Safely confirms a user's choice between yes (y) and no (n).
     * 
     * @param message the message that will be displayed to the user.
     * @return true if the user entered y, false if n.
     */
    public static boolean getYNConfirm(String message) {
        Scanner in = new Scanner(System.in);

        while (true) {
            System.out.print(message);
            String userInput = in.next();
            if (userInput.equalsIgnoreCase("y")) {
                return true;
            } else if (userInput.equalsIgnoreCase("n")) {
                return false;
            }
            System.out.println("Invalid Response!");
        }
    }
}
