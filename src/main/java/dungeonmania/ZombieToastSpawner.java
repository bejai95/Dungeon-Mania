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
    public void spawn (Game game, int tickCounter, String gameMode) {
        
        if ((gameMode == "hard" && tickCounter == 15) || (gameMode != "hard" && tickCounter == 20)) {
            Position emptyTile = game.getEmpty(spawnerPosition);

            ZombieToast newZombieToast = new ZombieToast(120, 20, new RandomMovement(), emptyTile);
            //Add this to entity list check with Raph if there is a method for this
        } else {
            return;
        }

    }

    //-----Getters and Setters-----
}
