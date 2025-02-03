package View;
import Controller.IController;

import Controller.GameState;
import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;
import java.util.List;

import controlP5.*;
import static controlP5.ControlP5Constants.ACTION_RELEASE;

/**
 * The graphical user interface for the Mill (Nine Men's Morris) game as a light theme.
 * <p>
 * This class extends the PApplet class from the Processing library and implements the IBoard interface.
 * It is responsible for rendering and managing the visual representation of the game, handling user inputs,
 * and interacting with the associated controller.
 * </p>
 */
public class BoardDay extends PApplet implements IBoard {
    private ControlP5 cp5;
    private final int width = 1225;
    private final int height = 800;
    private PFont fontGeorgia;
    private IController controller;
    private Button startButton;
    private Button switchColorsButton;
    private Button computerButton;
    private Button resetButton;
    private Button newGameButton;
    private PImage image;
    private boolean colorsSwapped = false;
    private boolean buttonsInitialized = false;
    private boolean resetButtonInitialized = false;
    private boolean newGameButtonInitialized = false;
    private final int[][] ellipsePositions = {
            // Äußeres Quadrat
            {400,150}, {625, 150}, {850,150}, {850, 375},{850,600},{625,600},{400,600}, {400,375},
            // Mittleres Quadrat
            {475, 225}, {625, 225}, {775,225}, {775, 375}, {775,525}, {625, 525}, {475, 525}, {475,375},
            // Inneres Quadrat
            {550, 300}, {625, 300}, {700,300}, {700,375}, {700, 450},{625, 450}, {550,450}, {550, 375}
    };
    /*-------------------------------------------------------------------------------------*/

    /**
     * Sets the controller of the board.
     *
     * @param controller The controller to be set for the board.
     */
    public void setController(IController controller) {
        this.controller = controller;
    }

    /**
     * Sets the initial settings for the graphical display.
     * This method ist automatically called by the Processing library before the sketch is displayed.
     *<p>
     * It sets the size of the graphical window.
     *</p>
     */
    @Override
    public void settings() {
        size(width, height);
    }

    /**
     * Initialize and sets up the initial state of the graphical user interface.
     * This method is automatically called by the Processing library at the beginning of the sketch.
     *<p>
     * It loads a font, an image, sets the initial game state to START and starts a new game.
     *</p>
     */
    @Override
    public void setup() {
        fontGeorgia = createFont("Georgia", 36);
        image = loadImage("images/day/windmillDay.jpg");
        controller.setGameState(GameState.START);
        controller.startNewGame(false);
    }

    /**
     * Invoked continuously by the Processing library to update and render frames in the graphical user interface.
     * <p>
     * This method delegates the responsibility to the associated controller, allowing it to manage the progression
     * of frames and update the display accordingly.
     * </p>
     */
    @Override
    public void draw() {
        controller.nextFrame();
    }

    /*------------------------------------draw screen status------------------------------------------*/
    /**
     * Draws the start screen of the graphical user interface.
     * <p>
     *     Displays the background image, the game title "MÜHLE", initializes buttons and draws the player boxes.
     * </p>
     */
    public void drawStartScreen() {
        background(image);

        textFont(fontGeorgia);
        textSize(120);
        fill(0);
        text("MÜHLE", 300,100);

        if (!buttonsInitialized) {
            initializeStartButtons();
            buttonsInitialized = true;
        }
        drawPlayerBox();
    }
    /**
     * Draws the playing screen of the graphical user interface during an active game.
     *
     * <p>
     *     Sets the background color, hides the buttons from the start screen, shows the reset button
     *     and draws the game board.
     * </p>
     */
    public void drawPlayingScreen() {
        background(247,246,182);
        startButton.setVisible(false);
        switchColorsButton.setVisible(false);
        computerButton.setVisible(false);

        drawLines();
        drawPieces();
        drawTimer(controller.getTimeLeftPlayer1(), controller.getTimeLeftPlayer2());
        drawPlayerText();
        drawRemainingPieces();
        drawRemovedPieces();

        if(newGameButtonInitialized) {
            newGameButton.setVisible(false);
            newGameButtonInitialized = false;
        }
        if (!resetButtonInitialized) {
            initializeResetButton();
            resetButtonInitialized = true;
        }
    }

