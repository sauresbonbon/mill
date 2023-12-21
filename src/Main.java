import processing.core.PApplet;

import Controller.Controller;
import View.Board;
import Model.Game;

public class Main {
    public static void main(String[] args) {
        var game = new Game();
        var field = new Board();
        var controller = new Controller();

        controller.setGrid(field);
        controller.setGame(game);
        field.setController(controller);

        PApplet.runSketch(new String[]{"View"}, field);
    }
}
