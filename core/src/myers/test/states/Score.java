package myers.test.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.Align;
import myers.test.MayhemGame;
import myers.test.entities.Player;
import myers.test.handlers.GameStateManager;

import java.util.Locale;

/**
 *
 */
public class Score extends GameState{

    BitmapFont font;
    float hudVerticalMargin, hudLeftX, hudRightX, hudCenterX, hudRow1Y, hudRow2Y, hudRow3Y, hudRow4Y, hudSectionWidth;

    private TextureRegion mainMenuActive, mainMenuInactive;
    private final int SCREEN_HEIGHT = Gdx.graphics.getHeight();
    private final int SCREEN_WIDTH = Gdx.graphics.getWidth();
    private final int BUTTON_WIDTH = SCREEN_WIDTH / 2;
    private final int BUTTON_HEIGHT = SCREEN_HEIGHT / 10;
    private float number = 500;
    private Player player;

    /**
     *
     * @param gameStateManager
     */
    public Score(GameStateManager gameStateManager) {
        super(gameStateManager);
        mainMenuActive  = MayhemGame.textureAtlas.findRegion("main_menu_yellow_button00");
        mainMenuInactive = MayhemGame.textureAtlas.findRegion("main_menu_yellow_button05");
        prepareHUD();
    }

    /**
     *
     */
    @Override
    public void handleInput() {

    }

    /**
     *
     * @param deltaTime
     */
    @Override
    public void update(float deltaTime) {

    }

    /**
     *
     * @param deltaTime
     */
    @Override
    public void render(float deltaTime) {
        Gdx.gl.glClearColor(255, 255 , 255, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        updateAndRenderHUD(deltaTime);

        spriteBatch.begin();


        int mainMenu_x = (SCREEN_WIDTH/2) - (BUTTON_WIDTH/2);
        float mainMenu_y = (float) (SCREEN_HEIGHT /5);

        if(Gdx.input.getX() < SCREEN_WIDTH/2 - (BUTTON_WIDTH/2) + BUTTON_WIDTH && Gdx.input.getX() > SCREEN_WIDTH/2 - (BUTTON_WIDTH/2) && SCREEN_HEIGHT-Gdx.input.getY() < (float) (SCREEN_HEIGHT / 5) + BUTTON_HEIGHT && SCREEN_HEIGHT - Gdx.input.getY() > (float) (SCREEN_HEIGHT / 5)){
            spriteBatch.draw(mainMenuInactive, mainMenu_x,  mainMenu_y, BUTTON_WIDTH, BUTTON_HEIGHT);
            if(Gdx.input.isTouched()){
                this.dispose();
                gameStateManager.setState(GameStateManager.MENU);
            }
        }
        else{
            spriteBatch.draw(mainMenuActive, mainMenu_x,  mainMenu_y, BUTTON_WIDTH, BUTTON_HEIGHT);
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
     */
    private void prepareHUD(){
        // create a BitmapFont from font file
        FreeTypeFontGenerator fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("Kalmansk-51WVB.otf"));
        FreeTypeFontGenerator.FreeTypeFontParameter fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();

        fontParameter.size = 48;
        fontParameter.borderWidth = 3.6f;
        fontParameter.color = new Color(1,1,0,1.0f);
        fontParameter.borderColor = new Color(0,0,0,1.0f);

        font = fontGenerator.generateFont(fontParameter);

        // scale font to fit world
        font.getData().setScale(MayhemGame.SCALE);

        // calculate hud margins
        hudVerticalMargin = font.getCapHeight()/2;
        hudLeftX = MayhemGame.VIRTUAL_WIDTH*MayhemGame.SCALE/3;
        hudRightX = 0;
        hudCenterX = MayhemGame.VIRTUAL_WIDTH*MayhemGame.SCALE/3;
        hudRow1Y = 7*MayhemGame.VIRTUAL_HEIGHT*MayhemGame.SCALE/8;
        hudRow2Y = 25*MayhemGame.VIRTUAL_HEIGHT*MayhemGame.SCALE/32;
        hudRow3Y = 5*MayhemGame.VIRTUAL_HEIGHT*MayhemGame.SCALE/8;
        hudRow4Y = 17*MayhemGame.VIRTUAL_HEIGHT*MayhemGame.SCALE/32;
        hudSectionWidth = MayhemGame.VIRTUAL_WIDTH*MayhemGame.SCALE/2;
    }

    /**
     *
     * @param deltaTime
     */
    private void updateAndRenderHUD(float deltaTime){
        spriteBatch.begin();
        //first row - score title
        font.draw(spriteBatch, "Score", hudCenterX, hudRow1Y, hudSectionWidth, Align.left, false);
        //font.draw(spriteBatch,"Time",hudCenterX,hudRow1Y,hudSectionWidth,Align.center,false);

        //second row - score value
        font.draw(spriteBatch,String.format(Locale.getDefault(),"%06d",MayhemGame.gameDataManager.gameData.getLastScore()),hudCenterX,hudRow2Y,hudSectionWidth,Align.left,false);

        // third row - time title
        font.draw(spriteBatch,"Time",hudCenterX,hudRow3Y,hudSectionWidth,Align.left,false);

        // fourth row - time value
        font.draw(spriteBatch,String.format(Locale.getDefault(),"%5.1f",MayhemGame.gameDataManager.gameData.getLastTime())+" s",hudCenterX,hudRow4Y,hudSectionWidth,Align.left,false);

        spriteBatch.end();
    }

}
