package dungeonmania;

import dungeonmania.util.Position;

public class Door extends StaticEntity {
    //-----Data-----
    private Boolean isOpen;
    private int matchingKeyNum;

    //-----Constructors-----
    public Door(int id, String type, Position position, int matchingKeyNum) {
        super(id, "door", position);
        this.isOpen = false;
        this.matchingKeyNum = matchingKeyNum;
    }

    //-----Methods-----
    public void openDoor (Key inputKey){
        if (inputKey.getKeyNum() == matchingKeyNum) {
            this.isOpen = true;
            inputKey.use();
        }
    }

    //-----Getters and Setters-----
    public Boolean getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(Boolean isOpen) {
        this.isOpen = isOpen;
    }
}
