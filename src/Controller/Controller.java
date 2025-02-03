package Controller;
import View.IBoard;
import Model.*;

import java.util.List;
import java.util.Stack;

/**
 *  The controller class acts as a mediator between the game model and the graphical user interface,
 *  controls the flow of the mill game, handles user input and updates the display according to the game state.
 *
 *<p>
 *     The controller class implements the control logic for the mill game by interacting with the
 *     associated game model (IGame), the graphical user interface (IBoard) and timers.
 *     It manages the game state, controls the flow of the game, handles user input and updates
 *     the graphical user interface according to the current state of the game.
 *</p>
 */
public class Controller implements IController{
    private IGame game;
    private IBoard board;
    private Timer timerPlayer1;
    private Timer timerPlayer2;
    private GameState gameState;
    private List<Integer> highlightMoves = null;
    private Integer highlightSelected = null;
    private int textStatus = 0;
    private Stack<Integer> s = new Stack<>();
    private boolean newMill = false;

    //------------------------------set interfaces-------------------------------------//

    /**
     * Sets the game board.
     *
     * @param board The implementation of the IBoard interface representing the game board.
     */
    public void setBoard(IBoard board) {
        this.board = board;
    }
    /**
     * Sets the game logic.
     *
     * @param game The implementation of the IGame interface representing the game logic.
     */
    public void setGame(IGame game) {
        this.game = game;
    }

    //--------------------------------------------------------------------------------//

    /**
     * Initiates a new game and resets various game-related parameters.
     *
     * @param computer The status whether a computer player is activated or not.
     */
    public void startNewGame(boolean computer) {
        s.clear();
        textStatus = 0;
        newMill = false;
        highlightMoves = null;
        timerPlayer1 = new Timer();
        timerPlayer2 = new Timer();
        game.startNewGame(computer);
    }

    /**
     * Initiates the timers for player turns and starts the timer for the first player.
     */
    public void startTimer() {
        timerPlayer1.start();
        timerPlayer2.start();
        timerPlayer1.switchRunning();
    }

    /**
     * Swaps the colors of the players in the current game.
     */
    public void swapColors() {
        game.swapColors();
    }

    /**
     * Checks if Player 1 is assigned the color white in the current game.
     *
     * @return Returns true if player 1 has the color white, false otherwise.
     */
    public boolean isPlayer1White() {
        return getPlayer1Color() == FieldState.WHITE;
    }

    /**
     * Toggles the computer player state in the current game.
     * It is used to toggle between enabling and disabling the computer.
     */
    public void switchComputerState() {
        game.switchComputerState();
    }

    /**
     * Handles the interaction with a game piece at the specified position.
     *
     *<p>
     * This method manages the game flow based on the player's interactions with the game
     * board. It covers actions such as placing a new piece, selecting a piece for movement,
     * making moves or jumps, removing pieces, updating game states, and triggering computer
     * moves as needed.
     *</p>
     *
     * @param i The index representing the position of the game piece on the game board.
     */
    public void handlePiece(int i) {
        Player currentPlayer = game.getCurrentPlayer();
        PlayingState currentState = currentPlayer.getPlayingState();

        if (currentState == PlayingState.SETTING_PHASE && game.isEmpty(i) && !newMill) {
            game.placePiece(i);
            if (game.isMill()) newMill = true;
            else {
                game.updatePlayingState(currentPlayer);
                game.switchPlayer();
                swapTimer();

                makeComputerMove();

            }
        } else if ((currentState == PlayingState.MOVING_PHASE || currentState == PlayingState.JUMPING_PHASE)
                && currentPlayer.getColor() == game.getGridAtPoint(i) && !newMill) {

            s.clear();
            s.push(i);
            textStatus = 2;
            highlightSelected = i;
            highlightMoves = game.allValidMoves(i);
        } else if ((currentState == PlayingState.MOVING_PHASE || currentState == PlayingState.JUMPING_PHASE)
                 && s.size() == 1 && game.isEmpty(i)) {
            s.push(i);
            int to = s.pop();
            int from = s.pop();
            highlightMoves = null;
            highlightSelected = null;
            textStatus = 1;

            if (currentState == PlayingState.MOVING_PHASE) {
                if(!game.makeMove(from, to)) {
                    s.clear();
                    return;
                }
            } else {
                if(!game.jumpPiece(from, to)){
                    s.clear();
                    return;
                }
            }

            if (game.isMill()) newMill = true;
            else {
                game.updatePlayingState(currentPlayer);
                swapTimer();
                game.switchPlayer();

                makeComputerMove();
            }
        } else if (newMill && game.removePiece(i)) {
            if(game.getCurrentPlayer().getPlayingState() == PlayingState.SETTING_PHASE) {
                textStatus = 0;
            } else textStatus = 1;
            newMill = false;
            game.updatePlayingState(currentPlayer);
            swapTimer();
            game.switchPlayer();

            makeComputerMove();
        }
        if(game.isGameOver()) setGameState(GameState.END);
    }
    private void swapTimer() {
        timerPlayer1.switchRunning();
        timerPlayer2.switchRunning();
    }
    private void makeComputerMove() {
        if(game.isGameOver()) setGameState(GameState.END);
        else if(game.getPlayer2().getComputerState()) {
            try {
                Thread.sleep( 150);
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
            }

            game.randomMove();

            game.updatePlayingState(game.getCurrentPlayer());
            game.switchPlayer();
        }
    }
    /*-------------------------------------view <-> controller-----------------------------------------*/

