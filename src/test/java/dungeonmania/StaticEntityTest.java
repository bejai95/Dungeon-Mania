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

// public class StaticEntityTest {
//     //-----StaticEntity Tests-----
//     //Check to see if removing static entity from list works
//     @Test
//     public void testRemoveStaticEntity(){
//         Position p1 = new Position(0, 0);
//         Position p2 = new Position(0, 1);
//         StaticEntity s1 = new StaticEntity(1, "Boulder", p1);
//         List<StaticEntity> oneItemList = new ArrayList<StaticEntity>(StaticEntity.getStaticEntitiesList());
//         StaticEntity s2 = new StaticEntity(2, "Wall", p2);
//         s2.removeStaticEntity();
//         List<StaticEntity> afterDeleteList = new ArrayList<StaticEntity>(StaticEntity.getStaticEntitiesList());
//         assertEquals(oneItemList, afterDeleteList);
//     }

//     //Checks to see if isCollision correctlys detects a collision
//     @Test
//     public void testIsCollisionTrue(){
//         Position p1 = new Position(1, 1);
//         Position p2 = new Position(1, 1);
//         StaticEntity s1 = new StaticEntity(1, "Boulder", p1);
//         assertTrue(s1.isCollision(p2));
//     }   

//     //Checks to see if isCollision correctlys detects that there is no collision
//     @Test
//     public void testIsCollisionFalse(){
//         Position p1 = new Position(1, 1);
//         Position p2 = new Position(0, 1);
//         StaticEntity s1 = new StaticEntity(1, "Boulder", p1);
//         assertFalse(s1.isCollision(p2));
//     }  
    
//     //-----Door Tests-----
//     //Tests to see if a matching key will unlock the door
//     @Test
//     public void testOpenDoorMatch(){
//         Position p1 = new Position(1, 1);
//         Key k1 = new Key(0,1);
//         Door d1 = new Door(1, "Door", p1, 1);
//         d1.openDoor(k1);
//         assertTrue(d1.getIsOpen());
//     } 
    
//     //Tests to see if a unmatching key will fail to unlock the door
//     @Test
//     public void testOpenDoorUnmatch(){
//         Position p1 = new Position(1, 1);
//         Key k1 = new Key(0,1);
//         Door d1 = new Door(1, "Door", p1, 2);
//         d1.openDoor(k1);
//         assertFalse(d1.getIsOpen());
//     }      

//     //-----Portal Tests-----
//     //Tests to see if portal will give the correct teleport location
//     @Test
//     public void testPortalTeleport(){
//         Position portal1Location = new Position(1, 1);
//         Position portal2Location = new Position(4, 6);  
//         Position portal3Location = new Position(7, 9);  
//         Position portal4Location = new Position(9, 9);  
//         Position expectedTeleport1Location = portal3Location; 
//         Position expectedTeleport2Location = portal4Location; 
//         Portal portal1 = new Portal(1, "portal", portal1Location, "blue");
//         Portal portal2 = new Portal(2, "portal", portal2Location, "green");
//         Portal portal3 = new Portal(3, "portal", portal3Location, "blue");
//         Portal portal4 = new Portal(4, "portal", portal4Location, "green");
//         assertEquals(expectedTeleport1Location, portal1.getTeleportLocation());
//         assertEquals(expectedTeleport2Location, portal2.getTeleportLocation());
//     }   

//     //-----Boulder-----
//     //Tests moving a boulder onto an empty cell

//     //has issues, if done in all tests it doesnt work, if u run just this test it works. Maybe a problem with static?
//     @Test
//     public void testBoulderMoveOpen(){
//         Position startPos = new Position(1, 1);        
//         Position expectedPos = startPos.translateBy(Direction.RIGHT);
//         Boulder b1 = new Boulder(1, "Boulder", startPos);
//         b1.move(Direction.RIGHT);
//         assertEquals(expectedPos, b1.getPosition());
//     }   

