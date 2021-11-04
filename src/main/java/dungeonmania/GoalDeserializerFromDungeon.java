package dungeonmania;

import com.google.gson.JsonDeserializationContext;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class GoalDeserializerFromDungeon implements JsonDeserializer<Goal> {
    
    @Override
    public Goal deserialize(JsonElement json, Type typeOfT, 
            JsonDeserializationContext context) throws JsonParseException {
        
        JsonObject jsonObject = json.getAsJsonObject();
        String goalType = jsonObject.get("goal").getAsString();

        if (goalType.equals("AND") || goalType.equals("OR")) {
            JsonArray JSONSubgoals = jsonObject.get("subgoals").getAsJsonArray();
            List<Goal> subgoals = new ArrayList<Goal>();

            for (JsonElement curr : JSONSubgoals) { 
                Goal subgoal = deserialize(curr, typeOfT, context);
                subgoals.add(subgoal);
            }

            if (goalType.equals("AND")) {
                return new AndGoal(subgoals);
            } else {
                return new OrGoal(subgoals);
            }
        }

        switch (goalType) {
            case "exit":
                return new ExitGoal();
            case "enemies":
                return new EnemiesGoal();
            case "boulders":
                return new BoulderGoal();
            case "treasure":
                return new TreasureGoal();
        }

        return null;
    }
}