    /**
     * Renders the next frame based on the current game state.
     *
     * <p>
     * This method is responsible for determining the appropriate action to take based on
     * the current game state. It switches between rendering the start screen, playing screen,
     * and end screen accordingly. Additionally, it may update screen information during the
     * playing state.
     * </p>
     */
    @Override
    public void nextFrame() {
        switch (getGameState()) {
            case START -> {
                board.drawStartScreen();
            }
            case PLAYING -> {
                board.drawPlayingScreen();
                updateScreenInformation();

            }
            case END -> {
                board.drawEndScreen();
            }
        }
    }

    private void updateScreenInformation() {
        if(highlightMoves != null) {
            board.highlightValidMoves(highlightMoves);
        }
        if(highlightSelected != null) {
            board.highlightSelectedPiece(highlightSelected);
        }
        if(game.getCurrentPlayer().getPlayingState() != PlayingState.SETTING_PHASE && textStatus == 0) {
            textStatus = 1;
        }
        if(newMill && game.getRemovablePieces() != null) {
            textStatus = 3;
            board.highlightRemovablePieces(game.getRemovablePieces());
        }
        board.drawStatus(textStatus);
    }

    /*------------------------------------getter und setter----------------------------------------*/
    /**
     * Returns the current state of the game.
     *
     * @return The current {@link GameState}.
     */
    public GameState getGameState() {
        return gameState;
    }

    /**
     * Sets the current game state.
     *
     * @param state The new {@link GameState} to be set.
     */
    public void setGameState(GameState state) {
        gameState = state;
    }

    /**
     * Returns the current move counter value from the game.
     * @return The current move counter value.
     */
    public int getMoveCounter() {
        return game.getMoveCounter();
    }

    /**
     * Returns the color of player 1 in the current game.
     * @return The color of player 1.
     */
    public FieldState getPlayer1Color() {
        return game.getPlayer1().getColor();
    }
    /**
     * Returns the color of player 2 in the current game.
     * @return The color of player 2.
     */
    public FieldState getPlayer2Color() {
        return game.getPlayer2().getColor();
    }
    /**
     * Returns the color of the current player in the current game.
     * @return The color of the current player.
     */
    public FieldState getCurrentPlayer() {
        return game.getCurrentPlayer().getColor();
    }

    /**
     * Returns the remaining time for player 1.
     * @return The remaining time for Player 1's turn.
     */
    public String getTimeLeftPlayer1() {
        return timerPlayer1.getTimeLeft();
    }
    /**
     * Returns the remaining time for player 2.
     * @return The remaining time for Player 2's turn.
     */
    public String getTimeLeftPlayer2() {
        return timerPlayer2.getTimeLeft();
    }

    /**
     * Returns the current count of pieces owned by player 1.
     * @return The count of pieces by player 1.
     */
    public int getPiecesCountPlayer1() {
        return game.getPlayer1().getPiecesCount();
    }
    /**
     * Returns the current count of pieces owned by player 2.
     * @return The count of pieces by player 2.
     */
    public int getPiecesCountPlayer2() {
        return game.getPlayer2().getPiecesCount();
    }

    /**
     *  Returns the amount of pieces removed for player 1.
     * @return  The amount of pieces removed for player 1.
     */
    public int getPlayer1PiecesRemoved() {
        return game.getPlayer1().getPiecesRemoved();
    }
    /**
     *  Returns the amount of pieces removed for player 2.
     * @return  The amount of pieces removed for player 2.
     */
    public int getPlayer2PiecesRemoved() {
        return game.getPlayer2().getPiecesRemoved();
    }

    /**
     * Retrieves the computer state of player 2 in the current game.
     * @return The computer state of player 2.
     */
    public boolean getComputerState() {
        return game.getPlayer2().getComputerState();
    }

    /**
     * Retrieves the color representation for a specific point on the game grid.
     *
     * @param point The index representing the position on the game grid.
     * @return An integer representing the color value for the specified grid point.
     */
    public int getColorForGrid(int point) {
        if (game.getGridAtPoint(point) == FieldState.WHITE) {
            return 255;
        } else if (game.getGridAtPoint(point) == FieldState.BLACK) {
            return 0;
        } else {
            return 120;
        }
    }
    /**
     * Retrieves a string representation of the color of the other player.
     * @return A string indicating the color of the other player ("Weiß" for white or "Schwarz" for black).
     */
    public String getOtherPlayerString() {
        if (game.getOtherPlayer().getColor() == FieldState.WHITE) {
            return "Weiß";
        } else return "Schwarz";
    }
    /**
     * Retrieves a string representation of the color of the current player.
     * @return A string indicating the color of the current player ("Weiß" for white or "Schwarz" for black).
     */
    public String getCurrentPlayerString() {
        if(game.getCurrentPlayer().getColor() == FieldState.WHITE){
            return "Weiß";
        }else return "Schwarz";
    }
    /**
     * Retrieves a string representation of the color of player 1.
     * @return A string indicating the color of player 1("Weiß" for white or "Schwarz" for black).
     */
    public String getPlayer1ColorString() {
        if(game.getPlayer1().getColor() == FieldState.BLACK) {
            return "Schwarz";
        } else return "Weiß";
    }
    /**
     * Retrieves a string representation of the color of player 2.
     * @return A string indicating the color of player 2("Weiß" for white or "Schwarz" for black).
     */
    public String getPlayer2ColorString() {
        if(game.getPlayer2().getColor() == FieldState.BLACK) {
            return "Schwarz";
        } else return "Weiß";
    }
}