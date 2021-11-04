package dungeonmania;

import dungeonmania.util.Position;

public class ChaseMovement implements Movement {
    
    Entity target;

    public ChaseMovement() {

    }

    /**
     * Move object by 1 in the direction that would minimise
     * distance to target
     */
    public Position move(Position currentPos) {

        if (target == null) {
            return currentPos;
        }

        // Get position vector from this object to the target
        Position dir = Position.calculatePositionBetween(currentPos, target.getPosition());
        Position offset;

        // This object is on the same tile as target, dont move
        if (dir.equals(new Position(0,0))) {
            return currentPos;
        }

        // Get component of vector with greatest magnitude and move 1 tile in that direction
        if (Math.abs(dir.getX()) >= Math.abs(dir.getY())) {
            int normalisedX = dir.getX()/Math.abs(dir.getX());
            offset = new Position(normalisedX, 0);
        } else {
            int normalisedY = dir.getY()/Math.abs(dir.getY());
            offset = new Position(0, normalisedY);   
        }
        return currentPos.translateBy(offset);
    }

    public void setTarget(Entity target) {
        this.target = target;
    }

}
