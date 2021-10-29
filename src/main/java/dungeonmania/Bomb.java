package dungeonmania;

public class Bomb extends Item implements Consumable {
    public Bomb(int itemId) {
        super(itemId);
        setUses(1);
    }
    public void consume() {

    }
    
}
