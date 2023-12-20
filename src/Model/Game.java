package Model;

import java.util.ArrayList;
import java.util.List;

public class Game implements IGame{

    private GameState state;
    private Piece piece;
    Player player1;
    Player player2;
    private final FieldState[] grid = new FieldState[24];

    public void startNewGame() {
        for(int i = 0; i < grid.length; i++) {
            grid[i] = FieldState.FREE;
        }
        Player player1 = new Player(FieldState.WHITE, 9);
        Player player2 = new Player(FieldState.BLACK, 9);

        System.out.println(this);

        for (int i = 0; i < grid.length; i++) {
            System.out.println(allValidMoves(grid, i));
        }
    }

    public boolean isEmpty(int point) {
        if(grid[point] == FieldState.FREE) {
            return true;
        } else return false;
    }

    // Methode braucht aktuellen Player, ausgewählte Position und ausgewählte Zielposition.
    public boolean isValidMove(Player player, Player playerOther, int from, int to) {
        if(isEmpty(to)) {
            return true;
        } else if (grid[to] == playerOther.getColor() || grid[to] == player.getColor()) {
            return false;
        }
        return false;
    }

    public List<Integer> allValidMoves(FieldState[] grid, int from) {
        List<Integer> validMoves = new ArrayList();
            //Points: 0,8,16    Special
            if (from == 0 || from == 8 || from == 16) {
                if(grid[from+1] == FieldState.FREE) {
                    validMoves.add(from+1);
                }
                if(grid[from+7] == FieldState.FREE){
                    validMoves.add(from+7);
                }
            }
            //Points: 1,3,5,7,9,11,13, 15     +8
            if(from == 1 || from == 3 || from == 5 || from == 7 || from == 9 || from == 11 || from ==13 || from == 15) {
                if(grid[from +8] == FieldState.FREE) {
                    validMoves.add(from+8);
                }
            }
            //Points 9,11,13,15,17,19,21,23    -8
            if(from == 9 || from == 11 || from ==13 || from == 15 || from == 17 || from == 19 || from == 21 || from ==23) {
                if(grid[from-8] == FieldState.FREE) {
                    validMoves.add(from-8);
                }
            }
            //Points: 7,15,23      -7
            if(from == 7 || from == 15 || from == 23) {
                if(grid[from-7] == FieldState.FREE) {
                    validMoves.add(from-7);
                }
                if(grid[from-1] == FieldState.FREE) {
                    validMoves.add(from-1);
                }
            }
            // Points: 1,2,3,4,5,6,9,10,11,12,13,14,18,20,22  +-1
            if(from == 1|| from == 2 || from ==3 || from == 4 || from == 5 || from == 6 || from == 9 || from == 10 ||
                    from == 11|| from == 12 || from == 13 || from == 14 ||from == 17 || from == 18 || from == 19 || from == 20 || from == 21 || from == 22) {
                if(grid[from+1] == FieldState.FREE) {
                    validMoves.add(from+1);
                }
                if(grid[from-1] == FieldState.FREE) {
                    validMoves.add(from-1);
                }
            }
            return validMoves;
    }
    public void updateField() {

    }

    @Override
    public String toString() {
        String gridState =  "(0)------------------(1)-------------------(2)\n" +
                            " |                    |                     |\n" +
                            " |    (8)------------(9)-------------(10)    |\n" +
                            " |     |              |               |     |\n" +
                            " |     |    (16)------(17)------(18)     |     |\n" +
                            " |     |     |                  |     |     |\n" +
                            " |     |     |                  |     |     |\n" +
                            "(7)---(15)---(23)                (19)---(11)---(3)\n" +
                            " |     |     |                  |     |     |\n" +
                            " |     |     |                  |     |     |\n" +
                            " |     |    (22)-------(21)------(20)    |     |\n" +
                            " |     |              |               |     |\n" +
                            " |    (14)------------(13)-------------(12)    |\n" +
                            " |                    |                     |\n" +
                            "(6)------------------(5)-------------------(4)";
        for(int i = 0; i < grid.length; i++) {
            gridState = gridState.replaceAll("\\("+i+"\\)", grid[i] == FieldState.FREE ? "(F)"
                    : grid[i] == FieldState.BLACK ? "(B)" : "(W)");
        }
        return gridState;
    }
}