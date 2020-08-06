package group4.mayhembay.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import group4.mayhembay.handlers.GameStateManager;
import group4.mayhembay.MayhemGame;


import static group4.mayhembay.MayhemGame.textureAtlas;

/**
 * This class is a representation of the main menu
 */
public class MainMenuScreen extends GameState {

    private TextureRegion gameTitle, startButtonActive, startButtonInactive, exitButtonActive, exitButtonInactive,
            scoreButtonActive, scoreButtonInactive, selectionButtonInactive, selectionButtonActive;

    private final int SCREEN_HEIGHT = Gdx.graphics.getHeight();
    private final int SCREEN_WIDTH = Gdx.graphics.getWidth();
    private final int BUTTON_WIDTH = SCREEN_WIDTH / 2;
    private final int BUTTON_HEIGHT = SCREEN_HEIGHT / 10;
    private final float TITLE_BUTTON_WIDTH = (float) (SCREEN_WIDTH / 2);
    private final int TITLE_BUTTON_HEIGHT = SCREEN_HEIGHT / 7;

    /**
     * This constructor initializes the MainMenuScreen object
     *
     * @param gameStateManager a GameStateManager that contains the state of
     *                         MainMenuScreen and information of other states
     */
    public MainMenuScreen(GameStateManager gameStateManager){

        super(gameStateManager);
        startButtonInactive = textureAtlas.findRegion("yellow_button00");
        startButtonActive = textureAtlas.findRegion("yellow_button05");
        exitButtonActive = textureAtlas.findRegion("exit_yellow_button00");
        exitButtonInactive = textureAtlas.findRegion("exit_yellow_button05");
        scoreButtonActive = textureAtlas.findRegion("score_yellow_button05");
        scoreButtonInactive = textureAtlas.findRegion("score_yellow_button00");
        selectionButtonActive = textureAtlas.findRegion("ship_selection_button05");
        selectionButtonInactive = textureAtlas.findRegion("ship_selection_button00");
        gameTitle = MayhemGame.textureAtlas.findRegion("game_title");
    }

    /**
     * This method handles user input on the main menu
     *
     */
    @Override
    public void handleInput() {
        // Start button is clicked
        if(Gdx.input.getX() < SCREEN_WIDTH/2 - (BUTTON_WIDTH/2) + BUTTON_WIDTH && Gdx.input.getX() > SCREEN_WIDTH/2 - (BUTTON_WIDTH/2) && SCREEN_HEIGHT-Gdx.input.getY() < (float) (SCREEN_HEIGHT / 1.5) + BUTTON_HEIGHT && SCREEN_HEIGHT - Gdx.input.getY() > (float) (SCREEN_HEIGHT / 1.5)){
            if(Gdx.input.isTouched()){
                gameStateManager.setState(GameStateManager.PLAY);
            }
        }

        //Score button is clicked
        if(Gdx.input.getX() < SCREEN_WIDTH/2 - (BUTTON_WIDTH/2) + BUTTON_WIDTH && Gdx.input.getX() > SCREEN_WIDTH/2 - (BUTTON_WIDTH/2) && SCREEN_HEIGHT-Gdx.input.getY() < (float) (SCREEN_HEIGHT / 2) + BUTTON_HEIGHT && SCREEN_HEIGHT - Gdx.input.getY() > (float) (SCREEN_HEIGHT / 2)){
            if(Gdx.input.isTouched()){

                //Game data is access using GameDataManager and getting the instance
                //MayhemGame.gameDataManager.gameData.printInfo();
                gameStateManager.setState(GameStateManager.SCORE);
            }
        }

        //Ship selection button is clicked
        if(Gdx.input.getX() < SCREEN_WIDTH/2 - (BUTTON_WIDTH/2) + BUTTON_WIDTH && Gdx.input.getX() > SCREEN_WIDTH/2 - (BUTTON_WIDTH/2) && SCREEN_HEIGHT-Gdx.input.getY() < (float) (SCREEN_HEIGHT / 3) + BUTTON_HEIGHT && SCREEN_HEIGHT - Gdx.input.getY() > (float) (SCREEN_HEIGHT / 3)){
            if(Gdx.input.isTouched()){
                gameStateManager.setState(GameStateManager.SELECTION);
            }
        }

        // Exit button is clicked
        if(Gdx.input.getX() < SCREEN_WIDTH/2 - (BUTTON_WIDTH/2) + BUTTON_WIDTH && Gdx.input.getX() > SCREEN_WIDTH/2 - (BUTTON_WIDTH/2) && SCREEN_HEIGHT-Gdx.input.getY() < (float) (SCREEN_HEIGHT / 6) + BUTTON_HEIGHT && SCREEN_HEIGHT - Gdx.input.getY() > (float) (SCREEN_HEIGHT / 6)){
            if(Gdx.input.isTouched()){
                Gdx.app.exit();
            }
        }
    }

    /**
     *This method is responsible for updating the main menu enviroment
     *
     * @param deltaTime a float that corresponds to the times passed since last update
     */
    @Override
    public void update(float deltaTime) {
        handleInput();
    }

    /**
     * This method is responsible for rendering the main menu
     * @param deltaTime a float that corresponds to the times passed since last update
     */
    @Override
    public void render(float deltaTime) {

//        Gdx.gl.glClearColor(255, 255 , 255, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        MayhemGame.background.render(deltaTime);

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

        int select_x = (SCREEN_WIDTH/2) - (BUTTON_WIDTH/2);
        float select_y = (float) (SCREEN_HEIGHT /3);

        if(Gdx.input.getX() < SCREEN_WIDTH/2 - (BUTTON_WIDTH/2) + BUTTON_WIDTH && Gdx.input.getX() > SCREEN_WIDTH/2 - (BUTTON_WIDTH/2) && SCREEN_HEIGHT-Gdx.input.getY() < (float) (SCREEN_HEIGHT / 3) + BUTTON_HEIGHT && SCREEN_HEIGHT - Gdx.input.getY() > (float) (SCREEN_HEIGHT / 3)){
            spriteBatch.draw(selectionButtonInactive, select_x,  select_y, BUTTON_WIDTH, BUTTON_HEIGHT);
        }
        else{
            spriteBatch.draw(selectionButtonActive, select_x,  select_y, BUTTON_WIDTH, BUTTON_HEIGHT);
        }

        int end_x = (SCREEN_WIDTH/2) - (BUTTON_WIDTH/2);
        float end_y = (float) (SCREEN_HEIGHT / 6);

        if(Gdx.input.getX() < SCREEN_WIDTH/2 - (BUTTON_WIDTH/2) + BUTTON_WIDTH && Gdx.input.getX() > SCREEN_WIDTH/2 - (BUTTON_WIDTH/2) && SCREEN_HEIGHT-Gdx.input.getY() < (float) (SCREEN_HEIGHT / 6) + BUTTON_HEIGHT && SCREEN_HEIGHT - Gdx.input.getY() > (float) (SCREEN_HEIGHT / 6)){
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
     * This method is responsible for disposing unused objects
     */
    @Override
    public void dispose() {
    }
}

