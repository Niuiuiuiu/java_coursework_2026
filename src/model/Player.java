package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a game player. Extends Person.
 * A player has a level, win rate, owns heroes, and belongs to a team.
 */
public class Player extends Person {
    private static final long serialVersionUID = 1L;

    private int level;
    private int matchesPlayed;
    private int matchesWon;
    private List<Hero> ownedHeroes;
    private String teamId;   // Reference to the team this player belongs to

    public Player() {
        super();
        this.ownedHeroes = new ArrayList<>();
    }

    public Player(String id, String name, String username, String password,
                  int level, int matchesPlayed, int matchesWon) {
        super(id, name, username, password);
        this.level = level;
        this.matchesPlayed = matchesPlayed;
        this.matchesWon = matchesWon;
        this.ownedHeroes = new ArrayList<>();
        this.teamId = null;
    }

    // --- Getters ---

    public int getLevel() { return level; }
    public int getMatchesPlayed() { return matchesPlayed; }
    public int getMatchesWon() { return matchesWon; }
    public List<Hero> getOwnedHeroes() { return ownedHeroes; }
    public String getTeamId() { return teamId; }

    /**
     * Win rate as a percentage (0–100). Returns 0 if no matches played.
     */
    public double getWinRate() {
        if (matchesPlayed == 0) return 0.0;
        return (double) matchesWon / matchesPlayed * 100.0;
    }

    @Override
    public String getRole() {
        return "Player";
    }

    // --- Setters ---

    public void setLevel(int level) { this.level = level; }
    public void setMatchesPlayed(int matchesPlayed) { this.matchesPlayed = matchesPlayed; }
    public void setMatchesWon(int matchesWon) { this.matchesWon = matchesWon; }
    public void setTeamId(String teamId) { this.teamId = teamId; }

    // --- Hero management ---

    /**
     * Add a hero to this player's collection.
     */
    public void addHero(Hero hero) {
        if (!ownedHeroes.contains(hero)) {
            ownedHeroes.add(hero);
        }
    }

    /**
     * Remove a hero from this player's collection.
     */
    public boolean removeHero(Hero hero) {
        return ownedHeroes.remove(hero);
    }

    /**
     * Find a hero owned by this player by hero ID.
     */
    public Hero findHeroById(String heroId) {
        for (Hero h : ownedHeroes) {
            if (h.getId().equals(heroId)) {
                return h;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return String.format("Player[%s] %s Lv.%d WinRate:%.1f%% Matches:%d",
                getId(), getName(), level, getWinRate(), matchesPlayed);
    }
}
