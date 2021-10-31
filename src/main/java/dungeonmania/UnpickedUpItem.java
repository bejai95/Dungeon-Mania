package dungeonmania;

import dungeonmania.util.Position;

import java.lang.reflect.*;

public class UnpickedUpItem extends StaticEntity {
    //-----Data-----
    private String itemClass;

    //-----Constructors-----
    /*
    id = The id of the unpickedup Static Entity (E.g. 2)
    itemClass = "HealthPotion"
    Note the ID gets transferred to the item on pickup
    */
    public UnpickedUpItem(int id, String type, Position position, String itemClass) {
        super(id, type, position);
        this.itemClass = itemClass;
    }

    //-----Methods-----
    //Method for picking up an item
    //Note: I am temporarily Using 0 for ID until ID system implemented
    //Note the ID gets transferred to the item on pickup
    public Item pickupItem () throws ClassNotFoundException, NoSuchMethodException, InstantiationException,
    IllegalAccessError, IllegalAccessException, InvocationTargetException {
        //Create the new item
        Class classType = Class.forName(itemClass);
        Constructor construct = classType.getConstructor(int.class);
        Item newItem = (Item)construct.newInstance(getId());

        //Remove this object from the static entities list
        super.removeStaticEntity();

        //return item
        return newItem;
    }

    //-----Getters and Setters-----
}