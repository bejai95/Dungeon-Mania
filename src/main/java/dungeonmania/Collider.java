package dungeonmania;

import java.util.HashMap;
import java.util.List;

public class Collider {
    
    HashMap<String, Boolean> collidableTypes = new HashMap<>();

    public Collider(List<Entity> entities) {
        initialiseMap(entities);
    }

    public boolean isCollidable(String type) {
        return false;
    }

    /**
     * Put each entity type in map with default value
     * @param entities
     */
    public void initialiseMap(List<Entity> entities) {

    }

    /**
     * Set the value of the given type to false in hashmap
     * @param types
     */
    public void setUncollidable(String type) {

    }

}
