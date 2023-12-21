package View;

import Controller.IController;

public interface IBoard {
    void setController(IController controller);
    void settings();
    void setup();
    void draw();
    void drawGrid();
    void drawPiece();
    void mousePressed();
}
