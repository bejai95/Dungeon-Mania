package dungeonmania;

import dungeonmania.util.Position;

public class ZombieToast extends MovingEntity {
    
    public static int defaultHealth = 100;
    public static int defaultAttack = 10;
    public static double defaultdefense = 0;
    public static int defaultSpeed = 1;

    public ZombieToast(int id, Position position, Movement moveBehaviour, double armourChance) {
        super(id, "zombie_toast", position, moveBehaviour);

        setHealth(defaultHealth);
        setDamage(defaultAttack);
        setDefense(defaultdefense);
        setSpeed(defaultSpeed);

        super.spawnArmour(armourChance);

    }
    /**
     * actually applies the random move since we want to see what our next move is before we do it for random movement
     */
    public void applyNextMove() {
        if (moveBehaviour instanceof RandomMovement) {
            RandomMovement rm = (RandomMovement) moveBehaviour;
            this.setPosition(rm.getNextPos());
        }
    }
}
