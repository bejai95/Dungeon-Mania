package dungeonmania;

import dungeonmania.util.Position;

public class ZombieToastSpawner extends StaticEntity {
    //-----Data-----
    private Position spawnerPosition = getPosition();

    //-----Constructors-----
    public ZombieToastSpawner(int id, String type, Position position) {
        super(id, "zombie_toast_spawner", position);
    }

    //-----Methods-----
    public ZombieToast spawn (int tickCounter, String gameMode, Position emptyTile ) {
        
        if ((gameMode == "hard" && tickCounter == 15) || (gameMode != "hard" && tickCounter == 20)) {
            ZombieToast newZombieToast = new ZombieToast(120, 20, new RandomMovement(), emptyTile);
            return newZombieToast;
        } else {
            return null;
        }

    }

    //-----Getters and Setters-----
}
