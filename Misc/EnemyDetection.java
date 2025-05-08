package Misc;
import PlayersEnemies.*;
import Events.*;

import java.util.Random;

public class EnemyDetection {
    private static final Random random = new Random();

    public static boolean detectPlayer(Enemy enemy) {
        if (enemy == null) {
            return false; // Если враг не существует, он не может заметить игрока
        }
        int chance = random.nextInt(101);

        // Если случайное число меньше или равно sneak, противник замечает игрока
        return chance <= enemy.getSneak();
    }
}