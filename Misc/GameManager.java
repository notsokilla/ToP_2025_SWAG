package Misc;

import PlayersEnemies.*;
import Events.*;
import java.util.Scanner;
import java.util.NoSuchElementException;

public class GameManager {
    public static void playGame(Scanner scanner, Player player, MainMap mainMap, BattleMap battleMap, Settlement settlement) {
        GameLogic gameLogic = new GameLogic(player, mainMap, battleMap, scanner); // Передаем scanner

        while (player.isAlive()) {
            if (gameLogic.isInSettlement()) {
                // Вывод карты поселения ТОЛЬКО при входе
                if (gameLogic.shouldPrintSettlement()) {
                }
            }
            else if (QuestHandler.isPlayerOnQuest(player.getX(), player.getY())){
                gameLogic.enterQuestBattle();
            }
            else {
                mainMap.printBoard(player);
            }

            System.out.println("\nКоманды: [W/A/S/D] - движение,, [E <слот>] - экипировать, [U <тип>] - снять, [T] - статус, [Q] - выход");
            System.out.print("Введите команду: ");

            try {
                String input = scanner.nextLine().trim().toUpperCase();

                if (input.isEmpty()) continue;

                if (input.equals("Q") || input.equals("QUIT")) {
                    System.out.println("Игра завершена.");
                    return;
                }

                switch (input.charAt(0)) {
                    case 'W': case 'A': case 'S': case 'D':
                        if (gameLogic.movePlayer(input.charAt(0))) {
                            if (!gameLogic.isInSettlement() && gameLogic.isPlayerOnEvent()) {
                                gameLogic.handleEvent(scanner);
                            }
                        } else {
                            System.out.println("Невозможно двигаться в этом направлении!");
                        }
                        break;
                    case 'E':
                        handleEquipCommand(player, input);
                        break;
                    case 'U':
                        handleUnequipCommand(player, input);
                        break;
                    case 'T':
                        player.showStatus();
                        break;
                    default:
                        System.out.println("Неизвестная команда!");
                }
            } catch (NoSuchElementException e) {
                System.out.println("Ошибка ввода. Завершение игры.");
                return;
            }
        }
        System.out.println(player.getPlayerName() + " погиб!");
        gameLogic.handleEvent(scanner);

    }

    public static String getItemName(Item item) {
        return (item != null) ? item.getName() : "Пусто";
    }

    static void handleEquipCommand(Player player, String input) {
        String[] parts = input.split("\\s+");
        if (parts.length < 2) {
            System.out.println("Используйте: E <номер_слота> или EQUIP <номер_слота>");
            return;
        }

        try {
            int slot = Integer.parseInt(parts[1]);
            if (slot < 0 || slot >= player.getInventory().length) {
                System.out.println("Неверный номер слота! Доступные слоты: 0-" + (player.getInventory().length-1));
                return;
            }

            Item item = player.getInventory()[slot];
            if (item == null) {
                System.out.println("Этот слот пуст!");
                return;
            }

            if (player.equipItem(item)) {
                player.getInventory()[slot] = null;
                System.out.println(item.getName() + " успешно экипирован!");
            } else {
                System.out.println("Не удалось экипировать " + item.getName() + "!");
            }
        } catch (NumberFormatException e) {
            System.out.println("Неверный формат номера слота! Введите число.");
        }
    }

    static void handleUnequipCommand(Player player, String input) {
        String[] parts = input.split("\\s+");
        if (parts.length < 2) {
            System.out.println("Используйте: U <тип> или UNEQUIP <тип>");
            System.out.println("Доступные типы: HELMET, CHESTPLATE, LEGGINGS, BOOTS, WEAPON");
            return;
        }

        try {
            ItemType type = ItemType.valueOf(parts[1].toUpperCase());
            if (player.unequipItem(type)) {
                System.out.println("Предмет успешно снят!");
            } else {
                System.out.println("Не удалось снять предмет этого типа!");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Неверный тип предмета! Доступные типы:");
            for (ItemType t : ItemType.values()) {
                System.out.println("- " + t.name());
            }
        }
    }

    public static boolean askToPlayAgain(Scanner scanner) {
        try {
            while (true) {
                System.out.print("Хотите начать заново? (yes/no): ");
                String input = scanner.nextLine().trim().toLowerCase();

                if (input.equals("yes")) {
                    return true;
                } else if (input.equals("no")) {
                    return false;
                }

                System.out.println("Неверный ввод! Введите 'yes' или 'no'");
            }
        } catch (Exception e) {
            System.out.println("Ошибка ввода. Игра будет завершена.");
            return false;
        }
    }

}