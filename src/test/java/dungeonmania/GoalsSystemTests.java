package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Direction;

public class GoalsSystemTests {

    @Test
    public void testMazeGoalsCorrect() {
        DungeonManiaController c = new DungeonManiaController();
        DungeonResponse r = c.newGame("maze", "Peaceful");
        assertEquals(r.getGoals(), ":exit");
    }

    @Test
    public void testPortalsGoalsCorrect() {
        DungeonManiaController c = new DungeonManiaController();
        DungeonResponse r = c.newGame("portals", "Peaceful");
        assertEquals(r.getGoals(), null); 
    }

    @Test
    public void testBouldersGoalsCorrect() {
        DungeonManiaController c = new DungeonManiaController();
        DungeonResponse r = c.newGame("boulders", "Peaceful");
        assertEquals(r.getGoals(), ":switch"); 
    }

    @Test
    public void testAdvancedGoalsCorrect() {
        DungeonManiaController c = new DungeonManiaController();
        DungeonResponse r = c.newGame("advanced", "Peaceful");
        assertEquals(r.getGoals(), "(:mercenary AND :treasure)"); 
    } 

    @Test
    public void testOrGoalsCorrect() {
        DungeonManiaController c = new DungeonManiaController();
        DungeonResponse r = c.newGame("or", "Peaceful");
        assertEquals(r.getGoals(), "(:mercenary OR :exit)"); 
    }

    @Test
    public void testNonsenseGoalNull() {
        DungeonManiaController c = new DungeonManiaController();
        DungeonResponse r = c.newGame("nonsenseGoal", "Peaceful");
        assertEquals(r.getGoals(), null); 
    }
}
