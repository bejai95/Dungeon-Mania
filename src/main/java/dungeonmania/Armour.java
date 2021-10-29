package dungeonmania;

public class Armour extends DefenseItem {
    public Armour(int itemId) {
        super(itemId);
        setUses(3);
        super.setMultipler(0.5);
    }
}
