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
    //This will move the boulder when given a direction for it to move
    public void move (Direction direction){
        //Gets the current and next position
        Position currpos = this.getPosition();
        Position newpos = currpos.translateBy(direction);

        //This will check if the next position would be a collision
        if (StaticEntity.isCollision(newpos)) {
            return;
        } else {
            //If no collision it will move
            this.setPosition(newpos);

            //Activates the switch if moved onto a switch
            FloorSwitch potentialPressedSwitch = FloorSwitch.isFloorSwitch(newpos);
            if (potentialPressedSwitch != null) {
                potentialPressedSwitch.setIsActive(true);
            }

            //Deactivates a switch if it moves off a switch
            FloorSwitch potentialUnpressedSwitch = FloorSwitch.isFloorSwitch(currpos);
            if (potentialUnpressedSwitch != null) {
                potentialUnpressedSwitch.setIsActive(false);
            }
        }
    }

    //-----Getters and Setters-----
    
}
