package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collections;

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
        Character character = new Character(1, new Position(5,5));
        character.move(Direction.UP);
        //have not moved horizontally
        assertEquals(character.getPosition().getX(), 5);
        assertEquals(character.getPosition().getY(), 4);
        //move 6 left
        character.move(Direction.LEFT);
        character.move(Direction.LEFT);
        character.move(Direction.LEFT);
        character.move(Direction.LEFT);
        character.move(Direction.LEFT);
        character.move(Direction.LEFT);
        assertEquals(character.getPosition().getX(), -1);
        assertEquals(character.getPosition().getY(), 4);
        //move one to the right
        character.move(Direction.RIGHT);
        assertEquals(character.getPosition().getX(), 0);
        assertEquals(character.getPosition().getY(), 4);
        //move down
        character.move(Direction.DOWN);
        assertEquals(character.getPosition().getX(), 0);
        assertEquals(character.getPosition().getY(), 5);
    }
    //THIS TEST WILL GET MOVED TO CHECKING THAT INTERFACE TODO
    /*
    @Test
    public void testPickUpItem() {
        Character character = new Character(1, "Character", new Position(5,5));
        //constructor for a treasure
        Treasure t1 = new UnPickedUpItem(3, new Position(5,5), "UnpickedUpItem", "Treasure");
        //check no throw since item is on same getPosition()
        assertDoesNotThrow(() -> {character.pickUpItem(t1.getitemId());});
        //check that it goes in the inventory as a certain type, wont be null if in inventory
        assertNotEquals(character.inventory.getItem(t1.getitemId()), null);
        //check when item not on same getPosition()
        Treasure t2 = new UnPickedUpItem(4, new Position(7,7), "UnpickedUpItem", "Treasure");
        assertThrows(InvalidActionException.class, () -> {character.pickUpItem(t2.getitemId());});

        //test trying to pickup item that does not exist
        assertThrows(InvalidActionException.class,() -> {character.pickUpItem(202020);});
    }*/
    @Test
    public void testGetDamage() {
        //test no items
        Character character = new Character(1, new Position(5,5));
        assertEquals(character.getDamage(), character.getBaseDamage());
        //test with items
        Sword sword = new Sword(4);
        //sword is now in inventory
        character.getInventory().addItemToInventory(sword);
        assertEquals(character.getDamage(), character.getBaseDamage() + sword.getDamage());
        //test that bow doubles it
        Bow bow = new Bow(6);
        character.getInventory().addItemToInventory(bow);
        assertEquals(character.getDamage(), bow.getAmountOfAttacks()*(character.getBaseDamage() + sword.getDamage()));
    }
    @Test
    public void testGetDefense() {
        //test that defense does nothing
        Character character = new Character(1, new Position(5,5));
        assertEquals(character.getDefense(), character.getBaseDefense());
        //test that defense does more with items
        Shield shield = new Shield(2);
        character.getInventory().addItemToInventory(shield);
        assertEquals(character.getDefense(), character.getBaseDefense() + 0.25);
        //make sure armour stacks with shields
        Armour armour = new Armour(3);
        character.getInventory().addItemToInventory(armour);
        assertEquals(character.getDefense(), character.getBaseDefense() + 0.25 + 0.5);
        Armour armour2 = new Armour(4);
        Armour armour4 = new Armour(5);
        Armour armour5 = new Armour(6);
        Armour armour64 = new Armour(7);
        Armour armour65 = new Armour(8);
        Armour armour66 = new Armour(9);
        character.getInventory().addItemToInventory(armour2);
        character.getInventory().addItemToInventory(armour5);
        character.getInventory().addItemToInventory(armour4);
        character.getInventory().addItemToInventory(armour64);
        character.getInventory().addItemToInventory(armour65);
        character.getInventory().addItemToInventory(armour66);
        //make sure max armour is 1
        assertEquals(character.getDefense(), 1);
        //now check that multiplier is zero since should do no damage
        assertEquals(character.getDefenseMultiplier(), 0);

    }
    @Test
    public void testGetHealth() {
        //test before and after a battle
        Character character = new Character(1, new Position(5,5));
        Spider spider = new Spider(3, new Position(5,5), new SquareMovement());
        double healthBeforeBattle = character.getHealth();
        BattleManager battleManager = new BattleManager(character, spider, Collections.emptyList());
        battleManager.battle();
        assertTrue(healthBeforeBattle > character.getHealth());
    }
    @Test
    public void testUse() {
        //test when trying to use an item you cant use throws exceptions
        //test using an item that exist does not throw errors
        Character character = new Character(1, new Position(5,5));
        HealthPotion hp = new HealthPotion(2);
        character.getInventory().addItemToInventory(hp);
        //trying to use should not throw error
        assertDoesNotThrow(()-> {hp.consume(character);;});
        assertTrue(character.getHealth() == character.getMaxHealth());
        //reduce health
        character.setHealth(50);
        //try to use again should do nothing
        hp.consume(character);
        assertTrue(character.getHealth() == 50);
        Bow bow = new Bow(3);
        character.getInventory().addItemToInventory(bow);
        //using a bow should not cause error until after 3 times
        assertDoesNotThrow(()-> {bow.getWeaponInfo();});
        assertDoesNotThrow(()-> {bow.getWeaponInfo();});
        assertDoesNotThrow(()-> {bow.getWeaponInfo();});
        //bow should be used up
        assertTrue(!(bow.canUse()));
    }
}
