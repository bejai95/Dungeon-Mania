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


public class StaticEntityTest {
    //-----StaticEntity Tests-----
    //Check to see if removing static entity from list works
    @Test
    public void testRemoveStaticEntity(){
        Position p1 = new Position(0, 0);
        Position p2 = new Position(0, 1);
        StaticEntity s1 = new StaticEntity(1, "Boulder", p1);
        List<StaticEntity> oneItemList = StaticEntity.getStaticEntitiesList();
        StaticEntity s2 = new StaticEntity(2, "Wall", p2);
        s2.removeStaticEntity();
        List<StaticEntity> afterDeleteList = StaticEntity.getStaticEntitiesList();
        assertEquals(oneItemList, afterDeleteList);
    }

    //Checks to see if isCollision correctlys detects a collision
    @Test
    public void testIsCollisionTrue(){
        Position p1 = new Position(1, 1);
        Position p2 = new Position(1, 1);
        StaticEntity s1 = new StaticEntity(1, "Boulder", p1);
        assertTrue(s1.isCollision(p2));
    }   

    //Checks to see if isCollision correctlys detects that there is no collision
    @Test
    public void testIsCollisionFalse(){
        Position p1 = new Position(1, 1);
        Position p2 = new Position(0, 1);
        StaticEntity s1 = new StaticEntity(1, "Boulder", p1);
        assertFalse(s1.isCollision(p2));
    }  
    
    // //-----Door Tests-----
    // //Tests to see if a matching key will unlock the door
    // @Test
    // public void testOpenDoorMatch(){
    //     Position p1 = new Position(1, 1);
    //     Key k1 = new Key(0);
    //     Door d1 = new Door(1, "Door", p1, k1);
    //     d1.openDoor(k1);
    //     assertTrue(d1.getIsOpen());
    // } 
    
    // //Tests to see if a unmatching key will fail to unlock the door
    // @Test
    // public void testOpenDoorUnmatch(){
    //     Position p1 = new Position(1, 1);
    //     Key k1 = new Key(0);
    //     Key k2 = new Key(1);
    //     Door d1 = new Door(2, "Door", p1, k1);
    //     d1.openDoor(k2);
    //     assertFalse(d1.getIsOpen());
    // }      

    // //-----Portal Tests-----
    // //Tests to see if portal will give the correct teleport location
    // @Test
    // public void testPortalTeleport(){
    //     Position portal1Location = new Position(1, 1);
    //     Position portal2Location = new Position(4, 6);   
    //     Portal portal2 = new Portal(1, "portal", portal2Location);
    //     Portal portal1 = new Portal(0, "portal", portal1Location, portal2);
    //     assertEquals(portal2.getPosition(), portal1.getTeleportLocation());
    // }   

    //-----Boulder-----
    //Tests moving a boulder onto an empty cell
    @Test
    public void testBoulderMoveOpen(){
        Position startPos = new Position(1, 1);        
        Position expectedPos = startPos.translateBy(Direction.RIGHT);
        Boulder b1 = new Boulder(1, "Boulder", startPos);
        b1.move(Direction.RIGHT);
        assertEquals(expectedPos, b1.getPosition());
    }   

    //Tests moving a boulder onto a wall
    @Test
    public void testBoulderMoveCollide(){
        Position startPos = new Position(1, 1);        
        Position wallPos = startPos.translateBy(Direction.RIGHT);
        Position expectedPos = startPos;
        Boulder b1 = new Boulder(1, "Boulder", startPos);
        Wall w1 = new Wall(2, "wall", wallPos);
        b1.move(Direction.RIGHT);
        assertEquals(expectedPos, b1.getPosition());
    }    


    //-----Floor Switch Tests-----
    //Tests to see if the isFloorSwitch function correctly recognises a floor switch
    @Test
    public void testIsFloorSwitch(){
        Position floorSwitchLocation = new Position(1, 1);
        FloorSwitch f1 = new FloorSwitch(0, "floor_switch", floorSwitchLocation);



        // assertEquals(, portal1.getTeleportLocation());
    }   
}
