package dungeonmania;

import java.util.List;

public class EnemiesGoal implements Goal{
    @Override
    public String getGoalsLeft(List<Entity> entities) {
        for(Entity entity : entities){
            if(entity instanceof MovingEntity){
                return ":mercenary";
            }
        }
        return "";
    }

}