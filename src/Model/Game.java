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
        allValidMoves(grid, 10);

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
            //Points: 0,8,16
            if (from == 0 || from == 8 || from == 16) {
                if(grid[from+1] == FieldState.FREE) {
                    validMoves.add(from+1);
                }
                if(grid[from+7] == FieldState.FREE){
                    validMoves.add(from+7);
                }
            }
            if(from == 7 || from == 15 || from == 23) {
                if(grid[from-7] == FieldState.FREE) {
                    validMoves.add(from-7);
                }
                if(grid[from-1] == FieldState.FREE) {
                    validMoves.add(from-1);
                }
                if(from == 7 && grid[from+8] == FieldState.FREE) {
                    validMoves.add(from+8);
                }
                if((from == 15) && grid[from-8] == FieldState.FREE) {
                    validMoves.add(from-8);
                }
                if((from == 15) && grid[+8] == FieldState.FREE) {
                    validMoves.add(from+8);
                }
                if(from == 23 && grid[from-8] == FieldState.FREE) {
                    validMoves.add(from-8);
                }
            }
            // Points: 2,4,6,10,12,14,18,20,22
            if(from == 2 || from == 4 || from == 6 || from == 10 || from == 12 || from == 14 || from == 18 || from == 20 || from == 22) {
                if(grid[from+1] == FieldState.FREE) {
                    validMoves.add(from+1);
                }
                if(grid[from-1] == FieldState.FREE) {
                    validMoves.add(from-1);
                }
            }

        System.out.println(validMoves);
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