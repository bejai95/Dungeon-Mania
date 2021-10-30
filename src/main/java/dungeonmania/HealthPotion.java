package dungeonmania;

public class HealthPotion extends Item implements Consumable {
    public HealthPotion(int itemId) {
        super(itemId);
        setUses(1);
    }
    public void consume() {
        if (this.getUses() != 0) {
            this.setUses(this.getUses() -1);
        }  
    }
}
