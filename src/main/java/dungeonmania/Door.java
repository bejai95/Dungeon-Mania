package dungeonmania;

import dungeonmania.util.Position;

public class Door extends StaticEntity {
    //-----Data-----
    private Boolean isOpen;
    private Key matchingKey;

    //-----Constructors-----
    public Door(int id, String type, Position position, Key matchingKey) {
        super(id, "door", position);
        this.isOpen = false;
        this.matchingKey = matchingKey;
    }
    //-----Methods-----



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
