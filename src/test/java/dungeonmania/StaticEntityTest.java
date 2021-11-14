package dungeonmania;
import dungeonmania.util.Position;
import dungeonmania.util.Direction;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import java.lang.reflect.*;

//Note these need to be changed as static entities functions rewritten

public class StaticEntityTest {
    //-----Door Interaction Tests-----
    //Check to see if the sunstone correctly opens the door
    @Test
    public void doorInteractionSunstoneTest(){
        //Crete the game
        DungeonManiaController controller1 = new DungeonManiaController();
        controller1.newGame("advanced-2", "Standard");
        Game game1 = controller1.getCurrentlyAccessingGame();

        //Get the inventory
        Inventory inventory1 = game1.getInventory();

        //Add sunstone to inventory
        int s1id = Game.generateUniqueId();
        SunStone sunstone1 = new SunStone(s1id);
        inventory1.addItemToInventory(sunstone1);

        //Get player position and next position
        Position playerpos = game1.getPlayer().getPosition();
        Position nextpos = playerpos.translateBy(Direction.RIGHT);
        
        //Create door
        int d1id = Game.generateUniqueId();
        Door door1 = new Door(d1id, nextpos, 1);
        game1.getEntities().add(door1);


        //Interact with the door
        game1.tick(null, Direction.RIGHT);


        //Check door is open
        assertTrue(door1.getIsOpen());

        //Check all items are still there
        assertTrue(inventory1.getItem(s1id) != null);
        
    }

     //Check to see if the sunstone take priority over keys
     @Test
     public void doorInteractionSunstonePriorityTest(){
         //Crete the game
         DungeonManiaController controller1 = new DungeonManiaController();
         controller1.newGame("advanced-2", "Standard");
         Game game1 = controller1.getCurrentlyAccessingGame();
 
         //Get the inventory
         Inventory inventory1 = game1.getInventory();
 
         //Add sunstone to inventory
         int s1id = Game.generateUniqueId();
         SunStone sunstone1 = new SunStone(s1id);
         inventory1.addItemToInventory(sunstone1);
 
         //Add matching key to inventory
         int k1id = Game.generateUniqueId();
         Key key1 = new Key(k1id, 1);
         inventory1.addItemToInventory(key1);
 
         //Add unmatching key to inventory
         int k2id = Game.generateUniqueId();
         Key key2 = new Key(k2id, 0);
         inventory1.addItemToInventory(key2);
 
         //Get player position and next position
         Position playerpos = game1.getPlayer().getPosition();
         Position nextpos = playerpos.translateBy(Direction.RIGHT);
         
         //Create door
         int d1id = Game.generateUniqueId();
         Door door1 = new Door(d1id, nextpos, 1);
         game1.getEntities().add(door1);
 
 
         //Interact with the door
         game1.tick(null, Direction.RIGHT);
 
 
         //Check door is open
         assertTrue(door1.getIsOpen());
 
         //Check all items are still there
         assertTrue(inventory1.getItem(s1id) != null);
         assertTrue(inventory1.getItem(k1id) != null);
         assertTrue(inventory1.getItem(k2id) != null);
         
     }

    //Check to see if matching key opens door
    @Test
    public void doorInteractionKeyTest(){
        //Crete the game
        DungeonManiaController controller1 = new DungeonManiaController();
        controller1.newGame("advanced-2", "Standard");
        Game game1 = controller1.getCurrentlyAccessingGame();

        //Get the inventory
        Inventory inventory1 = game1.getInventory();

        //Add matching key to inventory
        int k1id = Game.generateUniqueId();
        Key key1 = new Key(k1id, 1);
        inventory1.addItemToInventory(key1);

        //Add unmatching key to inventory
        int k2id = Game.generateUniqueId();
        Key key2 = new Key(k2id, 0);
        inventory1.addItemToInventory(key2);

        //Get player position and next position
        Position playerpos = game1.getPlayer().getPosition();
        Position nextpos = playerpos.translateBy(Direction.RIGHT);
        
        //Create door
        int d1id = Game.generateUniqueId();
        Door door1 = new Door(d1id, nextpos, 1);
        game1.getEntities().add(door1);


        //Interact with the door
        game1.tick(null, Direction.RIGHT);


        //Check door is open
        assertTrue(door1.getIsOpen());

        //Check all items are still there
        assertTrue(inventory1.getItem(k1id) == null);
        assertTrue(inventory1.getItem(k2id) != null);
        
    }

    //Check that unmatching key doesn't open door
    @Test
    public void doorInteractionUnmatchKeyTest(){
        //Crete the game
        DungeonManiaController controller1 = new DungeonManiaController();
        controller1.newGame("advanced-2", "Standard");
        Game game1 = controller1.getCurrentlyAccessingGame();

        //Get the inventory
        Inventory inventory1 = game1.getInventory();

        //Add unmatching key to inventory
        int k2id = Game.generateUniqueId();
        Key key2 = new Key(k2id, 0);
        inventory1.addItemToInventory(key2);

        //Get player position and next position
        Position playerpos = game1.getPlayer().getPosition();
        Position nextpos = playerpos.translateBy(Direction.RIGHT);
        
        //Create door
        int d1id = Game.generateUniqueId();
        Door door1 = new Door(d1id, nextpos, 1);
        game1.getEntities().add(door1);


        //Interact with the door
        game1.tick(null, Direction.RIGHT);


        //Check door is open
        assertFalse(door1.getIsOpen());

        //Check all items are still there
        assertTrue(inventory1.getItem(k2id) != null);
        
    }
}