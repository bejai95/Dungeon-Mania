package dungeonmania;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import com.google.gson.annotations.SerializedName;

import org.json.JSONArray;
import org.json.JSONObject;

import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.response.models.AnimationQueue;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import javax.sound.sampled.Port;

import java.lang.reflect.*;

public class Game {
    private String dungeonId;
    private String dungeonName;
    private List<Entity> entities;
    private List<String> buildables;
    private int tickCounter; // Initialized to zero
    private final List<AnimationQueue> animations = new ArrayList<>();
    private String gameMode;
    private static int numDungeonIds; // Initialized to zero
    private static int uniqueIdNum; // Initialized to zero
    private final int spiderLimit = 4;
    
    @SerializedName(value="goal", alternate="goal-condition")
    private Goal goal;
    
    private double mercenarySpawnChance = 0.005;
    private int spiderTicks = 10;
    
    public Game() {
    }

    public Goal getGoal(){ return goal;}

    public static int getNumDungeonIds() {
        return numDungeonIds;
    }

    public static void incrementNumDungeonIds() {
        numDungeonIds++;
    }
    /**
     * Initialises the inventory and buildables lists
     */
    public void initialiseBuildables() {
        this.buildables = new ArrayList<String>();
    }

    /**
     * Generates a unique id for an entity or item
     * @param cell
     * @return
     */
    static int generateUniqueId() {
        int ret = uniqueIdNum;
        uniqueIdNum++;
        return ret;
    }

    public void setHealthBar(double newHealth) {
        if (newHealth == 1) {
            animations.add(new AnimationQueue("PostTick", Integer.toString(getPlayer().getId()), Arrays.asList("healthbar set 1", "healthbar tint 0x00ff00"), false, -1));
        } else {
            animations.add(new AnimationQueue("PostTick", Integer.toString(getPlayer().getId()), Arrays.asList("healthbar set " + newHealth, "healthbar tint 0xff0000"), false, -1));
        }
    }

    /**
     * Checks if a cell is empty
     * @param cell
     * @return
     */
    public boolean isEmpty(Position cell) {
        for (int i = 0; i < entities.size(); i++) {
            if (cell.equals(entities.get(i).getPosition())) {
                return false;
            }
        }
        return true;
    }

