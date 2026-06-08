package util;

import model.*;
import java.util.*;

/**
 * Creates the initial hardcoded dataset for the Honor of Kings IMS.
 *
 * Dataset summary:
 *   - 3 teams
 *   - 12 players (each owns 3–4 heroes)
 *   - 15 heroes (each equipped with 2–3 equipment)
 *   - 20 equipment items
 *   - 10 match records
 */
public class DataInitializer {

    // ==================== Equipment ====================

    public static List<Equipment> createEquipment() {
        List<Equipment> list = new ArrayList<>();

        list.add(new Equipment("E001", "Shadow Blade",        EquipmentType.ATTACK,   90,   0,   0,    0, 1860, "Increases physical attack significantly."));
        list.add(new Equipment("E002", "Armor Piercing Bow",  EquipmentType.ATTACK,   40,  10,   0,    0, 2100, "Penetrates enemy armor."));
        list.add(new Equipment("E003", "Infinity Blade",      EquipmentType.ATTACK,  110,   0,   0,    0, 2140, "Massive critical damage boost."));
        list.add(new Equipment("E004", "Blood Drinker",       EquipmentType.ATTACK,   60,   0,   0,  300, 1740, "Lifesteal and durability."));
        list.add(new Equipment("E005", "Cursed Tome",         EquipmentType.MAGIC,     0,   0, 240,    0, 2100, "Greatly enhances magic power."));
        list.add(new Equipment("E006", "Void Staff",          EquipmentType.MAGIC,     0,   0, 180,    0, 2050, "Magic penetration item."));
        list.add(new Equipment("E007", "Holy Grail",          EquipmentType.MAGIC,     0,   0, 140,  300, 1930, "Mana regen and magic boost."));
        list.add(new Equipment("E008", "Sage Book",           EquipmentType.MAGIC,     0,   0, 200,    0, 2090, "Raw magic power for burst mages."));
        list.add(new Equipment("E009", "Thorn Armor",         EquipmentType.DEFENSE,   0,  40,   0,  800, 1950, "Reflects damage back to attackers."));
        list.add(new Equipment("E010", "Red Lotus Cloak",     EquipmentType.DEFENSE,   0, 240,   0, 1200, 1830, "Aura damage and high defense."));
        list.add(new Equipment("E011", "Guardian Armor",      EquipmentType.DEFENSE,   0, 180,   0,  600, 2010, "Balanced defensive stats."));
        list.add(new Equipment("E012", "Phoenix Eye",         EquipmentType.DEFENSE,   0, 120,   0, 1000, 2100, "Revives upon death (passive)."));
        list.add(new Equipment("E013", "Boots of Swiftness",  EquipmentType.MOVEMENT, 30,   0,   0,    0,  710, "Increases movement and attack speed."));
        list.add(new Equipment("E014", "Boots of Arcane",     EquipmentType.MOVEMENT,  0,   0,  60,    0,  730, "Magic penetration boots."));
        list.add(new Equipment("E015", "Boots of Defense",    EquipmentType.MOVEMENT,  0, 100,   0,    0,  730, "Physical defense boots."));
        list.add(new Equipment("E016", "Boots of Endurance",  EquipmentType.MOVEMENT,  0,   0,   0,  400,  720, "HP boost and tenacity."));
        list.add(new Equipment("E017", "Jungle Knife",        EquipmentType.JUNGLE,   30,   0,   0,    0,  700, "Bonus damage to jungle monsters."));
        list.add(new Equipment("E018", "Power Fist",          EquipmentType.ATTACK,   50,   0,   0,  200, 1500, "Mid-tier attack item."));
        list.add(new Equipment("E019", "Searing Dominator",   EquipmentType.SUPPORT,   0,   0,  80,  800, 1900, "Support aura for nearby allies."));
        list.add(new Equipment("E020", "Purification Charm",  EquipmentType.SUPPORT,   0,   0,   0,  600, 1820, "Removes crowd control effects."));

        return list;
    }

    // ==================== Heroes ====================

