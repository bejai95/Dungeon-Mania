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

    public MovingEntity(String type, int health, int speed, Movement moveBehaviour, Position position) {
        super(Entity.getNumEntityIds(), type, position);
        this.health = health;
        this.speed = speed;
        this.moveBehaviour = moveBehaviour;

    }

    public void move() {
        Position newPos = this.getPosition();
        for (int i = 0; i < speed; i++) {
            // TODO layer collision logic - redo move if colliding
            newPos = moveBehaviour.move(this.getPosition());
        }
        this.setPosition(newPos);
    }

    public void giveItem(Item item) {
        this.currentItem = item;
    }

    public Item dropItem() {
        return null;
    }

}
