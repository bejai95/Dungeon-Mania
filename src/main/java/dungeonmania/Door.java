package dungeonmania;


import dungeonmania.util.Position;

public class Door extends StaticEntity {
    //-----Data-----
    private Boolean isOpen;
    private int matchingKeyNum;

    //-----Constructors-----
    public Door(int id, Position position, int matchingKeyNum) {
        super(id, "door", position);
        this.isOpen = false;
        this.matchingKeyNum = matchingKeyNum;
        this.setIsInteractable(true);
    }

    //-----Methods-----
    //This will open a door and will return true if the door is sucessfully opened 
    public boolean openDoor (Key inputKey){
        if (inputKey.getKeyNum() == matchingKeyNum) {
            //Open the door
            this.isOpen = true;
            //Moves the door down to the bottom layer so the player can walk over it
            Position currentPos = this.getPosition();
            this.setPosition(currentPos.asLayer(0));
            //Use the key item
            inputKey.use();
            return true;
        }
        return false;
    }

    //-----Getters and Setters-----
    public Boolean getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(Boolean isOpen) {
        this.isOpen = isOpen;
    }
}
