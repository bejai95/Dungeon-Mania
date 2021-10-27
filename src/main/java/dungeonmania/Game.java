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
    private final List<AnimationQueue> animations;
    private String gameMode;
    
    public Game(String dungeonId, String dungeonName, String gameMode, String goals) {
        this.dungeonId = dungeonId;
        this.dungeonName = dungeonName;
        this.gameMode = gameMode;
    }

    public String getGoalsLeft() {
        return null;
    }

}
