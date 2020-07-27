package myers.test.states;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import myers.test.TestGame;
import myers.test.handlers.GameStateManager;

public abstract class GameState {

    protected GameStateManager gameStateManager;
    protected TestGame game;

    protected SpriteBatch spriteBatch;
    protected OrthographicCamera camera;
    protected OrthographicCamera hudCamera;

    protected GameState(GameStateManager gameStateManager) {
        this.gameStateManager = gameStateManager;
        game = gameStateManager.game();
        spriteBatch = game.getSpriteBatch();
        camera = game.getCamera();
        hudCamera = game.getHudCamera();
    }

    public abstract void handleInput();

    public abstract void update(float deltaTime);

    public abstract void render();

    public abstract void dispose();
}
