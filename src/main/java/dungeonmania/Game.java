package dungeonmania;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import com.google.gson.annotations.SerializedName;

import org.json.JSONArray;
import org.json.JSONObject;

import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class Game {
    private String dungeonId;
    private String dungeonName;
    private List<Entity> entities;
    private int lastId = 0;
    private List<Item> inventory;
    private List<String> buildables;
    private int tickCounter; // Initialized to zero

    // private final List<AnimationQueue> animations;
    private String gameMode;
    private static int numDungeonIds; // Initialized to zero
    
    @SerializedName(value="goal", alternate="goal-condition")
    private Goal goal;
    
    private double mercenarySpawnChance = 0.05;
    private int spiderTicks = 10;
    
    public Game() {
    }

    public int getUniqueId(){
        int ret = lastId;
        lastId++;
        return ret;
    }
    public static int getNumDungeonIds() {
        return numDungeonIds;
    }

    public static void incrementNumDungeonIds() {
        numDungeonIds++;
    }

    public void initializeInventoryAndBuildables() {
        this.inventory = new ArrayList<Item>();
        this.buildables = new ArrayList<String>();
    }

    /*
    private String getGoalsLeft(JSONObject gs){
        switch(gs.getString("goal")){
            case "exit":
                return Exit.goalComplete(entities);
            case "boulder":
                return Switches.goalComplete(entities);
            case "enemies":
                return Enemies.goalComplete(entities);
            case "treasure":
                return Gold.goalComplete(entities);
            case "AND":
                String conj1 = getGoalsLeft(gs.getJSONArray("subgoals").getJSONObject(0));
                if(conj1.equals("")){
                    return getGoalsLeft(gs.getJSONArray("subgoals").getJSONObject(1));
                }
                String conj2 = getGoalsLeft(gs.getJSONArray("subgoals").getJSONObject(1));
                if(conj2.equals("")){
                    return conj1;
                }else{
                    return "(" + conj1 + " AND " + conj2 + ")"; 
                }
            case "OR":
                String disj1 = getGoalsLeft(gs.getJSONArray("subgoals").getJSONObject(0));
                if(disj1.equals("")){
                    return "";
                }
                String disj2 = getGoalsLeft(gs.getJSONArray("subgoals").getJSONObject(1));
                if(disj2.equals("")){
                    return "";
                }else{
                    return "(" + disj1 + " OR " + disj2 + ")";
                }
        }
        return null;
    }
    */
    /*
    public String getGoalsLeft() {
        return getGoalsLeft(goalCondition.getGoal());
    }
    */

    //Checks if a cell is empty
    public boolean isEmpty(Position cell) {
        for (int i = 0; i < entities.size(); i++) {
            if (cell.equals(entities.get(i).getPosition())) {
                return false;
            }
        }
        return true;
    }

    //Finds an adjacent empty cell when given a cell
    public Position getEmpty(Position centre) {
        List<Position> adjacentPositions = centre.getAdjacentPositions();
        Position emptyTile = null;
        for (int i = 0; i < adjacentPositions.size(); i++) {
            if (isEmpty(adjacentPositions.get(i))) {
                emptyTile = adjacentPositions.get(i);
            }
        }
        return emptyTile;
    }

    public String getDungeonId() {
        return dungeonId;
    }

    public void setDungeonId(String dungeonId) {
        this.dungeonId = dungeonId;
    }

    public String getDungeonName() {
        return dungeonName;
    }

    public void setDungeonName(String dungeonName) {
        this.dungeonName = dungeonName;
    }

    public List<Entity> getEntities() {
        return entities;
    }

    public Inventory getInventory() {
        return getPlayer().inventory;
    }


    public List<String> getBuildables() {
        return buildables;
    }

   public String getGoalsLeft() {
        if (goal == null)  {
            return null;
        } else {
            return goal.getGoalsLeft(entities);
        }
    }

    // Generate a dungeon response
    public DungeonResponse generateDungeonResponse() {
        return new DungeonResponse(dungeonId, dungeonName, null, null, buildables, getGoalsLeft()); //TODO fix this up later
    }

    public void setGameMode(String gameMode) {
        this.gameMode = gameMode;
    }

    private Character getPlayer(){
        for(Entity entity : entities){
            if(entity instanceof Character){
                return (Character) entity;
            }
        }
        return null;
    }

    private List<Mercenary> getMercenaries(){
        return null; //TODO
    }

    private List<MovingEntity> getMovingEntities(){
        return null; //TODO
    }

    private Consumable getConsumableFromId(String itemUsed) throws IllegalArgumentException{
        return null; //TODO

    }

    private List<ZombieToastSpawner> getSpawners(){
        return null; //TODO
    }

    private Position getSpawnPositionSpawner(ZombieToastSpawner spawner){
        return null; //TODO
    }

    private List<Wall> getWalls(){
        List<Wall> ret = new ArrayList<>();
        for(Entity entity : entities){
            if(entity instanceof Wall){
                ret.add((Wall)ret);
            }
        }
        return ret;
    }

    private boolean isBoundary(Wall wall, List<Wall> walls){
        boolean noLeft = true;
        boolean noRight = true;
        boolean noUp = true; 
        boolean noDown = true;
        
        Position pos = wall.getPosition();
        for(Wall w : walls){
            if(w.getPosition().getX() == pos.getX()){
                noUp = w.getPosition().getY() > pos.getY();
                noDown = w.getPosition().getY() < pos.getY();
            }

            if(w.getPosition().getY() == pos.getY()){
                noLeft = w.getPosition().getX() > pos.getX();
                noRight = w.getPosition().getX() < pos.getX();
            }
        }

        return (noLeft || noRight || noUp || noDown);
    }

    private List<Wall> getBoundaries(){
        List<Wall> walls = getWalls();
        List<Wall> walls2 = getWalls();
        return walls.stream().filter(x -> isBoundary(x, walls2)).collect(Collectors.toList());
    }

    private int getXMin(){
        return 0; //TODO
    }

    private int getXMax(){
        return 0; //TODO
    }

    private int getYMin(){
        return 0; //TODO
    }

    private int getYMax(){
        return 0; //TODO
    }

    private Position getRandomPosition(){
        Position pos = new Position(ThreadLocalRandom.current().nextInt(getXMin(), getXMax()), ThreadLocalRandom.current().nextInt(getYMin(), getYMax()));
        return pos;
    }

    private List<Wall> getBoundariesToRight(List<Wall> boundaries, Position pos){
        List<Wall> toRight = boundaries.stream().filter(x -> x.getPosition().getX() > pos.getX()).collect(Collectors.toList());
        return toRight;
    }
    
    private Position getSpawnPositionRandom(){
        Position pos = getRandomPosition();
        List<Wall> boundaries = getBoundaries();
        List<Wall> toRight = getBoundariesToRight(boundaries, pos);

        while(toRight.size() % 2 == 0 && !isEmpty(pos)){
            pos = new Position(ThreadLocalRandom.current().nextInt(getXMin(), getXMax()), ThreadLocalRandom.current().nextInt(getYMin(), getYMax()));
            toRight = getBoundariesToRight(boundaries, pos);
        }

        return pos;
    }

    /*
    private void spawnRandomEnemies(){
        Double roll = ThreadLocalRandom.current().nextDouble(0, 1);
        Position pos = null;

        
        if(roll < mercenarySpawnChance){
            pos = getSpawnPositionRandom();
            Mercenary merc = new Mercenary(getUniqueId(), pos, 1, getPlayer());
            entities.add(merc);
        }

        if(tickCounter % spiderTicks == 0){
            pos = getSpawnPositionRandom();
            MovingEntity spider = new Spider(getUniqueId(), pos, new SquareMovement());
            entities.add(spider);
        }

        return;
    }

    private MovingEntity getEntityOnPlayer(Character player){
        List<MovingEntity> ms = getMovingEntities();
        for(MovingEntity entity : ms){
            if(player.getPosition().equals(entity.getPosition())){
                return entity;
            }
        }
        return null;
    }

    private void removeDeadEntities(){
        List<MovingEntity> deadEnts = getMovingEntities().stream().filter(x -> x.health <= 0).collect(Collectors.toList());
        entities.removeAll(deadEnts);
    }

    public DungeonResponse tick(String itemUsed, Direction movementDirection) throws IllegalArgumentException, InvalidActionException {
        Character player = getPlayer();
        Inventory inventory = player.inventory;

        //use item
        player.use(getConsumableFromId(itemUsed));

        //remove dead items
        inventory.removeDeadItems();

        //move in direction
        player.move(movementDirection);

        //move all the mobs -- needs list of moving entities
        List<MovingEntity> movingEntities = getMovingEntities();
        for(MovingEntity mob : movingEntities){
            mob.move();
        }
        
        //spawn in enemies -- needs tick counter
        List<ZombieToastSpawner> spawners = getSpawners();
        for(ZombieToastSpawner spawner : spawners){
            ZombieToast zomb = spawner.spawn(tickCounter, gameMode, getSpawnPositionSpawner(spawner));
            if(zomb != null){
                entities.add(zomb);
            }
        }

        spawnRandomEnemies();


        resetMercSpeeds();
        //battle -- needs list of mercenaries, needs movingEntity on same tile as player

        //TODO - When battles work, will just call Battle(player, enemy, mercenaries)
        MovingEntity baddie = getEntityOnPlayer(player);
        if(baddie != null){
            BattleManager bat = new BattleManager(player, baddie, getMercenaries());
            List<Battleable> survivors = bat.battle();
            removeDeadEntities();
        }

        //display remaining goals and end game if there are none
        
        // TODO - Just merge in goals and call the method to display as string in goals, it should work fine

        //increment tick counter
        tickCounter++;
        DungeonResponse ret = new DungeonResponse(dungeonId, dungeonName, entities.stream().map(x -> x.getInfo()).collect(Collectors.toList()), inventory.getItemsAsResponse(), getInventory().getBuildables(), goal.getGoalsLeft(entities));
        return ret;
    }


    private void resetMercSpeeds() {
    }
    */
    
}
