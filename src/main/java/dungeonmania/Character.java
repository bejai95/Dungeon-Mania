package dungeonmania;

import dungeonmania.util.Direction;
import dungeonmania.exceptions.InvalidActionException;

public class Character extends Entity {
    Inventory inventory = new Inventory();
    //List<Mercenary> allies = new ArrayList<>();
    double health;
    int damage;
    double baseDefense;

    public Character(int id, String type, int x, int y,int layer) {
        super(id, type, x, y, layer);
    }
    public int baseDamage() {
        return this.damage;
    }
    public void move(Direction moveDirection) {
        this.setPosition(this.getPosition().translateBy(moveDirection));
    }
    public int getDamage() {
        //gets damage of all things including inventory and use them
        int damage = this.baseDamage();
        for (Sword sword: this.inventory.getSwords()) {
            damage += sword.getDamage();
            use(sword);
        }
        //now we got total damage get the amount of bows and use them
        for (Bow bow: this.inventory.getBows()) {
            damage *= bow.getAmountOfAttacks();
            use(bow);
        }
        return damage;
        
    }
    public double getDefense() {
        double defence = this.baseDefense;
        for (DefenseItem d: this.inventory.getDefenseItems()) {
            defence += d.getMultipler();
            use(d);
        }
        return defence;
    }
    public double getHealth() {
        return this.health;
    }
    public void setHealth(double newHealth) {
        this.health = newHealth;
    }
    public void use(Consumable consumable) throws InvalidActionException {
        consumable.consume();
    }
}