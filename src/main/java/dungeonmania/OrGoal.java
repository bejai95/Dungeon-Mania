package dungeonmania;

import java.util.List;
import java.util.stream.Collectors;

public class OrGoal implements Goal{
    private List<Goal> subgoals;

    public OrGoal(List<Goal> subgoals){
        this.subgoals = subgoals;
    }

    private String orifiy(List<String> gs){
        if(gs.size() == 0){
            return "";
        }

        if(gs.size() == 1){
            return gs.get(0);
        }
        
        String ret = "(";
        for(String g : gs){
            if(gs.get(gs.size() - 1).equals(g)){
                ret = ret + g + ")";
            }else{
                ret = ret + g + " OR ";
            }
        }
        return ret;
    }
    @Override
    public String getGoalsLeft(List<Entity> entities) {
        List<String> subgoalStrings = subgoals.stream().map(x -> x.getGoalsLeft(entities)).collect(Collectors.toList());
        if(subgoalStrings.stream().anyMatch(x -> x.equals(""))){
            return "";
        }
        return orifiy(subgoalStrings);
    }
}
