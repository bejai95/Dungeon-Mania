package dungeonmania;

public class Key extends Item implements Material {
    private int keyNum;
    /**
     * Key num is the number which is given to a corresponding door to link them
     * @param itemId
     * @param keyNum
     */
    public Key(int itemId, int keyNum) {
        super(itemId, 1, "key");
        this.keyNum = keyNum;
    }

    public int getKeyNum() {
        return keyNum;
    }
  
}