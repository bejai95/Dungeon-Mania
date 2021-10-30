package dungeonmania;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.SerializedName;

import org.json.JSONArray;
import org.json.JSONObject;

import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class Game {
    private String dungeonId;
    private String dungeonName;
    private List<Entity> entities;
    private List<Item> inventory;
    private List<String> buildables;
    private int tickCounter; // Initialized to zero
    
    @SerializedName(value="goal-condition")
    private GoalCondition goalCondition;

    // private final List<AnimationQueue> animations;
    private String gameMode;
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

    //Checks if a cell is empty
    public boolean isEmpty(Position cell) {
        for (int i = 0; i < entities.size(); i++) {
            if (cell.equals(entities.get(i).getPosition())) {
                return false;
            }
        }
        return true;
    }

    //Finds an adjacent empty cell when given a cell
    public Position getEmpty(Position centre) {
        List<Position> adjacentPositions = centre.getAdjacentPositions();
        Position emptyTile = null;
        for (int i = 0; i < adjacentPositions.size(); i++) {
            if (isEmpty(adjacentPositions.get(i))) {
                emptyTile = adjacentPositions.get(i);
            }
        }
        return emptyTile;
    }

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

    public void setGameMode(String gameMode) {
        this.gameMode = gameMode;
    }

    private Character getPlayer(){
        for(Entity entity : entities){
            if(entity instanceof Character){
                return (Character) entity;
            }
        }
        return null;
    }

    private List<Mercenary> getMercenaries(){
        return null; //TODO
    }

    private List<MovingEntity> getMovingEntities(){
        return null; //TODO
    }


    public DungeonResponse tick(String itemUsed, Direction movementDirection) throws IllegalArgumentException, InvalidActionException {
        //use item
        //remove dead items
        //move in direction
        //move all the mobs -- needs list of moving entities
        
        //spawn in enemies -- needs tick counter

        //battle -- needs list of mercenaries, needs movingEntity on same tile as player
        //display remaining goals and end game if there are none
        //increment tick counter
        return null;
    }

    
}
