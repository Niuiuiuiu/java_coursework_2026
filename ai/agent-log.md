# Agent Log

Records of the three required AI agent roles used during development.

---

## Architect Agent

**Main contribution:**

Suggested the initial project folder structure (src/model/, src/service/, src/util/, docs/, ai/), the class dependency order for building model classes, and reviewed the overall OOP design. The Architect Agent helped plan:
- Which classes to create and in what order
- The inheritance hierarchy (Person → Player, Admin)
- Class relationships (Player owns Hero, Hero uses Equipment, Team contains Players)
- Enum design (HeroType, MatchResult, EquipmentType)
- The dependency build order: enums first → base classes → dependent classes

**Human decision:**

I accepted the overall structure because it matched the coursework requirements exactly. The dependency order made sense — building from bottom up (enums with no dependencies first, then classes that depend on those enums, etc.) avoids compilation errors. I noted that the AI's explanations helped me understand why each class should be built in a specific order.

**Related commits:**
- 868996d — initial project structure and plan
- 273cb80 — project structure pushed to remote
- 9793cb9 — model classes and data initializer

**Interface Design (Stage 6):**

Designed and created two Java interfaces to satisfy the coursework requirement:
- `Authenticatable` — defines the authentication contract: `checkPassword(String)`, `getUsername()`, `getRole()`. `Person` implements this interface, so all subclasses (Player, Admin) automatically satisfy it.
- `Searchable` — defines the search contract: `searchById(String)`, `searchByName(String)`. `SearchService` implements this interface, with the generic methods defaulting to player lookup.

Design decisions:
- `Authenticatable` is placed in `service/` since it defines a service-level contract
- `Person` implements `Authenticatable` because it already had all three required methods (`checkPassword`, `getUsername`, `getRole`)
- `Searchable` uses generic method names (`searchById`/`searchByName`) rather than entity-specific names, making it reusable for any entity type

**Human decision:**

I accepted both interfaces because they fit the existing code without requiring any method rewrites — Person already had `checkPassword`, `getUsername`, and `getRole`; SearchService already had entity-specific search methods. I chose to map `searchById`/`searchByName` to player lookup as the default, since player search is the most frequently used feature. This satisfies the Interface requirement in the Java Concepts checklist.

**Related commits:**
- (pending) — Authenticatable and Searchable interfaces

---

## Implementation Agent

**Main contribution:**

Wrote all 11 Java source files that form the foundation of the project:
- `HeroType.java`, `MatchResult.java`, `EquipmentType.java` — 3 enum classes
- `Person.java` — abstract superclass with id, name, username, password, and `getRole()` abstract method
- `Equipment.java` — equipment with attack/defense/magic/health bonuses and composite scoring
- `Hero.java` — hero with type, base stats, equipment management (max 6 items), total stat calculation
- `Player.java` — extends Person, adds level, win rate, owned heroes, team reference
- `Admin.java` — extends Person, adds admin role field
- `Team.java` — team with member ID list, match stats, win rate calculation
- `MatchRecord.java` — match between two teams with hero picks, result, date, duration
- `DataInitializer.java` — hardcoded dataset: 3 teams, 12 players, 15 heroes, 20 equipment, 10 match records using real Honor of Kings names

**Stage 4 — Service layer and Main menu:**

Wrote the complete service layer and console interface:
- `GameDataManager.java` — central data store: HashMap-backed CRUD for all entities, cascade delete (hero removal propagates to all player-owned lists), authentication method, team statistics (average level, top player)
- `SearchService.java` — formatted display for player/team/hero/equipment lookup and match history, with detailed formatting including hero equipment, team members, player owners
- `RankingService.java` — 4 leaderboard types (win rate, level, matches, custom score) with documented tie-breaking rules; 2 equipment ranking modes (composite score, usage count). Custom score formula: `WinRate × 0.4 + Level × 0.3 + Matches × 0.3`
- `InputHelper.java` — safe console input wrapper: readString, readInt, readIntInRange, readDouble, waitForEnter
- `Main.java` — full menu-driven console application: login system, player menu (7 options), admin menu (11 options). Admin can add/edit/delete players, heroes, equipment, teams, and matches.

All 16 Java files compiled successfully with zero errors.

Also wrote all documentation templates:
- `README.md` (8 sections matching Appendix C of the coursework)
- `plan.md` (11 sections covering all requirements)
- `docs/design.md`, `docs/test-cases.md`
- `.gitignore` for Java projects

**Human decision:**

I accepted the code because it compiles without errors and follows the plan's UML design. Key things I verified:
- All model fields are private (encapsulation)
- Inheritance is correct (Player and Admin extend Person)
- Player.getRole() returns "Player", Admin.getRole() returns "Admin" (polymorphism)
- Equipment has a getCompositeScore() method for future ranking features
- Hero.getTotalAttack/Defense/Health() calculate stat totals including equipment bonuses
- DataInitializer meets all minimum data requirements
- Stage 4: The program runs successfully — tested P001 lookup, SkyKing lookup, login as admin and player1. All 8 required features are accessible through the menu.
- Cascade deletes work: removing a Hero also removes it from all Players' owned hero lists.

