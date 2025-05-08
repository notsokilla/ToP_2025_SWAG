package PlayersEnemies;

import Misc.*;
import Events.*;

import java.util.ArrayList;
import java.util.List;

import static Misc.GameManager.getItemName;

public abstract class Player extends Entity {
    private int x;
    private int y;
    private int speed;
    private int health;
    private int armor;
    private int mana;
    private int damage;
    private String playerName;
    private Item[] inventory = new Item[3];
    private Item helmet;
    private Item chestplate;
    private Item leggings;
    private Item boots;
    private Item weapon;
    private int maxHealth;
    private List<Quest> quests;
    private Party party; // Отряд игрока
    private int actionPoints;
    private final int maxActionPoints = 5;
    private static int cash = 100;
    private int attackRange = 1;

    // Конструктор с параметрами
    public Player(int x, int y, int speed, int health, int armor, int mana, int damage, String playerName) {
        super(0, 0);
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.health = health;
        this.maxHealth = health; // Инициализация maxHealth
        this.armor = armor;
        this.mana = mana;
        this.damage = damage;
        this.playerName = playerName;
        this.party = new Party(); // Инициализируем отряд
        this.quests = new ArrayList<>(); // Инициализируем список квестов
        this.attackRange = getAttackRange();

    }
    @Override
    public int getCurrentActionPoints() {
        return actionPoints;
    }

    @Override
    public void startTurn() {
        this.actionPoints = maxActionPoints;
    }

    @Override
    public void spendActionPoints(int amount) {
        if (amount <= actionPoints) {
            this.actionPoints -= amount;
        }
    }

    @Override
    public boolean canAffordAction(int cost) {
        return actionPoints >= cost;
    }

    // Геттер для maxHealth
    public int getMaxHealth() {
        return maxHealth;
    }

    // Сеттер для maxHealth
    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    // Метод для экипировки предмета
    public boolean equipItem(Item item) {
        ItemType type = item.getType();
        Item currentItem = getItemByType(type);

        if (currentItem != null) {
            if (!addItemToInventory(currentItem)) {
                return false; // Нет места в сумке
            }
            currentItem.removeEffect(this);
        }

        setItemToSlot(item, type);
        item.applyEffect(this);
        return true;
    }

    // Метод для снятия предмета
    public boolean unequipItem(ItemType type) {
        Item item = getItemByType(type);
        if (item == null) return false;

        if (addItemToInventory(item)) {
            setItemToSlot(null, type);
            item.removeEffect(this);
            return true;
        }
        return false; // Нет места в сумке
    }

    // Вспомогательные методы
    private Item getItemByType(ItemType type) {
        switch (type) {
            case HELMET: return helmet;
            case CHESTPLATE: return chestplate;
            case LEGGINGS: return leggings;
            case BOOTS: return boots;
            case WEAPON: return weapon;
            default: return null;
        }
    }

    private void setItemToSlot(Item item, ItemType type) {
        switch (type) {
            case HELMET: helmet = item; break;
            case CHESTPLATE: chestplate = item; break;
            case LEGGINGS: leggings = item; break;
            case BOOTS: boots = item; break;
            case WEAPON: weapon = item; break;
        }
    }

    // Добавление предмета в инвентарь
    public boolean addItemToInventory(Item item) {
        for (int i = 0; i < inventory.length; i++) {
            if (inventory[i] == null) {
                inventory[i] = item;
                return true;
            }
        }
        return false;
    }

    // Геттеры для инвентаря и экипировки
    public Item[] getInventory() {
        return inventory;
    }

    public Item getHelmet() { return helmet; }
    public Item getChestplate() { return chestplate; }
    public Item getLeggings() { return leggings; }
    public Item getBoots() { return boots; }
    public Item getWeapon() { return weapon; }

    // Геттеры и сеттеры для основных параметров
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getSpeed() {
        return speed;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = Math.min(health, maxHealth); // Здоровье не может превышать maxHealth
    }

    public int getArmor() {
        return armor;
    }

    public void setArmor(int armor) {
        this.armor = armor;
    }

