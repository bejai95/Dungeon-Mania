/*package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Direction;


public class GoalsUnitTests {
    private List<Entity> getEmptyEntitiesList(){
        List<Entity> ents = new ArrayList<Entity>();
        return ents;
    }

    private List<Entity> getEntityListWithSpider(){
        List<Entity> ents = new ArrayList<Entity>();
        MovingEntity spider1 = new Spider(5, 1, new SquareMovement(), new Position(0,0));
        ents.add(spider1);
        return ents;
    }
    
    @Test
    public void testExitGoal() {
        ExitGoal goal = new ExitGoal();
        List<Entity> ents = getEntityListWithSpider();
        assertEquals(goal.getGoalsLeft(ents), ":exit");
    }

    @Test
    public void testAndGoal() {
        JSONObject goals = new JSONObject();
        goals.put("goal", "AND");
        JSONArray subgoals = new JSONArray();
        JSONObject subgoal1 = new JSONObject();
        JSONObject subgoal2 = new JSONObject();
        subgoal1.put("goal", "exit");
        subgoal2.put("goal", "boulder");
        subgoals.put(subgoal1);
        subgoals.put(subgoal2);
        goals.put("subgoals", subgoals);
        Game game = new Game("id", "maze", "peaceful", goals.toString());
        assertEquals(game.getGoalsLeft(), "(:exit AND :boulder)");
    }

    @Test
    public void testOrGoal() {
        JSONObject goals = new JSONObject();
        goals.put("goal", "OR");
        JSONArray subgoals = new JSONArray();
        JSONObject subgoal1 = new JSONObject();
        JSONObject subgoal2 = new JSONObject();
        subgoal1.put("goal", "enemies");
        subgoal2.put("goal", "treasure");
        subgoals.put(subgoal1);
        subgoals.put(subgoal2);
        goals.put("subgoals", subgoals);
        Game game = new Game("id", "maze", "peaceful", goals.toString());
        assertEquals(game.getGoalsLeft(), "(:mercenary OR :treasure)");
    }

    @Test
    public void testAndOrGoal() {
        JSONObject goals = new JSONObject();
        goals.put("goal", "AND");
        JSONArray subgoals = new JSONArray();
        JSONObject subgoal1 = new JSONObject();
        JSONObject subgoal2 = new JSONObject();
        subgoal1.put("goal", "enemies");
        subgoal2.put("goal", "OR");

        JSONArray subgoals2 = new JSONArray();
        JSONObject subgoal3 = new JSONObject();
        JSONObject subgoal4 = new JSONObject();
        subgoal3.put("goal", "trasure");
        subgoal4.put("goal", "exit");
        
        subgoals.put(subgoal1);
        subgoals.put(subgoal2);

        subgoals2.put(subgoal3);
        subgoals2.put(subgoal4);
        goals.put("subgoals", subgoals);
        subgoal2.put("subgoals", subgoals2);

        Game game = new Game("id", "maze", "peaceful", goals.toString());
        assertEquals(game.getGoalsLeft(), "(:mercenary AND (:treasure OR :exit))");
    }
}
*/