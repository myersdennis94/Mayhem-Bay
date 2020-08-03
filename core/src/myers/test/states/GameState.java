package myers.test.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import myers.test.MayhemGame;
import myers.test.handlers.GameData;
import myers.test.handlers.GameDataManager;
import myers.test.handlers.GameStateManager;

public abstract class GameState {

    protected GameStateManager gameStateManager;
    protected MayhemGame game;

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

    public abstract void render(float deltaTime);



    public abstract void dispose();
}
