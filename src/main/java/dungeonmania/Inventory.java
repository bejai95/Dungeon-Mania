package dungeonmania;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

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
    HashMap<String, List<Material>> recipes = new HashMap<String, List<Material>>(); 
    
    public Inventory() {

    }
    /**
     * @invariant the item wanting to be craft is buidable
     * @param itemName
     * @description craft will be in change of adding items to proper lists and taking away what is needed
     */
    public void craft(String itemName) {

    /**
     * Buildable checks all recipes and lists all the ones that are
     */
    }
    public List<String> buildable() {
        return new ArrayList<>();
    }
    public void addItem(Item item) {
        //adds an item and adds them to the nessecary lists
        //make sure that no duplicates inputted

    }
    public void removeItem(Item item) {
        //in change of removing from items as well as the lists consumables
        //weapons defence and materials
    }
    public void useItem(Consumable consumable) {

    }
    //remove all items that have zero uses left, call at end of each turn
    public void removeDeadItems() {

    }
    public void addRecipe(String name, List<Material> materials) {
        recipes.put(name, materials);
    }
    public List<Material> getRecipe(String name) {
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
}
