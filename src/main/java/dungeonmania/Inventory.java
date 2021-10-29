package dungeonmania;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

import dungeonmania.exceptions.InvalidActionException;

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
    HashMap<String, List<String>> recipes = new HashMap<String, List<String>>(); 
    
    public Inventory() {

    }
    /**
     * @invariant the item wanting to be craft is buidable
     * @param itemName
     * @description craft will be in change of adding items to proper lists and taking away what is needed
     */
    public Item craft(String recipeName, int itemId) throws ClassNotFoundException, NoSuchMethodException, InstantiationException,
          IllegalAccessError, IllegalAccessException, InvocationTargetException, InvalidActionException {
        if (!(isRecipeBuildable(recipeName))) {
            throw new InvalidActionException("You don't have the resources to craft this item");
        }
        List<String> recipe = this.getRecipe(recipeName);
        List<Item> items = this.getItems();
        for (String recipeMaterial: recipe) {
            for (Item item: items) {
                //if same class delete from items and break this loop
                if (item.getClass().getCanonicalName().equals(recipeMaterial)) {
                    //remove the item and break
                    this.removeItem(item);
                    break;
                }
            }
        }
        Class classType = Class.forName(recipeName);
        Constructor construct = classType.getConstructor(int.class);
        Item newItem = (Item)construct.newInstance(2);
        //add the new item to inventory
        this.addItemToInventory(newItem);
        //craft returns int of the item crafted id
        //invariant, assume the item can be crafted
        return newItem;
    }
    public List<String> generateBuildables() {
        List<String> recipesBuildable = new ArrayList<>();
        HashMap<String, List<String>> recipes = getRecipes();
        //for each recipe
        for (String recipeName: recipes.keySet()) {
            if (this.isRecipeBuildable(recipeName)) {
                //if can build
                recipesBuildable.add(recipeName);
            }
        }
        //go through each recipe
        //then count the amount of 
        return recipesBuildable;
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
        consumable.consume();
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
    /**
     * 
     * @param name should be the canonical name of an item
     * @param materials
     */
    public void addRecipe(String name, List<String> materials) {
        recipes.put(name, materials);
    }
    public List<String> getRecipe(String name) {
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
    public HashMap<String, List<String>> getRecipes() {
        return this.recipes;
    }
    public boolean isRecipeBuildable(String recipeName) {
        //stores the ids of all materials already counted for in inventory of materials
        List<Integer> materialsAlreadyUsed = new ArrayList<>();
        for (String material: recipes.get(recipeName)) {
            //check through the list of materials and see if item is in the recipe
            //if so add that specific item to list of materials already used
            int itemsUsedBeforeNewMaterial = materialsAlreadyUsed.size();
            for (Item item: this.getMaterials()) {
                //if the item is of the right material and is not already used add it to used materials
                if (item.getClass().getCanonicalName().equals(material) && (!(materialsAlreadyUsed.contains(item.getitemId())))) {
                    materialsAlreadyUsed.add(item.getitemId());
                    break;
                }
            }
            //if no items are already being used for this material then material was not found
            //hence recipe cant be done
            if (itemsUsedBeforeNewMaterial == materialsAlreadyUsed.size()) {
                return false;
            }
        }
        return true;
    }
}
