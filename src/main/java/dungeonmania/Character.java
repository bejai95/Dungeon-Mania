package dungeonmania;

import dungeonmania.util.Direction;
import dungeonmania.util.Position;
import dungeonmania.exceptions.InvalidActionException;

public class Character extends Entity {
    Inventory inventory = new Inventory();
    //List<Mercenary> allies = new ArrayList<>();
    double health;
    int damage;
    double baseDefense;

    public Character(int id, String type, Position position) {
        super(id, type, position);
    }
    public int baseDamage() {
        return this.damage;
    }
    public void move(Direction moveDirection) {
        this.getPosition().translateBy(moveDirection);
    }
    public int getDamage() {
        //gets damage of all things including inventory and use them
        int damage = this.baseDamage();
        for (Sword sword: inventory.getSwords()) {
            damage += sword.getDamage();
            use(sword);
        }
        //now we got total damage get the amount of bows and use them
        for (Bow bow: inventory.getBows()) {
            damage *= bow.getAmountOfAttacks();
            use(bow);
        }
        return damage;
        
    }
    public double getDefense() {
        double defence = this.baseDefense;
        for (DefenseItem d: inventory.getDefenseItems()) {
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