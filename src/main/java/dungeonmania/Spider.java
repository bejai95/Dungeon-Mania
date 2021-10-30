package dungeonmania;

import dungeonmania.util.Position;

public class Spider extends MovingEntity {
    
    public static int numSpiders;
//TODO static base stats
    public Spider(int health, int speed, Movement moveBehaviour, Position position, int id) {
        super("spider", health, speed, moveBehaviour, position, id);
        numSpiders++;
    }

}