I need to continue reviewing each class in detail to make sure I can explain every method.

**I also debugged a user error:** When I tried to search for P001 and SkyKing, I initially entered them at the wrong screen (Login instead of Player Lookup). The AI ran a diagnostic test proving the code works correctly, then showed me the correct flow: Login first → then Player Lookup.

**Related commits:**
- 9793cb9 — Stage 3: all model classes + DataInitializer
- 09e5706 — Stage 4: service classes + Main.java menu
- db63bcc — Stage 6: Authenticatable and Searchable interfaces
- (pending) — Stage 6: FileStorageService and file persistence

### File I/O Implementation (Stage 6):

Implemented data persistence using Java serialization:
- `FileStorageService.java` — save/load GameDataManager as a single binary file (`gamedata.dat`). Uses `ObjectOutputStream`/`ObjectInputStream` with try-with-resources for automatic resource cleanup. Handles missing files gracefully (returns null → falls back to initial dataset).
- `GameDataManager.java` — added `implements Serializable` (all contained model classes were already Serializable)
- `Main.java` integration — loads saved data on startup; auto-saves on logout and exit; added "Save Data to File" option (menu item 12) in admin menu

Design rationale: Since all model classes already implement Serializable (they had it since Stage 3), the simplest and most robust approach is to serialize the entire GameDataManager in one pass rather than writing per-entity CSV/JSON files. This avoids field-by-field parsing, preserves all object references, and correctly handles the HashMap/ArrayList internal structure.

**Human decision:**

I accepted this approach because it requires the least new code while still demonstrating meaningful File I/O. The serialization method naturally handles all edge cases (null teamId, empty hero lists) without manual field serialization. The auto-save on logout ensures data is never lost even if the user forgets to manually save.

---

## Testing / Reviewer Agent

**Main contribution:**

Reviewed all source files for bugs. Found 7 issues: addMatch never updated Team stats; formatMatchList had redundant logic; readOptionalString couldn't accept empty input; editPlayer lacked range validation; fragile subList; Scanner close risk. Fixed 3 issues that affected observable behavior.

**Human decision:**

I fixed the "leave blank" UX bug (added readOptionalString to InputHelper), made addMatch auto-update team totalMatches/totalWins, and added defensive copy for subList. Accepted reviewer's findings because Issues 2 and 4 were real bugs.

**Related commits:**
- 03e5ff9 — fix bugs found by Testing/Reviewer Agent

---

## Additional Notes

### Tools Used
- **Claude Code (Anthropic Claude)** — the main AI assistant used throughout all stages so far, accessed via VSCode extension
- **GitHub** — remote repository hosting at https://github.com/Niuiuiuiu/java_coursework_2026

### Prompt Strategy
I found that short, specific prompts in Chinese worked best (e.g., "person类中abstract是什么意思"). When I asked broad questions ("把目前我和你的所有对话存进去就行"), the AI was able to synthesize a comprehensive record. The AI also proactively asked clarifying questions when needed and confirmed actions before executing them (e.g., git push).

### Java Concept Learning (2026-06-09 to 2026-06-11)

The AI acted as a Java Tutor, explaining the following concepts in plain Chinese:

| Concept | What I Learned |
|---------|---------------|
| `abstract` class/method | Cannot be instantiated directly; subclasses must implement abstract methods |
| `implements` | Following an interface contract; different from `extends` (inheritance) |
| `Serializable` | A marker interface — a "save permission stamp" for file I/O |
| `serialVersionUID` | Version tag to prevent reading incompatible saved data |
| `equals()` / `hashCode()` | Object identity: same ID = same person; hash code for fast HashMap lookup |
| `List<E>` | An interface defining list behavior; `ArrayList` is one concrete implementation |
| Generics (`<Equipment>`) | Restricts a container to only hold a specific type |
| Enum structure | Fixed set of constants, each calling the constructor with display name |
| `displayName` | Human-readable name for enum values |
| `attackBonus` etc. | Equipment stat bonuses that contribute to total hero stats |
| Method parameter | Variable declared in method signature, receives value from caller |
| `String...` (varargs) | Variable-length argument — can pass any number of strings to a method |
| Nested for-each loops | Outer loop over target IDs, inner loop searches catalog for match |
| `private` method | Only accessible within the same class; used for internal helper methods |
| Inlay Hints (IDE) | Gray text showing parameter names in VSCode — not part of the code, just visual aid |
| Cascade delete | When deleting a Hero, also remove it from all Players' owned hero lists |

I reviewed all 11 Java source files and asked questions about every keyword and concept I didn't understand. This helped me satisfy the coursework requirement of being able to explain every class and method.
