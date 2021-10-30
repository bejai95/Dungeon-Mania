package dungeonmania;

import dungeonmania.util.Position;

public class ChaseMovement implements Movement {
    
    Entity target;

    public ChaseMovement(Entity target) {
        this.target = target;
    }

    /**
     * Move object by 1 in the direction that would minimise
     * distance to target
     */
    public Position move(Position currentPos) {

        // Get position vector from this object to the target
        Position dir = Position.calculatePositionBetween(currentPos, target.getPosition());
        
        // Get component of vector with greatest magnitude and move 1 tile in that direction
        if (Math.abs(dir.getX()) >= Math.abs(dir.getY())) {
            int normalisedX = dir.getX()/Math.abs(dir.getX());
            return new Position(normalisedX, currentPos.getY());
        } else {
            int normalisedY = dir.getY()/Math.abs(dir.getY());
            return new Position(currentPos.getX(), normalisedY);   
        }

    }

}
