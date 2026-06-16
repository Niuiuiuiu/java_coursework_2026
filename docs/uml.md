# UML Class Diagram

```
                         ┌──────────────────────┐
                         │   <<interface>>       │
                         │   Authenticatable     │
                         │  (service package)    │
                         ├──────────────────────┤
                         │ + checkPassword()     │
                         │ + getUsername()       │
                         │ + getRole()           │
                         └──────────┬───────────┘
                                    │ implements
                    ┌───────────────┼───────────────┐
                    │               │               │
         ┌──────────▼──────────┐    │    ┌──────────▼──────────┐
         │      Person         │    │    │  <<interface>>      │
         │     (abstract)      │    │    │    Searchable       │
         ├─────────────────────┤    │    │ (service package)   │
         │ - id: String        │    │    ├─────────────────────┤
         │ - name: String      │    │    │ + searchById()      │
         │ - username: String  │    │    │ + searchByName()    │
         │ - password: String  │    │    └──────────┬──────────┘
         ├─────────────────────┤    │               │ implements
         │ + checkPassword()   │    │    ┌──────────▼──────────┐
         │ + getRole() {abstract}    │    │   SearchService     │
         └──────────┬──────────┘    │    ├─────────────────────┤
                    │               │    │ - data: GameDataMgr │
         ┌──────────┼──────────┐    │    │ + searchById()      │
         │          │          │    │    │ + searchByName()    │
┌────────▼───┐ ┌───▼──────────┐│    │    │ + searchPlayerById()│
│   Player   │ │    Admin     ││    │    │ + searchTeamById()  │
├────────────┤ ├──────────────┤│    │    │ + formatPlayerFull()│
│ - level    │ │ - role       ││    │    └─────────────────────┘
│ - matches  │ ├──────────────┤│    │
│   Played   │ │ + getRole()  ││    │
│ - matchesWon│ │   → "Admin" ││    │
│ - ownedHe- │ └──────────────┘│    │
│   roes:List│                 │    │
│ - teamId   │                 │    │
├────────────┤                 │    │
│ + getWin   │                 │    │
│   Rate()   │                 │    │
│ + getRole()│                 │    │
│   →"Player"│                 │    │
└──────┬─────┘                 │    │
       │ owns                  │    │
       │ 1..*                  │    │
┌──────▼──────┐                │    │
│    Hero     │                │    │
├─────────────┤                │    │
│ - name      │                │    │
│ - type:     │                │    │
│   HeroType  │                │    │
│ - baseAttack│                │    │
│ - baseDef   │                │    │
│ - baseHealth│                │    │
│ - difficulty│                │    │
│ - equipped  │ uses           │    │
│   Items     │ 2..6           │    │
└──────┬──────┘                │    │
       │                       │    │
┌──────▼──────────┐            │    │
│   Equipment     │            │    │
├─────────────────┤            │    │
│ - name          │            │    │
│ - type: EquipType│           │    │
│ - attackBonus   │            │    │
│ - defenseBonus  │            │    │
│ - magicBonus    │            │    │
│ - healthBonus   │            │    │
│ - price         │            │    │
│ - description   │            │    │
├─────────────────┤            │    │
│ + getComposite  │            │    │
│   Score()       │            │    │
└─────────────────┘            │    │
                               │    │
┌──────────────┐               │    │
│    Team      │               │    │
├──────────────┤               │    │
│ - name       │               │    │
│ - memberIds  │               │    │
│ - totalMatch │               │    │
│ - totalWins  │               │    │
├──────────────┤               │    │
│ + getWinRate │               │    │
│ + addMember  │               │    │
│ + removeMemb │               │    │
└──────┬───────┘               │    │
       │ participates          │    │
       │                       │    │
┌──────▼──────────┐            │    │
│   MatchRecord   │            │    │
├─────────────────┤            │    │
│ - teamAId       │            │    │
│ - teamBId       │            │    │
│ - resultForTeamA│            │    │
│ - heroIdsPicked │            │    │
│ - matchDate     │            │    │
│ - durationMin   │            │    │
├─────────────────┤            │    │
│ + getWinner     │            │    │
│   TeamId()      │            │    │
└─────────────────┘            │    │


<<enum>> HeroType: MAGE, ASSASSIN, TANK, WARRIOR, SUPPORT, MARKSMAN
<<enum>> MatchResult: WIN, LOSS
<<enum>> EquipmentType: ATTACK, DEFENSE, MAGIC, MOVEMENT, JUNGLE, SUPPORT


      ┌──────────────────────────────┐
      │       GameDataManager        │
      │    (implements Serializable) │
      ├──────────────────────────────┤
      │ - players: HashMap<String,Player>
      │ - admins: HashMap<String,Admin>
      │ - heroes: HashMap<String,Hero>
      │ - equipment: HashMap<String,Eq>
      │ - teams: HashMap<String,Team>
      │ - matchRecords: List<MatchRec>
      ├──────────────────────────────┤
      │ + CRUD for all entities      │
      │ + authenticate()             │
      │ + getTeamAverageLevel()      │
      │ + getTeamTopPlayer()         │
      └──────────────────────────────┘
```

## Key Relationships

| Relationship | Type | Description |
|-------------|------|-------------|
| Person → Player | Inheritance | Player extends abstract Person |
| Person → Admin | Inheritance | Admin extends abstract Person |
| Person → Authenticatable | Interface impl. | Person implements Authenticatable |
| SearchService → Searchable | Interface impl. | SearchService implements Searchable |
| Player → Hero | Association (1..*) | Player owns 3+ heroes |
| Hero → Equipment | Association (2..6) | Hero equips 2-6 items |
| Team → Player | Aggregation | Team contains members by ID |
| Team → MatchRecord | Association | Teams participate in matches |
