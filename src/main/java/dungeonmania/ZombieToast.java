package dungeonmania;

import dungeonmania.util.Position;

public class ZombieToast extends MovingEntity {
    
    public static int defaultHealth;
    public static int defaultAttack;
    public static double defaultdefense;

    public ZombieToast(int health, int speed, Movement moveBehaviour, Position position) {
        super("zombie_toast", defaultHealth, speed, moveBehaviour, position);
    }

}
