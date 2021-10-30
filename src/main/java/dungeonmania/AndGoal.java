package dungeonmania;

import java.util.List;

public class AndGoal implements Goal{
    private List<Goal> subgoals;

    public AndGoal(List<Goal> subgoals){
        this.subgoals = subgoals;
    }
    @Override
    public String getGoalsLeft(List<Entity> entities) {
        Goal subgoal1 = subgoals.get(0);
        Goal subgoal2 = subgoals.get(1);
        String conj1 = subgoal1.getGoalsLeft(entities);
        if(conj1.equals("")){
            return subgoal2.getGoalsLeft(entities);
        }
        String conj2 = subgoal2.getGoalsLeft(entities);
        if(conj2.equals("")){
            return conj1;
        }else{
            return "(" + conj1 + " AND " + conj2 + ")"; 
        }
    }

    
}
