package myers.test.states;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.Viewport;
import myers.test.MayhemGame;
import myers.test.handlers.GameStateManager;

public abstract class GameState {

    protected GameStateManager gameStateManager;
    protected MayhemGame game;

    protected SpriteBatch spriteBatch;
    protected OrthographicCamera camera;
    protected OrthographicCamera hudCamera;
    protected Viewport viewport;

    protected GameState(GameStateManager gameStateManager) {
        this.gameStateManager = gameStateManager;
        game = gameStateManager.game();
        spriteBatch = game.getSpriteBatch();
        camera = game.getCamera();
        hudCamera = game.getHudCamera();
        viewport = game.getViewport();
    }

    public abstract void handleInput();

    public abstract void update(float deltaTime);

    public abstract void render();



    public abstract void dispose();
}