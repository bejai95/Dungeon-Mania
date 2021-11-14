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
    //-----Game Setup Helper Function-----
    private Game startGame() {
    	DungeonManiaController controller1 = new DungeonManiaController();
        controller1.newGame("advanced-2", "Standard");
        return controller1.getCurrentlyAccessingGame();
    }

	//-----Boulder/Bomb/Switch Tests-----
	//Tests to see if a player can sucessfully activate a bomb using a boulder
	@Test
    public void sucessfulBombBoulderTest(){
		//Crete the game
        Game game1 = startGame();

		//Gather Variables
		Character player1 = game1.getPlayer();
		Position player1Pos = player1.getPosition();
		Inventory inventory1 = game1.getInventory();

		//Create Bomb
		int up1ID = Game.generateUniqueId();
		Position bombPos = player1Pos.translateBy(Direction.DOWN);
		UnpickedUpItem unpick1 = new UnpickedUpItem(up1ID, "bomb", bombPos, "Bomb");
        game1.getEntities().add(unpick1);

		//Create Switch
		int s1ID = Game.generateUniqueId();
		Position switchPos = new Position(1, 4);
		FloorSwitch switch1 = new FloorSwitch(s1ID, switchPos);
        game1.getEntities().add(switch1);

		//Create Boulder
		int bID = Game.generateUniqueId();
		Position boulderPos = new Position(1, 5);
		Boulder boulder1 = new Boulder(bID, boulderPos);
        game1.getEntities().add(boulder1);

		//Move down and collect bomb
		game1.tick(null, Direction.DOWN);

		//Make sure bomb is picked up
		assertTrue(inventory1.getItem(up1ID) != null);

		//Move down and place bomb
		game1.tick(null, Direction.DOWN);
	    game1.tick(String.valueOf(up1ID), Direction.NONE);

        //Test bomb placed
        Boolean bombPresent = false;
        for (Entity ent : game1.getEntities()) {
            if (ent instanceof PlacedBomb) {
                bombPresent = true;
            }
        }
        assertTrue(bombPresent);

        //Push Bulder
        game1.tick(null, Direction.RIGHT);
        for(int i = 0; i < 3; i++) {
            game1.tick(null, Direction.DOWN);
        }
        game1.tick(null, Direction.LEFT);
        game1.tick(null, Direction.UP);

        //Test positions
        assertEquals(player1.getPosition(), boulderPos);
        assertEquals(boulder1.getPosition(), switchPos);

        //Test switch activated
        assertTrue(switch1.getIsActive());

        //Test bomb exploded
        bombPresent = false;
        for (Entity ent : game1.getEntities()) {
            if (ent instanceof PlacedBomb) {
                bombPresent = true;
            }
        }
        assertFalse(bombPresent);

        //Move boulder off switch
        game1.tick(null, Direction.UP);

        //Test positions
        assertEquals(player1.getPosition(), switchPos);
        assertEquals(boulder1.getPosition(), switchPos.translateBy(Direction.UP));

        //Test Switch deactivates
        assertFalse(switch1.getIsActive());



	}

	//-----Portal Tests-----
	//Tests to see if a player sucessfully will pass through a portal
	@Test
    public void sucessfulPotalTest(){
		//Crete the game
        Game game1 = startGame();

		//Gather Variables
		Character player1 = game1.getPlayer();
		Position player1Pos = player1.getPosition();

		//Create Blue Portal 1
		int pblue1ID = Game.generateUniqueId();
		Portal portalBlue1 = new Portal(pblue1ID, player1Pos.translateBy(Direction.DOWN), "BLUE");
		game1.getEntities().add(portalBlue1);

		//Create Blue Portal 2
		int pblue2ID = Game.generateUniqueId();
		Portal portalBlue2 = new Portal(pblue1ID, new Position(1, 12), "BLUE");
		game1.getEntities().add(portalBlue2);

		//Create Red Portal
		int pred1ID = Game.generateUniqueId();
		Portal portalRed1 = new Portal(pblue1ID, new Position(7, 12), "RED");
		game1.getEntities().add(portalRed1);

		//Move into portal
		game1.tick(null, Direction.DOWN);

		//Check player telported correctly
		assertEquals(player1.getPosition(), portalBlue2.getPosition().translateBy(Direction.DOWN));

	}


    //-----Door Interaction Tests-----
    //Check to see if the sunstone correctly opens the door
    @Test
    public void doorInteractionSunstoneTest(){
    	//Crete the game
        Game game1 = startGame();

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
        Game game1 = startGame();
 
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
        Game game1 = startGame();

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
        Game game1 = startGame();

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