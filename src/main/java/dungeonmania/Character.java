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

    public Character(int id, String type, Position position) {
        super(id, type, position);
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
        if (consumable instanceof InvincibilityPotion) {
            //set it to value that would make snese
            this.setInvincibleLength(3);
        }
        else if (consumable instanceof InvisibilityPotion) {
            //set it to value that would make sense
            this.setInvisibleLength(3);
        }
        consumable.consume();
    } 
    /**
     * Attempts to revive the player if has the one ring
     */
    public void revive() {
        for (Item item: this.inventory.getItems()) {
            if (item instanceof TheOneRing && this.getHealth() <= 0) {
                TheOneRing ring = (TheOneRing) item;
                ring.consume();
                //revive to full health
                this.setHealth(100);
            }
        }
    }
    public boolean isInvisible () {
        if (this.InvisibleDuration > 0) {
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
            return true;
        }
        else {
            return false;
        }
    }
    public void setInvincibleLength (int length) {
        this.InvincibleDuration = length;
    }


}