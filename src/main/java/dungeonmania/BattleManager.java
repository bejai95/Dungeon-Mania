package dungeonmania;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BattleManager {
    
    /**
     * Does a single battle instance between a player's ally and
     * the enemy
     * @param goodie
     * @param baddie
     * @return
     */
    private List<Battleable> battleInstance(Battleable goodie, Battleable baddie){
        List<Battleable> ret = new ArrayList<>();
        ret.add(goodie);
        ret.add(baddie);
        
        goodie.setHealth(((goodie.getHealth() - (baddie.getHealth() * baddie.getDamage()))/10)*goodie.getDefenceMultiplier());
        baddie.setHealth(((baddie.getHealth() - (goodie.getHealth() * goodie.getDamage()))/5)*baddie.getDefenceMultiplier());
        
        return ret.stream().filter(x -> x.getHealth() > 0).collect(Collectors.toList());
    }
    
    /**
     * Starts a battle instance for the player and a moving entity
     * which ends when one of the entites dies.
     * 
     * This function just makes the player and an enemy fight, checking whether
     * the entities are on the same tile must be handled elsewhere
     * @param player
     * @param baddie
     * @return
     */
    public static Battleable battle(Character player, Battleable baddie){        
        List<Battleable> allies = new ArrayList<>();
        allies.add(player);
        allies.addAll(player.getAllies());
        
        for(Battleable ally : allies){

        }
        while(player.getHealth() >= 0 && baddie.getHealth() >= 0){
            player.setHealth((player.getHealth() - (baddie.getHealth() * baddie.getDamage()))/10);
            baddie.setHealth((baddie.getHealth() - (player.getHealth() * player.getDamage()))/5);
        }

        if(player.getHealth() < 0){
            return player;
        }
        return baddie;
    }

    
}
