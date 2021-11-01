package dungeonmania;

import dungeonmania.response.models.ItemResponse;

abstract public class Item {
    int uses;
    int itemId;
    //game checks ids are unique
    public Item(int itemId) {
        this.itemId = itemId;
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
    public String getType() {
        return this.getClass().getSimpleName().toLowerCase();
    }
    public ItemResponse makeItemReponse() {
        Integer newItem = (Integer) getitemId();
        ItemResponse newItemResponse = new ItemResponse(newItem.toString(), getType());
        return newItemResponse;
    }
}