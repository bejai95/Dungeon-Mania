package dungeonmania;

import java.util.Map;

import dungeonmania.util.Position;

public interface Movement {
    

    public Position move(Position currentPos, Map<PositionSimple, Map<PositionSimple, Double>> grid);

}
