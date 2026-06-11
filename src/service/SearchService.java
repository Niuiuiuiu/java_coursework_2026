package service;

import model.*;

import java.util.List;

/**
 * Search and display service for players, teams, heroes, and equipment.
 * All display logic is centralized here for consistency.
 */
public class SearchService {

    private GameDataManager data;

    public SearchService(GameDataManager data) {
        this.data = data;
    }

    // ==================== Player Search ====================

    /**
     * Search a player by ID. Returns formatted string or null if not found.
     */
    public String searchPlayerById(String id) {
        Player p = data.getPlayerById(id);
        if (p == null) return null;
        return formatPlayerFull(p);
    }

    /**
     * Search a player by name. Returns formatted string or null if not found.
     */
    public String searchPlayerByName(String name) {
        Player p = data.findPlayerByName(name);
        if (p == null) return null;
        return formatPlayerFull(p);
    }

    /**
     * Format a player's full information for display.
     */
    private String formatPlayerFull(Player p) {
        StringBuilder sb = new StringBuilder();
        sb.append("========================================\n");
        sb.append("  Player Details\n");
        sb.append("========================================\n");
        sb.append(String.format("  ID       : %s\n", p.getId()));
        sb.append(String.format("  Name     : %s\n", p.getName()));
        sb.append(String.format("  Role     : %s\n", p.getRole()));
        sb.append(String.format("  Level    : %d\n", p.getLevel()));
        sb.append(String.format("  Win Rate : %.1f%%\n", p.getWinRate()));
        sb.append(String.format("  Matches  : %d played, %d won\n",
                p.getMatchesPlayed(), p.getMatchesWon()));

        // Team
        if (p.getTeamId() != null) {
            Team team = data.getTeamById(p.getTeamId());
            sb.append(String.format("  Team     : %s\n",
                    team != null ? team.getName() : p.getTeamId()));
        } else {
            sb.append("  Team     : (none)\n");
        }

        // Owned Heroes
        sb.append("  ----------------------------------------\n");
        sb.append("  Owned Heroes:\n");
        List<Hero> heroes = p.getOwnedHeroes();
        if (heroes.isEmpty()) {
            sb.append("    (none)\n");
        } else {
            for (Hero h : heroes) {
                sb.append(String.format("    [%s] %s (%s)\n",
                        h.getId(), h.getName(), h.getHeroType().getDisplayName()));
                // Equipment on this hero
                List<Equipment> items = h.getEquippedItems();
                if (items.isEmpty()) {
                    sb.append("      Equipment: (none)\n");
                } else {
                    sb.append("      Equipment: ");
                    for (int i = 0; i < items.size(); i++) {
                        if (i > 0) sb.append(", ");
                        sb.append(items.get(i).getName());
                    }
                    sb.append("\n");
                }
            }
        }
        sb.append("========================================\n");
        return sb.toString();
    }

    // ==================== Team Search ====================

    /**
     * Search a team by ID or name. Returns formatted string or null if not found.
     */
    public String searchTeamById(String id) {
        Team t = data.getTeamById(id);
        if (t == null) return null;
        return formatTeamFull(t);
    }

    public String searchTeamByName(String name) {
        Team t = data.findTeamByName(name);
        if (t == null) return null;
        return formatTeamFull(t);
    }

    private String formatTeamFull(Team t) {
        StringBuilder sb = new StringBuilder();
        sb.append("========================================\n");
        sb.append("  Team Overview\n");
        sb.append("========================================\n");
        sb.append(String.format("  ID        : %s\n", t.getId()));
        sb.append(String.format("  Name      : %s\n", t.getName()));
        sb.append(String.format("  Members   : %d\n", t.getMemberCount()));
        sb.append(String.format("  Avg Level : %.1f\n", data.getTeamAverageLevel(t.getId())));
        sb.append(String.format("  Matches   : %d total\n", t.getTotalMatches()));
        sb.append(String.format("  Win Rate  : %.1f%%\n", t.getWinRate()));

        // Top player
        Player top = data.getTeamTopPlayer(t.getId());
        if (top != null) {
            sb.append(String.format("  Top Player: %s (WinRate: %.1f%%)\n",
                    top.getName(), top.getWinRate()));
        }

        // All members
        sb.append("  ----------------------------------------\n");
        sb.append("  Members:\n");
        List<Player> members = data.getTeamMembers(t.getId());
        for (Player p : members) {
            sb.append(String.format("    [%s] %-15s Lv.%2d  WR:%.1f%%  Heroes:%d\n",
                    p.getId(), p.getName(), p.getLevel(),
                    p.getWinRate(), p.getOwnedHeroes().size()));
        }
        sb.append("========================================\n");
        return sb.toString();
    }

    // ==================== Hero Search ====================

    public String searchHeroByName(String name) {
        Hero h = data.findHeroByName(name);
        if (h == null) return null;
        return formatHeroFull(h);
    }

    public String searchHeroById(String id) {
        Hero h = data.getHeroById(id);
        if (h == null) return null;
        return formatHeroFull(h);
    }

