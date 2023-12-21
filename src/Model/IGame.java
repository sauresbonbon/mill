package Model;

import java.util.List;

public interface IGame {
    void startNewGame();
    boolean isEmpty(int point);
    boolean isValidMove(int from, int to);
    List<Integer> allValidMoves(FieldState[] grid, int from);
    void updateField();
    void makeMove(Player currentPlayer, int from, int to);
    void setPieces(Player currentPlayer, int point);
    void checkIfMill();
    void removePiece(Player otherPlayer, int point);
    void checkWinStatus(Player otherPlayer);
    void switchPlayer(Player currentPlayer);
}
