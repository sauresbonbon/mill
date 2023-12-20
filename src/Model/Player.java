package Model;

public class Player {
    int piecesCount = 9;
    FieldState color;
    Player(FieldState color, int piecesCount) {
        this.color = color;
        this.piecesCount = piecesCount;
    }

    FieldState getColor() {
        return color;
    }
}
