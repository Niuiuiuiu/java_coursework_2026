# Project Plan: AI-Assisted Honor of Kings IMS

## 1. Project Goal

An information management system for Honor of Kings that allows users to:
- Search and view player, team, hero, and equipment information
- View leaderboards and match history
- Admins can manage all data; players can view and edit their own info
- All features accessed through a menu-driven console interface

Target users: Game players (view data) and system administrators (manage data).

## 2. Requirement Analysis

| # | Feature | How It Will Be Implemented |
|---|---------|---------------------------|
| 1 | Player Lookup | SearchService searches HashMap<String, Player> by ID or name |
| 2 | Team Overview | SearchService finds team, aggregates member stats |
| 3 | Hero Details | SearchService looks up hero, shows stats, owners, equipment |
| 4 | Equipment Statistics | RankingService sorts equipment by custom formula |
| 5 | Match History | Filter MatchRecord list by player/team, return last N |
| 6 | Leaderboard | RankingService sorts players by win rate/level/matches |
| 7 | Data Management | Admin-only menu: CRUD operations via GameDataManager |
| 8 | Authentication | AuthenticationService checks credentials, assigns role |

## 3. Java Concepts Used

| Concept | Expected Location |
|---------|-------------------|
| Inheritance | `Player` and `Admin` extend `Person` |
| Interface | `Authenticatable` for login, `Searchable` for lookup methods |
| Polymorphism | Store both Player and Admin as Person references |
| Encapsulation | All model fields private, accessed via getters/setters |
| Collections | `HashMap<String, Player>` for fast lookup, `ArrayList<MatchRecord>` |
| Exception Handling | Try-catch for file I/O, invalid input validation |
| File I/O | Save/load data to/from text/CSV files |
| Enums | `HeroType` (Mage, Assassin, Tank, etc.), `MatchResult` (WIN/LOSS), `EquipmentType` |

## 4. Class Design

### Model Classes (src/model/)

| Class | Type | Responsibility |
|-------|------|---------------|
| `Person` | Abstract | Base class: id, name, username, password |
| `Player` | Concrete | Extends Person: level, winRate, heroes, team |
| `Admin` | Concrete | Extends Person: admin-specific permissions |
| `Hero` | Class | Name, type, baseStats, compatibleEquipment |
| `Equipment` | Class | Name, type, stats, description |
| `Team` | Class | Name, members (List<Player>), creationDate |
| `MatchRecord` | Class | MatchId, participants, result, date, heroesPicked |

### Service Classes (src/service/)

| Class | Responsibility |
|-------|---------------|
| `GameDataManager` | Central data store, CRUD operations |
| `AuthenticationService` | Login/logout, role verification |
| `SearchService` | Player/team/hero/equipment search |
| `RankingService` | Leaderboard, equipment rankings |
| `FileStorageService` | Save/load data from files |

### Utility Classes (src/util/)

| Class | Responsibility |
|-------|---------------|
| `InputHelper` | Safe console input with validation |
| `DataInitializer` | Create initial hardcoded dataset |

## 5. UML Draft

```
                    ┌──────────────┐
                    │    Person    │  (abstract)
                    │ - id: String │
                    │ - name: Str  │
                    │ - username   │
                    │ - password   │
                    └──┬───────┬──┘
                       │       │
              ┌────────┘       └────────┐
              ▼                         ▼
       ┌────────────┐           ┌────────────┐
       │   Player   │           │   Admin    │
       │ - level    │           └────────────┘
       │ - winRate  │
       │ - heroes[] │───┐
       └────────────┘   │
              │         │ owns
              │    ┌────▼────┐
              │    │   Hero  │
              │    │ - name  │
              │    │ - type  │
              │    │ - stats │
              │    └────┬────┘
              │         │ uses
       ┌──────┴──┐ ┌────▼────────┐
       │  Team   │ │  Equipment  │
       │ - name  │ │ - name      │
       │ - play- │ │ - type      │
       │   ers[] │ │ - stats     │
       └─────────┘ └─────────────┘

              ┌────────────────┐
              │  MatchRecord   │
              │ - matchId      │
              │ - participants │
              │ - result       │
              │ - heroesPicked │
              │ - date         │
              └────────────────┘
```

## 6. Data Design

### Initial Dataset
- 3 teams × 5+ players each
- 10+ players × 3+ heroes each
- 15+ heroes × 2+ equipment each
- 20+ equipment items
- 10+ match records

### Data Storage
- Hardcoded during early development (DataInitializer)
- File-based persistence for final submission (FileStorageService)
- Format: CSV or JSON text files

## 7. AI Usage Plan

| Agent Role | Task | What AI Is Allowed To Help With |
|------------|------|--------------------------------|
| Architect Agent | Class design, UML, module planning | Suggest structure, review design decisions |
| Implementation Agent | Write Java methods | Generate specific methods, explain logic |
| Testing/Reviewer Agent | Find bugs, review code | Analyze code for bugs, suggest test cases |

All AI outputs will be reviewed, tested, and modified as needed before acceptance.

## 8. Development Timeline

| Stage | Tasks | Status |
|-------|-------|--------|
| Stage 1 | Read requirements, create repo, write plan.md | ✅ |
| Stage 2 | AI Architect review of class design | ⬜ |
| Stage 3 | Implement model classes + initial data | ⬜ |
| Stage 4 | Menu system + search features | ⬜ |
| Stage 5 | Authentication + admin/player permissions | ⬜ |
| Stage 6 | Persistence + ranking functions | ⬜ |
| Stage 7 | AI Testing/Review, fix bugs | ⬜ |
| Stage 8 | Documentation, reflection, final testing | ⬜ |

## 9. Testing Plan

See [docs/test-cases.md](docs/test-cases.md) for detailed test cases.

Key test areas:
1. Player lookup by ID and name
2. Team overview with stats
3. Hero details with equipment
4. Equipment ranking correctness
5. Match history filtering
6. Leaderboard sorting and tie-breaking
7. Admin CRUD operations
8. Player vs Admin permission enforcement
9. Login/logout functionality
10. File save/load data integrity

## 10. Risk Analysis

| Risk | Mitigation |
|------|------------|
| AI generates buggy code | Always test before integrating; write tests first |
| Scope creep | Stick to core features first; extras only after passing checklist |
| Data consistency | Centralize all mutations in GameDataManager |
| File I/O errors | Use try-catch, validate file format, provide fallback data |
| Git history too thin | Commit after every meaningful change; use proper prefixes |

## 11. Final Reflection Placeholder

*To be completed after implementation. See [ai/reflection.md](ai/reflection.md)*
