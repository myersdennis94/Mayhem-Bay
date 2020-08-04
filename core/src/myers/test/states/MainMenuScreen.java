package myers.test.states;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import myers.test.handlers.GameData;
import myers.test.handlers.GameDataManager;
import myers.test.handlers.GameStateManager;
import myers.test.MayhemGame;

import java.util.HashMap;
import java.util.Map;

import static myers.test.MayhemGame.textureAtlas;

/**
 *
 */
public class MainMenuScreen extends GameState {

    private TextureRegion gameTitle, startButtonActive, startButtonInactive, exitButtonActive, exitButtonInactive,
            scoreButtonActive, scoreButtonInactive;

    private final int SCREEN_HEIGHT = Gdx.graphics.getHeight();
    private final int SCREEN_WIDTH = Gdx.graphics.getWidth();
    private final int BUTTON_WIDTH = SCREEN_WIDTH / 2;
    private final int BUTTON_HEIGHT = SCREEN_HEIGHT / 10;
    private final float TITLE_BUTTON_WIDTH = (float) (SCREEN_WIDTH / 2);
    private final int TITLE_BUTTON_HEIGHT = SCREEN_HEIGHT / 7;


    // background
    private TextureRegion[] backgrounds;
    private float[] backgroundOffsets = {0,0,0,0};
    private float backgroundMaxScrollingSpeed;

    /**
     *
     * @param gameStateManager
     */
    public MainMenuScreen(GameStateManager gameStateManager){

        super(gameStateManager);
        startButtonInactive = MayhemGame.textureAtlas.findRegion("yellow_button00");
        startButtonActive = MayhemGame.textureAtlas.findRegion("yellow_button05");
        exitButtonActive = MayhemGame.textureAtlas.findRegion("exit_yellow_button00");
        exitButtonInactive = MayhemGame.textureAtlas.findRegion("exit_yellow_button05");
        scoreButtonActive = MayhemGame.textureAtlas.findRegion("score_yellow_button05");
        scoreButtonInactive = MayhemGame.textureAtlas.findRegion("score_yellow_button00");
        gameTitle = MayhemGame.textureAtlas.findRegion("game_title");

        // background
        backgrounds = new TextureRegion[4];
        backgrounds[0] = textureAtlas.findRegion("tex_Water");
        backgrounds[1] = textureAtlas.findRegion("water2");
        backgrounds[2] = textureAtlas.findRegion("water3");
        backgrounds[3] = textureAtlas.findRegion("water4");
        backgroundMaxScrollingSpeed = (float)MayhemGame.VIRTUAL_HEIGHT*MayhemGame.SCALE / 4;
    }

    /**
     *
     */
    @Override
    public void handleInput() {
        // Start button is clicked
        if(Gdx.input.getX() < SCREEN_WIDTH/2 - (BUTTON_WIDTH/2) + BUTTON_WIDTH && Gdx.input.getX() > SCREEN_WIDTH/2 - (BUTTON_WIDTH/2) && SCREEN_HEIGHT-Gdx.input.getY() < (float) (SCREEN_HEIGHT / 1.5) + BUTTON_HEIGHT && SCREEN_HEIGHT - Gdx.input.getY() > (float) (SCREEN_HEIGHT / 1.5)){
            if(Gdx.input.isTouched()){
                this.dispose();
                gameStateManager.setState(GameStateManager.PLAY);
            }
        }

        //Score button is clicked
        if(Gdx.input.getX() < SCREEN_WIDTH/2 - (BUTTON_WIDTH/2) + BUTTON_WIDTH && Gdx.input.getX() > SCREEN_WIDTH/2 - (BUTTON_WIDTH/2) && SCREEN_HEIGHT-Gdx.input.getY() < (float) (SCREEN_HEIGHT / 2) + BUTTON_HEIGHT && SCREEN_HEIGHT - Gdx.input.getY() > (float) (SCREEN_HEIGHT / 2)){
            if(Gdx.input.isTouched()){
                this.dispose();

                //Game data is access using GameDataManager and getting the instance
                MayhemGame.gameDataManager.gameData.printInfo();
                gameStateManager.setState(GameStateManager.SCORE);
            }
        }

        // Exit button is clicked
        if(Gdx.input.getX() < SCREEN_WIDTH/2 - (BUTTON_WIDTH/2) + BUTTON_WIDTH && Gdx.input.getX() > SCREEN_WIDTH/2 - (BUTTON_WIDTH/2) && SCREEN_HEIGHT-Gdx.input.getY() < (float) (SCREEN_HEIGHT / 3) + BUTTON_HEIGHT && SCREEN_HEIGHT - Gdx.input.getY() > (float) (SCREEN_HEIGHT / 3)){
            if(Gdx.input.isTouched()){
                Gdx.app.exit();
            }
        }
    }

