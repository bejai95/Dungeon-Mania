package dungeonmania;

public class Armour extends Item implements DefenseItem {
    public Armour(int itemId) {
        super(itemId, 3, "armour");
    }
    public double getMultipler() {
        //has uses
        if (canUse()) {
            use();
            return 0.5;
        }
        //act as if item does not exist
        else {
            return 0;
        }
    }
}
