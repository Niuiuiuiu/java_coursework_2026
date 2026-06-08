package model;

import java.io.Serializable;

/**
 * Represents an equipment item that can be equipped by heroes.
 * Each equipment has a type, base stats, and a cost.
 */
public class Equipment implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String name;
    private EquipmentType type;
    private int attackBonus;      // Physical attack bonus
    private int defenseBonus;     // Physical defense bonus
    private int magicBonus;       // Magic power bonus
    private int healthBonus;      // Health points bonus
    private int price;            // Gold price
    private String description;

    public Equipment() {}

    public Equipment(String id, String name, EquipmentType type,
                     int attackBonus, int defenseBonus, int magicBonus,
                     int healthBonus, int price, String description) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.attackBonus = attackBonus;
        this.defenseBonus = defenseBonus;
        this.magicBonus = magicBonus;
        this.healthBonus = healthBonus;
        this.price = price;
        this.description = description;
    }

    // --- Getters ---

    public String getId() { return id; }
    public String getName() { return name; }
    public EquipmentType getType() { return type; }
    public int getAttackBonus() { return attackBonus; }
    public int getDefenseBonus() { return defenseBonus; }
    public int getMagicBonus() { return magicBonus; }
    public int getHealthBonus() { return healthBonus; }
    public int getPrice() { return price; }
    public String getDescription() { return description; }

    // --- Setters ---

    public void setId(String id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setType(EquipmentType type) { this.type = type; }
    public void setAttackBonus(int attackBonus) { this.attackBonus = attackBonus; }
    public void setDefenseBonus(int defenseBonus) { this.defenseBonus = defenseBonus; }
    public void setMagicBonus(int magicBonus) { this.magicBonus = magicBonus; }
    public void setHealthBonus(int healthBonus) { this.healthBonus = healthBonus; }
    public void setPrice(int price) { this.price = price; }
    public void setDescription(String description) { this.description = description; }

    /**
     * Calculates a composite score used for equipment ranking.
     */
    public int getCompositeScore() {
        return attackBonus + defenseBonus + magicBonus + healthBonus;
    }

    @Override
    public String toString() {
        return String.format("%s [%s] ATK+%d DEF+%d MAG+%d HP+%d (%d gold)",
                name, type.getDisplayName(),
                attackBonus, defenseBonus, magicBonus, healthBonus, price);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Equipment other = (Equipment) obj;
        return id != null && id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
