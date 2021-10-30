package dungeonmania;
import java.util.ArrayList;
import java.util.List;

import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class SquareMovement implements Movement {
    
    Direction direction;
    List<Position> path = new ArrayList<>();

    
    public SquareMovement() {
        direction = Direction.UP;
    }


    /**
     * Increment objects position by 1 in the current direction
     * unless it would move to a non-path tile, in which case
     * turn right
     */
    public Position move(Position currentPos) {

        // If this is the first move() call, define the path around currentPos
        if (path.isEmpty()) {
            path = currentPos.getAdjacentPositions();
        }

        // Get the forward tile
        Position newPos = currentPos.translateBy(direction);

        // Get the right tile if the forward tile is off path
        if (!path.contains(newPos)) {
            turnRight();
            newPos = currentPos.translateBy(direction);
        }
        
        return newPos;

    }

    /**
     * Change the direction to be 90 degrees right from its current one
     */
    public void turnRight() {
        // TODO find a cleaner way to do this
        if (direction.equals(Direction.UP)) {
            direction = Direction.RIGHT;
        } else if (direction.equals(Direction.RIGHT)) {
            direction = Direction.DOWN;
        } else if (direction.equals(Direction.DOWN)) {
            direction = Direction.LEFT;
        } else if (direction.equals(Direction.LEFT)) {
            direction = Direction.UP;
        }
    }

}
