package Misc;

import PlayersEnemies.*;
import Events.*;
import java.util.Random;
import java.util.Scanner;

public class GameInitializer {
    private static final int SIZE = 15;

    // Создание игрока
    public static Player createPlayer(Scanner scanner) {
        String playerName = getPlayerName(scanner);
        char playerGender = getPlayerGender(scanner);

        Player player;
        if (playerGender == 'M') {
            player = new Man(0, 0, 1, 90, 31, 50, 20, playerName);
            System.out.println(((Man) player).StartAvatar());
        } else {
            player = new Woman(0, 0, 1, 900, 3100, 50, 2000, playerName);
            System.out.println(((Woman) player).StartAvatar());
        }
        try {
            Thread.sleep(2000); // Задержка для отображения аватара
        } catch (InterruptedException e) {
            System.out.println("Ошибка при выполнении задержки: " + e.getMessage());
        }
        return player;
    }

    // Создание врага
    public static Enemy createEnemy() {
        Random random = new Random();
        if (random.nextBoolean()) {
            return new LitsarPekla(14, 14, 1, 200, 30, 30, 100, 20);
        } else {
            return new Lucifer(14, 14, 2, 999, 999, 999, 100, 100);
        }
    }



    // Получение имени игрока
    private static String getPlayerName(Scanner scanner) {
        System.out.println("Введите имя вашего персонажа:");
        return scanner.nextLine().trim();
    }

    // Получение пола игрока
    private static char getPlayerGender(Scanner scanner) {
        while (true) {
            System.out.println("Выберите пол вашего персонажа (M - мужчина, F - женщина):");
            String input = scanner.nextLine().trim().toUpperCase();
            if (input.equals("M") || input.equals("F")) {
                return input.charAt(0);
            } else {
                System.out.println("Неверный ввод. Пожалуйста, введите 'M' или 'F'.");
            }
        }
    }
}