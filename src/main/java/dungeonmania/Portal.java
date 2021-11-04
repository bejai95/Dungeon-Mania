package dungeonmania;

import java.util.List;

import java.util.ArrayList;
import java.util.List;

import dungeonmania.util.Position;

public class Portal extends StaticEntity {
    //-----Data-----
    private String potalColour;

    //Contains a list of all portals on the map
    private static List<Portal> portalList = new ArrayList<Portal>();

    //-----Constructors-----
    public Portal(int id, String type, Position position, String potalColour) {
        super(id, "potal", position);
        this.potalColour = potalColour;
        portalList.add(this);
    }

    //-----Methods-----
    //Gets the location for where the caracter should teleport to 
    public Position getTeleportLocation(){
        for (Portal portalItem : portalList) {
            if ((portalItem != this) && (portalItem.getPotalColour() == this.potalColour)) {
                return portalItem.getPosition();
            }
        }
        return getPosition();
    }

    public String getPotalColour() {
        return potalColour;
    }

}
