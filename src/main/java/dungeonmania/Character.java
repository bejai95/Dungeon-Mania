package dungeonmania;

import java.util.ArrayList;
import java.util.List;

import dungeonmania.util.Direction;

public class Character {
    Inventory inventory = new Inventory();
    //List<Mercenary> allies = new ArrayList<>();
    double health;
    int damage;
    double defense;

    public Character() {

    }

    public void move(Direction movemeDirection) {

    }
    public void pickUpItem(String id) {
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


    
}
