package dungeonmania;

import dungeonmania.util.Position;

    public abstract class StaticEntity extends Entity {

    //-----Data-----

    private boolean destroyThisTick;

    //-----Constructors-----
    public StaticEntity(int id, String type, Position position) {
        super(id, type, position);
        destroyThisTick = false;
    }
    //-----Methods-----

    //-----Getters and Setters-----

    public boolean isDestroyed() {
        return destroyThisTick;
    }

    public void destroy() {
        destroyThisTick = true;
    }

}
  