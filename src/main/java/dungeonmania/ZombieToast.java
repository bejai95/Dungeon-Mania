package dungeonmania;

import dungeonmania.util.Position;

public class ZombieToast extends MovingEntity {
    

    public ZombieToast(int health, int speed, Movement moveBehaviour, Position position) {
        super("zombie_toast", health, speed, moveBehaviour, position);
    }

}