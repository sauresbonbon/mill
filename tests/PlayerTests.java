import Model.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit test class for the Player class.
 */
public class PlayerTests {
    Player player;

    /**
     * Sets up the Player object before each test method.
     */
    @BeforeEach
    void setUp() {
        player = new Player(FieldState.WHITE, 9,9);
    }

    /**
     * Cleans up resources after each test method.
     */
    @AfterEach
    void tearDown(){}

    /**
     * Tests the swapColors method of the Player class.
     */
    @Test
    void testSwapColors() {
        Player player1 = new Player(FieldState.BLACK,9,0);
        Player player2 = new Player(FieldState.WHITE,9,0);

        FieldState originalColorPlayer1 = player1.getColor();
        FieldState originalColorPlayer2 = player2.getColor();

        player1.swapColors(player2);

        assertEquals(originalColorPlayer1, player2.getColor());
        assertEquals(originalColorPlayer2, player1.getColor());
    }

    /**
     * Tests the getMillList method of the Player class.
     */
    @Test
    void testGetMillList() {
        //Gültiger Test, ob die Liste leer ist
        assertTrue(player.getMillList().isEmpty());

        //Gültiger Test, ob die Liste korrekt aktualisiert wurde
        player.getMillList().add("|0|1|2|");
        player.getMillList().add("|2|3|4|");

        assertEquals(2,player.getMillList().size());
        assertTrue(player.getMillList().contains("|0|1|2|"));
        assertTrue(player.getMillList().contains("|2|3|4|"));
    }

    /**
     * Tests the getPiecesCount method of the Player class.
     */
    @Test
    void testGetPiecesCount() {
        //Test, ob die Anzahl der Spielsteine zu Beginn korrekt ist
        assertEquals(9, player.getPiecesCount());

        //Test, ob die Anzahl korrekt aktualisiert wurde
        player.setPiecesCount(5);
        assertEquals(5,player.getPiecesCount());
    }
    /**
     * Tests the getPiecesOnBoard method of the Player class.
     */
    @Test
    void testGetPiecesOnBoard() {
        assertEquals(9,player.getPiecesOnBoard());

        player.setPiecesOnBoard(4);
        assertEquals(4,player.getPiecesOnBoard());
    }

    /**
     * Tests the setPiecesRemoved method of the Player class.
     */
    @Test
    public void testSetPiecesRemoved() {

        player.setPiecesRemoved(5);
        assertEquals(5, player.getPiecesRemoved());

    }

    /**
     * Tests the getComputerState method of the Player class.
     */
    @Test
    public void testGetComputerState() {
        //Zu Beginn sollte der computer state auf false gesetzt sein
        assertFalse(player.getComputerState());

        player.setComputerState(true);
        assertTrue(player.getComputerState());
    }

    /**
     * Tests the setPlayingState method of the Player class.
     */
    @Test
    void testSetPlayingState() {
        //Zu Beginn sollte der Spieler in der SETTING_PHASE sein.
        assertEquals(PlayingState.SETTING_PHASE, player.getPlayingState());

        player.setPlayingState(PlayingState.MOVING_PHASE);
        assertEquals(PlayingState.MOVING_PHASE, player.getPlayingState());
    }

}
