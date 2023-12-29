package View;
import Controller.IController;

import processing.core.PApplet;
import controlP5.*;

public class Board extends PApplet implements IBoard {
    ControlP5 cp5;
    /**
     * The width of the graphic window.
     */
    final int width = 800;
    /**
     * The height of the graphic window.
     */
    final int height = 800;

    IController controller;

    /**
     *
     * @param controller
     */
    public void setController(IController controller) {
        this.controller = controller;
    }

    /**
     *
     */
    @Override
    public void settings() {
        size(width,height);
    }

    /**
     *
     */
    @Override
    public void setup() {
        controller.startNewGame();
        cp5 = new ControlP5(this);
    }

    /**
     *
     */
    @Override
    public void draw() {
        drawGrid();
    }

    /**
     *
     */
    @Override
    public void drawGrid() {
        float w = width/15;
        float h = height/15;

        for(int i = 0; i<15; i++) {
            for (int j = 0; j < 15; j++) {
                float x = j*w;
                float y = i*h;

                stroke(0);
                noFill();
                rect(x,y,w,h);
                strokeWeight(3);
            }
        }
    }

    /**
     *
     */
    public void drawPiece() {
    }

    /**
     *
     */
    public void mousePressed() {
        int col = (int) (mouseX /(width/15));
        int row = (int) (mouseX /(width/15));
        int index = row * 15 + col;

    }
}
//        background(255);
//        strokeWeight(10);
//
//        rect(width/8,height/8,6*(width/8),6*(height/8));
//        rect(2*width/8,2*height/8,4*(width/8),4*(height/8));
//        rect(3*width/8,3*height/8,2*(width/8),2*(height/8));
//
//        line((float) width /2,100, (float) width /2,300);
//        line((float) width /2,height-100, (float) width /2,height-300);
//        line(100, (float) height /2,300, (float) height /2);
//        line(width-100, (float) height /2,width-300, (float) height /2);
//
//        for(int i = 1; i<=3; i++) {
//            int xy = 100 * i;
//            circle(xy,xy,40);
//            circle(xy, (float) height /2,40);
//            circle(xy,height-xy,40);
//            circle((float) width /2,xy,40);
//            circle(width-xy,xy,40);
//            circle(width-xy, (float) height /2,40);
//            circle(width-xy,height-xy,40);
//            circle((float) width /2,height-xy,40);
//        }
