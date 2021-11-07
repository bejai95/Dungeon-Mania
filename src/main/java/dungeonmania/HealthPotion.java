package dungeonmania;

public class HealthPotion extends Item implements Consumable {
    public HealthPotion(int itemId) {
        super(itemId, 1);
    }
    public void consume(Character character) {
        if (this.getUses() != 0) {
            this.setUses(this.getUses() -1);
            character.setHealth(character.getMaxHealth());
        }  
    }
}
