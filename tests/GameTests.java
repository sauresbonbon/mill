import Model.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

/**
 * JUnit test class for the Game class.
 */
public class GameTests {
    Game game;

    /**
     * Sets up the Game object and starts a new game before each test method.
     */
    @BeforeEach
    void setUp() {
        game = new Game();
        game.startNewGame(false);
    }

    /**
     * Cleans up resources after each test method.
     */
    @AfterEach
    void tearDown() {}


    /**
     * Tests the swapColors method of the Game class.
     */
    @Test
    void testSwapColors() {
        //Farbe sollte ohne tausch zu Beginn auf Spieler 1 = Weiß und Spieler 2 = Schwarz gesetzt sein
        assertEquals(FieldState.WHITE, game.getPlayer1().getColor());
        assertEquals(FieldState.BLACK, game.getPlayer2().getColor());

        game.swapColors();

        assertEquals(FieldState.BLACK, game.getPlayer1().getColor());
        assertEquals(FieldState.WHITE, game.getPlayer2().getColor());
    }

    /**
     * Tests the switchComputerState method of the Game class.
     */
    @Test
    void testSwitchComputerState() {
        //Sollte zu Beginn auf false gesetzt sein.
        assertFalse(game.getPlayer2().getComputerState());

        game.switchComputerState();

        assertTrue(game.getPlayer2().getComputerState());
    }

    /**
     * Tests the updatePlayingState method of the Game class.
     */
    @Test
    void testUpdatePlayingState() {
        //Teste, ob Setting-Phase, wenn piecesCount == 0 zu Moving-Phase wechselt.

        assertEquals(PlayingState.SETTING_PHASE, game.getPlayer1().getPlayingState());

        game.getPlayer1().setPiecesCount(0);
        game.updatePlayingState(game.getPlayer1());

        assertEquals(PlayingState.MOVING_PHASE, game.getPlayer1().getPlayingState());

        //Teste, ob Moving-Phase, wenn piecesOnBoard == 3 zu Jumping-Phase wechselt.

        game.getPlayer1().setPiecesOnBoard(3);
        game.updatePlayingState(game.getPlayer1());

        assertEquals(PlayingState.JUMPING_PHASE, game.getPlayer1().getPlayingState());
    }

    /**
     * Tests the randomMove method of the Game class.
     */
    @Test
    void testRandomMove() {
        game.getPlayer1().setPlayingState(PlayingState.SETTING_PHASE);
        game.randomMove();
        //Zeigt dass Spieler 1 mit random Move ein Stein gesetzt hat
        assertEquals(1,game.getPlayer1().getPiecesOnBoard());


        //Spieler 1 hat hier einen Stein auf dem Feld und mit random Move wird dieser ausgewählt und
        // entweder auf Punkt 1 oder Punkt 7 gesetzt.
        //Ich überprüfe hier, ob der FieldState nach dem move leer ist.
        game.startNewGame(false);
        game.getPlayer1().setPlayingState(PlayingState.MOVING_PHASE);
        game.getGrid()[0] = game.getPlayer1().getColor();
        game.randomMove();
        assertEquals(game.getGrid()[0], FieldState.EMPTY);

        //Hier das selbe wie in der Moving-Phase
        game.startNewGame(false);
        game.getPlayer1().setPlayingState(PlayingState.JUMPING_PHASE);
        game.getGrid()[0] = game.getPlayer1().getColor();
        game.randomMove();
        assertEquals(game.getGrid()[0], FieldState.EMPTY);
    }

    /**
     * Tests the hasValidMoves method of the Game class.
     */
    @Test
    void hasValidMoves() {
        game.getGrid()[1] = game.getPlayer1().getColor();
        game.getGrid()[0] = game.getPlayer2().getColor();
        game.getGrid()[2] = game.getPlayer2().getColor();
        game.getGrid()[9] = game.getPlayer2().getColor();

        assertFalse(game.hasValidMoves());
    }