    /**
     *
     * @param deltaTime
     */
    @Override
    public void update(float deltaTime) {
        handleInput();
    }

    /**
     *
     * @param deltaTime
     */
    @Override
    public void render(float deltaTime) {

//        Gdx.gl.glClearColor(255, 255 , 255, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        renderBackground(deltaTime);

        spriteBatch.begin();

        int title_x = (SCREEN_WIDTH/2) - (int)(TITLE_BUTTON_WIDTH/2);
        float title_y = (float) (SCREEN_HEIGHT/ 1.2);

        spriteBatch.draw(gameTitle, title_x,  title_y, TITLE_BUTTON_WIDTH, TITLE_BUTTON_HEIGHT);

        int start_x = (SCREEN_WIDTH/2) - (BUTTON_WIDTH/2);
        float start_y = (float) (SCREEN_HEIGHT / 1.5);

        if(Gdx.input.getX() < SCREEN_WIDTH/2 - (BUTTON_WIDTH/2) + BUTTON_WIDTH && Gdx.input.getX() > SCREEN_WIDTH/2 - (BUTTON_WIDTH/2) && SCREEN_HEIGHT-Gdx.input.getY() < (float) (SCREEN_HEIGHT / 1.5) + BUTTON_HEIGHT && SCREEN_HEIGHT - Gdx.input.getY() > (float) (SCREEN_HEIGHT / 1.5)){
            spriteBatch.draw(startButtonActive, start_x,  start_y, BUTTON_WIDTH, BUTTON_HEIGHT);
        }
        else{
            spriteBatch.draw(startButtonInactive, start_x,  start_y, BUTTON_WIDTH, BUTTON_HEIGHT);
        }


        int score_x = (SCREEN_WIDTH/2) - (BUTTON_WIDTH/2);
        float score_y = (float) (SCREEN_HEIGHT / 2);

        if(Gdx.input.getX() < SCREEN_WIDTH/2 - (BUTTON_WIDTH/2) + BUTTON_WIDTH && Gdx.input.getX() > SCREEN_WIDTH/2 - (BUTTON_WIDTH/2) && SCREEN_HEIGHT-Gdx.input.getY() < (float) (SCREEN_HEIGHT / 2) + BUTTON_HEIGHT && SCREEN_HEIGHT - Gdx.input.getY() > (float) (SCREEN_HEIGHT / 2)){
            spriteBatch.draw(scoreButtonInactive, score_x,  score_y, BUTTON_WIDTH, BUTTON_HEIGHT);
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

    /**
     *
     */
    @Override
    public void dispose() {

    }
    /**
     *
     * @param deltaTime
     */
    private void renderBackground(float deltaTime){
        spriteBatch.begin();

        backgroundOffsets[0] += deltaTime * backgroundMaxScrollingSpeed / 8;
        backgroundOffsets[1] += deltaTime * backgroundMaxScrollingSpeed / 4;
        backgroundOffsets[2] += deltaTime * backgroundMaxScrollingSpeed / 2;
        backgroundOffsets[3] += deltaTime * backgroundMaxScrollingSpeed;

        for(int layer = 0; layer < backgroundOffsets.length; layer++){
            if(backgroundOffsets[layer] > MayhemGame.VIRTUAL_HEIGHT*MayhemGame.SCALE){
                backgroundOffsets[layer] = 0;
            }
            spriteBatch.draw(backgrounds[layer],0,-backgroundOffsets[layer],
                    MayhemGame.VIRTUAL_WIDTH*MayhemGame.SCALE,MayhemGame.VIRTUAL_HEIGHT*MayhemGame.SCALE);
            spriteBatch.draw(backgrounds[layer],0,-backgroundOffsets[layer]+
                    MayhemGame.VIRTUAL_HEIGHT*MayhemGame.SCALE,MayhemGame.VIRTUAL_WIDTH*MayhemGame.SCALE,MayhemGame.VIRTUAL_HEIGHT*MayhemGame.SCALE);
        }
        spriteBatch.end();
    }
}

