package dungeonmania;

public class Bomb extends Item implements Consumable {
    public Bomb(int itemId) {
        super(itemId);
        setUses(1);
    }
    public void consume(Character character) {
        if (this.getUses() != 0) {
            this.setUses(this.getUses() -1);
        }
    }
    
}
