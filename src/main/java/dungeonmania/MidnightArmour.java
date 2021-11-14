package dungeonmania;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class MidnightArmour extends Item implements Weapon, DefenseItem {
    public MidnightArmour(int itemId) {
        //uses are doubles since each time a battle is called it gets used once for attacking and once for defending
        super(itemId, 4, "midnight_armour");
        this.setUses(this.getUses() * 2);
    }
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
    public int getAmountOfAttacks() {
        return 1;
    }
    public int getDamage() {
        return 10;
    }
    public double getMultipler() {
        //has uses
        if (canUse()) {
            use();
            return 0.33;
        }
        //act as if item does not exist
        else {
            return 0;
        }
    }
}
