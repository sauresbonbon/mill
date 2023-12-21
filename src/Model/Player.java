package Model;

public class Player {
    int piecesCount = 9;
    int piecesOnBoard = 9;
    FieldState color;
    Player(FieldState color, int piecesCount, int piecesOnBoard) {
        this.color = color;
        this.piecesCount = piecesCount;
        this.piecesOnBoard = piecesOnBoard;
    }

    FieldState getColor() {
        return color;
    }

    int getPiecesCount() {
        return piecesCount;
    }
    int getPiecesOnBoard() {
        return piecesOnBoard;
    }
}
