package dungeonmania;

public class InvincibilityPotion extends Item implements Consumable {
    public InvincibilityPotion(int itemId) {
        super(itemId);
        setUses(1);
    }
    public void consume() {
        if (this.getUses() != 0) {
            this.setUses(this.getUses() -1);
        }
        
    }
    
}
