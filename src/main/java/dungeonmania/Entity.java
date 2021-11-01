package dungeonmania;

import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Position;



public abstract class Entity{
    private int id;
    private String type;
    private Position position;
    private boolean isInteractable;
    private static int numEntityIds; // Initialized to zero

    public Entity() {}

    public Entity(int id, String type, Position position) {
        this.id = id;
        this.type = type;
        this.position = position;
        numEntityIds++;
    }

    /**
     * Return whether ent1 is able to walk over ent2
     * @param ent1
     * @param ent2
     * @return
     */
    public static boolean canOverlap(Entity ent1, Entity ent2) {
        return ent1.getPosition().getLayer() <= ent2.getPosition().getLayer();
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
        return new EntityResponse(String.valueOf(id), type, position, isInteractable);
    }

    public static int getNumEntityIds() {
        return numEntityIds;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public void setIsInteractable(boolean canInteract) {
        this.isInteractable = canInteract;
    }
    
}