    /**
     * Draws the end screen of the graphical user interface when the game has ended.
     *
     * <p>
     *     Sets the background color and hides the buttons from the start screen.
     * </p>
     */
    public void drawEndScreen() {
        background(117,122,190);
        startButton.setVisible(false);
        switchColorsButton.setVisible(false);
        computerButton.setVisible(false);
        resetButton.setVisible(false);

        textSize(50);
        text(controller.getOtherPlayerString() + " ist der Gewinner!", 600,300);

        textSize(40);
        text("\nDu hast das Spiel in nur " + (controller.getMoveCounter()/2) + " Zügen gewonnen.", 600,350);

        if (!newGameButtonInitialized) {
            initializeNewGameButton();
            newGameButtonInitialized = true;
        }
    }

    /*----------------------------------highlight methods-----------------------------------------*/

    /**
     * Highlights the selected game piece on the grid.
     *<p>
     * This method takes the index of the selected piece on the grid and highlights it
     * by drawing a colored and outlined ellipse at its position.
     *</p>
     *
     * @param point The index of the selected piece on the grid.
     */
    public void highlightSelectedPiece(int point) {
        int x = ellipsePositions[point][0];
        int y = ellipsePositions[point][1];
        int ellipseSize = 35;

        stroke(50,95,66);
        strokeWeight(5);

        fill(controller.getColorForGrid(point));
        ellipse(x, y, ellipseSize, ellipseSize);
        stroke(0);
        strokeWeight(3);
    }

    /**
     * Highlights the valid moves on the grid.
     *<p>
     * This method takes a list valid moves on the grid and highlights each of them
     * by drawing colored and outlined ellipses at their positions.
     *</p>
     *
     * @param allValidMoves A list of valid moves on the grid.
     */
    public void highlightValidMoves(List<Integer> allValidMoves) {
        for (Integer allValidMove : allValidMoves) {
            int x = ellipsePositions[allValidMove][0];
            int y = ellipsePositions[allValidMove][1];
            int ellipseSize = 35;

            stroke(85, 153, 83);
            strokeWeight(3);

            fill(120);
            ellipse(x, y, ellipseSize, ellipseSize);
            stroke(0);
            strokeWeight(3);
        }
    }

    /**
     * Highlights removable pieces on the grid by drawing colored and outlined ellipses.
     *<p>
     * This method takes a list of removable pieces on the grid and highlights each of
     * them by drawing colored and outlined ellipses at their positions.
     *</p>
     *
     * @param removablePieces A list of removable pieces on the grid.
     */
    public void highlightRemovablePieces(List<Integer> removablePieces) {
        for (Integer removablePiece : removablePieces) {
            int x = ellipsePositions[removablePiece][0];
            int y = ellipsePositions[removablePiece][1];
            int ellipseSize = 35;

            stroke(34, 139, 34);
            strokeWeight(3);
            fill(controller.getColorForGrid(removablePiece));
            ellipse(x, y, ellipseSize, ellipseSize);
        }
        stroke(0);
        strokeWeight(3);
    }

    /*--------------------------------draw methods----------------------------------------*/
    /**
     * Draws the current status text on the screen based on the specified status code.
     *<p>
     * This method takes a status code as input and displays corresponding game status messages
     * on the screen, providing guidance to the players during different phases of the game.
     *</p>
     *
     * @param textStatus The status code indicating the current phase of the game.
     *                   - 0: Setting a game piece.
     *                   - 1: Choosing a player's own piece.
     *                   - 2: Choosing a marked field.
     *                   - 3: Removing an opponent's piece.
     */
    public void drawStatus(int textStatus) {
        fill(0);
        textSize(30);
        String currentPlayerText = controller.getCurrentPlayerString();

        switch (textStatus) {
            case 0:
                text(currentPlayerText + ", setze einen Spielstein.", 625, 675);
                break;
            case 1:
                text(currentPlayerText + ", wähle einen deiner Steine aus.", 625, 675);
                break;
            case 2:
                text(currentPlayerText + ", wähle ein markiertes Feld aus.", 625, 675);
                break;
            case 3:
                text(currentPlayerText + ", wähle einen Stein des Gegners, um ihn zu entfernen.", 625, 675);
                break;
            default:
                break;
        }
    }
    private void drawLines() {
        fill(255,160);
        rect(375, 125,500,500);

        strokeWeight(5);
        // Äußeres Quadrat
        line(400, 600, 400, 150);
        line(400, 150, 850, 150);
        line(400, 600, 850, 600);
        line(850, 600, 850, 150);

        // Mittleres Quadrat
        line(475, 225, 475, 525);
        line(475, 225, 775, 225);
        line(775, 525, 475, 525);
        line(775, 525, 775, 225);

        // Innerstes Quadrat
        line(550, 300, 700, 300);
        line(550, 450, 550, 300);
        line(700, 450, 700, 300);
        line(700, 450, 550, 450);

        // Querlinien
        line(625, 150, 625, 300);
        line(625, 600, 625, 450);
        line(400, 375, 550, 375);
        line(700, 375, 850, 375);
    }

