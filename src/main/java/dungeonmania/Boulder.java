package dungeonmania;

import dungeonmania.util.Position;
import dungeonmania.util.Direction;

public class Boulder extends StaticEntity {

    //-----Data-----

    //-----Constructors-----
    public Boulder(int id, Position position) {
        super(id, "boulder", position);
        this.setIsInteractable(true);
    }

    //-----Methods-----

    //-----Getters and Setters-----
    
}
