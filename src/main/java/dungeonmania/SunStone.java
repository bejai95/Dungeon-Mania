package dungeonmania;

public class SunStone extends Item implements Material {
    public SunStone(int itemId) {
        super(itemId, 1, "sun_stone");
    }
    // can be used as a key to open doors
    //use on door and should still be there, takes priority over sun stone
    //use as if treasure for crafting
}