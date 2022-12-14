package dungeonmania;

import java.util.List;
import java.util.stream.Collectors;

public class AndGoal implements Goal{
    private List<Goal> subgoals;

    public AndGoal(List<Goal> subgoals){
        this.subgoals = subgoals;
    }
    
    /**
     * Helper function which combines a listm of strings by
     * concattenating and placing AND between all elements
     * @param gs
     * @return goals left
     */
    private String andifiy(List<String> gs){
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
                ret = ret + g + " AND ";
            }
        }
        return ret;
    }
    /**
     * Gets the goals left by calling the andify on subgoals
     */
    @Override
    public String getGoalsLeft(List<Entity> entities) {
        List<String> subgoalStrings = subgoals.stream().map(x -> x.getGoalsLeft(entities)).filter(x -> !x.equals("")).collect(Collectors.toList());
        return andifiy(subgoalStrings);
    }

    
}
