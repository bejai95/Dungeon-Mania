package dungeonmania;

public class InvisibilityPotion extends Item implements Consumable {
    /**
     * 
     * @param itemId
     */
    public InvisibilityPotion(int itemId) {
        super(itemId, 1, "invisibility_potion");
    }
    /**
     * Set the invisibility duration of the character
     */
    public void consume(Character character) {
        if (this.getUses() != 0) {
            this.setUses(this.getUses() -1);
            character.setInvisibleLength(3);
        }       
    }
}
