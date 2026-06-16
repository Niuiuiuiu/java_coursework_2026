import model.*;
import service.*;
import util.*;

/**
 * Main entry point for the Honor of Kings IMS.
 * Provides a menu-driven console interface with authentication.
 */
public class Main {

    private GameDataManager dataManager;
    private SearchService searchService;
    private RankingService rankingService;
    private InputHelper input;
    private FileStorageService fileStorage;

    private Person currentUser;  // Currently logged-in user (null if not logged in)

    public Main() {
        this.fileStorage = new FileStorageService("gamedata.dat");

        // Try to load saved data first; fall back to initial dataset
        GameDataManager loaded = fileStorage.load();
        if (loaded != null) {
            this.dataManager = loaded;
        } else {
            this.dataManager = new GameDataManager();
            dataManager.initializeData();
        }

        this.searchService = new SearchService(dataManager);
        this.rankingService = new RankingService(dataManager);
        this.input = new InputHelper();
        this.currentUser = null;
    }

    public static void main(String[] args) {
        Main app = new Main();
        app.run();
    }

    // ==================== Main Loop ====================

    public void run() {
        System.out.println("==============================================");
        System.out.println("  Honor of Kings Information Management System");
        System.out.println("  Java Coursework 2026");
        System.out.println("==============================================");

        while (true) {
            if (currentUser == null) {
                showLoginMenu();
            } else if (currentUser instanceof Admin) {
                showAdminMenu();
            } else {
                showPlayerMenu();
            }
        }
    }

    // ==================== Login ====================

    private void showLoginMenu() {
        System.out.println("\n========== Login ==========");
        System.out.println("  1. Login");
        System.out.println("  0. Exit");
        System.out.println("============================");

        int choice = input.readIntInRange("Select: ", 0, 1);
        switch (choice) {
            case 1:
                doLogin();
                break;
            case 0:
                System.out.println("Saving data before exit...");
                fileStorage.save(dataManager);
                System.out.println("Goodbye!");
                input.close();
                System.exit(0);
                break;
        }
    }

    private void doLogin() {
        System.out.println("\n--- Login ---");
        String username = input.readString("Username: ");
        String password = input.readString("Password: ");

        Person user = dataManager.authenticate(username, password);
        if (user != null) {
            currentUser = user;
            System.out.println("\n✅ Login successful! Welcome, " + user.getName()
                    + " (" + user.getRole() + ")");
        } else {
            System.out.println("\n❌ Invalid username or password. Please try again.");
        }
    }

    private void doLogout() {
        fileStorage.save(dataManager);  // Auto-save on logout
        System.out.println("\n👋 Goodbye, " + currentUser.getName() + "!");
        currentUser = null;
    }

    // ==================== Player Menu ====================

    private void showPlayerMenu() {
        System.out.println("\n========== Honor of Kings IMS [" + currentUser.getRole() + "] ==========");
        System.out.println("  Logged in as: " + currentUser.getName() + " (" + currentUser.getId() + ")");
        System.out.println("----------------------------------------------");
        System.out.println("  1.  Player Lookup");
        System.out.println("  2.  Team Overview");
        System.out.println("  3.  Hero Details");
        System.out.println("  4.  Equipment Statistics");
        System.out.println("  5.  Match History");
        System.out.println("  6.  Leaderboard");
        System.out.println("  7.  My Profile");
        System.out.println("  0.  Logout");
        System.out.println("==============================================");

        int choice = input.readIntInRange("Select: ", 0, 7);
        switch (choice) {
            case 1: menuPlayerLookup(); break;
            case 2: menuTeamOverview(); break;
            case 3: menuHeroDetails(); break;
            case 4: menuEquipmentStats(); break;
            case 5: menuMatchHistory(); break;
            case 6: menuLeaderboard(); break;
            case 7: menuMyProfile(); break;
            case 0: doLogout(); break;
        }
    }

    // ==================== Admin Menu ====================