    /**
     * Tests the isGameOver method of the Game class.
     */
    @Test
    void testIsGameOver() {
        //Sollte false sein, da Spieler 1 noch in der Setting-Phase ist.
        game.getPlayer1().setPiecesOnBoard(2);
        assertFalse(game.isGameOver());


        game.getPlayer1().setPiecesOnBoard(5);
        assertFalse(game.isGameOver());


        game.getPlayer1().setPlayingState(PlayingState.JUMPING_PHASE);
        game.getPlayer1().setPiecesOnBoard(2);
        assertTrue(game.isGameOver());
    }

    /**
     * Tests the jumpPiece method of the Game class.
     */
    @Test
    void testJumpPiece() {
        game.getGrid()[2] = game.getCurrentPlayer().getColor();

        game.jumpPiece(2, 8);

        assertEquals(game.getGrid()[8], game.getCurrentPlayer().getColor());
        assertEquals(game.getGrid()[2], FieldState.EMPTY);
    }

    /**
     * Tests the makeMove method of the Game class.
     */
    @Test
    void testMakeMove() {
        game.getGrid()[4] = game.getPlayer1().getColor();

        assertEquals(game.getGrid()[4], game.getPlayer1().getColor());
        assertEquals(FieldState.EMPTY, game.getGrid()[5]);

        game.makeMove(4,5);

        assertEquals(FieldState.EMPTY, game.getGrid()[4]);
        assertEquals(game.getGrid()[5], game.getPlayer1().getColor());
    }

    /**
     * Tests the placePiece method of the Game class.
     */
    @Test
    void testSetPieces() {
        assertEquals(0, game.getPlayer1().getPiecesOnBoard());
        assertEquals(9, game.getPlayer1().getPiecesCount());
        assertEquals(FieldState.EMPTY, game.getGrid()[5]);

        assertTrue(game.placePiece(5));

        assertEquals(1, game.getPlayer1().getPiecesOnBoard());
        assertEquals(8, game.getPlayer1().getPiecesCount());
        assertEquals(game.getPlayer1().getColor(), game.getGrid()[5]);
    }

    /**
     * Tests the findMill method of the Game class.
     */
    @Test
    void testFindMill() {
        game.getGrid()[0] = game.getPlayer1().getColor();
        game.getGrid()[1] = game.getPlayer1().getColor();
        game.getGrid()[2] = game.getPlayer1().getColor();

        String newMill = game.findMill(game.getPlayer1());

        assertNotNull(newMill);

        assertEquals("|0|1|2|", newMill);
    }

    /**
     * Tests the isMill method of the Game class.
     */
    @Test
    void testIsMill() {
        //Zu Beginn sollte es keine Mühle geben
        assertFalse(game.isMill());

        game.getGrid()[0] = game.getPlayer1().getColor();
        game.getGrid()[1] = game.getPlayer1().getColor();
        game.getGrid()[2] = game.getPlayer1().getColor();

        String newMill = game.findMill(game.getPlayer1());

        assertTrue(game.isMill());

    }

    /**
     * Tests the removePiece method of the Game class.
     */
    @Test
    void testRemovePiece() {
        // Test für ungültiges entfernen eines Spielsteines aus einer bestehenden Mühle
        game.getPlayer2().setPiecesOnBoard(3);
        game.getGrid()[0] = game.getPlayer2().getColor();
        game.getGrid()[1] = game.getPlayer2().getColor();
        game.getGrid()[2] = game.getPlayer2().getColor();
        String newMill = "|0|1|2|";
        game.getOtherPlayer().getMillList().add(newMill);

        assertEquals(3, game.getPlayer2().getPiecesOnBoard());

        assertFalse(game.removePiece(1));

        assertEquals(3, game.getPlayer2().getPiecesOnBoard());


        //Test für ein gültiges entfernen eines Spielsteins
        game.startNewGame(false);
        //Mühle für Spieler 1 aufstellen
        game.getGrid()[6] = game.getPlayer1().getColor();
        game.getGrid()[5] = game.getPlayer1().getColor();
        game.getGrid()[4] = game.getPlayer1().getColor();
        String newMill2 = "|6|5|4|";
        game.getPlayer1().getMillList().add(newMill2);

        game.getPlayer2().setPiecesOnBoard(3);
        game.getGrid()[0] = game.getPlayer2().getColor();
        game.getGrid()[1] = game.getPlayer2().getColor();
        game.getGrid()[3] = game.getPlayer2().getColor();

        assertTrue(game.removePiece(1));
        assertEquals(2, game.getPlayer2().getPiecesOnBoard());
    }

