package dungeonmania;

import java.util.List;

import org.json.JSONObject;

public class Game {
    private String dungeonId;
    private String dungeonName;
    private List<Entity> entities; 
    private List<Item> inventory;
    private List<String> buildables;
    private JSONObject goals;
    //private final List<AnimationQueue> animations;
    private String gameMode;
    
    public Game(String dungeonId, String dungeonName, String gameMode, String goals) {
        this.dungeonId = dungeonId;
        this.dungeonName = dungeonName;
        this.gameMode = gameMode;
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

}
