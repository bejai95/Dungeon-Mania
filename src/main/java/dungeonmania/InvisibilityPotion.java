package dungeonmania;

public class InvisibilityPotion extends Item implements Consumable {
    public InvisibilityPotion(int itemId) {
        super(itemId);
        setUses(1);
    }
    public void consume(Character character) {
        if (this.getUses() != 0) {
            this.setUses(this.getUses() -1);
            character.setInvisibleLength(3);
        }       
    }
}
