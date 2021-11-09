package dungeonmania;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class Bow extends Item implements Weapon {
    /**
     * Constructor sets uses and the amount of times the bow will attack/turn
     * @param itemId
     */
    public Bow(int itemId) {
        super(itemId, 3, "bow");
    }
    /**
     * 
     * @return amount of attacks/turn
     */
    public int getAmountOfAttacks() {
        return 2;
    }
    public int getDamage() {
        return 0;
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
