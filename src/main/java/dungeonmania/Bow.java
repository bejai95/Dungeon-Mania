package dungeonmania;

public class Bow extends Item implements Consumable {
    int attackTimes;
    public Bow(int itemId) {
        super(3, itemId);
        this.attackTimes = 2;
    }
    public void consume() {
        
    }
    public int getAmountOfAttacks() {
        return this.attackTimes;
    }
    
}
