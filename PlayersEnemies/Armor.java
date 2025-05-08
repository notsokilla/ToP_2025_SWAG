
package PlayersEnemies;

public class Armor extends Item {
    private int defense;

    public Armor(String name, ItemType type, int defense) {
        super(name, type, 50);
        this.defense = defense;
    }

    @Override
    public void applyEffect(Player player) {
        player.setArmor(player.getArmor() + defense);
    }

    @Override
    public void removeEffect(Player player) {
        player.setArmor(player.getArmor() - defense);
    }
}