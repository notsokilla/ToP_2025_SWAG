package Misc;

import Events.EventBehavior;
import Events.EventTile;
import Events.QuestEvent;
import PlayersEnemies.Maradeur;
import PlayersEnemies.Player;

import java.util.Scanner;

import static Misc.MainMap.*;

public class QuestHandler {
    private static int questX = -1;
    private static int questY = -1;
    private static boolean questActive = false;
    // Добавить новые методы
    public static void setQuestMarker(int x, int y) {
        if (x >= 0 && x < SIZE && y >= 0 && y < SIZE) {
            questX = x;
            questY = y;
            questActive = true;
            setCell(x, y, 'Q');
        }
    }

    public void removeQuestMarker() {
        if (questX != -1 && questY != -1) {
            setCell(questX, questY, 'O');
            questX = -1;
            questY = -1;
            questActive = false;
        }
    }

    public boolean isQuestActive() {
        return questActive;
    }

    public static boolean isPlayerOnQuest(int playerX, int playerY) {
        return questActive && playerX == questX && playerY == questY;
    }

public static void StartQuestBattle(Player player,Scanner scanner) {
    BattleManager battleManager = new BattleManager();

    Maradeur questEnemy = new Maradeur(14, 14, 1, 1, 1, 1, 1, 1);
    QuestEvent questEvent = new QuestEvent(questEnemy, battleManager, scanner);
    questEvent.trigger(player);
    }
}
