package service;

import model.*;

import java.util.*;

/**
 * Ranking and leaderboard service.
 * Provides player leaderboards and equipment statistics ranking.
 *
 * Tie-breaking rules:
 *   - By Win Rate:  tie broken by level, then by matches played
 *   - By Level:     tie broken by win rate, then by matches played
 *   - By Matches:   tie broken by win rate, then by level
 *   - Custom Score: tie broken by win rate, then by level
 */
public class RankingService {

    private GameDataManager data;

    public RankingService(GameDataManager data) {
        this.data = data;
    }

    // ==================== Player Leaderboards ====================

    /**
     * Get top N players sorted by win rate (descending).
     * Ties broken by level, then by matches played.
     */
    public String getTopByWinRate(int n) {
        List<Player> sorted = new ArrayList<>(data.getAllPlayers());
        sorted.sort((a, b) -> {
            int cmp = Double.compare(b.getWinRate(), a.getWinRate());
            if (cmp != 0) return cmp;
            cmp = Integer.compare(b.getLevel(), a.getLevel());
            if (cmp != 0) return cmp;
            return Integer.compare(b.getMatchesPlayed(), a.getMatchesPlayed());
        });
        return formatLeaderboard("Top Players by Win Rate", sorted, n);
    }

    /**
     * Get top N players sorted by level (descending).
     * Ties broken by win rate, then by matches played.
     */
    public String getTopByLevel(int n) {
        List<Player> sorted = new ArrayList<>(data.getAllPlayers());
        sorted.sort((a, b) -> {
            int cmp = Integer.compare(b.getLevel(), a.getLevel());
            if (cmp != 0) return cmp;
            cmp = Double.compare(b.getWinRate(), a.getWinRate());
            if (cmp != 0) return cmp;
            return Integer.compare(b.getMatchesPlayed(), a.getMatchesPlayed());
        });
        return formatLeaderboard("Top Players by Level", sorted, n);
    }

    /**
     * Get top N players sorted by number of matches played (descending).
     * Ties broken by win rate, then by level.
     */
    public String getTopByMatches(int n) {
        List<Player> sorted = new ArrayList<>(data.getAllPlayers());
        sorted.sort((a, b) -> {
            int cmp = Integer.compare(b.getMatchesPlayed(), a.getMatchesPlayed());
            if (cmp != 0) return cmp;
            cmp = Double.compare(b.getWinRate(), a.getWinRate());
            if (cmp != 0) return cmp;
            return Integer.compare(b.getLevel(), a.getLevel());
        });
        return formatLeaderboard("Top Players by Matches Played", sorted, n);
    }

    /**
     * Get top N players by a custom score.
     * Formula: score = winRate * 0.4 + level * 0.3 + matchesPlayed * 0.3
     */
    public String getTopByCustomScore(int n) {
        List<Player> sorted = new ArrayList<>(data.getAllPlayers());
        sorted.sort((a, b) -> {
            double scoreA = a.getWinRate() * 0.4 + a.getLevel() * 0.3 + a.getMatchesPlayed() * 0.3;
            double scoreB = b.getWinRate() * 0.4 + b.getLevel() * 0.3 + b.getMatchesPlayed() * 0.3;
            int cmp = Double.compare(scoreB, scoreA);
            if (cmp != 0) return cmp;
            cmp = Double.compare(b.getWinRate(), a.getWinRate());
            if (cmp != 0) return cmp;
            return Integer.compare(b.getLevel(), a.getLevel());
        });
        return formatLeaderboardWithScore("Top Players by Custom Score", sorted, n);
    }

    private double getCustomScore(Player p) {
        return p.getWinRate() * 0.4 + p.getLevel() * 0.3 + p.getMatchesPlayed() * 0.3;
    }

    /**
     * Format a leaderboard for display.
     */
    private String formatLeaderboard(String title, List<Player> sorted, int n) {
        StringBuilder sb = new StringBuilder();
        sb.append("========================================\n");
        sb.append("  ").append(title).append("\n");
        sb.append("========================================\n");
        sb.append(String.format("  %-4s %-6s %-16s %-5s %-8s %-7s %s\n",
                "Rank", "ID", "Name", "Lv", "WinRate", "Matches", "Team"));
        sb.append("  ----------------------------------------------------------------------\n");

        int limit = Math.min(n, sorted.size());
        for (int i = 0; i < limit; i++) {
            Player p = sorted.get(i);
            String teamName = "";
            if (p.getTeamId() != null) {
                Team t = data.getTeamById(p.getTeamId());
                teamName = t != null ? t.getName() : p.getTeamId();
            }
            sb.append(String.format("  %-4d %-6s %-16s %-5d %-8.1f%% %-7d %s\n",
                    i + 1, p.getId(), p.getName(), p.getLevel(),
                    p.getWinRate(), p.getMatchesPlayed(), teamName));
        }
        sb.append("========================================\n");
        sb.append("  Tie-breaking: see docs/design.md for details.\n");
        return sb.toString();
    }

