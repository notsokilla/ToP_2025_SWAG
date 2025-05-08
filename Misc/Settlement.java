package Misc;

import PlayersEnemies.*;
import java.util.Scanner;

public class Settlement {
    private ItemCatalog itemCatalog;
    private SettlementMap settlementMap;
    int elderquest = 0;
    private MainMap mainMap;

    public Settlement() {
        this.settlementMap = new SettlementMap();
        itemCatalog = new ItemCatalog(); // Инициализируем каталог предметов
    }

    public void enterSettlement(Player player) {
        System.out.println("Вы вошли в поселение.");
        // Здесь будет логика взаимодействия с поселением (пока пусто)
    }

    // Событие в доме старосты (С)
    public void handleElderHouse(Player player) {
        System.out.println("Вы зашли в дом старосты.");
        System.out.println("Староста: Добро пожаловать в нашу деревню, путник!");
        System.out.println("1. Спросить о деревне.");
        System.out.println("2. Я могу вам чем-нибудь помочь?.");
        System.out.println("3. Поблагодарить и уйти.");
        if (Quest.isCompleted()){
            System.out.println("4. Я убил его.");
        }

        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();

        if (choice == 1) {
            System.out.println("Староста: Наша деревня небольшая, но здесь есть всё, что нужно для жизни.");
        } else if ((choice == 2) && (elderquest == 0)) {
            elderquest = 1;
            // Выдача квеста
            Quest quest = new Quest("Мерзкий Поганец", "Убей мародера, который живет неподалеку от деревни, на юге. \n Он почти каждую ночь крадет все что под руку попадется из нашей деревни. \n Однажды дочь кузнеца попыталась ему помешать - он её заколол. \n Убей его, узнаешь его по крюку вместо руки.");
            player.addQuest(quest);
            QuestHandler.setQuestMarker(12, 7);
            System.out.println("Староста: Да, есть тут одно дельце - " + quest.getDescription());
        } else if ((choice == 2) && (elderquest == 1)){
            System.out.println("Староста: Мне больше не о чем тебя просить, путник.");
        } else if (choice == 3){
            System.out.println("Староста: Возвращайтесь, если понадобится помощь.");
        } else {
            System.out.println("Староста: Отлично, путник, ты очень помог нашей деревне. Вот наша благодарность тебе.");
            player.setCash(-200);
        }
    }

    // Событие в мастерской кузнеца (К)
    public void handleBlacksmith(Player player) {
        System.out.println("Вы зашли в мастерскую кузнеца.");
        System.out.println("Кузнец: Здравствуй, путник! Хочешь посмотреть на мои товары?");
        System.out.println("1. Посмотреть товары.");
        System.out.println("2. Уйти.");

        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();

        if (choice == 1) {
            // Кузнец предлагает 2 случайных предмета типа WEAPON
            Item item1 = itemCatalog.getRandomItem(ItemType.WEAPON);
            Item item2 = itemCatalog.getRandomItem(ItemType.HELMET);
            Item item3 = itemCatalog.getRandomItem(ItemType.CHESTPLATE);

            System.out.println("Кузнец: У меня есть для тебя два отличных оружия:");
            System.out.println("1. " + item1.getName() + " Стоимость: " + item1.getPrice());
            System.out.println("2. " + item2.getName() + " Стоимость: " + item2.getPrice());
            System.out.println("3. " + item3.getName() + " Стоимость: " + item3.getPrice());
            System.out.println("4. Уйти.");

            int itemChoice = scanner.nextInt();
            if (itemChoice == 1 && player.getCash() >= item1.getPrice()) {
                boolean success = player.addItemToInventory(item1);
                player.setCash(item1.getPrice());
                if (success) {
                    System.out.println("Вы купили " + item1.getName() + "!");
                } else {
                    System.out.println("Ваш инвентарь полон.");
                }
            } else if (itemChoice == 2 && player.getCash() >= item2.getPrice()) {
                boolean success = player.addItemToInventory(item2);
                player.setCash(item2.getPrice());
                if (success) {
                    System.out.println("Вы купили " + item2.getName() + "!");
                } else {
                    System.out.println("Ваш инвентарь полон.");
                }
            }else if (itemChoice == 3 && player.getCash() >= item3.getPrice()) {
                    boolean success = player.addItemToInventory(item3);
                    player.setCash(item3.getPrice());
                    if (success) {
                        System.out.println("Вы купили " + item3.getName() + "!");
                    } else {
                        System.out.println("Ваш инвентарь полон.");
                    }
            } else {
                System.out.println("Кузнец: Возвращайся, если передумаешь!");
            }
        } else {
            System.out.println("Кузнец: Возвращайся, если понадобится оружие!");
        }
    }

