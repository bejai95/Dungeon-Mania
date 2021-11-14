package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

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
        Character character = new Character(3, new Position(3, 4));
        Armour a = new Armour(1);
        int startingUses = a.getUses();
        a.getMultipler();
        //the consume function should decrease the amount of uses
        assertNotEquals(startingUses, a.getUses());

        TheOneRing t = new TheOneRing(2);
        startingUses = t.getUses();
        t.consume(character);
        //the consume function should decrease the amount of uses
        assertNotEquals(startingUses, t.getUses());

        Bow b = new Bow(3);
        startingUses = b.getUses();
        b.getWeaponInfo();
        //the consume function should decrease the amount of uses
        assertNotEquals(startingUses, b.getUses());

        Shield s = new Shield(4);
        startingUses = s.getUses();
        s.getMultipler();
        //the consume function should decrease the amount of uses
        assertNotEquals(startingUses, s.getUses());

        Key k = new Key(5,1);
        startingUses = k.getUses();
        k.use();
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
        Character character = new Character(3, new Position(3, 4));
        //now set health to 0 to pretend died, did nothing since one ring
        character.revive();
        assertTrue(character.getHealth() == character.getMaxHealth());
        character.setHealth(0);
        character.revive();
        assertTrue(character.getHealth() == 0);
        //give the one ring
        TheOneRing ring = new TheOneRing(4);
        character.getInventory().addItemToInventory(ring);
        character.revive();
        assertTrue(character.getHealth() == character.getMaxHealth());
        //test that revive does nothing when you reuse the one ring
        character.setHealth(0);
        ring.consume(character);
        //now asssert nothing changed
        assertTrue(character.getHealth() == 0);
    }
    @Test
    public void healthPotionTests() {
        Character character = new Character(3, new Position(3, 4));
        HealthPotion hp = new HealthPotion(4);
        character.getInventory().addItemToInventory(hp);
        character.setHealth(50);
        //simulate a battle
        hp.consume(character);
        assertTrue(character.getHealth() == character.getMaxHealth());

    }
    @Test
    public void invicibilityTests() {
        Character character = new Character(3, new Position(3, 4));
        InvincibilityPotion ip = new InvincibilityPotion(4);
        character.getInventory().addItemToInventory(ip);
        ip.consume(character);
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
        Character character = new Character(3, new Position(3, 4));
        InvisibilityPotion ip = new InvisibilityPotion(4);
        character.getInventory().addItemToInventory(ip);
        ip.consume(character);
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
    @Test
    public void testItemResponse() {
        Bow bow = new Bow(3);
        assertTrue(bow.makeItemReponse().getId().equals(Integer.toString(bow.getitemId())));
        assertTrue(bow.makeItemReponse().getType().equals(bow.getType()));

    }
    @Test
    public void testingBowIfOutOfUses() {
        Bow bow = new Bow(3);
        bow.getWeaponInfo();
        bow.getWeaponInfo();
        bow.getWeaponInfo();
        //use bow 3 times
        //now try
        List<Integer> wep = bow.getWeaponInfo();
        assertTrue(wep.get(0) == 0);
        assertTrue(wep.get(1) == 0);
    }
    @Test
    public void testArmourOutOfUses() {
        Armour armour = new Armour(2);
        armour.getMultipler();
        armour.getMultipler();
        armour.getMultipler();

        assertTrue(armour.getMultipler() == 0);

    }
    @Test
    public void testSwordOutOfUses() {
        Sword sword = new Sword(1);
        sword.getWeaponInfo();
        sword.getWeaponInfo();
        sword.getWeaponInfo();

        List<Integer> wep = sword.getWeaponInfo();
        assertTrue(wep.get(0) == 0);
        assertTrue(wep.get(1) == 0);
    }
    @Test 
    public void invisOutOfUses() {
        InvisibilityPotion i = new InvisibilityPotion(1);
        Character character = new Character(3, new Position(3, 4));
        i.consume(character);
        //used on that character so now on a different character it should have no uses and not work
        //now do it again
        Character character2 = new Character(3, new Position(3, 4));
        i.consume(character2);
        assertTrue(!(character2.isInvisible()));

    }
    @Test 
    public void invincOutOfUses() {
        InvincibilityPotion i = new  InvincibilityPotion(1);
        Character character = new Character(3, new Position(3, 4));
        i.consume(character);
        //used on that character so now on a different character it should have no uses and not work
        //now do it again
        Character character2 = new Character(3, new Position(3, 4));
        i.consume(character2);
        assertTrue(!(character2.isInvincible()));
    }
}