package dungeonmania;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;
import java.lang.reflect.*;


public class staticEntityInteract {
    //-----Data-----
    Game currentGame = null;

    //-----Constructors-----
    public staticEntityInteract(Game currentGame) {
        this.currentGame = currentGame;
    }

    //-----Methods-----
    /**
     * Checks to see if the tile that the character moves onto has a static entity
     * to interact with. Returns a boolean to see if this should halt the movement.
     */
    public void findInteractableStaticEntity(Direction movementDirection){
        Position destinationTile = currentGame.getPlayer().getPosition().translateBy(movementDirection);
        List<StaticEntity> staticEntitiesList = new ArrayList<>();
        staticEntitiesList = currentGame.getStaticEntities();
        for (StaticEntity staticEntityItem : staticEntitiesList) {
            if (staticEntityItem.getPosition().equals(destinationTile) && staticEntityItem.canInteract()) {
                interactStaticEntity(staticEntityItem, movementDirection);
            }
        }
    }


    /**
     * Checks to see if the tile that the character moves onto has a static entity
     * to interact with. Returns a boolean to see if this should halt the movement.
     */
    private void interactStaticEntity(Entity interactionEntity, Direction movementDirection){
        if (interactionEntity instanceof Portal)  {
            //Teleports player when they step on a portal
            Portal interactionPortal = (Portal)interactionEntity;
            Position teleportLocation = interactionPortal.getTeleportLocation(currentGame.getStaticEntities());
            currentGame.getPlayer().setPosition(teleportLocation);
        } else if (interactionEntity instanceof Exit) {
            //put code in here that will end the game
        } else if (interactionEntity instanceof Door) {
            Door interactionDoor = (Door)interactionEntity;
            doorInteraction(interactionDoor);
        } else if (interactionEntity instanceof Boulder) {
            Boulder interactionBoulder = (Boulder)interactionEntity;
            moveBoulder(interactionBoulder, movementDirection);
        } else if (interactionEntity instanceof UnpickedUpItem){
            UnpickedUpItem interactionUnpickedUpItem = (UnpickedUpItem)interactionEntity;
            pickupCurrentItem(interactionUnpickedUpItem);
        }
    }

    /**
     * This will handle interaction with a door
     */
    private void doorInteraction(Door interactionDoor){
        List<Item> itemsList = currentGame.getInventory().getItems();
        for (Item selectedItem : itemsList) {
            if (selectedItem.getType().equals("key")) {
                Key inputKey = (Key)selectedItem;
                if (interactionDoor.openDoor(inputKey)) {
                    currentGame.addAnimation("PostTick", Integer.toString(interactionDoor.getId()), Arrays.asList("sprite door_open"), false, -1);
                }
            }
        }  
    }

    /**
     * Gets the item on the ground at the same position as the player  
     * @return null if there is no such item
     */
    private void pickupCurrentItem(UnpickedUpItem selectedPickup){
        Item newItem = null;
        try {
            newItem = selectedPickup.pickupItem();
        } catch (IllegalAccessError | ClassNotFoundException | IllegalAccessException | InvocationTargetException
                | InstantiationException | NoSuchMethodException e1) {
            throw new IllegalArgumentException("The item you are trying to pickup does not exist");
        }
        if(newItem != null){
            currentGame.getPlayer().getInventory().addItemToInventory(newItem);
            currentGame.getEntities().remove(selectedPickup);
        }
    }

    /**
     * Moves the boulder
     */
    private void moveBoulder(Entity boulder, Direction movementDirection){
        Position boulderPos = boulder.getPosition();
        Position boulderNextPos = boulderPos.translateBy(movementDirection);
        //Check to see if the boulder collides with anything
        if (currentGame.isCollision(boulder, boulderNextPos) == false) {
            //If no collision it will move
            boulder.setPosition(boulderNextPos);

            //Activates the switch if moved onto a switch
            FloorSwitch potentialPressedSwitch = isFloorSwitch(boulderNextPos);
            if (potentialPressedSwitch != null) {
                potentialPressedSwitch.setIsActive(true);
                explodeOnSwitchCheck(potentialPressedSwitch);
            }

            //Deactivates a switch if it moves off a switch
            FloorSwitch potentialUnpressedSwitch = isFloorSwitch(boulderPos);
            if (potentialUnpressedSwitch != null) {
                potentialUnpressedSwitch.setIsActive(false);
            }
        }
        return;
    }

