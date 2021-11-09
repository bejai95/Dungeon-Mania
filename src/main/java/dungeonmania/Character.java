package dungeonmania;

import dungeonmania.util.Direction;

import java.util.ArrayList;
import java.util.List;

import dungeonmania.util.Position;
import dungeonmania.exceptions.InvalidActionException;

public class Character extends Entity implements Battleable{
    private Inventory inventory = new Inventory();
    private double health;
    private int baseDamage;
    private double baseDefense;
    private int InvincibleDuration;
    private int InvisibleDuration;
    private double maxHealth;

    public Character(int id, Position position) {
        super(id, "player", position);
        this.health = 200;
        this.maxHealth = this.health;
        this.baseDamage = 3;
        this.baseDefense = 0;
        this.InvincibleDuration = 0;
        this.InvisibleDuration = 0;
    }
    public int getBaseDamage() {
        return this.baseDamage;
    }
    public void move(Direction moveDirection) {
        this.setPosition(this.getPosition().translateBy(moveDirection));
    }
    public int getDamage() {
        //gets damage of all things including inventory and use them
        int damage = this.getBaseDamage();
        int attackTurns = 1;
        for (Weapon weapon: this.inventory.getWeapons()) {
            List<Integer> weaponInfo = weapon.getWeaponInfo();
            damage += weaponInfo.get(0);
            attackTurns *= weaponInfo.get(1);
        }
        //damage is a multiple of both
        return damage*attackTurns;
        
    }
    public double getDefense() {
        double defence = this.baseDefense;
        for (DefenseItem d: this.inventory.getDefenseItems()) {
            defence += d.getMultipler();
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
    public Inventory getInventory() {
        return this.inventory;
    }
    public double getBaseDefense() {
        return this.baseDefense;
    }
}