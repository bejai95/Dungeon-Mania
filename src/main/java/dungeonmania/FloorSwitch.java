package dungeonmania;

import java.util.ArrayList;
import java.util.List;


import dungeonmania.util.Position;

public class FloorSwitch extends StaticEntity {
    //-----Data-----
    private Boolean isActive;
    private static int noOfSwitches;
    private static int noOfActiveSwitches;
    private static List<FloorSwitch> floorSwitchList = new ArrayList<FloorSwitch>();

    //-----Constructors-----
    public FloorSwitch(int id, String type, Position position) {
        super(id, "switch", position);
        isActive = false;
        noOfSwitches++;
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
        if (isActive == true) {
            noOfActiveSwitches++;
        } else {
            noOfActiveSwitches--;            
        }
        this.isActive = isActive;
    }

    public static int getNoOfSwitches() {
        return noOfSwitches;
    }

    public static int getNoOfActiveSwitches() {
        return noOfActiveSwitches;
    }


}
