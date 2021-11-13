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
    private List<Entity> getEmptyEntitiesList(){
        List<Entity> ents = new ArrayList<Entity>();
        return ents;
    }

    private List<Entity> getBouldersNotOnSwitches(){
        List<Entity> ents = new ArrayList<Entity>();
        Boulder boulder = new Boulder(0, new Position(0,0));
        FloorSwitch s = new FloorSwitch(1, new Position(0,1));
        ents.add(boulder);
        ents.add(s);
        return ents;
    }

    private List<Entity> getBouldersOnSwitches(){
        List<Entity> ents = new ArrayList<Entity>();
        Boulder boulder = new Boulder(0, new Position(0,0));
        FloorSwitch s = new FloorSwitch(1, new Position(0,0));
        ents.add(boulder);
        ents.add(s);
        return ents;
    }

    private List<Entity> getPlayerOnExit(){
        List<Entity> ents = new ArrayList<Entity>();
        Character player = new Character(0, new Position(0, 0));
        Exit exit = new Exit(1, new Position(0,0));
        ents.add(player);
        ents.add(exit);
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

    @Test
    public void testBoulderGoalIncomplete() {

        ExitGoal exgoal = new ExitGoal();
        BoulderGoal bgoal = new BoulderGoal();

        List<Goal> andsubs = new ArrayList<>();
        andsubs.add(exgoal);
        andsubs.add(bgoal);

        AndGoal agoal = new AndGoal(andsubs);

        List<Entity> ents = getBouldersNotOnSwitches();
        assertEquals(agoal.getGoalsLeft(ents), "(:exit AND :switch)");
    }

    @Test
    public void testBoulderGoalComplete() {

        ExitGoal exgoal = new ExitGoal();
        BoulderGoal bgoal = new BoulderGoal();

        List<Goal> andsubs = new ArrayList<>();
        andsubs.add(exgoal);
        andsubs.add(bgoal);

        AndGoal agoal = new AndGoal(andsubs);

        List<Entity> ents = getBouldersOnSwitches();
        assertEquals(agoal.getGoalsLeft(ents), ":exit");
    }

    @Test 
    public void testAndEmpty(){
        List<Goal> andsubs = new ArrayList<>();
        AndGoal agoal = new AndGoal(andsubs);
        List<Entity> ents = getEmptyEntitiesList();
        assertEquals(agoal.getGoalsLeft(ents), "");

    }

    @Test 
    public void testOrEmpty(){
        List<Goal> osubs = new ArrayList<>();
        OrGoal ogoal = new OrGoal(osubs);
        List<Entity> ents = getEmptyEntitiesList();
        assertEquals(ogoal.getGoalsLeft(ents), "");

    }

    @Test 
    public void testOrSingleton(){
        List<Goal> osubs = new ArrayList<>();
        ExitGoal egoal = new ExitGoal();
        osubs.add(egoal);
        OrGoal ogoal = new OrGoal(osubs);
        List<Entity> ents = getEmptyEntitiesList();
        assertEquals(ogoal.getGoalsLeft(ents), ":exit");

    }

    @Test
    public void testExitGoalComplete(){
        ExitGoal egoal = new ExitGoal();
        List<Entity> ents = getPlayerOnExit();
        assertEquals(egoal.getGoalsLeft(ents), "");
    }



    




}
