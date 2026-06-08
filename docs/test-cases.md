# Test Cases

Minimum 10 test cases required. Each test must include: ID, function tested, input, expected output, actual output, and pass/fail.

---

## Test 01: Player Lookup by ID

- **Function Tested:** Player Lookup
- **Input:** Search player by ID: "P001"
- **Expected Output:** Display player name, team, level, win rate, owned heroes, and equipment
- **Actual Output:** [Fill after testing]
- **Result:** [ ] Pass / [ ] Fail
- **Bug Found:** [If any]

---

## Test 02: Player Lookup by Name

- **Function Tested:** Player Lookup
- **Input:** Search player name: "Li Bai"
- **Expected Output:** Display Li Bai's details (team, level, win rate, heroes)
- **Actual Output:** [Fill after testing]
- **Result:** [ ] Pass / [ ] Fail
- **Bug Found:** [If any]

---

## Test 03: Team Overview

- **Function Tested:** Team Overview
- **Input:** Search team by name: "Team Alpha"
- **Expected Output:** Display team name, all members, average level, total matches, win rate, top player
- **Actual Output:** [Fill after testing]
- **Result:** [ ] Pass / [ ] Fail
- **Bug Found:** [If any]

---

## Test 04: Hero Details

- **Function Tested:** Hero Details
- **Input:** Search hero by name: "Luna"
- **Expected Output:** Display Luna's type, base stats, compatible equipment, players who own her
- **Actual Output:** [Fill after testing]
- **Result:** [ ] Pass / [ ] Fail
- **Bug Found:** [If any]

---

## Test 05: Equipment Ranking

- **Function Tested:** Equipment Statistics
- **Input:** View equipment ranking by usage count
- **Expected Output:** Equipment list sorted by number of heroes using each item, descending
- **Actual Output:** [Fill after testing]
- **Result:** [ ] Pass / [ ] Fail
- **Bug Found:** [If any]

---

## Test 06: Match History — Last N Matches

- **Function Tested:** Match History
- **Input:** Retrieve last 5 matches for player "P001"
- **Expected Output:** Display 5 most recent matches with opponent, date, result, heroes picked
- **Actual Output:** [Fill after testing]
- **Result:** [ ] Pass / [ ] Fail
- **Bug Found:** [If any]

---

## Test 07: Leaderboard by Win Rate

- **Function Tested:** Leaderboard
- **Input:** Display top 5 players by win rate
- **Expected Output:** Top 5 players sorted by win rate descending; ties handled by level
- **Actual Output:** [Fill after testing]
- **Result:** [ ] Pass / [ ] Fail
- **Bug Found:** [If any]

---

## Test 08: Admin — Add New Player

- **Function Tested:** Data Management
- **Input:** Admin adds a new player: ID "P011", name "NewPlayer", team "Team Alpha"
- **Expected Output:** Player successfully added; appears in player list
- **Actual Output:** [Fill after testing]
- **Result:** [ ] Pass / [ ] Fail
- **Bug Found:** [If any]

---

## Test 09: Admin — Delete Hero (Cascade Check)

- **Function Tested:** Data Management
- **Input:** Admin deletes hero "Zhuang Zhou"
- **Expected Output:** Hero removed from system AND removed from all player-owned hero lists
- **Actual Output:** [Fill after testing]
- **Result:** [ ] Pass / [ ] Fail
- **Bug Found:** [If any]

---

## Test 10: Login — Invalid Credentials

- **Function Tested:** Authentication
- **Input:** Login with username "admin", password "wrongpassword"
- **Expected Output:** Error message "Invalid credentials"; access denied
- **Actual Output:** [Fill after testing]
- **Result:** [ ] Pass / [ ] Fail
- **Bug Found:** [If any]

---

## Test 11: Player Permission Restriction

- **Function Tested:** Authentication / Authorization
- **Input:** Logged in as "player1", attempt to delete another player
- **Expected Output:** Error "Permission denied"; operation blocked
- **Actual Output:** [Fill after testing]
- **Result:** [ ] Pass / [ ] Fail
- **Bug Found:** [If any]

---

## Test 12: File Save and Load

- **Function Tested:** File I/O / Data Persistence
- **Input:** Save current data to file, modify a player, then load data from file
- **Expected Output:** Data reverts to saved state after loading
- **Actual Output:** [Fill after testing]
- **Result:** [ ] Pass / [ ] Fail
- **Bug Found:** [If any]
