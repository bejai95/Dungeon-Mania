package dungeonmania;

public class Shield extends Item implements DefenseItem {
    /**
     * 
     * @param itemId
     */
    public Shield(int itemId) {
        super(itemId, 3);
    }
    public double getMultipler() {
        use();
        return 0.25;
    }
}
