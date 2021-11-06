package dungeonmania;

public class TheOneRing extends Item implements Consumable {
    /**
     * 
     * @param itemId
     */
    public TheOneRing(int itemId) {
        super(itemId, 1);
    }
    /**
     * Consumes the one ring and restores player to full health
     */
    public void consume(Character character) {
        if (this.getUses() != 0) {
            this.setUses(this.getUses() -1);
            character.setHealth(character.getMaxHealth());
        }
    }  
}
