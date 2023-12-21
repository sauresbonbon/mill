package Model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Game implements IGame{
    Player player1;
    Player player2;
    private final FieldState[] grid = new FieldState[24];

    public void startNewGame() {
        Arrays.fill(grid, FieldState.FREE);
        Player player1 = new Player(FieldState.WHITE, 9,9);
        Player player2 = new Player(FieldState.BLACK, 9,9);

//        System.out.println(this);
//
//        System.out.println("Placing-Phase");
//        setPieces(player1, 0);
//        setPieces(player2, 4);
//
//        setPieces(player1, 6);
//        setPieces(player2, 2);
//
//        setPieces(player1, 15);
//        setPieces(player2, 16);
//
//        setPieces(player1, 20);
//        setPieces(player2, 3);
//
//        setPieces(player1, 7);
//        setPieces(player2, 1);
//
//        setPieces(player1, 8);
//        setPieces(player2, 23);
//
//        setPieces(player1, 14);
//        setPieces(player2, 9);
//
//        setPieces(player1, 13);
//        setPieces(player2, 17);
//
//        setPieces(player1, 12);
//        setPieces(player2, 22);
//
//        System.out.println(this);
//
//        removePiece(player1, 14);
//        removePiece(player1, 0);
//        removePiece(player1, 7);
//        removePiece(player1, 6);
//        removePiece(player1, 12);
//        removePiece(player1, 13);
//        removePiece(player1, 8);
//        checkIfMill();
//        System.out.println(this);
//
//        System.out.println("Moving-Phase");
//        makeMove(player1, 15,14);
//        System.out.println(this);
    }

    public boolean isEmpty(int point) {
        return grid[point] == FieldState.FREE;
    }

    // Methode braucht aktuellen Player, ausgewählte Position und ausgewählte Zielposition.
    public boolean isValidMove(int from, int to) {
        List<Integer> validMoves = allValidMoves(grid, from);
        if(isEmpty(to)) {
            for (Integer validMove : validMoves) {
                if (to == validMove) {
                    return true;
                }
            }
        }
        return false;
    }

    public List<Integer> allValidMoves(FieldState[] grid, int from) {
        List<Integer> validMoves = new ArrayList<>();
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

    public void makeMove(Player currentPlayer, int from, int to) {
        if(isValidMove(from, to) && currentPlayer.getColor() == grid[from]) {
            grid[from] = FieldState.FREE;
            grid[to] = currentPlayer.getColor();
            checkIfMill();
            switchPlayer(currentPlayer);
        } else {
            System.out.println("The point " + to + " is either already occupied or the move is not valid.");
        }
    }


    public void setPieces(Player currentPlayer, int point) {
        if(currentPlayer.piecesCount > 0) {
            if(isEmpty(point)) {
                grid[point] = currentPlayer.getColor();
            }
            currentPlayer.piecesCount -= 1;
        }
        switchPlayer(currentPlayer);
        checkIfMill();
    }
    public void updateField() {

    }

    public void checkIfMill() {
        for (int i = 0; i < 23; i++) {
            if(grid[i] == grid[i+7] && grid[i] == grid[i+6] && grid[i] != FieldState.FREE) {
                System.out.println("Found ORANGE Mill at:" + i + "-" + (i+7) + "-" + (i+6));
            }
            i += 7;
        }
        for(int i = 0; i < 20; i++) {
            if(i == 6 || i == 14) {
                i+=2;
            } else if(grid[i] == grid[i+1] && grid[i] == grid[i+2] && grid[i] != FieldState.FREE) {
                System.out.println("Found GREEN Mill at:" + i + "-" + (i+1) + "-" + (i+2));
            }
                i++;
        }
        for(int i = 1; i <= 7; i++) {
            if(grid[i] == grid[i+8] && grid[i] == grid[i+16] && grid[i] != FieldState.FREE) {
                System.out.println("Found BLUE Mill at:" + i + "-" + (i+8) + "-" + (i+16));
            }
            i++;
        }
    }
    //CurrentPlayer kann einen beliebigen Stein von otherPlayer entfernen
    public  void removePiece(Player otherPlayer, int point) {
        if(otherPlayer.getColor() == grid[point]) {
            grid[point] = FieldState.FREE;
            otherPlayer.piecesOnBoard -= 1;
            checkWinStatus(otherPlayer);
        } else {
            System.out.println("Error: Either an attempt is made to remove one of your own stones or the point is already empty.");
        }
    }
    public void checkWinStatus(Player otherPlayer) {
        if(otherPlayer.piecesOnBoard < 3) {
            System.out.println(otherPlayer + " is Game Over!");
        }
    }

    public void switchPlayer(Player currentPlayer) {
        currentPlayer = (currentPlayer == player1) ? player2 : player1;
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