    private void drawPieces() {
        int i = 0;
        for (int[] ellipsePosition : ellipsePositions) {
            int x = ellipsePosition[0];
            int y = ellipsePosition[1];
            int ellipseSize = 35;

            strokeWeight(2);

            if (isMouseOverPiece(mouseX, mouseY, x, y, ellipseSize)) {
                stroke(205,51,51);
                strokeWeight(3);
            }
            fill(controller.getColorForGrid(i));
            ellipse(x, y, ellipseSize, ellipseSize);
            stroke(0);
            strokeWeight(3);

            i++;
        }
    }
    private void drawTimer(String timer1, String timer2) {
        fill(0);
        textFont(createFont("Georgia", 28));

        text("Timer", 625, 50);
        text(timer1, 525, 95);
        text(timer2, 725, 95);
    }

    private void drawPlayerText() {
        fill(0);

        int player1TextSize = (controller.getCurrentPlayer() == controller.getPlayer1Color()) ? 40 : 25;
        textFont(createFont("Georgia", player1TextSize, player1TextSize == 40));

        text(controller.getPlayer1ColorString(), 175, 75);

        int player2TextSize = (controller.getCurrentPlayer() == controller.getPlayer2Color()) ? 40 : 25;
        textFont(createFont("Georgia", player2TextSize, player2TextSize == 40));

        text(controller.getPlayer2ColorString(), 1025, 75);
    }


    private void drawRemainingPieces() {
        int ellipseSize = 40;
        int spacing = 20;

        for (int i = 0; i < controller.getPiecesCountPlayer1(); i++) {
            float x = 175;
            float y = 150 + i * (ellipseSize + spacing);
            if(controller.isPlayer1White()) {
                fill(255);
            } else fill(0);
            ellipse(x,y,ellipseSize,ellipseSize);
        }
        for (int i = 0; i < controller.getPiecesCountPlayer2(); i++) {
            float x = 1025;
            float y = 150 + i * (ellipseSize + spacing);
            if(controller.isPlayer1White()) {
                fill(0);
            } else fill(255);
            ellipse(x,y,ellipseSize,ellipseSize);
        }
    }
    private void drawRemovedPieces() {
        int ellipseSize = 35;
        int spacing = 15;

        for (int i = 0; i < controller.getPlayer1PiecesRemoved(); i++) {
            float x = 350;
            float y = 250 + i * (ellipseSize + spacing);
            if (controller.isPlayer1White()) {
                fill(0);
            } else fill(255);
            ellipse(x, y, ellipseSize, ellipseSize);
        }

        for (int i = 0; i < controller.getPlayer2PiecesRemoved(); i++) {
            float x = 900;
            float y = 250 + i * (ellipseSize + spacing);
            if (controller.isPlayer1White()) {
                fill(255);
            } else fill(0);
            ellipse(x, y, ellipseSize, ellipseSize);
        }
    }

