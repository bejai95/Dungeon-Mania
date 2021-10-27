package dungeonmania;

import java.util.ArrayList;
import java.util.List;

public class Switches {
    private static List<Entity> getBoulders(List<Entity> entities){
        List<Entity> ret = new ArrayList<Entity>();
        for(Entity entity : entities){
            if(entity instanceof Boulder){
                ret.add(entity);
            }
        }
        return ret;
    }

    private static List<Entity> getSwitches(List<Entity> entities){
        List<Entity> ret = new ArrayList<Entity>();
        for(Entity entity : entities){
            if(entity instanceof Switch){
                ret.add(entity);
            }
        }
        return ret;
    }
    

    private static boolean existsSamePosition(Entity e, List<Entity> entities){
        for(Entity entity : entities){
            if(e.getPosition().equals(entity.getPosition())){
                return true;
            }
        }
        return false;
    }
    /**
     * Returns whether or not this goal has been completed in string form
     * as specified below
     * @return "" if the goal has been completed and ":switch" if not
     */
    public static String goalComplete(List<Entity> entities) {
        List<Entity> boulders = getBoulders(entities);
        List<Entity> switches = getSwitches(entities);

        for(Switch s : switches){
            if(!existsSamePosition(s, boulders)){
                return ":switch";
            }
        }

        return "";
    }
}
