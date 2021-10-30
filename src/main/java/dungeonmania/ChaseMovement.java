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
        return null;
    }

    public double getDistToTarget() {
        return 0;
    }

}