    // Событие в корчме (Т)
    public void handleTavern(Player player) {
        int nilhired = 0;
        System.out.println("Вы зашли в корчму.");
        if (player.getPartyRecruits() != null) {
            for (Recruit recruit : player.getPartyRecruits()) {
                String name = "Мрачный Нил";
                if (recruit.getName() == name) {
                    nilhired = 1;
                    System.out.println("1. Подойти к корчмарю.");
                    System.out.println("2. Уйти.");
                }
                else {
                    System.out.println("1. Подойти к одинокому путнику.");
                    System.out.println("2. Подойти к корчмарю.");
                    System.out.println("3. Уйти.");
                }
            }
        } else {
            System.out.println("1. Подойти к одинокому путнику.");
            System.out.println("2. Подойти к корчмарю.");
            System.out.println("3. Уйти.");
        }

        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();

        if ((choice == 1) && (nilhired == 0)) {
            // Одинокий путник
            Recruit loneTraveler = new Recruit("Мрачный Нил", 60, 20, 35);
            System.out.println("Одинокий путник: ...");
            System.out.println("1. Пойдешь со мной спасать мир от неведомых чудовищ?");
            System.out.println("2. Уйти.");

            int hireChoice = scanner.nextInt();
            if (hireChoice == 1) {
                // Пытаемся добавить одинокого путника в отряд
                if (player.addRecruitToParty(loneTraveler)) { // Используем метод addRecruitToParty
                    System.out.println(" Я пойду с тобой. Но только потому, что я слаб и легко поддаюсь давлению со стороны окружающих... *Вздох*");
                    System.out.println(loneTraveler.getName() +  " присоединился к вашему отряду!");
                } else {
                    System.out.println("Ваш отряд полон.");
                }
            } else {
                System.out.println("Одинокий путник: ...");
            }
        } else if (((choice == 2) && (nilhired == 0)) || (choice == 1)) {
            // Корчмарь предлагает зелья (старая логика)
            Item item1 = itemCatalog.getRandomItem(ItemType.CONSUMABLE);
            Item item2 = itemCatalog.getRandomItem(ItemType.CONSUMABLE);

            System.out.println("Корчмарь: У меня есть для тебя два полезных зелья, за счет заведения:");
            System.out.println("1. " + item1.getName());
            System.out.println("2. " + item2.getName());
            System.out.println("3. Уйти.");

            int itemChoice = scanner.nextInt();
            if (itemChoice == 1) {
                boolean success = player.addItemToInventory(item1);
                if (success) {
                    System.out.println("Вы купили " + item1.getName() + "!");
                } else {
                    System.out.println("Ваш инвентарь полон.");
                }
            } else if (itemChoice == 2) {
                boolean success = player.addItemToInventory(item2);
                if (success) {
                    System.out.println("Вы купили " + item2.getName() + "!");
                } else {
                    System.out.println("Ваш инвентарь полон.");
                }
            } else {
                System.out.println("Корчмарь: Возвращайся, если передумаешь!");
            }
        } else {
            System.out.println("Вы покинули корчму.");
        }
    }
}