package dungeonmania;

import dungeonmania.util.Position;

public class Wall  extends StaticEntity {

    //-----Data-----
    //-----Constructors-----
    public Wall(int id, Position position) {
        super(id, "wall", position);
        this.setIsInteractable(false);
    }

    //-----Methods-----
    //-----Getters and Setters-----
}

