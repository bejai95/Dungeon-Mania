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
        //has uses
        if (canUse()) {
            use();
            return 0.25;
        }
        //act as if item does not exist
        else {
            return 0;
        }
    }
}
