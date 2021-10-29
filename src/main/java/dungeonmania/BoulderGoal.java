package dungeonmania;

import java.util.ArrayList;
import java.util.List;

public class BoulderGoal implements Goal{
    private List<Entity> getBoulders(List<Entity> entities){
        List<Entity> ret = new ArrayList<Entity>();
        for(Entity entity : entities){
            if(entity instanceof Boulder){
                ret.add(entity);
            }
        }
        return ret;
    }

    private List<Entity> getSwitches(List<Entity> entities){
        List<Entity> ret = new ArrayList<Entity>();
        for(Entity entity : entities){
            if(entity instanceof Switch){
                ret.add(entity);
            }
        }
        return ret;
    }
    

    private boolean existsSamePosition(Entity e, List<Entity> entities){
        for(Entity entity : entities){
            if(e.getPosition().equals(entity.getPosition())){
                return true;
            }
        }
        return false;
    }
    
    @Override
    public String getGoalsLeft(List<Entity> entities) {
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
