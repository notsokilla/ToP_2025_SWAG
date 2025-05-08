package Events;

import PlayersEnemies.*;
import Misc.*;
import com.sun.tools.javac.Main;

import java.util.Scanner;

// Класс QuestEvent
public class QuestEvent implements EventBehavior {
    // Нестатические поля
    private Enemy enemy;
    private BattleManager battleManager;
    private Scanner scanner;

    // Конструктор с зависимостями
    public QuestEvent(Enemy enemy, BattleManager battleManager, Scanner scanner) {
        this.enemy = enemy;
        this.battleManager = battleManager;
        this.scanner = scanner;
    }

    // Реализация метода интерфейса (не статическая!)
    @Override
    public void trigger(Player player) {
        // Проверка состояния врага
        if (enemy.isAlive()) {
            System.out.println("Бой с " + enemy.getName() + " начинается!");

            // Запуск боя через BattleManager
            BattleMap battlemap = new BattleMap();
            boolean victory = battleManager.startBattle(player, enemy, battlemap, scanner);

            if (victory) {
                System.out.println("Вы победили!");
                // Отметка квеста как выполненного
                player.completeQuest("Мерзкий Поганец");
                player.setPosition(12, 7);
                MainMap.setCell(player.getX(), player.getY(), 'P');
            } else {
                System.out.println("Вы проиграли...");
            }
        } else {
            System.out.println("Враг уже повержен.");
        }
    }
}