    private String formatHeroFull(Hero h) {
        StringBuilder sb = new StringBuilder();
        sb.append("========================================\n");
        sb.append("  Hero Details\n");
        sb.append("========================================\n");
        sb.append(String.format("  ID          : %s\n", h.getId()));
        sb.append(String.format("  Name        : %s\n", h.getName()));
        sb.append(String.format("  Type        : %s\n", h.getHeroType().getDisplayName()));
        sb.append(String.format("  Difficulty  : %s\n", h.getDifficulty()));
        sb.append("  ----------------------------------------\n");
        sb.append("  Base Stats:\n");
        sb.append(String.format("    Attack  : %d\n", h.getBaseAttack()));
        sb.append(String.format("    Defense : %d\n", h.getBaseDefense()));
        sb.append(String.format("    Health  : %d\n", h.getBaseHealth()));
        sb.append("  ----------------------------------------\n");

        // Equipped items
        sb.append("  Equipped Items:\n");
        List<Equipment> items = h.getEquippedItems();
        if (items.isEmpty()) {
            sb.append("    (none)\n");
        } else {
            for (Equipment e : items) {
                sb.append(String.format("    [%s] %-20s %s\n",
                        e.getId(), e.getName(), e.toString()));
            }
        }

        // Total stats with equipment
        sb.append("  ----------------------------------------\n");
        sb.append("  Total Stats (with equipment):\n");
        sb.append(String.format("    Attack  : %d\n", h.getTotalAttack()));
        sb.append(String.format("    Defense : %d\n", h.getTotalDefense()));
        sb.append(String.format("    Health  : %d\n", h.getTotalHealth()));

        // Players who own this hero
        sb.append("  ----------------------------------------\n");
        sb.append("  Owned By:\n");
        boolean found = false;
        for (Player p : data.getAllPlayers()) {
            if (p.findHeroById(h.getId()) != null) {
                sb.append(String.format("    [%s] %s\n", p.getId(), p.getName()));
                found = true;
            }
        }
        if (!found) {
            sb.append("    (no one owns this hero)\n");
        }
        sb.append("========================================\n");
        return sb.toString();
    }

    // ==================== Equipment Search ====================

    public String searchEquipmentByName(String name) {
        Equipment e = data.findEquipmentByName(name);
        if (e == null) return null;
        return formatEquipmentFull(e);
    }

    private String formatEquipmentFull(Equipment e) {
        StringBuilder sb = new StringBuilder();
        sb.append("========================================\n");
        sb.append("  Equipment Details\n");
        sb.append("========================================\n");
        sb.append(String.format("  ID      : %s\n", e.getId()));
        sb.append(String.format("  Name    : %s\n", e.getName()));
        sb.append(String.format("  Type    : %s\n", e.getType().getDisplayName()));
        sb.append(String.format("  Price   : %d gold\n", e.getPrice()));
        sb.append("  Stats:\n");
        sb.append(String.format("    Attack  : +%d\n", e.getAttackBonus()));
        sb.append(String.format("    Defense : +%d\n", e.getDefenseBonus()));
        sb.append(String.format("    Magic   : +%d\n", e.getMagicBonus()));
        sb.append(String.format("    Health  : +%d\n", e.getHealthBonus()));
        sb.append(String.format("  Composite Score: %d\n", e.getCompositeScore()));
        sb.append(String.format("  Description: %s\n", e.getDescription()));
        sb.append("========================================\n");
        return sb.toString();
    }

    // ==================== Match History ====================

    /**
     * Get formatted match history for a player (last N matches).
     */
    public String getPlayerMatchHistory(String playerId, int n) {
        Player p = data.getPlayerById(playerId);
        if (p == null) return "Player not found.";

        List<MatchRecord> matches = data.getMatchesByPlayer(playerId, n);
        return formatMatchList(playerId + " (" + p.getName() + ")", matches);
    }

    /**
     * Get formatted match history for a team (last N matches).
     */
    public String getTeamMatchHistory(String teamId, int n) {
        Team t = data.getTeamById(teamId);
        if (t == null) return "Team not found.";

        List<MatchRecord> matches = data.getMatchesByTeam(teamId, n);
        return formatMatchList(teamId + " (" + t.getName() + ")", matches);
    }

    private String formatMatchList(String subject, List<MatchRecord> matches) {
        StringBuilder sb = new StringBuilder();
        sb.append("========================================\n");
        sb.append("  Match History for ").append(subject).append("\n");
        sb.append("========================================\n");
        if (matches.isEmpty()) {
            sb.append("  No matches found.\n");
        } else {
            sb.append(String.format("  %-8s %-20s %-10s %-8s %s\n",
                    "MatchID", "Date", "Result", "Duration", "Heroes"));
            sb.append("  ----------------------------------------------------------------------\n");
            for (MatchRecord m : matches) {
                String result;
                if (m.getTeamAId().equals(subject.split(" ")[0])) {
                    result = m.getResultForTeamA().toString();
                } else {
                    result = m.getResultForTeamA() == MatchResult.WIN
                            ? MatchResult.LOSS.toString() : MatchResult.WIN.toString();
                }
                // Abbreviate: use team IDs
                if (subject.contains("(")) {
                    // For player, use their team ID
                    String pid = subject.split(" ")[0];
                    Player player = data.getPlayerById(pid);
                    if (player != null && player.getTeamId() != null) {
                        if (player.getTeamId().equals(m.getTeamAId())) {
                            result = m.getResultForTeamA().toString();
                        } else {
                            result = m.getResultForTeamA() == MatchResult.WIN
                                    ? MatchResult.LOSS.toString() : MatchResult.WIN.toString();
                        }
                    }
                }
                sb.append(String.format("  %-8s %-20s %-10s %-8d min  Heroes: %s\n",
                        m.getMatchId(),
                        String.format("%tF", m.getMatchDate()),
                        result,
                        m.getDurationMinutes(),
                        m.getHeroIdsPicked()));
            }
        }
        sb.append("========================================\n");
        return sb.toString();
    }
}
