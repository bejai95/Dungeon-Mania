package dungeonmania;

import dungeonmania.response.models.ItemResponse;

abstract public class Item {
    private int uses;
    private int itemId;
    private String type;
    public Item(int itemId, int uses, String type) {
        this.itemId = itemId;
        this.uses = uses;
        this.type = type;
    }
    public int getUses() {
        return this.uses;
    }
    public int getitemId() {
        return this.itemId;
    }
    public void setUses(int uses) {
        this.uses = uses;
    }
    /**
     * 
     * @return the class' simple name in all lowercase
     */
    public String getType() {
        return this.type;
    }
    /**
     * 
     * @return ItemResponse of the item
     */
    public ItemResponse makeItemReponse() {
        Integer newItem = (Integer) getitemId();
        ItemResponse newItemResponse = new ItemResponse(newItem.toString(), getType());
        return newItemResponse;
    }
    /**
     * 
     * @return use if still durable
     */
    public boolean canUse() {
        if (this.getUses() > 0) {
            return true;
        }
        else {
            return false;
        }
    }
    public void use() {
        this.setUses(this.getUses() - 1);
    }
}