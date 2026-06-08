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
- (pending) — model classes and data initializer

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

I need to continue reviewing each class in detail to make sure I can explain every method.

**Related commits:**
- (pending) — Stage 3: all model classes + DataInitializer

---

## Testing / Reviewer Agent

**Main contribution:**

Will be used later in Stage 7 to review code for bugs, test edge cases, and verify data consistency (e.g., deleting a Hero removes it from all Players' owned hero lists).

**Human decision:**

Not yet engaged. Planned for Stage 7 of the development timeline.

**Related commits:**
- (pending)

---

## Additional Notes

### Tools Used
- **Claude Code (Anthropic Claude)** — the main AI assistant used throughout all stages so far, accessed via VSCode extension
- **GitHub** — remote repository hosting at https://github.com/Niuiuiuiu/java_coursework_2026

### Prompt Strategy
I found that short, specific prompts in Chinese worked best (e.g., "person类中abstract是什么意思"). When I asked broad questions ("把目前我和你的所有对话存进去就行"), the AI was able to synthesize a comprehensive record. The AI also proactively asked clarifying questions when needed and confirmed actions before executing them (e.g., git push).
