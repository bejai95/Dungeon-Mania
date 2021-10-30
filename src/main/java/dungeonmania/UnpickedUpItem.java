package dungeonmania;

import dungeonmania.util.Position;

import java.lang.reflect.*;

public class UnpickedUpItem extends StaticEntity {
    //-----Data-----
    private String itemClass;
    private int itemid;

    //-----Constructors-----
    /*Be careful not to get inputs mixed up:
    id = The id of the unpickedup Static Entity (E.g. 2)
    itemid = The id of the item (E.g. 5)
    itemClass = "HealthPotion"
    */
    public UnpickedUpItem(int id, String type, Position position, String itemClass, int itemid) {
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