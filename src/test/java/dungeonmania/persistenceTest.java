package dungeonmania;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.FileLoader;

import dungeonmania.DungeonManiaController;


@TestInstance(value = Lifecycle.PER_CLASS)
public class persistenceTest {
    @Test
    public void basicPersistence() {
        DungeonManiaController controller1 = new DungeonManiaController();
        
        // Test loading a game that does not exist
        assertThrows(IllegalArgumentException.class, () -> controller1.loadGame("NonexistentGame"));
        
        // Test creating and then saving some games
        assertDoesNotThrow(() -> controller1.newGame("maze.json", "standard"));
        controller1.saveGame("save1");
        assertDoesNotThrow(() -> controller1.newGame("portals.json", "standard"));
        controller1.saveGame("save2");

        //  Test that if the application is terminated, the current game state can be reloaded and play can continue from where it left off
        // Basically this means we should be able to create a new controller and access our other games from there
        DungeonManiaController controller2 = new DungeonManiaController();
        controller2.loadGame("save1");
        DungeonResponse response1 = assertDoesNotThrow(() -> controller2.loadGame("save1"));
        DungeonResponse response2 = assertDoesNotThrow(() -> controller2.loadGame("save2"));

        // Check that the goals are correct for both games (will do this later once I'm more familiar with how the JSON objects work)

    }

}
