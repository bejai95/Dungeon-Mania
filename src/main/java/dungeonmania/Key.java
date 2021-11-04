package dungeonmania;

public class Key extends Item implements Material, Consumable {
    private int keyNum;
    /**
     * Key num is the number which is given to a corresponding door to link them
     * @param itemId
     * @param keyNum
     */
    public Key(int itemId, int keyNum) {
        super(itemId);
        this.keyNum = keyNum;
        setUses(1);
    }
    /**
     * reduces the amount of uses of the key
     */
    public void consume(Character character) {
        if (this.getUses() != 0) {
            this.setUses(this.getUses() -1);
        }  
    }

    public int getKeyNum() {
        return keyNum;
    }
  
}