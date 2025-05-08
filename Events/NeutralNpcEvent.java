package Events;

import PlayersEnemies.Player;
import PlayersEnemies.Item;

import java.util.Scanner;

public class NeutralNpcEvent implements EventBehavior {
    private String npcName; // Имя NPC
    private String dialogue; // Диалог NPC
    private Item gift; // Подарок от NPC (может быть null)

    // Конструктор
    public NeutralNpcEvent(String npcName, String dialogue, Item gift) {
        this.npcName = npcName;
        this.dialogue = dialogue;
        this.gift = gift;
    }

    @Override
    public void trigger(Player player) {
        System.out.println("Вы встретили " + npcName + "!");
        System.out.println(npcName + " говорит: \"" + dialogue + "\"");

        if (gift != null) {
            System.out.println("1. Спасибо! (Принять подарок)");
            System.out.println("2. Нет, спасибо. (Отказаться от подарка)");

            // Игрок выбирает ответ
            Scanner scanner = new Scanner(System.in);
            int choice = 0;

            // Обработка ввода
            while (true) {
                try {
                    choice = scanner.nextInt();
                    if (choice == 1 || choice == 2) {
                        break; // Выход из цикла, если ввод корректен
                    } else {
                        System.out.println("Неверный выбор. Введите 1 или 2.");
                    }
                } catch (Exception e) {
                    System.out.println("Неверный ввод. Введите число 1 или 2.");
                    scanner.next(); // Очистка некорректного ввода
                }
            }

            if (choice == 1) {
                // Игрок принимает подарок
                System.out.println(npcName + " дарит вам " + gift.getName() + "!");
                boolean success = player.addItemToInventory(gift);
                if (success) {
                    System.out.println("Вы получили " + gift.getName() + "!");
                } else {
                    System.out.println("Ваш инвентарь полон, вы не можете взять " + gift.getName() + ".");
                }
            } else if (choice == 2) {
                // Игрок отказывается от подарка
                System.out.println(npcName + " выглядит немного расстроенным, но понимает ваш выбор.");
            }
        } else {
            System.out.println(npcName + " хотел что-то подарить, но у него ничего нет.");
        }

        System.out.println("Вы можете продолжить свое путешествие.");
    }
}