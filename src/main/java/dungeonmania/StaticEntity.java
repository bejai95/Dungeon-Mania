package dungeonmania;

import dungeonmania.util.Position;

import java.util.ArrayList;
import java.util.List;

public class StaticEntity extends Entity {

    //-----Data-----
    private static List<StaticEntity> staticEntitiesList = new ArrayList<StaticEntity>();

    //-----Constructors-----
    public StaticEntity(int id, String type, Position position) {
        super(id, type, position);
        staticEntitiesList.add(this);
    }
    //-----Methods-----
    //Removes the current instance of a static entities from the static entities list 
    public void removeStaticEntity (){
        staticEntitiesList.remove(this);
    }


    //Checks to see if a collision with a static entity would occur at a given position
    public static boolean isCollision(Position cell){
        for (StaticEntity staticEntityItem : staticEntitiesList) {
            if (staticEntityItem.getPosition().equals(cell) && staticEntityItem.getPosition().getLayer() == cell.getLayer()) {
                return true;
            }
        }
        return false;
    }

    //-----Getters and Setters-----
    public static List<StaticEntity> getStaticEntitiesList() {
        return staticEntitiesList;
    }
}
  