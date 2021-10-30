package dungeonmania;

import dungeonmania.util.Position;

public class Mercenary extends MovingEntity {
    
    boolean isHostile;
    int goldThreshold;
    int battleRadius;

    public Mercenary(int health, int speed, Movement moveBehaviour, Position position, int goldThreshold) {
        super("mercenary", health, speed, moveBehaviour, position);
        this.goldThreshold = goldThreshold;
    }


    public void takeGold(Treasure gold) {

    }

}
