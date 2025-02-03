import Model.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertTimeout;

/**
 * JUnit test class for the Timer class.
 */
public class TimerTests {

    Timer timer;

    /**
     * Sets up the Timer object before each test method.
     */
    @BeforeEach
    void setUp() {
        timer = new Timer();
    }

    /**
     * Cleans up resources after each test method.
     */
    @AfterEach
    void tearDown(){}

    /**
     * Tests the run method of the Timer class.
     *
     * @throws InterruptedException if the thread is interrupted while waiting.
     */
    @Test
    void testRun()  throws InterruptedException {
        timer.running = true;
        timer.startTime = 4;

        assertTimeout(Duration.ofSeconds(5), () -> {
            timer.start();
            timer.join();
        });
    }

    /**
     * Tests the switchRunning method of the Timer class.
     */
    @Test
    void testSwitchRunningMethod() {
        Assertions.assertFalse(timer.running);

        timer.switchRunning();

        Assertions.assertTrue(timer.running);
    }

    /**
     * Tests the getTimeLeft method of the Timer class.
     */
    @Test
    void testGetTimeLeft() {
        //Zu Beginn sollten noch 7 minuten Zeit sein.
        assertEquals("07:00", timer.getTimeLeft());

    }
}
