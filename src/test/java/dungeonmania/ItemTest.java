package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import dungeonmania.util.Position;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotEquals;


/**
 * Tests all the item subclasses and there functionality
 */
public class ItemTest {
    //Test that when items are used their uses are decreased
    @Test
    public void usesTests() {
        Character character = new Character(3, Character.class.getSimpleName(),new Position(3, 4));
        Armour a = new Armour(1);
        int startingUses = a.getUses();
        a.consume(character);
        //the consume function should decrease the amount of uses
        assertNotEquals(startingUses, a.getUses());

        TheOneRing t = new TheOneRing(2);
        startingUses = t.getUses();
        t.consume(character);
        //the consume function should decrease the amount of uses
        assertNotEquals(startingUses, t.getUses());

        Bow b = new Bow(3);
        startingUses = b.getUses();
        b.consume(character);
        //the consume function should decrease the amount of uses
        assertNotEquals(startingUses, b.getUses());

        Shield s = new Shield(4);
        startingUses = s.getUses();
        s.consume(character);
        //the consume function should decrease the amount of uses
        assertNotEquals(startingUses, s.getUses());

        Key k = new Key(5,1);
        startingUses = k.getUses();
        k.consume(character);
        //the consume function should decrease the amount of uses
        assertNotEquals(startingUses, k.getUses());

        HealthPotion hp = new HealthPotion(6);
        startingUses = hp.getUses();
        hp.consume(character);
        //the consume function should decrease the amount of uses
        assertNotEquals(startingUses, hp.getUses());

        InvincibilityPotion invic = new InvincibilityPotion(7);
        startingUses = invic.getUses();
        invic.consume(character);
        //the consume function should decrease the amount of uses
        assertNotEquals(startingUses, invic.getUses());

        InvisibilityPotion invis = new InvisibilityPotion(8);
        startingUses = invis.getUses();
        invis.consume(character);
        //the consume function should decrease the amount of uses
        assertNotEquals(startingUses, invis.getUses());

    }
    @Test
    public void theOneRingTests() {
        //test that revive does work
        Character character = new Character(3, "Character", new Position(3, 4));
        //now set health to 0 to pretend died
        character.setHealth(0);
        character.revive();
        assertFalse(character.getHealth() == 100);
        //give the one ring
        TheOneRing ring = new TheOneRing(4);
        character.inventory.addItemToInventory(ring);
        character.revive();
        assertTrue(character.getHealth() == character.getMaxHealth());
    }
    @Test
    public void healthPotionTests() {
        Character character = new Character(3, Character.class.getCanonicalName(), new Position(3, 4));
        HealthPotion hp = new HealthPotion(4);
        character.inventory.addItemToInventory(hp);
        character.setHealth(50);
        //simulate a battle
        character.use(hp);
        assertTrue(character.getHealth() == character.getMaxHealth());

    }
    @Test
    public void invicibilityTests() {
        Character character = new Character(3, Character.class.getCanonicalName(), new Position(3, 4));
        InvincibilityPotion ip = new InvincibilityPotion(4);
        character.inventory.addItemToInventory(ip);
        character.use(ip);
        assertTrue(character.getInvincibleLength() == 3);
        assertTrue(character.isInvincible());
        assertTrue(character.getInvincibleLength() == 2);
        assertTrue(character.isInvincible());
        assertTrue(character.getInvincibleLength() == 1);
        assertTrue(character.isInvincible());
        assertTrue(character.getInvincibleLength() == 0);
        //used it 3 times already so shouldnt work
        assertTrue(!(character.isInvincible()));
    }
    @Test
    public void InvisibilityTests() {
        Character character = new Character(3, Character.class.getCanonicalName(), new Position(3, 4));
        InvisibilityPotion ip = new InvisibilityPotion(4);
        character.inventory.addItemToInventory(ip);
        character.use(ip);
        assertTrue(character.getInvisibleLength() == 3);
        assertTrue(character.isInvisible());
        assertTrue(character.getInvisibleLength() == 2);
        assertTrue(character.isInvisible());
        assertTrue(character.getInvisibleLength() == 1);
        assertTrue(character.isInvisible());
        assertTrue(character.getInvisibleLength() == 0);
        //used it 3 times already so shouldnt work
        assertTrue(!(character.isInvisible()));
    }
    @Test
    public void getTypeNameTest() {
        Bow bow = new Bow(3);
        assertTrue(bow.getType().equals("bow"));
    }
}