    /**
     * Tests the switchPlayers method of the Game class.
     */
    @Test
    void testSwitchPlayers() {
        assertEquals(game.getCurrentPlayer(), game.getPlayer1());
        game.switchPlayer();
        assertEquals(game.getCurrentPlayer(), game.getPlayer2());
    }

    /**
     * Tests the isEmpty method of the Game class.
     */
    @Test
    void testIsEmpty() {
        //Test für ein leeres Feld bei Punkt 0
        assertTrue(game.isEmpty(0));

        //test für ein besetztes Feld bei Punkt 1
        game.getGrid()[1] = game.getCurrentPlayer().getColor();
        assertFalse(game.isEmpty(1));
    }

    /**
     * Tests the isValidMove method of the Game class.
     */
    @Test
    void testIsValidMove() {
        //Test eines gültigen Zugs von 0 nach 1
        game.getGrid()[0] = game.getCurrentPlayer().getColor();
        assertTrue(game.isValidMove(0,1));

        //Test eines gültigen Zugs von 9 nach 1
        game.getGrid()[9] = game.getCurrentPlayer().getColor();
        assertTrue(game.isValidMove(9,1));

        //Test eines ungültigen Zugs von 1 nach 8
        game.getGrid()[1] = game.getCurrentPlayer().getColor();
        assertFalse(game.isValidMove(1,8));

        //Test eines ungültigen Zugs von 0 nach 7, wenn 7 belegt
        game.getGrid()[0] = game.getCurrentPlayer().getColor();
        game.getGrid()[7] = game.getOtherPlayer().getColor();
        assertFalse(game.isValidMove(0,7));

    }

    /**
     * Tests the hasValidMoves method of the Game class.
     */
    @Test
    void testHasValidMoves() {
        game.getPlayer1().setPlayingState(PlayingState.MOVING_PHASE);
        //Ohne gesetzte Steine sollte hasValidMoves false sein
        assertFalse(game.hasValidMoves());

        game.getGrid()[4] = game.getPlayer1().getColor();

        assertTrue(game.hasValidMoves());
    }

