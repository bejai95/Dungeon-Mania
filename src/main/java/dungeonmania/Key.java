package dungeonmania;

public class Key extends Item implements Material, Consumable {
    private int keyNum;

    public Key(int itemId, int keyNum) {
        super(itemId);
        this.keyNum = keyNum;
        setUses(1);
    }


    public void consume() {
        if (this.getUses() != 0) {
            this.setUses(this.getUses() -1);
        }  
    }

    public int getKeyNum() {
        return keyNum;
    }
  
}