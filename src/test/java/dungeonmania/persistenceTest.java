package dungeonmania;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.FileLoader;


@TestInstance(value = Lifecycle.PER_CLASS)
public class persistenceTest {
    @Test
    public void basicPersistence() {
        DungeonManiaController controller1 = new DungeonManiaController();
        
        // Test loading a game that does not exist
        assertThrows(IllegalArgumentException.class, () -> controller1.loadGame("NonexistentGame"));
        
        // Test creating and then saving some games
        assertDoesNotThrow(() -> controller1.newGame("maze.json", "Standard"));
        controller1.saveGame("save1");
        assertDoesNotThrow(() -> controller1.newGame("boulders.json", "Standard"));
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
        assertEquals("maze.json", response1.getDungeonName());
        DungeonResponse response2 = assertDoesNotThrow(() -> controller2.loadGame("save2"));
        assertEquals("1", response2.getDungeonId());
        assertEquals("boulders.json", response2.getDungeonName());

        // Testing overwriting save files -> Should work
        DungeonManiaController controller3 = new DungeonManiaController();
        assertDoesNotThrow(() -> controller3.loadGame("save1"));
        controller3.saveGame("save2"); // Here we are overwriting save2 with the contents of save1
        assertDoesNotThrow(() -> controller3.loadGame("save2"));
        controller3.saveGame("save2");
        DungeonResponse response3 = assertDoesNotThrow(() -> controller3.loadGame("save2"));
        assertEquals("0", response3.getDungeonId()); // Should be the contents of save1
        assertEquals("maze.json", response3.getDungeonName());
    }
}
