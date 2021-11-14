package dungeonmania;

public class Armour extends Item implements DefenseItem, Material {
    public Armour(int itemId) {
        super(itemId, 3, "armour");
    }
    /**
     * @return the multipler, 0.5 if can be used. 0 if can't
     */
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
