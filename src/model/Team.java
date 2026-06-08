package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Represents a team containing multiple players.
 * Composition relationship: a Team contains Player references.
 */
public class Team implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String name;
    private List<String> memberIds;   // Player IDs (avoid circular reference)
    private Date creationDate;
    private int totalMatches;
    private int totalWins;

    public Team() {
        this.memberIds = new ArrayList<>();
        this.creationDate = new Date();
    }

    public Team(String id, String name) {
        this.id = id;
        this.name = name;
        this.memberIds = new ArrayList<>();
        this.creationDate = new Date();
        this.totalMatches = 0;
        this.totalWins = 0;
    }

    // --- Getters ---

    public String getId() { return id; }
    public String getName() { return name; }
    public List<String> getMemberIds() { return memberIds; }
    public Date getCreationDate() { return creationDate; }
    public int getTotalMatches() { return totalMatches; }
    public int getTotalWins() { return totalWins; }

    public double getWinRate() {
        if (totalMatches == 0) return 0.0;
        return (double) totalWins / totalMatches * 100.0;
    }

    public int getMemberCount() {
        return memberIds.size();
    }

    // --- Setters ---

    public void setId(String id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setCreationDate(Date creationDate) { this.creationDate = creationDate; }
    public void setTotalMatches(int totalMatches) { this.totalMatches = totalMatches; }
    public void setTotalWins(int totalWins) { this.totalWins = totalWins; }

    // --- Member management ---

    /**
     * Add a player to this team.
     */
    public void addMember(String playerId) {
        if (!memberIds.contains(playerId)) {
            memberIds.add(playerId);
        }
    }

    /**
     * Remove a player from this team.
     */
    public boolean removeMember(String playerId) {
        return memberIds.remove(playerId);
    }

    /**
     * Check if a player is on this team.
     */
    public boolean hasMember(String playerId) {
        return memberIds.contains(playerId);
    }

    @Override
    public String toString() {
        return String.format("Team[%s] %s Members:%d WinRate:%.1f%%",
                id, name, memberIds.size(), getWinRate());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Team other = (Team) obj;
        return id != null && id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
