package dungeonmania;

import dungeonmania.util.Position;

import java.lang.reflect.*;

public class UnpickedUpItem extends StaticEntity {
    //-----Data-----
    private String itemClass;
    private String itemid;

    //-----Constructors-----
    public UnpickedUpItem(int id, String type, Position position, String itemClass, String itemid) {
        super(id, "unpickedup_item", position);
        this.itemClass = itemClass;
        this.itemid = itemid;
    }

    //-----Methods-----
    //Using 0 for ID until ID system implemented
    public Item pickupItem () throws ClassNotFoundException, NoSuchMethodException, InstantiationException,
    IllegalAccessError, IllegalAccessException, InvocationTargetException {
        Class classType = Class.forName(itemClass);
        Constructor construct = classType.getConstructor(int.class);
        Item newItem = (Item)construct.newInstance(itemid);
        return newItem;
    }

    //-----Getters and Setters-----
}