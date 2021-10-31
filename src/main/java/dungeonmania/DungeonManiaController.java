package dungeonmania;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.FileLoader;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.plaf.synth.SynthStyle;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.eclipse.jetty.util.IO;
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

            Gson gson = new GsonBuilder()
                .registerTypeAdapter(Entity.class, new EntityDeserializerFromDungeon())
                .create();
                
            // Generate an Id for the new dungeon
            String newDungeonId = String.valueOf(Game.getNumDungeonIds());

            // Create a new game
            currentlyAccessingGame = gson.fromJson(JSONString, Game.class);
            currentlyAccessingGame.setDungeonId(newDungeonId);
            currentlyAccessingGame.setDungeonName(dungeonName);
            currentlyAccessingGame.setGameMode(gameMode);
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
            Gson gson = new GsonBuilder()
                .registerTypeAdapter(Entity.class, new InheritanceAdapter<Entity>())
                .setPrettyPrinting()
                .create();
            
            String JSONString = gson.toJson(this.currentlyAccessingGame);
            
            String path = "src\\main\\resources\\games\\" + name + ".json";
            
            // Write to the file
            FileWriter writer = new FileWriter(path);
            writer.write(JSONString);
            writer.close();

            // Need to give the file some time to save (trust us, this is necessary)
            Thread.sleep(5000);
            
            return currentlyAccessingGame.generateDungeonResponse();

        } catch (IOException e) {
            System.out.println("An IO issue occurred");
            e.printStackTrace();
            return null;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

    public DungeonResponse loadGame(String name) throws IllegalArgumentException {
        try {
            
            // Make sure that the file exists
            if (!FileLoader.listFileNamesInResourceDirectory("/games").contains(name)) {
                throw new IllegalArgumentException(name +  "is not a valid saved game name");
            }
            
            // Load the JSON string from the saved file
            String JSONString = FileLoader.loadResourceFile("/games/" + name + ".json");
            
            Gson gson = new GsonBuilder()
            .registerTypeAdapter(Entity.class, new InheritanceAdapter<Entity>())
            .create();

            // Load the game
            this.currentlyAccessingGame = gson.fromJson(JSONString, Game.class);

            return currentlyAccessingGame.generateDungeonResponse();

        } catch (IOException e) {
            throw new IllegalArgumentException("Directory path is invalid or some other IO issue occured");
        }
    }
    
    public List<String> allGames() {
        try {
            return FileLoader.listFileNamesInResourceDirectory("/games");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public DungeonResponse tick(String itemUsed, Direction movementDirection) throws IllegalArgumentException, InvalidActionException {
        return currentlyAccessingGame.tick(itemUsed, movementDirection);
    }
    public DungeonResponse interact(String entityId) throws IllegalArgumentException, InvalidActionException {
        return null;
    }
    public DungeonResponse build(String buildable) throws IllegalArgumentException, InvalidActionException {
        return null;
    }
    public Game getCurrentlyAccessingGame() {
        return currentlyAccessingGame;
    }

    
}
