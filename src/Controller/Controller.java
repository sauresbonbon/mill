package Controller;
import View.IField;
import Model.*;

public class Controller implements IController{
    IGame game;
    IField grid;

    public void setGrid(IField grid) {
        this.grid = grid;
    }

    public void setGame(IGame game) {
        this.game = game;
    }
    public void startNewGame() {
        game.startNewGame();
    }

    @Override
    public void nextFrame() {

    }
}
