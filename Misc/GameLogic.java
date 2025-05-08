package Misc;

import PlayersEnemies.*;
import Events.*;
import java.util.Random;
import java.util.Scanner;

public class GameLogic {
    private Player player;
    private Enemy enemy;
    private MainMap mainMap;
    private BattleMap battleMap;
    private Settlement settlement;
    private SettlementMap settlementMap;
    private boolean isInSettlement;
    private final Scanner scanner;
    private boolean settlementPrinted = false;

    public boolean shouldPrintSettlement() {
        return !settlementPrinted;
    }

    public void setSettlementPrinted(boolean printed) {
        this.settlementPrinted = printed;
    }

    public GameLogic(Player player, MainMap mainMap, BattleMap battleMap, Scanner scanner) {
        this.player = player;
        this.mainMap = mainMap;
        this.battleMap = battleMap;
        this.settlementMap = new SettlementMap();
        this.settlement = new Settlement();
        this.isInSettlement = false;
        this.scanner = scanner;
    }

    public boolean movePlayerOnMainMap(char direction) {
        int newX = player.getX();
        int newY = player.getY();

        switch (direction) {
            case 'W': newX--; break;
            case 'S': newX++; break;
            case 'A': newY--; break;
            case 'D': newY++; break;
            default: return false;
        }

        if (mainMap.getCell(newX, newY) == 'Д') {
            enterSettlement();
            return true;
        }

        if (newX < 0 || newX >= MainMap.SIZE || newY < 0 || newY >= MainMap.SIZE) {
            return false;
        }

        mainMap.setCell(player.getX(), player.getY(), 'O');
        player.setPosition(newX, newY);
        mainMap.setCell(newX, newY, 'P');

        if ((newX == 5 && newY == 5) || mainMap.isPlayerOnEvent(newX, newY)) {
            handleEvent(scanner);
        }

        return true;
    }

    public boolean movePlayerOnSettlementMap(char direction) {
        int newX = player.getX();
        int newY = player.getY();

        switch (direction) {
            case 'W': newX--; break;
            case 'S': newX++; break;
            case 'A': newY--; break;
            case 'D': newY++; break;
            default: return false;
        }

        if (newY < 0) {
            exitSettlement();
            return true;
        }

        if (newX < 0 || newX >= SettlementMap.SIZE || newY < 0 || newY >= SettlementMap.SIZE) {
            System.out.println("Невозможно двигаться в этом направлении!");
            return false;
        }

        char originalCell = settlementMap.getOriginalCell(player.getX(), player.getY());
        settlementMap.setCell(player.getX(), player.getY(), originalCell);

        player.setPosition(newX, newY);
        settlementMap.setCell(newX, newY, 'P');

        char cell = settlementMap.getOriginalCell(newX, newY);
        switch (cell) {
            case 'С': settlement.handleElderHouse(player); break;
            case 'К': settlement.handleBlacksmith(player); break;
            case 'Т': settlement.handleTavern(player); break;
        }

        settlementMap.printBoard(player);
        return true;
    }

    public boolean movePlayerOnBattleMap(char direction) {
        BattleMapHandler handler = new BattleMapHandler(battleMap);
        int newX = player.getX();
        int newY = player.getY();

        switch (direction) {
            case 'W': newX--; break;
            case 'S': newX++; break;
            case 'A': newY--; break;
            case 'D': newY++; break;
            case 'C': newX++; newY++; break;
            case 'Z': newX++; newY--; break;
            case 'Q': newX--; newY--; break;
            case 'E': newX--; newY++; break;
            default: return false;
        }

        if (!handler.isWithinBounds(newX, newY)) {
            return false;
        }

        char currentCell = battleMap.getCell(player.getX(), player.getY());
        handler.restoreCell(player.getX(), player.getY(), currentCell);

        player.setPosition(newX, newY);
        handler.updatePlayerPosition(newX, newY, player);
        return true;
    }