    private void showAdminMenu() {
        System.out.println("\n========== Honor of Kings IMS [ADMIN] ==========");
        System.out.println("  Logged in as: " + currentUser.getName());
        System.out.println("----------------------------------------------");
        System.out.println("  1.  Player Lookup");
        System.out.println("  2.  Team Overview");
        System.out.println("  3.  Hero Details");
        System.out.println("  4.  Equipment Statistics");
        System.out.println("  5.  Match History");
        System.out.println("  6.  Leaderboard");
        System.out.println("----------------------------------------------");
        System.out.println("  7.  Manage Players");
        System.out.println("  8.  Manage Heroes");
        System.out.println("  9.  Manage Equipment");
        System.out.println("  10. Manage Teams");
        System.out.println("  11. Manage Match Records");
        System.out.println("----------------------------------------------");
        System.out.println("  12. Save Data to File");
        System.out.println("  0.  Logout");
        System.out.println("==============================================");

        int choice = input.readIntInRange("Select: ", 0, 12);
        switch (choice) {
            case 1:  menuPlayerLookup(); break;
            case 2:  menuTeamOverview(); break;
            case 3:  menuHeroDetails(); break;
            case 4:  menuEquipmentStats(); break;
            case 5:  menuMatchHistory(); break;
            case 6:  menuLeaderboard(); break;
            case 7:  adminManagePlayers(); break;
            case 8:  adminManageHeroes(); break;
            case 9:  adminManageEquipment(); break;
            case 10: adminManageTeams(); break;
            case 11: adminManageMatches(); break;
            case 12: fileStorage.save(dataManager);
                     input.waitForEnter(); break;
            case 0:  doLogout(); break;
        }
    }

    // ==================== Player Lookup (5.1) ====================

    private void menuPlayerLookup() {
        System.out.println("\n--- Player Lookup ---");
        System.out.println("  1. Search by ID");
        System.out.println("  2. Search by Name");
        int choice = input.readIntInRange("Select: ", 1, 2);

        String result;
        if (choice == 1) {
            String id = input.readString("Enter Player ID: ");
            result = searchService.searchPlayerById(id);
        } else {
            String name = input.readString("Enter Player Name: ");
            result = searchService.searchPlayerByName(name);
        }

        if (result != null) {
            System.out.println(result);
        } else {
            System.out.println("Player not found.");
        }
        input.waitForEnter();
    }

    // ==================== Team Overview (5.2) ====================

    private void menuTeamOverview() {
        System.out.println("\n--- Team Overview ---");
        System.out.println("All Teams:");
        for (Team t : dataManager.getAllTeams()) {
            System.out.printf("  [%s] %s (%d members)\n",
                    t.getId(), t.getName(), t.getMemberCount());
        }
        System.out.println();
        System.out.println("  1. Search by ID");
        System.out.println("  2. Search by Name");
        int choice = input.readIntInRange("Select: ", 1, 2);

        String result;
        if (choice == 1) {
            String id = input.readString("Enter Team ID: ");
            result = searchService.searchTeamById(id);
        } else {
            String name = input.readString("Enter Team Name: ");
            result = searchService.searchTeamByName(name);
        }

        if (result != null) {
            System.out.println(result);
        } else {
            System.out.println("Team not found.");
        }
        input.waitForEnter();
    }

    // ==================== Hero Details (5.3) ====================

    private void menuHeroDetails() {
        System.out.println("\n--- Hero Details ---");
        System.out.println("All Heroes:");
        for (Hero h : dataManager.getAllHeroes()) {
            System.out.printf("  [%s] %-16s (%s)\n",
                    h.getId(), h.getName(), h.getHeroType().getDisplayName());
        }
        String name = input.readString("\nEnter Hero Name: ");
        String result = searchService.searchHeroByName(name);

        if (result != null) {
            System.out.println(result);
        } else {
            System.out.println("Hero not found.");
        }
        input.waitForEnter();
    }

    // ==================== Equipment Statistics (5.4) ====================

    private void menuEquipmentStats() {
        System.out.println("\n--- Equipment Statistics ---");
        System.out.println("  1. Rank by Composite Score");
        System.out.println("  2. Rank by Usage Count (heroes using it)");
        int choice = input.readIntInRange("Select: ", 1, 2);

        String result;
        if (choice == 1) {
            result = rankingService.getEquipmentRankingByScore();
        } else {
            result = rankingService.getEquipmentRankingByUsage();
        }
        System.out.println(result);
        input.waitForEnter();
    }

