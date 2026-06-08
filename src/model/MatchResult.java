package model;

/**
 * Enum representing the result of a match.
 */
public enum MatchResult {
    WIN("Win"),
    LOSS("Loss");

    private final String displayName;

    MatchResult(String displayName) {
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
