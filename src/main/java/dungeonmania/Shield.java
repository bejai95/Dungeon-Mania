package dungeonmania;

public class Shield extends DefenseItem {
    /**
     * 
     * @param itemId
     */
    public Shield(int itemId) {
        super(itemId);
        this.setUses(3);
        super.setMultipler(0.25);
    }
}
