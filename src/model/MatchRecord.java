package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Represents a match record between two teams.
 */
public class MatchRecord implements Serializable {
    private static final long serialVersionUID = 1L;

    private String matchId;
    private String teamAId;            // Team A ID
    private String teamBId;            // Team B ID
    private MatchResult resultForTeamA; // WIN or LOSS from Team A's perspective
    private List<String> heroIdsPicked; // Hero IDs picked in this match
    private Date matchDate;
    private int durationMinutes;       // Match duration

    public MatchRecord() {
        this.heroIdsPicked = new ArrayList<>();
        this.matchDate = new Date();
    }

    public MatchRecord(String matchId, String teamAId, String teamBId,
                       MatchResult resultForTeamA, Date matchDate, int durationMinutes) {
        this.matchId = matchId;
        this.teamAId = teamAId;
        this.teamBId = teamBId;
        this.resultForTeamA = resultForTeamA;
        this.heroIdsPicked = new ArrayList<>();
        this.matchDate = matchDate;
        this.durationMinutes = durationMinutes;
    }

    // --- Getters ---

    public String getMatchId() { return matchId; }
    public String getTeamAId() { return teamAId; }
    public String getTeamBId() { return teamBId; }
    public MatchResult getResultForTeamA() { return resultForTeamA; }
    public List<String> getHeroIdsPicked() { return heroIdsPicked; }
    public Date getMatchDate() { return matchDate; }
    public int getDurationMinutes() { return durationMinutes; }

    /**
     * Returns the winner team ID based on the result.
     */
    public String getWinnerTeamId() {
        return resultForTeamA == MatchResult.WIN ? teamAId : teamBId;
    }

    /**
     * Returns the loser team ID.
     */
    public String getLoserTeamId() {
        return resultForTeamA == MatchResult.WIN ? teamBId : teamAId;
    }

    // --- Setters ---

    public void setMatchId(String matchId) { this.matchId = matchId; }
    public void setTeamAId(String teamAId) { this.teamAId = teamAId; }
    public void setTeamBId(String teamBId) { this.teamBId = teamBId; }
    public void setResultForTeamA(MatchResult resultForTeamA) { this.resultForTeamA = resultForTeamA; }
    public void setMatchDate(Date matchDate) { this.matchDate = matchDate; }
    public void setDurationMinutes(int durationMinutes) { this.durationMinutes = durationMinutes; }

    public void addHeroPicked(String heroId) {
        if (!heroIdsPicked.contains(heroId)) {
            heroIdsPicked.add(heroId);
        }
    }

    @Override
    public String toString() {
        return String.format("Match[%s] %s vs %s — %s wins (%d min)",
                matchId, teamAId, teamBId, getWinnerTeamId(), durationMinutes);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        MatchRecord other = (MatchRecord) obj;
        return matchId != null && matchId.equals(other.matchId);
    }

    @Override
    public int hashCode() {
        return matchId != null ? matchId.hashCode() : 0;
    }
}