//     //Tests moving a boulder onto a wall
//     @Test
//     public void testBoulderMoveCollide(){
//         Position startPos = new Position(1, 1);        
//         Position wallPos = startPos.translateBy(Direction.RIGHT);
//         Position expectedPos = startPos;
//         Boulder b1 = new Boulder(1, "Boulder", startPos);
//         Wall w1 = new Wall(2, "wall", wallPos);
//         b1.move(Direction.RIGHT);
//         assertEquals(expectedPos, b1.getPosition());
//     }    


//     //-----Floor Switch Tests-----
//     //Tests to see if the isFloorSwitch function correctly recognises a floor switch
//     @Test
//     public void testIsFloorSwitch(){
//         Position floorSwitchLocation = new Position(1, 1);
//         Position emptyLocation = new Position(0, 0);
//         FloorSwitch f1 = new FloorSwitch(0, "floor_switch", floorSwitchLocation);
//         assertEquals(f1, FloorSwitch.isFloorSwitch(floorSwitchLocation));
//         assertEquals(null, FloorSwitch.isFloorSwitch(emptyLocation));        
//     }  
    
//     /*Tests to see if the isFloorSwitch activates when boulder is placed on top
//     and then deactivated when it is moved off it*/
//     @Test
//     public void testIsFloorActivate(){
//         Position boulderPos = new Position(1, 1, 1);        
//         Position floorSwitchPos = new Position(2, 1, 0);  
//         Boulder b1 = new Boulder(0, "Boulder", boulderPos);
//         FloorSwitch f1 = new FloorSwitch(0, "floor_switch", floorSwitchPos);
//         assertFalse(f1.getIsActive());
//         b1.move(Direction.RIGHT);
//         assertTrue(f1.getIsActive());
//         b1.move(Direction.RIGHT);
//         assertFalse(f1.getIsActive());
//     }  
    
//     //-----Place Bomb Tests-----
//     //Tests to see if a bomb explodes on placement by seeing if it is removed from the list
//     @Test
//     public void testExplodeOnPlacement(){
//         Position boulderPos = new Position(1, 1, 1);        
//         Position floorSwitchPos = new Position(2, 1, 0); 
//         Position bombPos1 = new Position(8, 8, 8); 
//         Position bombPos2 = floorSwitchPos.translateBy(Direction.RIGHT); 
//         Boulder boulder1 = new Boulder(0, "Boulder", boulderPos);
//         FloorSwitch f1 = new FloorSwitch(0, "floor_switch", floorSwitchPos);
//         boulder1.move(Direction.RIGHT);
//         PlacedBomb bomb1 = new PlacedBomb(0, "bomb", bombPos1);
//         List<PlacedBomb> initialBombList = new ArrayList<PlacedBomb>(PlacedBomb.getPlacedBombList());
//         PlacedBomb bomb2 = new PlacedBomb(0, "bomb", bombPos2);    
//         List<PlacedBomb> postBombList = new ArrayList<PlacedBomb>(PlacedBomb.getPlacedBombList());
//         assertEquals(initialBombList, postBombList);
//     }     
    
//     //Tests to see if a bomb explodes when a boulder is moved onto a switch
//     @Test
//     public void testExplodeOnSwitchMove(){
//         Position boulderPos = new Position(1, 1, 1);        
//         Position floorSwitchPos = new Position(2, 1, 0); 
//         Position bombPos1 = new Position(8, 8, 8); 
//         Position bombPos2 = floorSwitchPos.translateBy(Direction.RIGHT); 
//         Boulder boulder1 = new Boulder(0, "Boulder", boulderPos);
//         FloorSwitch f1 = new FloorSwitch(0, "floor_switch", floorSwitchPos);
//         PlacedBomb bomb1 = new PlacedBomb(0, "bomb", bombPos1);
//         List<PlacedBomb> initialBombList = new ArrayList<PlacedBomb>(PlacedBomb.getPlacedBombList());
//         PlacedBomb bomb2 = new PlacedBomb(0, "bomb", bombPos2);    
//         boulder1.move(Direction.RIGHT);
//         List<PlacedBomb> postBombList = new ArrayList<PlacedBomb>(PlacedBomb.getPlacedBombList());
//         assertEquals(initialBombList, postBombList);
//     }   
    
