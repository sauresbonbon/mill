import View.BoardNight;
import processing.core.PApplet;

import Controller.Controller;
import View.BoardDay;
import Model.Game;

/**
 * The Main class serves as the entry point for the program, initializing the game components
 * and launching the graphical user interface.
 */
public class Main {
    /**
     * The main method initializes instances of the Game, Board, and Controller classes and runs
     * the graphical user interface for the game.
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {
        var game = new Game();
//        var board = new BoardDay();
        var board = new BoardNight();
        var controller = new Controller();

        controller.setBoard(board);
        controller.setGame(game);
        board.setController(controller);

        PApplet.runSketch(new String[]{"View"}, board);
    }
}
