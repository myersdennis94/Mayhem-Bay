package myers.test.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import myers.test.handlers.GameStateManager;

public class Score extends GameState{

    public Score(GameStateManager gameStateManager) {
        super(gameStateManager);
    }

    @Override
    public void handleInput() {

    }

    @Override
    public void update(float deltaTime) {

    }

    @Override
    public void render(float deltaTime) {
        // clear screen
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);

        spriteBatch.begin();
        spriteBatch.end();
    }

    @Override
    public void dispose() {

    }
}
