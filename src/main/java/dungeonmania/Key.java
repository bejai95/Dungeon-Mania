package dungeonmania;

public class Key extends Item implements Material, Consumable {
    public Key(int itemId) {
        super(itemId);
        setUses(1);
    }
    public void consume() {
        
    }
}
