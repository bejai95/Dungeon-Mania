package dungeonmania;

import dungeonmania.util.Position;
import dungeonmania.util.Direction;

public class Boulder extends StaticEntity {

    //-----Data-----

    //-----Constructors-----
    public Boulder(int id, String type, Position position) {
        super(id, "boulder", position);
    }


    //-----Methods-----
    public void move (Direction direction){
        Position currpos = this.getPosition();
        Position newpos = currpos.translateBy(direction);
        if (StaticEntity.isCollision(newpos)) {
            return;
        } else {
            this.setPosition(newpos);

            //if it moves onto switch
            FloorSwitch pressedSwitch = FloorSwitch.isFloorSwitch(newpos);
            if (pressedSwitch != null) {
                pressedSwitch.setIsActive(true);
            }

            //if it moves off switch
            FloorSwitch unpressedSwitch = FloorSwitch.isFloorSwitch(currpos);
            if (unpressedSwitch != null) {
                unpressedSwitch.setIsActive(false);
            }
        }
    }

    //-----Getters and Setters-----
    
}
