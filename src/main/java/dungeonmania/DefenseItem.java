package dungeonmania;

public abstract class DefenseItem extends Item {
    double multipler;
    public DefenseItem(int uses, int itemId) {
        super(uses, itemId);
    }
    public double getMultipler() {
        return this.multipler;
    }
    public void setMultipler(double multipler) {
        this.multipler = multipler;
    }
}
