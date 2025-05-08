package Misc;
import PlayersEnemies.*;
import Events.*;

public class StoryManager {
    /**
     * Выводит вступительный сюжет перед началом игры.
     */
    public static void printIntro() {
        System.out.println("=========================================");
        System.out.println("          Добро пожаловать в игру!       ");
        System.out.println("=========================================");
        System.out.println("Вы очнулись в густом лесу. Вокруг тишина,");
        System.out.println("лишь шелест листьев и далекие крики птиц.");
        System.out.println("Ваша задача — выбраться из леса и найти путь");
        System.out.println("к спасению. Но будьте осторожны: в лесу");
        System.out.println("вас поджидают опасные существа...");
        System.out.println("=========================================");
        System.out.println();
    }

    /**
     * Выводит заключительный сюжет после победы.
     */
    public static void printOutro() {
        System.out.println("=========================================");
        System.out.println("          Поздравляем с победой!         ");
        System.out.println("=========================================");
        System.out.println("Вы смогли преодолеть все испытания и");
        System.out.println("победить врага. Лес больше не кажется");
        System.out.println("таким страшным, и вы видите свет вдалеке.");
        System.out.println("Ваше приключение подошло к концу, но");
        System.out.println("впереди вас ждут новые вызовы...");
        System.out.println("=========================================");
        System.out.println();
    }
}