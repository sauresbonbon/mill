package Model;

import java.util.List;

public interface IGame {
    void startNewGame();
    boolean isEmpty(int point);
    boolean isValidMove(Player player,Player playerOther, int from, int to);
    List<Integer> allValidMoves(FieldState[] grid, int from);
    void updateField();
}
