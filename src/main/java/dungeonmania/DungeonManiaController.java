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
    private static int dungeonIdNum; // Initialized to zero
    
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
        return Arrays.asList("standard", "peaceful", "hard");
    }

    /**
     * Generates a unique dungeon id
     * @param cell
     */
    private static int generateUniqueDungeonId() {
        int ret = dungeonIdNum;
        dungeonIdNum++;
        return ret;
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
    
    /**
     * newGame
     * @param dungeonName
     * @param gameMode
     * @throws IllegalArgumentException
     */
    public DungeonResponse newGame(String dungeonName, String gameMode) throws IllegalArgumentException {
        String gameModeLowercase = gameMode.toLowerCase();

        // Deal with throwing exceptions
        if (!getGameModes().contains(gameModeLowercase)) {
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
            String newDungeonId = String.valueOf(generateUniqueDungeonId());

            // Create a new game
            currentlyAccessingGame = gson.fromJson(JSONString, Game.class);
            currentlyAccessingGame.setDungeonId(newDungeonId);
            currentlyAccessingGame.setDungeonName(dungeonName);
            currentlyAccessingGame.setGameMode(gameModeLowercase);
            currentlyAccessingGame.initialiseBuildables();
            currentlyAccessingGame.setHealthBar(1);

            //Set portal colour sprites
            currentlyAccessingGame.setSprites();

            // Make all mercenaries chase the player
            List<Mercenary> allMercenaries = currentlyAccessingGame.getMercenaries();
            Character player = currentlyAccessingGame.getPlayer();
            for (Mercenary cur: allMercenaries) {
                cur.chase(player);
            }
            
            player.setGameMode(gameModeLowercase);
            
            return currentlyAccessingGame.generateDungeonResponse();
        }
        catch (IOException e) {
            throw new IllegalArgumentException("An IO issue occured");
        }
    }
    
    /**
     * saveGame
     * @param name
     */
    public DungeonResponse saveGame(String name) {
        try {
            Gson gson = new GsonBuilder()
                .registerTypeAdapter(Entity.class, new InheritanceAdapter<Entity>())
                .registerTypeAdapter(Movement.class, new InheritanceAdapter<Movement>())
                .registerTypeAdapter(Goal.class, new InheritanceAdapter<Goal>())
                .registerTypeAdapter(Item.class, new InheritanceAdapter<Item>())
                .setPrettyPrinting()
                .create();
            
            String JSONString = gson.toJson(this.currentlyAccessingGame);
            String path = "savedGames/";

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

    /**
     * loadGame
     * @param name
     * @throws IllegalArgumentException
     */
    public DungeonResponse loadGame(String name) throws IllegalArgumentException {
        try {
            
            String path = "savedGames/" + name + ".json";

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
            .registerTypeAdapter(Item.class, new InheritanceAdapter<Item>())
            .create();

            // Load the game
            this.currentlyAccessingGame = gson.fromJson(JSONString, Game.class);

            return currentlyAccessingGame.generateDungeonResponse();

        } catch (IOException e) {
            throw new IllegalArgumentException("Directory path is invalid or some other IO issue occured");
        }
    }
    
    /**
     * allGames
     */
    public List<String> allGames() {
        try {
            String path = "savedGames/";
            return FileLoader.listFileNamesInDirectoryOutsideOfResources(path);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * tick
     * @param itemUsed
     * @param movementDirection
     * @throws IllegalArgumentException
     * @throws InvalidActionException
     */
    public DungeonResponse tick(String itemUsed, Direction movementDirection) throws IllegalArgumentException, InvalidActionException {
        return currentlyAccessingGame.tick(itemUsed, movementDirection);
    }

    /**
     * interact
     * @param entityId
     * @throws IllegalArgumentException
     * @throws InvalidActionException
     */
    public DungeonResponse interact(String entityId) throws IllegalArgumentException, InvalidActionException {
        return currentlyAccessingGame.interact(entityId);

    }

    /**
     * build
     * @param buildable
     * @throws IllegalArgumentException
     * @throws InvalidActionException
     */
    public DungeonResponse build(String buildable) throws IllegalArgumentException, InvalidActionException {
        if (!(buildable.equals("bow") || buildable.equals("shield") || buildable.equals("midnight_armour") || buildable.equals("sceptre"))) {
            throw new IllegalArgumentException("buildable needs to be either bow, shield, midnight armour or sceptre");
        }
        try {
            currentlyAccessingGame.getInventory().craft(buildable, Game.generateUniqueId());
            return currentlyAccessingGame.generateDungeonResponse();
        } catch (InvalidActionException e) {
            throw new InvalidActionException("You don't have the resources to craft this item");
        } catch (Exception e) {
            System.out.println("An error occurred");
            e.printStackTrace();
            return null;
        }
    }

    public Game getCurrentlyAccessingGame() {
        return currentlyAccessingGame;
    }

    /**
     * Delete all currently existing saved games (useful for testing)
     */
    public void deleteExistingGames() {
        String path = "savedGames/";
        File savedGamesDir = new File(path);
        String[] saves = savedGamesDir.list();
        for (String s: saves) {
            File currentsavedGame = new File(savedGamesDir.getPath(), s);
            currentsavedGame.delete();
        }
    }
}
