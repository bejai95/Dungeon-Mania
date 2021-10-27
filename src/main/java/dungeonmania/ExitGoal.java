package dungeonmania;

import java.util.List;

public class ExitGoal {
    /**
     * Returns whether or not this goal has been completed in string form
     * as specified below
     * @return "" if the goal has been completed and ":exit" if not
     */
    public static String goalComplete(List<Entity> entities) {
        Entity player = entities.get(0);
        for(Entity entity : entities){
            if(entity instanceof Exit){
                if(player.getPosition().equals(entity.getPosition())){
                    return "";
                }
            }
        }
        return ":exit";
    }
}
