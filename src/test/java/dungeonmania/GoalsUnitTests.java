package dungeonmania;

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
import dungeonmania.util.Position;


public class GoalsUnitTests {
    /*private List<Entity> getEmptyEntitiesList(){
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
    }*/
    private List<Entity> getEmptyEntitiesList(){
        List<Entity> ents = new ArrayList<Entity>();
        return ents;
    }

    private List<Entity> getEntityListWithSpiderPlayerAndExit(){
        List<Entity> ents = new ArrayList<Entity>();
        MovingEntity spider1 = new Spider(0, new Position(0,0), new SquareMovement());
        Character player = new Character(1, new Position(0,1));
        StaticEntity exit = new Exit(2, new Position(0,2));
        StaticEntity treasure = new UnpickedUpItem(3, "treasure", new Position(0,4), "Treasure");
        ents.add(spider1);
        ents.add(player);
        ents.add(exit);
        ents.add(treasure);
        return ents;
    }

    @Test
    public void testAndOrGoal() {

        
        ExitGoal exgoal = new ExitGoal();
        EnemiesGoal engoal = new EnemiesGoal();
        TreasureGoal tgoal = new TreasureGoal();

        List<Goal> orsubs = new ArrayList<>();
        orsubs.add(tgoal);
        orsubs.add(exgoal);



        OrGoal ogoal = new OrGoal(orsubs);

        List<Goal> andsubs = new ArrayList<>();
        andsubs.add(engoal);
        andsubs.add(ogoal);

        AndGoal agoal = new AndGoal(andsubs);

        List<Entity> ents = getEntityListWithSpiderPlayerAndExit();
        assertEquals(agoal.getGoalsLeft(ents), "(:mercenary AND (:treasure OR :exit))");
    }

    @Test
    public void testAndOrGoalNoEntities() {

        
        ExitGoal exgoal = new ExitGoal();
        EnemiesGoal engoal = new EnemiesGoal();
        TreasureGoal tgoal = new TreasureGoal();

        List<Goal> orsubs = new ArrayList<>();
        orsubs.add(tgoal);
        orsubs.add(exgoal);



        OrGoal ogoal = new OrGoal(orsubs);

        List<Goal> andsubs = new ArrayList<>();
        andsubs.add(engoal);
        andsubs.add(ogoal);

        AndGoal agoal = new AndGoal(andsubs);

        List<Entity> ents = getEmptyEntitiesList();
        assertEquals(agoal.getGoalsLeft(ents), "");
    }

    @Test
    public void testBoulderGoalNoEntities() {

        ExitGoal exgoal = new ExitGoal();
        BoulderGoal bgoal = new BoulderGoal();

        List<Goal> andsubs = new ArrayList<>();
        andsubs.add(exgoal);
        andsubs.add(bgoal);

        AndGoal agoal = new AndGoal(andsubs);

        List<Entity> ents = getEmptyEntitiesList();
        assertEquals(agoal.getGoalsLeft(ents), ":exit");
    }
}
