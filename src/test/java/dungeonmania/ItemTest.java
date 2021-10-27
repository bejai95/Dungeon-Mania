package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotEquals;;


/**
 * Tests all the item subclasses and there functionality
 */
public class ItemTest {
    //Test that when items are used their uses are decreased
    @Test
    public void armourTests() {
        Armour a = new Armour(1);
        int startingUses = a.getUses();
        a.consume();
        //the consume function should decrease the amount of uses
        assertNotEquals(startingUses, a.getUses());

        TheOneRing t = new TheOneRing(2);
        startingUses = t.getUses();
        t.consume();
        //the consume function should decrease the amount of uses
        assertNotEquals(startingUses, t.getUses());

        Bow b = new Bow(3);
        startingUses = b.getUses();
        b.consume();
        //the consume function should decrease the amount of uses
        assertNotEquals(startingUses, b.getUses());

        Shield s = new Shield(4);
        startingUses = s.getUses();
        s.consume();
        //the consume function should decrease the amount of uses
        assertNotEquals(startingUses, s.getUses());

        Key k = new Key(5);
        startingUses = k.getUses();
        k.consume();
        //the consume function should decrease the amount of uses
        assertNotEquals(startingUses, k.getUses());

        HealthPotion hp = new HealthPotion(6);
        startingUses = hp.getUses();
        hp.consume();
        //the consume function should decrease the amount of uses
        assertNotEquals(startingUses, hp.getUses());

        InvincibilityPotion invic = new InvincibilityPotion(7);
        startingUses = invic.getUses();
        invic.consume();
        //the consume function should decrease the amount of uses
        assertNotEquals(startingUses, invic.getUses());

        InvisibilityPotion invis = new InvisibilityPotion(8);
        startingUses = invis.getUses();
        invis.consume();
        //the consume function should decrease the amount of uses
        assertNotEquals(startingUses, invis.getUses());

    }
    @Test
    public void theOneRingTests() {

    }
    @Test
    public void bowTests() {
        
    }
    @Test
    public void shieldTests() {
        
    }
    @Test
    public void swordTests() {
        
    }
    @Test
    public void treasureTests() {
        
    }
    @Test
    public void keyTests() {
        
    }
    @Test
    public void healthPotionTests() {
        
    }
    @Test
    public void invicibilityTests() {
        
    }
    @Test
    public void bombTests() {
        
    }
    @Test
    public void woodTests() {
        
    }
    @Test
    public void arrowTests() {
        
    }
    /**
     * Initalise the battle so we can test the values of certain items when used
     */
    public void initBattle() {

    }
}