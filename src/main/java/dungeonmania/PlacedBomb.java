package dungeonmania;

import dungeonmania.util.Position;
import java.util.ArrayList;
import java.util.List;

public class PlacedBomb extends StaticEntity {


    //-----Data-----

    //-----Constructors-----
    public PlacedBomb(int id, String type, Position position) {
        super(id, "bomb", position);
        this.setIsInteractable(false);
    }

    
    //-----Methods-----



    //-----Getters and Setters-----

}
