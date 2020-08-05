package group4.mayhembay.states;

import static group4.mayhembay.MayhemGame.textureAtlas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import group4.mayhembay.MayhemGame;
import group4.mayhembay.handlers.GameStateManager;

/**
 * This class is responsible for displaying and managing user ship selection.
 */
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

    // ships
    private TextureRegion shipSprite;
    private String[] shipName = {"tugboat","speedboat","submarine","tube","cargoship"};
    private int shipIter;

    // processing
    private float timeSinceLastClick = 0;

    /**
     * This constructor creates the Selection object.
     *
     * @param gameStateManager a <b><CODE>GameStateManager</CODE></b> that grants access to crucial game information.
     */
    public Selection(GameStateManager gameStateManager) {
        super(gameStateManager);

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
            case "tube":
                shipIter = 3;
                shipSprite = textureAtlas.findRegion(shipName[shipIter]);
                break;
            case "cargoship":
                shipIter = 4;
                shipSprite = textureAtlas.findRegion(shipName[shipIter]);
                break;
        }
    }

    /**
     * This method detects and handles user input in the ship selection screen.
     */
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

    /**
     * This method updates the ship selection screen.
     *
     * @param deltaTime a <b><CODE>float</CODE></b> that corresponds with the change in time in seconds.
     */
    @Override
    public void update(float deltaTime) {
        if(timeSinceLastClick > 0.2f){
            handleInput();
        }
        shipSprite = textureAtlas.findRegion(shipName[shipIter]);
        timeSinceLastClick += deltaTime;
    }

    /**
     * This method renders the objects within the ship selection screen.
     *
     * @param deltaTime a <b><CODE>float</CODE></b> corresponding to time passed in seconds used in rendering.
     */
    @Override
    public void render(float deltaTime) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        MayhemGame.background.render(deltaTime);

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
        if(shipName[shipIter] == "cargoship"){
            spriteBatch.draw(shipSprite,SCREEN_WIDTH*15/48,SCREEN_HEIGHT*5/12);
        }else{
            spriteBatch.draw(shipSprite,SCREEN_WIDTH*11/24,SCREEN_HEIGHT*5/12);
        }

        spriteBatch.end();
    }

    /**
     * This method is responsible for disposal.
     */
    @Override
    public void dispose() {

    }
}
