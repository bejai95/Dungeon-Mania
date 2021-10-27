package dungeonmania;

import java.util.ArrayList;
import java.util.List;

import dungeonmania.util.Direction;
import dungeonmania.exceptions.InvalidActionException;

public class Character extends Entity {
    Inventory inventory = new Inventory();
    //List<Mercenary> allies = new ArrayList<>();
    double health;
    int damage;
    double defense;

    public Character(int id, String type, int x, int y) {
        super(id, type, x, y);
    }

    public void move(Direction movemeDirection) {

    }
    public void pickUpItem(int id) throws InvalidActionException {
        //check that the item is on the same position
    }
    public int getDamage() {
        //gets damage of all things including inventory
        return 0;
        
    }
    public double getDefense() {
        //gets defense of all things including inventory
        return 0;
    }
    public double getHealth() {
        return this.health;
    }
    public void use(Consumable consumable) throws InvalidActionException {

    }


    
}