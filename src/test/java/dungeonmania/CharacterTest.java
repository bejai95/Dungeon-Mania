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
    /*
    @Test
    public void testMove() {
        //test moving in every direction
        Character character = new Character(1, "Character", 5, 5);
        character.move(Direction.UP);
        //have not moved horizontally
        assertEquals(character.getPosition().getX(), 5);
        assertEquals(character.getPosition().getY(), 6);
        //move 6 left
        character.move(Direction.LEFT);
        character.move(Direction.LEFT);
        character.move(Direction.LEFT);
        character.move(Direction.LEFT);
        character.move(Direction.LEFT);
        character.move(Direction.LEFT);
        assertEquals(character.getPosition().getX(), -1);
        assertEquals(character.getPosition().getY(), 6);
        //move one to the right
        character.move(Direction.RIGHT);
        assertEquals(character.getPosition().getX(), 0);
        assertEquals(character.getPosition().getY(), 6);
        //move down
        character.move(Direction.DOWN);
        assertEquals(character.getPosition().getX(), 0);
        assertEquals(character.getPosition().getY(), 5);
    }
    */
    //THIS TEST WILL GET MOVED TO CHECKING THAT INTERFACE TODO
    //@Test
    /*
    public void testPickUpItem() {
        Character character = new Character(1, "Character", 5, 5);
        //constructor for a treasure
        Treasure t1 = new UnPickedUpItem(3, new getPosition()(5, 5), "UnpickedUpItem", "Treasure");
        //check no throw since item is on same getPosition()
        assertDoesNotThrow(() -> {character.pickUpItem(t1.getitemId());});
        //check that it goes in the inventory as a certain type, wont be null if in inventory
        assertNotEquals(character.inventory.getItem(t1.getitemId()), null);
        //check when item not on same getPosition()
        Treasure t2 = new UnPickedUpItem(4, new getPosition()(7, 7), "UnpickedUpItem", "Treasure");
        assertThrows(InvalidActionException.class, () -> {character.pickUpItem(t2.getitemId());});

        //test trying to pickup item that does not exist
        assertThrows(InvalidActionException.class,() -> {character.pickUpItem(202020);});
    }
    */
    @Test
    public void testGetDamage() {
        //test no items
        Character character = new Character(1, "Character", 5, 5);
        assertEquals(character.getDamage(), character.damage);
        //test with items
        Sword sword = new Sword(4);
        //sword is now in inventory
        character.inventory.addItemToInventory(sword);
        assertEquals(character.getDamage(), character.damage + sword.getDamage());
    }
    @Test
    public void testGetDefense() {
        //test that defense does nothing
        Character character = new Character(1, "Character", 5, 5);
        assertEquals(character.getDefense(), character.baseDefense);
        //test that defense does more with items
        Shield shield = new Shield(2);
        character.inventory.addItemToInventory(shield);
        assertEquals(character.getDefense(), character.baseDefense + shield.getMultipler());
        //make sure armour stacks with shields
        Armour armour = new Armour(3);
        character.inventory.addItemToInventory(armour);
        assertEquals(character.getDefense(), character.baseDefense + shield.getMultipler() + armour.getMultipler());
    }
    /*
    @Test
    public void testGetHealth() {
        //test before and after a battle
        Character character = new Character(1, "Character", 5, 5);
        Spider spider = new Spider(2, new getPosition()(5,5), "Spider");
        double healthBeforeBattle = character.getHealth();
        BattleManager battleManager = new BattleManager();
        battleManager.battle(character, spider);
        assertTrue(healthBeforeBattle > character.getHealth());
    }
    */
    @Test
    public void testUse() {
        //test when trying to use an item you cant use throws exceptions
        //test using an item that exist does not throw errors
        Character character = new Character(1, "Character", 5, 5);
        HealthPotion hp = new HealthPotion(2);
        character.inventory.addItemToInventory(hp);
        //trying to use should not throw error
        assertDoesNotThrow(()-> {character.use(hp);});
        //try to use again should throw error
        assertThrows(InvalidActionException.class, ()-> {character.use(hp);});
        Bow bow = new Bow(3);
        character.inventory.addItemToInventory(bow);
        //using a bow should not cause error until after 3 times
        assertDoesNotThrow(()-> {character.use(bow);});
        assertDoesNotThrow(()-> {character.use(bow);});
        assertDoesNotThrow(()-> {character.use(bow);});
        //bow should be used up
        assertThrows(InvalidActionException.class, ()-> {character.use(bow);});
    }

}
