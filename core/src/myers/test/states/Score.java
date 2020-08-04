package myers.test.states;

import static myers.test.MayhemGame.textureAtlas;
import myers.test.states.Play;
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
    float hudVerticalMargin, hudLeftX, hudRightX, hudCenterX, hudRow1Y, hudRow2Y, hudRow3Y, hudRow4Y, hudRow5Y, hudRow6Y, hudSectionWidth;

    private TextureRegion mainMenuActive, mainMenuInactive;
    private final int SCREEN_HEIGHT = Gdx.graphics.getHeight();
    private final int SCREEN_WIDTH = Gdx.graphics.getWidth();
    private final int BUTTON_WIDTH = SCREEN_WIDTH / 2;
    private final int BUTTON_HEIGHT = SCREEN_HEIGHT / 10;

    // background
    private TextureRegion[] backgrounds;
    private float[] backgroundOffsets = {0,0,0,0};
    private float backgroundMaxScrollingSpeed;



    /**
     *
     * @param gameStateManager
     */
    public Score(GameStateManager gameStateManager) {
        super(gameStateManager);

        // background
        backgrounds = new TextureRegion[4];
        backgrounds[0] = textureAtlas.findRegion("tex_Water");
        backgrounds[1] = textureAtlas.findRegion("water2");
        backgrounds[2] = textureAtlas.findRegion("water3");
        backgrounds[3] = textureAtlas.findRegion("water4");
        backgroundMaxScrollingSpeed = (float)MayhemGame.VIRTUAL_HEIGHT*MayhemGame.SCALE / 4;

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
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderBackground(deltaTime);
        updateAndRenderHUD(deltaTime);

        spriteBatch.begin();

        int mainMenu_x = (SCREEN_WIDTH/2) - (BUTTON_WIDTH/2);
        float mainMenu_y = (float) (SCREEN_HEIGHT /8);

        if(Gdx.input.getX() < SCREEN_WIDTH/2 - (BUTTON_WIDTH/2) + BUTTON_WIDTH && Gdx.input.getX() > SCREEN_WIDTH/2 - (BUTTON_WIDTH/2) && SCREEN_HEIGHT-Gdx.input.getY() < (float) (SCREEN_HEIGHT / 8) + BUTTON_HEIGHT && SCREEN_HEIGHT - Gdx.input.getY() > (float) (SCREEN_HEIGHT / 8)){
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
        hudLeftX = MayhemGame.VIRTUAL_WIDTH*MayhemGame.SCALE/4;
        hudRightX = 0;
        hudCenterX = MayhemGame.VIRTUAL_WIDTH*MayhemGame.SCALE/3;
        hudRow1Y = 10*MayhemGame.VIRTUAL_HEIGHT*MayhemGame.SCALE/12;
        hudRow2Y = 10*MayhemGame.VIRTUAL_HEIGHT*MayhemGame.SCALE/13;
        hudRow3Y = 10*MayhemGame.VIRTUAL_HEIGHT*MayhemGame.SCALE/15;
        hudRow4Y = 10*MayhemGame.VIRTUAL_HEIGHT*MayhemGame.SCALE/17;
        hudRow5Y = 10*MayhemGame.VIRTUAL_HEIGHT*MayhemGame.SCALE/21;
        hudRow6Y = 10*MayhemGame.VIRTUAL_HEIGHT*MayhemGame.SCALE/25;
        hudSectionWidth = MayhemGame.VIRTUAL_WIDTH*MayhemGame.SCALE/2;
    }

    /**
     *
     * @param deltaTime
     */
    private void updateAndRenderHUD(float deltaTime){
        spriteBatch.begin();
        //first row - score title
        font.draw(spriteBatch, "DISTANCE", hudLeftX, hudRow1Y, hudSectionWidth, Align.center, false);

        //second row - score value
        font.draw(spriteBatch,String.format(Locale.getDefault(),"%06d",MayhemGame.gameDataManager.gameData.getLastScore()),hudLeftX,hudRow2Y,hudSectionWidth,Align.center,false);

        // third row - time title
        font.draw(spriteBatch,"TIME",hudLeftX,hudRow3Y,hudSectionWidth,Align.center,false);

        // fourth row - time value
        font.draw(spriteBatch,String.format(Locale.getDefault(),"%5.1f",MayhemGame.gameDataManager.gameData.getLastTime())+"s ",hudLeftX,hudRow4Y,hudSectionWidth,Align.center,false);

        // fifth row - total title
        font.draw(spriteBatch,"SCORE",hudLeftX,hudRow5Y,hudSectionWidth,Align.center,false);

        // sixth row - total score value
        font.draw(spriteBatch,String.format(Locale.getDefault(),"%06d",MayhemGame.gameDataManager.gameData.getTotalScoreScore()),hudLeftX,hudRow6Y,hudSectionWidth,Align.center,false);

        spriteBatch.end();
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
