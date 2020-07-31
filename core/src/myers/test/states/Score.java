package myers.test.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import myers.test.MayhemGame;
import myers.test.handlers.GameStateManager;

public class Score extends GameState{

    private Texture mainMenuActive, mainMenuInactive;
    private final int SCREEN_HEIGHT = Gdx.graphics.getHeight();
    private final int SCREEN_WIDTH = Gdx.graphics.getWidth();
    private  final int BUTTON_WIDTH = SCREEN_WIDTH / 2;
    private final int BUTTON_HEIGHT = SCREEN_HEIGHT / 10;

    private World world;

    public Score(GameStateManager gameStateManager) {
        super(gameStateManager);
        mainMenuActive  = new Texture("main_menu_yellow_button00.png");
        mainMenuInactive = new Texture("main_menu_yellow_button05.png");

        world = new World(new Vector2(0, 0), true);

    }

    @Override
    public void handleInput() {

    }

    @Override
    public void update(float deltaTime) {
        world.step(deltaTime, 6, 6);

    }

    @Override
    public void render(float deltaTime) {
        Gdx.gl.glClearColor(255, 255 , 255, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
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

    @Override
    public void dispose() {

    }
}
