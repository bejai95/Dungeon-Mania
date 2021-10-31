package dungeonmania;

import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Position;

public abstract class Entity{
    private int id;
    private String type;
    private Position position;
    // TODO interactability
    private static int numEntityIds; // Initialized to zero

    public Entity() {}

    public Entity(int id, String type, Position position) {
        this.id = id;
        this.type = type;
        this.position = position;
        numEntityIds++;
    }

    public int getId() {
        return this.id;
    }

    public String getType() {
        return this.type;
    }

    public Position getPosition() {
        return this.position;
    }

    public EntityResponse getInfo() {
        //return new EntityResponse(id, type, position, isInteractable);
        return null;
    }

    public static int getNumEntityIds() {
        return numEntityIds;
    }

    public void setPosition(Position position) {
        this.position = position;
    }


    
}
