package dungeonmania;

public class Bow extends Item implements Consumable {
    int attackTimes;
    /**
     * Constructor sets uses and the amount of times the bow will attack/turn
     * @param itemId
     */
    public Bow(int itemId) {
        super(itemId);
        this.attackTimes = 2;
        setUses(3);
    }
    /**
     * reduces uses of the bow
     */
    public void consume(Character character) {
        if (this.getUses() != 0) {
            setUses(this.getUses() - 1);
        }
    }
    /**
     * 
     * @return amount of attacks/turn
     */
    public int getAmountOfAttacks() {
        return this.attackTimes;
    }
    
}
