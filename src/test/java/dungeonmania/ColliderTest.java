package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;
import dungeonmania.Game;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class ColliderTest {
    

    @Test
    public void defaultCollider() {

        DungeonManiaController c1 = new DungeonManiaController();
        c1.newGame("maze", "Standard");
        Character ch = c1.getCurrentlyAccessingGame().getPlayer();


        Position originalPos = ch.getPosition();

        // Player is surrounded by walls in all sides but below
        c1.tick(null, Direction.RIGHT);
        assertEquals(originalPos, ch.getPosition());
        c1.tick(null, Direction.LEFT);
        assertEquals(originalPos, ch.getPosition());
        c1.tick(null, Direction.UP);
        assertEquals(originalPos, ch.getPosition());

        // Moving down should actually change position;
        c1.tick(null, Direction.DOWN);
        assertEquals(originalPos.translateBy(Direction.DOWN), ch.getPosition());

    }
}
