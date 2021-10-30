package dungeonmania;

public class Treasure extends Item implements Material {
    public Treasure(int itemId) {
        super(itemId);
        setUses(1);
    }
}
