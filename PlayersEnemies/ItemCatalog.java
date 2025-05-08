package PlayersEnemies;

import java.util.*;

public class ItemCatalog {
    private Map<ItemType, Map<String, Item>> catalog;

    public ItemCatalog() {
        catalog = new HashMap<>();
        initializeCatalog();
    }

    private void initializeCatalog() {
        // Оружие
        Map<String, Item> weapons = new HashMap<>();

        // Меч
        weapons.put("Меч из Стали", new Item("Меч из Стали", ItemType.WEAPON, 50) {
            @Override
            public void applyEffect(Player player) {
                player.setDamage(player.getDamage() + 10);
            }

            @Override
            public void removeEffect(Player player) {
                player.setDamage(player.getDamage() - 10);
            }
        });

        // Лук из Ясеня
        weapons.put("Лук из Ясеня", new Item("Лук из Ясеня", ItemType.WEAPON, 60) {
            @Override
            public void applyEffect(Player player) {
                player.setDamage(player.getDamage() + 10);
                player.setAttackRange(3); // Устанавливаем дальность атаки 3
            }

            @Override
            public void removeEffect(Player player) {
                player.setDamage(player.getDamage() - 10);
                player.setAttackRange(1); // Возвращаем стандартную дальность
            }
        });

        catalog.put(ItemType.WEAPON, weapons);

        // Броня
        Map<String, Item> armor = new HashMap<>();

        armor.put("Кожаный доспех", new Item("Кожаный доспех", ItemType.CHESTPLATE, 35) {
            @Override
            public void applyEffect(Player player) {
                player.setArmor(player.getArmor() + 4);
            }

            @Override
            public void removeEffect(Player player) {
                player.setArmor(player.getArmor() - 4);
            }
        });

        armor.put("Стальной шлем", new Item("Стальной шлем", ItemType.HELMET, 40) {
            @Override
            public void applyEffect(Player player) {
                player.setArmor(player.getArmor() + 3);
            }

            @Override
            public void removeEffect(Player player) {
                player.setArmor(player.getArmor() - 3);
            }
        });

        catalog.put(ItemType.HELMET, armor);
        catalog.put(ItemType.CHESTPLATE, armor);

        // Зелья
        Map<String, Item> potions = new HashMap<>();

        potions.put("Зелье исцеления", new Item("Зелье исцеления", ItemType.CONSUMABLE, 20) {
            @Override
            public void applyEffect(Player player) {
                if (player.getHealth() < player.getMaxHealth()) {
                    player.setHealth(Math.min(player.getHealth() + 20, player.getMaxHealth()));
                }
                else{
                    if (player.getPartyRecruits() != null){
                        for (Recruit recruit : player.getPartyRecruits()) {
                            if (!recruit.isAlive()){
                                recruit.setHealth(recruit.getHealth() + 20);
                            }
                            else{
                                recruit.setHealth(Math.min(recruit.getHealth() + 20, recruit.getMaxHealth()));
                            }
                        }
                    }
                }
            }

            @Override
            public void removeEffect(Player player) {}
        });

        potions.put("Зелье силы", new Item("Зелье силы", ItemType.CONSUMABLE, 30) {
            @Override
            public void applyEffect(Player player) {
                player.setDamage(player.getDamage() + 5);
            }

            @Override
            public void removeEffect(Player player) {
                player.setDamage(player.getDamage() - 5);
            }
        });

        catalog.put(ItemType.CONSUMABLE, potions);
    }

    public Item getItem(ItemType type, String name) {
        return catalog.getOrDefault(type, Collections.emptyMap()).get(name);
    }

    public Map<String, Item> getItemsByType(ItemType type) {
        return catalog.getOrDefault(type, new HashMap<>());
    }

    public void addItem(ItemType type, String name, Item item) {
        catalog.computeIfAbsent(type, k -> new HashMap<>()).put(name, item);
    }

    public Item getRandomItem(ItemType type) {
        Map<String, Item> items = catalog.get(type);
        if (items == null || items.isEmpty()) return null;

        List<String> itemNames = new ArrayList<>(items.keySet());
        return items.get(itemNames.get(new Random().nextInt(itemNames.size())));
    }
}