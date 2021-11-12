package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class MovementTest {
    
    @Test
    public void testSpiderMove() {

        MovingEntity spider1 = new Spider(0, new Position(0,0), new SquareMovement());

        // Spider's first move should move into the circle (top side)
        spider1.move();
        assertEquals(spider1.getPosition(), new Position(0,0).translateBy(Direction.UP));

        // First quarter of square movement (right side)
        spider1.move();
        spider1.move();
        assertEquals(spider1.getPosition(), new Position(0,0).translateBy(Direction.RIGHT));

        // Second quarter of square movement (bottom side)
        spider1.move();
        spider1.move();
        assertEquals(spider1.getPosition(), new Position(0,0).translateBy(Direction.DOWN));

        // Third quarter of square movement (left side)
        spider1.move();
        spider1.move();
        assertEquals(spider1.getPosition(), new Position(0,0).translateBy(Direction.LEFT));
    
        // Full loop of square movement (top side)
        spider1.move();
        spider1.move();
        assertEquals(spider1.getPosition(), new Position(0,0).translateBy(Direction.UP));

    }

    @Test
    public void testZombieMove() {
        MovingEntity zombie1 = new ZombieToast(0, new Position(0,0), new RandomMovement());

        for (int i = 0; i < 100; i++) {
            Position originalPos = zombie1.getPosition();
            zombie1.move();
            assert(Position.isAdjacent(originalPos,zombie1.getPosition()));
        }
    }

    @Test
    public void testMercMove() {
        Character c = new Character(1, new Position(0, 0));

        // Chase when above target
        Mercenary merc1 = new Mercenary(0, new Position(0,3));
        merc1.chase(c);
        merc1.move();
        assertEquals(merc1.getPosition(), new Position(0, 2));

        // Chase when to the right of target
        merc1.setPosition(new Position(3, 0));
        merc1.move();
        assertEquals(merc1.getPosition(), new Position(2, 0));

        // Chase when below target
        merc1.setPosition(new Position(0, -3));
        merc1.move();
        assertEquals(merc1.getPosition(), new Position(0, -2));

        // Chase when left of target
        merc1.setPosition(new Position(-3, 0));
        merc1.move();
        assertEquals(merc1.getPosition(), new Position(-2, 0));

        // Chase when up-right of target
        merc1.setPosition(new Position(2, 2));
        merc1.move();
        merc1.move();
        assertEquals(merc1.getPosition(), new Position(1, 1));

        // Chase when down-right of target
        merc1.setPosition(new Position(2, -2));
        merc1.move();
        merc1.move();
        assertEquals(merc1.getPosition(), new Position(1, -1));

        // Chase when down-left of target
        merc1.setPosition(new Position(-2, -2));
        merc1.move();
        merc1.move();
        assertEquals(merc1.getPosition(), new Position(-1, -1));

        // Chase when up-left of target
        merc1.setPosition(new Position(-2, 2));
        merc1.move();
        merc1.move();
        assertEquals(merc1.getPosition(), new Position(-1, 1));
    }

    

}
