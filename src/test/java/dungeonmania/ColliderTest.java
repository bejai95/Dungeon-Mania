package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;
import dungeonmania.Game;

public class ColliderTest {
    
    @Test
    public void defaultCollider() {
        Game game = new Game("test", "advanced.json", "peaceful", "exit");
        Collider col = new Collider(game.getEntities());

        assert(col.isCollidable("wall") == true);
        assert(col.isCollidable("exit") == false);
        assert(col.isCollidable("boulder") == true);
        assert(col.isCollidable("floor_switch") == false);
        assert(col.isCollidable("closed_door") == true);
        assert(col.isCollidable("open_door") == false);
        assert(col.isCollidable("portal") == false);
        assert(col.isCollidable("zomvie_toast_spawner") == true);
        assert(col.isCollidable("spider") == false);
        assert(col.isCollidable("zombie_toast") == false);
        assert(col.isCollidable("mercenary") == false);
        assert(col.isCollidable("treasure") == false);
        assert(col.isCollidable("key") == false);
        assert(col.isCollidable("health_potion") == false);
        assert(col.isCollidable("invincibility_potion") == false);
        assert(col.isCollidable("invisibility_potion") == false);
        assert(col.isCollidable("wood") == false);
        assert(col.isCollidable("arrows") == false);
        assert(col.isCollidable("bomb") == false);
        assert(col.isCollidable("sword") == false);
        assert(col.isCollidable("armour") == false);
        assert(col.isCollidable("mercenary") == false);
        assert(col.isCollidable("one_ring") == false);
        assert(col.isCollidable("bow") == false);
        assert(col.isCollidable("shield") == false);
    }

    public void testSetUncollidable() {
        Game game = new Game("test", "advanced.json", "peaceful", "exit");
        Collider col = new Collider(game.getEntities());

        col.setUncollidable("wall");
        assert(col.isCollidable("wall") == false);

        col.setUncollidable("boulder");
        assert(col.isCollidable("boulder") == false);

        col.setUncollidable("key");
        assert(col.isCollidable("key") == false);

    }

    public void testSpiderCollider() {

    }

    public void testZombieCollider() {

    }

    public void testMercenaryCollider() {

    }


}