    /**
     * Tests the allValidMoves method of the Game class.
     */
    @Test
    void testAllValidMoves() {
        //Gültiger Test für Punkt 0
        List<Integer> validMoves = game.allValidMoves(0);
        assertTrue(validMoves.contains(1));
        assertTrue(validMoves.contains(7));
        assertEquals(2,validMoves.size());

        //Gültiger Test für Punkt 13
        validMoves = game.allValidMoves(13);
        assertTrue(validMoves.contains(5));
        assertTrue(validMoves.contains(12));
        assertTrue(validMoves.contains(14));
        assertTrue(validMoves.contains(21));
        assertEquals(4,validMoves.size());

        //Ungültiger Test für 21 (Eigentlich Punkt 13 anstatt 5)
        validMoves = game.allValidMoves(21);
        assertTrue(validMoves.contains(20));
        assertTrue(validMoves.contains(22));
        assertFalse(validMoves.contains(5));
        assertEquals(3, validMoves.size());

        game.startNewGame(false);
        game.getGrid()[7] = game.getPlayer1().getColor();
        validMoves = game.allValidMoves(7);
        assertTrue(validMoves.contains(0));
        assertTrue(validMoves.contains(6));


        //Gültiger Test, wenn Spieler 1 in Jumping-Phase
        game.startNewGame(false);
        game.getPlayer1().setPlayingState(PlayingState.JUMPING_PHASE);
        //Setzt Spielsteine von Spieler 1 auf 0,7 und 15
        game.getGrid()[0] = game.getPlayer1().getColor();
        game.getGrid()[7] = game.getPlayer1().getColor();
        game.getGrid()[15] = game.getPlayer1().getColor();

        //Setzt Spielsteine von Spieler 2 auf
        game.getGrid()[1] = game.getPlayer2().getColor();
        game.getGrid()[2] = game.getPlayer2().getColor();
        game.getGrid()[11] = game.getPlayer2().getColor();
        game.getGrid()[21] = game.getPlayer2().getColor();
        game.getGrid()[23] = game.getPlayer2().getColor();
        assertEquals(16, game.allValidMoves(0).size());
        assertEquals("[3, 4, 5, 6, 8, 9, 10, 12, 13, 14, 16, 17, 18, 19, 20, 22]", game.allValidMoves(0).toString());
    }

    /**
     * Tests the getMoveCounter method of the Game class.
     */
    @Test
    void testGetMoveCounter() {
        //Initial sollte move counter = 0 sein

        assertEquals(0, game.getMoveCounter());

        //Wird aktualisiert, wenn Spieler gewechselt werden
        game.switchPlayer();

        assertEquals(1, game.getMoveCounter());
    }

    /**
     * Tests the toString method of the Game class.
     */
    @Test
    void testToString() {

        String expected1 = "It´s WHITE's turn" + "\n" +
                """
                    (F/0)------------------------(F/1)--------------------------(F/2)
                      |                            |                              |
                      |     (F/8)----------------(F/9)----------------(F/10)      |
                      |       |                    |                     |        |
                      |       |      (F/16)------(F/17)------(F/18)      |        |
                      |       |        |                        |        |        |
                      |       |        |                        |        |        |
                      |       |        |                        |        |        |
                      |       |        |                        |        |        |
                    (F/7)---(F/15)---(F/23)                   (F/19)---(F/11)---(F/3)
                      |       |        |                        |        |        |
                      |       |        |                        |        |        |
                      |       |        |                        |        |        |
                      |       |        |                        |        |        |
                      |       |      (F/22)------(F/21)------(F/20)      |        |
                      |       |                    |                     |        |
                      |     (F/14)---------------(F/13)---------------(F/12)      |
                      |                            |                              |
                    (F/6)------------------------(F/5)--------------------------(F/4)""";

        assertEquals(expected1, game.toString());

        game.getGrid()[21] = game.getPlayer2().getColor();

        String expected2 = "It´s WHITE's turn" + "\n" +
                """
                    (F/0)------------------------(F/1)--------------------------(F/2)
                      |                            |                              |
                      |     (F/8)----------------(F/9)----------------(F/10)      |
                      |       |                    |                     |        |
                      |       |      (F/16)------(F/17)------(F/18)      |        |
                      |       |        |                        |        |        |
                      |       |        |                        |        |        |
                      |       |        |                        |        |        |
                      |       |        |                        |        |        |
                    (F/7)---(F/15)---(F/23)                   (F/19)---(F/11)---(F/3)
                      |       |        |                        |        |        |
                      |       |        |                        |        |        |
                      |       |        |                        |        |        |
                      |       |        |                        |        |        |
                      |       |      (F/22)------(B/21)------(F/20)      |        |
                      |       |                    |                     |        |
                      |     (F/14)---------------(F/13)---------------(F/12)      |
                      |                            |                              |
                    (F/6)------------------------(F/5)--------------------------(F/4)""";

        assertEquals(expected2, game.toString());

    }
}
