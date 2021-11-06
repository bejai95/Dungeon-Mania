package dungeonmania;

import dungeonmania.response.models.ItemResponse;

abstract public class Item {
    int uses;
    int itemId;
    public Item(int itemId, int uses) {
        this.itemId = itemId;
        this.uses = uses;
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
        return this.getClass().getSimpleName().toLowerCase();
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