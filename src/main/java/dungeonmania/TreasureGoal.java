package dungeonmania;

import java.util.List;

public class TreasureGoal implements Goal{
    @Override
    public String getGoalsLeft(List<Entity> entities) {
        for(Entity entity : entities){
            if(entity instanceof UnpickedUpItem){
                UnpickedUpItem item = (UnpickedUpItem) entity;
                if(item.getItemClass().equals("Treasure")){
                    return ":treasure";
                }
            }
        }
        return "";
    }

}