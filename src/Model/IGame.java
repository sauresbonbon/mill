package Model;

import java.util.List;

public interface IGame {
    void startNewGame();
    boolean isEmpty(int point);
    boolean isValidMove(int from, int to);
    List<Integer> allValidMoves(int from);
    void makeMove(int from, int to);
    boolean setPieces(int point);
    boolean checkIfMill();
    void removePiece(int point);
    void checkWinStatus(Player otherPlayer);
    void switchPlayer();
}
