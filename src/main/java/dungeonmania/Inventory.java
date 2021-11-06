package dungeonmania;

import java.util.ArrayList;
import java.util.List;

import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.response.models.ItemResponse;

import java.util.HashMap;
import java.lang.reflect.*;

public class Inventory {
    List<Item> items = new ArrayList<>();
    //takes in a string which is the name of the recipe and returns a list of all the materials required
    HashMap<String, List<String>> recipes = new HashMap<String, List<String>>(); 
    
    public Inventory() {
        List<String> bowMats = new ArrayList<>();
        bowMats.add(Wood.class.getSimpleName().toLowerCase());
        bowMats.add(Arrow.class.getSimpleName().toLowerCase());
        bowMats.add(Arrow.class.getSimpleName().toLowerCase());
        bowMats.add(Arrow.class.getSimpleName().toLowerCase());
        addRecipe(Bow.class.getSimpleName().toLowerCase(), bowMats);
        List<String> shieldMats1 = new ArrayList<>();
        shieldMats1.add(Wood.class.getSimpleName().toLowerCase());
        shieldMats1.add(Wood.class.getSimpleName().toLowerCase());
        shieldMats1.add(Treasure.class.getSimpleName().toLowerCase());
        addRecipe(Shield.class.getSimpleName().toLowerCase() + "1", shieldMats1);
        List<String> shieldMats2 = new ArrayList<>();
        shieldMats2.add(Wood.class.getSimpleName().toLowerCase());
        shieldMats2.add(Wood.class.getSimpleName().toLowerCase());
        shieldMats2.add(Key.class.getSimpleName().toLowerCase());
        addRecipe(Shield.class.getSimpleName().toLowerCase() + "2", shieldMats2);
    }
    /**
     * @invariant the item wanting to be craft is buidable
     * @param itemName
     * @description craft will be in charge of adding items to proper lists and taking away what is needed
     */
    public Item craft(String itemName, int itemId) throws ClassNotFoundException, NoSuchMethodException, InstantiationException,
          IllegalAccessError, IllegalAccessException, InvocationTargetException, InvalidActionException, IllegalArgumentException {
        //if it is not a Shield and is not equal 
        if (!(Shield.class.getSimpleName().toLowerCase().equals(itemName) || Bow.class.getSimpleName().toLowerCase().equals(itemName))) {
            throw new IllegalArgumentException("Cannot craft something that is not a bow or a shield");
        }
        //first finds the first recipe that can craft ur item
        List<String> recipe = this.getRecipesOfItem(itemName);
        //the recipe cant be crafted
        if (recipe == null) {
            throw new InvalidActionException("You don't have the resources to craft this item");
        }

        for (String recipeMaterial: recipe) {
            //now remove items that you are using
            List<Material> mats = this.getMaterials();
            for (Material mat: mats) {
                //if same class delete from items and break this loop
                Item item = (Item) mat;
                //we know all materials have to be items
                if (item.getClass().getSimpleName().toLowerCase().equals(recipeMaterial)) {
                    //remove the item and break
                    this.removeItem(item);
                    break;
                }
            }
        }
        if (itemName.equals("bow")) {
            itemName = Bow.class.getCanonicalName();
        }
        else if (itemName.equals("shield")) {
            itemName = Shield.class.getCanonicalName();
        }
        //create the new item
        Class classType = Class.forName(itemName);
        Constructor construct = classType.getConstructor(int.class);
        Item newItem = (Item)construct.newInstance(itemId);
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
        //now check 
        List<String> itemsBuildable = new ArrayList<>();
        for (String r: recipesBuildable) {
            //if recipe is a bow recipe and is not already in itemsBuildable
            if (r.contains(Bow.class.getSimpleName().toLowerCase()) && !(itemsBuildable.contains(Bow.class.getSimpleName().toLowerCase()))) {
                itemsBuildable.add(Bow.class.getSimpleName().toLowerCase());
            }
            //if recipe is a shield recipe and not in itemsBuildable
            else if (r.contains(Shield.class.getSimpleName().toLowerCase()) && !(itemsBuildable.contains(Shield.class.getSimpleName().toLowerCase()))) {
                itemsBuildable.add(Shield.class.getSimpleName().toLowerCase());
            }
        }
        return recipesBuildable;
    }
    public void addItemToInventory(Item item) {
        //adds an item and adds them to the nessecary lists
        //make sure that no duplicates inputted
        if (!(this.getItems().contains(item))) {
            this.getItems().add(item);
        }
    }
    public void removeItem(Item item) {
        //in charge of removing from items as well as the lists consumables
        //weapons defence and materials
        if (this.getItems().contains(item)) {
            this.getItems().remove(item);
        }
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
    /**
     * getRecipesOfItem will return the first recipe which you have materials for and is for that specific item
     * @param itemName
     * @return
     */
    public List<String> getRecipesOfItem(String itemName) {
        //for a given item name go through all recipes that contain itemName and check which one to return
        for (String recipeName: getRecipes().keySet()) {
            if (this.isRecipeBuildable(recipeName) && recipeName.contains(itemName)) {
                return getRecipes().get(recipeName);
            }
        }
        return null;
    }
    public Item getItem(int id) {
        for (Item i: this.getItems()) {
            if (i.getitemId() == id) {
                return i;
            }
        }
        return null;
    }
    public List<Weapon> getWeapons() {
        List<Weapon> weapons = new ArrayList<>();
        for (Item item: this.getItems()) {
            if (item instanceof Weapon) {
                weapons.add((Weapon) item);
            }
        }
        return weapons;
    }
    public List<DefenseItem> getDefenseItems() {
        List<DefenseItem> defense = new ArrayList<>();
        for (Item item: this.getItems()) {
            if (item instanceof DefenseItem) {
                defense.add((DefenseItem) item);
            }
        }
        return defense;
    }
    public List<Consumable> getConsumables() {
        List<Consumable> consumable = new ArrayList<>();
        for (Item item: this.getItems()) {
            if (item instanceof Consumable) {
                consumable.add((Consumable) item);
            }
        }
        return consumable;
    }
    public List<Material> getMaterials() {
        List<Material> mats = new ArrayList<>();
        for (Item item: this.getItems()) {
            if (item instanceof Material) {
                mats.add((Material) item);
            }
        }
        return mats;
    }
    public List<Item> getItems() {
        return this.items;
    }
    public HashMap<String, List<String>> getRecipes() {
        return this.recipes;
    }
    /**
     * @pre that recipeName is valid
     * @param recipeName
     * @return
     */
    public boolean isRecipeBuildable(String recipeName) {
        //stores the ids of all materials already counted for in inventory of materials
        List<Integer> materialsAlreadyUsed = new ArrayList<>();
        for (String material: recipes.get(recipeName)) {
            //check through the list of materials and see if item is in the recipe
            //if so add that specific item to list of materials already used
            int itemsUsedBeforeNewMaterial = materialsAlreadyUsed.size();
            for (Material mat: this.getMaterials()) {
                //if the item is of the right material and is not already used add it to used materials
                Item item = (Item) mat;
                //we know everything is of type of item
                //make sure that the item can be used since we can't use a dead item for crafting
                if (item.getClass().getSimpleName().toLowerCase().equals(material) && (item.canUse()) && (!(materialsAlreadyUsed.contains(item.getitemId())))) {
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
    public Item getItemFromType(String type) {
        for (Item item: this.getItems()) {
            if (item.getType().equals(type)) {
                return item;
            }
        }
        return null;
    }
    public List<ItemResponse> getItemsAsResponse() {
        List<ItemResponse> newItemResponses = new ArrayList<ItemResponse>();
        for (Item item: this.getItems()) {
            newItemResponses.add(item.makeItemReponse());
        }
        return newItemResponses;
    }
    public List<Sword> getSwords() {
        List<Sword> swords = new ArrayList<>();
        for (Item item: this.getItems()) {
            if (item instanceof Sword) {
                swords.add((Sword) item);
            }
        }
        return swords;
    }
}
