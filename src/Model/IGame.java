package Model;

import java.util.List;

/**
 * Interface representing the general structure and behavior of the mill game.
 */
public interface IGame {
    /**
     * Starts a new game with the specified initial computer state.
     *
     * @param computer true if the computer is one of the players, false otherwise.
     */
    void startNewGame(boolean computer);
    /**
     * Updates the playing state based on the current player.
     *
     * @param player The player whose turn it is.
     */
    void updatePlayingState(Player player);

    /**
     * Initiates a random move for the current player.
     */
    void randomMove();
    /**
     * Swaps the colors of the players.
     */
    void swapColors();
    /**
     * Toggles the computer player's state.
     */
    void switchComputerState();
    /**
     * Switches the current player.
     */
    void switchPlayer();

    /**
     * Checks if a specific point on the board is empty.
     *
     * @param point The point to check.
     * @return true if the point is empty, false otherwise.
     */
    boolean isEmpty(int point);
    /**
     * Checks if a move from one point to another is valid.
     *
     * @param from The starting point.
     * @param to The destination point.
     * @return true if the move is valid, false otherwise.
     */
    boolean isValidMove(int from, int to);
    /**
     * Checks if a mill is formed on the board.
     *
     * @return true if a mill is formed, false otherwise.
     */
    boolean isMill();
    /**
     * Checks if the game is over.
     *
     * @return true if the game is over, false otherwise.
     */
    boolean isGameOver();
    /**
     * Places a piece on the specified point.
     *
     * @param point The point to place the piece.
     * @return true if the piece is placed successfully, false otherwise.
     */
    boolean placePiece(int point);
    /**
     * Makes a move from one point to another.
     *
     * @param from The starting point.
     * @param to The destination point.
     * @return true if the move is successful, false otherwise.
     */
    boolean makeMove(int from, int to);
    /**
     * Jumps a piece from one point to another.
     *
     * @param from The starting point.
     * @param to The destination point.
     * @return true if the piece is jumped successfully, false otherwise.
     */
    boolean jumpPiece(int from, int to);
    /**
     * Removes a piece from the specified point.
     *
     * @param point The point to remove the piece.
     * @return true if the piece is removed successfully, false otherwise.
     */
    boolean removePiece(int point);

    /**
     * Retrieves a list of all valid moves from a given point.
     *
     * @param from The starting point.
     * @return List of valid moves from the specified point.
     */
    List<Integer> allValidMoves(int from);
    /**
     * Retrieves a list of removable pieces on the board.
     *
     * @return List of removable pieces.
     */
    List<Integer> getRemovablePieces();

    /**
     * Finds a mill formed by the specified player.
     *
     * @param player The player to check for a mill.
     * @return String representation of the mill found by the player.
     */
    String findMill(Player player);

    /**
     * Retrieves the current state of the game board.
     *
     * @return Array representing the current state of the game board.
     */
    FieldState[] getGrid();
    /**
     * Retrieves the field state at a specific point on the board.
     *
     * @param point The point to retrieve the field state.
     * @return Field state at the specified point.
     */
    FieldState getGridAtPoint(int point);

    /**
     * Retrieves the current player.
     *
     * @return The current player.
     */
    Player getCurrentPlayer();
    /**
     * Retrieves the player who is not the current player.
     *
     * @return The player who is not the current player.
     */
    Player getOtherPlayer();
    /**
     * Retrieves the first player.
     *
     * @return The first player.
     */
    Player getPlayer1();
    /**
     * Retrieves the second player.
     *
     * @return The second player.
     */
    Player getPlayer2();

    /**
     * Retrieves the current move counter.
     *
     * @return The current move counter.
     */
    int getMoveCounter();
}
