package model;

/**
 * Enum representing the hero role types in Honor of Kings.
 */
public enum HeroType {
    MAGE("Mage"),
    ASSASSIN("Assassin"),
    TANK("Tank"),
    WARRIOR("Warrior"),
    SUPPORT("Support"),
    MARKSMAN("Marksman");

    private final String displayName;

    HeroType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
