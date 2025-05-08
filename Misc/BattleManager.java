// BattleManager.java
package Misc;

import PlayersEnemies.*;

import java.util.NoSuchElementException;
import java.util.Scanner;
import java.lang.Math;

public class BattleManager {
    private static final int MOVE_COST = 1;
    private static final int ATTACK_COST = 2;


    public static boolean startBattle(Player player, Enemy enemy, BattleMap battleMap, Scanner scanner) {
        battleMap.placePlayerAndParty(player);
        battleMap.placeEnemy(enemy);
        System.out.print("На вашем пути " + enemy.getName());
        try {
            // Задержка 2 секунды (2000 миллисекунд)
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            // Обработка возможного прерывания
            Thread.currentThread().interrupt();
            System.err.println("Ошибка при выполнении задержки: " + e.getMessage());
        }
        System.out.print("\n"+ enemy.Avatar());
        battleMap.printBoard();

        while (player.isAlive() && enemy.isAlive()) {
            // Ход игрока
            player.startTurn();
            handlePlayerTurn(player, enemy, battleMap, scanner);
            battleMap.printBoard();
            try {
                // Задержка 2 секунды (2000 миллисекунд)
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // Обработка возможного прерывания
                Thread.currentThread().interrupt();
                System.err.println("Ошибка при выполнении задержки: " + e.getMessage());
            }

            if (!enemy.isAlive()) break;

            //Ход рекрутов
            if (player.getPartyRecruits() != null) {
                for (Recruit recruit : player.getPartyRecruits()) {
                    recruit.startTurn(); // Сброс AP для каждого рекрута
                }
            }
            handleRecruitsTurn(player, enemy, battleMap);
            battleMap.printBoard();
            try {
                // Задержка 2 секунды (2000 миллисекунд)
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // Обработка возможного прерывания
                Thread.currentThread().interrupt();
                System.err.println("Ошибка при выполнении задержки: " + e.getMessage());
            }

            if (!enemy.isAlive()) break;

            // Ход врага
            enemy.startTurn();
            handleEnemyTurn(enemy, player, battleMap);
            battleMap.printBoard();
            try {
                // Задержка 2 секунды (2000 миллисекунд)
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // Обработка возможного прерывания
                Thread.currentThread().interrupt();
                System.err.println("Ошибка при выполнении задержки: " + e.getMessage());
            }

            if (!enemy.isAlive()) break;
        }

        // Завершение боя
        if (!player.isAlive()) {
            System.out.println("Вы погибли...");
            return false;
        } else {
            System.out.println("Вы победили " + enemy.getName() + "!");
            return true;
        }
    }

    private static boolean handlePlayerAttack(Player player, Enemy enemy) {
        if (!isInPlayerAttackRange(player, enemy)) {
            System.out.println("Враг слишком далеко!");
            return false;
        }

        if (player.canAffordAction(ATTACK_COST)) {
            int damage = calculateDamage(player.getDamage(), enemy.getArmor());
            enemy.takeDamage(damage);
            player.spendActionPoints(ATTACK_COST);
            System.out.println("Вы нанесли " + damage + " урона!");
            return true;
        }

        System.out.println("Недостаточно очков для атаки!");
        return false;
    }

    static void handlePlayerTurn(Player player, Enemy enemy, BattleMap battleMap, Scanner scanner) {
        while (player.getCurrentActionPoints() > 0 && enemy.isAlive() && player.isAlive()) {
            System.out.println("\nОчки действий: " + player.getCurrentActionPoints());
            System.out.println("1. Движение (W/A/S/D)");
            System.out.println("2. Атака");
            System.out.println("3. Использовать предмет");
            System.out.println("4. Пропустить ход");
            System.out.print("Выберите действие: ");

            String input = "";
            if (scanner.hasNextLine()) { // Важная проверка!
                input = scanner.nextLine().trim();
            }

            switch (input) {
                case "1":
                    handlePlayerMovement(player, battleMap, scanner);
                    break;
                case "2":
                    if (handlePlayerAttack(player, enemy)) {
                        // После атаки просто продолжаем цикл
                        if (!enemy.isAlive()) {
                            System.out.println("Враг побежден!");
                            return; // Выход, если враг мертв
                        } else {
                            break; // Завершаем ход после атаки
                        }
                    }
                    break;
                case "3":
                    Item[] inventory = player.getInventory();
                    for (int i = 0; i < inventory.length; i++) {
                        System.out.println(i + ": " + (inventory[i] != null ? inventory[i].getName() : "Пусто"));
                    }
                    try {
                        String input1 = scanner.nextLine().trim().toUpperCase();

                        if (input1.isEmpty()) continue;

                        System.out.println("\nКоманды: [E <слот>] - экипировать/использовать, [U <тип>] - снять");
                        System.out.print("Введите команду: ");
                        switch (input1.charAt(0)) {
                            case 'E':
                                GameManager.handleEquipCommand(player, input1);
                                break;
                            case 'U':
                                GameManager.handleUnequipCommand(player, input1);
                                break;
                        }
                    } catch (NoSuchElementException e) {
                        System.out.println("Ошибка ввода.");
                    }

                case "4":
                    player.spendActionPoints(player.getCurrentActionPoints());
                    return;
                default:
                    System.out.println("Неверный ввод!");
            }

            battleMap.printBoard();
            // Проверяем, остались ли AP после действия
            if (player.getCurrentActionPoints() <= 0) {
                System.out.println("Очки действий закончились!");
                return;
            }
        }
    }