    private String formatLeaderboardWithScore(String title, List<Player> sorted, int n) {
        StringBuilder sb = new StringBuilder();
        sb.append("========================================\n");
        sb.append("  ").append(title).append("\n");
        sb.append("  Formula: WinRate × 0.4 + Level × 0.3 + Matches × 0.3\n");
        sb.append("========================================\n");
        sb.append(String.format("  %-4s %-6s %-16s %-8s %-8s %-7s %s\n",
                "Rank", "ID", "Name", "Score", "WinRate", "Matches", "Team"));
        sb.append("  ----------------------------------------------------------------------\n");

        int limit = Math.min(n, sorted.size());
        for (int i = 0; i < limit; i++) {
            Player p = sorted.get(i);
            String teamName = "";
            if (p.getTeamId() != null) {
                Team t = data.getTeamById(p.getTeamId());
                teamName = t != null ? t.getName() : p.getTeamId();
            }
            sb.append(String.format("  %-4d %-6s %-16s %-8.1f %-8.1f%% %-7d %s\n",
                    i + 1, p.getId(), p.getName(),
                    getCustomScore(p), p.getWinRate(),
                    p.getMatchesPlayed(), teamName));
        }
        sb.append("========================================\n");
        return sb.toString();
    }

    // ==================== Equipment Ranking ====================

    /**
     * Rank equipment by composite score (sum of all stat bonuses).
     */
    public String getEquipmentRankingByScore() {
        List<Equipment> sorted = new ArrayList<>(data.getAllEquipment());
        sorted.sort((a, b) -> Integer.compare(b.getCompositeScore(), a.getCompositeScore()));
        return formatEquipmentRanking("Equipment Ranking by Composite Score", sorted);
    }

    /**
     * Rank equipment by how many heroes use it.
     */
    public String getEquipmentRankingByUsage() {
        List<Equipment> all = data.getAllEquipment();
        Map<String, Integer> usageCount = new HashMap<>();
        for (Equipment e : all) {
            usageCount.put(e.getId(), 0);
        }
        // Count heroes using each equipment
        for (Hero h : data.getAllHeroes()) {
            for (Equipment e : h.getEquippedItems()) {
                usageCount.put(e.getId(), usageCount.getOrDefault(e.getId(), 0) + 1);
            }
        }
        all.sort((a, b) -> {
            int cmp = Integer.compare(
                    usageCount.getOrDefault(b.getId(), 0),
                    usageCount.getOrDefault(a.getId(), 0));
            if (cmp != 0) return cmp;
            return Integer.compare(b.getCompositeScore(), a.getCompositeScore());
        });

        StringBuilder sb = new StringBuilder();
        sb.append("========================================\n");
        sb.append("  Equipment Ranking by Usage Count\n");
        sb.append("========================================\n");
        sb.append(String.format("  %-4s %-6s %-22s %-10s %-8s %s\n",
                "Rank", "ID", "Name", "Type", "Heroes", "Score"));
        sb.append("  ----------------------------------------------------------------------\n");
        for (int i = 0; i < all.size(); i++) {
            Equipment e = all.get(i);
            sb.append(String.format("  %-4d %-6s %-22s %-10s %-8d %d\n",
                    i + 1, e.getId(), e.getName(), e.getType().getDisplayName(),
                    usageCount.getOrDefault(e.getId(), 0), e.getCompositeScore()));
        }
        sb.append("========================================\n");
        return sb.toString();
    }

    private String formatEquipmentRanking(String title, List<Equipment> sorted) {
        StringBuilder sb = new StringBuilder();
        sb.append("========================================\n");
        sb.append("  ").append(title).append("\n");
        sb.append("========================================\n");
        sb.append(String.format("  %-4s %-6s %-22s %-10s %-6s %-6s %s\n",
                "Rank", "ID", "Name", "Type", "ATK", "DEF", "Score"));
        sb.append("  ----------------------------------------------------------------------\n");
        for (int i = 0; i < sorted.size(); i++) {
            Equipment e = sorted.get(i);
            sb.append(String.format("  %-4d %-6s %-22s %-10s %-6d %-6d %d\n",
                    i + 1, e.getId(), e.getName(), e.getType().getDisplayName(),
                    e.getAttackBonus(), e.getDefenseBonus(), e.getCompositeScore()));
        }
        sb.append("========================================\n");
        return sb.toString();
    }
}
