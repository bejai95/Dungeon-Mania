package dungeonmania;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.lang.reflect.*;

public class Inventory {
    List<Item> items = new ArrayList<>();
    //invariant only things implementing consumables are added to consumables list
    List<Item> consumables = new ArrayList<>();
    //invariant only things implementing materials are added to consumables list.
    List<Item> materials = new ArrayList<>();
    List<Bow> bows = new ArrayList<>();
    List<Sword> swords = new ArrayList<>();
    List<DefenseItem> defenceItems = new ArrayList<>();
    //takes in a string which is the name of the recipe and returns a list of all the materials required
    HashMap<String, List<Item>> recipes = new HashMap<String, List<Item>>(); 
    
    public Inventory() {

    }
    /**
     * @invariant the item wanting to be craft is buidable
     * @param itemName
     * @description craft will be in change of adding items to proper lists and taking away what is needed
     */
    public Item craft(String itemName) throws ClassNotFoundException {
        List<Item> recipe = this.getRecipe(itemName);
        List<Item> items = this.getItems();
        for (Item recipeMaterial: recipe) {
            for (Item item: items) {
                String recipeMaterialClass = recipeMaterial.getClass().getCanonicalName();
                //if same class delete from items and break this loop
                if (item.getClass().getCanonicalName().equals(recipeMaterialClass)) {
                    //remove the item and break
                    this.removeItem(item);
                    break;
                }
            }
        }
        Class newItem = Class.forName(itemName);
        //craft returns int of the item crafted id
        //invariant, assume the item can be crafted
        
        return new Wood(-1);
    }
    public List<String> generateBuildables() {
        //go through each recipe
        //then count the amount of 
        return new ArrayList<>();
    }
    public void addItemToInventory(Item item) {
        //adds an item and adds them to the nessecary lists
        //make sure that no duplicates inputted
        if (!(this.getItems().contains(item))) {
            this.getItems().add(item);
            if (item instanceof Material) {
                this.getMaterials().add(item);
            }
            if (item instanceof Consumable) {
                this.getConsumables().add(item);
            }
            if (item instanceof Bow) {
                Bow bow = (Bow) item;
                this.getBows().add(bow);
            }
            if (item instanceof Sword) {
                Sword sword = (Sword) item;
                this.getSwords().add(sword);
            }
            if (item instanceof DefenseItem) {
                DefenseItem d = (DefenseItem) item;
                this.getDefenseItems().add(d);
            }
        }
    }
    public void removeItem(Item item) {
        //in change of removing from items as well as the lists consumables
        //weapons defence and materials
        if (this.getItems().contains(item)) {
            this.getItems().remove(item);
            if (item instanceof Material) {
                this.getMaterials().remove(item);
            }
            if (item instanceof Consumable) {
                this.getConsumables().remove(item);
            }
            if (item instanceof Bow) {
                Bow bow = (Bow) item;
                this.getBows().remove(bow);
            }
            if (item instanceof Sword) {
                Sword sword = (Sword) item;
                this.getSwords().remove(sword);
            }
            if (item instanceof DefenseItem) {
                DefenseItem d = (DefenseItem) item;
                this.getDefenseItems().remove(d);
            }
        }
    }
    public void useItem(Consumable consumable) {

    }
    //remove all items that have zero uses left, call at end of each turn
    public void removeDeadItems() {
        List<Item> itemsToBeRemoved = new ArrayList<>();
        for (Item item: this.getItems()) {
            if (item.getUses() == 0) {
                itemsToBeRemoved.add(item);
            }
        }
        for (Item item: itemsToBeRemoved) {
            removeItem(item);
        }
    }
    public void addRecipe(String name, List<Item> materials) {
        recipes.put(name, materials);
    }
    public List<Item> getRecipe(String name) {
        return recipes.get(name);
    }
    public Item getItem(int id, List<? extends Item> list) {
        for (Item i: list) {
            if (i.getitemId() == id) {
                return i;
            }
        }
        return null;
    }
    public List<Sword> getSwords() {
        return this.swords;
    }
    public List<Bow> getBows() {
        return this.bows;
    }
    public List<DefenseItem> getDefenseItems() {
        return this.defenceItems;
    }
    public List<Item> getConsumables() {
        return this.consumables;
    }
    public List<Item> getMaterials() {
        return this.materials;
    }
    public List<Item> getItems() {
        return this.items;
    }
}
