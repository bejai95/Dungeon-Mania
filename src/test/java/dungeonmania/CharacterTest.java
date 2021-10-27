package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class CharacterTest {
    @Test
    public void testMove() {
        //test moving in every direction
        Character character = new Character(1, "Character", 5, 5);
        character.move(Direction.UP);
        //have not moved horizontally
        assertEquals(character.position.getX(), 5);
        assertEquals(character.position.getY(), 6);
        //move 6 left
        character.move(Direction.LEFT);
        character.move(Direction.LEFT);
        character.move(Direction.LEFT);
        character.move(Direction.LEFT);
        character.move(Direction.LEFT);
        character.move(Direction.LEFT);
        assertEquals(character.position.getX(), -1);
        assertEquals(character.position.getY(), 6);
        //move one to the right
        character.move(Direction.RIGHT);
        assertEquals(character.position.getX(), 0);
        assertEquals(character.position.getY(), 6);
        //move down
        character.move(Direction.DOWN);
        assertEquals(character.position.getX(), 0);
        assertEquals(character.position.getY(), 5);
    }
    @Test
    public void testPickUpItem() {
        Character character = new Character(1, "Character", 5, 5);
        //constructor for a treasure
        Treasure t1 = new UnPickedUpItem(3, new Position(5, 5), "UnpickedUpItem", "Treasure");
        //check no throw since item is on same position
        assertDoesNotThrow(() -> {character.pickUpItem(t1.getitemId());});
        //check that it goes in the inventory as a certain type, wont be null if in inventory
        assertNotEquals(character.inventory.getItem(t1.getitemId()), null);
        //check when item not on same position
        Treasure t2 = new UnPickedUpItem(4, new Position(7, 7), "UnpickedUpItem", "Treasure");
        assertThrows(InvalidActionException.class, () -> {character.pickUpItem(t2.getitemId());});

        //test trying to pickup item that does not exist
        assertThrows(InvalidActionException.class,() -> {character.pickUpItem(202020);});
    }
    @Test
    public void testGetDamage() {
        //test no items
        Character character = new Character(1, "Character", 5, 5);
        assertEquals(character.getDamage(), character.damage);
        //test with items
        Sword sword = new Sword(4);
        //sword is now in inventory
        character.inventory.addItem(sword);
        assertEquals(character.getDamage(), character.damage + sword.getDamage());
    }
    @Test
    public void testGetDefense() {
        //test that defense does nothing
        Character character = new Character(1, "Character", 5, 5);
        assertEquals(character.getDefense(), character.defense);
        //test that defense does more with items
        Shield shield = new Shield(2);
        character.inventory.addItem(shield);
        assertEquals(character.getDefense(), character.defense + shield.getMultipler());
        //make sure armour stacks with shields
        Armour armour = new Armour(3);
        character.inventory.addItem(armour);
        assertEquals(character.getDefense(), character.defense + shield.getMultipler() + armour.getMultipler());
    }
    @Test
    public void testGetHealth() {
        //test before and after a battle
        Character character = new Character(1, "Character", 5, 5);
        Spider spider = new Spider(2, new Position(5,5), "Spider");
        double healthBeforeBattle = character.getHealth();
        BattleManager battleManager = new BattleManager();
        battleManager.battle(character, spider);
        assertTrue(healthBeforeBattle > character.getHealth());
    }
    @Test
    public void testUse() {
        //test when trying to use an item you cant use throws exceptions
        //test using an item that exist does not throw errors
        Character character = new Character(1, "Character", 5, 5);
        HealthPotion hp = new HealthPotion(2);
        character.inventory.addItem(hp);
        //trying to use should not throw error
        assertDoesNotThrow(()-> {character.use(hp);});
        //try to use again should throw error
        assertThrows(InvalidActionException.class, ()-> {character.use(hp);});
        Bow bow = new Bow(3);
        character.inventory.addItem(bow);
        //using a bow should not cause error until after 3 times
        assertDoesNotThrow(()-> {character.use(bow);});
        assertDoesNotThrow(()-> {character.use(bow);});
        assertDoesNotThrow(()-> {character.use(bow);});
        //bow should be used up
        assertThrows(InvalidActionException.class, ()-> {character.use(bow);});
    }

}