    // ==================== Match History (5.5) ====================

    private void menuMatchHistory() {
        System.out.println("\n--- Match History ---");
        System.out.println("  1. By Player");
        System.out.println("  2. By Team");
        int choice = input.readIntInRange("Select: ", 1, 2);
        int n = input.readIntInRange("How many recent matches to show? ", 1, 20);

        String result;
        if (choice == 1) {
            String playerId = input.readString("Enter Player ID: ");
            result = searchService.getPlayerMatchHistory(playerId, n);
        } else {
            String teamId = input.readString("Enter Team ID: ");
            result = searchService.getTeamMatchHistory(teamId, n);
        }
        System.out.println(result);
        input.waitForEnter();
    }

    // ==================== Leaderboard (5.6) ====================

    private void menuLeaderboard() {
        System.out.println("\n--- Leaderboard ---");
        System.out.println("  1. Top by Win Rate");
        System.out.println("  2. Top by Level");
        System.out.println("  3. Top by Matches Played");
        System.out.println("  4. Top by Custom Score");
        int choice = input.readIntInRange("Select: ", 1, 4);
        int n = input.readIntInRange("Show top how many? ", 1, 12);

        String result;
        switch (choice) {
            case 1:  result = rankingService.getTopByWinRate(n); break;
            case 2:  result = rankingService.getTopByLevel(n); break;
            case 3:  result = rankingService.getTopByMatches(n); break;
            default: result = rankingService.getTopByCustomScore(n); break;
        }
        System.out.println(result);
        input.waitForEnter();
    }

    // ==================== My Profile ====================

    private void menuMyProfile() {
        if (currentUser instanceof Player) {
            Player p = (Player) currentUser;
            String result = searchService.searchPlayerById(p.getId());
            if (result != null) {
                System.out.println("\n--- My Profile ---");
                System.out.println(result);
            }
            // Also show match history
            String history = searchService.getPlayerMatchHistory(p.getId(), 5);
            System.out.println(history);
        }
        input.waitForEnter();
    }

    // ==================== Admin: Player Management ====================

    private void adminManagePlayers() {
        System.out.println("\n--- Manage Players ---");
        System.out.println("  1. List All Players");
        System.out.println("  2. Add New Player");
        System.out.println("  3. Edit Player");
        System.out.println("  4. Delete Player");
        int choice = input.readIntInRange("Select: ", 1, 4);

        switch (choice) {
            case 1:
                System.out.println("\nAll Players:");
                for (Player p : dataManager.getAllPlayers()) {
                    System.out.println("  " + p);
                }
                break;
            case 2:
                addNewPlayer();
                break;
            case 3:
                editPlayer();
                break;
            case 4:
                deletePlayer();
                break;
        }
        input.waitForEnter();
    }

    private void addNewPlayer() {
        System.out.println("\n--- Add New Player ---");
        String id = input.readString("New Player ID (e.g. P013): ");
        if (dataManager.getPlayerById(id) != null) {
            System.out.println("❌ Player ID already exists.");
            return;
        }
        String name = input.readString("Name: ");
        String username = input.readString("Username: ");
        String password = input.readString("Password: ");
        int level = input.readIntInRange("Level (1-100): ", 1, 100);

        Player p = new Player(id, name, username, password, level, 0, 0);

        System.out.println("\nAvailable Teams:");
        for (Team t : dataManager.getAllTeams()) {
            System.out.printf("  [%s] %s\n", t.getId(), t.getName());
        }
        String teamId = input.readString("Team ID (or leave blank): ");
        if (!teamId.isEmpty() && dataManager.getTeamById(teamId) != null) {
            p.setTeamId(teamId);
        }

        if (dataManager.addPlayer(p)) {
            System.out.println("✅ Player added successfully.");
        } else {
            System.out.println("❌ Failed to add player.");
        }
    }

