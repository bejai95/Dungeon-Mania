package dungeonmania;

import dungeonmania.util.Position;

public class Mercenary extends MovingEntity {
    
    boolean isHostile;
    int goldThreshold;
    int battleRadius;

    public Mercenary(int health, int speed, Movement moveBehaviour, Position position, int goldThreshold, int id, String type) {
        super(type, health, speed, moveBehaviour, position, id);
        this.goldThreshold = goldThreshold;
    }


    public void takeGold(Treasure gold) {

    }

}
