package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Direction;

public class CharacterTest {
    private EntityResponse getPlayer(List<EntityResponse> entities){
        for(EntityResponse entity : entities){
            if(entity.getType().equals("character")){
                return entity;
            }
        }
        return null;
    }
    
    @Test
    public void testInvalidMove() {
        DungeonManiaController c = new DungeonManiaController();
        DungeonResponse r = c.newGame("maze", "peaceful");
        assertEquals(getPlayer(c.tick(null, Direction.LEFT).getEntities()), getPlayer(r.getEntities()));
    }

    @Test
    public void testValidMove() {
        DungeonManiaController c = new DungeonManiaController();
        DungeonResponse r = c.newGame("maze", "peaceful");
        assertEquals(getPlayer(c.tick(null, Direction.DOWN).getEntities()).getPosition(), getPlayer(r.getEntities()).getPosition().translateBy(Direction.DOWN));
    }

    @Test
    public void testCollisionWithSwitch() {
        DungeonManiaController c = new DungeonManiaController();
        DungeonResponse r = c.newGame("maze", "peaceful");
        DungeonResponse s = c.tick(null, Direction.DOWN);
        assertEquals(getPlayer(c.tick(null, Direction.DOWN).getEntities()).getPosition(), getPlayer(s.getEntities()).getPosition().translateBy(Direction.DOWN));
    }

    @Test
    public void testBombUse() {
        DungeonManiaController c = new DungeonManiaController();
        DungeonResponse r = c.newGame("bomb", "peaceful");
        DungeonResponse s = c.tick(null, Direction.RIGHT);
        assertDoesNotThrow(() -> c.tick("bomb", Direction.RIGHT));

    }


}