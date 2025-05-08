package Events;

import PlayersEnemies.*;
import Misc.*;
import java.util.Scanner;

public class BattleEvent implements EventBehavior {
    private final BattleManager battleManager;
    private Enemy enemy;
    private Scanner scanner; // Добавляем поле

    public BattleEvent(BattleManager battleManager, Scanner scanner) {
        this.battleManager = battleManager;
        this.scanner = scanner; // Инициализируем через конструктор
    }

    @Override
    public void trigger(Player player) { // Правильная сигнатура
        if (enemy == null) {
            enemy = GameInitializer.createEnemy();
        }

        BattleMap battleMap = new BattleMap();
        battleMap.placePlayerAndParty(player);
        battleMap.placeEnemy(enemy);
        player.setPosition(0, 0);

        battleManager.startBattle(player, enemy, battleMap, scanner);
    }
}