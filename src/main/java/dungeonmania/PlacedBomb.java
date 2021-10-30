package dungeonmania;

import dungeonmania.util.Position;
import java.util.ArrayList;
import java.util.List;

public class PlacedBomb extends StaticEntity {


    //-----Data-----
    //This contains a list of all placed bombs on the map
    private static List<PlacedBomb> placedBombList = new ArrayList<PlacedBomb>();

    //-----Constructors-----
    public PlacedBomb(int id, String type, Position position) {
        super(id, "bomb", position);
        placedBombList.add(this);
        explodeOnPlacementCheck();
    }

    
    //-----Methods-----
    /*Checks to see if bomb will explode when it is first placed down.
    This will hapen if it is placed adjacent to an active switch*/
    public void explodeOnPlacementCheck (){
        List<Position> adjacentPositions = this.getPosition().getAdjacentPositions();
        FloorSwitch possibleSwitch;
        for (Position edgeCell : adjacentPositions) {
            possibleSwitch = FloorSwitch.isFloorSwitch(edgeCell);
            if (possibleSwitch != null && possibleSwitch.getIsActive()) {
                explode();
            }
        }
    }

    /*When a switch is pressed it can call this method with it's position to call
    on any adjacent bombs to explode*/
    public static void explodeOnSwitchCheck (Position switchPos){
        List<Position> adjacentPositions = switchPos.getAdjacentPositions();
        for (Position edgecell : adjacentPositions) {
            for (PlacedBomb possibleBomb : placedBombList ) {
                if (edgecell.equals(possibleBomb.getPosition())) {
                    possibleBomb.explode();
                }   
            }
        }
    }

    /*explodes killing all enemies 1 cell adjacent to the bomb
    and removing the placed bomb*/
    public void explode() {
        List<Position> adjacentPositions = this.getPosition().getAdjacentPositions();
        for (Position edgeCell : adjacentPositions) {
            //Removes the bomb from the placed bombs list
            placedBombList.remove(this);
            //Removes the bomb from the static entities list
            super.removeStaticEntity();
            //Add method here which kills enemies at the given position
            //....
        }
        return;
    }


    //-----Getters and Setters-----
    
}