    private void drawPlayerBox() {
        int player1Rect = controller.isPlayer1White() ? 255 : 0;
        int player1Text = controller.isPlayer1White() ? 0 : 255;

        int player2Rect = controller.isPlayer1White() ? 0 : 255;
        int player2Text = controller.isPlayer1White() ? 255 : 0;

        String player2 = controller.getComputerState() ? "Computer" : "Spieler 2";

        noStroke();

        fill(player1Rect);
        rect(100, 200, 125, 50);

        fill(player1Text);
        textAlign(CENTER, CENTER);
        textSize(19);
        text("Spieler 1", 165, 225);

        fill(player2Rect);
        rect(382, 200, 125, 50);

        fill(player2Text);
        textAlign(CENTER, CENTER);
        textSize(19);
        text(player2, 445, 225);
    }
    /*-----------------------------------handle mouse input--------------------------------------------*/
    /**
     * Overrides the mousePressed() method to handle user interactions while the game is in the PLAYING state.
     *<p>
     * This method is triggered when the mouse is pressed.
     * If so, it iterates through the grid positions and determines if the mouse cursor is over a game piece.
     * If a game piece is clicked, the corresponding controller method is invoked to handle the user's interaction.
     *</p>
     */
    @Override
    public void mousePressed() {
        if (controller.getGameState() == GameState.PLAYING) {
            for (int i = 0; i < ellipsePositions.length; i++) {
                int x = ellipsePositions[i][0];
                int y = ellipsePositions[i][1];
                int ellipseSize = 35;

                if (isMouseOverPiece(mouseX, mouseY, x, y, ellipseSize)) {
                    controller.handlePiece(i);
                    break;
                }
            }
        }
    }
    private boolean isMouseOverPiece(float mouseX, float mouseY, float ellipseX, float ellipseY, float ellipseSize) {
        float dx = mouseX - ellipseX;
        float dy = mouseY - ellipseY;
        float radius = ellipseSize / 2;
        return dx * dx + dy * dy <= radius * radius;
    }

    /*---------------------------------initialize buttons---------------------------------*/

    private void initializeResetButton() {
        cp5 = new ControlP5(this);

        resetButton = cp5.addButton("Neustarten")
                .setPosition(550, 725)
                .setSize(150, 50);

        resetButton.getCaptionLabel().setFont(createFont("Arial", 15));
        resetButton.setColorBackground(color(42,48,74))
                .setColorForeground(color(56,64,99))
                .setColorLabel(255);

        resetButton.addListenerFor(ACTION_RELEASE, callbackEvent -> {
            try {
                controller.startNewGame(controller.getComputerState());
                controller.setGameState(GameState.PLAYING);
                controller.startTimer();
                if(colorsSwapped) controller.swapColors();
                drawPlayingScreen();
            }  catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

    }
    private void initializeNewGameButton() {
        cp5 = new ControlP5(this);

        newGameButton = cp5.addButton("Erneut spielen?")
                .setPosition(350, 450)
                .setSize(500, 100);

        newGameButton.getCaptionLabel().setFont(createFont("Arial", 25));

        newGameButton.setColorBackground(color(18,23,42))
                .setColorForeground(color(28,36,66))
                .setColorLabel(255);
        newGameButton.addListenerFor(ACTION_RELEASE, callbackEvent -> {
            try {
                controller.startNewGame(controller.getComputerState());
                controller.setGameState(GameState.PLAYING);
                controller.startTimer();
                if(colorsSwapped) controller.swapColors();
                resetButtonInitialized = false;
                drawPlayingScreen();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }
    private void initializeStartButtons() {
        cp5 = new ControlP5(this);

        startButton = cp5.addButton("Spiel starten")
                .setPosition(100, 450)
                .setSize(width/3, height/5);

        startButton.getCaptionLabel().setFont(createFont("Georgia", 25));
        startButton.setColorBackground(color(73,60,72))
                .setColorForeground(color(88,72,87))
                .setColorLabel(255);
        startButton.addListenerFor(ACTION_RELEASE, callbackEvent -> {
            try {
                controller.setGameState(GameState.PLAYING);
                controller.startTimer();
                drawPlayingScreen();
            }  catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        switchColorsButton = cp5.addButton("Farben tauschen")
                .setPosition(100, 275)
                .setSize(width/3, 50);

        switchColorsButton.getCaptionLabel().setFont(createFont("Arial", 15));
        switchColorsButton.setColorBackground(color(49,73,111))
                .setColorForeground(color(60,88,134))
                .setColorLabel(255);
        switchColorsButton.addListenerFor(ACTION_RELEASE, callbackEvent -> {
            try {
                controller.swapColors();
            }  catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        computerButton = cp5.addButton("Spiel gegen Computer")
                .setPosition(100, 350)
                .setSize(width/3, 50);

        computerButton.getCaptionLabel().setFont(createFont("Arial", 15));
        computerButton.setColorBackground(color(49,73,111))
                .setColorForeground(color(60,88,134))
                .setColorLabel(255);
        computerButton.addListenerFor(ACTION_RELEASE, callbackEvent -> {
            try {
                controller.switchComputerState();
            }  catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }
}