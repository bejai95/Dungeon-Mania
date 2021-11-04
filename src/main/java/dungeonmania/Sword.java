package dungeonmania;

public class Sword extends Item implements Consumable {
    int damage;
    /**
     * Sword constructor sets the amount of damage the sword should do
     * @param itemId
     */
    public Sword(int itemId) {
        super(itemId);
        setDamage(3);
        this.damage = 25;
    }
    /**
     * reduces durability of sword
     */
    public void consume(Character character) {
        if (this.getUses() != 0) {
            this.setUses(this.getUses() -1);
        }
    }
    public int getDamage() {
        return this.damage;
    }
    public void setDamage(int damage) {
        this.damage = damage;
    }
}
