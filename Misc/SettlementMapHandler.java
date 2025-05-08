package Misc;

import PlayersEnemies.Player;

public class SettlementMapHandler implements MapHandler {
    private final SettlementMap settlementMap;
    private final GameLogic gameLogic;

    public SettlementMapHandler(SettlementMap settlementMap, GameLogic gameLogic) {
        this.settlementMap = settlementMap;
        this.gameLogic = gameLogic;
    }

    @Override
    public boolean isWithinBounds(int x, int y) {
        // Проверяем, находится ли игрок в пределах карты поселения
        return x >= 0 && x < SettlementMap.SIZE && y >= 0 && y < SettlementMap.SIZE;
    }

    @Override
    public void restoreCell(int x, int y, char currentCell) {
        // Восстанавливаем клетку, если она не занята игроком
        if (currentCell != 'P') {
            settlementMap.setCell(x, y, currentCell);
        } else {
            settlementMap.setCell(x, y, 'O'); // Очищаем клетку, если на ней был игрок
        }
    }

    @Override
    public void updatePlayerPosition(int newX, int newY, Player player) {
        // Очищаем старую позицию игрока
        settlementMap.setCell(player.getX(), player.getY(), 'O');

        // Устанавливаем новую позицию игрока
        player.setPosition(newX, newY);
        settlementMap.setCell(newX, newY, 'P');
    }

    @Override
    public boolean handleSpecialCases(int newX, int newY, Player player) {
        // Выход из поселения при движении влево (Y < 0)
        if (newY < 0) {
            gameLogic.exitSettlement();
            return true; // Специальный случай обработан
        }

        // Проверка на выход за пределы карты
        if (!isWithinBounds(newX, newY)) {
            System.out.println("Невозможно двигаться в этом направлении!");
            return true; // Специальный случай обработан
        }

        return false; // Специальный случай не обработан
    }
}