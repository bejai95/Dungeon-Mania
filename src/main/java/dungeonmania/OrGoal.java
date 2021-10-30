package dungeonmania;

import java.util.List;

public class OrGoal implements Goal{
    private List<Goal> subgoals;

    public OrGoal(List<Goal> subgoals){
        this.subgoals = subgoals;
    }
    @Override
    public String getGoalsLeft(List<Entity> entities) {
        Goal subgoal1 = subgoals.get(0);
        Goal subgoal2 = subgoals.get(1);
        String disj1 = subgoal1.getGoalsLeft(entities);
        if(disj1.equals("")){
            return "";
        }
        String disj2 = subgoal2.getGoalsLeft(entities);
        if(disj2.equals("")){
            return "";
        }else{
            return "(" + disj1 + " OR " + disj2 + ")"; 
        }
    }
}
