package dungeonmania;

import dungeonmania.util.Position;

public class EntityFactory {

    public Entity createEntity (int id, String type, int x, int y, int matchingKeyNum, String portalColour) {
        switch(type) {
            case "player":
                Position positionPlayer = new Position(x, y, 2);
                return new Character(id, "player", positionPlayer);
            case "wall":
                Position positionWall = new Position(x, y, 3);
                return new Wall(id, "wall", positionWall);
            case "exit":
                Position positionExit = new Position(x, y, 3);
                return new Exit(id, "exit", positionExit);
            case "boulder":
                Position positionBoulder = new Position(x, y, 3);
                return new Boulder(id, "boulder", positionBoulder);
            case "switch":
                Position positionSwitch = new Position(x, y, 3);
                return new FloorSwitch(id, "switch", positionSwitch);
            case "door":
                Position positionDoor = new Position(x, y, 3);
                return new Door(id, "door", positionDoor, matchingKeyNum);
            case "portal":
                Position positionPortal = new Position(x, y, 3);
                return new Portal(id, "portal", positionPortal, portalColour);
            case "zombie_toast_spawner":
                Position positionSpawner = new Position(x, y, 3);
                return new ZombieToastSpawner(id, "zombie_toast_spawner", positionSpawner);
            case "mercenary":
                Position positionMercenary = new Position(x, y, 3);
                // TODO get other logic from game
                return new Mercenary(id, positionMercenary, 0, null); 
            case "treasure":
                Position positionTreasure = new Position(x, y, 3);
                return new UnpickedUpItem(id, "treasure", positionTreasure, "Treasure");
            case "key":
                Position positionKey = new Position(x, y, 3);
                return new UnpickedUpItem(id, "key", positionKey, "Key", matchingKeyNum);
            case "health_potion":
                Position positionHealthPotion = new Position(x, y, 3);
                return new UnpickedUpItem(id, "health_potion", positionHealthPotion, "HealthPotion");
            case "invincibility_potion":
                Position positionInvincibilityPotion = new Position(x, y, 3);
                return new UnpickedUpItem(id, "invincibility_potion", positionInvincibilityPotion, "InvincibilityPotion");
            case "invisibility_potion":
                Position positionInvisibilityPotion = new Position(x, y, 3);
                return new UnpickedUpItem(id, "invisibility_potion", positionInvisibilityPotion, "InvisibilityPotion");
            case "wood":
                Position positionWood = new Position(x, y, 3);
                return new UnpickedUpItem(id, "wood", positionWood, "Wood");
            case "arrow":
                Position positionArrow = new Position(x, y, 3);
                return new UnpickedUpItem(id, "arrow", positionArrow, "Arrow");
            case "bomb":
                Position positionBomb = new Position(x, y, 3);
                return new UnpickedUpItem(id, "bomb", positionBomb, "Bomb");
            case "sword":
                Position positionSword = new Position(x, y, 3);
                return new UnpickedUpItem(id, "sword", positionSword, "Sword");
            case "armour":
                Position positionArmour = new Position(x, y, 3);
                return new UnpickedUpItem(id, "armour", positionArmour, "Armour");
            case "one_ring":
                Position positionOneRing = new Position(x, y, 3);
                return new UnpickedUpItem(id, "one_ring", positionOneRing, "OneRing");
            }
        
            return null;
    }
}
