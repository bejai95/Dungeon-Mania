package dungeonmania;

import dungeonmania.util.Position;

public class SwampTile extends StaticEntity {

    //-----Data-----
    private double movementFactor;
    //-----Constructors-----
    public SwampTile(int id, Position position, double movementFactor) {
        super(id, "wall", position);
        this.movementFactor = movementFactor;
    }
    public Double getMovementFactor() {
        return movementFactor;
    }

    //-----Methods-----
    //-----Getters and Setters-----
    
}
