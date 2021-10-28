package dungeonmania;

import java.util.List;

public class GoalCondition {
    private String goal;
    private List<GoalCondition> subgoals;
    public GoalCondition() {
    }

    public String getGoal() {
        return goal;
    }

    public void setGoal(String goal) {
        this.goal = goal;
    }

    
}
