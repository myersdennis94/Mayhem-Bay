package myers.test.handlers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Base64Coder;
import com.badlogic.gdx.utils.Json;

public class GameDataManager {

    public GameData gameData;
    private Json json = new Json();
    private FileHandle fileHandle = Gdx.files.local("bin/GameData.json");

    public GameDataManager() {
        initializeGameData();
    }

    public void initializeGameData() {
        if (!fileHandle.exists()) {
            gameData = new GameData();

            gameData.setHighscore(0);
            gameData.setMusicOn(false);
            gameData.setLastScore(0);
            gameData.setLastTime(0);
            gameData.setBestTime(0);

            saveData();
        } else {
            loadData();
        }
    }

    public void saveData() {
        if (gameData != null) {
            fileHandle.writeString(Base64Coder.encodeString(json.prettyPrint(gameData)),
                    false);
        }
    }

    public void loadData() {
        gameData = json.fromJson(GameData.class,
                Base64Coder.decodeString(fileHandle.readString()));
    }
}