    public static List<Hero> createHeroes(List<Equipment> allEquipment) {
        List<Hero> list = new ArrayList<>();

        // --- Mage ---
        Hero h01 = new Hero("H001", "Angela",        HeroType.MAGE,     45, 15, 2800, "Easy");
        Hero h02 = new Hero("H002", "Daji",          HeroType.MAGE,     50, 12, 2600, "Easy");
        Hero h03 = new Hero("H003", "Zhuge Liang",   HeroType.MAGE,     55, 18, 3000, "Hard");

        // --- Assassin ---
        Hero h04 = new Hero("H004", "Li Bai",        HeroType.ASSASSIN, 80, 20, 3200, "Hard");
        Hero h05 = new Hero("H005", "Ake",           HeroType.ASSASSIN, 85, 15, 2900, "Medium");
        Hero h06 = new Hero("H006", "Han Xin",       HeroType.ASSASSIN, 75, 22, 3100, "Hard");

        // --- Tank ---
        Hero h07 = new Hero("H007", "Xiang Yu",      HeroType.TANK,     30, 80, 5000, "Easy");
        Hero h08 = new Hero("H008", "Zhuang Zhou",   HeroType.TANK,     25, 85, 5200, "Easy");
        Hero h09 = new Hero("H009", "Lu Bu",         HeroType.TANK,     40, 70, 4800, "Medium");

        // --- Warrior ---
        Hero h10 = new Hero("H010", "Zhao Yun",      HeroType.WARRIOR,  65, 50, 4000, "Easy");
        Hero h11 = new Hero("H011", "Dian Wei",      HeroType.WARRIOR,  70, 45, 4200, "Medium");
        Hero h12 = new Hero("H012", "Guan Yu",       HeroType.WARRIOR,  60, 55, 3800, "Hard");

        // --- Support ---
        Hero h13 = new Hero("H013", "Cai Wenji",     HeroType.SUPPORT,  20, 30, 3500, "Easy");

        // --- Marksman ---
        Hero h14 = new Hero("H014", "Hou Yi",        HeroType.MARKSMAN, 70, 25, 3000, "Medium");
        Hero h15 = new Hero("H015", "Marco Polo",    HeroType.MARKSMAN, 65, 20, 2800, "Hard");

        list.add(h01); list.add(h02); list.add(h03);
        list.add(h04); list.add(h05); list.add(h06);
        list.add(h07); list.add(h08); list.add(h09);
        list.add(h10); list.add(h11); list.add(h12);
        list.add(h13); list.add(h14); list.add(h15);

        // Equip heroes (at least 2 items each)
        equipHero(h01, allEquipment, "E001", "E005", "E008");
        equipHero(h02, allEquipment, "E005", "E006");
        equipHero(h03, allEquipment, "E007", "E008", "E005");
        equipHero(h04, allEquipment, "E001", "E003", "E013");
        equipHero(h05, allEquipment, "E001", "E013");
        equipHero(h06, allEquipment, "E003", "E017", "E013");
        equipHero(h07, allEquipment, "E009", "E010", "E015");
        equipHero(h08, allEquipment, "E010", "E011");
        equipHero(h09, allEquipment, "E009", "E012", "E018");
        equipHero(h10, allEquipment, "E018", "E004", "E015");
        equipHero(h11, allEquipment, "E001", "E009");
        equipHero(h12, allEquipment, "E003", "E016");
        equipHero(h13, allEquipment, "E019", "E020", "E016");
        equipHero(h14, allEquipment, "E001", "E002", "E013");
        equipHero(h15, allEquipment, "E002", "E013", "E004");

        return list;
    }

    private static void equipHero(Hero hero, List<Equipment> all, String... ids) {
        for (String id : ids) {
            for (Equipment e : all) {
                if (e.getId().equals(id)) {
                    hero.addEquipment(e);
                }
            }
        }
    }

    // ==================== Teams ====================

    public static List<Team> createTeams() {
        List<Team> list = new ArrayList<>();

        Team t1 = new Team("T001", "Dragon Warriors");
        t1.setTotalMatches(120);
        t1.setTotalWins(72);
        t1.addMember("P001");
        t1.addMember("P002");
        t1.addMember("P003");
        t1.addMember("P004");
        t1.addMember("P005");

        Team t2 = new Team("T002", "Shadow Elite");
        t2.setTotalMatches(110);
        t2.setTotalWins(68);
        t2.addMember("P006");
        t2.addMember("P007");
        t2.addMember("P008");
        t2.addMember("P009");

        Team t3 = new Team("T003", "Mythic Storm");
        t3.setTotalMatches(105);
        t3.setTotalWins(60);
        t3.addMember("P010");
        t3.addMember("P011");
        t3.addMember("P012");

        list.add(t1);
        list.add(t2);
        list.add(t3);
        return list;
    }

    // ==================== Players ====================

