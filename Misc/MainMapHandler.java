package Misc;

import PlayersEnemies.Player;

public class MainMapHandler implements MapHandler {
    private final MainMap mainMap;
    private final GameLogic gameLogic; // Добавлено поле


    public MainMapHandler(MainMap mainMap, GameLogic gameLogic) { // Обновленный конструктор
        this.mainMap = mainMap;
        this.gameLogic = gameLogic;
    }

    @Override
    public boolean isWithinBounds(int x, int y) {
        return x >= 0 && x < MainMap.SIZE && y >= 0 && y < MainMap.SIZE;
    }

    @Override
    public void updatePlayerPosition(int newX, int newY, Player player) {
        mainMap.setCell(player.getX(), player.getY(), 'O'); // Очистка старой позиции
        player.setPosition(newX, newY);
        mainMap.setCell(newX, newY, 'P'); // Установка новой позиции
        // Если игрок входит в деревню
        if (mainMap.getCell(newX, newY) == 'Д' && !gameLogic.isInSettlement())   {

            gameLogic.enterSettlement();
        } else {
            mainMap.setCell(newX, newY, 'P');
        }
    }

    @Override
    public void restoreCell(int x, int y, char currentCell) {
        if (currentCell != 'P') {
            mainMap.setCell(x, y, currentCell);
        } else {
            mainMap.setCell(x, y, 'O');
        }
    }


    @Override
    public boolean handleSpecialCases(int newX, int newY, Player player) {
        // Дополнительная логика не требуется
        return false;
    }
}