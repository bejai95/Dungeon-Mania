package dungeonmania;

import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.util.Position;
import java.util.Map;

public class Mercenary extends MovingEntity {
    
    public static int defaultHealth = 200;
    public static int defaultAttack = 3;
    public static double defaultdefense = 0;
    public static int defaultSpeed = 1;
    
    int goldThreshold = 1;
    int currentGold = 0;
    int battleRadius = 2;

    public Mercenary(int id, Position position, double armourChance) {
        super(id, "mercenary", position, new ChaseMovement());
        this.currentGold = 0;
        setIsInteractable(true);
        
        setHealth(defaultHealth);
        setDamage(defaultAttack);
        setDefense(defaultdefense);
        setSpeed(defaultSpeed);

        this.spawnArmour(armourChance);

    }

    /**
     * Moves as normal but will not move onto ally character
     */
    public void move(Map<PositionSimple, Map<PositionSimple, Double>> grid) {
        Position newPos = this.getPosition();
        for (int i = 0; i < speed; i++) {
            ChaseMovement chaseMove = (ChaseMovement)moveBehaviour;
            // Only moves if merc is hostile, or if ally player is not adjacent
            if (getIsHostile()) {
                newPos = moveBehaviour.move(this.getPosition(), grid);
            } else if (!chaseMove.targetIsAdjacent(this.getPosition())) {
                newPos = moveBehaviour.move(this.getPosition(), grid);
            }
        }
        this.setPosition(newPos);
    }

    public void setGoldThreshold(int goldThreshold) {
        this.goldThreshold = goldThreshold;
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

        System.out.println("INTERACTION CALLED");

        if (!getIsHostile()) return;

        if (!inBribingRange(ch)) {
            throw new InvalidActionException("Player not within 2 cardinal tiles of mercenary");
        }
        Item t = ch.getInventory().getItemFromType("treasure");
        if(t != null) {
            // Remove the treasure
            ch.getInventory().removeItem(t);
            currentGold++;
        } else {
            throw new InvalidActionException("Player does not have any treasure to bribe with");
        }

        if (currentGold >= goldThreshold) {
            setIsInteractable(false);
            setIsHostile(false);
            System.out.println("BRIBE SUCCESSFUL");
        }
    }

    public boolean inBribingRange(Character ch) {
        // Get position of character, relative to mercenary
        Position relativePos = Position.calculatePositionBetween(this.getPosition(), ch.getPosition());

        // relativePos must have at least one 0 component for the player to be
        // in a cardinal direction (straight line) from merc
        if(Math.abs(relativePos.getX())*Math.abs(relativePos.getY()) != 0) {
            System.out.println("OUT OF RANGE");
            return false;
        }

        // If the non-zero component exceeds bribe range, character is out of range
        if (Math.abs(relativePos.getX()) > 2 || Math.abs(relativePos.getY()) > 2) {
            System.out.println("OUT OF RANGE");
            return false;
        }
        System.out.println("IN RANGE");
        return true;
    }

    public void chase(Character ch) {
        ChaseMovement newMovement = new ChaseMovement();
        newMovement.setTarget(ch);
        super.setMovement(newMovement);
    }

    @Override
    public boolean isAlly(){
        return !isHostile;
    }

}
