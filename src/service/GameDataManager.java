package service;

import model.*;
import util.DataInitializer;

import java.io.*;
import java.util.*;

/**
 * Central data store for the Honor of Kings IMS.
 * Holds all system data and provides CRUD operations.
 * All data mutations go through this class to maintain consistency.
 * Implements Serializable to support file persistence via FileStorageService.
 */
public class GameDataManager implements Serializable {
    private static final long serialVersionUID = 1L;

    // === Core data collections ===
    private Map<String, Player> players;        // ID → Player
    private Map<String, Admin> admins;          // ID → Admin
    private Map<String, Hero> heroes;           // ID → Hero
    private Map<String, Equipment> equipment;   // ID → Equipment
    private Map<String, Team> teams;            // ID → Team
    private List<MatchRecord> matchRecords;     // All matches

    public GameDataManager() {
        this.players = new HashMap<>();
        this.admins = new HashMap<>();
        this.heroes = new HashMap<>();
        this.equipment = new HashMap<>();
        this.teams = new HashMap<>();
        this.matchRecords = new ArrayList<>();
    }

    /**
     * Load all initial data from DataInitializer.
     */
    public void initializeData() {
        // Order matters: equipment first, then heroes, then players, then teams, then matches
        List<Equipment> equipList = DataInitializer.createEquipment();
        for (Equipment e : equipList) {
            equipment.put(e.getId(), e);
        }

        List<Hero> heroList = DataInitializer.createHeroes(equipList);
        for (Hero h : heroList) {
            heroes.put(h.getId(), h);
        }

        List<Player> playerList = DataInitializer.createPlayers(heroList);
        for (Player p : playerList) {
            players.put(p.getId(), p);
        }

        List<Team> teamList = DataInitializer.createTeams();
        for (Team t : teamList) {
            teams.put(t.getId(), t);
        }

        List<Admin> adminList = DataInitializer.createAdmins();
        for (Admin a : adminList) {
            admins.put(a.getId(), a);
        }

        matchRecords = DataInitializer.createMatches();
    }

    // ==================== Player CRUD ====================

    public Player getPlayerById(String id) {
        return players.get(id);
    }

    public List<Player> getAllPlayers() {
        return new ArrayList<>(players.values());
    }

    public Player findPlayerByName(String name) {
        for (Player p : players.values()) {
            if (p.getName().equalsIgnoreCase(name)) {
                return p;
            }
        }
        return null;
    }

    public boolean addPlayer(Player player) {
        if (players.containsKey(player.getId())) {
            return false;  // Duplicate ID
        }
        players.put(player.getId(), player);
        // Also add to team if teamId is set
        if (player.getTeamId() != null) {
            Team team = teams.get(player.getTeamId());
            if (team != null) {
                team.addMember(player.getId());
            }
        }
        return true;
    }

    public boolean updatePlayer(Player player) {
        if (!players.containsKey(player.getId())) {
            return false;
        }
        players.put(player.getId(), player);
        return true;
    }

    public boolean deletePlayer(String id) {
        Player p = players.remove(id);
        if (p != null) {
            // Remove from team
            if (p.getTeamId() != null) {
                Team team = teams.get(p.getTeamId());
                if (team != null) {
                    team.removeMember(id);
                }
            }
            return true;
        }
        return false;
    }

    // ==================== Admin CRUD ====================

    public Admin getAdminById(String id) {
        return admins.get(id);
    }

    public List<Admin> getAllAdmins() {
        return new ArrayList<>(admins.values());
    }

    // ==================== Hero CRUD ====================

    public Hero getHeroById(String id) {
        return heroes.get(id);
    }

    public List<Hero> getAllHeroes() {
        return new ArrayList<>(heroes.values());
    }

    public Hero findHeroByName(String name) {
        for (Hero h : heroes.values()) {
            if (h.getName().equalsIgnoreCase(name)) {
                return h;
            }
        }
        return null;
    }

    public boolean addHero(Hero hero) {
        if (heroes.containsKey(hero.getId())) {
            return false;
        }
        heroes.put(hero.getId(), hero);
        return true;
    }

    /**
     * Delete a hero AND remove it from all players who own it.
     */
    public boolean deleteHero(String id) {
        Hero h = heroes.remove(id);
        if (h != null) {
            // Cascade: remove from all player-owned hero lists
            for (Player p : players.values()) {
                p.removeHero(h);
            }
            return true;
        }
        return false;
    }

    // ==================== Equipment CRUD ====================

    public Equipment getEquipmentById(String id) {
        return equipment.get(id);
    }

    public List<Equipment> getAllEquipment() {
        return new ArrayList<>(equipment.values());//HashMap中返回所有值的方法
    }

    public Equipment findEquipmentByName(String name) {
        for (Equipment e : equipment.values()) {
            if (e.getName().equalsIgnoreCase(name)) {
                return e;
            }
        }
        return null;
    }

