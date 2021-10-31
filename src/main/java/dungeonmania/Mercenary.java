package dungeonmania;

import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.util.Position;

public class Mercenary extends MovingEntity implements interaction {
    
    public static int defaultHealth;
    public static int defaultAttack;
    public static double defaultdefense;
    public static int defaultSpeed = 1;

    int goldThreshold;
    int currentGold;
    int battleRadius;

    public Mercenary(int id, Position position, int goldThreshold, Character player) {
        super(id, "mercenary", position, new ChaseMovement(player));
        this.goldThreshold = goldThreshold;
        this.currentGold = 0;

        setHealth(defaultHealth);
        setDamage(defaultAttack);
        setDefense(defaultdefense);
        setSpeed(defaultSpeed);

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
    
    /**
     * Character bribes the merc
     * Take treasure from char inventory and turn
     * into ally if recieved enough treasure
     * @param ch
     */
    public void interact(Character ch) throws InvalidActionException {

        if (!inBribingRange(ch)) {
            throw new InvalidActionException("Player not within 2 cardinal tiles of mercenary");
        }

        if(/* ch.inventory has treasure */) {
            // Remove the treasure
            currentGold++;
        } else {
            throw new InvalidActionException("Player does not have any treasure to bribe with");
        }

        if (currentGold >= goldThreshold) {
            isHostile = false;
            // TODO change layer to something lower than players one
        }
    }

    private boolean inBribingRange(Character ch) {

        // Get position of character, relative to mercenary
        Position relativePos = Position.calculatePositionBetween(this.getPosition(), ch.getPosition());

        // relativePos must have at least one 0 component for the player to be
        // in a cardinal direction (straight line) from merc
        if(Math.abs(relativePos.getX())*Math.abs(relativePos.getY()) != 0) {
            return false;
        }

        // If the non-zero component exceeds bribe range, character is out of range
        if (Math.abs(relativePos.getX()) > 2 || Math.abs(relativePos.getY()) > 2) {
            return false;
        }

        return true;
    }

    // TODO maybe a chase(Entity ent) function to set target dynamically

}
