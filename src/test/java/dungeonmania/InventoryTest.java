package dungeonmania;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotEquals;



public class InventoryTest {

    
    @Test
    public void testCrafting() {
        Inventory inventory = new Inventory();

    }
    @Test
    public void testGenerateBuildables() {

    }
    @Test
    public void testAddItem() {
        Inventory inventory = new Inventory();
        //check add item works
        Sword sword = new Sword(3);
        Key key = new Key(4);
        Wood wood = new Wood(5);
        Treasure treasure = new Treasure(6);
        Shield shield = new Shield(7);
        Bomb bomb = new Bomb(8);
        Armour armour = new Armour(9);
        HealthPotion hp = new HealthPotion(10);

        //check key goes to materials and consumable
        inventory.addItem(key);
        assertTrue(inventory.getItem(key.getitemId(), inventory.getMaterials()) != null);
        assertTrue(inventory.getItem(key.getitemId(), inventory.getConsumables()) != null);

        //check sword goes to consumable and swords
        inventory.addItem(sword);
        assertTrue(inventory.getItem(sword.getitemId(), inventory.getSwords()) != null);
        assertTrue(inventory.getItem(sword.getitemId(), inventory.getConsumables()) != null);

        //check that wood goes to material
        inventory.addItem(wood);
        assertTrue(inventory.getItem(wood.getitemId(), inventory.getMaterials()) != null);

        //check that treasure goes materials
        inventory.addItem(treasure);
        assertTrue(inventory.getItem(treasure.getitemId(), inventory.getMaterials()) != null);
        //check that Shield and armour are in consumable and defenseItems
        inventory.addItem(shield);
        assertTrue(inventory.getItem(shield.getitemId(), inventory.getDefenseItems()) != null);  
        assertTrue(inventory.getItem(shield.getitemId(), inventory.getConsumables()) != null); 
        inventory.addItem(armour);  
        assertTrue(inventory.getItem(armour.getitemId(), inventory.getDefenseItems()) != null);  
        assertTrue(inventory.getItem(armour.getitemId(), inventory.getConsumables()) != null);  
        //check bomb just goes to consumable
        inventory.addItem(bomb);
        assertTrue(inventory.getItem(bomb.getitemId(), inventory.getConsumables()) != null);
        //check that potion just goes to consumable
        inventory.addItem(hp);
        assertTrue(inventory.getItem(hp.getitemId(), inventory.getConsumables()) != null);
    }
    @Test
    public void testRemoveItem() {
        //test that when u remove the item it does from all lists needed
        Inventory inventory = new Inventory();
        Sword sword = new Sword(3);
        Key key = new Key(4);
        Wood wood = new Wood(5);
        Treasure treasure = new Treasure(6);
        Shield shield = new Shield(7);
        Bomb bomb = new Bomb(8);
        Armour armour = new Armour(9);
        HealthPotion hp = new HealthPotion(10);
        inventory.addItem(key);
        inventory.addItem(sword);
        inventory.addItem(wood);
        inventory.addItem(treasure);
        inventory.addItem(shield);
        inventory.addItem(bomb);
        inventory.addItem(armour);
        inventory.addItem(hp);

        //check key not materials and consumable
        inventory.removeItem(key);
        assertTrue(inventory.getItem(key.getitemId(), inventory.getMaterials()) == null);
        assertTrue(inventory.getItem(key.getitemId(), inventory.getConsumables()) == null);

        //check sword not consumable and swords
        inventory.removeItem(sword);
        assertTrue(inventory.getItem(sword.getitemId(), inventory.getSwords()) == null);
        assertTrue(inventory.getItem(sword.getitemId(), inventory.getConsumables()) == null);

        //check not wood goes to material
        inventory.removeItem(wood);
        assertTrue(inventory.getItem(wood.getitemId(), inventory.getMaterials()) == null);

        //check treasure not goes materials
        inventory.removeItem(treasure);
        assertTrue(inventory.getItem(treasure.getitemId(), inventory.getMaterials()) == null);
        //check that Shield and armour not in consumable and defenseItems
        inventory.removeItem(shield);
        assertTrue(inventory.getItem(shield.getitemId(), inventory.getDefenseItems()) == null);  
        assertTrue(inventory.getItem(shield.getitemId(), inventory.getConsumables()) == null); 
        inventory.removeItem(armour);  
        assertTrue(inventory.getItem(armour.getitemId(), inventory.getDefenseItems()) == null);  
        assertTrue(inventory.getItem(armour.getitemId(), inventory.getConsumables()) == null);  
        //check bomb not goes to consumable
        inventory.removeItem(bomb);
        assertTrue(inventory.getItem(bomb.getitemId(), inventory.getConsumables()) == null);
        //check that potion not goes to consumable
        inventory.removeItem(hp);
        assertTrue(inventory.getItem(hp.getitemId(), inventory.getConsumables()) == null);

    }
    @Test
    public void testuseItem() {
        //test using does stuff
    }
    @Test
    public void testRemoveDeadItems() {

    }
    @Test
    public void testAddRecipe() {

    }
    @Test
    public void testGetRecipe() {

    }
    @Test
    public void testGetBows() {
        Inventory inventory = new Inventory();
        Bow bow = new Bow(3);
        Key key = new Key(4);
        inventory.addItem(bow);
        inventory.addItem(key);
        assertEquals(inventory.getBows(), Arrays.asList(bow));
    }
    @Test
    public void testGetSwords() {
        Inventory inventory = new Inventory();
        Sword sword = new Sword(3);
        Key key = new Key(4);
        inventory.addItem(sword);
        inventory.addItem(key);
        assertEquals(inventory.getSwords(), Arrays.asList(sword));
    }
    @Test
    public void testGetDefenseItems() {
        Inventory inventory = new Inventory();
        Sword sword = new Sword(3);
        Key key = new Key(4);
        Armour armour = new Armour(5);
        Shield shield = new Shield(6);
        inventory.addItem(sword);
        inventory.addItem(key);
        inventory.addItem(armour);
        inventory.addItem(shield);
        //order doesnt matter however the order should be preserved
        assertEquals(inventory.getDefenseItems(), Arrays.asList(armour,shield));
    } 
}
