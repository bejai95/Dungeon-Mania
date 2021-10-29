package dungeonmania;

import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Position;

public class Entity {
    private int id;
    private String type;
    private Position position;
    private static int numEntityIds; // Initialized to zero

    public Entity(int id, String type, int x, int y, int layer) {
        this.id = id;
        this.type = type;
        position = new Position(x, y, layer);
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
        return null;
    }

    public static int getNumEntityIds() {
        return numEntityIds;
    }

    
}
