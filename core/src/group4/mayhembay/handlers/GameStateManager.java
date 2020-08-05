package group4.mayhembay.handlers;

import com.badlogic.gdx.Gdx;
import group4.mayhembay.MayhemGame;
import group4.mayhembay.states.*;

import java.util.Stack;

/**
 * This class contains the methods for managing the game state.
 */
public class GameStateManager {

    private MayhemGame game;

    private Stack<GameState> gameStates;

    public static final int PLAY = 512370;
    public static final int MENU = 512322;
    public static final int SCORE = 512480;
    public static final int SELECTION = 512361;

    /**
     * This constructor creates a GameStateManager object.
     *
     * @param game <b><CODE>object</CODE></b> created by MayhemGame.
     */
    public GameStateManager(MayhemGame game) {
        this.game = game;
        gameStates = new Stack<GameState>();
        pushState(MENU);
    }

    /**
     * This method returns a game object.
     *
     * @return a <b><CODE>game</CODE></b> object.
     */
    public MayhemGame game() {
        return game;
    }

    /**
     * This method updates the states of the game.
     *
     * @param deltaTime a <b><CODE>float</CODE></b> referring to time passed since last update.
     */
    public void update(float deltaTime) {
        gameStates.peek().update(deltaTime);
    }

    /**
     * This method renders the
     */
    public void render() {
        gameStates.peek().render(Gdx.graphics.getDeltaTime());
    }

    /**
     * This method takes the selected and returns a new object
     *
     * @param state a <b><CODE>int</CODE></b> referring to a certain state of the game.
     *
     * @return a <b><CODE>object</CODE></b> of the new game state.
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
        else if(state == SELECTION){
            return new Selection(this);
        }
        return null;
    }

    /**
     * This method sets the state for the GameState stack.
     *
     * @param state a <b><CODE>int</CODE></b> referring to a certain state of the game.
     */
    public void setState(int state) {
        popState();
        pushState(state);
    }

    /**
     * This method pushes the game state on to the GameState stack.
     *
     * @param state a <b><CODE>int</CODE></b> referring to a certain state of the game.
     */
    public void pushState(int state) {
        gameStates.push(getState(state));
    }

    /**
     * This method removes the game state from the GameState stack.
     */
    public void popState() {
        GameState gameState = gameStates.pop();
        gameState.dispose();
    }


}