    private void editPlayer() {
        String id = input.readString("Enter Player ID to edit: ");
        Player p = dataManager.getPlayerById(id);
        if (p == null) {
            System.out.println("Player not found.");
            return;
        }
        System.out.println("Editing: " + p);
        System.out.println("(Leave blank to keep current value)");

        String name = readOptionalString("New Name [" + p.getName() + "]: ");
        if (!name.isEmpty()) p.setName(name);

        int level = readOptionalInt("New Level [" + p.getLevel() + "]: ");
        if (level > 0) p.setLevel(level);

        dataManager.updatePlayer(p);
        System.out.println("✅ Player updated.");
    }

    private void deletePlayer() {
        String id = input.readString("Enter Player ID to delete: ");
        Player p = dataManager.getPlayerById(id);
        if (p == null) {
            System.out.println("Player not found.");
            return;
        }
        System.out.print("Are you sure you want to delete " + p.getName() + "? (y/n): ");
        String confirm = input.readString("");
        if (confirm.equalsIgnoreCase("y")) {
            dataManager.deletePlayer(id);
            System.out.println("✅ Player deleted.");
        } else {
            System.out.println("Cancelled.");
        }
    }

    // ==================== Admin: Hero Management ====================

    private void adminManageHeroes() {
        System.out.println("\n--- Manage Heroes ---");
        System.out.println("  1. List All Heroes");
        System.out.println("  2. Add New Hero");
        System.out.println("  3. Delete Hero");
        int choice = input.readIntInRange("Select: ", 1, 3);

        switch (choice) {
            case 1:
                for (Hero h : dataManager.getAllHeroes()) {
                    System.out.println("  " + h);
                }
                break;
            case 2:
                String id = input.readString("New Hero ID (e.g. H016): ");
                if (dataManager.getHeroById(id) != null) {
                    System.out.println("❌ Hero ID already exists.");
                    break;
                }
                String name = input.readString("Name: ");
                System.out.println("Types: MAGE, ASSASSIN, TANK, WARRIOR, SUPPORT, MARKSMAN");
                String typeStr = input.readString("Type: ");
                HeroType type;
                try {
                    type = HeroType.valueOf(typeStr.toUpperCase());
                } catch (IllegalArgumentException e) {
                    System.out.println("❌ Invalid hero type.");
                    break;
                }
                int atk = input.readIntInRange("Base Attack (1-200): ", 1, 200);
                int def = input.readIntInRange("Base Defense (1-200): ", 1, 200);
                int hp = input.readIntInRange("Base Health (100-10000): ", 100, 10000);
                String diff = input.readString("Difficulty (Easy/Medium/Hard): ");

                Hero newHero = new Hero(id, name, type, atk, def, hp, diff);
                if (dataManager.addHero(newHero)) {
                    System.out.println("✅ Hero added.");
                }
                break;
            case 3:
                String delId = input.readString("Enter Hero ID to delete: ");
                if (dataManager.deleteHero(delId)) {
                    System.out.println("✅ Hero deleted (also removed from all players).");
                } else {
                    System.out.println("Hero not found.");
                }
                break;
        }
        input.waitForEnter();
    }

    // ==================== Admin: Equipment Management ====================

