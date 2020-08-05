package myers.test.states;

import static myers.test.MayhemGame.textureAtlas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import myers.test.MayhemGame;
import myers.test.handlers.GameStateManager;

public class Selection extends GameState {

    // button sprites
    private TextureRegion mainMenuActive, mainMenuInactive, leftButtonActive, leftButtonInactive,
        rightButtonActive, rightButtonInactive;

    // screen dimensions
    private final int SCREEN_HEIGHT = Gdx.graphics.getHeight();
    private final int SCREEN_WIDTH = Gdx.graphics.getWidth();
    private final int BUTTON_WIDTH = SCREEN_WIDTH / 4;
    private final int BUTTON_HEIGHT = SCREEN_HEIGHT / 20;

    // button positioning
    private float mainMenuX = SCREEN_WIDTH/10;
    private float mainMenuY = 7*SCREEN_HEIGHT/8;
    private float leftButtonX = SCREEN_WIDTH/12;
    private float leftButtonY = 5*SCREEN_HEIGHT/12;
    private float rightButtonX = 10*SCREEN_WIDTH/12;
    private float rightButtonY = 5*SCREEN_HEIGHT/12;

    // background
    private TextureRegion[] backgrounds;
    private float[]  backgroundOffsets = {0,0,0,0};
    private float backgroundMaxScrollingSpeed;

    // ships
    private TextureRegion shipSprite;
    private String[] shipName = {"tugboat","speedboat","submarine"};
    private int shipIter;

    // processing
    private float timeSinceLastClick = 0;

    /**
     * @param gameStateManager
     */
    public Selection(GameStateManager gameStateManager) {
        super(gameStateManager);

        backgrounds = new TextureRegion[4];
        backgrounds[0] = textureAtlas.findRegion("tex_Water");
        backgrounds[1] = textureAtlas.findRegion("water2");
        backgrounds[2] = textureAtlas.findRegion("water3");
        backgrounds[3] = textureAtlas.findRegion("water4");
        backgroundMaxScrollingSpeed = (float) MayhemGame.VIRTUAL_HEIGHT*MayhemGame.SCALE / 4;

        mainMenuActive  = textureAtlas.findRegion("main_menu_yellow_button00");
        mainMenuInactive = textureAtlas.findRegion("main_menu_yellow_button05");
        leftButtonActive = textureAtlas.findRegion("left");
        leftButtonInactive = textureAtlas.findRegion("leftdown");
        rightButtonActive = textureAtlas.findRegion("right");
        rightButtonInactive = textureAtlas.findRegion("rightdown");

        switch(MayhemGame.gameDataManager.gameData.getShip()){
            case "tugboat":
                shipIter = 0;
                shipSprite = textureAtlas.findRegion(shipName[shipIter]);
                break;
            case "speedboat":
                shipIter = 1;
                shipSprite = textureAtlas.findRegion(shipName[shipIter]);
                break;
            case "submarine":
                shipIter = 2;
                shipSprite = textureAtlas.findRegion(shipName[shipIter]);
                break;
        }
    }

    @Override
    public void handleInput() {
        if(Gdx.input.getX() < leftButtonX + BUTTON_HEIGHT && Gdx.input.getX() > leftButtonX && SCREEN_HEIGHT-Gdx.input.getY() < leftButtonY + BUTTON_HEIGHT && SCREEN_HEIGHT - Gdx.input.getY() > leftButtonY){
            if(Gdx.input.isTouched()){
                if(shipIter - 1 < 0){
                    shipIter = shipName.length - 1;
                }else{
                    shipIter--;
                }
                timeSinceLastClick = 0f;
            }
        }

        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            if(shipIter - 1 < 0){
                shipIter = shipName.length - 1;
            }else{
                shipIter--;
            }
            timeSinceLastClick = 0f;
        }

        if(Gdx.input.getX() > rightButtonX && Gdx.input.getX() < rightButtonX + BUTTON_HEIGHT && SCREEN_HEIGHT - Gdx.input.getY() < rightButtonY + BUTTON_HEIGHT && SCREEN_HEIGHT - Gdx.input.getY() > rightButtonY){
            if(Gdx.input.isTouched()){
                if(shipIter + 1 >= shipName.length){
                    shipIter = 0;
                }else{
                    shipIter++;
                }
                timeSinceLastClick = 0f;
            }
        }

        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            if(shipIter + 1 >= shipName.length){
                shipIter = 0;
            }else{
                shipIter++;
            }
            timeSinceLastClick = 0f;
        }

        if(Gdx.input.getX() < mainMenuX + BUTTON_WIDTH && Gdx.input.getX() > mainMenuX && SCREEN_HEIGHT-Gdx.input.getY() <  mainMenuY + BUTTON_HEIGHT && SCREEN_HEIGHT - Gdx.input.getY() >  mainMenuY){
            if(Gdx.input.isTouched()){
                MayhemGame.gameDataManager.gameData.setShip(shipName[shipIter]);
                gameStateManager.setState(GameStateManager.MENU);
            }
        }
    }

    @Override
    public void update(float deltaTime) {
        if(timeSinceLastClick > 0.2f){
            handleInput();
        }
        shipSprite = textureAtlas.findRegion(shipName[shipIter]);
        timeSinceLastClick += deltaTime;
    }

    @Override
    public void render(float deltaTime) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderBackground(deltaTime);

        spriteBatch.begin();

        if(Gdx.input.getX() < mainMenuX + BUTTON_WIDTH && Gdx.input.getX() > mainMenuX && SCREEN_HEIGHT-Gdx.input.getY() <  mainMenuY + BUTTON_HEIGHT && SCREEN_HEIGHT - Gdx.input.getY() >  mainMenuY){
            spriteBatch.draw(mainMenuInactive,mainMenuX,mainMenuY,BUTTON_WIDTH,BUTTON_HEIGHT);
        }else{
            spriteBatch.draw(mainMenuActive,mainMenuX,mainMenuY,BUTTON_WIDTH,BUTTON_HEIGHT);
        }

        if(Gdx.input.getX() < leftButtonX + BUTTON_HEIGHT && Gdx.input.getX() > leftButtonX && SCREEN_HEIGHT-Gdx.input.getY() < leftButtonY + BUTTON_HEIGHT && SCREEN_HEIGHT - Gdx.input.getY() > leftButtonY){
            spriteBatch.draw(leftButtonInactive,leftButtonX,leftButtonY,BUTTON_HEIGHT,BUTTON_HEIGHT);
        }else{
            spriteBatch.draw(leftButtonActive,leftButtonX,leftButtonY,BUTTON_HEIGHT,BUTTON_HEIGHT);
        }

        if(Gdx.input.getX() > rightButtonX && Gdx.input.getX() < rightButtonX + BUTTON_HEIGHT && SCREEN_HEIGHT - Gdx.input.getY() < rightButtonY + BUTTON_HEIGHT && SCREEN_HEIGHT - Gdx.input.getY() > rightButtonY){
            spriteBatch.draw(rightButtonInactive,rightButtonX,rightButtonY,BUTTON_HEIGHT,BUTTON_HEIGHT);
        }else{
            spriteBatch.draw(rightButtonActive,rightButtonX,rightButtonY,BUTTON_HEIGHT,BUTTON_HEIGHT);
        }

        spriteBatch.draw(shipSprite,SCREEN_WIDTH*11/24,SCREEN_HEIGHT*5/12);

        spriteBatch.end();
    }

    @Override
    public void dispose() {

    }

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