    public boolean movePlayer(char direction) {
        if (isInSettlement) {
            return movePlayerOnSettlementMap(direction);
        } else {
            return movePlayerOnMainMap(direction);
        }
    }
    public void enterQuestBattle(){
        if (QuestHandler.isPlayerOnQuest(player.getX(), player.getY())){
            QuestHandler.StartQuestBattle(player, scanner);

        }
    }

    public void enterSettlement() {
        if (!isInSettlement) {
            isInSettlement = true;
            System.out.println("Вы вошли в поселение.");
            mainMap.setCell(player.getX(), player.getY(), 'O');
            player.setPosition(5, 1);
            settlementMap.setCell(5, 1, 'P');
            settlementMap.printBoard(player);
        }
    }

    public void exitSettlement() {
        if (isInSettlement) {
            isInSettlement = false;
            System.out.println("Вы покинули поселение.");
            settlementMap.setCell(player.getX(), player.getY(), 'Д');
            player.setPosition(mainMap.getSettlementX(), mainMap.getSettlementY());
            mainMap.setCell(player.getX(), player.getY(), 'P');
        }
    }

    public void handleEvent(Scanner scanner) {
        if (mainMap.isEventProcessed(player.getX(), player.getY())) {
            System.out.println("Событие на этой клетке уже было обработано.");
            return;
        }

        EventTile event = createRandomEvent();
        if (event.getEventBehavior() instanceof BattleEvent) {
            ((BattleEvent)event.getEventBehavior()).trigger(player);  // Передаем scanner
        } else {
            event.triggerEvent(player);
        }

        mainMap.markEventAsProcessed(player.getX(), player.getY());
    }

    private void handleNpcEvent() {
        ItemCatalog catalog = new ItemCatalog();
        Item npcGift = catalog.getRandomItem(ItemType.CONSUMABLE);

        if (npcGift != null) {
            NeutralNpcEvent npcEvent = new NeutralNpcEvent(
                    "Старый мудрец",
                    "Добро пожаловать в наш мир, путник!",
                    npcGift
            );
            EventTile npcEventTile = new EventTile(
                    "Вы встретили кого-то дружелюбного!",
                    npcEvent,
                    '?'
            );
            npcEventTile.triggerEvent(player);
        } else {
            System.out.println("Незнакомец хотел что-то подарить, но у него ничего нет.");
        }
    }

    private void handleBattle(Enemy enemy, Scanner scanner) {
        battleMap.printBoard();

        while (player.isAlive() && enemy.isAlive()) {
            // Ход игрока
            player.startTurn();
            BattleManager.handlePlayerTurn(player, enemy, battleMap, scanner);
            battleMap.printBoard();

            if (!enemy.isAlive()) break;

            // Ход врага
            enemy.startTurn();
            BattleManager.handleEnemyTurn(enemy, player, battleMap);
            battleMap.printBoard();

            try { Thread.sleep(500); }
            catch (InterruptedException e) {}
        }

        if (!player.isAlive()) {
            System.out.println("Вы погибли...");
        } else {
            System.out.println("Вы победили " + enemy.getName() + "!");
        }
    }


    private EventTile createRandomEvent() {
        Random random = new Random();
        BattleManager battleManager = new BattleManager();

        EventBehavior eventBehavior = random.nextBoolean() ?
                new BattleEvent(battleManager, scanner) :
                new HealthBonusEvent();

        return new EventTile(
                eventBehavior instanceof BattleEvent ? "Нападение!" : "Зелье здоровья!",
                eventBehavior,
                eventBehavior instanceof BattleEvent ? '⚔' : '❤'
        );
    }


    public boolean isInSettlement() {
        return isInSettlement;
    }

    public boolean isPlayerOnEvent() {
        return mainMap.isPlayerOnEvent(player.getX(), player.getY());
    }

    public Enemy getEnemy() {
        return enemy;
    }

    public SettlementMap getSettlementMap() {
        return settlementMap;
    }


}