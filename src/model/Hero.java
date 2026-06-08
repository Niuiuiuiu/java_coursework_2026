package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a playable hero in Honor of Kings.
 * Heroes have a type, base stats, and can use compatible equipment.
 */
public class Hero implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String name;
    private HeroType heroType;
    private int baseAttack;
    private int baseDefense;
    private int baseHealth;
    private String difficulty;          // "Easy", "Medium", "Hard"
    private List<Equipment> equippedItems;  // Currently equipped items (max typically 6)

    public Hero() {
        this.equippedItems = new ArrayList<>();
    }

    public Hero(String id, String name, HeroType heroType,
                int baseAttack, int baseDefense, int baseHealth,
                String difficulty) {
        this.id = id;
        this.name = name;
        this.heroType = heroType;
        this.baseAttack = baseAttack;
        this.baseDefense = baseDefense;
        this.baseHealth = baseHealth;
        this.difficulty = difficulty;
        this.equippedItems = new ArrayList<>();
    }

    // --- Getters ---

    public String getId() { return id; }
    public String getName() { return name; }
    public HeroType getHeroType() { return heroType; }
    public int getBaseAttack() { return baseAttack; }
    public int getBaseDefense() { return baseDefense; }
    public int getBaseHealth() { return baseHealth; }
    public String getDifficulty() { return difficulty; }
    public List<Equipment> getEquippedItems() { return equippedItems; }

    // --- Setters ---

    public void setId(String id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setHeroType(HeroType heroType) { this.heroType = heroType; }
    public void setBaseAttack(int baseAttack) { this.baseAttack = baseAttack; }
    public void setBaseDefense(int baseDefense) { this.baseDefense = baseDefense; }
    public void setBaseHealth(int baseHealth) { this.baseHealth = baseHealth; }
    public void setDifficulty(String difficulty) { this.difficulty = difficulty; }

    /**
     * Equip an item to this hero (max 6 items).
     * Returns false if the inventory is full.
     */
    public boolean addEquipment(Equipment e) {
        if (equippedItems.size() >= 6) {
            return false;
        }
        if (!equippedItems.contains(e)) {
            equippedItems.add(e);
        }
        return true;
    }

    /**
     * Remove an equipped item from this hero.
     */
    public boolean removeEquipment(Equipment e) {
        return equippedItems.remove(e);
    }

    /**
     * Total attack = base + sum of all equipment bonuses.
     */
    public int getTotalAttack() {
        int bonus = 0;
        for (Equipment e : equippedItems) {
            bonus += e.getAttackBonus();
        }
        return baseAttack + bonus;
    }

    /**
     * Total defense = base + sum of all equipment bonuses.
     */
    public int getTotalDefense() {
        int bonus = 0;
        for (Equipment e : equippedItems) {
            bonus += e.getDefenseBonus();
        }
        return baseDefense + bonus;
    }

    /**
     * Total health = base + sum of all equipment bonuses.
     */
    public int getTotalHealth() {
        int bonus = 0;
        for (Equipment e : equippedItems) {
            bonus += e.getHealthBonus();
        }
        return baseHealth + bonus;
    }

    @Override
    public String toString() {
        return String.format("%s [%s] ATK:%d DEF:%d HP:%d (%s)",
                name, heroType.getDisplayName(),
                baseAttack, baseDefense, baseHealth, difficulty);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Hero other = (Hero) obj;
        return id != null && id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
