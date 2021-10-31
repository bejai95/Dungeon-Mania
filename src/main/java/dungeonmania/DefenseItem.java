package dungeonmania;

public abstract class DefenseItem extends Item implements Consumable {
    double multipler;
    public DefenseItem(int itemId) {
        super(itemId);
    }
    public double getMultipler() {
        return this.multipler;
    }
    public void setMultipler(double multipler) {
        this.multipler = multipler;
    }
    public void consume(Character character) {
        if (this.getUses() != 0) {
            this.setUses(this.getUses() -1);   
        }
    }
}
