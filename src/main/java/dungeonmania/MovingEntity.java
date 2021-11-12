package dungeonmania;

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
    Item currentItem;

    public MovingEntity(int id, String type, Position position, Movement moveBehaviour) {
        super(id, type, position);
        this.moveBehaviour = moveBehaviour;
        this.isHostile = true;
        this.currentItem = null;
    }

    public void setHealth(int health) {
        this.health = health;
    }

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
        return this.defense;
    }

    public int getSpeed() {
        return this.speed;
    }

    public boolean getIsHostile() {
        return this.isHostile;
    }

    public void setMovement(Movement move) {
        this.moveBehaviour = move;
    }

    public void setIsHostile(boolean bool) {
        this.isHostile = bool;
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

    public void giveItem(Item item) {
        this.currentItem = item;
    }

    public Item dropItem() {
        return null;
    }

    public void setHealth(double health){
        this.health = health;
    }

    public double getDefenseMultiplier(){
        return 1-defense;
    }

}
