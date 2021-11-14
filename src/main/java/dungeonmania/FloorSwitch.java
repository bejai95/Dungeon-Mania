package dungeonmania;

import java.util.ArrayList;
import java.util.List;


import dungeonmania.util.Position;

public class FloorSwitch extends StaticEntity {
    //-----Data-----
    //If switch has a boulder on top of it
    private Boolean isActive;

    //-----Constructors-----
    public FloorSwitch(int id, Position position) {
        super(id, "switch", position);
        isActive = false;
    }

    //-----Methods-----

    //-----Getters and Setters-----
    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

}
