package dungeonmania;

import java.util.List;

public interface Goal {
    /**
     * Returns whether or not this goal has been completed in string form
     * as specified below
     * @return "" if the goal has been completed and the goal in string form, e.g. ":exit" if not
     */
    public String getGoalsLeft(List<Entity> entities);
}
