package dungeonmania;

import java.util.Random;

import dungeonmania.util.Direction;
import dungeonmania.util.Position;
import java.util.Map;

public class RandomMovement implements Movement {
    
    /**
     * Move object by 1 in a random cardinal direction
     */
    public Position move(Position currentPos, Map<Position, Map<Position, Double>> grid) {
        Random rand = new Random();
        int randDirection = rand.nextInt(4);

        // TODO find a cleaner way to do this
        switch (randDirection) {
            case 0:
                return currentPos.translateBy(Direction.UP);
            case 1:
                return currentPos.translateBy(Direction.RIGHT);
            case 2:
                return currentPos.translateBy(Direction.DOWN);
            case 3:
                return currentPos.translateBy(Direction.LEFT);
            default:
                return null;
        }

    }

}
