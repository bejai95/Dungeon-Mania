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
import dungeonmania.util.Position;

@TestInstance(value = Lifecycle.PER_CLASS)
public class milestone2SystemTests {
    
    // Test that all of the controller methods are working correctly
    @Test
    public void testControllerMethods() {
        
        DungeonManiaController controller1 = new DungeonManiaController();
        
        // Test newGame exceptions
        assertThrows(IllegalArgumentException.class, () -> controller1.newGame("maze", "invalidGameMode"));
        assertThrows(IllegalArgumentException.class, () -> controller1.newGame("InvalidDungeonName", "Standard"));

        // Properly use newGame
        controller1.newGame("advanced-2", "Standard");

        // Test battling a mercenary, player health should decrease
        Game currentGame = controller1.getCurrentlyAccessingGame();
        Character currentPlayer = currentGame.getPlayer();
        for(int i = 0; i < 3; i++) { 
            controller1.tick(null, Direction.DOWN);
        }
        for(int i = 0; i < 3; i++) { 
            controller1.tick(null, Direction.UP);
        }
        assertTrue(currentPlayer.getHealth() < currentPlayer.getMaxHealth());

        // Now if we pick up and use a health potion, the player should regenerate to full health, and the player's inventory should go from being empty, to containing 1 item, to being empty again
        assertTrue(currentPlayer.getInventory().getItems().size() == 0);
        DungeonResponse res1 = controller1.tick(null, Direction.RIGHT);
        assertTrue(currentPlayer.getInventory().getItems().size() == 1);
        String healthPotionId = null;
        for (ItemResponse curr: res1.getInventory()) {
            if (curr.getType().equals("health_potion")) {
                healthPotionId = curr.getId();
            }
        }
        controller1.tick(healthPotionId, Direction.NONE);
        assertTrue(currentPlayer.getInventory().getItems().size() == 0);
        assertTrue(currentPlayer.getHealth() == currentPlayer.getMaxHealth());

        // Test walking into a wall, character should remain in same position (tick should still pass by)
        Position originalPosition1 = currentGame.getPlayer().getPosition(); 
        controller1.tick(null, Direction.LEFT);
        Position newPosition1 = currentGame.getPlayer().getPosition();
        assertTrue(originalPosition1.getX() == newPosition1.getX() && originalPosition1.getY() == newPosition1.getY());

        // Test trying to walk through a door that has not been unlocked yet
        for(int i = 0; i < 8; i++) {
            controller1.tick(null, Direction.DOWN);
        }
        for(int i = 0; i < 3; i++) {
            controller1.tick(null, Direction.RIGHT);
        }
        Position originalPosition2 = currentGame.getPlayer().getPosition(); 
        controller1.tick(null, Direction.DOWN);
        Position newPosition2 = currentGame.getPlayer().getPosition();
        assertTrue(originalPosition2.getX() == newPosition2.getX() && originalPosition2.getY() == newPosition2.getY());

        // Collect the key to the door and return to the same position
        for(int i = 0; i < 3; i++) {
            controller1.tick(null, Direction.LEFT);
        }
        for(int i = 0; i < 5; i++) {
            controller1.tick(null, Direction.UP);
        }
        for(int i = 0; i < 6; i++) {
            controller1.tick(null, Direction.RIGHT);
        }
        controller1.tick(null, Direction.UP);
        for(int i = 0; i < 4; i++) {
            controller1.tick(null, Direction.RIGHT);
        }
        for(int i = 0; i < 6; i++) {
            controller1.tick(null, Direction.DOWN);
        }
        controller1.tick(null, Direction.RIGHT);
        controller1.tick(null, Direction.DOWN); // Have just picked up the key
        controller1.tick(null, Direction.UP);
        controller1.tick(null, Direction.LEFT);
        for(int i = 0; i < 6; i++) {
            controller1.tick(null, Direction.UP);
        }
        for(int i = 0; i < 4; i++) {
            controller1.tick(null, Direction.LEFT);
        }
        controller1.tick(null, Direction.DOWN);
        for(int i = 0; i < 6; i++) {
            controller1.tick(null, Direction.LEFT);
        }
        for(int i = 0; i < 5; i++) {
            controller1.tick(null, Direction.DOWN);
        }
        for(int i = 0; i < 3; i++) {
            controller1.tick(null, Direction.RIGHT);
        }

        // Test trying to walk through that same locked door now that we have the key (should now be able to walk through it)
        assertTrue(currentPlayer.getInventory().getItems().size() == 1); // Should just be the key
        Position originalPosition3 = currentGame.getPlayer().getPosition(); 
        controller1.tick(null, Direction.DOWN);
        Position newPosition3 = currentGame.getPlayer().getPosition();
        assertTrue(originalPosition3.getX() != newPosition3.getX() && originalPosition3.getY() != newPosition3.getY());
        assertTrue(currentPlayer.getInventory().getItems().size() == 0); // Key should have been used now







        
        

    
    
    
    }
}
