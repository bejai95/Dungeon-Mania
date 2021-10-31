package dungeonmania;

import dungeonmania.util.Position;

public class Spider extends MovingEntity {

    public static int defaultHealth = 1;
    public static int defaultAttack = 1;
    public static double defaultdefense;
    public static int defaultSpeed = 1;

    public static int maxSpiders;
    public static int numSpiders;

    public Spider(int id, Position position, Movement moveBehaviour) {
        super(id, "spider", position, moveBehaviour);
        numSpiders++;

        setHealth(defaultHealth);
        setDamage(defaultAttack);
        setDefense(defaultdefense);
        setSpeed(defaultSpeed);
    }

}
