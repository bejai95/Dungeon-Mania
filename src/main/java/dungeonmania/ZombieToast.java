package dungeonmania;

import dungeonmania.util.Position;

public class ZombieToast extends MovingEntity {
    
    public static int defaultHealth;
    public static int defaultAttack;
    public static double defaultdefense;
    public static int defaultSpeed = 1;

    public ZombieToast(int id, Position position, Movement moveBehaviour) {
        super(id, "zombie_toast", position, moveBehaviour);

        setHealth(defaultHealth);
        setDamage(defaultAttack);
        setDefense(defaultdefense);
        setSpeed(defaultSpeed);

    }

}
