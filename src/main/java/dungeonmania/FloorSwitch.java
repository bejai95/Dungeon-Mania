package dungeonmania;

import org.eclipse.jetty.server.session.FileSessionDataStore;

import dungeonmania.util.Position;

public class FloorSwitch extends StaticEntity {
    //-----Data-----
    private Boolean isActive;
    private static int noOfSwitches;
    private static int noOfActiveSwitches;

    //-----Constructors-----
    public FloorSwitch(int id, String type, Position position) {
        super(id, "switch", position);
        isActive = false;
        noOfSwitches++;
    }

    //-----Methods-----

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
