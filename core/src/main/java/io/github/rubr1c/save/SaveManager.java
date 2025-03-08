package io.github.rubr1c.save;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;

import java.io.File;

public class SaveManager {
    private static final String SAVE_FOLDER = System.getenv("APPDATA") + File.separator + "Platformer";
    private static final String SAVE_FILE = SAVE_FOLDER + File.separator + "save.json";
    private static final Json json = new Json();

    static {
        json.setOutputType(JsonWriter.OutputType.json);
        createSaveDirectory();
    }

    private static void createSaveDirectory() {
        File directory = new File(SAVE_FOLDER);
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }

    public static void saveGame(GameData data) {
        try {
            FileHandle file = Gdx.files.absolute(SAVE_FILE);
            String jsonStr = json.prettyPrint(data);
            file.writeString(jsonStr, false);
        } catch (Exception e) {
            Gdx.app.error("SaveManager", "Failed to save game", e);
        }
    }

    public static GameData loadGame() {
        try {
            FileHandle file = Gdx.files.absolute(SAVE_FILE);
            if (file.exists()) {
                String jsonStr = file.readString();
                return json.fromJson(GameData.class, jsonStr);
            }
        } catch (Exception e) {
            Gdx.app.error("SaveManager", "Failed to load game", e);
        }
        return new GameData(); // Return default data if loading fails
    }
}