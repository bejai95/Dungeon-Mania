package dungeonmania;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public final class PositionSimple {
    private final int x, y;

    public PositionSimple(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public PositionSimple(Position pos){
        this.x = pos.getX();
        this.y = pos.getY();
    }

    @Override
    public final int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public final boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        PositionSimple other = (PositionSimple) obj;

        // z doesn't matter
        return x == other.x && y == other.y;
    }

    public final int getX() {
        return x;
    }

    public final int getY() {
        return y;
    }

    public final PositionSimple translateBy(int x, int y) {
        return this.translateBy(new PositionSimple(x, y));
    }

    public final PositionSimple translateBy(Direction direction) {
        return this.translateBy(new PositionSimple(direction.getOffset().getX(), direction.getOffset().getY()));
    }

    public final PositionSimple translateBy(PositionSimple position) {
        return new PositionSimple(this.x + position.x, this.y + position.y);
    }

    // (Note: doesn't include z)

    /**
     * Calculates the position vector of b relative to a (ie. the direction from a
     * to b)
     * @return The relative position vector
     */
    public static final PositionSimple calculatePositionBetween(PositionSimple a, PositionSimple b) {
        return new PositionSimple(b.x - a.x, b.y - a.y);
    }

    public static final boolean isAdjacent(PositionSimple a, PositionSimple b) {
        int x = a.x - b.x;
        int y = a.y - b.y;
        return Math.abs(x) + Math.abs(y) == 1;
    }

    // (Note: doesn't include z)
    public final PositionSimple scale(int factor) {
        return new PositionSimple(x * factor, y * factor);
    }

    @Override
    public final String toString() {
        return "Position [x=" + x + ", y=" + y + ", z=" + "]";
    }

    // Return Adjacent positions in an array list with the following element positions:
    // 0 1 2
    // 7 p 3
    // 6 5 4
    public List<PositionSimple> getAdjacentPositions() {
        List<PositionSimple> adjacentPositions = new ArrayList<>();
        adjacentPositions.add(new PositionSimple(x-1, y-1));
        adjacentPositions.add(new PositionSimple(x  , y-1));
        adjacentPositions.add(new PositionSimple(x+1, y-1));
        adjacentPositions.add(new PositionSimple(x+1, y));
        adjacentPositions.add(new PositionSimple(x+1, y+1));
        adjacentPositions.add(new PositionSimple(x  , y+1));
        adjacentPositions.add(new PositionSimple(x-1, y+1));
        adjacentPositions.add(new PositionSimple(x-1, y));
        return adjacentPositions;
    }

    public List<PositionSimple> getCardinallyAdjacentPositions() {
        List<PositionSimple> adjacentPositions = new ArrayList<>();
        adjacentPositions.add(new PositionSimple(x  , y-1));
        adjacentPositions.add(new PositionSimple(x+1, y));
        adjacentPositions.add(new PositionSimple(x  , y+1));
        adjacentPositions.add(new PositionSimple(x-1, y));
        return adjacentPositions;
    }
}