    /**
     * Finds an adjacent empty cell when given a cell
     * @param centre
     * @return
     */
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
        return getPlayer().getInventory();
    }

    public List<String> getBuildables() {
        return buildables;
    }
    /**
     * Gets all the goals left to complete
     */
   public String getGoalsLeft() {
        if (goal == null)  {
            return null;
        } else {
            return goal.getGoalsLeft(entities);
        }
    }

    // Generate a dungeon response
    /**
     * Returns the dungeon information as a dungeon response
     * @return
     */
    public DungeonResponse generateDungeonResponse() {
        if(getPlayer() == null){
            return new DungeonResponse(dungeonId, dungeonName, entities.stream().map(x -> x.getInfo()).collect(Collectors.toList()), null, null, getGoalsLeft(), animations);
        }
        
        return new DungeonResponse(dungeonId, dungeonName, entities.stream().map(x -> x.getInfo()).collect(Collectors.toList()), getInventory().getItemsAsResponse(), getInventory().generateBuildables(), getGoalsLeft(), animations);
    } 

    public void setGameMode(String gameMode) {
        this.gameMode = gameMode;
    }
    /**
     * Gets the player from the entities list
     * @return
     */
    public Character getPlayer(){
        for(Entity entity : entities){
            if(entity instanceof Character){
                return (Character) entity;
            }
        }
        return null;
    }
    /**
     * Gets a list of all the mercenaries on the map
     */
    public List<Mercenary> getMercenaries(){
        List<Mercenary> ret = new ArrayList<>();
        for(Entity entity : entities){
            if(entity instanceof Mercenary){
                ret.add((Mercenary) entity);
            }
        }
        return ret;
    }
    /**
     * Gets a list of all the moving entities on the map
     * @return
     */
    public List<MovingEntity> getMovingEntities(){
        List<MovingEntity> ret = new ArrayList<>();
        for(Entity entity : entities){
            if(entity instanceof MovingEntity){
                ret.add((MovingEntity) entity);
            }
        }
        return ret;
    }

    private List<Battleable> getBattlebles(){
        List<Battleable> ret = new ArrayList<>();
        for(Entity entity : entities){
            if(entity instanceof Battleable){
                ret.add((Battleable) entity);
            }
        }
        return ret;
    }

    private List<Entity> batToEnts(List<Battleable> bats){
        List<Entity> ret = new ArrayList<>();
        for(Battleable bat : bats){
            if(bat instanceof Entity){
                ret.add((Entity) bat);
            }
        }
        return ret;
    }
    /**
     * Gets a list of all the static entities on the map
     * @return
     */
    public List<StaticEntity> getStaticEntities(){
        List<StaticEntity> ret = new ArrayList<>();
        for(Entity entity : entities){
            if(entity instanceof StaticEntity){
                ret.add((StaticEntity) entity);
            }
        }
        return ret;
    }
    /**
     * Gets a list of all the spawners on the map
     * @return
     */
    public List<ZombieToastSpawner> getSpawners(){
        List<ZombieToastSpawner> ret = new ArrayList<>();
        for(Entity entity : entities){
            if(entity instanceof ZombieToastSpawner){
                ret.add((ZombieToastSpawner) entity);
            }
        }
        return ret;
    }

    /**
     * Gets a list of all floor switches on the map
     * @return
     */
    public List<FloorSwitch> getSwitches(){
        List<FloorSwitch> ret = new ArrayList<>();
        for(Entity entity : entities){
            if(entity instanceof FloorSwitch){
                ret.add((FloorSwitch) entity);
            }
        }
        return ret;
    }

    /**
     * Gets a list of all placed bombs
     * @return
     */
    public List<PlacedBomb> getBombs(){
        List<PlacedBomb> ret = new ArrayList<>();
        for(Entity entity : entities){
            if(entity instanceof PlacedBomb){
                ret.add((PlacedBomb) entity);
            }
        }
        return ret;
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
    /**
     * Gets a list of all the walls in the game
     * @return
     */
    private List<Wall> getWalls(){
        List<Wall> ret = new ArrayList<>();
        for(Entity entity : entities){
            if(entity instanceof Wall){
                ret.add((Wall)entity);
            }
        }
        return ret;
    }
    /**
     * Returns whether not a given wall is a boundary wall
     * @param wall
     * @param walls
     * @return
     */
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
    /**
     * Gets a list of all the boundary walls
     * @return
     */
    private List<Wall> getBoundaries(){
        List<Wall> walls = getWalls();
        List<Wall> walls2 = getWalls();
        return walls.stream().filter(x -> isBoundary(x, walls2)).collect(Collectors.toList());
    }
    /**
     * Gets the minimum y coordinate of a wall
     * @return
     */
    private int getXMin(){
        List<Wall> walls = getWalls();
        return (walls.stream().mapToInt(x -> x.getPosition().getX()).min().orElse(0));
    }
    /**
     * Gets the maximum x coordinate of a wall
     * @return
     */
    private int getXMax(){
        List<Wall> walls = getWalls();
        return (walls.stream().mapToInt(x -> x.getPosition().getX()).max().orElse(0));
    }
    /**
     * Gets the minimum y coordinate of a wall
     * @return
     */
    private int getYMin(){
        List<Wall> walls = getWalls();
        return (walls.stream().mapToInt(x -> x.getPosition().getY()).min().orElse(0));
    }
    /**
     * Gets the maximum y coordinate of a wall
     * @return
     */
    private int getYMax(){
        List<Wall> walls = getWalls();
        return (walls.stream().mapToInt(x -> x.getPosition().getY()).max().orElse(0));
    }
    /**
     * Gets a random position in the approximate bounds of the map
     * @return
     */
    private Position getRandomPosition(){
        Position pos = new Position(ThreadLocalRandom.current().nextInt(getXMin(), getXMax()), ThreadLocalRandom.current().nextInt(getYMin(), getYMax()));
        return pos;
    }
    /**
     * Gets the boundary walls to the right of the player
     * @param boundaries
     * @param pos
     * @return
     */
    private List<Wall> getBoundariesToRight(List<Wall> boundaries, Position pos){
        List<Wall> toRight = boundaries.stream().filter(x -> x.getPosition().getX() > pos.getX()).collect(Collectors.toList());
        return toRight;
    }
    /**
     * Gets a list of all blocks of walls (collections of tiles all occupied by walls,
     * represented by the leftmost wall) to the right of the player
     * @param boundaries
     * @param pos
     * @return
     */
    private List<Wall> getPiecesToRight(List<Wall> boundaries, Position pos){
        return getBoundariesToRight(boundaries, pos).stream().filter(x -> isEmpty(x.getPosition().translateBy(Direction.LEFT))).collect(Collectors.toList());
    }
    /**
     * Gets a random position inside the boundaries of the map
     * @return
     */
    private Position getSpawnPositionRandom(){
        
        if(getXMin() >= getXMax() || getYMin() >= getYMax()){
            Character player = getPlayer();
            int x = player.getPosition().getX();
            int y = player.getPosition().getY();
            return new Position(ThreadLocalRandom.current().nextInt(x-5, x+5), ThreadLocalRandom.current().nextInt(y-5, y+5));
        }
        
        Position pos = getRandomPosition();
        List<Wall> boundaries = getBoundaries();
        List<Wall> toRight = getPiecesToRight(boundaries, pos);

        while(toRight.size() % 2 == 0 && !isEmpty(pos)){
            pos = new Position(ThreadLocalRandom.current().nextInt(getXMin(), getXMax()), ThreadLocalRandom.current().nextInt(getYMin(), getYMax()));
            toRight = getBoundariesToRight(boundaries, pos);
        }

        return pos;
    }

    private int getNumberOfSpiders(){
        int ret = 0;
        for(Entity entity : entities){
            if(entity instanceof Spider){
                ret++;
            }
        }
        return ret;
    }
    /**
     * Spawns spiders every 10 ticks, has a random chance to spawn a mercenary every tick
     * and spawns whatever it does end up spawning at a random place on the map inside the walls
     */
    private void spawnRandomEnemies(){
        Double roll = ThreadLocalRandom.current().nextDouble(0, 1);
        Position pos = null;

        EntityFactory eFactory = new EntityFactory();

        if(roll < mercenarySpawnChance){
            pos = getSpawnPositionRandom();
            Mercenary merc = (Mercenary)eFactory.createEntity(Game.generateUniqueId(), "mercenary", pos.getX(), pos.getY(), 0, null);
            merc.chase(getPlayer());
            entities.add(merc);
        }

        if(tickCounter % spiderTicks == 0 && getNumberOfSpiders() < spiderLimit){
            pos = getSpawnPositionRandom();
            MovingEntity spider = (Spider)eFactory.createEntity(Game.generateUniqueId(), "spider", pos.getX(), pos.getY(), 0, null);
            entities.add(spider);
        }

        return;
    }
    /**
     * Gets the MovingEntity on the same position as the player
     * @param player
     * @return null if there is no such moving entity
     */
    public MovingEntity getEntityOnPlayer(Character player){
        List<MovingEntity> ms = getMovingEntities();
        for(MovingEntity entity : ms){
            if(player.getPosition().equals(entity.getPosition())){
                return entity;
            }
        }
        return null;
    }
    /**
     * Removes all entities whose health is 0 or less from the entities list
     */
    private void removeDeadEntities(){
        System.out.println("Entities before: " + entities.size());
        List<Battleable> deadEnts = getBattlebles().stream().filter(x -> x.getHealth() <= 0.0).collect(Collectors.toList());
        System.out.println("Number of dead entities: " + deadEnts.size());
        entities.removeAll(batToEnts(deadEnts));
        System.out.println("Entities after: " + entities.size());
    }

    private void removeSpecifiedEntities(){
        return;
    }


   public DungeonResponse tick(String itemUsed, Direction movementDirection) throws IllegalArgumentException, InvalidActionException {
        Character player = getPlayer();
        Inventory inventory = player.getInventory();
        Position destinationTile = player.getPosition().translateBy(movementDirection);

        //Create a new instance of the static entity interaction helper class
        staticEntityInteract staticInteraction = new staticEntityInteract(this);

        //use item
        //parse itemUsed by removing the underscore
        if(itemUsed != null){
            Item used = inventory.getItem(Integer.parseInt(itemUsed));
            System.out.println(used);
            if(used != null){
                if (used instanceof Bomb) {
                    Bomb usedBomb = (Bomb)used;
                    staticInteraction.placeBomb(player.getPosition(),usedBomb.getitemId());
                    inventory.removeItem(used);
                } else {
                    if(!(used instanceof Consumable)){
                        throw(new IllegalArgumentException());
                    }
                    Consumable cons = (Consumable) used;
                    cons.consume(player);
                }
            }
        } 

        //remove dead items
        inventory.removeDeadItems();

        //Interact with static entities
        staticInteraction.findInteractableStaticEntity(movementDirection);

        //move in direction
        player.move(movementDirection);
        
        //move all the mobs -- needs list of moving entities
        List<MovingEntity> movingEntities = getMovingEntities();
        for(MovingEntity mob : movingEntities){
            mob.move();
        }
        
        //spawn in enemies
        List<ZombieToastSpawner> spawners = getSpawners();
        for(ZombieToastSpawner spawner : spawners){
            staticInteraction.zombieSpawn(spawner);
        }

        spawnRandomEnemies();


        resetMercSpeeds();
        //battle -- needs list of mercenaries, needs movingEntity on same tile as player

        MovingEntity baddie = getEntityOnPlayer(player);
        if(baddie != null && !baddie.isAlly()){
            System.out.println("Health Before: " + player.getHealth());
            BattleManager bat = new BattleManager(player, baddie, getMercenaries());
            List<Battleable> dead = bat.battle();
            System.out.println("Health After: " + player.getHealth());
            System.out.println("Number dead in Battle" + dead.size());
            //entities.removeAll(dead);

            removeDeadEntities();
        }  

        // Adjust the health bar
        double healthInRequiredRegion = player.getHealth() / player.getMaxHealth();
        setHealthBar(healthInRequiredRegion);

        //increment tick counter
        tickCounter++;

        //display remaining goals and end game if there are none
        //DungeonResponse ret = new DungeonResponse(dungeonId, dungeonName, entities.stream().map(x -> x.getInfo()).collect(Collectors.toList()), inventory.getItemsAsResponse(), getInventory().generateBuildables(), goal.getGoalsLeft(entities));
        return generateDungeonResponse();
    }

    /**
     * Resets the speeds of all mercenaries in the game
     */
    private void resetMercSpeeds() {
        List<Mercenary> mercs = getMercenaries();
        for(Mercenary merc : mercs){
            merc.setSpeed(Mercenary.defaultSpeed);
        }
    }

     /**
     * Sets Portal Colours on the map
     */
    public void setSprites() {
        for (Entity entity : entities) {
            if (entity instanceof Portal) {
                Portal selectedPortal = (Portal)entity;
                String colour = selectedPortal.getportalColour().toLowerCase();
                String portalName = "portal_" + colour;
                selectedPortal.setType(portalName);
            } else if (entity instanceof Door) {
                Door selectedDoor = (Door)entity;
                if (selectedDoor.getMatchingKeyNum() == 1) {
                    selectedDoor.setType("door_silver");
                } else {
                    selectedDoor.setType("door_gold");
                }
            } else if (entity instanceof UnpickedUpItem){
                UnpickedUpItem selectedItem = (UnpickedUpItem)entity;
                if (selectedItem.getItemClass().equals("Key")) {
                    if (selectedItem.getKeyNum() == 1) {
                        selectedItem.setType("key_silver");
                    } else {
                        selectedItem.setType("key_gold");
                    }
                }
            }
        }
    }

    public int getTickCounter() {
        return tickCounter;
    }

    public String getGameMode() {
        return gameMode;
    }

    /**
     * Checks to see if a collision will occur when moving an entity from one 
     * cell to another
     */
    public boolean isCollision(Entity movingEntity, Position destination){
        for (Entity entity : entities) {
            if (entity.getPosition().equals(destination) && entity.getPosition().getLayer() >= destination.getLayer()) {
                return true;
            }
        }
        return false;
    }
    
}
