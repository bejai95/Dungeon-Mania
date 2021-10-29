package dungeonmania;

public class HealthPotion extends Item implements Consumable {
    public HealthPotion(int itemId) {
        super(itemId);
        setUses(1);
    }
    public void consume() {
        
    }
}
