package dungeonmania;

import dungeonmania.util.Position;

public abstract class MovingEntity extends Entity {
    
    int health;
    int damage;
    double baseDefense;
    int speed;
    Movement moveBehaviour;
    Item currentItem;

    public MovingEntity() {}

    //TODO damage and defense in contructor
    public MovingEntity(String type, int health, int speed, Movement moveBehaviour, Position position, int id) {
        super(id, type, position);
        this.health = health;
        this.speed = speed;
        this.moveBehaviour = moveBehaviour;

    }

    public void move() {
        
    }

    public void giveItem(Item item) {
        this.currentItem = item;
    }

    public Item dropItem() {
        return null;
    }

}
