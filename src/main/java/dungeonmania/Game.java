package dungeonmania;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.SerializedName;

import org.json.JSONArray;
import org.json.JSONObject;

import dungeonmania.response.models.DungeonResponse;

public class Game {
    private String dungeonId;
    private String dungeonName;
    private List<Entity> entities;
    private List<Item> inventory;
    private List<String> buildables;
    
    @SerializedName(value="goal-condition")
    private GoalCondition goalCondition;

    // private final List<AnimationQueue> animations;
    private String gameMode;
    private String saveName;
    private static int numDungeonIds; // Initialized to zero
    private String goalsAsString;
    
    public Game() {
    }


    public static int getNumDungeonIds() {
        return numDungeonIds;
    }

    public static void incrementNumDungeonIds() {
        numDungeonIds++;
    }

    public void initializeInventoryAndBuildables() {
        this.inventory = new ArrayList<Item>();
        this.buildables = new ArrayList<String>();
    }

    /*
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
    */
    /*
    public String getGoalsLeft() {
        return getGoalsLeft(goalCondition.getGoal());
    }
    */

    public String getDungeonId() {
        return dungeonId;
    }

    public void setDungeonId(String dungeonId) {
        this.dungeonId = dungeonId;
    }

    public String getDungeonName() {
        return dungeonName;
    }

    public void setDungeonName(String dungeonName) {
        this.dungeonName = dungeonName;
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

    public GoalCondition getGoalCondition() {
        return goalCondition;
    }

    // Generate a dungeon response
    public DungeonResponse generateDungeonResponse() {
        return new DungeonResponse(dungeonId, dungeonName, null, null, buildables, goalsAsString); //TODO fix this up later
    }
}
