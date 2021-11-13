package dungeonmania;

import java.util.List;

public class ExitGoal implements Goal{
    private Character getPlayer(List<Entity> entities){
        for(Entity entity : entities){
            if(entity instanceof Character){
                return (Character)entity;
            }
        }
        return null;
    } 
    
    @Override
    public String getGoalsLeft(List<Entity> entities) {
        System.out.println("starting");
        Character player = getPlayer(entities);
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
