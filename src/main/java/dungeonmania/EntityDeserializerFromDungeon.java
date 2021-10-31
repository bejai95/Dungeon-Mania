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
        String type = jsonObject.get("type").getAsString();
        String key = null;
        String colour = null;

        
        if (jsonObject.has("key")) {
            key = jsonObject.get("key").getAsString();
        } else if (jsonObject.has("colour")) {
            colour = jsonObject.get("colour").getAsString();
        }

        EntityFactory factory = new EntityFactory();
        return factory.createEntity(newEntityId, type, x, y, key, colour);
        
    }
}
