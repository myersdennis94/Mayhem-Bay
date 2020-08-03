package myers.test.handlers;

import com.badlogic.gdx.Gdx;
import myers.test.MayhemGame;
import myers.test.states.GameState;
import myers.test.states.Play;
import myers.test.states.MainMenuScreen;
import myers.test.states.Score;

import java.util.Stack;

/**
 *
 */
public class GameStateManager {

    private MayhemGame game;

    private Stack<GameState> gameStates;

    public static final int PLAY = 512370;
    public static final int MENU = 512322;
    public static final int SCORE = 512480;

    /**
     *
     * @param game
     */
    public GameStateManager(MayhemGame game) {
        this.game = game;
        gameStates = new Stack<GameState>();
//        pushState(PLAY);
        pushState(MENU);
    }

    /**
     *
     * @return
     */
    public MayhemGame game() {
        return game;
    }

    /**
     *
     * @param deltaTime
     */
    public void update(float deltaTime) {
        gameStates.peek().update(deltaTime);
    }

    /**
     *
     */
    public void render() {
        gameStates.peek().render(Gdx.graphics.getDeltaTime());
    }

    /**
     *
     * @param state
     * @return
     */
    private GameState getState(int state) {
        if (state == PLAY) {
            return new Play(this);
        }
        else if (state == MENU){
            return  new MainMenuScreen(this);
        }
        else if(state == SCORE){
            return new Score(this);
        }
        return null;
    }

    /**
     *
     * @param state
     */
    public void setState(int state) {
        popState();
        pushState(state);
    }

    /**
     *
     * @param state
     */
    public void pushState(int state) {
        gameStates.push(getState(state));
    }

    /**
     *
     */
    public void popState() {
        GameState gameState = gameStates.pop();
        gameState.dispose();
    }


}
