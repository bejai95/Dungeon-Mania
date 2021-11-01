package dungeonmania;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.FileLoader;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.plaf.synth.SynthStyle;

import java.util.stream.Collectors;

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
        
        // Deal with throwing exceptions
        if (!this.getGameModes().contains(gameMode)) {
            throw new IllegalArgumentException("Invalid gameMode argument");
        } else if (!DungeonManiaController.dungeons().contains(dungeonName)) {
            throw new IllegalArgumentException("Invalid dungeonName argument");
        }

        try {
            // Convert the entire dungeon JSON file into a JSON String
            String JSONString = FileLoader.loadResourceFile("/dungeons/" + dungeonName + ".json");

            Gson gson = new GsonBuilder()
                .registerTypeAdapter(Entity.class, new EntityDeserializerFromDungeon())
                .registerTypeAdapter(Goal.class, new GoalDeserializerFromDungeon())
                .create();
                
            // Generate an Id for the new dungeon
            String newDungeonId = String.valueOf(Game.getNumDungeonIds());

            // Create a new game
            currentlyAccessingGame = gson.fromJson(JSONString, Game.class);
            currentlyAccessingGame.setDungeonId(newDungeonId);
            currentlyAccessingGame.setDungeonName(dungeonName);
            currentlyAccessingGame.setGameMode(gameMode);
            currentlyAccessingGame.initialiseBuildables();
            Game.incrementNumDungeonIds();

            return currentlyAccessingGame.generateDungeonResponse();
        }
        catch (IOException e) {
            throw new IllegalArgumentException("An IO issue occured");
        }
    }
    
    public DungeonResponse saveGame(String name) {
        try {
            Gson gson = new GsonBuilder()
                .registerTypeAdapter(Entity.class, new InheritanceAdapter<Entity>())
                .registerTypeAdapter(Movement.class, new InheritanceAdapter<Movement>())
                .registerTypeAdapter(Goal.class, new InheritanceAdapter<Goal>())
                .setPrettyPrinting()
                .create();
            
            String JSONString = gson.toJson(this.currentlyAccessingGame);
            String path = "src\\main\\resources\\savedGames\\";

            // Create the savedGames directory if it does not already exist
            Files.createDirectories(Paths.get(path));
            
            // Write to the file
            FileWriter writer = new FileWriter(path + name + ".json");
            writer.write(JSONString);
            writer.close();
            
            return currentlyAccessingGame.generateDungeonResponse();

        } catch (IOException e) {
            System.out.println("An IO issue occurred");
            e.printStackTrace();
            return null;
        }
    }

    public DungeonResponse loadGame(String name) throws IllegalArgumentException {
        try {
            
            String path = "src\\main\\resources\\savedGames\\" + name + ".json";

            // Make sure that the file exists
            if (!FileLoader.listFileNamesInDirectoryOutsideOfResources(path).contains(name)) {
                throw new IllegalArgumentException(name +  "is not a valid saved game name");
            }
            
            // Load the JSON string from the saved file
            String JSONString = Files.readString(Paths.get(path));
            
            Gson gson = new GsonBuilder()
            .registerTypeAdapter(Entity.class, new InheritanceAdapter<Entity>())
            .registerTypeAdapter(Movement.class, new InheritanceAdapter<Movement>())
            .registerTypeAdapter(Goal.class, new InheritanceAdapter<Goal>())
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
            String path = "src\\main\\resources\\savedGames\\";
            return FileLoader.listFileNamesInDirectoryOutsideOfResources(path);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public DungeonResponse tick(String itemUsed, Direction movementDirection) throws IllegalArgumentException, InvalidActionException {
        return currentlyAccessingGame.tick(itemUsed, movementDirection);
    }
    public DungeonResponse interact(String entityId) throws IllegalArgumentException, InvalidActionException {
        Game game = getCurrentlyAccessingGame();
        Entity ent = game.getEntityById(entityId);
        if (ent == null) {
            throw new IllegalArgumentException("Id does not exist");
        } else if (!ent.canInteract()) {
            throw new IllegalArgumentException("Cannot interact with this entity");
        }

        ent.interact(game.getPlayer());

        Inventory inv = game.getPlayer().inventory;

        return new DungeonResponse(game.getDungeonId(), game.getDungeonName(), 
        game.getEntities().stream().map(x -> x.getInfo()).collect(Collectors.toList()), 
        inv.getItemsAsResponse(), inv.generateBuildables(), game.getGoalsLeft());


    }
    public DungeonResponse build(String buildable) throws IllegalArgumentException, InvalidActionException {
        return null;
    }
    public Game getCurrentlyAccessingGame() {
        return currentlyAccessingGame;
    }

    
}
