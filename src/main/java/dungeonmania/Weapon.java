package dungeonmania;
import java.util.List;

public interface Weapon {
    public int getDamage();
    public int getAmountOfAttacks();
    /**
     * 
     * @return List: Index 0 = attack damage, Index 1 = amount of attacks
     */
    public List<Integer> getWeaponInfo();
}
