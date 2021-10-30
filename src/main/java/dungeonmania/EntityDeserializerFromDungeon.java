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
            
        switch(jsonObject.get("type").getAsString()) {
            case "wall":
                Position positionWall = new Position(jsonObject.get("x").getAsInt(), jsonObject.get("y").getAsInt(), 3);
                return new Wall(newEntityId, "wall", positionWall);
            case "player":
                Position positionPlayer = new Position(jsonObject.get("x").getAsInt(), jsonObject.get("y").getAsInt(), 2);
                return new Character(newEntityId, "player", positionPlayer);
            }
        
        return null;
    }
}
