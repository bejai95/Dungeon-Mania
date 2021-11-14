package dungeonmania;

import java.util.Random;

import dungeonmania.util.Position;

public class MovingEntity extends Entity implements Battleable{
    
    //TODO dont forget to set values to the default battle stats in subclasses

    double health;
    int damage;
    double baseDefense;
    double defense;
    int speed;

    boolean isHostile;
    Movement moveBehaviour;
    Armour armour;

    public MovingEntity(int id, String type, Position position, Movement moveBehaviour) {
        super(id, type, position);
        this.moveBehaviour = moveBehaviour;
        this.isHostile = true;
        this.armour = null;
    }

    /**
     * @return The position of the next move in this
     * entity's movement pattern
     */
    public Position getNextMove() {
        return moveBehaviour.move(this.getPosition());
    }

    /**
     * Set this entity's position to where its movement would take it
     * after 'speed' times
     */
    public void move() {
        Position newPos = this.getPosition();
        for (int i = 0; i < speed; i++) {
            newPos = moveBehaviour.move(newPos);
        }
        this.setPosition(newPos);
    }

    /**
     * Given a spawnChance between 0.0 and 1.0 and
     * rolls for the armour to be spawned
     * @param spawnChance
     */
    public void spawnArmour(double spawnChance) {
        if (this.armour != null) return;

        Random rand = new Random();
        double roll = rand.nextDouble();
        if (roll < spawnChance) {
            this.armour = new Armour(Game.generateUniqueId());
        }
    }

    /**
     * Remove the current held item of the entity
     * and return it
     * @return
     */
    public Item dropItem() {
        Item temp = this.armour;
        this.armour = null;
        return temp;
    }

    public void setMovement(Movement move) {
        this.moveBehaviour = move;
    }

    public boolean getIsHostile() {
        return this.isHostile;
    }

    public void setIsHostile(boolean bool) {
        this.isHostile = bool;
    }

    // =========== Battle Related Functions =================

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public void setDefense(double defense) {
        this.baseDefense = defense;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
    
    public double getHealth() {
        return this.health;
    }

    public int getDamage() {
        return this.damage;
    }

    public double getDefense() {
        double defence = this.baseDefense;

        if (armour != null) {
            defence += armour.getMultipler();
        }
        //if value of defense has gone to high, set it to 1.
        if (defence > 1) {
            defence = 1;
        }
        return defence;
    }

    public int getSpeed() {
        return this.speed;
    }

    public void setHealth(double health){
        this.health = health;
    }

    public double getDefenseMultiplier(){
        return 1-getDefense();
    }

    public boolean isAlly(){
        return !isHostile;
    }

}