    private static void handlePlayerMovement(Player player, BattleMap battleMap, Scanner scanner) {
        System.out.print("Введите направление (W/A/S/D/Q/E/Z/C): ");
        String direction = scanner.nextLine().trim().toUpperCase();

        if (direction.length() == 1 && "WASDQEZC".contains(direction)) {
            if (player.canAffordAction(MOVE_COST)) {
                if (battleMap.movePlayer(direction.charAt(0))) {
                    player.spendActionPoints(MOVE_COST);
                } else {
                    System.out.println("Нельзя двигаться в этом направлении!");
                }
            } else {
                System.out.println("Недостаточно очков действий!");
            }
        } else {
            System.out.println("Неверное направление!");
        }
    }

    private static void handleRecruitsTurn(Player player, Enemy enemy, BattleMap battleMap) {
        if (player.getPartyRecruits() == null) return;

        for (Recruit recruit : player.getPartyRecruits()) {
            if (recruit.isAlive()) {
                while (recruit.getCurrentActionPoints() > 0 && enemy.isAlive()) {
                    if (isInAttackRange(recruit, enemy) && recruit.getCurrentActionPoints() >= 2) {
                        int damage = calculateDamage(recruit.getDamage(), enemy.getArmor());
                        enemy.takeDamage(damage);
                        recruit.spendActionPoints(ATTACK_COST);
                        System.out.printf("%s нанес %d урона!%n", recruit.getName(), damage);
                        return;
                    } else {
                        // Движение по направлению к врагу (логика как у врага)
                        int dx = Integer.compare(enemy.getX(), recruit.getX());
                        int dy = Integer.compare(enemy.getY(), recruit.getY());

                        int newX = recruit.getX() + dx;
                        int newY = recruit.getY() + dy;

                        if (battleMap.isValidPosition(newX, newY) && recruit.canAffordAction(MOVE_COST)) {
                            recruit.setPosition(newX, newY);
                            recruit.spendActionPoints(MOVE_COST);
                            System.out.println(recruit.getName() + " движется к врагу");
                        } else {
                            // Не может двигаться, пропускаем ход
                            recruit.spendActionPoints(recruit.getCurrentActionPoints());
                        }
                    }
                }
            }
        }

    }

    static void handleEnemyTurn(Enemy enemy, Player player, BattleMap battleMap) {
        final int ATTACK_COST = 2;
        final int MOVE_COST = 1;

        while (enemy.getCurrentActionPoints() > 0 && player.isAlive()) {
            // Проверка атаки игрока
            if (isInAttackRange(enemy, player)) {
                int damage = calculateDamage(enemy.getDamage(), player.getArmor());
                player.takeDamage(damage);
                enemy.spendActionPoints(ATTACK_COST);
                System.out.println(enemy.getName() + " атаковал вас!");
                return;
            }

            // Проверка атаки рекрутов
            if (player.getPartyRecruits() != null && !player.getPartyRecruits().isEmpty()) {
                for (Recruit recruit : player.getPartyRecruits()) {
                    if (isInAttackRange(enemy, recruit) && recruit.isAlive()) {
                        int damage = calculateDamage(enemy.getDamage(), recruit.getArmor());
                        recruit.takeDamage(damage);
                        enemy.spendActionPoints(ATTACK_COST);
                        System.out.println(enemy.getName() + " атаковал " + recruit.getName() + "!");
                        return;
                    }
                }
            }

            // Логика движения
            int dx = Integer.compare(player.getX(), enemy.getX());
            int dy = Integer.compare(player.getY(), enemy.getY());
            int newX = enemy.getX() + dx;
            int newY = enemy.getY() + dy;

            if (battleMap.isValidPosition(newX, newY)) {
                enemy.setPosition(newX, newY);
                enemy.spendActionPoints(MOVE_COST);
            } else {
                // Если движение невозможно - заканчиваем ход
                enemy.spendActionPoints(enemy.getCurrentActionPoints());
            }
        }
    }

    private static boolean isInAttackRange(Entity attacker, Entity target) {
        int distanceX = Math.abs(attacker.getX() - target.getX());
        int distanceY = Math.abs(attacker.getY() - target.getY());
        return distanceX <= 1 && distanceY <= 1;
    }
    private  static  boolean isInPlayerAttackRange(Player player, Entity target) {
        int distanceX = Math.abs(player.getX() - target.getX());
        int distanceY = Math.abs(player.getY() - target.getY());
        return distanceX <= player.getAttackRange() && distanceY <= player.getAttackRange();
    }

    private static int calculateDamage(int damage, int armor) {
        double mitigation = (double) armor / (armor + 100);
        int effectiveDamage = (int) (damage * (1 - mitigation));
        return Math.max(1, effectiveDamage);
    }
}