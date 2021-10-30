package dungeonmania;

import dungeonmania.util.Position;

import java.lang.reflect.*;

public class UnpickedUpItem extends StaticEntity {
    //-----Data-----
    //This contains the class string of the unpicked up item E.g. "HealthPotion"
    private String itemClass;
    //This contains the id string of the item E.g. "health_potion"
    private String itemid;

    //-----Constructors-----
    /*Be careful not to get inputs mixed up:
    id = "unpickedup_item"
    itemid = "health_potion"
    itemClass = "HealthPotion"
    */
    public UnpickedUpItem(int id, String type, Position position, String itemClass, String itemid) {
        super(id, "unpickedup_item", position);
        this.itemClass = itemClass;
        this.itemid = itemid;
    }

    //-----Methods-----
    //Method for picking up an item
    //Note: I am temporarily Using 0 for ID until ID system implemented
    public Item pickupItem () throws ClassNotFoundException, NoSuchMethodException, InstantiationException,
    IllegalAccessError, IllegalAccessException, InvocationTargetException {
        //Create the new item
        Class classType = Class.forName(itemClass);
        Constructor construct = classType.getConstructor(int.class);
        Item newItem = (Item)construct.newInstance(itemid);

        //Remove this object from the static entities list
        super.removeStaticEntity();

        //return item
        return newItem;
    }

    //-----Getters and Setters-----
}