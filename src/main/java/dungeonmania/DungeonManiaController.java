package dungeonmania;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.FileLoader;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONObject;

public class DungeonManiaController {
    private Game currentlyAccessingGame;
    
    public DungeonManiaController() {
        currentlyAccessingGame = null;
    }
    public String getSkin() {
        return "default";
    }
    public String getLocalisation() {
        return "en_US";
    }
    public List<String> getGameModes() {
        return Arrays.asList("Standard", "Peaceful", "Hard");
    }
    /**
     * /dungeons
     * 
     * Done for you.
     */
    public static List<String> dungeons() {
        try {
            return FileLoader.listFileNamesInResourceDirectory("/dungeons");
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }
    public DungeonResponse newGame(String dungeonName, String gameMode) throws IllegalArgumentException {
        
        // Get the name of the the dungeon with no extension
        String dungeonNameNoExtension = dungeonName;
        int pos = dungeonName.lastIndexOf(".");
        if (pos > 0 && pos < (dungeonName.length() -1)) { // If '.' is not the first or last character
            dungeonNameNoExtension = dungeonName.substring(0, pos);
        }
        
        // Deal with throwing exceptions
        if (!this.getGameModes().contains(gameMode)) {
            throw new IllegalArgumentException("Invalid gameMode argument");
        } else if (!DungeonManiaController.dungeons().contains(dungeonNameNoExtension)) {
            throw new IllegalArgumentException("Invalid dungeonName argument");
        }

        try {
            // Convert the entire dungeon JSON file into a JSON String
            String JSONString = FileLoader.loadResourceFile("/dungeons/" + dungeonName);

            GsonBuilder gsonBuilder = new GsonBuilder();
            Gson gson = gsonBuilder.create();

            // Generate an Id for the new dungeon
            String newDungeonId = String.valueOf(Game.getNumDungeonIds());

            // Create a new game
            currentlyAccessingGame = gson.fromJson(JSONString, Game.class);
            currentlyAccessingGame.setDungeonId(newDungeonId);
            currentlyAccessingGame.setDungeonName(dungeonName);
            currentlyAccessingGame.initializeInventoryAndBuildables();
            Game.incrementNumDungeonIds();

            return currentlyAccessingGame.generateDungeonResponse();
        }
        catch (IOException e) {
            throw new IllegalArgumentException("Dungeon name does exist (without extension) but incorrect filename");
        }
    }
    
    public DungeonResponse saveGame(String name) {
        try {
            GsonBuilder gsonBuilder = new GsonBuilder();
            Gson gson = gsonBuilder.setPrettyPrinting().create();
            String JSONString = gson.toJson(this.currentlyAccessingGame);
            
            String path = "src\\main\\resources\\games\\" + name + ".json";
            
            // Create a new file if the file doesn't exist (if the file does exist then we just want to overwrite it)
            File newFile = new File(path);
            newFile.createNewFile();

            // Now write to the file that we just created
            FileWriter newFileWriter = new FileWriter(path);
            newFileWriter.write(JSONString);
            newFileWriter.close();

            return currentlyAccessingGame.generateDungeonResponse();

        } catch (IOException e) {
            System.out.println("An IO issue occurred");
            e.printStackTrace();
            return null;
        }
    }
    
    public DungeonResponse loadGame(String name) throws IllegalArgumentException {
        try {
            
            // Make sure that the file exists
            if (!FileLoader.listFileNamesInResourceDirectory("/games").contains(name)) {
                throw new IllegalArgumentException("name is not a valid saved game name");
            }

            // Load the JSON string from the saved file
            String JSONString = FileLoader.loadResourceFile("/games/" + name + ".json");
            
            GsonBuilder gsonBuilder = new GsonBuilder();
            Gson gson = gsonBuilder.create();

            // Load the game
            this.currentlyAccessingGame = gson.fromJson(JSONString, Game.class);

            return currentlyAccessingGame.generateDungeonResponse();

        } catch (IOException e) {
            throw new IllegalArgumentException("Directory path is invalid or some other IO issue occured");
        }
    }
    
    public List<String> allGames() {
        return new ArrayList<>();
    }
    public DungeonResponse tick(String itemUsed, Direction movementDirection) throws IllegalArgumentException, InvalidActionException {
        return null;
    }
    public DungeonResponse interact(String entityId) throws IllegalArgumentException, InvalidActionException {
        return null;
    }
    public DungeonResponse build(String buildable) throws IllegalArgumentException, InvalidActionException {
        return null;
    }
}
