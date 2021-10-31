package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.util.Position;

public class InteractionTest {

    @Test
    public void destroySpawner() {

        Character ch = new Character(0, "character", new Position(0, 0));

        // Both spawners to right of player
        ZombieToastSpawner adjacentSpawner = new ZombieToastSpawner(1, "zombie_toast_spawner", new Position(0, 1));
        ZombieToastSpawner distantSpawner = new ZombieToastSpawner(2, "zombie_toast_spawner", new Position(0, 2));
        assertDoesNotThrow(adjacentSpawner.interact(ch));
        assertThrows(InvalidActionException.class, distantSpawner.interact(ch));

        // Both spawners to left of player
        adjacentSpawner.setPosition(new Position(-1, 0));
        distantSpawner.setPosition((new Position(-2, 0)));
        assertDoesNotThrow(adjacentSpawner.interact(ch));
        assertThrows(InvalidActionException.class, distantSpawner.interact(ch));

        // Both spawners above of player
        adjacentSpawner.setPosition(new Position(0,1));
        distantSpawner.setPosition((new Position(0, 2)));
        assertDoesNotThrow(adjacentSpawner.interact(ch));
        assertThrows(InvalidActionException.class, distantSpawner.interact(ch));

        // Both spawners below of player
        adjacentSpawner.setPosition(new Position(0, -1));
        distantSpawner.setPosition((new Position(0, -2)));
        assertDoesNotThrow(adjacentSpawner.interact(ch));
        assertThrows(InvalidActionException.class, distantSpawner.interact(ch));

        // Should not be able to interact diagonally
        distantSpawner.setPosition((new Position(1, 1))); // up-right
        assertThrows(InvalidActionException.class, distantSpawner.interact(ch));
        distantSpawner.setPosition((new Position(1, -1))); // down-right
        assertThrows(InvalidActionException.class, distantSpawner.interact(ch));
        distantSpawner.setPosition((new Position(-1, -1))); // down-left
        assertThrows(InvalidActionException.class, distantSpawner.interact(ch));
        distantSpawner.setPosition((new Position(-1, 1))); // up-left
        assertThrows(InvalidActionException.class, distantSpawner.interact(ch));
    }
        
    public void testBribe() {

        Character ch = new Character(0, "character", new Position(0, -1));
        Mercenary merc = new Mercenary(1, new Position(0, 0), 4, ch);
        
        // Merc out of range
        ch.setPosition(new Position(0,3));
        assertThrows(InvalidActionException.class, merc.interact(ch));
        ch.setPosition(new Position(3,0));
        assertThrows(InvalidActionException.class, merc.interact(ch));
        ch.setPosition(new Position(0,-3));
        assertThrows(InvalidActionException.class, merc.interact(ch));
        ch.setPosition(new Position(-3,0));
        assertThrows(InvalidActionException.class, merc.interact(ch));

        // Check cannot interact diagonally
        ch.setPosition(new Position(1,1));
        assertThrows(InvalidActionException.class, merc.interact(ch));
        ch.setPosition(new Position(1,-1));
        assertThrows(InvalidActionException.class, merc.interact(ch));
        ch.setPosition(new Position(-1,-1));
        assertThrows(InvalidActionException.class, merc.interact(ch));
        ch.setPosition(new Position(-1,1));
        assertThrows(InvalidActionException.class, merc.interact(ch)); 

        // Ch has no gold, merc in range
        ch.setPosition(new Position(0,2));
        assertThrows(InvalidActionException.class, merc.interact(ch));
        ch.setPosition(new Position(2,0));
        assertThrows(InvalidActionException.class, merc.interact(ch));
        ch.setPosition(new Position(0,-2));
        assertThrows(InvalidActionException.class, merc.interact(ch));
        ch.setPosition(new Position(-2,0));
        assertThrows(InvalidActionException.class, merc.interact(ch));
      
        // Ch has gold, merc in range
        ch.inventory.addItemToInventory(new Treasure(2));
        ch.inventory.addItemToInventory(new Treasure(2));
        ch.inventory.addItemToInventory(new Treasure(2));
        ch.inventory.addItemToInventory(new Treasure(2));

        ch.setPosition(new Position(0,2));
        assertDoesNotThrow(() -> merc.interact(ch));
        ch.setPosition(new Position(2,0));
        assertDoesNotThrow(() -> merc.interact(ch));
        ch.setPosition(new Position(0,-2));
        assertDoesNotThrow(() -> merc.interact(ch));
        ch.setPosition(new Position(-2,0));
        assertDoesNotThrow(() -> merc.interact(ch));
    }

}
