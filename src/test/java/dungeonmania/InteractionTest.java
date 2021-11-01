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

        // Player does not have sword
        ZombieToastSpawner adjacentSpawner1 = new ZombieToastSpawner(1, "zombie_toast_spawner", new Position(0, 1));
        ZombieToastSpawner distantSpawner1 = new ZombieToastSpawner(2, "zombie_toast_spawner", new Position(0, 2));
        assertThrows(InvalidActionException.class, () -> adjacentSpawner1.interact(ch));

        ch.inventory.addItemToInventory(new Sword(20));

        // Both spawners to right of player
        assertDoesNotThrow(() -> adjacentSpawner1.interact(ch));
        assertThrows(InvalidActionException.class, () -> distantSpawner1.interact(ch));

        // Both spawners to left of player
        ZombieToastSpawner adjacentSpawner2 = new ZombieToastSpawner(3, "zomnbie_toast_spawner", new Position(-1, 0));
        ZombieToastSpawner distantSpawner2 = new ZombieToastSpawner(4, "zomnbie_toast_spawner", new Position(-2, 0));
        assertDoesNotThrow(() -> adjacentSpawner2.interact(ch));
        assertThrows(InvalidActionException.class, () -> distantSpawner2.interact(ch));

        // Both spawners above of player
        ZombieToastSpawner adjacentSpawner3 = new ZombieToastSpawner(5, "zomnbie_toast_spawner", new Position(0, 1));
        ZombieToastSpawner distantSpawner3 = new ZombieToastSpawner(6, "zomnbie_toast_spawner", new Position(0, 2));
        assertDoesNotThrow(() -> adjacentSpawner3.interact(ch));
        assertThrows(InvalidActionException.class, () -> distantSpawner3.interact(ch));

        // Both spawners below of player
        ZombieToastSpawner adjacentSpawner4 = new ZombieToastSpawner(7, "zomnbie_toast_spawner", new Position(0, -1));
        ZombieToastSpawner distantSpawner4 = new ZombieToastSpawner(8, "zomnbie_toast_spawner", new Position(0, -2));
        assertDoesNotThrow(() -> adjacentSpawner4.interact(ch));
        assertThrows(InvalidActionException.class, () -> distantSpawner4.interact(ch));

        // Should not be able to interact diagonally
        ZombieToastSpawner distantSpawner5 = new ZombieToastSpawner(9, "zomnbie_toast_spawner", new Position(1,1));
        assertThrows(InvalidActionException.class, () -> distantSpawner5.interact(ch));
        ZombieToastSpawner distantSpawner6 = new ZombieToastSpawner(10, "zomnbie_toast_spawner", new Position(1,-1));
        assertThrows(InvalidActionException.class, () -> distantSpawner6.interact(ch));
        ZombieToastSpawner distantSpawner7 = new ZombieToastSpawner(11, "zomnbie_toast_spawner", new Position(-1,-1));
        assertThrows(InvalidActionException.class, () -> distantSpawner7.interact(ch));
        ZombieToastSpawner distantSpawner8 = new ZombieToastSpawner(12, "zomnbie_toast_spawner", new Position(-1,1));
        assertThrows(InvalidActionException.class, () -> distantSpawner8.interact(ch));
    }
        
    public void testBribe() {

        Character ch = new Character(0, "character", new Position(0, -1));
        Mercenary merc = new Mercenary(1, new Position(0, 0), 4);
        merc.chase(ch);
        
        // Merc out of range
        ch.setPosition(new Position(0,3));
        assertThrows(InvalidActionException.class, () -> merc.interact(ch));
        ch.setPosition(new Position(3,0));
        assertThrows(InvalidActionException.class, () -> merc.interact(ch));
        ch.setPosition(new Position(0,-3));
        assertThrows(InvalidActionException.class, () -> merc.interact(ch));
        ch.setPosition(new Position(-3,0));
        assertThrows(InvalidActionException.class, () -> merc.interact(ch));

        // Check cannot interact diagonally
        ch.setPosition(new Position(1,1));
        assertThrows(InvalidActionException.class, () -> merc.interact(ch));
        ch.setPosition(new Position(1,-1));
        assertThrows(InvalidActionException.class, () -> merc.interact(ch));
        ch.setPosition(new Position(-1,-1));
        assertThrows(InvalidActionException.class, () -> merc.interact(ch));
        ch.setPosition(new Position(-1,1));
        assertThrows(InvalidActionException.class, () -> merc.interact(ch)); 

        // Ch has no gold, merc in range
        ch.setPosition(new Position(0,2));
        assertThrows(InvalidActionException.class, () -> merc.interact(ch));
        ch.setPosition(new Position(2,0));
        assertThrows(InvalidActionException.class, () -> merc.interact(ch));
        ch.setPosition(new Position(0,-2));
        assertThrows(InvalidActionException.class, () -> merc.interact(ch));
        ch.setPosition(new Position(-2,0));
        assertThrows(InvalidActionException.class, () -> merc.interact(ch));
      
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
