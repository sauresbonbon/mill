package Model;

/**
 * A timer implementation for tracking elapsed time.
 *<p>
 * This class extends the Thread class to create a timer that can be used to measure
 * elapsed time in a game. It provides methods to start, stop, and retrieve the remaining
 * time in a formatted string.
 *</p>
 */
public class Timer extends Thread{
    /**
     * The initial start time for the timer in seconds (default: 7 minutes).
     */
    public long startTime = 420;
    /**
     * Indicates whether the timer is currently running.
     */
    public boolean running = false;
    private long elapsedTime = 0;
    private boolean alive = true;
    private String timeLeft = "07:00";
    private final Object lock = new Object();

    /**
     * Overrides the run method of the Thread class.
     *<p>
     * This method is executed when the timer thread is started. It continuously updates
     * the elapsed time and calculates the remaining time in a formatted string.
     *</p>
     */
    @Override
    public void run() {
        while (alive) {
            synchronized (lock) {
                if (running) {
                    elapsedTime += 1;
                    long currentTime = startTime - elapsedTime;

                    if (currentTime <= 0) { alive = false; }
                    else {
                        long minutes = (currentTime / 60);
                        long seconds = (currentTime % 60);

                        timeLeft = String.format("%02d:%02d", minutes, seconds);
                    }
                }
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    /**
     * Toggles the running state of the timer.
     */
    public void switchRunning() {
        synchronized (lock) {
            running = !running;
        }
    }
    /**
     * Retrieves the remaining time on the timer as a formatted string.
     *
     * @return A formatted string representing the time left on the timer.
     */
    public String getTimeLeft() { return timeLeft; }
}