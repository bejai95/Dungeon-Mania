package dungeonmania;

import dungeonmania.util.Position;

public class Mercenary extends MovingEntity {
    
    public static int defaultHealth;
    public static int defaultAttack;
    public static double defaultdefense;

    boolean isHostile;
    int goldThreshold;
    int currentGold;
    int battleRadius;

    public Mercenary(int speed, Position position, int goldThreshold, Character player) {
        super("mercenary", defaultHealth, speed, new ChaseMovement(player), position);
        this.goldThreshold = goldThreshold;
        this.currentGold = 0;
    }


    public void takeGold(Treasure gold) {

    }

}