    public static List<Player> createPlayers(List<Hero> allHeroes) {
        List<Player> list = new ArrayList<>();

        // --- Dragon Warriors (T001) ---
        Player p01 = new Player("P001", "SkyKing",       "player1",  "pass123", 30, 200, 120);
        Player p02 = new Player("P002", "DragonSlayer",  "player2",  "pass123", 28, 180, 100);
        Player p03 = new Player("P003", "MoonLight",     "player3",  "pass123", 25, 150,  90);
        Player p04 = new Player("P004", "NightWalker",   "player4",  "pass123", 27, 170,  85);
        Player p05 = new Player("P005", "PhoenixFire",   "player5",  "pass123", 29, 190, 130);

        // --- Shadow Elite (T002) ---
        Player p06 = new Player("P006", "ShadowBlade",   "player6",  "pass123", 30, 220, 150);
        Player p07 = new Player("P007", "StormRider",    "player7",  "pass123", 26, 160,  88);
        Player p08 = new Player("P008", "FrostQueen",    "player8",  "pass123", 24, 140,  75);
        Player p09 = new Player("P009", "IronWall",      "player9",  "pass123", 28, 195, 110);

        // --- Mythic Storm (T003) ---
        Player p10 = new Player("P010", "SwiftArrow",    "player10", "pass123", 27, 185, 105);
        Player p11 = new Player("P011", "MysticMage",    "player11", "pass123", 25, 155,  92);
        Player p12 = new Player("P012", "BladeDancer",   "player12", "pass123", 29, 210, 135);

        // Assign team references
        p01.setTeamId("T001"); p02.setTeamId("T001"); p03.setTeamId("T001");
        p04.setTeamId("T001"); p05.setTeamId("T001");
        p06.setTeamId("T002"); p07.setTeamId("T002"); p08.setTeamId("T002");
        p09.setTeamId("T002");
        p10.setTeamId("T003"); p11.setTeamId("T003"); p12.setTeamId("T003");

        // Assign heroes to players (at least 3 each)
        addOwnedHeroes(p01, allHeroes, "H001", "H004", "H007", "H014");
        addOwnedHeroes(p02, allHeroes, "H002", "H005", "H010");
        addOwnedHeroes(p03, allHeroes, "H003", "H008", "H011");
        addOwnedHeroes(p04, allHeroes, "H006", "H012", "H015");
        addOwnedHeroes(p05, allHeroes, "H001", "H013", "H004");
        addOwnedHeroes(p06, allHeroes, "H004", "H006", "H005", "H009");
        addOwnedHeroes(p07, allHeroes, "H010", "H007", "H014");
        addOwnedHeroes(p08, allHeroes, "H001", "H002", "H003");
        addOwnedHeroes(p09, allHeroes, "H007", "H008", "H011");
        addOwnedHeroes(p10, allHeroes, "H014", "H015", "H012");
        addOwnedHeroes(p11, allHeroes, "H001", "H003", "H013");
        addOwnedHeroes(p12, allHeroes, "H004", "H005", "H006", "H010");

        list.add(p01); list.add(p02); list.add(p03); list.add(p04);
        list.add(p05); list.add(p06); list.add(p07); list.add(p08);
        list.add(p09); list.add(p10); list.add(p11); list.add(p12);

        return list;
    }

    private static void addOwnedHeroes(Player p, List<Hero> all, String... ids) {
        for (String id : ids) {
            for (Hero h : all) {
                if (h.getId().equals(id)) {
                    p.addHero(h);
                }
            }
        }
    }

    // ==================== Admins ====================

    public static List<Admin> createAdmins() {
        List<Admin> list = new ArrayList<>();
        list.add(new Admin("A001", "SuperAdmin", "admin", "admin123", "SuperAdmin"));
        return list;
    }

    // ==================== Match Records ====================

