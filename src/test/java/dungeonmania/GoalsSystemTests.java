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
        DungeonResponse r = c.newGame("maze", "peaceful");
        assertEquals(r.getGoals(), ":exit");
    }

    @Test
    public void testPortalsGoalsCorrect() {
        DungeonManiaController c = new DungeonManiaController();
        DungeonResponse r = c.newGame("portals", "peaceful");
        assertEquals(r.getGoals(), null); 
    }

    @Test
    public void testBouldersGoalsCorrect() {
        DungeonManiaController c = new DungeonManiaController();
        DungeonResponse r = c.newGame("boulders", "peaceful");
        assertEquals(r.getGoals(), ":boulder"); 
    }

    @Test
    public void testAdvancedGoalsCorrect() {
        DungeonManiaController c = new DungeonManiaController();
        DungeonResponse r = c.newGame("advanced", "peaceful");
        assertEquals(r.getGoals(), "(:enemies AND :treasure)"); 
    }










}