//     //-----Zombie Toast Spawner-----
//     //Checks to see if Zombies are spawned under the right conditions
//     @Test
//     public void testZombieToastSpawner(){
//         Position spawnerPos = new Position(1,1); 
//         ZombieToastSpawner newSpawner = new ZombieToastSpawner(1, "spawner", spawnerPos);
//         ZombieToast newZombie1 = newSpawner.spawn(15, "hard");
//         ZombieToast newZombie2 = newSpawner.spawn(20, "easy");
//         ZombieToast newZombie3 = newSpawner.spawn(20, "hard");
//         ZombieToast newZombie4 = newSpawner.spawn(15, "easy");
//         ZombieToast newZombie5 = newSpawner.spawn(30, "hard");
//         ZombieToast newZombie6 = newSpawner.spawn(80, "easy");
//         assertFalse(newZombie1 == null);
//         assertFalse(newZombie2 == null);
//         assertTrue(newZombie3 == null);
//         assertTrue(newZombie4 == null);
//         assertFalse(newZombie1 == null);
//         assertFalse(newZombie2 == null);
//     }  
    
//     //Checks to see if Zombie toast spawner correctly finds the empty tile 
//     @Test
//     public void testZombieToastSpawnerLocation(){
//         Position wallPos1 = new Position(0,0); 
//         Position wallPos2 = new Position(1,0); 
//         Position wallPos3 = new Position(2,0); 
//         Position wallPos4 = new Position(0,1); 
//         Position wallPos5 = new Position(2,1); 
//         Position wallPos6 = new Position(0,2); 
//         Position wallPos7 = new Position(1,2);     
//         Wall newWall1 = new Wall(0, "wall", wallPos1);    
//         Wall newWall2 = new Wall(1, "wall", wallPos2);  
//         Wall newWall3 = new Wall(2, "wall", wallPos3);  
//         Wall newWall4 = new Wall(3, "wall", wallPos4);  
//         Wall newWall5 = new Wall(4, "wall", wallPos5);  
//         Wall newWall6 = new Wall(5, "wall", wallPos6);  
//         Wall newWall7 = new Wall(6, "wall", wallPos7);  

//         Position spawnerPos = new Position(1,1); 
//         ZombieToastSpawner newSpawner = new ZombieToastSpawner(7, "spawner", spawnerPos);

//         ZombieToast newZombie1 = newSpawner.spawn(15, "hard");

//         Position expectedPos = new Position(2,2);
//         assertEquals(expectedPos, newZombie1.getPosition()); 
//     }   

//     //-----UnpickedUpItem-----
//     //Tests that picking up an will give the correct class and itemid
//     @Test
//     public void testUnpickedupItem() throws ClassNotFoundException, IllegalAccessError, IllegalAccessException, InstantiationException, InvocationTargetException, NoSuchMethodException{
//         Position p1 = new Position(0, 0);
//         UnpickedUpItem newUnpickedUpItem = new UnpickedUpItem(0, "unPickedupItem", p1, "Wood");
//         Item newItem = newUnpickedUpItem.pickupItem();
//         assertEquals("class dungeonmania.Wood", String.valueOf(newItem.getClass())); 
//         assertEquals(0, newItem.getitemId()); 
//     }   

//     //Tests picking up a key works correctly
//     @Test
//     public void testUnpickedupItemKey() throws ClassNotFoundException, IllegalAccessError, IllegalAccessException, InstantiationException, InvocationTargetException, NoSuchMethodException{
//         Position p1 = new Position(0, 0);
//         UnpickedUpItem newUnpickedUpItem = new UnpickedUpItem(0, "unPickedupItem", p1, "Key", 2);
//         Item newItem = newUnpickedUpItem.pickupItem();
//         Key newKey = (Key)newItem;
//         assertEquals(2,newKey.getKeyNum()); 

//     }   
// }