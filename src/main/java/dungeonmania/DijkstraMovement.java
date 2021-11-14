package dungeonmania;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.stream.Collectors;

import dungeonmania.util.Position;

/*public class DijkstraMovement implements Movement{
    Entity target;

    public DijkstraMovement(Entity target) {
        this.target = target;
    }
*/
    
    /*
    Need:
    - a way of generating the costs:
        - cost(u, v) = number of ticks it takes to get from u to v
        - if not adjacent or there's a wall, put infinity
        - Directed graph because swamp tiles easier to enter than leave (in all directions??)
    */
    /*private String getPosString(Position pos){
        return ("(" + pos.getX() + ", " + pos.getY() + ")");
    }
    private void printSourceCol(Map<Position, Map<Position, Double>> grid, Position source){
        for(Position pos : grid.get(source).keySet()){
            System.out.println(getPosString(pos) + ":" + grid.get(source).get(pos));
        }
    }*/

    /*private List<Position> getAdjacentPositionsInGrid(Position pos, Map<Position, Map<Position, Double>> grid){
        return pos.getCardinallyAdjacentPositions().stream().filter(x -> grid.keySet().contains(x)).collect(Collectors.toList());
    }
    private Map<Position, Position> dijkstras(Map<Position, Map<Position, Double>> grid, Position source){
        //printSourceCol(grid, source);
        Map<Position, Double> dist = new HashMap<Position, Double>();
        Map<Position, Position> prev = new HashMap<Position, Position>();

        for(Position p : grid.keySet()){
            dist.put(p, Double.POSITIVE_INFINITY);
            prev.put(p, null);
        }
        dist.put(source, 0.0);*/
        /*Comparator<Position> gridComparator = new Comparator<Position>() {
            @Override
            public int compare(Position s1, Position s2) {
                //return (int) (dist.get(s2) - dist.get(s1));
                if(dist.get(s1) < dist.get(s2)){
                    return -1;
                }
                if(dist.get(s1) > dist.get(s2)){
                    return 1;
                }
                return 0;
            }
        };*/
       /* PriorityQueue<Position> queue = new PriorityQueue<Position>(grid.keySet().size(), (a, b) -> Double.compare(dist.get(a), dist.get(b)));
        //queue.addAll(grid.keySet());
        queue.add(source);
        while(!queue.isEmpty()){
            Position u = queue.remove();
            /*double d = dist.get(u); 
            int i = (int) d;
            System.out.println("(" + u.getX() + ", " + u.getY() + ")" + " Has dist: " + i);*/
            /*for(Position v : getAdjacentPositionsInGrid(u, grid)){
                if(grid.get(u).get(v) != Double.POSITIVE_INFINITY){
                    if(dist.get(u) + grid.get(u).get(v) < dist.get(v)){
                        dist.put(v, dist.get(u) + grid.get(u).get(v));
                        prev.put(v, u);
                        queue.add(v);
                    }
                }
            }
        }
        return prev;
    }*/
    /*
    Getting stack overflow
        - Print out map generated by dijkstras - could be problem
        - The recursion below looks fine, but it could be what's causing issues, take another look
    */

   /* private Position walkBackThroughPath(Position placeInPath, Map<Position, Position> pre, Position source, Position init){
        Position last = pre.get(placeInPath);
        if(last == null){
            System.out.println("Nowhere to move");
            return init;
        }
        if(Objects.equals(last, source)){
            System.out.println(placeInPath);
            return placeInPath;
        }
        return walkBackThroughPath(last, pre, source, init);
    }*/
    /*private void printMap(Map<Position, Position> pre){
        if(pre == null){
            return;
        }
        for(Position pos : pre.keySet()){
            System.out.println("(" + pos.getX() + ", " + pos.getY() + ") from (" + pre.get(pos).getX() + ", " + pre.get(pos).getY()); 
        }
    }*/
    
  /*  @Override
    public Position move(Position currentPos, Map<Position, Map<Position, Double>> grid) {
        Map<Position, Position> ret = dijkstras(grid, currentPos);
        //printMap(ret);
        return walkBackThroughPath(target.getPosition(), ret, currentPos, currentPos);
    }


    

}*/
