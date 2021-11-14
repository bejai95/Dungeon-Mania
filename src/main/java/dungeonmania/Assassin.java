package dungeonmania;

import dungeonmania.exceptions.InvalidActionException;

import dungeonmania.util.Position;

public class Assassin extends Mercenary {
    
    // TODO set default values, currently same as merc
    public static int defaultHealth = 200;
    public static int defaultAttack = 3;
    public static double defaultdefense = 0;
    public static int defaultSpeed = 1;

    private boolean hasRing;

    public Assassin(int id, Position position, double armourChance) {
        super(id, position, armourChance);
        setType("assassin");
        hasRing = false;
        setHealth(defaultHealth);
        setDamage(defaultAttack);
        setDefense(defaultdefense);
        setSpeed(defaultSpeed);

    }

    /**
     * Character bribes the assassin
     * Take treasure and one ring from char inventory and turn
     * into ally if recieved enough treasure
     * @param ch
     */
    @Override
    public void interact(Character ch) throws InvalidActionException {

        if (!getIsHostile()) return;

        if (!super.inBribingRange(ch)) {
            throw new InvalidActionException("Player not within 2 cardinal tiles of mercenary");
        }

        Item t = ch.getInventory().getItemFromType("treasure");
        Item r = ch.getInventory().getItemFromType("one_ring");
        Item ss = ch.getInventory().getItemFromType("sun_stone");
        if (ss != null) {
            currentGold++;
        }
        else if (t != null) {
            // Remove the treasure
            ch.getInventory().removeItem(t);
            currentGold++;
        } else {
            throw new InvalidActionException("Player does not have any treasure to bribe with");
        }

        // Remove one ring
        if(r != null && !hasRing) {
            ch.getInventory().removeItem(r);
            hasRing = true;
        }
        if (currentGold >= goldThreshold && hasRing) {
            setIsInteractable(false);
            setIsHostile(false);
            System.out.println("BRIBE SUCCESSFUL");
        }
    }

}
