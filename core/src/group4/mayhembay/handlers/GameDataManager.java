package group4.mayhembay.handlers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Base64Coder;
import com.badlogic.gdx.utils.Json;

/**
 * This class contains the methods for managing the game data.
 */
public class GameDataManager {

    public GameData gameData;
    private Json json = new Json();
    private FileHandle fileHandle = Gdx.files.local("bin/GameData.json");

    /**
     * This constructor creates the GameDataManager object.
     */
    public GameDataManager() {
        initializeGameData();
    }

    /**
     * This method initializes the game data for the GameDataManager object.
     */
    public void initializeGameData() {
        if (!fileHandle.exists()) {
            gameData = new GameData();

            gameData.setHighscore(0);
            gameData.setMusicOn(false);
            gameData.setLastScore(0);
            gameData.setLastTime(0);
            gameData.setBestTime(0);
            gameData.setShip("tugboat");

            saveData();
        } else {
            loadData();
        }
    }

    /**
     * This method saves game data to a file.
     */
    public void saveData() {
        if (gameData != null) {
            fileHandle.writeString(Base64Coder.encodeString(json.prettyPrint(gameData)),
                    false);
        }
    }

    /**
     * This method loads game data from a file.
     */
    public void loadData() {
        gameData = json.fromJson(GameData.class,
                Base64Coder.decodeString(fileHandle.readString()));
    }
}