package dungeonmania;

import java.util.Random;

import dungeonmania.util.Direction;
import dungeonmania.util.Position;
import java.util.Map;

public class RandomMovement implements Movement {
    Position nextPos = null;
    /**
     * Move object by 1 in a random cardinal direction
     */
    public Position move(Position currentPos, Map<PositionSimple, Map<PositionSimple, Double>> grid) {
        Random rand = new Random();
        int randDirection = rand.nextInt(4);
        // TODO find a cleaner way to do this
        switch (randDirection) {
            case 0:
                this.setNextPos(currentPos.translateBy(Direction.UP));
                return this.getNextPos();
            case 1:
                this.setNextPos(currentPos.translateBy(Direction.RIGHT));
                return this.getNextPos();
            case 2:
                this.setNextPos(currentPos.translateBy(Direction.DOWN));
                return this.getNextPos();
            case 3:
                this.setNextPos(currentPos.translateBy(Direction.LEFT));
                return this.getNextPos();
            default:
                return null;
        }

    }
    public void setNextPos(Position nextPos) {
        this.nextPos = nextPos;
    }
    public Position getNextPos() {
        return this.nextPos;
    }

}
