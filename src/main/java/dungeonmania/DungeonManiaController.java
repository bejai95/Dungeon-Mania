package dungeonmania;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.FileLoader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
            
            // From this JSON String, get the dungeon's goals and entities
            JSONObject entireFile = new JSONObject(JSONString);
            JSONObject goals = entireFile.getJSONObject("goal-condition");
            JSONArray entities = entireFile.getJSONArray("entities");

            // Generate an Id for the new dungeon
            String newDungeonId = String.valueOf(Game.getNumDungeonIds());

            // Create a new game
            this.currentlyAccessingGame = new Game(newDungeonId, dungeonName, entities, gameMode, goals);

            // Generate the DungeonResponse object TODO complete this
            DungeonResponse ret = new DungeonResponse(currentlyAccessingGame.getDungeonId(), currentlyAccessingGame.getDungeonName(), null, null, null, currentlyAccessingGame.getGoalsLeft());
            
            return ret;
        }
        catch (IOException e) {
            throw new IllegalArgumentException("Dungeon name does exist (without extension) but incorrect filename");
        }
    }
    
    public DungeonResponse saveGame(String name) throws IllegalArgumentException {
        return null;
    }
    public DungeonResponse loadGame(String name) throws IllegalArgumentException {
        return null;
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
