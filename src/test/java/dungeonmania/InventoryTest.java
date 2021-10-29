package dungeonmania;

import java.lang.reflect.Array;
import java.lang.reflect.Executable;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

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
        List<Item> mats = new ArrayList<>();
        Treasure treasure = new Treasure(1);
        Wood wood = new Wood(2);
        mats.add(wood);
        mats.add(treasure);
        inventory.addRecipe("Bow", mats);
        //now add wood and treasure that is different id since it shouldnt matter to the inventory
        Wood woodI1 = new Wood(3);
        Treasure TreasureI1 = new Treasure(4);
        inventory.addItemToInventory(woodI1);
        inventory.addItemToInventory(TreasureI1);
        assertDoesNotThrow(() -> {Item craftedBow = inventory.craft("Bow",6);});
        //now there should be nothing in items except the bow since materials used
        assertTrue(inventory.getItems() == Arrays.asList(craftedBow));

    }
    @Test
    public void testGenerateBuildables() {
        //check that if we have the materials in the inventory that recipe is crafted
        Inventory inventory = new Inventory();
        List<Item> mats = new ArrayList<>();
        Treasure treasure = new Treasure(1);
        Wood wood = new Wood(2);
        mats.add(wood);
        mats.add(treasure);
        inventory.addRecipe("Bow", mats);
        //now add wood and treasure that is different id since it shouldnt matter to the inventory
        Wood woodI1 = new Wood(3);
        Treasure TreasureI1 = new Treasure(4);
        inventory.addItemToInventory(woodI1);
        inventory.addItemToInventory(TreasureI1);
        assertTrue(inventory.generateBuildables() == Arrays.asList("Bow"));
    }
    @Test
    public void testaddItemToInventory() {
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
        inventory.addItemToInventory(key);
        assertTrue(inventory.getItem(key.getitemId(), inventory.getMaterials()) != null);
        assertTrue(inventory.getItem(key.getitemId(), inventory.getConsumables()) != null);

        //check sword goes to consumable and swords
        inventory.addItemToInventory(sword);
        assertTrue(inventory.getItem(sword.getitemId(), inventory.getSwords()) != null);
        assertTrue(inventory.getItem(sword.getitemId(), inventory.getConsumables()) != null);

        //check that wood goes to material
        inventory.addItemToInventory(wood);
        assertTrue(inventory.getItem(wood.getitemId(), inventory.getMaterials()) != null);

        //check that treasure goes materials
        inventory.addItemToInventory(treasure);
        assertTrue(inventory.getItem(treasure.getitemId(), inventory.getMaterials()) != null);
        //check that Shield and armour are in consumable and defenseItems
        inventory.addItemToInventory(shield);
        assertTrue(inventory.getItem(shield.getitemId(), inventory.getDefenseItems()) != null);  
        assertTrue(inventory.getItem(shield.getitemId(), inventory.getConsumables()) != null); 
        inventory.addItemToInventory(armour);  
        assertTrue(inventory.getItem(armour.getitemId(), inventory.getDefenseItems()) != null);  
        assertTrue(inventory.getItem(armour.getitemId(), inventory.getConsumables()) != null);  
        //check bomb just goes to consumable
        inventory.addItemToInventory(bomb);
        assertTrue(inventory.getItem(bomb.getitemId(), inventory.getConsumables()) != null);
        //check that potion just goes to consumable
        inventory.addItemToInventory(hp);
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
        inventory.addItemToInventory(key);
        inventory.addItemToInventory(sword);
        inventory.addItemToInventory(wood);
        inventory.addItemToInventory(treasure);
        inventory.addItemToInventory(shield);
        inventory.addItemToInventory(bomb);
        inventory.addItemToInventory(armour);
        inventory.addItemToInventory(hp);

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
    public void testRemoveDeadItems() {
        Inventory inventory = new Inventory();
        Sword sword = new Sword(1);
        inventory.addItemToInventory(sword);
        //now consume sword
        while (sword.getUses() > 0) {
            sword.consume();
        }
        inventory.removeDeadItems();
        assertTrue(inventory.getItem(sword.getitemId(), inventory.getItems()) == null);
    }
    @Test
    public void testAddRecipeGetRecipe() {
        Inventory inventory = new Inventory();
        List<Item> mats = new ArrayList<>();
        Treasure treasure = new Treasure(1);
        Wood wood = new Wood(2);
        mats.add(wood);
        mats.add(treasure);
        inventory.addRecipe("Bow", mats);
        assertTrue(inventory.getRecipe("Bow").equals(Arrays.asList(treasure, wood)));
    }
    @Test
    public void testGetBows() {
        Inventory inventory = new Inventory();
        Bow bow = new Bow(3);
        Key key = new Key(4);
        inventory.addItemToInventory(bow);
        inventory.addItemToInventory(key);
        assertEquals(inventory.getBows(), Arrays.asList(bow));
    }
    @Test
    public void testGetSwords() {
        Inventory inventory = new Inventory();
        Sword sword = new Sword(3);
        Key key = new Key(4);
        inventory.addItemToInventory(sword);
        inventory.addItemToInventory(key);
        assertEquals(inventory.getSwords(), Arrays.asList(sword));
    }
    @Test
    public void testGetDefenseItems() {
        Inventory inventory = new Inventory();
        Sword sword = new Sword(3);
        Key key = new Key(4);
        Armour armour = new Armour(5);
        Shield shield = new Shield(6);
        inventory.addItemToInventory(sword);
        inventory.addItemToInventory(key);
        inventory.addItemToInventory(armour);
        inventory.addItemToInventory(shield);
        //order doesnt matter however the order should be preserved
        assertEquals(inventory.getDefenseItems(), Arrays.asList(armour,shield));
    } 
}
