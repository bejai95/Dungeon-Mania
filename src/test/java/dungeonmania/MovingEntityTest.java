package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import dungeonmania.util.Position;

public class MovingEntityTest {
    
    @Test
    public void testSpiderMove() {

        MovingEntity spider1 = new Spider(0, new Position(0,0), new SquareMovement());

        // Spider's first move should move into the circle (top side)
        spider1.move();
        assertEquals(spider1.getPosition(), new Position(0,1));

        // First quarter of square movement (right side)
        spider1.move();
        spider1.move();
        assertEquals(spider1.getPosition(), new Position(1,0));

        // Second quarter of square movement (bottom side)
        spider1.move();
        spider1.move();
        assertEquals(spider1.getPosition(), new Position(0,-1));

        // Third quarter of square movement (left side)
        spider1.move();
        spider1.move();
        assertEquals(spider1.getPosition(), new Position(-1,0));
    
        // Full loop of square movement (top side)
        spider1.move();
        spider1.move();
        assertEquals(spider1.getPosition(), new Position(0,1));

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
        Character character = new Character(1, "Character", new Position(0, 0));

        // Chase when above target
        MovingEntity merc1 = new Mercenary(0, new Position(0,3), 1, character);
        merc1.move();
        merc1.move();
        assertEquals(merc1.getPosition(), new Position(0, 1));

        // Chase when to the right of target
        character.setPosition(new Position(3, 0));
        merc1.move();
        merc1.move();
        assertEquals(merc1.getPosition(), new Position(1, 0));

        // Chase when below target
        character.setPosition(new Position(0, -3));
        merc1.move();
        merc1.move();
        assertEquals(merc1.getPosition(), new Position(0, -1));

        // Chase when left of target
        character.setPosition(new Position(-3, 0));
        merc1.move();
        merc1.move();
        assertEquals(merc1.getPosition(), new Position(-1, 0));

        //TODO Chase diagonally
    }

    @Test
    public void testBribe() {

    }

}
