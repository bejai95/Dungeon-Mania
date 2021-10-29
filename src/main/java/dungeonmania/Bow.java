package dungeonmania;

public class Bow extends Item implements Consumable {
    int attackTimes;
    public Bow(int itemId) {
        super(itemId);
        this.attackTimes = 2;
        setUses(3);
    }
    public void consume() {
        
    }
    public int getAmountOfAttacks() {
        return this.attackTimes;
    }
    
}