    public int getMana() {
        return mana;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    // Метод для получения урона
    public void takeDamage(int damage) {
        health -= damage;
        if (health < 0) health = 0;
        System.out.printf("Получено %d урона (с учетом брони). Здоровье: %d/%d%n",
                damage, health, maxHealth);
    }

    // Проверка, жив ли игрок
    public boolean isAlive() {
        return health > 0;
    }

    // Абстрактный метод для отображения аватара
    public abstract String StartAvatar();

    public void addQuest(Quest quest) {
        quests.add(quest);
        System.out.println("Получен новый квест: " + quest.getTitle());
    }

    public void completeQuest(Quest quest) {
        quest.setCompleted(true);
    }

    public void printQuests() {
        if (quests.isEmpty()) {
            System.out.println("У вас нет активных квестов.");
        } else {
            System.out.println("Ваши квесты:");
            for (Quest quest : quests) {
                System.out.println(quest);
            }
        }
    }
    /**
     * Добавляет наемника в отряд.
     * @param recruit Наемник для добавления.
     * @return true, если наемник успешно добавлен, иначе false (если отряд полон).
     */
    public boolean addRecruitToParty(Recruit recruit) {
        return party.addRecruit(recruit);
    }

    /**
     * Удаляет наемника из отряда.
     * @param recruit Наемник для удаления.
     */
    public void removeRecruitFromParty(Recruit recruit) {
        party.removeRecruit(recruit);
    }

    /**
     * Возвращает текущий состав отряда.
     * @return Список наемников в отряде.
     */
    public List<Recruit> getPartyRecruits() {
        return party.getRecruits();
    }




    /**
     * Отображает текущий состав отряда.
     */
    public void printParty() {
        party.printParty();
    }


    public boolean canAfford(int cost) {
        return actionPoints >= cost;
    }

    public static int getCash(){
        return cash;
    }
    public void setCash(int price){
        this.cash = cash - price;
    }
    public void setCashReward(int reward){
        this.cash = cash + reward;
    }

    public void showStatus() {
        // Отображение характеристик игрока
        System.out.println("\n=== ХАРАКТЕРИСТИКИ ИГРОКА ===");
        System.out.println("Имя: " + getPlayerName());
        System.out.println("Здоровье: " + getHealth() + " / " + getMaxHealth());
        System.out.println("Урон: " + getDamage());
        System.out.println("Защита: " + getArmor());
        System.out.println("AP: " + actionPoints + "/" + maxActionPoints);

        // Отображение инвентаря
        System.out.println("\n=== ИНВЕНТАРЬ ===");
        System.out.println("Шлем: " + (getHelmet() != null ? getHelmet().getName() : "Пусто"));
        System.out.println("Нагрудник: " + (getChestplate() != null ? getChestplate().getName() : "Пусто"));
        System.out.println("Поножи: " + (getLeggings() != null ? getLeggings().getName() : "Пусто"));
        System.out.println("Сапоги: " + (getBoots() != null ? getBoots().getName() : "Пусто"));
        System.out.println("Оружие: " + (getWeapon() != null ? getWeapon().getName() : "Пусто"));

        System.out.println("\nСумка (3 слота):");
        System.out.println("\nВаши богатства: " + getCash() + " медяков");
        Item[] inventory = getInventory();
        for (int i = 0; i < inventory.length; i++) {
            System.out.println(i + ": " + (inventory[i] != null ? inventory[i].getName() : "Пусто"));
        }

        // Отображение отряда
        System.out.println("\n=== ОТРЯД ===");
        printParty();

        // Отображение квестов
        System.out.println("\n=== КВЕСТЫ ===");
        printQuests();
    }

    public void setAttackRange(int range) {
        this.attackRange = range;
    }

    public int getAttackRange() {
        return attackRange;
    }

    public boolean hasActiveQuest(String title) {
        return quests.stream()
                .anyMatch(q -> q.getTitle().equals(title) && !q.isCompleted());
    }

    public Quest getQuest(String title) {
        return quests.stream()
                .filter(q -> q.getTitle().equals(title))
                .findFirst()
                .orElse(null);
    }

    public boolean completeQuest(String title) {
        Quest quest = getQuest(title);
        if (quest != null && !quest.isCompleted()) {
            quest.setCompleted(true);
            return true;
        }
        return false;
    }

}