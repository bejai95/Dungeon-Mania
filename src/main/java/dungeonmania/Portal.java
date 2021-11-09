package dungeonmania;

import java.util.List;

import java.util.ArrayList;
import java.util.List;

import dungeonmania.util.Position;

public class Portal extends StaticEntity {
    //-----Data-----
    private String potalColour;


    //-----Constructors-----
    public Portal(int id, String type, Position position, String potalColour) {
        super(id, "potal", position);
        this.potalColour = potalColour;
        this.setIsInteractable(true);
    }

    //-----Methods-----
    /*When given a list of static entities this finds the coresponding portal and
    finds the location for where the character should teleport to */
    public Position getTeleportLocation(List<StaticEntity> staticEntitiesList){
        for (StaticEntity staticEntityItem : staticEntitiesList) {
            if (staticEntityItem instanceof Portal) {
                Portal potentialPortal = (Portal)staticEntityItem;
                if ((potentialPortal != this) && (potentialPortal.getPotalColour() == this.potalColour)) {
                    return potentialPortal.getPosition();
                }
            }
        }
        return getPosition();
    }

    //-----Getters and Setters-----
    public String getPotalColour() {
        return potalColour;
    }

}
