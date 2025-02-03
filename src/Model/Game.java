package Model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 *  The Game class represents the logic of a Mill (Nine Men's Morris) game.
 *  It includes methods for setting up the game, making moves, validating moves, handling player turns and more.
 *
 * <p>
 * The class provides functionalities for starting a new game, switching players, swapping colors,
 * toggling computer control, updating playing states, checking if a point on the board is empty,
 * determining if the game is over, placing pieces, making moves and more.
 * </p>
 *
 * @author Liv Nitsche
 * @version 1.0
 */
public class Game implements IGame {

    private Player player1, player2, currentPlayer;
    private int moveCounter = 0;
    private final FieldState[] grid = new FieldState[24];

    /*-----------------------------------------------------------------------------------------------*/

    /**
     * Starts a new game by initializing the game board and players.
     * In addition, it sets the computer state of player 2.
     */
    public void startNewGame(boolean computer) {
        Arrays.fill(grid, FieldState.EMPTY);
        player1 = currentPlayer = new Player(FieldState.WHITE, 9, 0);
        player2 = new Player(FieldState.BLACK, 9, 0);
        player2.setComputerState(computer);
    }

    /**
     * Switches the current player to the other player and adds 1 to the move counter.
     */
    public void switchPlayer() {
        moveCounter++;
        currentPlayer = (getCurrentPlayer() == player1) ? player2 : player1;
    }

    /**
     * Swaps the colors of the two players in the game.
     * It exchanges the color assignments between Player 1 and Player 2.
     */
    public void swapColors() { getPlayer1().swapColors(getPlayer2()); }

    /**
     * Toggles the computer control state of the player.
     */
    public void switchComputerState() {
        player2.setComputerState(!player2.getComputerState());
    }

    /**
     * Changes the playing state of the individual player.
     * If the player is in the SETTING_PHASE and has placed all pieces, it switches to the MOVING_PHASE.
     * If the player is in the MOVING_PHASE and has only 3 pieces remaining, it switches to the JUMPING_PHASE.
     *
     * @param player The player whose playing status wants to be changed.
     */
    public void updatePlayingState(Player player) {
        switch (player.getPlayingState()) {
            case SETTING_PHASE -> {
                if (player.getPiecesCount() == 0)
                    player.setPlayingState(PlayingState.MOVING_PHASE);
            }
            case MOVING_PHASE -> {
                if (player.getPiecesOnBoard() == 3)
                    player.setPlayingState(PlayingState.JUMPING_PHASE);
            }
        }
    }

    /**
     * Checks if the point on the board is empty.
     *
     * @param point The point which is being checked.
     * @return True if the point is empty, false otherwise.
     */
    public boolean isEmpty(int point) {
        return grid[point] == FieldState.EMPTY;
    }

    /**
     * Checks if the game is over.
     * <p>
     * The game is considered over if either the current player has fewer than 3 pieces on
     * the board, or the current player has more than 3 pieces on the board and has no valid moves.
     * Additionally, the game is cannot become game over during the SETTING_PHASE.
     * </p>
     *
     * @return True if the game is over, false otherwise.
     */
    public boolean isGameOver() {
        return (getCurrentPlayer().getPiecesOnBoard() < 3 || (getCurrentPlayer().getPiecesOnBoard() > 3 && !hasValidMoves()))
                && getCurrentPlayer().getPlayingState() != PlayingState.SETTING_PHASE;
    }

    /*----------------------------------------move methods-----------------------------------------------------*/
    /**
     * Places a game piece on the board at the specified point for the current player.
     *
     *<p>
     * This method attempts to place a game piece on the target point if the current player has
     * remaining pieces to place and the target point is empty.
     *</p>
     *
     * @param targetPoint The index of the point where the piece should be placed.
     * @return True if the piece was successfully placed, false otherwise.
     */
    public boolean placePiece(int targetPoint) {
        if (!(getCurrentPlayer().getPiecesCount() > 0 && isEmpty(targetPoint))) return false;
        grid[targetPoint] = getCurrentPlayer().getColor();
        getCurrentPlayer().setPiecesCount(getCurrentPlayer().getPiecesCount() - 1);
        getCurrentPlayer().setPiecesOnBoard(getCurrentPlayer().getPiecesOnBoard() + 1);
        return true;
    }

    /**
     * Method to move a piece to an adjacent empty point .
     *
     *<p>
     * This method checks if the move from the specified source point to the target point is valid,
     * and if so, it updates the game grid accordingly and removes any mills associated with the source point.
     *</p>
     *
     * @param from The index of the source point.
     * @param to   The index of the destination point.
     * @return True if the move is successfully executed, false otherwise.
     */
    public boolean makeMove(int from, int to) {
        if(!isValidMove(from,to)) return false;
        grid[from] = FieldState.EMPTY;
        grid[to] = getCurrentPlayer().getColor();
        getCurrentPlayer().getMillList().removeIf(mill -> mill.contains("|" + from + "|"));
        return true;
    }

    /**
     * Method to jump pieces as soon as the player is in the JUMPING_PHASE.
     *
     * <p>
     * This method checks if the target point is empty and, if so, jumps the game piece from the
     * specified source point to the target point.
     * It also removes any mills associated with the source point.
     * </p>
     *
     * @param from The index of the source point.
     * @param to   The index of the destination point.
     * @return True if the jump is successfully executed, false otherwise.
     */
    public boolean jumpPiece(int from, int to) {
        if (!isEmpty(to)) return false;
        grid[from] = FieldState.EMPTY;
        grid[to] = getCurrentPlayer().getColor();
        getCurrentPlayer().getMillList().removeIf(mill -> mill.contains("|" + from + "|"));
        return true;
    }

    /**
     * Executes a random move based on the current playing phase of the computer player.
     *
     * <p>
     * This method generates a random move depending on the current playing phase:
     * - In the setting phase, it places a game piece on a randomly chosen empty point.
     * - In the moving phase, it moves a game piece from a random source point to a random valid target point.
     * - In the jumping phase, it jumps a game piece from a random source point to a random valid empty target point.
     * </p>
     */
    public void randomMove() {
        switch(getCurrentPlayer().getPlayingState()) {
            case SETTING_PHASE -> {
                int randomPoint = getEmptyPoints().get((new Random()).nextInt(getEmptyPoints().size()));

                placePiece(randomPoint);
            }
            case MOVING_PHASE -> {
                int randomFrom = getCurrentPlayerPieces().get((new Random()).nextInt(getCurrentPlayerPieces().size()));
                int randomTo = allValidMoves(randomFrom).get((new Random()).nextInt(allValidMoves(randomFrom).size()));

                makeMove(randomFrom,randomTo);
            }
            case JUMPING_PHASE -> {
                int randomFrom = getCurrentPlayerPieces().get((new Random()).nextInt(getCurrentPlayerPieces().size()));
                int randomTo = allValidMoves(randomFrom).get((new Random()).nextInt(allValidMoves(randomFrom).size()));

                jumpPiece(randomFrom,randomTo);
            }
        }
        if(isMill()) removePiece(getRemovablePieces().get((new Random()).nextInt(getRemovablePieces().size())));
    }

    /*------------------------------------------------mill--------------------------------------------------*/
    /**
     * Searches for a mill formed by the player's pieces on the game board.
     *
     * <p>
     * A mill is a straight line of three pieces belonging to the same player along the board's edges.
     * The method checks all possible mill configurations and returns the first mill found by the specified player.
     * </p>
     *
     * @param player The player for whom mills are being searched.
     * @return A string of the mill if found, or null if no mill is found.
     */
    public String findMill(Player player) {
        List<List<Integer>> possibleMills = Arrays.asList(
                Arrays.asList(0, 1, 2), Arrays.asList(2, 3, 4), Arrays.asList(4, 5, 6), Arrays.asList(0, 7, 6),
                Arrays.asList(8, 9, 10), Arrays.asList(10, 11, 12), Arrays.asList(12, 13, 14), Arrays.asList(14, 15, 8),
                Arrays.asList(16, 17, 18), Arrays.asList(18, 19, 20), Arrays.asList(20, 21, 22), Arrays.asList(22, 23, 16),
                Arrays.asList(1, 9, 17), Arrays.asList(3, 11, 19), Arrays.asList(5, 13, 21), Arrays.asList(7, 15, 23)
        );

        return possibleMills.stream()
                .filter(mill -> grid[mill.getFirst()] == grid[mill.get(1)] && grid[mill.getFirst()] == grid[mill.get(2)]
                        && grid[mill.getFirst()] == player.getColor())
                .map(mill -> "|" + mill.getFirst() + "|" + mill.get(1) + "|" + mill.get(2) + "|")
                .filter(newMill -> !player.getMillList().contains(newMill))
                .findFirst()
                .orElse(null);
    }

    /**
     * Checks if the current player has formed a mill on the game grid by calling the findMill method.
     *
     * @return True if the current player has formed a mill, false otherwise.
     */
    public boolean isMill() {
        return findMill(getCurrentPlayer()) != null;
    }

    /*----------------------------------------remove methods------------------------------------------------------*/
    /**
     * Removes a piece from the point on the board.
     *
     * <p>
     * This method checks if the specified point is removable, if the player is allowed to remove and
     * if there are removable pieces available.
     * If conditions are met, it removes the piece and updates player states.
     * </p>
     *
     * @param point The index of the point.
     * @return True if the piece is successfully removed, false otherwise.
     */
    public boolean removePiece(int point) {
        if (!(isRemovable(point) && canRemove() && !getRemovablePieces().isEmpty())) return false;
        grid[point] = FieldState.EMPTY;
        getOtherPlayer().setPiecesOnBoard(getOtherPlayer().getPiecesOnBoard() - 1);
        getCurrentPlayer().setPiecesRemoved(1);
        updatePlayingState(getOtherPlayer());
        return true;
    }

    private boolean isRemovable(int point) {
        return (getOtherPlayer().getPlayingState() == PlayingState.JUMPING_PHASE && getGridAtPoint(point) == getOtherPlayer().getColor()) ||
                (getOtherPlayer().getColor() == grid[point] && getOtherPlayer().getMillList()
                        .stream().noneMatch(mill -> mill.contains("|" + point + "|")));
    }

    private boolean canRemove() {
        if (findMill(getCurrentPlayer()) == null) return false;
        getCurrentPlayer().getMillList().add(findMill(getCurrentPlayer()));
        return true;
    }


    /*------------------------------------------valid moves----------------------------------------------*/

    /**
     * Checks if a move from one point to another on the board is valid.
     *
     * <p>
     * Checks whether the destination point is empty and whether a move to it is in the validMoves list.
     * </p>
     *
     * @param from The index of the source point.
     * @param to   The index of the destination point.
     * @return True if the move is valid, false otherwise.
     */
    public boolean isValidMove(int from, int to) {
        return isEmpty(to) && allValidMoves(from).contains(to);
    }

    /**
     * Checks if the current player has valid moves.
     *
     * <p>
     * It iterates through all points on the board occupied by the current player's pieces
     * and checks if there are any valid moves available.
     * </p>
     *
     * @return True if the current player has valid moves, false otherwise.
     */
    public boolean hasValidMoves() {
        return IntStream.range(0, getGrid().length)
                .anyMatch(i -> getGridAtPoint(i) == getCurrentPlayer().getColor() && !allValidMoves(i).isEmpty());
    }

    /**
     * Determines all valid moves for the current player from a given board position.
     * <p>
     * In the JUMPING_PHASE, all empty points on the board are considered valid moves.
     * In other phases, the valid moves are determined based on the specific rules for the provided position.
     * </p>
     *
     * @param from The index of the source point on the board.
     * @return A list of integers representing valid moves from the source point.
     */
    public List<Integer> allValidMoves(int from) {
        List<Integer> validMoves = new ArrayList<>();

        if (getCurrentPlayer().getPlayingState() == PlayingState.JUMPING_PHASE) {
            for (int i = 0; i < grid.length; i++) {
                if (isEmpty(i) && !validMoves.contains(i)) validMoves.add(i);
            }
        } else {
            if (from == 0 || from == 8 || from == 16) {
                if (grid[from + 1] == FieldState.EMPTY) validMoves.add(from + 1);
                if (grid[from + 7] == FieldState.EMPTY) validMoves.add(from + 7);
            }

            if (from == 1 || from == 3 || from == 5 || from == 7 || from == 9 || from == 11 || from == 13 || from == 15) {
                if (grid[from + 8] == FieldState.EMPTY) validMoves.add(from + 8);
            }

            if (from == 9 || from == 11 || from == 13 || from == 15 || from == 17 || from == 19 || from == 21 || from == 23) {
                if (grid[from - 8] == FieldState.EMPTY) validMoves.add(from - 8);
            }

            if (from == 7 || from == 15 || from == 23) {
                if (grid[from - 7] == FieldState.EMPTY) validMoves.add(from - 7);
                if (grid[from - 1] == FieldState.EMPTY) validMoves.add(from - 1);
            }

            if (from == 1 || from == 2 || from == 3 || from == 4 || from == 5 || from == 6 || from == 9 || from == 10 || from == 11 || from == 12 || from == 13 || from == 14 || from == 17 || from == 18 || from == 19 || from == 20 || from == 21 || from == 22) {
                if (grid[from + 1] == FieldState.EMPTY) validMoves.add(from + 1);
                if (grid[from - 1] == FieldState.EMPTY) validMoves.add(from - 1);
            }
        }
        return validMoves;
    }


    /*-------------------------------------getter und setter--------------------------------------------*/

    /**
     * Returns a list of indices representing removable game pieces on the current game grid.
     *
     * @return A list of removable Pieces.
     */
    public List<Integer> getRemovablePieces() {
        return IntStream.range(0, grid.length)
                .filter(this::isRemovable)
                .boxed().collect(Collectors.toList());
    }
    private List<Integer> getEmptyPoints() {
        return IntStream.range(0, grid.length)
                .filter(this::isEmpty)
                .boxed().collect(Collectors.toList());
    }
    private List<Integer> getCurrentPlayerPieces() {
        return IntStream.range(0, grid.length)
                .filter(i -> grid[i] == getCurrentPlayer().getColor() && !allValidMoves(i).isEmpty())
                .boxed().collect(Collectors.toList());
    }

    /**
     * Return the current state of the game grid as an array of {@link FieldState}.
     *
     * @return An array representing the current state of the game grid.
     */
    public FieldState[] getGrid() {return grid;}

    /**
     * Returns the game grid at a specific point.
     *
     * @param point The index of the point on the grid.
     * @return The {@link FieldState} of the grid at the specific point.
     */
    public FieldState getGridAtPoint(int point) {return grid[point];}

    /**
     * Returns the current player.
     *
     * @return The current player.
     */
    public Player getCurrentPlayer() {return currentPlayer;}

    /**
     * Return the other player who is not currently taking their turn.
     *
     * @return The other player.
     */
    public Player getOtherPlayer() {return (currentPlayer == player1) ? player2 : player1;}

    /**
     * Returns the first player.
     *
     * @return The first player.
     */
    public Player getPlayer1() {return player1;}

    /**
     * Returns the second player.
     *
     * @return The second player.
     */
    public Player getPlayer2() {return player2;}
    /**
     *  Returns the current move counter value.
     *
     * @return The current move counter value.
     */
    public int getMoveCounter() {return moveCounter;}

    /*------------------------------------------toString---------------------------------------------*/

    /**
     * Returns a string representation of the game board. The corresponding and current field states are entered
     * in the index of the grid.
     * It also indicates which player's turn it is.
     *
     * @return A formatted string representing the current game board with field states.
     */
    @Override
    public String toString() {
        String gridState = "It´s " + currentPlayer.getColor() + "'s turn" + "\n" +
                """
                    (0)------------------------(1)--------------------------(2)
                      |                            |                              |
                      |     (8)----------------(9)----------------(10)      |
                      |       |                    |                     |        |
                      |       |      (16)------(17)------(18)      |        |
                      |       |        |                        |        |        |
                      |       |        |                        |        |        |
                      |       |        |                        |        |        |
                      |       |        |                        |        |        |
                    (7)---(15)---(23)                   (19)---(11)---(3)
                      |       |        |                        |        |        |
                      |       |        |                        |        |        |
                      |       |        |                        |        |        |
                      |       |        |                        |        |        |
                      |       |      (22)------(21)------(20)      |        |
                      |       |                    |                     |        |
                      |     (14)---------------(13)---------------(12)      |
                      |                            |                              |
                    (6)------------------------(5)--------------------------(4)""";
        for (int i = 0; i < grid.length; i++) {
            gridState = gridState.replaceAll("\\(" + i + "\\)", grid[i] == FieldState.EMPTY ? "(F/" + i + ")"
                    : grid[i] == FieldState.BLACK ? "(B/" + i + ")" : "(W/" + i + ")");
        }
        return gridState;
    }
}