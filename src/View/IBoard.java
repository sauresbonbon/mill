package View;

import Controller.IController;

import java.util.List;

/**
 * Interface defining the game board representation.
 * <p>
 * This interface outlines the methods that a class representing a game board should
 * implement. It is intended to be used for displaying different screens and states of
 * a game, as well as providing methods to highlight specific elements on the board.
 * </p>
 */
public interface IBoard {
    /**
     * Sets the controller for the game board.
     *
     * @param controller The controller to be associated with the game board.
     */
    void setController(IController controller);


    /**
     * Draws the playing screen, displaying the current state of the game in progress.
     */
    void drawPlayingScreen();
    /**
     * Draws the start screen, indicating the initial phase of the game.
     */
    void drawStartScreen();
    /**
     * Draws the end screen, indicating the completion or termination of the game.
     */
    void drawEndScreen();
    /**
     * Draws the status information on the board.
     *
     * @param status The status code to be displayed on the board.
     */
    void drawStatus(int status);


    /**
     * Highlights the valid moves on the board.
     *
     * @param allValidMoves A list of valid move positions.
     */
    void highlightValidMoves(List<Integer> allValidMoves);
    /**
     * Highlights the removable pieces on the board.
     *
     * @param removablePieces A list of removable piece positions.
     */
    void highlightRemovablePieces(List<Integer> removablePieces);
    /**
     * Highlights the selected piece on the board.
     *
     * @param point The position of the selected piece to be highlighted.
     */
    void highlightSelectedPiece(int point);
}
