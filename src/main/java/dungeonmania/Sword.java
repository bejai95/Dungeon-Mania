package dungeonmania;

public class Sword extends Item implements Consumable {
    int damage;
    public Sword(int itemId) {
        super(itemId);
        setDamage(3);
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
