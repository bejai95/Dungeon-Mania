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
    public void pickupItem (Inventory inventory) {
        Class classType = Class.forName(itemClass);
        Constructor construct = classType.getConstructor(int.class);
        Item newItem = (Item)construct.newInstance(itemid);
        //add the new item to inventory
        inventory.addItemToInventory(newItem);
    }

    //-----Getters and Setters-----
}