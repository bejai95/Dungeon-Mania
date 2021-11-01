package dungeonmania;

import dungeonmania.util.Direction;

import java.util.ArrayList;

import dungeonmania.util.Position;
import dungeonmania.exceptions.InvalidActionException;

public class Character extends Entity implements Battleable{
    Inventory inventory = new Inventory();
    double health;
    int damage;
    double baseDefense;
    int InvincibleDuration;
    int InvisibleDuration;
    double maxHealth;

    public Character(int id, String type, Position position) {
        super(id, type, position);
        this.health = 200;
        this.maxHealth = this.health;
        this.damage = 20;
        this.baseDefense = 0;
        this.InvincibleDuration = 0;
        this.InvisibleDuration = 0;
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
        //if value of defense has gone to high, set it to 1.
        if (defence > 1) {
            defence = 1;
        }
        return defence;
    }
    public double getDefenseMultiplier() {
        return 1 - getDefense();
    }
    public double getHealth() {
        return this.health;
    }
    public void setHealth(double newHealth) {
        this.health = newHealth;
    }
    public void use(Consumable consumable) throws InvalidActionException {
        consumable.consume(this);
    } 
    /**
     * Attempts to revive the player if has the one ring
     */
    public void revive() {
        for (Item item: this.inventory.getItems()) {
            if (item instanceof TheOneRing && this.getHealth() <= 0) {
                TheOneRing ring = (TheOneRing) item;
                ring.consume(this);
            }
        }
    }
    public boolean isInvisible () {
        if (this.InvisibleDuration > 0) {
            setInvisibleLength(getInvisibleLength() - 1);
            return true;
        }
        else {
            return false;
        }
    }
    public void setInvisibleLength(int length) {
        this.InvisibleDuration = length;
    }
    public boolean isInvincible () {
        if (this.InvincibleDuration > 0) {
            setInvincibleLength(getInvincibleLength() - 1);
            return true;
        }
        else {
            return false;
        }
    }
    public void setInvincibleLength (int length) {
        this.InvincibleDuration = length;
    }
    public int getInvincibleLength () {
        return this.InvincibleDuration;
    }
    public int getInvisibleLength () {
        return this.InvisibleDuration;
    }
    public double getMaxHealth() {
        return this.maxHealth;
    }
}