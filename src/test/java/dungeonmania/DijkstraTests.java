package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class DijkstraTests {
    
    private Map<PositionSimple, Map<PositionSimple, Double>> generateMazeGrid(){
        DungeonManiaController d = new DungeonManiaController();
        d.newGame("maze", "Peaceful");
        return d.getCurrentlyAccessingGame().generateAdjacencyMatrix();
    }

    @Test
    public void testNoObstructionStraight() {
        Character player = new Character(0, new Position(1, 1));
        ChaseMovement m = new ChaseMovement();
        m.setTarget(player);
        Map<PositionSimple, Map<PositionSimple, Double>> grid = generateMazeGrid();
        assertEquals(m.move(new Position(1, 3), grid), new Position(1, 2));
    }

    @Test
    public void testNoObstructionBent() {
        Character player = new Character(0, new Position(1, 1));
        ChaseMovement m = new ChaseMovement();
        m.setTarget(player);
        Map<PositionSimple, Map<PositionSimple, Double>> grid = generateMazeGrid();
        assertEquals(m.move(new Position(2, 3), grid), new Position(1, 3));
    }

    @Test
    public void testObstructionStraight() {
        Character player = new Character(0, new Position(1, 1));
        ChaseMovement m = new ChaseMovement();
        m.setTarget(player);
        Map<PositionSimple, Map<PositionSimple, Double>> grid = generateMazeGrid();
        assertEquals(m.move(new Position(3, 1), grid), new Position(3, 2));
    }

    @Test
    public void testObstructionBent() {
        Character player = new Character(0, new Position(1, 1));
        ChaseMovement m = new ChaseMovement();
        m.setTarget(player);
        DungeonManiaController d = new DungeonManiaController();
        d.newGame("advanced-2", "Hard");
        Map<PositionSimple, Map<PositionSimple, Double>> grid = d.getCurrentlyAccessingGame().generateAdjacencyMatrix();
        assertEquals(m.move(new Position(3, 5), grid), new Position(3, 4));
    }

    @Test
    public void testSwampStraight() {
        DungeonManiaController d = new DungeonManiaController();
        d.newGame("advanced-2", "Hard");
        Mercenary merc = null;
        for(Entity ent : d.getCurrentlyAccessingGame().getEntities()){
            if(ent instanceof Mercenary){
                merc = (Mercenary)ent;
            }
        }
        Position pos = null;
        List<EntityResponse> res = d.tick(null, Direction.NONE).getEntities();
        for (EntityResponse s : res){
            if(Integer.parseInt(s.getId()) == merc.getId()){
                pos = s.getPosition();
            }
        }
        //assertEquals(pos, new Position(3, 4));


    }

    



}
