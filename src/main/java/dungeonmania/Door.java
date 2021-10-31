package dungeonmania;

import dungeonmania.util.Position;

public class Door extends StaticEntity {
    //-----Data-----
    private Boolean isOpen;
    private int matchingKeyid;

    //-----Constructors-----
    public Door(int id, String type, Position position, int matchingKeyid) {
        super(id, "door", position);
        this.isOpen = false;
        this.matchingKeyid = matchingKeyid;
    }
    //-----Methods-----
    public void openDoor (Key inputKey){
        if (matchingKeyid == inputKey.getitemId()) {
            this.isOpen = true;
        }
    }

    //-----Getters and Setters-----
    public Boolean getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(Boolean isOpen) {
        this.isOpen = isOpen;
    }

    public Key getMatchingKey() {
        return matchingKey;
    }

    public void setMatchingKey(Key matchingKey) {
        this.matchingKey = matchingKey;
    }

    
}
