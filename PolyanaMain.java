import PlayersEnemies.*;
import Misc.*;
import Events.*;
import java.util.Scanner;
import java.util.NoSuchElementException;

public class PolyanaMain {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean playAgain = true;

        // Вывод вступительного сюжета
        StoryManager.printIntro();

        while (playAgain) {
            try {
                // Инициализация игры
                MainMap mainMap = new MainMap();
                BattleMap battleMap = new BattleMap();
                Settlement settlement = new Settlement();


                // Создание игрока
                System.out.println("\n=== СОЗДАНИЕ ПЕРСОНАЖА ===");
                Player player = GameInitializer.createPlayer(scanner);

                // Начальная позиция
                mainMap.placePlayer(0, 0, player);

                // Запуск игры
                System.out.println("\n=== ИГРА НАЧИНАЕТСЯ ===");
                GameManager.playGame(scanner, player, mainMap, battleMap, settlement);
                // Проверка победы
                if (player.isAlive() && Quest.isCompleted() && mainMap.isExitReached(player.getX(), player.getY())) {
                    StoryManager.printOutro();
                }

                // Запрос на повторную игру
                playAgain = GameManager.askToPlayAgain(scanner);

            } catch (Exception e) {
                System.out.println("Произошла ошибка: " + e.getMessage());
                System.out.println("Попробуем начать заново...");
                scanner = new Scanner(System.in); // Сброс сканера при ошибке
            }
        }

        // Завершение

        System.out.println("\nСпасибо за игру! До свидания!");
        scanner.close();
    }
}