package PlayersEnemies;

public class Recruit extends Entity {
    private String name;
    private int maxhealth;
    private int health;
    private int damage;
    private boolean inParty;
    private  int armor;
    private int actionPoints;
    private final int maxActionPoints = 5;

    public Recruit(String name, int health, int damage, int armor) {
        super(0, 0); // Начальная позиция будет устанавливаться при добавлении в отряд
        this.name = name;
        this.maxhealth = maxhealth;
        this.health = health;
        this.damage = damage;
        this.armor = armor;
        this.inParty = false;
        this.actionPoints = actionPoints;

    }
    public int getArmor() {return armor; }
    public String getName() { return name; }
    public int getHealth() { return health; }
    public int getMaxHealth() {return maxhealth; }
    public void setHealth(int health) {
        this.health = Math.min(health, maxhealth); // Здоровье не может превышать maxHealth
    }
    public int getDamage() { return damage; }
    public boolean isInParty() { return inParty; }
    public void setInParty(boolean inParty) { this.inParty = inParty; }

    public boolean canAttack(Entity target) {
        return Math.abs(getX() - target.getX()) <= 1 &&
                Math.abs(getY() - target.getY()) <= 1;
    }

    @Override
    public void startTurn() {
        this.actionPoints = maxActionPoints;
    }
    @Override
    public int getCurrentActionPoints(){
        return actionPoints;
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

    public void moveTowards(int targetX, int targetY) {
        int dx = Integer.compare(targetX, getX());
        int dy = Integer.compare(targetY, getY());

        // Проверяем, можем ли двигаться по диагонали
        if (dx != 0 && dy != 0 && canAffordAction(2)) {
            setPosition(getX() + dx, getY() + dy);
            spendActionPoints(2);
        }
        // Движение по прямой
        else if (dx != 0 && canAffordAction(1)) {
            setPosition(getX() + dx, getY());
            spendActionPoints(1);
        }
        else if (dy != 0 && canAffordAction(1)) {
            setPosition(getX(), getY() + dy);
            spendActionPoints(1);
        }
    }

    public void takeDamage(int damage) {
        this.health -= damage;
        if (this.health < 0) this.health = 0;
    }

    public boolean isAlive() {
        return health > 0;
    }

    @Override
    public String toString() {
        return name + " (Здоровье: " + health + ", Урон: " + damage + ", Защита: " + armor + ")";
    }


}
