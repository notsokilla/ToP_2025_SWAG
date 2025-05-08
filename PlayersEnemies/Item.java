
package PlayersEnemies;

public abstract class Item {
    private String name;
    private ItemType type;
    private int price;

    public Item(String name, ItemType type, int price) {
        this.name = name;
        this.type = type;
        this.price = price;
    }
    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public ItemType getType() {
        return type;
    }

    public abstract void applyEffect(Player player);
    public abstract void removeEffect(Player player);
}