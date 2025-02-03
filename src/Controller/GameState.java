package Controller;

/**
 * Enumeration representing the possible states of a game.
 *<p>
 * This enum defines the different states that a game can be in. It serves as a set
 * of constants to represent the game's overall status during its lifecycle.
 *</p>
 *<p>
 * The possible states include:
 * - START: Indicates the starting phase of the game.
 * - PLAYING: Represents the active playing phase of the game.
 * - END: Indicates the completion of the game.
 *</p>
 */
public enum GameState {
    START,
    PLAYING,
    END
}