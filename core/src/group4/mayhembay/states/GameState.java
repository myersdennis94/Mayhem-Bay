package group4.mayhembay.states;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import group4.mayhembay.MayhemGame;
import group4.mayhembay.handlers.GameStateManager;

/**
 * This abstract class is a representation of a game state.
 */
public abstract class GameState {

    protected GameStateManager gameStateManager;
    protected MayhemGame game;

    protected SpriteBatch spriteBatch;
    protected OrthographicCamera camera;
    protected OrthographicCamera hudCamera;

    /**
     * This constructor creates a GameState object.
     * @param gameStateManager a <b><CODE>GameStateManager</CODE></b> that allows access to other critical sections of the game.
     */
    protected GameState(GameStateManager gameStateManager) {
        this.gameStateManager = gameStateManager;
        game = gameStateManager.game();
        spriteBatch = game.getSpriteBatch();
        camera = game.getCamera();
        hudCamera = game.getHudCamera();
    }

    /**
     * This abstract method will handle user input when implemented.
     */
    public abstract void handleInput();

    /**
     * This abstract method will handle updating game states when implemented.
     *
     * @param deltaTime a <b><CODE>float</CODE></b> that corresponds to the amount of time between updates.
     */
    public abstract void update(float deltaTime);

    /**
     * This abstract method will handle rendering the game states when implemented.
     *
     * @param deltaTime a <b><CODE>float</CODE></b> that corresponds to the amount of time between updates.
     */
    public abstract void render(float deltaTime);

    /**
     * This abstract method will manage disposal of scene objects when implemented.
     */
    public abstract void dispose();
}