    public static List<MatchRecord> createMatches() {
        List<MatchRecord> list = new ArrayList<>();
        Calendar cal = Calendar.getInstance();

        // Match 1: Dragon Warriors vs Shadow Elite — Dragon wins
        cal.set(2026, Calendar.MAY, 10, 14, 0);
        MatchRecord m1 = new MatchRecord("M001", "T001", "T002", MatchResult.WIN, cal.getTime(), 22);
        m1.addHeroPicked("H001"); m1.addHeroPicked("H004"); m1.addHeroPicked("H010");
        m1.addHeroPicked("H005"); m1.addHeroPicked("H007"); m1.addHeroPicked("H014");
        list.add(m1);

        // Match 2: Shadow Elite vs Mythic Storm — Shadow wins
        cal.set(2026, Calendar.MAY, 12, 16, 30);
        MatchRecord m2 = new MatchRecord("M002", "T002", "T003", MatchResult.WIN, cal.getTime(), 28);
        m2.addHeroPicked("H004"); m2.addHeroPicked("H006"); m2.addHeroPicked("H009");
        m2.addHeroPicked("H014"); m2.addHeroPicked("H015"); m2.addHeroPicked("H013");
        list.add(m2);

        // Match 3: Mythic Storm vs Dragon Warriors — Dragon wins
        cal.set(2026, Calendar.MAY, 15, 20, 0);
        MatchRecord m3 = new MatchRecord("M003", "T003", "T001", MatchResult.LOSS, cal.getTime(), 25);
        m3.addHeroPicked("H014"); m3.addHeroPicked("H012"); m3.addHeroPicked("H001");
        m3.addHeroPicked("H007"); m3.addHeroPicked("H004"); m3.addHeroPicked("H001");
        list.add(m3);

        // Match 4: Dragon Warriors vs Mythic Storm — Dragon wins
        cal.set(2026, Calendar.MAY, 18, 19, 0);
        MatchRecord m4 = new MatchRecord("M004", "T001", "T003", MatchResult.WIN, cal.getTime(), 20);
        m4.addHeroPicked("H004"); m4.addHeroPicked("H014"); m4.addHeroPicked("H001");
        m4.addHeroPicked("H015"); m4.addHeroPicked("H012"); m4.addHeroPicked("H003");
        list.add(m4);

        // Match 5: Shadow Elite vs Dragon Warriors — Dragon wins
        cal.set(2026, Calendar.MAY, 20, 15, 0);
        MatchRecord m5 = new MatchRecord("M005", "T002", "T001", MatchResult.LOSS, cal.getTime(), 30);
        m5.addHeroPicked("H005"); m5.addHeroPicked("H006"); m5.addHeroPicked("H010");
        m5.addHeroPicked("H007"); m5.addHeroPicked("H004"); m5.addHeroPicked("H014");
        list.add(m5);

        // Match 6: Mythic Storm vs Shadow Elite — Mythic wins
        cal.set(2026, Calendar.MAY, 22, 18, 0);
        MatchRecord m6 = new MatchRecord("M006", "T003", "T002", MatchResult.WIN, cal.getTime(), 24);
        m6.addHeroPicked("H014"); m6.addHeroPicked("H015"); m6.addHeroPicked("H013");
        m6.addHeroPicked("H004"); m6.addHeroPicked("H005"); m6.addHeroPicked("H008");
        list.add(m6);

        // Match 7: Dragon Warriors vs Shadow Elite — Shadow wins
        cal.set(2026, Calendar.MAY, 25, 21, 0);
        MatchRecord m7 = new MatchRecord("M007", "T001", "T002", MatchResult.LOSS, cal.getTime(), 26);
        m7.addHeroPicked("H001"); m7.addHeroPicked("H010"); m7.addHeroPicked("H002");
        m7.addHeroPicked("H004"); m7.addHeroPicked("H009"); m7.addHeroPicked("H014");
        list.add(m7);

        // Match 8: Mythic Storm vs Dragon Warriors — Mythic wins
        cal.set(2026, Calendar.MAY, 28, 17, 0);
        MatchRecord m8 = new MatchRecord("M008", "T003", "T001", MatchResult.WIN, cal.getTime(), 23);
        m8.addHeroPicked("H015"); m8.addHeroPicked("H012"); m8.addHeroPicked("H003");
        m8.addHeroPicked("H007"); m8.addHeroPicked("H001"); m8.addHeroPicked("H004");
        list.add(m8);

        // Match 9: Shadow Elite vs Mythic Storm — Mythic wins
        cal.set(2026, Calendar.JUNE, 1, 19, 30);
        MatchRecord m9 = new MatchRecord("M009", "T002", "T003", MatchResult.LOSS, cal.getTime(), 27);
        m9.addHeroPicked("H006"); m9.addHeroPicked("H005"); m9.addHeroPicked("H011");
        m9.addHeroPicked("H014"); m9.addHeroPicked("H015"); m9.addHeroPicked("H001");
        list.add(m9);

        // Match 10: Dragon Warriors vs Shadow Elite — Dragon wins
        cal.set(2026, Calendar.JUNE, 5, 20, 0);
        MatchRecord m10 = new MatchRecord("M010", "T001", "T002", MatchResult.WIN, cal.getTime(), 21);
        m10.addHeroPicked("H004"); m10.addHeroPicked("H014"); m10.addHeroPicked("H010");
        m10.addHeroPicked("H005"); m10.addHeroPicked("H006"); m10.addHeroPicked("H007");
        list.add(m10);

        return list;
    }
}
