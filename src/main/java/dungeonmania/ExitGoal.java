package dungeonmania;

import java.util.List;

public class ExitGoal implements Goal{
    @Override
    public String getGoalsLeft(List<Entity> entities) {
        System.out.println("starting");
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
