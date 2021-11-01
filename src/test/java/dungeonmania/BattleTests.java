package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class BattleTests {
    @Test
    public void testWinAgainstSpider() {
        DungeonManiaController c = new DungeonManiaController();

        Character character = new Character(1, "Character", new Position(5,5));
        MovingEntity spider1 = new Spider(0, new Position(5,5), new SquareMovement());
        BattleManager bat = new BattleManager(character, spider1, Collections.emptyList());

        List<Battleable> alive = new ArrayList<>();
        alive.add(character);
 
        assertEquals(alive, bat.battle());
    }

    @Test
    public void testWinAgainstSpiderWithAlly() {
        DungeonManiaController c = new DungeonManiaController();

        Character character = new Character(1, "Character", new Position(5,5));
        MovingEntity spider1 = new Spider(0, new Position(5,5), new SquareMovement());
        List<Mercenary> mercs = new ArrayList<>();
        Mercenary merc = new Mercenary(2, new Position(5,4), 1, character);
        merc.interact(character);
        mercs.add(merc);

        BattleManager bat = new BattleManager(character, spider1, mercs);

        List<Battleable> alive = new ArrayList<>();
        alive.add(character);
        alive.add(merc);
 
        assertEquals(alive, bat.battle());
        
    }




}
