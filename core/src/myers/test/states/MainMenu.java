package myers.test.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import myers.test.handlers.GameManager;
import myers.test.handlers.GameStateManager;
import myers.test.MayhemGame;


public class MainMenu extends GameState {

    private TextureRegion gameTitle, startButtonActive, startButtonInactive, exitButtonActive, exitButtonInactive,
    scoreButtonActive, scoreButtonInactive;

    private final int SCREEN_HEIGHT = Gdx.graphics.getHeight();
    private final int SCREEN_WIDTH = Gdx.graphics.getWidth();
    private final int BUTTON_WIDTH = SCREEN_WIDTH / 2;
    private final int BUTTON_HEIGHT = SCREEN_HEIGHT / 10;
    private final float TITLE_BUTTON_WIDTH = (float) (SCREEN_WIDTH / 2);
    private final int TITLE_BUTTON_HEIGHT = SCREEN_HEIGHT / 7;

    private GameManager gameManager;


    public MainMenu(GameStateManager gameStateManager){

        super(gameStateManager);
        startButtonInactive = MayhemGame.textureAtlas.findRegion("yellow_button00");
        startButtonActive = MayhemGame.textureAtlas.findRegion("yellow_button05");
        exitButtonActive = MayhemGame.textureAtlas.findRegion("exit_yellow_button00");
        exitButtonInactive = MayhemGame.textureAtlas.findRegion("exit_yellow_button05");
        scoreButtonActive = MayhemGame.textureAtlas.findRegion("score_yellow_button05");
        scoreButtonInactive = MayhemGame.textureAtlas.findRegion("score_yellow_button00");
        gameTitle = MayhemGame.textureAtlas.findRegion("game_title");
        gameManager = gameStateManager.game().getGameManager();

    }

    @Override
    public void handleInput() {

    }

    @Override
    public void update(float deltaTime) {

    }

    @Override
    public void render(float deltaTime) {

        Gdx.gl.glClearColor(255, 255 , 255, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        spriteBatch.begin();

        int title_x = (SCREEN_WIDTH/2) - (int)(TITLE_BUTTON_WIDTH/2);
        float title_y = (float) (SCREEN_HEIGHT/ 1.2);

        spriteBatch.draw(gameTitle, title_x,  title_y, TITLE_BUTTON_WIDTH, TITLE_BUTTON_HEIGHT);

        int start_x = (SCREEN_WIDTH/2) - (BUTTON_WIDTH/2);
        float start_y = (float) (SCREEN_HEIGHT / 1.5);

        if(Gdx.input.getX() < SCREEN_WIDTH/2 - (BUTTON_WIDTH/2) + BUTTON_WIDTH && Gdx.input.getX() > SCREEN_WIDTH/2 - (BUTTON_WIDTH/2) && SCREEN_HEIGHT-Gdx.input.getY() < (float) (SCREEN_HEIGHT / 1.5) + BUTTON_HEIGHT && SCREEN_HEIGHT - Gdx.input.getY() > (float) (SCREEN_HEIGHT / 1.5)){
            spriteBatch.draw(startButtonActive, start_x,  start_y, BUTTON_WIDTH, BUTTON_HEIGHT);
            if(Gdx.input.isTouched()){
                this.dispose();
                gameStateManager.setState(GameStateManager.PLAY);
            }
        }
        else{
           spriteBatch.draw(startButtonInactive, start_x,  start_y, BUTTON_WIDTH, BUTTON_HEIGHT);
        }


        int score_x = (SCREEN_WIDTH/2) - (BUTTON_WIDTH/2);
        float score_y = (float) (SCREEN_HEIGHT / 2);

        if(Gdx.input.getX() < SCREEN_WIDTH/2 - (BUTTON_WIDTH/2) + BUTTON_WIDTH && Gdx.input.getX() > SCREEN_WIDTH/2 - (BUTTON_WIDTH/2) && SCREEN_HEIGHT-Gdx.input.getY() < (float) (SCREEN_HEIGHT / 2) + BUTTON_HEIGHT && SCREEN_HEIGHT - Gdx.input.getY() > (float) (SCREEN_HEIGHT / 2)){
            spriteBatch.draw(scoreButtonInactive, score_x,  score_y, BUTTON_WIDTH, BUTTON_HEIGHT);
            if(Gdx.input.isTouched()){
                this.dispose();
                gameStateManager.setState(GameStateManager.SCORE);
            }
        }
        else{
            spriteBatch.draw(scoreButtonActive, score_x,  score_y, BUTTON_WIDTH, BUTTON_HEIGHT);
        }

        int end_x = (SCREEN_WIDTH/2) - (BUTTON_WIDTH/2);
        float end_y = (float) (SCREEN_HEIGHT /3);

        if(Gdx.input.getX() < SCREEN_WIDTH/2 - (BUTTON_WIDTH/2) + BUTTON_WIDTH && Gdx.input.getX() > SCREEN_WIDTH/2 - (BUTTON_WIDTH/2) && SCREEN_HEIGHT-Gdx.input.getY() < (float) (SCREEN_HEIGHT / 3) + BUTTON_HEIGHT && SCREEN_HEIGHT - Gdx.input.getY() > (float) (SCREEN_HEIGHT / 3)){
            spriteBatch.draw(exitButtonInactive, end_x,  end_y, BUTTON_WIDTH, BUTTON_HEIGHT);
            if(Gdx.input.isTouched()){
                Gdx.app.exit();
            }
        }
        else{
            spriteBatch.draw(exitButtonActive, end_x,  end_y, BUTTON_WIDTH, BUTTON_HEIGHT);
        }

        spriteBatch.end();
    }

    @Override
    public void dispose() {

    }
}
