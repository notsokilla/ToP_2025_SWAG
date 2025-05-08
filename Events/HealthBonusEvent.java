
package Events;

import PlayersEnemies.Player;
import PlayersEnemies.Item;
import PlayersEnemies.ItemType;

public class HealthBonusEvent implements EventBehavior {
    @Override
    public void trigger(Player player) {
        System.out.println("Вы нашли перевернутую телегу, умудрившись поставить её на колеса, вы увидели под ней зелье исцеления!");


        // Пример добавления предмета в инвентарь
        Item hppotion = new Item("Зелье исцеления", ItemType.CONSUMABLE, 50) {
            @Override
            public void applyEffect(Player player) {
                player.setHealth(Math.min(player.getHealth() + 25, player.getMaxHealth()));
            }

            @Override
            public void removeEffect(Player player) {}
        };

        boolean success = player.addItemToInventory(hppotion);
        if (!success) {
            System.out.println("Сумка переполнена!");
        }
    }
}