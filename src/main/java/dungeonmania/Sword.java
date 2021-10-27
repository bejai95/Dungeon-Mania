package dungeonmania;

public class Sword extends Item implements Consumable {
    int damage;
    public Sword(int itemId) {
        super(3, itemId);
        this.damage = 25;
    }
    public void consume() {

    }
    public int getDamage() {
        return this.damage;
    }
    public void setDamage(int damage) {
        this.damage = damage;
    }
}
