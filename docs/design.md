# Design Document

## Architecture Overview

The system follows a layered architecture:

```
┌─────────────────────────┐
│      Console UI (Main)  │  ← Menu-driven interface
├─────────────────────────┤
│     Service Layer       │  ← Business logic, search, ranking, auth
├─────────────────────────┤
│     Model Layer         │  ← Data classes (Person, Player, Hero, etc.)
├─────────────────────────┤
│     Util Layer          │  ← Input handling, data initialization
└─────────────────────────┘
```

## Class Relationships

### Inheritance
- `Person` (abstract) → `Player`
- `Person` (abstract) → `Admin`

### Composition/Aggregation
- A `Team` contains multiple `Player` objects (composition via team roster)
- A `Player` owns multiple `Hero` objects
- A `Hero` can use multiple `Equipment` items
- `MatchRecord` references Players and Heroes

### Interfaces
- `Authenticatable` — defines login/logout behavior
- `Searchable` — defines search-by-id and search-by-name methods

## Data Flow

1. User input → `InputHelper` (validation) → Service methods
2. Service methods → `GameDataManager` (data access)
3. `GameDataManager` → `FileStorageService` (persistence)
4. Results → Console output

## Design Decisions

| Decision | Rationale |
|----------|-----------|
| Console-first | Meets core requirements; GUI is extra credit |
| Centralized GameDataManager | Single source of truth for all CRUD operations |
| HashMap for player storage | O(1) lookup by ID |
| Java serialization for persistence | All model classes already Serializable; serializing entire GameDataManager is simpler than per-field CSV, preserves object graph |
| Binary .dat format | Avoids encoding issues with Chinese hero/equipment names; no external library needed |
| Interface in service package | Authenticatable and Searchable are service-level contracts, not model concerns; Person and SearchService implement them respectively |
| ID-based references (String) | Teams reference players by ID string rather than object reference — avoids circular dependency and makes serialization cleaner |
| Ranking tie-breaking | Multi-level Comparator chain (primary → secondary → tertiary) with documented rules |