    /**
     * Places a bomb on the ground
     */
    public void placeBomb(Position placementPosition, int bombID){
        PlacedBomb newBomb = new PlacedBomb(bombID, placementPosition);
        currentGame.getEntities().add(newBomb);

    }

    /*When a switch is pressed it can call this method with it's position to call
    on any adjacent bombs to explode*/
    private void explodeOnSwitchCheck (FloorSwitch checkingSwitch){
        Position switchPos = checkingSwitch.getPosition(); 
        List<Position> adjacentPositions = switchPos.getAdjacentPositions();
        List<PlacedBomb> placedBombList = new ArrayList<PlacedBomb>();
        List<PlacedBomb> explodeList = new ArrayList<PlacedBomb>();
        placedBombList = currentGame.getBombs();

        /*Go through each adjacent location to the switch, then check the bombs list
        if it finds any bombs add it to the explode list*/
        for (Position edgeTile : adjacentPositions) {
            for (PlacedBomb possibleBomb : placedBombList ) {
                if (edgeTile.equals(possibleBomb.getPosition())) {
                    explodeList.add(possibleBomb);
                }   
            }
        }

        //Explodes all bombs on the explode list
        for (PlacedBomb explodeItem : explodeList) {
            explode(explodeItem);
        }
    }

    /*explodes killing all enemies 1 cell adjacent to the bomb
    and removing the placed bomb*/
    public void explode(PlacedBomb activeBomb) {
        List<MovingEntity> movingEntityList = new ArrayList<>();
        movingEntityList = currentGame.getMovingEntities();
        List<Position> adjacentPositions = activeBomb.getPosition().getAdjacentPositions();
        List<MovingEntity> killList = new ArrayList<MovingEntity>();
        //Removes the bomb from the placed bombs list
        currentGame.getEntities().remove(activeBomb);
        //Finds which surrounding entities to kill
        for (Position edgeTile : adjacentPositions) {
            for (MovingEntity movingEntityItem : movingEntityList) {
                if (edgeTile.equals(movingEntityItem.getPosition())) {
                    killList.add(movingEntityItem);
                }
            }
        }
        //Removes surrounding entities that have been killed
        for (MovingEntity deadEntity : killList) {
            currentGame.getEntities().remove(deadEntity);
        }
        return;
    }

    /**
     * Will spawn a zombie toast on an adjacent open tile 
     * and return the newly created zombie toast or null
     */
     public void zombieSpawn (ZombieToastSpawner activeSpawner) {
        Position spawnerPosition = activeSpawner.getPosition();

        //Checks to see if spawn conditions are met
        if ((currentGame.getGameMode() == "hard" && currentGame.getTickCounter() % 15 == 0) || (currentGame.getGameMode() != "hard" && currentGame.getTickCounter() % 20 == 0)) {
            //List of adjacent positions around spawner
            List<Position> adjacentPositions = spawnerPosition.getAdjacentPositions();

            //Sets the default spawn positions as on top of the spawner
            Position spawnPoint = spawnerPosition;

            //Find an adjacent open tile for the zombie toast to spawn
            for (Position edgeCell : adjacentPositions) {
                if (currentGame.isEmpty(edgeCell)) {
                    ;
                } else {
                    spawnPoint = edgeCell;
                    break;
                }
            }

            //Create new zombie toast
            EntityFactory efactory = new EntityFactory();
            ZombieToast zombie = (ZombieToast)efactory.createEntity(Game.generateUniqueId(), "zombie_toast",
            spawnPoint.getX(), spawnPoint.getY(), 0, null);
            currentGame.getEntities().add(zombie);
        } else {
            return;
        }

    }

    /**
     * Checks to see if a floor switch is present at a given position 
     * and returns the floor switch or null
     */
    private FloorSwitch isFloorSwitch(Position tile) {
        List<FloorSwitch> floorSwitchList = new ArrayList<>();
        floorSwitchList = currentGame.getSwitches();
        for (FloorSwitch floorSwitchItem : floorSwitchList) {
            if (floorSwitchItem.getPosition().equals(tile)) {
                return floorSwitchItem;
            }
        }
        return null;
    }

    //-----Getters and Setters-----
} 
