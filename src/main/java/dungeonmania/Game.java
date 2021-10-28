package dungeonmania;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class Game {
    private String dungeonId;
    private String dungeonName;
    private List<Entity> entities = new ArrayList<Entity>();
    private List<Item> inventory = new ArrayList<Item>();
    private List<String> buildables = new ArrayList<String>();
    private JSONObject goals;
    // private final List<AnimationQueue> animations;
    private String gameMode;
    private String saveName;
    private static int numDungeonIds; // Initialized to zero
    
    public Game(String dungeonId, String dungeonName, JSONArray entities, String gameMode, JSONObject goals) {
        this.dungeonId = dungeonId;
        this.dungeonName = dungeonName;
        this.gameMode = gameMode;
        this.goals = goals;
        this.saveName = null;
        numDungeonIds++;

        // Initialize the array of entities (convert the JSON array to an array of type Entity)
        for (int i = 0; i < entities.length(); i++) {
            JSONObject wholeEntity = entities.getJSONObject(i);
            int positionX = wholeEntity.getInt("x");
            int positionY = wholeEntity.getInt("y");
            String type = wholeEntity.getString("type");

            // Generate an Id for the new Entity
            String newEntityId = String.valueOf(Entity.getNumEntityIds());

            // TODO create a new entity with the fields above (just waiting on all entity subclasses to be implemented)
        }
    }



    public static int getNumDungeonIds() {
        return numDungeonIds;
    }

    private String getGoalsLeft(JSONObject gs){
        switch(gs.getString("goal")){
            case "exit":
                return Exit.goalComplete(entities);
            case "boulder":
                return Switches.goalComplete(entities);
            case "enemies":
                return Enemies.goalComplete(entities);
            case "treasure":
                return Gold.goalComplete(entities);
            case "AND":
                String conj1 = getGoalsLeft(gs.getJSONArray("subgoals").getJSONObject(0));
                if(conj1.equals("")){
                    return getGoalsLeft(gs.getJSONArray("subgoals").getJSONObject(1));
                }
                String conj2 = getGoalsLeft(gs.getJSONArray("subgoals").getJSONObject(1));
                if(conj2.equals("")){
                    return conj1;
                }else{
                    return "(" + conj1 + " AND " + conj2 + ")"; 
                }
            case "OR":
                String disj1 = getGoalsLeft(gs.getJSONArray("subgoals").getJSONObject(0));
                if(disj1.equals("")){
                    return "";
                }
                String disj2 = getGoalsLeft(gs.getJSONArray("subgoals").getJSONObject(1));
                if(disj2.equals("")){
                    return "";
                }else{
                    return "(" + disj1 + " OR " + disj2 + ")";
                }
        }
        return null;
    }

    public String getGoalsLeft() {
        return getGoalsLeft(goals);
    }

    public String getDungeonId() {
        return dungeonId;
    }


    public String getDungeonName() {
        return dungeonName;
    }

    public List<Entity> getEntities() {
        return entities;
    }

    public List<Item> getInventory() {
        return inventory;
    }

    public List<String> getBuildables() {
        return buildables;
    }

}
