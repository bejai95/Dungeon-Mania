package dungeonmania;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Queue;

import dungeonmania.util.Position;

public class DijkstraMovement implements Movement{
    Entity target;

    public DijkstraMovement() {

    }

    
    /*
    Need:
    - a way of generating the costs:
        - cost(u, v) = number of ticks it takes to get from u to v
        - if not adjacent or there's a wall, put infinity
        - Directed graph because swamp tiles easier to enter than leave (in all directions??)
    */
    private Map<Position, Position> dijkstras(Map<Position, Map<Position, Double>> grid, Position source){
        Map<Position, Double> dist = new HashMap<Position, Double>();
        Map<Position, Position> prev = new HashMap<Position, Position>();

        for(Position p : grid.keySet()){
            dist.put(p, Double.POSITIVE_INFINITY);
            prev.put(p, null);
        }
        dist.put(source, 0.0);
        Comparator<Position> gridComparator = new Comparator<Position>() {
            @Override
            public int compare(Position s1, Position s2) {
                return (int) (dist.get(s1) - dist.get(s2));
            }
        };
        Queue<Position> queue = new PriorityQueue<>(gridComparator);
        queue.addAll(grid.keySet());
        while(!queue.isEmpty()){
            Position u = queue.remove();
            for(Position v : grid.get(u).keySet()){
                if(grid.get(u).get(v) != Double.POSITIVE_INFINITY){
                    if(dist.get(u) + grid.get(u).get(v) < dist.get(v)){
                        dist.put(v, dist.get(u) + grid.get(u).get(v));
                        prev.put(v, u);
                    }
                }
            }
        }
        return prev;
    }


    private Position walkBackThroughPath(Position placeInPath, Map<Position, Position> pre, Position source){
        Position last = pre.get(placeInPath);
        if(Objects.equals(last, source)){
            return placeInPath;
        }
        return walkBackThroughPath(last, pre, source);
    }
    @Override
    public Position move(Position currentPos, Map<Position, Map<Position, Double>> grid) {
        return walkBackThroughPath(target.getPosition(), dijkstras(grid, currentPos), currentPos);
    }


    

}