    public boolean addEquipment(Equipment eq) {
        if (equipment.containsKey(eq.getId())) {
            return false;
        }
        equipment.put(eq.getId(), eq);
        return true;
    }

    /**
     * Delete equipment AND remove it from all heroes who have it equipped.
     */
    public boolean deleteEquipment(String id) {
        Equipment eq = equipment.remove(id);
        if (eq != null) {
            for (Hero h : heroes.values()) {
                h.removeEquipment(eq);
            }
            return true;
        }
        return false;
    }

    // ==================== Team CRUD ====================

    public Team getTeamById(String id) {
        return teams.get(id);
    }

    public List<Team> getAllTeams() {
        return new ArrayList<>(teams.values());
    }

    public Team findTeamByName(String name) {
        for (Team t : teams.values()) {
            if (t.getName().equalsIgnoreCase(name)) {
                return t;
            }
        }
        return null;
    }

    /**
     * Get all players belonging to a specific team.
     */
    public List<Player> getTeamMembers(String teamId) {
        List<Player> members = new ArrayList<>();
        Team team = teams.get(teamId); //返回Team类型的那个对象
        if (team != null) {
            for (String pid : team.getMemberIds()) {
                Player p = players.get(pid);
                if (p != null) {
                    members.add(p);
                }
            }
        }
        return members;
    }

    // ==================== Match Record CRUD ====================

    public List<MatchRecord> getAllMatches() {
        return new ArrayList<>(matchRecords);
    }

    public MatchRecord getMatchById(String matchId) {
        for (MatchRecord m : matchRecords) {
            if (m.getMatchId().equals(matchId)) {
                return m;
            }
        }
        return null;
    }

    /**
     * Get the last N matches for a specific player (by their team).
     */
    public List<MatchRecord> getMatchesByPlayer(String playerId, int limit) {
        Player player = players.get(playerId);
        if (player == null || player.getTeamId() == null) {
            return new ArrayList<>();
        }
        return getMatchesByTeam(player.getTeamId(), limit);
    }

    /**
     * Get the last N matches for a specific team.
     */
    public List<MatchRecord> getMatchesByTeam(String teamId, int limit) {
        List<MatchRecord> result = new ArrayList<>();
        for (MatchRecord m : matchRecords) {
            if (m.getTeamAId().equals(teamId) || m.getTeamBId().equals(teamId)) {
                result.add(m);
            }
        }
        // Sort by date descending (most recent first), then take the first `limit`
        result.sort((m1, m2) -> m2.getMatchDate().compareTo(m1.getMatchDate()));
        if (limit > 0 && limit < result.size()) {
            result = new ArrayList<>(result.subList(0, limit));
        }
        return result;
    }

    public boolean addMatch(MatchRecord match) {
        if (getMatchById(match.getMatchId()) != null) {
            return false;  // Duplicate
        }
        // Update team stats
        Team teamA = teams.get(match.getTeamAId());
        Team teamB = teams.get(match.getTeamBId());
        if (teamA != null) {
            teamA.setTotalMatches(teamA.getTotalMatches() + 1);
            if (match.getResultForTeamA() == MatchResult.WIN) {
                teamA.setTotalWins(teamA.getTotalWins() + 1);
            }
        }
        if (teamB != null) {
            teamB.setTotalMatches(teamB.getTotalMatches() + 1);
            if (match.getResultForTeamA() == MatchResult.LOSS) {
                teamB.setTotalWins(teamB.getTotalWins() + 1);
            }
        }
        return matchRecords.add(match);
    }

    // ==================== Authentication ====================

    /**
     * Find a Person (Player or Admin) by username and password.
     * Returns null if not found or password doesn't match.
     */
    public Person authenticate(String username, String password) {
        // Check admins first
        for (Admin a : admins.values()) {
            if (a.getUsername().equals(username) && a.checkPassword(password)) {
                return a;
            }
        }
        // Check players
        for (Player p : players.values()) {
            if (p.getUsername().equals(username) && p.checkPassword(password)) {
                return p;
            }
        }
        return null;
    }

    // ==================== Statistics ====================

    /**
     * Get the average level of a team's members.
     */
    public double getTeamAverageLevel(String teamId) {
        List<Player> members = getTeamMembers(teamId);
        if (members.isEmpty()) return 0.0;
        int total = 0;
        for (Player p : members) {
            total += p.getLevel();
        }
        return (double) total / members.size();
    }

    /**
     * Find the top player in a team by win rate.
     */
    public Player getTeamTopPlayer(String teamId) {
        List<Player> members = getTeamMembers(teamId);
        if (members.isEmpty()) return null;
        Player top = members.get(0);
        for (Player p : members) {
            if (p.getWinRate() > top.getWinRate()) {
                top = p;
            }
        }
        return top;
    }
}
