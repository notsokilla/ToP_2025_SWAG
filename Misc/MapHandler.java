
package Misc;

import PlayersEnemies.Player;

public interface MapHandler {
    boolean isWithinBounds(int x, int y);
    void restoreCell(int x, int y, char currentCell);
    void updatePlayerPosition(int newX, int newY, Player player);
    boolean handleSpecialCases(int newX, int newY, Player player); // Возвращает boolean
}