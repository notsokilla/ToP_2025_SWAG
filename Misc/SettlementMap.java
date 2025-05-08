package Misc;


import PlayersEnemies.Player;

public class SettlementMap {
    public static final int SIZE = 10; // Размер карты поселения
    private char[][] board;
    private char[][] originalBoard; // Массив для хранения оригинальных символов

    public SettlementMap() {
        board = new char[SIZE][SIZE];
        originalBoard = new char[SIZE][SIZE]; // Инициализируем массив оригинальных символов
        initializeBoard();
    }

    // Инициализация карты поселения
    public void initializeBoard() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                board[i][j] = 'O'; // 'O' - пустая клетка
                originalBoard[i][j] = 'O'; // Сохраняем оригинальный символ
            }
        }

        // Размещаем вход в поселение (левый край)
        for (int i = 0; i < SIZE; i++) {
            board[i][0] = 'Д'; // Символ деревни
            originalBoard[i][0] = 'Д'; // Сохраняем оригинальный символ
        }

        // Размещаем дом старосты (С)
        board[2][2] = 'С'; // Пример координат для дома старосты
        originalBoard[2][2] = 'С'; // Сохраняем оригинальный символ

        // Размещаем мастерскую кузнеца (К)
        board[4][4] = 'К'; // Пример координат для кузнеца
        originalBoard[4][4] = 'К'; // Сохраняем оригинальный символ

        // Размещаем корчму (Т)
        board[6][6] = 'Т'; // Пример координат для корчмы
        originalBoard[6][6] = 'Т'; // Сохраняем оригинальный символ
    }

    // Установка символа в клетку
    public void setCell(int x, int y, char symbol) {
        if (x >= 0 && x < SIZE && y >= 0 && y < SIZE) {
            board[x][y] = symbol;
        } else {
            System.out.println("Ошибка: координаты выходят за пределы поля.");
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

    // Получение оригинального символа в клетке
    public char getOriginalCell(int x, int y) {
        if (x >= 0 && x < SIZE && y >= 0 && y < SIZE) {
            return originalBoard[x][y];
        } else {
            System.out.println("Ошибка: координаты выходят за пределы поля.");
            return ' ';
        }
    }

    // Отображение карты поселения
    public void printBoard(Player player) {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                char cell = board[i][j];
                if (i == player.getX() && j == player.getY()) {
                    // Игрок выводится белым цветом
                    System.out.print(ConsoleColors.WHITE + 'P' + " " + ConsoleColors.RESET);
                } else if (cell == 'Д' || cell == 'С' || cell == 'К' || cell == 'Т') {
                    // Поселение и дома выводится синим цветом
                    System.out.print(ConsoleColors.BLUE + cell + " " + ConsoleColors.RESET);
                } else {
                    // Поле выводится зеленым цветом
                    System.out.print(ConsoleColors.GREEN + cell + " " + ConsoleColors.RESET);
                }
            }
            System.out.println();
        }
    }
}