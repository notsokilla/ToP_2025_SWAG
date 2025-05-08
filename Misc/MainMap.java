package Misc;

import PlayersEnemies.*;
import Events.*;

public class MainMap {
    public static final int SIZE = 15;
    private boolean[][] eventProcessed; // Флаги для отслеживания обработанных событий
    private static char[][] board;
    private int eventX, eventY; // Координаты события
    private int settlementX, settlementY; // Координаты поселения
    private int exitx, exity;
    ItemCatalog itemCatalog = new ItemCatalog();

    public MainMap() {
        board = new char[SIZE][SIZE];
        eventProcessed = new boolean[SIZE][SIZE]; // Инициализируем массив флагов
        initializeBoard();
    }


    public void removeEvent(int x, int y) {
        if (x >= 0 && x < SIZE && y >= 0 && y < SIZE) {
            if (board[x][y] == '?') {
                board[x][y] = 'O'; // Удаляем событие, заменяя его на пустую клетку
            }
        } else {
            System.out.println("Ошибка: координаты выходят за пределы поля.");
        }
    }


    // Метод для проверки, было ли событие на клетке обработано
    public boolean isEventProcessed(int x, int y) {
        if (x >= 0 && x < SIZE && y >= 0 && y < SIZE) {
            return eventProcessed[x][y]; // Возвращаем значение флага
        } else {
            System.out.println("Ошибка: координаты выходят за пределы поля.");
            return false;
        }
    }

    // Метод для пометки события как обработанного
    public void markEventAsProcessed(int x, int y) {
        if (x >= 0 && x < SIZE && y >= 0 && y < SIZE) {
            eventProcessed[x][y] = true; // Помечаем событие как обработанное
        } else {
            System.out.println("Ошибка: координаты выходят за пределы поля.");
        }
    }

    // Инициализация основной карты
    public void initializeBoard() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                board[i][j] = 'O'; // 'O' - пустая клетка
                eventProcessed[i][j] = false; // Изначально все события не обработаны
            }
        }
        exitx = 14;
        exity = 14;
        board[exitx][exity] = 'X';

        // Размещаем поселение
        settlementX = 7;
        settlementY = 7;
        board[settlementX][settlementY] = 'Д'; // Поселение в центре карты

        // Размещаем события
        eventX = 2;
        eventY = 2;
        board[eventX][eventY] = '?'; // Событие 1

        // Новое событие с нейтральным NPC
        int npcEventX = 5;
        int npcEventY = 5;
        board[npcEventX][npcEventY] = '?'; // Событие с NPC

        // Создаем событие с нейтральным NPC
        Item healthPotion = itemCatalog.getItem(ItemType.CONSUMABLE, "Зелье здоровья");
        NeutralNpcEvent npcEvent = new NeutralNpcEvent("Старый мудрец", "Добро пожаловать в наш мир, путник!", healthPotion);
    }
    public boolean isPlayerOnSettlement(int playerX, int playerY) {
        return playerX == settlementX && playerY == settlementY;
    }

    // Геттеры для координат события
    public int getEventX() {
        return eventX;
    }

    public int getEventY() {
        return eventY;
    }

    public int getSettlementX() {
        return settlementX;
    }

    public int getSettlementY() {
        return settlementY;
    }

    // Отображение основной карты
    public void printBoard(Player player) {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                char cell = board[i][j];
                if (i == player.getX() && j == player.getY()) {
                    // Игрок выводится белым цветом
                    System.out.print(ConsoleColors.WHITE + 'P' + " " + ConsoleColors.RESET);
                } else if (cell == 'Д') {
                    // Поселение выводится синим цветом
                    System.out.print(ConsoleColors.BLUE + cell + " " + ConsoleColors.RESET);
                } else if (cell == 'Q') {
                    // Квест выводится
                    System.out.print(ConsoleColors.YELLOW + cell + " " + ConsoleColors.RESET);
                } else if (cell == '?') {
                    // Событие выводится желтым цветом
                    System.out.print(ConsoleColors.YELLOW + cell + " " + ConsoleColors.RESET);
                } else if (cell == 'X') {
                    // Клетка "X" выводится красным цветом
                    System.out.print(ConsoleColors.RED + cell + " " + ConsoleColors.RESET);
                } else {
                    // Поле выводится зеленым цветом
                    System.out.print(ConsoleColors.GREEN + cell + " " + ConsoleColors.RESET);
                }
            }
            System.out.println();
        }
    }

    // Размещение игрока на основной карте
    public void placePlayer(int x, int y, Player player) {
        if (isCellEmpty(x, y)) {
            setCell(x, y, 'P');
            player.setPosition(x, y);
        } else {
            System.out.println("Клетка уже занята!");
        }
    }

    // Установка символа в клетку
    public static void setCell(int x, int y, char symbol) {
        if (x >= 0 && x < SIZE && y >= 0 && y < SIZE) {
            // Если клетка — деревня, не перезаписываем ее
            if (board[x][y] == 'Д') {
                return;
            }

            if (board[x][y] == 'X') {
                return;
            }
            board[x][y] = symbol;
        } else {
            System.out.println("Ошибка: координаты выходят за пределы поля.");
        }
    }

    // Проверка, пуста ли клетка
    public boolean isCellEmpty(int x, int y) {
        if (x >= 0 && x < SIZE && y >= 0 && y < SIZE) {
            return board[x][y] == 'O';
        } else {
            System.out.println("Ошибка: координаты выходят за пределы поля.");
            return false;
        }
    }

    // Получение символа в клетке
    public char getCell(int x, int y) {
        if (x >= 0 && x < SIZE && y >= 0 && y < SIZE) {
            return board[x][y];
        } else {
            System.out.println("Ошибка: координаты выходят за пределы поля.");
            return ' ';
        }
    }

    // Проверка, находится ли игрок на событии
    public boolean isPlayerOnEvent(int playerX, int playerY) {
        return playerX == eventX && playerY == eventY;
    }



    // Проверка, находится ли игрок в поселении
    public boolean isPlayerInSettlement(int playerX, int playerY) {
        return playerX == settlementX && playerY == settlementY;
    }

    public boolean isExitReached(int x, int y) {
        return x == SIZE - 1 && y == SIZE - 1; // Выход на правом или нижнем краю
    }
}