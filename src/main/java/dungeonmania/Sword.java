package dungeonmania;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class Sword extends Item implements Weapon {
    /**
     * Sword constructor sets the amount of damage the sword should do
     * @param itemId
     */
    public Sword(int itemId) {
        super(itemId, 3, "sword");
    }
    /**
     * reduces durability of sword
     */
    public int getDamage() {
        return 25;
    }
    public int getAmountOfAttacks() {
        //does not add to the amount of extra attacks you can do
        return 1;
    }
    /**
     * 
     * @return List: Index 0 = attack damage, Index 1 = amount of attacks
     */
    public List<Integer> getWeaponInfo() {
        //create an array list which will just return 0,0 if not being used
        List<Integer> weaponInfo = new ArrayList<>(Arrays.asList(0,0));
        if (canUse()) {
            weaponInfo.set(0, this.getDamage());
            weaponInfo.set(1, this.getAmountOfAttacks());
            use();
        }
        //can't use and just return 0,0
        return weaponInfo;
    }
}
