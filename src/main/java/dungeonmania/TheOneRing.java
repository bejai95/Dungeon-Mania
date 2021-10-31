package dungeonmania;

public class TheOneRing extends Item implements Consumable {
    public TheOneRing(int itemId) {
        super(itemId);
        setUses(1);
    }
    public void consume(Character character) {
        if (this.getUses() != 0) {
            this.setUses(this.getUses() -1);
            character.setHealth(character.getMaxHealth());
        }
    }  
}
