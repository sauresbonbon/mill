package Controller;
import Model.*;

/**
 * Interface defining the game controller.
 *<p>
 * This interface outlines the methods that a class representing a game controller
 * should implement. It provides methods for managing game flow, accessing game state,
 * retrieving player information, handling user input, and interacting with the game model.
 *</p>
 */
public interface IController {

    /**
     * Renders the next frame based on the current game state.
     */
    void nextFrame();
    /**
     * Initiates a new game with the specified configuration.
     *
     * @param computer The status whether a computer player is activated or not.
     */
    void startNewGame(boolean computer);
    /**
     * Handles the interaction with a game piece at the specified position.
     *
     * @param i The position of the game piece on the game board.
     */
    void handlePiece(int i);
    /**
     * Initiates the timers.
     */
    void startTimer();
    /**
     * Toggles the computer player state.
     */
    void switchComputerState();
    /**
     * Swaps the colors of the players.
     */
    void swapColors();
    /**
     * Sets the game state to the specified state.
     *
     * @param state The new game state to be set.
     */
    void setGameState(GameState state);

    /**
     * Retrieves the color representation for a specific point on the game grid.
     *
     * @param point The index representing the position on the game grid.
     * @return An integer representing the color value for the specified grid point.
     */
    int getColorForGrid(int point);
    /**
     * Retrieves the current move counter value from the game.
     *
     * @return The current move counter value.
     */
    int getMoveCounter();


    /**
     * Retrieves the current game state.
     *
     * @return The current game state.
     */
    GameState getGameState();


    /**
     * Retrieves the color of Player 1.
     *
     * @return The color of Player 1.
     */
    FieldState getPlayer1Color();
    /**
     * Retrieves the color of Player 2.
     *
     * @return The color of Player 2.
     */
    FieldState getPlayer2Color();
    /**
     * Retrieves the color of the current player.
     *
     * @return The color of the current player.
     */
    FieldState getCurrentPlayer();


    /**
     * Retrieves the remaining time for Player 1's turn.
     *
     * @return The remaining time for Player 1's turn.
     */
    String getTimeLeftPlayer1();
    /**
     * Retrieves the remaining time for Player 2's turn.
     *
     * @return The remaining time for Player 2's turn.
     */
    String getTimeLeftPlayer2();
    /**
     * Retrieves a string representation of the current players color.
     *
     * @return The color of the current player ("Weiß" for white or "Schwarz" for black).
     */
    String getCurrentPlayerString();
    /**
     * Retrieves a string representation of the other players color.
     *
     * @return The color of the other player ("Weiß" for white or "Schwarz" for black).
     */
    String getOtherPlayerString();
    /**
     * Retrieves a string representation of player 1 color.
     *
     * @return The color of player 1 ("Weiß" for white or "Schwarz" for black).
     */
    String getPlayer1ColorString();
    /**
     * Retrieves a string representation of the player 2 color.
     *
     * @return The color of player 2("Weiß" for white or "Schwarz" for black).
     */
    String getPlayer2ColorString();


    /**
     * Retrieves the count of pieces owned by Player 1.
     *
     * @return The count of pieces owned by Player 1.
     */
    int getPiecesCountPlayer1();
    /**
     * Retrieves the count of pieces owned by Player 2.
     *
     * @return The count of pieces owned by Player 2.
     */
    int getPiecesCountPlayer2();
    /**
     * Retrieves the amount of pieces removed by Player 1.
     *
     * @return The amount of pieces removed by Player 1.
     */
    int getPlayer1PiecesRemoved();
    /**
     * Retrieves the amount of pieces removed by Player 2.
     *
     * @return The amount of pieces removed by Player 2.
     */
    int getPlayer2PiecesRemoved();


    /**
     * Retrieves the computer state of player 2.
     *
     * @return true if player 2 is controlled by the computer, false if player 2 is a human player.
     */
    boolean getComputerState();
    /**
     * Checks if Player 1 is assigned the color white.
     *
     * @return true if Player 1 has the color white, false otherwise.
     */
    boolean isPlayer1White();
}
