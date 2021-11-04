package dungeonmania;

import java.util.ArrayList;
import java.util.List;


import dungeonmania.util.Position;

public class FloorSwitch extends StaticEntity {
    //-----Data-----
    //If switch has a boulder on top of it
    private Boolean isActive;

    //Contains a list of all floor switches on the map
    private static List<FloorSwitch> floorSwitchList = new ArrayList<FloorSwitch>();

    //-----Constructors-----
    public FloorSwitch(int id, String type, Position position) {
        super(id, "switch", position);
        isActive = false;
        floorSwitchList.add(this);
    }

    //-----Methods-----
    //Checks to see if a floor switch is present at a given position 
    //Returns the floor switch or null
    public static FloorSwitch isFloorSwitch(Position cell) {
        for (FloorSwitch floorSwitchItem : floorSwitchList) {
            if (floorSwitchItem.getPosition().equals(cell)) {
                return floorSwitchItem;
            }
        }
        return null;
    }

    //-----Getters and Setters-----
    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
        //Checks for nearby bombs to explode
        if (isActive == true) {
            PlacedBomb.explodeOnSwitchCheck(getPosition());
        }
    }

}
