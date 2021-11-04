package dungeonmania;

public class Wood extends Item implements Material {
    /**
     * 
     * @param itemId
     */
    public Wood(int itemId) {
        super(itemId);
        setUses(1);
    }
    
}
