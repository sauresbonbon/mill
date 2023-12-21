package Controller;
import View.IBoard;
import Model.*;

public class Controller implements IController{
    IGame game;
    IBoard grid;

    public void setGrid(IBoard grid) {
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
