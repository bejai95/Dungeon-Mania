package dungeonmania;

import dungeonmania.util.Position;
import java.util.ArrayList;
import java.util.List;

public class ZombieToastSpawner extends StaticEntity {
    //-----Data-----
    private Position spawnerPosition = getPosition();

    //-----Constructors-----
    public ZombieToastSpawner(int id, String type, Position position) {
        super(id, "zombie_toast_spawner", position);
    }

    //-----Methods-----
    /*Will spawn a zombie toast on an adjacent open tile
     and return the newly created zombie toast or null */
    public ZombieToast spawn (int tickCounter, String gameMode) {
        //Checks to see if spawn conditions are met
        if ((gameMode == "hard" && tickCounter % 15 == 0) || (gameMode != "hard" && tickCounter % 20 == 0)) {
            //List of adjacent positions around spawner
            List<Position> adjacentPositions = spawnerPosition.getAdjacentPositions();

            //Sets the deffault spawn positions as on top of the spawner
            Position spawnPoint = spawnerPosition;

            //Find an adjacent open tile for the zombie toast to spawn
            for (Position edgeCell : adjacentPositions) {
                if (StaticEntity.isCollision(edgeCell)) {
                    ;
                } else {
                    spawnPoint = edgeCell;
                    break;
                }
            }

            //Create new zombie toast
            ZombieToast newZombieToast = new ZombieToast(120, spawnPoint, new RandomMovement());
            return newZombieToast;
        } else {
            return null;
        }

    }

    //-----Getters and Setters-----
}
