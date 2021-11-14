package dungeonmania;

import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.util.Position;
import java.util.ArrayList;
import java.util.List;

public class ZombieToastSpawner extends StaticEntity {
    //-----Data-----

    //-----Constructors-----
    public ZombieToastSpawner(int id, Position position) {
        super(id, "zombie_toast_spawner", position);
        this.setIsInteractable(true);
    }

    //-----Methods-----


    /**
     * Player interacting with spawner destroys spawner if they have weapon
     */
    public void interact(Character ch) throws InvalidActionException {
        if (!Position.isAdjacent(this.getPosition(), ch.getPosition())) {
            throw new InvalidActionException("Player is not adjacent to spawner");
        } else if (ch.getInventory().getSwords().isEmpty()) {
            throw new InvalidActionException("Player does not have a sword");
        }

        destroy();        

    }

    //-----Getters and Setters-----
}
