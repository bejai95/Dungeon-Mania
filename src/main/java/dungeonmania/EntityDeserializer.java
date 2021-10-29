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

public class EntityDeserializer  implements JsonDeserializer<List<Entity>> {
    
    @Override
    public List<Entity> deserialize(JsonElement json, Type typeOfT, 
            JsonDeserializationContext context) throws JsonParseException {
        
        List<Entity> list = new ArrayList<Entity>();
        
        for (JsonElement current : json.getAsJsonArray()) {
            
            JsonObject currObject = current.getAsJsonObject();

            // Generate an Id for the new entity
            int newEntityId = Entity.getNumEntityIds();
            
            switch(currObject.get("type").getAsString()) {
                case "wall":
                    Position positionWall = new Position(currObject.get("x").getAsInt(), currObject.get("y").getAsInt(), 3);
                    list.add(new Wall(newEntityId, "wall", positionWall));
                    break;
                case "player":
                    Position positionPlayer = new Position(currObject.get("x").getAsInt(), currObject.get("y").getAsInt(), 2);
                    list.add(new Character(newEntityId, "player", positionPlayer));
                    break;
            }
        }
        
        
        return list;    
    }
}
