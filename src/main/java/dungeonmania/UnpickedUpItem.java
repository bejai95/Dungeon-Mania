package dungeonmania;

import dungeonmania.util.Position;

import java.lang.reflect.*;

public class UnpickedUpItem extends StaticEntity {
    //-----Data-----
    private String itemClass;
    private int keyNum;

    //-----Constructors-----
    /*
    id = The id of the unpickedup Static Entity (E.g. 2)
    itemClass = "HealthPotion"
    Note the ID gets transferred to the item on pickup
    */

    //Constructor for a key
    public UnpickedUpItem(int id, String type, Position position, String itemClass, int keyNum) {
        super(id, type, position);
        this.itemClass = itemClass;
        this.keyNum = keyNum;
        this.setIsInteractable(true);
    }
 
    //Constructor for any item that's not a key
    public UnpickedUpItem(int id, String type, Position position, String itemClass) {
        super(id, type, position);
        this.itemClass = itemClass;
        this.keyNum = -1;
        this.setIsInteractable(true);
    }

    //-----Methods-----
    //Method for picking up an item
    //Note: I am temporarily Using 0 for ID until ID system implemented
    //Note the ID gets transferred to the item on pickup
    public Item pickupItem () throws ClassNotFoundException, IllegalAccessError, IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchMethodException {
        if (itemClass.equals("Key")) {
            Key newKey = new Key(getId(),keyNum);
            return (Item)newKey;
        }

        //Create the new item if the item isn't a key
        Class classType = Class.forName("dungeonmania." + itemClass);
        Constructor construct = classType.getConstructor(int.class);
        Item newItem = (Item)construct.newInstance(getId());


        //return item
        return newItem;
    }

    //-----Getters and Setters-----
    public String getItemClass() {
        return itemClass;
    }
    
}