
package Misc;

import PlayersEnemies.Player;

public class BattleMapHandler implements MapHandler {
    private final BattleMap battleMap;

    public BattleMapHandler(BattleMap battleMap) {
        this.battleMap = battleMap;
    }

    @Override
    public boolean isWithinBounds(int x, int y) {
        return x >= 0 && x < BattleMap.SIZE && y >= 0 && y < BattleMap.SIZE;
    }

    @Override
    public void restoreCell(int x, int y, char currentCell) {
        // Для BattleMap не требуется восстанавливать клетки
    }

    @Override
    public void updatePlayerPosition(int newX, int newY, Player player) {
        // Для BattleMap не требуется обновлять символы
    }

    @Override
    public boolean handleSpecialCases(int newX, int newY, Player player) {
        // Нет специальных условий
        return false;
    }
}