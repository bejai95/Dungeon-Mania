package dungeonmania;


abstract public class Item {
    int uses;
    int itemId;
    //game checks ids are unique
    public Item(int uses, int itemId) {
        this.uses = uses;
        this.itemId = itemId;
    }
    public int getUses() {
        return this.uses;
    }
    public int itemId() {
        return this.itemId;
    }
}