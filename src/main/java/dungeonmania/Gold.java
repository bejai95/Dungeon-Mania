package dungeonmania;

import java.util.List;

public class Gold{
    /**
     * Returns whether or not this goal has been completed in string form
     * as specified below
     * @return "" if the goal has been completed and ":treasure" if not
     */
    public static String goalComplete(List<Entity> entities) {
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