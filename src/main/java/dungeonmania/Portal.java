package dungeonmania;

import java.util.List;

import java.util.ArrayList;
import java.util.List;

import dungeonmania.util.Position;

public class Portal extends StaticEntity {
    //-----Data-----
    private String portalColour;


    //-----Constructors-----
    public Portal(int id, Position position, String portalColour) {
        super(id, "portal", position);
        this.portalColour = portalColour;
        this.setIsInteractable(true);
    }

    //-----Methods-----
    /*When given a list of static entities this finds the coresponding portal and
    finds the location for where the character should teleport to */
    public Position getTeleportLocation(List<StaticEntity> staticEntitiesList){
        for (StaticEntity staticEntityItem : staticEntitiesList) {
            if (staticEntityItem instanceof Portal) {
                Portal potentialPortal = (Portal)staticEntityItem;
                if ((potentialPortal != this) && (portalColour.equals(potentialPortal.getportalColour()))){
                        return potentialPortal.getPosition();
                }
            }
        }
        return getPosition();
    }

    //-----Getters and Setters-----
    public String getportalColour() {
        return portalColour;
    }

}
