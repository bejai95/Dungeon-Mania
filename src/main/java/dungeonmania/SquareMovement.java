package dungeonmania;
import java.util.ArrayList;
import java.util.List;

import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class SquareMovement implements Movement {
    
    Direction direction;
    List<Position> path = new ArrayList<>();

    
    public SquareMovement(Position start) {
        direction = Direction.UP;
        path = getPath(start);
    }


    /**
     * Increment objects position by 1 in the current direction
     * unless it would move to a non-path tile, in which case
     * turn right
     */
    public void move() {

    }

    /**
     * Given the starting position, return a list of Positions
     * which represent the square path
     * @param start
     * @return
     */
    public List<Position> getPath(Position start) {
        return null;
    }

    /**
     * Change the direction to be 90 degrees from its current one
     */
    public void turnRight() {

    }

}
