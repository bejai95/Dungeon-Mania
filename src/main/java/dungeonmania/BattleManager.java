package dungeonmania;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class BattleManager {
    
    private Character player;
    private MovingEntity baddie;
    private List<Mercenary> mercenaries;

    public BattleManager(Character player, MovingEntity baddie, List<Mercenary> mercenaries){
        this.player = player;
        this.baddie = baddie;
        this.mercenaries = mercenaries;
    }
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
        
        goodie.setHealth((goodie.getHealth() - (baddie.getHealth() * baddie.getDamage())/10)*goodie.getDefenseMultiplier());
        baddie.setHealth((baddie.getHealth() - (goodie.getHealth() * goodie.getDamage())/5)*baddie.getDefenseMultiplier());
        
        return ret.stream().filter(x -> x.getHealth() <= 0).collect(Collectors.toList());
    }
    
    private List<Mercenary> getAllies(List<Mercenary> mercs){
        return null; //TODO
    }

    private List<Mercenary> getEnemies(List<Mercenary> mercs){
        return null; //TODO
    }


    private List<Mercenary> getEnemiesInRange(List<Mercenary> mercs, Character player){
        return null; //TODO
    }

    private List<Mercenary> getAlliesInRange(){
        return mercenaries.stream().filter(x -> !x.isHostile && x.entityInRadius(player)).collect(Collectors.toList()); 
    }

    private List<Mercenary> getMercsInRange(){
        return mercenaries.stream().filter(x -> x.entityInRadius(player)).collect(Collectors.toList());
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
    public List<Battleable> battle(){        
        List<Battleable> alive = new ArrayList<>();
        List<Mercenary> allies = getAlliesInRange();
        System.out.print(allies.size());
        alive.add(player);
        alive.add(baddie);
        alive.addAll(allies);
        List<Mercenary> mercsInRange = getMercsInRange();
        for(Mercenary merc : mercsInRange){
            merc.doubleSpeed();
            //Remember to undo this in the tick function after everyone moves
        }

        while(player.getHealth() > 0 && baddie.getHealth() > 0){
            alive.removeAll(battleInstance(player, baddie));
            for(Mercenary merc : allies){
                if(!alive.contains(baddie)){
                    break;
                }
                alive.removeAll(battleInstance(merc, baddie));
            }
        }

        return alive;
    }

    
}
