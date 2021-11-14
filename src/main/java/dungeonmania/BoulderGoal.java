package dungeonmania;

import java.util.ArrayList;
import java.util.List;

public class BoulderGoal implements Goal{
    /**
     * Gets a list of all the boulders in a list of entities
     * @param entities
     * @return
     */
    private List<Entity> getBoulders(List<Entity> entities){
        List<Entity> ret = new ArrayList<Entity>();
        for(Entity entity : entities){
            if(entity instanceof Boulder){
                ret.add(entity);
            }
        }
        return ret;
    }
    /**
     * Gets a list of all the switches in a list of entities
     * @param entities
     * @return
     */
    private List<FloorSwitch> getSwitches(List<Entity> entities){
        List<FloorSwitch> ret = new ArrayList<FloorSwitch>();
        for(Entity entity : entities){
            if(entity instanceof FloorSwitch){
                ret.add((FloorSwitch)entity);
            }
        }
        return ret;
    }
    
    /**
     * Tells you whether thereb exists an element in entities with the
     * same position as e
     * @param e
     * @param entities
     * @return
     */
    private boolean existsSamePosition(Entity e, List<Entity> entities){
        for(Entity entity : entities){
            if(e.getPosition().equals(entity.getPosition())){
                return true;
            }
        }
        return false;
    }
    /**
     * @return the goals left to do
     */
    @Override
    public String getGoalsLeft(List<Entity> entities) {
        List<Entity> boulders = getBoulders(entities);
        List<FloorSwitch> switches = getSwitches(entities);

        for(FloorSwitch s : switches){
            if(!existsSamePosition(s, boulders)){
                return ":switch";
            }
        }

        return "";
    }
}
