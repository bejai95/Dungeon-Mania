package dungeonmania;

import dungeonmania.util.Position;

public class Portal extends StaticEntity {
    //-----Data-----
    private Portal correspondingPortal;

    //-----Constructors-----
    public Portal(int id, String type, Position position, Portal correspondingPortal) {
        super(id, "potal", position);
        this.correspondingPortal = correspondingPortal;
    }

    public Portal(int id, String type, Position position) {
        super(id, "potal", position);
        this.correspondingPortal = null;
    }

    //-----Methods-----
    //Gets the location for where the caracter should teleport to 
    public Position getTeleportLocation(){
        Position exitPos = correspondingPortal.getPosition();
        return exitPos;
    }


    //-----Getters and Setters-----
    public Portal getCorrespondingPortal() {
        return correspondingPortal;
    }

    public void setCorrespondingPortal(Portal correspondingPortal) {
        this.correspondingPortal = correspondingPortal;
    }
}
