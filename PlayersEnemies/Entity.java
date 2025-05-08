package PlayersEnemies;

public abstract class Entity {
    protected int x;
    protected int y;
    protected int actionPoints;
    protected final int maxActionPoints = 5;

    public Entity(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // Общие методы для всех сущностей
    public int getX() { return x; }
    public int getY() { return y; }
    public int getCurrentActionPoints() { return actionPoints; }
    public void setPosition(int x, int y) { this.x = x; this.y = y; }
    public abstract void startTurn();
    public abstract void spendActionPoints(int amount);
    public abstract boolean canAffordAction(int cost);
}