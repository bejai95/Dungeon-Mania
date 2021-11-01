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

    public static int getNumDungeonIds() {
        return numDungeonIds;
    }

    public static void incrementNumDungeonIds() {
        numDungeonIds++;
    }

    public void initialiseBuildables() {
        this.buildables = new ArrayList<String>();
    }

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

    public Character getPlayer(){
        for(Entity entity : entities){
            if(entity instanceof Character){
                return (Character) entity;
            }
        }
        return null;
    }

    private List<Mercenary> getMercenaries(){
        List<Mercenary> ret = new ArrayList<>();
        for(Entity entity : entities){
            if(entity instanceof Mercenary){
                ret.add((Mercenary) entity);
            }
        }
        return ret;
    }

    private List<MovingEntity> getMovingEntities(){
        List<MovingEntity> ret = new ArrayList<>();
        for(Entity entity : entities){
            if(entity instanceof MovingEntity){
                ret.add((MovingEntity) entity);
            }
        }
        return ret;
    }

    private List<StaticEntity> getStaticEntities(){
        List<StaticEntity> ret = new ArrayList<>();
        for(Entity entity : entities){
            if(entity instanceof StaticEntity){
                ret.add((StaticEntity) entity);
            }
        }
        return ret;
    }

    private Consumable getConsumableFromId(String itemUsed) throws IllegalArgumentException{
        return null; //TODO

    }

    private List<ZombieToastSpawner> getSpawners(){
        List<ZombieToastSpawner> ret = new ArrayList<>();
        for(Entity entity : entities){
            if(entity instanceof ZombieToastSpawner){
                ret.add((ZombieToastSpawner) entity);
            }
        }
        return ret;
    }

    private Position getSpawnPositionSpawner(ZombieToastSpawner spawner){
        return null; //TODO
    }
    public Entity getEntityById(String id){
        Integer intId = Integer.parseInt(id);
        for(Entity entity : entities){
            if(entity.getId() == (int) intId){
                return entity;
            }
        }
        return null;
    }
    private List<Wall> getWalls(){
        List<Wall> ret = new ArrayList<>();
        for(Entity entity : entities){
            if(entity instanceof Wall){
                ret.add((Wall)entity);
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
        List<Wall> walls = getWalls();
        return (walls.stream().mapToInt(x -> x.getPosition().getX()).min().orElse(0));
    }

    private int getXMax(){
        List<Wall> walls = getWalls();
        return (walls.stream().mapToInt(x -> x.getPosition().getX()).max().orElse(0));
    }

    private int getYMin(){
        List<Wall> walls = getWalls();
        return (walls.stream().mapToInt(x -> x.getPosition().getY()).min().orElse(0));
    }

    private int getYMax(){
        List<Wall> walls = getWalls();
        return (walls.stream().mapToInt(x -> x.getPosition().getY()).max().orElse(0));
    }

    private Position getRandomPosition(){
        Position pos = new Position(ThreadLocalRandom.current().nextInt(getXMin(), getXMax()), ThreadLocalRandom.current().nextInt(getYMin(), getYMax()));
        return pos;
    }

    private List<Wall> getBoundariesToRight(List<Wall> boundaries, Position pos){
        List<Wall> toRight = boundaries.stream().filter(x -> x.getPosition().getX() > pos.getX()).collect(Collectors.toList());
        return toRight;
    }

    private List<Wall> getPiecesToRight(List<Wall> boundaries, Position pos){
        return getBoundariesToRight(boundaries, pos).stream().filter(x -> isEmpty(x.getPosition().translateBy(Direction.LEFT))).collect(Collectors.toList());
    }
    
    private Position getSpawnPositionRandom(){
        Position pos = getRandomPosition();
        List<Wall> boundaries = getBoundaries();
        List<Wall> toRight = getPiecesToRight(boundaries, pos);

        while(toRight.size() % 2 == 0 && !isEmpty(pos)){
            pos = new Position(ThreadLocalRandom.current().nextInt(getXMin(), getXMax()), ThreadLocalRandom.current().nextInt(getYMin(), getYMax()));
            toRight = getBoundariesToRight(boundaries, pos);
        }

        return pos;
    }

    private void spawnRandomEnemies(){
        Double roll = ThreadLocalRandom.current().nextDouble(0, 1);
        Position pos = null;

        EntityFactory eFactory = new EntityFactory();

        if(roll < mercenarySpawnChance){
            pos = getSpawnPositionRandom();
            Mercenary merc = (Mercenary)eFactory.createEntity(Entity.getNumEntityIds(), "mercenary", pos.getX(), pos.getY(), 0, null);
            merc.chase(getPlayer());
            entities.add(merc);
        }

        if(tickCounter % spiderTicks == 0){
            pos = getSpawnPositionRandom();
            MovingEntity spider = (Spider)eFactory.createEntity(Entity.getNumEntityIds(), "spider", pos.getX(), pos.getY(), 0, null);
            entities.add(spider);
        }

        return;
    }

    public MovingEntity getEntityOnPlayer(Character player){
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

    private UnpickedUpItem getItemOnPlayer(){
        Character player = getPlayer();
        for(Entity entity : entities){
            if(entity instanceof UnpickedUpItem && Objects.equals(entity.getPosition(), player.getPosition())){
                return (UnpickedUpItem) entity;
            }
        }
        return null;
    }
    public DungeonResponse tick(String itemUsed, Direction movementDirection) throws IllegalArgumentException, InvalidActionException {
        Character player = getPlayer();
        Inventory inventory = player.inventory;
        //use item
        Item used = inventory.getItemFromType(itemUsed);
        if(used != null){
            if(!(used instanceof Consumable)){
                throw(new IllegalArgumentException());
            }
            Consumable cons = (Consumable) used;
            player.use(cons);
        }

        entities.removeAll(getStaticEntities());
        entities.addAll(StaticEntity.getStaticEntitiesList());

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
            ZombieToast zomb = spawner.spawn(tickCounter, gameMode);
            if(zomb != null){
                entities.add(zomb);
            }
        }

        spawnRandomEnemies();


        resetMercSpeeds();
        //battle -- needs list of mercenaries, needs movingEntity on same tile as player

        MovingEntity baddie = getEntityOnPlayer(player);
        if(baddie != null){
            BattleManager bat = new BattleManager(player, baddie, getMercenaries());
            List<Battleable> survivors = bat.battle();
            removeDeadEntities();
        }        

        UnpickedUpItem pickup = getItemOnPlayer();
        if(pickup != null){
            try {
                inventory.addItemToInventory(pickup.pickupItem());
            }
            catch (Exception e) {
                throw new IllegalArgumentException("The item you are trying to pickup does not exist");
            }
        }


        //increment tick counter
        tickCounter++;

        /*
        //display remaining goals and end game if there are none
        DungeonResponse ret = new DungeonResponse(dungeonId, dungeonName, entities.stream().map(x -> x.getInfo()).collect(Collectors.toList()), inventory.getItemsAsResponse(), getInventory().generateBuildables(), goal.getGoalsLeft(entities));
        return ret;
        */

        return null;
    }



    private void resetMercSpeeds() {
        List<Mercenary> mercs = getMercenaries();
        for(Mercenary merc : mercs){
            merc.setSpeed(Mercenary.defaultSpeed);
        }
    }
    
    
}