    private void adminManageEquipment() {
        System.out.println("\n--- Manage Equipment ---");
        System.out.println("  1. List All Equipment");
        System.out.println("  2. Add New Equipment");
        System.out.println("  3. Delete Equipment");
        int choice = input.readIntInRange("Select: ", 1, 3);

        switch (choice) {
            case 1:
                for (Equipment e : dataManager.getAllEquipment()) {
                    System.out.println("  " + e);
                }
                break;
            case 2:
                String id = input.readString("New Equipment ID (e.g. E021): ");
                if (dataManager.getEquipmentById(id) != null) {
                    System.out.println("❌ Equipment ID already exists.");
                    break;
                }
                String name = input.readString("Name: ");
                System.out.println("Types: ATTACK, DEFENSE, MAGIC, MOVEMENT, JUNGLE, SUPPORT");
                String typeStr = input.readString("Type: ");
                EquipmentType type;
                try {
                    type = EquipmentType.valueOf(typeStr.toUpperCase());
                } catch (IllegalArgumentException e) {
                    System.out.println("❌ Invalid equipment type.");
                    break;
                }
                int atk = input.readIntInRange("Attack Bonus (0-500): ", 0, 500);
                int def = input.readIntInRange("Defense Bonus (0-500): ", 0, 500);
                int mag = input.readIntInRange("Magic Bonus (0-500): ", 0, 500);
                int hp = input.readIntInRange("Health Bonus (0-5000): ", 0, 5000);
                int price = input.readIntInRange("Price (0-10000): ", 0, 10000);
                String desc = input.readString("Description: ");

                Equipment eq = new Equipment(id, name, type, atk, def, mag, hp, price, desc);
                if (dataManager.addEquipment(eq)) {
                    System.out.println("✅ Equipment added.");
                }
                break;
            case 3:
                String delId = input.readString("Enter Equipment ID to delete: ");
                if (dataManager.deleteEquipment(delId)) {
                    System.out.println("✅ Equipment deleted (also unequipped from all heroes).");
                } else {
                    System.out.println("Equipment not found.");
                }
                break;
        }
        input.waitForEnter();
    }

    // ==================== Admin: Team Management ====================

    private void adminManageTeams() {
        System.out.println("\n--- Manage Teams ---");
        System.out.println("  1. List All Teams");
        System.out.println("  2. View Team Details");
        int choice = input.readIntInRange("Select: ", 1, 2);

        if (choice == 1) {
            for (Team t : dataManager.getAllTeams()) {
                System.out.println("  " + t);
            }
        } else {
            String id = input.readString("Enter Team ID: ");
            String result = searchService.searchTeamById(id);
            System.out.println(result != null ? result : "Team not found.");
        }
        input.waitForEnter();
    }

    // ==================== Admin: Match Management ====================

    private void adminManageMatches() {
        System.out.println("\n--- Manage Match Records ---");
        System.out.println("  1. List All Matches");
        System.out.println("  2. Add New Match");
        int choice = input.readIntInRange("Select: ", 1, 2);

        switch (choice) {
            case 1:
                System.out.println("\nAll Matches:");
                for (MatchRecord m : dataManager.getAllMatches()) {
                    System.out.printf("  [%s] %s vs %s — Winner: %s (%tF, %d min)\n",
                            m.getMatchId(), m.getTeamAId(), m.getTeamBId(),
                            m.getWinnerTeamId(), m.getMatchDate(), m.getDurationMinutes());
                }
                break;
            case 2:
                String mId = input.readString("New Match ID (e.g. M011): ");
                if (dataManager.getMatchById(mId) != null) {
                    System.out.println("❌ Match ID already exists.");
                    break;
                }
                System.out.println("Available Teams:");
                for (Team t : dataManager.getAllTeams()) {
                    System.out.printf("  [%s] %s\n", t.getId(), t.getName());
                }
                String teamA = input.readString("Team A ID: ");
                String teamB = input.readString("Team B ID: ");
                if (dataManager.getTeamById(teamA) == null
                        || dataManager.getTeamById(teamB) == null) {
                    System.out.println("❌ Invalid team ID.");
                    break;
                }
                System.out.print("Did Team A win? (y/n): ");
                String winner = input.readString("");
                MatchResult result = winner.equalsIgnoreCase("y")
                        ? MatchResult.WIN : MatchResult.LOSS;
                int dur = input.readIntInRange("Duration (minutes, 1-120): ", 1, 120);

                MatchRecord m = new MatchRecord(mId, teamA, teamB, result, new java.util.Date(), dur);
                if (dataManager.addMatch(m)) {
                    System.out.println("✅ Match record added.");
                }
                break;
        }
        input.waitForEnter();
    }

    // ==================== Helper methods ====================

    private String readOptionalString(String prompt) {
        System.out.print(prompt);
        return input.readString("").trim();
    }

    private int readOptionalInt(String prompt) {
        System.out.print(prompt);
        String s = input.readString("").trim();
        if (s.isEmpty()) return -1;
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
