package dungeonmania;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import dungeonmania.util.Position;
public class EntityDeserializerFromDungeon  implements JsonDeserializer<Entity> {
    
    @Override
    public Entity deserialize(JsonElement json, Type typeOfT, 
            JsonDeserializationContext context) throws JsonParseException {
        
        JsonObject jsonObject = json.getAsJsonObject();
        // Generate an Id for the new entity
        int newEntityId = Entity.getNumEntityIds();
        int x = jsonObject.get("x").getAsInt();
        int y = jsonObject.get("y").getAsInt();
            
        switch(jsonObject.get("type").getAsString()) {
            case "player":
                Position positionPlayer = new Position(x, y, 2);
                return new Character(newEntityId, "player", positionPlayer);
            case "wall":
                Position positionWall = new Position(x, y, 3);
                return new Wall(newEntityId, "wall", positionWall);
            case "exit":
                Position positionExit = new Position(x, y, 3);
                return new Exit(newEntityId, "exit", positionExit);
            case "boulder":
                Position positionBoulder = new Position(x, y, 3);
                return new Boulder(newEntityId, "boulder", positionBoulder);
            case "switch":
                Position positionSwitch = new Position(x, y, 3);
                return new FloorSwitch(newEntityId, "switch", positionSwitch);
            case "door":
                Position positionDoor = new Position(x, y, 3);
                // TODO logic in game that gets the matching key for the door
                return new Door(newEntityId, "door", positionDoor, null);
            case "portal":
                Position positionPortal = new Position(x, y, 3);
                // TODO logic in game that gets the matching portal
                return new Portal(newEntityId, "portal", positionPortal, null);
            case "zombie_toast_spawner":
                Position positionSpawner = new Position(x, y, 3);
                return new ZombieToastSpawner(newEntityId, "zombie_toast_spawner", positionSpawner);
            case "mercenary":
                Position positionMercenary = new Position(x, y, 3);
                // TODO get other logic from game, maybe adjust moving entity constructors to make more consistent
                return new Mercenary(0, 0, null, positionMercenary, 0, newEntityId, "mercenary");
            case "treasure":
                Position positionTreasure = new Position(x, y, 3);
                return new UnpickedUpItem(newEntityId, "treasure", positionTreasure, itemClass, itemid)
            
            

            
            
            
            
            }
        
        return null;
    }
}
