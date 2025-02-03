package Model;

import java.util.HashSet;

/**
 * Represents a player in the mill game.
 *<p>
 * Each player has a color, a set of game pieces, and information about the current game state.
 * This class provides methods to manage the player's game pieces, playing state, and interactions
 * with the game board.
 *</p>
 */
public class Player {
    private int piecesCount, piecesOnBoard, piecesRemoved = 0;
    private boolean computer;
    private HashSet<String> millList;
    private PlayingState playingState;
    private FieldState color;

    /**
     * Constructor of a new player with specified attributes.
     *
     * @param color         The color of the player's pieces.
     * @param piecesCount   The number of pieces the player has to place.
     * @param piecesOnBoard The number of pieces the player has on the board.
     */
    public Player(FieldState color, int piecesCount, int piecesOnBoard) {
        this.color = color;
        this.piecesCount = piecesCount;
        this.piecesOnBoard = piecesOnBoard;
        playingState = PlayingState.SETTING_PHASE;
        millList = new HashSet<>();
        computer = false;
    }

    /**
     * Swaps the color of two players.
     *
     * @param otherPlayer The other player with whom to swap colors.
     */
    public void swapColors(Player otherPlayer) {
        FieldState temp = this.color;
        this.color = otherPlayer.getColor();
        otherPlayer.setColor(temp);
    }
    /*-----------------------------------getter und setter----------------------------------------------*/

    /**
     * Gets te set of mills formed by the players board.
     *
     * @return The set of mills.
     */
    public HashSet<String> getMillList() { return millList; }

    /**
     * Returns the current playing phase in which the player is.
     *
     * @return The current playing phase of the player.
     */
    public PlayingState getPlayingState() { return playingState; }

    /**
     * Sets the current playing state in which the player is.
     *
     * @param state The current playing phase of the player.
     */
    public void setPlayingState(PlayingState state) { playingState = state; }
    /**
     * Returns the color({@link FieldState}) of the players pieces.
     *
     * @return The color of the players pieces.
     */
    public FieldState getColor() { return color; }
    /**
     * Sets the color({@link FieldState}) of the players pieces.
     *
     * @param newColor The new color of the players pieces.
     */
    public void setColor(FieldState newColor) { color = newColor; }

    /**
     * Gets the number of pieces the player has to place.
     *
     * @return The number of pieces the player has to place.
     */
    public int getPiecesCount() { return piecesCount; }

    /**
     * Sets the number of pieces the player has to place.
     *
     * @param newPiecesCount The new number of pieces the player has to place.
     */
    public void setPiecesCount(int newPiecesCount) { piecesCount = newPiecesCount; }

    /**
     * Gets the number of pieces the player has on the board.
     *
     * @return The number of pieces the player has on the board.
     */
    public int getPiecesOnBoard() { return piecesOnBoard; }

    /**
     * Sets the number of pieces the player has on the board.
     *
     * @param newPiecesOnBoard The new number of pieces the player has on the board.
     */
    public void setPiecesOnBoard(int newPiecesOnBoard) { piecesOnBoard = newPiecesOnBoard; }

    /**
     * Gets the amount of game pieces removed by the player.
     *
     * @return The amount of game pieces removed by the player.
     */
    public int getPiecesRemoved() { return piecesRemoved; }
    /**
     * Sets the count of game pieces removed by the player.
     *
     * @param i The number of game pieces to be added to the count of pieces removed.
     */
    public void setPiecesRemoved(int i) { piecesRemoved += i; }
    /**
     * Checks if the player is controlled by the computer.
     *
     * @return true if the player is controlled by the computer, false otherwise.
     */
    public boolean getComputerState() { return computer; }
    /**
     * Sets the computer state of the player.
     *
     * @param state true if the player is controlled by the computer, false if a human player.
     */
    public void setComputerState(boolean state) { computer = state; }
}
