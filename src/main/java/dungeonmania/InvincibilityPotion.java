package dungeonmania;

public class InvincibilityPotion extends Item implements Consumable {
    public InvincibilityPotion(int itemId) {
        super(itemId, 1, "invincibility_potion");
    }
    public void consume(Character character) {
        if (this.getUses() != 0) {
            this.setUses(this.getUses() -1);
            character.setInvincibleLength(3);
        }
        
    }
    
}
