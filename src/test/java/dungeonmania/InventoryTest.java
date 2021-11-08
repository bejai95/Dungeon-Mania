package dungeonmania;

import java.lang.reflect.Array;
import java.lang.reflect.Executable;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.util.Position;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotEquals;



public class InventoryTest {

    
    @Test
    public void testCrafting() {
        Inventory inventory = new Inventory();
        List<String> mats = new ArrayList<>();
        mats.add(Wood.class.getSimpleName().toLowerCase());
        mats.add(Treasure.class.getSimpleName().toLowerCase());
        inventory.addRecipe(Bow.class.getSimpleName().toLowerCase(), mats);
        //now add wood and treasure that is different id since it shouldnt matter to the inventory
        Wood woodI1 = new Wood(3);
        Treasure TreasureI1 = new Treasure(4);
        inventory.addItemToInventory(woodI1);
        inventory.addItemToInventory(TreasureI1);
        Item craftedBow = assertDoesNotThrow(() -> inventory.craft("bow",6));
        //now there should be nothing in items except the bow since materials used
        assertTrue(inventory.getItems().get(0).equals(craftedBow));
        assertTrue(inventory.getItems().get(0) instanceof Bow);
    }
    @Test
    public void testGenerateBuildables() {
        //check that if we have the materials in the inventory that recipe is crafted
        Inventory inventory = new Inventory();
        //now add wood and treasure that is different id since it shouldnt matter to the inventory
        Wood woodI1 = new Wood(3);
        Wood wood = new Wood(2);
        Arrow arrow = new Arrow(4);
        Arrow arrow2 = new Arrow(6);
        Arrow arrow3 = new Arrow(7);
        inventory.addItemToInventory(woodI1);
        inventory.addItemToInventory(wood);
        inventory.addItemToInventory(arrow);
        inventory.addItemToInventory(arrow2);
        inventory.addItemToInventory(arrow3);
        assertTrue(inventory.generateBuildables().get(0).equals(Bow.class.getSimpleName().toLowerCase()));
        //check that if you are an item short that it doesnt do anything
        inventory.removeItem(arrow);
        assertTrue(inventory.generateBuildables().equals(new ArrayList<String>()));

    }
    @Test
    public void testaddItemToInventory() {
        Inventory inventory = new Inventory();
        //check add item works
        Sword sword = new Sword(3);
        Key key = new Key(4,0);
        Wood wood = new Wood(5);
        Treasure treasure = new Treasure(6);
        Shield shield = new Shield(7);
        Bomb bomb = new Bomb(8);
        Armour armour = new Armour(9);
        HealthPotion hp = new HealthPotion(10);

        //check key goes to materials and consumable
        inventory.addItemToInventory(key);
        assertTrue(inventory.getItem(key.getitemId()) != null);

        //check sword goes to consumable and swords
        inventory.addItemToInventory(sword);
        assertTrue(inventory.getItem(sword.getitemId()) != null);

        //check that wood goes to material
        inventory.addItemToInventory(wood);
        assertTrue(inventory.getItem(wood.getitemId()) != null);

        //check that treasure goes materials
        inventory.addItemToInventory(treasure);
        assertTrue(inventory.getItem(treasure.getitemId()) != null);
    }
    @Test
    public void testRemoveItem() {
        //test that when u remove the item it does from all lists needed
        Inventory inventory = new Inventory();
        Sword sword = new Sword(3);
        Key key = new Key(4,1);
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
        assertTrue(inventory.getItem(key.getitemId()) == null);

        //check sword not consumable and swords
        inventory.removeItem(sword);
        assertTrue(inventory.getItem(sword.getitemId()) == null);

    }
    @Test
    public void testRemoveDeadItems() {
        Character character = new Character(3,Character.class.getSimpleName() , new Position(3, 4));
        Sword sword = new Sword(1);
        character.getInventory().addItemToInventory(sword);
        //now consume sword
        while (sword.getUses() > 0) {
            sword.getWeaponInfo();
        }
        character.getInventory().removeDeadItems();
        assertTrue(character.getInventory().getItem(sword.getitemId()) == null);
    }
    @Test
    public void testAddRecipeGetRecipe() {
        Inventory inventory = new Inventory();
        List<String> mats = new ArrayList<>();
        mats.add(Wood.class.getSimpleName().toLowerCase());
        mats.add(Treasure.class.getSimpleName().toLowerCase());
        inventory.addRecipe(Treasure.class.getSimpleName().toLowerCase(), mats);
        Wood wood1 = new Wood(2);
        Treasure t1 = new Treasure(5);
        inventory.addItemToInventory(wood1);
        inventory.addItemToInventory(t1);
        assertTrue(inventory.getRecipesOfItem(Treasure.class.getSimpleName().toLowerCase()).equals(Arrays.asList(Wood.class.getSimpleName().toLowerCase(), Treasure.class.getSimpleName().toLowerCase())));
    }
    @Test
    public void testGetBows() {
        Inventory inventory = new Inventory();
        Bow bow = new Bow(3);
        Key key = new Key(4,1);
        inventory.addItemToInventory(bow);
        inventory.addItemToInventory(key);
        assertEquals(inventory.getWeapons(), Arrays.asList(bow));
    }
    @Test
    public void testGetSwords() {
        Inventory inventory = new Inventory();
        Sword sword = new Sword(3);
        Key key = new Key(4,1);
        inventory.addItemToInventory(sword);
        inventory.addItemToInventory(key);
        assertEquals(inventory.getSwords(), Arrays.asList(sword));
    }
    @Test
    public void testGetDefenseItems() {
        Inventory inventory = new Inventory();
        Sword sword = new Sword(3);
        Key key = new Key(4,1);
        Armour armour = new Armour(5);
        Shield shield = new Shield(6);
        inventory.addItemToInventory(sword);
        inventory.addItemToInventory(key);
        inventory.addItemToInventory(armour);
        inventory.addItemToInventory(shield);
        //order doesnt matter however the order should be preserved
        assertEquals(inventory.getDefenseItems(), Arrays.asList(armour,shield));
    }
    @Test
    public void craftingShields() {
        //test if have material of one shield it works
        Inventory inventory = new Inventory();
        //make sure errors are thrown correctly
        assertThrows(IllegalArgumentException.class,() -> inventory.craft("gogogaga", 1));
        assertThrows(InvalidActionException.class, () -> inventory.craft("bow", 6));
        Wood wood = new Wood(1);
        Wood wood2 = new Wood(2);
        Wood wood4 = new Wood(7);
        Wood wood5 = new Wood(8);
        Treasure t1 = new Treasure(3);
        Treasure t2 = new Treasure(4);
        inventory.addItemToInventory(wood);
        inventory.addItemToInventory(wood2);
        inventory.addItemToInventory(wood4);
        inventory.addItemToInventory(wood5);
        inventory.addItemToInventory(t1);
        inventory.addItemToInventory(t2);
        //test if have material for the other shield recipe that works
        assertDoesNotThrow(() -> inventory.craft("shield", 21));
        assertDoesNotThrow(() -> inventory.craft("shield", 22));
        Wood wood6 = new Wood(10);
        Wood wood7 = new Wood(11);
        Key key1 = new Key(71, 0);
        inventory.addItemToInventory(wood6);
        inventory.addItemToInventory(wood7);
        inventory.addItemToInventory(key1);
        assertDoesNotThrow(() -> inventory.craft("shield", 23));
        //test that if u have material for both that it just crafts a shield
        //add arrows to try to confuse it
        Wood wood8 = new Wood(15);
        Wood wood0 = new Wood(16);
        Key key00 = new Key(72, 0);
        Treasure t3 = new Treasure(1510);
        Arrow a1 = new Arrow(100);
        Arrow a2 = new Arrow(102);
        Arrow a3 = new Arrow(101);
        inventory.addItemToInventory(key00);
        inventory.addItemToInventory(wood8);
        inventory.addItemToInventory(wood0);
        inventory.addItemToInventory(t3);
        inventory.addItemToInventory(a1);
        inventory.addItemToInventory(a2);
        inventory.addItemToInventory(a3);
        assertDoesNotThrow(() -> inventory.craft("shield", 230));
        assertTrue(inventory.getMaterials().contains(a1));
        assertTrue(!(inventory.getMaterials().contains(wood0)));
    }
    @Test
    public void testGetItemFromType() {
        Inventory inventory = new Inventory();
        int itemId = 3;
        HealthPotion hp = new HealthPotion(itemId);
        inventory.addItemToInventory(hp);
        assertTrue(inventory.getItemFromType("healthpotion") == hp);
        assertTrue(inventory.getItemFromType("cahskgah") == null);
    }
}
