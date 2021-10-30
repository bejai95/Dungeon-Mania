package dungeonmania;

import dungeonmania.util.Position;
import java.util.ArrayList;
import java.util.List;

public class PlacedBomb extends StaticEntity {


    //-----Data-----
    private static List<PlacedBomb> placedBombList = new ArrayList<PlacedBomb>();

    //-----Constructors-----
    public PlacedBomb(int id, String type, Position position) {
        super(id, "bomb", position);
        placedBombList.add(this);
        explodeOnPlacementCheck();
    }

    
    //-----Methods-----
    //Checks to see if bomb will explode when placed
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

    //Checks to see if bomb will explode when switch is pressed
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

    //explodes killing all enemies 1 cell adjacent to the bomb
    public void explode() {
        List<Position> adjacentPositions = this.getPosition().getAdjacentPositions();
        for (Position edgeCell : adjacentPositions) {
            return; //Add method which kills enemies at the given position
        }
    }


    //-----Getters and Setters-----
    
}
