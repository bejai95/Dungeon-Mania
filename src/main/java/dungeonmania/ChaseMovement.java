package dungeonmania;

public class ChaseMovement implements Movement {
    
    Entity target;

    public ChaseMovement(Entity target) {
        this.target = target;
    }

    /**
     * Move object by 1 in the direction that would minimise
     * distance to target
     */
    public void move() {

    }

    public double getDistToTarget() {
        return 0;
    }

}
