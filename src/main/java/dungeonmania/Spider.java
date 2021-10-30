package dungeonmania;

import dungeonmania.util.Position;

public class Spider extends MovingEntity {

    public static int defaultHealth;
    public static int defaultAttack;
    public static double defaultdefense;

    public static int maxSpiders;
    public static int numSpiders;

    public Spider(int speed, Movement moveBehaviour, Position position) {
        super("spider", defaultHealth, speed, moveBehaviour, position);
        numSpiders++;
    }

}
