package Model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Game implements IGame{
    Scanner sc = new Scanner(System.in);;
    private Player player1;
    private Player player2;
    Player currentPlayer;

    private final FieldState[] grid = new FieldState[24];

    public void startNewGame() {
        Arrays.fill(grid, FieldState.FREE);
        player1 = new Player(FieldState.WHITE, 9,9);
        player2 = new Player(FieldState.BLACK, 9,9);

        currentPlayer = player1;

        game();
    }
    public void game() {
        //Setting-Phase
        while(currentPlayer.getPiecesCount() > 0) {
            System.out.println(this);
            System.out.println(currentPlayer.getColor() + " setz deinen Stein.");
            int point = sc.nextInt();
            while(!setPieces(point)){
                point = sc.nextInt();
            }
            if(checkIfMill()) {
                System.out.println(currentPlayer.getColor() + " can remove a Piece from the other Player: ");
                int remPoint = sc.nextInt();
                removePiece(remPoint);
            }
            switchPlayer();
        }
        //Moving-Phase
        while(currentPlayer.getPiecesOnBoard() > 3) {
            System.out.println(this);
            for(int j = 0; j < grid.length; j++) {
                if(!isEmpty(j) && grid[j] == currentPlayer.getColor()) {
                    if(!allValidMoves(j).isEmpty()) {
                        System.out.println("Valid Move from " + j + " to " + allValidMoves(j));
                    }
                }
            }
            System.out.println(currentPlayer.getColor() + " can move a piece from... ");
            int from = sc.nextInt();
            System.out.println("to the point...");
            int to = sc.nextInt();
            makeMove(from,to);
            if(checkIfMill()) {
                System.out.println(currentPlayer.getColor() + " can remove a Piece from the other Player: ");
                int remPoint = sc.nextInt();
                removePiece(remPoint);
            }
            switchPlayer();
        }
    }

    public void makeMove(int from, int to) {
        if(isValidMove(from, to) && currentPlayer.getColor() == grid[from]) {
            grid[from] = FieldState.FREE;
            grid[to] = currentPlayer.getColor();
        } else {
            System.out.println("The point " + to + " is either already occupied or the move is not valid.");
        }
    }

    public boolean setPieces(int point) {
        if(currentPlayer.getPiecesCount() > 0) {
            if(isEmpty(point)) {
                grid[point] = currentPlayer.getColor();
                currentPlayer.setPiecesCount(currentPlayer.getPiecesCount() - 1);
                System.out.println(currentPlayer.getColor() + " has " + currentPlayer.getPiecesCount() + " pieces left.");
                return true;
            }
        }
        System.out.println("The point " + point + " is already occupied.");
        return false;
    }
    //TODO Aktuell wird eine alte Mühle immer wieder gewertet
    public boolean checkIfMill() {
        for (int i = 0; i < 23; i++) {
            if(grid[i] == grid[i+7] && grid[i] == grid[i+6] && grid[i] == currentPlayer.getColor()) {
                System.out.println("Found ORANGE Mill at:" + i + "-" + (i+7) + "-" + (i+6));
                return true;
            }
            i += 7;
        }
        for(int i = 0; i < 20; i++) {
            if(i == 6 || i == 14) {
                i+=2;
            } else if(grid[i] == grid[i+1] && grid[i] == grid[i+2] && grid[i] == currentPlayer.getColor()) {
                System.out.println("Found GREEN Mill at:" + i + "-" + (i+1) + "-" + (i+2));
                return true;
            }
                i++;
        }
        for(int i = 1; i <= 7; i++) {
            if(grid[i] == grid[i+8] && grid[i] == grid[i+16] && grid[i] == currentPlayer.getColor()) {
                System.out.println("Found BLUE Mill at:" + i + "-" + (i+8) + "-" + (i+16));
                return true;
            }
            i++;
        }
        return false;
    }

    /**
     * CurrentPlayer can remove any piece from otherPlayer.
     */
    public  void removePiece(int point) {
        Player otherPlayer = (currentPlayer == player1) ? player2 : player1;
        if(otherPlayer.getColor() == grid[point]) {
            grid[point] = FieldState.FREE;
            otherPlayer.setPiecesOnBoard(currentPlayer.getPiecesOnBoard() - 1);
            checkWinStatus(otherPlayer);
        } else {
            System.out.println("Error: Either an attempt is made to remove one of your own stones or the point is already empty.");
        }
    }
    public void checkWinStatus(Player otherPlayer) {
        if(otherPlayer.getPiecesOnBoard() < 3) {
            System.out.println(otherPlayer.getColor() + " is Game Over!");
        }
    }

    public void switchPlayer() {
        currentPlayer = (currentPlayer == player1) ? player2 : player1;
    }

    public boolean isEmpty(int point) {
        return grid[point] == FieldState.FREE;
    }

    public boolean isValidMove(int from, int to) {
        List<Integer> validMoves = allValidMoves(from);
        if(isEmpty(to)) {
            for (Integer validMove : validMoves) {
                if (to == validMove) {
                    return true;
                }
            }
        }
        return false;
    }
    public List<Integer> allValidMoves(int from) {
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
            if(from == 1|| from == 2 || from ==3 || from == 4 || from == 5 || from == 6 || from == 9 || from == 10 || from == 11|| from == 12 || from == 13 || from == 14 ||from == 17 || from == 18 || from == 19 || from == 20 || from == 21 || from == 22) {
                if(grid[from+1] == FieldState.FREE) {
                    validMoves.add(from+1);
                }
                if(grid[from-1] == FieldState.FREE) {
                    validMoves.add(from-1);
                }
            }
            return validMoves;
    }
    @Override
    public String toString() {
        String gridState =  "(0)------------------------(1)--------------------------(2)\n" +
                            "  |                            |                              |\n" +
                            "  |     (8)----------------(9)----------------(10)      |\n" +
                            "  |       |                    |                     |        |\n" +
                            "  |       |      (16)------(17)------(18)      |        |\n" +
                            "  |       |        |                        |        |        |\n" +
                            "  |       |        |                        |        |        |\n" +
                            "  |       |        |                        |        |        |\n" +
                            "  |       |        |                        |        |        |\n" +
                            "(7)---(15)---(23)                   (19)---(11)---(3)\n" +
                            "  |       |        |                        |        |        |\n" +
                            "  |       |        |                        |        |        |\n" +
                            "  |       |        |                        |        |        |\n" +
                            "  |       |        |                        |        |        |\n" +
                            "  |       |      (22)------(21)------(20)      |        |\n" +
                            "  |       |                    |                     |        |\n" +
                            "  |     (14)---------------(13)---------------(12)      |\n" +
                            "  |                            |                              |\n" +
                            "(6)------------------------(5)--------------------------(4)";
        for(int i = 0; i < grid.length; i++) {
            gridState = gridState.replaceAll("\\("+i+"\\)", grid[i] == FieldState.FREE ? "(F/"+ i +")"
                    : grid[i] == FieldState.BLACK ? "(B/"+ i +")" : "(W/"+ i +")");
        }
        return gridState;
    }
}