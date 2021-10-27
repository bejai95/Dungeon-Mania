package dungeonmania;

public abstract class WeaponItem extends Item {
    int damage;
    public WeaponItem(int uses, int itemId) {
        super(uses, itemId);
    }
    public int getDamage() {
        return this.damage;
    }
    public void setMultipler(int damage) {
        this.damage = damage;
    }
    
}
