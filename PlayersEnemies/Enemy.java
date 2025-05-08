package PlayersEnemies;
import Misc.*;
import Events.*;
import java.util.Random;


public abstract class Enemy extends Entity {
    private int x;
    private int y;
    private int speed;
    private int health;
    private int armor;
    private int damage;
    private int kritchance;
    private int sneak;
    private String name;

    public Enemy(int x, int y, int speed, int health, int armor, int damage, int sneak, int kritchance, String name) {
        super(x, y);
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.health = health;
        this.armor = armor;
        this.damage = damage;
        this.sneak = sneak;
        this.kritchance = kritchance;
        this.name = name;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getSpeed() {
        return speed;
    }

    public int getHealth() {
        return health;
    }

    public int getArmor() {
        return armor;
    }

    public int getDamage() {
        return damage;
    }

    public int getKritchance() {
        return kritchance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getSneak(){
        return sneak;
    }

    public void setSneak(int sneak){
        this.sneak = sneak;
    }

    public void takeDamage(int damage) {
        this.health -= damage;
        if (this.health < 0) {
            this.health = 0;
        }
    }

    public boolean isAlive() {
        return health > 0;
    }

    public abstract String Avatar();

    @Override
    public void startTurn() {
        this.actionPoints = maxActionPoints;
    }

    @Override
    public void spendActionPoints(int amount) {
        if (amount <= actionPoints) {
            this.actionPoints -= amount;
        }
    }

    @Override
    public boolean canAffordAction(int cost) {
        return actionPoints >= cost;
    }
}