package dungeonmania;

import dungeonmania.util.Position;

public class Mercenary extends MovingEntity {
    
    public static int defaultHealth;
    public static int defaultAttack;
    public static double defaultdefense;
    public static int defaultSpeed = 1;

    boolean isHostile;
    boolean isHelping;
    int goldThreshold;
    int currentGold;
    int battleRadius;

    public Mercenary(int id, Position position, int goldThreshold, Character player) {
        super(id, "mercenary", position, new ChaseMovement(player));
        isHostile = true;
        isHelping = false;
        this.goldThreshold = goldThreshold;
        this.currentGold = 0;

        setHealth(defaultHealth);
        setDamage(defaultAttack);
        setDefense(defaultdefense);
        setSpeed(defaultSpeed);

    }

    public void help() {
        if (!isHelping) {
            int currSpeed = getSpeed();
            setSpeed(currSpeed*2);
            isHelping = true;
        }
    }

    public void stopHelp() {
        if (isHelping) {
            int currSpeed = getSpeed();
            setSpeed(currSpeed/2);
            isHelping = false;
        }
    }
    
    public void doubleSpeed() {
        int currSpeed = getSpeed();
        setSpeed(currSpeed*2);
    }

    public boolean entityInRadius(Entity ent) {
        // Get position vector from merc to ent
        Position dir = Position.calculatePositionBetween(getPosition(), ent.getPosition());
        // Check the magnitude of both x,y component doesn't exceed radius
        return (Math.abs(dir.getX()) <= battleRadius && Math.abs(dir.getY()) <= battleRadius);
    }
    

    public void bribe() {

        // TODO access player inventory somehow. check how unpickedupitem does it
        currentGold++;
        if (currentGold >= goldThreshold) {
            isHostile = false;
            // TODO change layer to something lower than players one
        }
    }

    // TODO maybe a chase(Entity ent) function to set target dynamically

}
