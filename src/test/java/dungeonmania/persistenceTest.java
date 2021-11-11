package dungeonmania;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.FileLoader;


@TestInstance(value = Lifecycle.PER_CLASS)
public class persistenceTest {
    @Test
    public void testBasicPersistence() {
        DungeonManiaController controller1 = new DungeonManiaController();
        
        // Test loading a game that does not exist
        assertThrows(IllegalArgumentException.class, () -> controller1.loadGame("NonexistentGame"));
        
        controller1.deleteExistingGames();
        
        // Test creating and then saving some games
        assertDoesNotThrow(() -> controller1.newGame("advanced", "Standard"));
        controller1.saveGame("save1");
        assertDoesNotThrow(() -> controller1.newGame("maze", "Standard"));
        controller1.saveGame("save2");

        // Test that both games have been saved
        List<String> expected = Arrays.asList("save1", "save2");
        List<String> actual = controller1.allGames();
        assertEquals(expected, actual);

        //  Test that if the application is terminated, the current game state can be reloaded and play can continue from where it left off
        // Basically this means we should be able to create a new controller and access our other games from there
        DungeonManiaController controller2 = new DungeonManiaController();
        DungeonResponse response1 = assertDoesNotThrow(() -> controller2.loadGame("save1"));
        assertEquals("0", response1.getDungeonId());
        assertEquals("advanced", response1.getDungeonName());
        DungeonResponse response2 = assertDoesNotThrow(() -> controller2.loadGame("save2"));
        assertEquals("1", response2.getDungeonId());
        assertEquals("maze", response2.getDungeonName());

        // Testing overwriting save files -> Should work
        DungeonManiaController controller3 = new DungeonManiaController();
        assertDoesNotThrow(() -> controller3.loadGame("save1"));
        controller3.saveGame("save2"); // Here we are overwriting save2 with the contents of save1
        assertDoesNotThrow(() -> controller3.loadGame("save2"));
        controller3.saveGame("save2");
        DungeonResponse response3 = assertDoesNotThrow(() -> controller3.loadGame("save2"));
        assertEquals("0", response3.getDungeonId()); // Should be the contents of save1
        assertEquals("advanced", response3.getDungeonName());
    }

    //  basically tests persistence when inventory is not empty, also kind of a system test
    @Test
    public void testPersistenseConsistency() {
        
        DungeonManiaController controller1 = new DungeonManiaController();
        controller1.newGame("advanced-2", "Standard");
        
        for(int i = 0; i < 10; i++) {
            controller1.tick(null, Direction.RIGHT);
        }

        for(int i = 0; i < 3; i++) {
            controller1.tick(null, Direction.DOWN);
        }

        for(int i = 0; i < 5; i++) {
            controller1.tick(null, Direction.RIGHT);
        }

        for(int i = 0; i < 10; i++) {
            controller1.tick(null, Direction.DOWN);
        }

        for(int i = 0; i < 5; i++) {
            controller1.tick(null, Direction.LEFT);
        }

        for(int i = 0; i < 4; i++) {
            controller1.tick(null, Direction.UP);
        }

        for(int i = 0; i < 3; i++) {
            controller1.tick(null, Direction.LEFT);
        }

        DungeonResponse res1 = controller1.tick(null, Direction.LEFT);
        List<ItemResponse> previousInventory = res1.getInventory();
        List<String> previousInventoryStrings = new ArrayList<>();
        for (ItemResponse curr: previousInventory) {
            String currItemString = curr.getId() + curr.getType();
            previousInventoryStrings.add(currItemString);
        }
        String previousGoalsLeft = res1.getGoals();
        
        assertTrue(previousInventory.size() == 10);
        assertTrue(previousGoalsLeft.equals(":mercenary"));

        // Save the file, and load it from another controller, check that the inventories and goals are the same
        controller1.saveGame("random_save");
        DungeonManiaController controller2 = new DungeonManiaController();
        DungeonResponse res2 = controller2.loadGame("random_save");
        List<ItemResponse> newInventory = res2.getInventory();
        List<String> newInventoryStrings = new ArrayList<>();
        for (ItemResponse curr: newInventory) {
            String currItemString = curr.getId() + curr.getType();
            newInventoryStrings.add(currItemString);
        }
        String newGoalsLeft = res1.getGoals();
        
        assertTrue(previousInventoryStrings.equals(newInventoryStrings));
        assertTrue(previousGoalsLeft.equals(newGoalsLeft));
    }

    // Test that the state of open and closed doors remains between saves
    @Test
    public void testDoorState() {
        DungeonManiaController controller1 = new DungeonManiaController();
        controller1.newGame("advanced-2", "Standard");
        
        // Get a list of ids of all the doors
        List<Integer> doorIds = new ArrayList<Integer>();
        for (Entity curr: controller1.getCurrentlyAccessingGame().getEntities()) {
            if (curr.getType().equals("door")) {
                doorIds.add(curr.getId());
            }
        }

        assertTrue(doorIds.size() == 2);

        controller1.saveGame("Doors"); 
        controller1.loadGame("Doors");

        for (Integer curr: doorIds) {
            String idAsString = curr.toString();
            Door door = (Door)controller1.getCurrentlyAccessingGame().getEntityById(idAsString);
            assertTrue(door.getIsOpen().equals(false));
        }

        // Get the correct key for a certain door and unlock the door
        for(int i = 0; i < 10; i++) {
            controller1.tick(null, Direction.RIGHT);
        }
        for(int i = 0; i < 9; i++) { 
            controller1.tick(null, Direction.DOWN);
        }
        for(int i = 0; i < 5; i++) {
            controller1.tick(null, Direction.UP);
        }
        for(int i = 0; i < 4; i++) {
            controller1.tick(null, Direction.RIGHT);
        }
        for(int i = 0; i < 4; i++) {
            controller1.tick(null, Direction.DOWN);
        }

        // Find the door which the player is currently standing on
        int openDoorId = -1;
        for (Integer curr: doorIds) {
            String idAsString = curr.toString();
            Door door = (Door)controller1.getCurrentlyAccessingGame().getEntityById(idAsString);
            if (controller1.getCurrentlyAccessingGame().getPlayer().getPosition().equals(door.getPosition())) {
                openDoorId = door.getId();
            }
        }

        // Move away from the door
        for(int i = 0; i < 5; i++) {
            controller1.tick(null, Direction.DOWN);
        }

        controller1.saveGame("Doors"); 
        controller1.loadGame("Doors");

        // Make sure that the door we just unlocked is still open and the other door is still closed
        for (Integer curr: doorIds) {
            String idAsString = curr.toString();
            Door door = (Door)controller1.getCurrentlyAccessingGame().getEntityById(idAsString);
            if (door.getId() == openDoorId) {
                assertTrue(door.getIsOpen().equals(true));
            } else {
                assertTrue(door.getIsOpen().equals(false));
            }
        }
    }
}
