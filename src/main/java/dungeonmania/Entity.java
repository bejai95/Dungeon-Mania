package dungeonmania;

import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Position;

public class Entity {
    int id;
    String type;
    Position position;

    public Entity(int id, String type, int x, int y) {
        this.id = id;
        this.type = type;
        position = new Position(x, y); // decide what default layer of entity is
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
}
