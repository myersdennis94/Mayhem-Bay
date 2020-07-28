package myers.test.handlers;

import myers.test.MayhemGame;
import myers.test.states.GameState;
import myers.test.states.Play;

import java.util.Stack;

public class GameStateManager {

    private MayhemGame game;

    private Stack<GameState> gameStates;

    public static final int PLAY = 512370;

    public GameStateManager(MayhemGame game) {
        this.game = game;
        gameStates = new Stack<GameState>();
        pushState(PLAY);
    }

    public MayhemGame game() {
        return game;
    }

    public void update(float deltaTime) {
        gameStates.peek().update(deltaTime);
    }

    public void render() {
        gameStates.peek().render();
    }

    private GameState getState(int state) {
        if (state == PLAY) {
            return new Play(this);
        }
        return null;
    }

    public void setState(int state) {
        popState();
        pushState(state);
    }

    public void pushState(int state) {
        gameStates.push(getState(state));
    }

    public void popState() {
        GameState gameState = gameStates.pop();
        gameState.dispose();
    }
}
