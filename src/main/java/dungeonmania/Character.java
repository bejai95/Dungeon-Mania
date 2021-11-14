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
    private String gameMode;

    public Character(int id, Position position) {
        super(id, "player", position);
        this.health = 500;
        this.maxHealth = this.health;
        this.baseDamage = 3;
        this.baseDefense = 0;
        this.InvincibleDuration = 0;
        this.InvisibleDuration = 0;
    }
    public int getBaseDamage() {
        return this.baseDamage;
    }
    /**
     * moves in a given direction
     * @param moveDirection
     */
    public void move(Direction moveDirection) {
        this.setPosition(this.getPosition().translateBy(moveDirection));
    }
    /**
     * @return the accumulated damage of all the weapons in the character's inventory
     */
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
    /**
     * 
     * @return the accumulated defense from all the defense items in the character's inventory
     */
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
    /**
     * @return the actual multipler to apply to enemy damage
     */
    public double getDefenseMultiplier() {
        return 1 - getDefense();
    }
    public double getHealth() {
        return this.health;
    }
    /**
     * Sets the health of the character when damage is taken
     */
    public void setHealth(double newHealth) {
        if (!(this.isInvincible())) {
            this.health = newHealth;
        }
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
    /**
     * 
     * @return true if duration of invisibility potion still in effect
     */
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
    /**
     * 
     * @return true if duration of invincibility potion still in effect
     */
    public boolean isInvincible () {
        if (this.InvincibleDuration > 0 && (!(this.getGameMode().equals("hard")))) {
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
    public String getGameMode() {
        return this.gameMode;
    }
    /**
     * 
     * @param gameMode changes value of some character attributes if hard
     */
    public void setGameMode(String gameMode) {
        //sets gamemode and alters max health
        this.gameMode = gameMode;
        if (this.getGameMode().equals("hard")) {
            this.maxHealth = 200;
            this.setHealth(this.getMaxHealth());
        }
        //and current health
    }
}