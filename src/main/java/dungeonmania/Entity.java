package dungeonmania;

import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Position;



public abstract class Entity implements interaction {
    private int id;
    private String type;
    private Position position;
    private boolean isInteractable;

    public Entity(int id, String type, Position position) {
        this.id = id;
        this.type = type;
        this.position = position;
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

    public boolean canInteract() {
        return this.isInteractable;
    }

    public EntityResponse getInfo() {
        return new EntityResponse(String.valueOf(id), type, position, isInteractable);
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public void setIsInteractable(boolean canInteract) {
        this.isInteractable = canInteract;
    }
    
    public void interact(Character ch) {
        // no interaction by default, subclasses implement unique interactions
    }

    public void setType(String type) {
        this.type = type;
    }

}
